package com.bolowrc.tutoringmanager.repository;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.bolowrc.tutoringmanager.model.Student;

import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.ID;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_FIRSTNAME;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_LASTNAME;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_SCHOOL;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_TBL;

public class StudentRepository {

    private SchemaRepository dbhelper;

    public StudentRepository(Context ctx) {
        dbhelper = new SchemaRepository(ctx);
    }


    public boolean delete(long id) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try {
            return (db.delete(STUDENT_TBL, ID + "=?", new String[]{Long.toString(id)}) > 0);
        } catch (SQLiteException sqle) {
            return false;
        }
    }

    public void saveOrUpdate(Student student) throws RepositoryException {
        if (student.getId() == 0) save(student);
        else edit(student);
    }

    private void edit(Student student) throws RepositoryException {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try {
            db.update(STUDENT_TBL, getContentValues(student), ID + "=?", new String[]{student.getTextId()});
        } catch (SQLiteException sqle) {
            throw new RepositoryException(sqle);
        }
    }


    private void save(Student student) throws RepositoryException {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try {
            db.insert(STUDENT_TBL, null, getContentValues(student));
        } catch (SQLiteException sqle) {
            throw new RepositoryException(sqle);
        }
    }

    private ContentValues getContentValues(Student student) {
        ContentValues cv = new ContentValues();
        cv.put(STUDENT_FIRSTNAME, student.getFirstName());
        cv.put(STUDENT_LASTNAME, student.getLastName());
        cv.put(STUDENT_SCHOOL, student.getSchool());
        return cv;
    }

    public Cursor getStudents() {
        return executeQuery(null, null);
    }

    public Cursor getStudent(long id) {
        return executeQuery(ID + "=?", new String[]{Long.toString(id)});
    }

    private Cursor executeQuery(String selection, String[] selectionArgs) {
        try {
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            return db.query(STUDENT_TBL, null, selection, selectionArgs, null, null, null, null);
        } catch (SQLiteException sqle) {
            return null;
        }
    }


}
