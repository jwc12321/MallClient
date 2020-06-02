package com.mall.sls.message.ui;

import android.content.Context;
import android.content.Intent;
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
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.GoodsOrderDetails;
import com.mall.sls.data.entity.MessageInfo;
import com.mall.sls.message.DaggerMessageComponent;
import com.mall.sls.message.MessageContract;
import com.mall.sls.message.MessageModule;
import com.mall.sls.message.adapter.OrderMessageAdapter;
import com.mall.sls.message.presenter.MsgInfoPresenter;
import com.mall.sls.order.ui.GoodsOrderDetailsActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/19.
 * 描述：订单消息
 */
public class OrderMessageActivity extends BaseActivity implements MessageContract.MsgInfoView, OrderMessageAdapter.OnItemClickListener {
    @BindView(R.id.small_title)
    MediumThickTextView smallTitle;
    @BindView(R.id.small_title_rel)
    RelativeLayout smallTitleRel;
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
    @BindView(R.id.mark_read_tv)
    ConventionalTextView markReadTv;
    @BindView(R.id.clean_tv)
    ConventionalTextView cleanTv;
    @BindView(R.id.clean_ll)
    LinearLayout cleanLl;
    @BindView(R.id.right_iv)
    ImageView rightIv;
    private boolean isClick=false;
    private boolean isClear=false;

    private String typeId;
    @Inject
    MsgInfoPresenter msgInfoPresenter;
    private OrderMessageAdapter orderMessageAdapter;

    public static void start(Context context, String typeId) {
        Intent intent = new Intent(context, OrderMessageActivity.class);
        intent.putExtra(StaticData.TYPE_ID, typeId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_message);
        ButterKnife.bind(this);
        setHeight(null, smallTitle, null);
        initView();
    }

    private void initView() {
        refreshLayout.setOnMultiPurposeListener(simpleMultiPurposeListener);
        typeId = getIntent().getStringExtra(StaticData.TYPE_ID);
        orderMessageAdapter = new OrderMessageAdapter(this);
        orderMessageAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(orderMessageAdapter);
        msgInfoPresenter.getMsgInfo(StaticData.REFLASH_ONE, typeId);
    }

    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
            msgInfoPresenter.getMsgInfo(StaticData.REFLASH_ZERO, typeId);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            msgInfoPresenter.getMoreMsgInfo(typeId);
        }
    };

    @Override
    protected void initializeInjector() {
        DaggerMessageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .messageModule(new MessageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderMsgInfo(MessageInfo messageInfo) {
        refreshLayout.finishRefresh();
        if (messageInfo != null) {
            if (messageInfo != null && messageInfo.getMessageItemInfos().size() > 0) {
                recordRv.setVisibility(View.VISIBLE);
                noRecordLl.setVisibility(View.GONE);
                if (messageInfo.getMessageItemInfos().size() == Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                    refreshLayout.resetNoMoreData();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                orderMessageAdapter.setData(messageInfo.getMessageItemInfos());
            } else {
                recordRv.setVisibility(View.GONE);
                noRecordLl.setVisibility(View.VISIBLE);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void renderMoreMsgInfo(MessageInfo messageInfo) {
        refreshLayout.finishLoadMore();
        if (messageInfo != null && messageInfo.getMessageItemInfos() != null) {
            if (messageInfo.getMessageItemInfos().size() != Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            orderMessageAdapter.addMore(messageInfo.getMessageItemInfos());
        }
    }


    @OnClick({R.id.back, R.id.right_iv,R.id.clean_tv,R.id.mark_read_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.right_iv:
                isClick=!isClick;
                cleanLl.setVisibility(isClick?View.VISIBLE:View.GONE);
                break;
            case R.id.clean_tv://清空
                isClear=true;
                msgInfoPresenter.msgEmpty("-1");
                break;
            case R.id.mark_read_tv:
                msgInfoPresenter.msgChangeStatus("-1");
                break;
            default:
        }
    }

    @Override
    public void renderMsgEmpty() {
        showMessage(getString(R.string.delete_success));
        if(isClear){
            finish();
        }
    }

    @Override
    public void renderMsgChangeStatus() {
        isClick=false;
        cleanLl.setVisibility(isClick?View.VISIBLE:View.GONE);
    }

    @Override
    public void setPresenter(MessageContract.MsgInfoPresenter presenter) {

    }

    @Override
    public void goOrderDetails(String orderId,String id) {
        msgInfoPresenter.msgChangeStatus(id);
        GoodsOrderDetailsActivity.start(this,orderId);
    }

    @Override
    public void deleteItem(String id) {
        msgInfoPresenter.msgEmpty(id);
    }
}
