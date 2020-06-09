package com.mall.sls.lottery;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.JoinPrizeInfo;
import com.mall.sls.data.entity.LotteryItemInfo;
import com.mall.sls.data.entity.LotteryRecord;
import com.mall.sls.data.entity.PrizeVo;

import java.util.List;

/**
 * @author jwc on 2020/6/9.
 * 描述：
 */
public interface LotteryContract {
    interface LotteryItemPresenter extends BasePresenter {
        void getLotteryItemInfo(String refreshType);

        void getMoreLotteryItemInfo();
    }

    interface LotteryItemView extends BaseView<LotteryItemPresenter> {
        void renderLotteryItemInfo(LotteryItemInfo lotteryItemInfo);

        void renderMoreLotteryItemInfo(LotteryItemInfo lotteryItemInfo);
    }

    interface LotteryDetailsPresenter extends BasePresenter {
        void getSystemTime();
        void getJoinPrizeInfo(String prizeId, String number, String payType);

    }

    interface LotteryDetailsView extends BaseView<LotteryDetailsPresenter> {
        void renderSystemTime(String time);
        void renderJoinPrizeInfo(JoinPrizeInfo joinPrizeInfo);

    }

    interface LotteryResultPresenter extends BasePresenter{
        void getPrizeResult(String prizeId);
    }

    interface LotteryResultView extends BaseView<LotteryResultPresenter>{
        void renderPrizeResult(List<String> results);
    }

    interface LotteryRecordPresenter extends BasePresenter{
        void getLotteryRecord(String refreshType);
        void getMoreLotteryRecord();
    }

    interface LotteryRecordView extends BaseView<LotteryRecordPresenter>{
        void renderLotteryRecord(LotteryRecord lotteryRecord);
        void renderMoreLotteryRecord(LotteryRecord lotteryRecord);
    }

}
