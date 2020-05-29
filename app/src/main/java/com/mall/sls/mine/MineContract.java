package com.mall.sls.mine;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.InviteInfo;
import com.mall.sls.data.entity.MineInfo;
import com.mall.sls.data.entity.ShareInfo;
import com.mall.sls.data.entity.TeamInfo;
import com.mall.sls.data.entity.VipAmountInfo;

import java.util.List;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */
public interface MineContract {
    interface MineInfoPresenter extends BasePresenter{
        void getMineInfo();
        void getVipAmountInfo();
    }

    interface MineInfoView extends BaseView<MineInfoPresenter>{
        void renderMineInfo(MineInfo mineInfo);
        void renderVipAmountInfo(VipAmountInfo vipAmountInfo);
    }

    interface MyInvitePresenter extends BasePresenter{
        void getMyInvite();
    }

    interface MyInviteView extends BaseView<MyInvitePresenter>{
        void renderMyInvite(List<InviteInfo> inviteInfos);
    }

    interface MyTeamInfoPresenter extends BasePresenter{
        void getMyTeamInfo(String refreshType);
        void getMoreMyTeamInfo();
    }

    interface MyTeamInfoView extends BaseView<MyTeamInfoPresenter>{
        void renderMyTeamInfo(TeamInfo teamInfo);
        void renderMoreMyTeamInfo(TeamInfo teamInfo);
    }

    interface ShareInfoPresenter extends BasePresenter{
        void getShareInfo();
    }

    interface ShareInfoView extends BaseView<ShareInfoPresenter>{
        void renderShareInfo(ShareInfo shareInfo);
    }

    interface FeedBackPresenter extends BasePresenter{
        void addFeedBack(String description);
    }

    interface FeedBackView extends BaseView<FeedBackPresenter>{
        void renderAddFeedBack(Boolean isBoolean);
    }


}
