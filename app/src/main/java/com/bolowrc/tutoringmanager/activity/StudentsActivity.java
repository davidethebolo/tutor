package com.bolowrc.tutoringmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bolowrc.tutoringmanager.R;
import com.bolowrc.tutoringmanager.repository.StudentRepository;

import java.text.MessageFormat;

import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.ID;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_FIRSTNAME;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_LASTNAME;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_SCHOOL;

public class StudentsActivity extends ActionBarActivity {


    public static final String EXTRA_ID = "STUDENT_ID";
    private CursorAdapter adapter;
    private ListView listview = null;
    private StudentRepository studentRepository = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        studentRepository = new StudentRepository(this);


        listview = (ListView) findViewById(R.id.listView);
        Cursor crs = studentRepository.getStudentsData();
        adapter = new CursorAdapter(this, crs, 0) {
            @Override
            public View newView(Context ctx, Cursor arg1, ViewGroup arg2) {
                return getLayoutInflater().inflate(R.layout.student_list_row, null);
            }

            @Override
            public void bindView(View v, Context arg1, Cursor crs) {
                TextView txt = (TextView) v.findViewById(R.id.studentNameTxt);
                txt.setText(getStudentMessage(crs));
                ImageButton editBtn = (ImageButton) v.findViewById(R.id.studentEditBtn);
                editBtn.setOnClickListener(editListener);
                ImageButton deleteBtn = (ImageButton) v.findViewById(R.id.studentDeleteBtn);
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

    private String getStudentMessage(Cursor crs) {
        String firstName = crs.getString(crs.getColumnIndex(STUDENT_FIRSTNAME));
        String lastName = crs.getString(crs.getColumnIndex(STUDENT_LASTNAME));
        String school = crs.getString(crs.getColumnIndex(STUDENT_SCHOOL));
        return MessageFormat.format("{0} {1}, {2}", firstName, lastName, school);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_students, menu);
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


    public void newerStudentActivity(View view) {
        Intent i = new Intent(this, StudentActivity.class);
        i.putExtra(EXTRA_ID, 0);
        startActivity(i);
        finish();
    }


    private View.OnClickListener editListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = listview.getPositionForView(v);
            long id = adapter.getItemId(position);
            Intent i = new Intent(StudentsActivity.this, StudentActivity.class);
            i.putExtra(EXTRA_ID, id);
            startActivity(i);
            finish();
        }
    };

    private View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = listview.getPositionForView(v);
            long id = adapter.getItemId(position);
            if (studentRepository.delete(id))
                adapter.changeCursor(studentRepository.getStudentsData());
        }
    };

}
