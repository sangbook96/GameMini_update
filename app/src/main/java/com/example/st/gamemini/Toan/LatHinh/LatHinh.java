package com.example.st.gamemini.Toan.LatHinh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.st.gamemini.R;
import com.example.st.gamemini.Toan.HightScore.main_Score;

/**
 * Created by ST on 23/08/2017.
 */

public class LatHinh extends Activity {
    Button btPlay,btXepHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mennu_play_lathinh);
        khoiTao();
        onClickButton();
    }
    public void khoiTao(){
        btPlay=(Button)findViewById(R.id.btPlayGameLatHinh);
        btXepHang=(Button)findViewById(R.id.btXepHang);
    }
    public void onClickButton(){
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(LatHinh.this,Screen_Hinh.class);
                startActivity(in);
            }
        });
        btXepHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(LatHinh.this,main_Score.class);
                startActivity(in);
                Toast.makeText(LatHinh.this, "Xếp hạng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
