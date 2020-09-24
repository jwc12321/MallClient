package com.mall.sls.bank.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.webview.ui.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/9/9.
 * 描述：
 */
public class BankTipActivity extends BaseActivity {

    @BindView(R.id.content)
    MediumThickTextView content;
    @BindView(R.id.confirm_bt)
    ConventionalTextView confirmBt;
    @BindView(R.id.item_ll)
    LinearLayout itemLl;
    @BindView(R.id.item_rl)
    RelativeLayout itemRl;

    public static void start(Context context) {
        Intent intent = new Intent(context, BankTipActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_tip);
        ButterKnife.bind(this);
        content();
    }

    private void content() {
        String str1 = getString(R.string.not_support_bank);
        SpannableString spannableString1 = new SpannableString(str1);
        spannableString1.setSpan(new ClickableSpan() {
            public void onClick(View widget) {
                WebViewActivity.start(BankTipActivity.this, StaticData.COOPERATE_BANK);

            }
        }, 10, str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.appText14)), 10, str1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        content.setText(spannableString1);
        content.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @OnClick({R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                finish();
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
