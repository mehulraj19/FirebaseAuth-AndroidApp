package com.example.firebaseauthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainAreaActivity extends AppCompatActivity {

    private Button signOutButton, changePasswordButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_area);

        firebaseAuth = FirebaseAuth.getInstance();

        signOutButton = findViewById(R.id.signOutButton);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        setListeners();
    }
    private void setListeners() {
        signOutButton.setOnClickListener(v -> {
            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });
        changePasswordButton.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
        });
    }
}