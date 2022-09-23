1.	A list consisting of reminders the users want to be aware of. The application must allow
users to add reminders to a list, delete reminders from a list, and edit the reminders in
the list.

The List class takes care of this functionality by storing an array/list of Reminders. The List class has functionality for adding, deleting, and editing reminders with the addReminder, deleteReminder, and editReminder functions respectively.  

2.	The application must contain a database (DB) of reminders and corresponding data.

This is represented by the database class. The storeReminder(Reminder) function will take care of storing the data. 

3.	Users must be able to add reminders to a list by picking them from a hierarchical list,
where the first level is the reminder type (e.g., Appointment), and the second level is the name of the actual reminder (e.g., Dentist Appointment).

The functionality for this will come from the User Interface class’s createReminder() function. It will call on static data from the Reminder class for the type, and actual name. Specifically referencing typeList and subTypeList.  
	
4.	Users must also be able to specify a reminder by typing its name. In this case, the
application must look in its DB for reminders with similar names and ask the user
whether that is the item they intended to add. If a match (or nearby match) cannot be
found, the application must ask the user to select a reminder type for the reminder, or
add a new one, and then save the new reminder, together with its type, in the DB.

Support for this is built into the User class, which will query the database with the name provided for the reminder using the “searchReminder(String)” function. From here the searchReminder(String) function will either return a Reminder or create a new reminder by calling createReminder from User Interface, which will call newReminder(List) after a list and name are specified. If a new reminder type is needed the function addType(String) from the Reminder class will be called. All saving will be built into all functions as it must always be immediate. 

5.	The reminders must be saved automatically and immediately after they are modified.

This functionality is realized in the save function built into the reminders, which will in turn call the save function on the database. This will be called whenever a reminder is modified in any way. 

6.	Users must be able to check off reminders in the list (without deleting them).

Support for this is built into the User class with the “checkOffReminder(Reminder)” function, which will itself store the reminders check off status in the Reminder’s Completion attribute. 

7.	Users must also be able to clear all the check-off marks in the reminder list at once.

Support for this is built into the User class with the function “clearAllChecks().”

8.	Check-off marks for the reminder list are persistent and must also be saved immediately.

Support for this is built into the User class in the function checkOffReminder(Reminder), which will have the reminder in question call it’s save function immediately upon processing. 

9.	The application must present the reminders grouped by type.
Support for this is built into the User Interface class, specifically the function “displayRemindersByType()”  

10.	The application must support multiple reminder lists at a time (e.g., “Weekly”, “Monthly”, “Kid’s Reminders”). Therefore, the application must provide the users with the ability to create, (re)name, select, and delete reminder lists.

The support for list manipulation and the storing of its data is implemented in the User class. The support for displaying that information is within the User Interface class

11.	The application should have the option to set up reminders with day and time alert. If this option is selected allow option to repeat the behavior.

The User class contains the attribute which tracks whether the user is using alerts, and contains a function for changing the value of the attribute. 

12.	Extra Credit: Option to set up reminder based on location.

The alert class was extended to deal with setting listeners/announcers for geo-locational data.

13.	The User Interface (UI) must be intuitive and responsive.

This was not considered as this requirement does not affect the design process
