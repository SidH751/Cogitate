package com.my.cogitateapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.my.cogitateapp.R;

public class Dashboard extends AppCompatActivity {
    CardView newSession, focusMode, levels, zenPlayer;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        newSession = findViewById(R.id.newSessionCard);
        focusMode = findViewById(R.id.focusModeCard);
        levels = findViewById(R.id.levelsCard);
        zenPlayer = findViewById(R.id.zenPlayerCard);

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

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();





    }
}