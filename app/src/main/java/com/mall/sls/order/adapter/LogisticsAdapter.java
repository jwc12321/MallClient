package com.mall.sls.order.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
        holder.bindData(shipOrderInfo,holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return shipOrderInfos == null ? 0 : shipOrderInfos.size();
    }

    public class LogisticsView extends RecyclerView.ViewHolder {
        @BindView(R.id.submit_iv)
        ImageView submitIv;
        @BindView(R.id.up_line)
        View upLine;
        @BindView(R.id.up_rl)
        RelativeLayout upRl;
        @BindView(R.id.down_line)
        View downLine;
        @BindView(R.id.title)
        ConventionalTextView title;
        @BindView(R.id.content)
        ConventionalTextView content;
        @BindView(R.id.time)
        ConventionalTextView time;

        public LogisticsView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ShipOrderInfo shipOrderInfo,int position) {
            submitIv.setSelected(position==0);
            upLine.setVisibility(position==0?View.GONE:View.VISIBLE);
            downLine.setVisibility(position==(shipOrderInfos.size()-1)?View.GONE:View.VISIBLE);
            content.setText(shipOrderInfo.getRemark());
            time.setText(shipOrderInfo.getStatusTime());
            title.setText(shipOrderInfo.getStatusDesc());
        }
    }
}
