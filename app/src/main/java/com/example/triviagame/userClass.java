package com.example.triviagame;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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
        DocumentReference docRef = db.collection("TriviaUser").document(documentID);

        docRef.update("Coin", currentUser.getCoin());
    }


    //show user highscore and email
    public Map updateUI(FirebaseAuth mAuth){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("TriviaUser").document(mAuth.getCurrentUser().getEmail());
        DocumentSnapshot document;
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        newMap = document.getData();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return newMap;
        //CollectionReference userRef = db.collection("TriviaUser");

        /*Query userQuery = userRef.whereEqualTo("uid",mUser.getUid());
        Log.d("Test1", "Document ID: ");
        userQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot document: task.getResult()){

                        Log.d("Test", "Document ID: " + document.getId());
                        userClass user = document.toObject(userClass.class);
                        //superupdateUI(user);
                        user.setCurrentUser(user);
                        superupdateUI(user);
                        user.saveDocumentID(document.getId());
                        // Log.d(TAG,"display 3");
                    }
                }else{

                }
            }
        });*/
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