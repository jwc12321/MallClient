package com.mall.sls.address.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.PoiItem;
import com.mall.sls.R;
import com.mall.sls.common.widget.textview.ConventionalTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAddressAdapter extends RecyclerView.Adapter<SearchAddressAdapter.SearchAddressView> {
    private LayoutInflater layoutInflater;
    private List<PoiItem> poiItems;

    public void setData(List<PoiItem> poiItems) {
        this.poiItems = poiItems;
        notifyDataSetChanged();
    }

    public void addMore(List<PoiItem> moreList) {
        int pos = poiItems.size();
        poiItems.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public SearchAddressView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_map_address, parent, false);
        return new SearchAddressView(view);
    }

    @Override
    public void onBindViewHolder(SearchAddressView holder, int position) {
        PoiItem poiItem = poiItems.get(holder.getAdapterPosition());
        holder.bindData(poiItem);
        holder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.selectSearch(poiItem);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return poiItems == null ? 0 : poiItems.size();
    }

    public class SearchAddressView extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.name)
        ConventionalTextView name;
        @BindView(R.id.address)
        ConventionalTextView address;
        @BindView(R.id.item_ll)
        RelativeLayout itemLl;

        public SearchAddressView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(PoiItem poiItem) {
            name.setText(poiItem.getTitle());
            address.setText(poiItem.getProvinceName()+poiItem.getCityName()+poiItem.getAdName()+poiItem.getSnippet());
        }
    }

    public interface OnItemClickListener {
        void selectSearch(PoiItem poiItem);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
