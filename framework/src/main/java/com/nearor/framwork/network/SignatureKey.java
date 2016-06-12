package com.nearor.framwork.network;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.nearor.framwork.ValueObject;
import com.nearor.framwork.secure.Base64Encoder;
import com.nearor.framwork.secure.RSA;
import com.nearor.framwork.secure.Sec;
import com.nearor.framwork.util.Lg;


import java.io.IOException;

/**
 */
public class SignatureKey extends ValueObject {

    private static final String TAG = Lg.makeLogTag(SignatureKey.class);

    public static SignatureKey currentKey;

    /**
     * 加密的接口请求参数签名秘钥
     */
    @Expose
    private String ckey;
    /**
     * 解密后的秘钥
     */
    private String secret;
    /**
     * 服务器时间
     */
    @Expose
    private long stime;

    /**
     * 本地时间和服务器时间差值，stime - localtime，没错更新 stime 的时候必须更新该字段
     */
    private long deltaTime;

    public SignatureKey(String ckey, long stime) {
        this.ckey = ckey;
        this.stime = stime;
        this.deltaTime = stime - System.currentTimeMillis();
        this.secret = "";

        byte[] bytes = Base64Encoder.decode(ckey);
        try {
            this.secret = new String(RSA.decryptByPrivateKey(bytes, Sec.PRIVATE_KEY_FOR_API_SIGN));
        } catch (Exception e) {
            Lg.e(TAG, e.getMessage());
        }
    }

    public String getCkey() {
        return ckey;
    }

    public long getStime() {
        return stime;
    }

    /**
     * 接口签名时用到的时间戳
     */
    public long timestamp() {
        return (System.currentTimeMillis() + deltaTime) / 1000;
    }

    public String getSecret() {
        return secret;
    }

    public static class SignatureKeyAdapter extends TypeAdapter<SignatureKey> {

        @Override
        public SignatureKey read(JsonReader jsonReader) throws IOException {
            String ckey = "";
            long stime = 0;

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                if (name.equalsIgnoreCase("ckey")) {
                    ckey = jsonReader.nextString();
                } else if (name.equalsIgnoreCase("stime")) {
                    stime = jsonReader.nextLong();
                }
            }
            jsonReader.endObject();

            return new SignatureKey(ckey, stime);
        }

        @Override
        public void write(JsonWriter jsonWriter, SignatureKey signatureKey) throws IOException {
            jsonWriter.beginObject();
            jsonWriter.name("ckey").value(signatureKey.ckey);
            jsonWriter.name("stime").value(signatureKey.stime);
            jsonWriter.endObject();
        }
    }
}
