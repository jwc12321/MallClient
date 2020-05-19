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
import com.mall.sls.data.entity.InviteInfo;
import com.mall.sls.data.entity.LocalTeam;
import com.mall.sls.data.entity.MessageInfo;
import com.mall.sls.data.entity.MessageTypeInfo;
import com.mall.sls.data.entity.MineInfo;
import com.mall.sls.data.entity.MyCouponInfo;
import com.mall.sls.data.entity.OrderInfo;
import com.mall.sls.data.entity.OrderList;
import com.mall.sls.data.entity.OrderSubmitInfo;
import com.mall.sls.data.entity.ProvinceBean;
import com.mall.sls.data.entity.TokenInfo;
import com.mall.sls.data.request.AddAddressRequest;
import com.mall.sls.data.request.CartFastaddRequest;
import com.mall.sls.data.request.CertifyIdRequest;
import com.mall.sls.data.request.LoginRequest;
import com.mall.sls.data.request.MobileRequest;
import com.mall.sls.data.request.MsgIdRequest;
import com.mall.sls.data.request.MsgReadRequest;
import com.mall.sls.data.request.OneClickLoginRequest;
import com.mall.sls.data.request.OrderSubmitRequest;

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
    @POST("wx/auth/login/ali")
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
    Flowable<RemoteDataWrapper<String>> addAddress(@Header("X-Hc-Sign") String sign,@Body AddAddressRequest request);

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
    Flowable<RemoteDataWrapper<ConfirmOrderDetail>> cartFastAdd(@Header("X-Hc-Sign") String sign,@Body CartFastaddRequest request);

    //订单预提交
    @GET("app/cart/checkout")
    Flowable<RemoteDataWrapper<ConfirmOrderDetail>> cartCheckout(@Header("X-Hc-Sign") String sign,@Query("addressId") String addressId,@Query("cartId") String cartId,@Query("couponId") String couponId,@Query("userCouponId") String userCouponId);

    //查询当前预提交订单可用优惠券
    @GET("app/coupon/selectlist")
    Flowable<RemoteDataWrapper<List<CouponInfo>>> getCouponSelect(@Header("X-Hc-Sign") String sign, @Query("cartIds") String cartIds);

    //提交订单
    @POST("app/order/submit")
    Flowable<RemoteDataWrapper<OrderSubmitInfo>> orderSubmit(@Header("X-Hc-Sign") String sign,@Body OrderSubmitRequest request);

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
    Flowable<RemoteDataWrapper<String>> getCertifyId(@Header("X-Hc-Sign") String sign,@Body CertifyIdRequest request);

    //获取订单列表
    @GET("app/order/list")
    Flowable<RemoteDataWrapper<OrderList>> getOrderList(@Header("X-Hc-Sign") String sign, @Query("showType") String showType, @Query("page") String page, @Query("limit") String limit);

    //获取订单详情
    @GET("app/order/detail")
    Flowable<RemoteDataWrapper<OrderInfo>> getGoodsOrderDetails(@Header("X-Hc-Sign") String sign, @Query("orderId") String orderId);

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
    Flowable<RemoteDataWrapper<Ignore>> msgEmpty(@Header("X-Hc-Sign") String sign,@Body MsgIdRequest request);

    @POST("app/msg/changeStatus")
    Flowable<RemoteDataWrapper<Ignore>> msgChangeStatus(@Header("X-Hc-Sign") String sign,@Body MsgReadRequest request);
}
