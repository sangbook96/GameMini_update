package com.example.st.gamemini.Toan.LatHinh;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by ST on 10/1/2017.
 */

public class img_customHinh extends ImageView {
    boolean isChecked;

    public img_customHinh(Context context) {
        super(context);
        isChecked = false;
    }

    public img_customHinh(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public img_customHinh(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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