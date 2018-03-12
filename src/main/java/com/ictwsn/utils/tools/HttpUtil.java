package com.ictwsn.utils.tools;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-03-15.
 */
public class HttpUtil {

    public static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static PoolingHttpClientConnectionManager clientConnectionManager = null;
    private static CloseableHttpClient httpClient = null;
    private static RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
    private final static Object syncLock = new Object();
    private static Map<String, String> cookies;

    public HttpUtil() {
    }

    public HttpUtil(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    /**
     * 创建httpclient连接池并初始化
     */
    static {

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .build();
        clientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        clientConnectionManager.setMaxTotal(50);
        clientConnectionManager.setDefaultMaxPerRoute(25);
    }

    public static CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (syncLock) {
                if (httpClient == null) {
                    CookieStore cookieStore = new BasicCookieStore();
                    if (cookies != null) {
                        for (Map.Entry<String, String> entry : cookies.entrySet()) {
                            cookieStore.addCookie(new BasicClientCookie(entry.getKey(), entry.getValue()));
                        }
                    }
                    httpClient = HttpClients.custom().setConnectionManager(clientConnectionManager).setDefaultCookieStore(cookieStore).setDefaultRequestConfig(config).build();
                }
            }
        }
        return httpClient;
    }

    /**
     * get请求
     *
     * @param url
     * @param headers
     * @return
     */
    public static HttpEntity httpGet(String url, Map<String, Object> headers) {
        CloseableHttpClient httpClient = getHttpClient();
        URL url_ = null;
        URI uri = null;
        try {
            url_ = new URL(url);
            try {
                uri = new URI(url_.getProtocol(), null, url_.getHost(), url_.getPort(), url_.getPath(), url_.getQuery(), null);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println(uri);
        HttpRequest httpGet = new HttpGet(uri);
        if (headers != null && !headers.isEmpty()) {
            httpGet = setHeaders(headers, httpGet);
        }
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute((HttpGet) httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                return entity;
            } else {
                logger.info("请求错误，返回码:" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static HttpEntity httpGetByHttpClient(CloseableHttpClient httpClient, String url) {
        HttpRequest httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute((HttpGet) httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                return entity;
            } else {
                logger.info("合成错误！");
                logger.info("请求错误，返回码:" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url
     * @param headers
     * @return
     */
    public static HttpEntity httpPost(String url, Map<String, Object> headers) {
        CloseableHttpClient httpClient = getHttpClient();
        HttpRequest request = new HttpPost(url);
        if (headers != null && !headers.isEmpty()) {
            request = setHeaders(headers, request);
        }
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = (HttpPost) request;
            response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            return httpEntity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * post请求,使用json格式传参
     *
     * @param url
     * @param headers
     * @param jsonParam
     * @return
     */
    public static HttpEntity httpPost(String url, Map<String, Object> headers, String jsonParam) {
        CloseableHttpClient httpClient = getHttpClient();
        HttpRequest request = new HttpPost(url);
        if (headers != null && !headers.isEmpty()) {
            request = setHeaders(headers, request);
        }
        CloseableHttpResponse response = null;

        try {
            HttpPost httpPost = (HttpPost) request;
            StringEntity stringEntity = new StringEntity(jsonParam, "utf-8");
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);

            response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            return httpEntity;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * 使用表单键值对传参
     */
    public static Header[] postFormReturnHeaders(String url, Map<String, Object> headers, List<NameValuePair> data) {
        CloseableHttpClient httpClient = getHttpClient();
        HttpRequest request = new HttpPost(url);
        if (headers != null && !headers.isEmpty()) {
            request = setHeaders(headers, request);
        }
        CloseableHttpResponse response = null;
        UrlEncodedFormEntity uefEntity;
        try {
            HttpPost httpPost = (HttpPost) request;
            uefEntity = new UrlEncodedFormEntity(data, "UTF-8");
            httpPost.setEntity(uefEntity);
            // httpPost.setEntity(new StringEntity(data, ContentType.create("application/json", "UTF-8")));
            response = httpClient.execute(httpPost);
            return response.getAllHeaders();
//            System.out.println(response.getAllHeaders().toString());
//            Header[] headerss = response.getAllHeaders();
//            for(Header hea : headerss){
//                System.out.println(hea.getName()+"===="+hea.getValue());
//            }
//            HttpEntity entity = response.getEntity();
//            System.out.println(entity);
//            return entity;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * 设置请求头信息
     *
     * @param headers
     * @param request
     * @return
     */
    private static HttpRequest setHeaders(Map<String, Object> headers, HttpRequest request) {
        for (Map.Entry entry : headers.entrySet()) {
            if (!entry.getKey().equals("Cookie")) {
                request.addHeader((String) entry.getKey(), (String) entry.getValue());
            } else {
                Map<String, Object> Cookies = (Map<String, Object>) entry.getValue();
                for (Map.Entry entry1 : Cookies.entrySet()) {
                    request.addHeader(new BasicHeader("Cookie", (String) entry1.getValue()));
                }
            }
        }
        return request;
    }

    public static Map<String, String> getCookie(String url) {
        CloseableHttpClient httpClient = getHttpClient();
        HttpRequest httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute((HttpGet) httpGet);
            Header[] headers = response.getAllHeaders();
            Map<String, String> cookies = new HashMap<String, String>();
            for (Header header : headers) {
                cookies.put(header.getName(), header.getValue());
            }
            return cookies;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static String getContent(String url, String coding) {
        String content = null;
        HttpEntity httpEntity = HttpUtil.httpGet(url, null);
        try {
            if (httpEntity == null) {
                return null;
            }
            content = EntityUtils.toString(httpEntity, coding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void main(String[] args) {

    }

}
