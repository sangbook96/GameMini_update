package com.example.st.gamemini.Toan.CaiDat;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by ST on 11/2/2017.
 */

public class BackgroundAudioService extends Service implements MediaPlayer.OnCompletionListener {
    MediaPlayer mediaPlayer;

    public class BackgroundAudioServiceBinder extends Binder {
        BackgroundAudioService getService() {
            return BackgroundAudioService.this;
        }
    }

    private final IBinder basBinder = new BackgroundAudioServiceBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return basBinder;
    }


    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("PLAYERSERVICE", "onStartCommand");
        mediaPlayer = MediaPlayer.create(this, (int) intent.getExtras().get("Nhac"));
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        return START_NOT_STICKY;
    }

    public void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        Log.v("SIMPLESERVICE", "onDestroy");
        stopSelf();
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

}