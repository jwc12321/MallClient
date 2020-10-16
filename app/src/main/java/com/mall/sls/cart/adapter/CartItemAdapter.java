package com.mall.sls.cart.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.EditTextLimit;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.swipe.SwipeRevealLayout;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.CartItemInfo;
import com.mall.sls.data.entity.HiddenItemCartInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CartItemAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Literature> mLiteratureList;

    public void setLiteratureList(List<? extends Literature> literatureList) {
        if (mLiteratureList == null) {
            mLiteratureList = new ArrayList<>();
        }
        mLiteratureList.clear();
        mLiteratureList.addAll(literatureList);
        notifyDataSetChanged();
    }

    public CartItemAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case Literature.TYPE_NORMAL:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_normal_cart_item, parent, false);
                return new NormalViewHolder(itemView);
            case Literature.TYPE_EMPTY:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.empty_cartitem, parent, false);
                return new EmptyViewHolder(itemView);
            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_cancel_cart_item, parent, false);
                return new CancleViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case Literature.TYPE_NORMAL:
                ((NormalViewHolder) holder).bindView(position);
                break;
            case Literature.TYPE_EMPTY:
                ((EmptyViewHolder) holder).bindView(position);
                break;
            case Literature.TYPE_CANCEL:
                ((CancleViewHolder) holder).bindView(position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mLiteratureList == null ? 0 : mLiteratureList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mLiteratureList.get(position).getType();
    }


    class NormalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.delete_layout)
        FrameLayout deleteLayout;
        @BindView(R.id.choice_item)
        CheckBox choiceItem;
        @BindView(R.id.goods_iv)
        ImageView goodsIv;
        @BindView(R.id.goods_name)
        MediumThickTextView goodsName;
        @BindView(R.id.goods_price)
        MediumThickTextView goodsPrice;
        @BindView(R.id.sku)
        ConventionalTextView sku;
        @BindView(R.id.decrease_count)
        ImageView decreaseCount;
        @BindView(R.id.goods_count)
        EditText goodsCount;
        @BindView(R.id.increase_count)
        ImageView increaseCount;
        @BindView(R.id.goods_layout)
        RelativeLayout goodsLayout;
        @BindView(R.id.swipe_layout)
        SwipeRevealLayout swipeLayout;
        @BindView(R.id.goods_rl)
        RelativeLayout goodsRl;

        CartItemInfo cartItemInfo;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(int position) {
            swipeLayout.close(false);
            cartItemInfo = (CartItemInfo) mLiteratureList.get(position);
            GlideHelper.load((Activity) context, cartItemInfo.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
            goodsName.setText(cartItemInfo.getGoodsName());
            sku.setText(cartItemInfo.getSpecifications());
            goodsPrice.setText(NumberFormatUnit.numberFormat(cartItemInfo.getPrice()));
            choiceItem.setChecked(cartItemInfo.isIscheck());
            goodsCount.setText(cartItemInfo.getNumber());
            if (onItemClickListener != null) {
                choiceItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.checkItem(((CheckBox) v).isChecked(), position);
                    }
                });
                decreaseCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.doReduce(position, goodsCount, cartItemInfo.getId());
                    }
                });
                increaseCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.doIncrease(position, goodsCount, cartItemInfo.getId());
                    }
                });
                deleteLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.deleteItem(cartItemInfo.getId(), position, StaticData.REFRESH_ONE);
//                            mLiteratureList.remove(position);
//                            notifyItemRemoved(position);
                        }
                    }
                });
                goodsCount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (!TextUtils.isEmpty(goodsCount.getText().toString())) {
                            EditTextLimit.editLimit(goodsCount, 0);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        cartItemInfo.setInputNumber(editable.toString());
                    }
                });

                goodsCount.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            onItemClickListener.clickEditText(position, goodsCount);
                        } else {
                            onItemClickListener.returnEditText(goodsCount);
                        }
                    }
                });
                goodsRl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.goGoodsDetails(cartItemInfo.getGoodsId());
                    }
                });
            }
        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_delete)
        RelativeLayout itemDelete;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(int position) {
            itemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.deleteEmpty(StaticData.REFRESH_THREE);
                    }
                }
            });
        }
    }

    class CancleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.delete_layout)
        FrameLayout deleteLayout;
        @BindView(R.id.choice_item)
        ImageView choiceItem;
        @BindView(R.id.goods_iv)
        ImageView goodsIv;
        @BindView(R.id.goods_name)
        MediumThickTextView goodsName;
        @BindView(R.id.goods_price)
        MediumThickTextView goodsPrice;
        @BindView(R.id.sku)
        ConventionalTextView sku;
        @BindView(R.id.goods_layout)
        RelativeLayout goodsLayout;
        @BindView(R.id.swipe_layout)
        SwipeRevealLayout swipeLayout;

        private HiddenItemCartInfo hiddenItemCartInfo;

        public CancleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(int position) {
            swipeLayout.close(false);
            hiddenItemCartInfo = (HiddenItemCartInfo) mLiteratureList.get(position);
            GlideHelper.load((Activity) context, hiddenItemCartInfo.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
            goodsName.setText(hiddenItemCartInfo.getGoodsName());
            sku.setText(hiddenItemCartInfo.getSpecifications());
            goodsPrice.setText(NumberFormatUnit.numberFormat(hiddenItemCartInfo.getPrice()));
            deleteLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.deleteItem(hiddenItemCartInfo.getId(), position, StaticData.REFRESH_TWO);
//                        mLiteratureList.remove(position);
//                        notifyItemRemoved(position);
                    }
                }
            });
        }
    }


    public interface OnItemClickListener {
        void checkItem(boolean isChecked, int position);//点击店铺全选

        void doIncrease(int position, View showCountView, String id);  //增加数量

        void doReduce(int position, View showCountView, String id); //减少数量

        void deleteItem(String id, int position, String type);//删除单个

        void deleteEmpty(String type);//清空无效商品

        void clickEditText(int position, View showCountView);//填写数量

        void returnEditText(View showCountView);

        void goGoodsDetails(String goodsId);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
