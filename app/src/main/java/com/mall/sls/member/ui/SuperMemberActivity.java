package com.mall.sls.member.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.address.ui.AddAddressActivity;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.GoodsItemInfo;
import com.mall.sls.homepage.ui.SelectPayTypeActivity;
import com.mall.sls.member.adapter.MemberGoodsItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/12.
 * 描述：会员
 */
public class SuperMemberActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.head_photo)
    RoundedImageView headPhoto;
    @BindView(R.id.phone)
    ConventionalTextView phone;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;

    private MemberGoodsItemAdapter memberGoodsItemAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, SuperMemberActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_menber);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView(){
        memberGoodsItemAdapter=new MemberGoodsItemAdapter(this);
        recordRv.setAdapter(memberGoodsItemAdapter);
        List<GoodsItemInfo> goodsItemInfos=new ArrayList<>();
        GoodsItemInfo goodsItemInfo=new GoodsItemInfo("苹果","哈想吃","12","14");
        GoodsItemInfo goodsItemInfo1=new GoodsItemInfo("香蕉","房价肯定就发的","15","22");
        GoodsItemInfo goodsItemInfo2=new GoodsItemInfo("橘子","开发了都JFK的肌肤","18","66");
        goodsItemInfos.add(goodsItemInfo);
        goodsItemInfos.add(goodsItemInfo1);
        goodsItemInfos.add(goodsItemInfo2);
        memberGoodsItemAdapter.setData(goodsItemInfos);
    }

    @OnClick({R.id.confirm_bt, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                SelectPayTypeActivity.start(this);
                break;
            case R.id.back:
                finish();
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
