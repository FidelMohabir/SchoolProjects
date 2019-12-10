package edu.qc.seclass.glm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

public class AddReminder extends AppCompatActivity {

    Database db;
    MasterList master;
    ArrayList<String> types;
    ArrayList<String> subtypes;
    Spinner dropdowntype;
    Spinner dropdownsubtypes;
    Button addAlert;
    Button save;
    ArrayAdapter<String> adapter;
    Calendar date;
    int index;
    String listname;
    Alert alertToAdd = null;
    LayoutInflater inflater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        Intent i = getIntent();
        index = i.getIntExtra("Index", 0);
        listname = i.getStringExtra("ListName");

        master = MasterList.getInstance();
        db = new Database(this);
        inflater = this.getLayoutInflater();
        dropdowntype = (Spinner)findViewById(R.id.dropdownType);

        setTypeDropdown();
        dropdowntype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                setSubTypeDropDown();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, new String[0]);
                dropdownsubtypes.setAdapter(adapter);
            }

        });

        Button addtype = (Button) findViewById(R.id.btnAddType);
        Button addsubtype = (Button) findViewById(R.id.btnAddSubType);
        Button cancel  = (Button) findViewById(R.id.btnCancel);

        addtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTypeDialog();
            }
        });
        addsubtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewSubTypeDialog();
            }
        });

        addAlert = (Button) findViewById(R.id.btnAddAlert);
        addAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AlertDialog.class);
                startActivityForResult(intent, 1);
            }
        });

        save = (Button) findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = dropdowntype.getSelectedItem().toString();
                String subtype = dropdownsubtypes.getSelectedItem().toString();
                Reminder r = new Reminder(listname, type, subtype);
                if(alertToAdd!=null){
                    r.addAlert(alertToAdd);
                }
                long result = db.addReminder(r);
                //master.getAllLists().get(index).addReminderToList(r);
                master.getAllLists().get(index).reloadReminders(db);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == 1) {
                long time = data.getLongExtra("date", 0);
                if (time!=0) {
                    alertToAdd = new Alert(time);
                    long id = db.addAlert(alertToAdd);
                    alertToAdd.setAlertID(id);
                    alertToAdd.setRepeatDaily(data.getBooleanExtra("day",false));
                    alertToAdd.setRepeatWeekly(data.getBooleanExtra("week",false));
                    alertToAdd.setRepeatMonthly(data.getBooleanExtra("month",false));
                    alertToAdd.setRepeatYearly(data.getBooleanExtra("year",false));
                    addAlert.setText("Edit Alert");
                    addAlert.setEnabled(false);
                }
            }
        }
    }

    void setTypeDropdown(){
        types = db.getAllTypes();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, types.toArray(new String[0]));
        dropdowntype.setAdapter(adapter);
    }

    void setSubTypeDropDown(){
        String typechosen = dropdowntype.getSelectedItem().toString();
        subtypes = db.getSubTypes(typechosen);
        dropdownsubtypes = (Spinner)findViewById(R.id.dropdownName);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, subtypes.toArray(new String[0]));
        dropdownsubtypes.setAdapter(adapter);
    }

    public void createNewTypeDialog(){
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);
        alert.setTitle("Create New Type");
        final View mView = inflater.inflate(R.layout.create_type, null);
        alert.setView(mView);

        alert.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText edt = mView.findViewById(R.id.addTypeName);
                String typetoadd = edt.getText().toString();
                db.addType(typetoadd);
                setTypeDropdown();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        alert.show();
    }

    public void createNewSubTypeDialog(){
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);
        alert.setTitle("Create New SubType");
        final View mView = inflater.inflate(R.layout.crreate_subtype, null);
        alert.setView(mView);

        alert.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText edt = mView.findViewById(R.id.addSubTypeName);
                String typetoadd = edt.getText().toString();
                db.addSubType(typetoadd, dropdowntype.getSelectedItem().toString());
                setSubTypeDropDown();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        alert.show();
    }
}
