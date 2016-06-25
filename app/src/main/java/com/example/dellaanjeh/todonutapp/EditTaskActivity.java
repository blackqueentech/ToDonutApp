package com.example.dellaanjeh.todonutapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditTaskActivity extends AppCompatActivity {

    SQLHandler handler;
    TextView tvTaskName, tvTaskNotes, tvStatus, tvDueDate;
    EditText etTaskName, etTaskNotes, etDueDate;
    String taskName, dueDate, status, notes;
    Integer id;
    DBHelper dbHelper;
    Spinner spStatus;
    String[] statuses;
    DatePickerDialog dueDatePicker;
    SimpleDateFormat dateFormat;
    ArrayAdapter<String> statusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskName = extras.getString("EXTRA_NAME");
            dueDate = extras.getString("EXTRA_DUE_DATE");
            notes = extras.getString("EXTRA_NOTES");
            status = extras.getString("EXTRA_STATUS");
            id = extras.getInt("EXTRA_ID");
        }
        //lvTodotasks = (ListView) mainView.findViewById(R.id.lvTodotasks);
        tvTaskName = (TextView) findViewById(R.id.tvTaskName);
        etTaskName = (EditText) findViewById(R.id.etTaskName);
        etTaskName.setText(taskName, TextView.BufferType.EDITABLE);
        tvDueDate = (TextView) findViewById(R.id.tvDueDate);
        etDueDate = (EditText) findViewById(R.id.etDueDate);
        etDueDate.setText(dueDate, TextView.BufferType.EDITABLE);
        etDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                dueDatePicker = new DatePickerDialog(EditTaskActivity.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        etDueDate.setText(dateFormat.format(newDate.getTime()));
                    }

                },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dueDatePicker.show();
            }
        });
        dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

        tvTaskNotes = (TextView) findViewById(R.id.tvTaskNotes);
        etTaskNotes = (EditText) findViewById(R.id.etTaskNotes);
        etTaskNotes.setText(notes, TextView.BufferType.EDITABLE);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        // TODO: figure out how to reference the material_spinner.xml from here
        // https://android-arsenal.com/details/1/1720 - website for library
        // spStatus = (MaterialSpinner) rootView.findViewById(R.id.spinner);
        spStatus = (Spinner) findViewById(R.id.spStatus);
        statuses = new String[]{"Not yet completed", "Completed"};
        statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statuses);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setSelection(getIndex(spStatus, status));
        spStatus.setAdapter(statusAdapter);
        dbHelper = new DBHelper(this);
    }

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_save){
            // dialog and then remove task from list
            String name = etTaskName.getText().toString();
            String notes = etTaskNotes.getText().toString();
            String status = String.valueOf(spStatus.getSelectedItem());
            String duedate = etDueDate.getText().toString();
            dbHelper.editTask(id,name, duedate, notes, status);
//                Intent intent = new Intent(EditTaskActivity.this, TaskDetailsActivity.class);
//                intent.putExtra("EXTRA_NAME", name);
//                intent.putExtra("EXTRA_DUE_DATE", duedate);
//                intent.putExtra("EXTRA_STATUS", status);
//                intent.putExtra("EXTRA_NOTES", notes);
//                startActivity(intent);
            //use finish instead!
            setResult(Activity.RESULT_OK);
            EditTaskActivity.this.finish();
            Toast.makeText(getBaseContext(), "Task has been updated!", Toast.LENGTH_SHORT).show();
        } else if(item.getItemId() == R.id.action_back) {
            setResult(Activity.RESULT_CANCELED);
            EditTaskActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
