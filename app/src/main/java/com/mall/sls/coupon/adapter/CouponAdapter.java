package com.mall.sls.coupon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.mall.sls.R;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.CouponInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponView> {
    private LayoutInflater layoutInflater;
    private List<CouponInfo> couponInfos;
    private String couponType;

    public CouponAdapter(String couponType) {
        this.couponType = couponType;
    }

    public void setData(List<CouponInfo> couponInfos) {
        this.couponInfos = couponInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<CouponInfo> moreList) {
        int pos = couponInfos.size();
        couponInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public CouponView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_coupon, parent, false);
        return new CouponView(view);
    }

    @Override
    public void onBindViewHolder(CouponView holder, int position) {
        CouponInfo couponInfo = couponInfos.get(holder.getAdapterPosition());
        holder.bindData(couponInfo);
    }

    @Override
    public int getItemCount() {
        return couponInfos == null ? 0 : couponInfos.size();
    }

    public class CouponView extends RecyclerView.ViewHolder {
        @BindView(R.id.amount)
        MediumThickTextView amount;
        @BindView(R.id.condition)
        ConventionalTextView condition;
        @BindView(R.id.amount_ll)
        LinearLayout amountLl;
        @BindView(R.id.status_iv)
        ImageView statusIv;
        @BindView(R.id.use_tip)
        ConventionalTextView useTip;


        public CouponView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CouponInfo couponInfo) {

        }

    }


    public interface OnItemClickListener {

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
