package com.mall.sls.order.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.data.entity.ShipOrderInfo;
import com.mall.sls.order.adapter.LogisticsAdapter;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/12.
 * 描述：配送信息
 */
public class DeliveryinfoActivity extends BaseActivity {


    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.close_iv)
    ImageView closeIv;

    private LogisticsAdapter logisticsAdapter;
    private List<ShipOrderInfo> shipOrderInfos;

    public static void start(Context context, List<ShipOrderInfo> shipOrderInfos) {
        Intent intent = new Intent(context, DeliveryinfoActivity.class);
        intent.putExtra(StaticData.SHIP_ORDER_INFOS, (Serializable) shipOrderInfos);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        shipOrderInfos= (List<ShipOrderInfo>) getIntent().getSerializableExtra(StaticData.SHIP_ORDER_INFOS);
        logisticsAdapter=new LogisticsAdapter();
        recordRv.setAdapter(logisticsAdapter);
        logisticsAdapter.setData(shipOrderInfos);

    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.close_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_iv://
                finish();
                break;
            default:
        }
    }
}
