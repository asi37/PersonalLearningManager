package com.ranch.android.plm;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

;

/**
 * Created by Asitha on 4/9/2017.
 */

public class ReportProgressDialog extends DialogFragment {

    ReportProgressListener mListener;

    /*---------------------------- OVERRIDES -----------------------------*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_report_progress, container, false);

        EditText textTaskProgress = (EditText) rootView.findViewById(R.id.textProgress);
        Button btnSave = (Button) rootView.findViewById(R.id.btnProgressDialogSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int progress = Integer.valueOf(textTaskProgress.getText().toString().trim());
                if (progress <= 100 && progress >= 0) {
                    mListener.onReportProgressComplete(progress);
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Invalid value for Progress", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnCancel = (Button) rootView.findViewById(R.id.btnProgressDialogCancel);
        btnCancel.setOnClickListener(v -> dismiss());

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (ReportProgressListener) activity;
    }

    public interface ReportProgressListener {
        void onReportProgressComplete(int progress);
    }
}
