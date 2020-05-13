package com.mall.sls.member.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.DrawTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.GoodsItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberGoodsItemAdapter extends RecyclerView.Adapter<MemberGoodsItemAdapter.MemberGoodsItemView> {
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

    public MemberGoodsItemAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MemberGoodsItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_member_goods, parent, false);
        return new MemberGoodsItemView(view);
    }

    @Override
    public void onBindViewHolder(MemberGoodsItemView holder, int position) {
        GoodsItemInfo goodsItemInfo = goodsItemInfos.get(holder.getAdapterPosition());
        holder.bindData(goodsItemInfo);

    }

    @Override
    public int getItemCount() {
        return goodsItemInfos == null ? 0 : goodsItemInfos.size();
    }

    public class MemberGoodsItemView extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_iv)
        ImageView goodsIv;
        @BindView(R.id.goods_name)
        ConventionalTextView goodsName;
        @BindView(R.id.current_price)
        MediumThickTextView currentPrice;
        @BindView(R.id.original_price)
        DrawTextView originalPrice;

        public MemberGoodsItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(GoodsItemInfo goodsItemInfo) {
            GlideHelper.load((Activity) context, "", R.mipmap.ic_launcher, goodsIv);
            goodsName.setText(goodsItemInfo.getName());
            currentPrice.setText("¥" + goodsItemInfo.getRetailPrice());
            originalPrice.setText("¥" + goodsItemInfo.getRetailPrice());
        }
    }

    public interface OnItemClickListener {
        void goOrdinaryGoods();
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
