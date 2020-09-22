package com.mall.sls.sort.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseFragment;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.data.entity.FirstCategory;
import com.mall.sls.data.entity.FirstCategoryInfo;
import com.mall.sls.data.entity.SecondCategory;
import com.mall.sls.homepage.ui.ActivityGroupGoodsActivity;
import com.mall.sls.homepage.ui.GeneralGoodsDetailsActivity;
import com.mall.sls.homepage.ui.OrdinaryGoodsDetailActivity;
import com.mall.sls.sort.DaggerSortComponent;
import com.mall.sls.sort.SortContract;
import com.mall.sls.sort.SortModule;
import com.mall.sls.sort.adapter.FirstCategoryAdapter;
import com.mall.sls.sort.adapter.SecondCategoryAdapter;
import com.mall.sls.sort.presenter.SortPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/9/21.
 * 描述：
 */
public class SortFragment extends BaseFragment implements SortContract.SortView, FirstCategoryAdapter.OnItemClickListener,SecondCategoryAdapter.OnItemClickListener {

    @BindView(R.id.small_title)
    ConventionalTextView smallTitle;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_brand)
    ConventionalTextView searchBrand;
    @BindView(R.id.search_rl)
    RelativeLayout searchRl;
    @BindView(R.id.first_category_rv)
    RecyclerView firstCategoryRv;
    @BindView(R.id.second_category_rv)
    RecyclerView secondCategoryRv;
    private String categoryId;
    private List<FirstCategoryInfo> firstCategoryInfos;

    public static SortFragment newInstance() {
        SortFragment fragment = new SortFragment();
        return fragment;
    }

    @Inject
    SortPresenter sortPresenter;

    private FirstCategoryAdapter firstCategoryAdapter;
    private SecondCategoryAdapter secondCategoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_sort, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHeight(null, smallTitle, null);
        addAdapter();
    }

    private void initView() {

    }

    private void addAdapter() {
        firstCategoryAdapter = new FirstCategoryAdapter();
        firstCategoryAdapter.setOnItemClickListener(this);
        firstCategoryRv.setAdapter(firstCategoryAdapter);
        secondCategoryAdapter = new SecondCategoryAdapter(getActivity());
        secondCategoryAdapter.setOnItemClickListener(this);
        secondCategoryRv.setAdapter(secondCategoryAdapter);
    }

    @Override
    protected void initializeInjector() {
        DaggerSortComponent.builder()
                .applicationComponent(getApplicationComponent())
                .sortModule(new SortModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void renderFirstCategory(FirstCategory firstCategory) {
        if (firstCategory != null) {
            this.firstCategoryInfos = firstCategory.getFirstCategoryInfos();
            if (firstCategoryInfos != null && firstCategoryInfos.size() > 0) {
                firstCategoryInfos.get(0).setSelect(true);
                categoryId = firstCategoryInfos.get(0).getId();
            }
            firstCategoryAdapter.setData(firstCategoryInfos);
            sortPresenter.getSecondCategory(categoryId);
        }
    }

    @Override
    public void renderSecondCategory(SecondCategory secondCategory) {
        if (secondCategory != null) {
            secondCategoryAdapter.setData(secondCategory.getSecondCategoryInfos());
        }
    }

    @Override
    public void setPresenter(SortContract.SortPresenter presenter) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && sortPresenter != null && (firstCategoryInfos==null||firstCategoryInfos.size()==0)) {
            sortPresenter.getFirstCategory();
        }
    }

    @Override
    public void selectCategory(int position) {
        for (int i = 0; i < firstCategoryInfos.size(); i++) {
            if (position == i) {
                firstCategoryInfos.get(i).setSelect(true);
            } else {
                firstCategoryInfos.get(i).setSelect(false);
            }
        }
        categoryId = firstCategoryInfos.get(position).getId();
        firstCategoryAdapter.setData(firstCategoryInfos);
        sortPresenter.getSecondCategory(categoryId);
    }

    @Override
    public void goCategoryGoods(String categoryName,String categoryId) {
        CategoryGoodsActivity.start(getActivity(),categoryName,categoryId);
    }

    @Override
    public void goGoodsDetails(String goodsType, String goodsId) {
        if (TextUtils.equals(StaticData.REFLASH_ONE, goodsType)) {
            GeneralGoodsDetailsActivity.start(getActivity(), goodsId);
        } else if (TextUtils.equals(StaticData.REFLASH_TWO, goodsType)) {
            OrdinaryGoodsDetailActivity.start(getActivity(), goodsId);
        } else {
            ActivityGroupGoodsActivity.start(getActivity(), goodsId);
        }
    }

    @OnClick({R.id.search_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_rl:
                SearchGoodsActivity.start(getActivity());
                break;
            default:
        }
    }
}
