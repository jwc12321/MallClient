package com.mall.sls.service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.mall.sls.R;
import com.mall.sls.address.ui.AddressManageActivity;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.ClientIdManager;
import com.mall.sls.common.unit.SpikeManager;
import com.mall.sls.common.unit.TCAgentUnit;
import com.mall.sls.coupon.ui.CouponActivity;
import com.mall.sls.data.entity.GeTuiInfo;
import com.mall.sls.data.entity.LinkUrlInfo;
import com.mall.sls.homepage.ui.ActivityGroupGoodsActivity;
import com.mall.sls.homepage.ui.OrdinaryGoodsDetailActivity;
import com.mall.sls.lottery.ui.LotteryListActivity;
import com.mall.sls.message.ui.OrderMessageActivity;
import com.mall.sls.mine.ui.InviteFriendsActivity;
import com.mall.sls.mine.ui.MyInvitationActivity;
import com.mall.sls.order.ui.GoodsOrderDetailsActivity;
import com.mall.sls.webview.ui.LandingPageActivity;

import java.util.HashMap;
import java.util.Map;

public class GeTuiService extends GTIntentService {
    private String clickMessageId;
    private Gson gson = new Gson();
    private Map<String, GeTuiInfo> map = new HashMap<>();
    private String nativeType;

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    // 处理透传消息
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        // 透传消息的处理，详看 SDK demo
        String msgStr = new String(msg.getPayload());
        Log.d("111", "数据GTTransmitMessage" + msgStr + "==" + msg.getMessageId());
        LinkUrlInfo linkUrlInfo = gson.fromJson(msgStr, LinkUrlInfo.class);
        linkUrlClick(linkUrlInfo);
    }


    private void linkUrlClick(LinkUrlInfo linkUrlInfo) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (linkUrlInfo != null) {
            if (TextUtils.equals(StaticData.REFLASH_ZERO, linkUrlInfo.getLinkType())) {//h5界面
                intent.setClass(this, LandingPageActivity.class);
                intent.putExtra(StaticData.LANDING_PAGE_URL, linkUrlInfo.getLink());
            } else if (TextUtils.equals(StaticData.REFLASH_ONE, linkUrlInfo.getLinkType())) {
                nativeType = linkUrlInfo.getNativeType();
                if (TextUtils.equals(StaticData.ORDERDETAIL, nativeType)) {//订单详情
                    if (!TextUtils.isEmpty(linkUrlInfo.getLink())) {
                        Uri uri = Uri.parse("?" + linkUrlInfo.getLink());
                        String orderId = uri.getQueryParameter("orderId");
                        intent.setClass(this, GoodsOrderDetailsActivity.class);
                        intent.putExtra(StaticData.GOODS_ORDER_ID, orderId);
                    }
                } else if (TextUtils.equals(StaticData.FANS, nativeType)) {//我的粉丝
                    intent.setClass(this, MyInvitationActivity.class);
                } else if (TextUtils.equals(StaticData.MSGBOX, nativeType)) {//消息盒子
                    if (!TextUtils.isEmpty(linkUrlInfo.getLink())) {
                        Uri uri = Uri.parse("?" + linkUrlInfo.getLink());
                        String id = uri.getQueryParameter("id");
                        intent.setClass(this, OrderMessageActivity.class);
                        intent.putExtra(StaticData.TYPE_ID, id);
                    }
                } else if (TextUtils.equals(StaticData.PRIZE, nativeType)) {//抽奖
                    intent.setClass(this, LotteryListActivity.class);
                }
            }
            startActivity(intent);
        }
    }


    // 接收 cid
    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
        ClientIdManager.saveClientId(clientid);
    }

    // cid 离线上线通知
    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    // 各种事件处理回执
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }

    // 通知到达，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
    }

    // 通知点击，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
        Log.d("111", "GTNotificationMessage" + msg.getMessageId());
        this.clickMessageId = msg.getMessageId();
//        GeTuiInfo geTuiInfo=map.get(clickMessageId);
//        Log.d("111","GTNotificationMessage==="+geTuiInfo.getNickName());
    }
}
