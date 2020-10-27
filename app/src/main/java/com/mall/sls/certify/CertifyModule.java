package com.mall.sls.certify;


import dagger.Module;
import dagger.Provides;

@Module
public class CertifyModule {
    private CertifyContract.NameVerifiedView nameVerifiedView;
    private CertifyContract.CertifyPayView certifyPayView;
    private CertifyContract.MerchantCertifyView merchantCertifyView;
    private CertifyContract.MerchantCertifyTipView merchantCertifyTipView;

    public CertifyModule(CertifyContract.NameVerifiedView nameVerifiedView) {
        this.nameVerifiedView = nameVerifiedView;
    }

    public CertifyModule(CertifyContract.CertifyPayView certifyPayView) {
        this.certifyPayView = certifyPayView;
    }

    public CertifyModule(CertifyContract.MerchantCertifyView merchantCertifyView) {
        this.merchantCertifyView = merchantCertifyView;
    }

    public CertifyModule(CertifyContract.MerchantCertifyTipView merchantCertifyTipView) {
        this.merchantCertifyTipView = merchantCertifyTipView;
    }

    @Provides
    CertifyContract.NameVerifiedView provideNameVerifiedView(){
        return nameVerifiedView;
    }

    @Provides
    CertifyContract.CertifyPayView provideCertifyPayView(){
        return certifyPayView;
    }

    @Provides
    CertifyContract.MerchantCertifyView provideMerchantCertifyView(){
        return merchantCertifyView;
    }

    @Provides
    CertifyContract.MerchantCertifyTipView provideMerchantCertifyTipView(){
        return merchantCertifyTipView;
    }
}
