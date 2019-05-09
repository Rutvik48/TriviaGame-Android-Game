package com.example.triviagame;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuestionPage extends AppCompatActivity {

    private TextView tv_Question, tv_Timer, tv_Category, tv_SkipQuestion;
    private Button btn_Option1,btn_Option2,btn_Option3,btn_Option4, btn_ScorePoints, btn_BackToHome, btn_NextQuestion;
    private String rightAnswer;
    private int currentPoints = 30;
    private int currentCoins;
    private static int TOTAL_QUESTIONS;
    private static boolean RANDOM_QUESTION;
    private ConstraintLayout layout;
    private boolean timerRunning, isAnswerPicked, appForegroundStatus, timesUp;
    private CountDownTimer countDownTimer;
    private long timeLeftInMills = 45000;
    //will be used to return id answer was right or wrong
    boolean homeCondition;
    //category in order of 1.Science & Nature, 2.Science: Computers, 3.General Knowledge
    // 4.Geography, 5.Sport, 6.Vehicle, 7.Celebrities, 8.History
    private int []numberOfQestionByCategory = {139,80,141,142,69,34,25,123};
    private int []categoryQuesStartAt = {1,140,220,361,503,572,606,631};
    private int []selectedCategories;
    private int numberOfCategories = 8;
    private CategoriesPage categoriesPage = new CategoriesPage();
    private userClass user;
    private MediaPlayer mpWrong,mpCorrect,mpMusic;
    HeaderClass headerClassInstance = new HeaderClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page);

        setFullScreen();

        assignValues();
        setTotalQuestions();

        startClock();

        btn_BackToHome.setText("Back To\n Home");
        btn_ScorePoints.setText(Integer.toString(currentPoints));

        headerClassInstance.setBackground(layout, getApplicationContext());

        user = new userClass(getApplicationContext());

        clickedOption();

        nextQuestion();

        backToHome();

        skipQuestion();


    }


    //this function is to check if user can skip a question
    private void skipQuestion(){

        currentCoins = user.getCoins();

        if (currentCoins <= 0){
            tv_SkipQuestion.setText("0");
            tv_SkipQuestion.setTypeface(Typeface.SANS_SERIF, Typeface.ITALIC);
        }else
            tv_SkipQuestion.setText(Integer.toString(currentCoins));

        //this stores the number of coins user has
        int currentCoin = user.getCoins();
        Log.d("Number of coins ", Integer.toString(user.getCoins()));

        tv_SkipQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this stores the number of coins user has
                int currentCoin = user.getCoins();

                //if user doesn't have at lease one coin, user can't skip a question
                if(currentCoin >0){
                    currentCoin = currentCoin-1;
                    user.setCoins(currentCoin);
                    user.updateCoins();
                    showNextQuestion();
                    tv_SkipQuestion.setText(Integer.toString(currentCoin));
                }else{
                    tv_SkipQuestion.setText("No Coins");
                    Toast.makeText(getApplicationContext(),"Earn more coins to skip a question", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void backToHome(){

        btn_BackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                categoriesPage = null;
                if(homeCondition){
                    stopTimer();
                    startActivity(new Intent(getApplicationContext(),HomePage.class));
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(),"Press again to go to Home.", Toast.LENGTH_LONG).show();
                    homeCondition = true;
                }
            }
        });

    }

    private void addPoint(){
        //increase points by 10
        currentPoints += 10;
        updateScoreText(currentPoints);
    }


    private void deductPoint(){

        //deduct points by 5
        currentPoints -= 5;

        if(currentPoints < 5)
            endGame();
        else
            updateScoreText(currentPoints);
    }

    private void updateScoreText(int score){
        String strScore = Integer.toString(score);
        btn_ScorePoints.setText(strScore+ "    ");
    }

    public void setRandomQuestionTrue(){
        RANDOM_QUESTION = true;
    }

    public void setRandomQuestionFalse(){
        RANDOM_QUESTION = false;
    }

    private void setTotalQuestions(){

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());

        databaseAccess.open();

        TOTAL_QUESTIONS = databaseAccess.totalQuestions();
        databaseAccess.close();
    }


    public void getQuestion(){

        hideNextQuestionBtn();
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());

        databaseAccess.open();

        if(RANDOM_QUESTION){
            int randomID = getRandomNum(TOTAL_QUESTIONS);
            String []tempArray = databaseAccess.getData(randomID);

            resetQuestion(tempArray);
        }else{

            //this returns number from 0 to number of selected categories -1
            //ex. if user selects 4 categories, this returns a random number from 0 to 3
            int randCategory = getRandomNum((categoriesPage.selectedCategories.length)-1);

            //randCategory equals to ID number stored at random number returned by getRandomNum(...
            randCategory = categoriesPage.selectedCategories[randCategory];

            //range equals to number of questions available for selected category
            int range = numberOfQestionByCategory[randCategory-1];

            int randomID = getRandomNum(range);

            randomID += categoryQuesStartAt[randCategory-1];

            String []tempArray = databaseAccess.getData(randomID);

            resetQuestion(tempArray);
        }

        databaseAccess.close();

    }

    private String[] randomizeOptions(String[] anArray){

        boolean loopCondition = true;
        String []optionsArray = new String[4];
        Random random = new Random();
        int counter = 0;

        int rand = random.nextInt(4);
        for (int i = 0; i<4; i++){
            optionsArray[i] = anArray[rand%4];
            rand++;
        }

        return optionsArray;
    }

    private void hideNextQuestionBtn(){

        //by making this boolean false user can select an option
        isAnswerPicked = false;
        btn_NextQuestion.setVisibility(View.GONE);
    }

    private void showNextQuestionBtn(){

        //by making this boolean true user cannot select any option anymore
        isAnswerPicked = true;

        btn_NextQuestion.setVisibility(View.VISIBLE);
    }

    public void resetQuestion(String[] array){

        tv_Question.setText(array[0]);
        tv_Category.setText(array[6]);
        rightAnswer = array[5].trim();


        String tempArray[] = new String[4];
        for(int i = 0; i<4;i++)
            tempArray[i] = array[i+1];
        Log.d("TempArrayBefore Func",tempArray[0]);

        tempArray = randomizeOptions(tempArray);
        Log.d("TempArrayAfter Func",tempArray[0]);
        btn_Option1.setText(tempArray[0].trim());
        btn_Option2.setText(tempArray[1].trim());
        btn_Option3.setText(tempArray[2].trim());
        btn_Option4.setText(tempArray[3].trim());
    }

    private void clickedOption(){

        if(!isAnswerPicked){
            btn_Option1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(btn_Option1);

                }
            });

            btn_Option2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checkAnswer(btn_Option2);
                }
            });

            btn_Option3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checkAnswer(btn_Option3);
                }
            });

            btn_Option4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checkAnswer(btn_Option4);
                }
            });
        }
    }

    private void checkAnswer(Button clickedBtn){

        if(!isAnswerPicked){
            stopTimer();

            showNextQuestionBtn();

            if (rightAnswer.equals(clickedBtn.getText())){
                clickedBtn.setTextColor(Color.GREEN);
                addPoint();
                mpCorrect.start();
            }else{
                clickedBtn.setTextColor(Color.RED);
                deductPoint();
                showRightAnswer();
                mpWrong.start();
            }

        }
    }

    private void showRightAnswer(){

        if (rightAnswer.equals(btn_Option1.getText().toString().trim()))
            btn_Option1.setTextColor(Color.GREEN);
        else if (rightAnswer.equals(btn_Option2.getText().toString().trim()))
            btn_Option2.setTextColor(Color.GREEN);
        else if (rightAnswer.equals(btn_Option3.getText().toString().trim()))
            btn_Option3.setTextColor(Color.GREEN);
        else if (rightAnswer.equals(btn_Option4.getText().toString().trim()))
            btn_Option4.setTextColor(Color.GREEN);

    }

    private void nextQuestion(){

        btn_NextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextQuestion();
            }
        });
    }

    private void showNextQuestion(){
        getQuestion();
        resetOptionColor();
        startTimer();
    }

    private void resetOptionColor(){

        btn_Option1.setTextColor(Color.BLACK);
        btn_Option2.setTextColor(Color.BLACK);
        btn_Option3.setTextColor(Color.BLACK);
        btn_Option4.setTextColor(Color.BLACK);
    }

    public  void sleep(){
        SystemClock.sleep(1000);
    }

    public int getRandomNum(int range){
        //this wil return a random int from 1 to range
        return (new Random().nextInt(range+1));
    }

    public void assignValues(){
        mpMusic = MediaPlayer.create(this,R.raw.goinghigher);
        mpCorrect = MediaPlayer.create(this,R.raw.correct);
        mpWrong = MediaPlayer.create(this,R.raw.wronganswer);
        tv_Question = findViewById(R.id.tvQuestion);
        tv_Timer = findViewById(R.id.tvTimer);
        tv_SkipQuestion = findViewById(R.id.tvSkipQuestion);
        tv_Category = findViewById(R.id.tvCategory);
        btn_Option1 = findViewById(R.id.btnOption1);
        btn_Option2 = findViewById(R.id.btnOption2);
        btn_Option3 = findViewById(R.id.btnOption3);
        btn_Option4 = findViewById(R.id.btnOption4);
        btn_NextQuestion = findViewById(R.id.btnNextQuestion);

        //using "Back Home" and "User Info" buttons from top_menu_bar.xml
        btn_ScorePoints = findViewById(R.id.btnUserInfo);
        btn_BackToHome = findViewById(R.id.btnBackToHome);
        layout = findViewById(R.id.constraintLayout);

    }
    public void startClock(){
        if(timerRunning){
            stopTimer();
        }else{
            startTimer();
        }

        getQuestion();
    }
    public void stopTimer(){

        countDownTimer.cancel();
        timerRunning = false;
    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMills, 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMills = millisUntilFinished;
                updateTimer();
                timesUp = false;
            }

            @Override
            public void onFinish() {
                timesUp=true;
                if(appForegroundStatus == true){
                    endGame();
                }

            }
        }.start();
    }

    private void endGame(){

        EndPage endPage = new EndPage();
        endPage.setTotalPoints(currentPoints);
        startActivity(new Intent(getApplicationContext(),EndPage.class));
        finish();
    }
    public void updateTimer(){

        int minutes = (int)timeLeftInMills/60000;
        int seconds = (int) (timeLeftInMills %60000/1000) + 1;

        String timeLeftText;

        timeLeftText = "";
        if(minutes<10)timeLeftText ="0";
        timeLeftText += ""+minutes;
        timeLeftText += ":";

        if(seconds<10){
            timeLeftText +="0";
        }
        timeLeftText += seconds;

        tv_Timer.setText(timeLeftText);

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

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("QuestionPage Status: ","ForeGround");
        appForegroundStatus = true;
        if(timesUp == true){
            EndPage endPage = new EndPage();
            endPage.setTotalPoints(currentPoints);
            startActivity(new Intent(getApplicationContext(),EndPage.class));
            finish();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mpMusic.isPlaying()) {

            if(headerClassInstance.getMusicPref(getApplicationContext()))
                mpMusic.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("QuestionPage Status: ","BackGround");
        appForegroundStatus = false;
        mpMusic.pause();
    }

    //stop the Media to free up resources
    @Override
    protected void onStop() {
        super.onStop();
        stopService(new Intent(this,BackgroundSoundService.class));

    }

}