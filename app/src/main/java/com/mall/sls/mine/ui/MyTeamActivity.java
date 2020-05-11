package com.mall.sls.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.unit.TokenManager;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.MyTeamInfo;
import com.mall.sls.mine.adapter.MyTeamAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/11.
 * 描述：我的拼团
 */
public class MyTeamActivity extends BaseActivity implements MyTeamAdapter.OnItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.no_address_ll)
    LinearLayout noAddressLl;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private MyTeamAdapter myTeamAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, MyTeamActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        refreshLayout.setOnMultiPurposeListener(simpleMultiPurposeListener);
        addAdapter();
    }

    private void addAdapter() {
        myTeamAdapter = new MyTeamAdapter(this);
        myTeamAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(myTeamAdapter);
        addData();

    }


    private void addData() {
        List<MyTeamInfo> myTeamInfos = new ArrayList<>();
        MyTeamInfo goodsOrderInfo = new MyTeamInfo("13", "10", "1", "苹果","10");
        MyTeamInfo goodsOrderInfo1 = new MyTeamInfo("12", "10", "3", "香蕉","10");
        MyTeamInfo goodsOrderInfo2 = new MyTeamInfo("11", "20", "4", "橘子","20");
        MyTeamInfo goodsOrderInfo4 = new MyTeamInfo("12", "20", "3", "香蕉","20");
        MyTeamInfo goodsOrderInfo5 = new MyTeamInfo("11", "20", "4", "橘子","10");
        myTeamInfos.add(goodsOrderInfo);
        myTeamInfos.add(goodsOrderInfo1);
        myTeamInfos.add(goodsOrderInfo2);
        myTeamInfos.add(goodsOrderInfo4);
        myTeamInfos.add(goodsOrderInfo5);
        myTeamAdapter.setData(myTeamInfos);
    }


    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        }
    };

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
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
