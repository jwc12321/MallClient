package com.mall.sls.mine;

import dagger.Module;
import dagger.Provides;

/**
 * @author jwc on 2020/5/7.
 * 描述：
 */
@Module
public class MineModule {
    private MineContract.MineInfoView mineInfoView;
    private MineContract.MyInviteView myInviteView;
    private MineContract.MyTeamInfoView myTeamInfoView;
    private MineContract.ShareInfoView shareInfoView;
    private MineContract.FeedBackView feedBackView;
    private MineContract.SettingView settingView;
    private MineContract.AboutAppView aboutAppView;

    public MineModule(MineContract.MineInfoView mineInfoView) {
        this.mineInfoView = mineInfoView;
    }

    public MineModule(MineContract.MyInviteView myInviteView) {
        this.myInviteView = myInviteView;
    }

    public MineModule(MineContract.MyTeamInfoView myTeamInfoView) {
        this.myTeamInfoView = myTeamInfoView;
    }

    public MineModule(MineContract.ShareInfoView shareInfoView) {
        this.shareInfoView = shareInfoView;
    }

    public MineModule(MineContract.FeedBackView feedBackView) {
        this.feedBackView = feedBackView;
    }

    public MineModule(MineContract.SettingView settingView) {
        this.settingView = settingView;
    }

    public MineModule(MineContract.AboutAppView aboutAppView) {
        this.aboutAppView = aboutAppView;
    }

    @Provides
    MineContract.MineInfoView provideMineInfoView(){
        return mineInfoView;
    }

    @Provides
    MineContract.MyInviteView provideMyInviteView(){
        return myInviteView;
    }

    @Provides
    MineContract.MyTeamInfoView provideMyTeamInfoView(){
        return myTeamInfoView;
    }

    @Provides
    MineContract.ShareInfoView provideShareInfoView(){
        return shareInfoView;
    }

    @Provides
    MineContract.FeedBackView provideFeedBackView(){
        return feedBackView;
    }

    @Provides
    MineContract.SettingView provideSettingView(){
        return settingView;
    }

    @Provides
    MineContract.AboutAppView provideAboutAppView(){
        return aboutAppView;
    }
}
