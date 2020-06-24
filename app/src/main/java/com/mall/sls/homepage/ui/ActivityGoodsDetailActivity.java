package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.BriefUnit;
import com.mall.sls.common.unit.FormatUtil;
import com.mall.sls.common.unit.HtmlUnit;
import com.mall.sls.common.unit.MainStartManager;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.QRCodeFileUtils;
import com.mall.sls.common.unit.TCAgentUnit;
import com.mall.sls.common.unit.WXShareManager;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.DetailTearDownView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.common.widget.textview.WhiteDrawTextView;
import com.mall.sls.data.entity.ConfirmOrderDetail;
import com.mall.sls.data.entity.CustomViewsInfo;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.entity.GoodsSpec;
import com.mall.sls.data.entity.GroupPurchase;
import com.mall.sls.data.entity.InvitationCodeInfo;
import com.mall.sls.data.entity.ProductListCallableInfo;
import com.mall.sls.homepage.DaggerHomepageComponent;
import com.mall.sls.homepage.HomepageContract;
import com.mall.sls.homepage.HomepageModule;
import com.mall.sls.homepage.presenter.GoodsDetailsPresenter;
import com.mall.sls.mainframe.ui.MainFrameActivity;
import com.mall.sls.mine.ui.CustomerServiceActivity;
import com.mall.sls.mine.ui.SelectShareTypeActivity;
import com.mall.sls.webview.unit.JSBridgeWebChromeClient;
import com.stx.xhb.androidx.XBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/9.
 * 描述：活动商品详情
 */
public class ActivityGoodsDetailActivity extends BaseActivity implements HomepageContract.GoodsDetailsView, DetailTearDownView.TimeOutListener, NestedScrollView.OnScrollChangeListener {


    @BindView(R.id.banner)
    XBanner banner;
    @BindView(R.id.current_price)
    MediumThickTextView currentPrice;
    @BindView(R.id.goods_unit)
    ConventionalTextView goodsUnit;
    @BindView(R.id.original_price)
    WhiteDrawTextView originalPrice;
    @BindView(R.id.goods_original_unit)
    ConventionalTextView goodsOriginalUnit;
    @BindView(R.id.sales)
    ConventionalTextView sales;
    @BindView(R.id.time_type)
    ConventionalTextView timeType;
    @BindView(R.id.count_down)
    DetailTearDownView countDown;
    @BindView(R.id.goods_name)
    MediumThickTextView goodsName;
    @BindView(R.id.goods_brief)
    ConventionalTextView goodsBrief;
    @BindView(R.id.selected_goods)
    ConventionalTextView selectedGoods;
    @BindView(R.id.right_arrow_iv)
    ImageView rightArrowIv;
    @BindView(R.id.sku_rl)
    RelativeLayout skuRl;
    @BindView(R.id.delivery_time)
    ConventionalTextView deliveryTime;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.service_iv)
    ImageView serviceIv;
    @BindView(R.id.home_iv)
    ImageView homeIv;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.day_tv)
    MediumThickTextView dayTv;
    @BindView(R.id.scrollview)
    NestedScrollView scrollview;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.share_iv)
    ImageView shareIv;
    private ProductListCallableInfo productListCallableInfo;
    private List<CustomViewsInfo> data;
    private String goodsId;
    private List<GroupPurchase> groupPurchases;
    private List<String> banners;
    private GoodsDetailsInfo goodsDetailsInfo;
    private List<String> checkSkus;
    private int goodsCount = 1;
    private String unit;
    @Inject
    GoodsDetailsPresenter goodsDetailsPresenter;
    private String consumerPhone;
    private String groupId;
    private String groupRulesId;
    private String teamType; //1:即将开团 2：已开团
    private String backType;
    private String nameText;
    private String briefText;
    private WXShareManager wxShareManager;
    private String wxUrl;
    private String inviteCode;
    private Bitmap shareBitMap;
    private int screenWidth;
    private int screenHeight;
    private List<ProductListCallableInfo> productListCallableInfos;
    private List<GoodsSpec> goodsSpecs;
    private String picUrl;

    public static void start(Context context, String goodsId) {
        Intent intent = new Intent(context, ActivityGoodsDetailActivity.class);
        intent.putExtra(StaticData.GOODS_ID, goodsId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_activity_goods_detail);
        ButterKnife.bind(this);
        setHeight(back, null, share);
        initView();
    }

    private void initView() {
        goodsId = getIntent().getStringExtra(StaticData.GOODS_ID);
        EventBus.getDefault().register(this);
        wxShareManager = WXShareManager.getInstance(this);
        scrollview.setOnScrollChangeListener(this);
        settingHeight();
        xBannerInit();
        initWebView();
        goodsDetailsPresenter.getGoodsDetails(goodsId);
        goodsDetailsPresenter.getConsumerPhone();
        goodsDetailsPresenter.getInvitationCodeInfo();

    }


    private void settingHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = screenWidth;
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) banner.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.height = screenHeight;// 控件的高强制
        linearParams.width = screenWidth;// 控件的高强制
        banner.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }

    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //取消滚动条白边效果
        webView.setWebChromeClient(new JSBridgeWebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
                // handleMessage(Message msg);// 进行其他处理
            }
        });
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(webView.getSettings().MIXED_CONTENT_ALWAYS_ALLOW);  //注意安卓5.0以上的权限
        }
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
                Glide.with(ActivityGoodsDetailActivity.this).load(customViewsInfo.getXBannerUrl()).error(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL).into(roundedImageView);
            }
        });
    }

    @Override
    protected void initializeInjector() {
        DaggerHomepageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .homepageModule(new HomepageModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.back, R.id.confirm_bt, R.id.service_iv, R.id.sku_rl, R.id.home_iv, R.id.share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.home_iv:
                MainStartManager.saveMainStart(StaticData.REFLASH_ONE);
                MainFrameActivity.start(this);
                finish();
                break;
            case R.id.service_iv:
                CustomerServiceActivity.start(this, consumerPhone);
                break;
            case R.id.sku_rl:
                goSelectSpecReturn(StaticData.REFLASH_ONE);
                break;
            case R.id.confirm_bt://发起拼单
                TCAgentUnit.setEventId(this,getString(R.string.event_purchase_details));
                initiateBill();
                break;
            case R.id.share://分享
                TCAgentUnit.setEventId(this,getString(R.string.goods_details_share));
                if (!PayTypeInstalledUtils.isWeixinAvilible(ActivityGoodsDetailActivity.this)) {
                    showMessage(getString(R.string.install_weixin));
                    return;
                }
                shareBitMap = QRCodeFileUtils.createBitmap3(shareIv, 150, 150);//直接url转bitmap背景白色变成黑色，后面想到方法可以改善
                Intent intent = new Intent(this, SelectShareTypeActivity.class);
                startActivityForResult(intent, RequestCodeStatic.SELECT_SHARE_TYPE);
                break;
            default:
        }
    }

    private void initiateBill() {
//        if (productListCallableInfo == null) {
        goSelectSpec(StaticData.REFLASH_ONE);
//        } else {
//            goodsDetailsPresenter.cartFastAdd(goodsId, productListCallableInfo.getId(), true, String.valueOf(goodsCount), groupId, groupRulesId);
//        }
    }

    private void goSelectSpecReturn(String type) {
        Intent intent = new Intent(this, SelectSpecActivity.class);
        intent.putExtra(StaticData.PRODUCT_LIST, (Serializable) goodsSpecs);
        intent.putExtra(StaticData.SPECIFICATION_LIST, (Serializable) productListCallableInfos);
        intent.putExtra(StaticData.PIC_URL,picUrl);
        intent.putExtra(StaticData.SKU_CHECK, (Serializable) checkSkus);
        intent.putExtra(StaticData.CHOICE_TYPE, type);
        intent.putExtra(StaticData.GOODS_COUNT, goodsCount);
        startActivityForResult(intent, RequestCodeStatic.REQUEST_SPEC_RETURN);
    }


    private void goSelectSpec(String type) {
        Intent intent = new Intent(this, SelectSpecActivity.class);
        intent.putExtra(StaticData.PRODUCT_LIST, (Serializable) goodsSpecs);
        intent.putExtra(StaticData.SPECIFICATION_LIST, (Serializable) productListCallableInfos);
        intent.putExtra(StaticData.PIC_URL,picUrl);
        intent.putExtra(StaticData.SKU_CHECK, (Serializable) checkSkus);
        intent.putExtra(StaticData.CHOICE_TYPE, type);
        intent.putExtra(StaticData.GOODS_COUNT, goodsCount);
        startActivityForResult(intent, RequestCodeStatic.REQUEST_SPEC);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.REQUEST_SPEC_RETURN:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        checkSkus = (List<String>) bundle.getSerializable(StaticData.SKU_CHECK);
                        productListCallableInfo = (ProductListCallableInfo) bundle.getSerializable(StaticData.SKU_INFO);
                        goodsCount = bundle.getInt(StaticData.GOODS_COUNT);
                        selectedGoods.setText(getString(R.string.is_selected) + " " + productListCallableInfo.getSpecifications() + "/" + unit);
                    }
                    break;
                case RequestCodeStatic.REQUEST_SPEC:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        checkSkus = (List<String>) bundle.getSerializable(StaticData.SKU_CHECK);
                        productListCallableInfo = (ProductListCallableInfo) bundle.getSerializable(StaticData.SKU_INFO);
                        goodsCount = bundle.getInt(StaticData.GOODS_COUNT);
                        selectedGoods.setText(getString(R.string.is_selected) + " " + productListCallableInfo.getSpecifications() + "/" + unit);
                        goodsDetailsPresenter.cartFastAdd(goodsId, productListCallableInfo.getId(), true, String.valueOf(goodsCount), groupId, groupRulesId);
                    }
                    break;
                case RequestCodeStatic.SELECT_SHARE_TYPE:
                    if (data != null) {
                        backType = data.getStringExtra(StaticData.BACK_TYPE);
                        shareWx(TextUtils.equals(StaticData.REFLASH_ONE, backType));
                    }
                    break;
                default:
            }
        }
    }

    private void shareWx(boolean isFriend) {
//        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.app_icon);
        String url = wxUrl + "goods/activity/" + goodsId + StaticData.WX_INVITE_CODE + inviteCode;
        wxShareManager.shareUrlToWX(isFriend, url, shareBitMap, nameText, briefText);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderGoodsDetails(GoodsDetailsInfo goodsDetailsInfo) {
        this.goodsDetailsInfo = goodsDetailsInfo;
        if (goodsDetailsInfo != null) {
            banners = goodsDetailsInfo.getGallerys();
            if (data == null) {
                data = new ArrayList<>();
            } else {
                data.clear();
            }
            if (banners != null) {
                for (String bannerInfo : banners) {
                    data.add(new CustomViewsInfo(bannerInfo));
                }
            }
            banner.setPointsIsVisible(data.size() > 1);
            banner.setAutoPlayAble(data.size() > 1);
            banner.setBannerData(R.layout.xbanner_zero_item, data);
            currentPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(goodsDetailsInfo.getRetailPrice()));
            unit = goodsDetailsInfo.getUnit();
            goodsUnit.setText("/" + unit);
            goodsOriginalUnit.setText("/" + unit);
            originalPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(goodsDetailsInfo.getCounterPrice()));
            sales.setText("累计销量" + goodsDetailsInfo.getSalesQuantity());
            nameText = BriefUnit.returnName(goodsDetailsInfo.getRetailPrice(), goodsDetailsInfo.getName());
            briefText = BriefUnit.returnBrief(goodsDetailsInfo.getBrief());
            goodsName.setText(goodsDetailsInfo.getName());
            goodsBrief.setText(goodsDetailsInfo.getBrief());
            goodsBrief.setVisibility(TextUtils.isEmpty(goodsDetailsInfo.getBrief()) ? View.GONE : View.VISIBLE);
            selectedGoods.setText(getString(R.string.select_spec));
            if (!TextUtils.isEmpty(goodsDetailsInfo.getNow()) && !TextUtils.isEmpty(goodsDetailsInfo.getGroupExpireTime()) && !TextUtils.isEmpty(goodsDetailsInfo.getStartTime())) {
                long now = FormatUtil.dateToStamp(goodsDetailsInfo.getNow());
                long groupExpireTime = FormatUtil.dateToStamp(goodsDetailsInfo.getGroupExpireTime());
                long startTime = FormatUtil.dateToStamp(goodsDetailsInfo.getStartTime());
                if (now < startTime) {
                    long day = FormatUtil.day(now, startTime);
                    timeType.setText(getString(R.string.open_time));
                    confirmBt.setEnabled(false);
                    confirmBt.setText(FormatUtil.formatDate(String.valueOf(startTime)) + "开抢");
                    teamType = StaticData.REFLASH_ONE;
                    if (day > 0) {
                        dayTv.setText(day + "天");
                        dayTv.setVisibility(View.VISIBLE);
                        countDown.setVisibility(View.GONE);
                    } else {
                        dayTv.setVisibility(View.GONE);
                        countDown.setVisibility(View.VISIBLE);
                        countDown.startTearDown(startTime / 1000, now / 1000);
                    }
                } else if (now > startTime && now < groupExpireTime) {
                    long day = FormatUtil.day(now, groupExpireTime);
                    timeType.setText(getString(R.string.remaining_spike));
                    confirmBt.setEnabled(true);
                    teamType = StaticData.REFLASH_TWO;
                    confirmBt.setText(getString(R.string.go_buy));
                    if (day > 0) {
                        dayTv.setText(day + "天");
                        dayTv.setVisibility(View.VISIBLE);
                        countDown.setVisibility(View.GONE);
                    } else {
                        dayTv.setVisibility(View.GONE);
                        countDown.setVisibility(View.VISIBLE);
                        countDown.startTearDown(groupExpireTime / 1000, now / 1000);
                    }

                }
            }
            groupPurchases = goodsDetailsInfo.getGroupPurchases();
            if (groupPurchases != null && groupPurchases.size() == 1) {
                groupId = groupPurchases.get(0).getGrouponId();
                groupRulesId = groupPurchases.get(0).getRulesId();
            }
            picUrl=goodsDetailsInfo.getPicUrl();
            goodsSpecs=goodsDetailsInfo.getGoodsSpecs();
            productListCallableInfos = goodsDetailsInfo.getProductListCallableInfos();
            if (productListCallableInfos != null && productListCallableInfos.size() == 1) {
                ProductListCallableInfo productListCallableInfo = productListCallableInfos.get(0);
                String specifications = productListCallableInfo.getSpecifications();
                if (!TextUtils.isEmpty(specifications)) {
                    checkSkus = Arrays.asList(specifications.split(","));
                }
            }
            if (!TextUtils.isEmpty(goodsDetailsInfo.getDetail())) {
                webView.loadDataWithBaseURL(null, HtmlUnit.getHtmlData(goodsDetailsInfo.getDetail()), "text/html", "utf-8", null);
            }
            GlideHelper.load(this, goodsDetailsInfo.getPicUrl(), R.mipmap.icon_default_goods, shareIv);
        }
    }

    @Override
    public void renderConsumerPhone(String consumerPhone) {
        this.consumerPhone = consumerPhone;
    }

    @Override
    public void renderCartFastAdd(ConfirmOrderDetail confirmOrderDetail) {
        ConfirmOrderActivity.start(this, confirmOrderDetail, StaticData.REFLASH_FOUR, wxUrl, inviteCode);
    }

    @Override
    public void renderGroupRemind() {
        showMessage(getString(R.string.remind_to_you));
    }

    @Override
    public void renderInvitationCodeInfo(InvitationCodeInfo invitationCodeInfo) {
        if (invitationCodeInfo != null) {
            wxUrl = invitationCodeInfo.getBaseUrl();
            inviteCode = invitationCodeInfo.getInvitationCode();
        }
    }

    @Override
    public void setPresenter(HomepageContract.GoodsDetailsPresenter presenter) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDown.cancel();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void timeOut() {
        if (TextUtils.equals(StaticData.REFLASH_ONE, teamType)) {
            goodsDetailsPresenter.getGoodsDetails(goodsId);
        } else {
            showMessage(getString(R.string.activity_over));
            finish();
        }
    }

    //分享成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShareSuccess(String code) {
        showMessage(getString(R.string.share_success));
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY <= 0) {   //设置标题的背景颜色
            titleRel.setBackgroundColor(Color.argb((int) 0, 144, 151, 166));
        } else if (scrollY > 0 && scrollY <= titleRel.getHeight()) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) scrollY / titleRel.getHeight();
            float alpha = (255 * scale);
            titleRel.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        } else {    //滑动到banner下面设置普通颜色
            titleRel.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        TCAgentUnit.pageStart(this,getString(R.string.event_page_detail));
    }

    @Override
    protected void onPause() {
        super.onPause();
        TCAgentUnit.pageEnd(this,getString(R.string.event_page_detail));
    }

}
