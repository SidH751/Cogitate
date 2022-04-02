package com.my.cogitateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    Button submitForgot;
    EditText emailForgot;
    FirebaseAuth mAuth;
    String email;
    TextView backLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth= FirebaseAuth.getInstance();

        emailForgot = findViewById(R.id.forgotEmail);
        submitForgot=findViewById(R.id.submitForgotButton);
        backLogin=findViewById(R.id.backToLogin);
        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassword.this,LoginActivity.class));
            }
        });

        submitForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }
    void validateData()
    {
        email= emailForgot.getText().toString();
        if(email.isEmpty())
        {
            emailForgot.setError("Field cannot be empty");
        }
        else
        {
            passwordForgot();
        }
    }
    void passwordForgot()
    {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ForgotPassword.this,"Check your email to change password",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgotPassword.this,LoginActivity.class));
                }
                else
                {
                    Toast.makeText(ForgotPassword.this,"Error: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}