package com.example.triviagame;


import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.rgb;

public class CategoriesPage extends AppCompatActivity {

    private TextView textCategory1, textCategory2, textCategory3,
            textCategory4, textCategory5, textCategory6, textCategory7, textCategory8;
    public static boolean categoryState1, categoryState2, categoryState3,
            categoryState4, categoryState5, categoryState6, categoryState7, categoryState8;

    private Button btn_Continue, btn_BackHome, btn_UserInfo, btn_RandomQuestion;

    //selectedCategories holds ID number of selected categorie(s)
    //category in order of 1.Science & Nature, 2.Science: Computers, 3.General Knowledge
    // 4.Geography, 5.Sport, 6.Vehicle, 7.Celebrities, 8.History
    public static int []selectedCategories;
    private ConstraintLayout layout;
    private LinearLayout linearLayout;
    //private QuestionPage questionPage = new QuestionPage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_page);

        setFullScreen();

        //this method will assign buttons and text values to created variables
        assignValues();

        //will be called when user click on the button or text
        isClicked();

        setCategoryNames();

        //show question randomly
        startRandomQuestion();

        HeaderClass headerClassInstance = new HeaderClass();
        headerClassInstance.setBackground(layout, getApplicationContext());

        //CreateButton();
    }

    private void setCategoryNames(){
        //category in order of 1.Science & Nature, 2.Science: Computers, 3.General Knowledge
        // 4.Geography, 5.Sport, 6.Vehicle, 7.Celebrities, 8.History
        textCategory1.setText("Science & Nature");
        textCategory2.setText("Science: Computers");
        textCategory3.setText("General Knowledge");
        textCategory4.setText("Geography");
        textCategory5.setText("Sport");
        textCategory6.setText("Vehicle");
        textCategory7.setText("Celebrities");
        textCategory8.setText("History");
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

        btn_Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSlectedCategories();

                QuestionPage questionPage = new QuestionPage();
                questionPage.setRandomQuestionFalse();
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

        textCategory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryState1 = setTextState(textCategory1,categoryState1);
                checkCategoryState();
                //categoryState1 = true;
            }
        });

        textCategory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryState2 = setTextState(textCategory2,categoryState2);
                checkCategoryState();
                //categoryState2 = true;
            }
        });

        textCategory3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryState3 = setTextState(textCategory3,categoryState3);
                checkCategoryState();
                //categoryState3 = true;
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

        textCategory8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryState8 = setTextState(textCategory8,categoryState8);
                checkCategoryState();
            }
        });
    }

    private boolean clickListner(TextView textCategory, boolean categoryState){

        categoryState = setTextState(textCategory,categoryState);
        checkCategoryState();
        return categoryState;

    }

    //this method checks which category are/is selected
    private void checkCategoryState(){
        if((categoryState1)||(categoryState2)||
                (categoryState3)||(categoryState4)||
                (categoryState5)||(categoryState6)||
                (categoryState7)||(categoryState8))
            btn_Continue.setVisibility(View.VISIBLE);
        else
            btn_Continue.setVisibility(View.INVISIBLE);
    }

    public void makeAllCategoryStateFalse(){
        categoryState1 = false;
        categoryState2 = false;
        categoryState3 = false;
        categoryState4 = false;
        categoryState5 = false;
        categoryState6 = false;
        categoryState7 = false;
        categoryState8 = false;
    }

    public void getSlectedCategories(){
        String categoryString = "";

        if(categoryState1)
            categoryString += "1,";
        if(categoryState2)
            categoryString += "2,";
        if(categoryState3)
            categoryString += "3,";
        if(categoryState4)
            categoryString += "4,";
        if(categoryState5)
            categoryString += "5,";
        if(categoryState6)
            categoryString += "6,";
        if(categoryState7)
            categoryString += "7,";
        if(categoryState8)
            categoryString += "8,";

        makeAllCategoryStateFalse();
        String stringArray[] = categoryString.split(",");
        selectedCategories = changeToIntArray(stringArray, stringArray.length);
    }

    private int []changeToIntArray(String[] array, int length){
        int tempArray[] = new int[length];

        for (int i = 0; i <length; i++)
            tempArray[i] = Integer.parseInt(array[i]);

        return tempArray;
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
    private boolean setTextState(TextView textView, boolean categoryState){

        if(categoryState == false){
            textView.setTextColor(0xFFFF0000);
            textView.setTextSize(33);
            categoryState = true;
        }else{
            textView.setTextColor(0xFF000000);
            textView.setTextSize(26);
            categoryState = false;
        }

        return categoryState;
    }

    private void CreateButton(){

        TextView btnShow = new TextView(this);

        btnShow.setText("Dynamic");
        btnShow.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        linearLayout.addView(btnShow);
        btnShow.setTag("tvCategory1");
    }

    //this method will be called to assign buttons and text to variable
    private void assignValues(){
        textCategory1 = findViewById(R.id.tvCategory1);
        textCategory2 = findViewById(R.id.tvCategory2);
        textCategory3 = findViewById(R.id.tvCategory3);
        textCategory4 = findViewById(R.id.tvCategory4);
        textCategory5 = findViewById(R.id.tvCategory5);
        textCategory6 = findViewById(R.id.tvCategory6);
        textCategory7 = findViewById(R.id.tvCategory7);
        textCategory8 = findViewById(R.id.tvCategory8);
        btn_Continue = findViewById(R.id.btnContinue);
        btn_BackHome = findViewById(R.id.btnBackToHome);
        btn_UserInfo = findViewById(R.id.btnUserInfo);
        btn_BackHome = findViewById(R.id.btnBackToHome);
        btn_UserInfo = findViewById(R.id.btnUserInfo);
        btn_RandomQuestion = findViewById(R.id.btnRandomQuestion);
        layout = findViewById(R.id.constraintLayout);
        linearLayout = findViewById(R.id.catLinearLayout);

    }

    @Override
    public void onBackPressed() {
        //back button will not do anything
    }

}
