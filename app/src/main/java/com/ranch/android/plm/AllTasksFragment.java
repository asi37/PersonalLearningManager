package com.ranch.android.plm;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
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

public class AllTasksFragment extends Fragment  {


    private TaskDataSource dataSource;
    private List<Task> tasksAll;
    private ListView tasksList;
    private SearchView mSearchView;

    /*---------------------------- OVERRIDES -----------------------------*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_tasks, container, false);

        tasksList = (ListView) rootView.findViewById(R.id.tasksList);
        tasksList.setOnItemClickListener((parent, view, position, id) -> {
            Task task  = tasksAll.get(position);
            Intent intent = new Intent(getContext(),TaskActivity.class);
            intent.putExtra(".model.Task",task);
            intent.putExtra(TaskActivity.CRUD_ACTION_MESSAGE, CrudAction.POPULATE.name());
            startActivity(intent);
        });

        dataSource = new TaskDataSource(getContext());
        tasksList.setTextFilterEnabled(true);
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

        try {
            dataSource.open();
            tasksAll = dataSource.findAll();
            ArrayAdapter<Task> adapter = new TaskListAdapter(getContext(), tasksAll);
            tasksList.setAdapter(adapter);
        } catch (ParseException e) {
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG);
        }
    }



}
