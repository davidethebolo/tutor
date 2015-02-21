package com.bolowrc.tutoringmanager.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.bolowrc.tutoringmanager.R;
import com.bolowrc.tutoringmanager.model.Lesson;
import com.bolowrc.tutoringmanager.repository.LessonRepository;

import java.util.List;


public class LessonsActivity extends ActionBarActivity {

    public static final String LESSON_EXTRA_ID = "LESSON_ID";
    private CursorAdapter adapter;
    private ListView listview = null;
    private LessonRepository lessonRepository = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        lessonRepository = new LessonRepository(this);

        listview = (ListView) findViewById(R.id.lessonsListView);
        List<Lesson> lessons = lessonRepository.getLessons();
        String[] lessonsSummary = new String[lessons.size()];
        int i=0;
        for(Lesson lesson: lessons){
            lessonsSummary[i++] = lesson.toString();
        }
        ListAdapter lessonsAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, lessonsSummary);
        listview.setAdapter(lessonsAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lessons, menu);
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


    public void newerLessonActivity(View view) {
        Intent i = new Intent(this, LessonActivity.class);
        i.putExtra(LESSON_EXTRA_ID, 0);
        startActivity(i);
        finish();
    }



}
