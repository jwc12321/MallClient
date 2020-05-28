package com.mall.sls.common.unit;

public class AvatarUrlManager {

    public static final String AVATAR_URL = "AvatarUrl";

    public static String getAvatarUrl(){

        return SPManager.getInstance().getData(AVATAR_URL);
    }

    public static void saveAvatarUrl(String avatarUrl){
        SPManager.getInstance().putData(AVATAR_URL,avatarUrl);
    }
}
