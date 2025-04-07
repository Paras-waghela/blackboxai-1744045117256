
Built by https://www.blackbox.ai

---

```markdown
# Hospital File Management System

## Project Overview
The Hospital File Management System is a Java-based application that utilizes Remote Method Invocation (RMI) to manage patient files in a hospital setting. Users can create, view, and download patient records through a user-friendly graphical interface. This application aims to streamline the handling of patient information in a secure and efficient manner.

## Installation
To set up the project, follow the steps below:

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Compile the Java files:**
   Ensure you have JDK installed. Execute the following command:
   ```bash
   javac *.java
   ```

3. **Run the application:**
   You need to run the server and client in separate terminals. 

   - Start the server:
     ```bash
     java Server
     ```

   - Then run the client:
     ```bash
     java HospitalSystemTest
     ```

## Usage
Once the server is running and the client GUI is open, you can perform the following actions:

- **Create Patient File:** Click on "Create Patient File", fill in the details, and save the record.
- **View Patient Files:** Click on "View Patient Files" to see a list of patient records stored on the server.
- **Download Patient File:** Select a file from the list and click "Download Selected" to save it to your local system.

## Features
- User-friendly GUI built with Java Swing.
- Create and manage patient records using RMI.
- List all patient files stored on the server.
- Download selected patient files to your local machine.
- Supports basic validation when creating patient files.

## Dependencies
The project requires the following Java packages:
- `java.rmi` for RMI functionality.
- `java.io` for file handling and serialization.
- `javax.swing` for creating the GUI.

## Project Structure
The project is organized into the following files:

```
Hospital File Management System/
├── PatientFile.java                # Represents a patient file with details.
├── PatientFileManager.java         # The RMI interface for managing patient files.
├── PatientFileManagerImpl.java     # Implementation of the PatientFileManager interface.
├── Server.java                     # Starts the RMI server.
├── RMIHelper.java                  # Provides methods for RMI service connection.
├── MainForm.java                   # Main GUI form for the application.
├── CreatePatientDialog.java         # Dialog for creating new patient files.
├── FileBrowserPanel.java           # Panel for browsing and downloading patient files.
└── HospitalSystemTest.java         # Test class to start both server and client.
```

## Conclusion
The Hospital File Management System is a practical application for managing patient files in a hospital environment. By leveraging Java's RMI for remote file management, it provides a seamless way for healthcare professionals to access and store patient information securely.
```