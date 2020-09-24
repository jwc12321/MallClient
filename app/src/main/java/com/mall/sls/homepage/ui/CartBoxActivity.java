package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.data.entity.HiddenItemCartInfo;
import com.mall.sls.homepage.adapter.HiddenCartGoodsAdapter;
import java.io.Serializable;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/9/24.
 * 描述：
 */
public class CartBoxActivity extends BaseActivity {
    @BindView(R.id.title)
    ConventionalTextView title;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.hidden_number)
    ConventionalTextView hiddenNumber;
    @BindView(R.id.left_bt)
    ConventionalTextView leftBt;
    @BindView(R.id.right_bt)
    ConventionalTextView rightBt;

    private String leftBtText;
    private String rightBtText;
    private String commonTitle;
    private String totalNumber;
    private List<HiddenItemCartInfo> hiddenItemCartInfos;
    private HiddenCartGoodsAdapter hiddenCartGoodsAdapter;

    public static void start(Context context,String commonTitle,String leftBtText,String rightBtText,String totalNumber,List<HiddenItemCartInfo> hiddenItemCartInfos) {
        Intent intent = new Intent(context, CartBoxActivity.class);
        intent.putExtra(StaticData.COMMON_TITLE, commonTitle);
        intent.putExtra(StaticData.LEFT_BT_TEXT, leftBtText);
        intent.putExtra(StaticData.RIGHT_BT_TEXT, rightBtText);
        intent.putExtra(StaticData.TOTAL_NUMBER,totalNumber);
        intent.putExtra(StaticData.HIDDEN_CART_INFO_S, (Serializable) hiddenItemCartInfos);
        ((Activity)context).startActivityForResult(intent, RequestCodeStatic.HIDDEN_CART);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_box);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        commonTitle=getIntent().getStringExtra(StaticData.COMMON_TITLE);
        leftBtText=getIntent().getStringExtra(StaticData.LEFT_BT_TEXT);
        rightBtText=getIntent().getStringExtra(StaticData.RIGHT_BT_TEXT);
        totalNumber=getIntent().getStringExtra(StaticData.TOTAL_NUMBER);
        hiddenItemCartInfos= (List<HiddenItemCartInfo>) getIntent().getSerializableExtra(StaticData.HIDDEN_CART_INFO_S);
        title.setText(commonTitle);
        leftBt.setText(leftBtText);
        rightBt.setText(rightBtText);
        hiddenCartGoodsAdapter = new HiddenCartGoodsAdapter(this);
        recordRv.setLayoutManager(new GridLayoutManager(this, 3));
        recordRv.setAdapter(hiddenCartGoodsAdapter);
        hiddenCartGoodsAdapter.setData(hiddenItemCartInfos);
        hiddenNumber.setText(hiddenItemCartInfos.size()+"件  (共"+totalNumber+"件)");
    }

    @OnClick({R.id.left_bt, R.id.right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_bt:
                returnData(StaticData.REFRESH_ZERO);
                break;
            case R.id.right_tv:
                returnData(StaticData.REFRESH_ONE);
                break;
            default:
        }
    }

    private void returnData(String type){
        Intent intent = new Intent();
        intent.putExtra(StaticData.HIDDEN_TYPE,type);
        setResult(RESULT_OK, intent);
        finish();
    }



    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
