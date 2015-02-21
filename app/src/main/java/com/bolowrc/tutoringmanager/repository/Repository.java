package com.bolowrc.tutoringmanager.repository;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import static com.bolowrc.tutoringmanager.repository.DatabaseStrings.ID;

public class Repository {

    SchemaRepository dbhelper;

    Cursor executeQuery(String table, String selection, String[] selectionArgs) {
        try {
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            return db.query(table, null, selection, selectionArgs, null, null, null, null);
        } catch (SQLiteException sqle) {
            return null;
        }
    }

    boolean delete(long id, String table) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try {
            return (db.delete(table, ID + "=?", new String[]{Long.toString(id)}) > 0);
        } catch (SQLiteException sqle) {
            return false;
        }
    }

 String getStringFieldValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }

    long getLongFieldValue(Cursor cursor, String field) {
        return cursor.getLong(cursor.getColumnIndex(field));
    }
    double getDoubleFieldValue(Cursor cursor, String field) {
        return cursor.getDouble(cursor.getColumnIndex(field));
    }
    boolean getBooleanFieldValue(Cursor cursor, String field) {
        return cursor.getInt(cursor.getColumnIndex(field))==1;
    }


}
