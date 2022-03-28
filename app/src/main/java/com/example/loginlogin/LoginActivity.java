package com.example.loginlogin;

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

public class LoginActivity extends AppCompatActivity {
    EditText emailTextView, passwordTextView;
    Button loginButton;
    FirebaseAuth mAuth; //comment
    TextInputLayout l1, l2;//comment2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Firebase Authentication instance
        mAuth = FirebaseAuth.getInstance();

        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAccount();
            }
        });
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
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),
                                        "Login successful!!",
                                        Toast.LENGTH_LONG)
                                        .show();

                                Intent intent
                                        = new Intent(LoginActivity.this,
                                        LandingPage.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Login failed!!",
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
        }
    }
}