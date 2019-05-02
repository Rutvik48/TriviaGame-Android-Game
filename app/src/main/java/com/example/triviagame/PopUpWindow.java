package com.example.triviagame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class PopUpWindow extends Activity {

    //TAG to handle errors
    public static final String TAG = "AddUserData";

    //variables
    private PopUpWindow popUpWindow;
    private LayoutInflater layoutInflater;
    private TextView tv_UserName;
    private TextView tv_HighestScore, tv_TotalCoin;
    private Button btn_SignOut;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private boolean userFirestoreCheck;
    private DocumentSnapshot mLastQueriedDocument;
    private int counter = 0;
    private ConstraintLayout layout;
    private userClass userClass = new userClass();
    private Map dataMap;

    /*
     * The fireStroe query takes some time to complete so i'm thinking of adding a
     * progress bar to show the user that the pop up is loading
     *
     * */
    private ProgressBar mProgress;

    private userClass mUserClass = new userClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);

        DisplayMetrics displayMetrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        assignVariables();

        userSignOut();
        //displaying this activity will take 60% of the height and 80% of the width
        int wid = displayMetrics.widthPixels;
        int hig = displayMetrics.heightPixels;

        getWindow().isFloating();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        getWindow().setLayout((int)(wid*.8),(int)(hig*.5));

        HeaderClass headerClassInstance = new HeaderClass();
        headerClassInstance.setOppositeBackground(layout, getApplicationContext());

        //check if user exits
        if(mAuth.getUid() != null) {

            tv_UserName.setText("Email: "+userClass.getUserEmail());
            Log.d(TAG, "Coins2: " + userClass.getCoins());
            tv_TotalCoin.setText("Coins: "+Integer.toString(userClass.getCoins()));
            tv_HighestScore.setText("Highest Score: "+ Integer.toString(userClass.getHighestScore()));
            //updateUI();
            Log.d(TAG,"error");
        } else{
            btn_SignOut.setText("Log Out");
            Log.d(TAG,"error");
        }

    }


    private void userSignOut(){

        btn_SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getUid() != null){
                    mAuth.signOut();
                    counter++;
                }else {
                    btn_SignOut.setText("Log In");

                    if (btn_SignOut.getText() == "Log In" && counter == 1) {
                        startActivity(new Intent(getApplicationContext(), LogInPage.class));
                        finish();
                    }
                }
            }
        });
    }

    private void assignVariables(){
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
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

/*package com.example.triviagame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PopUpWindow extends Activity {

    //TAG to handle errors
    public static final String TAG = "AddUserData";

    //variables
    private PopUpWindow popUpWindow;
    private LayoutInflater layoutInflater;
    private TextView userNameText;
    private TextView userHighscoreText, tv_TotalCoin;
    private Button btn_SignOut;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private boolean userFirestoreCheck;
    private DocumentSnapshot mLastQueriedDocument;
    private int counter = 0;

    /*
     * The fireStroe query takes some time to complete so i'm thinking of adding a
     * progress bar to show the user that the pop up is loading
     *
     * */
    /*private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);

        //this gets the dimension of the screen
        DisplayMetrics displayMetrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        assignVariables();

        userSignOut();
        //displaying this activity will take 60% of the height and 80% of the width
        int wid = displayMetrics.widthPixels;
        int hig = displayMetrics.heightPixels;

        getWindow().isFloating();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout((int)(wid*.8),(int)(hig*.5));


        //check if user exits
        if(mAuth.getUid() != null){


            checkIfUserExistsInFireStore();

            //if user exits
            if(userFirestoreCheck != true){
                //addUserToFireStore();
                Log.d(TAG,"added user to database");

            }else{
                //updateUI();
                Log.d(TAG,"display ");

            }
            Toast.makeText(getApplicationContext(),mAuth.getUid(),Toast.LENGTH_LONG);

        }else{
            Log.d(TAG,"error");
            Toast.makeText(getApplicationContext(),"User Not Found",Toast.LENGTH_LONG);
        }

    }

    private void userSignOut(){

        btn_SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                btn_SignOut.setText("Log In");

                if(btn_SignOut.getText() == "Log In" && counter == 1){
                    startActivity(new Intent(getApplicationContext(), LogInPage.class));
                }else{
                    counter++;
                }
            }
        });
    }

    public void superupdateUI(userClass user){

        int temp = user.getHighScore();
        Log.d(TAG,"Inside SuperUpdateUI ");
        userNameText.setText("Email: "+user.getEmail());
        userHighscoreText.setText("Highest Score: " + String.valueOf(user.getHighScore()));
        tv_TotalCoin.setText("Coins: "+ user.getCoin());

    }

    //show user highscore and email
    public void updateUI(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference userRef = db.collection("TriviaUser");


        Query userQuery = userRef.whereEqualTo("userID",FirebaseAuth.getInstance().getCurrentUser().getUid());

        userQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d(TAG,"Inside if updateUI ");

                    for(QueryDocumentSnapshot document: task.getResult()){
                        userClass user = document.toObject(userClass.class);
                        superupdateUI(user);
                        Log.d(TAG,"Inside if>for updateUI ");
                    }

                    Log.d(TAG,"Inside if at end updateUI ");
                    Toast.makeText(getApplicationContext(),"User Not Found",Toast.LENGTH_LONG);
                }else{

                }
            }
        });
    }



    //query and finds the document according to values
    public void checkIfUserExistsInFireStore(){
        final boolean[] temp = new boolean[1];

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference yourCollRef = rootRef.collection("TriviaUser");

        Query query = yourCollRef.whereEqualTo("userID", mUser.getUid());

        boolean condition = query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    Log.d(TAG,"yo1");
                    Log.d(TAG,"yo2");

                    //addUserToFireStore();
                    updateUI();
                    userFirestoreCheck = true;

                } else {
                    Log.d(TAG,"yo3");
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    userFirestoreCheck = false;
                }
            }
        }).isSuccessful();

        if(condition){
            Log.d(TAG,"True Condition ");
        }
        else
            Log.d(TAG,"False Condition ");
        Log.d(TAG,"yo4");
    }


    public void addUserToFireStore(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference newUserRef = db.collection("TriviaUser").document();
        userClass user = new userClass();

        user.setEmail(mUser.getEmail());
        user.setUserID(mUser.getUid());
        user.setCoin("0");
        user.setHighScore(0);

        newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    updateUI();
                    Log.d(TAG, "added user");

                }else{

                    Log.d(TAG,"error adding user");
                }
            }
        });

    }

    private void assignVariables(){
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userNameText = findViewById(R.id.tvUserName);
        userHighscoreText = findViewById(R.id.tvHighestScore);
        tv_TotalCoin = findViewById(R.id.tvTotalCoin);
        btn_SignOut = findViewById(R.id.btnSignOut);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}*/