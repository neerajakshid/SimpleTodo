package com.codepath.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends ArrayAdapter<ItemModel> {


    public ItemsAdapter(Context context,List<ItemModel> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemModel list = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_row, parent, false);
        }

        // Lookup view for data population
        TextView tvItemId = (TextView) convertView.findViewById(R.id.tvItemId);
        TextView tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        TextView tvDueDate = (TextView) convertView.findViewById(R.id.tvDueDate);
        // Populate the data into the template view using the data object
        tvItemName.setText(list.itemBody);
        tvPriority.setText(list.priority);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        tvDueDate.setText(df.format(list.dueDate));
        tvItemId.setText(Integer.toString(list.id));
        tvItemId.setVisibility(View.GONE);

        // Return the completed view to render on screen
        return convertView;
    }
}
