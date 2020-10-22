package com.mall.sls.order.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
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
    @BindView(R.id.logistics_no)
    ConventionalTextView logisticsNo;
    @BindView(R.id.copy_bt)
    ImageView copyBt;
    @BindView(R.id.logistics_no_rl)
    RelativeLayout logisticsNoRl;

    private LogisticsAdapter logisticsAdapter;
    private List<ShipOrderInfo> shipOrderInfos;
    private String waybillNo;

    private ClipboardManager myClipboard;
    private ClipData myClip;

    public static void start(Context context, String waybillNo, List<ShipOrderInfo> shipOrderInfos) {
        Intent intent = new Intent(context, DeliveryinfoActivity.class);
        intent.putExtra(StaticData.WAY_BILL_NO, waybillNo);
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

    private void initView() {
        waybillNo = getIntent().getStringExtra(StaticData.WAY_BILL_NO);
        shipOrderInfos = (List<ShipOrderInfo>) getIntent().getSerializableExtra(StaticData.SHIP_ORDER_INFOS);
        logisticsNo.setText(getString(R.string.sf_no) + waybillNo);
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        logisticsAdapter = new LogisticsAdapter();
        recordRv.setAdapter(logisticsAdapter);
        logisticsAdapter.setData(shipOrderInfos);

    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.close_iv,R.id.logistics_no_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_iv://
                finish();
                break;
            case R.id.logistics_no_rl:
                myClip = ClipData.newPlainText("text", waybillNo);
                myClipboard.setPrimaryClip(myClip);
                showMessage(getString(R.string.copy_successfully));
                break;
            default:
        }
    }
}
