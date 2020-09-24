package com.mall.sls.mine.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.webview.ui.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/27.
 * 描述：
 */
public class PrivacyPolicyTipActivity extends BaseActivity {
    @BindView(R.id.content)
    ConventionalTextView content;
    @BindView(R.id.hide_cancel_bt)
    ConventionalTextView hideCancelBt;
    @BindView(R.id.hide_confirm_bt)
    ConventionalTextView hideConfirmBt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy_tip);
        ButterKnife.bind(this);
        content();
    }

    @OnClick({R.id.hide_cancel_bt, R.id.hide_confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hide_cancel_bt:
                selectBack(StaticData.REFRESH_ZERO);
                break;
            case R.id.hide_confirm_bt:
                selectBack(StaticData.REFRESH_ONE);
                break;
            default:
        }
    }

    private void selectBack(String type) {
        Intent backIntent = new Intent();
        backIntent.putExtra(StaticData.BACK_TYPE, type);
        setResult(Activity.RESULT_OK, backIntent);
        finish();
    }

    private void content() {
        String str1 = getString(R.string.privacy_content);
        SpannableString spannableString1 = new SpannableString(str1);

        spannableString1.setSpan(new ClickableSpan() {
            public void onClick(View widget) {
                WebViewActivity.start(PrivacyPolicyTipActivity.this, StaticData.USER_AGREEMENT);

            }
        }, 27, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(getResources()
                .getColor(R.color.backGround51)), 27, 33, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString1.setSpan(new ClickableSpan() {
            public void onClick(View widget) {
                WebViewActivity.start(PrivacyPolicyTipActivity.this, StaticData.USER_PRIVACY);

            }
        }, 34, 40, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(getResources()
                        .getColor(R.color.backGround51)), 34,
                40, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        content.setText(spannableString1);
        content.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
