package com.mall.sls.address.ui;

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
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.address.AddressContract;
import com.mall.sls.address.AddressModule;
import com.mall.sls.address.DaggerAddressComponent;
import com.mall.sls.address.adapter.AddressManageAdapter;
import com.mall.sls.address.presenter.AddressManagePresenter;
import com.mall.sls.certify.ui.CerifyPayActivity;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.AddressInfo;
import com.mall.sls.homepage.ui.ConfirmOrderActivity;
import com.mall.sls.order.ui.GoodsOrderDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/8.
 * 描述：地址管理
 */
public class AddressManageActivity extends BaseActivity implements AddressContract.AddressManageView ,AddressManageAdapter.OnItemClickListener{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.right_tv)
    MediumThickTextView rightTv;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.address_rv)
    RecyclerView addressRv;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.no_address_ll)
    LinearLayout noAddressLl;

    private AddressManageAdapter addressManageAdapter;
    @Inject
    AddressManagePresenter addressManagePresenter;

    private String choiceType;//0:下单界面过来 1：我的界面过来
    private AddressInfo addressInfo;
    private String addressId;

    public static void start(Context context,String choiceType) {
        Intent intent = new Intent(context, AddressManageActivity.class);
        intent.putExtra(StaticData.CHOICE_TYPE,choiceType);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manage);
        ButterKnife.bind(this);
        setHeight(back,title,rightTv);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressManagePresenter.getAddressInfo();
    }

    private void initView(){
        choiceType=getIntent().getStringExtra(StaticData.CHOICE_TYPE);
        if(TextUtils.equals(StaticData.REFLASH_ZERO,choiceType)){
            title.setText(getString(R.string.select_address));
        }else {
            title.setText(getString(R.string.my_address));
        }
        addressManageAdapter=new AddressManageAdapter(this);
        addressManageAdapter.setOnItemClickListener(this);
        addressRv.setAdapter(addressManageAdapter);
    }

    @Override
    protected void initializeInjector() {
        DaggerAddressComponent.builder()
                .applicationComponent(getApplicationComponent())
                .addressModule(new AddressModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.confirm_bt, R.id.back,R.id.right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
            case R.id.right_tv:
                Intent intent = new Intent(this, AddAddressActivity.class);
                intent.putExtra(StaticData.CHOICE_TYPE, choiceType);
                intent.putExtra(StaticData.ADDRESS_INFO, addressInfo);
                startActivityForResult(intent, RequestCodeStatic.ADD_ADDRESS);
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
    public void setPresenter(AddressContract.AddressManagePresenter presenter) {

    }

    @Override
    public void renderAddressInfo(List<AddressInfo> addressInfos) {
        if(addressInfos!=null&&addressInfos.size()>0){
            addressRv.setVisibility(View.VISIBLE);
            noAddressLl.setVisibility(View.GONE);
        }else {
            addressRv.setVisibility(View.GONE);
            noAddressLl.setVisibility(View.VISIBLE);
        }
        addressManageAdapter.setData(addressInfos);
    }

    @Override
    public void select(String addressId) {
        if(TextUtils.equals(StaticData.REFLASH_ZERO,choiceType)){
            returnAddress(addressId);
        }
    }

    @Override
    public void updateAddress(AddressInfo addressInfo) {
        Intent intent = new Intent(this, AddAddressActivity.class);
        intent.putExtra(StaticData.CHOICE_TYPE, choiceType);
        intent.putExtra(StaticData.ADDRESS_INFO, addressInfo);
        startActivityForResult(intent, RequestCodeStatic.ADD_ADDRESS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.ADD_ADDRESS://
                    if (data != null) {
                        addressId =  data.getStringExtra(StaticData.ADDRESS_ID);
                        returnAddress(addressId);
                    }
                    break;
                default:
            }
        }
    }

    private void returnAddress(String addressId){
        Intent intent = new Intent();
        intent.putExtra(StaticData.ADDRESS_ID,addressId);
        setResult(RESULT_OK, intent);
        finish();
    }

}
