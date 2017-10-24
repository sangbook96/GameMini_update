package com.example.st.gamemini.Toan.LatHinh;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.st.gamemini.R;
import com.example.st.gamemini.Toan.HightScore.main_Score;

/**
 * Created by ST on 10/16/2017.
 */

public class Screen_Hinh extends Activity {
    adapter_Hinh adp;
    GridView grd;
    Button btShow;
    boolean check = false;
    TextView txtHint, txtScoreHinh, txtLevel,txtDiemCao1;
    int hang = 2, level = 1;
    private Chronometer timer;
    CountDownTimer cdt;
    final Handler handler = new Handler();
    Animation animation;
    Board b;
    int DiemScore=0;int diemcao=0;
    //sound
    private SoundPool soundPool;
    private AudioManager audioManager;
    // Số luồng âm thanh phát ra tối đa.
    private static final int MAX_STREAMS = 5;
    // Chọn loại luồng âm thanh để phát nhạc.
    private static final int streamType = AudioManager.STREAM_MUSIC;
    private boolean loaded;
    private int soundIdGameOver, soundIDTryAgain, soundIdWin, soundIdGood, soundIDHind;
    private float volume;
    private SharedPreferences sharedPreferences;//Lưu điểm

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_hinh);
        b = new Board();
        //timer = (Chronometer) findViewById(R.id.timer);
        adp = new adapter_Hinh(this, R.layout.item_hinh, b.arr);
        AnhXa();
        setOnClick();
        txtScoreHinh.setText(String.valueOf(b.arr.size() + 4));
        //txtDiemCao.setText();
    }

    public void AnhXa() {
        grd = (GridView) findViewById(R.id.gvHinh);
        btShow = (Button) findViewById(R.id.btShowHinh);
        txtHint = (TextView) findViewById(R.id.txtHint);
        txtScoreHinh = (TextView) findViewById(R.id.txtScoreHinh);
        txtDiemCao1=(TextView)findViewById(R.id.txtHigh_Score_hinh_1);
        txtLevel = (TextView) findViewById(R.id.txtlevelHinh);

        KhoiTao(hang);
        music();
        //set sự kiện show tất cả item cho người dùng nhớ
        ShowButton();
        //showw time

    }

    public void KhoiTao(int a) {
        grd.setNumColumns(a);
        b.Level(a);
        txtLevel.setText(String.valueOf(level));
        grd.setAdapter(adp);
    }

    public void setOnClick() {
        grd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                if (b.arr.get(position).isSelected() == false) {
                    check = true;
                    b.arr.get(position).setSelected(check);
                    //
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            KiemTra(position);
                            adp.notifyDataSetChanged();
                        }
                    }, 650);
                }
                adp.notifyDataSetChanged();
            }
        });

    }

    public void KiemTra(int postion) {
        //kiểm tra số lượt click
        if (b.luotClick <= (b.arr.size() + 4)) {
            b.luotClick++;//số lượt click tăng lên
            b.maxitem--;//sum click trong 1 màn.
            txtScoreHinh.setText(String.valueOf(b.maxitem));
        } else {
            Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show();
            DialogGameOver();
        }
        Log.d("Số lượt click ", "" + b.luotClick);
        if (b.getpos == -1) {
            b.getpos = postion;
            b.kiemtra = b.arr.get(postion).getHinh();
            Log.d("kt", "" + b.kiemtra);
        } else {
            if (b.getpos != postion) {
                b.arr.get(postion).setSelected(true);
                //kiểm tra nếu 2 hình giống nhau thì sẽ biến mất
                if (b.kiemtra == b.arr.get(postion).getHinh()) {
                    Log.d("", "" + b.getpos + " " + postion);
                    //arr.get(postion).setSelected(true);
                    b.arr.get(postion).setSelectedView(true);
                    b.arr.get(b.getpos).setSelectedView(true);
                    b.getpos = -1;
                    //tổng số item chọn đúng.
                    b.sumItem += 2;
                    b.diem=b.diem+10;
                    playSoundGood();
                    //Tinh diem cho mooix lần chọn đúng.
                    txtDiemCao1.setText(String.valueOf(b.diem));
                    if (b.sumItem == b.arr.size()) {
                        Toast.makeText(this, "Bạn win", Toast.LENGTH_SHORT).show();
                        //thiết lập lại số tổng số item chọn đúng và tổng số lần chọn item
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                playSoundWin();
                                //thiết lập nextGame
                                b.arr.clear();
                                b.maxitem = 0;
                                b.sumItem = 0;
                                b.luotClick = 0;
                                hang = hang + 2;
                                KhoiTao(hang);
                                level = level + 1;
                                txtLevel.setText(String.valueOf(level));
                                txtScoreHinh.setText(String.valueOf(b.maxitem));

                            }
                        }, 1300);

                    }
                } else {
                    if (b.getpos != -1) {
                        Log.d("Item chọn sai", "" + b.getpos + "   " + postion);
                        //item chọn sai thiết lập lại hình up
                        b.arr.get(b.getpos).setSelected(false);
                        b.arr.get(postion).setSelected(false);
                        b.getpos = -1;
                        playSoundTryAgain();
                    }
                }
            }
        }
    }

    public void ShowButton() {
        btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Screen_Hinh.this, "bt click", Toast.LENGTH_SHORT).show();

                //Sau 6s sẽ ẩn các item
                playSoundHint();
                cdt = new CountDownTimer(4000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        check = true;
                        for (int i = 0; i < b.arr.size(); i++) {
                            b.arr.get(i).setSelected(check);
                            Log.d("Thông báo", "");
                        }
                        btShow.setVisibility(View.INVISIBLE);
                        txtHint.setVisibility(View.VISIBLE);
                        txtHint.setText(String.valueOf(millisUntilFinished / 1000) + " s");
                        adp.notifyDataSetChanged();
                    }
                    @Override
                    public void onFinish() {
                        check = false;
                        for (int i = 0; i < b.arr.size(); i++) {
                            b.arr.get(i).setSelected(check);
                            Log.d("Thông báo", "");
                        }
                        btShow.setVisibility(View.VISIBLE);
                        txtHint.setVisibility(View.INVISIBLE);
                        adp.notifyDataSetChanged();
                        playSoundHint();
                    }

                }.start();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //timer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        //timer.stop();
    }


    //Dialog game
    public void DialogGameOver() {
        AlertDialog.Builder algameOver = new AlertDialog.Builder(this);
        algameOver.setMessage("Bạn Đã Thua");
        algameOver.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent in =new Intent(Screen_Hinh.this,main_Score.class);
//                Bundle bundle=new Bundle();
//                bundle.putInt("HighScore", Integer.parseInt(String.valueOf(b.diem)));
                in.putExtra("TruyenDuLieu",Integer.parseInt(String.valueOf(b.diem)));
                Toast.makeText(Screen_Hinh.this, ""+b.diem, Toast.LENGTH_SHORT).show();
                startActivity(in);

            }
        });
        AlertDialog alert = algameOver.create();
        alert.show();
    }

    public void DialogWinNext() {
        AlertDialog.Builder al = new AlertDialog.Builder(this);
        al.setMessage("Bạn có muốn next game?");
        al.setTitle("Thông báo");
        al.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                b.arr.clear();
                b.maxitem = 0;
                b.sumItem = 0;
                b.luotClick = 0;
                hang = hang + 2;
                KhoiTao(hang);
                level = level + 1;
                txtLevel.setText(String.valueOf(level));
                txtScoreHinh.setText(String.valueOf(b.maxitem));
            }
        });
        al.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alert = al.create();
        alert.show();
    }

    //xử lí sound trong game.
    public void music() {
        // Đối tượng AudioManager sử dụng để điều chỉnh âm lượng.
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // Chỉ số âm lượng hiện tại của loại luồng nhạc cụ thể (streamType).
        float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);


        // Chỉ số âm lượng tối đa của loại luồng nhạc cụ thể (streamType).
        float maxVolumeIndex = (float) audioManager.getStreamMaxVolume(streamType);

        // Âm lượng  (0 --> 1)
        this.volume = currentVolumeIndex / maxVolumeIndex;

        // Cho phép thay đổi âm lượng các luồng kiểu 'streamType' bằng các nút
        // điều khiển của phần cứng.
        this.setVolumeControlStream(streamType);

        // Với phiên bản Android SDK >= 21
        if (Build.VERSION.SDK_INT >= 21) {

            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        // Với phiên bản Android SDK < 21
        else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        // Sự kiện SoundPool đã tải lên bộ nhớ thành công.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });

        // Tải file nhạc tiếng gameover! vào SoundPool.
        this.soundIdGameOver = this.soundPool.load(this, R.raw.gameover1, 1);
        this.soundIdWin = this.soundPool.load(this, R.raw.winning, 1);
        // Tải file nhạc tiếng good vào SoundPool.
        this.soundIdGood = this.soundPool.load(this, R.raw.good1, 1);
        //Tải file nhạc ẩn hiện vào soundPool
        this.soundIDHind = this.soundPool.load(this, R.raw.hintsound, 1);
        this.soundIDTryAgain = this.soundPool.load(this, R.raw.tryagain2, 1);

    }

    // Khi 2 hình giống nhau
    public void playSoundGood() {
        if (loaded) {
            float leftVolumn = volume;
            float rightVolumn = volume;
            // Phát âm thanh tiếng good. Trả về ID của luồng mới phát ra.
            int streamId = this.soundPool.play(this.soundIdGood, leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    // Khi hiện tất cả hình
    public void playSoundHint() {
        if (loaded) {
            float leftVolumn = volume;
            float rightVolumn = volume;
            // Phát âm thanh tiếng Hint. Trả về ID của luồng mới phát ra.
            int streamId = this.soundPool.play(this.soundIDHind, leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    // Khi người dùng thua cuộc.
    public void playSoundGameOver() {
        if (loaded) {
            float leftVolumn = volume;
            float rightVolumn = volume;

            // Phát âm thanh tiếng gameOver
            // Trả về ID của luồng mới phát ra.
            int streamId = this.soundPool.play(this.soundIdGameOver, leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    public void playSoundWin() {
        if (loaded) {
            float leftVolumn = volume;
            float rightVolumn = volume;
            // Phát âm thanh tiếng Hint. Trả về ID của luồng mới phát ra.
            int streamId = this.soundPool.play(this.soundIdWin, leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    public void playSoundTryAgain() {
        if (loaded) {
            float leftVolumn = volume;
            float rightVolumn = volume;
            // Phát âm thanh tiếng Hint. Trả ề ID của luồng mới phát ra.
            int streamId = this.soundPool.play(this.soundIDTryAgain, leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }
}
