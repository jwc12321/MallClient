package com.mall.sls.common;

import com.google.gson.annotations.SerializedName;

public class StaticData {
    public static final String  WX_APP_ID = "wxcea8bff4c8e832be";
    //登录中
    public static final String LOGGING="1";
    //加载中
    public static final String LOADING="2";
    //处理中
    public static final String PROCESSING ="3";
    //list每一页取10个
    public static final String TEN_LIST_SIZE = "10";
    public static final String REFLASH_ZERO = "0";
    public static final String REFLASH_ONE = "1";
    public static final String REFLASH_TWO = "2";
    public static final String REFLASH_THREE = "3";
    public static final String REFLASH_FOUR = "4";
    public static final String REFLASH_FIVE = "5";
    public static final String FIFTY_LIST_SIZE = "50";

    //通用文本title
    public static final String COMMON_TITLE = "commonTitle";
    //通用文本内容
    public static final String COMMON_CONTENT = "commonContent";
    public static final String COMMON_CONFIRM_BT = "commonComfirmBt";
    public static final String WEBVIEW_DETAILINFO="webviewDetailInfo";
    //用户协议
    public static final String USER_AGREEMENT="http://hhccrj.com/userRegisteredAgreement.html";
    //隐私政策
    public static final String USER_PRIVACY ="http://hhccrj.com/userPrivacyAgreement.html";
    //实人认证协议
    public static final String OCR_AGREEMENT="http://hhccrj.com/ocrAgreement.html";
    //选择的type
    public static final String CHOICE_TYPE="choiceType";
    //订单id
    public static final String GOODS_ORDER_ID="goodsOrderId";
    //商品id
    public static final String GOODS_ID="goodsId";
    //地址
    public static final String ADDRESS_INFO="addressInfo";
    //商品详情
    public static final String GOODS_DETAILS_INFO="goodsDetailsInfo";
    //选择的规格
    public static final String SKU_CHECK="skuCheck";
    //商品数量
    public static final String GOODS_COUNT="goodsCount";
    //商品规格
    public static final String SKU_INFO="skuInfo";
    //客服电话
    public static final String CONSUMER_PHONE="consumerPhone";
    //备注
    public static final String REMARK="remark";
    //下单商品详情
    public static final String CONFIRM_ORDER_DETAIL="confirmOrderDetail";
    //购物车id
    public static final String CART_IDS="cartIds";
    //用户优惠券ID
    public static final String USER_COUPON_ID="userCouponId";
    //优惠券ID
    public static final String COUPON_ID="couponId";

    //待支付
    public static final String TO_PAY="101";
    //已取消
    public static final String CANCELLED="102";
    //已取消
    public static final String SYS_CANCELLED="103";
    //待退款
    public static final String PENDING_REFUND="202";
    //已退款
    public static final String REFUNDED="203";
    //代分享
    public static final String TO_BE_SHARE="204";
    //代发货
    public static final String TO_BE_DELIVERED="206";
    //待收货
    public static final String TO_BE_RECEIVED="301";
    //已收货
    public static final String RECEIVED="401";
    //已收货
    public static final String SYS_RECEIVED="402";
    //typeId
    public static final String TYPE_ID="typeId";
    //电话
    public static final String MOBILE="mobile";
    //剩余人数
    public static final String SURPLUS="surplus";
    //购买方式
    public static final String PURCHASE_TYPE="purchaseType";
    //选择支付宝还是微信  0：微信 1：支付宝
    public static final String SELECT_TYPE="selectType";
    //头像
    public static final String AVATAR_URL="avatarUrl";
    //金额
    public static final String PAYMENT_AMOUNT="paymentAmount";
    //unionId
    public static final String UNION_ID="unionId";//微信登录的unionid
    public static final String ACCESS_CODE="accessCode";//一键登录的code
    public static final String SMS_CODE="smsCode";//短信验证码
    public static final String CRETIFY_AMOUNT="certifyAmount";//实人认证费用
    public static final String VIP_AMOUNT="vipAmount";//开通超级会员费用
    public static final String GROUPON_ID="grouponId";//团购ID
    public static final String ACTIVITY_URL="activityUrl";//活动团分享url
    public static final String END_TIME="endTime";//结束时间
    public static final String BACK_TYPE="backType";
    public static final String GOODS_PRODUCT_ID="goodsProductId";//skuId

    public static final String CANCEL_TEXT="cancelText";
    public static final String CONFIRM_TEXT="confirmText";
    public static final String TIP_BACK="tipBack";
    public static final String GOODS_NAME="goodsName";
    public static final String GOODS_BRIEF="goodsBrief";
    public static final String WX_URL="wxUrl";//分享h5url
    public static final String INVITE_CODE="inviteCode";//邀请码
    public static final String WX_INVITE_CODE="?invitationCode=";
    public static final String PIC_URL="picUrl";
    public static final String CITY_DATA = "china_city_data.json";
    public static final String ADDRESS_ID="addressId";
    public static final String VIP_DESCRIPTION="vipDescription";//超级会员描述
    public static final String CERTIFY_PAY ="certifyPay";//是否已经支付实人认证钱
    public static final String SHIP_ORDER_INFOS="shipOrderInfos";//物流信息
    public static final String LOCAL_CITY="localCity";
    public static final String CHOICE_CITY="choiceType";
    public static final String PROVINCE="province";
    public static final String CITY="city";
    public static final String COUNT="county";
    public static final String LAT="lat";
    public static final String LON="lon";
    public static final String DETAIL_ADDRESS="detailAddress";
    public static final String VIP_EXPIRE_DATE="vipExpireDate";//过期时间
    public static final String PRIZE_NUMBER="prizeNumber";//抽奖机会
    public static final String PRIZE_VO="prizeVo";//抽奖信息
    public static final String PRIZE_ID="prizeId";
    public static final String PRIZE_TIME="prizeTime";

    public static final String SELECT_ID="selectId";
    public static final String AREA_CODE="areaCode";
    public static final String ADDRESS_TITLE="addressTitle";
    public static final String ORDER_STATUS="orderStatus";//订单状态
    public static final String PAY_TYPE="payType";//支付方式
    public static final String REFUND_TIME="refundTime";//退款处理时间
    public static final String ARRIVAL_TIME="arrivalTime";//到账时间
    public static final String LANDING_PAGE_URL="landingPageUrl";//落地页url

    //推送，金刚区，banner
    public static final String ORDERDETAIL="ORDERDETAIL";//订单详情
    public static final String GOODS_INFO ="GOODSINFO";//banner挑商品详情
    public static final String COUPON ="COUPON";//优惠卷
    public static final String INVITATION ="INVITATION";//邀请好友
    public static final String SECKILL ="SECKILL";//秒杀
    public static final String ADDRESS ="ADDRESS";
    public static final String PRIZE="PRIZE";//抽奖
    public static final String FANS="FANS";//我的粉丝
    public static final String MSGBOX="MSGBOX";//消息盒子

    public static final String PRODUCT_LIST="productList";//规格价格列表
    public static final String SPECIFICATION_LIST="specificationList";//规格列表
    public static final String CART_ITEM_IDS="cartItemIds";//购物车每个item的ids
}
