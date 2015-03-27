package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddItemActivity extends ActionBarActivity {
    EditText etAddItem;
    Spinner addSpinner;
    String priority;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        etAddItem = (EditText) findViewById(R.id.etAddItem);
        addSpinner = (Spinner) findViewById(R.id.addSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addSpinner.setAdapter(adapter);

        addSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priority = addSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddItemActivity.this, "Please select a priority", Toast.LENGTH_SHORT).show();
                return;
            }
        });

    }

    public void onAddSaveItem(View v){
        EditText etAddItem =  (EditText) findViewById (R.id.etAddItem);
        String addText = etAddItem.getText().toString();
        addSpinner = (Spinner) findViewById(R.id.addSpinner);
        priority = addSpinner.getSelectedItem().toString();
        if(addText.matches("")) {
            Toast.makeText(this, "Please enter an item", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Intent editData = new Intent();
            editData.putExtra("AddText", addText);
            editData.putExtra("priority", priority);
            setResult(RESULT_OK, editData);
            finish();
        }
    }


}