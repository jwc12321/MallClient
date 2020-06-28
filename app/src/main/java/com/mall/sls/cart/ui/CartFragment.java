package com.mall.sls.cart.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseFragment;
import com.mall.sls.R;
import com.mall.sls.cart.CartContract;
import com.mall.sls.cart.CartModule;
import com.mall.sls.cart.DaggerCartComponent;
import com.mall.sls.cart.adapter.CartItemAdapter;
import com.mall.sls.cart.adapter.Literature;
import com.mall.sls.cart.presenter.CartPresenter;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.CartInfo;
import com.mall.sls.data.entity.CartItemInfo;
import com.mall.sls.data.entity.EmptyItem;
import com.mall.sls.data.entity.HiddenItemCartInfo;

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
        cartItemAdapter = new CartItemAdapter(getActivity());
        cartItemAdapter.setOnItemClickListener(this);
        cartRv.setAdapter(cartItemAdapter);
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
    public void renderCartInfo(CartInfo cartInfo) {
        mLiteratureList.clear();
        if (cartInfo != null) {
            cartItemInfos = cartInfo.getNormalList();
            hiddenItemCartInfos = cartInfo.getCancelList();
            emptyItems = new ArrayList<>();
            emptyItems.add(new EmptyItem());
            mLiteratureList.addAll(cartItemInfos);
            if(hiddenItemCartInfos!=null&&hiddenItemCartInfos.size()>0) {
                mLiteratureList.addAll(emptyItems);
                mLiteratureList.addAll(hiddenItemCartInfos);
            }
            cartItemAdapter.setLiteratureList(mLiteratureList);
            calculatingPrice();
        }
    }

    @Override
    public void renderDeleteCartItem(Boolean isBoolean) {
        if(TextUtils.equals(StaticData.REFLASH_ONE,type)){
            cartItemInfos.remove(deletePosition);
            calculatingPrice();
        }else if(TextUtils.equals(StaticData.REFLASH_TWO,type)){
            if(deletePosition-1-cartItemInfos.size()>-1&&deletePosition-1-cartItemInfos.size()<hiddenItemCartInfos.size()) {
                hiddenItemCartInfos.remove(deletePosition - 1 - cartItemInfos.size());
            }
        }else {
            mLiteratureList.clear();
            mLiteratureList.addAll(cartItemInfos);
            cartItemAdapter.setLiteratureList(mLiteratureList);
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
    public void deleteItem(String id,int position,String type) {
        this.deletePosition=position;
        this.type=type;
        cartPresenter.deleteCartItem(id);
    }

    @Override
    public void deleteEmpty(String type) {
        this.type=type;
        String id="";
        if(hiddenItemCartInfos!=null){
            for (int i=0;i<hiddenItemCartInfos.size();i++){
                if(i==hiddenItemCartInfos.size()-1){
                    id=id+hiddenItemCartInfos.get(i).getId();
                }else {
                    id=id+hiddenItemCartInfos.get(i).getId()+",";
                }
            }
        }
        cartPresenter.deleteCartItem(id);
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
        if(cartItemInfos==null||cartItemInfos.size()==0){
            confirmBt.setEnabled(false);
            confirmBt.setText(getString(R.string.settlement));
            selectAll.setChecked(true);
            selectAll.setEnabled(false);
            totalPrice.setText("0.00");
        }else {
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



    @OnClick({R.id.select_all, R.id.confirm_bt})
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

                break;
            default:
        }
    }

    private void returnFirst() {
        confirmBt.setText(getString(R.string.settlement));
        totalPrice.setText("0.00");
        selectAll.setChecked(false);
    }

}
