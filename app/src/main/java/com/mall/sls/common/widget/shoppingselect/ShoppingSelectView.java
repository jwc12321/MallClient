package com.mall.sls.common.widget.shoppingselect;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import com.mall.sls.R;
import com.mall.sls.common.unit.TextViewttf;
import com.mall.sls.data.entity.GoodsSpec;
import java.util.List;


/*
 * 项目名:    shopping-selection
 * 描述:     TODO 商品规格选择View
 */
public class ShoppingSelectView extends LinearLayout {
    /**
     * 数据源
     */
    private List<GoodsSpec> list;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 规格标题栏的文本间距
     */
    private int titleMargin = 5;
    /**
     * 整个商品属性的左右间距
     */
    private int flowLayoutMargin = 0;
    /**
     * 属性按钮的高度
     */
    private int buttonHeight = 30;
    /**
     * 属性按钮之间的左边距
     */
    private int buttonLeftMargin = 20;
    /**
     * 属性按钮之间的上边距
     */
    private int buttonTopMargin = 10;
    /**
     * 文字与按钮的边距
     */
    private int textPadding = 10;
    private int topTextPadding = 6;

    private int textLeft=20;
    private int textColor;
    /**
     * 选择后的回调监听
     */
    private OnSelectedListener listener;
    private List<String> checkSkus;



    public ShoppingSelectView(Context context) {
        super(context);
        initView(context);
    }

    public ShoppingSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ShoppingSelectView(Context context,
                           AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        initView(context);
//        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ShoppingSelectView, defStyle, 0);
//        textColor = typedArray.getColor(R.styleable.ShoppingSelectView_viewTextColor, 0XFFFFFFFF);
    }


    private void initView(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        textColor=getResources().getColor(R.color.appText3);
        this.context = context;
    }

    public void getView() {
        if (list==null||list.size() < 0) {
            return;
        }
        for (int z = 0; z < list.size(); z++) {
            GoodsSpec attr = list.get(z);
            //设置规格分类的标题
            TextView textView = new TextView(context);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin = dip2px(context, titleMargin);
            int leftMargin=dip2px(context, textLeft);
            textView.setTextColor(getResources().getColor(R.color.appText3));
            textView.setTextSize(13);
            TextViewttf.setTextConventional(textView);
            textView.setText(attr.getName());
            params.setMargins(0, 0, 0, margin);
            textView.setLayoutParams(params);
            addView(textView);
            //设置一个大规格下的所有小规格
            FlowLayout layout = new FlowLayout(context);
            layout.setTitle(attr.getName(), String.valueOf(z));
//            layout.setPadding(dip2px(context, flowLayoutMargin), 0, dip2px(context, flowLayoutMargin), 0);
            //设置选择监听
            if (listener != null) {
                layout.setListener(listener);
            }
            for (int i = 0; i < attr.getSpecs().size(); i++) {
                //属性按钮
                RadioButton button = new RadioButton(context);
//                //默认选中第一个
//                if (i == 0) {
//                    button.setChecked(true);
//                }
                //设置按钮的参数
//                LayoutParams buttonParams = new LayoutParams(LayoutParams.WRAP_CONTENT, dip2px(context, buttonHeight));
                LayoutParams buttonParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                //设置文字的边距
                int padding = dip2px(context, textPadding);
                int topPadding = dip2px(context, topTextPadding);
                button.setPadding(padding, topPadding, padding, topPadding);
                //设置margin属性，需传入LayoutParams否则会丢失原有的布局参数
                MarginLayoutParams marginParams = new MarginLayoutParams(buttonParams);
//                marginParams.leftMargin = dip2px(context, buttonLeftMargin);
                marginParams.rightMargin = dip2px(context, buttonLeftMargin);
                marginParams.bottomMargin = dip2px(context, buttonTopMargin);

                button.setLayoutParams(marginParams);
//                button.setGravity(Gravity.CENTER);
                button.setBackgroundResource(R.drawable.tv_sel);
                button.setButtonDrawable(android.R.color.transparent);
                button.setTextSize(13);
                button.setText(attr.getSpecs().get(i).getValue());
                button.setTextColor(getResources().getColorStateList(R.color.spec_text_select));
                if(checkSkus!=null&&checkSkus.size()==list.size()) {
                    button.setChecked(TextUtils.equals(attr.getSpecs().get(i).getValue(),checkSkus.get(z)));
                }
                layout.addView(button);
            }
            addView(layout);
        }
    }


    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setCheckSkus(List<String> checkSkus) {
        this.checkSkus = checkSkus;
    }
    public void setData(List<GoodsSpec> data) {
        list = data;
        getView();
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        this.listener = listener;
    }
}
