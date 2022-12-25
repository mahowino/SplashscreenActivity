package com.example.splashscreenactivity.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.splashscreenactivity.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread splashscreen=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    Intent intent=new Intent(getApplicationContext(), PhoneNumberActivity.class);
                    startActivity(intent);
                    finish();

                }catch (Exception e){
                   e.printStackTrace();
                }
            }
        };

        splashscreen.start();
    }
}