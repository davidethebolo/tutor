package com.bolowrc.tutoringmanager.repository;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.bolowrc.tutoringmanager.model.Lesson;

import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.ID;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.LESSON_AMOUNT;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.LESSON_DATE;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.LESSON_HOUR;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.LESSON_PAID;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.LESSON_STUDENT_ID;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.LESSON_TBL;

public class LessonRepository extends Repository {


    public LessonRepository(Context ctx) {
        dbhelper = new SchemaRepository(ctx);
    }


    public boolean delete(long id) {
        return delete(id, LESSON_TBL);
    }


    public void saveOrUpdate(Lesson lesson) throws RepositoryException {
        if (lesson.getId() == 0) save(lesson);
        else edit(lesson);
    }

    private void edit(Lesson lesson) throws RepositoryException {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try {
            db.update(LESSON_TBL, getContentValues(lesson), ID + "=?", new String[]{lesson.getTextId()});
        } catch (SQLiteException sqle) {
            throw new RepositoryException(sqle);
        }
    }


    private void save(Lesson lesson) throws RepositoryException {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try {
            db.insert(LESSON_TBL, null, getContentValues(lesson));
        } catch (SQLiteException sqle) {
            throw new RepositoryException(sqle);
        }
    }


    public Cursor getLessons() {
        return executeQuery(LESSON_TBL, null, null);
    }

    public Cursor getLesson(long id) {
        return executeQuery(LESSON_TBL, ID + "=?", new String[]{Long.toString(id)});
    }

    private ContentValues getContentValues(Lesson lesson) {
        ContentValues cv = new ContentValues();
        cv.put(LESSON_STUDENT_ID, lesson.getStudentId());
        cv.put(LESSON_DATE, lesson.getDate());
        cv.put(LESSON_HOUR, lesson.getHour());
        cv.put(LESSON_AMOUNT, lesson.getAmount());
        cv.put(LESSON_PAID, lesson.isPaid());
        return cv;
    }

}
