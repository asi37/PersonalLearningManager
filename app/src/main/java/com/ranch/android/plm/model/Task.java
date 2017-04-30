package com.ranch.android.plm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.ranch.android.plm.MainActivity;
import com.ranch.android.plm.utility.Util;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Asitha on 4/12/2017.
 */

public class Task implements Parcelable {

    public static final String TABLE_TASK = "task";

    public static final String COLUMN_ID = "id";
    private long id;

    public static final String COLUMN_TITLE = "title";
    private String title;

    public static final String COLUMN_DESCRIPTION = "description";
    private String description;

    public static final String COLUMN_SUBJECT = "subject";
    private String subject;

    public static final String COLUMN_DEADLINE = "deadline";
    private Date deadline;

    public static final String COLUMN_PROGRESS = "progress";
    private int progress;

    public static final String COLUMN_COMPLETED = "completed";
    private boolean completed;

    public static final String COLUMN_SEVERITY = "severity";
    private int severity;

    public static final String COLUMN_PRIORITY = "priority";
    private int priority;

    public Task() {
    }

    public Task(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.description = in.readString();
        this.subject = in.readString();
        try {
            this.deadline = Util.parseDates(in.readString());
        } catch (ParseException e) {
           Log.e(MainActivity.LOGTAG, "Error while parsing  date");
        }
        this.progress = in.readInt();
        this.completed = in.readInt()==1 ? true : false;
        this.severity = in.readInt();
        this.priority = in.readInt();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    //This should be deleted
    public Task(long id, String title, String description, String subject, Date deadline, int progress, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.subject = subject;
        this.deadline = deadline;
        this.progress = progress;
        this.completed = completed;
    }
//
//    @Override
//    public String toString() {
//        return id +"/"+title+"/"+subject+"/"+deadline.toString()+"/"+progress+"/"+completed;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.i(MainActivity.LOGTAG, "Task: writeToParcel");

        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(subject);
        dest.writeString(Util.formatDates(deadline));
        dest.writeInt(progress);
        dest.writeInt(completed ? 1 : 0);
        dest.writeInt(severity);
        dest.writeInt(priority);


    }

    public static final Parcelable.Creator<Task> CREATOR =
            new Parcelable.Creator<Task>() {

                @Override
                public Task createFromParcel(Parcel source) {
                    Log.i(MainActivity.LOGTAG, "createFromParcel");
                    return new Task(source);
                }

                @Override
                public Task[] newArray(int size) {
                    Log.i(MainActivity.LOGTAG, "newArray");
                    return new Task[size];
                }

            };



//    public static int calculateRank(ArrayList<Task> tasksList){
//
//    }
}
