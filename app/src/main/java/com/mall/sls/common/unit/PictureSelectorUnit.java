package com.mall.sls.common.unit;

import android.app.Activity;
import android.content.Context;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.mall.sls.R;

import java.util.List;

/**
 * @author jwc on 2020/10/26.
 * 描述：
 */
public class PictureSelectorUnit {
    public static void loadImage(Context context, int position, List<LocalMedia> medias){
        PictureSelector.create((Activity) context)
                .themeStyle(R.style.picture_default_style)
                .isNotPreviewDownload(true)
                .loadImageEngine(GlideEngine.createGlideEngine())
                .openExternalPreview(position, medias);
    }
}
