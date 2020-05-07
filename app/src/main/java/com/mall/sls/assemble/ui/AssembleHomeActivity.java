package com.mall.sls.assemble.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.assemble.adapter.AssembleAdapter;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.data.entity.AssembleInfo;
import com.mall.sls.data.entity.AssembleItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author jwc on 2020/5/7.
 * 描述：
 */
public class AssembleHomeActivity extends BaseActivity {
    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    private AssembleAdapter assembleAdapter;
    private List<AssembleInfo> assembleInfos;

    public static void start(Context context) {
        Intent intent = new Intent(context, AssembleHomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assemble_home);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        assembleInfos = new ArrayList<>();
        assembleAdapter = new AssembleAdapter(this);
        recordRv.setAdapter(assembleAdapter);
        AssembleItem assembleItem1 = new AssembleItem("http://www.baidu.com/img/bdlogo.png", "1");
        AssembleItem assembleItem2 = new AssembleItem("http://www.baidu.com/img/bdlogo.png", "2");
        AssembleItem assembleItem3 = new AssembleItem("http://www.baidu.com/img/bdlogo.png", "3");
        AssembleItem assembleItem4 = new AssembleItem("http://www.baidu.com/img/bdlogo.png", "4");
        AssembleItem assembleItem5 = new AssembleItem("http://www.baidu.com/img/bdlogo.png", "5");
        List<AssembleItem> assembleItems = new ArrayList<>();
        assembleItems.add(assembleItem1);
        assembleItems.add(assembleItem2);
        assembleItems.add(assembleItem3);
        assembleItems.add(assembleItem4);
        assembleItems.add(assembleItem5);
        AssembleInfo assembleInfo = new AssembleInfo();
        assembleInfo.setAssembleItems(assembleItems);
        assembleInfos.add(assembleInfo);
        assembleInfos.add(assembleInfo);
        assembleInfos.add(assembleInfo);
        assembleInfos.add(assembleInfo);
        assembleInfos.add(assembleInfo);
        for(int i=0;i<assembleItems.size();i++){
            View view = View.inflate(this,R.layout.item_assemble_tip,null);
            TextView text=view.findViewById(R.id.tv);
            ImageView imageView=view.findViewById(R.id.img);
            GlideHelper.load(this, assembleItems.get(i).getImageUrl(), R.mipmap.ic_launcher, imageView);
            text.setText(assembleItems.get(i).getName());
            view.setTag(i);
            viewFlipper.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Guanggao", "点击了" + v.getTag());
                }
            });
        }
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
