package com.mall.sls.bank.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.bank.BankContract;
import com.mall.sls.bank.BankModule;
import com.mall.sls.bank.DaggerBankComponent;
import com.mall.sls.bank.adapter.SelectBankCardAdapter;
import com.mall.sls.bank.presenter.BankCardSPresenter;
import com.mall.sls.common.StaticData;
import com.mall.sls.data.entity.BankCardInfo;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/9/11.
 * 描述：
 */
public class SelectBankCardActivity extends BaseActivity implements SelectBankCardAdapter.OnItemClickListener, BankContract.BankCardSView{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.confirm_bt)
    LinearLayout confirmBt;

    private SelectBankCardAdapter adapter;

    @Inject
    BankCardSPresenter bankCardSPresenter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bank_card);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bankCardSPresenter.getBankCardInfos();
    }


    @Override
    protected void initializeInjector() {
        DaggerBankComponent.builder()
                .applicationComponent(getApplicationComponent())
                .bankModule(new BankModule(this))
                .build()
                .inject(this);
    }


    private void initView(){
        adapter=new SelectBankCardAdapter(this);
        adapter.setOnItemClickListener(this);
        recordRv.setAdapter(adapter);
    }

    @OnClick({R.id.confirm_bt,R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                AddBankCardActivity.start(this);
                break;
            case R.id.back:
                finish();
                break;
            default:
        }
    }



    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void selectBankCard(BankCardInfo bankCardInfo) {
        Intent intent = new Intent();
        Bundle bundle=new Bundle();
        bundle.putSerializable(StaticData.BANK_CARD_INFO,bankCardInfo);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void renderBankCardS(List<BankCardInfo> bankCardInfos) {
        adapter.setData(bankCardInfos);
    }

    @Override
    public void setPresenter(BankContract.BankCardSPresenter presenter) {

    }
}
