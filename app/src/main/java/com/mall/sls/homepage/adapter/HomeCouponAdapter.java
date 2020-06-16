package com.mall.sls.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.unit.ConvertDpAndPx;
import com.mall.sls.common.unit.MarginsUnit;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.HomeCouponInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeCouponAdapter extends RecyclerView.Adapter<HomeCouponAdapter.HomeCouponView> {
    private LayoutInflater layoutInflater;
    private List<HomeCouponInfo> homeCouponInfos;
    private Context context;

    public void setData(List<HomeCouponInfo> homeCouponInfos) {
        this.homeCouponInfos = homeCouponInfos;
        notifyDataSetChanged();
    }

    public HomeCouponAdapter(Context context) {
        this.context = context;
    }

    @Override
    public HomeCouponView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_home_coupon, parent, false);
        return new HomeCouponView(view);
    }

    @Override
    public void onBindViewHolder(HomeCouponView holder, int position) {
        HomeCouponInfo homeCouponInfo = homeCouponInfos.get(holder.getAdapterPosition());
        holder.bindData(homeCouponInfo);
        if(position==0){
            MarginsUnit.setMargins(holder.itemRl, ConvertDpAndPx.Dp2Px(context,15),0,ConvertDpAndPx.Dp2Px(context,4),0);
        }else if(position==homeCouponInfos.size()-1){
            MarginsUnit.setMargins(holder.itemRl,ConvertDpAndPx.Dp2Px(context,4),0,ConvertDpAndPx.Dp2Px(context,15),0);
        }else {
            MarginsUnit.setMargins(holder.itemRl,ConvertDpAndPx.Dp2Px(context,4),0,ConvertDpAndPx.Dp2Px(context,4),0);
        }

    }

    @Override
    public int getItemCount() {
        return homeCouponInfos == null ? 0 : homeCouponInfos.size();
    }

    public class HomeCouponView extends RecyclerView.ViewHolder {
        @BindView(R.id.amount)
        MediumThickTextView amount;
        @BindView(R.id.condition)
        ConventionalTextView condition;
        @BindView(R.id.item_rl)
        RelativeLayout itemRl;

        public HomeCouponView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(HomeCouponInfo homeCouponInfo) {
            amount.setText(NumberFormatUnit.numberFormat(homeCouponInfo.getDiscount()));
            condition.setText("满" + NumberFormatUnit.numberFormat(homeCouponInfo.getMin()) + "可用");
        }
    }

}
