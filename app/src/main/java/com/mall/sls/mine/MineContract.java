package com.mall.sls.mine;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.InviteInfo;
import com.mall.sls.data.entity.MineInfo;

import java.util.List;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */
public interface MineContract {
    interface MineInfoPresenter extends BasePresenter{
        void getMineInfo();
    }

    interface MineInfoView extends BaseView<MineInfoPresenter>{
        void renderMineInfo(MineInfo mineInfo);
    }

    interface MyInvitePresenter extends BasePresenter{
        void getMyInvite();
    }

    interface MyInviteView extends BaseView<MyInvitePresenter>{
        void renderMyInvite(List<InviteInfo> inviteInfos);
    }


}
