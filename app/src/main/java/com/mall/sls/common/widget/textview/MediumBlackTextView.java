package com.mall.sls.common.widget.textview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.mall.sls.MainApplication;

//苹方-简 中黑体
@SuppressLint("AppCompatCustomView")
public class MediumBlackTextView extends TextView{

    public MediumBlackTextView(Context context) {
        super(context);
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/medium_black.ttf");
        setTypeface(typeFace1);
    }

    public MediumBlackTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/medium_black.ttf");
        setTypeface(typeFace1);
    }

    public MediumBlackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/medium_black.ttf");
        setTypeface(typeFace1);
    }

    public MediumBlackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/medium_black.ttf");
        setTypeface(typeFace1);
    }
}
