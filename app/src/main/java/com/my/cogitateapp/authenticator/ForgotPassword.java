package com.my.cogitateapp.authenticator;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.my.cogitateapp.R;

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
        submitForgot = findViewById(R.id.submitForgotButton);
        backLogin=findViewById(R.id.backToLogin);
        backLogin.setOnClickListener(
                view -> startActivity(new Intent(ForgotPassword.this, LoginActivity.class)));

        submitForgot.setOnClickListener(view -> validateData());
    }
    void validateData() {
        email = emailForgot.getText().toString();
        if(email.isEmpty()) {
            emailForgot.setError("Field cannot be empty");
        } else {
            passwordForgot();
        }
    }
    void passwordForgot() {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Toast.makeText(ForgotPassword.this,"Check your email to change password",Toast.LENGTH_LONG).show();
                startActivity(new Intent(ForgotPassword.this,LoginActivity.class));
            } else {
                Toast.makeText(ForgotPassword.this,"Error: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}