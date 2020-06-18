package com.mall.sls.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.BuildConfig;
import com.mall.sls.R;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.mine.DaggerMineComponent;
import com.mall.sls.mine.MineContract;
import com.mall.sls.mine.MineModule;
import com.mall.sls.mine.presenter.AboutAppPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constant.UiType;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

/**
 * @author jwc on 2020/6/18.
 * 描述：
 */
public class AboutAppActivity extends BaseActivity implements MineContract.AboutAppView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.new_version)
    ConventionalTextView newVersion;
    @BindView(R.id.right_arrow_iv)
    ImageView rightArrowIv;
    @BindView(R.id.last_version)
    ConventionalTextView lastVersion;
    @BindView(R.id.update_rl)
    RelativeLayout updateRl;

    @Inject
    AboutAppPresenter aboutAppPresenter;
    @BindView(R.id.current_version)
    ConventionalTextView currentVersion;

    private AppUrlInfo appUrlInfo;

    public static void start(Context context) {
        Intent intent = new Intent(context, AboutAppActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView(){
        currentVersion.setText("V"+BuildConfig.VERSION_NAME);
        aboutAppPresenter.getAppUrlInfo();
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
        return null;
    }

    @Override
    public void renderAppUrlInfo(AppUrlInfo appUrlInfo) {
        this.appUrlInfo = appUrlInfo;
        if (appUrlInfo != null) {
            lastVersion.setVisibility(appUrlInfo.isIfLatest() ? View.VISIBLE : View.GONE);
            rightArrowIv.setVisibility(appUrlInfo.isIfLatest() ? View.GONE : View.VISIBLE);
            newVersion.setVisibility(appUrlInfo.isIfLatest() ? View.GONE : View.VISIBLE);
            updateRl.setEnabled(!appUrlInfo.isIfLatest());
        }
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


    @OnClick({R.id.update_rl, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_rl:
                updateApp(appUrlInfo);
                break;
            case R.id.back:
                finish();
                break;
            default:
        }
    }

    @Override
    public void setPresenter(MineContract.AboutAppPresenter presenter) {

    }
}
