package com.mall.sls.bank;


import dagger.Module;
import dagger.Provides;

/**
 * @author jwc on 2020/9/9.
 * 描述：
 */
@Module
public class BankModule {
    private BankContract.BankCardSView bankCardSView;
    private BankContract.AddBankCardView addBankCardView;
    private BankContract.ConfirmBindBankView confirmBindBankView;
    private BankContract.UntieBankCardView untieBankCardView;
    private BankContract.BankCardPayView bankCardPayView;
    private BankContract.AddChinaGCardView addChinaGCardView;
    private BankContract.ChinaGPayView chinaGPayView;

    public BankModule(BankContract.BankCardSView bankCardSView) {
        this.bankCardSView = bankCardSView;
    }

    public BankModule(BankContract.AddBankCardView addBankCardView) {
        this.addBankCardView = addBankCardView;
    }

    public BankModule(BankContract.ConfirmBindBankView confirmBindBankView) {
        this.confirmBindBankView = confirmBindBankView;
    }

    public BankModule(BankContract.UntieBankCardView untieBankCardView) {
        this.untieBankCardView = untieBankCardView;
    }

    public BankModule(BankContract.BankCardPayView bankCardPayView) {
        this.bankCardPayView = bankCardPayView;
    }

    public BankModule(BankContract.AddChinaGCardView addChinaGCardView) {
        this.addChinaGCardView = addChinaGCardView;
    }

    public BankModule(BankContract.ChinaGPayView chinaGPayView) {
        this.chinaGPayView = chinaGPayView;
    }

    @Provides
    BankContract.BankCardSView provideBankCardSView(){
        return bankCardSView;
    }

    @Provides
    BankContract.AddBankCardView provideAddBankCardView(){
        return addBankCardView;
    }

    @Provides
    BankContract.ConfirmBindBankView provideConfirmBindBankView(){
        return confirmBindBankView;
    }

    @Provides
    BankContract.UntieBankCardView provideUntieBankCardView(){
        return untieBankCardView;
    }

    @Provides
    BankContract.BankCardPayView provideBankCardPayView(){
        return bankCardPayView;
    }

    @Provides
    BankContract.AddChinaGCardView provideAddChinaGCardView(){
        return addChinaGCardView;
    }

    @Provides
    BankContract.ChinaGPayView provideChinaGPayView(){
        return chinaGPayView;
    }
}
