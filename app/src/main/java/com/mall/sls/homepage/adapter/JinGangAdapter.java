package com.mall.sls.homepage.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.data.entity.BannerInfo;
import com.mall.sls.data.entity.JinGangInfo;
import com.mall.sls.data.entity.VipAmountInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JinGangAdapter extends RecyclerView.Adapter<JinGangAdapter.JinGangView> {
    private LayoutInflater layoutInflater;
    private List<BannerInfo> jinGangInfos;
    private Context context;

    public void setData(List<BannerInfo> jinGangInfos) {
        this.jinGangInfos = jinGangInfos;
        notifyDataSetChanged();
    }


    public JinGangAdapter(Context context) {
        this.context = context;
    }

    @Override
    public JinGangView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_jin_gang, parent, false);
        return new JinGangView(view);
    }

    @Override
    public void onBindViewHolder(JinGangView holder, int position) {
        BannerInfo bannerInfo = jinGangInfos.get(holder.getAdapterPosition());
        holder.bindData(bannerInfo);
        holder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.goType(bannerInfo);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return jinGangInfos == null ? 0 : jinGangInfos.size();
    }

    public class JinGangView extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.name)
        ConventionalTextView name;
        @BindView(R.id.item_ll)
        LinearLayout itemLl;

        public JinGangView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(BannerInfo bannerInfo) {
            GlideHelper.load((Activity) context, bannerInfo.getUrl(), R.mipmap.icon_default_goods, icon);
            name.setText(bannerInfo.getName());
        }
    }

    public interface OnItemClickListener {
        void goType(BannerInfo bannerInfo);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
