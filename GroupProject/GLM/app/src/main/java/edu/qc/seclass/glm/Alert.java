package edu.qc.seclass.glm;

public abstract class Alert {
    static protected int currentId = 1;
    public abstract void alertListening();
    public abstract void alertAnnounce();
    public abstract int getReminderID();
    public abstract int getID();
    public abstract String toString();

    static public void setCurrentId(int id) {
        try {
            if (id < 0) {
                throw new Exception("id error");
            }
            currentId = id;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    static public int getCurrentID(){return currentId;}
}
