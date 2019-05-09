package com.example.triviagame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PopUpWindow extends Activity {

    //TAG to handle errors
    public static final String TAG = "AddUserData";

    //variables
    private TextView tv_UserName;
    private TextView tv_HighestScore, tv_TotalCoin;
    private Button btn_SignOut;
    private int counter = 0;
    private ConstraintLayout layout;
    private userClass userClass;

    private MediaPlayer mpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);

        userClass = new userClass(getApplicationContext());
        assignVariables();

        userSignOut();
        setWindowSize();

        //set text to buttons and TextViews
        setTexts();

        userClass.userJustLogedIn = false;

    }

    private void setTexts(){

        //check if user exits
        if(userClass.mAuth.getUid() != null) {

            tv_UserName.setText("Email: "+userClass.getUserEmail());
            tv_TotalCoin.setText("Coins: "+Integer.toString(userClass.getCoins()));
            tv_HighestScore.setText("Highest Score: "+ Integer.toString(userClass.getHighestScore()));

        } else{
            btn_SignOut.setText("Log Out");
            Log.d(TAG,"error");
        }
    }

    private void setWindowSize(){

        DisplayMetrics displayMetrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //displaying this activity will take 60% of the height and 80% of the width
        int wid = displayMetrics.widthPixels;
        int hig = displayMetrics.heightPixels;

        getWindow().isFloating();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        getWindow().setLayout((int)(wid*.8),(int)(hig*.65));

        HeaderClass headerClassInstance = new HeaderClass();
        headerClassInstance.setOppositeBackground(layout, getApplicationContext());
    }


    private void userSignOut(){

        btn_SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpButton.start();
                if (counter == 0){
                    Toast toast = Toast.makeText(getApplicationContext(),"Logging Out? Press it again!",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                    counter++;

                }else {
                    userClass.mAuth.signOut();
                    btn_SignOut.setText("Log In");
                    userClass.emptyUserFields();

                    if ( counter == 1) {
                        startActivity(new Intent(getApplicationContext(), LogInPage.class));
                        finish();
                    }
                }
            }
        });
    }

    private void assignVariables(){

        mpButton = MediaPlayer.create(this,R.raw.buttonpress);
        tv_UserName = findViewById(R.id.tvUserName);
        tv_HighestScore = findViewById(R.id.tvHighestScore);
        tv_TotalCoin = findViewById(R.id.tvTotalCoin);
        btn_SignOut = findViewById(R.id.btnSignOut);
        layout = findViewById(R.id.constraintLayout);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}