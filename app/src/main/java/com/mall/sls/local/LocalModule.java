package com.mall.sls.local;

import dagger.Module;
import dagger.Provides;

/**
 * @author jwc on 2020/5/9.
 * 描述：
 */
@Module
public class LocalModule {
    private LocalContract.LocalTeamView localTeamView;
    private LocalContract.RushBuyView rushBuyView;
    private LocalContract.WaitBuyView waitBuyView;

    public LocalModule(LocalContract.LocalTeamView localTeamView) {
        this.localTeamView = localTeamView;
    }

    public LocalModule(LocalContract.RushBuyView rushBuyView) {
        this.rushBuyView = rushBuyView;
    }

    public LocalModule(LocalContract.WaitBuyView waitBuyView) {
        this.waitBuyView = waitBuyView;
    }

    @Provides
    LocalContract.LocalTeamView provideLocalTeamView(){
        return localTeamView;
    }

    @Provides
    LocalContract.RushBuyView provideRushBuyView(){
        return rushBuyView;
    }

    @Provides
    LocalContract.WaitBuyView provideWaitBuyView(){
        return waitBuyView;
    }
}
