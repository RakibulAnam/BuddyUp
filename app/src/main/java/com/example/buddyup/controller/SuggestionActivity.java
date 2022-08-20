package com.example.buddyup.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buddyup.R;
import com.example.buddyup.model.OnLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SuggestionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView goTo,finalLocation,inLocation,signal,question;
    private Button inLocationBtn,foundBuddyBtn;
    private DatabaseReference databaseReference,senderRef;
    private String currentUserId,location,senderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);


        Intent intent = getIntent();

        location = intent.getStringExtra("location");
        senderId = intent.getStringExtra("senderId");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("OnLocation");
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        senderRef = FirebaseDatabase.getInstance().getReference("HangoutRequest").child(senderId);

        goTo = findViewById(R.id.locationPrompt);
        finalLocation = findViewById(R.id.finalLocation);
        inLocation = findViewById(R.id.click1);
        signal = findViewById(R.id.whenInLocationTV);
        question = findViewById(R.id.foundBuddyTv);

        inLocationBtn = findViewById(R.id.inLocationBtnId);
        inLocationBtn.setOnClickListener(this);
        foundBuddyBtn = findViewById(R.id.foundBuddyBtn);
        foundBuddyBtn.setOnClickListener(this);



        finalLocation.setText(location);




    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.inLocationBtnId:

                reached();

                break;

            case R.id.foundBuddyBtn:

                foundBuddy();

                break;

        }

    }

    private void foundBuddy() {

        senderRef.removeValue();
        FirebaseDatabase.getInstance().getReference().child("RequestAccepted").child(senderId).removeValue();
        FirebaseDatabase.getInstance().getReference().child("OnLocation").child(currentUserId).removeValue();

        startActivity(new Intent(SuggestionActivity.this,TopicActivity.class));


    }

    private void reached() {

        OnLocation onLocation = new OnLocation(currentUserId,location);

        databaseReference.child(currentUserId).setValue(onLocation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SuggestionActivity.this,"Added",Toast.LENGTH_LONG).show();
            }
        });


        goTo.setVisibility(View.GONE);
        finalLocation.setVisibility(View.GONE);
        inLocation.setVisibility(View.GONE);

        signal.setVisibility(View.VISIBLE);
        question.setVisibility(View.VISIBLE);
        foundBuddyBtn.setVisibility(View.VISIBLE);

        inLocationBtn.setVisibility(View.GONE);

    }
}