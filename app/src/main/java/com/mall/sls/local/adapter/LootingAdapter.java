package com.mall.sls.local.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.GoodsItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LootingAdapter extends RecyclerView.Adapter<LootingAdapter.LootingView> {
    private LayoutInflater layoutInflater;
    private List<GoodsItemInfo> goodsItemInfos;
    private Context context;
    private SpannableString spanText;

    public void setData(List<GoodsItemInfo> goodsItemInfos) {
        this.goodsItemInfos = goodsItemInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<GoodsItemInfo> moreList) {
        int pos = goodsItemInfos.size();
        goodsItemInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    public LootingAdapter(Context context) {
        this.context = context;
    }

    @Override
    public LootingView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_looting, parent, false);
        return new LootingView(view);
    }

    @Override
    public void onBindViewHolder(LootingView holder, int position) {
        GoodsItemInfo goodsItemInfo = goodsItemInfos.get(holder.getAdapterPosition());
        holder.bindData(goodsItemInfo);
        holder.itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.goGoodsDetails(goodsItemInfo.getGoodsType(),goodsItemInfo.getGoodsId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return goodsItemInfos == null ? 0 : goodsItemInfos.size();
    }

    public class LootingView extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_iv)
        ImageView goodsIv;
        @BindView(R.id.groupType)
        ConventionalTextView groupType;
        @BindView(R.id.goods_name)
        ConventionalTextView goodsName;
        @BindView(R.id.goods_name_rl)
        LinearLayout goodsNameRl;
        @BindView(R.id.goods_introduction)
        ConventionalTextView goodsIntroduction;
        @BindView(R.id.price_type)
        ConventionalTextView priceType;
        @BindView(R.id.current_price)
        MediumThickTextView currentPrice;
        @BindView(R.id.current_price_rl)
        LinearLayout currentPriceRl;
        @BindView(R.id.original_price)
        MediumThickTextView originalPrice;
        @BindView(R.id.confirm_bt)
        ConventionalTextView confirmBt;
        @BindView(R.id.item_rl)
        RelativeLayout itemRl;

        public LootingView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(GoodsItemInfo goodsItemInfo) {
            GlideHelper.load((Activity) context, goodsItemInfo.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
            goodsName.setText(goodsItemInfo.getName());
            goodsIntroduction.setText(goodsItemInfo.getBrief());
            currentPrice.setText(NumberFormatUnit.numberFormat(goodsItemInfo.getRetailPrice()));
            originalPrice.setText(NumberFormatUnit.numberFormat(goodsItemInfo.getCounterPrice()));
            groupType.setVisibility(TextUtils.equals(StaticData.REFRESH_ONE, goodsItemInfo.getGroupType()) ? View.VISIBLE : View.GONE);
            groupType.setText(goodsItemInfo.getKeywords());
            if (TextUtils.equals(StaticData.REFRESH_ONE, goodsItemInfo.getGoodsType())) {
                groupType.setVisibility(View.GONE);
                priceType.setText(context.getString(R.string.selling_price));
                confirmBt.setText(context.getString(R.string.buy_now));
            } else if (TextUtils.equals(StaticData.REFRESH_TWO, goodsItemInfo.getGoodsType())) {
                groupType.setVisibility(View.VISIBLE);
                priceType.setText(context.getString(R.string.group_purchase_price_tv));
                confirmBt.setText(context.getString(R.string.go_to_spell));
            } else {
                groupType.setVisibility(View.VISIBLE);
                priceType.setText(context.getString(R.string.activity_price));
                confirmBt.setText(context.getString(R.string.go_buy));
            }
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
