package edu.qc.seclass.glm;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private boolean usingAlerts;
    private String email;
    private String fName;
    private String lName;
    private boolean personalSet;
    private ArrayList<ReminderList> listOfReminderLists;

    public User (String name, String password, boolean usingAlerts) {
        this.username = name;
        this.password = password;
        this.usingAlerts = usingAlerts;
        this.personalSet = false;
    }

    public void setPersonalInfo(String first, String last, String email){
        this.fName = first;
        this.lName = last;
        this.email = email;
        this.personalSet = true;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return password;
    }

    public int getUsingAlerts(){ return (usingAlerts ? 1: 0);}
    public boolean getUsingAlertsBoolean(){ return this.usingAlerts;}

    public boolean isPersonalSet(){return personalSet;}

    public String getEmail(){return this.email;}

    public String getFirstName(){return this.fName;}

    public String getLastName(){return this.lName;}

    public void addReminderList(ReminderList reminderList) {
        this.listOfReminderLists.add(reminderList);
    }

    public void deleteReminderList(ReminderList reminderList) {
        listOfReminderLists.remove(reminderList);
        reminderList = null;
    }

    public int getReminderListCount(){
        return listOfReminderLists.size();
    }
}
