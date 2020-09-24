package com.mall.sls.bank.ui;

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
import com.mall.sls.bank.presenter.ConfirmBindBankPresenter;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.PhoneUnit;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.BindResultInfo;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author jwc on 2020/9/9.
 * 描述：
 */
public class AuthenticationActivity extends BaseActivity implements BankContract.ConfirmBindBankView {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.phone_tip)
    ConventionalTextView phoneTip;
    @BindView(R.id.check_code_tv)
    ConventionalTextView checkCodeTv;
    @BindView(R.id.check_code_et)
    ConventionalEditTextView checkCodeEt;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.no_receive_bt)
    ConventionalTextView noReceiveBt;
    private String checkCode;
    private String phoneNumber;
    private String bankId;

    @Inject
    ConfirmBindBankPresenter confirmBindBankPresenter;

    public static void start(Context context, String phoneNumber, String bankId) {
        Intent intent = new Intent(context, AuthenticationActivity.class);
        intent.putExtra(StaticData.PHONE_NUMBER, phoneNumber);
        intent.putExtra(StaticData.BANK_ID, bankId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        phoneNumber = getIntent().getStringExtra(StaticData.PHONE_NUMBER);
        bankId = getIntent().getStringExtra(StaticData.BANK_ID);
        phoneNumber = PhoneUnit.hidePhone(phoneNumber);
        content();
    }


    @OnTextChanged({R.id.check_code_et})
    public void checkCodeEnable() {
        checkCode = checkCodeEt.getText().toString().trim();
        confirmBt.setEnabled(!TextUtils.isEmpty(checkCode));
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


    @OnClick({R.id.back, R.id.confirm_bt, R.id.no_receive_bt})
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
            default:
        }
    }

    private void confirm() {
        confirmBindBankPresenter.confirmBindBank(bankId, checkCode);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderConfirmBindBank(BindResultInfo bindResultInfo) {
        if (bindResultInfo != null) {
            if (TextUtils.equals(StaticData.BANK_PAY_FAILED, bindResultInfo.getBindStatus())) {
                showMessage(bindResultInfo.getMessage());
            } else {
                AddBankCardActivity.finishActivity();
                OperationResultActivity.start(this, bindResultInfo.getBindStatus());
                finish();
            }
        }
    }

    @Override
    public void setPresenter(BankContract.ConfirmBindBankPresenter presenter) {

    }
}
