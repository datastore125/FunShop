package com.example.animeshpatra.funshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    protected boolean _active = true;
    protected int _splashTime = 2000; // time to display the splash screen in ms
    public static final String DEFAULT = "No data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (Exception e) {

                } finally {

                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    String name = sharedPreferences.getString("Name", DEFAULT);
                    String password = sharedPreferences.getString("Password", DEFAULT);
                    String phone = sharedPreferences.getString("PhoneNo", DEFAULT);
                    String email = sharedPreferences.getString("Email", DEFAULT);
                    if (email.equals(DEFAULT) && password.equals(DEFAULT)){
                        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    }else {
                        Intent intent = new Intent(SplashScreen.this, CategoryTabActivity.class);
                        startActivity(intent);
                    }
                    //startActivity(new Intent(SplashScreen.this,
                      //      LoginActivity.class));
                    //finish();
                }
            };
        };
        splashTread.start();
    }
}
