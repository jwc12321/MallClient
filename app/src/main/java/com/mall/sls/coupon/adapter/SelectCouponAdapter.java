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
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.CouponInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCouponAdapter extends RecyclerView.Adapter<SelectCouponAdapter.SelectCouponView> {
    private LayoutInflater layoutInflater;
    private List<CouponInfo> couponInfos;
    private String userCouponId;

    public SelectCouponAdapter(String userCouponId) {
        this.userCouponId = userCouponId;
    }

    public void setData(List<CouponInfo> couponInfos) {
        this.couponInfos = couponInfos;
        notifyDataSetChanged();
    }

    public void setData(String userCouponId) {
        this.userCouponId = userCouponId;
        notifyDataSetChanged();
    }

    public void addMore(List<CouponInfo> moreList) {
        int pos = couponInfos.size();
        couponInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public SelectCouponView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_select_coupon, parent, false);
        return new SelectCouponView(view);
    }

    @Override
    public void onBindViewHolder(SelectCouponView holder, int position) {
        CouponInfo couponInfo = couponInfos.get(holder.getAdapterPosition());
        holder.bindData(couponInfo);
        holder.selectIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.selectWhat(couponInfo.getCid(), couponInfo.getId());
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

    public class SelectCouponView extends RecyclerView.ViewHolder {
        @BindView(R.id.amount)
        MediumThickTextView amount;
        @BindView(R.id.condition)
        ConventionalTextView condition;
        @BindView(R.id.amount_ll)
        LinearLayout amountLl;
        @BindView(R.id.select_iv)
        ImageView selectIv;
        @BindView(R.id.name)
        MediumThickTextView name;
        @BindView(R.id.endTime)
        MediumThickTextView endTime;
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


        public SelectCouponView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CouponInfo couponInfo) {
            amount.setText(NumberFormatUnit.numberFormat(couponInfo.getDiscount()));
            condition.setText("满" + NumberFormatUnit.numberFormat(couponInfo.getMin()) + "元可使用");
            name.setText(couponInfo.getName());
            endTime.setText(couponInfo.getEndTime() + "到期");
            if (!TextUtils.isEmpty(couponInfo.getId()) && TextUtils.equals(couponInfo.getId(), userCouponId)) {
                selectIv.setSelected(true);
            } else {
                selectIv.setSelected(false);
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
        void selectWhat(String couponId, String userCouponId);

        void upDownView(ImageView upIv, ImageView downIv, ConventionalTextView limitTv, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
