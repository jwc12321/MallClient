package com.mall.sls.coupon.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseFragment;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.coupon.CouponContract;
import com.mall.sls.coupon.CouponModule;
import com.mall.sls.coupon.DaggerCouponComponent;
import com.mall.sls.coupon.adapter.CouponAdapter;
import com.mall.sls.coupon.presenter.CouponListPresenter;
import com.mall.sls.data.entity.CouponInfo;
import com.mall.sls.data.entity.MyCouponInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.List;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/12.
 * 描述：未使用的优惠卷
 */
public class CouponUnusedFragment extends BaseFragment implements CouponContract.CouponListView, CouponAdapter.OnItemClickListener {

    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.no_record_ll)
    LinearLayout noRecordLl;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private CouponAdapter couponAdapter;

    @Inject
    CouponListPresenter couponListPresenter;
    private List<CouponInfo> couponInfos;

    public static CouponUnusedFragment newInstance() {
        CouponUnusedFragment fragment = new CouponUnusedFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_coupon, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        refreshLayout.setOnMultiPurposeListener(simpleMultiPurposeListener);
        couponAdapter = new CouponAdapter(StaticData.REFLASH_ZERO);
        couponAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(couponAdapter);
        couponListPresenter.getCouponInfos(StaticData.REFLASH_ONE, StaticData.REFLASH_ZERO);
    }

    @Override
    protected void initializeInjector() {
        DaggerCouponComponent.builder()
                .applicationComponent(getApplicationComponent())
                .couponModule(new CouponModule(this))
                .build()
                .inject(this);
    }


    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
            couponListPresenter.getCouponInfos(StaticData.REFLASH_ZERO, StaticData.REFLASH_ZERO);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            couponListPresenter.getMoreCouponInfos(StaticData.REFLASH_ZERO);
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()&&couponListPresenter!=null) {
            couponListPresenter.getCouponInfos(StaticData.REFLASH_ONE,StaticData.REFLASH_ZERO);
        }
    }

    @Override
    public void renderCouponInfos(MyCouponInfo myCouponInfo) {
        refreshLayout.finishRefresh();
        if (myCouponInfo != null) {
            this.couponInfos=myCouponInfo.getCouponInfos();
            if (couponInfos != null && couponInfos.size() > 0) {
                recordRv.setVisibility(View.VISIBLE);
                noRecordLl.setVisibility(View.GONE);
                if (couponInfos.size() == Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                    refreshLayout.resetNoMoreData();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                couponAdapter.setData(couponInfos);
            } else {
                recordRv.setVisibility(View.GONE);
                noRecordLl.setVisibility(View.VISIBLE);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            if(listener!=null){
                listener.returnUnusedNumebr(myCouponInfo.getTotal());
            }
        }
    }

    @Override
    public void renderMoreCouponInfos(MyCouponInfo myCouponInfo) {
        refreshLayout.finishLoadMore();
        if (myCouponInfo != null && myCouponInfo.getCouponInfos() != null) {
            if (myCouponInfo.getCouponInfos().size() != Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            couponAdapter.addMore(myCouponInfo.getCouponInfos());
        }
    }

    @Override
    public void setPresenter(CouponContract.CouponListPresenter presenter) {

    }

    @Override
    public void goUsed() {
        if(listener!=null){
            listener.goLocalTeam();
        }
    }

    @OnClick({R.id.no_record_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.no_record_bt:
                if(listener!=null){
                    listener.goLocalTeam();
                }
                break;
            default:
        }
    }


    public interface CouponUnusedListener {
        void goLocalTeam();
        void returnUnusedNumebr(String number);
    }

    private CouponUnusedListener listener;

    public void setCouponUnusedListener(CouponUnusedListener listener) {
        this.listener = listener;
    }

    @Override
    public void upDownView(ImageView upIv, ImageView downIv, ConventionalTextView limitTv, int position) {
        CouponInfo couponInfo=couponInfos.get(position);
        if(couponInfo.isUp()){
            couponInfo.setUp(false);
            upIv.setVisibility(View.GONE);
            downIv.setVisibility(View.VISIBLE);
            limitTv.setVisibility(View.GONE);
        }else {
            couponInfo.setUp(true);
            upIv.setVisibility(View.VISIBLE);
            downIv.setVisibility(View.GONE);
            limitTv.setVisibility(View.VISIBLE);
        }
    }
}
