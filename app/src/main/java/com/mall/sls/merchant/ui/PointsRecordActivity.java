package com.mall.sls.merchant.ui;
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
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.PointsRecord;
import com.mall.sls.merchant.DaggerMerchantComponent;
import com.mall.sls.merchant.MerchantContract;
import com.mall.sls.merchant.MerchantModule;
import com.mall.sls.merchant.adapter.PointsRecordAdapter;
import com.mall.sls.merchant.presenter.PointsRecordPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/10/26.
 * 描述：
 */
public class PointsRecordActivity extends BaseActivity implements MerchantContract.PointsRecordView {
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

    @Inject
    PointsRecordPresenter pointsRecordPresenter;
    private PointsRecordAdapter pointsRecordAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, PointsRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_record);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        refreshLayout.setOnMultiPurposeListener(simpleMultiPurposeListener);
        pointsRecordAdapter=new PointsRecordAdapter();
        recordRv.setAdapter(pointsRecordAdapter);
        pointsRecordPresenter.getPointsRecord(StaticData.REFRESH_ONE);
    }

    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
            pointsRecordPresenter.getPointsRecord(StaticData.REFRESH_ZERO);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            pointsRecordPresenter.getMorePointsRecord();
        }
    };

    @Override
    protected void initializeInjector() {
        DaggerMerchantComponent.builder()
                .applicationComponent(getApplicationComponent())
                .merchantModule(new MerchantModule(this))
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
    public void renderPointsRecord(PointsRecord pointsRecord) {
        refreshLayout.finishRefresh();
        if (pointsRecord != null) {
            if (pointsRecord.getPointsRecordInfos()!= null && pointsRecord.getPointsRecordInfos().size() > 0) {
                recordRv.setVisibility(View.VISIBLE);
                noRecordLl.setVisibility(View.GONE);
                if (pointsRecord.getPointsRecordInfos().size() == Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                    refreshLayout.resetNoMoreData();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                pointsRecordAdapter.setData(pointsRecord.getPointsRecordInfos());
            } else {
                recordRv.setVisibility(View.GONE);
                noRecordLl.setVisibility(View.VISIBLE);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void renderMorePointsRecord(PointsRecord pointsRecord) {
        refreshLayout.finishLoadMore();
        if (pointsRecord != null && pointsRecord.getPointsRecordInfos() != null) {
            if (pointsRecord.getPointsRecordInfos().size() != Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            pointsRecordAdapter.addMore(pointsRecord.getPointsRecordInfos());
        }
    }

    @Override
    public void setPresenter(MerchantContract.PointsRecordPresenter presenter) {

    }
}
