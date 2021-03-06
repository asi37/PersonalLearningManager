package com.ranch.android.plm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ranch.android.plm.action.CrudAction;
import com.ranch.android.plm.db.TaskDataSource;
import com.ranch.android.plm.model.Task;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Asitha on 4/9/2017.
 */

public class ToDoTasksFragment extends Fragment {

    private TaskDataSource dataSource;
    private List<Task> tasksToDo;
    private ListView tasksToDoList;

    /*---------------------------- OVERRIDES -----------------------------*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_to_do_tasks, container, false);

        tasksToDoList = (ListView) rootView.findViewById(R.id.taskToDoList);
        tasksToDoList.setOnItemClickListener((parent, view, position, id) -> {
            Task task = tasksToDo.get(position);
            Intent intent = new Intent(getContext(), TaskActivity.class);
            intent.putExtra(".model.Task", task);
            intent.putExtra(TaskActivity.CRUD_ACTION_MESSAGE, CrudAction.POPULATE.name());
            startActivity(intent);
        });

        dataSource = new TaskDataSource(getContext());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshDisplay();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dataSource.close();
    }

    /*--------------------------- METHODS --------------------------------*/
    private void refreshDisplay() {
        String filter = Task.COLUMN_COMPLETED + " = 0 AND " + Task.COLUMN_PROGRESS + " != 100";

        try {
            dataSource.open();
            tasksToDo = dataSource.findFiltered(filter,null);
            ArrayAdapter<Task> adapter = new TaskListAdapter(getContext(), tasksToDo);
            tasksToDoList.setAdapter(adapter);
        } catch (ParseException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG);
        }
    }
}
