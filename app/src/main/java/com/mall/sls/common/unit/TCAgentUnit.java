package com.mall.sls.common.unit;

import android.app.Activity;
import android.content.Context;

import com.tendcloud.tenddata.TCAgent;

/**
 * @author jwc on 2020/6/17.
 * 描述：maidian
 */
public class TCAgentUnit {
    public static void pageStart(Context context,String activityName){
        TCAgent.onPageStart(context,activityName);
    }

    public static void pageEnd(Context context,String activityName){
        TCAgent.onPageEnd(context,activityName);
    }

    public static void setEventId(Context context, String eventId){
        TCAgent.onEvent(context, eventId);
    }

    public static void setEventIdLabel(Context context, String eventId,String label){
        TCAgent.onEvent(context, eventId,label);
    }
}
