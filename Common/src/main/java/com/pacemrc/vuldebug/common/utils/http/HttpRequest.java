package com.pacemrc.vuldebug.common.utils.http;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;

public class HttpRequest {

    private HttpClient httpClient;

    public Response getResponse() {
        return response;
    }

    private Response response;

    {
        this.response = new Response();
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public HttpRequest(String proxyHost, int proxyPort){

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        HttpHost proxy = new HttpHost(proxyHost, proxyPort);
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        httpClientBuilder.setDefaultRequestConfig(config);
        this.httpClient = httpClientBuilder.build();
    }

    public HttpRequest() {
        this.httpClient = HttpClientBuilder.create().build();
    }

    public void exec(HttpRequestBase httpRequest) throws IOException {

        HttpResponse response = this.httpClient.execute(httpRequest);
        Header[] allHeaders = response.getAllHeaders();
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity responseEntity = response.getEntity();
        String responseBody = responseEntity != null ? EntityUtils.toString(responseEntity, StandardCharsets.UTF_8) : null;
        this.response.setResponseBody(responseBody);
        this.response.setStatusCode(statusCode);
        this.response.setHeaders(allHeaders);

    }

    public Response sendGet(HttpGet httpGet) throws IOException {
        this.exec(httpGet);

        return response;
    }


    public Response sendByteArrayPost(HttpPost postRequest, byte[] bytes) throws IOException {

        ByteArrayEntity entity = new ByteArrayEntity(bytes);
        postRequest.setEntity(entity);
        this.exec(postRequest);

        return response;
    }

    public Response sendStringPost(HttpPost postRequest, String postBody) throws IOException {

        StringEntity stringEntity = new StringEntity(postBody);
        postRequest.setEntity(stringEntity);
        this.exec(postRequest);

        return response;

    }
    public Response sendFormFilePost(HttpPost postRequest, String filePath) throws IOException {

        File file = new File(filePath);
        String fileName = file.getName();

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                .addPart("file", new FileBody(file, ContentType.DEFAULT_BINARY, fileName))
                .addPart("description", new StringBody("File description", ContentType.TEXT_PLAIN));
        HttpEntity multipartEntity = multipartEntityBuilder.build();
        postRequest.setEntity(multipartEntity);
        this.exec(postRequest);

        return response;
    }
}
