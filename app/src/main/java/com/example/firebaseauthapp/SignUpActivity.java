package com.example.firebaseauthapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private EditText email_et, password_et;
    private Button createAccountButton;

    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        email_et = findViewById(R.id.signupEmail_et);
        password_et = findViewById(R.id.signupPassword_et);
        createAccountButton = findViewById(R.id.createAccountButton);

        setListeners();
    }
    private void setListeners(){
        createAccountButton.setOnClickListener(v -> {
            if (Validations()) {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {
                            showToast("Welcome to this App");
                            startActivity(new Intent(getApplicationContext(), MainAreaActivity.class));
                            finish();
                        })
                        .addOnFailureListener(e -> showToast("Something went wrong: " + e.getMessage()));
            } else {
                showToast("Enter in All the fields");
            }
        });
    }

    private Boolean Validations() {
        if (!TextUtils.isEmpty(email_et.getText().toString().trim()) && !TextUtils.isEmpty(password_et.getText().toString().trim())) {
            email = email_et.getText().toString().trim();
            password = password_et.getText().toString().trim();
            return true;
        }
        return false;
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}