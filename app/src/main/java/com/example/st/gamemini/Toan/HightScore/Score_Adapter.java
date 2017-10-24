package com.example.st.gamemini.Toan.HightScore;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.st.gamemini.R;

import java.util.ArrayList;

/**
 * Created by ST on 10/18/2017.
 */

public class Score_Adapter extends ArrayAdapter<Score> {
    private Context context;
    private  int resource;
    private ArrayList<Score> arr;
    private ArrayAdapter<Score>adp;



    public Score_Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Score> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.arr=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_score,parent,false);
        ImageView img=(ImageView)convertView.findViewById(R.id.img_highScore);
        TextView txtName=(TextView)convertView.findViewById(R.id.txtName_Score);
        TextView txtDiem=(TextView)convertView.findViewById(R.id.txtDiem_Score);
        TextView txtTime=(TextView)convertView.findViewById(R.id.txtTime_Score);
        TextView txtLevel=(TextView)convertView.findViewById(R.id.txtLevel_Score);
        Score s=arr.get(position);
        txtName.setText(s.getName());
        txtDiem.setText(String.valueOf(s.getDiem()));
        txtTime.setText(s.getThoiGian());
        txtLevel.setText(String.valueOf(s.getLevel()));
        return convertView;
    }
}
