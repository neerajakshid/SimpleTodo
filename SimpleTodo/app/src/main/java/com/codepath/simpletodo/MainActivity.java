package com.codepath.simpletodo;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends ActionBarActivity implements AddEditFragment.AddEditFragmentListener{
    ArrayList<ItemModel> items;
    ItemsAdapter itemsAdapter;
    ItemModel selectedItem;
    ListView lvItems;
    ItemDatabase db;
    NotificationManager myNotificationManager;
    private int notificationId = 111;

    PendingIntent pendingIntent;

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
        displayNotification();
    }


    protected void displayNotification() {
        Calendar cal = GregorianCalendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, 14);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        //calendar.set(Calendar.AM_PM,Calendar.PM);
        Intent myIntent = new Intent(MainActivity.this, Reciever.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//      alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),1000*60*60*24, pendingIntent); // set repeating alarm to triger every 24 hours
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ItemModel itemId = (ItemModel) lvItems.getItemAtPosition(position);
                db.deleteItem(itemId);
                readItems();
                Toast.makeText(MainActivity.this, "Selected item deleted", Toast.LENGTH_SHORT).show();
                return true;
            }

        });

        // set up onItemClickListener
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Intent editPage = new Intent(MainActivity.this, EditItemActivity.class);
                selectedItem = (ItemModel) lvItems.getItemAtPosition(position);
                //  editPage.putExtra("id", (int)itemId.getId());
                // startActivityForResult(editPage, EDIT_REQUEST_CODE);
                showEditDialog();
            }
        });

    }
    private void showEditDialog(){
        FragmentManager fm = getSupportFragmentManager();
        AddEditFragment alertDialog = AddEditFragment.newInstance("Edit Item");
        alertDialog.show(fm, "fragment_add_edit_item");
    }
    private void showAddDialog(){
        FragmentManager fm = getSupportFragmentManager();
        AddEditFragment alertDialog = AddEditFragment.newInstance("Add Item");
        alertDialog.show(fm, "fragment_add_edit_item");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    // Using Activity
    //@Override
    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    }*/

    public void onAddItem() {
        // Intent addPage = new Intent(MainActivity.this, AddItemActivity.class);
        //startActivityForResult(addPage, ADD_REQUEST_CODE);

        // using DialogFragment
        selectedItem = null;
        showAddDialog();
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
            onAddItem();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ItemModel getSelectedItem(){
        return selectedItem;
    }

    @Override
    public void onFinishAddEditFragment(String itemName, String priority, Date dueDate) {
        if (selectedItem == null) {
            ItemModel newItem = new ItemModel(itemName, priority, dueDate);
            writeItems(newItem);
        } else {
            int id = (int) selectedItem.getId();
            ItemModel editItem = new ItemModel(id, itemName, priority, dueDate);
            db.updateItem(editItem);
            readItems();
        }
    }
}