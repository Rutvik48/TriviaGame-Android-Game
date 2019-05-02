package com.example.triviagame;


import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;



public class HomePage extends AppCompatActivity {

    //variables to assign the button or text that user see on screen
    //NOTE: btn is for Button, tv for TextView
    private Button btn_GameStart, btn_BackHome, btn_UserInfo;
    public TextView tv_LogIn, tv_ChangeBackground, tv_GameName;
    private ConstraintLayout layout;
    //to exit the app this condition will be used
    private Boolean exitCondition = false;

    private FirebaseAuth auth;

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

        //this loads information from FireStore Database and adds it to userClass variables
        userClass userClass = new userClass();
        userClass.updateUI();

    }

    public void checkUser(){
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();


        //this checks if any user is logged in or not
        if (auth.getCurrentUser() != null){
            //if user is looged in hide the "log in/ Sign up" text
            tv_LogIn.setVisibility(View.INVISIBLE);
        }else{
            //when there is no user logged in, hide the UserInfo text
            btn_UserInfo.setVisibility(View.GONE);
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
                startActivity(new Intent(getApplicationContext(), PopUpWindow.class));
            }
        });

        btn_GameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CategoriesPage.class));
                finish();
            }
        });

        tv_LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

    }

    //function to assign buttons and texts to java variables
    private void assignVariables(){

        btn_GameStart = findViewById(R.id.btnGameStart);
        tv_LogIn = findViewById(R.id.tvLogIn);
        btn_BackHome = findViewById(R.id.btnBackToHome);
        btn_UserInfo = findViewById(R.id.btnUserInfo);
        tv_ChangeBackground = findViewById(R.id.tvChangeBackground);
        layout = findViewById(R.id.layoutHeader);
        tv_GameName = findViewById(R.id.tvGameName);
    }

    //asks user to click twice on back button to exit the app
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
