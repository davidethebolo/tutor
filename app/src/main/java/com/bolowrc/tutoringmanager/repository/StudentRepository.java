package com.bolowrc.tutoringmanager.repository;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.bolowrc.tutoringmanager.model.Student;

import java.util.ArrayList;
import java.util.List;

import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.ID;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_FIRSTNAME;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_LASTNAME;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_SCHOOL;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_TBL;

public class StudentRepository extends Repository {


    public StudentRepository(Context ctx) {
        dbhelper = new SchemaRepository(ctx);
    }


    public boolean delete(long id) {
        return delete(id, STUDENT_TBL);
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

    public Cursor getStudentsData() {
        return executeQuery(STUDENT_TBL, null, null);
    }

    public Cursor getStudentData(long id) {
        return executeQuery(STUDENT_TBL, ID + "=?", new String[]{Long.toString(id)});
    }

    public List<Student> getStudents() {
        List<Student> studentsInfo = new ArrayList<Student>();
        Cursor students = getStudentsData();
        if (students.moveToFirst()) {
            while (!students.isAfterLast()) {
                studentsInfo.add(new Student(getLongFieldValue(students, ID), getStringFieldValue(students, STUDENT_FIRSTNAME), getStringFieldValue(students, STUDENT_LASTNAME), getStringFieldValue(students, STUDENT_SCHOOL)));
                students.moveToNext();
            }
        }
        return studentsInfo;
    }

    public Student getStudent(long id) {
        Cursor students = getStudentData(id);
        if (students.isAfterLast()) return null;
        students.moveToFirst();
        return new Student(getLongFieldValue(students, ID), getStringFieldValue(students, STUDENT_FIRSTNAME), getStringFieldValue(students, STUDENT_LASTNAME), getStringFieldValue(students, STUDENT_SCHOOL));
    }

}
