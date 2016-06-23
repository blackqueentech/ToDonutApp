package com.example.dellaanjeh.todonutapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by dellaanjeh on 6/19/16.
 */
public class AddToListDialog extends DialogFragment {

    SQLHandler handler;
    TextView tvTaskName, tvTaskNotes, tvStatus, tvDueDate;
    EditText etTaskName, etTaskNotes, etDueDate;
    Button btnAddTask;
    DBHelper dbHelper;
    ListView lvTodotasks;
    Spinner spStatus;
    String[] statuses;
    DatePickerDialog dueDatePicker;
    SimpleDateFormat dateFormat;
    ArrayAdapter<String> statusAdapter;
    ArrayList<TodoTaskItems> todoList;
    TodoTaskListAdapter adapter;

    //we need a listener for the add action!
    AddToListListener listener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // can you inflate two different layouts in onCreateView?
        View rootView = inflater.inflate(R.layout.add_to_list_dialog, container,
                false);
        View mainView = inflater.inflate(R.layout.activity_main, container, false);
        getDialog().setTitle("Create a New Task");
        lvTodotasks = (ListView) mainView.findViewById(R.id.lvTodotasks);
        tvTaskName = (TextView) rootView.findViewById(R.id.tvTaskName);
        etTaskName = (EditText) rootView.findViewById(R.id.etTaskName);

        tvDueDate = (TextView) rootView.findViewById(R.id.tvDueDate);
        etDueDate = (EditText) rootView.findViewById(R.id.etDueDate);
        etDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                dueDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

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

        tvTaskNotes = (TextView) rootView.findViewById(R.id.tvTaskNotes);
        etTaskNotes = (EditText) rootView.findViewById(R.id.etTaskNotes);
        tvStatus = (TextView) rootView.findViewById(R.id.tvStatus);
        // TODO: figure out how to reference the material_spinner.xml from here
        // https://android-arsenal.com/details/1/1720 - website for library
        // spStatus = (MaterialSpinner) rootView.findViewById(R.id.spinner);
        spStatus = (Spinner) rootView.findViewById(R.id.spStatus);
        statuses = new String[]{"Not yet completed", "Completed"};
        statusAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, statuses);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(statusAdapter);
        dbHelper = new DBHelper(getActivity());
        btnAddTask = (Button) rootView.findViewById(R.id.btnAddTask);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to list
                String name = etTaskName.getText().toString();
                String notes = etTaskNotes.getText().toString();
                String status = String.valueOf(spStatus.getSelectedItem());
                String duedate = etDueDate.getText().toString();
                addListenerOnSpinnerItemSelection();
                dbHelper.addTask(name, duedate, notes, status);
                //prompt the adapter to refresh
                listener.onItemAdded();
                AddToListDialog.this.dismiss();
                // TODO: get list to update upon existing dialog
                //adapter.notifyDataSetChanged();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //the listener is just the main activity
        listener = (MainActivity)getActivity();
    }

    // I may not need this...
    public void addListenerOnSpinnerItemSelection(){
        spStatus.setOnItemSelectedListener(new CustomOnStatusSelectedListener());
    }

    interface AddToListListener{
        public void onItemAdded();
    }
}