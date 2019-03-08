package com.dpzx.okhttpmanager;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Create by xuqunxing on  2019/3/6
 */
public class BaseNetRequestManager {

    public static void initBaseNet(Context context) {

        ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
//        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .cookieJar(cookieJar1)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 请求get方法
     */
    public static void doGetRequest(Context context, String url, HashMap<String, String> headerParamsMap, Callback callback) {
        GetBuilder getBuilder = OkHttpUtils.get();
        if (null != headerParamsMap) {
            for (String key : headerParamsMap.keySet()) {
                getBuilder.addHeader(key, headerParamsMap.get(key));
            }
        }
        getBuilder.url(url)
//                .addParams("time", System.currentTimeMillis() + "")
                .build()
                .execute(callback);
    }

    /**
     * 请求post方法
     */
    public static void doPostRequest(Context context, String url, HashMap<String, String> headerParamsMap, String params, Callback callback) {
        PostStringBuilder postStringBuilder = OkHttpUtils.postString();
        if (null != headerParamsMap) {
            for (String key : headerParamsMap.keySet()) {
                postStringBuilder.addHeader(key, headerParamsMap.get(key));
            }
        }
        postStringBuilder.url(url)
                .mediaType(MediaType.parse("application/x-www-form-urlencoded"))
                .content(params)
                .build()
                .execute(callback);
    }

    public static void doPostFile(Context context, String url, Callback callback) {
        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        if (!file.exists()) {
            Toast.makeText(context, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpUtils
                .postFile()
                .url(url)
                .file(file)
                .build()
                .execute(callback);
    }
}
