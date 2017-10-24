package com.example.st.gamemini.Toan.LatHinh;

import android.media.AudioManager;
import android.media.SoundPool;
/**
 * Created by ST on 10/16/2017.
 */

public class Music {
    private SoundPool soundPool;
    private AudioManager audioManager;
    //số luồng âm thanh phát ra tối đa 5;
    private static final int max_Streams=5;
    //chọn loại luồng phát ra âm thanh
    // Chọn loại luồng âm thanh để phát nhạc.
    private static final int streamType = AudioManager.STREAM_MUSIC;

    private boolean loaded;

    private int soundIdDestroy;
    private int soundIdGun;
    private float volume;

}
