# Use Case Model

**Author**: Team 3

## 1 Use Case Diagram

![Use Case Diagram](https://github.com/qc-se-fall2018/370Fall18Team3/blob/master/GroupProject/Docs/Use%20Case%20Diagram.png)

## 2 Use Case Descriptions

Add a Reminder: This case must allow a user to attach a reminder to a given list to keep track of it, and possibly send an alert at a prescribed time. The precondition is that there must be a Reminder List already created to be able to add the reminder to. The post condition is to save the reminder and properly display it in the correct list and type. The way to achieve this would be to choose a ReminderList from the main screen, hit the ‘Add’ button and give all the required info, then hit save. 

Create a New Type: This case must allow a user to create a new Type of a reminder while adding a reminder. The precondition is that Type does not already exist and the user is in the midst of adding a reminder. The post condition is to save the type and, going forward, give the user the option to choose this type when creating a new reminder. The way to achieve this would be to, while adding a Reminder, type in a type of a reminder that doesn’t already exist, and confirm that you want to add the Reminder Type. 

Create a New SubType: This case must allow a user to create a new SubType of a Type while adding a reminder. The precondition is that SubType does not already exist and the user is in the midst of adding a reminder. The post condition is to save the subtype and, going forward, give the user the option to choose this subtype when creating a new reminder of that type. The way to achieve this would be to, while adding a Reminder, type in a subtype of that type that doesn’t already exist, and confirm that you want to add the Reminder SubType. 

Create an Alert: This case must allow a user to add an alert to a reminder in order to alert them at a certain time. The precondition is that the user is in the midst of creating/or already created a Reminder. The post condition is to save the alert to the correct reminder and send the alert the correct time. The way to achieve this would be to activate the reminder by hitting the ‘Alert’ switch from the edit reminder screen, specify when the alert is wanted, and hit save.
Add a List: This case must allow a user to create a new reminder list to be able to add reminders to. The precondition is that the user is on the main screen. The post condition is to save the list and take the user to that list’s main screen. The way to achieve this would be to choose hit the ‘Add’ button on the main screen and type the name of the reminder, then hit save. 

Rename List: This case must allow a user to rename an already existing reminder list. The precondition is that there are already created lists. The post condition is to save the list with the new name. The way to achieve this would be to either hit the ‘Edit’ button on the main screen for a specific reminder list and type the name of the reminder, or on the screen for that reminder list, tap the header where the name is displayed and type the name there.

Delete List: This case must allow a user to delete an already existing reminder list. The precondition is that there are already created lists. The post condition is to delete that list and all reminders present in it. The way to achieve this would be to choose hit the ‘Delete’ button on the main screen for a specific reminder list and confirm the choice.
