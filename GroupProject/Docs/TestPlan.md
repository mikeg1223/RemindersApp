# Test Plan

*This is the template for your test plan. The parts in italics are concise explanations of what should go in the corresponding sections and should not appear in the final document.*

 **Authors**: Chenxian Xia, Willian Almonte, Michael Grossman - Tests.

## 1 Testing Strategy

 1.1 Overall strategy

 *This section should provide details about your unit-, integration-, system-, and regression-testing strategies. In particular, it should discuss which activities you will perform as part of your testing process, and who will perform such activities.*

 <img src="Resources\pyramid.png" alt="Testing_Pyramid" style="zoom:70%;" />

The general strategy for testing our android app will be based on "The Testing Pyramid" found in *Fundamentals of testing* on developer.android.com. The idea is to work our way up by first performing unit tests, then integration tests to make sure those units can interact with each other, and then Systems tests and UI tests, which put the entire app under stress. Below is a detailed description of a few different types of tests we plan to implement: 

- **Unit Tests:** These will help us make sure every single component of the app operates as intended. Simply put, the strategy here is to test every button, every gesture, every function, every event, or any other singular functional entity that we can think of. This is will be done by the android development team.
- **Integration Tests:** As the next natural stage of testing, the action plan with integration testing is to begin right after all units in our app have been tested (or at least all those needed for a solid integration test) and proven to be working properly, to see if they can interact with each other without issue. Ideally, we want as many team members as possible to help with these tests, not only the developers. By doing this, we can expose blind spots and provide feedback to each other on whether distinct components interact well together and if additional features should be added.
- **System Tests:** The strategy here is to ideally get multiple members of the group both inside and outside of the android development team to test the app end-to-end, as if it was a production-level app, this testing stage allows us to test finished versions of the app. We will physically interreact with our android devices and possibly create automatized tests to really make sure all features of the software system work as intended. We will test everything from signing up, logging in, creating/editing/deleting reminders,  creating/editing/deleting reminder lists, editing the user's profile, alerts, among other segments of the app. We will also try to test the app across various different android devices.
- **Acceptance Tests (Optional):** Acceptance tests are very useful because they provide feedback from stakeholders outside the development team. Here, we can ask family and friends, teaching assistants, and professors to tests our app and give us outside feedback and points of improvements.
- **Regression Tests:** It is important, after improvements or corrections to any code base, to check that all previously-functioning features are still working properly and that they integrate well with new functionality. Our plan here is to rerun old unit and integration tests with every significant change that is done to the app, as well as running newly implemented tests.

### 1.2 Test Selection

 *Here you should discuss how you are going to select your test cases, that is, which black-box and/or white-box techniques you will use. If you plan to use different techniques at different testing levels (e.g., unit and system), you should clarify that.*

The test case selection process will be heavily based in the project requirements. We will begin by thinking about the required behavior of individual segments of code and then test whether those software components respond as intended given any input. After, we will test the integration of individual units as well as end-to-end functionality, as described by the testing pyramid in the above section.

Because they require knowledge about the internal structure of the app, all of the lower levels tests like Unit Tests and Integration Tests will be implemented with an "expected vs. actual output" scheme using primarily **white-box testing techniques** such as 

- Conditional testing (the classic if A then B, else C tests)
- Loop testing
- Code coverage analysis (which helps us make sure all parts of the code are tested).

Systems and Regression Tests will be done using **black-box testing techniques** such as 

- Functional Testing (where testers consider input and output values based on the project requirement)
- Non-functional testing (concerned with performance, usability, and other non-functional requirements as opposed to core functionality)

### 1.3 Adequacy Criterion

*Define how you are going to assess the quality of your test cases. Typically, this involves some form of functional or structural coverage. If you plan to use different techniques at different testing levels (e.g., unit and system), you should clarify that.*

​	<u>**Brute force Method:**</u>

* Most common technique of debugging, where in this approach, every statement the program is loaded with print statements. This case, we can test each component one at a time, so in case if there`s an error, the program can detect the function where the error is caused and fix it.

<u>**Backtracking**</u>	

* This approach starts from where the error occurs and from there, the source code is derived backward till the error is discovered. 

<u>**System Testing**</u> :  Path Coverage 

* Using counter variable to count the number of passes from the input to the output. Each function should execute at least one time and passes the output of counts

<u>**Unit Testing**</u>

* Test each statements and functions manually one by one in case of any errors 



### 1.4 Bug Tracking

*Describe how bugs and enhancement requests will be tracked.*

 <u>**Unit Testing**</u>

* Test each individual units, functions to see if there`s any errors, if there is we can detect the issue in the function and fix the problem

<u>**System Testing**</u>

* When unit testing and system testing both passed, if any sort of errors occurs during running process, then we can detect which parts of the function occurs, and we would know which part should be fixed or modified. 

<u>**Integrated Testing**</u>

* Application must passed all the unit testing, and it did not pass the integrated testing, then we know there`s error that occurs

### 1.5 Technology

*Describe any testing technology you intend to use or build (e.g., JUnit, Selenium).*

* We'll be using (Junit) to built our own test cases to manually test each functions and report each results in the "Test Case" diagram.  

## 2 Test Cases

 *This section should be the core of this document. You should provide a table of test cases, one per row. For each test case, the table should provide its purpose, the steps necessary to perform the test, the expected result, the actual result (to be filled later), pass/fail information (to be filled later), and any additional information you think is relevant.*

*Abbreviations:*

- *TU: Unit test*

- *TI: Integration test*

| Test Case ID | Purpose                                                      | Steps                                                        | Expected Result                                              | Actual Result                                                | Pass/Fail |
| ------------ | :----------------------------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | --------- |
| TU01         | Check response when valid email and password is entered      | 1. Open app<br />2. Enter email<br />3. Enter password<br />4.Press Login button | Login should be successful                                   | success                                                      | Pass      |
| TU02         | Check response when invalid email and password is entered    | 1. Open app<br />2. Enter email<br />3. Enter password<br />4.Press Login button | Login should not be successful and user should be notified why | not successful                                               | pass      |
| TU03         | Check response when email and password fields are empty      | 1. Open app<br />2. Press Login button                       | Login should not be successful and user should be notified why | not successful                                               | pass      |
| TU04         | Check response when user creates a reminder                  | 1.Press "create reminder" button<br />2.Enter reminder data<br />3.Press done button | A new reminder should be created; user should be notified    | created, listed, notified                                    | pass      |
| TU05         | Check response when user creates a duplicate reminder        | 1.Press "create reminder" button<br />2.Enter reminder data<br />3.Press done button | No reminder should be created, user should be notified why   | not created, but listed. Does not persist on next load       | fail      |
| TU06         | Check response when an alert is due                          | 1. Press "create reminder" button<br />2. Create dummy reminder with alert in 10secs | An alert should be issued 10secs after pressing create button |                                                              |           |
| TU07         | Check response when an alert is set for a moment in the past | 1. Press "create reminder" button<br />2. Create dummy reminder with alert for 10secs ago | The creation of the reminder should be nullified; the user should be notified why |                                                              |           |
| TU08         | Check response when user creates a reminder list             | 1.Press "create list" button<br />2.Enter reminder list data<br />3.Press done button | A new reminder list should be created; user should be notified | list was listed, user notified                               | pass      |
| TU09         | Check response when user creates a duplicate reminder list   | 1.Press "create reminder list" button<br />2.Enter reminder list data<br />3.Press done button | Reminder list should not be created, user should be notified why | is case sensitive, passes if exactly the same                | fail      |
| TU10         | Check response when user selects all reminders in a list     | 1. Open app<br />2. Press "select all"  button               | All reminders in the active list should be selected          | all selected properly, but backing out and in again removes them? | fail      |
| TU11         | Check response when user deletes all reminders in a list     | 1. Open app<br />2. Press "select all"  button<br /> 3. Press delete button | The active list should be empty                              | clear button works                                           | pass      |
| TU12         | Check response when user checks all reminders in a list      | 1. Open app<br />2. Press "select all"  button<br /> 3. Press check-off button | All reminders in the active list should be 'completed'       |                                                              |           |

