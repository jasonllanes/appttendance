package com.example.applicationattendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class SubjectandStudent extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "StudentDatabase.db";
    public static final String TABLE_TEACHER = "TEACHERNAME";
    public static final String TABLE_SUBJECT = "SUBJECTS_TABLE";
    public static final String TABLE_STUDENT = "STUDENTS_TABLE";

    public static final String COL_0 = "TEACHER";
    public static final String COL_00 = "CREATED";
    public static final String COL_1 = "ID";
    public static final String COL_01 = "STUDENT_ID";
    public static final String COL_11 = "SUBJECT_ID";
    public static final String COL_2 = "SUBJECTS";
    public static final String COL_3 = "STUDENT";
    public static final String COL_12= "ATTENDANCE";
    public static final String COL_13= "TIME";
    public String length ;
    public String name;
    public SubjectandStudent(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TEACHER + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,TEACHER TEXT,CREATED TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_SUBJECT + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,SUBJECTS TEXT,TIME TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_STUDENT + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,SUBJECT_ID INTEGER,STUDENT TEXT,ATTENDANCE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }
    public boolean addTeacher(String teachername){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_0,teachername);
        contentValues.put(COL_00,"DONE");
        long result = db.insert(TABLE_TEACHER,null,contentValues);
        if(result == -1 ){
            return false;
        }else {
            return true;
        }
    }
    public boolean addSub(String subject,String time){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2,subject);
            contentValues.put(COL_13,time);
            long result = db.insert(TABLE_SUBJECT,null,contentValues);
            if(result == -1 ){
                return false;
            }else {
                return true;
            }
    }
    public boolean addStudent(String subject_id,String student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_11,subject_id);
        contentValues.put(COL_3,student);
        contentValues.put(COL_12,"Absent");
        long result = db.insert(TABLE_STUDENT,null,contentValues);
        if(result == -1 ){
            return false;
        }else {
            return true;
        }
    }
    public boolean updateAttendance(String id,String attendance){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_12,attendance);
        long result = myDB.update(TABLE_STUDENT, contentValues,"ID = ?",new String [] {id});
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }
    public boolean updateSubject(String id,String subject,String time){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,subject);
        contentValues.put(COL_13,time);
        long result = myDB.update(TABLE_SUBJECT, contentValues,"ID = ?",new String [] {id});
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }
    public boolean updateName(String id,String name){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_0,name);
        long result = myDB.update(TABLE_TEACHER, contentValues,"ID = ?",new String [] {id});
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }
    public boolean updateStudent(String id,String name){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3,name);
        long result = myDB.update(TABLE_STUDENT, contentValues,"ID = ?",new String [] {id});
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }
    public boolean deleteDataSubject(String id){
        SQLiteDatabase myDB = this.getWritableDatabase();
        long result = myDB.delete(TABLE_SUBJECT, COL_1 + "=?", new String[]{id});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean deleteDataStudent(String id){
        SQLiteDatabase myDB = this.getWritableDatabase();
        long result = myDB.delete(TABLE_STUDENT, COL_1 + "=?", new String[]{id});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public int getLength(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT STUDENTS FROM " + TABLE_STUDENT + " WHERE SUBJECT_ID = " + id;
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();
        length = String.valueOf(count);
        cursor.close();
        return count;

    }
    public ArrayList<HashMap<String, String>> getAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> bookList = new ArrayList<>();
        String query = "SELECT ID, SUBJECTS, TIME FROM "+ TABLE_SUBJECT;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("ID",cursor.getString(cursor.getColumnIndex(COL_1)));
            user.put("SUBJECTS",cursor.getString(cursor.getColumnIndex(COL_2)));
            user.put("TIME",cursor.getString(cursor.getColumnIndex(COL_13)));
            bookList.add(user);
        }
        return bookList;
    }

    public ArrayList<HashMap<String, String>> getStudents(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> bookList = new ArrayList<>();
        String query = "SELECT ID,STUDENT,ATTENDANCE FROM "+ TABLE_STUDENT +" WHERE SUBJECT_ID = " + id;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("id",cursor.getString(cursor.getColumnIndex(COL_1)));
            user.put("student",cursor.getString(cursor.getColumnIndex(COL_3)));
            user.put("attendance",cursor.getString(cursor.getColumnIndex(COL_12)));
            bookList.add(user);
        }
        return bookList;
    }
    public String checkTeacherName() {
        SQLiteDatabase myDB = this.getWritableDatabase();
        // checks if there's an access key in preferences
        Cursor res = myDB.rawQuery("select * from " + TABLE_TEACHER , null);
        if(res.moveToFirst()){
            name = res.getString(res.getColumnIndex("TEACHER"));
        }else{
            name = "";
        }
        return name;
    }
    public Cursor checkSub() {
        SQLiteDatabase myDB = this.getWritableDatabase();
        // checks if there's an access key in preferences
        Cursor res = myDB.rawQuery("select * from " + TABLE_SUBJECT , null);
        return res;
    }
    public Cursor checkForAccount() {
        SQLiteDatabase myDB = this.getWritableDatabase();
        // checks if there's an access key in preferences
        Cursor res = myDB.rawQuery("select * from " + TABLE_TEACHER, null);
        return res;
    }
    public boolean subjectExist(String createdSubject) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor res = myDB.query(TABLE_SUBJECT, new String [] {COL_2},COL_2 + "=? " , new String[]{createdSubject},null,null,null,null);
        if(res.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }
    public boolean studentExist(String createdStudent,String id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor res = myDB.rawQuery("select * from " + TABLE_STUDENT + " WHERE SUBJECT_ID=" + "=?"+ " AND " + " STUDENT =" + "=?"  , new String[]{id,createdStudent});
//        Cursor res = myDB.query(TABLE_STUDENT, new String [] {COL_3},COL_3+"=?",new String[] {createdStudent},null,null,null,null);
        if(res.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }
}
