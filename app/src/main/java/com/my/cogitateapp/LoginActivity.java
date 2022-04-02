package com.my.cogitateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText emailTextView, passwordTextView;
    Button loginButton;
    FirebaseAuth mAuth;
    TextInputLayout l1, l2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Firebase Authentication instance
        mAuth = FirebaseAuth.getInstance();

        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(view -> loginUserAccount());
    }

    void loginUserAccount(){
        l1 = findViewById(R.id.emaillayout);
        l2 = findViewById(R.id.passwordlayout);
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        String emailString, passwordString;
        emailString = emailTextView.getText().toString().trim();
        passwordString = passwordTextView.getText().toString();

        if(TextUtils.isEmpty(emailString)){
            l1.setError("Field can't be Empty");

        } else if (TextUtils.isEmpty(passwordString)){
            l2.setError("Field can't be Empty");
        } else {

            //Firebase Authentication
            mAuth.signInWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener((task) -> {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),
                                    "Login successful!!",
                                    Toast.LENGTH_LONG)
                                    .show();

                            Intent intent
                                    = new Intent(LoginActivity.this,
                                    Dashboard.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Login failed!!",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
        }
    }
}