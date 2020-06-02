package com.mall.sls.common.unit;

import android.text.TextUtils;

/**
 * @author jwc on 2020/6/2.
 * 描述：
 */
public class BriefUnit {
    public static String returnBrief(String brief){
        String briefText= TextUtils.isEmpty(brief)?"":brief;
        int rd=Math.random()>0.5?1:0;
        if(rd==0){
            return "给你推荐一款必买好货！\n"+briefText;
        }else {
            return "爆款推荐，手慢无哦！\n"+briefText;
        }
    }
    public static String returnName(String price,String name){
        return "仅"+price+"元 | "+name;
    }
}
