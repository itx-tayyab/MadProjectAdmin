package com.example.madprojectadmin;

import android.os.Bundle;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import com.example.madprojectadmin.ActivityAdminLoginPage;
import com.example.madprojectadmin.R;


public class ActivityAdminSplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityadminsplashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent tohome;
                tohome = new Intent(ActivityAdminSplashScreen.this, ActivityAdminLoginPage.class);
                startActivity(tohome);
                finish();
            }
        },5000);

    }



}