package com.example.triviagame;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.triviagame.PopUpWindow.TAG;

@IgnoreExtraProperties
public class userClass {


    private String Email;
    private @ServerTimestamp Date timestamp;
    private int coin;
    private int highScore;
    private String userID;
    private static String documentID;
    private static userClass currentUser;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private  Map newMap = new HashMap();
    private static String userEmail, uID;
    private static int highestScore, coins;

    public userClass(){
    }

    public userClass(String email,Date timestamp, int coin, int highScore, String userID,String documentID){

        this.Email = email;
        this.timestamp = timestamp;
        this.coin = coin;
        this.highScore = highScore;
        this.userID = userID;
        this.documentID = documentID;

    }

    public void setUserEmail(String email){
        userEmail = email;
    }

    public void setuID(String id){
        uID = id;
    }

    public void setHighestScore(String score){
        highestScore = Integer.parseInt(score);
    }

    public void setCoins(String coin){
        coins = Integer.parseInt(coin);
    }

    public String getUserEmail(){
        return userEmail;
    }

    public String getuID(){
        return uID;
    }

    public int getHighestScore(){
        return highestScore;
    }

    public int getCoins(){
        return coins;
    }

    public String getEmail(){
        return Email;
    }
    public void setEmail(String email){
        this.Email = email;
    }

    public void setCurrentUser(userClass user){
        currentUser = user;
    }

    public userClass getCurrentUser(){
        return currentUser;
    }

    public void updateCoins(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("TriviaUser").document(getUserEmail());

        docRef.update("Coin", getCoins());
    }
    public void updateHighestScore(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("TriviaUser").document(getUserEmail());

        docRef.update("Score", getHighestScore());
    }



    public void saveDocumentID(String docId){
        documentID = docId;
    }

    public int getCoin(){
        return coin;
    }
    public void setCoin(int coin){
        this.coin = coin;
    }

    //show user highscore and email
    public void updateUI() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Log.d(TAG, "DocumentSnapshot data: " + mAuth);
        DocumentReference docRef = db.collection("TriviaUser").document(mAuth.getCurrentUser().getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map newMap = new HashMap();
                        newMap = document.getData();
                        Log.d(TAG, "NewMap = " + newMap.get("uid"));
                        setUserEmail(newMap.get("Email").toString());
                        setuID(newMap.get("uid").toString());
                        setCoins(newMap.get("Coin").toString());
                        setHighestScore(newMap.get("Score").toString());
                        //tv_TotalCoin.setText(newMap.get("uid").toString());
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public int getHighScore(){
        return highScore;
    }
    public void setHighScore(int highScore){
        this.highScore = highScore;
    }

    public String getUserID(){
        return userID;
    }
    public  void setUserID(String userID){
        this.userID = userID;
    }

    public String getDocumentID(){return documentID;}
    public void setDocumentID(){this.documentID = userID;}

    public Date getTimestamp(){return timestamp;}
}