package com.example.st.gamemini.Toan.Toan;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ST on 9/14/2017.
 */

public class textView extends TextView {
    boolean isChecked;
    public textView(Context context) {
        super(context);
        isChecked=false;
    }

    public textView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public textView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //lấy ra chiều rộng chiều cao
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=getMeasuredWidth();//trả về chiều ngang của item
        int height=getMeasuredHeight();//trả về chiều cao của item
        setMeasuredDimension(width,width);
    }

}
