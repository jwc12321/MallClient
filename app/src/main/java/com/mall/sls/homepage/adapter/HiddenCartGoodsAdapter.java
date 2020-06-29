package com.mall.sls.homepage.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.data.entity.HiddenItemCartInfo;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HiddenCartGoodsAdapter extends RecyclerView.Adapter<HiddenCartGoodsAdapter.HiddenCartGoodsView> {
    private LayoutInflater layoutInflater;
    private List<HiddenItemCartInfo> hiddenItemCartInfos;
    private Context context;

    public void setData(List<HiddenItemCartInfo> hiddenItemCartInfos) {
        this.hiddenItemCartInfos = hiddenItemCartInfos;
        notifyDataSetChanged();
    }


    public HiddenCartGoodsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public HiddenCartGoodsView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_hidden_cart_goods, parent, false);
        return new HiddenCartGoodsView(view);
    }

    @Override
    public void onBindViewHolder(HiddenCartGoodsView holder, int position) {
        HiddenItemCartInfo hiddenItemCartInfo = hiddenItemCartInfos.get(holder.getAdapterPosition());
        holder.bindData(hiddenItemCartInfo);

    }

    @Override
    public int getItemCount() {
        return hiddenItemCartInfos == null ? 0 : hiddenItemCartInfos.size();
    }

    public class HiddenCartGoodsView extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_iv)
        RoundedImageView goodsIv;

        public HiddenCartGoodsView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(HiddenItemCartInfo hiddenItemCartInfo) {
            GlideHelper.load((Activity) context, hiddenItemCartInfo.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
        }
    }

}
