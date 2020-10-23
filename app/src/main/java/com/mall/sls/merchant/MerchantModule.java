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

    public MerchantModule(MerchantContract.MerchantRightsView merchantRightsView) {
        this.merchantRightsView = merchantRightsView;
    }

    @Provides
    MerchantContract.MerchantRightsView provideMerchantRightsView(){
        return merchantRightsView;
    }
}
