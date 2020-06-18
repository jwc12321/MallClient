package com.mall.sls;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import com.igexin.sdk.IUserLoggerInterface;
import com.igexin.sdk.PushManager;
import com.meituan.android.walle.WalleChannelReader;
import com.mall.sls.common.unit.DynamicTimeFormat;
import com.mall.sls.common.unit.SPManager;
import com.mall.sls.common.unit.ScreenAdaptation;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import update.UpdateAppUtils;

/**
 * Created by Administrator on 2017/12/15.
 */

public class MainApplication extends MultiDexApplication {
    private static ApplicationComponent mApplicationComponent;
    private static Context context;

    static {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                //全局设置（优先级最低）
                layout.setEnableAutoLoadMore(true);
                layout.setEnableOverScrollDrag(false);
                layout.setEnableOverScrollBounce(true);
                layout.setEnableLoadMoreWhenContentNotFull(true);
                layout.setEnableScrollContentWhenRefreshed(true);
            }
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);

                return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
            }
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initDaggerComponent();

        UpdateAppUtils.init(this);
//        Android7.0的照片问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        SPManager.getInstance().register(this);

        UMShareAPI.get(this);//初始化sdk
        String channelId = WalleChannelReader.getChannel(this.getApplicationContext());
        UMConfigure.init(this, "5ed46e8cdbc2ec08279bd3d7", channelId, UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        disableAPIDialog();
        PushManager.getInstance().initialize(this);
        PushManager.getInstance().setDebugLogger(this, new IUserLoggerInterface() {
            @Override
            public void log(String s) {
                Log.e("PUSH_LOG","个推："+s);
            }
        });
        //talkingdata
//        JLibrary.InitEntry(this); //移动安全联盟统一SDK初始化
        TCAgent.LOG_ON=true;
        // 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
        TCAgent.init(this, "466B709D82C84BED8F948E46D5F5755D", channelId);
        // 如果已经在AndroidManifest.xml配置了App ID和渠道ID，调用TCAgent.init(this)即可；或与AndroidManifest.xml中的对应参数保持一致。
        TCAgent.setReportUncaughtExceptions(true);
    }

    @TargetApi(9)
    protected void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }


    public static Context getContext() {
        return context;
    }

    private void initDaggerComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static void setmApplicationComponent(ApplicationComponent mApplicationComponent) {
        MainApplication.mApplicationComponent = mApplicationComponent;
    }


    /**
     * 反射 禁止弹窗
     */
    private void disableAPIDialog() {
        if (Build.VERSION.SDK_INT < 28) return;
        try {
            Class clazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object activityThread = currentActivityThread.invoke(null);
            Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void setData(int width,int height) {
        new ScreenAdaptation(this,width,height).register();
    }
}
