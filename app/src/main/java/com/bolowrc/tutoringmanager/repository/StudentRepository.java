package com.bolowrc.tutoringmanager.repository;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

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

    public void save(String firstName, String lastName, String school) throws RepositoryException {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(STUDENT_FIRSTNAME, firstName);
        cv.put(STUDENT_LASTNAME, lastName);
        cv.put(STUDENT_SCHOOL, school);
        try {
            db.insert(STUDENT_TBL, null, cv);
        } catch (SQLiteException sqle) {
            throw new RepositoryException(sqle);
        }
    }

    public boolean delete(long id) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try {
            return (db.delete(STUDENT_TBL, ID + "=?", new String[]{Long.toString(id)}) > 0);
        } catch (SQLiteException sqle) {
            return false;
        }

    }

    public Cursor getStudents() {
        Cursor crs = null;
        try {
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            crs = db.query(STUDENT_TBL, null, null, null, null, null, null, null);
        } catch (SQLiteException sqle) {
            return null;
        }
        return crs;
    }


}
