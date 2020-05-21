package com.mall.sls.message.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.swipe.SwipeRevealLayout;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.data.entity.MessageItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderMessageAdapter extends RecyclerView.Adapter<OrderMessageAdapter.OrderMessageView> {
    private LayoutInflater layoutInflater;
    private List<MessageItemInfo> messageItemInfos;
    private Context context;

    public OrderMessageAdapter(Context context) {
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
    public OrderMessageView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_order_message, parent, false);
        return new OrderMessageView(view);
    }

    @Override
    public void onBindViewHolder(OrderMessageView holder, int position) {
        MessageItemInfo messageItemInfo = messageItemInfos.get(holder.getAdapterPosition());
        holder.bindData(messageItemInfo);
        holder.goodsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.goOrderDetails(messageItemInfo.getAssociatedId(), messageItemInfo.getId());
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

    public class OrderMessageView extends RecyclerView.ViewHolder {
        @BindView(R.id.delete_layout)
        FrameLayout deleteLayout;
        @BindView(R.id.title)
        ConventionalTextView title;
        @BindView(R.id.content)
        ConventionalTextView content;
        @BindView(R.id.goods_iv)
        ImageView goodsIv;
        @BindView(R.id.goods_content)
        ConventionalTextView goodsContent;
        @BindView(R.id.goods_rl)
        RelativeLayout goodsRl;
        @BindView(R.id.time)
        ConventionalTextView time;
        @BindView(R.id.goods_layout)
        RelativeLayout goodsLayout;
        @BindView(R.id.swipe_layout)
        SwipeRevealLayout swipeLayout;


        public OrderMessageView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(MessageItemInfo messageItemInfo) {
            swipeLayout.close(false);
            GlideHelper.load((Activity) context, messageItemInfo.getImageUrl(), R.mipmap.icon_default_goods, goodsIv);
            title.setText(messageItemInfo.getTitle());
            content.setText(messageItemInfo.getContent());
            goodsContent.setText(messageItemInfo.getContent());
            time.setText(messageItemInfo.getAddTime());
            goodsRl.setVisibility(TextUtils.equals(StaticData.REFLASH_ONE, messageItemInfo.getLinkUrl()) ? View.VISIBLE : View.GONE);
            content.setVisibility(TextUtils.equals(StaticData.REFLASH_ONE, messageItemInfo.getLinkUrl()) ? View.GONE : View.VISIBLE);
            goodsLayout.setEnabled(TextUtils.equals(StaticData.REFLASH_ONE, messageItemInfo.getLinkUrl()));
        }

    }


    public interface OnItemClickListener {
        void goOrderDetails(String orderId, String id);

        void deleteItem(String id);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
