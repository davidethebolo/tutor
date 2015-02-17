package com.bolowrc.tutoringmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.bolowrc.tutoringmanager.R;
import com.bolowrc.tutoringmanager.repository.LessonRepository;


public class LessonsActivity extends ActionBarActivity {

    public static final String LESSON_EXTRA_ID = "LESSON_ID";
    private CursorAdapter adapter;
    private ListView listview = null;
    private LessonRepository lessonRepository = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        lessonRepository = new LessonRepository(this);

//
//        listview = (ListView) findViewById(R.id.listView);
//        Cursor crs = studentRepository.getStudents();
//        adapter = new CursorAdapter(this, crs, 0) {
//            @Override
//            public View newView(Context ctx, Cursor arg1, ViewGroup arg2) {
//                return getLayoutInflater().inflate(R.layout.student_list_row, null);
//            }
//
//            @Override
//            public void bindView(View v, Context arg1, Cursor crs) {
//                TextView txt = (TextView) v.findViewById(R.id.studentNameTxt);
//                txt.setText(getStudentMessage(crs));
//                ImageButton editBtn = (ImageButton) v.findViewById(R.id.studentEditBtn);
//                editBtn.setOnClickListener(editListener);
//                ImageButton deleteBtn = (ImageButton) v.findViewById(R.id.studentDeleteBtn);
//                deleteBtn.setOnClickListener(deleteListener);
//            }
//
//            @Override
//            public long getItemId(int position) {
//                Cursor crs = adapter.getCursor();
//                crs.moveToPosition(position);
//                return crs.getLong(crs.getColumnIndex(ID));
//            }
//        };
//
//        listview.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lessons, menu);
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
            Intent i = new Intent(LessonsActivity.this, LessonActivity.class);
            i.putExtra(LESSON_EXTRA_ID, id);
            startActivity(i);
            finish();
        }
    };

    private View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = listview.getPositionForView(v);
            long id = adapter.getItemId(position);
            if (lessonRepository.delete(id))
                adapter.changeCursor(lessonRepository.getLessons());
        }
    };


}
