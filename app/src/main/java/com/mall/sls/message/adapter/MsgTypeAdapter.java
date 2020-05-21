package com.mall.sls.message.adapter;

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
import com.mall.sls.data.entity.MessageTypeInfo;
import com.mall.sls.mine.adapter.InviteItemAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MsgTypeAdapter extends RecyclerView.Adapter<MsgTypeAdapter.MsgTypeView> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<MessageTypeInfo> messageTypeInfos;

    public MsgTypeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MessageTypeInfo> messageTypeInfos) {
        this.messageTypeInfos = messageTypeInfos;
        notifyDataSetChanged();
    }

    @Override
    public MsgTypeView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_message_type, parent, false);
        return new MsgTypeView(view);
    }

    @Override
    public void onBindViewHolder(MsgTypeView holder, int position) {
        MessageTypeInfo messageTypeInfo = messageTypeInfos.get(holder.getAdapterPosition());
        holder.bindData(messageTypeInfo);
        holder.itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    if(TextUtils.equals(StaticData.REFLASH_ONE,messageTypeInfo.getId())){
                        onItemClickListener.goOrderMessage(messageTypeInfo.getId());
                    }else if(TextUtils.equals(StaticData.REFLASH_TWO,messageTypeInfo.getId())){
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageTypeInfos == null ? 0 : messageTypeInfos.size();
    }

    public class MsgTypeView extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.right_arrow_iv)
        ImageView rightArrowIv;
        @BindView(R.id.title)
        ConventionalTextView title;
        @BindView(R.id.content)
        ConventionalTextView content;
        @BindView(R.id.item_rl)
        RelativeLayout itemRl;


        public MsgTypeView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(MessageTypeInfo messageTypeInfo) {
            GlideHelper.load((Activity) context, messageTypeInfo.getIconImg(), R.mipmap.icon_default_goods, icon);
            title.setText(messageTypeInfo.getType());
            content.setText(messageTypeInfo.getFirstMsgContent());
        }

    }


    public interface OnItemClickListener {
        void goOrderMessage(String id);//系统消息
        void goReminderMessage(String id);//活动福利
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
