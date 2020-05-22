package com.mall.sls.common.unit;

import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;

import com.mall.sls.MainApplication;

public class TextViewttf {

    //平方简 常规体
    public static void setTextConventional(TextView textView){
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/Roboto-Medium-12.ttf");
        textView.setTypeface(typeFace1);
    }
    //平方简 中黑体
    public static void setTextMediumBlack(TextView textView){
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/Roboto-Bold-3.ttf");
        textView.setTypeface(typeFace1);
    }

    public static void setEditConventional(EditText editText){
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/Roboto-Medium-12.ttf");
        editText.setTypeface(typeFace1);
    }

    //平方简 中黑体
    public static void setEdittMediumBlack(EditText editText){
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/Roboto-Bold-3.ttf");
        editText.setTypeface(typeFace1);
    }
}
