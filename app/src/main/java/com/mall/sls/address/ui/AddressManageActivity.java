package com.mall.sls.address.ui;

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
import com.mall.sls.address.AddressContract;
import com.mall.sls.address.AddressModule;
import com.mall.sls.address.DaggerAddressComponent;
import com.mall.sls.address.adapter.AddressManageAdapter;
import com.mall.sls.address.presenter.AddressManagePresenter;
import com.mall.sls.certify.ui.CerifyPayActivity;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.AddressInfo;

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
public class AddressManageActivity extends BaseActivity implements AddressContract.AddressManageView {
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

    public static void start(Context context) {
        Intent intent = new Intent(context, AddressManageActivity.class);
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
        addressManageAdapter=new AddressManageAdapter(this);
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
                AddAddressActivity.start(this,null);
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
}
