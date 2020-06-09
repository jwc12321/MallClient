package com.mall.sls.homepage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.certify.ui.CerifyPayActivity;
import com.mall.sls.common.StaticData;
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
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/6/1.
 * 描述：
 */
public class CityPickerActivity extends BaseActivity implements TextWatcher, SideIndexBar.OnIndexTouchedChangedListener, InnerListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.index_bar)
    SideIndexBar mIndexBar;
    @BindView(R.id.city_rv)
    RecyclerView cityRv;
    @BindView(R.id.overlay)
    TextView overlay;
    @BindView(R.id.small_title)
    ConventionalTextView smallTitle;
    @BindView(R.id.address_et)
    ConventionalEditTextView addressEt;
    @BindView(R.id.no_record_ll)
    LinearLayout noRecordLl;

    private LinearLayoutManager mLayoutManager;
    private CityListAdapter mAdapter;
    private List<City> mAllCities;
    private List<HotCity> mHotCities;
    private List<City> mResults;

    private DBManager dbManager;


    private LocatedCity mLocatedCity;
    private int locateState;
    private OnPickListener mOnPickListener;
    private String localCity;

    public static void start(Context context, String localCity) {
        Intent intent = new Intent(context, CityPickerActivity.class);
        intent.putExtra(StaticData.LOCAL_CITY, localCity);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_picker);
        ButterKnife.bind(this);
        setHeight(null, smallTitle, null);
        initData();
        initView();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cityRv.setLayoutManager(mLayoutManager);
        cityRv.setHasFixedSize(true);
        cityRv.addItemDecoration(new SectionItemDecoration(this, mAllCities), 0);
        cityRv.addItemDecoration(new DividerItemDecoration(this), 1);
        mAdapter = new CityListAdapter(this, mAllCities, mHotCities, locateState);
        mAdapter.autoLocate(true);
        mAdapter.setInnerListener(this);
        mAdapter.setLayoutManager(mLayoutManager);
        cityRv.setAdapter(mAdapter);
        cityRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        addressEt.addTextChangedListener(this);
    }

    private void initData() {
        localCity = getIntent().getStringExtra(StaticData.LOCAL_CITY);
        //初始化热门城市
        if (mHotCities == null || mHotCities.isEmpty()) {
            mHotCities = new ArrayList<>();
            mHotCities.add(new HotCity("北京", "101010100"));
            mHotCities.add(new HotCity("上海", "101020100"));
            mHotCities.add(new HotCity("广州", "101280101"));
            mHotCities.add(new HotCity("深圳", "101280601"));
            mHotCities.add(new HotCity("天津", "101030100"));
            mHotCities.add(new HotCity("杭州", "101210101"));
            mHotCities.add(new HotCity("南京", "101190101"));
            mHotCities.add(new HotCity("成都", "101270101"));
            mHotCities.add(new HotCity("武汉", "101200101"));
        }
        //初始化定位城市，默认为空时会自动回调定位
        if (mLocatedCity == null) {
            mLocatedCity = new LocatedCity(localCity, "0");
//            locateState = LocateState.LOCATING;
            locateState = LocateState.SUCCESS;
        } else {
            locateState = LocateState.SUCCESS;
        }

        dbManager = new DBManager(this);
        mAllCities = dbManager.getAllCities();
        mAllCities.add(0, mLocatedCity);
        mAllCities.add(1, new HotCity("热门城市", "0"));
        mResults = mAllCities;
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            default:
        }
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
        String keyword = s.toString();
        if (TextUtils.isEmpty(keyword)) {
            noRecordLl.setVisibility(View.GONE);
            mResults = mAllCities;
            ((SectionItemDecoration) (cityRv.getItemDecorationAt(0))).setData(mResults);
            mAdapter.updateData(mResults);
        } else {
            //开始数据库查找
            mResults = dbManager.searchCity(keyword);
            ((SectionItemDecoration) (cityRv.getItemDecorationAt(0))).setData(mResults);
            if (mResults == null || mResults.isEmpty()) {
                noRecordLl.setVisibility(View.VISIBLE);
            } else {
                noRecordLl.setVisibility(View.GONE);
                mAdapter.updateData(mResults);
            }
        }
        cityRv.scrollToPosition(0);
    }


    @Override
    public void onIndexChanged(String index, int position) {
        //滚动RecyclerView到索引位置
        mAdapter.scrollToSection(index);
    }

    public void locationChanged(LocatedCity location, int state) {
        mAdapter.updateLocateState(location, state);
    }

    @Override
    public void dismiss(int position, City data) {
        Intent intent = new Intent();
        intent.putExtra(StaticData.CHOICE_CITY, data.getName());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void locate() {
        if (mOnPickListener != null) {
            mOnPickListener.onLocate();
        }
    }

    public void setOnPickListener(OnPickListener listener) {
        this.mOnPickListener = listener;
    }
}
