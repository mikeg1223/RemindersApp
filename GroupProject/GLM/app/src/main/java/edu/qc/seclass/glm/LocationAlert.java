package edu.qc.seclass.glm;

import android.location.Location;

import java.util.StringTokenizer;

public class LocationAlert extends Alert {
    private double latitude;
    private double longitude;
    private Location loc;
    private Reminder  reminder;
    private int alertID;

    public LocationAlert(double latitude, double longitude, Reminder remind) {
        this.latitude = latitude;
        this.longitude = longitude;
        //this.loc = new Location();
        this.alertID = super.currentId;
        super.currentId += 1;
        this.reminder = remind;
    }


    public LocationAlert(double latitude, double longitude, Reminder remind, int id){

        this.latitude = latitude;
        this.longitude = longitude;
        //this.loc = new Location();
        this.alertID = id;
        this.reminder = remind;
    }

    public LocationAlert(String fromDB, int rid, int aid){
        StringTokenizer st = new StringTokenizer(fromDB, " ");
        int la = Integer.parseInt(st.nextToken());
        int lo = Integer.parseInt(st.nextToken());
        this.latitude = la;
        this.longitude = lo;
        this.alertID = aid;
    }

    public void setLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getReminderID(){
        return this.reminder.getReminderID();
    }

    public int getID(){
        return this.alertID;
    }

    public String toString(){
        return Double.toString(this.latitude) + " " + Double.toString(this.longitude);
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getLocation() {
        return String.valueOf(this.latitude)+", "+ String.valueOf(this.longitude);
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
