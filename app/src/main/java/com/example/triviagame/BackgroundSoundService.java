package com.example.triviagame;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class BackgroundSoundService extends Service {
    //@androidx.annotation.Nullable
    private int length = 0;
    private static final String TAG = null;
    MediaPlayer player,player2,player3;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.jazzyfrenchy);


        player.setLooping(true); // Set looping


        player.setVolume(70,70);

    }
    @SuppressLint("WrongConstant")
    public int onStartCommand(Intent intent, int flags, int startId) {



        player.start();


        return START_STICKY;
    }

    public void onStart(Intent intent, int startId) {
        // TO DO
        player.start();
        Toast.makeText(this, "Service Started and Playing Music", Toast.LENGTH_LONG).show();
    }
    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {
        player.pause();

        // player.release();
        // player = null;

    }
    public void onPause() {
        if(player.isPlaying())
        {
            player.pause();
            length=player.getCurrentPosition();

        }


    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();


    }

    @Override
    public void onLowMemory() {

    }

}