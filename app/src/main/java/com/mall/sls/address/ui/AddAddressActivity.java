package com.mall.sls.address.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.address.AddressContract;
import com.mall.sls.address.AddressModule;
import com.mall.sls.address.DaggerAddressComponent;
import com.mall.sls.address.presenter.AddAddressPresenter;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.address.AreaPickerView;
import com.mall.sls.common.unit.PermissionUtil;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.AddressInfo;
import com.mall.sls.data.entity.ProvinceBean;
import com.mall.sls.data.request.AddAddressRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author jwc on 2020/5/8.
 * 描述：添加地址
 */
public class AddAddressActivity extends BaseActivity implements AddressContract.AddAddressView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.name_et)
    ConventionalEditTextView nameEt;
    @BindView(R.id.ms_iv)
    ImageView msIv;
    @BindView(R.id.men_iv)
    ImageView menIv;
    @BindView(R.id.phone_number_et)
    ConventionalEditTextView phoneNumberEt;
    @BindView(R.id.address_tv)
    ConventionalTextView addressTv;
    @BindView(R.id.address)
    ConventionalTextView address;
    @BindView(R.id.right_arrow_iv)
    ImageView rightArrowIv;
    @BindView(R.id.house_number_et)
    ConventionalEditTextView houseNumberEt;
    @BindView(R.id.house)
    ConventionalTextView house;
    @BindView(R.id.company)
    ConventionalTextView company;
    @BindView(R.id.school)
    ConventionalTextView school;
    @BindView(R.id.other)
    ConventionalTextView other;
    @BindView(R.id.default_iv)
    ImageView defaultIv;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.right_tv)
    MediumThickTextView rightTv;
    @BindView(R.id.item_ll)
    LinearLayout itemLl;

    private String labelType;
    private String genderType;
    private Boolean defaultType = false;
    private String name;
    private String phoneNumebr;
    private String houseNumber;

    private AddressInfo addressInfo;
    private AreaPickerView areaPickerView;
    private int[] i;
    private String[] codeValue;
    //省
    private String province;
    //市
    private String city;
    //区
    private String county;
    private String areaCode;
    private String addressId;
    private String choiceType;
    private String lat;
    private String lon;
    private List<String> group;

    @Inject
    AddAddressPresenter addAddressPresenter;


    public static void start(Context context, AddressInfo addressInfo) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        intent.putExtra(StaticData.ADDRESS_INFO, addressInfo);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        setHeight(back, title, rightTv);
        initView();
    }

    private void initView() {
        addressInfo = (AddressInfo) getIntent().getSerializableExtra(StaticData.ADDRESS_INFO);
        choiceType = getIntent().getStringExtra(StaticData.CHOICE_TYPE);
        if (addressInfo != null) {
            addressId = addressInfo.getId();
            rightTv.setVisibility(View.VISIBLE);
            nameEt.setText(addressInfo.getName());
            genderType = addressInfo.getGender();
            phoneNumberEt.setText(addressInfo.getTel());
            province = addressInfo.getProvince();
            city = addressInfo.getCity();
            county = addressInfo.getCounty();
            areaCode = addressInfo.getAreaCode();
            houseNumber = addressInfo.getAddressDetail();
            address.setText(province + city + county);
            houseNumberEt.setText(houseNumber);
            labelType = addressInfo.getType();
            genderType = addressInfo.getGender();
            defaultType = addressInfo.getDefault();
            lat = addressInfo.getLat();
            lon = addressInfo.getLng();
            defaultIv.setSelected(defaultType);
            title.setText(getString(R.string.update_address));
        } else {
            genderType = StaticData.REFLASH_ZERO;
            rightTv.setVisibility(View.INVISIBLE);
            title.setText(getString(R.string.add_new_address));
        }
        labelSelect();
        genderSelect();
        group = new ArrayList<>();
        group.add(Manifest.permission_group.LOCATION);
    }

    @Override
    protected void initializeInjector() {
        DaggerAddressComponent.builder()
                .applicationComponent(getApplicationComponent())
                .addressModule(new AddressModule(this))
                .build()
                .inject(this);
    }


    @OnTextChanged({R.id.name_et})
    public void checNameEnable() {
        name = nameEt.getText().toString().trim();
    }

    @OnTextChanged({R.id.phone_number_et})
    public void checPhoneNumberEnable() {
        phoneNumebr = phoneNumberEt.getText().toString().trim();
    }

    @OnTextChanged({R.id.house_number_et})
    public void checHouseNumberEnable() {
        houseNumber = houseNumberEt.getText().toString().trim();
    }


    @OnClick({R.id.confirm_bt, R.id.back, R.id.house, R.id.company, R.id.school, R.id.other, R.id.ms_iv, R.id.men_iv, R.id.address, R.id.default_iv, R.id.right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.house://标签 家
                labelType = getString(R.string.house);
                labelSelect();
                break;
            case R.id.company://标签 公司
                labelType = getString(R.string.company);
                labelSelect();
                break;
            case R.id.school://标签 学校
                labelType = getString(R.string.school);
                labelSelect();
                break;
            case R.id.other://标签 其他
                labelType = getString(R.string.other);
                labelSelect();
                break;
            case R.id.ms_iv://性别 女
                genderType = StaticData.REFLASH_ZERO;
                genderSelect();
                break;
            case R.id.men_iv://性别 男
                genderType = StaticData.REFLASH_ONE;
                genderSelect();
                break;
            case R.id.address:
//                areaPickerView.setSelect(i);
//                areaPickerView.show();
                if (requestRuntimePermissions(PermissionUtil.permissionGroup(group, null), RequestCodeStatic.REQUEST_PERMISSION_LOCATION)) {
                    Intent intent = new Intent(this, SelectAddressActivity.class);
                    startActivityForResult(intent, RequestCodeStatic.SELECT_LAT_LON);
                }
                break;
            case R.id.default_iv:
                defaultType = !defaultType;
                defaultIv.setSelected(defaultType);
                break;
            case R.id.confirm_bt:
                confirm();
                break;
            case R.id.right_tv:
                addAddressPresenter.deleteAddress(addressId);
                break;
            default:
        }
    }

    private void confirm() {
        if (TextUtils.isEmpty(name)) {
            showMessage(getString(R.string.input_receiver));
            return;
        }
        if (TextUtils.isEmpty(phoneNumebr)) {
            showMessage(getString(R.string.input_phone_number));
            return;
        }
        if (TextUtils.isEmpty(province) || TextUtils.isEmpty(city) || TextUtils.isEmpty(county)) {
            showMessage(getString(R.string.click_select_address));
            return;
        }
        if (TextUtils.isEmpty(houseNumber)) {
            showMessage(getString(R.string.input_detail_address));
            return;
        }
        AddAddressRequest addAddressRequest = new AddAddressRequest();
        addAddressRequest.setId(addressId);
        addAddressRequest.setName(name);
//        addAddressRequest.setGender(genderType);
        addAddressRequest.setTel(phoneNumebr);
        addAddressRequest.setProvince(province);
        addAddressRequest.setCity(city);
        addAddressRequest.setCounty(county);
        addAddressRequest.setAreaCode(areaCode);
        addAddressRequest.setAddressDetail(houseNumber);
        addAddressRequest.setType(labelType);
        addAddressRequest.setDefault(defaultType);
        addAddressRequest.setLat(lat);
        addAddressRequest.setLng(lon);
        addAddressPresenter.addAddress(addAddressRequest);

    }

    private void labelSelect() {
        house.setSelected(TextUtils.equals(getString(R.string.house), labelType) ? true : false);
        company.setSelected(TextUtils.equals(getString(R.string.company), labelType) ? true : false);
        school.setSelected(TextUtils.equals(getString(R.string.school), labelType) ? true : false);
        other.setSelected(TextUtils.equals(getString(R.string.other), labelType) ? true : false);
    }

    private void genderSelect() {
        msIv.setSelected(TextUtils.equals(StaticData.REFLASH_ZERO, genderType) ? true : false);
        menIv.setSelected(TextUtils.equals(StaticData.REFLASH_ONE, genderType) ? true : false);
    }


    @Override
    public View getSnackBarHolderView() {
        return itemLl;
    }


    @Override
    public void renderAddAddress(String addressId) {
        if (TextUtils.equals(StaticData.REFLASH_ZERO, choiceType)) {
            Intent intent = new Intent();
            intent.putExtra(StaticData.ADDRESS_ID, addressId);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public void renderAresa(List<ProvinceBean> provinceBeans) {
        areaPickerView = new AreaPickerView(this, R.style.Dialog, provinceBeans);
        areaPickerView.setAreaPickerViewCallback(new AreaPickerView.AreaPickerViewCallback() {
            @Override
            public void callback(int... value) {
                i = value;
                if (value.length == 3) {
                    province = provinceBeans.get(value[0]).getName();
                    city = provinceBeans.get(value[0]).getCityBeans().get(value[1]).getName();
                    county = provinceBeans.get(value[0]).getCityBeans().get(value[1]).getAreaBeans().get(value[2]).getName();
                } else {
                    province = provinceBeans.get(value[0]).getName();
                    city = provinceBeans.get(value[0]).getCityBeans().get(value[1]).getName();
                    county = "";
                }
                address.setText(province + city + county);
            }

            @Override
            public void callCode(String... value) {
                codeValue = value;
            }
        });
        areaPickerView.dismiss();
    }

    @Override
    public void renderDeleteAddress() {
        showMessage(getString(R.string.delete_success));
        finish();
    }

    @Override
    public void setPresenter(AddressContract.AddAddressPresenter presenter) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.SELECT_LAT_LON://
                    if (data != null) {
                        province = data.getStringExtra(StaticData.PROVINCE);
                        city = data.getStringExtra(StaticData.CITY);
                        county = data.getStringExtra(StaticData.COUNT);
                        lat = data.getStringExtra(StaticData.LAT);
                        lon = data.getStringExtra(StaticData.LON);
                        houseNumber = data.getStringExtra(StaticData.DETAIL_ADDRESS);
                        areaCode = data.getStringExtra(StaticData.AREA_CODE);
                        address.setText(province + city + county);
                        houseNumberEt.setText(houseNumber);
                    }
                    break;
                default:
            }
        }
    }
}
