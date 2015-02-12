package com.bolowrc.tutoringmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bolowrc.tutoringmanager.R;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toLessons(View v) {
        Intent i = new Intent(this, LessonsActivity.class);
        startActivity(i);
    }

    public void toStudents(View v) {
        Intent i = new Intent(this, StudentsActivity.class);
        startActivity(i);
    }

    public void toStatistics(View v) {
        Intent i = new Intent(this, StatisticsActivity.class);
        startActivity(i);
    }

}
