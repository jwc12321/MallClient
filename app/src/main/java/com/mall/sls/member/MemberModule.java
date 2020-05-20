package com.mall.sls.member;

import dagger.Module;
import dagger.Provides;

/**
 * @author jwc on 2020/5/12.
 * 描述：
 */
@Module
public class MemberModule {
    private MemberContract.SuperMemberView superMemberView;

    public MemberModule(MemberContract.SuperMemberView superMemberView) {
        this.superMemberView = superMemberView;
    }

    @Provides
    MemberContract.SuperMemberView provideSuperMemberView(){
        return superMemberView;
    }
}
