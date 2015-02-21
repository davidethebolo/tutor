package com.bolowrc.tutoringmanager.repository;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.ID;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.LESSON_AMOUNT;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.LESSON_DATE;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.LESSON_HOUR;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.LESSON_PAID;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.LESSON_STUDENT_ID;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.LESSON_TBL;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_FIRSTNAME;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_LASTNAME;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_SCHOOL;
import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.STUDENT_TBL;

public class SchemaRepository extends SQLiteOpenHelper {
    public static final String DBNAME = "TUTORING_MANAGER";

    public SchemaRepository(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String studentTable = "CREATE TABLE " + STUDENT_TBL +
                " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                STUDENT_FIRSTNAME + " TEXT," +
                STUDENT_LASTNAME + " TEXT," +
                STUDENT_SCHOOL + " TEXT)";
        db.execSQL(studentTable);
        String lessonTable = "CREATE TABLE " + LESSON_TBL +
                " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                LESSON_STUDENT_ID + " INTEGER," +
                LESSON_DATE + " TEXT," +
                LESSON_HOUR + " DOUBLE," +
                LESSON_AMOUNT + " DOUBLE," +
                LESSON_PAID + " BOOLEAN)";
        db.execSQL(lessonTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}