package com.example.triviagame;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;



public class HomePage extends AppCompatActivity {

    //variables to assign the button or text that user see on screen
    //NOTE: btn is for Button, tv for TextView
    private Button btn_GameStart, btn_BackHome, btn_UserInfo;
    public TextView tv_LogIn, tv_ChangeBackground, tv_GameName;
    private ImageView iv_logo;
    private Switch sw_Music;
    private ConstraintLayout layout;
    //to exit the app this condition will be used
    private Boolean ifVis = true,exitCondition = false;
    public  static boolean musicSetting;
    private FirebaseAuth auth;
    private MediaPlayer mpPlayGame, mpHigh, mpBackground;
    Animation frombottom,fromtop;

    HeaderClass headerClassInstance = new HeaderClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //setContentView(R.layout.top_menu_bar);

        setFullScreen();

        // this will assign variables to button, text, etc from xml file
        assignVariables();

        initializeSettings();

        checkUser();

        clickListner();
        changeBackground();

        switchMusic();

        sw_Music.setChecked(headerClassInstance.getMusicPref(getApplicationContext()));
        musicSetting = headerClassInstance.getMusicPref(getApplicationContext());
        playAnimantion();


    }

    //plays the animation
    private void playAnimantion(){
        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromthetop);
        btn_GameStart.setAnimation(frombottom);
        tv_ChangeBackground.setAnimation(frombottom);
        btn_UserInfo.setAnimation(fromtop);
        iv_logo.setAnimation(fromtop);


        if(ifVis){
            tv_LogIn.setAnimation(fromtop);}


    }

    private void switchMusic(){

        sw_Music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                headerClassInstance.changeMusicPref(getApplicationContext());
                musicSetting = headerClassInstance.getMusicPref(getApplicationContext());

                if(musicSetting){
                    mpBackground.start();
                }
                else{
                    mpBackground.pause();
                }

                sw_Music.setChecked(musicSetting);
                //Toast.makeText(getApplicationContext(),"Music Changed", Toast.LENGTH_LONG).show();
            }
        });
    }

    //stop the Media to free up resources
    @Override
    protected void onStop() {
        super.onStop();

        Log.d("On Stop", "OnStop00000000000000000000000000000000000000000000000");
        stopMediaPlayer();

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("On Start", "OnStart=====================================================");

        if(musicSetting)
            startMediaPlayer();
    }

    public void startMediaPlayer(){
       // MediaPlayer mp = new MediaPlayer();
        mpBackground = MediaPlayer.create(this,R.raw.jazzyfrenchy);
        mpBackground.start();
    }

    public void stopMediaPlayer(){
        mpBackground.stop();
        mpBackground.release();
    }



    public void checkUser(){
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //this checks if any user is logged in or not
        if (auth.getCurrentUser() != null){
            //if user is looged in hide the "log in/ Sign up" text
            tv_LogIn.setText("Add Question");

            //this loads information from FireStore Database and adds it to userClass variables
            userClass userClass = new userClass(getApplicationContext());
            userClass.updateUI();
        }
    }

    //set settings based on user preferences
    private void initializeSettings(){

        headerClassInstance.setBackground(layout, getApplicationContext());

        //we do not need to show BackHome button on HomePage
        btn_BackHome.setVisibility(View.INVISIBLE);

        //set text programmatically rather than doing fix text using xml
        tv_GameName.setText(HeaderClass.GAME_NAME);
        btn_GameStart.setText("Start Game");
        tv_LogIn.setText("Log in / Sign Up");
    }

    //this method is called when a user clicks any button on Home Screen
    private void clickListner(){

        btn_UserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(auth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(), PopUpWindow.class));
                    mpHigh.start();
                }else{
                    Toast.makeText(getApplicationContext(), "Sign up to get free 10 coins.",Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_GameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CategoriesPage.class));
                mpPlayGame.start();

                finish();
            }
        });

        tv_LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpHigh.start();

                if (auth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(), AddQuestionPage.class));
                }else
                    startActivity(new Intent(getApplicationContext(), LogInPage.class));

                finish();

            }
        });
    }

    //using HeaderClass this method changes background of application
    private void changeBackground(){
        tv_ChangeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpHigh.start();
                int backgroundNum = headerClassInstance.getCurBackground(getApplicationContext());

                int temp = headerClassInstance.chnageBackground(backgroundNum, layout);

                headerClassInstance.saveBackground(temp, getApplicationContext());
            }
        });
    }

    public void setFullScreen(){

        //hides the title bar
        getSupportActionBar().hide();

        //this code makes the status bar transparent
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND
        );

    }

    //function to assign buttons and texts to java variables
    private void assignVariables(){

        iv_logo = findViewById(R.id.imageView4);
        mpBackground = MediaPlayer.create(this,R.raw.jazzyfrenchy);
        mpBackground.setLooping(true);
        mpPlayGame = MediaPlayer.create(this,R.raw.buttonpress);
        mpHigh = MediaPlayer.create(this,R.raw.swoosh);
        btn_GameStart = findViewById(R.id.btnGameStart);
        tv_LogIn = findViewById(R.id.tvLogIn);
        btn_BackHome = findViewById(R.id.btnBackToHome);
        btn_UserInfo = findViewById(R.id.btnUserInfo);
        tv_ChangeBackground = findViewById(R.id.tvChangeBackground);
        layout = findViewById(R.id.layoutHeader);
        tv_GameName = findViewById(R.id.tvGameName);
        sw_Music = findViewById(R.id.swMusic);
    }

    //asks user to click twice on back button to exit the app
    @Override
    public void onBackPressed() {
        if (exitCondition){
            super.onBackPressed();
            finish();
            stopService(new Intent(this, MyService.class));
        }else{
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            exitCondition = true;
        }
    }
}