package com.mall.sls.local;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */

import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;
import com.mall.sls.local.ui.LootingFragment;
import com.mall.sls.local.ui.LootingSoonFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {LocalModule.class})
public interface LocalComponent {
    void inject(LootingSoonFragment lootingSoonFragment);
    void inject(LootingFragment lootingFragment);
}
