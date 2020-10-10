package com.mall.sls.bank.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.bank.BankContract;
import com.mall.sls.bank.BankModule;
import com.mall.sls.bank.DaggerBankComponent;
import com.mall.sls.bank.adapter.BankCardManageAdapter;
import com.mall.sls.bank.presenter.BankCardSPresenter;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.TCAgentUnit;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.BankCardInfo;
import com.mall.sls.webview.ui.WebViewActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/9/9.
 * 描述：银行卡管理
 */
public class BankCardManageActivity extends BaseActivity implements BankContract.BankCardSView, BankCardManageAdapter.OnItemClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.no_record_ll)
    LinearLayout noRecordLl;
    @BindView(R.id.cooperate_bank_ll)
    LinearLayout cooperateBankLl;
    @BindView(R.id.confirm_bt)
    LinearLayout confirmBt;
    private BankCardManageAdapter bankCardManageAdapter;

    @Inject
    BankCardSPresenter bankCardSPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, BankCardManageActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_manage);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        bankCardManageAdapter = new BankCardManageAdapter(this);
        bankCardManageAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(bankCardManageAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bankCardSPresenter.getBankCardInfos();
        TCAgentUnit.pageStart(this, getString(R.string.bank_card_page));
    }

    @OnClick({R.id.back, R.id.confirm_bt, R.id.cooperate_bank_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_bt://添加银行卡
                AddBankCardActivity.start(this);
                break;
            case R.id.cooperate_bank_ll://合作银行
                WebViewActivity.start(this, StaticData.COOPERATE_BANK);
                break;
            default:
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

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderBankCardS(List<BankCardInfo> bankCardInfos) {
        if (bankCardInfos != null && bankCardInfos.size() > 0) {
            recordRv.setVisibility(View.VISIBLE);
            noRecordLl.setVisibility(View.GONE);
        } else {
            recordRv.setVisibility(View.GONE);
            noRecordLl.setVisibility(View.VISIBLE);
        }
        bankCardManageAdapter.setData(bankCardInfos);
    }

    @Override
    public void setPresenter(BankContract.BankCardSPresenter presenter) {

    }

    @Override
    public void UntieBankCard(BankCardInfo bankCardInfo) {
        UntieBankCardActivity.start(this, bankCardInfo);
    }

    @Override
    protected void onPause() {
        super.onPause();
        TCAgentUnit.pageEnd(this, getString(R.string.bank_card_page));
    }
}
