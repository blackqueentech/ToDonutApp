package com.example.dellaanjeh.todonutapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by dellaanjeh on 6/19/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ToDo";
    private static final String DATABASE_TABLE_NAME = "Tasks";
    public static final String COLUMN1 = "Task_ID";
    public static final String COLUMN2 = "Task_Name";
    public static final String COLUMN3 = "Due_Date";
    public static final String COLUMN4 = "Notes";
    public static final String COLUMN5 = "Status";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This runs once after the installation and creates a database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_TABLE_NAME + " (" + COLUMN1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN2 + " TEXT, " + COLUMN3 + " TEXT, " + COLUMN4 + " TEXT, " + COLUMN5 + " TEXT)");

    }

    /**
     * This would run after the user updates the app. This is in case you want
     * to modify the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }

    /**
     * This method adds a record to the database. All we pass in is the todo
     * text
     */
    public long addTask(String name, String dueDate, String notes, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN2, name);
        cv.put(COLUMN3, dueDate);
        cv.put(COLUMN4, notes);
        cv.put(COLUMN5, status);
        return db.insert(DATABASE_TABLE_NAME, null, cv);
    }

    public long editTask(Integer id, String name, String dueDate, String notes, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN2, name);
        cv.put(COLUMN3, dueDate);
        cv.put(COLUMN4, notes);
        cv.put(COLUMN5, status);
        return db.update(DATABASE_TABLE_NAME, cv, COLUMN1 + "=?", new String[]{id.toString()});
    }

    /**
     * //This method returns all notes from the database
     */
    public ArrayList<TodoTaskItems> getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TodoTaskItems> todoList = new ArrayList<TodoTaskItems>();

        Cursor cursor = db.rawQuery("SELECT * from " + DATABASE_TABLE_NAME,
                new String[] {});

        if (cursor.moveToFirst()) {
            do {
                TodoTaskItems task = new TodoTaskItems();

                task.id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN1));
                task.taskName = cursor.getString(cursor.getColumnIndex(COLUMN2));
                task.dueDate = cursor.getString(cursor.getColumnIndex(COLUMN3));
                task.taskNotes = cursor.getString(cursor.getColumnIndex(COLUMN4));
                task.status = cursor.getString(cursor.getColumnIndex(COLUMN5));

                todoList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return todoList;
    }

    public TodoTaskItems getTask(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DATABASE_TABLE_NAME, new String[]{COLUMN1, COLUMN2, COLUMN3, COLUMN4, COLUMN5}, COLUMN1 + "=?", new String[]{"" + id},null,null,null,null);
        if(cursor.moveToFirst()){
            TodoTaskItems task = new TodoTaskItems();
            task.id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN1));
            task.taskName = cursor.getString(cursor.getColumnIndex(COLUMN2));
            task.dueDate = cursor.getString(cursor.getColumnIndex(COLUMN3));
            task.taskNotes = cursor.getString(cursor.getColumnIndex(COLUMN4));
            task.status = cursor.getString(cursor.getColumnIndex(COLUMN5));
            return task;
        }else{
            return null;
        }
    }
    /*
     * //This method deletes a record from the database.
     */
    public void deleteTask(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String string = String.valueOf(id);
        int delete = db.delete(DATABASE_TABLE_NAME, COLUMN1 + "=?", new String[]{string});
        Log.i("Q","Deleted " + delete);
    }

}