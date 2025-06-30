package com.example.madprojectadmin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.madprojectadmin.ActivityAdminSignUpPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityAdminLoginPage extends AppCompatActivity {

    EditText Email, Password;
    Button Login;
    TextView signup;
    FirebaseAuth mAuth;

    private static final String TAG = "SignupActivity";

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User already signed in
            Log.d(TAG, "User already signed in: " + currentUser.getEmail());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityadminloginpage);

        mAuth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Login = findViewById(R.id.btn_adminlogin);
        signup = findViewById(R.id.btn_adminsignup);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ActivityAdminLoginPage.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(ActivityAdminLoginPage.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(ActivityAdminLoginPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Signup success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(ActivityAdminLoginPage.this, "Account created successfully!", Toast.LENGTH_SHORT).show();

                                    Intent login = new Intent(ActivityAdminLoginPage.this, ActivityAdminHome.class);
                                    startActivity(login);
                                    finish();

                                } else {
                                    Log.w(TAG, "Signup failed", task.getException());
                                    Toast.makeText(ActivityAdminLoginPage.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(ActivityAdminLoginPage.this, ActivityAdminSignUpPage.class);
                startActivity(login);
                finish();
            }
        });
    }
}
