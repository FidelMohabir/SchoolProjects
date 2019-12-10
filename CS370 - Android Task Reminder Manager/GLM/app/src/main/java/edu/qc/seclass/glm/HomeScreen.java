package edu.qc.seclass.glm;

import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();

    MasterList master;
    Database db;
    ArrayList<ReminderList> reminderListNames = new ArrayList<ReminderList>();
    ListAdapter customAdapter;
    ListView reminderListsView;
    LayoutInflater inflater;
    EditText reminderListName;
    TextView mainTitle;
    TextView mainCaption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        db = new Database(this);

        setUpMasterList();

        //Set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Edit Text
        reminderListName = findViewById(R.id.reminderListName);
        //ListView for Reminder Lists
        reminderListsView = (ListView) findViewById(R.id.remindersListView);

        mainTitle = findViewById(R.id.mainTitle);
        mainCaption = findViewById(R.id.mainCaption);

        customAdapter = new CustomAdapter(this, reminderListNames);
        reminderListsView.setAdapter(customAdapter);
        inflater = this.getLayoutInflater();


        FloatingActionButton addingNewReminderList = (FloatingActionButton) findViewById(R.id.addReminderListButton);
        addingNewReminderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewReminderListDialog();
            }
        });

        reminderListsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.reminderListTitle);
                Intent nextActivity = new Intent(view.getContext(), reminderListActivity.class);
                nextActivity.putExtra("ListName", textView.getText());
                nextActivity.putExtra("Index", position);
                startActivityForResult(nextActivity, 1);
            }
        });

        renderList();
    }

    void setUpMasterList(){
        master = MasterList.getInstance();

        ArrayList<String> lists = db.getAllLists();
        ArrayList<String> types = db.getAllTypes();

        int index = 0;
        for (String s:lists) {
            master.addList(s);
            ArrayList<Reminder> reminderArray = db.getRemindersByList(s);
            for (Reminder r:reminderArray) {
                master.getAllLists().get(index).addReminderToList(r);
            }
            index+=1;
        }
        reminderListNames = master.getAllLists();
    }

    public void renderList(){
        if(!reminderListNames.isEmpty()){
            mainTitle.setVisibility(View.INVISIBLE);
            mainCaption.setVisibility(View.INVISIBLE);
        } else {
            mainTitle.setVisibility(View.VISIBLE);
            mainCaption.setVisibility(View.VISIBLE);
        }
    }

    public void updateView(){
        reminderListNames = master.getAllLists();
        reminderListsView.setAdapter(new CustomAdapter(this, reminderListNames));
        renderList();
    }

    public void addNewReminderTypesDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Add Types");
        final View mView = inflater.inflate(R.layout.add_reminders_type, null);
        alert.setView(mView);

        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //User Adds Reminder Type
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        alert.show();
    }

    public void createNewReminderListDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Create New Reminder List");
        final View mView = inflater.inflate(R.layout.create_reminders_list, null);
        alert.setView(mView);

        alert.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText edt = mView.findViewById(R.id.reminderListName);
                //ReminderList reminderList = new ReminderList(edt.getText().toString());

                //reminderListNames.add(reminderList);
                String listname = edt.getText().toString();
                master.addList(listname);
                db.addList(listname);
                updateView();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        alert.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == 1) {

            }
        }
    }
}
