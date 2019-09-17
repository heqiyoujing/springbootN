package com.example.springboot.httpClient;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: yiqq
 * @date: 2018/10/15
 * @description:
 */
public class HttpTest {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://qhyxpic.oss.kujiale.com/fpimgnew/2017/02/05/WJcvawr7_w4EjwAAAA8_800x800.jpg");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows N 6.1;Win64;x64)");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            System.out.println("ContentType:" + httpEntity.getContentType().getValue());
            InputStream inputStream = httpEntity.getContent();
            FileUtils.copyInputStreamToFile(inputStream,new File("D://copy.jpg"));
        }
        response.close();
        httpClient.close();
    }
}
