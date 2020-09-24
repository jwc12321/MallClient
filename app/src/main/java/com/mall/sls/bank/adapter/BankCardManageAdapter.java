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
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.BankCardInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BankCardManageAdapter extends RecyclerView.Adapter<BankCardManageAdapter.BankCardManageView> {
    private LayoutInflater layoutInflater;
    private List<BankCardInfo> bankCardInfos;
    private Context context;

    public void setData(List<BankCardInfo> bankCardInfos) {
        this.bankCardInfos = bankCardInfos;
        notifyDataSetChanged();
    }

    public BankCardManageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BankCardManageView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_bank_card_manage, parent, false);
        return new BankCardManageView(view);
    }

    @Override
    public void onBindViewHolder(BankCardManageView holder, int position) {
        BankCardInfo bankCardInfo = bankCardInfos.get(holder.getAdapterPosition());
        holder.bindData(bankCardInfo);
        holder.bankRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.UntieBankCard(bankCardInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bankCardInfos == null ? 0 : bankCardInfos.size();
    }

    public class BankCardManageView extends RecyclerView.ViewHolder {
        @BindView(R.id.logo_iv)
        ImageView logoIv;
        @BindView(R.id.bank_name)
        MediumThickTextView bankName;
        @BindView(R.id.card_type)
        ConventionalTextView cardType;
        @BindView(R.id.card_number)
        MediumThickTextView cardNumber;
        @BindView(R.id.bank_rl)
        RelativeLayout bankRl;

        public BankCardManageView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(BankCardInfo bankCardInfo) {
            GlideHelper.load((Activity) context, bankCardInfo.getIcon(), R.mipmap.app_icon, logoIv);
            bankName.setText(bankCardInfo.getBankName());
            if(TextUtils.equals(StaticData.CHU_XU,bankCardInfo.getCardType())){
                cardType.setText(context.getString(R.string.debit_card));
            }else {
                cardType.setText(context.getString(R.string.credit_card));
            }
            cardNumber.setText(BankUtil.lastFourDigits(bankCardInfo.getCardNo()));
            BankUtil.setBankBg(bankCardInfo.getBankCode(),bankRl);
        }
    }

    public interface OnItemClickListener {
        void UntieBankCard(BankCardInfo bankCardInfo);

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
