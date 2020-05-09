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
import com.mall.sls.address.adapter.AddressManageAdapter;
import com.mall.sls.certify.ui.CerifyPayActivity;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.AddressInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/8.
 * 描述：地址管理
 */
public class AddressManageActivity extends BaseActivity {
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

    private void initView(){
        addressManageAdapter=new AddressManageAdapter(this);
        addressRv.setAdapter(addressManageAdapter);
        AddressInfo addressInfo1=new AddressInfo("小蒋","198898989","犯得上发生纠纷附近的肌肤的科技发达开始JFK事件发生的","1","家");
        AddressInfo addressInfo2=new AddressInfo("小回复","889898989","肌肤的说服力的事件发生的","2","学校");
        AddressInfo addressInfo3=new AddressInfo("小减肥的","9090090909","u夫欸蕊蕊房间数量JFK地方","2","");
        List<AddressInfo> addressInfos=new ArrayList<>();
        addressInfos.add(addressInfo1);
        addressInfos.add(addressInfo2);
        addressInfos.add(addressInfo3);
        addressManageAdapter.setData(addressInfos);
    }

    @OnClick({R.id.confirm_bt, R.id.back,R.id.right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
            case R.id.right_tv:
                AddAddressActivity.start(this);
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
}
