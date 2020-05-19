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
import com.mall.sls.data.entity.InvitItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InviteItemAdapter extends RecyclerView.Adapter<InviteItemAdapter.InviteItemView> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<InvitItemInfo> invitItemInfos;

    public InviteItemAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<InvitItemInfo> invitItemInfos) {
        this.invitItemInfos = invitItemInfos;
        notifyDataSetChanged();
    }

    @Override
    public InviteItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_my_invitation_item, parent, false);
        return new InviteItemView(view);
    }

    @Override
    public void onBindViewHolder(InviteItemView holder, int position) {
        InvitItemInfo invitItemInfo = invitItemInfos.get(holder.getAdapterPosition());
        holder.bindData(invitItemInfo);
    }

    @Override
    public int getItemCount() {
        return invitItemInfos == null ? 0 : invitItemInfos.size();
    }

    public class InviteItemView extends RecyclerView.ViewHolder {
        @BindView(R.id.head_photo)
        RoundedImageView headPhoto;
        @BindView(R.id.phone)
        ConventionalTextView phone;
        @BindView(R.id.status_tv)
        ConventionalTextView statusTv;
        @BindView(R.id.status_iv)
        ImageView statusIv;


        public InviteItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(InvitItemInfo invitItemInfo) {
            GlideHelper.load((Activity) context,invitItemInfo.getAvatar(), R.mipmap.icon_defalut_head, headPhoto);
            phone.setText(invitItemInfo.getMobile());
            if(TextUtils.equals(StaticData.REFLASH_ZERO,invitItemInfo.getUserLevel())){
                statusTv.setText(context.getString(R.string.not_certified));
                statusTv.setSelected(false);
                statusIv.setSelected(false);
            }else {
                statusTv.setText(context.getString(R.string.is_verified));
                statusTv.setSelected(true);
                statusIv.setSelected(true);
            }
        }

    }
}
