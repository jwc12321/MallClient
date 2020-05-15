package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author jwc on 2020/5/14.
 * 描述：请填写备注
 */
public class FillRemarksActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.right_tv)
    MediumThickTextView rightTv;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.remark_et)
    EditText remarkEt;
    @BindView(R.id.word_count)
    ConventionalTextView wordCount;

    private String remark;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_remarks);
        ButterKnife.bind(this);
        setHeight(back,title,rightTv);
        initView();
    }

    private void initView(){
        remark=getIntent().getStringExtra(StaticData.REMARK);
        remarkEt.setText(remark);
        wordCount.setText(remark.length()+"/50");
    }

    @OnTextChanged({R.id.remark_et})
    public void checkRemarkEnable() {
        remark = remarkEt.getText().toString().trim();
        wordCount.setText(remark.length()+"/50");
    }


    @OnClick({R.id.back, R.id.right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.right_tv:
                confirm();
                break;
            default:
        }
    }

    private void confirm(){
        if(TextUtils.isEmpty(remark)){
            showMessage(getString(R.string.fill_remarks));
            return;
        }
        Intent backIntent = new Intent();
        backIntent.putExtra(StaticData.REMARK, remark);
        setResult(Activity.RESULT_OK, backIntent);
        finish();
    }



    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
