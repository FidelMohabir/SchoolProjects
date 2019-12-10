package edu.qc.seclass.glm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomAdapterReminders extends ArrayAdapter<Reminder> {
    Context context;
    ArrayList<Reminder> items;
    LayoutInflater inflater;
    TextView reminderTitle;
    TextView reminderName;
    View customView;
    Database db;
    MasterList master;

    CustomAdapterReminders(Context context, ArrayList<Reminder> i){
        super(context, R.layout.list_item, i);
        this.items = i;
        db = new Database(getContext());
        master = MasterList.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = LayoutInflater.from(getContext());
        customView = convertView;

        if(customView == null){
            customView = inflater.inflate(R.layout.reminder_item, parent, false);
        }

        final Reminder singleReminder = getItem(position);
        reminderTitle = (TextView) customView.findViewById(R.id.reminderTitle);
        reminderName = (TextView) customView.findViewById(R.id.reminderName);

        Button deleteBtn = (Button) customView.findViewById(R.id.deleteReminderBtn);
        deleteBtn.setTag(position);

        Button editBtn = (Button) customView.findViewById(R.id.editReminderBtn);
        editBtn.setTag(position);

        CheckBox checkBox = (CheckBox) customView.findViewById(R.id.checkboxReminder);
        checkBox.setTag(position);

        reminderTitle.setText(singleReminder.reminderType);
        reminderName.setText(singleReminder.reminderName);
        checkBox.setChecked(singleReminder.isChecked());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                master.getAllLists().get(reminderListActivity.index).getRemindersInList().get((int)compoundButton.getTag()).toggleCheck();
                if(isChecked){
                    db.check(singleReminder);
                }
                else {
                    db.uncheck(singleReminder);
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View convertView){
                int positionToRemove = (int) convertView.getTag();
                items.remove(positionToRemove);
                notifyDataSetChanged(); //refresh your listview based on new data
                db.deleteReminder(singleReminder);
            }
        });

        /*editBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View convertView) {
                final int positionToEdit = (int) convertView.getTag();

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Edit");
                final View mView = inflater.inflate(R.layout.edit_reminders_list, null);

                EditText edt = (EditText) mView.findViewById(R.id.reminderListName);
                edt.setText(items.get(positionToEdit).reminderName);

                alert.setView(mView);
                alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                alert.show();
            }
        });*/

        return customView;
    }

    private void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
