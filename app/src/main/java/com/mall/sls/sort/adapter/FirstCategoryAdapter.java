package com.mall.sls.sort.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.mall.sls.R;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.data.entity.FirstCategoryInfo;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstCategoryAdapter extends RecyclerView.Adapter<FirstCategoryAdapter.FirstCategoryView> {
    private LayoutInflater layoutInflater;
    private List<FirstCategoryInfo> firstCategoryInfos;

    public void setData(List<FirstCategoryInfo> firstCategoryInfos) {
        this.firstCategoryInfos = firstCategoryInfos;
        notifyDataSetChanged();
    }


    @Override
    public FirstCategoryView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_first_category, parent, false);
        return new FirstCategoryView(view);
    }

    @Override
    public void onBindViewHolder(FirstCategoryView holder, int position) {
        FirstCategoryInfo firstCategoryInfo = firstCategoryInfos.get(holder.getAdapterPosition());
        holder.bindData(firstCategoryInfo);
        holder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.selectCategory(holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return firstCategoryInfos == null ? 0 : firstCategoryInfos.size();
    }

    public class FirstCategoryView extends RecyclerView.ViewHolder {
        @BindView(R.id.select_iv)
        View selectIv;
        @BindView(R.id.name)
        ConventionalTextView name;
        @BindView(R.id.item_ll)
        LinearLayout itemLl;

        public FirstCategoryView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(FirstCategoryInfo categoryInfo) {
            name.setText(categoryInfo.getName());
            selectIv.setVisibility(categoryInfo.getSelect() ? View.VISIBLE : View.INVISIBLE);
            itemLl.setSelected(categoryInfo.getSelect());
        }
    }

    public interface OnItemClickListener {
        void selectCategory(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
