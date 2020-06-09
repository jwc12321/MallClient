package com.mall.sls.lottery;

/**
 * @author jwc on 2020/6/9.
 * 描述：
 */

import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;
import com.mall.sls.lottery.ui.LotteryDetailActivity;
import com.mall.sls.lottery.ui.LotteryListActivity;
import com.mall.sls.lottery.ui.LotteryRecordActivity;
import com.mall.sls.lottery.ui.LotteryResultActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {LotteryModule.class})
public interface LotteryComponent {
    void inject(LotteryListActivity lotteryListActivity);
    void inject(LotteryDetailActivity lotteryDetailActivity);
    void inject(LotteryResultActivity lotteryResultActivity);
    void inject(LotteryRecordActivity lotteryRecordActivity);
}
