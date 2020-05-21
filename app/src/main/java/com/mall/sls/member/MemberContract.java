package com.mall.sls.member;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.common.unit.VerifyManager;
import com.mall.sls.data.entity.LocalTeam;

/**
 * @author jwc on 2020/5/20.
 * 描述：
 */
public interface MemberContract {
    interface SuperMemberPresente extends BasePresenter{
        void getVipGroupons(String refreshType);
        void getMoreVipGroupons();
        void alipayMember(String orderType, String payType);
        void vipOpen();
    }

    interface SuperMemberView extends BaseView<SuperMemberPresente>{
        void renderVipGroupons(LocalTeam localTeam);
        void renderMoreVipGroupons(LocalTeam localTeam);
        void renderAlipayMember(String alipayStr);
        void renderVipOpen(Boolean isBoolean);
    }
}
