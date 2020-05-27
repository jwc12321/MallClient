package com.mall.sls.homepage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.MediumThickTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/19.
 * 描述：
 */
public class AloneGroupActivity extends BaseActivity {
    @BindView(R.id.close_iv)
    ImageView closeIv;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.weixin_iv)
    ImageView weixinIv;

    private String goodsProductId;
    private String grouponId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alone_group);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        goodsProductId=getIntent().getStringExtra(StaticData.GOODS_PRODUCT_ID);
        grouponId=getIntent().getStringExtra(StaticData.GROUPON_ID);
    }

    @OnClick({R.id.close_iv,R.id.weixin_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.weixin_iv:
                WXGoodsDetailsActivity.start(this,goodsProductId,grouponId);
                break;
            default:
        }
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
