package com.mall.sls.order.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.PayRecordInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayRecordAdapter extends RecyclerView.Adapter<PayRecordAdapter.PayRecordView> {

    private LayoutInflater layoutInflater;
    private List<PayRecordInfo> payRecordInfos;
    private Context context;

    public PayRecordAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PayRecordInfo> payRecordInfos) {
        this.payRecordInfos = payRecordInfos;
        notifyDataSetChanged();
    }

    @Override
    public PayRecordView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_pay_record, parent, false);
        return new PayRecordView(view);
    }

    @Override
    public void onBindViewHolder(PayRecordView holder, int position) {
        PayRecordInfo payRecordInfo = payRecordInfos.get(holder.getAdapterPosition());
        holder.bindData(payRecordInfo);
        holder.payNoRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.copyOrderNo(payRecordInfo.getPayNo());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return payRecordInfos == null ? 0 : payRecordInfos.size();
    }

    public class PayRecordView extends RecyclerView.ViewHolder {
        @BindView(R.id.pay_no)
        ConventionalTextView payNo;
        @BindView(R.id.copy_bt)
        MediumThickTextView copyBt;
        @BindView(R.id.pay_time)
        ConventionalTextView payTime;
        @BindView(R.id.pay_time_rl)
        RelativeLayout payTimeRl;
        @BindView(R.id.pay_type)
        ConventionalTextView payType;
        @BindView(R.id.pay_status)
        ConventionalTextView payStatus;
        @BindView(R.id.pay_number)
        ConventionalTextView payNumber;
        @BindView(R.id.pay_number_rl)
        RelativeLayout payNumberRl;
        @BindView(R.id.pay_amount)
        ConventionalTextView payAmount;
        @BindView(R.id.pay_no_rl)
        RelativeLayout payNoRl;

        public PayRecordView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(PayRecordInfo payRecordInfo) {
            payNo.setText(payRecordInfo.getPayNo());
            if (TextUtils.equals(StaticData.REFRESH_ZERO, payRecordInfo.getPayType())) {
                payType.setText(context.getString(R.string.weixin_pay));
            } else if (TextUtils.equals(StaticData.REFRESH_ONE, payRecordInfo.getPayType())) {
                payType.setText(context.getString(R.string.ali_pay));
            } else if (TextUtils.equals(StaticData.REFRESH_THREE, payRecordInfo.getPayType())) {
                payType.setText(context.getString(R.string.bank_pay));
            }
            if (TextUtils.equals(StaticData.REFRESH_ONE, payRecordInfo.getOrderStatus())) {
                payStatus.setText(context.getString(R.string.payment_success));
            } else if (TextUtils.equals(StaticData.REFRESH_TWO, payRecordInfo.getOrderStatus())) {
                payStatus.setText(context.getString(R.string.payment_process));
            } else if (TextUtils.equals(StaticData.REFRESH_THREE, payRecordInfo.getOrderStatus())) {
                payStatus.setText(context.getString(R.string.payment_failed));
            }
            payAmount.setText(NumberFormatUnit.numberFormat(payRecordInfo.getActualPrice()));
            payTime.setText(payRecordInfo.getPayTime());
            payNumber.setText(payRecordInfo.getPaySn());
            payTimeRl.setVisibility(TextUtils.isEmpty(payRecordInfo.getPayTime())?View.GONE:View.VISIBLE);
            payNumberRl.setVisibility(TextUtils.isEmpty(payRecordInfo.getPaySn())?View.GONE:View.VISIBLE);
        }
    }


    public interface OnItemClickListener {
        void copyOrderNo(String payNo);//复制

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
