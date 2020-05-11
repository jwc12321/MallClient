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
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.unit.VerifyManager;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.login.ui.LoginActivity;
import com.mall.sls.order.ui.GoodsOrderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/7.
 * 描述：
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.right_iv)
    ImageView rightIv;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.phone)
    MediumThickTextView phone;
    @BindView(R.id.number_ll)
    LinearLayout numberLl;
    @BindView(R.id.head_photo)
    RoundedImageView headPhoto;
    @BindView(R.id.member_type_iv)
    ImageView memberTypeIv;
    @BindView(R.id.right_arrow_iv)
    ImageView rightArrowIv;
    @BindView(R.id.all_order_rl)
    RelativeLayout allOrderRl;
    @BindView(R.id.vip_iv)
    ImageView vipIv;
    @BindView(R.id.pending_payment_iv)
    ImageView pendingPaymentIv;
    @BindView(R.id.pending_delivery_iv)
    ImageView pendingDeliveryIv;
    @BindView(R.id.shipping_iv)
    ImageView shippingIv;
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
        initView();
    }

    private void initView() {
        memberTypeIv.setBackgroundResource(R.mipmap.icon_certified_member);
    }

    @Override
    public void onResume() {
        super.onResume();
        verifiedIv.setSelected(TextUtils.equals("1",VerifyManager.getVerify()));
    }

    @OnClick({R.id.right_iv, R.id.all_order_rl, R.id.pending_payment_iv, R.id.pending_delivery_iv, R.id.shipping_iv, R.id.my_team, R.id.address_manage, R.id.invite_friends, R.id.verified_iv, R.id.my_invitation_iv})
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
                AddressManageActivity.start(getActivity());
                break;
            case R.id.invite_friends://邀请好友
                break;
            case R.id.verified_iv://认证
                if (!TextUtils.equals("1", VerifyManager.getVerify())) {
                    CerifyTipActivity.start(getActivity());
                }
                break;
            case R.id.my_invitation_iv://我的邀请
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
                default:
            }
        }
    }

}
