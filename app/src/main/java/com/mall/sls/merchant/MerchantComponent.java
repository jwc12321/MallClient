package com.mall.sls.merchant;

/**
 * @author jwc on 2020/10/22.
 * 描述：
 */

import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;
import com.mall.sls.merchant.ui.MerchantRightsActivity;
import com.mall.sls.merchant.ui.PointsRecordActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {MerchantModule.class})
public interface MerchantComponent {
    void inject(MerchantRightsActivity activity);
    void inject(PointsRecordActivity activity);
}
