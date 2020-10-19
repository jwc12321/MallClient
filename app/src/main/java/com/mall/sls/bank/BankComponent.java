package com.mall.sls.bank;

import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;
import com.mall.sls.bank.ui.AddBankCardActivity;
import com.mall.sls.bank.ui.AddChinaGCardActivity;
import com.mall.sls.bank.ui.AuthenticationActivity;
import com.mall.sls.bank.ui.BankCardManageActivity;
import com.mall.sls.bank.ui.BankCardPayActivity;
import com.mall.sls.bank.ui.ChinaGPayActivity;
import com.mall.sls.bank.ui.SelectBankCardActivity;
import com.mall.sls.bank.ui.UntieBankCardActivity;

import dagger.Component;

/**
 * @author jwc on 2020/9/9.
 * 描述：
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {BankModule.class})
public interface BankComponent {
    void inject(BankCardManageActivity bankCardManageActivity);
    void inject(UntieBankCardActivity untieBankCardActivity);
    void inject(AddBankCardActivity addBankCardActivity);
    void inject(AuthenticationActivity authenticationActivity);
    void inject(BankCardPayActivity bankCardPayActivity);
    void inject(SelectBankCardActivity selectBankCardActivity);
    void inject(AddChinaGCardActivity addChinaGCardActivity);
    void inject(ChinaGPayActivity activity);
}
