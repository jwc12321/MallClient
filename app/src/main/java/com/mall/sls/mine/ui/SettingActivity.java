package com.mall.sls.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.TokenManager;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.WebViewDetailInfo;
import com.mall.sls.webview.ui.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/11.
 * 描述：设置
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.feedback_rl)
    RelativeLayout feedbackRl;
    @BindView(R.id.clear_cache_rl)
    RelativeLayout clearCacheRl;
    @BindView(R.id.register_tv)
    ConventionalTextView registerTv;
    @BindView(R.id.privacy_tv)
    ConventionalTextView privacyTv;
    private WebViewDetailInfo webViewDetailInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setHeight(back, title, null);
    }

    @OnClick({R.id.back, R.id.confirm_bt, R.id.feedback_rl, R.id.clear_cache_rl,R.id.register_tv, R.id.privacy_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.feedback_rl:
                FeedBackActivity.start(this);
                break;
            case R.id.confirm_bt://确认退出登录
                TokenManager.saveToken("");
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.clear_cache_rl:
                showMessage(getString(R.string.clear_cache_success));
                break;
            case R.id.register_tv://用户协议
                webViewDetailInfo = new WebViewDetailInfo();
                webViewDetailInfo.setTitle(getString(R.string.registration_agreement_tv));
                webViewDetailInfo.setUrl(StaticData.USER_AGREEMENT);
                WebViewActivity.start(this, webViewDetailInfo);
                break;
            case R.id.privacy_tv://隐私政策
                webViewDetailInfo = new WebViewDetailInfo();
                webViewDetailInfo.setTitle(getString(R.string.privacy_policy_tv));
                webViewDetailInfo.setUrl(StaticData.USER_PRIVACY);
                WebViewActivity.start(this, webViewDetailInfo);
                break;
            default:
        }
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
