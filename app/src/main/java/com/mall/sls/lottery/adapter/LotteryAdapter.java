package com.mall.sls.lottery.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.FormatUtil;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.LotteryTearDownView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.PrizeVo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LotteryAdapter extends RecyclerView.Adapter<LotteryAdapter.LotteryView> {
    private LayoutInflater layoutInflater;
    private List<PrizeVo> prizeVos;
    private Context context;
    private long nowTime;

    public void setData(List<PrizeVo> prizeVos,long nowTime) {
        this.nowTime=nowTime;
        this.prizeVos = prizeVos;
        notifyDataSetChanged();
    }

    public void addMore(List<PrizeVo> moreList,long nowTime) {
        this.nowTime=nowTime;
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
        @BindView(R.id.participantNumber)
        MediumThickTextView participantNumber;
        @BindView(R.id.count_down)
        LotteryTearDownView countDown;
        @BindView(R.id.goods_iv)
        ImageView goodsIv;
        @BindView(R.id.goods_name)
        ConventionalTextView goodsName;
        @BindView(R.id.endTime)
        ConventionalTextView endTime;
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
            participantNumber.setText(prizeVo.getParticipantNumber());
            GlideHelper.load((Activity) context, prizeVo.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
            goodsName.setText(prizeVo.getPrizeTitle());
            endTime.setText(prizeVo.getEndTime()+"开奖");
            goodsPrice.setText(NumberFormatUnit.twoDecimalFormat(prizeVo.getGoodsPrice()));
            if(TextUtils.equals(StaticData.REFLASH_ONE,prizeVo.getPrizeStatus())){
                confirmBt.setSelected(true);
                if(TextUtils.equals(StaticData.REFLASH_ZERO,prizeVo.getCounterPrice())||TextUtils.equals("0.00",prizeVo.getCounterPrice())){
                    confirmBt.setText(context.getString(R.string.participate_draw));
                }else {
                    confirmBt.setText(NumberFormatUnit.twoDecimalFormat(prizeVo.getCounterPrice())+context.getString(R.string.participate_draw));
                }
            }else {
                confirmBt.setSelected(false);
                confirmBt.setText(context.getString(R.string.waiting_draw));
            }
            long groupExpireTime = FormatUtil.dateToStamp(prizeVo.getEndTime());
            if(groupExpireTime<nowTime){
                countDown.setVisibility(View.GONE);
            }else {
                countDown.setVisibility(View.VISIBLE);
                countDown.startTearDown(groupExpireTime/1000, nowTime/1000);
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
