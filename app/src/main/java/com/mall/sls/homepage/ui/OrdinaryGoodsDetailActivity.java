package com.mall.sls.homepage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.CustomViewsInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.stx.xhb.androidx.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/9.
 * 描述：普通商品详情
 */
public class OrdinaryGoodsDetailActivity extends BaseActivity {
    @BindView(R.id.banner)
    XBanner banner;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.current_price)
    MediumThickTextView currentPrice;
    @BindView(R.id.unit)
    ConventionalTextView unit;
    @BindView(R.id.original_price)
    ConventionalTextView originalPrice;
    @BindView(R.id.sales)
    ConventionalTextView sales;
    @BindView(R.id.goods_name)
    MediumThickTextView goodsName;
    @BindView(R.id.selected_goods)
    ConventionalTextView selectedGoods;
    @BindView(R.id.right_arrow_iv)
    ImageView rightArrowIv;
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
    @BindView(R.id.down_phone_number)
    ConventionalTextView downPhoneNumber;
    @BindView(R.id.down_spell_bt)
    ConventionalTextView downSpellBt;
    @BindView(R.id.down_poor_tv)
    ConventionalTextView downPoorTv;
    @BindView(R.id.scrollview)
    NestedScrollView scrollview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.service_iv)
    ImageView serviceIv;
    @BindView(R.id.home_iv)
    ImageView homeIv;
    @BindView(R.id.initiate_bill_bt)
    LinearLayout initiateBillBt;
    @BindView(R.id.individual_shopping_tv)
    LinearLayout individualShoppingTv;

    private List<CustomViewsInfo> data;

    public static void start(Context context) {
        Intent intent = new Intent(context, OrdinaryGoodsDetailActivity.class);
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
        xBannerInit();
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
        banner.setBannerData(R.layout.xbanner_zero_item, data);
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

    @OnClick({R.id.back, R.id.individual_shopping_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.individual_shopping_tv://单独购买
                SelectSpecActivity.start(this);
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
