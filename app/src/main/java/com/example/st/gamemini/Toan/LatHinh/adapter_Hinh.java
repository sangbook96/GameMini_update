package com.example.st.gamemini.Toan.LatHinh;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.st.gamemini.R;

import java.util.List;

/*
 * Created by ST on 9/18/2017.
 */


public class adapter_Hinh extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Hinh> arrList;
    int diem = 0;
    final int a = 0;
    Animation animation;
    Animator animator;

    public adapter_Hinh(Context context, int layout, List<Hinh> arrList) {
        this.context = context;
        this.layout = layout;
        this.arrList = arrList;
    }

    //trả về tổng số phần tử
    @Override
    public int getCount() {
        return arrList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrList;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        animation = AnimationUtils.loadAnimation(context, R.anim.rotate);
        //
        final ImageView imgHinh = (ImageView) view.findViewById(R.id.imgHinh);
        final Hinh h = arrList.get(position);
        final Handler handler = new Handler();

        if (h.isSelected() == true) {
            imgHinh.setImageResource(h.getHinh());
            view.startAnimation(animation);
            Log.d("view start", "");
            //imgHinh.setImageResource(R.drawable.emoij_up);
        } else {
            // imgHinh.setImageResource(h.getHinh());
            imgHinh.setImageResource(R.drawable.emoij_up);
        }
        if (h.isSelectedView() == true) {
            imgHinh.setVisibility(View.INVISIBLE);
        } else {
            imgHinh.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
