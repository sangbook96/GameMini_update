package com.example.st.gamemini.Toan.HightScore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.st.gamemini.R;

public class main_Score extends Activity {
    TextView txtVang, txtBac, txtDong;
    int ScoreVang=0, ScoreBac=0, ScoreDong=0, ScoreSoSanh=0;
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hight_score);
        AnhXa();
        XuLi();
        doSave();
        loadSave();
        txtVang.setText(String.valueOf(ScoreVang));
        txtBac.setText(String.valueOf(ScoreBac));
        txtDong.setText(String.valueOf(ScoreDong));
    }

    public void AnhXa() {
        txtVang = (TextView) findViewById(R.id.txtHgihScoreVang);
        txtBac = (TextView) findViewById(R.id.txtHgihScoreBac);
        txtDong = (TextView) findViewById(R.id.txtHgihScoreDong);
    }

    public void XuLi() {
        Intent in = getIntent();
//        Bundle bundle = in.getBundleExtra("TruyenDuLieu");
        ScoreSoSanh = in.getIntExtra("TruyenDuLieu",0);
        Toast.makeText(this, "Diem so sanh"+ScoreSoSanh, Toast.LENGTH_SHORT).show();
    }

    public void doSave() {
        //khai báo lưu điểm.

        sharedPreferences = getSharedPreferences("BangXepHang", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (ScoreVang < ScoreSoSanh) {
            ScoreVang=ScoreSoSanh;
            editor.putInt("ScoreVang", ScoreVang);
        } else if (ScoreSoSanh < ScoreVang && ScoreSoSanh > ScoreBac) {
            ScoreBac=ScoreSoSanh;
            editor.putInt("ScoreBac", ScoreBac);
        } else if (ScoreSoSanh < ScoreBac && ScoreSoSanh > ScoreDong) {
            ScoreDong=ScoreSoSanh;
            editor.putInt("ScoreDong", ScoreDong);
        }
        editor.apply();
    }

    //load các giá trị đã lưu
    public void loadSave() {
        sharedPreferences = getSharedPreferences("BangXepHang", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            ScoreVang = sharedPreferences.getInt("ScoreVang", 0);
            ScoreBac = sharedPreferences.getInt("ScoreBac", 0);
            ScoreDong = sharedPreferences.getInt("ScoreDong", 0);
        }
        Log.d("",""+ScoreVang+ScoreBac+ScoreDong);

    }
}
