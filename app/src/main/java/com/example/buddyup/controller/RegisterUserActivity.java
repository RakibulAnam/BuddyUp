package com.example.buddyup.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buddyup.R;
import com.example.buddyup.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView banner;
    private EditText editTextFullName, editTextAge, editTextEmail, editTextPassword;
    private Button registerUserbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUserbtn = findViewById(R.id.register_btn);
        registerUserbtn.setOnClickListener(this);

        editTextEmail = findViewById(R.id.rEmail);
        editTextPassword = findViewById(R.id.rPassword);
        editTextFullName = findViewById(R.id.full_name);
        editTextAge = findViewById(R.id.ageETID);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.banner:
                startActivity(new Intent(RegisterUserActivity.this,MainActivity.class));
                break;

            case R.id.register_btn:
                registerUser();
                break;



        }

    }

    private void registerUser() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        checkEmpty(email,editTextEmail);
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Proper Email is Required");
            editTextEmail.requestFocus();
            return;
        }
        checkEmpty(password,editTextPassword);

        if (password.length()<6){
            editTextPassword.setError("Min Length 6 Characters");
            editTextPassword.requestFocus();
            return;
        }

        checkEmpty(fullName,editTextFullName);
        checkEmpty(age,editTextAge);


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    User user = new User(fullName,age,email);

                    FirebaseDatabase.getInstance().getReference("Users").child(
                            FirebaseAuth.getInstance().getCurrentUser().getUid()
                    ).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterUserActivity.this,"User Registered Successfully",Toast.LENGTH_LONG
                                ).show();



                            }else{
                                Toast.makeText(RegisterUserActivity.this,"Failed to Register 1",Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                    });

                }else {

                    Toast.makeText(RegisterUserActivity.this,"Failed to Register 2",Toast.LENGTH_LONG
                    ).show();

                }

            }
        });

        startActivity(new Intent(RegisterUserActivity.this,MainActivity.class));


    }

    public void checkEmpty(String field, EditText editText) {
        if (field.isEmpty()) {
            editText.setError(field + " is Required");
            editText.requestFocus();
            return;
        }

    }

}