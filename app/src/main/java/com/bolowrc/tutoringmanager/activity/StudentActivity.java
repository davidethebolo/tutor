package com.bolowrc.tutoringmanager.activity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bolowrc.tutoringmanager.R;
import com.bolowrc.tutoringmanager.repository.RepositoryException;
import com.bolowrc.tutoringmanager.repository.StudentRepository;

import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.ID;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_FIRSTNAME;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_LASTNAME;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_SCHOOL;

public class StudentActivity extends ActionBarActivity {

    private StudentRepository db = null;
    private CursorAdapter adapter;
    private ListView listview = null;
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = listview.getPositionForView(v);
            long id = adapter.getItemId(position);
            if (db.delete(id))
                adapter.changeCursor(db.getStudents());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        db = new StudentRepository(this);
        listview = (ListView) findViewById(R.id.listView);
        Cursor crs = db.getStudents();
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

    public void salva(View v) {
        EditText firstName = (EditText) findViewById(R.id.firstName);
        EditText lastName = (EditText) findViewById(R.id.lastName);
        EditText school = (EditText) findViewById(R.id.school);
        if (firstName.length() > 0 && lastName.length() > 0) {
            try {
                db.save(firstName.getEditableText().toString(), lastName.getEditableText().toString(), school.getEditableText().toString());
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
            adapter.changeCursor(db.getStudents());
        }
    }

}
