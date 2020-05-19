package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.FormatUtil;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.unit.TimeUtil;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.DrawTextView;
import com.mall.sls.common.widget.textview.FourTearDownView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.common.widget.textview.TearDownView;
import com.mall.sls.data.entity.ConfirmOrderDetail;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.entity.GroupPeople;
import com.mall.sls.data.entity.GroupPurchase;
import com.mall.sls.data.entity.ProductListCallableInfo;
import com.mall.sls.homepage.DaggerHomepageComponent;
import com.mall.sls.homepage.HomepageContract;
import com.mall.sls.homepage.HomepageModule;
import com.mall.sls.homepage.presenter.GoodsDetailsPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/14.
 * 描述：活动团
 */
public class ActivityGroupGoodsActivity extends BaseActivity implements HomepageContract.GoodsDetailsView, TearDownView.TimeOutListener, FourTearDownView.TimeOutListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.activity_rule_iv)
    ImageView activityRuleIv;
    @BindView(R.id.goods_name)
    ConventionalTextView goodsName;
    @BindView(R.id.count_down)
    TearDownView countDown;
    @BindView(R.id.activity_name)
    ConventionalTextView activityName;
    @BindView(R.id.goods_iv)
    ImageView goodsIv;
    @BindView(R.id.goods_introduction)
    ConventionalTextView goodsIntroduction;
    @BindView(R.id.current_price)
    MediumThickTextView currentPrice;
    @BindView(R.id.original_price)
    DrawTextView originalPrice;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.people_number)
    ConventionalTextView peopleNumber;
    @BindView(R.id.goods_number)
    ConventionalTextView goodsNumber;
    @BindView(R.id.over_tv)
    ConventionalTextView overTv;
    @BindView(R.id.count_down_time)
    FourTearDownView countDownTime;
    @BindView(R.id.goods_rl)
    RelativeLayout goodsRl;
    @BindView(R.id.discountMember)
    ConventionalTextView discountMember;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;


    @Inject
    GoodsDetailsPresenter goodsDetailsPresenter;

    private String goodsId;
    private GoodsDetailsInfo goodsDetailsInfo;
    private String groupId;
    private String groupRulesId;

    private ProductListCallableInfo productListCallableInfo;
    private int goodsCount = 1;
    private List<GroupPeople> groupPeoples;
    private List<GroupPurchase> groupPurchases;

    public static void start(Context context, String goodsId) {
        Intent intent = new Intent(context, ActivityGroupGoodsActivity.class);
        intent.putExtra(StaticData.GOODS_ID, goodsId);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_group_goods);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        goodsId = getIntent().getStringExtra(StaticData.GOODS_ID);
        goodsDetailsPresenter.getGoodsDetails(goodsId);

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
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderGoodsDetails(GoodsDetailsInfo goodsDetailsInfo) {
        this.goodsDetailsInfo = goodsDetailsInfo;
        if (goodsDetailsInfo != null) {
            GlideHelper.load(this, goodsDetailsInfo.getPicUrl(), R.mipmap.ic_launcher, goodsIv);
            activityName.setText(goodsDetailsInfo.getGroupName());
            goodsName.setText(goodsDetailsInfo.getName());
            discountMember.setText(goodsDetailsInfo.getDiscountMember() + goodsDetailsInfo.getUnit() + "成团");
            peopleNumber.setText(goodsDetailsInfo.getGroupPeopleNum() + "人已抢");
            goodsNumber.setText(goodsDetailsInfo.getGroupGoodsNum());
            countDown.setTimeOutListener(this);
            countDownTime.setTimeOutListener(this);
            goodsIntroduction.setText(goodsDetailsInfo.getBrief());
            currentPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(goodsDetailsInfo.getRetailPrice()));
            originalPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(goodsDetailsInfo.getCounterPrice()));
            if (!TextUtils.isEmpty(goodsDetailsInfo.getNow()) && !TextUtils.isEmpty(goodsDetailsInfo.getGroupExpireTime())) {
                long now = FormatUtil.dateToStamp(goodsDetailsInfo.getNow());
                long groupExpireTime = FormatUtil.dateToStamp(goodsDetailsInfo.getGroupExpireTime());
                if (now < groupExpireTime) {
                    countDown.startTearDown(groupExpireTime, now);
                    countDownTime.startTearDown(groupExpireTime, now);
                }
            }
            groupPeoples = goodsDetailsInfo.getGroupPeoples();
            if (groupPeoples != null && groupPeoples.size() > 0) {
                for (int i = 0; i < groupPeoples.size(); i++) {
                    View view1 = View.inflate(this, R.layout.item_group_people, null);
                    TextView people = view1.findViewById(R.id.people);
                    long now = FormatUtil.dateToStamp(goodsDetailsInfo.getNow());
                    long createTime = FormatUtil.dateToStamp(groupPeoples.get(i).getAddTime());
                    String timeLast = TimeUtil.getTimeFormatText(String.valueOf(now), String.valueOf(createTime));
                    people.setText(groupPeoples.get(i).getNickname() + timeLast + "参与拼单");
                    viewFlipper.addView(view1);
                }
                viewFlipper.setFlipInterval(2000);
                viewFlipper.startFlipping();
            }
            groupPurchases = goodsDetailsInfo.getGroupPurchases();
            if (groupPurchases != null && groupPurchases.size() == 1) {
                groupId = groupPurchases.get(0).getGrouponId();
                groupRulesId = groupPurchases.get(0).getRulesId();
            }
        }

    }

    @Override
    public void renderConsumerPhone(String consumerPhone) {

    }

    @Override
    public void renderCartFastAdd(ConfirmOrderDetail confirmOrderDetail) {
        ConfirmOrderActivity.start(this,confirmOrderDetail,StaticData.REFLASH_FOUR);
    }

    @Override
    public void setPresenter(HomepageContract.GoodsDetailsPresenter presenter) {

    }

    @Override
    public void timeOut() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTime.cancel();
        countDown.cancel();
    }

    @OnClick({R.id.confirm_bt, R.id.back, R.id.goods_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                goSelectSpec(StaticData.REFLASH_ONE);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.goods_rl:
                ActivityGoodsDetailActivity.start(this, goodsId);
                finish();
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
                        goodsDetailsPresenter.cartFastAdd(goodsId,productListCallableInfo.getId(),true,String.valueOf(goodsCount),groupId,groupRulesId);
                    }
                    break;
                default:
            }
        }
    }
}
