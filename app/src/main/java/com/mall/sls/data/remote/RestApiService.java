package com.mall.sls.data.remote;


import com.google.gson.annotations.SerializedName;
import com.mall.sls.data.RemoteDataWrapper;
import com.mall.sls.data.entity.AddressInfo;
import com.mall.sls.data.entity.AiNongPay;
import com.mall.sls.data.entity.AliPay;
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.data.entity.BankCardInfo;
import com.mall.sls.data.entity.BankPayInfo;
import com.mall.sls.data.entity.BaoFuPay;
import com.mall.sls.data.entity.BaoFuPayInfo;
import com.mall.sls.data.entity.BindResultInfo;
import com.mall.sls.data.entity.CardInfo;
import com.mall.sls.data.entity.CartAddInfo;
import com.mall.sls.data.entity.CartInfo;
import com.mall.sls.data.entity.CertifyInfo;
import com.mall.sls.data.entity.ConfirmCartOrderDetail;
import com.mall.sls.data.entity.ConfirmOrderDetail;
import com.mall.sls.data.entity.CouponInfo;
import com.mall.sls.data.entity.FirstCategory;
import com.mall.sls.data.entity.FirstCategoryInfo;
import com.mall.sls.data.entity.GeneralGoodsDetailsInfo;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.entity.GoodsItem;
import com.mall.sls.data.entity.GoodsItemInfo;
import com.mall.sls.data.entity.GoodsOrderDetails;
import com.mall.sls.data.entity.HomePageInfo;
import com.mall.sls.data.entity.HomeSnapUp;
import com.mall.sls.data.entity.HomeSnapUpInfo;
import com.mall.sls.data.entity.Ignore;
import com.mall.sls.data.entity.IntegralPointsInfo;
import com.mall.sls.data.entity.InvitationCodeInfo;
import com.mall.sls.data.entity.InviteInfo;
import com.mall.sls.data.entity.JoinPrizeInfo;
import com.mall.sls.data.entity.LocalTeam;
import com.mall.sls.data.entity.LotteryItemInfo;
import com.mall.sls.data.entity.LotteryRecord;
import com.mall.sls.data.entity.MerchantCertifyInfo;
import com.mall.sls.data.entity.MessageInfo;
import com.mall.sls.data.entity.MessageTypeInfo;
import com.mall.sls.data.entity.MineInfo;
import com.mall.sls.data.entity.MyCouponInfo;
import com.mall.sls.data.entity.OrderAddCartInfo;
import com.mall.sls.data.entity.OrderList;
import com.mall.sls.data.entity.OrderPackageInfo;
import com.mall.sls.data.entity.OrderSubmitInfo;
import com.mall.sls.data.entity.PayMethodInfo;
import com.mall.sls.data.entity.ProvinceBean;
import com.mall.sls.data.entity.RefundInfo;
import com.mall.sls.data.entity.SearchHistory;
import com.mall.sls.data.entity.SecondCategory;
import com.mall.sls.data.entity.SecondCategoryInfo;
import com.mall.sls.data.entity.ShareInfo;
import com.mall.sls.data.entity.TeamInfo;
import com.mall.sls.data.entity.TokenInfo;
import com.mall.sls.data.entity.TokenRefreshInfo;
import com.mall.sls.data.entity.UploadUrlInfo;
import com.mall.sls.data.entity.VipAmountInfo;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.entity.WxPay;
import com.mall.sls.data.request.AddAddressRequest;
import com.mall.sls.data.request.BankPayRequest;
import com.mall.sls.data.request.BuyNowRequest;
import com.mall.sls.data.request.CartAddRequest;
import com.mall.sls.data.request.CartFastaddRequest;
import com.mall.sls.data.request.CartGeneralCheckedRequest;
import com.mall.sls.data.request.CartOrderSubmitRequest;
import com.mall.sls.data.request.CartUpdateNumberRequest;
import com.mall.sls.data.request.CertifyIdRequest;
import com.mall.sls.data.request.ChinaGPayRequest;
import com.mall.sls.data.request.ChinaGPrepayRequest;
import com.mall.sls.data.request.ChinaGSendCodeRequest;
import com.mall.sls.data.request.CodeRequest;
import com.mall.sls.data.request.ConfirmBindBankRequest;
import com.mall.sls.data.request.DescriptionRequest;
import com.mall.sls.data.request.GroupRemindRequest;
import com.mall.sls.data.request.JoinPrizeRequest;
import com.mall.sls.data.request.LoginRequest;
import com.mall.sls.data.request.MerchantCertifyRequest;
import com.mall.sls.data.request.MobileRequest;
import com.mall.sls.data.request.MsgIdRequest;
import com.mall.sls.data.request.MsgReadRequest;
import com.mall.sls.data.request.OnClickBindRequest;
import com.mall.sls.data.request.OneClickLoginRequest;
import com.mall.sls.data.request.OrderAddCartRequest;
import com.mall.sls.data.request.OrderIdRequest;
import com.mall.sls.data.request.OrderPayRequest;
import com.mall.sls.data.request.OrderRequest;
import com.mall.sls.data.request.OrderSubmitRequest;
import com.mall.sls.data.request.PayRequest;
import com.mall.sls.data.request.SmsCodeBindRequest;
import com.mall.sls.data.request.StartBindBankRequest;
import com.mall.sls.data.request.TypeRequest;
import com.mall.sls.data.request.UserPayDtoRequest;
import com.mall.sls.data.request.WeiXinLoginRequest;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/27.
 */

public interface RestApiService {

    //获取下载地址
    @GET("app/version/android")
    Flowable<RemoteDataWrapper<AppUrlInfo>> getAppUrlInfo(@Header("X-Hc-Sign") String sign, @Query("version") String version);

    //登录
    @POST("app/auth/login/code")
    Flowable<RemoteDataWrapper<TokenInfo>> loginIn(@Header("X-Hc-Sign") String sign, @Body LoginRequest loginRequest);

    //一键登录
    @POST("app/auth/login/ali")
    Flowable<RemoteDataWrapper<TokenInfo>> oneClickLogin(@Header("X-Hc-Sign") String sign, @Body OneClickLoginRequest request);

    //发送验证码
    @POST("app/auth/regCaptcha")
    Flowable<RemoteDataWrapper<Ignore>> sendCodeV(@Header("X-Hc-Sign") String sign, @Body MobileRequest request);

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
    Flowable<RemoteDataWrapper<ConfirmOrderDetail>> cartCheckout(@Header("X-Hc-Sign") String sign, @Query("addressId") String addressId, @Query("cartId") String cartId, @Query("couponId") String couponId, @Query("userCouponId") String userCouponId,@Query("shipChannel") String shipChannel);

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
    Flowable<RemoteDataWrapper<LotteryRecord>> getLotteryRecord(@Header("X-Hc-Sign") String sign, @Query("page") String page, @Query("limit") String limit);

    //领取优惠卷
    @POST("app/coupon/receive")
    Flowable<RemoteDataWrapper<List<CouponInfo>>> couponReceive(@Header("X-Hc-Sign") String sign, @Body TypeRequest request);

    //查询购物车商品数量
    @GET("app/cart/general/count")
    Flowable<RemoteDataWrapper<String>> getCartCount(@Header("X-Hc-Sign") String sign);

    //加入购物车
    @POST("app/cart/general/add")
    Flowable<RemoteDataWrapper<CartAddInfo>> cartAdd(@Header("X-Hc-Sign") String sign, @Body CartAddRequest request);

    //购物车选中商品购买
    @POST("app/cart/general/checked")
    Flowable<RemoteDataWrapper<ConfirmCartOrderDetail>> cartGeneralChecked(@Header("X-Hc-Sign") String sign, @Body CartGeneralCheckedRequest request);

    //删除购物车指定商品
    @DELETE("app/cart/general/delete/{id}")
    Flowable<RemoteDataWrapper<Boolean>> deleteCartItem(@Header("X-Hc-Sign") String sign, @Path("id") String id);

    //查询购物车列表
    @GET("app/cart/general/list")
    Flowable<RemoteDataWrapper<CartInfo>> getCartInfo(@Header("X-Hc-Sign") String sign);

    //购物车内修改商品数量
    @POST("app/cart/general/update")
    Flowable<RemoteDataWrapper<CartAddInfo>> cartUpdateNumer(@Header("X-Hc-Sign") String sign, @Body CartUpdateNumberRequest request);

    //立即购买
    @POST("app/cart/general/fastadd")
    Flowable<RemoteDataWrapper<ConfirmCartOrderDetail>> buyNow(@Header("X-Hc-Sign") String sign, @Body BuyNowRequest request);

    //普通商品详情
    @GET("app/goods/baseGood")
    Flowable<RemoteDataWrapper<GeneralGoodsDetailsInfo>> getGeneralGoodsDetailsInfo(@Header("X-Hc-Sign") String sign, @Query("goodsId") String goodsId);

    //普通商品提交订单
    @POST("app/order/general/submit")
    Flowable<RemoteDataWrapper<OrderSubmitInfo>> cartOrderSubmit(@Header("X-Hc-Sign") String sign, @Body CartOrderSubmitRequest request);

    //查询物流接口
    @GET("app/order/logistics/{id}")
    Flowable<RemoteDataWrapper<List<OrderPackageInfo>>> getOrderLogistics(@Header("X-Hc-Sign") String sign, @Path("id") String id);

    //订单详情加入购物车
    @POST("app/order/general/addCartBatch")
    Flowable<RemoteDataWrapper<Boolean>> addCartBatch(@Header("X-Hc-Sign") String sign, @Body OrderIdRequest request);

    //正在抢购
    @GET("app/goods/rushBuy")
    Flowable<RemoteDataWrapper<LocalTeam>> getRushBuy(@Header("X-Hc-Sign") String sign, @Query("page") String page, @Query("limit") String limit);

    //即将开抢
    @GET("app/goods/waitBuy")
    Flowable<RemoteDataWrapper<LocalTeam>> getWaitBuy(@Header("X-Hc-Sign") String sign, @Query("page") String page, @Query("limit") String limit);

    //拼团抢购列表
    @GET("app/home/snap-up")
    Flowable<RemoteDataWrapper<HomeSnapUp>> getHomeSnapUp(@Header("X-Hc-Sign") String sign);

    //商品L1类目列表
    @GET("app/goods/categories-l1")
    Flowable<RemoteDataWrapper<FirstCategory>> getFirstCategory(@Header("X-Hc-Sign") String sign);

    //根据L1类目过滤配置数量的商品
    @GET("app/goods/l1/{categoryId}/goods")
    Flowable<RemoteDataWrapper<SecondCategory>> getSecondCategory(@Header("X-Hc-Sign") String sign, @Path("categoryId") String categoryId);

    //根据L2类目过滤全部商品
    @GET("app/goods/l2/{categoryId}/goods")
    Flowable<RemoteDataWrapper<GoodsItem>> getCategoryGoods(@Header("X-Hc-Sign") String sign, @Path("categoryId") String categoryId,@Query("sortType") String sortType, @Query("orderType") String orderType ,@Query("page") String page, @Query("limit") String limit);

    //关键字搜索
    @GET("app/goods/search")
    Flowable<RemoteDataWrapper<GoodsItem>> getKeywordGoods(@Header("X-Hc-Sign") String sign, @Query("keyword") String keyword,@Query("sortType") String sortType, @Query("orderType") String orderType ,@Query("page") String page, @Query("limit") String limit);

    //商品搜索历史记录
    @GET("app/goods/search-history")
    Flowable<RemoteDataWrapper<List<String>>> getSearchHistory(@Header("X-Hc-Sign") String sign);

    //删除历史记录
    @DELETE("app/goods/search-history")
    Flowable<RemoteDataWrapper<Ignore>> deleteHistory(@Header("X-Hc-Sign") String sign);

    //查询绑定的银行卡记录
    @GET("app/baofoo/list")
    Flowable<RemoteDataWrapper<List<BankCardInfo>>> getBankCardInfos(@Header("X-Hc-Sign") String sign);

    //查询银行卡的属性信息
    @GET("app/baofoo/card-info")
    Flowable<RemoteDataWrapper<CardInfo>> getCardInfo(@Header("X-Hc-Sign") String sign, @Query("cardNo") String cardNo);

    //开始绑定银行卡
    @POST("app/baofoo/begin-sign")
    Flowable<RemoteDataWrapper<BankCardInfo>> getStartBindBankInfo(@Header("X-Hc-Sign") String sign, @Body StartBindBankRequest request);

    //确认绑定银行卡
    @POST("app/baofoo/confirm-sign")
    Flowable<RemoteDataWrapper<BindResultInfo>> confirmBindBank(@Header("X-Hc-Sign") String sign, @Body ConfirmBindBankRequest request);

    //解绑银行卡
    @POST("app/baofoo/{id}/cancel-sign")
    Flowable<RemoteDataWrapper<Boolean>> untieBankCard(@Header("X-Hc-Sign") String sign, @Path("id") String id);

    //宝付直接支付
    @POST("app/baofoo/single-pay")
    Flowable<RemoteDataWrapper<BankPayInfo>> baoFooSinglePay(@Header("X-Hc-Sign") String sign, @Body BankPayRequest request);

    //获取宝付支付验证码
    @POST("public/f/code/baofoo")
    Flowable<RemoteDataWrapper<Ignore>> sendBaoFuCode(@Header("X-Hc-Sign") String sign, @Body MobileRequest request);

    //获取用户认证的信息
    @GET("app/auth/certify-info")
    Flowable<RemoteDataWrapper<CertifyInfo>> getCertifyInfo(@Header("X-Hc-Sign") String sign);

    //普通商品订单再来一单
    @POST("app/order/general/add-cart")
    Flowable<RemoteDataWrapper<OrderAddCartInfo>> orderAddCart(@Header("X-Hc-Sign") String sign, @Body OrderAddCartRequest request);

    //是否打开邀请码
    @GET("app/common/invitation-open")
    Flowable<RemoteDataWrapper<Boolean>> getInvitationOpen(@Header("X-Hc-Sign") String sign);

    //获取支付方式的接口
    @GET("app/common/pay-method")
    Flowable<RemoteDataWrapper<List<PayMethodInfo>>> getPayMethod(@Header("X-Hc-Sign") String sign, @Query("devicePlatform") String devicePlatform, @Query("orderType") String orderType);

    //微信支付
    @POST("app/pay/begin-pay")
    Flowable<RemoteDataWrapper<WxPay>> getWxPay(@Header("X-Hc-Sign") String sign, @Body PayRequest request);
    //支付宝支付
    @POST("app/pay/begin-pay")
    Flowable<RemoteDataWrapper<AliPay>> getAliPay(@Header("X-Hc-Sign") String sign, @Body PayRequest request);
    //宝付支付
    @POST("app/pay/begin-pay")
    Flowable<RemoteDataWrapper<BaoFuPay>> getBaoFuPay(@Header("X-Hc-Sign") String sign, @Body PayRequest request);
    //爱农支付
    @POST("app/pay/begin-pay")
    Flowable<RemoteDataWrapper<AiNongPay>> getAiNongPay(@Header("X-Hc-Sign") String sign, @Body PayRequest request);

    //刷新token
    @GET("app/auth/token/refresh")
    Flowable<RemoteDataWrapper<TokenRefreshInfo>> getTokenRefreshInfo(@Header("X-Hc-Sign") String sign);

    //查询退款记录
    @GET("app/order/refund-log/{orderId}")
    Flowable<RemoteDataWrapper<List<RefundInfo>>> getRefundInfo(@Header("X-Hc-Sign") String sign, @Path("orderId") String orderId);

    //爱农预支付
    @POST("app/ainong/prepay")
    Flowable<RemoteDataWrapper<String>> chinaGPrepay(@Header("X-Hc-Sign") String sign, @Body ChinaGPrepayRequest request);

    //爱农发送验证码
    @POST("app/ainong/sms")
    Flowable<RemoteDataWrapper<Boolean>> chinaGSendCode(@Header("X-Hc-Sign") String sign, @Body ChinaGSendCodeRequest request);

    //爱农支付
    @POST("app/ainong/confirm-pay")
    Flowable<RemoteDataWrapper<BankPayInfo>> chinaGPay(@Header("X-Hc-Sign") String sign, @Body ChinaGPayRequest request);

    //获取快递费用描述
    @GET("app/common/ship-info")
    Flowable<RemoteDataWrapper<String>> getShipInfo(@Header("X-Hc-Sign") String sign);

    //获取配送方式
    @GET("app/common/express")
    Flowable<RemoteDataWrapper<List<String>>> getDeliveryMethod(@Header("X-Hc-Sign") String sign);

    //获取商户认证信息
    @GET("app/certify/merchant")
    Flowable<RemoteDataWrapper<MerchantCertifyInfo>> getMerchantCertifyInfo(@Header("X-Hc-Sign") String sign);

    //提交商户认证
    @POST("app/certify/merchant")
    Flowable<RemoteDataWrapper<Boolean>> merchantCertify(@Header("X-Hc-Sign") String sign, @Body MerchantCertifyRequest request);

    //上传图片
    @Multipart
    @POST("app/storage/upload")
    Flowable<RemoteDataWrapper<UploadUrlInfo>> uploadFile(@Header("X-Hc-Sign") String sign, @PartMap Map<String, RequestBody> requestBodyMap);

    //查询总积分和可兑换积分
    @GET("app/integral/points")
    Flowable<RemoteDataWrapper<IntegralPointsInfo>> getIntegralPointsInfo(@Header("X-Hc-Sign") String sign);



}
