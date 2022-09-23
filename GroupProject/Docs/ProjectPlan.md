# Project Plan 

**Author**: \<**Team 3**\> 

## 1 Introduction 

*The goal of the project is to design a reminder android application which is a time management software program. The application will alert the user of important events such as meetings, appointments, and exams. The application provides the user the ability to create a checklist of reminders and memo's with the option of setting time or location specific alerts.*

## 2 Process Description

* **Login**
  * Description: This activity marks the first interaction with the user and takes in login/signup info. It is a relatively simple UI entity made only of two text fields such as Email and Password and one signing button which allow user to access next page. This screen only appears once. After providing information it will not appear anymore.
  * Entrance criteria: Opening the app (for the first time or after logging off) or logging off from a session.
  * Exit criteria: Entering the right credentials or sign up data or closing the app.
* ** List of Reminders**
    * Description: This is the main activity of the app. It displays a list of reminders if reminder exist. It has two button that provide the user to add reminder and another button to access profile configuration.If there is any reminder exist, user can modify that reminder by clicking on the specific reminder name.

    * Entrance criteria: Opening the app (after login info has been saved) or pressing the "back" button on another activity.
    * Exit criteria: Clicking a button to enter another activity or closing the app.

* **Profile Configuration**
  * Description: This activity is responsible for configuring a users profiles. It helps user to change their informations such that, last name, first name, email, password. Beside that, user get opportunity to activate or deactivate alert. After changing any informations user can save the new information or can log out from the app. 

  * Entrance Criteria: The user selects the edit profile option from the Main Display activity.
  * Exit Criteria: The user saves the profile, or discards the changes.

* **Create Reminder**
  * Description: Displayed to the user when they want to create a new reminder after clicking the add button from the list of the reminder screen. This page allows user to set a name for the reminder. After user added the reminder name that reminder will appear in the List of Reminder page.
  * Entrance criteria: Clicking a "+" button from Reminders List Activity.
  * Exit criteria: Canceling the reminder creation process or clicking a "BACK" button while having provided necessary data for a new reminder.

* ** Add Reminder**
  * This page will pop up after clicking specific reminder from the List of Reminder. Here use can see four types of reminder such as, Doctor, School, Appointment and Exams. User can select the type of reminder and also can add name, descriptions and set priority of the reminder. Beside that, user also has access to set date and time for the reminder. After finishing everything user can save the data by clicking on the ADD button. After setting everything user will be notified from the app. To clarify user will not able to enter same name twice for the reminder.
* Entrance criteria: At least one reminder has to be in the List of Reminder.
  * Exit criteria: Canceling the Add reminder creation process by clicking on the "BACK" button while having provided necessary data for a new reminder.

* **Alert**
  * Description: This activity is responsible for displaying a message to the user regarding a reminder. It will display the name of the reminder that prompted the alert, as well as the option to dismiss the Alert. The alert may trigger a sound file if needed.
  * Entrance Criteria: Either the date, time, or location for one of the reminders currently active is in alert range.
  * Exit Criteria: The user chooses to dismiss the Alert.


* 
## 3 Team 

- *Team members' names* 

  | Names:            |
  | ----------------- |
  | Michael Grossman  |
  | Kenneth hasiholan |
  | Chenxian Xia      |
  | Yousra Hanif      |
  | Fatema Hanif      |
  | Willian Almonte   |

  

- Roles, with a short description of each role

  | <u>Roles</u>           | Description                                                  |
  | ---------------------- | ------------------------------------------------------------ |
  | Project Manager        | Responsible for overall team management, planning,  organizing and directing team members with specific tasks in the projects. |
  | Graphic user Interface | Designing a user friendly user interface that allows users to interact with the device through the application |
  | Debugging              | Identifying and detecting bugs in the software and maintain smoothness of the application to make sure everything functions properly |
  | Coding                 | Implementing UML design into actual code and write programs for each designs |
  | Database               | Organized collection of data or information where data can directly retreat and search through the database |
  | Testing                | Testing each functions to make sure every function works properly |

  

- Table showing which team member(s) has which role(s)

| Names:           | Roles:                |
| ---------------- | --------------------- |
| Kenneth Hasiholan | Coding, Debugging, DB, GUI |
| Michael Grossman | PM, Coding, Debugging |
| Yousra Hanif | Testing, database                      |
| Fatema Hanif | Testing, database                       |
| Willian Almonte  |Coding, Testing, Database|
| Chenxian Xia | UI, Debugging/Testing, Coding/ Database |
