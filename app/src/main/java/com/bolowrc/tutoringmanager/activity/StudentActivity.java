package com.bolowrc.tutoringmanager.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import com.bolowrc.tutoringmanager.R;
import com.bolowrc.tutoringmanager.model.Student;
import com.bolowrc.tutoringmanager.repository.RepositoryException;
import com.bolowrc.tutoringmanager.repository.StudentRepository;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static com.bolowrc.tutoringmanager.activity.StudentsActivity.EXTRA_ID;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_FIRSTNAME;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_LASTNAME;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_SCHOOL;

public class StudentActivity extends ActionBarActivity {

    private StudentRepository studentRepository = null;
    private long id = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        studentRepository = new StudentRepository(this);
        Intent i = getIntent();
        id = i.getExtras().getLong(EXTRA_ID);
        if (id != 0L) {
            Cursor crs = studentRepository.getStudent(id);
            crs.moveToFirst();
            editText(R.id.firstNameInput).setText(crs.getString(crs.getColumnIndex(STUDENT_FIRSTNAME)));
            editText(R.id.lastNameInput).setText(crs.getString(crs.getColumnIndex(STUDENT_LASTNAME)));
            editText(R.id.schoolInput).setText(crs.getString(crs.getColumnIndex(STUDENT_SCHOOL)));
        }
    }

    public void saveStudent(View v) {
        if (!getStringFrom(R.id.firstNameInput).isEmpty() && !getStringFrom(R.id.schoolInput).isEmpty()) {
            try {
                save(getStringFrom(R.id.firstNameInput), getStringFrom(R.id.lastNameInput), getStringFrom(R.id.schoolInput));
                makeText(getApplicationContext(), getString(R.string.studentSaved), LENGTH_LONG).show();
                Intent i = new Intent(this, StudentsActivity.class);
                startActivity(i);
            } catch (RepositoryException e) {
                makeText(getApplicationContext(), getString(R.string.problemsSavingStudent), LENGTH_LONG).show();
            }
        } else {
            makeText(getApplicationContext(), getString(R.string.validationSavingStudent), LENGTH_LONG).show();
        }
    }

    private void save(String firstName, String lastName, String school) throws RepositoryException {
        Student student = new Student(id, firstName, lastName, school);
        studentRepository.saveOrUpdate(student);

    }

    private String getStringFrom(int firstNameTextId) {
        return editText(firstNameTextId).getEditableText().toString();
    }

    private EditText editText(int firstNameTextId) {
        return (EditText) findViewById(firstNameTextId);
    }

}
