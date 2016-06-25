package com.example.dellaanjeh.todonutapp;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dellaanjeh on 6/19/16.
 */
public class TodoTaskListAdapter extends BaseAdapter {

    Context context;
    ArrayList<TodoTaskItems> todoList;

    public TodoTaskListAdapter(Context c, ArrayList<TodoTaskItems> list) {
        this.context = c;
        todoList = list;
    }

    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        TodoTaskItems item = todoList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tasklayout, null);

        }
        TextView tvTaskName = (TextView) convertView.findViewById(R.id.tvTaskName);
        tvTaskName.setText(item.getTaskName());
        TextView tvDueDate = (TextView) convertView.findViewById(R.id.tvDate);
        tvDueDate.setText(item.getDueDate());

        if (item.getStatus().equals("Completed")) {
            tvTaskName.setPaintFlags(tvTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        return convertView;
    }

    public void setTodoList(ArrayList<TodoTaskItems> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }
}
