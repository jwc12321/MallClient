package com.mall.sls.order.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.OrderGoodsVo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderGoodsItemAdapter extends RecyclerView.Adapter<OrderGoodsItemAdapter.OrderGoodsItemView> {
    private LayoutInflater layoutInflater;
    private List<OrderGoodsVo> orderGoodsVos;
    private Context context;
    private String orderId;

    public void setData(List<OrderGoodsVo> orderGoodsVos,String orderId) {
        this.orderGoodsVos = orderGoodsVos;
        this.orderId=orderId;
        notifyDataSetChanged();
    }

    public OrderGoodsItemAdapter(Context context) {
        this.context = context;
    }

    @Override
    public OrderGoodsItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_order_goods_item, parent, false);
        return new OrderGoodsItemView(view);
    }

    @Override
    public void onBindViewHolder(OrderGoodsItemView holder, int position) {
        OrderGoodsVo orderGoodsVo = orderGoodsVos.get(holder.getAdapterPosition());
        holder.bindData(orderGoodsVo);
        holder.itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.goOrderDetail(orderId);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderGoodsVos == null ? 0 : orderGoodsVos.size();
    }

    public class OrderGoodsItemView extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_iv)
        ImageView goodsIv;
        @BindView(R.id.goods_name)
        MediumThickTextView goodsName;
        @BindView(R.id.sku)
        ConventionalTextView sku;
        @BindView(R.id.goods_number)
        ConventionalTextView goodsNumber;
        @BindView(R.id.goods_price)
        ConventionalTextView goodsPrice;
        @BindView(R.id.item_rl)
        RelativeLayout itemRl;

        public OrderGoodsItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(OrderGoodsVo orderGoodsVo) {
            GlideHelper.load((Activity) context, orderGoodsVo.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
            goodsName.setText(orderGoodsVo.getGoodsName());
            sku.setText(orderGoodsVo.getSpecifications());
            goodsPrice.setText(NumberFormatUnit.goodsFormat(orderGoodsVo.getPrice()));
            goodsNumber.setText("x" + orderGoodsVo.getNumber());
        }
    }


    public interface OnItemClickListener {
        void goOrderDetail(String id);//去订单详情

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
