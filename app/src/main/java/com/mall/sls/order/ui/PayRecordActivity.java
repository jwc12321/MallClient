package com.mall.sls.order.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
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
import com.mall.sls.bank.ui.PhoneTipActivity;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.PayRecordInfo;
import com.mall.sls.order.adapter.PayRecordAdapter;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/10/10.
 * 描述：
 */
public class PayRecordActivity extends BaseActivity implements PayRecordAdapter.OnItemClickListener{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.order_number_tv)
    MediumThickTextView orderNumberTv;
    @BindView(R.id.copy_bt)
    MediumThickTextView copyBt;
    @BindView(R.id.order_number_rl)
    RelativeLayout orderNumberRl;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.no_record_ll)
    LinearLayout noRecordLl;
    @BindView(R.id.item_ll)
    LinearLayout itemLl;

    private List<PayRecordInfo> payRecordInfos;
    private PayRecordAdapter payRecordAdapter;
    private String orderNumber;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    public static void start(Context context, List<PayRecordInfo> payRecordInfos,String orderNumber) {
        Intent intent = new Intent(context, PayRecordActivity.class);
        intent.putExtra(StaticData.PAY_RECORD_INFO, (Serializable) payRecordInfos);
        intent.putExtra(StaticData.ORDER_NUMBER,orderNumber);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_record);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        payRecordInfos= (List<PayRecordInfo>) getIntent().getSerializableExtra(StaticData.PAY_RECORD_INFO);
        orderNumber=getIntent().getStringExtra(StaticData.ORDER_NUMBER);
        orderNumberTv.setText(orderNumber);
        payRecordAdapter=new PayRecordAdapter(this);
        payRecordAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(payRecordAdapter);
        if(payRecordInfos!=null&&payRecordInfos.size()>0){
            recordRv.setVisibility(View.VISIBLE);
            noRecordLl.setVisibility(View.GONE);
            payRecordAdapter.setData(payRecordInfos);
        }else {
            recordRv.setVisibility(View.GONE);
            noRecordLl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back,R.id.copy_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.copy_bt:
                myClip = ClipData.newPlainText("text", orderNumber);
                myClipboard.setPrimaryClip(myClip);
                showMessage(getString(R.string.copy_successfully));
                break;
            default:
        }
    }

    @Override
    public void copyOrderNo(String payNo) {
        myClip = ClipData.newPlainText("text", payNo);
        myClipboard.setPrimaryClip(myClip);
        showMessage(getString(R.string.copy_successfully));
    }
}
