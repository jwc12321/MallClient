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

    public MineModule(MineContract.MineInfoView mineInfoView) {
        this.mineInfoView = mineInfoView;
    }

    public MineModule(MineContract.MyInviteView myInviteView) {
        this.myInviteView = myInviteView;
    }

    @Provides
    MineContract.MineInfoView provideMineInfoView(){
        return mineInfoView;
    }

    @Provides
    MineContract.MyInviteView provideMyInviteView(){
        return myInviteView;
    }
}
