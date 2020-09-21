package com.mall.sls.sort.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.TCAgentUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.GoodsItem;
import com.mall.sls.data.entity.PrizeVo;
import com.mall.sls.homepage.adapter.GoodsItemAdapter;
import com.mall.sls.homepage.ui.ActivityGroupGoodsActivity;
import com.mall.sls.homepage.ui.GeneralGoodsDetailsActivity;
import com.mall.sls.homepage.ui.OrdinaryGoodsDetailActivity;
import com.mall.sls.lottery.ui.LotteryDetailActivity;
import com.mall.sls.sort.DaggerSortComponent;
import com.mall.sls.sort.SortContract;
import com.mall.sls.sort.SortModule;
import com.mall.sls.sort.presenter.CategoryGoodsPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/9/21.
 * 描述：
 */
public class CategoryGoodsActivity extends BaseActivity implements SortContract.CategoryGoodsView, GoodsItemAdapter.OnItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.default_tv)
    ConventionalTextView defaultTv;
    @BindView(R.id.default_iv)
    ImageView defaultIv;
    @BindView(R.id.default_rl)
    RelativeLayout defaultRl;
    @BindView(R.id.price_tv)
    ConventionalTextView priceTv;
    @BindView(R.id.price_iv)
    ImageView priceIv;
    @BindView(R.id.price_rl)
    RelativeLayout priceRl;
    @BindView(R.id.sales_tv)
    ConventionalTextView salesTv;
    @BindView(R.id.sales_iv)
    ImageView salesIv;
    @BindView(R.id.sales_rl)
    RelativeLayout salesRl;
    @BindView(R.id.price_arrow_iv)
    ImageView priceArrowIv;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.no_record_ll)
    LinearLayout noRecordLl;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private String categoryId;
    private String categoryName;
    private String sortType;
    private String orderType;
    private String selectType;//1:默认 2：价格降序 3：价格升序 4：销量
    private Boolean priceDesc=false;

    private GoodsItemAdapter goodsItemAdapter;
    @Inject
    CategoryGoodsPresenter categoryGoodsPresenter;


    public static void start(Context context, String categoryName,String categoryId) {
        Intent intent = new Intent(context, CategoryGoodsActivity.class);
        intent.putExtra(StaticData.CATEGORY_NAME,categoryName);
        intent.putExtra(StaticData.CATEGORY_ID, categoryId);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_goods);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        categoryName=getIntent().getStringExtra(StaticData.CATEGORY_NAME);
        categoryId = getIntent().getStringExtra(StaticData.CATEGORY_ID);
        title.setText(categoryName);
        refreshLayout.setOnMultiPurposeListener(simpleMultiPurposeListener);
        goodsItemAdapter = new GoodsItemAdapter(this);
        goodsItemAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(goodsItemAdapter);
        categoryGoodsPresenter.getCategoryGoods(StaticData.REFLASH_ONE, categoryId, sortType, orderType);
    }

    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
            categoryGoodsPresenter.getCategoryGoods(StaticData.REFLASH_ZERO, categoryId, sortType, orderType);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            categoryGoodsPresenter.getMoreCategoryGoods(categoryId, sortType, orderType);
        }
    };

    @Override
    protected void initializeInjector() {
        DaggerSortComponent.builder()
                .applicationComponent(getApplicationComponent())
                .sortModule(new SortModule(this))
                .build()
                .inject(this);
    }

    private void selectType(String type) {
        defaultIv.setVisibility(TextUtils.equals(StaticData.REFLASH_ONE, type) ? View.VISIBLE : View.GONE);
        priceIv.setVisibility((TextUtils.equals(StaticData.REFLASH_TWO, type) || TextUtils.equals(StaticData.REFLASH_THREE, type)) ? View.VISIBLE : View.GONE);
        salesIv.setVisibility(TextUtils.equals(StaticData.REFLASH_FOUR, type) ? View.VISIBLE : View.GONE);
        if (TextUtils.equals(StaticData.REFLASH_ONE, type)) {
            sortType="";
            orderType="";
            priceArrowIv.setBackgroundResource(R.mipmap.icon_up_down_arrow);
        } else if (TextUtils.equals(StaticData.REFLASH_TWO, type)) {
            sortType=StaticData.SORT_TYPE_PRICE;
            orderType=StaticData.ORDER_TYPE_DESC;
            priceArrowIv.setBackgroundResource(R.mipmap.icon_down_arrow);
        } else if (TextUtils.equals(StaticData.REFLASH_THREE, type)) {
            sortType=StaticData.SORT_TYPE_PRICE;
            orderType=StaticData.ORDER_TYPE_ASC;
            priceArrowIv.setBackgroundResource(R.mipmap.icon_up_arrow);
        } else if (TextUtils.equals(StaticData.REFLASH_FOUR, type)) {
            sortType=StaticData.SORT_TYPE_SALES;
            orderType=StaticData.ORDER_TYPE_DESC;
            priceArrowIv.setBackgroundResource(R.mipmap.icon_up_down_arrow);
        }
        categoryGoodsPresenter.getCategoryGoods(StaticData.REFLASH_ONE,categoryId,sortType,orderType);

    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void goOrdinaryGoodsDetails(String goodsId) {
        OrdinaryGoodsDetailActivity.start(this, goodsId);
    }

    @Override
    public void goActivityGroupGoods(String goodsId) {
        ActivityGroupGoodsActivity.start(this, goodsId);
    }

    @Override
    public void goGeneralGoodsDetails(String goodsId) {
        GeneralGoodsDetailsActivity.start(this, goodsId);
    }

    @Override
    public void renderCategoryGoods(GoodsItem goodsItem) {
        refreshLayout.finishRefresh();
        if (goodsItem != null) {
            if (goodsItem.getGoodsItemInfos() != null && goodsItem.getGoodsItemInfos().size() > 0) {
                recordRv.setVisibility(View.VISIBLE);
                noRecordLl.setVisibility(View.GONE);
                if (goodsItem.getGoodsItemInfos().size() == Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                    refreshLayout.resetNoMoreData();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                goodsItemAdapter.setData(goodsItem.getGoodsItemInfos());
            } else {
                recordRv.setVisibility(View.GONE);
                noRecordLl.setVisibility(View.VISIBLE);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void renderMoreCategoryGoods(GoodsItem goodsItem) {
        refreshLayout.finishLoadMore();
        if (goodsItem != null && goodsItem.getGoodsItemInfos() != null) {
            if (goodsItem.getGoodsItemInfos().size() != Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            goodsItemAdapter.addMore(goodsItem.getGoodsItemInfos());
        }
    }

    @Override
    public void setPresenter(SortContract.CategoryGoodsPresenter presenter) {

    }

    @OnClick({R.id.back, R.id.default_rl,R.id.price_rl,R.id.sales_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.default_rl:
                priceDesc=false;
                selectType=StaticData.REFLASH_ONE;
                selectType(selectType);
                break;
            case R.id.price_rl:
                priceDesc=!priceDesc;
                if(priceDesc){
                    selectType=StaticData.REFLASH_TWO;
                }else {
                    selectType=StaticData.REFLASH_THREE;
                }
                selectType(selectType);
                break;
            case R.id.sales_rl:
                priceDesc=false;
                selectType=StaticData.REFLASH_FOUR;
                selectType(selectType);
                break;
            default:
        }
    }
}
