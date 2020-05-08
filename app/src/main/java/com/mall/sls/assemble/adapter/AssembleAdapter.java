package com.mall.sls.assemble.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.assemble.ui.AssembleHomeActivity;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.data.entity.AssembleInfo;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssembleAdapter extends RecyclerView.Adapter<AssembleAdapter.AssembleView> {
    private LayoutInflater layoutInflater;
    private List<AssembleInfo> assembleInfos;
    private Context context;


    public AssembleAdapter(AssembleHomeActivity context) {
        this.context = context;
    }

    public void setData(List<AssembleInfo> assembleInfos) {
        this.assembleInfos = assembleInfos;
        notifyDataSetChanged();
    }

    @Override
    public AssembleView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_assemble, parent, false);
        return new AssembleView(view);
    }

    @Override
    public void onBindViewHolder(AssembleView holder, int position) {
        AssembleInfo assembleInfo = assembleInfos.get(holder.getAdapterPosition());
        holder.bindData(assembleInfo);

    }

    @Override
    public int getItemCount() {
        return assembleInfos == null ? 0 : assembleInfos.size();
    }

    public class AssembleView extends RecyclerView.ViewHolder {
        @BindView(R.id.view_flipper)
        ViewFlipper viewFlipper;

        public AssembleView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(AssembleInfo assembleInfo) {
            for(int i=0;i<assembleInfo.getAssembleItems().size();i++){
                View view = View.inflate(context,R.layout.item_assemble_tip,null);
                TextView text=view.findViewById(R.id.tv1);
                ImageView imageView=view.findViewById(R.id.img1);
                GlideHelper.load((Activity) context, assembleInfo.getAssembleItems().get(i).getImageUrl(), R.mipmap.ic_launcher, imageView);
                text.setText(assembleInfo.getAssembleItems().get(i).getName());
                viewFlipper.addView(view);
            }
            viewFlipper.setFlipInterval(2000);
            viewFlipper.startFlipping();
        }
    }

}
