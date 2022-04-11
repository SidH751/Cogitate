package com.my.cogitateapp.interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.my.cogitateapp.R;
import com.my.cogitateapp.authenticator.LoginActivity;
import com.my.cogitateapp.authenticator.RegisterActivity;

public class LandingPage extends AppCompatActivity {

    Button firstRegisterBtn;
    Button firstLoginBtn;
    boolean showContent = false;

    FirebaseUser currentuser;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        firstLoginBtn = findViewById(R.id.loginFirstScreen);
        firstRegisterBtn = findViewById(R.id.registerFirstScreen);




        //Lambda functions used
        firstRegisterBtn.setOnClickListener(
                (p) -> { Intent intent = new Intent(LandingPage.this, RegisterActivity.class);
            startActivity(intent);}
        );
        
        firstLoginBtn.setOnClickListener(
               (p) -> {
                   Intent intent = new Intent(LandingPage.this, LoginActivity.class);
            startActivity(intent);
                }
        );

        final View content = findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if(showContent){
                    content.getViewTreeObserver().removeOnPreDrawListener(this);
                }
                showContentAfterSomeTime();
                return false;
            }
        });



        mAuth=FirebaseAuth.getInstance();
        currentuser=mAuth.getCurrentUser();
        if(currentuser!=null){
            Intent i=new Intent(LandingPage.this, Dashboard.class);
            startActivity(i);
            this.finish();
        }



    }



    private void showContentAfterSomeTime() {
        new Handler().postDelayed(() -> showContent = true, 1500);
    }
}