package com.lee.wechat.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.lee.wechat.net.HttpRequestClient;
import com.lee.wechat.net.TLSSocketFactory;
import com.lee.wechat.net.intercept.RequestInterceptor;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;


/**
 * @Author: Lee
 * @CreateDate: 2020/9/7 21:25
 * @company:LeeStudio
 * @email: ltms2013@outlook.com
 * @Description: Glide参数初始化配置
 */
@GlideModule
public final class GlideInit extends AppGlideModule {

    /**
     * 磁盘缓存大小
     */
    private int diskSize = 1024 * 1024 * 100;
    /**
     * 取1/8最大内存作为最大缓存
     */
    private int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;

    /**
     * 关闭manifest的解析,避免添加相同的module
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    /**
     * 通过GlideBuilder设置默认的结构(Engine,BitmapPool ,ArrayPool,MemoryCache等等).
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 定义缓存大小和位置
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskSize));
        // sd卡中
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "glideCache", diskSize));
        // 自定义内存大小
        builder.setMemoryCache(new LruResourceCache(memorySize));
        // 自定义图片池大小
        builder.setBitmapPool(new LruBitmapPool(memorySize));

        RequestOptions options = new RequestOptions().format(DecodeFormat.PREFER_RGB_565);
        builder.setDefaultRequestOptions(options);
    }

    /**
     * 支持HTTPS图片加载
     * @param context
     * @param glide
     * @param registry
     */
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

      OkHttpClient  httpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
              .addInterceptor(new RequestInterceptor())
                .sslSocketFactory(new TLSSocketFactory(trustAllCert), trustAllCert)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                })
                .build();
        glide.getRegistry().replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(HttpRequestClient.getInstence().initHttpClient()));
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
