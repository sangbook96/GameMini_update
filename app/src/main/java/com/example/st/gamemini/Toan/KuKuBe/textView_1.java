package com.example.st.gamemini.Toan.KuKuBe;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ST on 10/24/2017.
 */

public class textView_1 extends TextView {
    public textView_1(Context context) {
        super(context);
    }

    public textView_1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public textView_1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //lấy ra chiều rộng chiều cao
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();//trả về chiều ngang của item
        int height = getMeasuredHeight();//trả về chiều cao của item
        setMeasuredDimension(width, width);
    }

}
