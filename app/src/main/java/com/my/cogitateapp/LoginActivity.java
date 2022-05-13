package com.my.cogitateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailTextView, passwordTextView;
    TextView forgotPass;
    Button loginButton;
    FirebaseAuth mAuth;
    FirebaseUser curAuth;
    TextInputLayout l1, l2;
    Intent i;
    CheckBox rememberMe;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefEditor;
    Boolean remember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Firebase Authentication instance
        mAuth = FirebaseAuth.getInstance();

        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        forgotPass = findViewById(R.id.forgotPwd);
        rememberMe= findViewById(R.id.remember);
        loginPreferences=getSharedPreferences("loginPref",MODE_PRIVATE);
        loginPrefEditor=loginPreferences.edit();
        remember=loginPreferences.getBoolean("remember",false);

        if(remember==true)
        {
            emailTextView.setText(loginPreferences.getString("email",""));
            passwordTextView.setText(loginPreferences.getString("password",""));
            rememberMe.setChecked(true);


        }


        loginButton.setOnClickListener(view -> loginUserAccount());
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(i);
            }
        });




    }




    void loginUserAccount() {
        l1 = findViewById(R.id.emaillayout);
        l2 = findViewById(R.id.passwordlayout);
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        String emailString, passwordString;
        emailString = emailTextView.getText().toString().trim();
        passwordString = passwordTextView.getText().toString();

        if (TextUtils.isEmpty(emailString)) {
            l1.setError("Field can't be Empty");

        } else if (TextUtils.isEmpty(passwordString)) {
            l2.setError("Field can't be Empty");
        } else {

            //Firebase Authentication
            mAuth.signInWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener((task) -> {
                        if (task.isSuccessful()) {
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
        if (rememberMe.isChecked())
        {
            loginPrefEditor.putBoolean("remember",true);
            loginPrefEditor.putString("email",emailString);
            loginPrefEditor.putString("password",passwordString);
            loginPrefEditor.commit();

        }
        else
        {
            loginPrefEditor.clear();
            loginPrefEditor.commit();
        }
    }
}