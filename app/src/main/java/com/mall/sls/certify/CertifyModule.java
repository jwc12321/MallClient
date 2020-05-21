package com.mall.sls.certify;


import dagger.Module;
import dagger.Provides;

@Module
public class CertifyModule {
    private CertifyContract.NameVerifiedView nameVerifiedView;
    private CertifyContract.CertifyPayView certifyPayView;

    public CertifyModule(CertifyContract.NameVerifiedView nameVerifiedView) {
        this.nameVerifiedView = nameVerifiedView;
    }

    public CertifyModule(CertifyContract.CertifyPayView certifyPayView) {
        this.certifyPayView = certifyPayView;
    }

    @Provides
    CertifyContract.NameVerifiedView provideNameVerifiedView(){
        return nameVerifiedView;
    }

    @Provides
    CertifyContract.CertifyPayView provideCertifyPayView(){
        return certifyPayView;
    }
}
