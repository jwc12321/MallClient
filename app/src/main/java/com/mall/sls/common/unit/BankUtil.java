package com.mall.sls.common.unit;

import android.text.TextUtils;
import android.widget.RelativeLayout;

import com.mall.sls.R;
import com.mall.sls.common.BankStaticData;

/**
 * @author jwc on 2020/9/10.
 * 描述：
 */
public class BankUtil {
    public static String lastFourDigits(String cardNo) {
        if (!TextUtils.isEmpty(cardNo) && cardNo.length() > 4) {
            return cardNo.substring(cardNo.length() - 4, cardNo.length());
        }
        return "";
    }

    public static void setBankBg(String bankCode, RelativeLayout view) {
        if (TextUtils.equals(BankStaticData.BANK_ICBC, bankCode)||TextUtils.equals(BankStaticData.BANK_BOC, bankCode)
                ||TextUtils.equals(BankStaticData.BANK_CITIC, bankCode)||TextUtils.equals(BankStaticData.BANK_CMB, bankCode)
                ||TextUtils.equals(BankStaticData.BANK_GDB, bankCode)||TextUtils.equals(BankStaticData.BANK_HXB, bankCode)
                ||TextUtils.equals(BankStaticData.BANK_BOB, bankCode)||TextUtils.equals(BankStaticData.BANK_ZSB, bankCode)) {
            view.setBackgroundResource(R.drawable.bank_red_bg);
        } else if (TextUtils.equals(BankStaticData.BANK_CEB, bankCode)) {
            view.setBackgroundResource(R.drawable.bank_purple_bg);
        } else if (TextUtils.equals(BankStaticData.BANK_ABC, bankCode)||TextUtils.equals(BankStaticData.BANK_PSBC, bankCode)
                ||TextUtils.equals(BankStaticData.BANK_SHB, bankCode)||TextUtils.equals(BankStaticData.BANK_CBHB, bankCode)) {
            view.setBackgroundResource(R.drawable.bank_green_bg);
        } else if (TextUtils.equals(BankStaticData.BANK_PAB, bankCode)) {
            view.setBackgroundResource(R.drawable.bank_oranage_bg);
        } else {
            view.setBackgroundResource(R.drawable.bank_blue_bg);
        }
    }

    public static String idCardVis(String idCardNo) {
        if (!TextUtils.isEmpty(idCardNo) && idCardNo.length() ==18) {
            return idCardNo.substring(0, 3)+"************"+idCardNo.substring(idCardNo.length() - 3, idCardNo.length());
        }
        return "";
    }
}
