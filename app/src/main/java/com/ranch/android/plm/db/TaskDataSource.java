package com.ranch.android.plm.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ranch.android.plm.model.Task;
import com.ranch.android.plm.utility.Util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.ranch.android.plm.MainActivity.LOGTAG;

/**
 * Created by Asitha on 4/12/2017.
 */

public class TaskDataSource implements DataSource {

    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    private String[] allColumns =
            {Task.COLUMN_ID,
                    Task.COLUMN_TITLE,
                    Task.COLUMN_DESCRIPTION,
                    Task.COLUMN_SUBJECT,
                    Task.COLUMN_DEADLINE,
                    Task.COLUMN_PROGRESS,
                    Task.COLUMN_COMPLETED,
                    Task.COLUMN_SEVERITY,
                    Task.COLUMN_PRIORITY
            };

    public TaskDataSource(Context context) {
        dbHelper = new PLMDBOpenHelper(context);
    }

    public List<Task> findAll() throws ParseException {

        Cursor cursor = database.query(Task.TABLE_TASK, allColumns,
                null, null, null, null, null);

        Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
        List<Task> tasks = cursorToList(cursor);
        return tasks;
    }

    public List<Task> findFiltered(String selection, String orderBy) throws ParseException {

        Cursor cursor = database.query(Task.TABLE_TASK, allColumns,
                selection, null, null, null, orderBy);

        Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
        List<Task> tasks = cursorToList(cursor);
        return tasks;
    }

    public Task create(Task task) {
        ContentValues content = new ContentValues();

        content.put(Task.COLUMN_TITLE, task.getTitle());
        content.put(Task.COLUMN_DESCRIPTION, task.getDescription());
        content.put(Task.COLUMN_SUBJECT, task.getSubject());
        content.put(Task.COLUMN_DEADLINE, Util.formatDates(task.getDeadline()));
        content.put(Task.COLUMN_PROGRESS, task.getProgress());
        content.put(Task.COLUMN_COMPLETED, task.isCompleted() ? 1 : 0);
        content.put(Task.COLUMN_SEVERITY, task.getSeverity());
        content.put(Task.COLUMN_PRIORITY, task.getPriority());

        long id = database.insert(Task.TABLE_TASK, null, content);
        task.setId(id);
        return task;
    }

    public Task update(Task task) {
        ContentValues content = new ContentValues();

        content.put(Task.COLUMN_ID, task.getId());
        content.put(Task.COLUMN_TITLE, task.getTitle());
        content.put(Task.COLUMN_DESCRIPTION, task.getDescription());
        content.put(Task.COLUMN_SUBJECT, task.getSubject());
        content.put(Task.COLUMN_DEADLINE, Util.formatDates(task.getDeadline()));
        content.put(Task.COLUMN_PROGRESS, task.getProgress());
        content.put(Task.COLUMN_COMPLETED, task.isCompleted() ? 1 : 0);
        content.put(Task.COLUMN_SEVERITY, task.getSeverity());
        content.put(Task.COLUMN_PRIORITY, task.getPriority());

        int id = database.update(Task.TABLE_TASK, content,Task.COLUMN_ID+" = ?", new String[]{Long.toString(task.getId())});

        return task;
    }

    public void delete(Task task){
        database.delete(Task.TABLE_TASK,Task.COLUMN_ID+" = ?", new String[]{Long.toString(task.getId())});
    }

    private List<Task> cursorToList(Cursor cursor) throws ParseException {
        List<Task> tasks = new ArrayList<Task>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Task task = new Task();

                task.setId(cursor.getLong(cursor.getColumnIndex(Task.COLUMN_ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndex(Task.COLUMN_TITLE)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(Task.COLUMN_TITLE)));
                task.setSubject(cursor.getString(cursor.getColumnIndex(Task.COLUMN_SUBJECT)));
                task.setDeadline(Util.parseDates(cursor.getString(cursor.getColumnIndex(Task.COLUMN_DEADLINE))));
                task.setProgress(cursor.getInt(cursor.getColumnIndex(Task.COLUMN_PROGRESS)));
                task.setCompleted(cursor.getInt(cursor.getColumnIndex(Task.COLUMN_COMPLETED)) == 1 ? true : false);
                task.setSeverity(cursor.getInt(cursor.getColumnIndex(Task.COLUMN_SEVERITY)));
                task.setPriority(cursor.getInt(cursor.getColumnIndex(Task.COLUMN_PRIORITY)));

                tasks.add(task);
            }
        }
        return tasks;
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
        Log.i(LOGTAG, "Database opened");
    }

    public void close() {
        dbHelper.close();
        Log.i(LOGTAG, "Database closed");
    }
}
