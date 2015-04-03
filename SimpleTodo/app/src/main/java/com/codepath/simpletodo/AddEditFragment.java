package com.codepath.simpletodo;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddEditFragment extends DialogFragment {
    EditText etEditItem;
    Spinner spinner;
    String priority;
    int year, month, day;
    DatePicker datePicker;
    public AddEditFragment() {
        // Empty constructor required for DialogFragment
    }

    public static AddEditFragment newInstance(String title) {
        AddEditFragment frag = new AddEditFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    public interface AddEditFragmentListener {
        void onFinishAddEditFragment(String itemName, String priority, Date date);
    }
    AddEditFragmentListener addEditFragmentListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addEditFragmentListener = (AddEditFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " should implement AddEditFragmentListener");
        }
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

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_item, container);
        etEditItem = (EditText) view.findViewById(R.id.etEditItem);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priority = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddEditFragment.this.getActivity(), "Please select a priority", Toast.LENGTH_SHORT).show();
                return;
            }
        });
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically
        etEditItem.requestFocus();
        MainActivity mainActivity = (MainActivity) getActivity();
        ItemModel item = mainActivity.getSelectedItem();
    if (item != null) {
        etEditItem.setText(item.itemBody);
        spinner.setSelection(getIndex(spinner, item.priority));


        Calendar cal = new GregorianCalendar();
        cal.setTime(item.dueDate);
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        // set current date into Date Picker
        datePicker.init(year, month, day, null);
    }

    Button button = (Button) view.findViewById(R.id.btnSave);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar calendar = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
            String str = etEditItem.getText().toString();
            if(str.matches("")) {
                Toast.makeText(AddEditFragment.this.getActivity(), "Please enter an item", Toast.LENGTH_SHORT).show();
                return;
            } else{
                addEditFragmentListener.onFinishAddEditFragment(etEditItem.getText().toString(), spinner.getSelectedItem().toString(), calendar.getTime());
                dismiss();
            }

        }
    });

    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    return view;
}
}
