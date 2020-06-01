package com.mall.sls.common.widget.citypicker.adapter;


import com.mall.sls.common.widget.citypicker.model.City;

public interface InnerListener {
    void dismiss(int position, City data);
    void locate();
}
