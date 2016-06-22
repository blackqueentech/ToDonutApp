package com.example.dellaanjeh.todonutapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SQLHandler sqlHandler;
    ListView lvTodotasks;
    List<TodoTaskItems> todoList;
    ArrayAdapter<TodoTaskItems> adapter;
    Button btnAddtoList;
    TextView tvEmptyList;
    DBHelper dh;
    public TodoTaskListAdapter ttladapter;
    SharedPreferences sp;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTodotasks = (ListView) findViewById(R.id.lvTodotasks);
        tvEmptyList = (TextView) findViewById(R.id.tvEmptyList);
        btnAddtoList = (Button) findViewById(R.id.btnAddtoList);
        sqlHandler = new SQLHandler(this);
        dh = new DBHelper(this);
        todoList = dh.getAllTasks();
        adapter = new TodoTaskListAdapter(MainActivity.this, todoList);
        lvTodotasks.setAdapter(adapter);
        lvTodotasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, TaskDetailsActivity.class);
                TodoTaskItems task = adapter.getItem(position);
                intent.putExtra("EXTRA_NAME", task.getTaskName());
                intent.putExtra("EXTRA_DUE_DATE", task.getDueDate());
                intent.putExtra("EXTRA_STATUS", task.getStatus());
                intent.putExtra("EXTRA_NOTES", task.getTaskNotes());
                startActivity(intent);
            }
        });

        btnAddtoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToListDialog dialog = new AddToListDialog();
                //dialog.setContentView(R.layout.add_to_list_dialog);
                //dialog.
                dialog.show(fm, "New Task");

            }
        });

        adapter.notifyDataSetChanged();
    }
}
