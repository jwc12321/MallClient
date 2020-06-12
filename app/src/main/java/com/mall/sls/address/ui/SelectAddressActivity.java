package com.mall.sls.address.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.address.adapter.MapAddressAdapter;
import com.mall.sls.address.adapter.SearchAddressAdapter;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.location.LocationBean;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.homepage.ui.CityPickerActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by JWC on 2018/5/29.
 * 新增收货地址
 * 留着以后可能会用
 */

public class SelectAddressActivity extends BaseActivity implements LocationSource, AMapLocationListener, PoiSearch.OnPoiSearchListener, AMap.OnCameraChangeListener, MapAddressAdapter.OnItemClickListener, SearchAddressAdapter.OnItemClickListener {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.local_city)
    ConventionalTextView localCity;
    @BindView(R.id.local_city_ll)
    LinearLayout localCityLl;
    @BindView(R.id.address_et)
    ConventionalEditTextView addressEt;
    @BindView(R.id.cancel_bt)
    ConventionalTextView cancelBt;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.map_rl)
    RelativeLayout mapRl;
    @BindView(R.id.address_rv)
    RecyclerView addressRv;
    @BindView(R.id.search_rv)
    RecyclerView searchRv;
    @BindView(R.id.no_record_ll)
    LinearLayout noRecordLl;
    @BindView(R.id.search_rl)
    RelativeLayout searchRl;
    private AMap aMap;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private double latitude;
    private double longitude;
    private String city;

    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;//搜索
    private LocationBean currentLoc;
    private MapAddressAdapter mapAddressAdapter;
    private SearchAddressAdapter searchAddressAdapter;
    private String keyWord;
    private String type;
    private AMapLocation aMapLocation;

    public static void start(Context context) {
        Intent intent = new Intent(context, SelectAddressActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        mapView.onCreate(savedInstanceState);
        initView();
        initMap();
    }

    private void initView() {
        mapAddressAdapter = new MapAddressAdapter();
        mapAddressAdapter.setOnItemClickListener(this);
        addressRv.setAdapter(mapAddressAdapter);
        searchAddressAdapter = new SearchAddressAdapter();
        searchAddressAdapter.setOnItemClickListener(this);
        searchRv.setAdapter(searchAddressAdapter);
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    /**
     * 监听手机输入框
     */
    @OnTextChanged({R.id.address_et})
    public void checkAddressEnable() {
        keyWord = addressEt.getText().toString().trim();
        doSearchKeyWord(keyWord, city);
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setOnCameraChangeListener(this);
        setupLocationStyle();
    }

    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.mipmap.icon_gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(600000);
            mLocationOption.setOnceLocationLatest(false);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        this.aMapLocation = aMapLocation;
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                Log.d("jjj0", "精度和纬度" + aMapLocation.getLatitude() + "=====" + aMapLocation.getLongitude() + "==" + aMapLocation.getCity() + "==" + aMapLocation.getCityCode() + "==" + aMapLocation.getProvince());
                city = aMapLocation.getCity();
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 16, 0, 0)));
                localCity.setText(city);
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    protected void doSearchQuery(String city, String mType, double latitude, double longitude) {
        type = StaticData.REFLASH_ONE;
        query = new PoiSearch.Query("", mType, city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(50);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        //以当前定位的经纬度为准搜索周围5000米范围
        // 设置搜索区域为以lp点为圆心，其周围5000米范围
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 1000, true));//
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    protected void doSearchKeyWord(String keyWord, String city) {
        type = StaticData.REFLASH_ZERO;
        query = new PoiSearch.Query(keyWord, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(50);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 1000) {
            if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                Log.d("jjj0", "精度和纬度======" + poiResult + "==" + poiResult.getPois().size());
                if (poiResult.getQuery().equals(query)) {// 是否是同一条
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    if (TextUtils.equals(StaticData.REFLASH_ONE, type)) {
                        mapAddressAdapter.setData(poiItems);
                    } else {
                        searchRl.setVisibility(View.VISIBLE);
                        if (poiItems != null && poiItems.size() > 0) {
                            searchRv.setVisibility(View.VISIBLE);
                            noRecordLl.setVisibility(View.GONE);
                        } else {
                            searchRv.setVisibility(View.GONE);
                            noRecordLl.setVisibility(View.VISIBLE);
                        }
                        searchAddressAdapter.setData(poiItems);
                    }
                }
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(final CameraPosition cameraPosition) {
        latitude = cameraPosition.target.latitude;
        longitude = cameraPosition.target.longitude;
        Log.d("jjj0", "精度和纬度" + latitude + "=====" + longitude);
        doSearchQuery("", "", latitude, longitude);
//        LatLngEntity latLngEntity = new LatLngEntity(cameraPosition.target.latitude, cameraPosition.target.longitude);
//        //地理反编码工具类，代码在后面
//        GeoCoderUtil.getInstance(SelectAddressActivity.this).geoAddress(latLngEntity, new GeoCoderUtil.GeoCoderAddressListener() {
//            @Override
//            public void onAddressResult(String result) {
//                latitude = cameraPosition.target.latitude;
//                longitude = cameraPosition.target.longitude;
//                currentLoc = new LocationBean(longitude, latitude, result, "");
//                Log.d("jjj0", "精度和纬度" + latitude + "=====" + longitude);
//                doSearchQuery("", "", latitude, longitude);
//            }
//        });
    }

    @Override
    public void select(PoiItem poiItem) {
        String province = poiItem.getProvinceName();
        String city = poiItem.getCityName();
        String county = poiItem.getAdName();
        LatLonPoint latLonPoint = poiItem.getLatLonPoint();
        String lat = String.valueOf(latLonPoint.getLatitude());
        String lon = String.valueOf(latLonPoint.getLongitude());
        String detailAddress = poiItem.getSnippet();
        String areaCode = poiItem.getAdCode();
        Log.d("11", "数据" + province + "==" + city + "==" + county + "==" + lat + "==" + lon + "==" + detailAddress + "==" + areaCode);
        Intent intent = new Intent();
        intent.putExtra(StaticData.PROVINCE, province);
        intent.putExtra(StaticData.CITY, city);
        intent.putExtra(StaticData.COUNT, county);
        intent.putExtra(StaticData.LAT, lat);
        intent.putExtra(StaticData.LON, lon);
        intent.putExtra(StaticData.DETAIL_ADDRESS, detailAddress);
        intent.putExtra(StaticData.AREA_CODE, areaCode);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick({R.id.local_city_ll, R.id.back, R.id.cancel_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.local_city_ll:
                Intent intent = new Intent(this, CityPickerActivity.class);
                intent.putExtra(StaticData.LOCAL_CITY, city);
                startActivityForResult(intent, RequestCodeStatic.CHOICE_CITY);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.cancel_bt:
                searchRl.setVisibility(View.GONE);
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.CHOICE_CITY://
                    if (data != null) {
                        city = data.getStringExtra(StaticData.CHOICE_CITY);
                        localCity.setText(city);
                    }
                    break;
                default:
            }
        }
    }

    @Override
    public void selectSearch(PoiItem poiItem) {
        String province = poiItem.getProvinceName();
        String city = poiItem.getCityName();
        String county = poiItem.getAdName();
        LatLonPoint latLonPoint = poiItem.getLatLonPoint();
        String lat = String.valueOf(latLonPoint.getLatitude());
        String lng = String.valueOf(latLonPoint.getLongitude());
        String detailAddress = poiItem.getSnippet();
        Log.d("11", "数据" + province + "==" + city + "==" + county + "==" + lat + "==" + lng + "==" + detailAddress);
    }

}
