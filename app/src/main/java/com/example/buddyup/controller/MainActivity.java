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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button logInBtn;



    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.registerID);
        register.setOnClickListener(this);


        logInBtn = findViewById(R.id.login_btn);
        logInBtn.setOnClickListener(this);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();





    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.registerID:

                startActivity(
                        new Intent(MainActivity.this, RegisterUserActivity.class)
                );
                break;

            case R.id.login_btn:

                userLogin();

                break;


        }
    }

    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

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

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Send too Profile Activity

                    startActivity(new Intent(MainActivity.this,ProfileActivity.class));

                }else
                {
                    Toast.makeText(MainActivity.this,"Failed to Login Check credentials",Toast.LENGTH_LONG
                    ).show();
                }
            }
        });


    }


    public void checkEmpty(String field, EditText editText) {
        if (field.isEmpty()) {
            editText.setError(" is Required");
            editText.requestFocus();
            return;
        }

    }

}