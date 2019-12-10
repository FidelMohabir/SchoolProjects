# Project Plan

**Author**: Team 3

## 1 Introduction


The goal of this project is to develop an Android Application that allows users to create a list of reminders under specific reminder lists to ensure they will not forget to complete a certain task.


## 2 Process Description

4 Screens:
Main screen (display all lists, with ability to add new lists, rename lists, and delete lists)
	Entrance Criteria: Default screen when the user opens the app. 
	Exit criteria: When the user closes the application
ReminderList Screen (For different lists, will display all the reminders of that list, grouped by type. Ability to add, edit, and delete reminders. Also check-off reminders)
	Entrance: When the user creates a new List or selects a List to view
	Exit: When the user specifies the type of reminder and the description of the reminder. 
Add Reminder Screen (Prompt user for all the necessary info to create a reminder)
	Entrance: When user wants to create a new Reminder in a list
	Exit: The user saves or cancels the request to add a Reminder
Alert screen (If user wants to add an alert to a reminder, will show a calendar, time choice, and repeat choice, or for editing)
	Entrance: When user wants to add alerts to a reminder
	Exit: Saving alerts or hitting cancel button

**Masterlist class**
getAllTypes()
-Returns name of reminder types

getAllLists()
-Returns a list of lists.

addList(name)
-Creates a list with the input string name

renameList(oldName, newName)
-replaces list by the name of input string oldName, with the input string newName.

deleteList(name)
-deletes the list with the name of input string name.

addType(name)
-creates new remindertype

**ReminderList class**
uncheckAll()
-Unchecks all listed items in that reminder list

addReminderTypetoList(ReminderType)
-Adds all reminders in an associated reminder type to list

search(name)
-search for a particular reminder in the list


**ReminderType class**
getAllSubTypes()
-Returns a list of subtypes

addSubType(name)
-Create a subtype with input string name

AddReminder(Reminder)
-Put a specific reminder in the reminder type

EditReminder(ReminderID, Reminder)
-Edit specific Reminder

deleteReminder(Reminder)
-Delete reminder

**SubType class**
addSubType(name)
-Create subtype of input string name

**Reminder class**
toggleCheck()
-Checks the specific Remidner

AddAlert()
-Adds an alert to reminder

**Alert class**
toggleAlert()
-Toggle alert on or off

editAlert(Alert)
-Edit specific alert

getAlertStatus()
-Check status of alert


## 3 Team

| Name             | Role                      | Description                                                                                                                     |
|------------------|---------------------------|---------------------------------------------------------------------------------------------------------------------------------|
| Steven Zaslowsky | Database Manager          | Will create and manage the database to cater the needs of the app                                                               |
| Faraaz Motiwala  | Testing and Design        | Will create test cases to ensure app works as expected. Designs User Interfaces and layouts.                                    |
| Rifat Hossain    | Backend Developer         | Build the software to perform necessary functionality                                                                           |
| Jin Cheng        | Testing and Documentation | Will perform tests to ensure correct functionality. Updates documents to conform to changes made during the development process |
| Fidel Mohabir    | Backend Developer         | Build the software to perform necessary functionality                                                                           |