package com.example.triviagame;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {


    private EditText inputEmail;
    private FirebaseAuth auth;
    private ProgressBar mProgressBar;
    private Button Passwordbtn, btn_Home, btn_UserInfo;
    private ConstraintLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        setFullScreen();

        inputEmail = findViewById(R.id.resetEmailField);
        Passwordbtn = findViewById(R.id.resetPasswordField);
        btn_Home = findViewById(R.id.btnBackToHome);
        btn_UserInfo = findViewById(R.id.btnUserInfo);
        layout = findViewById(R.id.layout);

        HeaderClass headerClassInstance = new HeaderClass();
        headerClassInstance.setBackground(layout, getApplicationContext());

        btn_UserInfo.setVisibility(View.GONE);

        mProgressBar = new ProgressBar(this);


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();



        //send user back to login page
        btn_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomePage.class));
            }
        });





        //when user clicks on reset password
        Passwordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();

                //check if empty and in email format @email.com
                if(TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    inputEmail.setError("enter a valid email address");
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);

                //firebase password function
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(ResetPasswordActivity.this,"We have sent you instruction to reset your password",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ResetPasswordActivity.this,"Failed to send email",Toast.LENGTH_LONG).show();
                        }
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

    }
    public void setFullScreen(){

        //hides the title bar
        getSupportActionBar().hide();

        //this code makes the status bar transparent
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onBackPressed() {
    }
}
