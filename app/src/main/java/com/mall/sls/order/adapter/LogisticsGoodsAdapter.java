package com.mall.sls.order.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.data.entity.OrderGoodsVo;
import com.mall.sls.data.entity.ShipOrderInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogisticsGoodsAdapter extends RecyclerView.Adapter<LogisticsGoodsAdapter.LogisticsGoodsView> {
    private LayoutInflater layoutInflater;
    private List<OrderGoodsVo> orderGoodsVos;
    private Context context;
    private List<ShipOrderInfo> shipOrderInfos;

    public void setData(List<OrderGoodsVo> orderGoodsVos, List<ShipOrderInfo> shipOrderInfos) {
        this.orderGoodsVos = orderGoodsVos;
        this.shipOrderInfos = shipOrderInfos;
        notifyDataSetChanged();
    }

    public LogisticsGoodsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public LogisticsGoodsView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_logistics_goods, parent, false);
        return new LogisticsGoodsView(view);
    }

    @Override
    public void onBindViewHolder(LogisticsGoodsView holder, int position) {
        OrderGoodsVo orderGoodsVo = orderGoodsVos.get(holder.getAdapterPosition());
        holder.bindData(orderGoodsVo);
        holder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.goDeliveryInfoActivity(shipOrderInfos);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderGoodsVos == null ? 0 : orderGoodsVos.size();
    }

    public class LogisticsGoodsView extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_iv)
        ImageView goodsIv;
        @BindView(R.id.item_ll)
        LinearLayout itemLl;

        public LogisticsGoodsView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(OrderGoodsVo orderGoodsVo) {
            GlideHelper.load((Activity) context, orderGoodsVo.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
        }
    }


    public interface OnItemClickListener {
        void goDeliveryInfoActivity(List<ShipOrderInfo> shipOrderInfos);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
