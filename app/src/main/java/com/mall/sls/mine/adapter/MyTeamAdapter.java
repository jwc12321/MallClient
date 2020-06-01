package com.mall.sls.mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.BlackDrawTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.MyTeamInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.MyTeamView> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<MyTeamInfo> myTeamInfos;

    public MyTeamAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MyTeamInfo> myTeamInfos) {
        this.myTeamInfos = myTeamInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<MyTeamInfo> moreList) {
        int pos = myTeamInfos.size();
        myTeamInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public MyTeamView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_my_team, parent, false);
        return new MyTeamView(view);
    }

    @Override
    public void onBindViewHolder(MyTeamView holder, int position) {
        MyTeamInfo myTeamInfo = myTeamInfos.get(holder.getAdapterPosition());
        holder.bindData(myTeamInfo);
        holder.rightBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    if(TextUtils.equals(StaticData.REFLASH_ONE,myTeamInfo.getStatus())){
                        onItemClickListener.shareWx(myTeamInfo.getGrouponId(),myTeamInfo.getGoodsProductId(),myTeamInfo.getName(),myTeamInfo.getBrief());
                    }else {
                        onItemClickListener.goOrderDetails(myTeamInfo.getOrderId());
                    }
                }
            }
        });
        holder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTeamInfos == null ? 0 : myTeamInfos.size();
    }

    public class MyTeamView extends RecyclerView.ViewHolder {
        @BindView(R.id.time)
        ConventionalTextView time;
        @BindView(R.id.team_status)
        ConventionalTextView teamStatus;
        @BindView(R.id.line)
        View line;
        @BindView(R.id.team_type)
        ConventionalTextView teamType;
        @BindView(R.id.goods_iv)
        ImageView goodsIv;
        @BindView(R.id.goods_name)
        MediumThickTextView goodsName;
        @BindView(R.id.goods_price)
        ConventionalTextView goodsPrice;
        @BindView(R.id.goods_number)
        ConventionalTextView goodsNumber;
        @BindView(R.id.total_number)
        ConventionalTextView totalNumber;
        @BindView(R.id.is_pay)
        ConventionalTextView isPay;
        @BindView(R.id.total_amount)
        ConventionalTextView totalAmount;
        @BindView(R.id.countdown)
        ConventionalTextView countdown;
        @BindView(R.id.left_bt)
        ConventionalTextView leftBt;
        @BindView(R.id.right_bt)
        ConventionalTextView rightBt;
        @BindView(R.id.bt_ll)
        RelativeLayout btLl;
        @BindView(R.id.item_ll)
        LinearLayout itemLl;


        public MyTeamView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(MyTeamInfo myTeamInfo) {
            time.setText(myTeamInfo.getAddTime());
            if(TextUtils.equals(StaticData.REFLASH_ZERO,myTeamInfo.getIsActivity())){
                teamType.setText(context.getString(R.string.daily_group));
            }else {
                teamType.setText(context.getString(R.string.activity_group));
            }
            setOrderStatus(myTeamInfo.getStatus());
            GlideHelper.load((Activity) context, myTeamInfo.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
            goodsName.setText(myTeamInfo.getName());
            goodsPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(myTeamInfo.getGoodsPrice()));
            goodsNumber.setText("x" + myTeamInfo.getNumber());
            totalNumber.setText("共" + myTeamInfo.getNumber() + "件");
            totalAmount.setText(NumberFormatUnit.twoDecimalFormat(myTeamInfo.getActualPrice()));
        }

        //10:拼团中 20：拼团成
        private void setOrderStatus(String status) {
            switch (status) {
                case StaticData.REFLASH_ONE:
                    teamStatus.setText(context.getString(R.string.in_group));
                    leftBt.setVisibility(View.GONE);
                    rightBt.setVisibility(View.VISIBLE);
                    break;
                case StaticData.REFLASH_THREE:
                    teamStatus.setText(context.getString(R.string.successful_team));
                    leftBt.setVisibility(View.VISIBLE);
                    rightBt.setVisibility(View.GONE);
                    break;
                default:
            }
        }

    }


    public interface OnItemClickListener {
        void shareWx(String grouponId, String goodsProductId,String goodsName,String brief);
        void goOrderDetails(String goodsOrderId);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
