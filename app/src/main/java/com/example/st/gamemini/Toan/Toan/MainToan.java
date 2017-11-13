package com.example.st.gamemini.Toan.Toan;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.st.gamemini.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by ST on 9/14/2017.
 */

public class MainToan extends Activity {
    GridView gv;
    ArrayList<toan> arr;
    ArrayList<Integer> arr1;
    ArrayList<Integer> arr2;
    adapter_toan adp;
    Random rd;
    CongCuHoTro help;
    int diemTongMatrix = 0;
    int diem = 0;//điểm người dùng chọn để mang so sánh vs kq diemRandom
    int diemRanrom = 0;//trả về kq điểm random
    int diemScore = 0;
    int number1 = 1, numer2 = 4;
    List<Integer> list;
    protected boolean check = false;
    protected boolean checkView = false;
    TextView txtScore, txtRandom, txtHighSocre;
    ProgressBar progressBar;
    int progress;
    MyCountDownTimer myCountDownTimer;
    SharedPreferences sharedPreferences;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_toan);
        help = new CongCuHoTro();
        khoiTao();
        Time(100000, 1000);
        music();
        myCountDownTimer.start();
        adp = new adapter_toan(this, R.layout.item_toan, arr);
        gv.setAdapter(adp);
        randomKQ();
        adp.notifyDataSetChanged();
    }

    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void khoiTao() {
        arr1 = new ArrayList<>();
        arr2 = new ArrayList<>();
        txtScore = (TextView) findViewById(R.id.sc);
        txtRandom = (TextView) findViewById(R.id.txtRandom);
        this.txtHighSocre = (TextView) findViewById(R.id.txtBestScore);
        progressBar = (ProgressBar) findViewById(R.id.progess);

        gv = (GridView) findViewById(R.id.gvToan);
        arr = new ArrayList<toan>();
        gv.setNumColumns(6);
        addNumber(number1, numer2);
        Log.d("", "Điểm tổng:" + diemTongMatrix);
        setOnitemClick();
        this.loadSave();
    }

    public void addNumber(int a, int b) {
        for (int i = 0; i < 36; i++) {
            arr.add(new toan(ranRom(a, b), false, false));
            diemTongMatrix += arr.get(i).getNumber();
            Log.d("Trả về điểm tổng", String.valueOf(diemTongMatrix));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void setOnitemClick() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arr.get(position).isSelected() == false) {
                    check = true;
                    arr.get(position).setSelected(check);
                    int b = arr.get(position).getNumber();
                    AddDiem(b, check);
                    if (diem == diemRanrom) {
                        checkView = true;

                        for (int i = 0; i < arr.size(); i++) {
                            if (arr.get(i).isSelected() == true) {
                                arr.get(i).setSelectedView(checkView);
                                //thiết lập các item bằng 0.
                                arr.get(i).setNumber(0);
                                playSoundHint();
                            }
                        }
                        randomKQ();
                        diem = 0;
                        //nếu điểm tổng các ô đã mất bằng điểm tổng số ô khi add vào arr thì nextgame
                        if (diemScore == diemTongMatrix) {
                            playSoundWin();
                            NextGame();
                            randomKQ();
                            loadSave();
                        }
                    }
                } else {
                    check = false;
                    arr.get(position).setSelected(check);
                    int c = arr.get(position).getNumber();
                    AddDiem(c, check);
                }
                adp.notifyDataSetChanged();
            }
        });
    }

    public void AddDiem(int a, boolean checkAdd) {
        //thêm các phần tử đã chọn trong gridView để tính tổng và so sánh
        //Tính tổng các phần tử đã check true
        arr1.add(a);
        if (check == true) {
            diem += a;
            if (diem < diemRanrom) {
                Log.d("", "Kết quả chưa đúng");
            }
            if (diem == diemRanrom) {
                Log.d("", "Kết quả đúng");
                diemScore += diem;
                txtScore.setText(String.format("" + diemScore));
            } else {
                if (diem > diemRanrom) {
                    checkAdd=false;
                    adp.notifyDataSetChanged();

                }


            }

        } else {
            diem -= a;
        }
    }

    public int ranRom(int min, int max) {
        //random các ô ngẫu nhiên
        try {
            Random rn = new Random();
            int range = max - min + 1;
            int randomNum = min + rn.nextInt(range);
            return randomNum;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void randomKQ() {
        int diemKq = 0, temp = 0;
        rd = new Random();
        diemRanrom = 0;
        arr2.clear();//làm mới mảng
        for (int j = 0; j < arr.size(); j++) {
            if (arr.get(j).getNumber() != 0) {
                temp++;
                //thêm các phần tử vào mảng tạm.
                arr2.add(arr.get(j).getNumber());
            }
        }
        Collections.shuffle(arr2);//xáo trộn các item
        Log.d("cac phan tu arr2", arr2 + "");
        Log.d("arr2.size", arr2.size() + "");
        //Nếu các phần tử trong arr >4 tính tổng bằng cách chọn 4 phần tử đầu tiên sau khi đã xáo trộn shuffle
        //else Tính tổng các phần tử trong arr2
        if (temp >= 4) {
            for (int l = 0; l < 4; l++) {
                diemRanrom = diemRanrom + arr2.get(l).intValue();
                Log.d("Diem Kq", diemRanrom + "");
            }
        } else {
            for (int k = 0; k < arr2.size(); k++) {
                diemRanrom = diemRanrom + arr2.get(k).intValue();
                Log.d("Diem Kq", diemRanrom + "");
                //arr2.remove(k);
                Log.d("arr5", "" + arr2);
            }
        }

        Log.d("Điểm random", diemRanrom + "");
        txtRandom.setText(String.format("" + diemRanrom));

    }

    public void NextGame() {
        int a = 200000, b = 1000;
        for (int i = 0; i < arr.size(); i++) {
            arr.get(i).setSelected(false);
            arr.get(i).setSelectedView(false);
        }
        arr.clear();
        arr2.clear();
        myCountDownTimer.cancel();
        progressBar.setProgress(0);
        Time(200000, 2000);
        myCountDownTimer.start();
        //tăng các phần tử thêm vào lên 1 từ vị trí 1-4->2-5
        number1++;
        numer2++;
        if (numer2 <= 99) {
            addNumber(number1, numer2);
        } else {
            number1 = 0;

            if (numer2 > 99) {
                number1++;
                numer2 = number1 + 4;
                addNumber(number1, numer2);
            }
        }
        adp.notifyDataSetChanged();
    }

    public void Time(long a, long b) {
        progressBar.setProgress(1000);
        myCountDownTimer = new MyCountDownTimer(a, b);

    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            progress = (int) (millisUntilFinished / 1000);
            progressBar.setProgress(progress);
        }

        @Override
        public void onFinish() {
            doSave();
            playSoundGameOver();
            Toast.makeText(MainToan.this, "GameOver!", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    //Lưu điểm
    //được gọi khi người dùng lưu điểm
    public void doSave() {
        //khai báo lưu điểm.

        sharedPreferences = getSharedPreferences("HighScore1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (Integer.valueOf((String) txtHighSocre.getText()) < Integer.valueOf((String) txtScore.getText())) {
            editor.putInt("Score", Integer.valueOf(txtScore.getText().toString().trim()));

        }
        editor.apply();
    }

    //load các giá trị đã lưu
    public void loadSave() {
        sharedPreferences = getSharedPreferences("HighScore1", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String diemcao = String.valueOf(sharedPreferences.getInt("Score", 0));
            this.txtHighSocre.setText(diemcao);
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

    public void playSoundTryAgain() {
        if (loaded) {
            float leftVolumn = volume;
            float rightVolumn = volume;
            // Phát âm thanh tiếng Hint. Trả ề ID của luồng mới phát ra.
            int streamId = this.soundPool.play(this.soundIDTryAgain, leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }
}

