package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.view.accessibility.AccessibilityViewCommand;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.common.widget.textview.WhiteDrawTextView;
import com.mall.sls.data.entity.CustomViewsInfo;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.entity.GroupPurchase;
import com.mall.sls.data.entity.ProductListCallableInfo;
import com.mall.sls.homepage.DaggerHomepageComponent;
import com.mall.sls.homepage.HomepageContract;
import com.mall.sls.homepage.HomepageModule;
import com.mall.sls.homepage.presenter.GoodsDetailsPresenter;
import com.mall.sls.mine.ui.CustomerServiceActivity;
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
    private ProductListCallableInfo productListCallableInfo;
    private List<CustomViewsInfo> data;
    private String goodsId;
    private List<GroupPurchase> groupPurchases;
    private List<String> banners;
    private GoodsDetailsInfo goodsDetailsInfo;
    private List<String> checkSkus;
    private int goodsCount=1;
    private String unit;
    @Inject
    GoodsDetailsPresenter goodsDetailsPresenter;
    private String consumerPhone;

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
        goodsDetailsPresenter.getGoodsDetails(goodsId);
        goodsDetailsPresenter.getConsumerPhone();

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

    @OnClick({R.id.back, R.id.individual_shopping_tv, R.id.initiate_bill_bt, R.id.service_iv, R.id.sku_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.initiate_bill_bt://发起拼单
                goSelectSpec(StaticData.REFLASH_ONE);
                break;
            case R.id.service_iv:
                CustomerServiceActivity.start(this,consumerPhone);
                break;
            case R.id.sku_rl:
            case R.id.individual_shopping_tv://单独购买
                goSelectSpec(StaticData.REFLASH_ZERO);
                break;
            default:
        }
    }

    private void goSelectSpec(String type){
        Intent intent = new Intent(this, SelectSpecActivity.class);
        intent.putExtra(StaticData.GOODS_DETAILS_INFO, goodsDetailsInfo);
        intent.putExtra(StaticData.SKU_CHECK, (Serializable) checkSkus);
        intent.putExtra(StaticData.CHOICE_TYPE,type);
        intent.putExtra(StaticData.GOODS_COUNT,goodsCount);
        startActivityForResult(intent, RequestCodeStatic.REQUEST_SPEC);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.REQUEST_SPEC:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        checkSkus= (List<String>) bundle.getSerializable(StaticData.SKU_CHECK);
                        productListCallableInfo= (ProductListCallableInfo) bundle.getSerializable(StaticData.SKU_INFO);
                        goodsCount=bundle.getInt(StaticData.GOODS_COUNT);
                        selectedGoods.setText(getString(R.string.is_selected)+" "+productListCallableInfo.getSpecifications()+"/"+unit);
                        individualShoppingPrice.setText("¥" + productListCallableInfo.getPrice());
                        initiateBillPrice.setText("¥" + productListCallableInfo.getPreferentialPrice());
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
            currentPrice.setText("¥" + goodsDetailsInfo.getRetailPrice());
            unit=goodsDetailsInfo.getUnit();
            goodsUnit.setText("/" + unit);
            originalPrice.setText("¥" + goodsDetailsInfo.getCounterPrice());
            sales.setText("累计销量" + goodsDetailsInfo.getSalesQuantity() + "件");
            goodsName.setText(goodsDetailsInfo.getName());
            selectedGoods.setText(getString(R.string.is_selected));
            groupNumber.setText(goodsDetailsInfo.getGroupNum() + "人正在拼单，可直接参与");
            groupPurchases = goodsDetailsInfo.getGroupPurchases();
            if (groupPurchases == null || groupPurchases.size() == 0) {
                upGroup.setVisibility(View.GONE);
                downGroup.setVisibility(View.GONE);
            } else if (groupPurchases != null && groupPurchases.size() == 1) {
                upGroup.setVisibility(View.VISIBLE);
                downGroup.setVisibility(View.GONE);
                upPhoneNumber.setText(groupPurchases.get(0).getMobile());
                upPoorTv.setText("还差" + groupPurchases.get(0).getSurplus() + "人拼成");
            } else if (groupPurchases != null && groupPurchases.size() == 2) {
                upGroup.setVisibility(View.VISIBLE);
                downGroup.setVisibility(View.VISIBLE);
                upPhoneNumber.setText(groupPurchases.get(0).getMobile());
                upPoorTv.setText("还差" + groupPurchases.get(0).getSurplus() + "人拼成");
                downPhoneNumber.setText(groupPurchases.get(1).getMobile());
                downPoorTv.setText("还差" + groupPurchases.get(1).getSurplus() + "人拼成");
            }
            individualShoppingPrice.setText("¥" + goodsDetailsInfo.getCounterPrice());
            initiateBillPrice.setText("¥" + goodsDetailsInfo.getRetailPrice());
        }
    }

    @Override
    public void renderConsumerPhone(String consumerPhone) {
        this.consumerPhone=consumerPhone;
    }

    @Override
    public void setPresenter(HomepageContract.GoodsDetailsPresenter presenter) {

    }
}
