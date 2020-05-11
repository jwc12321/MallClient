package com.mall.sls.common.widget.textview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.mall.sls.MainApplication;


/**
 * @author jwc on 2020/5/11.
 * 描述：
 */
@SuppressLint("AppCompatCustomView")
public class BlackDrawTextView extends TextView {

    public BlackDrawTextView(Context context) {
        super(context);
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/conventional.ttf");
        setTypeface(typeFace1);
        //初始化Paint
        initPaint();
    }

    public BlackDrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/conventional.ttf");
        setTypeface(typeFace1);
        initPaint();
    }

    public BlackDrawTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/conventional.ttf");
        setTypeface(typeFace1);
        initPaint();
    }
    private void initPaint() {
        //删除线的颜色和样式
        paint = new Paint();
        paint.setColor(Color.parseColor("#333333"));
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(4);
    }
    private Paint paint;
    @Override
    protected void onDraw(Canvas canvas) {
        //TextView布局的高度和宽度
        float x = this.getWidth();
        float y = this.getHeight();
        //根据Textview的高度和宽度设置删除线的位置
        //四个参数的意思：起始x的位置，起始y的位置，终点x的位置，终点y的位置
        canvas.drawLine(0f, y/2f, x, y/2f, paint);
        //super最后调用表示删除线在位于文字的上边
        //super方法先调用删除线不显示
        super.onDraw(canvas);
    }
}
