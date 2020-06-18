package com.mall.sls.common;

import android.app.Activity;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GlideHelper {

    public static void load(Activity activity, String url, int placeHolder, ImageView target){
        if(activity!=null) {
                Glide.with(activity)
                        .load(url)
                        .error(placeHolder)
//                        .placeholder(placeHolder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .into(target);
        }
    }

    public static void load(Activity activity, String url, ImageView target){
        if(activity!=null) {
            Glide.with(activity)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(target);
        }
    }

}
