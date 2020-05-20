package com.mall.sls.common.widget.textview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.mall.sls.R;

import java.lang.ref.WeakReference;

/**
 * Created by JWC on 2018/6/5.
 */

public class MSTearDownView extends LinearLayout {

    public long endTime = 0L;
    public long sysTime =0L;
    private boolean mIsAttachedToWindow = false;
    public long cutdownTime=0L;

    private static final int MESSAGE_WHAT = 0;

    private TearDownHandler mHandler = new TearDownHandler(this);

    private ConventionalTextView minutsTextView, secondTextView;

    public MSTearDownView(Context context) {
        super(context);
        init(context, null);

    }

    public MSTearDownView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);

    }

    public MSTearDownView(Context context,
                        AttributeSet attributeSet, int paramInt) {
        super(context, attributeSet, paramInt);
        init(context, attributeSet);
    }

    //type=0：还没开始 type=1：已经开始，但是还没结束
    public void startTearDown(long endTime,long sysTime) {
        this.endTime = endTime;
        this.sysTime=sysTime;
        this.cutdownTime=endTime-sysTime;
        if (mIsAttachedToWindow) {
            mHandler.removeMessages(MESSAGE_WHAT);
            mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, 0);
        }
    }

    private void init(Context context, AttributeSet attributeSet) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_ms_tear_down, this);
        minutsTextView = (ConventionalTextView) findViewById(R.id.min_time);
        secondTextView = (ConventionalTextView) findViewById(R.id.second_time);
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
            long remainTime= cutdownTime;
            if (remainTime >0) {
                long day = 0;
                long hour = 0;
                long min = 0;
                long sec = 0;
                day = remainTime / (24 * 60 * 60);
                hour = (remainTime / (60 * 60) - day * 24);
                min = (remainTime / 60 - day * 24 * 60 - hour * 60);
                sec = (remainTime - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
                if(String.valueOf(min).length()==1){
                    minutsTextView.setText("0"+min);
                }else {
                    minutsTextView.setText(String.valueOf(min));
                }
                if(String.valueOf(sec).length()==1){
                    secondTextView.setText("0"+sec);
                }else {
                    secondTextView.setText(String.valueOf(sec));
                }
                mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, 1000);
            } else {
                if(timeOutListener!=null) {
                    timeOutListener.timeOut();
                }
            }
        }
    }


    public static class TearDownHandler extends Handler {
        private WeakReference<MSTearDownView> mTextViewRef;

        public TearDownHandler(MSTearDownView view) {
            mTextViewRef = new WeakReference<MSTearDownView>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            MSTearDownView textView = mTextViewRef.get();
            if (null != textView) {
                removeMessages(MESSAGE_WHAT);
                textView.cutDown();
                textView.tearDown();
            }
        }
    }

    private void cutDown(){
        cutdownTime=cutdownTime-1;
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
