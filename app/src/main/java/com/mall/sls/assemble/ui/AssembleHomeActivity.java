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
        AssembleItem assembleItem6 = new AssembleItem("http://www.baidu.com/img/bdlogo.png", "6");
        List<AssembleItem> assembleItems = new ArrayList<>();
        assembleItems.add(assembleItem1);
        assembleItems.add(assembleItem2);
        assembleItems.add(assembleItem3);
        assembleItems.add(assembleItem4);
        assembleItems.add(assembleItem5);
        assembleItems.add(assembleItem6);
        AssembleInfo assembleInfo = new AssembleInfo();
        assembleInfo.setAssembleItems(assembleItems);
        assembleInfos.add(assembleInfo);
        assembleInfos.add(assembleInfo);
        assembleInfos.add(assembleInfo);
        assembleInfos.add(assembleInfo);
        assembleInfos.add(assembleInfo);
        for(int i=0;i<assembleItems.size()/2;i++){
            View view1 = View.inflate(this,R.layout.item_assemble_tip,null);
            TextView text1=view1.findViewById(R.id.tv1);
            ImageView image1View1=view1.findViewById(R.id.img1);
            TextView text2=view1.findViewById(R.id.tv2);
            ImageView image1View2=view1.findViewById(R.id.img2);
            GlideHelper.load(this, assembleItems.get(2*i).getImageUrl(), R.mipmap.ic_launcher, image1View1);
            text1.setText(assembleItems.get(2*i).getName());
            text1.setTag(2*i);
            GlideHelper.load(this, assembleItems.get(2*i+1).getImageUrl(), R.mipmap.ic_launcher, image1View2);
            text2.setText(assembleItems.get(2*i+1).getName());
            text2.setTag(2*i+1);
            viewFlipper.addView(view1);
            text1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Guanggao", "点击了" + v.getTag());
                }
            });
            text2.setOnClickListener(new View.OnClickListener() {
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
