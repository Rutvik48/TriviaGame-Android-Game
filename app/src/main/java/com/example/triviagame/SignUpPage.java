package com.example.triviagame;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

public class SignUpPage extends AppCompatActivity {

    private EditText userPassword, userEmail;
    private Button signUp_btn, btn_BackHome, btn_UserInfo;
    private TextView alreadyLoggedIn_tv;
    private ProgressDialog progressDialog;
    // FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        setFullScreen();

        initialize();
        //firebaseAuth = FirebaseAuth.getInstance();

        signUp_btn.setOnClickListener(signUp_btn_listner);
        alreadyLoggedIn_tv.setOnClickListener(alrdLgnIn_listner);
        btn_BackHome.setOnClickListener(btn_BackHomeListner);
        btn_UserInfo.setOnClickListener(btn_UserInfoListner);

        btn_UserInfo.setVisibility(View.INVISIBLE);
    }

    View.OnClickListener btn_UserInfoListner= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //user info will be shown
            Toast.makeText(SignUpPage.this, "User Info", Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener btn_BackHomeListner= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SignUpPage.this, HomePage.class);
            startActivity(intent);
            finish();
        }
    };

    View.OnClickListener signUp_btn_listner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(SignUpPage.this, "Before Break", Toast.LENGTH_SHORT).show();
            if(validateInput()){
                Toast.makeText(SignUpPage.this,"Proccesing...",Toast.LENGTH_SHORT).show();
                String email=userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                addAccount(email, password);
            }
        }
    };


    View.OnClickListener alrdLgnIn_listner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SignUpPage.this, LogInPage.class);
            startActivity(intent);
            finish();
        }
    };

    public void setFullScreen() {

        //hides the title bar
        getSupportActionBar().hide();

        //this code makes the status bar transparent
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }


    private void addAccount(String email, String pswd) {
        Toast.makeText(SignUpPage.this, pswd, Toast.LENGTH_SHORT).show();
        //progressDialog.setMessage("You can subscribe to my channel until you are verified!");
        //progressDialog.show();

        /*firebaseAuth.createUserWithEmailAndPassword(email, pswd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //updateUI(user);
                            finish();
                            Intent intent = new Intent(NewAccountPage.this, HomePage.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(NewAccountPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });*/
    }


    private Boolean validateInput(){
        Boolean result = false;

        if(userPassword.getText().toString().isEmpty()||
                userEmail.getText().toString().isEmpty()){
            Toast.makeText(SignUpPage.this,"Invalid User Input",Toast.LENGTH_SHORT).show();
        }else
            result = true;

        return result;
    }

    private void initialize() {

        //userName = (EditText)findViewById(R.id.etUserName);
        userPassword = (EditText)findViewById(R.id.etPassword);
        userEmail = (EditText)findViewById(R.id.etEmailID);
        signUp_btn = (Button)findViewById(R.id.btnSignUp);
        alreadyLoggedIn_tv = (TextView)findViewById(R.id.tvAlreadyLoggedIn);
        btn_BackHome = (Button)findViewById(R.id.btnBackToHome);
        btn_UserInfo = (Button)findViewById(R.id.btnUserInfo);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
