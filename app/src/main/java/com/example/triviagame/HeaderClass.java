package com.example.triviagame;

import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HeaderClass extends AppCompatActivity {

    public ConstraintLayout layout_header = findViewById(R.id.layoutHeader);

    private int backgroundNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_menu_bar);


        layout_header = (ConstraintLayout)findViewById(R.id.layoutHeader);
    }

    public void changeBackgroung(){
        //change background of constraint
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (backgroundNum == 0){
                layout_header.setBackgroundResource(R.drawable.background_gradient1);
                backgroundNum = 1;
            }else{
                layout_header.setBackgroundResource(R.drawable.background_gradient);
                backgroundNum = 0;
            }
        }else{
            //tv_ChangeBackground.setVisibility(View.INVISIBLE);

        }
    }


}
