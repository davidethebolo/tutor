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

import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.ID;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_FIRSTNAME;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_LASTNAME;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_SCHOOL;

public class StudentsActivity extends ActionBarActivity {


    private CursorAdapter adapter;
    private ListView listview = null;
    private StudentRepository studentRepository = null;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = listview.getPositionForView(v);
            long id = adapter.getItemId(position);
            if (studentRepository.delete(id))
                adapter.changeCursor(studentRepository.getStudents());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        studentRepository = new StudentRepository(this);


        listview = (ListView) findViewById(R.id.listView);
        Cursor crs = studentRepository.getStudents();
        adapter = new CursorAdapter(this, crs, 0) {
            @Override
            public View newView(Context ctx, Cursor arg1, ViewGroup arg2) {
                View v = getLayoutInflater().inflate(R.layout.listactivity_row, null);
                return v;
            }

            @Override
            public void bindView(View v, Context arg1, Cursor crs) {
                String firstName = crs.getString(crs.getColumnIndex(STUDENT_FIRSTNAME));
                String lastName = crs.getString(crs.getColumnIndex(STUDENT_LASTNAME));
                String school = crs.getString(crs.getColumnIndex(STUDENT_SCHOOL));
                TextView txt = (TextView) v.findViewById(R.id.txt_firstName);
                txt.setText(firstName);
                txt = (TextView) v.findViewById(R.id.txt_lastName);
                txt.setText(lastName);
                txt = (TextView) v.findViewById(R.id.txt_school);
                txt.setText(school);
                ImageButton imgbtn = (ImageButton) v.findViewById(R.id.btn_delete);
                imgbtn.setOnClickListener(clickListener);
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
        startActivity(i);
    }


}
