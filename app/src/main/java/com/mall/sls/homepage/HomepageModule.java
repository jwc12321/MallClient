package com.mall.sls.homepage;

import dagger.Module;
import dagger.Provides;

@Module
public class HomepageModule {
    private HomepageContract.HomePageView homePageView;
    private HomepageContract.GoodsDetailsView goodsDetailsView;
    private HomepageContract.ConfirmOrderView confirmOrderView;

    public HomepageModule(HomepageContract.HomePageView homePageView) {
        this.homePageView = homePageView;
    }

    public HomepageModule(HomepageContract.GoodsDetailsView goodsDetailsView) {
        this.goodsDetailsView = goodsDetailsView;
    }

    public HomepageModule(HomepageContract.ConfirmOrderView confirmOrderView) {
        this.confirmOrderView = confirmOrderView;
    }

    @Provides
    HomepageContract.HomePageView provideHomePageView(){
        return homePageView;
    }

    @Provides
    HomepageContract.GoodsDetailsView provideGoodsDetailsView(){
        return goodsDetailsView;
    }

    @Provides
    HomepageContract.ConfirmOrderView provideConfirmOrderView(){
        return confirmOrderView;
    }
}
