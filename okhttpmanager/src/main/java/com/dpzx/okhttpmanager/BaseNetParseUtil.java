package com.dpzx.okhttpmanager;

import android.content.Context;

import java.util.HashMap;

/**
 * Create by xuqunxing on  2019/3/7
 */
public class BaseNetParseUtil {


    public static HashMap addCommmonPostRequestValue(Context context) {
        HashMap<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("client-info","5.0,Android,5.0.1");
        paramsMap.put("Accept-Encoding", "gzip, deflate");
        paramsMap.put("accept", "*/*");
        paramsMap.put("Accept-Language", "zh-CN,zh;q=0.9");
        paramsMap.put("User-Agent", BaseNetUtil.getUserAgent(context));
        paramsMap.put("imei", NetLibUtil.getIMEI(context));
        return paramsMap;
    }

    public static HashMap addCommmonGetRequestValue(Context context) {
        HashMap<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("client-info","5.0,Android,5.0.1");
        paramsMap.put("Accept-Encoding", "gzip, deflate");
        paramsMap.put("accept", "*/*");
        paramsMap.put("Accept-Language", "zh-CN,zh;q=0.9");
        paramsMap.put("User-Agent", BaseNetUtil.getUserAgent(context));
        paramsMap.put("imei", NetLibUtil.getIMEI(context));
        return paramsMap;
    }

    /**
     * 网络请求请求字段拼接方法
     */
    public static String parsePostData(String... values) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            if (i % 2 == 0) {
                if (i == 0) {
                    stringBuffer.append(value);
                } else {
                    stringBuffer.append("&" + value);
                }
            } else {
                stringBuffer.append("=" + value);
            }
        }
        return stringBuffer.toString();
    }

    public static String parseGetData(String... values) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            stringBuffer.append("/" + value);
        }
        return stringBuffer.toString();
    }

}
