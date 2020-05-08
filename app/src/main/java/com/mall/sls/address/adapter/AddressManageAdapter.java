package com.mall.sls.address.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.RoundedBackgroundSpan;
import com.mall.sls.data.entity.AddressInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressManageAdapter extends RecyclerView.Adapter<AddressManageAdapter.AddressManageView> {
    private LayoutInflater layoutInflater;
    private List<AddressInfo> addressInfos;
    private Context context;

    public void setData(List<AddressInfo> addressInfos) {
        this.addressInfos = addressInfos;
        notifyDataSetChanged();
    }

    public AddressManageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public AddressManageView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_address_management, parent, false);
        return new AddressManageView(view);
    }

    @Override
    public void onBindViewHolder(AddressManageView holder, int position) {
        AddressInfo addressInfo = addressInfos.get(holder.getAdapterPosition());
        holder.bindData(addressInfo);
        holder.itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.select(holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return addressInfos == null ? 0 : addressInfos.size();
    }

    public class AddressManageView extends RecyclerView.ViewHolder {
        @BindView(R.id.edit)
        ConventionalTextView edit;
        @BindView(R.id.name)
        ConventionalTextView name;
        @BindView(R.id.phone)
        ConventionalTextView phone;
        @BindView(R.id.address)
        ConventionalTextView address;
        @BindView(R.id.item_rl)
        RelativeLayout itemRl;

        public AddressManageView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(AddressInfo addressInfo) {
            name.setText(addressInfo.getName());
            address.setText(addressInfo.getAddress());
            phone.setText(addressInfo.getPhone());
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
            if (TextUtils.equals("1", addressInfo.getStatus())) {
                String thisTag = " 默认 ";
                stringBuilder.append(thisTag);
                RoundedBackgroundSpan span = new RoundedBackgroundSpan(Color.parseColor("#FFD7DA"), Color.parseColor("#EF3342"));
                stringBuilder.setSpan(span, stringBuilder.length() - thisTag.length(), stringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                stringBuilder.append(" ");
            }
            stringBuilder.append(addressInfo.getAddress());
//            stringBuilder.append("      ");
//            String thissTag = " 家 ";
//            stringBuilder.append(thissTag);
//            RoundedBackgroundSpan span = new RoundedBackgroundSpan(Color.parseColor("#FFD7DA"), Color.parseColor("#EF3342"));
//            stringBuilder.setSpan(span, stringBuilder.length() - thissTag.length(), stringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ImageSpan imageSpan = new ImageSpan(context, R.mipmap.icon_house);
//            stringBuilder.setSpan(imageSpan, stringBuilder.length() - 1, stringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            address.setText(stringBuilder);
        }
    }

    public interface OnItemClickListener {
        void select(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
