package com.mall.sls.mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.data.entity.InviteInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.InviteView> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<InviteInfo> inviteInfos;

    public InviteAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<InviteInfo> inviteInfos) {
        this.inviteInfos = inviteInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<InviteInfo> moreList) {
        int pos = inviteInfos.size();
        inviteInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public InviteView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_my_invitation, parent, false);
        return new InviteView(view);
    }

    @Override
    public void onBindViewHolder(InviteView holder, int position) {
        InviteInfo inviteInfo = inviteInfos.get(holder.getAdapterPosition());
        holder.bindData(inviteInfo);
        holder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.upDownView(holder.downIv, holder.recordRv, holder.line,holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return inviteInfos == null ? 0 : inviteInfos.size();
    }

    public class InviteView extends RecyclerView.ViewHolder {
        @BindView(R.id.head_photo)
        RoundedImageView headPhoto;
        @BindView(R.id.phone)
        ConventionalTextView phone;
        @BindView(R.id.down_iv)
        ImageView downIv;
        @BindView(R.id.status_tv)
        ConventionalTextView statusTv;
        @BindView(R.id.status_iv)
        ImageView statusIv;
        @BindView(R.id.item_ll)
        RelativeLayout itemLl;
        @BindView(R.id.record_rv)
        RecyclerView recordRv;
        @BindView(R.id.line)
        View line;

        private InviteItemAdapter inviteItemAdapter;


        public InviteView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            inviteItemAdapter = new InviteItemAdapter(context);
            recordRv.setAdapter(inviteItemAdapter);
        }

        public void bindData(InviteInfo inviteInfo) {
            GlideHelper.load((Activity) context, inviteInfo.getAvatar(), R.mipmap.icon_defalut_head, headPhoto);
            phone.setText(inviteInfo.getMobile());
            if (TextUtils.equals(StaticData.REFRESH_ZERO, inviteInfo.getUserLevel())) {
                statusTv.setText(context.getString(R.string.not_certified));
                statusTv.setSelected(false);
                statusIv.setSelected(false);
            } else {
                statusTv.setText(context.getString(R.string.is_verified));
                statusTv.setSelected(true);
                statusIv.setSelected(true);
            }
            inviteItemAdapter.setData(inviteInfo.getInvitItemInfos());
        }

    }


    public interface OnItemClickListener {
        void upDownView(ImageView downIv, RecyclerView recordRv,View line, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
