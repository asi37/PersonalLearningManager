package com.ranch.android.plm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ranch.android.plm.model.Task;

import java.util.List;

/**
 * Created by Asitha on 4/12/2017.
 */

public class TaskListAdapter extends ArrayAdapter<Task> implements Filterable {

    Context context;
    private List<Task> tasks;
    private List<Task> tasksOri;

    public TaskListAdapter(Context context, List<Task> tasks) {
        super(context, android.R.id.content, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.listitem_task, null);

        Task task = tasks.get(position);

        TextView textTitle = (TextView) view.findViewById(R.id.txtTaskListTitle);
        TextView textProgress = (TextView) view.findViewById(R.id.txtTaskListProgress);
        TextView textCompleted = (TextView) view.findViewById(R.id.txtTaskListCompleted);

        textTitle.setText(task.getTitle());
        textProgress.setText("Progress : " +Float.toString(task.getProgress()));

        if(task.isCompleted()){
            textCompleted.setVisibility(View.VISIBLE);
        }else{
            textCompleted.setVisibility(View.INVISIBLE);
        }
        return view;
    }
}
