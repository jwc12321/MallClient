package com.mall.sls.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.mall.sls.common.unit.ClientIdManager;
import com.mall.sls.data.entity.GeTuiInfo;

import java.util.HashMap;
import java.util.Map;

public class GeTuiService extends GTIntentService {
    private String clickMessageId;
    private Gson gson=new Gson();
    private Map<String,GeTuiInfo> map= new HashMap<>();

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    // 处理透传消息
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        // 透传消息的处理，详看 SDK demo
        String msgStr = new String(msg.getPayload());
        Log.d("111","数据GTTransmitMessage"+msgStr+"=="+msg.getMessageId());
//        if(!TextUtils.isEmpty(msgStr)) {
//            GeTuiInfo geTuiInfo = gson.fromJson(msgStr, GeTuiInfo.class);
//            map.put(msg.getMessageId(), geTuiInfo);
//        }
//        Log.e(TAG, "GTTransmitMessage -> " + msgStr+"=="+msg.getMessageId());
//        if(clickMessageId!=null){//有点击过了通知
//            if(TextUtils.equals(clickMessageId,msg.getMessageId())){
//                //做后面的页面跳转处理
//                Intent intent = new Intent(context, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.putExtra("111", msgStr);
//                //***在这里需要向跳转的activity中传递参数
//                startActivity(intent);
//            }
//        }
//        clickMessageId=null;
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
        Log.d("111","GTNotificationMessage"+msg.getMessageId());
        this.clickMessageId=msg.getMessageId();
//        GeTuiInfo geTuiInfo=map.get(clickMessageId);
//        Log.d("111","GTNotificationMessage==="+geTuiInfo.getNickName());
    }
}
