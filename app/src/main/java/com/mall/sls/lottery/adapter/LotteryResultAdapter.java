package com.mall.sls.lottery.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.mall.sls.R;
import com.mall.sls.common.widget.textview.MediumThickTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LotteryResultAdapter extends RecyclerView.Adapter<LotteryResultAdapter.LotteryResultView> {
    private LayoutInflater layoutInflater;
    private List<String> results;

    public void setData(List<String> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @Override
    public LotteryResultView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_lottery_result, parent, false);
        return new LotteryResultView(view);
    }

    @Override
    public void onBindViewHolder(LotteryResultView holder, int position) {
        String result = results.get(holder.getAdapterPosition());
        holder.bindData(result);

    }

    @Override
    public int getItemCount() {
        return results == null ? 0 : results.size();
    }

    public class LotteryResultView extends RecyclerView.ViewHolder {
        @BindView(R.id.number)
        MediumThickTextView number;
        @BindView(R.id.item_ll)
        RelativeLayout itemLl;;

        public LotteryResultView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(String result) {
            number.setText(result);
        }
    }


}
