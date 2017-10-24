package com.example.st.gamemini.Toan.Toan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;

import com.example.st.gamemini.R;

import java.util.List;

/**
 * Created by ST on 9/14/2017.
 */

public class adapter_toan extends BaseAdapter {
    private Context context;
    private int layout;
    private List<toan> arrList;
    int diem = 0;
    boolean checkDiem = false;
    final int b = 0;
    Animation anim;

    public adapter_toan(Context context, int layout, List<toan> arrList) {
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

    private class ViewHolder {
        textView txtToan;
        boolean selected;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtToan = (textView) convertView.findViewById(R.id.txtNumber);
            holder.selected = false;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final toan t = arrList.get(position);
        holder.txtToan.setText(String.valueOf(t.getNumber()));
        diem = Integer.parseInt(holder.txtToan.getText().toString());
        if (t.isSelected() == true) {
            holder.txtToan.setBackgroundResource(R.drawable.b_selected);

        } else {
            holder.txtToan.setBackgroundResource(R.drawable.b);
        }
        if (t.isSelectedView() == true) {
            holder.txtToan.setVisibility(View.INVISIBLE);

        } else {
            holder.txtToan.setVisibility(View.VISIBLE);
        }

//        holder.txtToan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.selected == false) {
//                    holder.selected = true;
//                    holder.txtToan.setBackgroundResource(R.drawable.b_selected);
//                    Toast.makeText(context, "Điểm bđ: "+diem, Toast.LENGTH_SHORT).show();
//                    diem+=b;
//                    Toast.makeText(context, "Điểm: "+diem, Toast.LENGTH_SHORT).show();
//                    holder.txtToan.setVisibility(View.GONE);
//                } else {
//                    holder.selected = false;
//                    holder.txtToan.setBackgroundResource(R.drawable.b);
//                    diem-=b;
//                }
//            }
//        });
        return convertView;
    }

}
