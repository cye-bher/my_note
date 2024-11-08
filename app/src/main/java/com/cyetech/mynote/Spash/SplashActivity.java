package com.cyetech.mynote.Spash;
// java/com/yourpackage/SplashActivity.java


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.cyetech.mynote.MainActivity;
import com.cyetech.mynote.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Delay of 6 seconds (6000 milliseconds)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start MainActivity after 6 seconds
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close SplashActivity
            }
        }, 6000); // 6000 milliseconds = 6 seconds
    }
}
