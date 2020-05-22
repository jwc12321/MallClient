package com.mall.sls.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.MobileManager;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.SystemUtil;
import com.mall.sls.common.unit.TokenManager;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.TokenInfo;
import com.mall.sls.homepage.ui.ConfirmOrderActivity;
import com.mall.sls.login.DaggerLoginComponent;
import com.mall.sls.login.LoginContract;
import com.mall.sls.login.LoginModule;
import com.mall.sls.login.presenter.WeiXinLoginPresenter;
import com.mall.sls.mainframe.ui.MainFrameActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/21.
 * 描述：
 */
public class WeixinLoginActivity extends BaseActivity implements LoginContract.WeiXinLoginView {


    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv)
    MediumThickTextView tv;
    @BindView(R.id.confirm_bt)
    LinearLayout confirmBt;
    @BindView(R.id.choice_item)
    CheckBox choiceItem;
    @BindView(R.id.register_tv)
    ConventionalTextView registerTv;
    @BindView(R.id.privacy_tv)
    ConventionalTextView privacyTv;

    // 微信登录
    private static IWXAPI WXapi;
    @Inject
    WeiXinLoginPresenter weiXinLoginPresenter;

    private String deviceId;
    private String deviceOsVersion;
    private String devicePlatform;


    public static void start(Context context) {
        Intent intent = new Intent(context, WeixinLoginActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin_login);
        ButterKnife.bind(this);
        setHeight(null, title, null);
        initView();
    }

    private void initView(){
        sActivityRef = new WeakReference<>(this);
        EventBus.getDefault().register(this);
        deviceId = SystemUtil.getAndroidId(this);
        deviceOsVersion = SystemUtil.getSystemVersion();
        devicePlatform = "android";
        TokenManager.saveToken("");
        PushManager.getInstance().unBindAlias(this, MobileManager.getMobile(),true);
    }

    @OnClick({R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt://登录
                WXLogin();
                break;
            default:
        }
    }

    /**
     * 登录微信
     */
    private void WXLogin() {
        if (!choiceItem.isChecked()) {
            showMessage(getString(R.string.choice_login));
            return;
        }
        if (PayTypeInstalledUtils.isWeixinAvilible(WeixinLoginActivity.this)) {
            WXapi = WXAPIFactory.createWXAPI(this, StaticData.WX_APP_ID, true);
            WXapi.registerApp(StaticData.WX_APP_ID);
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo";
            WXapi.sendReq(req);
        } else {
            showMessage(getString(R.string.install_weixin));
        }

    }

    @Override
    protected void initializeInjector() {
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    //支付成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccess(String code) {
        if(!TextUtils.isEmpty(code)) {
            weiXinLoginPresenter.weixinLogin(deviceId,deviceOsVersion,devicePlatform,code);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderWeixinLogin(TokenInfo tokenInfo) {
        if(tokenInfo!=null){
            //设置个推的别名和标签
            if(!TextUtils.isEmpty(tokenInfo.getToken())) {
                PushManager.getInstance().bindAlias(this, tokenInfo.getUserInfo().getMobile());
                Tag[] tags = new Tag[1];
                tags[0] = new Tag().setName("all");
                PushManager.getInstance().setTag(this, tags, String.valueOf(System.currentTimeMillis()));
                MobileManager.saveMobile(tokenInfo.getUserInfo().getMobile());
                TokenManager.saveToken(tokenInfo.getToken());
                MainFrameActivity.start(this);
                finish();
            }else {
                BindPhoneActivity.start(this,tokenInfo.getUnionId());
            }
        }
    }

    @Override
    public void setPresenter(LoginContract.WeiXinLoginPresenter presenter) {

    }

    private static WeakReference<WeixinLoginActivity> sActivityRef;

    public static void finishActivity() {
        if (sActivityRef != null && sActivityRef.get() != null) {
            sActivityRef.get().finish();
        }

    }
}
