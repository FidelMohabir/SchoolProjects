# Test Plan

**Author**: Team 3

## 1 Testing Strategy

### 1.1 Overall strategy

Agile approach will be used, each week the required activity will be delivered then to be tested.
This project has database and will be cloud base, therefore it will be used by multiple user at the same time.

### 1.2 Test Selection

Back-end and database functionality will use white-box for testing.
Front-end GUI will use black-box for testing.

### 1.3 Adequacy Criterion

Unit test will be used for every test case.

### 1.4 Bug Tracking

Use unit test to cover every activity so that can track down the bugs. We will be using a shared spreadsheet so each group member can both alert everyone else to bugs and to keep track of which ones have been fixed in a timely manner.

### 1.5 Technology

We will be using JUnit to test as the group is used to working with it.

## 2 Test Cases 
 *Note: Our test cases will include additional test in the beta/alpha of our app.

MasterList:
 
| Purpose                                                       | Steps                                                        | Expect                   | Result | Pass/fail information |
|---------------------------------------------------------------|--------------------------------------------------------------|--------------------------|--------|-----------------------|
| It should able to add a list                                  | 1. user login  2. create list1 and list2                     | getAllLists().size == 2  |   Test Completed     |           Pass            |
| It should able to delete a list                               | 1. user login  2. create list1 and list2  3. delete list1    | getAllLists() == [list2] |   Test Completed    |           Pass           |
| It should list no reminder list when there’s no reminder list | 1. user login                                                | getAllLists().size == 0  |   Test Completed     |           Pass         |
| It should able to change the name of a list                   | 1. user login  2. create list1  3.change list1 name to list2 | getAllLists() == [list2] |   Test Completed    |           Pass          |
 
 
ReminderList:
 
| Purpose                                                                    | Steps                                                                                                                                                         | Expect                                                                        | Result | Pass/fail |
|----------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------|--------|-----------|
| It should able to add a reminder                                           | 1. user login  2. create list1  3. create a reminder for list1 named reminder1  3. create a reminder for list1 named reminder2                                | List1.reminders.size == 2                                                     |    Test Completed    |      Pass     |
| It should able to delete a reminder                                        | 1. user login  2. create list1  3. create a reminder for list1 named reminder1  3. create a reminder for list1 named reminder2  4. delete reminder1           | List1.reminders.size == 1 && List1.reminders == [reminder2]                   |    Test Completed    |    Pass       |
| It should list no reminder when there’s no reminder                        | 1. user login  2. create list1                                                                                                                                | List1.reminders.size == 0 && List1.reminders == []                            |    Test Completed    |     Pass      |
| It should able to change the name of a list                                | 1. user login  2. create list1  3. create a reminder for list1 named reminder1  3. change reminer1 to reminder2                                               | List1.reminders[0].reminderName == reminder2                                  |    Test Completed    |     Pass      |
| It should able to uncheck all the reminders when all are already unchecked | 1. user login  2. create list1  3. create 2 reminder for list1 named reminder1 and reminder2  4. uncheckAll()                                                 | List1.reminders[0] .isChecked == false  List1.reminders[1].isChecked == false |   Test Completed     |    Pass       |
| It should able to uncheck all the reminders when all are checked           | 1. user login  2. create list1  3. create 2 reminder for list1 named reminder1 and reminder2  4.set reminder1 and reminder2 isChecked = true  4. uncheckAll() | List1.reminders[0] .isChecked == false  List1.reminders[1].isChecked == false |    Test Completed    |     Pass      |
| It should able to group the reminder by reminder list                      | 1. user login  2. create list1 and list2  3. create 2 reminder for list1 named reminder1 and reminder2  3. create 1 reminder for list2 named reminder3        | List1.reminders.size == 2  List2.reminders.size == 1                          |    Test Completed    |      Pass     |
 
 
Reminder
 
| Purpose                                        | Steps                                                                                                                  | Expect                      | Result | Pass/fail information |
|------------------------------------------------|------------------------------------------------------------------------------------------------------------------------|-----------------------------|--------|-----------------------|
| It should able to toggle check  When checked   | 1. user login  2. create list1  3. create reminder1 for list1  4.reminder1.isChecked = true  5.reminder1.toggleCheck() | Remider1.isChecked == false |    Test Completed    |           Pass            |
| It should able to toggle check  When unchecked | 1. user login  2. create list1  3. create reminder1 for list1  4.reminder1.toggleCheck()                               | Remider1.isChecked == true  |   Test Completed     |           Pass            |
| It should able to add a alert                  | 1. user login  2. create list1  3. create reminder1 for list1  4.reminder1.addAlert()                                  | Reminder1.alerts.size == 1  |    Test Completed    |           Pass            |
| It should able to add multiple alert           | 1. user login  2. create list1  3. create reminder1 for list1  4.reminder1.addAlert()  4.reminder1.addAlert()          | Reminder1.alerts.size == 2  |     Test Completed   |           Pass            |
 
 
Alert:


| Purpose                                             | Steps                                                                                                                                                              | Expect                                      | Result | Pass/fail information |
|-----------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------|--------|-----------------------|
| It should able to disable a alert when it is active | 1. user login  2. create list1  3. create reminder1 for list1  4.reminder1.addAlert()  5.reminder1.alerts[0].isAcitve == ture  6.reminder1.alerts[0].toggleAlert() | reminder1.alerts[0].isAcitve == false       |   Test Completed     |        Pass               |
| It should able to set the date time of a alert      | 1. user login  2. create list1  3. create reminder1 for list1  4.reminder1.addAlert()  5.reminder1.alerts[0].editAlert(dateTime, 02-08-2019 19:00)                 | reminder1.alerts[0].day == 02-08-2019 19:00 |    Test Completed    |            Pass           |
| It should able to set to repeat weekly              | 1. user login  2. create list1  3. create reminder1 for list1  4.reminder1.addAlert()  5.reminder1.alerts[0].editAlert(repeatWeekly, true)                         | reminder1.alerts[0].repeatWeekly == true    |    Test Completed    |           Pass            |
| It should able to set NOT repeat weekly             | 1. user login  2. create list1  3. create reminder1 for list1  4.reminder1.addAlert()  5.reminder1.alerts[0].editAlert(repeatWeekly, false)                        | reminder1.alerts[0].repeatWeekly == false   |   Test Completed     |            Pass           |
| It should able to set to repeat monthly             | 1. user login  2. create list1  3. create reminder1 for list1  4.reminder1.addAlert()  5.reminder1.alerts[0].editAlert(repeatMonthly, true)                        | reminder1.alerts[0].repeatMonthly== true    |    Test Completed    |            Pass           |
| It should able to set NOT repeat monthly            | 1. user login  2. create list1  3. create reminder1 for list1  4.reminder1.addAlert()  5.reminder1.alerts[0].editAlert(repeatMonthly, false)                       | reminder1.alerts[0].repeatMonthly == false  |    Test Completed    |           Pass            |
| It should able to set to repeat yearly              | 1. user login  2. create list1  3. create reminder1 for list1  4.reminder1.addAlert()  5.reminder1.alerts[0].editAlert(repeatYearly, true)                         | reminder1.alerts[0].repeatYearly== true     |    Test Completed    |          Pass             |
| It should able to set NOT repeat yearly             | 1. user login  2. create list1  3. create reminder1 for list1  4.reminder1.addAlert()  5.reminder1.alerts[0].editAlert(repeatYearly, false)                        | reminder1.alerts[0].repeatYearly == false   |    Test Completed    |          Pass             |

