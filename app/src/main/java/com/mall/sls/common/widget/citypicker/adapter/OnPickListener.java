package com.mall.sls.common.widget.citypicker.adapter;


import com.mall.sls.common.widget.citypicker.model.City;

public interface OnPickListener {
    void onPick(int position, City data);
    void onLocate();
    void onCancel();
}
