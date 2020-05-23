package com.mall.sls.common.unit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.widget.Toast;

import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

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
            msg.thumbData = ImageUtils.bmpToByteArray(thumbBmp, true);  // 设置缩略图
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("imgshareappdata");
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

}
