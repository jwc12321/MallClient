package com.mall.sls.lottery.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.LotteryItemInfo;
import com.mall.sls.data.entity.PrizeVo;
import com.mall.sls.webview.ui.LandingPageActivity;
import com.mall.sls.lottery.DaggerLotteryComponent;
import com.mall.sls.lottery.LotteryContract;
import com.mall.sls.lottery.LotteryModule;
import com.mall.sls.lottery.adapter.LotteryAdapter;
import com.mall.sls.lottery.presenter.LotteryItemPresenter;
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
 * 描述：抽奖列表
 */
public class LotteryListActivity extends BaseActivity implements LotteryContract.LotteryItemView, LotteryAdapter.OnItemClickListener {


    @Inject
    LotteryItemPresenter lotteryItemPresenter;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.lucky_draw_tv)
    ConventionalTextView luckyDrawTv;
    @BindView(R.id.lucky_draw_number)
    ConventionalTextView luckyDrawNumber;
    @BindView(R.id.tip_rl)
    RelativeLayout tipRl;
    @BindView(R.id.lottery_record_tv)
    ConventionalTextView lotteryRecordTv;
    @BindView(R.id.lottery_record_number)
    ConventionalTextView lotteryRecordNumber;
    @BindView(R.id.record_rl)
    RelativeLayout recordRl;
    @BindView(R.id.lucky_koi)
    ImageView luckyKoi;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.no_record_ll)
    LinearLayout noRecordLl;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.koi_rl)
    RelativeLayout koiRl;

    private LotteryAdapter lotteryAdapter;
    private List<String> brocadeCarps;
    private String prizeNumber;
    private List<String> prizeRules;
    private boolean isFirst = true;
    private String prizeRuleUrl;


    public static void start(Context context) {
        Intent intent = new Intent(context, LotteryListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_list);
        ButterKnife.bind(this);
        navigationBar();
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        refreshLayout.setOnMultiPurposeListener(simpleMultiPurposeListener);
        lotteryAdapter = new LotteryAdapter(this);
        lotteryAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(lotteryAdapter);
    }

    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
            lotteryItemPresenter.getLotteryItemInfo(StaticData.REFRESH_ZERO);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            lotteryItemPresenter.getMoreLotteryItemInfo();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirst) {
            lotteryItemPresenter.getLotteryItemInfo(StaticData.REFRESH_ONE);
            isFirst = false;
        }
    }

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
    public void renderLotteryItemInfo(LotteryItemInfo lotteryItemInfo) {
        refreshLayout.finishRefresh();
        if (lotteryItemInfo != null) {
            prizeNumber = lotteryItemInfo.getPrizeNumber();
            luckyDrawNumber.setText("x " + lotteryItemInfo.getPrizeNumber());
            lotteryRecordNumber.setText(lotteryItemInfo.getHistoryPrizeCount());
            brocadeCarps = lotteryItemInfo.getBrocadeCarps();
            koiRl.setVisibility(TextUtils.equals(StaticData.REFRESH_ZERO,lotteryItemInfo.getHistoryPrizeCount())?View.GONE:View.VISIBLE);
            prizeRuleUrl=lotteryItemInfo.getPrizeRuleUrl();
            if (brocadeCarps != null && brocadeCarps.size() > 0) {
                for (int i = 0; i < brocadeCarps.size(); i++) {
                    View view1 = View.inflate(this, R.layout.item_brocade_carps, null);
                    TextView brocade = view1.findViewById(R.id.brocade);
                    brocade.setText(brocadeCarps.get(i));
                    viewFlipper.addView(view1);
                }
                viewFlipper.setFlipInterval(2000);
                viewFlipper.startFlipping();
            }
            if (lotteryItemInfo.getPrizeVos() != null && lotteryItemInfo.getPrizeVos().size() > 0) {
                recordRv.setVisibility(View.VISIBLE);
                noRecordLl.setVisibility(View.GONE);
                if (lotteryItemInfo.getPrizeVos().size() == Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                    refreshLayout.resetNoMoreData();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                lotteryAdapter.setData(lotteryItemInfo.getPrizeVos());
            } else {
                recordRv.setVisibility(View.GONE);
                noRecordLl.setVisibility(View.VISIBLE);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            prizeRules = lotteryItemInfo.getPrizeRules();
        }
    }

    @Override
    public void renderMoreLotteryItemInfo(LotteryItemInfo lotteryItemInfo) {
        refreshLayout.finishLoadMore();
        if (lotteryItemInfo != null && lotteryItemInfo.getPrizeVos() != null) {
            if (lotteryItemInfo.getPrizeVos().size() != Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            lotteryAdapter.addMore(lotteryItemInfo.getPrizeVos());
        }
    }

    @Override
    public void setPresenter(LotteryContract.LotteryItemPresenter presenter) {

    }

    @Override
    public void goLotteryDetails(PrizeVo prizeVo) {
        isFirst = true;
        LotteryDetailActivity.start(this, prizeNumber, prizeVo);
    }

    @OnClick({R.id.back, R.id.record_rl, R.id.tip_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.record_rl:
                LotteryRecordActivity.start(this);
                break;
            case R.id.tip_rl:
                LandingPageActivity.start(this,prizeRuleUrl);
                break;
            default:
        }
    }
}
