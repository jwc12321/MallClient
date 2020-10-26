package com.mall.sls.merchant.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.mall.sls.R;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.data.entity.PointsRecordInfo;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PointsRecordAdapter extends RecyclerView.Adapter<PointsRecordAdapter.PointsRecordView> { ;
    private LayoutInflater layoutInflater;
    private List<PointsRecordInfo> pointsRecordInfos;

    public void setData(List<PointsRecordInfo> pointsRecordInfos) {
        this.pointsRecordInfos = pointsRecordInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<PointsRecordInfo> moreList) {
        int pos = pointsRecordInfos.size();
        pointsRecordInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public PointsRecordView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_points_record, parent, false);
        return new PointsRecordView(view);
    }

    @Override
    public void onBindViewHolder(PointsRecordView holder, int position) {
        PointsRecordInfo pointsRecordInfo = pointsRecordInfos.get(holder.getAdapterPosition());
        holder.bindData(pointsRecordInfo);

    }

    @Override
    public int getItemCount() {
        return pointsRecordInfos == null ? 0 : pointsRecordInfos.size();
    }

    public class PointsRecordView extends RecyclerView.ViewHolder {
        @BindView(R.id.content)
        ConventionalTextView content;
        @BindView(R.id.price)
        ConventionalTextView price;
        @BindView(R.id.time)
        ConventionalTextView time;

        public PointsRecordView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(PointsRecordInfo pointsRecordInfo) {
            content.setText(pointsRecordInfo.getContent());
            time.setText(pointsRecordInfo.getAddTime());
            if(!TextUtils.isEmpty(pointsRecordInfo.getCount())&&Integer.parseInt(pointsRecordInfo.getCount())>0){
                price.setText("+"+pointsRecordInfo.getCount());
            }else {
                price.setText(pointsRecordInfo.getCount());
            }
        }
    }

}
