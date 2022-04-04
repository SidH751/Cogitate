package com.my.cogitateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    // instance variables

    EditText emailTextView, passwordTextView;
    Button registerButton;
    FirebaseAuth mAuth;
    TextInputLayout l1, l2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       emailTextView = findViewById(R.id.email);
       passwordTextView = findViewById(R.id.password);
       registerButton = findViewById(R.id.registerButton);

       registerButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               registerNewUser();
           }
       });









    }

    //Registration method

    void registerNewUser(){

        String emailString, passwordString;
        emailString = emailTextView.getText().toString();
        passwordString = passwordTextView.getText().toString();

        if(TextUtils.isEmpty(emailString)){

            emailTextView.setError("Please enter your Email");
        } else if (TextUtils.isEmpty(passwordString)){
            passwordTextView.setError("Password cannot be Empty");
        } else {
            // Firebase Registration
            mAuth.createUserWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // Registration successful
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                        "Registration successful!",
                                        Toast.LENGTH_LONG)
                                        .show();

                                Intent intent
                                        = new Intent(RegisterActivity.this,
                                        LoginActivity.class);
                                startActivity(intent);
                            } else {

                                // Registration failed
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Registration failed!!"
                                                + " Please try again later",
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
        }



    }
}