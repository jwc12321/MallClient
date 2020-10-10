package com.mall.sls.bank.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.bank.BankContract;
import com.mall.sls.bank.BankModule;
import com.mall.sls.bank.DaggerBankComponent;
import com.mall.sls.bank.presenter.BankCardPayPresenter;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.BankUtil;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.BankCardInfo;
import com.mall.sls.data.entity.BankPayInfo;
import com.mall.sls.data.entity.UserPayInfo;
import com.mall.sls.data.request.BankPayRequest;
import com.mall.sls.webview.ui.WebViewActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/9/10.
 * 描述：
 */
public class BankCardPayActivity extends BaseActivity implements BankContract.BankCardPayView {


    @BindView(R.id.close_iv)
    ImageView closeIv;
    @BindView(R.id.order_no_tv)
    ConventionalTextView orderNoTv;
    @BindView(R.id.right_arrow_iv)
    ImageView rightArrowIv;
    @BindView(R.id.bank_card_no)
    ConventionalTextView bankCardNo;
    @BindView(R.id.need_pay)
    ConventionalTextView needPay;
    @BindView(R.id.cooperate_bank_rl)
    RelativeLayout cooperateBankRl;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.bank_pay_type_rl)
    RelativeLayout bankPayTypeRl;
    @BindView(R.id.add_iv)
    ImageView addIv;
    @BindView(R.id.cooperate_bank_tv)
    ConventionalTextView cooperateBankTv;
    @BindView(R.id.order_sn_rl)
    RelativeLayout orderSnRl;

    private String payAmount;
    private String payId;
    private String bankId;
    private BankCardInfo bankCardInfo;
    private String cardNo;
    private String cardType;
    private Boolean haveCard = false;
    private String result;
    private BankPayRequest request;
    private String mobile;
    private UserPayInfo userPayInfo;


    @Inject
    BankCardPayPresenter bankCardPayPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_pay);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        sActivityRef = new WeakReference<>(this);
        userPayInfo = (UserPayInfo) getIntent().getSerializableExtra(StaticData.USER_PAY_INFO);
        if (userPayInfo != null) {
            payId = userPayInfo.getId();
            payAmount = userPayInfo.getOrderPrice();
            orderSnRl.setVisibility(TextUtils.isEmpty(userPayInfo.getOrderSn()) ? View.GONE : View.VISIBLE);
            orderNoTv.setText(userPayInfo.getOrderSn());
        }
        needPay.setText(NumberFormatUnit.numberFormat(payAmount) + "元");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!haveCard) {
            bankCardPayPresenter.getBankCardInfos();
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerBankComponent.builder()
                .applicationComponent(getApplicationComponent())
                .bankModule(new BankModule(this))
                .build()
                .inject(this);
    }


    @OnClick({R.id.close_iv, R.id.confirm_bt, R.id.cooperate_bank_rl, R.id.bank_pay_type_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
                backResult(StaticData.BANK_PAY_CANCEL);
                break;
            case R.id.confirm_bt:
                confirm();
                break;
            case R.id.cooperate_bank_rl://合作银行
                WebViewActivity.start(this, StaticData.COOPERATE_BANK);
                break;
            case R.id.bank_pay_type_rl://选择支付银行卡
                selectBankCard();
                break;
            default:
        }
    }

    private void confirm() {
        if (TextUtils.isEmpty(bankId)) {
            showMessage(getString(R.string.select_bank_card));
            return;
        }
        request = new BankPayRequest();
        request.setBindId(bankId);
        request.setMobile(mobile);
        request.setPayId(payId);
        bankCardPayPresenter.baoFooSinglePay(request);
    }

    private void selectBankCard() {
        if (haveCard) {
            Intent intent = new Intent(this, SelectBankCardActivity.class);
            startActivityForResult(intent, RequestCodeStatic.SELECT_BANK_CARD);
        } else {
            AddBankCardActivity.start(this);
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderBankCardS(List<BankCardInfo> bankCardInfos) {
        if (bankCardInfos != null && bankCardInfos.size() > 0) {
            haveCard = true;
            confirmBt.setEnabled(true);
            bankCardInfo = bankCardInfos.get(0);
            inputBankInfo(bankCardInfo);
            addIv.setVisibility(View.GONE);
        } else {
            haveCard = false;
            bankCardNo.setText(getString(R.string.add_bank_card));
            confirmBt.setEnabled(false);
            addIv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void renderBankPayInfo(BankPayInfo bankPayInfo) {
        if (bankPayInfo != null) {
            if (TextUtils.equals(StaticData.BANK_PAY_FAILED, bankPayInfo.getStatus()) && !TextUtils.isEmpty(bankPayInfo.getMessage())) {
                showMessage(bankPayInfo.getMessage());
            }
            backResult(bankPayInfo.getStatus());
        }
    }

    @Override
    public void renderBaoFuCode() {

    }

    private void inputBankInfo(BankCardInfo bankCardInfo) {
        bankId = bankCardInfo.getId();
        if (TextUtils.equals(StaticData.CHU_XU, bankCardInfo.getCardType())) {
            cardType = getString(R.string.debit_card);
        } else {
            cardType = getString(R.string.credit_card);
        }
        cardNo = bankCardInfo.getBankName() + cardType + "(" + BankUtil.lastFourDigits(bankCardInfo.getCardNo()) + ")";
        mobile = bankCardInfo.getMobile();
        bankCardNo.setText(cardNo);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.SELECT_BANK_CARD:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        bankCardInfo = (BankCardInfo) bundle.getSerializable(StaticData.BANK_CARD_INFO);
                        inputBankInfo(bankCardInfo);
                    }
                    break;
                case RequestCodeStatic.BANK_DETAIL:
                    if (data != null) {
                        result = data.getStringExtra(StaticData.PAY_RESULT);
                        backResult(result);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private static WeakReference<BankCardPayActivity> sActivityRef;

    public static void finishActivity() {
        if (sActivityRef != null && sActivityRef.get() != null) {
            sActivityRef.get().finish();
        }
    }

    private void backResult(String result) {
        Intent backIntent = new Intent();
        backIntent.putExtra(StaticData.PAY_RESULT, result);
        setResult(Activity.RESULT_OK, backIntent);
        finish();
    }

    @Override
    public void setPresenter(BankContract.BankCardPayPresenter presenter) {

    }
}
