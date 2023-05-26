package com.app.sdchatbot.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.sdchatbot.R;
import com.app.sdchatbot.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth userAuthentication;

    private EditText nameEditReg, emailEditReg, passwordEditReg;
    private ProgressBar progressBarReg;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userAuthentication = FirebaseAuth.getInstance();

        nameEditReg = findViewById(R.id.nameRegisterId);
        emailEditReg = findViewById(R.id.emailRegisterId);
        passwordEditReg = findViewById(R.id.passwordRegisterId);
        progressBarReg = findViewById(R.id.progressBarRegId);
        registerBtn = findViewById(R.id.regButtonId);




        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerMethod();
            }
        });

    }

    //this method is for user registration
    private void registerMethod()
    {
        String name = nameEditReg.getText().toString();
        String email = emailEditReg.getText().toString().trim();
        String password = passwordEditReg.getText().toString().trim();

        if(name.isEmpty())
        {
            nameEditReg.setError("Please input your name.");
            nameEditReg.requestFocus();
        }
        else if(email.isEmpty())
        {
            emailEditReg.setError("Please input your email.");
            emailEditReg.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())//to check the format of email
        {
            emailEditReg.setError("Please provide valid email.");
            emailEditReg.requestFocus();
        }
        else if(password.isEmpty())
        {
            passwordEditReg.setError("Please input your password.");
            passwordEditReg.requestFocus();
        }
        else if(password.length() < 6)
        {
            passwordEditReg.setError("Your password should at least be 6 characters.");
        }
        else
        {
            progressBarReg.setVisibility(View.VISIBLE);
            userAuthentication.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                //Object for user
                                User user = new User(name,email);

                                //adding it on firebase database
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(name)
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            progressBarReg.setVisibility(View.GONE);
                                            Toast.makeText(RegisterActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();

                                            //go to the log in screen
                                            startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
                                        }
                                        else
                                        {
                                            progressBarReg.setVisibility(View.GONE);
                                            Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                progressBarReg.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }


}