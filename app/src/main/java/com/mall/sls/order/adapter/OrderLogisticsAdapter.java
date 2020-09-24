package com.mall.sls.order.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.data.entity.OrderPackageInfo;
import com.mall.sls.data.entity.ShipOrderInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderLogisticsAdapter extends RecyclerView.Adapter<OrderLogisticsAdapter.OrderLogisticsView> {
    private LayoutInflater layoutInflater;
    private List<OrderPackageInfo> orderPackageInfos;
    private Context context;

    public void setData(List<OrderPackageInfo> orderPackageInfos) {
        this.orderPackageInfos = orderPackageInfos;
        notifyDataSetChanged();
    }

    public OrderLogisticsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public OrderLogisticsView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_order_logistics, parent, false);
        return new OrderLogisticsView(view);
    }

    @Override
    public void onBindViewHolder(OrderLogisticsView holder, int position) {
        OrderPackageInfo orderPackageInfo = orderPackageInfos.get(holder.getAdapterPosition());
        holder.bindData(orderPackageInfo);
        if(onItemClickListener!=null) {
            holder.itemLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(orderPackageInfo.getShipOrderInfos()!=null&&orderPackageInfo.getShipOrderInfos().size()>0) {
                        onItemClickListener.goDeliveryInfoActivity(orderPackageInfo.getShipOrderInfos());
                    }
                }
            });
            holder.viewMapBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.goSfH5Url(orderPackageInfo.getSfH5Url());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return orderPackageInfos == null ? 0 : orderPackageInfos.size();
    }

    public class OrderLogisticsView extends RecyclerView.ViewHolder implements LogisticsGoodsAdapter.OnItemClickListener{
        @BindView(R.id.status)
        ConventionalTextView status;
        @BindView(R.id.shipSn)
        ConventionalTextView shipSn;
        @BindView(R.id.goods_rv)
        RecyclerView goodsRv;
        @BindView(R.id.total_number)
        ConventionalTextView totalNumber;
        @BindView(R.id.view_map_bt)
        ConventionalTextView viewMapBt;
        @BindView(R.id.item_ll)
        LinearLayout itemLl;
        @BindView(R.id.statusDesc)
        ConventionalTextView statusDesc;

        private LogisticsGoodsAdapter logisticsGoodsAdapter;

        public OrderLogisticsView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            logisticsGoodsAdapter=new LogisticsGoodsAdapter(context);
            logisticsGoodsAdapter.setOnItemClickListener(this);
            goodsRv.setAdapter(logisticsGoodsAdapter);
        }

        public void bindData(OrderPackageInfo orderPackageInfo) {
            status.setText(orderPackageInfo.getStatus());
            shipSn.setText(orderPackageInfo.getShipChannel() + "：" + orderPackageInfo.getShipSn());
            shipSn.setVisibility((TextUtils.isEmpty(orderPackageInfo.getShipChannel())||TextUtils.isEmpty(orderPackageInfo.getShipSn()))?View.INVISIBLE:View.VISIBLE);
            statusDesc.setText(orderPackageInfo.getStatusDesc());
            logisticsGoodsAdapter.setData(orderPackageInfo.getOrderGoodsVos(),orderPackageInfo.getShipOrderInfos());
            totalNumber.setText("共"+orderPackageInfo.getOrderGoodsVos().size()+"件商品");
            viewMapBt.setVisibility(TextUtils.equals(StaticData.REFRESH_ONE,orderPackageInfo.getShowMap())?View.VISIBLE:View.GONE);
        }

        @Override
        public void goDeliveryInfoActivity(List<ShipOrderInfo> shipOrderInfos) {
            if(onItemClickListener!=null){
                onItemClickListener.goDeliveryInfoActivity(shipOrderInfos);
            }
        }
    }


    public interface OnItemClickListener {
        void goDeliveryInfoActivity(List<ShipOrderInfo> shipOrderInfos);
        void goSfH5Url(String sfH5Url);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
