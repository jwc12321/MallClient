package com.mall.sls.certify.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.widget.textview.MediumThickTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CerifyPayActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.weixin_iv)
    ImageView weixinIv;
    @BindView(R.id.select_weixin_iv)
    ImageView selectWeixinIv;
    @BindView(R.id.ali_iv)
    ImageView aliIv;
    @BindView(R.id.select_ali_iv)
    ImageView selectAliIv;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    private String selectType="1";

    public static void start(Context context) {
        Intent intent = new Intent(context, CerifyPayActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerify_pay);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        selectPayType();
    }


    @OnClick({R.id.confirm_bt, R.id.back,R.id.select_weixin_iv,R.id.select_ali_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                UploadIdActivity.start(this);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.select_weixin_iv:
                selectType="1";
                selectPayType();
                break;
            case R.id.select_ali_iv:
                selectType="2";
                selectPayType();
                break;
            default:
        }
    }

    private void selectPayType(){
        selectWeixinIv.setSelected(TextUtils.equals("1",selectType));
        selectAliIv.setSelected(TextUtils.equals("2",selectType));
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
