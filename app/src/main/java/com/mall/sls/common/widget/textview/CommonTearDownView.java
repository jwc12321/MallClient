package com.mall.sls.common.widget.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.mall.sls.R;
import com.mall.sls.common.StaticData;

import java.lang.ref.WeakReference;

/**
 * Created by JWC on 2018/6/5.
 */

public class CommonTearDownView extends LinearLayout {

    public long endTime = 0L;
    public long sysTime = 0L;
    private boolean mIsAttachedToWindow = false;
    public long cutdownTime = 0L;
    private int textColor;
    private float textSize;
    private String colonType;
    private int textBackground;
    private String hourHide;

    private static final int MESSAGE_WHAT = 0;

    private TearDownHandler mHandler = new TearDownHandler(this);

    private ConventionalTextView dayTextView, hourTextView, minTextView, secondTextView;
    private ConventionalTextView dayColon, hourColon, minColon, secondColon;

    public CommonTearDownView(Context context) {
        this(context, null);

    }

    public CommonTearDownView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);

    }

    public CommonTearDownView(Context context,
                              AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        init(context, attributeSet, defStyle);
    }

    //type=0：还没开始 type=1：已经开始，但是还没结束
    public void startTearDown(long endTime, long sysTime) {
        this.endTime = endTime;
        this.sysTime = sysTime;
        this.cutdownTime = endTime - sysTime;
        if (mIsAttachedToWindow) {
            mHandler.removeMessages(MESSAGE_WHAT);
            mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, 0);
        }
    }

    private void init(Context context, AttributeSet attributeSet, int defStyle) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.common_tear_down_view, this);
        dayTextView = (ConventionalTextView) findViewById(R.id.day_time);
        hourTextView = (ConventionalTextView) findViewById(R.id.hour_time);
        minTextView = (ConventionalTextView) findViewById(R.id.min_time);
        secondTextView = (ConventionalTextView) findViewById(R.id.second_time);
        dayColon = (ConventionalTextView) findViewById(R.id.day_colon);
        hourColon = (ConventionalTextView) findViewById(R.id.hour_colon);
        minColon = (ConventionalTextView) findViewById(R.id.min_colon);
        secondColon = (ConventionalTextView) findViewById(R.id.second_colon);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CommonTearDownView, defStyle, 0);
        textColor = typedArray.getColor(R.styleable.CommonTearDownView_viewTextColor, 0);
        textSize = typedArray.getDimension(R.styleable.CommonTearDownView_viewTextSize, 12);
        colonType = typedArray.getString(R.styleable.CommonTearDownView_colonType);
        textBackground=typedArray.getResourceId(R.styleable.CommonTearDownView_textBackground,0);
        hourHide = typedArray.getString(R.styleable.CommonTearDownView_hourHide);
        initColor(textColor);
        initSize(textSize);
        initColonType(colonType);
        initBackground(textBackground);
        if(TextUtils.isEmpty(hourHide)){
            hourHide=StaticData.REFRESH_ZERO;
        }
    }

    private void initColor(int color) {
        if (color != 0) {
            dayTextView.setTextColor(color);
            hourTextView.setTextColor(color);
            minTextView.setTextColor(color);
            secondTextView.setTextColor(color);
            dayColon.setTextColor(color);
            hourColon.setTextColor(color);
            minColon.setTextColor(color);
            secondColon.setTextColor(color);
        }
    }

    private void initSize(float size) {
        dayTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);//dip
        hourTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);//dip
        minTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);//dip
        secondTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);//dip
        dayColon.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);//dip
        hourColon.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);//dip
        minColon.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);//dip
        secondColon.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);//dip
    }

    private void initColonType(String type) {
        if (TextUtils.equals(StaticData.REFRESH_ONE, type)) {
            dayColon.setText(":");
            hourColon.setText(":");
            minColon.setText(":");
            secondColon.setText("");
            secondColon.setVisibility(GONE);
        }else {
            dayColon.setText("天");
            hourColon.setText("时");
            minColon.setText("分");
            secondColon.setText("秒");
            secondColon.setVisibility(VISIBLE);
        }
    }

    private void initBackground(int background) {
        if (background != 0) {
            dayTextView.setBackgroundResource(background);
            hourTextView.setBackgroundResource(background);
            minTextView.setBackgroundResource(background);
            secondTextView.setBackgroundResource(background);
        }
    }


    @Override
    protected void onAttachedToWindow() {
        // TODO Auto-generated method stub
        mHandler.removeMessages(MESSAGE_WHAT);
        mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, 1000);
        mIsAttachedToWindow = true;
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        mHandler.removeMessages(MESSAGE_WHAT);
        mIsAttachedToWindow = false;
        super.onDetachedFromWindow();
    }

    public void tearDown() {
        if (mIsAttachedToWindow) {
            long remainTime = cutdownTime;
            if (remainTime > -1) {
                long hour = 0;
                long min = 0;
                long sec = 0;
                long day = 0;
                day = remainTime / (24 * 60 * 60);
                hour = (remainTime / (60 * 60) - day * 24);
                min = (remainTime / 60 - day * 24 * 60 - hour * 60);
                sec = (remainTime - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
                if(day==0){
                    dayTextView.setVisibility(GONE);
                    dayColon.setVisibility(GONE);
                }else {
                    if (String.valueOf(day).length() == 1) {
                        dayTextView.setText("0" + day);
                    } else {
                        dayTextView.setText(String.valueOf(day));
                    }
                }
                if(TextUtils.equals(StaticData.REFRESH_ZERO,hourHide)&&hour==0){
                    hourTextView.setVisibility(GONE);
                    hourColon.setVisibility(GONE);
                }else {
                    if (String.valueOf(hour).length() == 1) {
                        hourTextView.setText("0" + hour);
                    } else {
                        hourTextView.setText(String.valueOf(hour));
                    }
                }
                if (String.valueOf(min).length() == 1) {
                    minTextView.setText("0" + min);
                } else {
                    minTextView.setText(String.valueOf(min));
                }
                if (String.valueOf(sec).length() == 1) {
                    secondTextView.setText("0" + sec);
                } else {
                    secondTextView.setText(String.valueOf(sec));
                }
                mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, 1000);
            } else {
                if (timeOutListener != null) {
                    timeOutListener.timeOut();
                }
            }
        }
    }


    public static class TearDownHandler extends Handler {
        private WeakReference<CommonTearDownView> mTextViewRef;

        public TearDownHandler(CommonTearDownView view) {
            mTextViewRef = new WeakReference<CommonTearDownView>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            CommonTearDownView textView = mTextViewRef.get();
            if (null != textView) {
                removeMessages(MESSAGE_WHAT);
                textView.cutDown();
                textView.tearDown();
            }
        }
    }

    private void cutDown() {
        cutdownTime = cutdownTime - 1;
    }

    public void cancel() {
        if (mHandler != null && mHandler.hasMessages(MESSAGE_WHAT)) {
            mHandler.removeMessages(MESSAGE_WHAT);
        }
    }

    public interface TimeOutListener {
        void timeOut();//从还没结束到结束
    }

    private TimeOutListener timeOutListener;

    public void setTimeOutListener(TimeOutListener timeOutListener) {
        this.timeOutListener = timeOutListener;
    }
}
