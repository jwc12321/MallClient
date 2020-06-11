package com.mall.sls;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.HandleBackUtil;
import com.mall.sls.common.widget.dialog.LoadingDialog;
import com.mall.sls.data.RemoteDataException;
import com.mall.sls.login.ui.WeixinLoginActivity;
import com.mall.sls.mainframe.ui.MainFrameActivity;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/12/27.
 */

public abstract class BaseActivity extends AppCompatActivity implements LoadDataView{
    int statusBarHeight1 = -1;
    private MainApplication mainApplication;
    private Toast toast;

    /**
     * snack bar holder view
     * 用于显示snack bar, 基于activity本身或者fragment调用统一使用该函数,方便处理一些视图的偏移,fab等.
     */
    public abstract View getSnackBarHolderView();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        }
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        mainApplication = (MainApplication) getApplication();//获取MyApplication的对象
//        if (width == 720) {
//            mainApplication.setData(width, height);
//        }
        changeStateBar();
        initializeInjector();
    }

    public int statusBarheight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    public void setHeight(View view1, View view2, View view3) {
        if (view1 != null && view1.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view1.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view1.requestLayout();
        }
        if (view2 != null && view2.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view2.requestLayout();
        }
        if (view3 != null && view3.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view3.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view3.requestLayout();
        }
    }

    public void setHeight(View view1, View view2, View view3, View view4) {
        if (view1 != null && view1.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view1.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view1.requestLayout();
        }
        if (view2 != null && view2.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view2.requestLayout();
        }
        if (view3 != null && view3.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view3.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view3.requestLayout();
        }
        if (view4 != null && view4.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view4.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view4.requestLayout();
        }
    }

    private void changeStateBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
            } catch (Exception e) {
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    protected void initializeInjector() {
        getApplicationComponent().inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return ((MainApplication) getApplication()).getApplicationComponent();
    }


    //权限
    public boolean requestRuntimePermissions(final String[] permissions, final int requestCode) {
        boolean ret = true;
        for (String permission : permissions) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                ret &= (PermissionChecker.checkSelfPermission(BaseActivity.this, permission) == PermissionChecker.PERMISSION_GRANTED);
            else
                ret &= (ContextCompat.checkSelfPermission(BaseActivity.this, permission) == PackageManager.PERMISSION_GRANTED);
        }
        if (ret) {
            return true;
        }
        boolean rationale = false;
        for (String permission : permissions) {
            rationale |= ActivityCompat.shouldShowRequestPermissionRationale(BaseActivity.this, permission);

        }
        if (rationale) {
            makePrimaryColorSnackBar(R.string.common_request_permission, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.common_allow_permission, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
                        }
                    })
                    .show();

        } else {
            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);

        }
        return false;
    }

    public Snackbar makeColorSnackBar(@StringRes int resId, int duration, @ColorInt int colorId) {
        Snackbar snackbar = Snackbar.make(getSnackBarHolderView(), resId, duration);
        snackbar.getView().setBackgroundColor(colorId);
        return snackbar;
    }

    public Snackbar makePrimaryColorSnackBar(@StringRes int resId, int duration) {
        return makeColorSnackBar(resId, duration, Color.parseColor("#3F51B5"));
    }


    @Override
    public void showMessage(String msg) {
        toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LayoutInflater inflater = this.getLayoutInflater();
        LinearLayout toastLayout = (LinearLayout) inflater.inflate(R.layout.base_toast, null);
        TextView txtToast = (TextView) toastLayout.findViewById(R.id.txt_toast);
        txtToast.setText(msg);
        toast.setView(toastLayout);
        toast.show();
    }

    @Override
    public void showError(Throwable e) {
        if (e != null) {
            if (e instanceof RemoteDataException) {
                if (TextUtils.equals(StaticData.TOKEN_OVER_ONE,((RemoteDataException) e).getRetCode())||TextUtils.equals(StaticData.TOKEN_OVER_TWO,((RemoteDataException) e).getRetCode())){
                    WeixinLoginActivity.start(this);
                    MainFrameActivity.finishActivity();
                }else{
                    showMessage(((RemoteDataException) e).getMessage(this));
                }
            } else {
                if (e instanceof HttpException) {
                    HttpException exception = (HttpException) e;
                    if (TextUtils.equals("401", exception.response().code() + "")) {
                        WeixinLoginActivity.start(this);
                        MainFrameActivity.finishActivity();
                    } else {
                        showMessage(getString(R.string.fail_to_access_servers));
                    }
                } else {
                    showMessage(getString(R.string.fail_to_access_servers));
                }
            }
        }

    }

    private LoadingDialog loadingDialog;

    @Override
    public void showLoading(String tips) {
        if (!this.isFinishing()) {
            loadingDialog = new LoadingDialog(this, tips);
            loadingDialog.getWindow().setDimAmount(0f);
            loadingDialog.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public void showError(String errMsg) {
        if (getApplicationContext() != null) {
            showMessage(errMsg);
        }
    }

    @Override
    public void onBackPressed() {
        if (!HandleBackUtil.handleBackPress(this)) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 重写 getResource 方法，防止系统字体影响
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1) {
            Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                v.clearFocus();
                if (imm != null) {
                    assert v != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

}
