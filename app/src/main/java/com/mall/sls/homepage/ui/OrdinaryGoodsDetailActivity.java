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
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.BriefUnit;
import com.mall.sls.common.unit.HtmlUnit;
import com.mall.sls.common.unit.MainStartManager;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.QRCodeFileUtils;
import com.mall.sls.common.unit.TCAgentUnit;
import com.mall.sls.common.unit.WXShareManager;
import com.mall.sls.common.widget.textview.ConventionalTextView;
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
 * 描述：普通商品平团详情
 */
public class OrdinaryGoodsDetailActivity extends BaseActivity implements HomepageContract.GoodsDetailsView, NestedScrollView.OnScrollChangeListener {


    @BindView(R.id.banner)
    XBanner banner;
    @BindView(R.id.current_price)
    MediumThickTextView currentPrice;
    @BindView(R.id.current_price_ll)
    LinearLayout currentPriceLl;
    @BindView(R.id.original_price)
    WhiteDrawTextView originalPrice;
    @BindView(R.id.sales)
    ConventionalTextView sales;
    @BindView(R.id.goods_name)
    MediumThickTextView goodsName;
    @BindView(R.id.goods_brief)
    ConventionalTextView goodsBrief;
    @BindView(R.id.courierType)
    ConventionalTextView courierType;
    @BindView(R.id.selected_goods)
    ConventionalTextView selectedGoods;
    @BindView(R.id.right_arrow_iv)
    ImageView rightArrowIv;
    @BindView(R.id.sku_rl)
    RelativeLayout skuRl;
    @BindView(R.id.delivery_time)
    ConventionalTextView deliveryTime;
    @BindView(R.id.group_number)
    ConventionalTextView groupNumber;
    @BindView(R.id.up_phone_number)
    ConventionalTextView upPhoneNumber;
    @BindView(R.id.up_spell_bt)
    ConventionalTextView upSpellBt;
    @BindView(R.id.up_poor_tv)
    ConventionalTextView upPoorTv;
    @BindView(R.id.up_group)
    RelativeLayout upGroup;
    @BindView(R.id.down_phone_number)
    ConventionalTextView downPhoneNumber;
    @BindView(R.id.down_spell_bt)
    ConventionalTextView downSpellBt;
    @BindView(R.id.down_poor_tv)
    ConventionalTextView downPoorTv;
    @BindView(R.id.down_group)
    RelativeLayout downGroup;
    @BindView(R.id.group_flipper)
    ViewFlipper groupFlipper;
    @BindView(R.id.group_ll)
    LinearLayout groupLl;
    @BindView(R.id.goods_detail_iv)
    ImageView goodsDetailIv;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.scrollview)
    NestedScrollView scrollview;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.service_iv)
    ImageView serviceIv;
    @BindView(R.id.home_iv)
    ImageView homeIv;
    @BindView(R.id.initiate_bill_price)
    MediumThickTextView initiateBillPrice;
    @BindView(R.id.initiate_bill_bt)
    LinearLayout initiateBillBt;
    @BindView(R.id.individual_shopping_price)
    MediumThickTextView individualShoppingPrice;
    @BindView(R.id.individual_shopping_tv)
    LinearLayout individualShoppingTv;
    @BindView(R.id.share_iv)
    ImageView shareIv;
    private ProductListCallableInfo productListCallableInfo;
    private List<CustomViewsInfo> data;
    private String goodsId;
    private List<GroupPurchase> groupPurchases;
    private List<GroupPurchase> allGroupPurchases;
    private List<String> banners;
    private GoodsDetailsInfo goodsDetailsInfo;
    private List<String> checkSkus;
    private int goodsCount = 1;
    private String unit;
    private String groupId;
    private String groupRulesId;
    private String upGroupId;
    private String upGroupRulesId;
    private String downGroupId;
    private String downGroupRulesId;
    private String upMobile;
    private String downMobile;
    private String upSurplus;
    private String downSurplus;
    private String consumerPhone;
    private String purchaseType;
    private String oldGroupRulesId;
    private boolean isGroup;
    private List<ProductListCallableInfo> productListCallableInfos;
    private List<GoodsSpec> goodsSpecs;
    private String picUrl;
    private WXShareManager wxShareManager;
    private String backType;
    private String nameText;
    private String briefText;
    private String wxUrl;
    private String inviteCode;
    private Bitmap shareBitMap;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int themeId;
    private int screenWidth;
    private int screenHeight;

    @Inject
    GoodsDetailsPresenter goodsDetailsPresenter;

    public static void start(Context context, String goodsId) {
        Intent intent = new Intent(context, OrdinaryGoodsDetailActivity.class);
        intent.putExtra(StaticData.GOODS_ID, goodsId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinary_goods_detail);
        ButterKnife.bind(this);
        setHeight(back, null, share);
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        goodsId = getIntent().getStringExtra(StaticData.GOODS_ID);
        wxShareManager = WXShareManager.getInstance(this);
        scrollview.setOnScrollChangeListener(this);
        themeId = R.style.picture_default_style;
        allGroupPurchases = new ArrayList<>();
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
//                zoom(banners,position);
            }
        });
        //加载广告图片
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //在此处使用图片加载框架加载图片，demo中使用glide加载，可替换成自己项目中的图片加载框架
                RoundedImageView roundedImageView = (RoundedImageView) view;
                CustomViewsInfo customViewsInfo = ((CustomViewsInfo) model);
                Glide.with(OrdinaryGoodsDetailActivity.this).load(customViewsInfo.getXBannerUrl()).error(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL).into(roundedImageView);
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

    @OnClick({R.id.back, R.id.individual_shopping_tv, R.id.initiate_bill_bt, R.id.service_iv, R.id.sku_rl, R.id.up_spell_bt, R.id.down_spell_bt, R.id.home_iv, R.id.share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.home_iv:
                MainStartManager.saveMainStart(StaticData.REFRESH_ONE);
                MainFrameActivity.start(this);
                finish();
                break;
            case R.id.initiate_bill_bt://发起拼单
                groupId = "";
                groupRulesId = oldGroupRulesId;
                purchaseType = StaticData.REFRESH_TWO;
                isGroup = true;
                initiateBill();
                TCAgentUnit.setEventId(this, getString(R.string.initiate_bill_buy));
                break;
            case R.id.service_iv:
                CustomerServiceActivity.start(this, consumerPhone);
                break;
            case R.id.sku_rl:
                goSelectSpecReturn(StaticData.REFRESH_ZERO);
                break;
            case R.id.individual_shopping_tv://单独购买
                groupId = "";
                groupRulesId = oldGroupRulesId;
                purchaseType = StaticData.REFRESH_ONE;
                isGroup = false;
                individualShopping();
                TCAgentUnit.setEventId(this, getString(R.string.individual_shopping));
                break;
            case R.id.up_spell_bt:
                groupId = upGroupId;
                groupRulesId = upGroupRulesId;
                purchaseType = StaticData.REFRESH_THREE;
                isGroup = true;
                goSpellingReminder(upMobile, upSurplus);
                TCAgentUnit.setEventId(this, getString(R.string.buy_together));
                break;
            case R.id.down_spell_bt:
                groupId = downGroupId;
                groupRulesId = downGroupRulesId;
                purchaseType = StaticData.REFRESH_THREE;
                isGroup = true;
                goSpellingReminder(downMobile, downSurplus);
                TCAgentUnit.setEventId(this, getString(R.string.buy_together));
                break;
            case R.id.share://分享
                TCAgentUnit.setEventId(this, getString(R.string.goods_details_share));
                if (!PayTypeInstalledUtils.isWeixinAvilible(OrdinaryGoodsDetailActivity.this)) {
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

    private void goSpellingReminder(String mobile, String surplus) {
        Intent intent = new Intent(this, SpellingReminderActivity.class);
        intent.putExtra(StaticData.MOBILE, mobile);
        intent.putExtra(StaticData.SURPLUS, surplus);
        startActivityForResult(intent, RequestCodeStatic.SPELLING_REMINDER);
    }

    private void initiateBill() {
//        if (productListCallableInfo == null) {
        goSelectSpec(StaticData.REFRESH_ONE);
//        } else {
//            goodsDetailsPresenter.cartFastAdd(goodsId, productListCallableInfo.getId(), true, String.valueOf(goodsCount), groupId, groupRulesId);
//        }
    }

    private void individualShopping() {
//        if (productListCallableInfo == null) {
        goSelectSpec(StaticData.REFRESH_ZERO);
//        } else {
//            goodsDetailsPresenter.cartFastAdd(goodsId, productListCallableInfo.getId(), false, String.valueOf(goodsCount), groupId, groupRulesId);
//        }
    }

    private void goSelectSpecReturn(String type) {
        Intent intent = new Intent(this, SelectSpecActivity.class);
        intent.putExtra(StaticData.PRODUCT_LIST, (Serializable) goodsSpecs);
        intent.putExtra(StaticData.SPECIFICATION_LIST, (Serializable) productListCallableInfos);
        intent.putExtra(StaticData.PIC_URL, picUrl);
        intent.putExtra(StaticData.SKU_CHECK, (Serializable) checkSkus);
        intent.putExtra(StaticData.CHOICE_TYPE, type);
        intent.putExtra(StaticData.GOODS_COUNT, goodsCount);
        startActivityForResult(intent, RequestCodeStatic.REQUEST_SPEC_RETURN);
    }

    private void goSelectSpec(String type) {
        Intent intent = new Intent(this, SelectSpecActivity.class);
        intent.putExtra(StaticData.PRODUCT_LIST, (Serializable) goodsSpecs);
        intent.putExtra(StaticData.SPECIFICATION_LIST, (Serializable) productListCallableInfos);
        intent.putExtra(StaticData.PIC_URL, picUrl);
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
                case RequestCodeStatic.REQUEST_SPEC_RETURN://请选择规格
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        checkSkus = (List<String>) bundle.getSerializable(StaticData.SKU_CHECK);
                        productListCallableInfo = (ProductListCallableInfo) bundle.getSerializable(StaticData.SKU_INFO);
                        goodsCount = bundle.getInt(StaticData.GOODS_COUNT);
                        selectedGoods.setText(getString(R.string.is_selected) + " " + productListCallableInfo.getSpecifications() + "/" + unit);
                        individualShoppingPrice.setText(NumberFormatUnit.goodsFormat(productListCallableInfo.getPrice()));
                        initiateBillPrice.setText(NumberFormatUnit.goodsFormat(productListCallableInfo.getPreferentialPrice()));
                    }
                    break;
                case RequestCodeStatic.REQUEST_SPEC://单独购买和发起拼单取选择规格
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        checkSkus = (List<String>) bundle.getSerializable(StaticData.SKU_CHECK);
                        productListCallableInfo = (ProductListCallableInfo) bundle.getSerializable(StaticData.SKU_INFO);
                        goodsCount = bundle.getInt(StaticData.GOODS_COUNT);
                        selectedGoods.setText(getString(R.string.is_selected) + " " + productListCallableInfo.getSpecifications() + "/" + unit);
                        individualShoppingPrice.setText(NumberFormatUnit.goodsFormat(productListCallableInfo.getPrice()));
                        initiateBillPrice.setText(NumberFormatUnit.goodsFormat(productListCallableInfo.getPreferentialPrice()));
                        goodsDetailsPresenter.cartFastAdd(goodsId, productListCallableInfo.getId(), isGroup, String.valueOf(goodsCount), groupId, groupRulesId);
                    }
                    break;
                case RequestCodeStatic.SPELLING_REMINDER:
                    initiateBill();
                    break;
                case RequestCodeStatic.SELECT_SHARE_TYPE:
                    if (data != null) {
                        backType = data.getStringExtra(StaticData.BACK_TYPE);
                        shareWx(TextUtils.equals(StaticData.REFRESH_ONE, backType));
                    }
                    break;
                default:
            }
        }
    }

    private void shareWx(boolean isFriend) {
        String url = wxUrl + "goods/ordinary/" + goodsId + StaticData.WX_INVITE_CODE + inviteCode;
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
            currentPrice.setText(NumberFormatUnit.numberFormat(goodsDetailsInfo.getRetailPrice()));
            unit = goodsDetailsInfo.getUnit();
            originalPrice.setText(NumberFormatUnit.goodsFormat(goodsDetailsInfo.getCounterPrice()));
            sales.setVisibility(NumberFormatUnit.isZero(goodsDetailsInfo.getSalesQuantity())?View.GONE:View.VISIBLE);
            sales.setText(getString(R.string.cumulative_sales) + goodsDetailsInfo.getSalesQuantity()+getString(R.string.pieces));
            nameText = BriefUnit.returnName(goodsDetailsInfo.getRetailPrice(), goodsDetailsInfo.getName());
            briefText = BriefUnit.returnBrief(goodsDetailsInfo.getBrief());
            goodsName.setText(goodsDetailsInfo.getName());
            goodsBrief.setText(goodsDetailsInfo.getBrief());
            goodsBrief.setVisibility(TextUtils.isEmpty(goodsDetailsInfo.getBrief()) ? View.GONE : View.VISIBLE);
            selectedGoods.setText(getString(R.string.select_spec));
            groupNumber.setText(goodsDetailsInfo.getGroupNum() + "人正在拼单，可直接参与");
            groupPurchases = goodsDetailsInfo.getGroupPurchases();
            oldGroupRulesId = goodsDetailsInfo.getRulesId();
            if (TextUtils.equals(StaticData.REFRESH_ONE, goodsDetailsInfo.getCourierType())) {
                courierType.setText(getString(R.string.same_city_delivery));
            } else {
                courierType.setText(getString(R.string.type_express_delivery));
            }
            initGroup();
            picUrl = goodsDetailsInfo.getPicUrl();
            goodsSpecs = goodsDetailsInfo.getGoodsSpecs();
            productListCallableInfos = goodsDetailsInfo.getProductListCallableInfos();
            if (productListCallableInfos != null && productListCallableInfos.size() > 0) {
                individualShoppingPrice.setText(NumberFormatUnit.goodsFormat(productListCallableInfos.get(0).getPrice()));
                initiateBillPrice.setText(NumberFormatUnit.goodsFormat(productListCallableInfos.get(0).getPreferentialPrice()));
                if (productListCallableInfos.size() == 1) {
                    ProductListCallableInfo productListCallableInfo = productListCallableInfos.get(0);
                    String specifications = productListCallableInfo.getSpecifications();
                    if (!TextUtils.isEmpty(specifications)) {
                        checkSkus = Arrays.asList(specifications.split(","));
                    }
                }
            }
            if (!TextUtils.isEmpty(goodsDetailsInfo.getDetail())) {
                webView.loadDataWithBaseURL(null, HtmlUnit.getHtmlData(goodsDetailsInfo.getDetail()), "text/html", "utf-8", null);
            }
            goodsDetailIv.setVisibility(TextUtils.isEmpty(goodsDetailsInfo.getDetail()) ? View.GONE : View.VISIBLE);
            GlideHelper.load(this, goodsDetailsInfo.getPicUrl(), R.mipmap.icon_default_goods, shareIv);
        }
    }

    //可以取拼单的列表
    private void initGroup() {
        if (groupPurchases == null || groupPurchases.size() == 0) {
            groupLl.setVisibility(View.GONE);
        } else {
            if (groupPurchases.size() == 1) {
                groupLl.setVisibility(View.VISIBLE);
                upGroup.setVisibility(View.VISIBLE);
                downGroup.setVisibility(View.GONE);
                upPhoneNumber.setText(groupPurchases.get(0).getMobile());
                upPoorTv.setText("还差" + groupPurchases.get(0).getSurplus() + "人拼成");
                upGroupId = groupPurchases.get(0).getGrouponId();
                upGroupRulesId = groupPurchases.get(0).getRulesId();
                upMobile = groupPurchases.get(0).getMobile();
                upSurplus = groupPurchases.get(0).getSurplus();
                groupFlipper.setVisibility(View.GONE);
            } else if (groupPurchases.size() == 2) {
                groupLl.setVisibility(View.VISIBLE);
                upGroup.setVisibility(View.VISIBLE);
                downGroup.setVisibility(View.VISIBLE);
                upPhoneNumber.setText(groupPurchases.get(0).getMobile());
                upPoorTv.setText("还差" + groupPurchases.get(0).getSurplus() + "人拼成");
                downPhoneNumber.setText(groupPurchases.get(1).getMobile());
                downPoorTv.setText("还差" + groupPurchases.get(1).getSurplus() + "人拼成");
                upGroupId = groupPurchases.get(0).getGrouponId();
                upGroupRulesId = groupPurchases.get(0).getRulesId();
                downGroupId = groupPurchases.get(1).getGrouponId();
                downGroupRulesId = groupPurchases.get(1).getRulesId();
                upMobile = groupPurchases.get(0).getMobile();
                upSurplus = groupPurchases.get(0).getSurplus();
                downMobile = groupPurchases.get(1).getMobile();
                downSurplus = groupPurchases.get(1).getSurplus();
                groupFlipper.setVisibility(View.GONE);
            } else {
                upGroup.setVisibility(View.GONE);
                downGroup.setVisibility(View.GONE);
                groupFlipper.setVisibility(View.VISIBLE);
                allGroupPurchases.clear();
                allGroupPurchases = groupPurchases;
                if (groupPurchases.size() % 2 == 1) {
                    allGroupPurchases.addAll(groupPurchases);
                }
                for (int i = 0; i < allGroupPurchases.size() / 2; i++) {
                    View view1 = View.inflate(this, R.layout.item_group_purchase, null);
                    ConventionalTextView listUpPhoneNumber = view1.findViewById(R.id.list_up_phone_number);
                    ConventionalTextView listUpSpellBt = view1.findViewById(R.id.list_up_spell_bt);
                    ConventionalTextView listUpPoorTv = view1.findViewById(R.id.list_up_poor_tv);
                    ConventionalTextView listDownPhoneNumber = view1.findViewById(R.id.list_down_phone_number);
                    ConventionalTextView listDownSpellBt = view1.findViewById(R.id.list_down_spell_bt);
                    ConventionalTextView listDownPoorTv = view1.findViewById(R.id.list_down_poor_tv);
                    listUpPhoneNumber.setText(allGroupPurchases.get(2 * i).getMobile());
                    listUpPoorTv.setText("还差" + allGroupPurchases.get(2 * i).getSurplus() + "人拼成");
                    listUpSpellBt.setTag(2 * i);
                    listDownPhoneNumber.setText(allGroupPurchases.get(2 * i + 1).getMobile());
                    listDownPoorTv.setText("还差" + allGroupPurchases.get(2 * i + 1).getSurplus() + "人拼成");
                    listDownSpellBt.setTag(2 * i + 1);
                    groupFlipper.addView(view1);
                    listUpSpellBt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("Guanggao", "点击了" + v.getTag());
                            showReminder((Integer) v.getTag());
                        }
                    });
                    listDownSpellBt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("Guanggao", "点击了" + v.getTag());
                            showReminder((Integer) v.getTag());
                        }
                    });
                }
                groupFlipper.setFlipInterval(5000);
                groupFlipper.startFlipping();

            }
        }
    }

    private void showReminder(int position) {
        groupId = allGroupPurchases.get(position).getGrouponId();
        groupRulesId = allGroupPurchases.get(position).getRulesId();
        purchaseType = StaticData.REFRESH_THREE;
        isGroup = true;
        goSpellingReminder(allGroupPurchases.get(position).getMobile(), allGroupPurchases.get(position).getSurplus());
        TCAgentUnit.setEventId(this, getString(R.string.buy_together));
    }

    @Override
    public void renderConsumerPhone(String consumerPhone) {
        this.consumerPhone = consumerPhone;
    }

    @Override
    public void renderCartFastAdd(ConfirmOrderDetail confirmOrderDetail) {
        ConfirmOrderActivity.start(this, confirmOrderDetail, purchaseType, wxUrl, inviteCode);
    }

    @Override
    public void renderGroupRemind() {

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

    //分享成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShareSuccess(String code) {
        showMessage(getString(R.string.share_success));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

    public void zoom(List<String> photos, int position) {
        selectList.clear();
        for (int i = 0; i < photos.size(); i++) {
            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(photos.get(i));
            selectList.add(localMedia);
        }
        PictureSelector.create(this).themeStyle(themeId).openExternalPreview(position, selectList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TCAgentUnit.pageStart(this, getString(R.string.goods_details));
    }

    @Override
    protected void onPause() {
        super.onPause();
        TCAgentUnit.pageEnd(this, getString(R.string.goods_details));
    }
}
