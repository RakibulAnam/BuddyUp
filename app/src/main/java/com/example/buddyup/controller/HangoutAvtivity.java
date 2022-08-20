package com.example.buddyup.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.buddyup.R;
import com.example.buddyup.model.HangoutRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HangoutAvtivity extends AppCompatActivity {

    private EditText enterInterest;
    private Spinner enterLocation;
    private String[] locations;
    private Button makeHangoutRequest;
    private DatabaseReference hangRef;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangout_avtivity);

        enterInterest = findViewById(R.id.interest);
        enterLocation = findViewById(R.id.spinnerLocation);
        makeHangoutRequest = findViewById(R.id.hrBtn);
        hangRef = FirebaseDatabase.getInstance().getReference().child("HangoutRequest");


        locations = getResources().getStringArray(R.array.location);

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this,R.layout.location_spinner_view,R.id.locationSpinnerTV
                ,locations);

        enterLocation.setAdapter(locationAdapter);

        makeHangoutRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hanoutReq();
            }
        });


    }

    private void hanoutReq() {

        String interest = enterInterest.getText().toString().trim();
        String location = enterLocation.getSelectedItem().toString();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

//        String hId = hangRef.push().getKey();

        HangoutRequest hr = new HangoutRequest(interest,location,currentUserId);



        hangRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(hr).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(HangoutAvtivity.this,"Added",Toast.LENGTH_LONG).show();
            }
        });

        startActivity(new Intent(HangoutAvtivity.this,WaitingActivity.class));



    }
}