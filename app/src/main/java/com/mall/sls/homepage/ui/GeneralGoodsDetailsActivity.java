package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.luck.picture.lib.entity.LocalMedia;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.HtmlUnit;
import com.mall.sls.common.unit.MainStartManager;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.unit.PictureSelectorUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.common.widget.textview.WhiteDrawTextView;
import com.mall.sls.data.entity.ConfirmCartOrderDetail;
import com.mall.sls.data.entity.CustomViewsInfo;
import com.mall.sls.data.entity.GeneralGoodsDetailsInfo;
import com.mall.sls.data.entity.GoodsBaseVo;
import com.mall.sls.data.entity.GoodsSpec;
import com.mall.sls.data.entity.ProductListCallableInfo;
import com.mall.sls.homepage.DaggerHomepageComponent;
import com.mall.sls.homepage.HomepageContract;
import com.mall.sls.homepage.HomepageModule;
import com.mall.sls.homepage.presenter.GeneralGoodsDetailsPresenter;
import com.mall.sls.mainframe.ui.MainFrameActivity;
import com.mall.sls.webview.unit.JSBridgeWebChromeClient;
import com.stx.xhb.androidx.XBanner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/6/23.
 * 描述：普通商品详情
 */
public class GeneralGoodsDetailsActivity extends BaseActivity implements HomepageContract.GeneralGoodsDetailsView, NestedScrollView.OnScrollChangeListener {

    @BindView(R.id.banner)
    XBanner banner;
    @BindView(R.id.current_price)
    MediumThickTextView currentPrice;
    @BindView(R.id.sales)
    ConventionalTextView sales;
    @BindView(R.id.goods_name)
    MediumThickTextView goodsName;
    @BindView(R.id.goods_brief)
    ConventionalTextView goodsBrief;
    @BindView(R.id.goods_detail_iv)
    ImageView goodsDetailIv;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.scrollview)
    NestedScrollView scrollview;
    @BindView(R.id.home_iv)
    ImageView homeIv;
    @BindView(R.id.buy_now_bt)
    ConventionalTextView buyNowBt;
    @BindView(R.id.add_cart_bt)
    ConventionalTextView addCartBt;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.cart)
    ImageView cart;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.original_price)
    WhiteDrawTextView originalPrice;
    @BindView(R.id.delivery_method_ll)
    LinearLayout deliveryMethodLl;
    private String goodsId;
    private int screenWidth;
    private int screenHeight;

    private GoodsBaseVo goodsBaseVo;
    private List<String> banners;
    private List<CustomViewsInfo> data;

    private List<ProductListCallableInfo> productListCallableInfos;
    private List<GoodsSpec> goodsSpecs;
    private String picUrl;
    private ProductListCallableInfo productListCallableInfo;
    private List<String> checkSkus;
    private int goodsCount = 1;
    private String type;
    private List<String> ids;
    private List<LocalMedia> medias;
    private LocalMedia localMedia;


    @Inject
    GeneralGoodsDetailsPresenter generalGoodsDetailsPresenter;

    public static void start(Context context, String goodsId) {
        Intent intent = new Intent(context, GeneralGoodsDetailsActivity.class);
        intent.putExtra(StaticData.GOODS_ID, goodsId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_goods_details);
        ButterKnife.bind(this);
        setHeight(back, null, cart);
        initView();
        generalGoodsDetailsPresenter.getGeneralGoodsDetailsInfo(goodsId);
    }

    private void initView() {
        goodsId = getIntent().getStringExtra(StaticData.GOODS_ID);
        scrollview.setOnScrollChangeListener(this);
        ids = new ArrayList<>();
        data = new ArrayList<>();
        medias = new ArrayList<>();
        settingHeight();
        xBannerInit();
        initWebView();
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
        webView.setBackgroundColor(getResources().getColor(R.color.backGround83));
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
                PictureSelectorUnit.loadImage(GeneralGoodsDetailsActivity.this, position,medias );
            }
        });
        //加载广告图片
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //在此处使用图片加载框架加载图片，demo中使用glide加载，可替换成自己项目中的图片加载框架
                RoundedImageView roundedImageView = (RoundedImageView) view;
                CustomViewsInfo customViewsInfo = ((CustomViewsInfo) model);
                Glide.with(GeneralGoodsDetailsActivity.this).load(customViewsInfo.getXBannerUrl()).error(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL).into(roundedImageView);
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

    @OnClick({R.id.back, R.id.home_iv, R.id.add_cart_bt, R.id.buy_now_bt, R.id.cart,R.id.delivery_method_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.home_iv:
                MainStartManager.saveMainStart(StaticData.REFRESH_ZERO);
                MainFrameActivity.start(this);
                finish();
                break;
            case R.id.add_cart_bt://加入购物车
                type = StaticData.REFRESH_ZERO;
                goSelectSpec();
                break;
            case R.id.buy_now_bt://立即购买
                type = StaticData.REFRESH_ONE;
                goSelectSpec();
                break;
            case R.id.cart:
                MainStartManager.saveMainStart(StaticData.REFRESH_TWO);
                MainFrameActivity.start(this);
                finish();
                break;
            case R.id.delivery_method_ll:
                DeliveryNoteActivity.start(this);
                break;
            default:
        }
    }


    private void goSelectSpec() {
        Intent intent = new Intent(this, SelectSpecActivity.class);
        intent.putExtra(StaticData.PRODUCT_LIST, (Serializable) goodsSpecs);
        intent.putExtra(StaticData.SPECIFICATION_LIST, (Serializable) productListCallableInfos);
        intent.putExtra(StaticData.PIC_URL, picUrl);
        intent.putExtra(StaticData.SKU_CHECK, (Serializable) checkSkus);
        intent.putExtra(StaticData.CHOICE_TYPE, StaticData.REFRESH_ONE);
        intent.putExtra(StaticData.GOODS_COUNT, goodsCount);
        startActivityForResult(intent, RequestCodeStatic.REQUEST_SPEC);
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderCartCount(String number) {

    }

    @Override
    public void renderCartAdd() {
        showMessage(getString(R.string.add_cart_success));
    }

    @Override
    public void renderGeneralGoodsDetailsInfo(GeneralGoodsDetailsInfo generalGoodsDetailsInfo) {
        if (generalGoodsDetailsInfo != null) {
            goodsBaseVo = generalGoodsDetailsInfo.getGoodsBaseVo();
            if (goodsBaseVo != null) {
                banners = goodsBaseVo.getGallerys();
                data.clear();
                medias.clear();
                if (banners != null) {
                    for (String bannerInfo : banners) {
                        data.add(new CustomViewsInfo(bannerInfo));
                        localMedia=new LocalMedia();
                        localMedia.setPath(bannerInfo);
                        medias.add(localMedia);
                    }
                }
                banner.setPointsIsVisible(data.size() > 1);
                banner.setBannerData(R.layout.xbanner_zero_item, data);
                currentPrice.setText(NumberFormatUnit.numberFormat(goodsBaseVo.getRetailPrice()));
                originalPrice.setText(NumberFormatUnit.goodsFormat(goodsBaseVo.getCounterPrice()));
                sales.setVisibility(NumberFormatUnit.isZero(goodsBaseVo.getSalesQuantity()) ? View.GONE : View.VISIBLE);
                sales.setText(getString(R.string.cumulative_sales) + goodsBaseVo.getSalesQuantity() + getString(R.string.pieces));
                goodsName.setText(goodsBaseVo.getName());
                goodsBrief.setText(goodsBaseVo.getBrief());
                if (!TextUtils.isEmpty(goodsBaseVo.getDetail())) {
                    webView.loadDataWithBaseURL(null, HtmlUnit.getHtmlData(goodsBaseVo.getDetail()), "text/html", "utf-8", null);
                }
                goodsDetailIv.setVisibility(TextUtils.isEmpty(goodsBaseVo.getDetail()) ? View.GONE : View.VISIBLE);
                picUrl = goodsBaseVo.getPicUrl();
            }
            goodsSpecs = generalGoodsDetailsInfo.getGoodsSpecs();
            productListCallableInfos = generalGoodsDetailsInfo.getProductListCallableInfos();
            if (productListCallableInfos != null && productListCallableInfos.size() > 0) {
                if (productListCallableInfos.size() == 1) {
                    ProductListCallableInfo productListCallableInfo = productListCallableInfos.get(0);
                    String specifications = productListCallableInfo.getSpecifications();
                    if (!TextUtils.isEmpty(specifications)) {
                        checkSkus = Arrays.asList(specifications.split(","));
                    }
                }
            }
        }
    }

    @Override
    public void renderBuyNow(ConfirmCartOrderDetail confirmCartOrderDetail) {
        ids.clear();
        if (confirmCartOrderDetail != null && confirmCartOrderDetail.getCartItemInfos() != null && confirmCartOrderDetail.getCartItemInfos().size() > 0) {
            ids.add(confirmCartOrderDetail.getCartItemInfos().get(0).getId());
            CartConfirmOrderActivity.start(this, ids, StaticData.REFRESH_ONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.REQUEST_SPEC://单独购买和发起拼单取选择规格
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        checkSkus = (List<String>) bundle.getSerializable(StaticData.SKU_CHECK);
                        productListCallableInfo = (ProductListCallableInfo) bundle.getSerializable(StaticData.SKU_INFO);
                        goodsCount = bundle.getInt(StaticData.GOODS_COUNT);
                        if (TextUtils.equals(StaticData.REFRESH_ZERO, type)) {
                            generalGoodsDetailsPresenter.cartAdd(productListCallableInfo.getId(), String.valueOf(goodsCount));
                        } else {
                            generalGoodsDetailsPresenter.buyNow(productListCallableInfo.getId(), String.valueOf(goodsCount));
                        }
                    }
                    break;
                default:
            }
        }
    }


    @Override
    public void setPresenter(HomepageContract.GeneralGoodsDetailsPresenter presenter) {

    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (isNightMode(this)) {
            if (scrollY <= 0) {   //设置标题的背景颜色
                titleRel.setBackgroundColor(Color.argb((int) 0, 144, 151, 166));
            } else {    //滑动到banner下面设置普通颜色
                titleRel.setBackgroundColor(getResources().getColor(R.color.backGround60));
            }
        } else {
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
    }
}
