package com.example.taxloancalci;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    private TextView loadingText;
    private int dotCount = 0;
    private final String[] dotStates = {"", ".", "..", "..."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.logoImageView);
        LottieAnimationView lottieAnimationView = findViewById(R.id.lottieAnimationView);
        loadingText = findViewById(R.id.loadingTextView);

        // The Lottie animation will start automatically due to app:lottie_autoPlay="true"

        // Start the dot animation
        animateDots();

        // Navigate to MainActivity after 3 seconds
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 4300);
    }

    private void animateDots() {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(loadingText, "alpha", 0f, 1f);
        fadeIn.setDuration(500);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(loadingText, "alpha", 1f, 0f);
        fadeOut.setDuration(500);
        fadeOut.setStartDelay(1000);  // Stay visible for 1 second

        animatorSet.playSequentially(fadeIn, fadeOut);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                dotCount = (dotCount + 1) % dotStates.length;
                loadingText.setText("Loading assets" + dotStates[dotCount]);
                animateDots();  // Restart the animation
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        animatorSet.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // No need to remove callbacks from handler as it's not used anymore
    }
}