package com.app.sdchatbot.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.sdchatbot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button logInButton,registerButton;
    private FirebaseAuth firebaseAuthMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuthMain = FirebaseAuth.getInstance();

        logInButton = findViewById(R.id.logInButtonID);
        registerButton = findViewById(R.id.registerButtonID);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logInIntent = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(logInIntent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    //It will check if the user is already logged in
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = firebaseAuthMain.getCurrentUser();
        if(user != null)
        {
            startActivity(new Intent(MainActivity.this, SplashScreenActivity.class));
            finish();
        }
    }
}