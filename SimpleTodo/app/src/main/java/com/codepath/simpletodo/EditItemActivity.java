package com.codepath.simpletodo;

import android.app.ActionBar;
import android.app.Activity;
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


public class EditItemActivity extends ActionBarActivity {
    EditText etEditItem;
    Spinner spinner;
    String priority;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priority = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(EditItemActivity.this, "Please select a priority", Toast.LENGTH_SHORT).show();
                return;
            }
        });
        id = getIntent().getExtras().getInt("id");
        ItemDatabase db = new ItemDatabase(this.getBaseContext());
        ItemModel itemRes = db.getSingleItem(id);
        etEditItem.setText(itemRes.getItemBody());
        spinner.setSelection(getIndex(spinner, itemRes.getPriority()));
    }

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
    public void onSaveItem(View v){
        EditText etEditItem =  (EditText) findViewById (R.id.etEditItem);
        String editText = etEditItem.getText().toString();
        spinner = (Spinner) findViewById(R.id.spinner);
        priority = spinner.getSelectedItem().toString();
        if(editText.matches("")) {
            Toast.makeText(this, "Please enter an item", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Intent editData = new Intent();
            editData.putExtra("EditText", editText);
            editData.putExtra("priority", priority);
            editData.putExtra("id", id);
            setResult(RESULT_OK, editData);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
