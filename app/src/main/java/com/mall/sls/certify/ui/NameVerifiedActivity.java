package com.mall.sls.certify.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.aliyun.aliyunface.api.ZIMCallback;
import com.aliyun.aliyunface.api.ZIMFacade;
import com.aliyun.aliyunface.api.ZIMFacadeBuilder;
import com.aliyun.aliyunface.api.ZIMResponse;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.certify.CertifyContract;
import com.mall.sls.certify.CertifyModule;
import com.mall.sls.certify.DaggerCertifyComponent;
import com.mall.sls.certify.presenter.NameVerifiedPresenter;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.HideUnit;
import com.mall.sls.common.unit.RegularUnit;
import com.mall.sls.common.unit.VerifyManager;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.homepage.ui.BackTipActivity;
import com.mall.sls.webview.ui.WebViewActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

//实人认证
public class NameVerifiedActivity extends BaseActivity implements CertifyContract.NameVerifiedView {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.verify_iv_first)
    ConventionalTextView verifyIvFirst;
    @BindView(R.id.verify_line_first)
    View verifyLineFirst;
    @BindView(R.id.verify_iv_second)
    ConventionalTextView verifyIvSecond;
    @BindView(R.id.verify_line_second)
    View verifyLineSecond;
    @BindView(R.id.verify_iv_third)
    ConventionalTextView verifyIvThird;
    @BindView(R.id.verify_tv_first)
    ConventionalTextView verifyTvFirst;
    @BindView(R.id.verify_tv_second)
    ConventionalTextView verifyTvSecond;
    @BindView(R.id.verify_tv_third)
    ConventionalTextView verifyTvThird;
    @BindView(R.id.name_iv)
    ImageView nameIv;
    @BindView(R.id.certName_et)
    ConventionalEditTextView certNameEt;
    @BindView(R.id.id_iv)
    ImageView idIv;
    @BindView(R.id.certNo_et)
    ConventionalEditTextView certNoEt;
    @BindView(R.id.next_bt)
    TextView nextBt;
    @BindView(R.id.first_ll)
    LinearLayout firstLl;
    @BindView(R.id.result_success_ll)
    LinearLayout resultSuccessLl;
    @BindView(R.id.result_ll)
    LinearLayout resultLl;
    @BindView(R.id.status_iv)
    ImageView statusIv;
    @BindView(R.id.status_tv)
    ConventionalTextView statusTv;
    @BindView(R.id.auth_again_bt)
    TextView authAgainBt;
    @BindView(R.id.name_tv)
    ConventionalTextView nameTv;
    @BindView(R.id.face_bt)
    TextView faceBt;
    @BindView(R.id.second_ll)
    LinearLayout secondLl;
    @BindView(R.id.real_name)
    ConventionalTextView realName;
    @BindView(R.id.real_id)
    ConventionalTextView realId;
    @BindView(R.id.confirm_bt)
    TextView confirmBt;
    @BindView(R.id.protocol_tv)
    ConventionalTextView protocolTv;
    private String metaInfos;
    private String certName;
    private String certNo;
    private int pageType = 1;

    @Inject
    NameVerifiedPresenter nameVerifiedPresenter;
    private String certifyId;

    public static void start(Context context) {
        Intent intent = new Intent(context, NameVerifiedActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_verified);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        pageType = 1;
        ZIMFacade.install(this);
        metaInfos = ZIMFacade.getMetaInfos(this);
        authStatus(pageType);
    }

    @Override
    protected void initializeInjector() {
        DaggerCertifyComponent.builder()
                .applicationComponent(getApplicationComponent())
                .certifyModule(new CertifyModule(this))
                .build()
                .inject(this);

    }

    //1:填写姓名身份证 2：显示采集本人人脸按钮 3：成功 4：失败
    private void authStatus(int type) {
        verifyIvFirst.setSelected(true);
        verifyTvFirst.setSelected(true);
        verifyLineFirst.setSelected(type > 1);
        verifyLineSecond.setSelected(type == 3);
        verifyTvSecond.setSelected(type != 1);
        verifyIvSecond.setSelected(type != 1);
        verifyTvThird.setSelected(type == 3);
        verifyIvThird.setSelected(type == 3);
        firstLl.setVisibility(type == 1 ? View.VISIBLE : View.GONE);
        secondLl.setVisibility(type == 2 ? View.VISIBLE : View.GONE);
        resultLl.setVisibility((type == 3 || type == 4) ? View.VISIBLE : View.GONE);
        statusIv.setSelected(type == 3 ? true : false);
        if (type == 3) {
            statusTv.setText(getString(R.string.verfiy_success));
        } else {
            statusTv.setText(getString(R.string.verfiy_failed));
        }
        authAgainBt.setVisibility(type == 4 ? View.VISIBLE : View.GONE);
        resultSuccessLl.setVisibility(type == 3 ? View.VISIBLE : View.GONE);
    }

    /**
     * 监听手机输入框
     */
    @OnTextChanged({R.id.certName_et})
    public void checkCertNameEnable() {
        certName = certNameEt.getText().toString().trim();
        nextBt.setEnabled(!TextUtils.isEmpty(certName) && !TextUtils.isEmpty(certNo));
    }

    /**
     * 监听手机输入框
     */
    @OnTextChanged({R.id.certNo_et})
    public void checkCertNoEnable() {
        certNo = certNoEt.getText().toString().trim();
        nextBt.setEnabled(!TextUtils.isEmpty(certName) && !TextUtils.isEmpty(certNo));
    }

    @OnClick({R.id.back, R.id.next_bt, R.id.auth_again_bt, R.id.face_bt, R.id.confirm_bt,R.id.protocol_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                if (pageType == 2) {
                    Intent intent = new Intent(this, BackTipActivity.class);
                    intent.putExtra(StaticData.COMMON_TITLE, getString(R.string.first_back_verify));
                    startActivityForResult(intent, RequestCodeStatic.BACK_VERIFY);
                } else {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            case R.id.confirm_bt:
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.next_bt:
                if (!RegularUnit.validateName(certName)) {
                    showMessage(getString(R.string.regular_name));
                    return;
                }
                if (!RegularUnit.vaildateIdCard(certNo)) {
                    showMessage(getString(R.string.regular_id_card));
                    return;
                }
                nameVerifiedPresenter.getCertifyId(certName, certNo, metaInfos);
                break;
            case R.id.auth_again_bt://重新认证
                pageType = 1;
                authStatus(pageType);
                break;
            case R.id.face_bt:
                verifyId();
                break;
            case R.id.protocol_tv:
                WebViewActivity.start(this,StaticData.OCR_AGREEMENT);
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.BACK_VERIFY:
                    pageType = 1;
                    authStatus(pageType);
                    break;
                default:
            }
        }
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderCertifyId(String certifyId) {
        this.certifyId = certifyId;
        pageType = 2;
        authStatus(pageType);
        String hideName = HideUnit.hideLastName(certName) + "、" + HideUnit.hideIdCard(certNo);
        nameTv.setText(hideName);
    }

    private void verifyId() {
        if (!TextUtils.isEmpty(certifyId)) {
            ZIMFacade zimFacade = ZIMFacadeBuilder.create(NameVerifiedActivity.this);
            zimFacade.verify(certifyId, true, new ZIMCallback() {
                @Override
                public boolean response(ZIMResponse response) {
                    // TODO: 接入方在这里实现自身业务
                    if (null != response && 1000 == response.code) {
                        showMessage("认证成功");
                        // 认证成功
                        pageType = 3;
                        authStatus(pageType);
                        VerifyManager.saveVerify(StaticData.REFRESH_ONE);
                        realName.setText(HideUnit.hideLastName(certName));
                        realId.setText(HideUnit.hideIdCard(certNo));
                        nameVerifiedPresenter.getUsersRpStatus();
                    } else {
                        // 认证失败
                        pageType = 4;
                        showMessage("失败");
                        authStatus(pageType);
                        VerifyManager.saveVerify(StaticData.REFRESH_ZERO);
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void renderUsesRpStatus(Boolean isBoolean) {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() != KeyEvent.ACTION_UP) {//不响应按键抬起时的动作
            if (pageType == 2) {
                Intent intent = new Intent(this, BackTipActivity.class);
                intent.putExtra(StaticData.COMMON_TITLE, getString(R.string.first_back_verify));
                startActivityForResult(intent, RequestCodeStatic.BACK_VERIFY);
            } else {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
            return false;//表示不分发
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void setPresenter(CertifyContract.NameVerifiedPresenter presenter) {

    }
}
