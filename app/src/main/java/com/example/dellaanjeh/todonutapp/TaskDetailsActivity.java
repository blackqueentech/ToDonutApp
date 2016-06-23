package com.example.dellaanjeh.todonutapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskDetailsActivity extends AppCompatActivity {

    TextView tvTaskNameLabel, tvDueDateLabel, tvTaskNotesLabel, tvStatusLabel;
    TextView tvTaskName, tvDueDate, tvTaskNotes, tvStatus;
    String taskName, dueDate, status, notes;
    Button btnDelete, btnEdit, btnBack;
    DBHelper helper;
    TodoTaskListAdapter adapter;
    TodoTaskItems item;
    ArrayList<TodoTaskItems> todoList;
    Integer id;
    final Context context = this;

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
        adapter = new TodoTaskListAdapter(TaskDetailsActivity.this, todoList);
        helper = new DBHelper(this);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View view = li.inflate(R.layout.delete_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(view);

                final TextView message = (TextView) view
                        .findViewById(R.id.tvDeleteMessage);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // delete item from list
                                        Log.d(Integer.toString(id), "task ID");
                                        helper.deleteTask(id);
                                        adapter.notifyDataSetChanged();
                                        //adapter.setTodoList(helper.getAllTasks());
                                        Intent intent = new Intent(TaskDetailsActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskDetailsActivity.this, EditTaskActivity.class);
                intent.putExtra("EXTRA_ID", id);
                intent.putExtra("EXTRA_NAME", taskName);
                intent.putExtra("EXTRA_DUE_DATE", dueDate);
                intent.putExtra("EXTRA_STATUS", status);
                intent.putExtra("EXTRA_NOTES", notes);
                startActivity(intent);
            }
        });

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskDetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskName = extras.getString("EXTRA_NAME");
            dueDate = extras.getString("EXTRA_DUE_DATE");
            notes = extras.getString("EXTRA_NOTES");
            status = extras.getString("EXTRA_STATUS");
            id = extras.getInt("EXTRA_ID");
        }

        tvTaskName.setText(taskName);
        tvDueDate.setText(dueDate);
        tvTaskNotes.setText(notes);
        tvStatus.setText(status);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_delete){
            // dialog and then remove task from list
        } else if(item.getItemId() == R.id.action_edit) {
            // open edit task activity
        }
        return super.onOptionsItemSelected(item);
    }
}
