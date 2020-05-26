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
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.FormatUtil;
import com.mall.sls.common.unit.HtmlUnit;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.DetailTearDownView;
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
 * 描述：活动商品详情
 */
public class ActivityGoodsDetailActivity extends BaseActivity implements HomepageContract.GoodsDetailsView, DetailTearDownView.TimeOutListener {


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

    public static void start(Context context, String goodsId) {
        Intent intent = new Intent(context, ActivityGoodsDetailActivity.class);
        intent.putExtra(StaticData.GOODS_ID, goodsId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_goods_detail);
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

    @OnClick({R.id.back, R.id.confirm_bt, R.id.service_iv, R.id.sku_rl,R.id.home_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
            case R.id.home_iv:
                finish();
                break;
            case R.id.service_iv:
                CustomerServiceActivity.start(this, consumerPhone);
                break;
            case R.id.sku_rl:
                goSelectSpecReturn(StaticData.REFLASH_ONE);
                break;
            case R.id.confirm_bt://发起拼单
                initiateBill();
                break;
            default:
        }
    }

    private void initiateBill() {
        if (productListCallableInfo == null) {
            goSelectSpec(StaticData.REFLASH_ONE);
        } else {
            goodsDetailsPresenter.cartFastAdd(goodsId, productListCallableInfo.getId(), true, String.valueOf(goodsCount), groupId, groupRulesId);
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
            if (!TextUtils.isEmpty(goodsDetailsInfo.getNow()) && !TextUtils.isEmpty(goodsDetailsInfo.getGroupExpireTime()) && !TextUtils.isEmpty(goodsDetailsInfo.getStartTime())) {
                long now = FormatUtil.dateToStamp(goodsDetailsInfo.getNow());
                long groupExpireTime = FormatUtil.dateToStamp(goodsDetailsInfo.getGroupExpireTime());
                long startTime = FormatUtil.dateToStamp(goodsDetailsInfo.getStartTime());
                if (now < startTime) {
                    timeType.setText(getString(R.string.open_time));
                    countDown.startTearDown(startTime/1000, now/1000);
                    confirmBt.setEnabled(false);
                    confirmBt.setText(FormatUtil.formatMSDateTime(goodsDetailsInfo.getStartTime())+"开抢");
                    teamType = StaticData.REFLASH_ONE;
                } else if (now > startTime && now < groupExpireTime) {
                    countDown.startTearDown(groupExpireTime/1000, now/1000);
                    timeType.setText(getString(R.string.remaining_spike));
                    confirmBt.setEnabled(true);
                    teamType = StaticData.REFLASH_TWO;
                    confirmBt.setText(getString(R.string.go_buy));
                }
            }
            groupPurchases = goodsDetailsInfo.getGroupPurchases();
            if (groupPurchases != null && groupPurchases.size() == 1) {
                groupId = groupPurchases.get(0).getGrouponId();
                groupRulesId = groupPurchases.get(0).getRulesId();
            }
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
        ConfirmOrderActivity.start(this, confirmOrderDetail, StaticData.REFLASH_FOUR);
        finish();
    }

    @Override
    public void renderGroupRemind() {
        showMessage(getString(R.string.remind_to_you));
    }

    @Override
    public void setPresenter(HomepageContract.GoodsDetailsPresenter presenter) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDown.cancel();
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
}
