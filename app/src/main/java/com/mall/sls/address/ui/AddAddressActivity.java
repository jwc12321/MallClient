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
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.smarttop.library.bean.AdressBean;
import com.smarttop.library.bean.City;
import com.smarttop.library.bean.County;
import com.smarttop.library.bean.Province;
import com.smarttop.library.bean.Street;
import com.smarttop.library.db.manager.AddressDictManager;
import com.smarttop.library.utils.LogUtil;
import com.smarttop.library.widget.AddressSelector;
import com.smarttop.library.widget.BottomDialog;
import com.smarttop.library.widget.OnAddressSelectedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author jwc on 2020/5/8.
 * 描述：添加地址
 */
public class AddAddressActivity extends BaseActivity implements OnAddressSelectedListener, AddressSelector.OnDialogCloseListener, AddressSelector.onSelectorAreaPositionListener {
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

    private AddressDictManager addressDictManager;
    private BottomDialog dialog;
    private String provinceCode;
    private String cityCode;
    private String countyCode;
    private String streetCode;
    private int provincePosition;
    private int cityPosition;
    private int countyPosition;
    private int streetPosition;


    public static void start(Context context) {
        Intent intent = new Intent(context, AddAddressActivity.class);
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
        labelType="1";
        genderType="1";
        labelSelect();
        genderSelect();
        initAddressDialog();
    }

    private void initAddressDialog(){
        AddressSelector selector = new AddressSelector(this);
        //获取地址管理数据库
        addressDictManager = selector.getAddressDictManager();

        selector.setTextSize(14);//设置字体的大小
//        selector.setIndicatorBackgroundColor("#00ff00");
        selector.setIndicatorBackgroundColor(android.R.color.holo_orange_light);//设置指示器的颜色
//        selector.setBackgroundColor(android.R.color.holo_red_light);//设置字体的背景

        selector.setTextSelectedColor(android.R.color.holo_orange_light);//设置字体获得焦点的颜色

        selector.setTextUnSelectedColor(android.R.color.holo_blue_light);//设置字体没有获得焦点的颜色
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
                genderType="1";
                genderSelect();
                break;
            case R.id.men_iv://性别 男
                genderType="2";
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
        if (dialog != null) {
            dialog.show();
        } else {
            dialog = new BottomDialog(this);
            dialog.setOnAddressSelectedListener(this);
            dialog.setDialogDismisListener(this);
            dialog.setTextSize(14);//设置字体的大小
            dialog.setIndicatorBackgroundColor(android.R.color.holo_orange_light);//设置指示器的颜色
            dialog.setTextSelectedColor(android.R.color.holo_orange_light);//设置字体获得焦点的颜色
            dialog.setTextUnSelectedColor(android.R.color.holo_blue_light);//设置字体没有获得焦点的颜色
//            dialog.setDisplaySelectorArea("31",1,"2704",1,"2711",0,"15582",1);//设置已选中的地区
            dialog.setSelectorAreaPositionListener(this);
            dialog.show();
        }
    }

    private void labelSelect() {
        house.setSelected(TextUtils.equals("1", labelType) ? true : false);
        company.setSelected(TextUtils.equals("2", labelType) ? true : false);
        school.setSelected(TextUtils.equals("3", labelType) ? true : false);
        other.setSelected(TextUtils.equals("4", labelType) ? true : false);
    }

    private void genderSelect(){
        msIv.setSelected(TextUtils.equals("1",genderType)?true:false);
        menIv.setSelected(TextUtils.equals("2",genderType)?true:false);
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }


    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {
        provinceCode = (province == null ? "" : province.code);
        cityCode = (city == null ? "" : city.code);
        countyCode = (county == null ? "" : county.code);
        streetCode = (street == null ? "" : street.code);
        LogUtil.d("数据", "省份id=" + provinceCode);
        LogUtil.d("数据", "城市id=" + cityCode);
        LogUtil.d("数据", "乡镇id=" + countyCode);
        LogUtil.d("数据", "街道id=" + streetCode);
        String s = (province == null ? "" : province.name) + (city == null ? "" : city.name) + (county == null ? "" : county.name) +
                (street == null ? "" : street.name);
        address.setText(s);
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void dialogclose() {
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    /**
     * 根据code 来显示选择过的地区
     */
    private void getSelectedArea(){
        String province = addressDictManager.getProvince(provinceCode);
        String city = addressDictManager.getCity(cityCode);
        String county = addressDictManager.getCounty(countyCode);
        String street = addressDictManager.getStreet(streetCode);
        address.setText(province+city+county+street);
        LogUtil.d("数据", "省份=" + province);
        LogUtil.d("数据", "城市=" + city);
        LogUtil.d("数据", "乡镇=" + county);
        LogUtil.d("数据", "街道=" + street);
    }

    @Override
    public void selectorAreaPosition(int provincePosition, int cityPosition, int countyPosition, int streetPosition) {
        this.provincePosition = provincePosition;
        this.cityPosition = cityPosition;
        this.countyPosition = countyPosition;
        this.streetPosition = streetPosition;
        LogUtil.d("数据", "省份位置=" + provincePosition);
        LogUtil.d("数据", "城市位置=" + cityPosition);
        LogUtil.d("数据", "乡镇位置=" + countyPosition);
        LogUtil.d("数据", "街道位置=" + streetPosition);
    }
}
