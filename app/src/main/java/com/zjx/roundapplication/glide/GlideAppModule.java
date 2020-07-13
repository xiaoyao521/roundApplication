package com.zjx.roundapplication.glide;

import android.content.Context;

import androidx.annotation.NonNull;


import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.zjx.roundapplication.SSLUtil;

import java.io.InputStream;
import java.security.cert.CertificateException;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;


/**
 * @作者 luckly
 * @创建日期 2020/6/16 14:58
 * @类描述 处理ssl证书问题，和自定义缓存路径
 */
@GlideModule
public class GlideAppModule extends AppGlideModule {

    /**
     * OkHttp 是一个底层网络库(相较于 Cronet 或 Volley 而言)，尽管它也包含了 SPDY 的支持。
     * OkHttp 与 Glide 一起使用可以提供可靠的性能，并且在加载图片时通常比 Volley 产生的垃圾要少。
     * 对于那些想要使用比 Android 提供的 HttpUrlConnection 更 nice 的 API，
     * 或者想确保网络层代码不依赖于 app 安装的设备上 Android OS 版本的应用，OkHttp 是一个合理的选择。
     * 如果你已经在 app 中某个地方使用了 OkHttp ，这也是选择继续为 Glide 使用 OkHttp 的一个很好的理由，就像选择其他网络库一样。
     * 添加 OkHttp 集成库的 Gradle 依赖将使 Glide 自动开始使用 OkHttp 来加载所有来自 http 和 https URL 的图片
     *
     * @param context
     * @param glide
     * @param registry
     */
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
        //添加拦截器到Glide
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(SSLUtil.getSslSocketFactory(null, null, null),getX509TrustManager());
        OkHttpClient okHttpClient = builder.build();
        //原来的是  new OkHttpUrlLoader.Factory()；
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));

    }

    /**
     * 获取x509TrustManager
     * 上一个方法包含了他
     *
     * @return
     */
    private static X509TrustManager getX509TrustManager() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
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
                    }
            };
            return (X509TrustManager) trustAllCerts[0];
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 完全禁用清单解析
     * 禁止解析Manifest文件
     * 主要针对V3升级到v4的用户，可以提升初始化速度，避免一些潜在错误
     *
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        //自定义缓存目录，磁盘缓存给150M 另外一种设置缓存方式
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "GlideImgCache", 150 * 1024 * 1024));
        //配置图片缓存格式 默认格式为8888
        builder.setDefaultRequestOptions(RequestOptions.formatOf(DecodeFormat.PREFER_ARGB_8888));
    }


}
