package com.mall.sls.common.widget.citypicker.adapter;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.R;
import com.mall.sls.common.widget.citypicker.adapter.decoration.GridItemDecoration;
import com.mall.sls.common.widget.citypicker.model.City;
import com.mall.sls.common.widget.citypicker.model.HotCity;
import com.mall.sls.common.widget.citypicker.model.LocateState;
import com.mall.sls.common.widget.citypicker.model.LocatedCity;
import com.mall.sls.common.widget.textview.ConventionalTextView;

import java.util.List;

/**
 * @Author: Bro0cL
 * @Date: 2018/2/5 12:06
 */
public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.BaseViewHolder> {
    private static final int VIEW_TYPE_LOCATION = 10;
    private static final int VIEW_TYPE_HOT     = 11;

    private Context mContext;
    private List<City> mData;
    private List<HotCity> mHotData;
    private int locateState;
    private InnerListener mInnerListener;
    private LinearLayoutManager mLayoutManager;
    private boolean stateChanged;
    private boolean autoLocate;

    public CityListAdapter(Context context, List<City> data, List<HotCity> hotData, int state) {
        this.mData = data;
        this.mContext = context;
        this.mHotData = hotData;
        this.locateState = state;
    }

    public void autoLocate(boolean auto){
        autoLocate = auto;
    }

    public void setLayoutManager(LinearLayoutManager manager){
        this.mLayoutManager = manager;
    }

    public void updateData(List<City> data){
        this.mData = data;
        notifyDataSetChanged();
    }

    public void updateLocateState(LocatedCity location, int state){
        mData.remove(0);
        mData.add(0, location);
        stateChanged = !(locateState == state);
        locateState = state;
        refreshLocationItem();
    }

    public void refreshLocationItem(){
        //如果定位城市的item可见则进行刷新
        if (stateChanged && mLayoutManager.findFirstVisibleItemPosition() == 0) {
            stateChanged = false;
            notifyItemChanged(0);
        }
    }

    /**
     * 滚动RecyclerView到索引位置
     * @param index
     */
    public void scrollToSection(String index){
        if (mData == null || mData.isEmpty()) return;
        if (TextUtils.isEmpty(index)) return;
        int size = mData.size();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(index.substring(0, 1), mData.get(i).getSection().substring(0, 1))){
                if (mLayoutManager != null){
                    mLayoutManager.scrollToPositionWithOffset(i, 0);
                    if (TextUtils.equals(index.substring(0, 1), "当")) {
                        //防止滚动时进行刷新
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (stateChanged) notifyItemChanged(0);
                            }
                        }, 1000);
                    }
                    return;
                }
            }
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case VIEW_TYPE_LOCATION:
                view = LayoutInflater.from(mContext).inflate(R.layout.cp_list_item_location_layout, parent, false);
                return new LocationViewHolder(view);
            case VIEW_TYPE_HOT:
                view = LayoutInflater.from(mContext).inflate(R.layout.cp_list_item_hot_layout, parent, false);
                return new HotViewHolder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.cp_list_item_default_layout, parent, false);
                return new DefaultViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (holder instanceof DefaultViewHolder){
            final int pos = holder.getAdapterPosition();
            final City data = mData.get(pos);
            if (data == null) return;
            ((DefaultViewHolder)holder).name.setText(data.getName());
            ((DefaultViewHolder) holder).name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mInnerListener != null){
                        mInnerListener.dismiss(pos, data);
                    }
                }
            });
        }
        //定位城市
        if (holder instanceof LocationViewHolder){
            final int pos = holder.getAdapterPosition();
            final City data = mData.get(pos);
            if (data == null) return;
            //设置宽高

            switch (locateState){
                case LocateState.LOCATING:
                    ((LocationViewHolder) holder).current.setText(R.string.cp_locating);
                    break;
                case LocateState.SUCCESS:
                    ((LocationViewHolder) holder).current.setText(data.getName());
                    break;
                case LocateState.FAILURE:
                    ((LocationViewHolder) holder).current.setText(R.string.cp_locate_failed);
                    break;
            }
            ((LocationViewHolder) holder).current.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (locateState == LocateState.SUCCESS) {
                        if (mInnerListener != null) {
                            mInnerListener.dismiss(pos, data);
                        }
                    } else if (locateState == LocateState.FAILURE){
                        locateState = LocateState.LOCATING;
                        notifyItemChanged(0);
                        if (mInnerListener != null){
                            mInnerListener.locate();
                        }
                    }
                }
            });
            //第一次弹窗，如果未定位则自动定位
            if (autoLocate && locateState == LocateState.LOCATING && mInnerListener != null){
                mInnerListener.locate();
                autoLocate = false;
            }
        }
        //热门城市
        if (holder instanceof HotViewHolder){
            final int pos = holder.getAdapterPosition();
            final City data = mData.get(pos);
            if (data == null) return;
            GridListAdapter mAdapter = new GridListAdapter(mContext, mHotData);
            mAdapter.setInnerListener(mInnerListener);
            ((HotViewHolder) holder).mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && TextUtils.equals("当", mData.get(position).getSection().substring(0, 1)))
            return VIEW_TYPE_LOCATION;
        if (position == 1 && TextUtils.equals("热", mData.get(position).getSection().substring(0, 1)))
            return VIEW_TYPE_HOT;
        return super.getItemViewType(position);
    }

    public void setInnerListener(InnerListener listener){
        this.mInnerListener = listener;
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder{
        BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class DefaultViewHolder extends BaseViewHolder{
        ConventionalTextView name;

        DefaultViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cp_list_item_name);
        }
    }

    public static class HotViewHolder extends BaseViewHolder {
        RecyclerView mRecyclerView;

        HotViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = itemView.findViewById(R.id.cp_hot_list);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(),
                    GridListAdapter.SPAN_COUNT, LinearLayoutManager.VERTICAL, false));
            int space = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.dp_10);
            mRecyclerView.addItemDecoration(new GridItemDecoration(GridListAdapter.SPAN_COUNT,
                    space));
        }
    }

    public static class LocationViewHolder extends BaseViewHolder {
        ConventionalTextView current;
        ConventionalTextView reLocate;

        LocationViewHolder(View itemView) {
            super(itemView);
            current = itemView.findViewById(R.id.cp_list_item_location);
            reLocate = itemView.findViewById(R.id.re_locate);
        }
    }
}
