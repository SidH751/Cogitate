package com.my.cogitateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.ViewCompat;


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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.my.cogitateapp.LoginActivity;
import com.my.cogitateapp.R;
import com.my.cogitateapp.User;


public class RegisterActivity extends AppCompatActivity {

    // instance variables


    EditText emailTextView, passwordTextView, userNameTextView;
    Button registerButton;
    FirebaseAuth mAuth;
    TextInputLayout l1, l2;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userNameTextView = findViewById(R.id.username);
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");

        user = new User();

        registerButton.setOnClickListener(view -> { registerNewUser(); });

    }

    //Registration method

    void registerNewUser(){


        String emailString, passwordString, userName;
        emailString = emailTextView.getText().toString();
        passwordString = passwordTextView.getText().toString();
        userName = userNameTextView.getText().toString();


        //checking if necessary fields are empty

        if(TextUtils.isEmpty(emailString)){

            emailTextView.setError("Please enter your Email");
        } else if (TextUtils.isEmpty(passwordString)){
            passwordTextView.setError("Password cannot be Empty");
        } else {


            // Firebase Registration method
            mAuth.createUserWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener(task -> {

                        // Registration successful
                        if (task.isSuccessful()) {

                            addDataToFireBase(emailString, userName);
                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance()
                                            .getCurrentUser().getUid()).setValue(user);
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
                    });
        }
    }

    //method to add data to database in firebase
    private void addDataToFireBase(String email, String userName) {
        user.setEmailId(email);
        user.setUserName(userName);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(user);

                Toast.makeText(RegisterActivity.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(RegisterActivity.this, "failed to add data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
