package com.mall.sls.message.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.MessageTypeInfo;
import com.mall.sls.message.DaggerMessageComponent;
import com.mall.sls.message.MessageContract;
import com.mall.sls.message.MessageModule;
import com.mall.sls.message.adapter.MsgTypeAdapter;
import com.mall.sls.message.presenter.MsgTypePresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/19.
 * 描述：
 */
public class MessageTypeActivity extends BaseActivity implements MessageContract.MsgTypeView,MsgTypeAdapter.OnItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;

    @Inject
    MsgTypePresenter msgTypePresenter;
    private MsgTypeAdapter msgTypeAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, MessageTypeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_type);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        msgTypeAdapter=new MsgTypeAdapter(this);
        msgTypeAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(msgTypeAdapter);
        msgTypePresenter.getMsgType();
    }

    @Override
    protected void initializeInjector() {
        DaggerMessageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .messageModule(new MessageModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent backIntent = new Intent();
                setResult(Activity.RESULT_OK, backIntent);
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
    public void renderMsgType(List<MessageTypeInfo> messageTypeInfos) {
        msgTypeAdapter.setData(messageTypeInfos);
    }

    @Override
    public void setPresenter(MessageContract.MsgTypePresenter presenter) {

    }

    @Override
    public void goOrderMessage(String id) {
        OrderMessageActivity.start(this,id);
    }

    @Override
    public void goReminderMessage(String id) {
        ReminderMessageActivity.start(this,id);
    }
}
