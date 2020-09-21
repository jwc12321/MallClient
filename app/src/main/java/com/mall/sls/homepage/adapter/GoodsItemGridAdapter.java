package com.mall.sls.homepage.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
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
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.DrawTextView;
import com.mall.sls.data.entity.GoodsItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsItemGridAdapter extends RecyclerView.Adapter<GoodsItemGridAdapter.GoodsItemGridView> {

    private LayoutInflater layoutInflater;
    private List<GoodsItemInfo> goodsItemInfos;
    private Context context;

    public void setData(List<GoodsItemInfo> goodsItemInfos) {
        this.goodsItemInfos = goodsItemInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<GoodsItemInfo> moreList) {
        int pos = goodsItemInfos.size();
        goodsItemInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    public GoodsItemGridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public GoodsItemGridView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_goods_item_grid, parent, false);
        return new GoodsItemGridView(view);
    }

    @Override
    public void onBindViewHolder(GoodsItemGridView holder, int position) {
        GoodsItemInfo goodsItemInfo = goodsItemInfos.get(holder.getAdapterPosition());
        holder.bindData(goodsItemInfo);
        holder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    if (TextUtils.equals(StaticData.REFLASH_ONE, goodsItemInfo.getGoodsType())) {
                        onItemClickListener.goGeneralGoodsDetails(goodsItemInfo.getGoodsId());
                    } else if (TextUtils.equals(StaticData.REFLASH_TWO, goodsItemInfo.getGoodsType())) {
                        onItemClickListener.goOrdinaryGoodsDetails(goodsItemInfo.getGoodsId());
                    } else {
                        onItemClickListener.goActivityGroupGoods(goodsItemInfo.getGoodsId());
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return goodsItemInfos == null ? 0 : goodsItemInfos.size();
    }

    public class GoodsItemGridView extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_iv)
        RoundedImageView goodsIv;
        @BindView(R.id.group_type)
        ConventionalTextView groupType;
        @BindView(R.id.goods_name)
        ConventionalTextView goodsName;
        @BindView(R.id.goods_name_rl)
        LinearLayout goodsNameRl;
        @BindView(R.id.goods_subtitle_name)
        ConventionalTextView goodsSubtitleName;
        @BindView(R.id.current_price)
        ConventionalTextView currentPrice;
        @BindView(R.id.original_price)
        DrawTextView originalPrice;
        @BindView(R.id.item_ll)
        LinearLayout itemLl;
        @BindView(R.id.yuan_symbol)
        ConventionalTextView yuanSymbol;

        public GoodsItemGridView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(GoodsItemInfo goodsItemInfo) {
            GlideHelper.load((Activity) context, goodsItemInfo.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
            goodsName.setText(goodsItemInfo.getName());
            goodsSubtitleName.setText(goodsItemInfo.getSubtitleName());
            yuanSymbol.setText(StaticData.YUAN_SYMBOL);
            currentPrice.setText(NumberFormatUnit.numberFormat(goodsItemInfo.getRetailPrice()));
            originalPrice.setText(NumberFormatUnit.goodsFormat(goodsItemInfo.getCounterPrice()));
            groupType.setText(goodsItemInfo.getKeywords());
            groupType.setVisibility(TextUtils.equals(StaticData.REFLASH_ONE, goodsItemInfo.getGoodsType()) ? View.GONE : View.VISIBLE);
        }
    }

    public interface OnItemClickListener {
        void goOrdinaryGoodsDetails(String goodsId);

        void goActivityGroupGoods(String goodsId);

        void goGeneralGoodsDetails(String goodsId);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
