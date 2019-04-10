package com.example.triviagame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.prefs.PreferenceChangeEvent;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;


public class HomePage extends AppCompatActivity {

    //variables to assign the button or text that user see on screen
    //NOTE: btn is for Button, tv for TextView
    private Button btn_GameStart, btn_BackHome, btn_UserInfo;
    private TextView tv_LogIn, tv_ChangeBackground;
    private ConstraintLayout layout;
    //private FirebaseAuth firebaseAuth;
    //to exit the app this condition will be used
    private Boolean exitCondition = false;
    private int backgroundNum = 0;
    private SharedPreferences backgroundPreferences;
    private SharedPreferences.Editor preferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //setContentView(R.layout.top_menu_bar);

        setFullScreen();

        // this will assign variables to button, text, etc from xml file
        assignVariables();

        backgroundPreferences = PreferenceManager.getDefaultSharedPreferences(HomePage.this);
        preferencesEditor = backgroundPreferences.edit();
        saveBackground(backgroundPreferences.getInt("Background", 0));

        //we do not need to show BackHome button on HomePage
        btn_BackHome.setVisibility(View.INVISIBLE);

        //set text programmatically rather than doing fix text using xml
        btn_GameStart.setText("Start Game");
        tv_LogIn.setText("Log in / Sign Up");


        tv_LogIn.setOnClickListener(tv_LogIn_Listner);
        btn_UserInfo.setOnClickListener(btn_UserInfo_Listner);
        btn_GameStart.setOnClickListener(btn_GameStart_Listner);
        tv_ChangeBackground.setOnClickListener(tv_ChangeBackground_Listner);


    }

    View.OnClickListener btn_UserInfo_Listner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(HomePage.this, PopUpWindow.class);
            startActivity(intent);
        }
    };

    View.OnClickListener btn_GameStart_Listner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(HomePage.this, CategoriesPage.class);
            startActivity(intent);
        }
    };


    View.OnClickListener tv_LogIn_Listner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(HomePage.this, LogInPage.class);
            startActivity(intent);
            finish();

        }
    };

    //change background of constraint
    View.OnClickListener tv_ChangeBackground_Listner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            backgroundPreferences = PreferenceManager.getDefaultSharedPreferences(HomePage.this);
            preferencesEditor = backgroundPreferences.edit();


            if (backgroundNum == 0){
                layout.setBackgroundResource(backgroundPreferences.getInt("BackgroundNum",0));
                saveBackground(R.drawable.background_gradient1);
                backgroundNum = 1;
            }else{
                layout.setBackgroundResource(R.drawable.background_gradient);
                backgroundNum = 0;
            }

            //change background of constraint
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (backgroundNum == 0){
                    layout.setBackgroundResource(backgroundPreferences.getInt("BackgroundNum",0));
                    saveBackground(R.drawable.background_gradient1);
                }else{
                    layout.setBackgroundResource(R.drawable.background_gradient);
                    backgroundNum = 0;
                }
            }else{
                tv_ChangeBackground.setVisibility(View.INVISIBLE);

            }*/
        }
    };

    private void saveBackground(int backNum){
        backgroundPreferences = PreferenceManager.getDefaultSharedPreferences(HomePage.this);
        preferencesEditor = backgroundPreferences.edit();

        preferencesEditor.putInt("BackgroundNum", backNum);
        preferencesEditor.commit();
    }
    public void setFullScreen(){

        //hides the title bar
        getSupportActionBar().hide();

        //this code makes the status bar transparent
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

    }

    private void assignVariables(){
        btn_GameStart = (Button)findViewById(R.id.btnGameStart);
        tv_LogIn = (TextView)findViewById(R.id.tvLogIn);
        btn_BackHome = (Button)findViewById(R.id.btnBackToHome);
        btn_UserInfo = (Button)findViewById(R.id.btnUserInfo);
        tv_ChangeBackground = (TextView)findViewById(R.id.tvChangeBackground);
        layout = (ConstraintLayout)findViewById(R.id.layoutHeader);
    }
    @Override
    public void onBackPressed() {
        if (exitCondition){
            finish();
            super.onBackPressed();
        }else{
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            exitCondition = true;
        }
    }
}
