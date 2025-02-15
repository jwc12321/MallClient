package com.mall.sls.common.widget.citypicker.util;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.citypicker.model.City;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class CityListLoader {

    public static final String BUNDATA = "bundata";

    private static List<City> mCityListData = new ArrayList<>();
    private static List<City> mAllCityListData = new ArrayList<>();

    private static List<City> mProListData = new ArrayList<>();
    private List<City> cityList;//获取所有的市
    private List<City> allAityList = new ArrayList<>();//获取所有的市

    /**
     * 解析所有的城市数据 357个数据
     */
    public List<City> getCityListData() {
        return mCityListData;
    }

    /**
     * 解析所有的城市数据 357个数据
     */
    public List<City> getAllCityListData() {
        return mAllCityListData;
    }

    /**
     * 只解析省份34个数据
     */
    public List<City> getProListData() {
        return mProListData;
    }

    private volatile static CityListLoader instance;

    private CityListLoader() {

    }

    /**
     * 单例模式
     *
     * @return
     */
    public static CityListLoader getInstance() {
        if (instance == null) {
            synchronized (CityListLoader.class) {
                if (instance == null) {
                    instance = new CityListLoader();
                }
            }
        }
        return instance;
    }

    /**
     * 解析357个城市数据
     *
     * @param context
     */
    public void loadCityData(Context context) {

        String cityJson = Utils.getJson(context, StaticData.CITY_DATA);
        Type type = new TypeToken<ArrayList<City>>() {
        }.getType();

        //解析省份
        ArrayList<City> mProvinceBeanArrayList = new Gson().fromJson(cityJson, type);
        if (mProvinceBeanArrayList == null || mProvinceBeanArrayList.isEmpty()) {
            return;
        }

        for (int p = 0; p < mProvinceBeanArrayList.size(); p++) {

            //遍历每个省份
            City itemProvince = mProvinceBeanArrayList.get(p);

            //每个省份对应下面的市
            cityList = itemProvince.getCityList();
            allAityList.addAll(cityList);
            //遍历当前省份下面城市的所有数据
            for (int j = 0; j < cityList.size(); j++) {
                mCityListData.add(cityList.get(j));
                mAllCityListData.add(cityList.get(j));
                List<City> areaList = cityList.get(j).getCityList();
                for (int z = 0; z < areaList.size(); z++) {
                    mAllCityListData.add(areaList.get(z));
                }
            }
        }
    }

    private City choiceCity;
    private List<City> choceCitys;
    private List<City> returnCitys;

    public List<City> getArea(String city) {
        returnCitys=new ArrayList<>();
        if (allAityList != null) {
            for (int i = 0; i < allAityList.size(); i++) {
                choiceCity = allAityList.get(i);
                if (TextUtils.equals(city, choiceCity.getName())) {
                    returnCitys.add(choiceCity);
                    returnCitys.addAll(choiceCity.getCityList());
                    return returnCitys;
                }
                choceCitys = choiceCity.getCityList();
                for (int z = 0; z < choceCitys.size(); z++) {
                    if (TextUtils.equals(city, choceCitys.get(z).getName())) {
                        returnCitys.add(choiceCity);
                        returnCitys.addAll(choiceCity.getCityList());
                        return returnCitys;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 解析34个省市直辖区数据
     *
     * @param context
     */
    public void loadProData(Context context) {
        String cityJson = Utils.getJson(context, StaticData.CITY_DATA);
        Type type = new TypeToken<ArrayList<City>>() {
        }.getType();

        //解析省份
        mProListData = new Gson().fromJson(cityJson, type);
    }

}
