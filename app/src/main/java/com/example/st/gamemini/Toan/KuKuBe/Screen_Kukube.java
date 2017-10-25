package com.example.st.gamemini.Toan.KuKuBe;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.st.gamemini.R;

import java.util.ArrayList;

/**
 * Created by ST on 10/24/2017.
 */

public class Screen_Kukube extends Activity {
    TextView txtBestScore, txtScore, txtimer;
    GridView gridKukube;
    TaoMau t = new TaoMau();
    ArrayList arr;
    adapter_kukube adp;
    int diem = 0;
    int soO = 2;
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
    SharedPreferences sharedPreferences;
    CountDownTimer countDownTimer = new CountDownTimer(5000, 100) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtimer.setText(millisUntilFinished / 100 + "");
        }
        @Override
        public void onFinish() {
            doSave();
            playSoundGameOver();
            finish();
        }
    }.start();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_kukube);
        AnhXa();
//    /*    KhoiTao();
        setDuLieu();
        music();
        loadSave();
        setonClick();
    }

    public void AnhXa() {
        txtBestScore = (TextView) findViewById(R.id.txtBestScoreKukube);
        txtScore = (TextView) findViewById(R.id.txtScoreKukube);
        txtimer = (TextView) findViewById(R.id.Chronometer_ScoreKukube);
        gridKukube = (GridView) findViewById(R.id.gridkukube);

    }


    private void setDuLieu() {
        Level();
        adp = new adapter_kukube(this, R.layout.item_kukube, arr);
        gridKukube.setAdapter(adp);
        txtScore.setText(String.valueOf(diem));
        adp.notifyDataSetChanged();
    }

    public void Level() {
        if (diem < 10) {
            gridKukube.setNumColumns(2);
            soO = 4;
        } else {
            gridKukube.setNumColumns(diem / 10 + 2);
            soO = (diem / 10 + 2) * (diem / 10 + 2);
        }
        arr = new ArrayList<String>(t.taoBangMau(soO));
    }

    private void setonClick() {
        gridKukube.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == t.dapAn) {
                    countDownTimer.start();
                    setDuLieu();
                    playSoundHint();
                }
            }
        });
    }
    //Lưu điểm
    //được gọi khi người dùng lưu điểm
    public void doSave() {
        //khai báo lưu điểm.

        sharedPreferences = getSharedPreferences("HighScoreKukuKuBe", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (Integer.valueOf((String) txtBestScore.getText()) < Integer.valueOf((String) txtScore.getText())) {
            editor.putInt("Score", Integer.valueOf(txtScore.getText().toString().trim()));
            Toast.makeText(this, "Điểm Cao!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Điểm của bạn!", Toast.LENGTH_SHORT).show();
        }
        editor.apply();
    }
    //load các giá trị đã lưu
    public void loadSave() {
        sharedPreferences = getSharedPreferences("HighScoreKukuKuBe", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String diemcao = String.valueOf(sharedPreferences.getInt("Score", 0));
            this.txtBestScore.setText(diemcao);
        }
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
}
