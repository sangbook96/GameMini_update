package com.example.st.gamemini.Toan.FreakingMath;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.st.gamemini.R;
import com.example.st.gamemini.Toan.Main.MainActivity;

import java.util.Random;

/**
 * Created by ST on 11/12/2017.
 */

public class Main_FreakingMath extends Activity {
    int so1, so2, kqDung = 0, kq = 0, diem = 0;
    TextView txtSo1, txtSo2, txtKq, txtDiemCuaBan, txtDiemCuaBanDialog, txtDiemCaoDialog;
    Button btTrue, btFalse, btDialog;
    boolean check = false;
    Random rd;
    Handler handler;
    ProgressBar progressBar;
    int progress;
    MyCountDownTimer myCountDownTimer;
    SharedPreferences sharedPreferences;
    private Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freaking_math);
        khoitao();
        TaoRandom();
        Time(2000, 10);
        setOnClick();

    }

    public void khoitao() {
        txtSo1 = (TextView) findViewById(R.id.txtSo1);
        txtSo2 = (TextView) findViewById(R.id.txtSo2);
        txtKq = (TextView) findViewById(R.id.txtKetQua);
        txtDiemCuaBan = (TextView) findViewById(R.id.txtDiemCuaBan);
        btTrue = (Button) findViewById(R.id.btTrue);
        btFalse = (Button) findViewById(R.id.btFalse);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_freaking);
        dialog = new Dialog(Main_FreakingMath.this);
        dialog.setContentView(R.layout.end_dialog);
        //Xét Title
        btDialog = (Button) dialog.findViewById(R.id.btThoat);
        txtDiemCuaBanDialog = (TextView) dialog.findViewById(R.id.yourMove);
        txtDiemCaoDialog = (TextView) dialog.findViewById(R.id.yourBest);
        txtDiemCaoDialog.setText(0+"");
    }

    public void TaoRandom() {
        so1 = random(1, 9);
        so2 = random(2, 9);
        kqDung = so1 + so2;
        Integer a = 0, b = 0;
        a = kqDung - 1;
        b = kqDung + 1;
        kq = random(a, b);
        txtSo1.setText(String.valueOf(so1));
        txtSo2.setText(String.valueOf(so2));
        txtKq.setText(String.valueOf(kq));

    }

    public void setOnClick() {
        btTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCountDownTimer.start();
                if (kq == kqDung) {
                    diem++;
                    TaoRandom();
                    txtDiemCuaBan.setText(String.valueOf(diem));
                } else {
                    doSave();
                    Dialog();

                }
            }
        });
        btFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCountDownTimer.start();
                if (kq != kqDung) {
                    diem++;

                    TaoRandom();
                    txtDiemCuaBan.setText(String.valueOf(diem));
                } else {
                    doSave();
                    Dialog();
                }
            }
        });

    }

    public int random(int min, int max) {
        //random các ô ngẫu nhiên
        try {
            rd = new Random();
            int range = max - min + 1;
            int randomNum = min + rd.nextInt(range);
            return randomNum;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void Time(long a, long b) {
        progressBar.setProgress(10);
        myCountDownTimer = new MyCountDownTimer(a, b);

    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            progress = (int) (millisUntilFinished / 10);
            progressBar.setProgress(progress);
        }

        @Override
        public void onFinish() {
            Dialog();
        }
    }

    public void Dialog() {
        myCountDownTimer.cancel();
        //khởi tạo dialog

        txtDiemCuaBanDialog.setText("Điểm của bạn: "+String.valueOf(diem));
        //save
        loadSave();
        btDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Main_FreakingMath.this, MainActivity.class);
                startActivity(in);
            }
        });
        dialog.setCancelable(false);//not click screen
        dialog.setTitle("Game Over");
        dialog.show();
    }

    //Lưu điểm
    //được gọi khi người dùng lưu điểm
    public void doSave() {
        //khai báo lưu điểm.

        sharedPreferences = getSharedPreferences("HighScoreFreakingMath", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (Integer.parseInt(String.valueOf(txtDiemCaoDialog.getText())) < diem) {
            editor.putInt("Score", diem);

        }
        editor.apply();
    }

    //load các giá trị đã lưu
    public void loadSave() {
        sharedPreferences = getSharedPreferences("HighScoreFreakingMath", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String diemcao = String.valueOf(sharedPreferences.getInt("Score", 0));
            this.txtDiemCaoDialog.setText("Điểm Cao: "+diemcao);
        }
    }


}
