package com.example.madprojectadmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityCreateNewUser extends AppCompatActivity {

    EditText Name, Email, Password;
    Button CreateAccount;

    ImageView BackButton;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycreatenewuser);



        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        CreateAccount = findViewById(R.id.btn_createaccount);
        BackButton = findViewById(R.id.backbutton);

        BackButton.setOnClickListener(v -> onBackPressed());

        //temporary button
       /* Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toaddmenuitem = new Intent(ActivityAdminLoginPage.this,ActivityAdminHome.class);
                startActivity(toaddmenuitem);
            }
        });*/



    }



}
