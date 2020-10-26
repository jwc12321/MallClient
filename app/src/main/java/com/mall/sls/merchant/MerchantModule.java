package com.mall.sls.merchant;

import dagger.Module;
import dagger.Provides;

/**
 * @author jwc on 2020/10/22.
 * 描述：
 */
@Module
public class MerchantModule {
    private MerchantContract.MerchantRightsView merchantRightsView;
    private MerchantContract.PointsRecordView pointsRecordView;

    public MerchantModule(MerchantContract.MerchantRightsView merchantRightsView) {
        this.merchantRightsView = merchantRightsView;
    }

    public MerchantModule(MerchantContract.PointsRecordView pointsRecordView) {
        this.pointsRecordView = pointsRecordView;
    }

    @Provides
    MerchantContract.MerchantRightsView provideMerchantRightsView(){
        return merchantRightsView;
    }

    @Provides
    MerchantContract.PointsRecordView providePointsRecordView(){
        return pointsRecordView;
    }
}
