package com.mall.sls.certify;

import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;
import com.mall.sls.certify.ui.CerifyPayActivity;
import com.mall.sls.certify.ui.MerchantCertifyActivity;
import com.mall.sls.certify.ui.NameVerifiedActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {CertifyModule.class})
public interface CertifyComponent {
    void inject(NameVerifiedActivity nameVerifiedActivity);
    void inject(CerifyPayActivity cerifyPayActivity);
    void inject(MerchantCertifyActivity activity);
}
