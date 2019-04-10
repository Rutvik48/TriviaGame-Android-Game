package com.example.triviagame;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class LogInPage extends AppCompatActivity {

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
}
