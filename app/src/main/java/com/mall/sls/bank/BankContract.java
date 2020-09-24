package com.mall.sls.bank;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.BankCardInfo;
import com.mall.sls.data.entity.BankPayInfo;
import com.mall.sls.data.entity.BindResultInfo;
import com.mall.sls.data.entity.CardInfo;
import com.mall.sls.data.entity.CertifyInfo;
import com.mall.sls.data.request.BankPayRequest;
import com.mall.sls.data.request.StartBindBankRequest;

import java.util.List;

/**
 * @author jwc on 2020/9/9.
 * 描述：
 */
public interface BankContract {
    interface BankCardSPresenter extends BasePresenter{
        void getBankCardInfos();
    }

    interface BankCardSView extends BaseView<BankCardSPresenter>{
        void renderBankCardS(List<BankCardInfo> bankCardInfos);
    }

    interface AddBankCardPresenter extends BasePresenter{
        void getCardInfo(String cardNo);
        void getStartBindBankInfo(StartBindBankRequest request);
        void getCertifyInfo();
    }

    interface AddBankCardView extends BaseView<AddBankCardPresenter>{
        void renderCardInfo(CardInfo cardInfo);
        void renderStartBindBankInfo(BankCardInfo bankCardInfo);
        void renderCertifyInfo(CertifyInfo certifyInfo);
    }

    interface ConfirmBindBankPresenter extends BasePresenter{
        void confirmBindBank(String id, String smsCode);
    }

    interface ConfirmBindBankView extends BaseView<ConfirmBindBankPresenter>{
        void renderConfirmBindBank(BindResultInfo bindResultInfo);
    }

    interface UntieBankCardPresenter extends BasePresenter{
        void untieBankCard(String id);
    }

    interface UntieBankCardView extends BaseView<UntieBankCardPresenter>{
        void renderUntieBankCard(Boolean isBoolean);
    }

    interface BankCardPayPresenter extends BasePresenter{
        void getBankCardInfos();
        void baoFooSinglePay(BankPayRequest request);
        void sendBaoFuCode(String mobile);
    }
    interface BankCardPayView extends BaseView<BankCardPayPresenter>{
        void renderBankCardS(List<BankCardInfo> bankCardInfos);
        void renderBankPayInfo(BankPayInfo bankPayInfo);
        void renderBaoFuCode();
    }


}
