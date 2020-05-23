package com.mall.sls.common.unit;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * @author jwc on 2020/5/23.
 * 描述：
 */
public class ImageUtils {
    public static byte[] bmpToByteArray(Bitmap bmp, boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            recycleBitmap(bmp);
        }

        byte[] result = output.toByteArray();

        try {
            output.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return result;
    }

    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }
}
