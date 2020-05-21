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
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.DrawTextView;
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
        //一期这个功能去掉
        holder.remindIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.remind(holder.remindIv,holder.getAdapterPosition());
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
        ConventionalTextView goodsName;
        @BindView(R.id.goods_name_rl)
        LinearLayout goodsNameRl;
        @BindView(R.id.goods_introduction)
        ConventionalTextView goodsIntroduction;
        @BindView(R.id.remind_iv)
        ImageView remindIv;
        @BindView(R.id.current_price)
        MediumThickTextView currentPrice;
        @BindView(R.id.original_price)
        DrawTextView originalPrice;
        @BindView(R.id.item_rl)
        RelativeLayout itemRl;

        public LootingSoonView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(GoodsItemInfo goodsItemInfo) {
            GlideHelper.load((Activity) context, goodsItemInfo.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
            goodsName.setText(goodsItemInfo.getName());
            goodsIntroduction.setText(goodsItemInfo.getBrief());
            currentPrice.setText("¥" + goodsItemInfo.getRetailPrice());
            originalPrice.setText("¥" + goodsItemInfo.getCounterPrice());
            remindIv.setEnabled(TextUtils.equals(StaticData.REFLASH_ONE,goodsItemInfo.getSubscriptionStatus())?false:true);
        }
    }

    public interface OnItemClickListener {
        void goActivityGoodsDetail(String goodsId);
        void remind(ImageView remindIv,int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
