package com.mall.sls.mainframe;

import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {MainFrameModule.class})
public interface MainFrameComponent {
}
