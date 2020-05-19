package com.mall.sls.member.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/19.
 * 描述：会员说明
 */
public class MemberDescriptionActivity extends BaseActivity {
    @BindView(R.id.content)
    ConventionalTextView content;
    @BindView(R.id.scrollview)
    NestedScrollView scrollview;
    @BindView(R.id.item_ll)
    LinearLayout itemLl;
    @BindView(R.id.all_rl)
    RelativeLayout allRl;

    public static void start(Context context, String commonContent) {
        Intent intent = new Intent(context, MemberDescriptionActivity.class);
        intent.putExtra(StaticData.COMMON_CONTENT, commonContent);
        context.startActivity(intent);
    }


    private String commonContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_description);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        commonContent = getIntent().getStringExtra(StaticData.COMMON_CONTENT);
        content.setText(commonContent);
    }


    @OnClick({R.id.all_rl, R.id.item_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.all_rl:
                finish();
                break;
            case R.id.item_ll:
                break;
            default:
        }
    }



    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
