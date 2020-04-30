package com.mall.sls.common.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;

import com.mall.sls.R;
import com.mall.sls.common.widget.textview.ConventionalTextView;


public class LoadingDialog extends AlertDialog {


    private String tips;
    private Context context;
    private ConventionalTextView textView;
    public LoadingDialog(Context context,String tips) {
        super(context);
        this.context=context;
        this.tips=tips;
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_loading);
        textView=(ConventionalTextView) findViewById(R.id.text);
        if (TextUtils.equals("1",tips)){
            textView.setText(context.getString(R.string.signing_on));
        }else if(TextUtils.equals("2",tips)){
            textView.setText(context.getString(R.string.loading));
        }else if(TextUtils.equals("3",tips)){
            textView.setText(context.getString(R.string.processing));
        }
        setCanceledOnTouchOutside(false);
    }
}