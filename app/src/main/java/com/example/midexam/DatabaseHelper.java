package com.example.midexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MidExam.db";
    private static final int DB_VERSION = 2;

    private static final String TABLE_NAME = "students";
    private static final String COL_ID = "student_id";
    private static final String COL_NAME = "student_name";
    private static final String COL_ENROLLED = "enrolled";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // student_id is UNIQUE, so the same ID cannot be saved twice
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID + " TEXT NOT NULL UNIQUE, "
                + COL_NAME + " TEXT, "
                + COL_ENROLLED + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // ADD - returns false if the student ID already exists
    public boolean addStudent(String id, String name, boolean enrolled) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID, id);
        values.put(COL_NAME, name);
        values.put(COL_ENROLLED, enrolled ? 1 : 0);

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // VIEW - all students (column 0 = ID, 1 = name, 2 = enrolled)
    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COL_ID + ", " + COL_NAME + ", " + COL_ENROLLED
                + " FROM " + TABLE_NAME, null);
    }

    // FIND - search by student ID or by name
    public Cursor findStudent(String key) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COL_ID + ", " + COL_NAME + ", " + COL_ENROLLED
                + " FROM " + TABLE_NAME
                + " WHERE " + COL_ID + " = ? OR " + COL_NAME + " = ?", new String[]{key, key});
    }

    // EDIT - update name and enrolled by student ID
    public boolean updateStudent(String id, String name, boolean enrolled) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_ENROLLED, enrolled ? 1 : 0);

        int rows = db.update(TABLE_NAME, values, COL_ID + " = ?", new String[]{id});
        return rows > 0;
    }

    // DELETE - by student ID or by name
    public boolean deleteStudent(String key) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, COL_ID + " = ? OR " + COL_NAME + " = ?", new String[]{key, key});
        return rows > 0;
    }
}