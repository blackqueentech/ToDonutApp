package com.example.dellaanjeh.todonutapp;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLHandler sqlHandler;
    ListView lvTodotasks;
    ArrayList<TodoTaskItems> todoList;
    Button btnAddtoList;
    TextView tvEmptyList;
    DBHelper dh;
    public TodoTaskListAdapter adapter;
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
        adapter = new TodoTaskListAdapter(this, todoList);
        lvTodotasks.setAdapter(adapter);

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
