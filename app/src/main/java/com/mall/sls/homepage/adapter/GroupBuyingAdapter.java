package com.mall.sls.homepage.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.ConvertDpAndPx;
import com.mall.sls.common.unit.MarginsUnit;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.DrawTextView;
import com.mall.sls.data.entity.GoodsItemInfo;
import com.mall.sls.data.entity.HomeSnapUpInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupBuyingAdapter extends RecyclerView.Adapter<GroupBuyingAdapter.GroupBuyingView> {
    private LayoutInflater layoutInflater;
    private List<GoodsItemInfo> goodsItemInfos;
    private Context context;

    public void setData(List<GoodsItemInfo> goodsItemInfos) {
        this.goodsItemInfos = goodsItemInfos;
        notifyDataSetChanged();
    }

    public GroupBuyingAdapter(Context context) {
        this.context = context;
    }

    @Override
    public GroupBuyingView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_group_buying, parent, false);
        return new GroupBuyingView(view);
    }

    @Override
    public void onBindViewHolder(GroupBuyingView holder, int position) {
        GoodsItemInfo goodsItemInfo = goodsItemInfos.get(holder.getAdapterPosition());
        holder.bindData(goodsItemInfo);
        holder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.goGoodsDetails(goodsItemInfo.getGoodsType(),goodsItemInfo.getGoodsId());
                }
            }
        });
        if (position == 0) {
            MarginsUnit.setMargins(holder.itemLl, ConvertDpAndPx.Dp2Px(context, 15), 0, ConvertDpAndPx.Dp2Px(context, 5), 0);
        } else if (position == goodsItemInfos.size() - 1) {
            MarginsUnit.setMargins(holder.itemLl, ConvertDpAndPx.Dp2Px(context, 5), 0, ConvertDpAndPx.Dp2Px(context, 15), 0);
        } else {
            MarginsUnit.setMargins(holder.itemLl, ConvertDpAndPx.Dp2Px(context, 5), 0, ConvertDpAndPx.Dp2Px(context, 5), 0);
        }

    }

    @Override
    public int getItemCount() {
        return goodsItemInfos == null ? 0 : goodsItemInfos.size();
    }

    public class GroupBuyingView extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_iv)
        RoundedImageView goodsIv;
        @BindView(R.id.goods_name)
        ConventionalTextView goodsName;
        @BindView(R.id.current_price)
        ConventionalTextView currentPrice;
        @BindView(R.id.original_price)
        DrawTextView originalPrice;
        @BindView(R.id.item_ll)
        LinearLayout itemLl;
        @BindView(R.id.yuan_symbol)
        ConventionalTextView yuanSymbol;

        public GroupBuyingView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(GoodsItemInfo goodsItemInfo) {
            GlideHelper.load((Activity) context, goodsItemInfo.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
            goodsName.setText(goodsItemInfo.getName());
            yuanSymbol.setText(StaticData.YUAN_SYMBOL);
            currentPrice.setText(NumberFormatUnit.numberFormat(goodsItemInfo.getRetailPrice()));
            originalPrice.setText(NumberFormatUnit.goodsFormat(goodsItemInfo.getCounterPrice()));
        }
    }

    public interface OnItemClickListener {
        void goGoodsDetails(String goodsType,String goodsId);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
