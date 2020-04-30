package com.mall.sls;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.mall.sls.common.unit.HandleBackInterface;
import com.mall.sls.common.unit.HandleBackUtil;


/**
 *
 */

public class BaseFragment extends Fragment implements LoadDataView, HandleBackInterface {
    private Toast toast;
    private Dialog mWeiboDialog;
    int statusBarHeight1 = -1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();
    }

    public int statusBarheight(){
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    public void setHeight(View view1,View view2,View view3){
        if (view1!=null&&view1.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view1.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view1.requestLayout();
        }
        if (view2!=null&&view2.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view2.requestLayout();
        }
        if (view3!=null&&view3.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view3.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view3.requestLayout();
        }
    }

    public void setHeight(View view1,View view2,View view3,View view4){
        if (view1!=null&&view1.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view1.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view1.requestLayout();
        }
        if (view2!=null&&view2.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view2.requestLayout();
        }
        if (view3!=null&&view3.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view3.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view3.requestLayout();
        }
        if (view4!=null&&view4.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view4.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view4.requestLayout();
        }
    }

    public void setHeight(View view1,View view2,View view3,View view4,View view5){
        if (view1!=null&&view1.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view1.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view1.requestLayout();
        }
        if (view2!=null&&view2.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view2.requestLayout();
        }
        if (view3!=null&&view3.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view3.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view3.requestLayout();
        }
        if (view4!=null&&view4.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view4.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view4.requestLayout();
        }
        if (view5!=null&&view5.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view5.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view5.requestLayout();
        }
    }


    public View getSnackBarHolderView() {
        if (getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).getSnackBarHolderView();
        } else {
            throw new RuntimeException(getString(R.string.snack_bar_exception_msg));
        }
    }

    public boolean requestRuntimePermissions(final String[] permissions, final int requestCode) {
        boolean ret = true;
        for (String permission : permissions) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                ret &= (PermissionChecker.checkSelfPermission(getContext(), permission) == PermissionChecker.PERMISSION_GRANTED);
            else
                ret &= (ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED);
        }
        if (ret) {
            return true;
        }
        boolean rationale = false;
        for (String permission : permissions) {
            rationale |= shouldShowRequestPermissionRationale(permission);
        }
        if (rationale) {
            makePrimaryColorSnackBar(R.string.common_request_permission, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.common_allow_permission, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestPermissions(permissions, requestCode);
                        }
                    })
                    .show();

        } else {
            requestPermissions(permissions, requestCode);
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

    public ApplicationComponent getApplicationComponent() {
        if (getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).getApplicationComponent();
        } else {
            throw new RuntimeException(getString(R.string.snack_bar_exception_msg));
        }
    }

    protected void initializeInjector() {

    }

    @Override
    public void showMessage(String msg) {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).showMessage(msg);
            }
        }
    }

    @Override
    public void showError(Throwable e) {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).showError(e);
            }
        }
    }

    @Override
    public void showLoading(String tips) {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).showLoading(tips);
            }
        }
    }

    @Override
    public void dismissLoading() {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).dismissLoading();
            }
        }
    }


    protected void notifyNetWorkChange(boolean isConnected) {

    }

    @Override
    public boolean onBackPressed() {
        return HandleBackUtil.handleBackPress(this);
    }
}
