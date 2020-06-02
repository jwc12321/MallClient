package com.mall.sls.homepage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.widget.citypicker.adapter.CityListAdapter;
import com.mall.sls.common.widget.citypicker.adapter.InnerListener;
import com.mall.sls.common.widget.citypicker.adapter.OnPickListener;
import com.mall.sls.common.widget.citypicker.adapter.decoration.DividerItemDecoration;
import com.mall.sls.common.widget.citypicker.adapter.decoration.SectionItemDecoration;
import com.mall.sls.common.widget.citypicker.db.DBManager;
import com.mall.sls.common.widget.citypicker.model.City;
import com.mall.sls.common.widget.citypicker.model.HotCity;
import com.mall.sls.common.widget.citypicker.model.LocateState;
import com.mall.sls.common.widget.citypicker.model.LocatedCity;
import com.mall.sls.common.widget.citypicker.util.ScreenUtil;
import com.mall.sls.common.widget.citypicker.view.SideIndexBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author jwc on 2020/6/1.
 * 描述：
 */
public class CityPickerActivity extends BaseActivity implements TextWatcher,
        View.OnClickListener, SideIndexBar.OnIndexTouchedChangedListener, InnerListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.index_bar)
    SideIndexBar mIndexBar;
    @BindView(R.id.city_rl)
    RecyclerView cityRl;
    @BindView(R.id.overlay)
    TextView overlay;

    private LinearLayoutManager mLayoutManager;
    private CityListAdapter mAdapter;
    private List<City> mAllCities;
    private List<HotCity> mHotCities;
    private List<City> mResults;

    private DBManager dbManager;


    private LocatedCity mLocatedCity;
    private int locateState;
    private OnPickListener mOnPickListener;

    public static void start(Context context) {
        Intent intent = new Intent(context, CityPickerActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_picker);
        ButterKnife.bind(this);
        setHeight(back,null,null);
        initData();
        initView();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cityRl.setLayoutManager(mLayoutManager);
        cityRl.setHasFixedSize(true);
        cityRl.addItemDecoration(new SectionItemDecoration(this, mAllCities), 0);
        cityRl.addItemDecoration(new DividerItemDecoration(this), 1);
        mAdapter = new CityListAdapter(this, mAllCities, mHotCities, locateState);
        mAdapter.autoLocate(true);
        mAdapter.setInnerListener(this);
        mAdapter.setLayoutManager(mLayoutManager);
        cityRl.setAdapter(mAdapter);
        cityRl.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //确保定位城市能正常刷新
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mAdapter.refreshLocationItem();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });

        mIndexBar.setNavigationBarHeight(ScreenUtil.getNavigationBarHeight(this));
        mIndexBar.setOverlayTextView(overlay)
                .setOnIndexChangedListener(this);
    }

    private void initData() {
        //初始化热门城市
        if (mHotCities == null || mHotCities.isEmpty()) {
            mHotCities = new ArrayList<>();
            mHotCities.add(new HotCity("北京", "101010100"));
            mHotCities.add(new HotCity("上海",  "101020100"));
            mHotCities.add(new HotCity("广州",  "101280101"));
            mHotCities.add(new HotCity("深圳",  "101280601"));
            mHotCities.add(new HotCity("天津",  "101030100"));
            mHotCities.add(new HotCity("杭州",  "101210101"));
            mHotCities.add(new HotCity("南京",  "101190101"));
            mHotCities.add(new HotCity("成都",  "101270101"));
            mHotCities.add(new HotCity("武汉",  "101200101"));
        }
        //初始化定位城市，默认为空时会自动回调定位
        if (mLocatedCity == null) {
            mLocatedCity = new LocatedCity(getString(R.string.cp_locating),  "0");
            locateState = LocateState.LOCATING;
        } else {
            locateState = LocateState.SUCCESS;
        }

        dbManager = new DBManager(this);
        List<City> result = new ArrayList<>();
        City city1=new City("杭州","h","10000");
        result.add(city1);
        mAllCities = result;
        mAllCities.add(0, mLocatedCity);
        mAllCities.add(1, new HotCity("热门城市",  "0"));
        mResults = mAllCities;
    }

    private void initDate(){

    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void dismiss(int position, City data) {

    }

    @Override
    public void locate() {

    }

    @Override
    public void onIndexChanged(String index, int position) {

    }
}
