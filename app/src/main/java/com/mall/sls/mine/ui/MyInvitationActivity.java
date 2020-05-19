package com.mall.sls.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.InvitItemInfo;
import com.mall.sls.data.entity.InviteInfo;
import com.mall.sls.mine.DaggerMineComponent;
import com.mall.sls.mine.MineContract;
import com.mall.sls.mine.MineModule;
import com.mall.sls.mine.adapter.InviteAdapter;
import com.mall.sls.mine.presenter.MyInvitePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/19.
 * 描述：我的邀请
 */
public class MyInvitationActivity extends BaseActivity implements MineContract.MyInviteView,InviteAdapter.OnItemClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.no_record_ll)
    LinearLayout noRecordLl;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private InviteAdapter inviteAdapter;
    private List<InviteInfo> inviteInfos;
    @Inject
    MyInvitePresenter myInvitePresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, MyInvitationActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_invitation);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView() {
        inviteAdapter = new InviteAdapter(this);
        inviteAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(inviteAdapter);
        myInvitePresenter.getMyInvite();
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    protected void initializeInjector() {
        DaggerMineComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mineModule(new MineModule(this))
                .build()
                .inject(this);
    }


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
    public void upDownView(ImageView downIv, RecyclerView recordRv, View line,int position) {
        InviteInfo inviteInfo = inviteInfos.get(position);
        if (inviteInfo.isUp()) {
            inviteInfo.setUp(false);
            downIv.setSelected(false);
            line.setVisibility(View.INVISIBLE);
            recordRv.setVisibility(View.GONE);
        } else {
            inviteInfo.setUp(true);
            downIv.setSelected(true);
            line.setVisibility(View.VISIBLE);
            recordRv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void renderMyInvite(List<InviteInfo> inviteInfos) {
        this.inviteInfos=inviteInfos;
        if(inviteInfos!=null&&inviteInfos.size()>0){
            recordRv.setVisibility(View.VISIBLE);
            noRecordLl.setVisibility(View.GONE);
        }else {
            recordRv.setVisibility(View.GONE);
            noRecordLl.setVisibility(View.VISIBLE);
        }
        inviteAdapter.setData(inviteInfos);
    }

    @Override
    public void setPresenter(MineContract.MyInvitePresenter presenter) {

    }
}
