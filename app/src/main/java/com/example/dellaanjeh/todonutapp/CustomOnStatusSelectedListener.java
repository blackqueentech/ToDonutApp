package com.example.dellaanjeh.todonutapp;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by dellaanjeh on 6/21/16.
 */
public class CustomOnStatusSelectedListener implements AdapterView.OnItemSelectedListener {
    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                               long id) {
        String selection = parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
