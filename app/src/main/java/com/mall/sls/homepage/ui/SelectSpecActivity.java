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
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.edittextview.SoftKeyBoardListener;
import com.mall.sls.common.widget.scrollview.GradationScrollView;
import com.mall.sls.common.widget.shoppingselect.OnSelectedListener;
import com.mall.sls.common.widget.shoppingselect.ShoppingSelectView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.entity.GoodsSpec;
import com.mall.sls.data.entity.ProductListCallableInfo;

import java.io.Serializable;
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
    @BindView(R.id.close_iv)
    ImageView closeIv;
    @BindView(R.id.goods_price)
    MediumThickTextView goodsPrice;
    @BindView(R.id.model)
    ConventionalTextView model;
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
    @BindView(R.id.item_rl)
    RelativeLayout itemRl;
    @BindView(R.id.all_rl)
    RelativeLayout allRl;
    private int currentCount;
    private List<GoodsSpec> goodsSpecs;
    Map<String, String> map = new HashMap<String, String>();
    private List<ProductListCallableInfo> productListCallableInfos;
    private List<String> checkSkus;
    private String goodsSpecStr = "";
    private ProductListCallableInfo productListCallableInfo;
    private String choiceType;//0：单独购买 1：拼单
    private String picUrl;

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
        map.clear();
        checkSkus = (List<String>) getIntent().getSerializableExtra(StaticData.SKU_CHECK);
        choiceType = getIntent().getStringExtra(StaticData.CHOICE_TYPE);
        currentCount = getIntent().getIntExtra(StaticData.GOODS_COUNT, 1);
        goodsCount.setText(String.valueOf(currentCount));
        goodsSpecs = (List<GoodsSpec>) getIntent().getSerializableExtra(StaticData.PRODUCT_LIST);
        productListCallableInfos = (List<ProductListCallableInfo>) getIntent().getSerializableExtra(StaticData.SPECIFICATION_LIST);
        picUrl=getIntent().getStringExtra(StaticData.PIC_URL);
        if (checkSkus != null && checkSkus.size() > 0) {
            shopselectView.setCheckSkus(checkSkus);
            for (int i = 0; i < checkSkus.size(); i++) {
                goodsSpecStr = goodsSpecStr + checkSkus.get(i) + ",";
                map.put(String.valueOf(i), checkSkus.get(i));
            }
            goodsSpecStr = goodsSpecStr.substring(0, goodsSpecStr.length() - 1);
            for (ProductListCallableInfo productListCallableInfo : productListCallableInfos) {
                if (TextUtils.equals(productListCallableInfo.getSpecifications(), goodsSpecStr)) {
                    this.productListCallableInfo = productListCallableInfo;
                    break;
                }
            }
            if (productListCallableInfo != null) {
                GlideHelper.load(this, productListCallableInfo.getUrl(), R.mipmap.icon_default_goods, goodsIv);
                if (TextUtils.equals(StaticData.REFLASH_ZERO, choiceType)) {
                    goodsPrice.setText("¥" + productListCallableInfo.getPrice());
                } else {
                    goodsPrice.setText("¥" + productListCallableInfo.getPreferentialPrice());
                }
                if (TextUtils.equals("0", productListCallableInfo.getNumber())) {
                    model.setText(getString(R.string.out_stock));
                    confirmBt.setEnabled(false);
                } else {
                    model.setText(getString(R.string.is_selected) + " " + productListCallableInfo.getSpecifications());
                    confirmBt.setEnabled(true);
                }
            }
        } else {
            GlideHelper.load(this, picUrl, R.mipmap.icon_default_goods, goodsIv);
            if (productListCallableInfos != null && productListCallableInfos.size() > 0) {
                if (TextUtils.equals(StaticData.REFLASH_ZERO, choiceType)) {
                    goodsPrice.setText("¥" + productListCallableInfos.get(0).getPrice());
                } else {
                    goodsPrice.setText("¥" + productListCallableInfos.get(0).getPreferentialPrice());
                }
            }
            model.setText(getString(R.string.is_selected));
            confirmBt.setEnabled(false);
        }
        SoftKeyBoardListener.setListener(this, onSoftKeyBoardChangeListener);
        shopselectView.setOnSelectedListener(this);
        initSpec();

    }

    private void initSpec() {
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
            calculatingPrice(productListCallableInfo);
        }
    };

    @OnClick({R.id.confirm_bt, R.id.close_iv, R.id.decrease_count, R.id.increase_count})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
                finish();
                break;
            case R.id.confirm_bt:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(StaticData.SKU_INFO, productListCallableInfo);
                bundle.putSerializable(StaticData.SKU_CHECK, (Serializable) checkSkus);
                bundle.putInt(StaticData.GOODS_COUNT, currentCount);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.decrease_count://数量减少
                if (currentCount == 1 || currentCount == 0) {
                    return;
                }
                currentCount--;
                goodsCount.setText(String.valueOf(currentCount));
                break;
            case R.id.increase_count://数量增加
                currentCount++;
                goodsCount.setText(String.valueOf(currentCount));
                calculatingPrice(productListCallableInfo);
                break;
            default:
        }
    }

    private void calculatingPrice(ProductListCallableInfo productListCallableInfo) {
        if (productListCallableInfo != null) {
            if (!TextUtils.isEmpty(productListCallableInfo.getNumber()) && (currentCount > Integer.parseInt(productListCallableInfo.getNumber()))) {
                currentCount = Integer.parseInt(productListCallableInfo.getNumber());
                goodsCount.setText(String.valueOf(currentCount));
                showMessage("库存只有" + currentCount + "件");
            }
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
        goodsSpecStr = "";
        map.put(position, smallTitle);
        if (goodsSpecs != null && map.size() == goodsSpecs.size()) {
            if (checkSkus == null) {
                checkSkus = new ArrayList<>();
            } else {
                checkSkus.clear();
            }
            Map<String, String> resultMap = sortMapByKey(map);    //按Key进行排序
            for (Map.Entry<String, String> entry : resultMap.entrySet()) {
                goodsSpecStr = goodsSpecStr + entry.getValue() + ",";
                checkSkus.add(entry.getValue());

            }
            goodsSpecStr = goodsSpecStr.substring(0, goodsSpecStr.length() - 1);
            productListCallableInfo = null;
            for (ProductListCallableInfo productListCallableInfo : productListCallableInfos) {
                if (TextUtils.equals(productListCallableInfo.getSpecifications(), goodsSpecStr)) {
                    this.productListCallableInfo = productListCallableInfo;
                    break;
                }
            }
            if (productListCallableInfo != null) {
                GlideHelper.load(this, productListCallableInfo.getUrl(), R.mipmap.icon_default_goods, goodsIv);
                if (TextUtils.equals(StaticData.REFLASH_ZERO, choiceType)) {
                    goodsPrice.setText("¥" + productListCallableInfo.getPrice());
                } else {
                    goodsPrice.setText("¥" + productListCallableInfo.getPreferentialPrice());
                }
                model.setText(getString(R.string.is_selected) + " " + productListCallableInfo.getSpecifications());
                if (TextUtils.equals("0", productListCallableInfo.getNumber())) {
                    model.setText(getString(R.string.out_stock));
                    confirmBt.setEnabled(false);
                } else {
                    model.setText(getString(R.string.is_selected) + " " + productListCallableInfo.getSpecifications());
                    calculatingPrice(productListCallableInfo);
                    confirmBt.setEnabled(true);
                }
            }
        } else {
            confirmBt.setEnabled(false);
        }
    }
}
