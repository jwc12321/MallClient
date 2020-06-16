package com.mall.sls.common.unit;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author jwc on 2020/6/16.
 * 描述：
 */
public class MarginsUnit {
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
