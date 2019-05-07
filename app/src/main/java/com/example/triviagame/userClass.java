package com.example.triviagame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.IgnoreExtraProperties;
import java.util.HashMap;
import java.util.Map;

import static com.example.triviagame.PopUpWindow.TAG;

@IgnoreExtraProperties
public class userClass {

    public FirebaseAuth mAuth;
    public  FirebaseFirestore db;
    private  Map newMap = new HashMap();
    private static String userEmail, uID;
    private static int highestScore, coins;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefEditor;
    public final String FIRESTORE_USER_EMAIL = "Email";
    public final String FIRESTORE_USER_ID = "UserID";
    public final String FIRESTORE_COINS = "Coins";
    public final String FIRESTORE_HEIGHEST_SCORE = "Score";
    public static boolean userJustLogedIn;

    public userClass (){
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }


    public userClass(Context context){
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        prefEditor = sharedPreferences.edit();
    }


    public void setUpSharedPreferences(int prefBackground, Context context){

        prefEditor.putInt("Background", prefBackground);
        prefEditor.apply();

    }

    public void assignUserFields(String email, String userID){

        setUserEmail(email);
        setuID(userID);
        setCoins(0);
        setHighestScore(0);
    }

    public void emptyUserFields(){
        assignUserFields(null, null);
    }

    public void setUserEmail(String email){

        userEmail = email;
        prefEditor.putString(FIRESTORE_USER_EMAIL, email);
        prefEditor.apply();

    }

    public void setuID(String id){

        uID = id;
        prefEditor.putString(FIRESTORE_USER_ID, id);
        prefEditor.apply();
    }

    public void setHighestScore(int score){
        highestScore = score;
        prefEditor.putInt(FIRESTORE_HEIGHEST_SCORE, score);
        prefEditor.apply();
    }

    public void setCoins(int coin){
        coins = coin;
        prefEditor.putInt(FIRESTORE_COINS, coin);
        prefEditor.apply();
    }

    public String getUserEmail(){
        return sharedPreferences.getString(FIRESTORE_USER_EMAIL, "");
    }

    public String getuID(){
        return sharedPreferences.getString(FIRESTORE_USER_ID, "");
    }

    public int getHighestScore(){
        return sharedPreferences.getInt(FIRESTORE_HEIGHEST_SCORE, 0);
    }

    public int getCoins(){
        return sharedPreferences.getInt(FIRESTORE_COINS, 0);
    }


    public void updateCoins(){

        updateData(FIRESTORE_COINS, getCoins());
    }
    public void updateHighestScore(){

        updateData(FIRESTORE_HEIGHEST_SCORE, getHighestScore());
    }

    private void updateData(String dataName, int data){

        if (mAuth.getCurrentUser() != null) {
            DocumentReference docRef = db.collection("TriviaUser").document(getUserEmail());
            docRef.update(dataName, data);
        }

    }

    //show user highscore and email
    public void updateUI() {

        DocumentReference docRef = db.collection("TriviaUser").document(mAuth.getCurrentUser().getEmail());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        newMap = document.getData();
                        if(userJustLogedIn){
                            userJustLogedIn = false;
                            Log.d("userJustLoggedIn State:", "userJustLoggedIn State: True");
                            userLoggedIn();

                        }else{
                            Log.d("userJustLoggedIn State:", "userJustLoggedIn State: False");
                            getDataFromMap();
                        }
                    } else {
                        Log.d(TAG, "No such document found");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void userLoggedIn(){

        setUserEmail(newMap.get(FIRESTORE_USER_EMAIL).toString());
        setuID(newMap.get(FIRESTORE_USER_ID).toString());
        setCoins(Integer.parseInt(newMap.get(FIRESTORE_COINS).toString()));
        setHighestScore(Integer.parseInt(newMap.get(FIRESTORE_HEIGHEST_SCORE).toString()));

    }


    private void getDataFromMap(){

        setUserEmail(newMap.get(FIRESTORE_USER_EMAIL).toString());
        setuID(newMap.get(FIRESTORE_USER_ID).toString());

        //get coins stored on firebase
        int coins = Integer.parseInt(newMap.get(FIRESTORE_COINS).toString());

        /*
        * checks if current user coins are higher then firebase coins
        * current user coins will be higher when user played the game offline
        * the if statement will update it when user connects to internet
        * this also ensure that, someone can't just increase numbers of coins on database,
         * if someone does else statement will change it back to right number of coins */
        if(getCoins() > coins || userJustLogedIn) {
            setCoins(coins);
            updateCoins();
        }else
            updateCoins();

        int score = Integer.parseInt(newMap.get(FIRESTORE_HEIGHEST_SCORE).toString());
        if(getHighestScore() > score)
            setHighestScore(score);
        else
            updateHighestScore();
    }
}