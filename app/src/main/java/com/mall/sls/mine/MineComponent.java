package com.mall.sls.mine;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */

import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;
import com.mall.sls.mine.ui.MineFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {MineModule.class})
public interface MineComponent {
    void inject(MineFragment mineFragment);
}
