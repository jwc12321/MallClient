package com.mall.sls.order.ui;

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
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.OrderPackageInfo;
import com.mall.sls.data.entity.ShipOrderInfo;
import com.mall.sls.order.DaggerOrderComponent;
import com.mall.sls.order.OrderContract;
import com.mall.sls.order.OrderModule;
import com.mall.sls.order.adapter.OrderLogisticsAdapter;
import com.mall.sls.order.presenter.OrderLogisticsPresenter;
import com.mall.sls.webview.ui.WebViewActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/6/30.
 * 描述：查看物流
 */
public class ViewLogisticsActivity extends BaseActivity implements OrderContract.OrderLogisticsView,OrderLogisticsAdapter.OnItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.package_number)
    ConventionalTextView packageNumber;
    @BindView(R.id.logistics_rv)
    RecyclerView logisticsRv;

    private OrderLogisticsAdapter orderLogisticsAdapter;
    private String goodsOrderId;

    @Inject
    OrderLogisticsPresenter orderLogisticsPresenter;

    public static void start(Context context, String goodsOrderId) {
        Intent intent = new Intent(context, ViewLogisticsActivity.class);
        intent.putExtra(StaticData.GOODS_ORDER_ID, goodsOrderId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_logistics);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        goodsOrderId = getIntent().getStringExtra(StaticData.GOODS_ORDER_ID);
        orderLogisticsAdapter = new OrderLogisticsAdapter(this);
        orderLogisticsAdapter.setOnItemClickListener(this);
        logisticsRv.setAdapter(orderLogisticsAdapter);
        orderLogisticsPresenter.getOrderLogistics(goodsOrderId);
    }


    @Override
    protected void initializeInjector() {
        DaggerOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .orderModule(new OrderModule(this))
                .build()
                .inject(this);
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderOrderLogistics(List<OrderPackageInfo> orderPackageInfos) {
        if(orderPackageInfos!=null){
            packageNumber.setText("以下商品已被拆成"+orderPackageInfos.size()+"个包裹");
        }
        orderLogisticsAdapter.setData(orderPackageInfos);
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            default:
        }
    }

    @Override
    public void setPresenter(OrderContract.OrderLogisticsPresenter presenter) {

    }

    @Override
    public void goDeliveryInfoActivity(List<ShipOrderInfo> shipOrderInfos) {
        DeliveryinfoActivity.start(this, "" ,shipOrderInfos);
    }

    @Override
    public void goSfH5Url(String sfH5Url) {
        WebViewActivity.start(this, sfH5Url);
    }
}
