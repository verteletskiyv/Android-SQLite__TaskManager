package com.study.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    private static final String db_name = "task_manager";
    private static final int db_version = 1;

    private static final String db_table = "task";
    private static final String db_column = "task_name";



    public DataBase(@Nullable Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NUT NULL);", db_table, db_column);
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s", db_table);
        db.execSQL(query);
        onCreate(db);
    }


    public void insertData (String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(db_column, task);
        db.insertWithOnConflict(db_table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void deleteData (String taskName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(db_table, db_column + " = ?", new String[] {taskName});
        db.close();
    }

    public ArrayList<String> getAllTasks() {
        ArrayList<String> allTasks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(db_table, new String[] {db_column}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(db_column);
            allTasks.add(cursor.getString(index));
        }
        cursor.close();
        db.close();

        return allTasks;
    }

}
