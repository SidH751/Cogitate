package com.my.cogitateapp.interfaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.my.cogitateapp.R;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    CardView newSession, focusMode, levels, zenPlayer;
    Button signOutButton;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    GoogleSignInClient mGoogleSignInClient;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        newSession = findViewById(R.id.newSessionCard);
        focusMode = findViewById(R.id.focusModeCard);
        levels = findViewById(R.id.levelsCard);
        zenPlayer = findViewById(R.id.zenPlayerCard);
        signOutButton=findViewById(R.id.SignOut);

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });



        newSession.setOnClickListener( p-> {
            Intent intent = new Intent(Dashboard.this, StandardMode.class);
            startActivity(intent);}
        );

        focusMode.setOnClickListener(p -> {
            Intent intent = new Intent(Dashboard.this, FocusMode.class);
            startActivity(intent);}
        );

        zenPlayer.setOnClickListener(p -> {
            Intent intent = new Intent(Dashboard.this, ZenPlayer.class);
            startActivity(intent);}
        );

        levels.setOnClickListener(p -> {
            Intent intent = new Intent(Dashboard.this, LevelsPage.class);
            startActivity(intent);}
        );

        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView= findViewById(R.id.navigationmenu);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mAuth=FirebaseAuth.getInstance();
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                Intent intent1=new Intent(Dashboard.this, setting_activity.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                mAuth.signOut();
                Intent intent2=new Intent(Dashboard.this, LandingPage.class);
                startActivity(intent2);
                Toast.makeText(getApplicationContext(), "Log Out Successful!!", Toast.LENGTH_LONG).show();
                break;
            case R.id.helpandfeedback:
                Intent intent3=new Intent(Dashboard.this, HelpandFeedback.class);
                startActivity(intent3);
                break;
            case R.id.contactus:
                Intent intent4=new Intent(Dashboard.this, Contact_us.class);
                startActivity(intent4);
                break;
        }
        return true;
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(Dashboard.this,"User signed out",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
}