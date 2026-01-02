# Student Records Management System (Java CLI)

A command line application built in Java that demonstrates object-oriented design, clean separation of responsibilities, and file-based persistence using CSV.

## Features
- Add students (ID, name, GPA)
- List all students
- Search by ID or name
- Filter by GPA range
- Sort by GPA (descending) or name (ascending)
- Update student GPA
- Delete student by ID
- Save and load records to/from CSV
- Auto-load on startup and auto-save on exit

## Project Structure
student-records-system/
  data/
    students.csv
  src/
    Main.java
    model/
      Student.java
    service/
      RecordManager.java
    storage/
      FileStorage.java

## Requirements
- Java 17 or newer

## How to Run (Windows PowerShell)

Compile:
javac -d out src\Main.java src\model\Student.java src\service\RecordManager.java src\storage\FileStorage.java

Run:
java -cp out Main

## Notes
- GPA is validated between 0.0 and 12.0
- CSV data is stored in data\students.csv
- Invalid CSV rows are skipped during load
