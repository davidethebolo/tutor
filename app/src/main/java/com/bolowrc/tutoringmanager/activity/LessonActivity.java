package com.bolowrc.tutoringmanager.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import com.bolowrc.tutoringmanager.R;
import com.bolowrc.tutoringmanager.repository.LessonRepository;


public class LessonActivity extends ActionBarActivity {

    private LessonRepository lessonRepository = null;
    private long id = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        lessonRepository = new LessonRepository(this);
    }

    public void saveLesson(View v) {
    }


    private String getStringFrom(int firstNameTextId) {
        return editText(firstNameTextId).getEditableText().toString();
    }

    private EditText editText(int firstNameTextId) {
        return (EditText) findViewById(firstNameTextId);
    }
}
