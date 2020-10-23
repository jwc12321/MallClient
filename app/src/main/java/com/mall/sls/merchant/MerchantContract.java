package com.mall.sls.merchant;


import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.IntegralPointsInfo;

/**
 * @author jwc on 2020/10/22.
 * 描述：
 */
public interface MerchantContract {
    interface MerchantRightsPresenter extends BasePresenter{
        void getIntegralPointsInfo();
    }

    interface MerchantRightsView extends BaseView<MerchantRightsPresenter>{
        void renderMerchantRights(IntegralPointsInfo integralPointsInfo);
    }
}
