package com.ranch.android.plm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ranch.android.plm.action.CrudAction;
import com.ranch.android.plm.db.TaskDataSource;
import com.ranch.android.plm.model.Task;

/**
 * Created by Asitha on 4/12/2017.
 */

public class TaskActivity extends AppCompatActivity implements ReportProgressDialog.ReportProgressListener {


    public static final String CRUD_ACTION_MESSAGE = "crud_action_message";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TaskDataSource datasource;
    private Task task;
    private String crudAction;
    private TaskDetailFragment taskDetailFragment;

    /*---------------------------- OVERRIDES -----------------------------*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        datasource = new TaskDataSource(this);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            crudAction = b.getString(CRUD_ACTION_MESSAGE);
            if (crudAction.equals(CrudAction.NEW.name())) {
                getSupportFragmentManager().beginTransaction().add(R.id.frame_container, TaskDetailFragment.newInstance(crudAction)).commit();
            } else if (crudAction.equals(CrudAction.POPULATE.name())) {
                task = b.getParcelable(".model.Task");
                getSupportFragmentManager().beginTransaction().add(R.id.frame_container, TaskDetailFragment.newInstance(task, crudAction)).commit();
            }
        }

        setTitle(task.getTitle());

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_task);
//        setSupportActionBar(toolbar);

//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//        // Set up the ViewPager with the sections adapter.
//        mViewPager = (ViewPager) findViewById(R.id.container_task);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
//
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_task);
//        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    public void onReportProgressComplete(int progress) {
        taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (progress == 100) {
            completeTask();
        } else {
            task.setProgress(progress);
            taskDetailFragment.setProgBarTaskProgress(progress);
            datasource.open();
            datasource.update(task);
            datasource.close();
        }
    }

    /*--------------------------- EVENT HANDLERS -------------------------*/
    public void taskSaveClickHandler(MenuItem item) {
//        TaskDetailFragment f = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        try {
            task = taskDetailFragment.getFieldValues();
            datasource.open();
            if (crudAction.equals(CrudAction.NEW.name())) {
                task = datasource.create(task);
                Toast.makeText(this, "New Task added, ID: " + task.getId(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MainActivity.class));
            } else if (crudAction.equals(CrudAction.POPULATE.name())) {
                task = datasource.update(task);
                Toast.makeText(this, "Task is updated", Toast.LENGTH_LONG).show();
            }
            datasource.close();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    public void completeTaskClickHandler(View view) {
        if (!task.isCompleted()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure want to complete this Task?").setPositiveButton("Yes", dialogCompleteTaskClickListener)
                    .setNegativeButton("No", dialogCompleteTaskClickListener).show();
        }
    }

    public void deleteTaskClickHandler(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to delete this Task?").setPositiveButton("Yes", dialogDeleteTaskClickListener)
                .setNegativeButton("No", dialogDeleteTaskClickListener).show();
    }

    public void reportProgressTaskClickHandler(MenuItem item) {
        ReportProgressDialog reportProgressDialog = new ReportProgressDialog();
        reportProgressDialog.show(getSupportFragmentManager(), MainActivity.LOGTAG);
    }

    public void reportProgressTaskClickHandler(View view) {
        ReportProgressDialog reportProgressDialog = new ReportProgressDialog();
        reportProgressDialog.show(getSupportFragmentManager(), MainActivity.LOGTAG);
    }

    /*--------------------------- METHODS --------------------------------*/
    private void completeTask() {
        taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        task.setProgress(100);
        task.setCompleted(true);
        datasource.open();
        task = datasource.update(task);
        Toast.makeText(this, "Task is completed", Toast.LENGTH_LONG).show();
        datasource.close();
        taskDetailFragment.setCompleted(true);
        taskDetailFragment.setProgBarTaskProgress(100);
        taskDetailFragment.setFieldsEditable(false);
    }

    private void deleteTask() {
        datasource.open();
        datasource.delete(task);
        datasource.close();
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
        Toast.makeText(this, "Task: " + task.getTitle() + " deleted.", Toast.LENGTH_LONG).show();
    }



    /*--------------------------- DIALOGS LISTENERS -----------------------*/
    DialogInterface.OnClickListener dialogCompleteTaskClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    completeTask();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                    taskDetailFragment.setCompleted(false);
                    break;
            }
        }
    };

    DialogInterface.OnClickListener dialogDeleteTaskClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    deleteTask();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    CompletedTasksFragment taskDetailFragment = new CompletedTasksFragment();
                    return taskDetailFragment;
                case 1:
                    ToDoTasksFragment toDoAssignmentsTab = new ToDoTasksFragment();
                    return toDoAssignmentsTab;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Details";
                case 1:
                    return "Resources";
            }
            return null;
        }

    }
}