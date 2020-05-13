package com.mall.sls.address.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.certify.ui.CerifyPayActivity;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.AddressInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author jwc on 2020/5/8.
 * 描述：添加地址
 */
public class AddAddressActivity extends BaseActivity  {
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

    private String labelType;
    private String genderType;
    private Boolean defaultType=false;
    private String name;
    private String phoneNumebr;
    private String houseNumber;

    private String provinceCode;
    private String cityCode;
    private String countyCode;
    private String streetCode;
    private int provincePosition;
    private int cityPosition;
    private int countyPosition;
    private int streetPosition;
    private  AddressInfo addressInfo;


    public static void start(Context context, AddressInfo addressInfo) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        intent.putExtra(StaticData.ADDRESS_INFO,addressInfo);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView() {
        addressInfo= (AddressInfo) getIntent().getSerializableExtra(StaticData.ADDRESS_INFO);
        if(addressInfo!=null){
            nameEt.setText(addressInfo.getName());
            genderType=addressInfo.getGender();
            phoneNumberEt.setText(addressInfo.getTel());


        }else {
            genderType=StaticData.REFLASH_ZERO;
        }
        genderType="1";
        labelSelect();
        genderSelect();
        initAddressDialog();
    }

    private void initAddressDialog(){
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


    @OnClick({R.id.confirm_bt, R.id.back, R.id.house, R.id.company, R.id.school, R.id.other,R.id.ms_iv,R.id.men_iv,R.id.address,R.id.default_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.house://标签 家
                labelType = "1";
                labelSelect();
                break;
            case R.id.company://标签 公司
                labelType = "2";
                labelSelect();
                break;
            case R.id.school://标签 学校
                labelType = "3";
                labelSelect();
                break;
            case R.id.other://标签 其他
                labelType = "4";
                labelSelect();
                break;
            case R.id.ms_iv://性别 女
                genderType=StaticData.REFLASH_ZERO;
                genderSelect();
                break;
            case R.id.men_iv://性别 男
                genderType=StaticData.REFLASH_ONE;
                genderSelect();
                break;
            case R.id.address:
                showDialog();
                break;
            case R.id.default_iv:
                defaultType=!defaultType;
                defaultIv.setSelected(defaultType);
                break;
            case R.id.confirm_bt:
                CerifyPayActivity.start(this);
                break;
            default:
        }
    }

    private void showDialog(){
    }

    private void labelSelect() {
        house.setSelected(TextUtils.equals("1", labelType) ? true : false);
        company.setSelected(TextUtils.equals("2", labelType) ? true : false);
        school.setSelected(TextUtils.equals("3", labelType) ? true : false);
        other.setSelected(TextUtils.equals("4", labelType) ? true : false);
    }

    private void genderSelect(){
        msIv.setSelected(TextUtils.equals(StaticData.REFLASH_ZERO,genderType)?true:false);
        menIv.setSelected(TextUtils.equals(StaticData.REFLASH_ONE,genderType)?true:false);
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }




}
