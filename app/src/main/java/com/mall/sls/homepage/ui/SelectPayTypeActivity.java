package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.MediumThickTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/11.
 * 描述：
 */
public class SelectPayTypeActivity extends BaseActivity {
    @BindView(R.id.pay_amount)
    MediumThickTextView payAmount;
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
    @BindView(R.id.item_rl)
    LinearLayout itemRl;
    @BindView(R.id.all_rl)
    RelativeLayout allRl;
    @BindView(R.id.close_iv)
    ImageView closeIv;

    private String choiceType;
    private String selectType="0";

    public static void start(Context context) {
        Intent intent = new Intent(context, SelectPayTypeActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pay_type);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        choiceType=getIntent().getStringExtra(StaticData.CHOICE_TYPE);
        confirmBt.setSelected(TextUtils.equals(StaticData.REFLASH_ONE,choiceType)?true:false);
        selectPayType();
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.all_rl, R.id.item_rl, R.id.close_iv, R.id.confirm_bt,R.id.select_weixin_iv,R.id.select_ali_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.all_rl:
            case R.id.close_iv:
                finish();
                break;
            case R.id.item_rl:
                break;
            case R.id.confirm_bt:
                Intent backIntent = new Intent();
                backIntent.putExtra(StaticData.SELECT_TYPE, selectType);
                setResult(Activity.RESULT_OK, backIntent);
                finish();
                break;
            case R.id.select_weixin_iv:
                selectType="0";
                selectPayType();
                break;
            case R.id.select_ali_iv:
                selectType="1";
                selectPayType();
                break;
            default:
        }
    }

    private void selectPayType(){
        selectWeixinIv.setSelected(TextUtils.equals(StaticData.REFLASH_ZERO,selectType));
        selectAliIv.setSelected(TextUtils.equals(StaticData.REFLASH_ONE,selectType));
    }
}
