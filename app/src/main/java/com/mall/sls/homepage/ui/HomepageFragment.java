package com.mall.sls.homepage.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.BaseFragment;
import com.mall.sls.R;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.location.LocationHelper;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.CustomViewsInfo;
import com.mall.sls.data.entity.GoodsItemInfo;
import com.mall.sls.homepage.adapter.GoodsItemAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.stx.xhb.androidx.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author jwc on 2020/5/7.
 * 描述：
 */
public class HomepageFragment extends BaseFragment implements GoodsItemAdapter.OnItemClickListener{

    @BindView(R.id.small_)
    MediumThickTextView small;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.local_rl)
    RelativeLayout localRl;
    @BindView(R.id.banner)
    XBanner banner;
    @BindView(R.id.scrollview)
    NestedScrollView scrollview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.local_city)
    ConventionalTextView localCity;
    @BindView(R.id.selected_group_tv)
    MediumThickTextView selectedGroupTv;
    @BindView(R.id.more_right_arrow_iv)
    ImageView moreRightArrowIv;
    @BindView(R.id.other_more_tv)
    ConventionalTextView otherMoreTv;
    @BindView(R.id.goods_rv)
    RecyclerView goodsRv;
    private LocationHelper mLocationHelper;
    private String city;
    private String longitude;
    private String latitude;

    private List<CustomViewsInfo> data;
    private GoodsItemAdapter goodsItemAdapter;

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
        setHeight(null, small, null);
        initView();
    }


    private void initView() {
        refreshLayout.setOnMultiPurposeListener(simpleMultiPurposeListener);
        mapLocal();
        xBannerInit();
        initAdapter();
        if (data == null) {
            data = new ArrayList<>();
        } else {
            data.clear();
        }
        data.add(new CustomViewsInfo("http://www.baidu.com/img/bdlogo.png"));
        data.add(new CustomViewsInfo("http://www.baidu.com/img/bdlogo.png"));
        data.add(new CustomViewsInfo("http://www.baidu.com/img/bdlogo.png"));
        banner.setPointsIsVisible(data.size() > 1);
        banner.setAutoPlayAble(data.size() > 1);
        banner.setBannerData(R.layout.xbanner_item, data);
    }

    private void initAdapter(){
        goodsItemAdapter=new GoodsItemAdapter(getActivity());
        goodsItemAdapter.setOnItemClickListener(this);
        goodsRv.setAdapter(goodsItemAdapter);
        List<GoodsItemInfo> goodsItemInfos=new ArrayList<>();
        GoodsItemInfo goodsItemInfo=new GoodsItemInfo("苹果","哈想吃","12","14");
        GoodsItemInfo goodsItemInfo1=new GoodsItemInfo("香蕉","房价肯定就发的","15","22");
        GoodsItemInfo goodsItemInfo2=new GoodsItemInfo("橘子","开发了都JFK的肌肤","18","66");
        goodsItemInfos.add(goodsItemInfo);
        goodsItemInfos.add(goodsItemInfo1);
        goodsItemInfos.add(goodsItemInfo2);
        goodsItemAdapter.setData(goodsItemInfos);
    }

    private void xBannerInit() {
        //设置广告图片点击事件
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
            }
        });
        //加载广告图片
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //在此处使用图片加载框架加载图片，demo中使用glide加载，可替换成自己项目中的图片加载框架
                RoundedImageView roundedImageView = (RoundedImageView) view;
                CustomViewsInfo customViewsInfo = ((CustomViewsInfo) model);
                Glide.with(getActivity()).load(customViewsInfo.getXBannerUrl()).error(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL).into(roundedImageView);
            }
        });
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
                }
                localCity.setText(city);
            }
        });

        if (requestRuntimePermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, RequestCodeStatic.REQUEST_PERMISSION_LOCATION)) {
            mLocationHelper.start();
        }
    }

    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

        }
    };


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

    @Override
    public void goOrdinaryGoods() {
        OrdinaryGoodsDetailActivity.start(getActivity());
    }
}
