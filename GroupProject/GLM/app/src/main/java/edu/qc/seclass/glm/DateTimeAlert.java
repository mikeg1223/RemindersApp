package edu.qc.seclass.glm;

import java.time.LocalDateTime;
import java.util.StringTokenizer;

public class DateTimeAlert extends Alert{
    private LocalDateTime alertDateTime;
    public int alertID;
    public Reminder reminder;

    public DateTimeAlert(int year, int month, int dayOfMonth, int hour, int minute, Reminder reminder) {
        this.alertDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
        this.alertID = super.currentId;
        super.currentId += 1;
        this.reminder = reminder;
    }

    public DateTimeAlert(int year, int month, int dayOfMonth, int hour, int minute, Reminder reminder, int alertID) {
        this.alertDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
        this.reminder = reminder;
        this.alertID = alertID;
    }

    public DateTimeAlert(String fromDB, Reminder reminder, int alertID){
        StringTokenizer st = new StringTokenizer(fromDB, " ");
        int y = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        int min = Integer.parseInt(st.nextToken());

        this.alertDateTime = LocalDateTime.of(y, m, d, h, min);
        this.reminder = reminder;
        this.alertID = alertID;
    }

    @Override
    public int getReminderID() { return this.reminder.getReminderID(); }

    @Override
    public int getID() {
        return this.alertID;
    }

    public String getDateTime(){
        return this.toString();
    }

    public LocalDateTime getDateTimeAlert(){return this.alertDateTime;}

    public String getFormattedDate(){
        return alertDateTime.getYear() + "-" + alertDateTime.getMonthValue() + "-" + alertDateTime.getDayOfMonth();
    }
    public String getFormattedTime(){
        return alertDateTime.getHour() + (alertDateTime.getMinute()>9 ? ":" : ":0") + alertDateTime.getMinute();
    }

    public void setDateTime(int year, int month, int dayOfMonth, int hour, int minute){
        this.alertDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
    }

    @Override
    public void alertListening() {
        // TODO
    }

    @Override
    public void alertAnnounce() {
        // TODO
    }

    public String toString() {
        String val = alertDateTime.getYear() + " " + alertDateTime.getMonthValue() + " " +
                alertDateTime.getDayOfMonth() + " " + alertDateTime.getHour() + " " +
                alertDateTime.getMinute();

        return val;
    }
}
