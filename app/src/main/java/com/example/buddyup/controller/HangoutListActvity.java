package com.example.buddyup.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.buddyup.R;
import com.example.buddyup.adapter.MyAadapter;
import com.example.buddyup.model.HangoutRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HangoutListActvity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<HangoutRequest> hangList;
    DatabaseReference hangRef;
    MyAadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangout_list_actvity);

        recyclerView = findViewById(R.id.recyclerView);
        hangRef = FirebaseDatabase.getInstance().getReference("HangoutRequest");
        hangList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAadapter(this, hangList);
        recyclerView.setAdapter(adapter);


        hangRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    HangoutRequest hor = dataSnapshot.getValue(HangoutRequest.class);
                    hangList.add(hor);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}