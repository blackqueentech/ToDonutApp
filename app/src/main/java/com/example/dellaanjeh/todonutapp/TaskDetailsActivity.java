package com.example.dellaanjeh.todonutapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetailsActivity extends AppCompatActivity {

    TextView tvTaskNameLabel, tvDueDateLabel, tvTaskNotesLabel, tvStatusLabel;
    TextView tvTaskName, tvDueDate, tvTaskNotes, tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        tvTaskNameLabel = (TextView) findViewById(R.id.tvTaskNameLabel);
        tvTaskName = (TextView) findViewById(R.id.tvTaskName);
        tvDueDateLabel = (TextView) findViewById(R.id.tvDueDateLabel);
        tvDueDate = (TextView) findViewById(R.id.tvDueDate);
        tvStatusLabel = (TextView) findViewById(R.id.tvStatusLabel);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvTaskNotesLabel = (TextView) findViewById(R.id.tvTaskNotesLabel);
        tvTaskNotes = (TextView) findViewById(R.id.tvTaskNotes);

    }
}
