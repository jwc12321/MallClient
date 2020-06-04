package com.mall.sls.mine.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.MineRewardInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeView> {
    private LayoutInflater layoutInflater;
    private List<MineRewardInfo> mineRewardInfos;
    private Context context;

    public void setData(List<MineRewardInfo> mineRewardInfos) {
        this.mineRewardInfos = mineRewardInfos;
        notifyDataSetChanged();
    }

    public IncomeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public IncomeView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_income, parent, false);
        return new IncomeView(view);
    }

    @Override
    public void onBindViewHolder(IncomeView holder, int position) {
        MineRewardInfo mineRewardInfo = mineRewardInfos.get(holder.getAdapterPosition());
        holder.bindData(mineRewardInfo);
        holder.view.setVisibility(holder.getAdapterPosition()==0?View.GONE:View.VISIBLE);
        holder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    if (TextUtils.equals(context.getString(R.string.coupon), mineRewardInfo.getDes())){
                        onItemClickListener.goCoupon();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mineRewardInfos == null ? 0 : mineRewardInfos.size();
    }

    public class IncomeView extends RecyclerView.ViewHolder {
        @BindView(R.id.view)
        View view;
        @BindView(R.id.number)
        MediumThickTextView number;
        @BindView(R.id.name)
        ConventionalTextView name;
        @BindView(R.id.item_ll)
        RelativeLayout itemLl;

        public IncomeView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(MineRewardInfo mineRewardInfo) {
            number.setText(mineRewardInfo.getValue());
            name.setText(mineRewardInfo.getDes());
        }
    }

    public interface OnItemClickListener {
        void goCoupon();//优惠卷
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
