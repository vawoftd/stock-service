package com.vawo.foundation.demo.utils;


import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LJT on 16-11-28.
 * email: linjuntan@sensetime.com
 */
public class HttpUtil {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static final Charset UTF_8 = Charset.forName("utf-8");

    @SuppressWarnings("unchecked")
    private static void addBody(MultipartEntityBuilder builder, String key, Object value) {
        if (value instanceof List) {
            List<?> values = (List<?>) value;
            for (Object obj : values) {
                addBody(builder, key, obj);
            }
        } else if (value instanceof byte[]) {
            builder.addBinaryBody(key, (byte[]) value);
        } else if (value instanceof File) {
            builder.addBinaryBody(key, (File) value);
        } else if (value instanceof File[]) {
            File[] files = (File[]) value;
            for (int i = 0; i < files.length; i++) {
                FileBody fileBody = new FileBody(files[i]);
                builder.addPart(key, fileBody);
            }
        } else if (value instanceof Object[]) {
            Object[] sv = (Object[]) value;
            for (int i = 0; i < sv.length; i++) {
                builder.addTextBody(key, (String) sv[i]);
            }
        } else {
            builder.addTextBody(key, value.toString());
        }
    }

    private static void addMultipleBody(MultipartEntityBuilder builder, String key, Object value) {
        if (value instanceof List) {
            List<?> values = (List<?>) value;
            for (Object obj : values) {
                addBody(builder, key, obj);
            }
        } else if (value instanceof byte[]) {
            builder.addBinaryBody(key, (byte[]) value);
        } else if (value instanceof File) {
            builder.addBinaryBody(key, (File) value);
        } else if (value instanceof File[]) {
            File[] files = (File[]) value;
            for (int i = 0; i < files.length; i++) {
                FileBody fileBody = new FileBody(files[i]);
                builder.addPart(key, fileBody);
            }
        } else if (value instanceof String[]) {
            String[] psStrings = (String[]) value;
            key += "[]";
            for (String v : psStrings) {
                if (v.startsWith("{")) {
                    builder.addTextBody(key, v, ContentType.APPLICATION_JSON);
                } else {
                    builder.addTextBody(key, v);
                }
            }
        } else if (value instanceof Object[]) {
            Object[] sv = (Object[]) value;
            for (int i = 0; i < sv.length; i++) {
                builder.addTextBody(key, (String) sv[i]);
            }
        } else {
            if (value.toString().startsWith("{")) {
                builder.addTextBody(key, value.toString(), ContentType.APPLICATION_JSON);
            } else {
                builder.addTextBody(key, value.toString());
            }
        }
    }


    private static HttpPost buildBinaryPost(String url, Map<String, Object> params) {
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName("utf-8"));
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (params != null && params.size() > 0) {
            Object value;
            for (String key : params.keySet()) {
                value = params.get(key);
                addBody(builder, key, value);
            }
        }
        post.setConfig(RequestConfig.custom().setConnectTimeout(5000).build());
        post.setEntity(builder.build());
        return post;
    }

    private static HttpPost buildFeatureSearchPost(String url, Map<String, Object> params) {
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName("utf-8"));
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (params != null && params.size() > 0) {
            Object value;
            for (String key : params.keySet()) {
                value = params.get(key);
                if (value instanceof String[]) {
                    String[] psStrings = (String[]) value;
                    key += "[]";
                    for (String v : psStrings) {
                        if (v.startsWith("{")) {
                            builder.addTextBody(key, v, ContentType.APPLICATION_JSON);
                        } else {
                            builder.addTextBody(key, v);
                        }
                    }
                } else {
                    addBody(builder, key, value);
                }
            }
        }
        post.setConfig(RequestConfig.custom().setConnectTimeout(5000).build());
        post.setEntity(builder.build());
        return post;
    }

    private static HttpPost buildImagePost(String url, Map<String, Object> params) {
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (params != null && params.size() > 0) {
            Object value;
            for (String key : params.keySet()) {
                value = params.get(key);
                if (value instanceof byte[]) {
                    builder.addBinaryBody("imageData", (byte[]) value, ContentType.DEFAULT_BINARY, "image.jpg");
                } else {
                    addBody(builder, key, value);
                }
            }
        }
        post.setConfig(RequestConfig.custom().setConnectTimeout(5000).build());
        post.setEntity(builder.build());
        return post;
    }

    private static HttpPost buildImageVerifyPost(String url, Map<String, Object> params) {
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        builder.addBinaryBody("imageOne", (byte[]) params.get("src"), ContentType.DEFAULT_BINARY, "image1.jpg");
        builder.addBinaryBody("imageTwo", (byte[]) params.get("dest"), ContentType.DEFAULT_BINARY, "image2.jpg");
        post.setConfig(RequestConfig.custom().setConnectTimeout(5000).build());
        post.setEntity(builder.build());
        return post;
    }

    public static HttpResult doVerifyPost(String url, Map<String, Object> params) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = buildImageVerifyPost(url, params);
        CloseableHttpResponse response = null;
        HttpResult result = null;
        try {
            response = client.execute(post);
            result = getHttpResult(response);
        } catch (Exception e) {
            logger.debug(">>> post error url:" + url, e);
            logger.error(">>> post error url:" + url);
        } finally {
            close(client, response);
        }
        return result;
    }

    public static HttpResult doImagePost(String url, Map<String, Object> params) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = buildImagePost(url, params);
        CloseableHttpResponse response = null;
        HttpResult result = null;
        try {
            response = client.execute(post);
            result = getHttpResult(response);
        } catch (Exception e) {
            logger.debug(">>> post error url:" + url, e);
            logger.error(">>> post error url:" + url);
        } finally {
            close(client, response);
        }
        return result;
    }

    private static HttpPost buildHttpPost(String url, Map<String, String> params) throws Exception {
        if (StringUtils.isBlank(url)) {
            logger.error(">>>构建HttpPost时,url不能为null");
            throw new Exception("url is null.");
        }
        HttpPost post = new HttpPost(url);
        HttpEntity he = null;
        if (params != null) {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                formparams.add(new BasicNameValuePair(key, params.get(key)));
            }
            post.setEntity(new UrlEncodedFormEntity(formparams));
        }
        // 在RequestContent.process中会自动写入消息体的长度，自己不用写入，写入反而检测报错
        // setContentLength(post, he);
        return post;

    }

    public static HttpResult doBinaryPost(String url, Map<String, Object> params) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = buildBinaryPost(url, params);
        CloseableHttpResponse response = null;
        HttpResult result = null;
        try {
            response = client.execute(post);
            result = getHttpResult(response);
        } catch (Exception e) {
            logger.debug(">>> post error url:" + url, e);
            logger.error(">>> post error url:" + url);
        } finally {
            close(client, response);
        }
        return result;
    }

    /**
     * 1:N特征搜索
     */
    public static HttpResult doFeatureSearchPost(String url, Map<String, Object> params) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = buildFeatureSearchPost(url, params);
        CloseableHttpResponse response = null;
        HttpResult result = null;
        try {
            response = client.execute(post);
            result = getHttpResult(response);
        } catch (Exception e) {
            logger.debug(">>> post error url:" + url, e);
            logger.error(">>> post error url:" + url);
        } finally {
            close(client, response);
        }
        return result;
    }

    public static HttpResult doPost(String url, Map<String, String> params) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = buildHttpPost(url, params);
        CloseableHttpResponse response = null;
        HttpResult result = null;
        try {
            response = client.execute(post);
            result = getHttpResult(response);
        } catch (Exception e) {
            logger.debug(">>> post error url:" + url, e);
            logger.error(">>> post error url:" + url);
        } finally {
            close(client, response);
        }
        return result;
    }

    public static HttpResult doPost(String url, byte[] data) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "text/xml;charset=UTF-8");
        HttpEntity entity = new ByteArrayEntity(data);
        post.setEntity(entity);
        CloseableHttpResponse response = null;
        HttpResult result = null;
        try {
            response = client.execute(post);
            result = getHttpResult(response);
        } catch (Exception e) {
            logger.debug(">>> post error url:" + url, e);
            logger.error(">>> post error url:" + url);
        } finally {
            close(client, response);
        }
        return result;
    }

    private static HttpResult getHttpResult(CloseableHttpResponse response) throws IOException {
        if (response != null) {
            HttpEntity entity = response.getEntity();
            HttpResult httpResult = new HttpResult();
            httpResult.setStatus(response.getStatusLine().getStatusCode());
            httpResult.setData(EntityUtils.toString(entity));
            EntityUtils.consume(entity);
            return httpResult;
        }
        return null;
    }

    private static void close(CloseableHttpClient client, CloseableHttpResponse response) {
        try {
            if (response != null)
                response.close();
            client.close();
        } catch (Exception e) {
            logger.debug(">>> close response or client error", e);
            logger.error(">>> close response or client error");
        }
    }

    public static HttpResult doGet(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
//        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        CloseableHttpResponse response = null;
        HttpResult result = null;
        try {
            response = client.execute(get);
            result = getHttpResult(response);
        } catch (Exception e) {
            logger.error(">>> doGet request failed, url:{}, message:{}", url, e.getMessage());
        } finally {
            close(client, response);
        }
        return result;
    }

    public static void download(String url, String dest) {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream inputStream = entity.getContent();
                //System.out.println(vipPhoto.GetName());
                File tmp = new File(new String(dest.getBytes()));
                if (tmp.exists()) {
                    return;
                }
                tmp.createNewFile();
                FileOutputStream out = new FileOutputStream(tmp);
                byte[] buff = new byte[1024];
                int size = -1;
                while ((size = inputStream.read(buff)) != -1) {
                    out.write(buff, 0, size);
                }
                out.close();
                inputStream.close();
            }
            response.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据URL下载图片
     *
     * @param url
     * @return
     */
    public static byte[] downloadImage(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        byte[] dataBuffer = null;
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                HttpEntity entity = response.getEntity();
                InputStream input = entity.getContent();
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                IOUtils.copy(input, output);
                output.flush();
                input.close();
                dataBuffer = output.toByteArray();
                output.close();
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataBuffer;
    }

    /**
     * build getUrl str
     *
     * @param url
     * @param params
     * @return
     */
    public static String buildUrl(String url, Map<String, String> params) {
        StringBuffer uriStr = new StringBuffer(url);
        if (params != null) {
            List<NameValuePair> ps = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                ps.add(new BasicNameValuePair(key, params.get(key)));
            }
            uriStr.append("?");
            uriStr.append(URLEncodedUtils.format(ps, UTF_8));
        }
        return uriStr.toString();
    }
}
