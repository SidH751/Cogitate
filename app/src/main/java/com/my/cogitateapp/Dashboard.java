package com.my.cogitateapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.my.cogitateapp.R;

public class Dashboard extends AppCompatActivity {
    CardView newSession, focusMode, levels, zenPlayer;

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


    }
}