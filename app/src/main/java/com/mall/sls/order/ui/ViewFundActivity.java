package com.mall.sls.order.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.TCAgentUnit;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.RefundInfo;
import com.mall.sls.order.DaggerOrderComponent;
import com.mall.sls.order.OrderContract;
import com.mall.sls.order.OrderModule;
import com.mall.sls.order.adapter.RefundAdapter;
import com.mall.sls.order.presenter.RefundPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/7/15.
 * 描述：
 */
public class ViewFundActivity extends BaseActivity implements OrderContract.RefundView {
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
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private String orderId;
    private RefundAdapter refundAdapter;

    @Inject
    RefundPresenter refundPresenter;

    public static void start(Context context, String orderId) {
        Intent intent = new Intent(context, ViewFundActivity.class);
        intent.putExtra(StaticData.GOODS_ORDER_ID, orderId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fund);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }


    private void initView() {
        refreshLayout.setOnMultiPurposeListener(simpleMultiPurposeListener);
        orderId = getIntent().getStringExtra(StaticData.GOODS_ORDER_ID);
        refundAdapter = new RefundAdapter(this);
        recordRv.setAdapter(refundAdapter);
        refreshLayout.finishLoadMoreWithNoMoreData();
        refundPresenter.getRefundInfo(StaticData.REFRESH_ONE, orderId);
    }

    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
            refundPresenter.getRefundInfo(StaticData.REFRESH_ZERO, orderId);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        }
    };

    @Override
    protected void initializeInjector() {
        DaggerOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .orderModule(new OrderModule(this))
                .build()
                .inject(this);
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
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderRefundInfo(List<RefundInfo> refundInfos) {
        refreshLayout.finishRefresh();
        if (refundInfos != null && refundInfos.size() > 0) {
            recordRv.setVisibility(View.VISIBLE);
            noRecordLl.setVisibility(View.GONE);
        } else {
            recordRv.setVisibility(View.GONE);
            noRecordLl.setVisibility(View.VISIBLE);
        }
        refundAdapter.setData(refundInfos);
    }

    @Override
    public void setPresenter(OrderContract.RefundPresenter presenter) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        TCAgentUnit.pageStart(this, getString(R.string.view_fund_page));
    }

    @Override
    protected void onPause() {
        super.onPause();
        TCAgentUnit.pageEnd(this, getString(R.string.view_fund_page));
    }
}
