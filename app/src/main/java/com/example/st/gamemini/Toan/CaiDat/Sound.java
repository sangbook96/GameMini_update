package com.example.st.gamemini.Toan.CaiDat;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.st.gamemini.R;

/**
 * Created by ST on 11/6/2017.
 */

public class Sound extends Service{
    private SoundPool soundPool;
    private AudioManager audioManager;
    // Số luồng âm thanh phát ra tối đa.
    private static final int MAX_STREAMS = 5;
    // Chọn loại luồng âm thanh để phát nhạc.
    private static final int streamType = AudioManager.STREAM_MUSIC;
    private boolean loaded;
    private int soundIdGameOver, soundIDTryAgain, soundIdWin, soundIdGood, soundIDHind;
    private float volume;
    //xử lí sound trong game.

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public void music() {
        // Đối tượng AudioManager sử dụng để điều chỉnh âm lượng.
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // Chỉ số âm lượng hiện tại của loại luồng nhạc cụ thể (streamType).
        float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);


        // Chỉ số âm lượng tối đa của loại luồng nhạc cụ thể (streamType).
        float maxVolumeIndex = (float) audioManager.getStreamMaxVolume(streamType);

        // Âm lượng  (0 --> 1)
        this.volume = currentVolumeIndex / maxVolumeIndex;

//         Cho phép thay đổi âm lượng các luồng kiểu 'streamType' bằng các nút
//         điều khiển của phần cứng.
        //this.setVolumeControlStream(streamType);

         //Với phiên bản Android SDK >= 21
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
