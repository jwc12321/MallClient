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
import com.mall.sls.common.unit.FormatUtil;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.DrawTextView;
import com.mall.sls.common.widget.textview.GoodsTypeTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.GoodsItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LootingSoonAdapter extends RecyclerView.Adapter<LootingSoonAdapter.LootingSoonView> {
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

    public LootingSoonAdapter(Context context) {
        this.context = context;
    }

    @Override
    public LootingSoonView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_looting_soon, parent, false);
        return new LootingSoonView(view);
    }

    @Override
    public void onBindViewHolder(LootingSoonView holder, int position) {
        GoodsItemInfo goodsItemInfo = goodsItemInfos.get(holder.getAdapterPosition());
        holder.bindData(goodsItemInfo);
        holder.itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.goActivityGoodsDetail(goodsItemInfo.getGoodsId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return goodsItemInfos == null ? 0 : goodsItemInfos.size();
    }

    public class LootingSoonView extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_iv)
        ImageView goodsIv;
        @BindView(R.id.goods_name)
        GoodsTypeTextView goodsName;
        @BindView(R.id.goods_brief)
        ConventionalTextView goodsBrief;
        @BindView(R.id.yuan_symbol)
        ConventionalTextView yuanSymbol;
        @BindView(R.id.current_price)
        MediumThickTextView currentPrice;
        @BindView(R.id.original_price)
        DrawTextView originalPrice;
        @BindView(R.id.confirm_bt)
        ConventionalTextView confirmBt;
        @BindView(R.id.item_rl)
        RelativeLayout itemRl;

        public LootingSoonView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(GoodsItemInfo goodsItemInfo) {
            GlideHelper.load((Activity) context, goodsItemInfo.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
            goodsBrief.setText(goodsItemInfo.getBrief());
            yuanSymbol.setText(StaticData.YUAN_SYMBOL);
            currentPrice.setText(NumberFormatUnit.numberFormat(goodsItemInfo.getRetailPrice()));
            originalPrice.setText(NumberFormatUnit.goodsFormat(goodsItemInfo.getCounterPrice()));
            long startTime = FormatUtil.dateToStamp(goodsItemInfo.getStartTime());
            confirmBt.setText(FormatUtil.formatDate(String.valueOf(startTime)) + "开抢");
            if(!TextUtils.isEmpty(goodsItemInfo.getKeywords())){
                goodsName.setSingleTagAndContent(goodsItemInfo.getKeywords(),goodsItemInfo.getName());
            }else {
                goodsName.setText(goodsItemInfo.getName());
            }
        }
    }

    public interface OnItemClickListener {
        void goActivityGoodsDetail(String goodsId);

        void remind(ImageView remindIv, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
