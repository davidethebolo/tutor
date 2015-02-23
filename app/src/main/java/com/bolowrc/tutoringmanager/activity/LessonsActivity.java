package com.bolowrc.tutoringmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bolowrc.tutoringmanager.R;
import com.bolowrc.tutoringmanager.model.Lesson;
import com.bolowrc.tutoringmanager.model.Student;
import com.bolowrc.tutoringmanager.repository.DatabaseStrings;
import com.bolowrc.tutoringmanager.repository.LessonRepository;
import com.bolowrc.tutoringmanager.repository.StudentRepository;

import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.ID;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.LESSON_STUDENT_ID;


public class LessonsActivity extends ActionBarActivity {

    public static final String LESSON_EXTRA_ID = "LESSON_ID";
    private CursorAdapter adapter;
    private ListView listview = null;
    private LessonRepository lessonRepository;
    private StudentRepository studentRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        lessonRepository = new LessonRepository(this);
        studentRepository = new StudentRepository(this);

        listview = (ListView) findViewById(R.id.lessonsListView);

        Cursor crs = lessonRepository.getLessonsData();
        adapter = new CursorAdapter(this, crs, 0) {
            @Override
            public View newView(Context ctx, Cursor arg1, ViewGroup arg2) {
                return getLayoutInflater().inflate(R.layout.lesson_list_row, null);
            }

            @Override
            public void bindView(View v, Context arg1, Cursor crs) {

                Lesson lesson = lessonRepository.getLesson(crs.getLong(crs.getColumnIndex(DatabaseStrings.ID)));

                setText(v, getStudentSummary(crs), R.id.lessonStudentName);
                setText(v, lesson.getPrintableDate(), R.id.lessonDate);
                setText(v, lesson.getPrintableAmount(), R.id.lessonAmount);
                setText(v, lesson.getPrintableHour(), R.id.lessonHour);
                CheckBox paid = (CheckBox) v.findViewById(R.id.lessonPaid);
                paid.setChecked(lesson.isPaid());

                ImageButton editBtn = (ImageButton) v.findViewById(R.id.lessonEditBtn);
                editBtn.setOnClickListener(editListener);
                ImageButton deleteBtn = (ImageButton) v.findViewById(R.id.lessonDeleteBtn);
                deleteBtn.setOnClickListener(deleteListener);
            }

            @Override
            public long getItemId(int position) {
                Cursor crs = adapter.getCursor();
                crs.moveToPosition(position);
                return crs.getLong(crs.getColumnIndex(ID));
            }
        };
        listview.setAdapter(adapter);
    }

    private void setText(View v, String text, int id) {
        TextView dateTextView = (TextView) v.findViewById(id);
        dateTextView.setText(text);
    }

    private String getStudentSummary(Cursor crs) {
        Student student = studentRepository.getStudent(crs.getLong(crs.getColumnIndex(LESSON_STUDENT_ID)));
        return student != null ? student.getSummary() : getString(R.string.studentNotAvailable);
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


    private View.OnClickListener editListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = listview.getPositionForView(v);
            long id = adapter.getItemId(position);
            finish();
        }
    };

    private View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = listview.getPositionForView(v);
            long id = adapter.getItemId(position);
            if (lessonRepository.delete(id))
                adapter.changeCursor(lessonRepository.getLessonsData());
        }
    };


}
