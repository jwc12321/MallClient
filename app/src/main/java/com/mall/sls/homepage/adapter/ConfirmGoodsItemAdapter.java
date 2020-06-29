package com.mall.sls.homepage.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
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
import com.mall.sls.data.entity.CartItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmGoodsItemAdapter extends RecyclerView.Adapter<ConfirmGoodsItemAdapter.ConfirmGoodsItemView> {
    private LayoutInflater layoutInflater;
    private List<CartItemInfo> cartItemInfos;
    private Context context;
    private SpannableString spanText;

    public void setData(List<CartItemInfo> cartItemInfos) {
        this.cartItemInfos = cartItemInfos;
        notifyDataSetChanged();
    }

    public ConfirmGoodsItemAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ConfirmGoodsItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_confirm_goods_item, parent, false);
        return new ConfirmGoodsItemView(view);
    }

    @Override
    public void onBindViewHolder(ConfirmGoodsItemView holder, int position) {
        CartItemInfo cartItemInfo = cartItemInfos.get(holder.getAdapterPosition());
        holder.bindData(cartItemInfo);

    }

    @Override
    public int getItemCount() {
        return cartItemInfos == null ? 0 : cartItemInfos.size();
    }

    public class ConfirmGoodsItemView extends RecyclerView.ViewHolder {
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
        @BindView(R.id.hide_rl)
        RelativeLayout hideRl;

        public ConfirmGoodsItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CartItemInfo cartItemInfo) {
            GlideHelper.load((Activity) context, cartItemInfo.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
            goodsName.setText(cartItemInfo.getGoodsName());
            sku.setText(cartItemInfo.getSpecifications());
            goodsPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(cartItemInfo.getPrice()));
            goodsNumber.setText("x"+cartItemInfo.getNumber());
            hideRl.setVisibility(cartItemInfo.getCanBuy()?View.GONE:View.VISIBLE);
        }
    }

}
