package com.example.buddyup.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buddyup.controller.AcceptRequestActivity;
import com.example.buddyup.R;
import com.example.buddyup.model.HangoutRequest;

import java.util.ArrayList;

public class MyAadapter extends RecyclerView.Adapter<MyAadapter.MyViewHolder> {

    private static Context context;
    private static ArrayList<HangoutRequest> list;

    public MyAadapter(Context context, ArrayList<HangoutRequest> list) {
        this.context = context;
        this.list = list;
    }

    public MyAadapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.hanglist,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HangoutRequest hor = list.get(position);
        holder.interest.setText(hor.getInterest());
        holder.location.setText(hor.getLocation());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView interest, location;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            interest = itemView.findViewById(R.id.interestLTV);
            location = itemView.findViewById(R.id.locationLTV);

            itemView.setOnClickListener(this);





        }

        @Override
        public void onClick(View view) {

            int positon = getAdapterPosition();

            String interest = list.get(positon).getInterest();
            String location = list.get(positon).getLocation();
            String id = list.get(positon).getId();


            Intent intent = new Intent(context, AcceptRequestActivity.class);

            intent.putExtra("interest",interest);
            intent.putExtra("location",location);
            intent.putExtra("sender_Id",id);

            context.startActivity(intent);



            Toast.makeText(context,interest,Toast.LENGTH_LONG).show();
        }
    }
}
