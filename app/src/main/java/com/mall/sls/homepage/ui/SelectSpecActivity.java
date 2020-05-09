package com.mall.sls.homepage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.widget.edittextview.SoftKeyBoardListener;
import com.mall.sls.common.widget.scrollview.GradationScrollView;
import com.mall.sls.common.widget.shoppingselect.OnSelectedListener;
import com.mall.sls.common.widget.shoppingselect.ShoppingSelectView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.GoodsSpec;
import com.mall.sls.data.entity.ProductSku;
import com.mall.sls.data.entity.ProductSkuInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/9.
 * 描述：选择规格
 */
public class SelectSpecActivity extends BaseActivity implements OnSelectedListener {
    @BindView(R.id.goods_iv)
    ImageView goodsIv;
    @BindView(R.id.goods_name)
    MediumThickTextView goodsName;
    @BindView(R.id.goods_price)
    MediumThickTextView goodsPrice;
    @BindView(R.id.model)
    MediumThickTextView model;
    @BindView(R.id.goods_rl)
    RelativeLayout goodsRl;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.shopselectView)
    ShoppingSelectView shopselectView;
    @BindView(R.id.decrease_count)
    ImageView decreaseCount;
    @BindView(R.id.goods_count)
    EditText goodsCount;
    @BindView(R.id.increase_count)
    ImageView increaseCount;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;

    private int currentCount = 1;
    private List<GoodsSpec> goodsSpecs;
    Map<String, String> map = new HashMap<String, String>();
    private String goodsSpecStr;

    public static void start(Context context) {
        Intent intent = new Intent(context, SelectSpecActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_spec);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        SoftKeyBoardListener.setListener(this, onSoftKeyBoardChangeListener);
        shopselectView.setOnSelectedListener(this);
        goodsSpecs = new ArrayList<>();
        initSpec();

    }

    private void initSpec() {
        List<String> specs = new ArrayList<>();
        specs.add("黄色");
        specs.add("白色");
        specs.add("黑色");
        specs.add("橘色");
        specs.add("紫色");
        specs.add("蓝色");
        specs.add("绿色");
        specs.add("橙色");
        List<String> specs1 = new ArrayList<>();
        specs1.add("L");
        specs1.add("XL");
        specs1.add("M");
        specs1.add("XXL");
        List<String> specs2 = new ArrayList<>();
        specs2.add("17");
        specs2.add("30");
        specs2.add("42");
        GoodsSpec goodsSpec = new GoodsSpec();
        goodsSpec.setName("颜色");
        goodsSpec.setSpecs(specs);
        GoodsSpec goodsSpec1 = new GoodsSpec();
        goodsSpec1.setName("尺码");
        goodsSpec1.setSpecs(specs1);
        GoodsSpec goodsSpec2 = new GoodsSpec();
        goodsSpec2.setName("大小");
        goodsSpec2.setSpecs(specs2);
        goodsSpecs.add(goodsSpec);
        goodsSpecs.add(goodsSpec1);
        goodsSpecs.add(goodsSpec2);
        shopselectView.setData(goodsSpecs);
    }

    /**
     * 软键盘弹出收起监听
     */
    private SoftKeyBoardListener.OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener = new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
        @Override
        public void keyBoardShow(int height) {

        }

        @Override
        public void keyBoardHide(int height) {
            if (TextUtils.isEmpty(goodsCount.getText().toString())) {
                currentCount = 1;
                goodsCount.setText("1");
            } else {
                currentCount = Integer.parseInt(goodsCount.getText().toString());
                if (currentCount == 0) {
                    currentCount = 1;
                    goodsCount.setText("1");
                }
            }
        }
    };

    @OnClick({R.id.all_rl, R.id.item_rl, R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.all_rl:
                finish();
                break;
            case R.id.item_rl:
                break;
            case R.id.confirm_bt:
                break;
            default:
        }
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void onSelected(String position, String title, String smallTitle) {
        goodsSpecStr="";
        map.put(position, smallTitle);
        if (goodsSpecs != null && map.size() == goodsSpecs.size()) {
            Map<String, String> resultMap = sortMapByKey(map);    //按Key进行排序
            for (Map.Entry<String, String> entry : resultMap.entrySet()) {
                goodsSpecStr = goodsSpecStr + entry.getValue();
            }
            showMessage(goodsSpecStr);
        } else {
            confirmBt.setEnabled(false);
        }
    }
}
