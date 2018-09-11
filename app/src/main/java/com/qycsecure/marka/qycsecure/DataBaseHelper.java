package com.qycsecure.marka.qycsecure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "QycSecure";

    private static final String TABLE_NAME = "Company";
    private static final String KEY_ID = "Id";
    private static final String KEY_NAME = "Name";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_IMAGE = "Image";
    private static final String KEY_OCCUPATION = "Occupation";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_USERNAME + " TEXT," + KEY_PASSWORD + " TEXT,"
                + KEY_OCCUPATION + " TEXT," + KEY_IMAGE + " BLOB" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(String name, String occupation, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_OCCUPATION, occupation);
        values.put(KEY_IMAGE, image);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void addAdmin(String name, String username, String password, String occupation, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_USERNAME, username);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_OCCUPATION, occupation);
        values.put(KEY_IMAGE, image);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public int updateUser(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        return db.update(TABLE_NAME, values, KEY_ID + " =?", new String[]{String.valueOf(id)});
    }

    public int upgradeUser(int id, String name, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, username);
        values.put(KEY_PASSWORD, password);
        return db.update(TABLE_NAME, values, KEY_ID+ " =?", new String[]{String.valueOf(id)});
    }


    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " =?", new String[]{String.valueOf(id)});
        db.close();
    }

    public String getName(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        String result;
        if (cursor != null) {
            cursor.moveToFirst();
        }
        result = cursor.getString(cursor.getColumnIndex("Name"));
        return result;
    }

    public String getOccupation(int id) {
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_OCCUPATION}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
       String result;
        if (cursor != null) {
            cursor.moveToFirst();
        }
        result = cursor.getString(cursor.getColumnIndex("Occupation"));
        return result;
    }

    public String getUsername(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_USERNAME}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        String result;
        if (cursor != null) {
            cursor.moveToFirst();
        }
        result = cursor.getString(cursor.getColumnIndex("Username"));
        return result;
    }

    public String getPassword(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_PASSWORD}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        String result;
        if (cursor != null) {
            cursor.moveToFirst();
        }
        result = cursor.getString(cursor.getColumnIndex("Password"));
        return result;
    }

    public byte[] getUserImage(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_IMAGE}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        byte[] result;
        if (cursor != null) {
            cursor.moveToFirst();
        }
        result = cursor.getBlob(cursor.getColumnIndex("Image"));
        return result;
    }

    public List<String> getAllNames(){
        List<String> listNames = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                listNames.add(cursor.getString(1));
            }
            while(cursor.moveToNext());
        }
        return listNames;
    }

    public ArrayList<Integer> getAllID(){
        ArrayList<Integer> listID = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                listID.add(cursor.getInt(0));
            }
            while(cursor.moveToNext());
        }
        return listID;
    }

    public ArrayList<String> getAllAdminUsername(){
        ArrayList<String> listUsername = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                listUsername.add(cursor.getString(cursor.getColumnIndex("Username")));
            }
            while(cursor.moveToNext());
        }
        return listUsername;
    }

    public ArrayList<String> getAllAdminPassword(){
        ArrayList<String> listPassword = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                listPassword.add(cursor.getString(cursor.getColumnIndex("Password")));
            }
            while(cursor.moveToNext());
        }
        return listPassword;
    }
}
