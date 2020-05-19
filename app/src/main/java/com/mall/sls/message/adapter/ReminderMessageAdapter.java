package com.mall.sls.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.mall.sls.R;
import com.mall.sls.common.widget.swipe.SwipeRevealLayout;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.data.entity.MessageItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReminderMessageAdapter extends RecyclerView.Adapter<ReminderMessageAdapter.ReminderMessageView> {
    private LayoutInflater layoutInflater;
    private List<MessageItemInfo> messageItemInfos;
    private Context context;

    public ReminderMessageAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MessageItemInfo> messageItemInfos) {
        this.messageItemInfos = messageItemInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<MessageItemInfo> moreList) {
        int pos = messageItemInfos.size();
        messageItemInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public ReminderMessageView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_reminder_message, parent, false);
        return new ReminderMessageView(view);
    }

    @Override
    public void onBindViewHolder(ReminderMessageView holder, int position) {
        MessageItemInfo messageItemInfo = messageItemInfos.get(holder.getAdapterPosition());
        holder.bindData(messageItemInfo);
        holder.goodsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.goRead(messageItemInfo.getId());
                }
            }
        });
        holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.deleteItem(messageItemInfo.getId());
                    messageItemInfos.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageItemInfos == null ? 0 : messageItemInfos.size();
    }

    public class ReminderMessageView extends RecyclerView.ViewHolder {
        @BindView(R.id.delete_layout)
        FrameLayout deleteLayout;
        @BindView(R.id.title)
        ConventionalTextView title;
        @BindView(R.id.content)
        ConventionalTextView content;
        @BindView(R.id.time)
        ConventionalTextView time;
        @BindView(R.id.goods_layout)
        RelativeLayout goodsLayout;
        @BindView(R.id.swipe_layout)
        SwipeRevealLayout swipeLayout;


        public ReminderMessageView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(MessageItemInfo messageItemInfo) {
            swipeLayout.close(false);
            title.setText(messageItemInfo.getTitle());
            content.setText(messageItemInfo.getContent());
            time.setText(messageItemInfo.getAddTime());
        }

    }


    public interface OnItemClickListener {
        void goRead(String id);

        void deleteItem(String id);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
