package com.example.statefull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new DatabaseManager(this);
        TextView tv = findViewById(R.id.secondary_text);
        String[] splashes = getResources().getStringArray(R.array.splash_quotes);
        Random rand = new Random();
        String j = splashes[rand.nextInt(splashes.length)];
        tv.setText(j);
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                    Log.d("isitLogged", isitLogged() ? "true" : "false");
                    if (isitLogged()) {
                        Log.d("Calling", "MindActivity");
                        Intent intent = new Intent(SplashActivity.this, MindActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.d("Calling", "MainActivity");
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                }
            }
        };
        thread.start();
    }

    public boolean isitLogged() {
        return DatabaseManager.databaseManager.logged();

    }
}