package com.bolowrc.tutoringmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.bolowrc.tutoringmanager.R;


public class SplashActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int i = 0;
                    while (i++ < 2) {
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                } finally {
                    closeMainActivity();
                    try {
                        join();
                    } catch (InterruptedException e) {
                    }
                }
            }

        };
        splashTread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    private void closeMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
