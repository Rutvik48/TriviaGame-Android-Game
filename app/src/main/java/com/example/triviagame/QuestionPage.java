package com.example.triviagame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuestionPage extends AppCompatActivity {

    private TextView tv_Question ;
    private Button btn_Option1,btn_Option2,btn_Option3,btn_Option4, tv_time, tv_ScorePoints;
    private String rightAnswer;
    private static int TOTAL_QUESTIONS;
    private static Boolean RANDOM_QUESTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page);

        setFullScreen();

        assignValues();
        setTotalQuestions();

        setQuestion();
        resetQuestionPage();

        tv_time.setText("Time");
        tv_ScorePoints.setText("Score /\n Points");
        //HomePage homePage = new HomePage();

        //homePage.setFullScreen();
    }

    public void setRandomQuestionTrue(){
        this.RANDOM_QUESTION = true;
    }


    private void setQuestion(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        //databaseAccess.close();
        databaseAccess.open();

        //String querry_result = databaseAccess.getAddress("");
        int randomID = getRandomNum(TOTAL_QUESTIONS);
        String []tempArray = databaseAccess.getAddress(randomID);;

        tv_Question.setText(tempArray[0]);
        rightAnswer = tempArray[1];

        btn_Option2.setText(rightAnswer);

        databaseAccess.close();
    }

    public void resetQuestionPage(){
        btn_Option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getRandomNum(TOTAL_QUESTIONS);
                startActivity(new Intent(getApplicationContext(),QuestionPage.class));
                finish();
            }
        });
    }

    public int getRandomNum(int range){
        //this wil return a random int from 1 to range
        return (new Random().nextInt(range+1));
    }

    public void assignValues(){
        tv_Question = findViewById(R.id.tvQuestion);
        btn_Option1 = findViewById(R.id.btnOption1);
        btn_Option2 = findViewById(R.id.btnOption2);
        btn_Option3 = findViewById(R.id.btnOption3);
        btn_Option4 = findViewById(R.id.btnOption4);
        //using "Back Home" and "User Info" buttons from top_menu_bar.xml
        tv_ScorePoints = findViewById(R.id.btnUserInfo);
        tv_time = findViewById(R.id.btnBackToHome);

    }
    public void setFullScreen(){

        //hides the title bar
        getSupportActionBar().hide();

        //this code makes the status bar transparent
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

    }

    private void setTotalQuestions(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        //databaseAccess.close();
        databaseAccess.open();

        //Toast.makeText(getApplicationContext(), Integer.toString(databaseAccess.totalQuestions()),Toast.LENGTH_LONG).show();
        this.TOTAL_QUESTIONS = databaseAccess.totalQuestions();
        databaseAccess.close();
    }

}
