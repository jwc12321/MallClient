package com.mall.sls.member.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.PayResult;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.StaticHandler;
import com.mall.sls.common.unit.VerifyManager;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.LocalTeam;
import com.mall.sls.homepage.ui.ActivityGroupGoodsActivity;
import com.mall.sls.homepage.ui.SelectPayTypeActivity;
import com.mall.sls.member.DaggerMemberComponent;
import com.mall.sls.member.MemberContract;
import com.mall.sls.member.MemberModule;
import com.mall.sls.member.adapter.MemberGoodsItemAdapter;
import com.mall.sls.member.presenter.SuperMemberPresenter;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/12.
 * 描述：会员
 */
public class SuperMemberActivity extends BaseActivity implements MemberContract.SuperMemberView, MemberGoodsItemAdapter.OnItemClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.head_photo)
    RoundedImageView headPhoto;
    @BindView(R.id.phone)
    ConventionalTextView phone;
    @BindView(R.id.status)
    ConventionalTextView status;
    @BindView(R.id.description)
    ConventionalTextView description;
    @BindView(R.id.confirm_bt)
    ImageView confirmBt;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    private Handler mHandler = new MyHandler(this);

    private MemberGoodsItemAdapter memberGoodsItemAdapter;
    private String avatarUrl;
    private String mobile;
    @Inject
    SuperMemberPresenter superMemberPresenter;

    public static void start(Context context, String avatarUrl, String mobile) {
        Intent intent = new Intent(context, SuperMemberActivity.class);
        intent.putExtra(StaticData.AVATAR_URL, avatarUrl);
        intent.putExtra(StaticData.MOBILE, mobile);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_menber);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        avatarUrl = getIntent().getStringExtra(StaticData.AVATAR_URL);
        mobile = getIntent().getStringExtra(StaticData.MOBILE);
        GlideHelper.load(this, avatarUrl, R.mipmap.icon_defalut_head, headPhoto);
        phone.setText(mobile);
        memberGoodsItemAdapter = new MemberGoodsItemAdapter(this);
        memberGoodsItemAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(memberGoodsItemAdapter);
        confirmBt.setEnabled(TextUtils.equals(StaticData.REFLASH_THREE, VerifyManager.getVerify()) ? false : true);

        if (TextUtils.equals(StaticData.REFLASH_TWO, VerifyManager.getVerify())) {
            confirmBt.setEnabled(false);
            status.setText(getString(R.string.is_open));
        } else {
            confirmBt.setEnabled(true);
            status.setText(getString(R.string.nonactivated));
        }
        superMemberPresenter.getVipGroupons(StaticData.REFLASH_ONE);
    }

    @OnClick({R.id.confirm_bt, R.id.back, R.id.description})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                Intent intent = new Intent(this, SelectPayTypeActivity.class);
                intent.putExtra(StaticData.CHOICE_TYPE, StaticData.REFLASH_ONE);
                startActivityForResult(intent, RequestCodeStatic.PAY_TYPE);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.description:
                MemberDescriptionActivity.start(this, "附近的科技发达");
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.PAY_TYPE:
                    if (data != null) {
                        String selectType = data.getStringExtra(StaticData.SELECT_TYPE);
                        if (TextUtils.equals(StaticData.REFLASH_ZERO, selectType)) {
                            //微信
                            if (PayTypeInstalledUtils.isWeixinAvilible(SuperMemberActivity.this)) {

                            } else {
                                showMessage(getString(R.string.install_weixin));
                            }
                        } else {
                            if (PayTypeInstalledUtils.isAliPayInstalled(SuperMemberActivity.this)) {
                                superMemberPresenter.alipayMember(StaticData.REFLASH_ONE, selectType);
                            } else {
                                showMessage(getString(R.string.install_alipay));
                            }
                        }
                    }
                    break;
                default:
            }
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerMemberComponent.builder()
                .applicationComponent(getApplicationComponent())
                .memberModule(new MemberModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderVipGroupons(LocalTeam localTeam) {
        if (localTeam != null) {
            memberGoodsItemAdapter.setData(localTeam.getGoodsItemInfos());
        }
    }

    @Override
    public void renderMoreVipGroupons(LocalTeam localTeam) {

    }

    @Override
    public void renderAlipayMember(String alipayStr) {
        if(!TextUtils.isEmpty(alipayStr)) {
            startAliPay(alipayStr);
        }

    }

    @Override
    public void renderVipOpen(Boolean isBoolean) {
        if (isBoolean) {
            showMessage(getString(R.string.open_vip));
            VerifyManager.saveVerify(StaticData.REFLASH_THREE);
            confirmBt.setEnabled(false);
            status.setText(getString(R.string.is_open));
        }
    }

    @Override
    public void setPresenter(MemberContract.SuperMemberPresente presenter) {

    }

    @Override
    public void goActivityGroupGoods(String goodsId) {
        ActivityGroupGoodsActivity.start(this, goodsId);
    }

    private void startAliPay(String sign) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                PayTask payTask = new PayTask(SuperMemberActivity.this);
                Map<String, String> result = payTask.payV2(sign, true);
                Message message = Message.obtain();
                message.what = RequestCodeStatic.SDK_PAY_FLAG;
                message.obj = result;
                mHandler.sendMessage(message);
            }
        };

        new Thread(runnable).start();
    }

    public static class MyHandler extends StaticHandler<SuperMemberActivity> {

        public MyHandler(SuperMemberActivity target) {
            super(target);
        }

        @Override
        public void handle(SuperMemberActivity target, Message msg) {
            switch (msg.what) {
                case RequestCodeStatic.SDK_PAY_FLAG:
                    target.alpay(msg);
                    break;
            }
        }
    }

    //跳转到主页
    private void alpay(Message msg) {
        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
        String resultStatus = payResult.getResultStatus();
        Log.d("111", "数据" + payResult.getResult() + "==" + payResult.getResultStatus());
        if (TextUtils.equals(resultStatus, "9000")) {
            superMemberPresenter.vipOpen();
        } else if (TextUtils.equals(resultStatus, "6001")) {
            showMessage(getString(R.string.pay_cancel));
        } else {
            showMessage(getString(R.string.pay_failed));
        }
    }
}
