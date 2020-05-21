package com.mall.sls.certify;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.common.unit.VerifyManager;

public interface CertifyContract {
    interface NameVerifiedPresenter extends BasePresenter {
        void getCertifyId(String certName, String certNo, String metaInfo);
        void getUsersRpStatus();
    }

    interface NameVerifiedView extends BaseView<NameVerifiedPresenter> {
        void renderCertifyId(String certifyId);
        void renderUsesRpStatus(Boolean isBoolean);
    }

    interface CertifyPayPresenter extends BasePresenter{
        void alipay(String orderType, String payType);
    }

    interface CertifyPayView extends BaseView<CertifyPayPresenter>{
        void renderAlipay(String alipayStr);
    }
}
