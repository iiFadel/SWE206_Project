# SWE206 Lab Project: Phase Three

## Overview

This project is part of the SWE206 Lab and involves building a desktop application using Java. The application is designed to manage competitions, send congratulatory emails to winners, and provide a user-friendly interface for handling competition data stored in an Excel file.

### Group Members

- **Fadel Hassan Abbas** - UI Design and Development
- **Mohammed Alwaleed Abushwarib** - Database Design and Development
- **Haydar Ali Albayibi** - Email Functionality, Database Development Assistance
- **Jehad Ibrahem Alrehaily** - Competition Website View, Additional UI Development

## Non-Functional Requirements

1. The application shall be developed in Java.
2. The application shall use an Excel file to store and track competition data.
3. The system shall be efficient.
4. The user interface shall be a desktop application.
5. The user interface shall be easy to use.
6. The system shall be interoperable with an email application to prepare messages.

## Use Case Descriptions

### 1. Send Congratulations to Competition Winners

- **Actors:** News Team Member, Email Application
- **Description:** Allows the news team to send congratulatory emails to students who placed in the top three of a competition.
- **Trigger:** User selects "Send Congratulation Email" in competition details.
- **Preconditions:** Student emails must be in the database, competition deadline passed, and a template is stored.
- **Postconditions:** Emails are prepared and ready to be sent.

### 2. View All Competitions

- **Actors:** News Team Member, Database
- **Description:** Displays a summary of all competitions stored in the database.
- **Trigger:** Launching the app.
- **Preconditions:** A database with competition data exists.

### 3. View Competition Details

- **Actors:** News Team Member, Database
- **Description:** Displays detailed information for a selected competition.
- **Trigger:** User selects a competition.
- **Preconditions:** The Excel file has stored competition data.

### 4. Access Competition Website

- **Actors:** News Team Member, Database
- **Description:** Provides the ability to visit the competition's official website.
- **Trigger:** User selects "Visit website."
- **Preconditions:** The competition must have a website.

### 5. Add New Competition

- **Actors:** News Team Member, Database
- **Description:** Allows the news team member to add a new competition to the database.
- **Trigger:** User selects "Add new competition."
- **Preconditions:** The competition event time is scheduled.

### 6. Add Team/Student to Competition

- **Actors:** News Team Member, Database
- **Description:** Assigns a student or team to a competition.
- **Trigger:** User selects "Add competitors."
- **Preconditions:** The competition’s due date is known.

### 7. Send Update Competition Reminder

- **Actors:** News Team Member, Database
- **Description:** Sends a reminder to update competition winners.
- **Trigger:** Opening the application.
- **Preconditions:** The competition has a due date, and winners exist.

## Diagrams

The report includes various diagrams such as Use Case Diagrams, Class Diagrams, Activity Diagrams, and Sequence Diagrams that illustrate the application's functionality and flow. These diagrams can be found in the project documentation.

## Running the Application

### Prerequisites

- **Java Development Kit (JDK)**
- **Maven** - Ensure Maven is installed on your IDE. [Installation Guide](https://maven.apache.org/install.html)
- **JavaFX SDK** - Required for running the application.

### Instructions

1. **Import Project**
   - Open Eclipse and click on `File -> Import`.
   - Select `Projects from Folder or Archive -> Directory`, choose the project, and click `Finish`.

2. **Maven Update**
   - Right-click on the project, navigate to `Maven -> Update Project`, and wait for the dependencies to download.

3. **Run Application**
   - Open `MainLauncher` in the `Application` package and run it.

### Additional Setup for Mac and Windows

- In the run configuration, add the following VM arguments under `Arguments`:

  ```sh
  --module-path /Path/To/javafx-sdk-16/lib --add-modules=javafx.swing,javafx.graphics,javafx.fxml,javafx.media,javafx.web --add-reads javafx.graphics=ALL-UNNAMED --add-opens javafx.controls/com.sun.javafx.charts=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.iio=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.iio.common=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.css=ALL-UNNAMED --add-opens javafx.base/com.sun.javafx.runtime=ALL-UNNAMED
 
 ### Excel File Requirement
   - Place an Excel file named Competitions Participations.xlsx in the project directory. If using a different file, ensure it has the same cell structure.

### Notes
   - The application reads the Excel data upon start, and changes are shown in real-time. However, these changes will only be saved to the Excel file upon closing the program.
