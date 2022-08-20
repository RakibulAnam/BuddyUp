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
import com.example.buddyup.model.RequestAccepted;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AcceptRequestActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView interest,location;
    private Button accept, decline;
    private DatabaseReference reference;
    String currentUserId;
    String gotSenderId;
    String gotLocation;
    String gotInterest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_request);

        Intent intent = getIntent();

        gotInterest = intent.getStringExtra("interest");
        gotLocation = intent.getStringExtra("location");
        gotSenderId = intent.getStringExtra("sender_Id");

        interest = findViewById(R.id.gotInterestId);
        location = findViewById(R.id.gotLocationId);

        accept = findViewById(R.id.acceptBtnId);
        accept.setOnClickListener(this);
        decline = findViewById(R.id.declineBtnId);
        decline.setOnClickListener(this);

        interest.setText(gotInterest);
        location.setText(gotLocation);

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference();






    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.declineBtnId:

                onDecline();

                break;
            case R.id.acceptBtnId:

                onAccept();

                break;

        }

    }

    private void onAccept() {

        String receiverId = currentUserId;
        String senderId = gotSenderId;

        RequestAccepted requestAccepted = new RequestAccepted(receiverId,senderId);

        reference.child("RequestAccepted").child(senderId).setValue(requestAccepted).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AcceptRequestActivity.this,"RA Added",Toast.LENGTH_LONG).show();
            }
        });


        Intent intent = new Intent(AcceptRequestActivity.this,SuggestionActivity.class);
        intent.putExtra("location",gotLocation);
        intent.putExtra("senderId",senderId);
        startActivity(intent);

    }

    private void onDecline() {

        startActivity(new Intent(AcceptRequestActivity.this,ProfileActivity.class));

    }
}