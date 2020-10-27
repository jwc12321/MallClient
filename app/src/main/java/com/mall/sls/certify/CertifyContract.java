package com.mall.sls.certify;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.common.unit.VerifyManager;
import com.mall.sls.data.entity.AliPay;
import com.mall.sls.data.entity.BaoFuPay;
import com.mall.sls.data.entity.BaoFuPayInfo;
import com.mall.sls.data.entity.MerchantCertifyInfo;
import com.mall.sls.data.entity.MineInfo;
import com.mall.sls.data.entity.PayMethodInfo;
import com.mall.sls.data.entity.UploadUrlInfo;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.entity.WxPay;

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
        void getPayMethod(String devicePlatform,String orderType);
        void getWxPay(String orderId, String orderType, String paymentMethod);
        void getAliPay(String orderId, String orderType, String paymentMethod);
        void getBaoFuPay(String orderId, String orderType, String paymentMethod);
    }

    interface CertifyPayView extends BaseView<CertifyPayPresenter>{
        void renderPayMethod(List<PayMethodInfo> payMethods);
        void renderWxPay(WxPay wxPay);
        void renderAliPay(AliPay aliPay);
        void renderBaoFuPay(BaoFuPay baoFuPay);
    }

    interface MerchantCertifyPresenter extends BasePresenter{
        void getMerchantCertifyInfo();
        void merchantCertify(String businessLicense, String doorHeader, String address, String detail);
        void uploadFile(String photoUrl,String type);
    }

    interface MerchantCertifyView extends BaseView<MerchantCertifyPresenter>{
        void renderMerchantCertifyInfo(MerchantCertifyInfo merchantCertifyInfo);
        void renderMerchantCertify(Boolean isBoolean);
        void renderUploadFile(UploadUrlInfo uploadUrlInfo);
    }

    interface MerchantCertifyTipPresenter extends BasePresenter{
        void getMineInfo();
    }

    interface MerchantCertifyTipView extends BaseView<MerchantCertifyTipPresenter>{
        void renderMineInfo(MineInfo mineInfo);
    }

}
