I have six classes.

User Class:
- It has user id and user password. Those are private value so not everyone can access those. 
- It allows to add users using addUser() method , edit users by editUser() method and remover users throug removerUser()method.


ReminderList:
- Reminder list has name and Id.
- It allows to create, delete, rename, select list using createList(), deleteList(), renameList(), selectList() method respectively.
- Also it allows add reminder in the list, delete reminder, edit reminder, search, save, clear reminder using createReminder(), deleteReminder(), editReminder(), searchReminder(), saveReminder(), clearReminder method respectively. 
- Moreover, it can set time for reminder using reminderTime() method. 
- This class have relation (composition) with Checking Class


Checking class:
- The main work it does helping reminder list.
- It allows to add check mark to the reminder in reminderlist using addCheck() method and also uncheck reminder in that list using checkOffMark() method. 


Reminder:
- Reminderlist has a relation (composition) with reminder.
- Reminder has a name.
- Also some method for setReminderName() by this method we can set reminder name, findReminder() method is used for finding a reminder, assignReminderId for assigning reminder and setLocation()method. 
- To access reminder based on location it has Google Maps locations.


ReminderType:
- ReminderType and Reminder has a compiste relation. 
- It also has type and an id.
- It allows to addReminder() method to add reminder here.


ReminderFrequency:
- ReminderFrequency has composite relation with Reminder.
- It gives end date of the reminder by usng endDate() method. 
- Also, tell us what is the frequency of that reminder by using yearly(), monthly(), weekly() and daily() method.







