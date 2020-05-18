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

    public MineModule(MineContract.MineInfoView mineInfoView) {
        this.mineInfoView = mineInfoView;
    }

    @Provides
    MineContract.MineInfoView provideMineInfoView(){
        return mineInfoView;
    }
}
