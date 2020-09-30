package com.mall.sls.homepage;


import dagger.Module;
import dagger.Provides;

@Module
public class HomepageModule {
    private HomepageContract.HomePageView homePageView;
    private HomepageContract.GoodsDetailsView goodsDetailsView;
    private HomepageContract.ConfirmOrderView confirmOrderView;
    private HomepageContract.WXGoodsDetailsView wxGoodsDetailsView;
    private HomepageContract.GeneralGoodsDetailsView generalGoodsDetailsView;
    private HomepageContract.CartConfirmOrderView cartConfirmOrderView;
    private HomepageContract.PayMethodView payMethodView;

    public HomepageModule(HomepageContract.HomePageView homePageView) {
        this.homePageView = homePageView;
    }

    public HomepageModule(HomepageContract.GoodsDetailsView goodsDetailsView) {
        this.goodsDetailsView = goodsDetailsView;
    }

    public HomepageModule(HomepageContract.ConfirmOrderView confirmOrderView) {
        this.confirmOrderView = confirmOrderView;
    }

    public HomepageModule(HomepageContract.WXGoodsDetailsView wxGoodsDetailsView) {
        this.wxGoodsDetailsView = wxGoodsDetailsView;
    }

    public HomepageModule(HomepageContract.GeneralGoodsDetailsView generalGoodsDetailsView) {
        this.generalGoodsDetailsView = generalGoodsDetailsView;
    }

    public HomepageModule(HomepageContract.CartConfirmOrderView cartConfirmOrderView) {
        this.cartConfirmOrderView = cartConfirmOrderView;
    }

    public HomepageModule(HomepageContract.PayMethodView payMethodView) {
        this.payMethodView = payMethodView;
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

    @Provides
    HomepageContract.WXGoodsDetailsView provideWXGoodsDetailsView(){
        return wxGoodsDetailsView;
    }

    @Provides
    HomepageContract.GeneralGoodsDetailsView provideGeneralGoodsDetailsView(){
        return generalGoodsDetailsView;
    }

    @Provides
    HomepageContract.CartConfirmOrderView provideCartConfirmOrderView(){
        return cartConfirmOrderView;
    }

    @Provides
    HomepageContract.PayMethodView providePayMethodView(){
        return payMethodView;
    }

}
