package com.ranch.android.plm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.ranch.android.plm.action.CrudAction;
import com.ranch.android.plm.model.Task;
import com.ranch.android.plm.utility.Util;

import java.text.ParseException;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {

    private TextView textTitle;
    private ProgressBar progBarTaskProgress;
    private CheckBox cbTaskCompleted;
    private TextView textDeadline;
    private TextView textSubject;
    private TextView textDescription;
    private Spinner spinnerSeverity;
    private Spinner spinnerPriority;

    private String crudAction;

    /*---------------------------- INSTANTIATION -------------------------*/
    public static TaskDetailFragment newInstance(Task task, String crudAction) {

        Bundle args = new Bundle();
        args.putParcelable(".model.Task", task);
        args.putString(TaskActivity.CRUD_ACTION_MESSAGE, crudAction);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static TaskDetailFragment newInstance(String crudAction) {

        Bundle args = new Bundle();
        args.putString(TaskActivity.CRUD_ACTION_MESSAGE, crudAction);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /*---------------------------- OVERRIDES -----------------------------*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_task_detail, container, false);

        textTitle = (TextView) rootView.findViewById(R.id.textTaskTitle);
        progBarTaskProgress = (ProgressBar) rootView.findViewById(R.id.progBarTaskProgress);
        cbTaskCompleted = (CheckBox) rootView.findViewById(R.id.cbTaskCompleted);
        textDeadline = (TextView) rootView.findViewById(R.id.textDeadline);
        textSubject = (TextView) rootView.findViewById(R.id.textSubject);
        textDescription = (TextView) rootView.findViewById(R.id.textDescription);
        spinnerSeverity = (Spinner) rootView.findViewById(R.id.spinnerSeverity);
        spinnerPriority = (Spinner) rootView.findViewById(R.id.spinnerPriority);

        Bundle args = getArguments();
        if (args != null) {
            crudAction = args.getString(TaskActivity.CRUD_ACTION_MESSAGE);
            if (crudAction.equals(CrudAction.POPULATE.name())) {
                Task task = args.getParcelable(".model.Task");
                setFieldValues(task);
                if (task.isCompleted()) {
                    setFieldsEditable(false);
                } else {
                    setFieldsEditable(true);
                }
            } else {
                CardView cv = (CardView) rootView.findViewById(R.id.cardTaskCompleted);
                rootView.removeView(cv);
            }
        }

        textTitle.clearFocus();

        Spinner spinnerPriority = (Spinner) rootView.findViewById(R.id.spinnerPriority);
        Spinner spinnerSeverity = (Spinner) rootView.findViewById(R.id.spinnerSeverity);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.priority_number_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerSeverity.setAdapter(adapter);
        spinnerPriority.setAdapter(adapter);

//        cbTaskCompleted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(cbTaskCompleted.isChecked()){
//                    if(task.s)
//                }
//            }
//        });

        return rootView;
    }

    /*---------------------------- METHODS -------------------------------*/
    public Task getFieldValues() throws ParseException {
        Task task = new Task();

        task.setTitle(textTitle.getText().toString());
        task.setProgress(progBarTaskProgress.getProgress());
        task.setCompleted(cbTaskCompleted.isChecked());
        task.setSubject(textSubject.getText().toString());
//        if(textDeadline.getText() != null) {
//            task.setDeadline(Util.parseDates(textDeadline.getText().toString()));
//        }else{
        task.setDeadline(new Date());
//        }
        task.setDescription(textDescription.getText().toString());

        task.setSeverity(Integer.valueOf(spinnerSeverity.getSelectedItem().toString()));
        task.setPriority(Integer.valueOf(spinnerPriority.getSelectedItem().toString()));

        return task;
    }

    public void setFieldValues(Task task) {
        int temp;

        textTitle.setText(task.getTitle());
        progBarTaskProgress.setProgress(task.getProgress());
        cbTaskCompleted.setChecked(task.isCompleted());
        textDeadline.setText(Util.formatDates(task.getDeadline()));
        textSubject.setText(task.getSubject());
        textDescription.setText(task.getDescription());

        temp = task.getSeverity();
        spinnerSeverity.setSelection(++temp);
        temp = task.getPriority();
        spinnerPriority.setSelection(++temp);

    }

    public void setFieldsEditable(boolean enabled) {
        textTitle.setEnabled(enabled);
        textDeadline.setEnabled(enabled);
        textSubject.setEnabled(enabled);
        textDescription.setEnabled(enabled);
    }

    public boolean getCompleted() {
        return cbTaskCompleted.isChecked();
    }

    public void setCompleted(boolean isCompleted) {
        cbTaskCompleted.setChecked(isCompleted);
    }

    public int getProgBarTaskProgress() {
        return progBarTaskProgress.getProgress();
    }

    public void setProgBarTaskProgress(int progress) {
        progBarTaskProgress.setProgress(progress);
    }

}
