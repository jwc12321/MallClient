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
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.GoodsItem;
import com.mall.sls.homepage.adapter.GoodsItemAdapter;
import com.mall.sls.homepage.ui.ActivityGroupGoodsActivity;
import com.mall.sls.homepage.ui.GeneralGoodsDetailsActivity;
import com.mall.sls.homepage.ui.OrdinaryGoodsDetailActivity;
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
    @BindView(R.id.price_arrow_iv)
    ImageView priceArrowIv;
    @BindView(R.id.price_ll)
    LinearLayout priceLl;
    @BindView(R.id.sales_tv)
    ConventionalTextView salesTv;
    @BindView(R.id.sales_iv)
    ImageView salesIv;
    @BindView(R.id.sales_rl)
    RelativeLayout salesRl;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.no_record_ll)
    LinearLayout noRecordLl;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.price_arrow)
    ImageView priceArrow;
    private String categoryId;
    private String categoryName;
    private String sortType="";
    private String orderType="";
    private String selectType=StaticData.REFRESH_ONE;//1:默认 2：价格降序 3：价格升序 4：销量
    private Boolean priceDesc = false;

    private GoodsItemAdapter goodsItemAdapter;
    @Inject
    CategoryGoodsPresenter categoryGoodsPresenter;


    public static void start(Context context, String categoryName, String categoryId) {
        Intent intent = new Intent(context, CategoryGoodsActivity.class);
        intent.putExtra(StaticData.CATEGORY_NAME, categoryName);
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
        categoryName = getIntent().getStringExtra(StaticData.CATEGORY_NAME);
        categoryId = getIntent().getStringExtra(StaticData.CATEGORY_ID);
        title.setText(categoryName);
        refreshLayout.setOnMultiPurposeListener(simpleMultiPurposeListener);
        goodsItemAdapter = new GoodsItemAdapter(this);
        goodsItemAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(goodsItemAdapter);
        categoryGoodsPresenter.getCategoryGoods(StaticData.REFRESH_ONE, categoryId, sortType, orderType);
    }

    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
            categoryGoodsPresenter.getCategoryGoods(StaticData.REFRESH_ZERO, categoryId, sortType, orderType);
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

    //setBackgroundResource暗黑模式没用
    private void selectType(String type) {
        defaultIv.setVisibility(TextUtils.equals(StaticData.REFRESH_ONE, type) ? View.VISIBLE : View.GONE);
        priceIv.setVisibility((TextUtils.equals(StaticData.REFRESH_TWO, type) || TextUtils.equals(StaticData.REFRESH_THREE, type)) ? View.VISIBLE : View.GONE);
        salesIv.setVisibility(TextUtils.equals(StaticData.REFRESH_FOUR, type) ? View.VISIBLE : View.GONE);
        if (TextUtils.equals(StaticData.REFRESH_ONE, type)) {
            sortType = "";
            orderType = "";
            priceArrowIv.setVisibility(View.VISIBLE);
            priceArrow.setVisibility(View.GONE);
        } else if (TextUtils.equals(StaticData.REFRESH_TWO, type)) {
            sortType = StaticData.SORT_TYPE_PRICE;
            orderType = StaticData.ORDER_TYPE_DESC;
            priceArrowIv.setVisibility(View.GONE);
            priceArrow.setVisibility(View.VISIBLE);
            priceArrow.setSelected(false);
        } else if (TextUtils.equals(StaticData.REFRESH_THREE, type)) {
            sortType = StaticData.SORT_TYPE_PRICE;
            orderType = StaticData.ORDER_TYPE_ASC;
            priceArrowIv.setVisibility(View.GONE);
            priceArrow.setVisibility(View.VISIBLE);
            priceArrow.setSelected(true);
        } else if (TextUtils.equals(StaticData.REFRESH_FOUR, type)) {
            sortType = StaticData.SORT_TYPE_SALES;
            orderType = StaticData.ORDER_TYPE_DESC;
            priceArrowIv.setVisibility(View.VISIBLE);
            priceArrow.setVisibility(View.GONE);
        }
        categoryGoodsPresenter.getCategoryGoods(StaticData.REFRESH_ONE, categoryId, sortType, orderType);

    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void goGoodsDetails(String goodsType, String goodsId) {
        if (TextUtils.equals(StaticData.REFRESH_ONE, goodsType)) {
            GeneralGoodsDetailsActivity.start(this, goodsId);
        } else if (TextUtils.equals(StaticData.REFRESH_TWO, goodsType)) {
            OrdinaryGoodsDetailActivity.start(this, goodsId);
        } else {
            ActivityGroupGoodsActivity.start(this, goodsId);
        }
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

    @OnClick({R.id.back, R.id.default_rl, R.id.price_ll, R.id.sales_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.default_rl:
                priceDesc = false;
                selectType = StaticData.REFRESH_ONE;
                selectType(selectType);
                break;
            case R.id.price_ll:
                priceDesc = !priceDesc;
                if (priceDesc) {
                    selectType = StaticData.REFRESH_TWO;
                } else {
                    selectType = StaticData.REFRESH_THREE;
                }
                selectType(selectType);
                break;
            case R.id.sales_rl:
                priceDesc = false;
                selectType = StaticData.REFRESH_FOUR;
                selectType(selectType);
                break;
            default:
        }
    }
}
