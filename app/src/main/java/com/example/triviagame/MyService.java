package com.example.triviagame;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;

import android.widget.Toast;

//import com.example.triviagame.R;

public class MyService extends Service {
    MediaPlayer myPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Successfully Created", Toast.LENGTH_LONG).show();

        myPlayer = MediaPlayer.create(this, R.raw.jazzyfrenchy);
        //setting loop play to true
        //this will make the ringtone continuously playing        myPlayer.setLooping(false); // Set looping
    }
    @Override
    public void onStart(Intent intent, int startid) {
        // Toast.makeText(this, "Service Started and Playing Music", Toast.LENGTH_LONG).show();
        myPlayer.start();
    }
    @Override
    public void onDestroy() {
        //Toast.makeText(this, "Service Stopped and Music Stopped", Toast.LENGTH_LONG).show();
        myPlayer.stop();
        myPlayer.release();
    }



}