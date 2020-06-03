package com.mall.sls.order.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.GoodsOrderInfo;
import com.mall.sls.data.entity.OrderGoodsVo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsOrderAdapter extends RecyclerView.Adapter<GoodsOrderAdapter.GoodsOrderView> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<GoodsOrderInfo> goodsOrderInfos;

    public GoodsOrderAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GoodsOrderInfo> goodsOrderInfos) {
        this.goodsOrderInfos = goodsOrderInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<GoodsOrderInfo> moreList) {
        int pos = goodsOrderInfos.size();
        goodsOrderInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public GoodsOrderView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_goods_order, parent, false);
        return new GoodsOrderView(view);
    }

    @Override
    public void onBindViewHolder(GoodsOrderView holder, int position) {
        GoodsOrderInfo goodsOrderInfo = goodsOrderInfos.get(holder.getAdapterPosition());
        holder.bindData(goodsOrderInfo);
        holder.rightBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    if (TextUtils.equals(StaticData.TO_PAY, goodsOrderInfo.getOrderStatus())) {
                        onItemClickListener.payOrder(goodsOrderInfo.getId(), goodsOrderInfo.getActualPrice());
                    }else {
                        onItemClickListener.wxShare(goodsOrderInfo ,holder.goodsIv);
                    }
                }
            }
        });
        holder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.goOrderDetail(goodsOrderInfo.getId());
                }
            }
        });
        holder.leftBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null && TextUtils.equals(StaticData.TO_PAY, goodsOrderInfo.getOrderStatus())) {
                    onItemClickListener.cancelOrder(goodsOrderInfo.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsOrderInfos == null ? 0 : goodsOrderInfos.size();
    }

    public class GoodsOrderView extends RecyclerView.ViewHolder {
        @BindView(R.id.time)
        ConventionalTextView time;
        @BindView(R.id.order_status)
        MediumThickTextView orderStatus;
        @BindView(R.id.goods_iv)
        ImageView goodsIv;
        @BindView(R.id.goods_name)
        MediumThickTextView goodsName;
        @BindView(R.id.sku)
        ConventionalTextView sku;
        @BindView(R.id.goods_price)
        ConventionalTextView goodsPrice;
        @BindView(R.id.goods_number)
        ConventionalTextView goodsNumber;
        @BindView(R.id.total_number)
        ConventionalTextView totalNumber;
        @BindView(R.id.is_pay)
        ConventionalTextView isPay;
        @BindView(R.id.total_amount)
        ConventionalTextView totalAmount;
        @BindView(R.id.left_bt)
        ConventionalTextView leftBt;
        @BindView(R.id.right_bt)
        ConventionalTextView rightBt;
        @BindView(R.id.bt_ll)
        LinearLayout btLl;
        @BindView(R.id.item_ll)
        LinearLayout itemLl;

        private List<OrderGoodsVo> orderGoodsVos;

        public GoodsOrderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(GoodsOrderInfo goodsOrderInfo) {
            time.setText(goodsOrderInfo.getAddTime());
            orderGoodsVos = goodsOrderInfo.getOrderGoodsVos();
            if (orderGoodsVos != null && orderGoodsVos.size() > 0) {
                GlideHelper.load((Activity) context, orderGoodsVos.get(0).getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
                goodsName.setText(orderGoodsVos.get(0).getGoodsName());
                goodsPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(orderGoodsVos.get(0).getPrice()));
                goodsNumber.setText("x" + orderGoodsVos.get(0).getNumber());
                sku.setText(orderGoodsVos.get(0).getSpecifications());
            }
            totalAmount.setText("¥" + NumberFormatUnit.twoDecimalFormat(goodsOrderInfo.getActualPrice()));
            setOrderStatus(goodsOrderInfo.getOrderStatus());
            if (TextUtils.equals(StaticData.TO_PAY, goodsOrderInfo.getOrderStatus())) {
                isPay.setText(context.getString(R.string.to_be_paid));
            } else {
                isPay.setText(context.getString(R.string.actually_apaid));
            }
        }

        //状态 101-待支付 102 -取消 103-系统自动取消 204-待分享 206-待发货 301-待收获 401-完成 402-完成(系统)
        private void setOrderStatus(String status) {
            switch (status) {
                case StaticData.TO_PAY:
                    orderStatus.setText(context.getString(R.string.pending_payment));
                    btLl.setVisibility(View.VISIBLE);
                    leftBt.setVisibility(View.VISIBLE);
                    rightBt.setVisibility(View.VISIBLE);
                    rightBt.setText(context.getString(R.string.to_pay));
                    leftBt.setText(context.getString(R.string.cancel_order));
                    break;
                case StaticData.TO_BE_SHARE:
                    orderStatus.setText(context.getString(R.string.pending_share));
                    btLl.setVisibility(View.VISIBLE);
                    leftBt.setVisibility(View.GONE);
                    rightBt.setVisibility(View.VISIBLE);
                    rightBt.setText(context.getString(R.string.invite_friends));
                    break;
                case StaticData.TO_BE_DELIVERED:
                    orderStatus.setText(context.getString(R.string.pending_delivery));
                    btLl.setVisibility(View.GONE);
                    break;
                case StaticData.TO_BE_RECEIVED:
                    orderStatus.setText(context.getString(R.string.shipping));
                    btLl.setVisibility(View.GONE);
                    break;
                case StaticData.RECEIVED:
                case StaticData.SYS_RECEIVED:
                    orderStatus.setText(context.getString(R.string.completed));
                    btLl.setVisibility(View.GONE);
                    break;
                case StaticData.CANCELLED:
                case StaticData.SYS_CANCELLED:
                    orderStatus.setText(context.getString(R.string.is_cancel));
                    btLl.setVisibility(View.GONE);
                    break;
                default:
            }
        }

    }


    public interface OnItemClickListener {
        void cancelOrder(String id);//取消订单

        void payOrder(String id, String amount);//支付订单

        void confirmOrder(String id);//确认收货

        void goOrderDetail(String id);//去订单详情

        void wxShare(GoodsOrderInfo goodsOrderInfo,ImageView shareIv);

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
