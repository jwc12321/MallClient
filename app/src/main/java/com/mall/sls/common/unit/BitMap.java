package com.mall.sls.common.unit;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author jwc on 2020/6/1.
 * 描述：
 */
public class BitMap {

    // 饿汉式
    private static BitMap instance = new BitMap();

    private BitMap() {
    }

    public static BitMap getInstance() {
        return instance;
    }

    /*
     *    get image from network
     *    @param [String]imageURL
     *    @return [BitMap]image
     */
    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


        /**
     * 根据 图片URL 转换成 Bitmap
     *
     * @param url
     * @return
     */
    public static void returnBitMap(Activity activity, final String url, final android.os.Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;
                Bitmap bitmap = null;

//                try {
//                    imageurl = new URL(url);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
                try {
//                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
//                    conn.setDoInput(true);
//                    conn.connect();
//                    InputStream is = conn.getInputStream();
//                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                   bitmap = Glide.with(activity)
                            .asBitmap()
                            .load(url)
                            .submit(360, 360)
                            .get();
//                    bitmap = Bitmap.createBitmap(mybitmap, 0, 0, mybitmap.getWidth(), mybitmap.getHeight());
                    Message message = handler.obtainMessage();
                    message.obj = bitmap;
                    message.arg1 = 1;
                    handler.sendMessage(message);

//                    is.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
