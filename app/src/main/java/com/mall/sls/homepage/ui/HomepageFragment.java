package com.mall.sls.homepage.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.mall.sls.BaseFragment;
import com.mall.sls.R;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.location.LocationHelper;

import butterknife.ButterKnife;

/**
 * @author jwc on 2020/5/7.
 * 描述：
 */
public class HomepageFragment extends BaseFragment {

    private LocationHelper mLocationHelper;
    private String city;
    private String longitude;
    private String latitude;

    public static HomepageFragment newInstance() {
        HomepageFragment fragment = new HomepageFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_homepage, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    private void initView(){
        mapLocal();
    }


    //地图定位
    private void mapLocal() {
        mLocationHelper = LocationHelper.sharedInstance(getContext());
        mLocationHelper.addOnLocatedListener(new LocationHelper.OnLocatedListener() {
            @Override
            public void onLocated(AMapLocation aMapLocation) {
                if (aMapLocation == null || (TextUtils.isEmpty(aMapLocation.getDistrict()) && TextUtils.equals("0.0", String.valueOf(aMapLocation.getLongitude())) && TextUtils.equals("0.0", String.valueOf(aMapLocation.getLatitude())))) {
                    showMessage("定位失败，请重新定位");
                    city = "";
                    longitude = "";
                    latitude = "";
                } else {
                    city = aMapLocation.getDistrict();
                    longitude = aMapLocation.getLongitude() + "";
                    latitude = aMapLocation.getLatitude() + "";
                    showMessage(city+longitude+latitude);
                }
            }
        });

        if (requestRuntimePermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, RequestCodeStatic.REQUEST_PERMISSION_LOCATION)) {
            mLocationHelper.start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCodeStatic.REQUEST_PERMISSION_LOCATION:
                for (int gra : grantResults) {
                    if (gra != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                mLocationHelper.start();
                break;
        }
    }

}
