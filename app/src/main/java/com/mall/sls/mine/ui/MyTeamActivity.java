package com.mall.sls.mine.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.WXShareManager;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.TeamInfo;
import com.mall.sls.homepage.ui.ConfirmOrderActivity;
import com.mall.sls.mine.DaggerMineComponent;
import com.mall.sls.mine.MineContract;
import com.mall.sls.mine.MineModule;
import com.mall.sls.mine.adapter.MyTeamAdapter;
import com.mall.sls.mine.presenter.MyTeamInfoPresenter;
import com.mall.sls.order.ui.GoodsOrderDetailsActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/11.
 * 描述：我的拼团
 */
public class MyTeamActivity extends BaseActivity implements MineContract.MyTeamInfoView, MyTeamAdapter.OnItemClickListener {


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
    private MyTeamAdapter myTeamAdapter;
    @Inject
    MyTeamInfoPresenter myTeamInfoPresenter;

    private String wxUrl;
    private String inviteCode;
    private String backType;
    private String grouponId;
    private String goodsProductId;
    private WXShareManager wxShareManager;
    private String nameText;
    private String briefText;

    public static void start(Context context,String wxUrl, String inviteCode) {
        Intent intent = new Intent(context, MyTeamActivity.class);
        intent.putExtra(StaticData.WX_URL, wxUrl);
        intent.putExtra(StaticData.INVITE_CODE, inviteCode);
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
        EventBus.getDefault().register(this);
        wxShareManager = WXShareManager.getInstance(this);
        refreshLayout.setOnMultiPurposeListener(simpleMultiPurposeListener);
        wxUrl = getIntent().getStringExtra(StaticData.WX_URL);
        inviteCode = getIntent().getStringExtra(StaticData.INVITE_CODE);
        addAdapter();
        myTeamInfoPresenter.getMyTeamInfo(StaticData.REFLASH_ONE);
    }

    private void addAdapter() {
        myTeamAdapter = new MyTeamAdapter(this);
        myTeamAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(myTeamAdapter);
    }


    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
            myTeamInfoPresenter.getMyTeamInfo(StaticData.REFLASH_ZERO);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            myTeamInfoPresenter.getMoreMyTeamInfo();
        }
    };

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
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderMyTeamInfo(TeamInfo teamInfo) {
        refreshLayout.finishRefresh();
        if (teamInfo != null) {
            if (teamInfo != null && teamInfo.getTeamInfos().size() > 0) {
                recordRv.setVisibility(View.VISIBLE);
                noRecordLl.setVisibility(View.GONE);
                if (teamInfo.getTeamInfos().size() == Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                    refreshLayout.resetNoMoreData();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                myTeamAdapter.setData(teamInfo.getTeamInfos());
            } else {
                recordRv.setVisibility(View.GONE);
                noRecordLl.setVisibility(View.VISIBLE);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void renderMoreMyTeamInfo(TeamInfo teamInfo) {
        refreshLayout.finishLoadMore();
        if (teamInfo != null && teamInfo.getTeamInfos() != null) {
            if (teamInfo.getTeamInfos().size() != Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            myTeamAdapter.addMore(teamInfo.getTeamInfos());
        }
    }

    @Override
    public void setPresenter(MineContract.MyTeamInfoPresenter presenter) {

    }

    @Override
    public void shareWx(String grouponId, String goodsProductId,String goodsName,String brief) {
        if (!PayTypeInstalledUtils.isWeixinAvilible(MyTeamActivity.this)) {
            showMessage(getString(R.string.install_weixin));
            return;
        }
        this.grouponId=grouponId;
        this.goodsProductId=goodsProductId;
        nameText=goodsName;
        briefText=brief;
        Intent intent = new Intent(this, SelectShareTypeActivity.class);
        startActivityForResult(intent, RequestCodeStatic.SELECT_SHARE_TYPE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.SELECT_SHARE_TYPE:
                    if (data != null) {
                        backType = data.getStringExtra(StaticData.BACK_TYPE);
                        shareGroupWx(TextUtils.equals(StaticData.REFLASH_ONE, backType));
                    }
                    break;
                default:
            }
        }
    }

    private void shareGroupWx(boolean isFriend) {
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.app_icon);
        String url = wxUrl + "group/" + grouponId + "/" + goodsProductId + StaticData.WX_INVITE_CODE + inviteCode;
        wxShareManager.shareUrlToWX(isFriend, url, bitmap, nameText, briefText);
    }

    @Override
    public void goOrderDetails(String goodsOrderId) {
        GoodsOrderDetailsActivity.start(this,goodsOrderId);
    }

    //分享成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShareSuccess(String code) {
        showMessage(getString(R.string.share_success));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
