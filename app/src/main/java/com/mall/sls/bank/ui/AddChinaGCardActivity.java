package com.mall.sls.bank.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.bank.BankContract;
import com.mall.sls.bank.BankModule;
import com.mall.sls.bank.DaggerBankComponent;
import com.mall.sls.bank.presenter.AddChinaGCardPresenter;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.PermissionUtil;
import com.mall.sls.common.unit.PhoneUnit;
import com.mall.sls.common.unit.TCAgentUnit;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.CertifyInfo;
import com.mall.sls.data.request.ChinaGPrepayRequest;
import com.mall.sls.mainframe.ui.ScanActivity;
import com.mall.sls.webview.ui.WebViewActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author jwc on 2020/9/9.
 * 描述：添加银行卡
 */
public class AddChinaGCardActivity extends BaseActivity implements BankContract.AddChinaGCardView {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.cardholder_et)
    ConventionalEditTextView cardholderEt;
    @BindView(R.id.card_number_tv)
    ConventionalTextView cardNumberTv;
    @BindView(R.id.card_number_et)
    ConventionalEditTextView cardNumberEt;
    @BindView(R.id.bank_camera_iv)
    ImageView bankCameraIv;
    @BindView(R.id.id_card_et)
    ConventionalEditTextView idCardEt;
    @BindView(R.id.phone_number_tv)
    ConventionalTextView phoneNumberTv;
    @BindView(R.id.phone_number_et)
    ConventionalEditTextView phoneNumberEt;
    @BindView(R.id.bank_tip_iv)
    ImageView bankTipIv;
    @BindView(R.id.safe_code_line)
    View safeCodeLine;
    @BindView(R.id.safe_code_et)
    ConventionalEditTextView safeCodeEt;
    @BindView(R.id.safe_code_ll)
    LinearLayout safeCodeLl;
    @BindView(R.id.expire_date_line)
    View expireDateLine;
    @BindView(R.id.expire_date_et)
    ConventionalEditTextView expireDateEt;
    @BindView(R.id.expire_date_ll)
    LinearLayout expireDateLl;
    @BindView(R.id.choice_item)
    CheckBox choiceItem;
    @BindView(R.id.content)
    ConventionalTextView content;
    @BindView(R.id.checkbox_ll)
    LinearLayout checkboxLl;
    @BindView(R.id.next_bt)
    MediumThickTextView nextBt;
    @BindView(R.id.item_rl)
    RelativeLayout itemRl;
    private String cardName;
    private String cardNumber;
    private String idCardNumber;
    private String phoneNumber;
    private String safeCode;
    private String expireDate;
    private List<String> group;
    private String scanData;
    private static WeakReference<AddChinaGCardActivity> sActivityRef;
    private ChinaGPrepayRequest request;
    private String payId;
    private String result;


    @Inject
    AddChinaGCardPresenter addChinaGCardPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_china_g_card);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        group = new ArrayList<>();
        group.add(Manifest.permission_group.CAMERA);
        sActivityRef = new WeakReference<>(this);
        content();
        payId = getIntent().getStringExtra(StaticData.PAY_ID);
        addChinaGCardPresenter.getCertifyInfo();
    }

    private void content() {
        String agreement = getString(R.string.fast_payment_agreement);
        SpannableString spannableString1 = new SpannableString(agreement);
        spannableString1.setSpan(new ClickableSpan() {
            public void onClick(View widget) {
                WebViewActivity.start(AddChinaGCardActivity.this, StaticData.FAST_PAYMENT_AGREEMENT);

            }
        }, 7, agreement.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.backGround1)), 7, agreement.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        content.setText(spannableString1);
        content.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @OnTextChanged({R.id.cardholder_et})
    public void checkNameEnable() {
        cardName = cardholderEt.getText().toString().trim();
    }

    @OnTextChanged({R.id.card_number_et})
    public void checkCardNumberEnable() {
        cardNumber = cardNumberEt.getText().toString().trim();
    }

    @OnTextChanged({R.id.id_card_et})
    public void checkIdCardEnable() {
        idCardNumber = idCardEt.getText().toString().trim();
    }

    @OnTextChanged({R.id.phone_number_et})
    public void checkPhoneNumberEnable() {
        phoneNumber = phoneNumberEt.getText().toString().trim();
    }

    @OnTextChanged({R.id.safe_code_et})
    public void checkSafeCodeEnable() {
        safeCode = safeCodeEt.getText().toString().trim();
    }

    @OnTextChanged({R.id.expire_date_et})
    public void checkExpireDateEnable() {
        expireDate = expireDateEt.getText().toString().trim();
    }


    @OnClick({R.id.back, R.id.next_bt, R.id.bank_camera_iv, R.id.bank_tip_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                backResult(StaticData.BANK_PAY_CANCEL);
                break;
            case R.id.next_bt:
                confirm();
                break;
            case R.id.bank_camera_iv://扫描银行卡
                scanBankCard();
                break;
            case R.id.bank_tip_iv://提示
                PhoneTipActivity.start(this);
                break;
            default:
        }
    }

    private void scanBankCard() {
        if (requestRuntimePermissions(PermissionUtil.permissionGroup(group, null), RequestCodeStatic.REQUEST_CAMERA)) {
            ScanActivity.start(this, false, true);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.SCAN:
                    if (data != null) {
                        scanData = data.getStringExtra(StaticData.SCAN_DATA);
                        inputResult(scanData);
                    }
                    break;
                case RequestCodeStatic.CHINA_PAY_DETAIL:
                    if (data != null) {
                        result = data.getStringExtra(StaticData.PAY_RESULT);
                        backResult(result);
                    }
                    break;
                default:
            }
        }
    }

    private void inputResult(String scanData) {
        if (!TextUtils.isEmpty(scanData)) {
            cardNumber = scanData;
            cardNumberEt.setText(cardNumber);
        }
    }

    private void confirm() {
        TCAgentUnit.setEventId(this, getString(R.string.bank_card_add_click));
        if (TextUtils.isEmpty(cardName)) {
            showMessage(getString(R.string.input_cardholder_name));
            return;
        }
        if (TextUtils.isEmpty(cardNumber)) {
            showMessage(getString(R.string.input_cardholder_card_number));
            return;
        }
        if (TextUtils.isEmpty(idCardNumber)) {
            showMessage(getString(R.string.input_cardholder_id_card));
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            showMessage(getString(R.string.input_bank_phone_number));
            return;
        }
        if (!PhoneUnit.isPhone(phoneNumber)) {
            showMessage(getString(R.string.input_right_phone_number));
            return;
        }
        if (!choiceItem.isChecked()) {
            showMessage(getString(R.string.agree_fast_payment_agreement));
            return;
        }
        request = new ChinaGPrepayRequest();
        request.setAccNo(cardNumber);
        request.setExpired(expireDate);
        request.setCvv2(safeCode);
        request.setName(cardName);
        request.setIdCard(idCardNumber);
        request.setMobile(phoneNumber);
        request.setPayId(payId);
        addChinaGCardPresenter.chinaGPrepay(request);

    }

    @Override
    protected void initializeInjector() {
        DaggerBankComponent.builder()
                .applicationComponent(getApplicationComponent())
                .bankModule(new BankModule(this))
                .build()
                .inject(this);
    }


    @Override
    public View getSnackBarHolderView() {
        return itemRl;
    }

    public static void finishActivity() {
        if (sActivityRef != null && sActivityRef.get() != null) {
            sActivityRef.get().finish();
        }
    }


    @Override
    public void renderCertifyInfo(CertifyInfo certifyInfo) {
        if (certifyInfo != null) {
            if (!TextUtils.isEmpty(certifyInfo.getRealName())) {
                cardName = certifyInfo.getRealName();
                cardholderEt.setText(cardName);
            }
            if (!TextUtils.isEmpty(certifyInfo.getIdCard())) {
                idCardNumber = certifyInfo.getIdCard();
                idCardEt.setText(idCardNumber);
            }
            if (!TextUtils.isEmpty(certifyInfo.getMobile())) {
                phoneNumber = certifyInfo.getMobile();
                phoneNumberEt.setText(phoneNumber);
            }

        }
    }

    @Override
    public void renderChinaGPrepay(String tn) {
        ChinaGPayActivity.start(this, phoneNumber, payId, tn);
    }


    @Override
    public void setPresenter(BankContract.AddChinaGCardPresenter presenter) {

    }

    private void backResult(String result) {
        Intent backIntent = new Intent();
        backIntent.putExtra(StaticData.PAY_RESULT, result);
        setResult(Activity.RESULT_OK, backIntent);
        finish();
    }

}
