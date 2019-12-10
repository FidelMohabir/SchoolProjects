package edu.qc.seclass.glm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class reminderListActivity extends AppCompatActivity {

    Database db;
    MasterList master;
    ArrayList<Reminder> reminders;
    public static String listname;
    public static int index;
    ListAdapter customAdapter;
    ListView reminderView;
    LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reminder_list);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            Intent i = getIntent();
            listname = i.getStringExtra("ListName");
            index = i.getIntExtra("Index", 0);

            TextView text = (TextView) findViewById(R.id.listName);
            text.setText(listname);

            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            db = new Database(this);
            master = MasterList.getInstance();
            reminders = master.getAllLists().get(index).getRemindersInList();
            //reminders = db.getRemindersByList(listname);

            reminderView = (ListView)findViewById(R.id.remindersView);
            customAdapter = new CustomAdapterReminders(this, reminders);
            reminderView.setAdapter(customAdapter);
            inflater = this.getLayoutInflater();

            FloatingActionButton addingReminderButton = (FloatingActionButton) findViewById(R.id.btnAddReminder);
            addingReminderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity();
                }
            });

            Button uncheckall  = (Button) findViewById(R.id.btnUncheckAll);
            uncheckall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    master.getAllLists().get(index).uncheckAll();
                    updateView();
                }
            });
        }
        catch (Exception e){
            int x = 1;
        }
    }

    public void updateView(){
        try {
            reminders = master.getAllLists().get(index).getRemindersInList();
            reminderView.setAdapter(new CustomAdapterReminders(this, reminders));
        }
        catch (Exception e){
            int x  =1;
        }
    }

    public void openActivity(){
        Intent intent = new Intent(this, AddReminder.class);
        intent.putExtra("ListName", listname);
        intent.putExtra("Index", index);
        startActivityForResult(intent,1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            updateView();
        }
    }
}
