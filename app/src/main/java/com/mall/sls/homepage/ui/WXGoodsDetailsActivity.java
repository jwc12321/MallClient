package com.mall.sls.homepage.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.DrawTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.WXGoodsDetailsInfo;
import com.mall.sls.homepage.HomepageContract;
import com.mall.sls.homepage.adapter.GoodsItemAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author jwc on 2020/5/25.
 * 描述：H5商品详情
 */
public class WXGoodsDetailsActivity extends BaseActivity implements HomepageContract.WXGoodsDetailsView,GoodsItemAdapter.OnItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.goods_iv)
    ImageView goodsIv;
    @BindView(R.id.goods_name)
    MediumThickTextView goodsName;
    @BindView(R.id.current_price)
    MediumThickTextView currentPrice;
    @BindView(R.id.original_price)
    DrawTextView originalPrice;
    @BindView(R.id.price_ll)
    LinearLayout priceLl;
    @BindView(R.id.price_type)
    ConventionalTextView priceType;
    @BindView(R.id.fighter_mobile)
    ConventionalTextView fighterMobile;
    @BindView(R.id.team_mobile)
    ConventionalTextView teamMobile;
    @BindView(R.id.specification)
    ConventionalTextView specification;
    @BindView(R.id.team_result)
    MediumThickTextView teamResult;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.selected_group_tv)
    MediumThickTextView selectedGroupTv;
    @BindView(R.id.other_rl)
    RelativeLayout otherRl;
    @BindView(R.id.goods_rv)
    RecyclerView goodsRv;

    private GoodsItemAdapter goodsItemAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx_goods_details);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        initAdapter();
    }

    private void initAdapter() {
        goodsItemAdapter = new GoodsItemAdapter(this);
        goodsItemAdapter.setOnItemClickListener(this);
        goodsRv.setAdapter(goodsItemAdapter);
    }



    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderWXGoodsDetailsInfo(WXGoodsDetailsInfo wxGoodsDetailsInfo) {
        if(wxGoodsDetailsInfo!=null){
            GlideHelper.load(this, wxGoodsDetailsInfo.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
            goodsName.setText(wxGoodsDetailsInfo.getName());
            currentPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(wxGoodsDetailsInfo.getRetailPrice()));
            originalPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(wxGoodsDetailsInfo.getCounterPrice()));
            goodsItemAdapter.setData(wxGoodsDetailsInfo.getGoodsItemInfos());
        }
    }

    @Override
    public void setPresenter(HomepageContract.WXGoodsDetailsPresenter presenter) {

    }


    @Override
    public void goOrdinaryGoodsDetails(String goodsId) {
        OrdinaryGoodsDetailActivity.start(this, goodsId);
    }

    @Override
    public void goActivityGroupGoods(String goodsId) {
        ActivityGroupGoodsActivity.start(this, goodsId);
    }
}
