1. A list consisting of reminders the users want to be aware of. The application must allow
users to add reminders to a list, delete reminders from a list, and edit the reminders in
the list.

- implementing by Creating a class ReminderList with the requirement method 

2. The application must contain a database (DB) of reminders and corresponding data.

- created a class for database reminderlist with some corresponding data

3. Users must be able to add reminders to a list by picking them from a hierarchical list,
where the first level is the reminder type (e.g., Appointment), and the second level is the
name of the actual reminder (e.g., Dentist Appointment).

- users can input the type of reminder by the first level is determined by the ReminderType class 
and the second level is determined by the Reminder class.

4. Users must also be able to specify a reminder by typing its name. In this case, the
application must look in its DB for reminders with similar names and ask the user
whether that is the item they intended to add. If a match (or nearby match) cannot be
found, the application must ask the user to select a reminder type for the reminder, or
add a new one, and then save the new reminder, together with its type, in the DB.

- user can use the search method to find the match in database and if a similar match isn’t found 
the user will be asked to create/select a reminderType and any changes made to reminderType

5. The reminders must be saved automatically and immediately after they are modified.

-method autosave is created in every singles class that have edit method

6. Users must be able to check off reminders in the list (without deleting them).

-Each Reminder class object includes a boolean attribute called isCheckedOff that
  determines whether the item is crossed off or not.

7. Users must also be able to clear all the check-off marks in the reminder list at once.

-method CLearchekedOffMarks created to accountable for locating all reminders in the database 
relating to the list by identifying every item that shas been checkoff and reset their value

8. Check-off marks for the reminder list are persistent and must also be saved immediately.

-the class reminder type and reminder list and data base has autosave 

9. The application must present the reminders grouped by type.
-Reminder objects include a reminderType attribute that is kept with the reminder,
 allowing each reminder to be grouped by its reminder type as necessary.

10. The application must support multiple reminders lists at a time (e.g., “Weekly”, “Monthly”,
“Kid’s Reminders”). Therefore, the application must provide the users with the ability to
create, (re)name, select, and delete reminder lists.

-in the reminderlist it has methods for adding and etc functionality,
 which may generate many groups that can operate at the same time.


11. The application should have the option to set up reminders with day and time alert. If this
option is selected allow option to repeat the behavior.

When a reminder is generated in the Reminder class, time and date properties are added, 
and method repeatReminder(date, time) parameters of date and time are supplied.

12. Extra Credit: Option to set up reminder based on location.

- created a loaction class with some attributes as address , coordinates, date and time

13. The User Interface (UI) must be intuitive and responsive.

-Not displayed or modeled in the UML as its a UI element
