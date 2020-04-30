package com.mall.sls.common.widget.textview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.mall.sls.MainApplication;

//苹方-简 常规体
@SuppressLint("AppCompatCustomView")
public class ConventionalEditTextView extends EditText {
    public ConventionalEditTextView(Context context) {
        super(context);
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/conventional.ttf");
        setTypeface(typeFace1);
    }

    public ConventionalEditTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/conventional.ttf");
        setTypeface(typeFace1);
    }

    public ConventionalEditTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/conventional.ttf");
        setTypeface(typeFace1);
    }
}
