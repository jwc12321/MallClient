package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.HtmlUnit;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.common.widget.textview.WhiteDrawTextView;
import com.mall.sls.data.entity.ConfirmOrderDetail;
import com.mall.sls.data.entity.CustomViewsInfo;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.entity.GroupPurchase;
import com.mall.sls.data.entity.ProductListCallableInfo;
import com.mall.sls.homepage.DaggerHomepageComponent;
import com.mall.sls.homepage.HomepageContract;
import com.mall.sls.homepage.HomepageModule;
import com.mall.sls.homepage.presenter.GoodsDetailsPresenter;
import com.mall.sls.mine.ui.CustomerServiceActivity;
import com.mall.sls.webview.unit.JSBridgeWebChromeClient;
import com.stx.xhb.androidx.XBanner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/9.
 * 描述：普通商品详情
 */
public class OrdinaryGoodsDetailActivity extends BaseActivity implements HomepageContract.GoodsDetailsView {


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
    @BindView(R.id.goods_name)
    MediumThickTextView goodsName;
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
    @BindView(R.id.initiate_bill_price)
    MediumThickTextView initiateBillPrice;
    @BindView(R.id.initiate_bill_bt)
    LinearLayout initiateBillBt;
    @BindView(R.id.individual_shopping_price)
    MediumThickTextView individualShoppingPrice;
    @BindView(R.id.individual_shopping_tv)
    LinearLayout individualShoppingTv;
    @BindView(R.id.goods_brief)
    ConventionalTextView goodsBrief;
    @BindView(R.id.group_ll)
    LinearLayout groupLl;
    private ProductListCallableInfo productListCallableInfo;
    private List<CustomViewsInfo> data;
    private String goodsId;
    private List<GroupPurchase> groupPurchases;
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
    @Inject
    GoodsDetailsPresenter goodsDetailsPresenter;
    private String consumerPhone;
    private String purchaseType;
    private String oldGroupRulesId;

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
        goodsId = getIntent().getStringExtra(StaticData.GOODS_ID);
        xBannerInit();
        initWebView();
        goodsDetailsPresenter.getGoodsDetails(goodsId);
        goodsDetailsPresenter.getConsumerPhone();

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

    @OnClick({R.id.back, R.id.individual_shopping_tv, R.id.initiate_bill_bt, R.id.service_iv, R.id.sku_rl, R.id.up_spell_bt, R.id.down_spell_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.initiate_bill_bt://发起拼单
                groupId = "";
                groupRulesId = oldGroupRulesId;
                purchaseType = StaticData.REFLASH_TWO;
                initiateBill();
                break;
            case R.id.service_iv:
                CustomerServiceActivity.start(this, consumerPhone);
                break;
            case R.id.sku_rl:
                goSelectSpecReturn(StaticData.REFLASH_ZERO);
                break;
            case R.id.individual_shopping_tv://单独购买
                groupId = "";
                groupRulesId = oldGroupRulesId;
                purchaseType = StaticData.REFLASH_ONE;
                individualShopping();
                break;
            case R.id.up_spell_bt:
                groupId = upGroupId;
                groupRulesId = upGroupRulesId;
                purchaseType = StaticData.REFLASH_THREE;
                goSpellingReminder(upMobile, upSurplus);
                break;
            case R.id.down_spell_bt:
                groupId = downGroupId;
                groupRulesId = downGroupRulesId;
                purchaseType = StaticData.REFLASH_THREE;
                goSpellingReminder(downMobile, downSurplus);
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
        if (productListCallableInfo == null) {
            goSelectSpec(StaticData.REFLASH_ONE);
        } else {
            goodsDetailsPresenter.cartFastAdd(goodsId, productListCallableInfo.getId(), true, String.valueOf(goodsCount), groupId, groupRulesId);
        }
    }

    private void individualShopping() {
        if (productListCallableInfo == null) {
            goSelectSpec(StaticData.REFLASH_ZERO);
        } else {
            goodsDetailsPresenter.cartFastAdd(goodsId, productListCallableInfo.getId(), false, String.valueOf(goodsCount), groupId, groupRulesId);
        }
    }

    private void goSelectSpecReturn(String type) {
        Intent intent = new Intent(this, SelectSpecActivity.class);
        intent.putExtra(StaticData.GOODS_DETAILS_INFO, goodsDetailsInfo);
        intent.putExtra(StaticData.SKU_CHECK, (Serializable) checkSkus);
        intent.putExtra(StaticData.CHOICE_TYPE, type);
        intent.putExtra(StaticData.GOODS_COUNT, goodsCount);
        startActivityForResult(intent, RequestCodeStatic.REQUEST_SPEC_RETURN);
    }

    private void goSelectSpec(String type) {
        Intent intent = new Intent(this, SelectSpecActivity.class);
        intent.putExtra(StaticData.GOODS_DETAILS_INFO, goodsDetailsInfo);
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
                        individualShoppingPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(productListCallableInfo.getPrice()));
                        initiateBillPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(productListCallableInfo.getPreferentialPrice()));
                    }
                    break;
                case RequestCodeStatic.REQUEST_SPEC:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        checkSkus = (List<String>) bundle.getSerializable(StaticData.SKU_CHECK);
                        productListCallableInfo = (ProductListCallableInfo) bundle.getSerializable(StaticData.SKU_INFO);
                        goodsCount = bundle.getInt(StaticData.GOODS_COUNT);
                        selectedGoods.setText(getString(R.string.is_selected) + " " + productListCallableInfo.getSpecifications() + "/" + unit);
                        individualShoppingPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(productListCallableInfo.getPrice()));
                        initiateBillPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(productListCallableInfo.getPreferentialPrice()));
                        goodsDetailsPresenter.cartFastAdd(goodsId, productListCallableInfo.getId(), true, String.valueOf(goodsCount), groupId, groupRulesId);
                    }
                    break;
                case RequestCodeStatic.SPELLING_REMINDER:
                    initiateBill();
                    break;
                default:
            }
        }
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
            banner.setBannerData(R.layout.xbanner_item, data);
            currentPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(goodsDetailsInfo.getRetailPrice()));
            unit = goodsDetailsInfo.getUnit();
            goodsUnit.setText("/" + unit);
            goodsOriginalUnit.setText("/" + unit);
            originalPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(goodsDetailsInfo.getCounterPrice()));
            sales.setText("累计销量" + goodsDetailsInfo.getSalesQuantity() + "件");
            goodsName.setText(goodsDetailsInfo.getName());
            goodsBrief.setText(goodsDetailsInfo.getBrief());
            goodsBrief.setVisibility(TextUtils.isEmpty(goodsDetailsInfo.getBrief())?View.GONE:View.VISIBLE);
            selectedGoods.setText(getString(R.string.is_selected));
            groupNumber.setText(goodsDetailsInfo.getGroupNum() + "人正在拼单，可直接参与");
            groupPurchases = goodsDetailsInfo.getGroupPurchases();
            oldGroupRulesId = goodsDetailsInfo.getRulesId();
            if (groupPurchases == null || groupPurchases.size() == 0) {
                groupLl.setVisibility(View.GONE);
                upGroup.setVisibility(View.GONE);
                downGroup.setVisibility(View.GONE);
            } else if (groupPurchases != null && groupPurchases.size() == 1) {
                groupLl.setVisibility(View.VISIBLE);
                upGroup.setVisibility(View.VISIBLE);
                downGroup.setVisibility(View.GONE);
                upPhoneNumber.setText(groupPurchases.get(0).getMobile());
                upPoorTv.setText("还差" + groupPurchases.get(0).getSurplus() + "人拼成");
                upGroupId = groupPurchases.get(0).getGrouponId();
                upGroupRulesId = groupPurchases.get(0).getRulesId();
                upMobile = groupPurchases.get(0).getMobile();
                upSurplus = groupPurchases.get(0).getSurplus();
            } else if (groupPurchases != null && groupPurchases.size() == 2) {
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
            }
            individualShoppingPrice.setText("¥" + goodsDetailsInfo.getCounterPrice());
            initiateBillPrice.setText("¥" + goodsDetailsInfo.getRetailPrice());
            if (!TextUtils.isEmpty(goodsDetailsInfo.getDetail())) {
                webView.loadDataWithBaseURL(null, HtmlUnit.getHtmlData(goodsDetailsInfo.getDetail()), "text/html", "utf-8", null);
            }
        }
    }

    @Override
    public void renderConsumerPhone(String consumerPhone) {
        this.consumerPhone = consumerPhone;
    }

    @Override
    public void renderCartFastAdd(ConfirmOrderDetail confirmOrderDetail) {
        ConfirmOrderActivity.start(this, confirmOrderDetail, purchaseType);
        finish();
    }

    @Override
    public void renderGroupRemind() {

    }

    @Override
    public void setPresenter(HomepageContract.GoodsDetailsPresenter presenter) {

    }
}
