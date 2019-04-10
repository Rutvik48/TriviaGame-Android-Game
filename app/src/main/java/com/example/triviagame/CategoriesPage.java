package com.example.triviagame;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import static android.graphics.Color.rgb;

public class CategoriesPage extends AppCompatActivity {

    private TextView textCategory1, textCategory2, textCategory3,
            textCategory4, textCategory5, textCategory6, textCategory7;

    private int categoryState1 = 0, categoryState2 = 0, categoryState3 = 0,
            categoryState4 = 0, categoryState5 = 0, categoryState6 = 0, categoryState7 = 0;

    private Button btn_Continue, btn_BackHome, btn_UserInfo, btn_RandomQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_page);

        setFullScreen();

        //this method will assign buttons and text values to created variables
        assignValues();

        //will be called when user click on the button or text
        isClicked();

        //show question randomly
        startRandomQuestion();


    }


    private void startRandomQuestion(){

        btn_RandomQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                QuestionPage questionPage = new QuestionPage();
                //this will set variable "RANDOM_QUESTION" = true in QuestionPage class
                questionPage.setRandomQuestionTrue();

                //this will go to QuationPage to show questions Randomly
                startActivity(new Intent(getApplicationContext(),QuestionPage.class));
                finish();
            }
        });
    }
    private void isClicked(){

        textCategory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryState1 = setTextState(textCategory1,categoryState1);
                checkCategoryState();
            }
        });

        btn_Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesPage.this, QuestionPage.class);
                startActivity(intent);
                finish();
            }
        });
        btn_UserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //user info will be shown
                Intent intent = new Intent(CategoriesPage.this, PopUpWindow.class);
                startActivity(intent);
            }
        });

        btn_BackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesPage.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
        textCategory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryState2 = setTextState(textCategory2,categoryState2);
                checkCategoryState();
            }
        });

        textCategory3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryState3 = setTextState(textCategory3,categoryState3);
                checkCategoryState();
            }
        });

        textCategory4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryState4 = setTextState(textCategory4,categoryState4);
                checkCategoryState();
            }
        });

        textCategory5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryState5 = setTextState(textCategory5,categoryState5);
                checkCategoryState();
            }
        });

        textCategory6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryState6 = setTextState(textCategory6,categoryState6);
                checkCategoryState();
            }
        });

        textCategory7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryState7 = setTextState(textCategory7,categoryState7);
                checkCategoryState();
            }
        });
    }

    //this method checks which category are/is selected
    private void checkCategoryState(){
        if((categoryState1 == 1)||(categoryState2 == 1)||(categoryState3 == 1)||(categoryState4 == 1)||
                (categoryState5 == 1)||(categoryState6 == 1)||(categoryState7 == 1)||false)
            btn_Continue.setVisibility(View.VISIBLE);
        else
            btn_Continue.setVisibility(View.INVISIBLE);
    }

    public void setFullScreen() {

        //hides the title bar
        getSupportActionBar().hide();

        //this code makes the status bar transparent
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    //changes text color to indicate selected category
    private int setTextState(TextView textView, int categoryState){

        if(categoryState == 0){
            textView.setTextColor(0xFFFF0000);
            textView.setTextSize(33);
            categoryState = 1;
        }else{
            textView.setTextColor(0xFF000000);
            textView.setTextSize(26);
            categoryState = 0;
        }

        return categoryState;
    }

    //this method will be called to assign buttons and text to variable
    private void assignValues(){
        textCategory1 = (TextView)findViewById(R.id.tvCategory1);
        textCategory2 = (TextView)findViewById(R.id.tvCategory2);
        textCategory3 = (TextView)findViewById(R.id.tvCategory3);
        textCategory4 = (TextView)findViewById(R.id.tvCategory4);
        textCategory5 = (TextView)findViewById(R.id.tvCategory5);
        textCategory6 = (TextView)findViewById(R.id.tvCategory6);
        textCategory7 = (TextView)findViewById(R.id.tvCategory7);
        btn_Continue = (Button)findViewById(R.id.btnContinue);
        btn_BackHome = (Button)findViewById(R.id.btnBackToHome);
        btn_UserInfo = (Button)findViewById(R.id.btnUserInfo);
        btn_BackHome = (Button)findViewById(R.id.btnBackToHome);
        btn_UserInfo = (Button)findViewById(R.id.btnUserInfo);
        btn_RandomQuestion = (Button)findViewById(R.id.btnRandomQuestion);

    }

    @Override
    public void onBackPressed() {
        //back button will not do anything
    }

}
