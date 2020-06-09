package com.mall.sls.lottery.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.lottery.DaggerLotteryComponent;
import com.mall.sls.lottery.LotteryContract;
import com.mall.sls.lottery.LotteryModule;
import com.mall.sls.lottery.adapter.LotteryResultAdapter;
import com.mall.sls.lottery.presenter.LotteryResultPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/6/9.
 * 描述：抽奖结果页
 */
public class LotteryResultActivity extends BaseActivity implements LotteryContract.LotteryResultView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.result_rv)
    RecyclerView resultRv;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.scrollview)
    NestedScrollView scrollview;
    @BindView(R.id.time)
    MediumThickTextView time;

    @Inject
    LotteryResultPresenter lotteryResultPresenter;

    private String prizeId;
    private String prizeTime;
    private LotteryResultAdapter lotteryResultAdapter;

    public static void start(Context context, String prizeId, String prizeTime) {
        Intent intent = new Intent(context, LotteryResultActivity.class);
        intent.putExtra(StaticData.PRIZE_ID, prizeId);
        intent.putExtra(StaticData.PRIZE_TIME, prizeTime);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_result);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView() {
        prizeId = getIntent().getStringExtra(StaticData.PRIZE_ID);
        prizeTime = getIntent().getStringExtra(StaticData.PRIZE_TIME);
        time.setText("开奖时间:"+prizeTime);
        resultRv.setLayoutManager(new GridLayoutManager(this, 2));
        lotteryResultAdapter=new LotteryResultAdapter();
        resultRv.setAdapter(lotteryResultAdapter);
        lotteryResultPresenter.getPrizeResult(prizeId);
    }

    @Override
    protected void initializeInjector() {
        DaggerLotteryComponent.builder()
                .applicationComponent(getApplicationComponent())
                .lotteryModule(new LotteryModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.back, R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
            case R.id.confirm_bt:
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
    public void renderPrizeResult(List<String> results) {
        lotteryResultAdapter.setData(results);
    }

    @Override
    public void setPresenter(LotteryContract.LotteryResultPresenter presenter) {

    }
}
