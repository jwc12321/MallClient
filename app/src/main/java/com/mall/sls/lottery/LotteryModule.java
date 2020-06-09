package com.mall.sls.lottery;

import dagger.Module;
import dagger.Provides;

/**
 * @author jwc on 2020/6/9.
 * 描述：
 */
@Module
public class LotteryModule {
    private LotteryContract.LotteryItemView lotteryItemView;
    private LotteryContract.LotteryDetailsView lotteryDetailsView;
    private LotteryContract.LotteryResultView lotteryResultView;
    private LotteryContract.LotteryRecordView lotteryRecordView;

    public LotteryModule(LotteryContract.LotteryItemView lotteryItemView) {
        this.lotteryItemView = lotteryItemView;
    }

    public LotteryModule(LotteryContract.LotteryDetailsView lotteryDetailsView) {
        this.lotteryDetailsView = lotteryDetailsView;
    }

    public LotteryModule(LotteryContract.LotteryResultView lotteryResultView) {
        this.lotteryResultView = lotteryResultView;
    }

    public LotteryModule(LotteryContract.LotteryRecordView lotteryRecordView) {
        this.lotteryRecordView = lotteryRecordView;
    }

    @Provides
    LotteryContract.LotteryItemView provideLotteryItemView(){
        return lotteryItemView;
    }

    @Provides
    LotteryContract.LotteryDetailsView provideLotteryDetailsView(){
        return lotteryDetailsView;
    }

    @Provides
    LotteryContract.LotteryResultView provideLotteryResultView(){
        return lotteryResultView;
    }

    @Provides
    LotteryContract.LotteryRecordView provideLotteryRecordView(){
        return lotteryRecordView;
    }
}
