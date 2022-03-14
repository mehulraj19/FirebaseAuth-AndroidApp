package com.example.firebaseauthapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText email_et, password_et;
    private Button loginButton, loginCreateButton;

    private FirebaseAuth firebaseAuth;

    String email, password;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(getApplicationContext(), MainAreaActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        email_et = findViewById(R.id.emailLogin_et);
        password_et = findViewById(R.id.passwordLogin_et);
        loginButton = findViewById(R.id.loginButton);
        loginCreateButton = findViewById(R.id.loginCreateButton);

        setListeners();
    }

    private void setListeners() {
        loginButton.setOnClickListener(v -> {
            if (Validations()) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {
                            showToast("Welcome back");
                            startActivity(new Intent(getApplicationContext(), MainAreaActivity.class));
                            finish();
                        })
                        .addOnFailureListener(e -> showToast(" Something went wrong: " + e.getMessage()));
            } else {
                showToast("Enter in All the fields");
            }
        });
        loginCreateButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));
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