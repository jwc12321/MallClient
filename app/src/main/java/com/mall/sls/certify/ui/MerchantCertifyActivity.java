package com.mall.sls.certify.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.address.ui.SelectAddressActivity;
import com.mall.sls.certify.CertifyContract;
import com.mall.sls.certify.CertifyModule;
import com.mall.sls.certify.DaggerCertifyComponent;
import com.mall.sls.certify.presenter.MerchantCertifyPresenter;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.CameraUnit;
import com.mall.sls.common.unit.GlideEngine;
import com.mall.sls.common.unit.MainStartManager;
import com.mall.sls.common.unit.PermissionUtil;
import com.mall.sls.common.widget.scrollview.ReboundScrollView;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.MerchantCertifyInfo;
import com.mall.sls.data.entity.UploadUrlInfo;
import com.mall.sls.mainframe.ui.MainFrameActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;


/**
 * @author jwc on 2020/10/22.
 * 描述：
 */
public class MerchantCertifyActivity extends BaseActivity implements CertifyContract.MerchantCertifyView {


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
    @BindView(R.id.number_rl)
    RelativeLayout numberRl;
    @BindView(R.id.verify_tv_first)
    ConventionalTextView verifyTvFirst;
    @BindView(R.id.verify_tv_second)
    ConventionalTextView verifyTvSecond;
    @BindView(R.id.verify_tv_third)
    ConventionalTextView verifyTvThird;
    @BindView(R.id.verify_tv_rl)
    RelativeLayout verifyTvRl;
    @BindView(R.id.business_license_iv)
    ImageView businessLicenseIv;
    @BindView(R.id.door_photos_iv)
    ImageView doorPhotosIv;
    @BindView(R.id.city_tv)
    ConventionalTextView cityTv;
    @BindView(R.id.right_arrow_iv)
    ImageView rightArrowIv;
    @BindView(R.id.city_rv)
    RelativeLayout cityRv;
    @BindView(R.id.detail_address_et)
    ConventionalEditTextView detailAddressEt;
    @BindView(R.id.next_bt)
    MediumThickTextView nextBt;
    @BindView(R.id.business_license_delete_iv)
    ImageView businessLicenseDeleteIv;
    @BindView(R.id.door_photos_delete_iv)
    ImageView doorPhotosDeleteIv;
    @BindView(R.id.item_rl)
    RelativeLayout itemRl;
    @BindView(R.id.result_iv)
    ImageView resultIv;
    @BindView(R.id.result_tv)
    ConventionalTextView resultTv;
    @BindView(R.id.result_reason)
    ConventionalTextView resultReason;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.result_ll)
    LinearLayout resultLl;
    @BindView(R.id.upload_ll)
    LinearLayout uploadLl;
    private String photoType; //1:上传营业执照 2：上传门头照片
    private CameraUnit cameraUnit;
    private int chooseMode = PictureMimeType.ofImage();
    private int themeId;
    private int maxSelectNum = 9;
    private List<LocalMedia> selectList;
    private String file;
    private String businessLicense;
    private String doorHeader;
    private String businessLicenseFile;
    private String doorHeaderFile;
    private String address;
    private String detailAddress;
    private String failReason;
    private String merchantStatus;
    private List<String> group;
    private String province;
    private String city;
    private String county;

    @Inject
    MerchantCertifyPresenter merchantCertifyPresenter;

    public static void start(Context context, String merchantStatus, String failReason) {
        Intent intent = new Intent(context, MerchantCertifyActivity.class);
        intent.putExtra(StaticData.MERCHANT_STATUS, merchantStatus);
        intent.putExtra(StaticData.FAIL_REASON, failReason);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_certify);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        failReason = getIntent().getStringExtra(StaticData.FAIL_REASON);
        merchantStatus = getIntent().getStringExtra(StaticData.MERCHANT_STATUS);
        cameraUnit = new CameraUnit(this);
        selectList = new ArrayList<>();
        group = new ArrayList<>();
        themeId = R.style.picture_WeChat_style;
        authStatus();
        if (TextUtils.isEmpty(merchantStatus) || TextUtils.equals(StaticData.MERCHANT_CERTIFY_SUCCESS, merchantStatus) || TextUtils.equals(StaticData.MERCHANT_CERTIFY_CANCEL, merchantStatus)) {
            uploadLl.setVisibility(View.VISIBLE);
            resultLl.setVisibility(View.GONE);
            nextBt.setVisibility(View.VISIBLE);
            confirmBt.setVisibility(View.GONE);
        } else {
            uploadLl.setVisibility(View.GONE);
            resultLl.setVisibility(View.VISIBLE);
            nextBt.setVisibility(View.GONE);
            confirmBt.setVisibility(View.VISIBLE);
            if (TextUtils.equals(StaticData.MERCHANT_CERTIFY_FAIL, merchantStatus)) {
                resultIv.setSelected(false);
                resultTv.setText(getString(R.string.certify_fail));
                resultReason.setText(failReason);
                confirmBt.setText(getString(R.string.recertify));
            } else if (TextUtils.equals(StaticData.MERCHANT_CERTIFY_WAIT, merchantStatus)) {
                resultIv.setSelected(true);
                resultTv.setText(getString(R.string.certify_submit));
                resultReason.setText(getString(R.string.certify_wait));
                confirmBt.setText(getString(R.string.view_homepage));
            }
        }
        merchantCertifyPresenter.getMerchantCertifyInfo();
    }

    @OnTextChanged({R.id.detail_address_et})
    public void detailAddressEnable() {
        detailAddress = detailAddressEt.getText().toString().trim();
    }

    @Override
    public View getSnackBarHolderView() {
        return itemRl;
    }

    @Override
    protected void initializeInjector() {
        DaggerCertifyComponent.builder()
                .applicationComponent(getApplicationComponent())
                .certifyModule(new CertifyModule(this))
                .build()
                .inject(this);

    }

    private void authStatus() {
        verifyIvFirst.setSelected(true);
        verifyTvFirst.setSelected(true);
        verifyLineFirst.setSelected(true);
        verifyLineSecond.setSelected(false);
        verifyTvSecond.setSelected(true);
        verifyIvSecond.setSelected(true);
        verifyTvThird.setSelected(false);
        verifyIvThird.setSelected(false);
    }

    @OnClick({R.id.back, R.id.business_license_iv, R.id.door_photos_iv, R.id.city_rv, R.id.next_bt, R.id.door_photos_delete_iv, R.id.business_license_delete_iv, R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.business_license_iv://上传营业执照
                photoType = StaticData.REFRESH_ONE;
                selectPhoto();
                break;
            case R.id.door_photos_iv://上传门头照片
                photoType = StaticData.REFRESH_TWO;
                selectPhoto();
                break;
            case R.id.city_rv://选择地址
                group.clear();
                group.add(Manifest.permission_group.LOCATION);
                if (requestRuntimePermissions(PermissionUtil.permissionGroup(group, null), RequestCodeStatic.REQUEST_PERMISSION_LOCATION)) {
                    Intent intent = new Intent(this, SelectAddressActivity.class);
                    startActivityForResult(intent, RequestCodeStatic.SELECT_LAT_LON);
                }
                break;
            case R.id.next_bt://下一步
                confirm();
                break;
            case R.id.door_photos_delete_iv:
                doorHeader = "";
                doorHeaderFile = "";
                GlideHelper.load(this, doorHeaderFile, R.mipmap.icon_upload_photo, doorPhotosIv);
                doorPhotosDeleteIv.setVisibility(View.GONE);
                nextBtEnable();
                break;
            case R.id.business_license_delete_iv:
                businessLicense = "";
                businessLicenseFile = "";
                GlideHelper.load(this, businessLicenseFile, R.mipmap.icon_upload_photo, businessLicenseIv);
                businessLicenseDeleteIv.setVisibility(View.GONE);
                nextBtEnable();
                break;
            case R.id.confirm_bt:
                if (TextUtils.equals(StaticData.MERCHANT_CERTIFY_WAIT, merchantStatus)) {
                    MainStartManager.saveMainStart(StaticData.REFRESH_ZERO);
                    MainFrameActivity.start(this);
                } else {
                    resultLl.setVisibility(View.GONE);
                    uploadLl.setVisibility(View.VISIBLE);
                    nextBt.setVisibility(View.VISIBLE);
                    confirmBt.setVisibility(View.GONE);
                }
                break;
            default:
        }
    }

    private void confirm() {
        if (TextUtils.isEmpty(businessLicense)) {
            showMessage(getString(R.string.please_upload_business_license));
            return;
        }
        if (TextUtils.isEmpty(doorHeader)) {
            showMessage(getString(R.string.please_upload_door_head_photos));
            return;
        }
        if (TextUtils.isEmpty(address) || TextUtils.isEmpty(detailAddress)) {
            showMessage(getString(R.string.select_store));
            return;
        }
        merchantCertifyPresenter.merchantCertify(businessLicense, doorHeader, address, detailAddress);
    }

    private void selectPhoto() {
        group.clear();
        group.add(Manifest.permission_group.CAMERA);
        group.add(Manifest.permission_group.STORAGE);
        if (requestRuntimePermissions(PermissionUtil.permissionGroup(group, null), RequestCodeStatic.REQUEST_CAMERA)) {
            openAlbum();
        } else {
            Toast.makeText(this, "没有可用的相机", Toast.LENGTH_SHORT).show();
        }
    }

    private void openAlbum() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(MerchantCertifyActivity.this)
                .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .isWeChatStyle(true)// 是否开启微信图片选择风格
                .isUseCustomCamera(false)// 是否使用自定义相机
                .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .maxVideoSelectNum(1) // 视频最大选择数量
                .imageSpanCount(4)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
                .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 如果视频有旋转角度则对换宽高,默认为false
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .isPreviewImage(true)// 是否可预览图片
                .isPreviewVideo(false)// 是否可预览视频
                .isCamera(true)// 是否显示拍照按钮
                .isEnableCrop(false)// 是否裁剪
                .isCompress(true)// 是否压缩
                .synOrAsy(false)//同步true或异步false 压缩 默认同步
//                .selectionData(selectList)// 是否传入已选图片
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    private final static String TAG = MerchantCertifyActivity.class.getSimpleName();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    setPhoto(selectList);
                    break;
                case RequestCodeStatic.SELECT_LAT_LON://
                    if (data != null) {
                        province = data.getStringExtra(StaticData.PROVINCE);
                        city = data.getStringExtra(StaticData.CITY);
                        county = data.getStringExtra(StaticData.COUNT);
                        detailAddress = data.getStringExtra(StaticData.DETAIL_ADDRESS) + data.getStringExtra(StaticData.ADDRESS_TITLE);
                        address = province + city + county;
                        cityTv.setText(address);
                        initDetailAddress();
                        nextBtEnable();
                    }
                    break;
            }
        }
    }


    private void setPhoto(List<LocalMedia> selectList) {
        if (selectList != null && selectList.size() > 0) {
            file = selectList.get(0).getCompressPath();
            if (TextUtils.equals(StaticData.REFRESH_ONE, photoType)) {
                businessLicenseFile = file;
                GlideHelper.load(this, businessLicenseFile, R.mipmap.icon_upload_photo, businessLicenseIv);
                businessLicenseDeleteIv.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(StaticData.REFRESH_TWO, photoType)) {
                doorHeaderFile = file;
                GlideHelper.load(this, doorHeaderFile, R.mipmap.icon_upload_photo, doorPhotosIv);
                doorPhotosDeleteIv.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(file)) {
                merchantCertifyPresenter.uploadFile(file, StaticData.MERCHANT);
            }
        }
    }

    @Override
    public void renderMerchantCertifyInfo(MerchantCertifyInfo merchantCertifyInfo) {
        if (merchantCertifyInfo != null) {
            businessLicense = merchantCertifyInfo.getBusinessLicense();
            doorHeader = merchantCertifyInfo.getDoorHeader();
            businessLicenseDeleteIv.setVisibility(TextUtils.isEmpty(businessLicense) ? View.GONE : View.VISIBLE);
            doorPhotosDeleteIv.setVisibility(TextUtils.isEmpty(doorHeader) ? View.GONE : View.VISIBLE);
            GlideHelper.load(this, businessLicense, R.mipmap.icon_upload_photo, businessLicenseIv);
            GlideHelper.load(this, doorHeader, R.mipmap.icon_upload_photo, doorPhotosIv);
            detailAddress = merchantCertifyInfo.getDetail();
            address = merchantCertifyInfo.getAddress();
            cityTv.setText(address);
            detailAddressEt.setText(detailAddress);
            nextBtEnable();
        }
    }

    @Override
    public void renderMerchantCertify(Boolean isBoolean) {
        if (isBoolean) {
            merchantStatus = StaticData.MERCHANT_CERTIFY_WAIT;
            uploadLl.setVisibility(View.GONE);
            resultLl.setVisibility(View.VISIBLE);
            nextBt.setVisibility(View.GONE);
            confirmBt.setVisibility(View.VISIBLE);
            resultIv.setSelected(true);
            resultTv.setText(getString(R.string.certify_submit));
            resultReason.setText(getString(R.string.certify_wait));
            confirmBt.setText(getString(R.string.view_homepage));
        }
    }

    @Override
    public void renderUploadFile(UploadUrlInfo uploadUrlInfo) {
        if (uploadUrlInfo != null) {
            if (TextUtils.equals(StaticData.REFRESH_ONE, photoType)) {
                businessLicense = uploadUrlInfo.getUrl();
            } else if (TextUtils.equals(StaticData.REFRESH_TWO, photoType)) {
                doorHeader = uploadUrlInfo.getUrl();
            }
            nextBtEnable();
        }
    }

    @Override
    public void setPresenter(CertifyContract.MerchantCertifyPresenter presenter) {

    }

    private void initDetailAddress() {
        detailAddressEt.setFocusable(true);
        detailAddressEt.setFocusableInTouchMode(true);
        detailAddressEt.requestFocus();
        detailAddressEt.setText(detailAddress);
        if (!TextUtils.isEmpty(detailAddress)) {
            detailAddressEt.setSelection(detailAddress.length());//将光标移至文字末尾
        }
    }

    private void nextBtEnable() {
        if (!TextUtils.isEmpty(businessLicense) && !TextUtils.isEmpty(doorHeader) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(detailAddress)) {
            nextBt.setEnabled(true);
        } else {
            nextBt.setEnabled(false);
        }
    }
}
