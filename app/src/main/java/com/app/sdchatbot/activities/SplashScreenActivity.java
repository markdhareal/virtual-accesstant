package com.app.sdchatbot.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.app.sdchatbot.R;

public class SplashScreenActivity extends AppCompatActivity {

    TextView virtualAccesstantTexTView;
    LottieAnimationView lottieRobotAnimation,lottieLoadingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        virtualAccesstantTexTView = findViewById(R.id.virtualAccesstantTextViewID);
        lottieRobotAnimation = findViewById(R.id.lottieRobotSplash);
        lottieLoadingAnimation = findViewById(R.id.lottieLoadingSplash);

        virtualAccesstantTexTView.animate().translationX(1400).setDuration(1000).setStartDelay(4000);
        lottieRobotAnimation.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        lottieLoadingAnimation.animate().translationY(1400).setDuration(1000).setStartDelay(4000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, MainActivityChatBot.class));
            }
        },5000);
    }
}