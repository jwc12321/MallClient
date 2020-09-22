package com.mall.sls.sort.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.edittextview.SoftKeyBoardListener;
import com.mall.sls.common.widget.flowlayout.FlowLayoutView;
import com.mall.sls.common.widget.flowlayout.TagAdapter;
import com.mall.sls.common.widget.flowlayout.TagFlowLayout;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.data.entity.GoodsItem;
import com.mall.sls.homepage.adapter.GoodsItemAdapter;
import com.mall.sls.homepage.ui.ActivityGroupGoodsActivity;
import com.mall.sls.homepage.ui.GeneralGoodsDetailsActivity;
import com.mall.sls.homepage.ui.OrdinaryGoodsDetailActivity;
import com.mall.sls.sort.DaggerSortComponent;
import com.mall.sls.sort.SortContract;
import com.mall.sls.sort.SortModule;
import com.mall.sls.sort.presenter.SearchGoodsPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/7/8.
 * 描述：
 */
public class SearchGoodsActivity extends BaseActivity implements SortContract.SearchGoodsView, GoodsItemAdapter.OnItemClickListener {


    @BindView(R.id.small_title)
    ConventionalTextView smallTitle;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.goods_et)
    ConventionalEditTextView goodsEt;
    @BindView(R.id.close_iv)
    ImageView closeIv;
    @BindView(R.id.search_rl)
    RelativeLayout searchRl;
    @BindView(R.id.clear_history)
    ConventionalTextView clearHistory;
    @BindView(R.id.history_tag)
    TagFlowLayout historyTag;
    @BindView(R.id.history_ll)
    LinearLayout historyLl;
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
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.goods_ll)
    LinearLayout goodsLl;
    @BindView(R.id.no_record_ll)
    LinearLayout noRecordLl;
    @BindView(R.id.price_arrow)
    ImageView priceArrow;
    private List<String> wordDatas;
    private TagAdapter<String> wordAdapter;//选择后写入的tag
    private int screenWidth;
    private GoodsItemAdapter goodsItemAdapter;
    private String keyword;
    private String sortType;
    private String orderType;
    private String selectType;//1:默认 2：价格降序 3：价格升序 4：销量
    private Boolean priceDesc = false;

    @Inject
    SearchGoodsPresenter searchGoodsPresenter;


    public static void start(Context context) {
        Intent intent = new Intent(context, SearchGoodsActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_goods);
        ButterKnife.bind(this);
        setHeight(null, smallTitle, null);
        initView();
    }

    private void initView() {
        refreshLayout.setOnMultiPurposeListener(simpleMultiPurposeListener);
        getWidth();
        addAdapter();
        setTagview();
        editText();
        searchGoodsPresenter.getSearchHistory(StaticData.REFLASH_ONE);
    }

    @Override
    protected void initializeInjector() {
        DaggerSortComponent.builder()
                .applicationComponent(getApplicationComponent())
                .sortModule(new SortModule(this))
                .build()
                .inject(this);
    }

    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
            searchGoodsPresenter.getKeywordGoods(StaticData.REFLASH_ZERO, keyword, sortType, orderType);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            searchGoodsPresenter.getMoreKeywordGoods(keyword, sortType, orderType);
        }
    };


    private void editText() {

        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                closeIv.setVisibility(View.VISIBLE);
            }

            @Override
            public void keyBoardHide(int height) {
                goodsEt.clearFocus();
                closeIv.setVisibility(View.GONE);
                getGoodsData();
            }
        });

        goodsEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handled = true;
                    hiddenEdit();
                }
                return handled;
            }
        });
    }

    private void getGoodsData() {
        keyword = goodsEt.getText().toString().trim();
        if (!TextUtils.isEmpty(keyword)) {
            searchGoodsPresenter.getKeywordGoods(StaticData.REFLASH_ONE, keyword, sortType, orderType);
        } else {
            historyLl.setVisibility(View.VISIBLE);
            goodsLl.setVisibility(View.GONE);
            noRecordLl.setVisibility(View.GONE);
        }
    }

    private void getSearchGoodsData() {
        goodsEt.setText(keyword);
        closeIv.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(keyword)) {
            searchGoodsPresenter.getKeywordGoods(StaticData.REFLASH_ONE, keyword, sortType, orderType);
        } else {
            historyLl.setVisibility(View.VISIBLE);
            goodsLl.setVisibility(View.GONE);
            noRecordLl.setVisibility(View.GONE);
        }
    }


    private void addAdapter() {
        getWidth();
        goodsItemAdapter = new GoodsItemAdapter(this);
        goodsItemAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(goodsItemAdapter);
    }


    private void setTagview() {
        wordDatas = new ArrayList<>();
        wordAdapter = new TagAdapter<String>(wordDatas) {
            @Override
            public View getView(FlowLayoutView parent, int position, String o) {
                TextView view = (TextView) LayoutInflater.from(SearchGoodsActivity.this).inflate(R.layout.historyt_search_tagview, parent, false);
                view.setText(o);
                return view;
            }
        };
        historyTag.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayoutView parent) {
                keyword = wordDatas.get(position);
                getSearchGoodsData();
                return false;
            }
        });
        historyTag.setAdapter(wordAdapter);
    }


    private void getWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels / 2;
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    private void hiddenEdit() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(goodsEt.getWindowToken(), 0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    @Override
    public boolean isShouldHideInput(View v, MotionEvent event) {
        return super.isShouldHideInput(v, event);
    }

    @OnClick({R.id.back, R.id.close_iv, R.id.clear_history, R.id.default_rl, R.id.price_ll, R.id.sales_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                hiddenEdit();
                finish();
                break;
            case R.id.close_iv:
                goodsEt.setText("");
                break;
            case R.id.clear_history:
                searchGoodsPresenter.deleteHistory();
                break;
            case R.id.default_rl:
                priceDesc = false;
                selectType = StaticData.REFLASH_ONE;
                selectType(selectType);
                break;
            case R.id.price_ll:
                priceDesc = !priceDesc;
                if (priceDesc) {
                    selectType = StaticData.REFLASH_TWO;
                } else {
                    selectType = StaticData.REFLASH_THREE;
                }
                selectType(selectType);
                break;
            case R.id.sales_rl:
                priceDesc = false;
                selectType = StaticData.REFLASH_FOUR;
                selectType(selectType);
                break;
            default:
        }
    }

    private void selectType(String type) {
        defaultIv.setVisibility(TextUtils.equals(StaticData.REFLASH_ONE, type) ? View.VISIBLE : View.GONE);
        priceIv.setVisibility((TextUtils.equals(StaticData.REFLASH_TWO, type) || TextUtils.equals(StaticData.REFLASH_THREE, type)) ? View.VISIBLE : View.GONE);
        salesIv.setVisibility(TextUtils.equals(StaticData.REFLASH_FOUR, type) ? View.VISIBLE : View.GONE);
        if (TextUtils.equals(StaticData.REFLASH_ONE, type)) {
            sortType = "";
            orderType = "";
            priceArrowIv.setVisibility(View.VISIBLE);
            priceArrow.setVisibility(View.GONE);
        } else if (TextUtils.equals(StaticData.REFLASH_TWO, type)) {
            sortType = StaticData.SORT_TYPE_PRICE;
            orderType = StaticData.ORDER_TYPE_DESC;
            priceArrowIv.setVisibility(View.GONE);
            priceArrow.setVisibility(View.VISIBLE);
            priceArrow.setSelected(false);
        } else if (TextUtils.equals(StaticData.REFLASH_THREE, type)) {
            sortType = StaticData.SORT_TYPE_PRICE;
            orderType = StaticData.ORDER_TYPE_ASC;
            priceArrowIv.setVisibility(View.GONE);
            priceArrow.setVisibility(View.VISIBLE);
            priceArrow.setSelected(true);
        } else if (TextUtils.equals(StaticData.REFLASH_FOUR, type)) {
            sortType = StaticData.SORT_TYPE_SALES;
            orderType = StaticData.ORDER_TYPE_DESC;
            priceArrowIv.setVisibility(View.VISIBLE);
            priceArrow.setVisibility(View.GONE);
        }
        searchGoodsPresenter.getKeywordGoods(StaticData.REFLASH_ONE, keyword, sortType, orderType);

    }

    @Override
    public void renderSearchHistory(List<String> history) {
        wordDatas.clear();
        wordDatas.addAll(history);
        wordAdapter.setDatas(wordDatas);
    }

    @Override
    public void renderDeleteHistory() {
        historyTag.removeAllViews();
    }

    @Override
    public void renderKeywordGoods(GoodsItem goodsItem) {
        searchGoodsPresenter.getSearchHistory(StaticData.REFLASH_ZERO);
        historyLl.setVisibility(View.GONE);
        refreshLayout.finishRefresh();
        if (goodsItem != null) {
            if (goodsItem.getGoodsItemInfos() != null && goodsItem.getGoodsItemInfos().size() > 0) {
                goodsLl.setVisibility(View.VISIBLE);
                noRecordLl.setVisibility(View.GONE);
                if (goodsItem.getGoodsItemInfos().size() == Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                    refreshLayout.resetNoMoreData();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                goodsItemAdapter.setData(goodsItem.getGoodsItemInfos());
            } else {
                goodsLl.setVisibility(View.GONE);
                noRecordLl.setVisibility(View.VISIBLE);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void renderMoreKeywordGoods(GoodsItem goodsItem) {
        refreshLayout.finishLoadMore();
        if (goodsItem != null && goodsItem.getGoodsItemInfos() != null) {
            if (goodsItem.getGoodsItemInfos().size() != Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            goodsItemAdapter.addMore(goodsItem.getGoodsItemInfos());
        }
    }

    @Override
    public void setPresenter(SortContract.SearchGoodsPresenter presenter) {

    }

    @Override
    public void goGoodsDetails(String goodsType, String goodsId) {
        if (TextUtils.equals(StaticData.REFLASH_ONE, goodsType)) {
            GeneralGoodsDetailsActivity.start(this, goodsId);
        } else if (TextUtils.equals(StaticData.REFLASH_TWO, goodsType)) {
            OrdinaryGoodsDetailActivity.start(this, goodsId);
        } else {
            ActivityGroupGoodsActivity.start(this, goodsId);
        }
    }
}
