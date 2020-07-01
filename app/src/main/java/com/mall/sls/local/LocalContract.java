package com.mall.sls.local;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.LocalTeam;
import com.mall.sls.order.ui.ViewLogisticsActivity;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */
public interface LocalContract {
    interface LocalTeamPresenter extends BasePresenter{
        void getLocalTeam(String refreshType,String type);
        void getMoreLocalTeam(String type);
        void groupRemind(String ruleId);
    }

    interface LocalTeamView extends BaseView<LocalTeamPresenter>{
        void renderLocalTeam(LocalTeam localTeam);
        void renderMoreLocalTeam(LocalTeam localTeam);
        void renderError(Throwable throwable);
        void renderGroupRemind();
    }

    interface RushBuyPresenter extends BasePresenter{
        void getRushBuy(String refreshType);
        void getMoreRushBuy();
    }

    interface RushBuyView extends BaseView<RushBuyPresenter>{
        void renderRushBuy(LocalTeam localTeam);
        void renderMoreRushBuy(LocalTeam localTeam);
    }

    interface WaitBuyPresenter extends BasePresenter{
        void getWaitBuy(String refreshType);
        void getMoreWaitBuy();
    }

    interface WaitBuyView extends BaseView<WaitBuyPresenter>{
        void renderWaitBuy(LocalTeam localTeam);
        void renderMoreWaitBuy(LocalTeam localTeam);
    }
}
