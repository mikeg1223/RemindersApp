## Design #1 
### Fatema Hanif

![Design # 1](UML%20Design%20Pictures/Design%20%231.PNG)

Pros:Repeats reminders based on specified date, time, weekly, monthly and yearly intervals.

Cons: Every classes are heavily depending on each other. So if one class fail whole app will fail immediately.



---

## Desgin # 2

### Author: kennethhas

![Design # 2](UML%20Design%20Pictures/Design%20%232.PNG)





Pros:
Design layout from UML to code is well thought.out
detailing the reminder optinal location class.

Cons:

RepeatsReminder and location gps should be Inhertnace 

---

## Design # 3

### By Michael Grossman (mikeg1223)

![Design # 3](UML%20Design%20Pictures/Design%20%233.PNG)



Pros:
The use of inheritence on the alerts. The flow of data from User to List, and Reminder.

Cons:
Unnecessarily complex, which may lead to inflexibility. Not modular. 

---

## Design # 4 

### QQxiaozhuzhu

![Design # 4](UML%20Design%20Pictures/Design%20%234.PNG)



Pros: Have some basic design features for Reminder_application such as user information, basic database, and reminder types.

Cons: Missing few require attributes and operations, such as dates, time, reminder`s day, no auto save featsures. 

---

## Design # 5

### By Willian Almonte

![Design #5](UML%20Design%20Pictures/Design%20%235.PNG)

Pros: Design is neat and organized. Have most of the required attributes and operations for a good Reminder application design 



Cons: No database, and reminder class`s a bit thick 



---

## Design # 6
### by: Yousra Hanif


![Design # 6](UML%20Design%20Pictures/Design%20%236.PNG)



Pros: Good use of Create, delete, edit, and update methods.



Cons: Does not have alert method. 

---

## Team Design

![Team Design](UML%20Design%20Pictures/design-team.PNG)

### Main commonalities:

The majority of commonalities between our designs lie in our classes. The majority of us decided to implement the User class as a basis of entry. Another commonality is that we all decided to represent the Reminder Lists as their own class. The majority of us also decided to implement the location alert, albeit in different ways. 

### Main Differences:

Our final design draws from the best of the individual designs. As a result the main differences we see are the unique design ideas that individuals came up with. One difference being the Alert hierarchy, which only one teammate implemented. Another difference is the Priority field, which allows the user to assign a priority rating to reminders. Most of our designs used different methods of creating reminds, and  one teammate decided to add a repeat option to their reminders. 

### Main Design Decisions:

We decided to use the inheritance model for Alerts in order to simplify the reminder classes alert attribute. This will allows the alert to be generated as the type it needs to be without needing separate options as null values. We decided to separate reminder and reminderList into separate classes to simplify the reminder class and make the overall program more modular. We decided to use the User class as the interacting class for the user interface, and we gave this class login credentials to allow for separate users on the same machine. 


## Summary: 

In our group everyone had different conceptions about the UML and how the app would work. We had regular group meetings to ensure that everyone gained a clear understanding about UML, and its functions. In our group sessions, we demonstrated our respective designs, then identified all pros and cons of our individual designs. We discussed the advantages and disadvantages of each plan. Our meetings in conjunction with the examples in class led us to our re-design. Ideas were greeted respectfully with open considerations, and reviewed by the team as a whole.
