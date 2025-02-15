package com.mall.sls.common.unit;

import android.text.TextUtils;
import android.util.Log;

import org.myapache.commons.codec.digest.DigestUtils;
import org.myapache.commons.codec.digest.HmacAlgorithms;
import org.myapache.commons.codec.digest.HmacUtils;

import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignUnit {
    private static String key = "123456";

    public static String Hmacsha256(String key, String data) throws Exception {

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");

        sha256_HMAC.init(secret_key);

        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();

        for (byte item : array) {

            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));

        }

        return sb.toString();
    }

    public static String signPost(String method, String body) {
        if (TextUtils.isEmpty(TimeManager.getTime())) {
            TimeManager.saveTime(FormatUtil.timeSecond());
        } else if (Long.parseLong(FormatUtil.timeSecond()) - Long.parseLong(TimeManager.getTime()) > 2) {
            TimeManager.saveTime(FormatUtil.timeSecond());
        }
        String postSignature;
        if (!TextUtils.equals("null", body)) {
            postSignature = "POST" + "\n" + method + "\n" + "null" + "\n" + DigestUtils.md5Hex(body) + "\n" + TimeManager.getTime();
        } else {
            postSignature = "POST" + "\n" + method + "\n" + "null" + "\n" + body + "\n" + TimeManager.getTime();
        }
        Log.e("111", "okhttp" + postSignature);
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmacHex(postSignature);
    }

    public static String signGet(String method, String queryString) {
        if (TextUtils.isEmpty(TimeManager.getTime())) {
            TimeManager.saveTime(FormatUtil.timeSecond());
        } else if (Long.parseLong(FormatUtil.timeSecond()) - Long.parseLong(TimeManager.getTime()) > 2) {
            TimeManager.saveTime(FormatUtil.timeSecond());
        }
        String postSignature = "GET" + "\n" + method + "\n" + queryString + "\n" + "null" + "\n" + TimeManager.getTime();
        Log.e("111", "okhttp" + postSignature);
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmacHex(postSignature);
    }

    public static String signDelete(String method, String queryString) {
        if (TextUtils.isEmpty(TimeManager.getTime())) {
            TimeManager.saveTime(FormatUtil.timeSecond());
        } else if (Long.parseLong(FormatUtil.timeSecond()) - Long.parseLong(TimeManager.getTime()) > 2) {
            TimeManager.saveTime(FormatUtil.timeSecond());
        }
        String postSignature = "DELETE" + "\n" + method + "\n" + queryString + "\n" + "null" + "\n" + TimeManager.getTime();
        Log.e("111", "okhttp" + postSignature);
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmacHex(postSignature);
    }

    private static String md5hex(String body) {
        String hexStr = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(body.getBytes("utf-8"));
            hexStr = bytesToHexString(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexStr;
    }

    public static String bytesToHexString(byte... src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
