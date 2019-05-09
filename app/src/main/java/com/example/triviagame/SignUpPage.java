package com.example.triviagame;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUpPage extends AppCompatActivity {

    //define a constant for your tag in MainActivity
    private static final String TAG = "LoginActivity";
    private Button buttonRegister, btn_BackHome, btn_UserInfo;
    private EditText editTextEmail, editTextPassword;
    private ProgressDialog progressDialog;
    private TextView alreadyLoggedIn_tv;
    private FirebaseAuth firebaseAuth;
    private ConstraintLayout layout;


    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //validate whats entered on the fields
        if(!validate(email,password)){
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        progressDialog.dismiss();
        /*
        Firebase
        * Create a new createAccount method which takes in an email address and password,
        * validates them and then creates a new user with the createUserWithEmailAndPassword method.
        *
        * */
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpPage.this,"Registered Successfully", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    createUserFieldOnFireStore(user);
                    Intent intent = new Intent(SignUpPage.this,HomePage.class);
                    startActivity(intent);
                    finish();
                }else{
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(SignUpPage.this,"Could not register", Toast.LENGTH_SHORT).show();

                    //use this to go to different activity
                    //updateUI(null);
                }
            }


        });

    }

    private void btnBackToHomeClicked(){

        btn_BackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPage.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }
//update the ui or send user to different activity

    public void createUserFieldOnFireStore(FirebaseUser user){

        Log.d(TAG, "added user1");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        userClass userClass = new userClass(getApplicationContext());
        CollectionReference temp = db.collection("TriviaUser");

        Map<String, Object> dataTemp = new HashMap<>();
        String email = user.getEmail();
        dataTemp.put(userClass.FIRESTORE_USER_EMAIL,email);
        String uid = user.getUid();
        dataTemp.put(userClass.FIRESTORE_USER_ID, uid);
        dataTemp.put(userClass.FIRESTORE_COINS, 10);
        dataTemp.put(userClass.FIRESTORE_HEIGHEST_SCORE, 0);
        temp.document(email).set(dataTemp);


        userClass.assignUserFields(email,uid);
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
            editTextEmail.setError("enter a valid email address");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            editTextPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }
        return valid;
    }

    private void initialize() {

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        buttonRegister = findViewById(R.id.resetPasswordField);
        editTextEmail = findViewById(R.id.etEmailID);
        editTextPassword = findViewById(R.id.etPassword);
        alreadyLoggedIn_tv = findViewById(R.id.tvAlreadyLoggedIn);
        btn_BackHome = findViewById(R.id.btnBackToHome);
        btn_UserInfo = findViewById(R.id.btnUserInfo);
        layout = findViewById(R.id.constraintLayout);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        setFullScreen();
        initialize();

        btnBackToHomeClicked();

        HeaderClass headerClassInstance = new HeaderClass();
        headerClassInstance.setBackground(layout, getApplicationContext());

        btn_UserInfo.setVisibility(View.INVISIBLE);


        //button press registers user
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == buttonRegister){
                    registerUser();
                }
            }
        });

        alreadyLoggedIn_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpPage.this, LogInPage.class));
                finish();
            }
        });

    }

    //this disables the back button
    @Override
    public void onBackPressed() {
        finish();
    }
}