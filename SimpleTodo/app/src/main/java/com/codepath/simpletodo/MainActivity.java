package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    ArrayList<ItemModel> items;
    ItemsAdapter itemsAdapter;
    ListView lvItems;
    ItemDatabase db;
    private final int EDIT_REQUEST_CODE = 200;
    private final int ADD_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new ItemDatabase(this.getBaseContext());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        setupListViewListener();
    }


    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               ItemModel itemId = (ItemModel) lvItems.getItemAtPosition(position);
               db.deleteItem(itemId);
               readItems();
               return true;
            }

        });

        // set up onItemClickListener
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editPage = new Intent(MainActivity.this, EditItemActivity.class);
                ItemModel itemId = (ItemModel) lvItems.getItemAtPosition(position);
                editPage.putExtra("id", (int)itemId.getId());
                startActivityForResult(editPage, EDIT_REQUEST_CODE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            String editText = data.getExtras().getString("EditText");
            int id = data.getExtras().getInt("id");
            String priority = data.getExtras().getString("priority");
            ItemModel editItem = new ItemModel(id, editText, priority);
            db.updateItem(editItem);
            readItems();
        } else if(resultCode == RESULT_OK && requestCode == ADD_REQUEST_CODE){
            String addText = data.getExtras().getString("AddText");
            String priority = data.getExtras().getString("priority");
            ItemModel addItem = new ItemModel(addText, priority);
            writeItems(addItem);
        }
    }

    public void onAddItem(View v) {
        Intent addPage = new Intent(MainActivity.this, AddItemActivity.class);
        startActivityForResult(addPage, ADD_REQUEST_CODE);
    }

    private void readItems() {
        items = db.getAllItems();
        itemsAdapter = new ItemsAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
    }

    private void writeItems(ItemModel newItem) {
        db.addItemToDB(newItem);
        readItems();
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