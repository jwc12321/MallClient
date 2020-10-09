package com.mall.sls.member;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.common.unit.VerifyManager;
import com.mall.sls.data.entity.AliPay;
import com.mall.sls.data.entity.BaoFuPay;
import com.mall.sls.data.entity.BaoFuPayInfo;
import com.mall.sls.data.entity.LocalTeam;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.entity.WxPay;

/**
 * @author jwc on 2020/5/20.
 * 描述：
 */
public interface MemberContract {
    interface SuperMemberPresente extends BasePresenter{
        void getVipGroupons(String refreshType);
        void getMoreVipGroupons();
        void vipOpen();
        void getWxPay(String orderId, String orderType, String paymentMethod);
        void getAliPay(String orderId, String orderType, String paymentMethod);
        void getBaoFuPay(String orderId, String orderType, String paymentMethod);
    }

    interface SuperMemberView extends BaseView<SuperMemberPresente>{
        void renderVipGroupons(LocalTeam localTeam);
        void renderMoreVipGroupons(LocalTeam localTeam);
        void renderVipOpen(Boolean isBoolean);
        void renderWxPay(WxPay wxPay);
        void renderAliPay(AliPay aliPay);
        void renderBaoFuPay(BaoFuPay baoFuPay);
    }
}
