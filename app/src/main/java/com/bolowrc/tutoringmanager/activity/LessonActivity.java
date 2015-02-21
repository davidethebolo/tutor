package com.bolowrc.tutoringmanager.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.bolowrc.tutoringmanager.R;
import com.bolowrc.tutoringmanager.model.Lesson;
import com.bolowrc.tutoringmanager.model.Student;
import com.bolowrc.tutoringmanager.repository.LessonRepository;
import com.bolowrc.tutoringmanager.repository.RepositoryException;
import com.bolowrc.tutoringmanager.repository.StudentRepository;

import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;


public class LessonActivity extends ActionBarActivity {

    private LessonRepository lessonRepository = null;
    private StudentRepository studentRepository;

    private long id = 0L;
    List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        studentRepository = new StudentRepository(this);
        students = studentRepository.getStudentsSummary();
        initStudentSpinner();

        lessonRepository = new LessonRepository(this);
    }


    public void saveLesson(View v) {
        CheckBox paid = (CheckBox) findViewById(R.id.paidCheckBox);
        Spinner studentSpinner = getStudentSpinner();
        studentSpinner.getSelectedItemPosition();
        Lesson lesson = new Lesson(students.get(studentSpinner.getSelectedItemPosition()).getId(), paid.isChecked());
        try {
            lessonRepository.saveOrUpdate(lesson);
            makeText(getApplicationContext(), getString(R.string.lessonSaved), LENGTH_LONG).show();
        } catch (RepositoryException e) {
            makeText(getApplicationContext(), getString(R.string.problemsSavingLesson), LENGTH_LONG).show();
        }
    }


    private String getStringFrom(int firstNameTextId) {
        return editText(firstNameTextId).getEditableText().toString();
    }

    private EditText editText(int firstNameTextId) {
        return (EditText) findViewById(firstNameTextId);
    }

    private String[] getStudentsSummary() {
        String[] studentsSummary = new String[students.size()];
        int i = 0;
        for (Student student : students) {
            studentsSummary[i++] = student.getSummary();
        }
        return studentsSummary;
    }

    private void initStudentSpinner() {
        Spinner spinner = getStudentSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item,
                getStudentsSummary());
        spinner.setAdapter(adapter);
    }

    private Spinner getStudentSpinner() {
        return (Spinner) findViewById(R.id.spinner);
    }


}
