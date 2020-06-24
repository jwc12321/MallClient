package com.mall.sls.homepage;

import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;
import com.mall.sls.homepage.ui.ActivityGoodsDetailActivity;
import com.mall.sls.homepage.ui.ActivityGroupGoodsActivity;
import com.mall.sls.homepage.ui.ConfirmOrderActivity;
import com.mall.sls.homepage.ui.GeneralGoodsDetailsActivity;
import com.mall.sls.homepage.ui.HomepageFragment;
import com.mall.sls.homepage.ui.OrdinaryGoodsDetailActivity;
import com.mall.sls.homepage.ui.WXGoodsDetailsActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {HomepageModule.class})
public interface HomepageComponent {
    void inject(HomepageFragment homepageFragment);
    void inject(OrdinaryGoodsDetailActivity ordinaryGoodsDetailActivity);
    void inject(ActivityGroupGoodsActivity activityGroupGoodsActivity);
    void inject(ActivityGoodsDetailActivity activityGoodsDetailActivity);
    void inject(ConfirmOrderActivity confirmOrderActivity);
    void inject(WXGoodsDetailsActivity wxGoodsDetailsActivity);
    void inject(GeneralGoodsDetailsActivity generalGoodsDetailsActivity);
}
