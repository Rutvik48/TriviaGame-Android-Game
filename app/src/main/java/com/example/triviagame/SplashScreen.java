package com.example.triviagame;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {
    private ConstraintLayout layout;
    private int st = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //removes the title from top and makes full window
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //we can hide the notification bar by changing second parameter to FLAG_FULLSCREEN from FLAG_DIM_BEHIND
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        setContentView(R.layout.activity_splash_screen);

        //hides the title bar
        getSupportActionBar().hide();

        LogoLauncher logoLauncher=new LogoLauncher();
        logoLauncher.start();

        layout = findViewById(R.id.constraintLayout);
        HeaderClass headerClassInstance = new HeaderClass();
        headerClassInstance.setBackground(layout, getApplicationContext());

        if(headerClassInstance.getMusicPref(getApplicationContext()))
            startService(new Intent(this, BackgroundSoundService.class));

    }

    private class LogoLauncher extends Thread{
        public void run(){
            try{
                sleep(2000 );
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Intent intent = new Intent(SplashScreen.this, HomePage.class);
            startActivity(intent);
            SplashScreen.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        //back button will not do anything
    }
}