package edu.qc.seclass.glm;
import java.time.LocalDate;
import java.util.StringTokenizer;

public class DateAlert extends Alert{
    private LocalDate alertDate;
    public int alertID;
    public Reminder reminder;

    public DateAlert(int day, int month, int year, Reminder remind) {
        this.alertDate = LocalDate.of(year, month, day);
        this.alertID = super.currentId;
        super.currentId += 1;
        this.reminder = remind;
    }

    public DateAlert(int day, int month, int year, Reminder remind, int id){
        this.alertDate = LocalDate.of(year, month, day);
        this.alertID = id;
        this.reminder = remind;
    }

    public DateAlert(String fromDB, int rid, int aid){
        StringTokenizer st = new StringTokenizer(fromDB, " ");
        int d = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        this.alertDate = LocalDate.of(y, m, d);
        this.alertID = aid;
    }

    public void setDate(int day, int month, int year){
        this.alertDate = LocalDate.of(year, month, day);
    }

    public String toString(){
        return alertDate.getMonthValue() + " " + this.alertDate.getDayOfMonth() + " " + alertDate.getYear();
    }

    public String getDate(){
        return this.toString();
    }

    public int getReminderID(){
        return this.reminder.getReminderID();
    }

    public int getID(){
        return this.alertID;
    }

    @Override
    public void alertListening() {
        //TODO
    }

    @Override
    public void alertAnnounce() {
        //TODO
    }
}