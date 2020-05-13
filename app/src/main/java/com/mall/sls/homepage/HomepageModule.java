package com.mall.sls.homepage;

import dagger.Module;
import dagger.Provides;

@Module
public class HomepageModule {
    private HomepageContract.HomePageView homePageView;
    private HomepageContract.GoodsDetailsView goodsDetailsView;

    public HomepageModule(HomepageContract.HomePageView homePageView) {
        this.homePageView = homePageView;
    }

    public HomepageModule(HomepageContract.GoodsDetailsView goodsDetailsView) {
        this.goodsDetailsView = goodsDetailsView;
    }

    @Provides
    HomepageContract.HomePageView provideHomePageView(){
        return homePageView;
    }

    @Provides
    HomepageContract.GoodsDetailsView privodeGoodsDetailsView(){
        return goodsDetailsView;
    }
}
