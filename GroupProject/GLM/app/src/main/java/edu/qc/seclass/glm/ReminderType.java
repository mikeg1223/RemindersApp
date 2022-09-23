package edu.qc.seclass.glm;

public class ReminderType {
    private static int currentID = 1;
    private int reminderTypeID;
    private String reminderTypeName;

    public ReminderType(String name){
        this.reminderTypeName = name;
        this.reminderTypeID = ReminderType.currentID;
        ReminderType.currentID += 1;
    }

    public String getReminderTypeName() {
        return this.reminderTypeName;
    }
    public int getReminderTypeID() {
        return this.reminderTypeID;
    }
}
