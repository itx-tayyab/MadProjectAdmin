package com.example.madprojectadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class ActivityProfile extends AppCompatActivity {

    EditText nameEditText, addressEditText, emailEditText, phoneEditText, passwordEditText;
    Button saveInfoButton;
    ImageView BackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityprofile); // Ensure XML file is named payment.xml

        // Initialize views
        nameEditText = findViewById(R.id.name);
        addressEditText = findViewById(R.id.address);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phone);
        passwordEditText = findViewById(R.id.password);
        saveInfoButton = findViewById(R.id.btn_profile);
        BackButton = findViewById(R.id.backbutton);

        BackButton.setOnClickListener(v -> onBackPressed());

        // Button click listener
        saveInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });
    }

    private void placeOrder() {
        String name = nameEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show();
        } else {
            String message = "Information Saved Successfully!\n\n" +
                    "Name: " + name + "\n" +
                    "Address: " + address + "\n" +
                    "Email: " + email + "\n" +
                    "Phone: " + phone + "\n" +
                    "Password: " + password;

            Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        }
    }
}
