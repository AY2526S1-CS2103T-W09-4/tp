# **QuickCLI User Guide**

## **Table of Contents**

- [About: How to use the guide](#_QuickCLI_User_Guide)
- [Product Overview](#_Product_Overview)
- [Quick Start](#_Quick_Start)
- [Features](#_Features)
  - [Understanding the Command Format](#_Understanding_the_Command)
  - [Adding a contact: add](#_Adding_a_contact:)
  - [Listing all contacts: list](#_Listing_all_contacts:)
  - [Finding contacts: find](#_Finding_contacts:_find)
  - [Editing a contact: edit](#_Editing_a_contact:)
  - [Deleting a contact: delete](#_Deleting_a_contact:)
  - [Adding notes to a contact: note](#_Adding_notes_to)
  - [Sorting contacts: sort](#_Sorting_contacts:_sort)
  - [Clearing all contacts: clear](#_Sorting_contacts:_sort)
  - [Viewing help: help](#_Viewing_help:_help)
  - [Exiting the program: exit](#_Exiting_the_program:)
- [Data Management](#_Data_Management)
  - [Saving the data](#_Saving_the_data)
  - [Editing the data file](#_Editing_the_data)
- [FAQ](#_FAQ)
- [Command Summary](#_Command_Summary)
- [Troubleshooting](#_Troubleshooting)
- [Contact & Support](#_Contact_&_Support)
- [Glossary](#_Glossary)

## **About: How to use the guide**

Words highlighted in <mark> yellow </mark> represent commands that should be typed into the **command terminal** in QuickCLI under the section [Features](#_Features).

**Tip Boxes** provide additional guidance or useful hints to help you use QuickCLI more effectively:
<img width="553" height="109" alt="image" src="https://github.com/user-attachments/assets/ae8307da-aec9-448d-a3ce-644025781e3e" />

**Warning Boxes** alert you about something important, risky, or potentially destructive.
<img width="565" height="109" alt="image" src="https://github.com/user-attachments/assets/3a9df3d2-4b3d-4f75-bd08-7d02e0193954" />


## **Product Overview**

QuickCLI is a **desktop application for freelance professionals** who need to manage multiple client relationships efficiently. Optimized for users who can type fast, QuickCLI allows you to manage your contacts faster than traditional GUI applications through a Command Line Interface (CLI), while still providing the visual benefits of a Graphical User Interface (GUI).

**Perfect for:** Freelance developers, designers, writers, marketers, and consultants who value keyboard efficiency and need quick access to client information.

## **Quick Start**

- **Check System Requirements**
  - Ensure you have Java 17 or above installed on your computer, follow the Instruction Guide for Windows users [here](https://se-education.org/guides/tutorials/javaInstallationWindows.html)
  - Mac users: Follow the Instruction Guide [here](https://se-education.org/guides/tutorials/javaInstallationMac.html)
- **Download QuickCLI**
  - Download the latest quickcli.jar file from our release page [here](https://github.com/AY2526S1-CS2103T-W09-4/tp/releases/tag/v0.3.1)
- **Set Up the Application**
  - Create a folder where you want to store QuickCLI (e.g., C:\\QuickCLI or ~/QuickCLI)
  - Copy the downloaded JAR file into this folder
- **Launch QuickCLI**
  - Open a command terminal/console
    - For Windows users, you can use the Command Prompt:

<img width="987" height="720" alt="image" src="https://github.com/user-attachments/assets/2865b6ad-e53c-4968-bf46-95ee09ebe4bd" />


- - 1. For Mac Users, you can use the Terminal:

<img width="650" height="365" alt="image" src="https://github.com/user-attachments/assets/f46e1166-a88d-4e8b-87d2-538bf8c3e8e3" />


- 1. Navigate to the folder: cd path/to/quickcli/folder
  - Run the application: java -jar quickcli.jar
  - The GUI should appear in a few seconds with some sample data
  - You should see:
    - A contact list panel displaying sample contacts
    - A command input box at the top where you'll type commands
    - A result display panel showing command feedback
    <img width="904" height="734" alt="image" src="https://github.com/user-attachments/assets/9583da02-47f1-40a0-9764-609f42c8ee66" />


- **Try Your First Commands** Type these commands in the command box and press Enter:
  - <mark> list </mark> - View all contacts
  - <mark> add n/Jane Smith p/91234567 </mark> - Add a new contact
  - <mark> find John </mark> - Search for contacts named John
  - <mark> help </mark> - View available commands

## **Features**

### **Understanding the Command Format**

Before diving into specific commands, here's how you read the command syntax:

- **Words in UPPER_CASE** are parameters you need to provide
  - Example: add n/NAME means replace NAME with actual name like add <mark> add n/John Doe </mark>
- **Items in square brackets \[\]** are optional
  - Example: n/NAME \[t/TAG\] can be used as n/John Doe t/client or just n/John Doe
- **Items with ...** can be used multiple times (including zero)
  - Example: \[t/TAG\]... can be omitted **OR**
  - Used once: t/designer **OR**
  - Multiple times t/designer t/priority
- **Parameters can be in any order**
  - Example: n/NAME p/PHONE is the same as p/PHONE n/NAME
- **Extraneous parameters** for commands that don't take parameters (like help, list, exit, clear) will be ignored
  - Example: help 123 will be interpreted as help

### **Managing Contacts**

#### Adding a contact: add

Adds a new client contact so you can manage your client list in QuickCLI.

**Format:** <mark> add n/NAME p/PHONE \[e/EMAIL\] \[a/ADDRESS\] \[c/COMPANY\] \[pr/PRIORITY\] \[t/TAG\]... \[r/REMARKS\] </mark> 
