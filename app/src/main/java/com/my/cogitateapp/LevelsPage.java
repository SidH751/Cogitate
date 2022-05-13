package com.my.cogitateapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.my.cogitateapp.R;
import com.my.cogitateapp.User;

public class LevelsPage extends AppCompatActivity {

    TextView textv1,textv2,textv3;
    Task<DataSnapshot> databaseReference;
    FirebaseDatabase firebaseDatabase;
    User data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        setContentView(R.layout.activity_levels_page);
        textv1=findViewById(R.id.textView1);
        textv2=findViewById(R.id.textView2);
        textv3=findViewById(R.id.textView3);

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        DataSnapshot dbSnap = task.getResult();
                        data = dbSnap.getValue(User.class);
                        if(data!=null){
                            textv1.setText(data.getEmailId());
                            textv2.setText(data.getUserName());
                        }
                    } else {
                        Toast.makeText(LevelsPage.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}