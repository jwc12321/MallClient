package com.mall.sls.homepage.ui;

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
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.MobileManager;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.DrawTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.ConfirmOrderDetail;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.entity.InvitationCodeInfo;
import com.mall.sls.data.entity.ProductListCallableInfo;
import com.mall.sls.data.entity.WXGoodsDetailsInfo;
import com.mall.sls.homepage.DaggerHomepageComponent;
import com.mall.sls.homepage.HomepageContract;
import com.mall.sls.homepage.HomepageModule;
import com.mall.sls.homepage.adapter.GoodsItemAdapter;
import com.mall.sls.homepage.presenter.WXGoodsDetailsPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private String goodsProductId;
    private String grouponId;
    private String goodsId;
    private List<String> memberPhoneList;
    private List<String> specifications;
    private String specificationStr="";
    private GoodsDetailsInfo goodsDetailsInfo;

    private ProductListCallableInfo productListCallableInfo;
    private int goodsCount = 1;
    private String groupId;
    private String groupRulesId;
    private boolean isEnd=false;
    private String mobile;
    private String wxUrl;
    private String inviteCode;

    @Inject
    WXGoodsDetailsPresenter wxGoodsDetailsPresenter;

    public static void start(Context context,String goodsProductId,String grouponId) {
        Intent intent = new Intent(context, WXGoodsDetailsActivity.class);
        intent.putExtra(StaticData.GOODS_PRODUCT_ID,goodsProductId);
        intent.putExtra(StaticData.GROUPON_ID,grouponId);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx_goods_details);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        goodsProductId=getIntent().getStringExtra(StaticData.GOODS_PRODUCT_ID);
        grouponId=getIntent().getStringExtra(StaticData.GROUPON_ID);
        initAdapter();
        wxGoodsDetailsPresenter.getWXGoodsDetailsInfo(goodsProductId,grouponId);
        wxGoodsDetailsPresenter.getInvitationCodeInfo();
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
    public void renderWXGoodsDetailsInfo(GoodsDetailsInfo goodsDetailsInfo) {
        this.goodsDetailsInfo=goodsDetailsInfo;
        if(goodsDetailsInfo!=null){
            GlideHelper.load(this, goodsDetailsInfo.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
            goodsName.setText(goodsDetailsInfo.getName());
            currentPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(goodsDetailsInfo.getRetailPrice()));
            originalPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(goodsDetailsInfo.getCounterPrice()));
            goodsItemAdapter.setData(goodsDetailsInfo.getGoodsItemInfos());
            memberPhoneList=goodsDetailsInfo.getMemberPhoneList();
            goodsId=goodsDetailsInfo.getGoodsId();
            if(memberPhoneList!=null){
                if(memberPhoneList.size()==1){
                    mobile=memberPhoneList.get(0);
                    fighterMobile.setText(memberPhoneList.get(0));
                    teamMobile.setText("");
                }else if(memberPhoneList.size()>1){
                    mobile=memberPhoneList.get(0);
                    fighterMobile.setText(memberPhoneList.get(0));
                    teamMobile.setText(memberPhoneList.get(1));
                }
            }
            specifications=goodsDetailsInfo.getSpecifications();
            if(specifications!=null){
                for (String s:specifications){
                    specificationStr=specificationStr+s+" ";
                }
            }
            specification.setText("拼主所选规格："+specificationStr);
            groupRulesId=goodsDetailsInfo.getGrouponRulesId();
            if(TextUtils.equals(StaticData.REFLASH_ZERO,goodsDetailsInfo.getSurplus())){
                teamResult.setText(getString(R.string.bill_full));
                confirmBt.setText(getString(R.string.initiate_bill));
                isEnd=true;
                groupId="";
            }else {
                teamResult.setText("");
                confirmBt.setText(getString(R.string.join_pinyin));
                isEnd=false;
                groupId=goodsDetailsInfo.getGroupId();
                if(!TextUtils.isEmpty(MobileManager.getMobile())&&TextUtils.equals(MobileManager.getMobile(),mobile)){
                    confirmBt.setEnabled(false);
                }else {
                    confirmBt.setEnabled(true);
                }
            }
        }
    }

    @Override
    public void renderCartFastAdd(ConfirmOrderDetail confirmOrderDetail) {
        if(isEnd){
            ConfirmOrderActivity.start(this, confirmOrderDetail, StaticData.REFLASH_TWO,wxUrl,inviteCode);
            finish();
        }else {
            ConfirmOrderActivity.start(this, confirmOrderDetail, StaticData.REFLASH_THREE,wxUrl,inviteCode);
            finish();
        }
    }

    @Override
    public void renderInvitationCodeInfo(InvitationCodeInfo invitationCodeInfo) {
        if(invitationCodeInfo!=null){
            wxUrl=invitationCodeInfo.getBaseUrl();
            inviteCode=invitationCodeInfo.getInvitationCode();
        }
    }

    @Override
    public void setPresenter(HomepageContract.WXGoodsDetailsPresenter presenter) {

    }


    @Override
    protected void initializeInjector() {
        DaggerHomepageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .homepageModule(new HomepageModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void goOrdinaryGoodsDetails(String goodsId) {
        OrdinaryGoodsDetailActivity.start(this, goodsId);
    }

    @Override
    public void goActivityGroupGoods(String goodsId) {
        ActivityGroupGoodsActivity.start(this, goodsId);
    }

    @Override
    public void goGeneralGoodsDetails(String goodsId) {
        GeneralGoodsDetailsActivity.start(this,goodsId);
    }

    @OnClick({R.id.back,R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_bt:
                goSelectSpec(StaticData.REFLASH_ONE);
                break;
            default:
        }
    }

    private void goSelectSpec(String type) {
        Intent intent = new Intent(this, SelectSpecActivity.class);
        intent.putExtra(StaticData.GOODS_DETAILS_INFO, goodsDetailsInfo);
        intent.putExtra(StaticData.CHOICE_TYPE, type);
        startActivityForResult(intent, RequestCodeStatic.REQUEST_SPEC);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.REQUEST_SPEC:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        productListCallableInfo = (ProductListCallableInfo) bundle.getSerializable(StaticData.SKU_INFO);
                        goodsCount = bundle.getInt(StaticData.GOODS_COUNT);
                        wxGoodsDetailsPresenter.cartFastAdd(goodsId, productListCallableInfo.getId(), true, String.valueOf(goodsCount), groupId, groupRulesId);
                    }
                    break;
                default:
            }
        }
    }

}
