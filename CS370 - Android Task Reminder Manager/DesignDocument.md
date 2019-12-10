# Design Document

**Author**: Team 3

## 1 Design Considerations

### 1.1 Assumptions

Ideally the reminder list should be displayed in a calendar format so we will have to figure out how to introduce one to the system. We assume that this program is being run on an android device. The application design should be simplistic so as to allow an individual user to perform a basic task without any difficulty.


### 1.2 Constraints

The system must be able to run well on lower hardware android phones. The system must be built in such a way that the user will access the database within a reasonable timeframe and while interacting with the minimum amount of activity screens. The system only saves the lists on the apps, which prevents access from different devices.


### 1.3 System Environment


The software must be able to work on any android phone with android 21 or higher. The system is programmed in xml and java entirely.  The system ideally will be working with high end phones and also low end phones.


## 2 Architectural Design

### 2.1 Component Diagram


![ComponentDiagram](https://github.com/qc-se-fall2018/370Fall18Team3/blob/master/GroupProject/Docs/ComponentDiagram.jpeg)

This component diagram shows the dependences between different components. The database is used to save any data even after the application is no longer running. The user has the ability to create a list which is dependent on reminders.


### 2.2 Deployment Diagram

All components (ReminderDatabase, Reminder, Alert, ReminderType, ReminderList, MasterList) will be deployed on an android device.



## 3 Low-Level Design

### 3.1 Class Diagram

![Class Diagram](https://github.com/qc-se-fall2018/370Fall18Team3/blob/master/GroupProject/Design-Team/group-design.pdf)

### 3.2 Other Diagrams

## 4 User Interface Design

![UI](https://github.com/qc-se-fall2018/370Fall18Team3/blob/master/GroupProject/Docs/370_UI_MOCKUP.pdf)