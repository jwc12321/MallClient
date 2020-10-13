package com.mall.sls.common.unit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Looper;
import android.widget.Toast;

import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author jwc on 2020/5/23.
 * 描述：
 */
public class WXShareManager {
    private static final String APP_ID = StaticData.WX_APP_ID;

    private static final String SHARE_IMAGE_PATH = "0";
    private static final String SHARE_IMAGE_DATA = "1";

    private static WXShareManager manager;
    private static Context appcontext;

    public IWXAPI api;

    public WXShareManager(Context context) {
        appcontext = context;
        api = WXAPIFactory.createWXAPI(appcontext, APP_ID, true);
    }

    public static WXShareManager getInstance(Context context) {
        if (appcontext == null || !appcontext.equals(context) || manager == null) {
            appcontext = context;
            manager = new WXShareManager(context);
        }
        return manager;
    }

    private class WXShareThread extends Thread {

        private Object content;
        private String datatype;
        private boolean isTimeline; //发送到朋友圈还是微信好友;

        public WXShareThread(Object content, String datatype, boolean isTimeline) {
            this.content = content;
            this.datatype = datatype;
            this.isTimeline = isTimeline;
        }

        @Override
        public void run() {
            super.run();
            if (!hasPreHandleException(content)) {
                WXImageObject imgObj = null;
                Bitmap thumbBmp = null;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                switch (datatype) {
                    case SHARE_IMAGE_PATH:
                        imgObj = new WXImageObject();
                        imgObj.setImagePath((String) content);
                        thumbBmp = Bitmap.createScaledBitmap(
                                BitmapFactory.decodeFile((String) content, options), 150, 150, true);
                        break;
                    case SHARE_IMAGE_DATA:
                        imgObj = new WXImageObject();
                        imgObj.imageData = (byte[]) content;
                        thumbBmp = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(
                                (byte[]) content, 0, imgObj.imageData.length), 100, 100, true);
                        break;
                }
                sharePictureToWX(imgObj, thumbBmp, isTimeline);
            }
        }
    }

    /**
     * 预先检查
     *
     * @param content
     * @return
     */
    private boolean hasPreHandleException(Object content) {
        if (null == content) {//空指针异常，传过来的Object是空
            Looper.prepare();
            Toast.makeText(appcontext, R.string.no_data, Toast.LENGTH_SHORT).show();
            Looper.loop();
            return true;
        }
        if (!api.isWXAppInstalled()) {//微信没有安装
            Looper.prepare();
            Toast.makeText(appcontext, R.string.install_weixin, Toast.LENGTH_SHORT).show();
            Looper.loop();
            return true;
        }
        return false;
    }

    public void sharePictureToWX(Object content, String datatype, boolean isTimeline) {
        new WXShareThread(content, datatype, isTimeline).start();
    }

    /**
     * 分享图片 isTimeline 是否发送朋友圈，true为朋友圈，false为朋友圈好友
     */
    public void sharePictureToWX(WXImageObject mediaObject, Bitmap thumbBmp, boolean isTimeline) {
        WXMediaMessage msg = new WXMediaMessage();
        if (mediaObject != null) {
            msg.mediaObject = mediaObject;
        } else {
            return;
        }
        if (thumbBmp != null) {
            msg.thumbData = bitmapBytes(thumbBmp, 32);  // 设置缩略图
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = isTimeline ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 分享链接
     */
    public void shareUrlToWX(boolean isTimeline, String url, Bitmap thumbBmp, String title, String descroption) {
        //初始化一个WXWebpageObject填写url
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = url;
        //用WXWebpageObject对象初始化一个WXMediaMessage
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = title;
        msg.description = descroption;
        msg.setThumbImage(thumbBmp);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = isTimeline ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     *
     * @param bitmap
     * @param
     * @return
     */
    public static byte[] bitmapBytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb && options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }

    //分享小程序
    public void shareWXMini(String webPageUrl, String userName, String path, String title, String description, Bitmap thumbBmp) {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = webPageUrl; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_PREVIEW;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = userName;     // 小程序原始id
        miniProgramObj.path = path;            //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = description;               // 小程序消息desc
        if (thumbBmp != null) {
            msg.thumbData = bitmapBytes(thumbBmp, 330);
        }
        // 小程序消息封面图片，小于128k
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("miniProgram");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
        api.sendReq(req);
    }

    public static Bitmap drawWXMiniBitmap(Bitmap bitmap, int width, int height) {
        Bitmap mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 用这个Bitmap生成一个Canvas,然后canvas就会把内容绘制到上面这个bitmap中
        Canvas mCanvas = new Canvas(mBitmap);
        // 绘制画笔
        Paint mPicturePaint = new Paint();
        // 绘制背景图片
        mCanvas.drawBitmap(mBitmap, 0.0f, 0.0f, mPicturePaint);
        // 绘制图片的宽、高
        int width_head = bitmap.getWidth();
        int height_head = bitmap.getHeight();
        // 绘制图片－－保证其在水平方向居中
        mCanvas.drawBitmap(bitmap, (width - width_head) / 2, (height - height_head) / 2,
                mPicturePaint);
        // 保存绘图为本地图片
        mCanvas.save();
        mCanvas.restore();
        return mBitmap;
    }


    /**
     * 根据图片路径，把图片转为byte数组
     *
     * @return byte[]
     */
    public byte[] image2Bytes(String imgSrc) {
        FileInputStream fin;
        byte[] bytes = null;
        try {
            fin = new FileInputStream(new File(imgSrc));
            bytes = new byte[fin.available()];
            //将文件内容写入字节数组
            fin.read(bytes);
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bytes;
    }

    /**
     * 图片url转bitmap
     */
    private Bitmap getBitMBitmap(String urlpath) {
        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
            // TODO Auto-generated catch block
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    //图片转byteArray
    private byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }
}
