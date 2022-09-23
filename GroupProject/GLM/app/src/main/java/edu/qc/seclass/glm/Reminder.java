package edu.qc.seclass.glm;

public class Reminder {
    private static int currentID = 1;
    private String name;
    private ReminderType reminderType;
    private boolean completed;
    private int priority;
    private boolean usesAlerts;
    private int reminderID;
    private String description;


    public Reminder(String name, ReminderType reminderType, boolean completed, int priority, boolean usesAlerts) {
        this.name = name;
        this.reminderType = reminderType;
        this.completed = completed;
        this.priority = priority;
        this.usesAlerts = usesAlerts;
        this.reminderID = Reminder.currentID;
        Reminder.currentID += 1;
        this.reminderID = Reminder.currentID;
    }

    public Reminder(String name, ReminderType reminderType, boolean completed, int priority, boolean usesAlerts, int id) {
        this.name = name;
        this.reminderType = reminderType;
        this.completed = completed;
        this.priority = priority;
        this.usesAlerts = usesAlerts;
        this.reminderID = id;
        this.description = description;
    }
    public Reminder(String name, ReminderType reminderType, boolean completed, int priority, boolean usesAlerts, int id, String description) {
        this.name = name;
        this.reminderType = reminderType;
        this.completed = completed;
        this.priority = priority;
        this.usesAlerts = usesAlerts;
        this.reminderID = id;
        this.description = description;
    }

    public int getReminderID() {
        return this.reminderID;
    }

    public String getName() {
        return this.name;
    }

    public String getReminderTypeName() {
        return this.reminderType.getReminderTypeName();
    }

    public ReminderType getReminderType() {
        return this.reminderType;
    }

    public int getReminderTypeID() {
        return this.reminderType.getReminderTypeID();
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public int getPriority() {
        return this.priority;
    }

    public boolean usesAlerts() {
        return this.usesAlerts;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String newType) {
        this.reminderType = new ReminderType(newType);
    }

    public void setType(ReminderType newType) {
        this.reminderType = newType;
    }

    public void edit(String name, ReminderType reminderType, boolean completed, int priority) {
        this.name = name;
        this.reminderType = reminderType;
        this.completed = completed;
        this.priority = priority;
        // send update to DB (this happens at the activity level)
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void checkOff() {
        this.completed = true;
    }
    static public int getCurrentID(){
        return currentID;
    }

    public void setCompleted(boolean input){
        completed = input;
    }
}
