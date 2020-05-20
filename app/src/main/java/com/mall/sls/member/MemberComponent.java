package com.mall.sls.member;

/**
 * @author jwc on 2020/5/20.
 * 描述：
 */

import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;
import com.mall.sls.member.ui.SuperMemberActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {MemberModule.class})
public interface MemberComponent {
    void inject(SuperMemberActivity superMemberActivity);
}
