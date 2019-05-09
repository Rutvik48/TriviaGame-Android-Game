package com.example.triviagame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;


public class LogInPage extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private Button btnsignUp, btnLogin,btn_BackHome, btn_UserInfo;
    private TextView btnReset;
    public Boolean userLogIn = false;
    private ConstraintLayout layout;

    private MediaPlayer mpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);


        setFullScreen();

        //variables will be assingned to buttons, textview, etc..
        assignVariables();
        btnLogInClicked();
        btnSignUpOrResetClicked();
        btnBackToHomeClicked();

        HeaderClass headerClassInstance = new HeaderClass();
        headerClassInstance.setBackground(layout, getApplicationContext());

        btn_UserInfo.setVisibility(View.INVISIBLE);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //check if user has an account
        if(auth.getCurrentUser() != null){
            userLogIn = true;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mpButton.stop();
        mpButton.release();
    }

    private void assignVariables(){

        mpButton = MediaPlayer.create(this,R.raw.buttonpress);
        mEmail = findViewById(R.id.etUserName);
        mPassword = findViewById(R.id.etPassword);
        progressBar = new ProgressBar(this);
        btnsignUp = findViewById(R.id.resetPasswordField);
        btnLogin = findViewById(R.id.btnLogIn);
        btnReset = findViewById(R.id.tvForgotPassword);
        btn_BackHome = findViewById(R.id.btnBackToHome);
        btn_UserInfo = findViewById(R.id.btnUserInfo);
        layout = findViewById(R.id.constraintLayout);
    }


    private void btnBackToHomeClicked(){

        btn_BackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInPage.this, HomePage.class);
                startActivity(intent);
                mpButton.start();
                finish();
            }
        });
    }

    private void btnSignUpOrResetClicked(){

        //open signup  activity
        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInPage.this, SignUpPage.class));
                finish();
                mpButton.start();
            }
        });

        //open reset activity
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInPage.this,ResetPasswordActivity.class));
                finish();
                mpButton.start();
            }
        });
    }

    private void btnLogInClicked(){
        mpButton.start();
        //login activity
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if(!validate(email,password)){
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LogInPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //If sign has failed, displaymeesage to user
                        //if succeeds auth state listner will be notified and logic handle the user
                        progressBar.setVisibility(View.GONE);
                        userClass user = new userClass(getApplicationContext());
                        user.userJustLogedIn = true;
                        user.updateUI();
                        //there was an error
                        if(!task.isSuccessful()){
                            Toast.makeText(LogInPage.this,"Authentication failed", Toast.LENGTH_LONG ).show();
                        }
                        else{
                            Intent intent = new Intent(LogInPage.this,HomePage.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }
    /*
     * validate :boolean
     *
     * This function checks if the email and password fields are empty or
     * the length is wrong. Also outputs a error message
     *
     * returns if valid is right
     * */
    public boolean validate(String email, String password){
        boolean valid = true;

        //get contains of the fields


        //check if email field is empty or email is wrong format
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("enter a valid email address");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mPassword.setError(null);
        }
        return valid;
    }

    public void setFullScreen() {

        //hides the title bar
        getSupportActionBar().hide();

        //this code makes the status bar transparent
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
    }

    @Override
    public void onBackPressed() {
        //back button will not do anything
    }

/*
    private EditText userName, userPassword;
    private TextView logInInfo;
    private Button loginBtn, signupBtn, btn_BackHome, btn_UserInfo;
    //private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);
        //firebaseAuth = FirebaseAuth.getInstance();
        setFullScreen();
        initialize();
        loginBtn.setOnClickListener(btn_LogInListner);
        signupBtn.setOnClickListener(btn_SignUpListner);
        btn_BackHome.setOnClickListener(btn_BackHomeListner);
        btn_UserInfo.setOnClickListener(btn_UserInfoListner);
        btn_UserInfo.setVisibility(View.INVISIBLE);
    }
    View.OnClickListener btn_LogInListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            authenticate(userName.getText().toString().trim(), userPassword.getText().toString().trim());
        }
    };
    View.OnClickListener btn_SignUpListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LogInPage.this, SignUpPage.class);
            startActivity(intent);
            finish();
        }
    };
    View.OnClickListener btn_UserInfoListner= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //user info will be shown
            Toast.makeText(LogInPage.this, "User Info", Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener btn_BackHomeListner= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LogInPage.this, HomePage.class);
            startActivity(intent);
            finish();
        }
    };
    private void authenticate(String name, String pswd) {
        /*firebaseAuth.signInWithEmailAndPassword(name,pswd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithEmail:success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Intent intent = new Intent(LogInPage.this, CategoriesPage.class);
                    //updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.getException());
                    Toast.makeText(LogInPage.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }
                // ...
            }
        });*/


        /*if((name.equals(pswd))&& (!"".equals(name))){
            Intent intent = new Intent(LogInPage.this, CategoriesPage.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(LogInPage.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
        }*/
}
/*
    public void setFullScreen() {
        //hides the title bar
        getSupportActionBar().hide();
        //this code makes the status bar transparent
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }
    private void initialize(){
        userName = (EditText)findViewById(R.id.etUserName);
        userPassword = (EditText)findViewById(R.id.etPassword);
        loginBtn = (Button)findViewById(R.id.btnLogIn);
        signupBtn = (Button)findViewById(R.id.btnSignUp);
        btn_BackHome = (Button)findViewById(R.id.btnBackToHome);
        btn_UserInfo = (Button)findViewById(R.id.btnUserInfo);
    }
    @Override
    public void onBackPressed() {
        //back button will not do anything
    }
}*/