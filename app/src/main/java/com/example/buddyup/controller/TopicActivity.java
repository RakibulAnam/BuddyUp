package com.example.buddyup.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.buddyup.R;
import com.google.firebase.auth.FirebaseAuth;

public class TopicActivity extends AppCompatActivity {

    private Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);


        logOut = findViewById(R.id.lgtBtn);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(TopicActivity.this,MainActivity.class));
            }
        });



    }
}