package com.mall.sls.data.remote;


import com.mall.sls.data.RemoteDataWrapper;
import com.mall.sls.data.entity.AddressInfo;
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.data.entity.ConfirmOrderDetail;
import com.mall.sls.data.entity.CouponInfo;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.entity.GoodsOrderDetails;
import com.mall.sls.data.entity.HomePageInfo;
import com.mall.sls.data.entity.Ignore;
import com.mall.sls.data.entity.InvitationCodeInfo;
import com.mall.sls.data.entity.InviteInfo;
import com.mall.sls.data.entity.JoinPrizeInfo;
import com.mall.sls.data.entity.LocalTeam;
import com.mall.sls.data.entity.LotteryItemInfo;
import com.mall.sls.data.entity.MessageInfo;
import com.mall.sls.data.entity.MessageTypeInfo;
import com.mall.sls.data.entity.MineInfo;
import com.mall.sls.data.entity.MyCouponInfo;
import com.mall.sls.data.entity.OrderList;
import com.mall.sls.data.entity.OrderSubmitInfo;
import com.mall.sls.data.entity.PrizeVo;
import com.mall.sls.data.entity.ProvinceBean;
import com.mall.sls.data.entity.ShareInfo;
import com.mall.sls.data.entity.TeamInfo;
import com.mall.sls.data.entity.TokenInfo;
import com.mall.sls.data.entity.VipAmountInfo;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.request.AddAddressRequest;
import com.mall.sls.data.request.CartFastaddRequest;
import com.mall.sls.data.request.CertifyIdRequest;
import com.mall.sls.data.request.CodeRequest;
import com.mall.sls.data.request.DescriptionRequest;
import com.mall.sls.data.request.GroupRemindRequest;
import com.mall.sls.data.request.JoinPrizeRequest;
import com.mall.sls.data.request.LoginRequest;
import com.mall.sls.data.request.MobileRequest;
import com.mall.sls.data.request.MsgIdRequest;
import com.mall.sls.data.request.MsgReadRequest;
import com.mall.sls.data.request.OnClickBindRequest;
import com.mall.sls.data.request.OneClickLoginRequest;
import com.mall.sls.data.request.OrderPayRequest;
import com.mall.sls.data.request.OrderRequest;
import com.mall.sls.data.request.OrderSubmitRequest;
import com.mall.sls.data.request.SmsCodeBindRequest;
import com.mall.sls.data.request.UserPayDtoRequest;
import com.mall.sls.data.request.WeiXinLoginRequest;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/27.
 */

public interface RestApiService {

    //获取下载地址
    @GET("api/public/f/version/android")
    Flowable<RemoteDataWrapper<AppUrlInfo>> getAppUrlInfo(@Header("X-Hc-Sign") String sign, @Query("version") String version);

    //登录
    @POST("app/auth/login/code")
    Flowable<RemoteDataWrapper<TokenInfo>> loginIn(@Header("X-Hc-Sign") String sign, @Body LoginRequest loginRequest);

    //一键登录
    @POST("app/auth/login/ali")
    Flowable<RemoteDataWrapper<TokenInfo>> oneClickLogin(@Header("X-Hc-Sign") String sign, @Body OneClickLoginRequest request);

    //发送验证码
    @POST("app/auth/regCaptcha")
    Flowable<RemoteDataWrapper<String>> sendCodeV(@Header("X-Hc-Sign") String sign, @Body MobileRequest request);

    //获取用户认证状态
    @GET("app/certify")
    Flowable<RemoteDataWrapper<Boolean>> getUsersRpStatus(@Header("X-Hc-Sign") String sign);

    //获取首页信息
    @GET("app/home/index")
    Flowable<RemoteDataWrapper<HomePageInfo>> getHomePageInfo(@Header("X-Hc-Sign") String sign);

    //获取商品详情
    @GET("app/goods/detail")
    Flowable<RemoteDataWrapper<GoodsDetailsInfo>> getGoodsDetailsInfo(@Header("X-Hc-Sign") String sign, @Query("goodsId") String goodsId);

    //获取地址列表
    @GET("app/address/list")
    Flowable<RemoteDataWrapper<List<AddressInfo>>> getAddressInfo(@Header("X-Hc-Sign") String sign);

    //新增地址
    @POST("app/address/save")
    Flowable<RemoteDataWrapper<String>> addAddress(@Header("X-Hc-Sign") String sign, @Body AddAddressRequest request);

    //获取客服信息
    @GET("app/common/customer")
    Flowable<RemoteDataWrapper<String>> getConsumerPhone(@Header("X-Hc-Sign") String sign);

    //获取地址
    @GET("app/common/area")
    Flowable<RemoteDataWrapper<List<ProvinceBean>>> getAreas(@Header("X-Hc-Sign") String sign);

    //删除地址
    @DELETE("app/address/{id}")
    Flowable<RemoteDataWrapper<Ignore>> deleteAddress(@Header("X-Hc-Sign") String sign, @Path("id") String id);

    //商品购买接口
    @POST("app/cart/fastadd")
    Flowable<RemoteDataWrapper<ConfirmOrderDetail>> cartFastAdd(@Header("X-Hc-Sign") String sign, @Body CartFastaddRequest request);

    //订单预提交
    @GET("app/cart/checkout")
    Flowable<RemoteDataWrapper<ConfirmOrderDetail>> cartCheckout(@Header("X-Hc-Sign") String sign, @Query("addressId") String addressId, @Query("cartId") String cartId, @Query("couponId") String couponId, @Query("userCouponId") String userCouponId);

    //查询当前预提交订单可用优惠券
    @GET("app/coupon/selectlist")
    Flowable<RemoteDataWrapper<List<CouponInfo>>> getCouponSelect(@Header("X-Hc-Sign") String sign, @Query("cartIds") String cartIds);

    //提交订单
    @POST("app/order/submit")
    Flowable<RemoteDataWrapper<OrderSubmitInfo>> orderSubmit(@Header("X-Hc-Sign") String sign, @Body OrderSubmitRequest request);

    //获取优惠卷列表
    @GET("app/coupon/mylist")
    Flowable<RemoteDataWrapper<MyCouponInfo>> getCouponInfos(@Header("X-Hc-Sign") String sign, @Query("status") String status, @Query("page") String page, @Query("limit") String limit);

    //本地拼团
    @GET("app/goods")
    Flowable<RemoteDataWrapper<LocalTeam>> getLocalTeam(@Header("X-Hc-Sign") String sign, @Query("type") String type, @Query("page") String page, @Query("limit") String limit);

    //获取我的信息
    @GET("app/auth/info")
    Flowable<RemoteDataWrapper<MineInfo>> getMineInfo(@Header("X-Hc-Sign") String sign);

    //开始认证流程
    @POST("app/certify")
    Flowable<RemoteDataWrapper<String>> getCertifyId(@Header("X-Hc-Sign") String sign, @Body CertifyIdRequest request);

    //获取订单列表
    @GET("app/order/list")
    Flowable<RemoteDataWrapper<OrderList>> getOrderList(@Header("X-Hc-Sign") String sign, @Query("showType") String showType, @Query("page") String page, @Query("limit") String limit);

    //获取订单详情
    @GET("app/order/detail")
    Flowable<RemoteDataWrapper<GoodsOrderDetails>> getGoodsOrderDetails(@Header("X-Hc-Sign") String sign, @Query("orderId") String orderId);

    //获取我的邀请
    @GET("app/auth/inviter")
    Flowable<RemoteDataWrapper<List<InviteInfo>>> getInviteInfos(@Header("X-Hc-Sign") String sign);

    //获取消息类型
    @GET("app/msg/type")
    Flowable<RemoteDataWrapper<List<MessageTypeInfo>>> getMessageTypeInfos(@Header("X-Hc-Sign") String sign);

    //获取用户消息列表
    @GET("app/msg/list")
    Flowable<RemoteDataWrapper<MessageInfo>> getMessageInfo(@Header("X-Hc-Sign") String sign, @Query("typeId") String typeId, @Query("page") String page, @Query("limit") String limit);

    @POST("app/msg/empty")
    Flowable<RemoteDataWrapper<Ignore>> msgEmpty(@Header("X-Hc-Sign") String sign, @Body MsgIdRequest request);

    @POST("app/msg/changeStatus")
    Flowable<RemoteDataWrapper<Ignore>> msgChangeStatus(@Header("X-Hc-Sign") String sign, @Body MsgReadRequest request);

    //我的拼团
    @GET("app/groupon")
    Flowable<RemoteDataWrapper<TeamInfo>> getTeamInfo(@Header("X-Hc-Sign") String sign, @Query("page") String page, @Query("limit") String limit);

    //超级会员专享团购
    @GET("app/goods/vipGroupons")
    Flowable<RemoteDataWrapper<LocalTeam>> getVipGroupons(@Header("X-Hc-Sign") String sign, @Query("page") String page, @Query("limit") String limit);

    //支付宝支付实人认证和超级会员
    @POST("app/user/pay/appPay")
    Flowable<RemoteDataWrapper<String>> alipayMember(@Header("X-Hc-Sign") String sign, @Body UserPayDtoRequest request);

    //活动团开团提醒
    @POST("app/goods/groupRemind")
    Flowable<RemoteDataWrapper<Ignore>> groupRemind(@Header("X-Hc-Sign") String sign, @Body GroupRemindRequest request);

    //开通超级会员
    @POST("app/vip/open")
    Flowable<RemoteDataWrapper<Boolean>> vipOpen(@Header("X-Hc-Sign") String sign);

    //支付宝支付订单
    @POST("app/order/pay")
    Flowable<RemoteDataWrapper<String>> orderAliPay(@Header("X-Hc-Sign") String sign, @Body OrderPayRequest request);

    //app微信code码登陆
    @POST("app/auth/login/wx/code")
    Flowable<RemoteDataWrapper<TokenInfo>> weiXinLogin(@Header("X-Hc-Sign") String sign, @Body WeiXinLoginRequest request);

    //获取默认的推荐码
    @GET("app/common/invitationCode")
    Flowable<RemoteDataWrapper<String>> getInvitationCode(@Header("X-Hc-Sign") String sign);

    //app微信绑定手机号登陆
    @POST("app/auth/login/wx/phone")
    Flowable<RemoteDataWrapper<TokenInfo>> bindSmsCodeLogin(@Header("X-Hc-Sign") String sign, @Body SmsCodeBindRequest request);

    //阿里一键登录接口(绑定微信)
    @POST("app/auth/login/wx/ali")
    Flowable<RemoteDataWrapper<TokenInfo>> bindOneClickLogin(@Header("X-Hc-Sign") String sign, @Body OnClickBindRequest request);

    //查询认证或者超级会员支付金额
    @GET("app/common/payAmount")
    Flowable<RemoteDataWrapper<VipAmountInfo>> getVipAmountInfo(@Header("X-Hc-Sign") String sign);

    //H5团购详情
    @GET("app/goods/grouponDetail")
    Flowable<RemoteDataWrapper<GoodsDetailsInfo>> getWXGoodsDetailsInfo(@Header("X-Hc-Sign") String sign, @Query("goodsProductId") String goodsProductId, @Query("grouponId") String grouponId);

    //分享信息
    @GET("app/auth/share")
    Flowable<RemoteDataWrapper<ShareInfo>> getShareInfo(@Header("X-Hc-Sign") String sign);

    //获取邀请码和h5链接地址
    @GET("app/auth/invitationCode")
    Flowable<RemoteDataWrapper<InvitationCodeInfo>> getInvitationCodeInfo(@Header("X-Hc-Sign") String sign);

    //意见反馈
    @POST("app/auth/addFeedBack")
    Flowable<RemoteDataWrapper<Boolean>> addFeedBack(@Header("X-Hc-Sign") String sign, @Body DescriptionRequest request);

    //微信支付实人认证和超级会员
    @POST("app/user/pay/wx")
    Flowable<RemoteDataWrapper<WXPaySignResponse>> wxPayMember(@Header("X-Hc-Sign") String sign, @Body UserPayDtoRequest request);

    //微信支付订单
    @POST("app/order/wxPay")
    Flowable<RemoteDataWrapper<WXPaySignResponse>> orderWxPay(@Header("X-Hc-Sign") String sign, @Body OrderPayRequest request);

    //取消订单
    @POST("app/order/cancel")
    Flowable<RemoteDataWrapper<Ignore>> cancelOrder(@Header("X-Hc-Sign") String sign, @Body OrderRequest request);

    //绑定微信
    @POST("app/auth/bindWx")
    Flowable<RemoteDataWrapper<Ignore>> bindWx(@Header("X-Hc-Sign") String sign, @Body CodeRequest request);

    //抽奖列表
    @GET("app/prize/list")
    Flowable<RemoteDataWrapper<LotteryItemInfo>> getLotteryItemInfo(@Header("X-Hc-Sign") String sign, @Query("page") String page, @Query("limit") String limit);

    //获取系统时间
    @GET("app/prize/systemTime")
    Flowable<RemoteDataWrapper<String>> getSystemTime(@Header("X-Hc-Sign") String sign);

    //抽奖结果
    @GET("app/prize/prizeResult")
    Flowable<RemoteDataWrapper<List<String>>> getPrizeResult(@Header("X-Hc-Sign") String sign, @Query("prizeId") String prizeId);

    //参加抽奖
    @POST("app/prize/joinPrize")
    Flowable<RemoteDataWrapper<JoinPrizeInfo>> getJoinPrizeInfo(@Header("X-Hc-Sign") String sign, @Body JoinPrizeRequest request);

    //开奖历史
    @GET("app/prize/history")
    Flowable<RemoteDataWrapper<List<PrizeVo>>> getLotteryRecord(@Header("X-Hc-Sign") String sign, @Query("page") String page, @Query("limit") String limit);

}
