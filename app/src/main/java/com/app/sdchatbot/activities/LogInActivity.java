package com.app.sdchatbot.activities;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.sdchatbot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private EditText emailEditLogIn, passwordEditLogIn;
    private ProgressBar progressBarLogIn;
    private Button logInBtn;
    private FirebaseAuth firebaseAuthLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        firebaseAuthLogIn = FirebaseAuth.getInstance();

        emailEditLogIn = findViewById(R.id.emailLogInId);
        passwordEditLogIn = findViewById(R.id.passwordLogInId);
        progressBarLogIn = findViewById(R.id.progressBarLogId);
        logInBtn = findViewById(R.id.logInButtonLogAct);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogIn();
            }
        });
    }

    //Method for user log in
    private void userLogIn()
    {
        String email = emailEditLogIn.getText().toString().trim();
        String password =  passwordEditLogIn.getText().toString().trim();

        if(email.isEmpty())
        {
            emailEditLogIn.setError("Please input your email.");
            emailEditLogIn.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailEditLogIn.setError("Please provide valid email.");
            emailEditLogIn.requestFocus();
        }
        else if(password.isEmpty())
        {
            passwordEditLogIn.setError("Please input your password.");
            passwordEditLogIn.requestFocus();
        }
        else if(password.length() < 6)
        {
            passwordEditLogIn.setError("Your password should at least be 6 characters");
        }
        else
        {
            progressBarLogIn.setVisibility(View.VISIBLE);
            firebaseAuthLogIn.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                progressBarLogIn.setVisibility(View.GONE);
                                Toast.makeText(LogInActivity.this, "Log in successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LogInActivity.this, SplashScreenActivity.class));
                            }
                            else
                            {
                                progressBarLogIn.setVisibility(View.GONE);
                                Toast.makeText(LogInActivity.this, "Failed to log in.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}