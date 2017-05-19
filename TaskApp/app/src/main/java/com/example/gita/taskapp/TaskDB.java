package com.example.gita.taskapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gita.taskapp.interfaces.IDataSource;
import com.example.gita.taskapp.models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;




public class TaskDB implements IDataSource<Task> {

    private static final String DB_NAME = "Tasks.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Tasks";
    private static final String ID = "_id";
    private static final String TASK_TITLE = "title";
    private static final String TASK_CONTENT = "content";
    SQLiteDatabase db;

    public TaskDB(Context ctx) {
        Helper helper = new Helper(ctx, DB_NAME, null, DB_VERSION);
        db = helper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    private Task makeTask(Cursor cursor) {
        Task task = new Task();
        task.id = UUID.fromString(cursor.getString(cursor.getColumnIndex(ID)));
        task.Title = cursor.getString(cursor.getColumnIndex(TASK_TITLE));
        task.Content = cursor.getString(cursor.getColumnIndex(TASK_CONTENT));
        return task;
    }

    @Override
    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        while(cursor.moveToNext()) {
            Task task = makeTask(cursor);
            tasks.add(task);
        }
        cursor.close();
        return tasks;
    }

    /**
     * Adds a Task to the database
     * @param task
     */
    @Override
    public void add(Task task) {
        ContentValues cv = makeCV(task);
        cv.put("_id", task.id.toString());
        db.insert(TABLE_NAME, null, cv);
    }

    /**
     * Updates a Task in the database
     * @param task
     */
    @Override
    public void update(Task task) {
        ContentValues contentValues = makeCV(task);
        db.update(TABLE_NAME, contentValues, ID + " = ?", new String[]{task.id.toString()});
    }

    private ContentValues makeCV(Task task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK_TITLE, task.Title);
        cv.put(TASK_CONTENT, task.Content);
        return cv;
    }

    @Override
    public void delete(Task task) {
        db.delete(TABLE_NAME, ID + " = ?", new String[]{task.id.toString()});
    }

    class Helper extends SQLiteOpenHelper {

        public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String createStatement = String.format("create table %s "
                    + " (%s text primary key not null," // uuid id
                    + " %s text not null," // name
                    + " %s text not null);" // age
                    , TABLE_NAME, ID, TASK_TITLE, TASK_CONTENT);
            sqLiteDatabase.execSQL(createStatement);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
