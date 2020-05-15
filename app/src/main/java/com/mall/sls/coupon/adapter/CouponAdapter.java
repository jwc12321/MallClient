package com.mall.sls.coupon.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.NumberFormatUnit;
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
        holder.usedBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.goUsed();
                }
            }
        });
        holder.upDownRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.upDownView(holder.upIv,holder.downIv,holder.limit,holder.getAdapterPosition());
                }
            }
        });
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
        @BindView(R.id.name)
        MediumThickTextView name;
        @BindView(R.id.endTime)
        MediumThickTextView endTime;
        @BindView(R.id.used_bt)
        ConventionalTextView usedBt;
        @BindView(R.id.use_tip)
        ConventionalTextView useTip;
        @BindView(R.id.down_iv)
        ImageView downIv;
        @BindView(R.id.up_iv)
        ImageView upIv;
        @BindView(R.id.up_down_rl)
        RelativeLayout upDownRl;
        @BindView(R.id.limit)
        ConventionalTextView limit;


        public CouponView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CouponInfo couponInfo) {
            amount.setText(NumberFormatUnit.numberFormat(couponInfo.getDiscount()));
            condition.setText("满" + NumberFormatUnit.numberFormat(couponInfo.getMin()) + "元可使用");
            name.setText(couponInfo.getName());
            endTime.setText(couponInfo.getEndTime() + "到期");
            if (TextUtils.equals(StaticData.REFLASH_ZERO, couponType)) {
                statusIv.setVisibility(View.INVISIBLE);
                usedBt.setEnabled(true);
            } else if (TextUtils.equals(StaticData.REFLASH_ONE, couponType)) {
                statusIv.setVisibility(View.VISIBLE);
                statusIv.setSelected(true);
                usedBt.setEnabled(false);
            } else {
                statusIv.setVisibility(View.VISIBLE);
                statusIv.setSelected(false);
                usedBt.setEnabled(false);
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (!TextUtils.isEmpty(couponInfo.getStartTime()) && !TextUtils.isEmpty(couponInfo.getEndTime())) {
                stringBuilder.append("限" + couponInfo.getStartTime() + "至" + couponInfo.getEndTime());
            }
            if (!TextUtils.isEmpty(couponInfo.getLimitCondition())) {
                stringBuilder.append("\n");
                stringBuilder.append(couponInfo.getLimitCondition());
            }
            if (!TextUtils.isEmpty(couponInfo.getMin())) {
                stringBuilder.append("\n");
                stringBuilder.append("满" + NumberFormatUnit.numberFormat(couponInfo.getMin()) + "元可使用");
                stringBuilder.append("\n");
            }
            stringBuilder.append("不可与其他优惠活动叠加使用");
            limit.setText(stringBuilder.toString());
            useTip.setText(couponInfo.getDescription());
        }

    }


    public interface OnItemClickListener {
        void goUsed();

        void upDownView(ImageView upIv, ImageView downIv, ConventionalTextView limitTv, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
