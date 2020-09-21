package com.lee.wechat.net;


import com.lee.wechat.net.intercept.RequestInterceptor;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/8 19:36
 * @company:
 * @email: ltms2013@outlook.com
 * @Description: Http请求客户端
 */

public class HttpRequestClient {

    protected static Retrofit.Builder retrofitBuilder;

    private static HttpRequestClient httpRequestClient;

    private OkHttpClient httpClient;

    /**
     * 请求基本url
     */
    protected String BASE_URL;
    /**
     * 连接超时时长(单位/秒)
     */
    private static long CONNECT_TIMEOUT = 10;
    /**
     * 请求超时时长(单位/秒)
     */
    private static long READ_TIMEOUT = 10;

    /**
     * 响应超时时长(单位/秒)
     */
    private static long WRITE_TIMEOUT = 10;

    /**
     * 请求拦截器
     */
    protected static RequestInterceptor requestInterceptor;


    /**
     * 单例获取Http客户端
     *
     * @return
     */
    public static HttpRequestClient getInstence() {
        if (httpRequestClient == null) {
            synchronized (HttpRequestClient.class) {
                httpRequestClient = new HttpRequestClient();
            }
        }
        return httpRequestClient;
    }


    /**
     * 初始化BaseUrl
     *
     * @param baseurl 请求接口主机参数
     */
    public HttpRequestClient init(String baseurl) {
        this.BASE_URL = baseurl;
        return this;
    }


    /**
     * 創建数据请求接口Service
     *
     * @param clazz 需要创建的Service clazz
     * @param <T>   数据请求接口定义Class对象
     * @return
     */
    public <T> T createApi(final Class<T> clazz) {
        return initRetrofit().create(clazz);
    }


    /**
     * 实例化Retrofit
     *
     * @return retrofitBuilder
     */
    public Retrofit initRetrofit() {
        if (retrofitBuilder == null) {
            retrofitBuilder = new Retrofit.Builder();
        }
        if (requestInterceptor == null) {
            requestInterceptor = new RequestInterceptor();
        }
        retrofitBuilder
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(initHttpClient());
        return retrofitBuilder.build();
    }

    /**
     * 初始化httpClient
     *
     * @return
     */
    public OkHttpClient initHttpClient() {
        if (httpClient == null) {
            httpClient = new OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)
                    .sslSocketFactory(new TLSSocketFactory(trustAllCert), trustAllCert)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String s, SSLSession sslSession) {
                            return true;
                        }
                    })
                    .build();
        }

        return httpClient;
    }

    /**
     * 定义一个信任所有证书的TrustManager
     */
    final X509TrustManager trustAllCert = new X509TrustManager() {
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    };


}
