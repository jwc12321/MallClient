package com.mall.sls.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.mine.DaggerMineComponent;
import com.mall.sls.mine.MineContract;
import com.mall.sls.mine.MineModule;
import com.mall.sls.mine.presenter.SettingPresenter;
import com.mall.sls.webview.ui.WebViewActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constant.UiType;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

/**
 * @author jwc on 2020/5/11.
 * 描述：设置
 */
public class SettingActivity extends BaseActivity implements MineContract.SettingView {
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
    @BindView(R.id.update_rl)
    RelativeLayout updateRl;
    @BindView(R.id.item_rl)
    RelativeLayout itemRl;

    @Inject
    SettingPresenter settingPresenter;
    @BindView(R.id.contact_service_rl)
    RelativeLayout contactServiceRl;

    private String consumerPhone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        settingPresenter.getConsumerPhone();
    }

    @OnClick({R.id.back, R.id.confirm_bt, R.id.feedback_rl, R.id.clear_cache_rl, R.id.register_tv, R.id.privacy_tv, R.id.update_rl,R.id.contact_service_rl})
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
                WebViewActivity.start(this, StaticData.USER_AGREEMENT);
                break;
            case R.id.privacy_tv://隐私政策
                WebViewActivity.start(this, StaticData.USER_PRIVACY);
                break;
            case R.id.update_rl://版本检测
                AboutAppActivity.start(this);
                break;
            case R.id.contact_service_rl:
                CustomerServiceActivity.start(this,consumerPhone);
                break;
            default:
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerMineComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mineModule(new MineModule(this))
                .build()
                .inject(this);
    }


    @Override
    public View getSnackBarHolderView() {
        return itemRl;
    }

    @Override
    public void renderAppUrlInfo(AppUrlInfo appUrlInfo) {
        updateApp(appUrlInfo);
    }

    @Override
    public void renderConsumerPhone(String consumerPhone) {
        this.consumerPhone = consumerPhone;
    }

    @Override
    public void setPresenter(MineContract.SettingPresenter presenter) {

    }

    private void updateApp(AppUrlInfo appUrlInfo) {
        if (appUrlInfo != null && !appUrlInfo.isIfLatest() && !TextUtils.isEmpty(appUrlInfo.getUrl())) {
            UpdateConfig updateConfig = new UpdateConfig();
            updateConfig.setCheckWifi(true);
            updateConfig.setForce(appUrlInfo.isForceUpdate());
            updateConfig.setAlwaysShowDownLoadDialog(!appUrlInfo.isForceUpdate());
            updateConfig.setNotifyImgRes(R.mipmap.icon_update);
            UiConfig uiConfig = new UiConfig();
            uiConfig.setUiType(UiType.CUSTOM);
            uiConfig.setCustomLayoutId(R.layout.view_update_dialog_custom);
            uiConfig.setUpdateLogoImgRes(R.mipmap.icon_update);
            UpdateAppUtils
                    .getInstance()
                    .apkUrl(appUrlInfo.getUrl())
                    .updateTitle(getString(R.string.new_version_update))
                    .updateContent(appUrlInfo.getMessage())
                    .uiConfig(uiConfig)
                    .updateConfig(updateConfig)
                    .update();
        }
    }
}
