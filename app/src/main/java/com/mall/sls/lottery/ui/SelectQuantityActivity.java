package com.mall.sls.lottery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/6/9.
 * 描述：
 */
public class SelectQuantityActivity extends BaseActivity {
    @BindView(R.id.goods_iv)
    ImageView goodsIv;
    @BindView(R.id.close_iv)
    ImageView closeIv;
    @BindView(R.id.goods_name)
    MediumThickTextView goodsName;
    @BindView(R.id.goods_rl)
    RelativeLayout goodsRl;
    @BindView(R.id.decrease_count)
    ImageView decreaseCount;
    @BindView(R.id.goods_count)
    ConventionalTextView goodsCount;
    @BindView(R.id.increase_count)
    ImageView increaseCount;
    @BindView(R.id.remaining_times)
    ConventionalTextView remainingTimes;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.item_rl)
    LinearLayout itemRl;
    @BindView(R.id.all_rl)
    RelativeLayout allRl;

    private String prizeNumber;
    private String name;
    private String picUrl;
    private int currentCount=1;
    private int prizeNumberInt;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_quantity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        prizeNumber = getIntent().getStringExtra(StaticData.PRIZE_NUMBER);
        prizeNumberInt=Integer.parseInt(prizeNumber);
        name=getIntent().getStringExtra(StaticData.GOODS_NAME);
        picUrl=getIntent().getStringExtra(StaticData.PIC_URL);
        goodsName.setText(name);
        GlideHelper.load(this, picUrl, R.mipmap.icon_default_goods, goodsIv);
        remainingTimes.setText(prizeNumber);
    }


    @OnClick({ R.id.confirm_bt,R.id.close_iv,R.id.decrease_count,R.id.increase_count})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
                finish();
                break;
            case R.id.confirm_bt:
                Intent intent = new Intent();
                intent.putExtra(StaticData.COUNT,String.valueOf(currentCount));
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.decrease_count://数量减少
                if (currentCount == 1||currentCount==0) {
                    return;
                }
                currentCount--;
                goodsCount.setText(String.valueOf(currentCount));
                remainingTimes.setText(String.valueOf(prizeNumberInt-currentCount));
                break;
            case R.id.increase_count://数量增加
                if(currentCount==prizeNumberInt){
                    return;
                }
                currentCount++;
                goodsCount.setText(String.valueOf(currentCount));
                remainingTimes.setText(String.valueOf(prizeNumberInt-currentCount));
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
