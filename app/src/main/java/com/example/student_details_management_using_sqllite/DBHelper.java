package com.example.student_details_management_using_sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    //It will create the datebase with the version 1.
    public DBHelper(Context context) {
        super(context, "Student.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Student(name TEXT primary key, contact TEXT, dob TEXT, gpa TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists Student");
    }

    // INSERT
    public boolean insertStudentData(int gpa, String name, String contact, String dob){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);
        contentValues.put("gpa", String.valueOf(gpa));
        long result = db.insert("Student", null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    // UPDATE
    public boolean updateStudentData(String name, String contact, String dob){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);
        Cursor cursor = db.rawQuery("SELECT * FROM Student WHERE name = ?", new String[] {name});
        if(cursor.getCount() > 0){
            long result = db.update("Student", contentValues, "name=?", new String[] {name} );
            if(result == -1){
                return false;
            }
            else{
                return true;
            }
        }
        else{
            return false;
        }
    }

    // DELETE
    public boolean deleteStudentData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Student WHERE name=?", new String[] {name});
        if(cursor.getCount() > 0){
            long result = db.delete("Student", "name=?", new String[] {name});
            if(result == -1){
                return false;
            }
            else{
                return true;
            }
        }
        else{
            return false;
        }
    }

    // READ
    public Cursor readStudentData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Student", null);
        return cursor;
    }

}