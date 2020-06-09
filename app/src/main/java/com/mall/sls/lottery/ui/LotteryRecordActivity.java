package com.mall.sls.lottery.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.lottery.presenter.LotteryRecordPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author jwc on 2020/6/9.
 * 描述：
 */
public class LotteryRecordActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.result_rv)
    RecyclerView resultRv;
    @BindView(R.id.time)
    MediumThickTextView time;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.scrollview)
    NestedScrollView scrollview;

    @Inject
    LotteryRecordPresenter lotteryRecordPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_result);
        ButterKnife.bind(this);
        setHeight(back,title,null);
    }



    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
