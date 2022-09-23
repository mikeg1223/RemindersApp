package edu.qc.seclass.glm;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // Create new database
        super(context, "RemindersDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Create tables for schema
        sqLiteDatabase.execSQL("create table CurrentID(reminderListID INTEGER, reminderID INTEGER,"+
                                " alertID INTEGER, PRIMARY KEY (reminderListID, reminderID, alertID));");

        sqLiteDatabase.execSQL("create table User(username TEXT primary key, password TEXT NOT NULL, usingAlerts INT, email TEXT," +
                                "firstName TEXT, lastName TEXT);");

        sqLiteDatabase.execSQL("create table ReminderType(reminderTypeName TEXT primary key);");

        sqLiteDatabase.execSQL("create table ReminderList(reminderListID INTEGER primary key, reminderListName TEXT, " +
                                "username TEXT, UNIQUE(reminderListName, username), FOREIGN KEY(username) REFERENCES User(username));");

        sqLiteDatabase.execSQL("create table Reminder(reminderID INTEGER primary key, reminderName TEXT, " +
                                "priority INTEGER, completed INTEGER, reminderListID INTEGER, alertID INTEGER, " +
                                "reminderTypeName TEXT, username TEXT, description TEXT, UNIQUE(reminderName, username)," +
                                "FOREIGN KEY(reminderTypeName) REFERENCES ReminderType(reminderTypeName), FOREIGN KEY(username) REFERENCES User(username),"+
                                " FOREIGN KEY(reminderListID) REFERENCES ReminderList(reminderListID));");

        sqLiteDatabase.execSQL("create table Alert(alertID INTEGER primary key, alertDetails TEXT, reminderID INTEGER," +
                "FOREIGN KEY(reminderID) REFERENCES Reminder(reminderID));");

        sqLiteDatabase.execSQL("create table lastLoggedIn(id INTEGER PRIMARY KEY, username TEXT, password TEXT, isValid BOOL);");

        // create default id counter tuple
        ContentValues contentValues = new ContentValues();
        contentValues.put("reminderListID", 1);
        contentValues.put("reminderID", 1);
        contentValues.put("alertID", 1);
        long results = sqLiteDatabase.insert("CurrentID", null, contentValues);
        System.out.print(results);

        // create lastLoggedIn Tuple for storing log-ins
        contentValues = new ContentValues();
        contentValues.put("id", 0);
        contentValues.putNull("username");
        contentValues.putNull("password");
        contentValues.putNull("isValid");
        results = sqLiteDatabase.insert("lastLoggedIn", null, contentValues);

        // create ReminderListType's
        contentValues = new ContentValues();
        contentValues.put("reminderTypeName", "Doctor");
        results = sqLiteDatabase.insert("ReminderType", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("reminderTypeName", "Appointments");
        results = sqLiteDatabase.insert("ReminderType", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("reminderTypeName", "Exams");
        results = sqLiteDatabase.insert("ReminderType", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("reminderTypeName", "School");
        results = sqLiteDatabase.insert("ReminderType", null, contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Reminder");
    }

    public boolean updateID(String idName, int newCount){

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(idName, newCount);
        long results = DB.update("CurrentID", contentValues, null, null);
        return results != -1;

    }

    @SuppressLint("Range")
    public int getID(String idName){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.query("CurrentID", new String[]{idName}, null, null, null, null, null);
        cursor.moveToFirst();
        int return_value = cursor.getInt(cursor.getColumnIndex(idName));
        cursor.close();
        return return_value;
    }

    public boolean updateLastLogIn(User user){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("isValid", true);
        long results = DB.update("lastLoggedIn", contentValues,null,null);
        return results != -1;
    }

    public boolean logOut(){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("isValid", false);
        long results = DB.update("lastLoggedIn", contentValues,null,null);
        return results != -1;
    }

    public boolean queryUserName(String username){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.query("User", new String[]{"username"},"username = ?",
                                new String[] {username},null, null, null);
        boolean return_value = cursor.moveToFirst(); //returns false if the cursor is empty
        cursor.close();
        return return_value;
    }

    public boolean insertUser(User user){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("usingAlerts", user.getUsingAlerts());
        long results = DB.insert("User", null, contentValues);
        return results != -1;
    }


    @SuppressLint("Range")
    public User getUser(String username){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.query("User", new String[]{"username", "password", "usingAlerts",
                                "email", "firstName", "lastName"}, "username = ?",
                                new String[]{username}, null, null, null);
        cursor.moveToFirst();
        User ret_user = new User(cursor.getString(cursor.getColumnIndex("username")),
                        cursor.getString(cursor.getColumnIndex("password")),
                cursor.getInt(cursor.getColumnIndex("usingAlerts"))>0 );
        if (!cursor.isNull(cursor.getColumnIndex("email"))){
            ret_user.setPersonalInfo(cursor.getString(cursor.getColumnIndex("firstName")),
                                    cursor.getString(cursor.getColumnIndex("lastName")),
                                    cursor.getString(cursor.getColumnIndex("email")));
        }
        cursor.close();
        return ret_user;
    }


    public boolean userLoginAttempt(String inUser, String inPass){
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = DB.query("User", new String[]{"username"}, "password = ? AND username = ?", new String[]{inPass, inUser}, null, null, null );
        boolean return_value = cursor.getCount() == 1;
        cursor.close();
        return return_value;
    }

    @SuppressLint("Range")
    public String[] isLoggedIn(){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.query("lastLoggedIn", new String[]{"username", "password", "isValid"}, null, null, null, null, null);
        String[] ret_val = null;
        cursor.moveToFirst();
        if (cursor != null){
            cursor.moveToFirst();
            ret_val = new String[]{cursor.getString(cursor.getColumnIndex("username")), cursor.getString(cursor.getColumnIndex("password")),
                                    String.valueOf(cursor.getInt(2) > 0)};
        }
        cursor.close();
        return ret_val;
    }

    public boolean updateUser(User user){

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("usingAlerts", user.getUsingAlerts());
        long results;
        if (user.isPersonalSet()) {
            contentValues.put("email", user.getEmail());
            contentValues.put("firstName", user.getFirstName());
            contentValues.put("lastName", user.getLastName());
        }
        results = DB.update("User", contentValues, "username = ?", new String[]{user.getUsername()});
        return results != -1;

    }

    public boolean deleteUser(User user){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.delete("User", "username = ?", new String[]{user.getUsername()}) != -1;
    }

    public boolean insertReminder(Reminder reminder, ReminderList reminderlist,User user){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("reminderID", reminder.getReminderID());
        contentValues.put("reminderName", reminder.getName());
        contentValues.put("reminderTypeName", reminder.getReminderTypeName());
        contentValues.put("reminderListID", reminderlist.getReminderListID());
        contentValues.put("priority", reminder.getPriority());
        contentValues.put("completed", reminder.isCompleted());
        contentValues.put("username", user.getUsername());
        contentValues.put("description", reminder.getDescription());

        long response = -1;
        try
        {
            response = DB.insert("Reminder", null, contentValues);
            updateID("reminderID", reminder.getReminderID());
        }
        catch(SQLException e)
        {
            // Sep 12, 2013 6:50:17 AM
            Log.e("Exception","SQLException"+String.valueOf(e.getMessage()));
            e.printStackTrace();
        }

        // FIXME ReminderID gets incremented even when no reminder is added to the DB

        if(response != -1){
            updateID("reminderID", Reminder.getCurrentID());
        }

        return response != -1;
    }

    public boolean deleteReminder(Reminder reminder){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.delete("Reminder", "reminderID = ?", new String[]{Integer.toString(reminder.getReminderID())}) != -1;
    }

    public boolean updateReminder(Reminder reminder, ReminderList reminderlist, User user){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("reminderName", reminder.getName());
        contentValues.put("reminderTypeName", reminder.getReminderTypeName());
        contentValues.put("reminderListID", reminderlist.getReminderListID());
        contentValues.put("priority", reminder.getPriority());
        contentValues.put("completed", reminder.isCompleted());
        contentValues.put("username", user.getUsername());
        contentValues.put("description", reminder.getDescription());
        long results = DB.update("Reminder", contentValues, "reminderID = ?", new String[]{Integer.toString(reminder.getReminderID())});
        return results != -1;
    }

    public boolean insertReminderList(ReminderList reminderlist, User user){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("reminderListID", reminderlist.getReminderListID());
        contentValues.put("reminderListName", reminderlist.getName());
        contentValues.put("username", user.getUsername());

        long response = -1;
        try
        {
            response = DB.insert("ReminderList", null, contentValues);
        }
        catch(SQLException e)
        {
            // Sep 12, 2013 6:50:17 AM
            Log.e("Exception","SQLException"+String.valueOf(e.getMessage()));
            e.printStackTrace();
        }

        if(response != -1) {
            updateID("ReminderListID", ReminderList.getCurrentID());
        }

        return response != -1;
    }

    public boolean updateReminderList(ReminderList reminderlist, User user, String newName){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("reminderListID", reminderlist.getReminderListID());
        contentValues.put("reminderListName", newName);
        contentValues.put("username", user.getUsername());
        long results = DB.update("ReminderList", contentValues, "reminderListID = ?", new String[]{Integer.toString(reminderlist.getReminderListID())});
        return results != -1;
    }

    public boolean deleteReminderList(ReminderList reminderlist){
        SQLiteDatabase DB = this.getWritableDatabase();

        for(Reminder reminder: reminderlist.getList()){ // delete all reminders that belong to this list
            this.deleteReminder(reminder);
        }
        DB.delete("Reminder", "reminderListID =  ?", new String[]{Integer.toString(reminderlist.getReminderListID())});
        // FIXME function doesn't delete reminders after 1+ list name change
        return DB.delete("ReminderList", "reminderListID =  ?", new String[]{Integer.toString(reminderlist.getReminderListID())}) != -1;
    }

    @SuppressLint("Range")
    public ReminderList getReminderList(int reminderListID, User user){
        SQLiteDatabase DB = this.getReadableDatabase();
        ReminderList ret_reminderList;

        Cursor cursor = DB.query("ReminderList", new String[]{"reminderListID", "reminderListName"},
                "reminderListID = ? AND username = ?",
                new String[]{String.valueOf(reminderListID), user.getUsername()}, null, null, null);

        if(cursor.moveToFirst()){
            ret_reminderList = new ReminderList(cursor.getInt(cursor.getColumnIndex("reminderListID")),
                cursor.getString(cursor.getColumnIndex("reminderListName")));

            Cursor reminderCursor = DB.query("Reminder", null,"reminderListID = ?",
                    new String[] {String.valueOf(ret_reminderList.getReminderListID())},null, null, null);

            while(reminderCursor.moveToNext()) {
                Cursor userCursor = DB.query("User", null,"username = ?",
                        new String[] {reminderCursor.getString(1)},null, null, null);

                boolean usesAlerts = false;
                if(userCursor.moveToFirst()){
                    usesAlerts = userCursor.getInt(userCursor.getColumnIndex("usingAlerts")) ==1;
                }
                Reminder reminder = new Reminder(reminderCursor.getString(reminderCursor.getColumnIndex("reminderName")),
                        new ReminderType(reminderCursor.getString(reminderCursor.getColumnIndex("reminderTypeName"))),
                        1==reminderCursor.getInt(reminderCursor.getColumnIndex("completed")),
                        reminderCursor.getInt(reminderCursor.getColumnIndex("priority")),
                        usesAlerts, reminderCursor.getInt(reminderCursor.getColumnIndex("reminderID")),
                        reminderCursor.getString(reminderCursor.getColumnIndex("description")));

                ret_reminderList.addReminder(reminder);
            }
        }else{
            ret_reminderList = null;//new ReminderList(reminderListName); // list is not in DB
            //this.insertReminderList(ret_reminderList, user);
        }
        cursor.close();
        return ret_reminderList;
    }


    public boolean insertReminderType(Reminder reminder){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("reminderTypeName",String.valueOf(reminder.getReminderTypeName()));

        long response = -1;
        try
        {
            response = DB.insert("ReminderType", null, contentValues);
            updateID("reminderTypeID", reminder.getReminderTypeID());
        }
        catch(SQLException e)
        {
            // Sep 12, 2013 6:50:17 AM
            Log.e("Exception","SQLException"+String.valueOf(e.getMessage()));
            e.printStackTrace();
        }

        return response != -1;
    }


    public boolean insertAlert(Alert alert){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("alertID", alert.getID());
        contentValues.put("reminderID", alert.getReminderID());
        contentValues.put("alertDetails", alert.toString());
        long results = DB.insert("Alert", null, contentValues);
        updateID("alertID", Alert.getCurrentID());
        return results != -1;
    }

    public boolean updateAlert(Alert alert){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("alertID", alert.getID());
        contentValues.put("reminderID", alert.getReminderID());
        contentValues.put("alertDetails", alert.toString());
        long results = DB.update("Alert", contentValues, "alertID = ?", new String[]{Integer.toString(alert.getID())});
        return results != -1;
        //testing
    }

    @SuppressLint("Range")
    public Alert getAlert(Reminder reminder){

        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.query("Alert", new String[]{"alertID", "alertDetails", "reminderID"},
                "reminderID = ?", new String[]{String.valueOf(reminder.getReminderID())},
                null, null, null);

        if(cursor.moveToFirst()) {
            DateTimeAlert dateTimeAlert = new DateTimeAlert(cursor.getString(cursor.getColumnIndex("alertDetails")),
                    reminder,
                    cursor.getInt(cursor.getColumnIndex("alertID")));
            return dateTimeAlert;
        }
        return null;
    }

    public boolean deleteAlert(Alert alert){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.delete("Alert", "alertID = ?", new String[]{Integer.toString(alert.getID())}) != -1;
    }

    public boolean truncateAlertTable(){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.delete("Alert", null, null) != -1;
    }

    @SuppressLint("Range")
    public Reminder queryReminder(String reminderName){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.query("Reminder", null,"LOWER(reminderName) = LOWER(?)",
                new String[] {reminderName},null, null, null);

        if(cursor.moveToFirst()){
            // query Alert table to find out whether reminder has alerts
            Cursor alertCursor = DB.query("Alert", null, "reminderID = ?",
                    new String[] {String.valueOf(cursor.getInt(0))}, null, null, null);

            Reminder reminder;
            reminder = new Reminder(cursor.getString(1),
                    new ReminderType(cursor.getString(6)),
                    1==cursor.getInt(3),
                    cursor.getInt(2),
                    alertCursor.moveToFirst());
            reminder.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            cursor.close();
            return reminder;
        }
        cursor.close();
        return null; //returns null if the cursor is empty
    }

    public Cursor getAllReminderLists(User user){
        SQLiteDatabase DB = this.getWritableDatabase();
        String name = user.getUsername();
        Cursor cursor = DB.query("ReminderList",null,"username = ?",new String[]{name}, null, null, null);
        return cursor;
    }

    public Cursor getAllReminders(ReminderList reminderlist, User user){
        SQLiteDatabase DB = this.getReadableDatabase();
        String name = user.getUsername();
        String list = Integer.toString(reminderlist.getReminderListID());
        Cursor cursor = DB.query("Reminder",null, "reminderListID = ? and username = ?", new String[]{list, name},null,null,"reminderTypeName");

        return cursor;
    }

}
