package com.example.st.gamemini.Toan.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.st.gamemini.R;
import com.example.st.gamemini.Toan.CaiDat.BackgroundAudioService;
import com.example.st.gamemini.Toan.FreakingMath.Main_FreakingMath;
import com.example.st.gamemini.Toan.KuKuBe.Screen_Kukube;
import com.example.st.gamemini.Toan.LatHinh.LatHinh;
import com.example.st.gamemini.Toan.Toan.MainToan;

public class MainActivity extends Activity {
    ImageView imgLatHinh, imgGiaiToan, imgHighScore, imgKukube;
    Button btShare, btRate;
    Intent playbackServiceIntent;
    SharedPreferences sharedPreferences;
    boolean check=true;
    public void KhoiTao() {
       /* Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(), R.drawable.lathinh);
        Bitmap circularBitmap=ImageConverter.getRoundedCornerBitMap(bitmap,100);//bo tròn các góc của bitmap
        Bitmap bitmap1= BitmapFactory.decodeResource(this.getResources(),R.drawable.giaitoan);
        Bitmap circularBitmap1=ImageConverter.getRoundedCornerBitMap(bitmap1,100);
        Bitmap bitmap2= BitmapFactory.decodeResource(this.getResources(),R.drawable.high_score);
        Bitmap circularBitmap2=ImageConverter.getRoundedCornerBitMap(bitmap2,100);*/
        imgLatHinh = (ImageView) findViewById(R.id.imgLatHinh);
        imgGiaiToan = (ImageView) findViewById(R.id.imgGiaiToan);
        imgHighScore = (ImageView) findViewById(R.id.imgHighScore_Main);
        imgKukube = (ImageView) findViewById(R.id.imgkukube);
        btShare = (Button) findViewById(R.id.btShare);
        btRate = (Button) findViewById(R.id.btRate);
        imgLatHinh.setImageResource(R.drawable.math_memory);
        imgGiaiToan.setImageResource(R.drawable.sum_finder);
        imgHighScore.setImageResource(R.drawable.high_score);
        imgKukube.setImageResource(R.drawable.iconkukube2);

       /* imgLatHinh.setImageBitmap(circularBitmap);
        imgGiaiToan.setImageBitmap(circularBitmap1);
        imgHighScore.setImageBitmap(circularBitmap2);*/
    }

    public void onClickButton() {
        imgLatHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplication(), LatHinh.class);
                startActivity(in);
            }
        });
        imgGiaiToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplication(), MainToan.class);
                startActivity(in);
            }
        });
        imgHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplication(), Main_FreakingMath.class);
               startActivity(in);
            }
        });
        imgKukube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplication(), Screen_Kukube.class);
                startActivity(in);
            }
        });
        btRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Rate-Chức năng đang phát triển.", Toast.LENGTH_SHORT).show();
            }
        });
        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Share-Chức năng đang phát triển.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KhoiTao();
        onClickButton();
    }

    public void startservice() {
        playbackServiceIntent = new Intent(this, BackgroundAudioService.class);
        playbackServiceIntent.putExtra("Nhac", R.raw.marim);
        startService(playbackServiceIntent);

    }
    //load các giá trị đã lưu
    public void loadSave() {
        sharedPreferences = getSharedPreferences("SaveSetup", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            check=sharedPreferences.getBoolean("SaveBatTat",true);
            startservice();
        }
    }
}
