package com.mall.sls.certify;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.common.unit.VerifyManager;
import com.mall.sls.data.entity.BaoFuPayInfo;
import com.mall.sls.data.entity.WXPaySignResponse;

import java.util.List;

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
        void getPayMethod(String devicePlatform);
        void getWxPay(String orderId, String orderType, String paymentMethod);
        void getAliPay(String orderId, String orderType, String paymentMethod);
        void getBaoFuPay(String orderId, String orderType, String paymentMethod);
    }

    interface CertifyPayView extends BaseView<CertifyPayPresenter>{
        void renderPayMethod(List<String> payMethods);
        void renderWxPay(WXPaySignResponse wxPaySignResponse);
        void renderAliPay(String aliPayStr);
        void renderBaoFuPay(BaoFuPayInfo baoFuPayInfo);
    }
}
