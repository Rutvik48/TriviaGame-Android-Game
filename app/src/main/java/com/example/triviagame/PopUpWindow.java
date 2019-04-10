package com.example.triviagame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class PopUpWindow extends Activity {

    private PopUpWindow popUpWindow;
    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);

        DisplayMetrics displayMetrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //displaying this activity will take 60% of the height and 80% of the width
        //int width = (int)0.8*(displayMetrics.widthPixels);
        //int height = (int)0.6*(displayMetrics.heightPixels);

        //getWindow().setLayout(width,height);
        int wid = displayMetrics.widthPixels;
        int hig = displayMetrics.heightPixels;

        getWindow().isFloating();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        getWindow().setLayout((int)(wid*.8),(int)(hig*.5));

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
