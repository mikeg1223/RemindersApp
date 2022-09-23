package edu.qc.seclass.glm;

import java.util.ArrayList;

public class ReminderList {
    private static int currentID = 1;
    private int reminderListID;
    private String name;
    private ArrayList<Reminder> list = new ArrayList<>();

    public ReminderList(String name) {
        this.name = name;
        this.reminderListID = currentID;
        ReminderList.currentID += 1;
    }
    public ReminderList(int id, String name) {
        this.name = name;
        this.reminderListID = id;
    }

    static public int getCurrentID(){
        return currentID;
    }

    public int getReminderListID(){return this.reminderListID;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addReminder(Reminder reminder){
        list.add(reminder);
    }

    public void deleteReminder(Reminder reminder){
        list.remove(reminder);
        reminder = null;
    }

    static public void setCurrentID(int id){
        try {
            if (id < 0) {
                throw new Exception("id error");
            }
            currentID = id;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void checkOffReminder(Reminder reminder){
        if(list.contains(reminder)){
            reminder.checkOff();
        }
    }

    //checks off all reminders
    public void checkOffAllReminders(){
        for(Reminder reminder: list)
        {
            reminder.checkOff();
        }
    }

    // deletes all reminders
    public void clearReminderList() {
        list.clear();
    }

    public ArrayList<Reminder> getList() {
        return this.list;
    }

    public Reminder findReminderByName(String reminderName){
        for(Reminder reminder: this.list)
        {
            if(reminderName.equals(reminder.getName())){
                return reminder;
            }
        }
        return null;
    }

    public int getReminderCount(){
       return list.size();
    }

    @Override
    public String toString() {
        return "ReminderList{" +
                "reminderListID=" + reminderListID +
                ", name='" + name + '\'' +
                ", list=" + list +
                '}';
    }
}
