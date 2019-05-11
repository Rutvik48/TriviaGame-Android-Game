package com.example.triviagame;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddQuestionPage extends AppCompatActivity {

    private EditText et_Category,et_Question, et_CorrectAns, et_WrongAns1,et_WrongAns2, et_WrongAns3;
    private Button btn_Submit, btn_Home;
    private String category, question, correctAns,wrongAns1,wrongAns2, wrongAns3;
    private FirebaseAuth firebaseAuth;
    private ConstraintLayout layout;
    private static String CATEGORY = "Category";
    private static String QUESTION = "Question";
    private static String CORRECT_ANS = "Correct_Answer";
    private static String WRONG_ANS1 = "Option2";
    private static String WRONG_ANS2 = "Option2";
    private static String WRONG_ANS3 = "Option3";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question_page);

        setFullScreen();

        // this will assign variables to button, text, etc from xml file
        assignVariables();

        submitData();

        HeaderClass headerClassInstance = new HeaderClass();
        headerClassInstance.setBackground(layout, getApplicationContext());

        homeListner();

    }

    private void homeListner(){

        btn_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomePage.class));
                finish();
            }
        });

    }

    private boolean validateData(){
        boolean condition = false;

        category = et_Category.getText().toString().trim();
        question = et_Question.getText().toString().trim();
        correctAns = et_CorrectAns.getText().toString().trim();
        wrongAns1 = et_WrongAns1.getText().toString().trim();
        wrongAns2 = et_WrongAns2.getText().toString().trim();
        wrongAns3 = et_WrongAns3.getText().toString().trim();

        if(!category.isEmpty() && !question.isEmpty()){

            if (!correctAns.isEmpty() && !wrongAns1.isEmpty() && !wrongAns2.isEmpty() && !wrongAns3.isEmpty()){

                if (!correctAns.equals(wrongAns1) && !correctAns.equals(wrongAns2) && !correctAns.equals(wrongAns3)){
                    condition = true;
                }else
                    Toast.makeText(getApplicationContext(), "Correct answer cannot be same as wrong answer", Toast.LENGTH_LONG).show();
            }else
                Toast.makeText(getApplicationContext(),"Please fill all the option fields", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(getApplicationContext(),"Please fill question and categoy.", Toast.LENGTH_LONG).show();


        return condition;

    }

    private void submitData(){

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateData())

                    if (/*uploadDataToFireStore()*/true){
                        Toast.makeText(getApplicationContext(),"Question has been added.", Toast.LENGTH_SHORT).show();
                        clearAllFields();
                    }//else
                      //  Toast.makeText(getApplicationContext(),"Please make sure you have a internet connection. Try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void clearAllFields(){

        et_Category.setText("");
        et_Question.setText("");
        et_CorrectAns.setText("");
        et_WrongAns1.setText("");
        et_WrongAns2.setText("");
        et_WrongAns3.setText("");

        Toast.makeText(getApplicationContext(),"Thank you for your contribution.", Toast.LENGTH_LONG).show();

    }

    private boolean uploadDataToFireStore(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        userClass userClass = new userClass(getApplicationContext());
        CollectionReference collRef = db.collection("QuestionDatabase");

        Map<String, Object> dataTemp = new HashMap<>();
        dataTemp.put(CATEGORY, category);
        dataTemp.put(QUESTION, question);
        dataTemp.put(CORRECT_ANS, correctAns);
        dataTemp.put(WRONG_ANS1,wrongAns1);
        dataTemp.put(WRONG_ANS2,wrongAns2);
        dataTemp.put(WRONG_ANS3,wrongAns3);

        boolean ret = collRef.document(question).set(dataTemp).isComplete();
        return ret;
    }

    private void setFullScreen(){

        //hides the title bar
        getSupportActionBar().hide();

        //this code makes the status bar transparent
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND
        );

    }

    private void assignVariables(){

        et_Category = findViewById(R.id.etCategory);
        et_Question = findViewById(R.id.etQuestion);
        et_CorrectAns = findViewById(R.id.etCorrectAnswer);
        et_WrongAns1 = findViewById(R.id.etWrongAns1);
        et_WrongAns2 = findViewById(R.id.etWrongAns2);
        et_WrongAns3 = findViewById(R.id.etWrongAns3);
        btn_Submit = findViewById(R.id.btnSubmit);
        btn_Home = findViewById(R.id.btnHome);
        firebaseAuth = FirebaseAuth.getInstance();
        layout = findViewById(R.id.layout);
    }

    @Override
    public void onBackPressed() {
    }
}
