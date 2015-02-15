package com.bolowrc.tutoringmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import com.bolowrc.tutoringmanager.R;
import com.bolowrc.tutoringmanager.repository.RepositoryException;
import com.bolowrc.tutoringmanager.repository.StudentRepository;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class StudentActivity extends ActionBarActivity {

    private StudentRepository studentRepository = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        studentRepository = new StudentRepository(this);
    }

    public void saveStudent(View v) {
        if (!getStringFrom(R.id.firstNameInput).isEmpty() && !getStringFrom(R.id.schoolInput).isEmpty()) {
            try {
                studentRepository.save(getStringFrom(R.id.firstNameInput), getStringFrom(R.id.lastNameInput), getStringFrom(R.id.schoolInput));
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

    private String getStringFrom(int firstNameTextId) {
        return ((EditText) findViewById(firstNameTextId)).getEditableText().toString();
    }

}
