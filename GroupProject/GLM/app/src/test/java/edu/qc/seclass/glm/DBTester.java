package edu.qc.seclass.glm;

import org.junit.Test;

import static org.junit.Assert.*;

import android.content.Context;

public class DBTester{
    @Test
    public void DBTesterMethod() {
        DBHelper DB = new DBHelper(null);

        User user = new User("email@email.com", "123", true);
        DB.insertUser(user);

        ReminderList reminderList = new ReminderList("ReminderList1");
        DB.insertReminderList(reminderList, user);

        Reminder reminder= new Reminder("reminder1", new ReminderType("Appointment"), false, 3, false, "");
        DB.insertReminder(reminder,reminderList, user);

        System.out.println(DB.getAllReminders().toString());
        assertEquals(4, 2 + 2);
    }
}