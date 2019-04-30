package com.example.triviagame;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;


import java.util.Date;

@IgnoreExtraProperties
public class userClass {


    private String Email;
    private @ServerTimestamp Date timestamp;
    private int coin;
    private int highScore;
    private String userID;
    private String documentID;


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

    public String getEmail(){
        return Email;
    }
    public void setEmail(String email){
        this.Email = email;
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