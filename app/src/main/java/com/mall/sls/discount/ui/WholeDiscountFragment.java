package com.mall.sls.discount.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mall.sls.BaseFragment;
import com.mall.sls.R;

import butterknife.ButterKnife;

/**
 * @author jwc on 2020/5/9.
 * 描述：全网优惠
 */
public class WholeDiscountFragment extends BaseFragment {

    public static WholeDiscountFragment newInstance() {
        WholeDiscountFragment fragment = new WholeDiscountFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_whole_discount, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }
}
