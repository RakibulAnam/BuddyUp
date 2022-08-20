package com.example.buddyup.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.buddyup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WaitingActivity extends AppCompatActivity {

    private DatabaseReference requestRef, locationRef;
    private TextView waitingtext, foundBuddyText;
    private Button foundBuddyBtn;
    private String currentUserId, receiverUserId;
    private Boolean requestAccepted = false;
    private Boolean arrived = false;
    private ProgressBar waitLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        foundBuddyText = findViewById(R.id.ffquestionID);
        foundBuddyBtn = findViewById(R.id.foundBuddyBtn);





        waitingtext = findViewById(R.id.waitingText);
        waitLoad = findViewById(R.id.progressBar);

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        requestRef = FirebaseDatabase.getInstance().getReference().child("RequestAccepted").child(currentUserId);

        requestRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        receiverUserId = snapshot.child("acceptorId").getValue(String.class);
                        Log.d("RecId", "onDataChange: " + receiverUserId);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );


        locationRef = FirebaseDatabase.getInstance().getReference().child("OnLocation");


        reqConfirmcontent();


        if (requestAccepted == true) {
            inLocationcontent();
        }





    }

    private void inLocationcontent() {

        locationRef.child(receiverUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    waitingtext.setText("Your Buddy Is Here, Signaling you by waving hands");
                    waitLoad.setVisibility(View.GONE);
                    arrived = true;

                    startActivity(new Intent(WaitingActivity.this,TopicActivity.class));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (arrived == false) {
            refresh(1000);
        }


    }

    private void reqConfirmcontent() {
        requestRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    waitingtext.setText("Request Accepted, Buddy is Coming, will Signal you by waving hands ");
                    requestAccepted = true;
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (requestAccepted == false) {
            refresh(1000);
        }

    }

    private void refresh(int millisecond) {

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (requestAccepted == false) {
                    reqConfirmcontent();
                }
                else {

                    inLocationcontent();
                }


            }
        };

        handler.postDelayed(runnable, millisecond);

    }



}