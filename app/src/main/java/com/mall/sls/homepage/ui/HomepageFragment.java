package com.mall.sls.homepage.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.BaseFragment;
import com.mall.sls.R;
import com.mall.sls.address.ui.AddressManageActivity;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.location.LocationHelper;
import com.mall.sls.common.unit.BindWxManager;
import com.mall.sls.common.unit.ConvertDpAndPx;
import com.mall.sls.common.unit.LocalCityManager;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.PermissionUtil;
import com.mall.sls.common.unit.SpikeManager;
import com.mall.sls.common.unit.TCAgentUnit;
import com.mall.sls.common.unit.UpdateManager;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.coupon.ui.CouponActivity;
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.data.entity.BannerInfo;
import com.mall.sls.data.entity.CustomViewsInfo;
import com.mall.sls.data.entity.GoodsItemInfo;
import com.mall.sls.data.entity.HomeCouponInfo;
import com.mall.sls.data.entity.HomePageInfo;
import com.mall.sls.data.entity.WebViewDetailInfo;
import com.mall.sls.data.event.WXLoginEvent;
import com.mall.sls.homepage.DaggerHomepageComponent;
import com.mall.sls.homepage.HomepageContract;
import com.mall.sls.homepage.HomepageModule;
import com.mall.sls.homepage.adapter.GoodsItemAdapter;
import com.mall.sls.homepage.adapter.HomeCouponAdapter;
import com.mall.sls.homepage.adapter.JinGangAdapter;
import com.mall.sls.homepage.presenter.HomePagePresenter;
import com.mall.sls.lottery.ui.LotteryListActivity;
import com.mall.sls.message.ui.MessageTypeActivity;
import com.mall.sls.mine.ui.InviteFriendsActivity;
import com.mall.sls.webview.ui.LandingPageActivity;
import com.mall.sls.webview.ui.WebViewActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.stx.xhb.androidx.XBanner;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constant.UiType;
import listener.OnBtnClickListener;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

/**
 * @author jwc on 2020/5/7.
 * 描述：
 */
public class HomepageFragment extends BaseFragment implements HomepageContract.HomePageView, GoodsItemAdapter.OnItemClickListener, JinGangAdapter.OnItemClickListener {


    @BindView(R.id.small_)
    MediumThickTextView small;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.local_city)
    ConventionalTextView localCity;
    @BindView(R.id.local_ll)
    LinearLayout localLl;
    @BindView(R.id.message_count)
    ConventionalTextView messageCount;
    @BindView(R.id.message_rl)
    RelativeLayout messageRl;
    @BindView(R.id.local_rl)
    RelativeLayout localRl;
    @BindView(R.id.banner)
    XBanner banner;
    @BindView(R.id.coupon_rv)
    RecyclerView couponRv;
    @BindView(R.id.receive_iv)
    ImageView receiveIv;
    @BindView(R.id.jingang_rv)
    RecyclerView jingangRv;
    @BindView(R.id.other_rl)
    RelativeLayout otherRl;
    @BindView(R.id.goods_rv)
    RecyclerView goodsRv;
    @BindView(R.id.scrollview)
    NestedScrollView scrollview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private LocationHelper mLocationHelper;
    private String city;

    private List<CustomViewsInfo> data;
    private GoodsItemAdapter goodsItemAdapter;
    private List<BannerInfo> bannerInfos;
    private JinGangAdapter jinGangAdapter;
    private List<BannerInfo> jinGangInfos;
    private HomeCouponAdapter homeCouponAdapter;
    private List<HomeCouponInfo> homeCouponInfos;
    @Inject
    HomePagePresenter homePagePresenter;
    private List<String> group;
    private boolean isFirst = true;

    private int screenWidth;
    private int screenHeight;
    private BigDecimal screenWidthBg;
    private BigDecimal screenHeightBg;
    private BannerInfo bannerInfo;
    private String nativeType;
    // 微信登录
    private static IWXAPI WXapi;

    private List<GoodsItemInfo> startInfos;
    private List<GoodsItemInfo> endInfos;
    private List<GoodsItemInfo> goodsItemInfos;

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
        startInfos = new ArrayList<>();
        endInfos = new ArrayList<>();
        goodsItemInfos=new ArrayList<>();
        mapLocal();
        settingHeight();
        xBannerInit();
        initAdapter();
        homePagePresenter.getHomePageInfo(StaticData.REFLASH_ONE);
        homePagePresenter.getAppUrlInfo();
    }

    private void initAdapter() {
        goodsItemAdapter = new GoodsItemAdapter(getActivity());
        goodsItemAdapter.setOnItemClickListener(this);
        goodsRv.setAdapter(goodsItemAdapter);
        jinGangAdapter = new JinGangAdapter(getActivity());
        jinGangAdapter.setOnItemClickListener(this);
        jingangRv.setAdapter(jinGangAdapter);
        homeCouponAdapter = new HomeCouponAdapter(getActivity());
        couponRv.setAdapter(homeCouponAdapter);
    }

    private void xBannerInit() {
        //设置广告图片点击事件
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                if (bannerInfos != null && position < bannerInfos.size()) {
                    bannerInfo = bannerInfos.get(position);
                    bannerClick(StaticData.REFLASH_ZERO);
                }
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

    //点击banner
    private void bannerClick(String type) {
        if (bannerInfo != null) {
            if (TextUtils.equals(StaticData.REFLASH_ZERO, type)) {
                TCAgentUnit.setEventIdLabel(getActivity(), getString(R.string.home_banner), bannerInfo.getNativeType());
            } else {
                TCAgentUnit.setEventIdLabel(getActivity(), getString(R.string.king_kong), bannerInfo.getNativeType());
            }
            if (TextUtils.equals(StaticData.REFLASH_ZERO, bannerInfo.getLinkType()) && bannerInfo.isLinkOpen()) {//h5界面
                LandingPageActivity.start(getActivity(), bannerInfo.getLink());
            } else if (TextUtils.equals(StaticData.REFLASH_ONE, bannerInfo.getLinkType()) && bannerInfo.isLinkOpen()) {
                nativeType = bannerInfo.getNativeType();
                if (TextUtils.equals(StaticData.GOODS_INFO, nativeType)) {//商品详情
                    if (!TextUtils.isEmpty(bannerInfo.getLink()) && bannerInfo.isLinkOpen()) {
                        Uri uri = Uri.parse("?" + bannerInfo.getLink());
                        String goodsId = uri.getQueryParameter("goodsId");
                        String groupType = uri.getQueryParameter("groupType");
                        if (TextUtils.equals(StaticData.REFLASH_ZERO, groupType)) {
                            OrdinaryGoodsDetailActivity.start(getActivity(), goodsId);
                        } else {
                            ActivityGroupGoodsActivity.start(getActivity(), goodsId);
                        }
                    }
                } else if (TextUtils.equals(StaticData.COUPON, nativeType)) {
                    Intent intent = new Intent(getActivity(), CouponActivity.class);
                    startActivityForResult(intent, RequestCodeStatic.GO_COUPON);
                } else if (TextUtils.equals(StaticData.INVITATION, nativeType)) {
                    InviteFriendsActivity.start(getActivity());
                } else if (TextUtils.equals(StaticData.SECKILL, nativeType)) {
                    if (homepageListener != null) {
                        SpikeManager.saveSpike(StaticData.REFLASH_ONE);
                        homepageListener.goLocalTeam();
                    }
                } else if (TextUtils.equals(StaticData.ADDRESS, nativeType)) {
                    AddressManageActivity.start(getActivity(), StaticData.REFLASH_ONE);
                } else if (TextUtils.equals(StaticData.PRIZE, nativeType)) {
                    LotteryListActivity.start(getActivity());
                }
            }
        }
    }

    private void settingHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenWidthBg = new BigDecimal(dm.widthPixels - ConvertDpAndPx.Dp2Px(getActivity(), 20));
        screenHeightBg = screenWidthBg.multiply(new BigDecimal("140")).divide(new BigDecimal("355"), 0, BigDecimal.ROUND_DOWN);
        screenHeight = Integer.parseInt(screenHeightBg.toString());
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) banner.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.height = screenHeight;// 控件的高强制
        linearParams.width = screenWidth;// 控件的高强制
        banner.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
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
                } else {
                    LocalCityManager.saveLocalCity(aMapLocation.getCity());
                    city = aMapLocation.getDistrict();
                }
//                AreaCodeManager.saveAreaCode(areaCode);
                localCity.setText(city);
            }
        });
        group = new ArrayList<>();
        group.add(Manifest.permission_group.LOCATION);

        if (requestRuntimePermissions(PermissionUtil.permissionGroup(group, null), RequestCodeStatic.REQUEST_PERMISSION_LOCATION)) {
            mLocationHelper.start();
        }
    }

    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
            homePagePresenter.getHomePageInfo(StaticData.REFLASH_ZERO);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            goodsItemAdapter.addMore(endInfos);
            refreshLayout.finishLoadMore();
            refreshLayout.finishLoadMoreWithNoMoreData();
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
    protected void initializeInjector() {
        DaggerHomepageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .homepageModule(new HomepageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void goOrdinaryGoodsDetails(String goodsId) {
        TCAgentUnit.setEventId(getActivity(), getString(R.string.home_goods));
        OrdinaryGoodsDetailActivity.start(getActivity(), goodsId);
    }

    @Override
    public void goActivityGroupGoods(String goodsId) {
        TCAgentUnit.setEventId(getActivity(), getString(R.string.home_goods));
        ActivityGroupGoodsActivity.start(getActivity(), goodsId);
    }

    @Override
    public void goGeneralGoodsDetails(String goodsId) {
        GeneralGoodsDetailsActivity.start(getActivity(), goodsId);
    }

    @Override
    public void renderHomePageInfo(HomePageInfo homePageInfo) {
        refreshLayout.finishRefresh();
        if (homePageInfo != null) {
            bannerInfos = homePageInfo.getBannerInfos();
            if (data == null) {
                data = new ArrayList<>();
            } else {
                data.clear();
            }
            if (bannerInfos != null) {
                for (BannerInfo bannerInfo : bannerInfos) {
                    data.add(new CustomViewsInfo(bannerInfo.getUrl()));
                }
            }
            banner.setPointsIsVisible(data.size() > 1);
            banner.setAutoPlayAble(data.size() > 1);
            banner.setBannerData(R.layout.xbanner_item, data);
            messageCount.setVisibility(TextUtils.equals(StaticData.REFLASH_ZERO, homePageInfo.getUnreadMsgCount()) ? View.GONE : View.VISIBLE);
            jinGangInfos = homePageInfo.getJinGangInfos();
            if (jinGangInfos != null) {
                if (isFirst) {
                    jingangRv.setLayoutManager(new GridLayoutManager(getActivity(), jinGangInfos.size()));
                    isFirst = false;
                }
                jinGangAdapter.setData(jinGangInfos);
            }
            homeCouponInfos = homePageInfo.getHomeCouponInfos();
            if (homeCouponInfos == null || homeCouponInfos.size() == 0) {
                couponRv.setVisibility(View.GONE);
                receiveIv.setVisibility(View.GONE);
            } else {
                couponRv.setVisibility(View.VISIBLE);
                homeCouponAdapter.setData(homeCouponInfos);
                receiveIv.setVisibility(View.VISIBLE);
                //绑定微信
                if (TextUtils.equals(StaticData.REFLASH_ZERO, BindWxManager.getBindWx())) {
                    receiveIv.setSelected(false);
                    EventBus.getDefault().register(this);
                } else {
                    receiveIv.setSelected(true);
                }
            }
            startInfos.clear();
            endInfos.clear();
            goodsItemInfos=homePageInfo.getGoodsItemInfos();//android加载慢，自己分页了
            if(goodsItemInfos!=null&&goodsItemInfos.size()>5){
                for (int i=0;i<goodsItemInfos.size();i++){
                    if(i<5){
                        startInfos.add(goodsItemInfos.get(i));
                    }else {
                        endInfos.add(goodsItemInfos.get(i));
                    }
                }
                refreshLayout.resetNoMoreData();
                goodsItemAdapter.setData(startInfos);
            }else {
                goodsItemAdapter.setData(goodsItemInfos);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void renderBindWx() {
        showMessage(getString(R.string.bind_success_wx));
        BindWxManager.saveBindWx(StaticData.REFLASH_ONE);
        EventBus.getDefault().unregister(this);
        receiveIv.setSelected(true);
        homePagePresenter.couponReceive(StaticData.REFLASH_ONE);
    }

    @Override
    public void renderAppUrlInfo(AppUrlInfo appUrlInfo) {
        if (appUrlInfo != null) {
            updateApp(appUrlInfo);
        }
    }

    @Override
    public void renderCouponReceive() {
        showMessage(getString(R.string.receive_success));
        receiveIv.setVisibility(View.GONE);
        couponRv.setVisibility(View.GONE);
    }

    private void updateApp(AppUrlInfo appUrlInfo) {
        if (appUrlInfo != null && !appUrlInfo.isIfLatest() && !TextUtils.isEmpty(appUrlInfo.getUrl())) {
            if (!TextUtils.isEmpty(UpdateManager.getUpdate()) && TextUtils.equals(UpdateManager.getUpdate(), appUrlInfo.getCurrentVersion()) && !appUrlInfo.isForceUpdate()) {
                return;
            }
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
                    .setCancelBtnClickListener(new OnBtnClickListener() {
                        @Override
                        public boolean onClick() {
                            UpdateManager.saveUpdate(appUrlInfo.getCurrentVersion());
                            return false;
                        }
                    })
                    .update();
        }
    }

    @Override
    public void setPresenter(HomepageContract.HomePagePresenter presenter) {

    }

    @Override
    public void goType(BannerInfo bannerInfo) {
        this.bannerInfo = bannerInfo;
        bannerClick(StaticData.REFLASH_ONE);
    }


    public interface HomepageListener {
        void goLocalTeam();
    }

    private HomepageListener homepageListener;

    public void setHomepageListener(HomepageListener homepageListener) {
        this.homepageListener = homepageListener;
    }

    @OnClick({R.id.other_rl, R.id.message_rl, R.id.local_ll, R.id.receive_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.other_rl:
                if (homepageListener != null) {
                    homepageListener.goLocalTeam();
                }
                break;
            case R.id.message_rl:
                Intent intent = new Intent(getActivity(), MessageTypeActivity.class);
                startActivityForResult(intent, RequestCodeStatic.MESSAGE);
                break;
            case R.id.local_ll:
//                CityPickerActivity.start(getActivity());
                break;
            case R.id.receive_iv:
                if (TextUtils.equals(StaticData.REFLASH_ZERO, BindWxManager.getBindWx())) {
                    wxBind();
                } else {
                    homePagePresenter.couponReceive(StaticData.REFLASH_ONE);
                }
                break;
            default:
        }
    }


    /**
     * 登录取code
     */
    private void wxBind() {
        if (PayTypeInstalledUtils.isWeixinAvilible(getActivity())) {
            WXapi = WXAPIFactory.createWXAPI(getActivity(), StaticData.WX_APP_ID, true);
            WXapi.registerApp(StaticData.WX_APP_ID);
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo";
            WXapi.sendReq(req);
        } else {
            showMessage(getString(R.string.install_weixin));
        }

    }

    //获取code
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccess(WXLoginEvent wxLoginEvent) {
        if (wxLoginEvent != null && !TextUtils.isEmpty(wxLoginEvent.getCode())) {
            homePagePresenter.bindWx(wxLoginEvent.getCode());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.MESSAGE:
                    homePagePresenter.getHomePageInfo(StaticData.REFLASH_ZERO);
                    break;
                case RequestCodeStatic.GO_COUPON:
                    if (homepageListener != null) {
                        homepageListener.goLocalTeam();
                    }
                    break;
                default:
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && getActivity() != null && !getActivity().isDestroyed()) {
            TCAgentUnit.pageStart(getActivity(), getString(R.string.home));
        } else if (getActivity() != null && !getActivity().isDestroyed()) {
            TCAgentUnit.pageEnd(getActivity(), getString(R.string.home));
        }
    }
}
