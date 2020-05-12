package com.mall.sls.order.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.data.entity.OrderTimeInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderInformationAdapter extends RecyclerView.Adapter<OrderInformationAdapter.OrderInformationView> {
    private LayoutInflater layoutInflater;
    private List<OrderTimeInfo> orderTimeInfos;
    private Context context;

    public void setData(List<OrderTimeInfo> orderTimeInfos) {
        this.orderTimeInfos = orderTimeInfos;
        notifyDataSetChanged();
    }

    public OrderInformationAdapter(Context context) {
        this.context = context;
    }

    @Override
    public OrderInformationView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_order_information, parent, false);
        return new OrderInformationView(view);
    }

    @Override
    public void onBindViewHolder(OrderInformationView holder, int position) {
        OrderTimeInfo orderTimeInfo = orderTimeInfos.get(holder.getAdapterPosition());
        holder.bindData(orderTimeInfo);
        holder.copyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.copyOrderNo(orderTimeInfo.getTime());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderTimeInfos == null ? 0 : orderTimeInfos.size();
    }

    public class OrderInformationView extends RecyclerView.ViewHolder {
        @BindView(R.id.time_type)
        ConventionalTextView timeType;
        @BindView(R.id.time)
        ConventionalTextView time;
        @BindView(R.id.copy_bt)
        ConventionalTextView copyBt;

        public OrderInformationView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(OrderTimeInfo orderTimeInfo) {
            timeType.setText(orderTimeInfo.getTimeType());
            time.setText(orderTimeInfo.getTime());
            copyBt.setVisibility(TextUtils.equals(context.getString(R.string.order_number),orderTimeInfo.getTimeType())?View.VISIBLE:View.GONE);
        }
    }

    public interface OnItemClickListener {
        void copyOrderNo(String orderNo);//支付订单

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
