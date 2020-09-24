package com.mall.sls.lottery.ui;

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
import com.mall.sls.common.unit.FormatUtil;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.LotteryRecord;
import com.mall.sls.data.entity.PrizeVo;
import com.mall.sls.lottery.DaggerLotteryComponent;
import com.mall.sls.lottery.LotteryContract;
import com.mall.sls.lottery.LotteryModule;
import com.mall.sls.lottery.adapter.LotteryRecordAdapter;
import com.mall.sls.lottery.presenter.LotteryRecordPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/6/9.
 * 描述：
 */
public class LotteryRecordActivity extends BaseActivity implements LotteryContract.LotteryRecordView,LotteryRecordAdapter.OnItemClickListener {

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
    private LotteryRecordAdapter lotteryRecordAdapter;


    @Inject
    LotteryRecordPresenter lotteryRecordPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, LotteryRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_record);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        refreshLayout.setOnMultiPurposeListener(simpleMultiPurposeListener);
        lotteryRecordAdapter = new LotteryRecordAdapter(this);
        lotteryRecordAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(lotteryRecordAdapter);
        lotteryRecordPresenter.getLotteryRecord(StaticData.REFRESH_ONE);


    }

    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
            lotteryRecordPresenter.getLotteryRecord(StaticData.REFRESH_ZERO);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            lotteryRecordPresenter.getMoreLotteryRecord();
        }
    };


    @Override
    protected void initializeInjector() {
        DaggerLotteryComponent.builder()
                .applicationComponent(getApplicationComponent())
                .lotteryModule(new LotteryModule(this))
                .build()
                .inject(this);
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderLotteryRecord(LotteryRecord lotteryRecord) {
        refreshLayout.finishRefresh();
        if (lotteryRecord != null) {
            if (lotteryRecord.getPrizeVos()!= null && lotteryRecord.getPrizeVos().size() > 0) {
                recordRv.setVisibility(View.VISIBLE);
                noRecordLl.setVisibility(View.GONE);
                if (lotteryRecord.getPrizeVos().size() == Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                    refreshLayout.resetNoMoreData();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                lotteryRecordAdapter.setData(lotteryRecord.getPrizeVos());
            } else {
                recordRv.setVisibility(View.GONE);
                noRecordLl.setVisibility(View.VISIBLE);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void renderMoreLotteryRecord(LotteryRecord lotteryRecord) {
        refreshLayout.finishLoadMore();
        if (lotteryRecord != null && lotteryRecord.getPrizeVos() != null) {
            if (lotteryRecord.getPrizeVos().size() != Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            lotteryRecordAdapter.addMore(lotteryRecord.getPrizeVos());
        }
    }

    @Override
    public void setPresenter(LotteryContract.LotteryRecordPresenter presenter) {

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
    public void goLotteryDetails(PrizeVo prizeVo) {
        LotteryDetailActivity.start(this,"0",prizeVo);
    }
}
