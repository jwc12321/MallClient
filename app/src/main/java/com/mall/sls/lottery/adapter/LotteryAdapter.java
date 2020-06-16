package com.mall.sls.lottery.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.mall.sls.data.entity.PrizeVo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LotteryAdapter extends RecyclerView.Adapter<LotteryAdapter.LotteryView> {
    private LayoutInflater layoutInflater;
    private List<PrizeVo> prizeVos;
    private Context context;

    public void setData(List<PrizeVo> prizeVos) {
        this.prizeVos = prizeVos;
        notifyDataSetChanged();
    }

    public void addMore(List<PrizeVo> moreList) {
        int pos = prizeVos.size();
        prizeVos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }


    public LotteryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public LotteryView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_lottery, parent, false);
        return new LotteryView(view);
    }

    @Override
    public void onBindViewHolder(LotteryView holder, int position) {
        PrizeVo prizeVo = prizeVos.get(holder.getAdapterPosition());
        holder.bindData(prizeVo);
        holder.itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.goLotteryDetails(prizeVo);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return prizeVos == null ? 0 : prizeVos.size();
    }

    public class LotteryView extends RecyclerView.ViewHolder {
        @BindView(R.id.prizeId)
        ConventionalTextView prizeId;
        @BindView(R.id.prizeTime)
        ConventionalTextView prizeTime;
        @BindView(R.id.goods_iv)
        ImageView goodsIv;
        @BindView(R.id.goods_name)
        ConventionalTextView goodsName;
        @BindView(R.id.goods_price)
        MediumThickTextView goodsPrice;
        @BindView(R.id.confirm_bt)
        ConventionalTextView confirmBt;
        @BindView(R.id.item_rl)
        RelativeLayout itemRl;

        public LotteryView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(PrizeVo prizeVo) {
            GlideHelper.load((Activity) context, prizeVo.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
            goodsName.setText(prizeVo.getPrizeTitle());
            prizeTime.setText(prizeVo.getPrizeTime() + " 开奖");
            goodsPrice.setText(NumberFormatUnit.twoDecimalFormat(prizeVo.getCounterPrice()));
            prizeId.setText("第"+prizeVo.getPrizeId()+"期");
            if (TextUtils.equals(StaticData.REFLASH_ONE, prizeVo.getPrizeStatus())) {
                confirmBt.setSelected(true);
                if (TextUtils.equals(StaticData.REFLASH_ZERO, prizeVo.getPrice()) || TextUtils.equals("0.00", prizeVo.getPrice())) {
                    confirmBt.setText("0" + context.getString(R.string.yuan_draw));
                } else {
                    confirmBt.setText(NumberFormatUnit.twoDecimalFormat(prizeVo.getPrice()) + context.getString(R.string.yuan_draw));
                }
            } else {
                confirmBt.setSelected(false);
                confirmBt.setText(context.getString(R.string.waiting_draw));
            }
        }
    }

    public interface OnItemClickListener {
        void goLotteryDetails(PrizeVo prizeVo);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
