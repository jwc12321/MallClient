package com.mall.sls.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.RefundInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RefundAdapter extends RecyclerView.Adapter<RefundAdapter.RefundView> {
    private LayoutInflater layoutInflater;
    private List<RefundInfo> refundInfos;
    private Context context;

    public void setData(List<RefundInfo> refundInfos) {
        this.refundInfos = refundInfos;
        notifyDataSetChanged();
    }

    public RefundAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RefundView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_view_fund, parent, false);
        return new RefundView(view);
    }

    @Override
    public void onBindViewHolder(RefundView holder, int position) {
        RefundInfo refundInfo = refundInfos.get(holder.getAdapterPosition());
        holder.bindData(refundInfo);
    }

    @Override
    public int getItemCount() {
        return refundInfos == null ? 0 : refundInfos.size();
    }

    public class RefundView extends RecyclerView.ViewHolder {
        @BindView(R.id.black_view)
        View blackView;
        @BindView(R.id.refund_number_tv)
        ConventionalTextView refundNumberTv;
        @BindView(R.id.refund_number)
        ConventionalTextView refundNumber;
        @BindView(R.id.status)
        ConventionalTextView status;
        @BindView(R.id.refund_amount)
        MediumThickTextView refundAmount;
        @BindView(R.id.starting_time)
        ConventionalTextView startingTime;
        @BindView(R.id.arrival_time)
        ConventionalTextView arrivalTime;
        @BindView(R.id.arrival_time_tv)
        ConventionalTextView arrivalTimeTv;
        @BindView(R.id.fund_tip)
        ConventionalTextView fundTip;


        public RefundView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(RefundInfo refundInfo) {
            refundNumber.setText(refundInfo.getRefundNo());
            refundAmount.setText(NumberFormatUnit.goodsFormat(refundInfo.getRefundAmount()));
            startingTime.setText(refundInfo.getAddTime());
            if (refundInfo.getRefundSuccess() != null && refundInfo.getRefundSuccess()) {
                status.setText(context.getString(R.string.refund_success));
                status.setSelected(true);
                arrivalTimeTv.setText(context.getString(R.string.arrival_time));
                arrivalTime.setText(context.getString(R.string.one_three_day));
                fundTip.setVisibility(View.VISIBLE);
            } else {
                status.setText(context.getString(R.string.refund_fail));
                status.setSelected(false);
                arrivalTimeTv.setText(context.getString(R.string.fail_time));
                arrivalTime.setText(refundInfo.getUpdateTime());
                fundTip.setVisibility(View.GONE);
            }
        }
    }
}
