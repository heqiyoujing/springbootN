package com.example.springboot.httpClient;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: yiqq
 * @date: 2018/12/28
 * @description:
 */
public class Client {
    private static String OK = "200";
    /**post请求，传递参数为json，在httpPost内部将JSONObject转化为json字符串*/
    public static String httpPost(String url, JSONObject json){
        String result = null;
        /**创建一个post对象*/
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        /**设置请求超时时间*/
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(8000)
                .setSocketTimeout(10000)
                .build();
        httpPost.setConfig(requestConfig);
        /**设置请求头*/
        httpPost.setHeader("Content-type","application/json;charset=" + StandardCharsets.UTF_8);
        /**添加参数*/
        try {
            StringEntity entity = new StringEntity(json.toString(),"utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        /**执行post请求*/
        CloseableHttpResponse httpResponse = null;
        try{
            httpResponse = closeableHttpClient.execute(httpPost);
            if(OK.equals(httpResponse.getStatusLine().getStatusCode())){
                result = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if ( httpResponse != null )
                    httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**post请求方式，参数为map参数，请求类型为form-data*/
    public static String httpPost(String url, Map<String, String> params){
        String result = null;
        /**创建一个post对象*/
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        /**设置请求超时时间*/
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(8000)
                .setSocketTimeout(10000)
                .build();
        httpPost.setConfig(requestConfig);
        /**设置请求头*/
        httpPost.setHeader("Content-type","application/json;charset=" + StandardCharsets.UTF_8);
        //组织请求参数
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        if(params != null && params.size() > 0){
            Set<String> keySet = params.keySet();
            for(String key : keySet) {
                paramList.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(paramList, "utf-8"));
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /**执行post请求*/
        CloseableHttpResponse httpResponse = null;
        try{
            httpResponse = closeableHttpClient.execute(httpPost);
            if(OK.equals(httpResponse.getStatusLine().getStatusCode())){
                result = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if ( httpResponse != null )
                    httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**post请求，传递参数为json字符串*/
    public static String httpPost(String url, String json){
        String result = null;
        /**创建一个post对象*/
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        /**设置请求超时时间*/
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(8000)
                .setSocketTimeout(10000)
                .build();
        httpPost.setConfig(requestConfig);
        /**设置请求头*/
        httpPost.setHeader("Content-type","application/json;charset=" + StandardCharsets.UTF_8);

        try {
            httpPost.setEntity(new StringEntity(json));
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /**执行post请求*/
        CloseableHttpResponse httpResponse = null;
        try{
            httpResponse = closeableHttpClient.execute(httpPost);
            if(OK.equals(httpResponse.getStatusLine().getStatusCode())){
                result = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if ( httpResponse != null )
                    httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
