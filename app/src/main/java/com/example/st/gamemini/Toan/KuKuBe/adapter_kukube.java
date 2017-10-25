package com.example.st.gamemini.Toan.KuKuBe;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.st.gamemini.R;

import java.util.ArrayList;

/**
 * Created by ST on 10/24/2017.
 */

public class adapter_kukube extends   ArrayAdapter<String> {
    private Context context;
    private int resource;
    private ArrayList<String> arr;
    private ArrayAdapter<String> adp;


    public adapter_kukube(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arr = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_kukube, parent, false);
        TextView txtColor = (TextView) convertView.findViewById(R.id.txtItemKukukube);

        //kukube kukube = arr.get(position);
        txtColor.setBackgroundColor(Color.parseColor(arr.get(position).trim()));

        return convertView;
    }
}