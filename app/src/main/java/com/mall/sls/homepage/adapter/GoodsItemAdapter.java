package com.mall.sls.homepage.adapter;

import android.app.Activity;
import android.content.Context;
import android.opengl.Visibility;
import android.text.SpannableString;
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
import com.mall.sls.common.unit.VerifyManager;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.DrawTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.GoodsItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsItemAdapter extends RecyclerView.Adapter<GoodsItemAdapter.GoodsItemView> {
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

    public GoodsItemAdapter(Context context) {
        this.context = context;
    }

    @Override
    public GoodsItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_goods_item, parent, false);
        return new GoodsItemView(view);
    }

    @Override
    public void onBindViewHolder(GoodsItemView holder, int position) {
        GoodsItemInfo goodsItemInfo = goodsItemInfos.get(holder.getAdapterPosition());
        holder.bindData(goodsItemInfo);
        holder.confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.goOrdinaryGoodsDetails(goodsItemInfo.getGoodsId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return goodsItemInfos == null ? 0 : goodsItemInfos.size();
    }

    public class GoodsItemView extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_iv)
        ImageView goodsIv;
        @BindView(R.id.goods_name)
        ConventionalTextView goodsName;
        @BindView(R.id.goods_name_rl)
        LinearLayout goodsNameRl;
        @BindView(R.id.goods_introduction)
        ConventionalTextView goodsIntroduction;
        @BindView(R.id.confirm_bt)
        ConventionalTextView confirmBt;
        @BindView(R.id.current_price)
        MediumThickTextView currentPrice;
        @BindView(R.id.original_price)
        DrawTextView originalPrice;
        @BindView(R.id.groupType)
        ConventionalTextView groupType;

        public GoodsItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(GoodsItemInfo goodsItemInfo) {
            GlideHelper.load((Activity) context, goodsItemInfo.getPicUrl(), R.mipmap.ic_launcher, goodsIv);
            goodsName.setText(goodsItemInfo.getName());
            goodsIntroduction.setText(goodsItemInfo.getBrief());
            currentPrice.setText("¥" + goodsItemInfo.getRetailPrice());
            originalPrice.setText("¥" + goodsItemInfo.getCounterPrice());
            groupType.setVisibility(TextUtils.equals(StaticData.REFLASH_ONE,goodsItemInfo.getGroupType())?View.VISIBLE:View.GONE );
        }
    }

    public interface OnItemClickListener {
        void goOrdinaryGoodsDetails(String goodsId);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
