package com.example.dellaanjeh.todonutapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddToListDialog.AddToListListener {

    SQLHandler sqlHandler;
    ListView lvTodotasks;
    ArrayList<TodoTaskItems> todoList;
    TodoTaskListAdapter adapter;
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

        //set the empty view!
        lvTodotasks.setEmptyView(tvEmptyList);

        btnAddtoList = (Button) findViewById(R.id.btnAddtoList);
        sqlHandler = new SQLHandler(this);
        dh = new DBHelper(this);
        todoList = dh.getAllTasks();
        // TODO: This isn't initializing even with seemingly correct parameters...
        adapter = new TodoTaskListAdapter(MainActivity.this, todoList);
        lvTodotasks.setAdapter(adapter);
        lvTodotasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, TaskDetailsActivity.class);
                TodoTaskItems task = (TodoTaskItems) adapter.getItem(position);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_add){
            AddToListDialog dialog = new AddToListDialog();
            dialog.show(fm, "New Task");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemAdded() {
        // created a setter to replace the list w all tasks since
        // base adapter doesn't have a cursor to track changes with
        adapter.setTodoList(dh.getAllTasks());
    }
}