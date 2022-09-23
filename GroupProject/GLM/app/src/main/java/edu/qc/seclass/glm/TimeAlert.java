package edu.qc.seclass.glm;

import android.content.Context;

import java.util.Calendar;

import java.time.LocalTime;
import java.util.StringTokenizer;

public class TimeAlert extends Alert{
    private LocalTime timeAlert;
    public Reminder reminder;
    public int alertID;

    public TimeAlert(int hours, int minutes, Reminder remind) {
        this.timeAlert = LocalTime.of(hours, minutes);
        this.alertID = super.currentId;
        super.currentId += 1;
        reminder = remind;
    }

    public TimeAlert(int hours, int minutes, Reminder remind, int id){
        this.timeAlert = LocalTime.of(hours, minutes);
        this.alertID = id;
        reminder = remind;
    }

    public TimeAlert(String fromDB, int rid, int aid){
        StringTokenizer st = new StringTokenizer(fromDB, " ");
        int h = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        this.timeAlert = LocalTime.of(h, m);
        this.alertID = aid;
    }

    public String toString(){
        return Integer.toString(this.timeAlert.getHour()) +" "+ Integer.toString(this.timeAlert.getMinute());
    }

    public void setTime(int hours, int minutes){
        this.timeAlert = LocalTime.of(hours, minutes);
    }

    public String getTime(){
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
