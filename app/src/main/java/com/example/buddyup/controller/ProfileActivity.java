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
import com.example.buddyup.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button logOutbtn;
    private Button hangBtn, hListBtn;

    private TextView fullName;
    private FirebaseUser user;
    private DatabaseReference reference, hangReference;
    private String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logOutbtn = findViewById(R.id.logOut_btn);
        logOutbtn.setOnClickListener(this);


        fullName = findViewById(R.id.pfullname);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();


        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    fullName.setText(userProfile.fullName);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ProfileActivity.this, "SSomething Went Wrong",Toast.LENGTH_LONG).show();

            }
        });

        hangBtn = findViewById(R.id.hangBtn);
        hangBtn.setOnClickListener(this);


        hListBtn = findViewById(R.id.hListBtn);


        FirebaseDatabase.getInstance().getReference().child("HangoutRequest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    hListBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        hListBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.logOut_btn:

                logOut();

                break;

            case R.id.hangBtn:
                makeRequest();
                break;

            case R.id.hListBtn:

                showList();

                break;


        }
    }

    private void showList() {
        startActivity(new Intent(ProfileActivity.this,HangoutListActvity.class));
    }

    private void makeRequest() {
        startActivity(new Intent(ProfileActivity.this,HangoutAvtivity.class));
    }

    private void logOut() {
        hangReference = FirebaseDatabase.getInstance().getReference("HangoutRequest").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        hangReference.removeValue();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }
}