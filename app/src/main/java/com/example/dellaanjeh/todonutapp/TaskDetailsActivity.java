package com.example.dellaanjeh.todonutapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskDetailsActivity extends AppCompatActivity {

    private static final int EDIT_REQUEST = 123;
    TextView tvTaskNameLabel, tvDueDateLabel, tvTaskNotesLabel, tvStatusLabel;
    TextView tvTaskName, tvDueDate, tvTaskNotes, tvStatus;
    String taskName, dueDate, status, notes;
    DBHelper helper;
    TodoTaskListAdapter adapter;
    TodoTaskItems item;
    ArrayList<TodoTaskItems> todoList;
    Integer taskId;
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


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskName = extras.getString("EXTRA_NAME");
            dueDate = extras.getString("EXTRA_DUE_DATE");
            notes = extras.getString("EXTRA_NOTES");
            status = extras.getString("EXTRA_STATUS");
            taskId = extras.getInt("EXTRA_ID");
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
            LayoutInflater li = LayoutInflater.from(context);
            View view = li.inflate(R.layout.delete_dialog, null);


            ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.ToDonutDialog);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);

            // set prompts.xml to alertdialog builder
           // alertDialogBuilder.setView(view);

            final TextView message = (TextView) view
                    .findViewById(R.id.tvDeleteMessage);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    Log.d(Integer.toString(taskId), "task ID");
                                    helper.deleteTask(taskId);
                                    TaskDetailsActivity.this.finish();
                                    Toast.makeText(getBaseContext(), "Task deleted!", Toast.LENGTH_SHORT).show();
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
            alertDialog.setTitle("Are you sure you want to delete this task?");

            // show it
            alertDialog.show();
        } else if(item.getItemId() == R.id.action_edit) {
            // open edit task activity
            Intent intent = new Intent(TaskDetailsActivity.this, EditTaskActivity.class);
            intent.putExtra("EXTRA_ID", taskId);
            intent.putExtra("EXTRA_NAME", taskName);
            intent.putExtra("EXTRA_DUE_DATE", dueDate);
            intent.putExtra("EXTRA_STATUS", status);
            intent.putExtra("EXTRA_NOTES", notes);
            startActivityForResult(intent,EDIT_REQUEST);
        } else if(item.getItemId() == R.id.action_back) {
            TaskDetailsActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //lets rebind the updated data
        TodoTaskItems task = helper.getTask(taskId);
        tvTaskName.setText(task.getTaskName());
        tvDueDate.setText(task.getDueDate());
        tvTaskNotes.setText(task.getTaskNotes());
        tvStatus.setText(task.getStatus());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
        return true;
    }

}
