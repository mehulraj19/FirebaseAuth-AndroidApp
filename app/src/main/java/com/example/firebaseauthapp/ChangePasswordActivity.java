package com.example.firebaseauthapp;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ChangePasswordActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private EditText oldPassword_et, newPassword_et;
    private Button changePasswordButton;

    String oldPassword, newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        firebaseAuth = FirebaseAuth.getInstance();

        oldPassword_et = findViewById(R.id.old_password_et);
        newPassword_et = findViewById(R.id.new_password_et);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        setListeners();
    }
    private void setListeners() {
        changePasswordButton.setOnClickListener(v -> {
            if (Validations()){
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if(currentUser != null) {
                    AuthCredential authCredential = EmailAuthProvider.getCredential(Objects.requireNonNull(currentUser.getEmail()), oldPassword);
                    currentUser.reauthenticate(authCredential)
                            .addOnSuccessListener(unused -> currentUser.updatePassword(newPassword)
                                    .addOnSuccessListener(unused1 -> {
                                        showToast("Password has been updated!!");
                                        finish();
                                    })
                                    .addOnFailureListener(e -> showToast("Something went wrong: " + e.getMessage())))
                            .addOnFailureListener(e -> showToast("Your current password does not match the actual password."));
                }
            } else {
                showToast("Enter in All the fields!!");
            }
        });
    }

    private Boolean Validations() {
        if (!TextUtils.isEmpty(oldPassword_et.getText().toString().trim()) && (!TextUtils.isEmpty(newPassword_et.getText().toString().trim()))) {
            oldPassword = oldPassword_et.getText().toString().trim();
            newPassword = newPassword_et.getText().toString().trim();
            return true;
        }
        return false;
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}