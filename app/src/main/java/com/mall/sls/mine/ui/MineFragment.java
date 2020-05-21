package com.mall.sls.mine.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.BaseFragment;
import com.mall.sls.R;
import com.mall.sls.address.ui.AddressManageActivity;
import com.mall.sls.certify.ui.CerifyTipActivity;
import com.mall.sls.certify.ui.NameVerifiedActivity;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.VerifyManager;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.coupon.ui.CouponActivity;
import com.mall.sls.data.entity.MineInfo;
import com.mall.sls.data.entity.MineRewardInfo;
import com.mall.sls.data.entity.UserInfo;
import com.mall.sls.login.ui.LoginActivity;
import com.mall.sls.member.ui.SuperMemberActivity;
import com.mall.sls.mine.DaggerMineComponent;
import com.mall.sls.mine.MineContract;
import com.mall.sls.mine.MineModule;
import com.mall.sls.mine.presenter.MineInfoPresenter;
import com.mall.sls.order.ui.GoodsOrderActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/7.
 * 描述：
 */
public class MineFragment extends BaseFragment implements MineContract.MineInfoView {

    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.right_iv)
    ImageView rightIv;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.phone)
    MediumThickTextView phone;
    @BindView(R.id.member_type_iv)
    ImageView memberTypeIv;
    @BindView(R.id.cash_back)
    MediumThickTextView cashBack;
    @BindView(R.id.coupon)
    MediumThickTextView coupon;
    @BindView(R.id.coupon_ll)
    LinearLayout couponLl;
    @BindView(R.id.dividend)
    MediumThickTextView dividend;
    @BindView(R.id.rice_grain)
    MediumThickTextView riceGrain;
    @BindView(R.id.head_photo)
    RoundedImageView headPhoto;
    @BindView(R.id.right_arrow_iv)
    ImageView rightArrowIv;
    @BindView(R.id.all_order_rl)
    RelativeLayout allOrderRl;
    @BindView(R.id.pending_payment_iv)
    ImageView pendingPaymentIv;
    @BindView(R.id.pending_delivery_iv)
    ImageView pendingDeliveryIv;
    @BindView(R.id.shipping_iv)
    ImageView shippingIv;
    @BindView(R.id.vip_iv)
    ImageView vipIv;
    @BindView(R.id.vip_type)
    ConventionalTextView vipType;
    @BindView(R.id.super_member_rl)
    RelativeLayout superMemberRl;
    @BindView(R.id.my_team)
    ImageView myTeam;
    @BindView(R.id.address_manage)
    ImageView addressManage;
    @BindView(R.id.invite_friends)
    ImageView inviteFriends;
    @BindView(R.id.verified_iv)
    ImageView verifiedIv;
    @BindView(R.id.my_invitation_iv)
    ImageView myInvitationIv;


    @Inject
    MineInfoPresenter mineInfoPresenter;
    private String goVerify = "0";

    private UserInfo userInfo;
    private List<MineRewardInfo> mineRewardInfos;
    private String avatarUrl;
    private String mobile;
    private boolean certifyPay;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHeight(null, title, rightIv);
    }

    @Override
    protected void initializeInjector() {
        DaggerMineComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mineModule(new MineModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.equals(StaticData.REFLASH_ONE, goVerify)) {
            mineInfoPresenter.getMineInfo();
            goVerify = StaticData.REFLASH_ZERO;
        }
    }

    @OnClick({R.id.right_iv, R.id.all_order_rl, R.id.pending_payment_iv, R.id.pending_delivery_iv, R.id.shipping_iv, R.id.my_team, R.id.address_manage, R.id.invite_friends, R.id.verified_iv, R.id.my_invitation_iv, R.id.super_member_rl, R.id.coupon_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_iv://设置
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivityForResult(intent, RequestCodeStatic.SETTING);
                break;
            case R.id.all_order_rl://全部订单
                GoodsOrderActivity.start(getActivity(), "0");
                break;
            case R.id.pending_payment_iv://待付款
                GoodsOrderActivity.start(getActivity(), "1");
                break;
            case R.id.pending_delivery_iv://待配送
                GoodsOrderActivity.start(getActivity(), "2");
                break;
            case R.id.shipping_iv://配送中
                GoodsOrderActivity.start(getActivity(), "3");
                break;
            case R.id.my_team://我的拼团
                MyTeamActivity.start(getActivity());
                break;
            case R.id.address_manage://地址管理
                AddressManageActivity.start(getActivity(), StaticData.REFLASH_ONE);
                break;
            case R.id.invite_friends://邀请好友
                break;
            case R.id.verified_iv://认证
                if (TextUtils.equals(StaticData.REFLASH_ZERO, VerifyManager.getVerify())) {
                    goVerify = StaticData.REFLASH_ONE;
                    if(certifyPay){
                        NameVerifiedActivity.start(getActivity());
                    }else {
                        CerifyTipActivity.start(getActivity());
                    }
                }
                break;
            case R.id.my_invitation_iv://我的邀请
                MyInvitationActivity.start(getActivity());
                break;
            case R.id.super_member_rl://超级会员
                if (TextUtils.equals(StaticData.REFLASH_ZERO, VerifyManager.getVerify())) {
                    showMessage(getString(R.string.to_open_person_authentication));
                } else {
                    goVerify = StaticData.REFLASH_ONE;
                    SuperMemberActivity.start(getActivity(), avatarUrl, mobile);
                }
                break;
            case R.id.coupon_ll://优惠卷
                Intent remarkIntent = new Intent(getActivity(), CouponActivity.class);
                startActivityForResult(remarkIntent, RequestCodeStatic.GO_COUPON);
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.SETTING:
                    LoginActivity.start(getActivity());
                    getActivity().finish();
                    break;
                case RequestCodeStatic.GO_COUPON:
                    if (listener != null) {
                        listener.goLocalTeam();
                    }
                    break;
                default:
            }
        }
    }

    public interface MineListener {
        void goLocalTeam();
    }

    private MineListener listener;

    public void setMineListener(MineListener listener) {
        this.listener = listener;
    }

    @Override
    public void renderMineInfo(MineInfo mineInfo) {
        if (mineInfo != null) {
            userInfo = mineInfo.getUserInfo();
            if (userInfo != null) {
                avatarUrl = userInfo.getAvatarUrl();
                mobile = userInfo.getMobile();
                certifyPay=userInfo.getCertifyPay();
                GlideHelper.load(getActivity(), avatarUrl, R.mipmap.icon_defalut_head, headPhoto);
                phone.setText(mobile);
                VerifyManager.saveVerify(userInfo.getUserLevel());
                if (TextUtils.equals(StaticData.REFLASH_ZERO, userInfo.getUserLevel())) {
                    memberTypeIv.setBackgroundResource(R.mipmap.icon_ordinary_member);
                    verifiedIv.setSelected(false);
                    vipType.setText(getString(R.string.open_now));
                } else if (TextUtils.equals(StaticData.REFLASH_ONE, userInfo.getUserLevel())) {
                    memberTypeIv.setBackgroundResource(R.mipmap.icon_certified_member);
                    verifiedIv.setSelected(true);
                    vipType.setText(getString(R.string.open_now));
                } else if (TextUtils.equals(StaticData.REFLASH_TWO, userInfo.getUserLevel())) {
                    memberTypeIv.setBackgroundResource(R.mipmap.icon_super_member);
                    verifiedIv.setSelected(true);
                    vipType.setText(getString(R.string.view_now));
                }
            }
            mineRewardInfos = mineInfo.getMineRewardInfos();
            for (MineRewardInfo mineRewardInfo : mineRewardInfos) {
                if (TextUtils.equals(getString(R.string.cash_back), mineRewardInfo.getDes())) {
                    cashBack.setText(mineRewardInfo.getValue());
                } else if (TextUtils.equals(getString(R.string.coupon), mineRewardInfo.getDes())) {
                    coupon.setText(mineRewardInfo.getValue());
                } else if (TextUtils.equals(getString(R.string.dividend), mineRewardInfo.getDes())) {
                    dividend.setText(mineRewardInfo.getValue());
                } else if (TextUtils.equals(getString(R.string.rice_grain), mineRewardInfo.getDes())) {
                    riceGrain.setText(mineRewardInfo.getValue());
                }

            }
        }
    }

    @Override
    public void setPresenter(MineContract.MineInfoPresenter presenter) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && mineInfoPresenter != null) {
            mineInfoPresenter.getMineInfo();
        }
    }
}
