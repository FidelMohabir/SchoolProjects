package edu.qc.seclass.glm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class CustomAdapter extends ArrayAdapter<ReminderList> {
    Context context;
    ArrayList<ReminderList> items;
    LayoutInflater inflater;
    TextView remindersListName;
    View customView;
    Database db;
    MasterList master;

    CustomAdapter(Context context, ArrayList<ReminderList> i){
        super(context, R.layout.list_item, i);
        this.items = i;
        db = new Database(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = LayoutInflater.from(getContext());
        customView = convertView;

        master = MasterList.getInstance();

        if(customView == null){
            customView = inflater.inflate(R.layout.list_item, parent, false);
        }

        ReminderList singleReminderList = getItem(position);
        remindersListName = (TextView) customView.findViewById(R.id.reminderListTitle);

        Button deleteBtn = (Button) customView.findViewById(R.id.deleteReminderListBtn);
        deleteBtn.setTag(position);

        Button editBtn = (Button) customView.findViewById(R.id.editReminderListBtn);
        editBtn.setTag(position);

        remindersListName.setText(singleReminderList.getNameOfList());

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View convertView){
                int positionToRemove = (int) convertView.getTag();
                String name = items.get(positionToRemove).getNameOfList();
                items.remove(positionToRemove);
                db.deleteList(name);
                master.deleteList(name);
                notifyDataSetChanged(); //refresh your listview based on new data
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View convertView) {
                final int positionToEdit = (int) convertView.getTag();

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Edit");
                final View mView = inflater.inflate(R.layout.edit_reminders_list, null);

                EditText edt = (EditText) mView.findViewById(R.id.reminderListName);
                edt.setText(items.get(positionToEdit).getNameOfList());

                alert.setView(mView);
                alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //User updates Reminder List
                        EditText updatedText = (EditText) ((EditText) mView.findViewById(R.id.reminderListName));
                        String oldname = items.get(positionToEdit).getNameOfList();
                        items.get(positionToEdit).setNameOfList(updatedText.getText().toString());
                        notifyDataSetChanged();
                        db.editListName(updatedText.getText().toString(), oldname);
                        master.renameList(oldname, updatedText.getText().toString());
                        closeKeyboard(mView);
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                alert.show();
            }
        });

        return customView;
    }

    private void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
