package com.bolowrc.tutoringmanager.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.bolowrc.tutoringmanager.R;
import com.bolowrc.tutoringmanager.model.Lesson;
import com.bolowrc.tutoringmanager.model.Student;
import com.bolowrc.tutoringmanager.repository.LessonRepository;
import com.bolowrc.tutoringmanager.repository.RepositoryException;
import com.bolowrc.tutoringmanager.repository.StudentRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;


public class LessonActivity extends ActionBarActivity {

private static final    String[] hour = {"0.5", "1", "1.5", "2"};

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
        initHourSpinner();

        lessonRepository = new LessonRepository(this);
    }


    public void saveLesson(View v) {
        CheckBox paid = (CheckBox) findViewById(R.id.lessonPaidCheckBox);
        Spinner studentSpinner = getSpinner(R.id.lessonStudentSpinner);Spinner hourSpinner = getSpinner(R.id.lessonHourSpinner);

        long studentId = students.get(studentSpinner.getSelectedItemPosition()).getId();
        double hours = Double.valueOf(hour[hourSpinner.getSelectedItemPosition()]);
        double amount = Double.valueOf(((EditText) findViewById(R.id.lessonAmountEditText)).getText().toString());

        DatePicker datePicker = (DatePicker) findViewById(R.id.lessonDatePicker);
        int dayOfMonth = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Calendar date = new GregorianCalendar(year, month, dayOfMonth);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


        Lesson lesson = new Lesson(studentId,simpleDateFormat.format(date.getTime()), amount, hours, paid.isChecked());
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
        Spinner spinner = getSpinner(R.id.lessonStudentSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item,
                getStudentsSummary());
        spinner.setAdapter(adapter);
    }

    private void initHourSpinner() {
        Spinner hourSpinner = getSpinner(R.id.lessonHourSpinner);
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, hour);
        hourSpinner.setAdapter(adapter);

    }




    private Spinner getSpinner(int id) {
        return (Spinner) findViewById(id);
    }


}
