package com.mall.sls.mine.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.QRCodeFileUtils;
import com.mall.sls.common.unit.WXShareManager;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.ShareInfo;
import com.mall.sls.mine.DaggerMineComponent;
import com.mall.sls.mine.MineContract;
import com.mall.sls.mine.MineModule;
import com.mall.sls.mine.presenter.ShareInfoPresenter;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/26.
 * 描述：邀请好友s
 */
public class InviteFriendsActivity extends BaseActivity implements MineContract.ShareInfoView {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.look_iv)
    ImageView lookIv;
    @BindView(R.id.code_tv)
    MediumThickTextView codeTv;
    @BindView(R.id.code)
    MediumThickTextView code;
    @BindView(R.id.copy_bt)
    MediumThickTextView copyBt;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.qrcode_iv)
    ImageView qrcodeIv;
    @BindView(R.id.share_rl)
    RelativeLayout shareRl;
    @BindView(R.id.link_bt)
    ImageView linkBt;
    @BindView(R.id.poster_bt)
    ImageView posterBt;
    private Bitmap bitmap;


    @Inject
    ShareInfoPresenter shareInfoPresenter;

    private ClipboardManager myClipboard;
    private ClipData myClip;
    private String inviteCode;
    private String shareUrl;
    private String backType;
    private Bitmap posterBitmap;
    private WXShareManager wxShareManager;

    public static void start(Context context) {
        Intent intent = new Intent(context, InviteFriendsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        wxShareManager = WXShareManager.getInstance(this);
        shareInfoPresenter.getShareInfo();
    }

    @Override
    protected void initializeInjector() {
        DaggerMineComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mineModule(new MineModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.back, R.id.copy_bt, R.id.link_bt, R.id.poster_bt, R.id.look_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.copy_bt://复制邀请码
                myClip = ClipData.newPlainText("text", inviteCode);
                myClipboard.setPrimaryClip(myClip);
                showMessage(getString(R.string.copy_successfully));
                break;
            case R.id.link_bt://复制链接地址
                myClip = ClipData.newPlainText("text", shareUrl);
                myClipboard.setPrimaryClip(myClip);
                showMessage(getString(R.string.copy_successfully));
                break;
            case R.id.poster_bt:
                if (!PayTypeInstalledUtils.isWeixinAvilible(InviteFriendsActivity.this)) {
                    showMessage(getString(R.string.install_weixin));
                    return;
                }
                posterBitmap = QRCodeFileUtils.createBitmap2(shareRl);
                Intent intent = new Intent(this, SelectShareTypeActivity.class);
                startActivityForResult(intent, RequestCodeStatic.SELECT_SHARE_TYPE);
                break;
            case R.id.look_iv:
                MyInvitationActivity.start(this);
                break;
            default:
        }
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderShareInfo(ShareInfo shareInfo) {
        if (shareInfo != null) {
            inviteCode = shareInfo.getInvitationCode();
            shareUrl = shareInfo.getShareUrl();
            code.setText(inviteCode);
            GlideHelper.load(this, shareInfo.getPhotoUrl(), R.mipmap.icon_poster, iv);
            bitmap = QRCodeFileUtils.createQRCodeBitmap(shareUrl, qrcodeIv.getWidth(), qrcodeIv.getHeight(), "0");
            qrcodeIv.setImageBitmap(bitmap);
        }
    }

    @Override
    public void setPresenter(MineContract.ShareInfoPresenter presenter) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.SELECT_SHARE_TYPE:
                    if (data != null) {
                        backType = data.getStringExtra(StaticData.BACK_TYPE);
                        WXImageObject imgObj = new WXImageObject(posterBitmap);
                        if (TextUtils.equals(StaticData.REFRESH_ONE, backType)) {//朋友圈
                            wxShareManager.sharePictureToWX(imgObj, posterBitmap, true);
                        } else {//好友
                            wxShareManager.sharePictureToWX(imgObj, posterBitmap, false);
                        }
                    }
                    break;
                default:
            }
        }
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
