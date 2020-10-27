package com.mall.sls.merchant;


import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.IntegralPointsInfo;
import com.mall.sls.data.entity.PointsRecord;

/**
 * @author jwc on 2020/10/22.
 * 描述：
 */
public interface MerchantContract {
    interface MerchantRightsPresenter extends BasePresenter{
        void getIntegralPointsInfo();
        void merchantCancel();
    }

    interface MerchantRightsView extends BaseView<MerchantRightsPresenter>{
        void renderMerchantRights(IntegralPointsInfo integralPointsInfo);
        void renderMerchantCancel(Boolean isBoolean);
    }

    interface PointsRecordPresenter extends BasePresenter{
        void getPointsRecord(String refreshType);
        void getMorePointsRecord();
    }

    interface PointsRecordView extends BaseView<PointsRecordPresenter>{
        void renderPointsRecord(PointsRecord pointsRecord);
        void renderMorePointsRecord(PointsRecord pointsRecord);
    }
}
