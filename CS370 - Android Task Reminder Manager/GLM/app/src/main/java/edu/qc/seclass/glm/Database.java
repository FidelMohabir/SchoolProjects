package edu.qc.seclass.glm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    String CREATE_REMINDERLISTS_TABLE = "CREATE TABLE ReminderLists(" +
            "reminderlistID INTEGER PRIMARY KEY," +
            "reminderlistname TEXT)";

    String CREATE_REMINDERTYPES_TABLE = "CREATE TABLE ReminderTypes(" +
            "remindertypeID INTEGER PRIMARY KEY," +
            "remindertypename TEXT)";

    String CREATE_REMINDERSUBTYPES_TABLE = "CREATE TABLE ReminderSubTypes(" +
            "remindersubtypeID INTEGER PRIMARY KEY," +
            "remindersubtypename TEXT," +
            "remindertypeID INTEGER NOT NULL REFERENCES ReminderTypes(remindertypeID))";

    String CREATE_ALERTS_TABLE = "CREATE TABLE Alerts(" +
            "alertID INTEGER PRIMARY KEY," +
            "isactive INTEGER," +
            "day BIGINT," +
            "repeatdaily INTEGER," +
            "repeatweekly INTEGER," +
            "repeatmonthly INTEGER," +
            "repeatyearly INTEGER)";

    String CREATE_REMINDERS_TABLE = "CREATE TABLE Reminders(" +
            "reminderID INTEGER PRIMARY KEY," +
            "reminderlistID INTEGER NOT NULL REFERENCES ReminderLists(reminderlistID)," +
            "remindertypeID INTEGER NOT NULL REFERENCES ReminderTypes(remindertypeID)," +
            "remindername TEXT," +
            "ischecked INTEGER DEFAULT 0," +
            "alertID INTEGER NOT NULL REFERENCES Alerts(alertID))";

    public Database(Context context)
    {
        super(context, "ReminderApp", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_REMINDERLISTS_TABLE);
        db.execSQL(CREATE_REMINDERTYPES_TABLE);
        db.execSQL(CREATE_REMINDERSUBTYPES_TABLE);
        db.execSQL(CREATE_ALERTS_TABLE);
        db.execSQL(CREATE_REMINDERS_TABLE);

        addType("Meeting");
        addType("Appointment");
        addType("Exercise");

        addSubType("School", "Meeting");
        addSubType("Work", "Meeting");
        addSubType("Physician", "Appointment");
        addSubType("Dentist", "Appointment");
        addSubType("Eye Doctor", "Appointment");
        addSubType("Run", "Exercise");
        addSubType("Lift", "Exercise");
        addSubType("Swim", "Exercise");
        addSubType("Boxing", "Exercise");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void resetDB(){
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "drop table if exists ReminderLists";
        db.execSQL(sql);
        sql = "drop table if exists ReminderTypes";
        db.execSQL(sql);
        sql = "drop table if exists ReminderSubTypes";
        db.execSQL(sql);
        sql = "drop table if exists ReminderTypes";
        db.execSQL(sql);
        sql = "drop table if exists Alerts";
        db.execSQL(sql);
        sql = "drop table if exists Reminders";
        db.execSQL(sql);

        db.execSQL(CREATE_REMINDERLISTS_TABLE);
        db.execSQL(CREATE_REMINDERTYPES_TABLE);
        db.execSQL(CREATE_REMINDERSUBTYPES_TABLE);
        db.execSQL(CREATE_ALERTS_TABLE);
        db.execSQL(CREATE_REMINDERS_TABLE);

        addType("Meeting");
        addType("Appointment");
        addType("Exercise");

        addSubType("School", "Meeting");
        addSubType("Work", "Meeting");
        addSubType("Physician", "Appointment");
        addSubType("Dentist", "Appointment");
        addSubType("Eye Doctor", "Appointment");
        addSubType("Run", "Exercise");
        addSubType("Lift", "Exercise");
        addSubType("Swim", "Exercise");
        addSubType("Boxing", "Exercise");
    }

    ArrayList<String> getAllLists(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<String> listnames = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT reminderlistname FROM ReminderLists", null);

        if (c.moveToFirst()) {
            do {
                listnames.add(c.getString(c.getColumnIndex("reminderlistname")));
            } while (c.moveToNext());
        }

        c.close();
        return listnames;
    }

    ArrayList<String> getAllTypes(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<String> typenames = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT remindertypename FROM ReminderTypes", null);

        if (c.moveToFirst()) {
            do {
                typenames.add(c.getString(c.getColumnIndex("remindertypename")));
            } while (c.moveToNext());
        }

        c.close();
        return typenames;
    }

    ArrayList<String> getSubTypes(String type){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<String> subtypes = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT remindersubtypename " +
                        "FROM ReminderSubTypes AS sub " +
                        "LEFT JOIN ReminderTypes AS type ON sub.remindertypeID = type.remindertypeID " +
                        "WHERE type.remindertypename ='" + type + "'",
                null);

        if (c.moveToFirst()) {
            do {
                subtypes.add(c.getString(c.getColumnIndex("remindersubtypename")));
            } while (c.moveToNext());
        }

        c.close();
        return subtypes;
    }

    ArrayList<Reminder> getRemindersByList(String sList){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Reminder> reminders = new ArrayList<Reminder>();

        int listid = getListID(sList);

        String[] column_names = new String[] {"reminderID","reminderlistID","remindertypeID","remindername","ischecked","alertID"};
        String where_clause = "CAST(reminderlistID as VARCHAR) = ?";
        Cursor cursor = db.query("Reminders", column_names, where_clause, new String[] { Integer.toString(listid) }, null, null,
                "remindertypeID");

        if (cursor.moveToFirst()) {
            do {
                String list = getListName(cursor.getInt(cursor.getColumnIndex("reminderlistID")));
                String type = getTypeName(cursor.getInt(cursor.getColumnIndex("remindertypeID")));
                int c = cursor.getInt(cursor.getColumnIndex("ischecked"));
                Boolean checked = (c == 1);
                Reminder r = new Reminder(list, type, cursor.getString(cursor.getColumnIndex("remindername")));
                r.reminderID = cursor.getInt(cursor.getColumnIndex("reminderID"));
                r.setChecked(checked);
                reminders.add(r);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return reminders;
    }

    ArrayList<Reminder> getRemindersByType(String sType){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Reminder> reminders = new ArrayList<Reminder>();

        int typeid = getTypeID(sType);
        Cursor cursor = db.query("Reminders",
                new String[]{"reminderID","reminderlistID","remindertypeID","remindername","ischecked","ischecked"},
                "remindertypeID =?",
                new String[]{String.valueOf(typeid)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        String list = getListName(cursor.getInt(cursor.getColumnIndex("reminderlistID")));
        String type = getTypeName(cursor.getInt(cursor.getColumnIndex("remindertypeID")));

        Reminder r = new Reminder(list, type, cursor.getString(cursor.getColumnIndex("remindername")));
        reminders.add(r);

        cursor.close();

        return reminders;
    }

    Reminder getReminder(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("Reminders",
                                new String[]{"reminderID","reminderlistID","remindertypeID","remindername","ischecked","ischecked"},
                                "reminderID =?",
                                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        String list = getListName(cursor.getInt(cursor.getColumnIndex("reminderlistID")));
        String type = getTypeName(cursor.getInt(cursor.getColumnIndex("remindertypeID")));

        Reminder r = new Reminder(list, type, cursor.getString(cursor.getColumnIndex("remindername")));

        cursor.close();

        return r;
    }

    Alert getAlert(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT alertID,isactive,day,repeatdaily,repeatweekly,repeatmonthly,repeatyearly " +
                                    "FROM Alerts " +
                                    "WHERE alertID=?", new String[]{Long.toString(id)});

        c.moveToFirst();
        int alertid = c.getInt(c.getColumnIndex("alertID"));
        int date = c.getInt(c.getColumnIndex("day"));
        boolean day = c.getInt(c.getColumnIndex("repeatdaily"))==1;
        boolean week = c.getInt(c.getColumnIndex("repeatweekly"))==1;
        boolean month = c.getInt(c.getColumnIndex("repeatmonthly"))==1;
        boolean year = c.getInt(c.getColumnIndex("repeatyearly"))==1;

        Alert a = new Alert(date);
        a.setRepeatDaily(day);
        a.setRepeatWeekly(week);
        a.setRepeatMonthly(month);
        a.setRepeatYearly(year);
        c.close();

        return a;
    }

    int getListID(String name){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT reminderlistID FROM ReminderLists " +
                                    "WHERE reminderlistname='" +name+ "'", null);

        //int ret = c.getInt(c.getColumnIndex("reminderlistID"));
        c.moveToFirst();
        int ret = c.getInt(0);
        c.close();
        return ret;
    }

    int getTypeID(String name){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT remindertypeID FROM ReminderTypes " +
                                    "WHERE remindertypename='" +name+ "'", null);


        //int ret = c.getInt(c.getColumnIndex("remindertypeID"));
        c.moveToFirst();
        int ret = c.getInt(0);
        c.close();
        return ret;
    }

    String getListName(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT reminderlistname FROM ReminderLists " +
                                    "WHERE reminderlistID=?", new String[]{Integer.toString(id)});

        /*String where_clause = "CAST(reminderlistID as VARCHAR) = ?";
        Cursor c = db.query("ReminderLists", new String[]{"reminderlistname"}, where_clause, new String[] { Integer.toString(id) }, null, null,
                null);*/

        c.moveToFirst();
        String ret = c.getString(c.getColumnIndex("reminderlistname"));
        //String ret = c.getString(0);
        c.close();
        return ret;
    }

    String getTypeName(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT remindertypename FROM ReminderTypes " +
                                    "WHERE remindertypeID=" +id, null);

        c.moveToFirst();
        String ret = c.getString(c.getColumnIndex("remindertypename"));
        c.close();
        return ret;
    }

    void addList(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("reminderlistname", name);
        db.insert("ReminderLists", null, values);

    }

    void deleteList(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        String[] args = {name};
        db.delete("ReminderLists", "reminderlistname =?" , new String[]{name});

    }

    void deleteType(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        String[] args = {name};
        db.delete("ReminderTypes", "remindertypename =?" , new String[]{name});

    }

    void editListName(String newname, String oldname){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("reminderlistname", newname);
        db.update("ReminderLists", values, "reminderlistname = ?", new String[]{oldname});
    }

    void addType(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("remindertypename", name);
        db.insert("ReminderTypes", null, values);

    }

    public long addReminder(Reminder r){
        SQLiteDatabase db = this.getWritableDatabase();

        int listID = getListID(r.reminderList);
        int typeID = getTypeID(r.reminderType);

        ContentValues values = new ContentValues();
        values.put("reminderlistID", listID);
        values.put("remindertypeID", typeID);
        values.put("remindername", r.reminderName);
        if(r.hasAlert()) values.put("alertID", r.getAlert().getAlertID());
        else values.put("alertID", 0);
        values.put("ischecked", r.isChecked());

        long result = db.insert("Reminders", null, values);

        return result;
    }

    int editReminder(Reminder r){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("remindername", r.reminderName);

        return db.update("Reminder", values,  "reminderID = ?",
                new String[]{String.valueOf(r.reminderID)});
    }

    public void deleteReminder(Reminder r) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Reminders","reminderID = ?", new String[]{String.valueOf(r.reminderID)});
    }

    void uncheckAll(String listName, ArrayList<Reminder> reminders){
        SQLiteDatabase db = this.getWritableDatabase();

        for (Reminder r : reminders) {
            ContentValues values = new ContentValues();
            values.put("ischecked", false);

            db.update("Reminders", values,"reminderID = ?", new String[]{String.valueOf(r.reminderID)});
        }
    }

    void addSubType(String name, String type){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("remindersubtypename", name);
        values.put("remindertypeID", getTypeID(type));
        db.insert("ReminderSubTypes", null, values);

    }

    void uncheck(Reminder r){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("UPDATE Reminders " +
                "SET ischecked = 0 " +
                "WHERE remindertypeID=" +r.reminderID, null);
    }
    void check(Reminder r){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("UPDATE Reminders " +
                                    "SET ischecked = 1 " +
                                    "WHERE remindertypeID=?", new String[]{Integer.toString(r.reminderID)});
        /*ContentValues values = new ContentValues();
        values.put("ischecked", 1);

        return db.update("Reminder", values,"reminderID = ?", new String[]{String.valueOf(r.reminderID)});*/
    }

    long addAlert(Alert a){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("isactive", true);
        values.put("day", a.getDay());
        values.put("repeatdaily", a.isRepeatDaily());
        values.put("repeatweekly", a.isRepeatWeekly());
        values.put("repeatmonthy", a.isRepeatMonthly());
        values.put("repeatyearly", a.isRepeatYearly());

        return db.insert("Alerts", null, values);
    }

    int unactivateAlert(Alert a){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("isactive", false);

        return db.update("Alert", values,"alertID = ?",
                new String[]{String.valueOf(a.getAlertID())});
    }
    int activateAlert(Alert a){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("isactive", true);

        return db.update("Alert", values,"alertID = ?",
                new String[]{String.valueOf(a.getAlertID())});
    }

    int editAlert(Alert a){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("isactive", true);
        values.put("day", a.getDay());
        values.put("repeatdaily", a.isRepeatDaily());
        values.put("repeatweekly", a.isRepeatWeekly());
        values.put("repeatmonthy", a.isRepeatMonthly());
        values.put("repeatyearly", a.isRepeatYearly());

        return db.update("Alert", values,  "reminderID = ?",
                new String[]{String.valueOf(a.getAlertID())});
    }
}