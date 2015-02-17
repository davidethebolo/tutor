package com.bolowrc.tutoringmanager.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.bolowrc.tutoringmanager.R;
import com.bolowrc.tutoringmanager.repository.LessonRepository;
import com.bolowrc.tutoringmanager.repository.StudentRepository;


public class LessonActivity extends ActionBarActivity {

    private LessonRepository lessonRepository = null;
    private StudentRepository studentRepository;

    private long id = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        studentRepository = new StudentRepository(this);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item,
                studentRepository.getStudentsSummary());
        spinner.setAdapter(adapter);

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
