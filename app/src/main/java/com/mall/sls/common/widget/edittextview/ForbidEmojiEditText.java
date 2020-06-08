package com.mall.sls.common.widget.edittextview;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.mall.sls.MainApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForbidEmojiEditText extends AppCompatEditText {


    public ForbidEmojiEditText(Context context) {
        super(context);
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/Roboto-Regular-14.ttf");
        setTypeface(typeFace1);
    }

    public ForbidEmojiEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/Roboto-Regular-14.ttf");
        setTypeface(typeFace1);
    }

    public ForbidEmojiEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface typeFace1 = Typeface.createFromAsset(MainApplication.getContext().getAssets(), "fonts/Roboto-Regular-14.ttf");
        setTypeface(typeFace1);
    }

    @Override
    public void setFilters(InputFilter[] filters) {
        InputFilter emojiFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                       int dstart, int dend) {
                Pattern emoji = Pattern.compile(
                        "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                        Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                return null;
            }
        };
        super.setFilters(new InputFilter[]{emojiFilter});
    }
}
