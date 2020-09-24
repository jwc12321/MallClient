package com.mall.sls.bank.ui;

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
import com.mall.sls.bank.BankContract;
import com.mall.sls.bank.BankModule;
import com.mall.sls.bank.DaggerBankComponent;
import com.mall.sls.bank.presenter.UntieBankCardPresenter;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.BankUtil;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.BankCardInfo;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/9/9.
 * 描述：解绑银行卡
 */
public class UntieBankCardActivity extends BaseActivity implements BankContract.UntieBankCardView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.logo_iv)
    ImageView logoIv;
    @BindView(R.id.bank_name)
    MediumThickTextView bankName;
    @BindView(R.id.card_type)
    ConventionalTextView cardType;
    @BindView(R.id.card_number)
    MediumThickTextView cardNumber;
    @BindView(R.id.unbind_bt)
    MediumThickTextView unbindBt;
    @BindView(R.id.bank_rl)
    RelativeLayout bankRl;
    @BindView(R.id.pay_limit_tv)
    ConventionalTextView payLimitTv;
    @BindView(R.id.one_limit)
    ConventionalTextView oneLimit;
    @BindView(R.id.one_limit_ll)
    RelativeLayout oneLimitLl;
    @BindView(R.id.day_limit)
    ConventionalTextView dayLimit;
    @BindView(R.id.day_limit_ll)
    RelativeLayout dayLimitLl;

    private BankCardInfo bankCardInfo;
    private String bankId;
    @Inject
    UntieBankCardPresenter untieBankCardPresenter;

    public static void start(Context context, BankCardInfo bankCardInfo) {
        Intent intent = new Intent(context, UntieBankCardActivity.class);
        intent.putExtra(StaticData.BANK_CARD_INFO, bankCardInfo);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_untie_bank_card);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        bankCardInfo = (BankCardInfo) getIntent().getSerializableExtra(StaticData.BANK_CARD_INFO);
        if (bankCardInfo != null) {
            bankId = bankCardInfo.getId();
            GlideHelper.load(this, bankCardInfo.getIcon(), R.mipmap.app_icon, logoIv);
            bankName.setText(bankCardInfo.getBankName());
            if(TextUtils.equals(StaticData.CHU_XU,bankCardInfo.getCardType())){
                cardType.setText(getString(R.string.debit_card));
            }else {
                cardType.setText(getString(R.string.credit_card));
            }
            cardNumber.setText(BankUtil.lastFourDigits(bankCardInfo.getCardNo()));
            BankUtil.setBankBg(bankCardInfo.getBankCode(), bankRl);
            oneLimit.setText(NumberFormatUnit.goodsFormat(bankCardInfo.getOnceLimit()));
            dayLimit.setText(NumberFormatUnit.goodsFormat(bankCardInfo.getDayLimit()));
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


    @OnClick({R.id.back, R.id.unbind_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.unbind_bt://解除绑定
                untieBankCardPresenter.untieBankCard(bankId);
                break;
            default:
        }
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderUntieBankCard(Boolean isBoolean) {
        if (isBoolean) {
            showMessage(getString(R.string.untie_success));
            finish();
        }
    }

    @Override
    public void setPresenter(BankContract.UntieBankCardPresenter presenter) {

    }
}
