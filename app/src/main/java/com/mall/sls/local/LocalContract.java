package com.mall.sls.local;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.LocalTeam;

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
}
