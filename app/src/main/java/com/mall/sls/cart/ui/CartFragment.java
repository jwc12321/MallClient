package com.mall.sls.cart.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.BoolRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mall.sls.BaseFragment;
import com.mall.sls.R;
import com.mall.sls.cart.CartContract;
import com.mall.sls.cart.CartModule;
import com.mall.sls.cart.DaggerCartComponent;
import com.mall.sls.cart.adapter.CartItemAdapter;
import com.mall.sls.cart.adapter.Literature;
import com.mall.sls.cart.presenter.CartPresenter;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.FlashCartManager;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.edittextview.SoftKeyBoardListener;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.RemoteDataException;
import com.mall.sls.data.entity.CartInfo;
import com.mall.sls.data.entity.CartItemInfo;
import com.mall.sls.data.entity.EmptyItem;
import com.mall.sls.data.entity.HiddenItemCartInfo;
import com.mall.sls.homepage.ui.CartConfirmOrderActivity;
import com.mall.sls.login.ui.WeixinLoginActivity;
import com.mall.sls.mainframe.ui.MainFrameActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/6/24.
 * 描述：购物车
 */
public class CartFragment extends BaseFragment implements CartContract.CartView, CartItemAdapter.OnItemClickListener {
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.cart_rv)
    RecyclerView cartRv;
    @BindView(R.id.select_all)
    CheckBox selectAll;
    @BindView(R.id.confirm_bt)
    ConventionalTextView confirmBt;
    @BindView(R.id.total_price)
    ConventionalTextView totalPrice;

    @Inject
    CartPresenter cartPresenter;
    @BindView(R.id.statistics_rl)
    RelativeLayout statisticsRl;
    @BindView(R.id.no_record_bt)
    MediumThickTextView noRecordBt;
    @BindView(R.id.no_record_ll)
    LinearLayout noRecordLl;

    private CartItemAdapter cartItemAdapter;
    private List<Literature> mLiteratureList;
    private List<CartItemInfo> cartItemInfos;
    private List<EmptyItem> emptyItems;
    private List<HiddenItemCartInfo> hiddenItemCartInfos;

    private View showCountView;
    private CartItemInfo cartItemInfo;
    private int currentCount;

    private BigDecimal totalPriceBd;//总价
    private BigDecimal countBd;//数量
    private BigDecimal unitPriceBd;//单价
    private int totalCount = 0;
    private int deletePosition;
    private String type;
    private List<String> ids;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHeight(null, title, null);
        initView();
    }

    private void initView() {
        mLiteratureList = new ArrayList<>();
        ids=new ArrayList<>();
        cartItemAdapter = new CartItemAdapter(getActivity());
        cartItemAdapter.setOnItemClickListener(this);
        cartRv.setAdapter(cartItemAdapter);

        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

            }

            @Override
            public void keyBoardHide(int height) {
                if (showCountView != null) {
                    ((EditText) showCountView).clearFocus();
                }
            }
        });
    }

    @Override
    protected void initializeInjector() {
        DaggerCartComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cartModule(new CartModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && cartPresenter != null && TextUtils.equals(StaticData.REFLASH_ONE, FlashCartManager.getFlashCart())) {
            cartPresenter.getCartInfo(StaticData.REFLASH_ONE);
            FlashCartManager.saveFlashCart(StaticData.REFLASH_ZERO);
        }
    }

    @Override
    public void renderCartInfo(CartInfo cartInfo) {
        mLiteratureList.clear();
        if (cartInfo != null) {
            cartItemInfos = cartInfo.getNormalList();
            hiddenItemCartInfos = cartInfo.getCancelList();
            if(cartItemInfos.size()==0&&hiddenItemCartInfos.size()==0){
                noRecordLl.setVisibility(View.VISIBLE);
                cartRv.setVisibility(View.GONE);
                statisticsRl.setVisibility(View.GONE);
            }else {
                noRecordLl.setVisibility(View.GONE);
                cartRv.setVisibility(View.VISIBLE);
                statisticsRl.setVisibility(View.VISIBLE);
                emptyItems = new ArrayList<>();
                emptyItems.add(new EmptyItem());
                mLiteratureList.addAll(cartItemInfos);
                if (hiddenItemCartInfos != null && hiddenItemCartInfos.size() > 0) {
                    mLiteratureList.addAll(emptyItems);
                    mLiteratureList.addAll(hiddenItemCartInfos);
                }
                cartItemAdapter.setLiteratureList(mLiteratureList);
                calculatingPrice();
            }
        }
    }

    @Override
    public void renderDeleteCartItem(Boolean isBoolean) {
        if (TextUtils.equals(StaticData.REFLASH_ONE, type)) {
            cartItemInfos.remove(deletePosition);
            calculatingPrice();
        } else if (TextUtils.equals(StaticData.REFLASH_TWO, type)) {
            if (deletePosition - 1 - cartItemInfos.size() > -1 && deletePosition - 1 - cartItemInfos.size() < hiddenItemCartInfos.size()) {
                hiddenItemCartInfos.remove(deletePosition - 1 - cartItemInfos.size());
            }
        } else {
            mLiteratureList.clear();
            mLiteratureList.addAll(cartItemInfos);
            cartItemAdapter.setLiteratureList(mLiteratureList);
        }
        if(cartItemInfos.size()==0&&hiddenItemCartInfos.size()==0){
            noRecordLl.setVisibility(View.VISIBLE);
            cartRv.setVisibility(View.GONE);
            statisticsRl.setVisibility(View.GONE);
        }else {
            noRecordLl.setVisibility(View.GONE);
            cartRv.setVisibility(View.VISIBLE);
            statisticsRl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void renderCartUpdateNumber() {
        cartItemInfo.setNumber(String.valueOf(currentCount));
        ((EditText) showCountView).setText(String.valueOf(currentCount));
        cartItemAdapter.notifyDataSetChanged();
        calculatingPrice();
    }

    @Override
    public void setPresenter(CartContract.CartPresenter presenter) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && cartPresenter != null) {
            returnFirst();
            cartPresenter.getCartInfo(StaticData.REFLASH_ONE);
        }
    }

    @Override
    public void checkItem(boolean isChecked, int position) {
        if (position >= 0 && position < cartItemInfos.size()) {
            cartItemInfo = cartItemInfos.get(position);
            cartItemInfo.setIscheck(isChecked);
            calculatingPrice();
        }
    }

    @Override
    public void doIncrease(int position, View showCountView, String id) {
        this.showCountView = showCountView;
        cartItemInfo = cartItemInfos.get(position);
        currentCount = Integer.parseInt(cartItemInfo.getNumber());
        currentCount++;
        cartPresenter.cartUpdateNumber(id, String.valueOf(currentCount));
    }

    @Override
    public void doReduce(int position, View showCountView, String id) {
        this.showCountView = showCountView;
        cartItemInfo = cartItemInfos.get(position);
        currentCount = Integer.parseInt(cartItemInfo.getNumber());
        if (currentCount == 1) {
            showMessage(getString(R.string.goods_can_not_be_reduced));
            return;
        }
        currentCount--;
        cartPresenter.cartUpdateNumber(id, String.valueOf(currentCount));
    }

    @Override
    public void deleteItem(String id, int position, String type) {
        this.deletePosition = position;
        this.type = type;
        cartPresenter.deleteCartItem(id);
    }

    @Override
    public void deleteEmpty(String type) {
        this.type = type;
        String id = "";
        if (hiddenItemCartInfos != null) {
            for (int i = 0; i < hiddenItemCartInfos.size(); i++) {
                if (i == hiddenItemCartInfos.size() - 1) {
                    id = id + hiddenItemCartInfos.get(i).getId();
                } else {
                    id = id + hiddenItemCartInfos.get(i).getId() + ",";
                }
            }
        }
        cartPresenter.deleteCartItem(id);
    }

    @Override
    public void clickEditText(int position, View showCountView) {
        this.showCountView = showCountView;
        cartItemInfo = cartItemInfos.get(position);
        if (!TextUtils.isEmpty(cartItemInfo.getNumber())) {
            currentCount = Integer.parseInt(cartItemInfo.getNumber());
            if (TextUtils.equals("0", cartItemInfo.getNumber()) || TextUtils.equals("00", cartItemInfo.getNumber())) {
                showMessage(getString(R.string.goods_can_not_be_reduced));
                ((EditText) showCountView).setText(String.valueOf(cartItemInfo.getNumber()));
            } else {
                currentCount = Integer.parseInt(cartItemInfo.getInputNumber());
                cartPresenter.cartUpdateNumber(cartItemInfo.getId(), cartItemInfo.getInputNumber());
            }
        } else {
            showMessage(getString(R.string.goods_can_not_be_reduced));
            cartItemInfo.setNumber(String.valueOf(cartItemInfo.getNumber()));
            ((EditText) showCountView).setText(String.valueOf(cartItemInfo.getNumber()));
        }
    }

    @Override
    public void returnEditText(View showCountView) {
        this.showCountView = showCountView;
    }

    private void calculatingPrice() {
        totalCount = 0;
        totalPriceBd = new BigDecimal(0);
        for (int i = 0; i < cartItemInfos.size(); i++) {
            CartItemInfo cartItemInfo = cartItemInfos.get(i);
            if (cartItemInfo.isIscheck()) {
                totalCount++;
                unitPriceBd = new BigDecimal(cartItemInfo.getPrice());
                countBd = new BigDecimal(TextUtils.isEmpty(cartItemInfo.getNumber()) ? "0" : cartItemInfo.getNumber());
                totalPriceBd = unitPriceBd.multiply(countBd).add(totalPriceBd);
            }
        }
        if (cartItemInfos == null || cartItemInfos.size() == 0) {
            confirmBt.setEnabled(false);
            confirmBt.setText(getString(R.string.settlement));
            selectAll.setChecked(true);
            selectAll.setEnabled(false);
            totalPrice.setText("0.00");
        } else {
            selectAll.setEnabled(true);
            confirmBt.setText(getString(R.string.settlement) + "(" + totalCount + ")");
            if (totalCount == cartItemInfos.size()) {
                selectAll.setChecked(true);
            } else {
                selectAll.setChecked(false);
            }
            totalPrice.setText(NumberFormatUnit.twoDecimalFormat(totalPriceBd.toString()));
        }
    }


    @OnClick({R.id.select_all, R.id.confirm_bt,R.id.no_record_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_all:
                if (cartItemInfos.size() > 0) {
                    if (selectAll.isChecked()) {
                        for (int i = 0; i < cartItemInfos.size(); i++) {
                            cartItemInfos.get(i).setIscheck(true);
                        }
                    } else {
                        for (int i = 0; i < cartItemInfos.size(); i++) {
                            cartItemInfos.get(i).setIscheck(false);
                        }
                    }
                    cartItemAdapter.notifyDataSetChanged();
                    calculatingPrice();
                }
                break;
            case R.id.confirm_bt:
                confirm();
                break;
            case R.id.no_record_bt:
                if(cartListener!=null){
                    cartListener.goHomePage();
                }
                break;
            default:
        }
    }

    private void confirm(){
        ids.clear();
        for (int i=0;i<cartItemInfos.size();i++){
            if(cartItemInfos.get(i).isIscheck()){
                ids.add(cartItemInfos.get(i).getId());
            }
        }
        if(ids.size()==0){
            showMessage(getString(R.string.please_select_goods));
            return;
        }
        CartConfirmOrderActivity.start(getActivity(),ids,StaticData.REFLASH_TWO);
    }

    private void returnFirst() {
        confirmBt.setText(getString(R.string.settlement));
        totalPrice.setText("0.00");
        selectAll.setChecked(false);
    }


    @Override
    public void showError(Throwable e) {
        if (e != null) {
            if (e instanceof RemoteDataException) {
                if (TextUtils.equals(RemoteDataException.TOKEN_OVER_ONE, ((RemoteDataException) e).getRetCode()) || TextUtils.equals(RemoteDataException.TOKEN_OVER_TWO, ((RemoteDataException) e).getRetCode())) {
                    WeixinLoginActivity.start(getActivity());
                    MainFrameActivity.finishActivity();
                } else if (TextUtils.equals(RemoteDataException.CODE_SEVEN_ZERO_ONE, ((RemoteDataException) e).getRetCode())) {
                    ((EditText) showCountView).setText(String.valueOf(cartItemInfo.getNumber()));
                    showMessage(((RemoteDataException) e).getMessage(getActivity()));
                } else {
                    showMessage(((RemoteDataException) e).getMessage(getActivity()));
                }
            } else {
                if (e instanceof HttpException) {
                    HttpException exception = (HttpException) e;
                    if (TextUtils.equals("401", exception.response().code() + "")) {
                        WeixinLoginActivity.start(getActivity());
                        MainFrameActivity.finishActivity();
                    } else {
                        showMessage(getString(R.string.fail_to_access_servers));
                    }
                } else {
                    showMessage(getString(R.string.fail_to_access_servers));
                }
            }
        }

    }

    public interface CartListener {
        void goHomePage();
    }

    private CartListener cartListener;

    public void setCartListener(CartListener cartListener) {
        this.cartListener = cartListener;
    }

}
