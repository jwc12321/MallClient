package com.mall.sls.sort.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.SecondCategoryInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondCategoryAdapter extends RecyclerView.Adapter<SecondCategoryAdapter.SecondCategoryView> {
    private LayoutInflater layoutInflater;
    private List<SecondCategoryInfo> secondCategoryInfos;
    private Context context;


    public SecondCategoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<SecondCategoryInfo> secondCategoryInfos) {
        this.secondCategoryInfos = secondCategoryInfos;
        notifyDataSetChanged();
    }


    @Override
    public SecondCategoryView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_second_category, parent, false);
        return new SecondCategoryView(view);
    }

    @Override
    public void onBindViewHolder(SecondCategoryView holder, int position) {
        SecondCategoryInfo secondCategoryInfo = secondCategoryInfos.get(holder.getAdapterPosition());
        holder.bindData(secondCategoryInfo);
        holder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.goCategoryGoods(secondCategoryInfo.getName(),secondCategoryInfo.getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return secondCategoryInfos == null ? 0 : secondCategoryInfos.size();
    }

    public class SecondCategoryView extends RecyclerView.ViewHolder implements CategoryGoodsItemAdapter.OnItemClickListener{
        @BindView(R.id.name)
        MediumThickTextView name;
        @BindView(R.id.goods_rv)
        RecyclerView goodsRv;
        @BindView(R.id.item_ll)
        LinearLayout itemLl;

        private CategoryGoodsItemAdapter categoryGoodsItemAdapter;

        public SecondCategoryView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            categoryGoodsItemAdapter=new CategoryGoodsItemAdapter(context);
            categoryGoodsItemAdapter.setOnItemClickListener(this);
            goodsRv.setLayoutManager(new GridLayoutManager(context, 3));
            goodsRv.setAdapter(categoryGoodsItemAdapter);

        }

        public void bindData(SecondCategoryInfo secondCategoryInfo) {
            name.setText(secondCategoryInfo.getName());
            categoryGoodsItemAdapter.setData(secondCategoryInfo.getGoodsItemInfos());
        }

        @Override
        public void goGoodsDetails(String goodsType, String goodsId) {
            if(onItemClickListener!=null){
                onItemClickListener.goGoodsDetails(goodsType,goodsId);
            }

        }
    }

    public interface OnItemClickListener {
        void goCategoryGoods(String categoryName,String categoryId);
        void goGoodsDetails(String goodsType,String goodsId);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
