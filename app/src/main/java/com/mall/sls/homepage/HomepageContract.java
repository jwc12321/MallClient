package com.mall.sls.homepage;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.entity.HomePageInfo;

public interface HomepageContract {
    interface HomePagePresenter extends BasePresenter{
        void getHomePageInfo(String refreshType);
    }

    interface HomePageView extends BaseView<HomePagePresenter>{
        void renderHomePageInfo(HomePageInfo homePageInfo);
    }

    interface GoodsDetailsPresenter extends BasePresenter{
        void getGoodsDetails(String goodsId);
    }

    interface GoodsDetailsView extends BaseView<GoodsDetailsPresenter>{
        void renderGoodsDetails(GoodsDetailsInfo goodsDetailsInfo);

    }

}
