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

    public LocalModule(LocalContract.LocalTeamView localTeamView) {
        this.localTeamView = localTeamView;
    }

    @Provides
    LocalContract.LocalTeamView provideLocalTeamView(){
        return localTeamView;
    }
}
