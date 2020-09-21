package com.mall.sls.coupon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.CouponInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeCouponReceiveAdapter extends RecyclerView.Adapter<HomeCouponReceiveAdapter.HomeCouponReceiveView> {
    private LayoutInflater layoutInflater;
    private List<CouponInfo> couponInfos;

    public void setData(List<CouponInfo> couponInfos) {
        this.couponInfos = couponInfos;
        notifyDataSetChanged();
    }

    @Override
    public HomeCouponReceiveView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_home_coupon_receive, parent, false);
        return new HomeCouponReceiveView(view);
    }

    @Override
    public void onBindViewHolder(HomeCouponReceiveView holder, int position) {
        CouponInfo couponInfo = couponInfos.get(holder.getAdapterPosition());
        holder.bindData(couponInfo);
    }

    @Override
    public int getItemCount() {
        return couponInfos == null ? 0 : couponInfos.size();
    }

    public class HomeCouponReceiveView extends RecyclerView.ViewHolder {
        @BindView(R.id.symbol)
        ConventionalTextView symbol;
        @BindView(R.id.amount)
        MediumThickTextView amount;
        @BindView(R.id.condition)
        ConventionalTextView condition;
        @BindView(R.id.amount_ll)
        LinearLayout amountLl;
        @BindView(R.id.name)
        MediumThickTextView name;
        @BindView(R.id.endTime)
        ConventionalTextView endTime;
        @BindView(R.id.coupon_rl)
        RelativeLayout couponRl;


        public HomeCouponReceiveView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CouponInfo couponInfo) {
            amount.setText(NumberFormatUnit.numberFormat(couponInfo.getDiscount()));
            condition.setText("满" + NumberFormatUnit.numberFormat(couponInfo.getMin()) + "元可用");
            name.setText(couponInfo.getName());
            endTime.setText(couponInfo.getEndTime() + "到期");
        }

    }
}
