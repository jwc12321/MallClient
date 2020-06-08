package com.mall.sls.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.data.entity.ShipOrderInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogisticsAdapter extends RecyclerView.Adapter<LogisticsAdapter.LogisticsView> {
    private LayoutInflater layoutInflater;
    private List<ShipOrderInfo> shipOrderInfos;
    private Context context;

    public void setData(List<ShipOrderInfo> shipOrderInfos) {
        this.shipOrderInfos = shipOrderInfos;
        notifyDataSetChanged();
    }

    @Override
    public LogisticsView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_logistics, parent, false);
        return new LogisticsView(view);
    }

    @Override
    public void onBindViewHolder(LogisticsView holder, int position) {
        ShipOrderInfo shipOrderInfo = shipOrderInfos.get(holder.getAdapterPosition());
        holder.bindData(shipOrderInfo);
        if(holder.getAdapterPosition()==0){
            holder.upLine.setVisibility(View.GONE);
            holder.downLine.setVisibility(View.VISIBLE);
            holder.submitIv.setSelected(true);
        }else if(holder.getAdapterPosition()==shipOrderInfos.size()-1){
            holder.upLine.setVisibility(View.VISIBLE);
            holder.downLine.setVisibility(View.GONE);
            holder.submitIv.setSelected(false);
        }else {
            holder.upLine.setVisibility(View.VISIBLE);
            holder.downLine.setVisibility(View.VISIBLE);
            holder.submitIv.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return shipOrderInfos == null ? 0 : shipOrderInfos.size();
    }

    public class LogisticsView extends RecyclerView.ViewHolder {
        @BindView(R.id.submit_iv)
        ImageView submitIv;
        @BindView(R.id.statusDesc)
        ConventionalTextView statusDesc;
        @BindView(R.id.statusTime)
        ConventionalTextView statusTime;
        @BindView(R.id.up_line)
        View upLine;
        @BindView(R.id.down_line)
        View downLine;

        public LogisticsView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ShipOrderInfo shipOrderInfo) {
            statusDesc.setText(shipOrderInfo.getStatusDesc());
            statusTime.setText(shipOrderInfo.getStatusTime());
        }
    }
}
