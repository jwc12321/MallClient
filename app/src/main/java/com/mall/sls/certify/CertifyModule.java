package com.mall.sls.certify;


import dagger.Module;
import dagger.Provides;

@Module
public class CertifyModule {
    private CertifyContract.NameVerifiedView nameVerifiedView;
    private CertifyContract.VerifyPayView verifyPayView;

    public CertifyModule(CertifyContract.NameVerifiedView nameVerifiedView) {
        this.nameVerifiedView = nameVerifiedView;
    }

    public CertifyModule(CertifyContract.VerifyPayView verifyPayView) {
        this.verifyPayView = verifyPayView;
    }

    @Provides
    CertifyContract.NameVerifiedView provideNameVerifiedView(){
        return nameVerifiedView;
    }

    @Provides
    CertifyContract.VerifyPayView provideVerifyPayView(){
        return verifyPayView;
    }
}
