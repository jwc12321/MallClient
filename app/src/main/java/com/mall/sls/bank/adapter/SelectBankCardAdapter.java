package com.mall.sls.bank.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.BankUtil;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.data.entity.BankCardInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectBankCardAdapter extends RecyclerView.Adapter<SelectBankCardAdapter.SelectBankCardView> {
    private LayoutInflater layoutInflater;
    private List<BankCardInfo> bankCardInfos;
    private Context context;

    public void setData(List<BankCardInfo> bankCardInfos) {
        this.bankCardInfos = bankCardInfos;
        notifyDataSetChanged();
    }

    public SelectBankCardAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SelectBankCardView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_select_bank_card, parent, false);
        return new SelectBankCardView(view);
    }

    @Override
    public void onBindViewHolder(SelectBankCardView holder, int position) {
        BankCardInfo bankCardInfo = bankCardInfos.get(holder.getAdapterPosition());
        holder.bindData(bankCardInfo);
        holder.itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.selectBankCard(bankCardInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bankCardInfos == null ? 0 : bankCardInfos.size();
    }

    public class SelectBankCardView extends RecyclerView.ViewHolder {
        @BindView(R.id.logo_iv)
        ImageView logoIv;
        @BindView(R.id.bank_name)
        ConventionalTextView bankName;
        @BindView(R.id.item_rl)
        RelativeLayout itemRl;

        public SelectBankCardView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(BankCardInfo bankCardInfo) {
            GlideHelper.load((Activity) context, bankCardInfo.getIcon(), R.mipmap.app_icon, logoIv);
            bankName.setText(bankCardInfo.getBankName());
            if (TextUtils.equals(StaticData.CHU_XU, bankCardInfo.getCardType())) {
                bankName.setText(bankCardInfo.getBankName()+context.getString(R.string.debit_card)+"("+ BankUtil.lastFourDigits(bankCardInfo.getCardNo())+")");
            } else {
                bankName.setText(bankCardInfo.getBankName()+context.getString(R.string.credit_card)+"("+ BankUtil.lastFourDigits(bankCardInfo.getCardNo())+")");
            }
        }
    }

    public interface OnItemClickListener {
        void selectBankCard(BankCardInfo bankCardInfo);

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
