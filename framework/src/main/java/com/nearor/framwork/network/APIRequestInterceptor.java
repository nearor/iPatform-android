package com.nearor.framwork.network;

import android.text.TextUtils;


import com.nearor.framwork.app.ClientInfo;
import com.nearor.framwork.preference.GlobalValue;
import com.nearor.framwork.secure.DigestUtil;
import com.nearor.framwork.util.Lg;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


import okio.Buffer;

/**
 * HTTP 请求拦截器，对请求参数加签名
 */
class APIRequestInterceptor implements Interceptor {
    private static final String TAG = Lg.makeLogTag(APIRequestInterceptor.class);

    @Override
    public Response intercept(Chain chain) throws IOException {
        Lg.d(TAG, "Request: " + chain.request().toString());
        return chain.proceed(signatureRequest(chain.request()));
    }

    public static Request signatureRequest(Request request) throws IOException {
        Request.Builder builder = request.newBuilder();

        // 对 GET，POST 请求参数加签名信息
        if (request.method().equalsIgnoreCase("GET")) {
            Map<String, String> parmas = paramsFromGETRequest(request);
            // 原始参数加签名
            if (!parmas.containsKey("needSignature") || parmas.get("needSignature").equalsIgnoreCase("true")) {
                Map<String, String> securParams = secureParams(parmas);
                HttpUrl.Builder urlBuilder = request.httpUrl().newBuilder();
                for (Map.Entry<String, String> entry : securParams.entrySet()) {
                    urlBuilder.setQueryParameter(entry.getKey(), entry.getValue());
                }

                builder.url(urlBuilder.build());
            }
        } else if (request.method().equalsIgnoreCase("POST")) {
            // multipart 文件上传不需要签名
            if (RetrofitHelper.isFileUploadRequest(request)) {
                Map<String, String> parmas = paramsFromPOSTRequest(request);
                // 原始参数加签名
                if (!parmas.containsKey("needSignature") || parmas.get("needSignature").equalsIgnoreCase("true")) {
                    Map<String, String> securParams = secureParams(parmas);
                    StringBuilder bodyString = new StringBuilder();
                    for (Map.Entry<String, String> entry : securParams.entrySet()) {
                        bodyString.append(entry.getKey());
                        bodyString.append("=");
                        bodyString.append(URLEncoder.encode(entry.getValue(), "UTF8"));
                        bodyString.append("&");
                    }
                    RequestBody newBody = RequestBody.create(request.body().contentType(), bodyString.toString());

                    builder.post(newBody);
                }
            }
        }

        builder = builder.addHeader("clientInfo", ClientInfo.getInstance().toString());
        String token = GlobalValue.getInstance().getUserToken();
        token = TextUtils.isEmpty(token) ? "" : token;
        builder = builder.addHeader("userToken", token);

        return builder.build();
    }

    /**
     * 获取 GET 请求参数，只获取 query 形式的参数，如果有重名参数，则只获取第一个
     */
    public static Map<String, String> paramsFromGETRequest(Request request) throws IllegalArgumentException {
        if (!request.method().equalsIgnoreCase("GET")) {
            throw new IllegalArgumentException("Just for GET request");
        }

        Map<String, String> params = new HashMap<String, String>();
        Set<String> names = request.httpUrl().queryParameterNames();
        for (String name: names) {
            params.put(name, request.httpUrl().queryParameter(name));
        }
        return params;
    }

    public static Map<String, String> paramsFromPOSTRequest(Request request) throws IllegalArgumentException, IOException {
        if (!request.method().equalsIgnoreCase("POST")) {
            throw new IllegalArgumentException("Just for POST request");
        }

        Buffer buffer = new Buffer();
        request.body().writeTo(buffer);
        return parseQuery(buffer.readUtf8());
    }

    public static Map<String, String> parseQuery(String query) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();

        if (TextUtils.isEmpty(query)) {
            return params;
        }

        String[] pairs = query.split("&");

        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            if (idx != -1 && idx < pair.length()) {
                params.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
        }

        return params;
    }

    /**
     * 对构造好的请求参数Map进行签名，获得签名过的最终请求参数Map
     */
    public static Map<String, String> secureParams(Map<String, String> params) {

        String sec = DigestUtil.MD5;
        String secret = "";
        String timestamp = "0";
        if (SignatureKey.currentKey != null) {
            secret =SignatureKey.currentKey.getSecret();
            timestamp = String.valueOf(SignatureKey.currentKey.timestamp());
        }

        HashMap<String, String> secParams = new HashMap<>();
        if (params != null) {
            secParams.putAll(params);
        }

        secParams.put("signature_method", sec);
        secParams.put("timestamp", timestamp);
        secParams.put("trader", ClientInfo.TRADER);
        secParams.put("traderName", ClientInfo.TRADER_NAME);

        if (secParams.containsKey("signature")) {
            secParams.remove("signature");
        }
        String signature = getSignature(secParams, secret, sec);
        secParams.put("signature", signature);

        return secParams;
    }


    /**
     * 签名生成算法
     */
    public static String getSignature(HashMap<String, String> params, String secret, String secType) {
        TreeMap<String, String> sortedMap = new TreeMap<String, String>();
        Iterator<Map.Entry<String, String>> itr = params.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, String> item = itr.next();
            String value = item.getValue() == null ? "" : item.getValue();
            sortedMap.put(item.getKey().toLowerCase(Locale.US), value);
        }

        // 先将参数以其参数名的字典序升序进行排序
        Set<Map.Entry<String, String>> entrys = sortedMap.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
        basestring.append(secret);

        if (DigestUtil.MD5.equals(secType)) {
            return DigestUtil.md5Hex(basestring.toString());
        } else {
            return basestring.toString();
        }
    }

}
