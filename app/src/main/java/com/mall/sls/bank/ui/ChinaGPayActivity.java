package com.mall.sls.bank.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.bank.BankContract;
import com.mall.sls.bank.BankModule;
import com.mall.sls.bank.DaggerBankComponent;
import com.mall.sls.bank.presenter.ChinaGPayPresenter;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.PhoneUnit;
import com.mall.sls.common.widget.textview.ColdDownButton;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.BankPayInfo;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author jwc on 2020/9/9.
 * 描述：
 */
public class ChinaGPayActivity extends BaseActivity implements BankContract.ChinaGPayView {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.phone_tip)
    ConventionalTextView phoneTip;
    @BindView(R.id.sms_code_tv)
    ConventionalTextView smsCodeTv;
    @BindView(R.id.sms_code_et)
    ConventionalEditTextView smsCodeEt;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.no_receive_bt)
    ConventionalTextView noReceiveBt;
    @BindView(R.id.send_v_code)
    ColdDownButton sendVCode;
    private String smsCode;
    private String phoneNumber;
    private String payId;
    private String chinaGTn;

    @Inject
    ChinaGPayPresenter chinaGPayPresenter;

    public static void start(Context context, String phoneNumber, String payId,String chinaGTn) {
        Intent intent = new Intent(context, ChinaGPayActivity.class);
        intent.putExtra(StaticData.PHONE_NUMBER, phoneNumber);
        intent.putExtra(StaticData.PAY_ID, payId);
        intent.putExtra(StaticData.CHINA_G_TN,chinaGTn);
        ((Activity)context).startActivityForResult(intent, RequestCodeStatic.CHINA_PAY_DETAIL);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_china_g_pay);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        phoneNumber = getIntent().getStringExtra(StaticData.PHONE_NUMBER);
        payId = getIntent().getStringExtra(StaticData.PAY_ID);
        chinaGTn = getIntent().getStringExtra(StaticData.CHINA_G_TN);
        phoneNumber = PhoneUnit.hidePhone(phoneNumber);
        content();
        sendVCode.startCold();
    }


    @OnTextChanged({R.id.sms_code_et})
    public void smsCodeEnable() {
        smsCode = smsCodeEt.getText().toString().trim();
        confirmBt.setEnabled(!TextUtils.isEmpty(smsCode));
    }


    private void content() {
        String str1 = "本次操作需要短信确认，请输入\n" + phoneNumber + "收到的短信验证码";
        SpannableString spannableString1 = new SpannableString(str1);
        spannableString1.setSpan(new StyleSpan(Typeface.BOLD), 14, 26, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.appText3)), 14, 26, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        phoneTip.setText(spannableString1);
        phoneTip.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void initializeInjector() {
        DaggerBankComponent.builder()
                .applicationComponent(getApplicationComponent())
                .bankModule(new BankModule(this))
                .build()
                .inject(this);
    }


    @OnClick({R.id.back, R.id.confirm_bt, R.id.no_receive_bt,R.id.send_v_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_bt:
                confirm();
                break;
            case R.id.no_receive_bt:
                ReceiveCodeTipActivity.start(this);
                break;
            case R.id.send_v_code://发送验证
                chinaGPayPresenter.chinaGSendCode(chinaGTn);
                break;
            default:
        }
    }

    private void confirm() {
        chinaGPayPresenter.chinaGPay(payId, smsCode, chinaGTn);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }


    @Override
    public void renderChinaGSendCode(Boolean isBoolean) {
        if(isBoolean){
            sendVCode.startCold();
        }
    }

    @Override
    public void renderChinaGPay(BankPayInfo bankPayInfo) {
        if(bankPayInfo!=null) {
            backResult(bankPayInfo.getStatus());
        }
    }

    @Override
    public void setPresenter(BankContract.ChinaGPayPresenter presenter) {

    }

    private void backResult(String result) {
        Intent backIntent = new Intent();
        backIntent.putExtra(StaticData.PAY_RESULT, result);
        setResult(Activity.RESULT_OK, backIntent);
        finish();
    }
}
