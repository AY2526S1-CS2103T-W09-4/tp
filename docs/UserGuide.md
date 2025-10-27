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
- **Extraneous parameters** for commands that don't take parameters (like help, exit, clear) will be ignored
  - Example: <mark> help 123 </mark> will be interpreted as help

### **Managing Contacts**

#### Adding a contact: add

Adds a new client contact so you can manage your client list in QuickCLI.

**Format:** <mark> add n/NAME p/PHONE \[e/EMAIL\] \[a/ADDRESS\] \[c/COMPANY\] \[pr/PRIORITY\] \[t/TAG\]... \[r/REMARKS\] </mark> 
**Parameters:**

<div class="joplin-table-wrapper"><table><tbody><tr><th><p><strong>Parameter</strong></p></th><th><p><strong>Definition / Explanation</strong></p></th></tr><tr><td><p>n/NAME</p></td><td><p>Client's name (required)</p><ul><li>Alphanumeric characters and spaces only</li><li>1-100 characters</li></ul></td></tr><tr><td><p>p/PHONE</p></td><td><p>Phone number (required)</p><ul><li>Numbers only, 3-15 digits</li></ul></td></tr><tr><td><p>e/EMAIL</p></td><td><p>Email address (optional)</p><ul><li>Must be valid email format</li></ul></td></tr><tr><td><p>a/ADDRESS</p></td><td><p>Address (optional)</p></td></tr><tr><td><p>C/COMPANY</p></td><td><p>Company name (optional)</p><ul><li>Can include letters, numbers, spaces, and . , &amp; -</li></ul></td></tr><tr><td><p>t/TAG</p></td><td><p>Tags for categorization (optional, multiple allowed)</p><ul><li>Single word, alphanumeric only</li></ul></td></tr><tr><td><p>pr/PRIORITY</p></td><td><p>Priority level (optional)</p><ul><li>Can be HIGH, MEDIUM, or LOW (case-insensitive)</li><li>Or numeric: 1-2 = HIGH, 3-4 = MEDIUM, 5 = LOW<ul><li>For more details on this feature, refer to Section: <a href="#_Setting_priority_for">Setting priority for a contact: priority</a></li></ul></li></ul></td></tr><tr><td><p>r/REMARKS</p></td><td><ul><li>Add remarks or project details to a contact (optional)<ul><li>For more details on this feature, refer to Section: <a href="#_Adding_notes_to">Adding notes to a contact: note</a></li></ul></li></ul></td></tr></tbody></table></div>

**Examples:**

| **Command** | **You have added Client...** |
| --- | --- |
| <mark> add n/John Doe p/98765432 </mark>  | John Doe - name, phone |
| <mark> add n/Sarah Chen p/91234567 [e/sarah@design.co](mailto:e/sarah@design.co) c/Chen Designs t/designer t/priority </mark> | Sarah Chen - name, phone; optional: email, company, 2 tags |
| <mark> add n/Mike Wong p/87654321 [e/mike@techcorp.com](mailto:e/mike@techcorp.com) t/developer r/Prefers Email Communication </mark>| Mike Wong - name, phone; optional: email, tag, remark |
| <mark> add n/Jane Smith p/92345678 e/jane@urgentclient.com pr/HIGH t/priority </mark> | Jane Smith - name, phone; optional: email, priority |

**Note:** QuickCLI prevents duplicate contacts. Two contacts are considered duplicates if they have the same name AND phone number.

#### Listing all contacts: list

Shows all contacts in your database. You can also filter by tags.

**Format:**

- <mark> list </mark> - Shows all contacts
- <mark> list t/TAG </mark> - Shows contacts with specific tag (learn about tags in the add command section)

**Examples:**

<mark> list </mark>
<mark> list t/priority </mark> 
<mark> list t/designer </mark>

#### Finding contacts: find

Searches across all fields including name, phone, email, address, company, tags, and priority level.

**Format:** <mark> find KEYWORD \[MORE_KEYWORDS\] </mark>

- Search is case-insensitive (john matches John)
- Partial matching is supported (Joh matches John)
- Multiple keywords use OR logic (find john marys all Johns OR Marys)

**Examples:**

- <mark> find john </mark>
- <mark> find chen wong </mark>
- <mark> find design </mark>
- <mark> find HIGH </mark> (finds all high-priority contacts)
- <mark> find urgent high </mark> (finds contacts with "urgent" OR "high" in any field)

<img width="708" height="146" alt="image" src="https://github.com/user-attachments/assets/3ea68fd4-bf55-4fc5-9a21-f0f8fc1002e2" />


### **Modifying Contact Information**

#### Editing a contact: edit

Updates the details of an existing contact.

**Format:** <mark> edit INDEX \[n/NAME\] \[p/PHONE\] \[e/EMAIL\] \[c/COMPANY\] \[pr/PRIORITY\] \[t/TAG\]... </mark>

- Edit the contact at the specified INDEX (shown in the contact list)
- At least one field must be provided

<img width="778" height="195" alt="image" src="https://github.com/user-attachments/assets/dc8a29b0-869a-4c6f-8998-533866c49b7f" />


**Examples:**

- <mark> edit 1 p/91234567 </mark>
- <mark> edit 2 n/John Smith [e/john@newcompany.com](mailto:e/john@newcompany.com) </mark>
- <mark> edit 3 t/priority t/developer </mark>
- <mark> edit 2 pr/MEDIUM t/important </mark>
- <mark> edit 4 t/ </mark>

#### Adding notes to a contact: note

Add remarks or project details to a contact.

**Format:** <mark> note INDEX r/REMARKS </mark>

- Adds notes to the contact at the specified INDEX
- Replaces any existing notes
- Maximum 500 characters
- The **clock button** appears next to each note added to a contact.

<img width="80" height="26" alt="image" src="https://github.com/user-attachments/assets/279188d2-62d9-4a4a-8580-f4975e8654c4" />

- Clicking the clock button displays the **timestamp** showing when the note was added.

<img width="274" height="29" alt="image" src="https://github.com/user-attachments/assets/d47d9f26-437b-491a-a7e7-1536b8ba7995" />

- If no note exists, the icon only appears on hover.

**Examples:**

<mark> note 1 r/Discussed new website project, budget \$5000 </mark> 
<mark> note 2 r/Meeting scheduled for next Tuesday, 2pm  </mark>
<br/>

Setting priority for a contact: priority  
<br/>Assign or update the priority level for a contact to help you focus on important clients.  
<br/>**Format:** <mark> priority INDEX pr/PRIORITY </mark>

- Set priority for the contact at the specified INDEX
- Replaces any existing priority level
- Priority levels: HIGH, MEDIUM, LOW (case-insensitive)
- Alternative: Use numbers 1-5 (1-2 = HIGH, 3-4 = MEDIUM, 5 = LOW)

**Visual indicators:**

- **HIGH** priority: Red badge with \`!!!\` symbol
- **MEDIUM** priority: Orange badge with \`!!\` symbol
- **LOW** priority: Green badge with \`!\` symbol

**Parameters:**

- pr/PRIORITY: Priority level (required)
- Valid values: \`HIGH\`, \`MEDIUM\`, \`LOW\`, or \`1\`, \`2\`, \`3\`, \`4\`, \`5\`
- Case-insensitive

**Examples:**

- <mark> priority 1 pr/HIGH </mark>
- <mark> priority 2 pr/medium </mark>
- <mark> priority 3 pr/1 </mark>

<img width="913" height="197" alt="image" src="https://github.com/user-attachments/assets/7fcb4e47-300e-483d-a02d-3d52daabb6da" />

#### Deleting a contact: delete

Removes a contact from your database.

**Format:** <mark> delete INDEX </mark>

- Deletes the contact at the specified INDEX
- The index refers to the number shown in the current contact list
- The index must be a positive integer (1, 2, 3, ...)

**Examples:**

<mark> delete 3 </mark> deletes Charlotte Oliveiro

<img width="905" height="737" alt="image" src="https://github.com/user-attachments/assets/c2f24953-7b94-4863-88d6-5e5324724f72" />

<img width="865" height="202" alt="image" src="https://github.com/user-attachments/assets/34b01396-f1a0-48a0-9267-7760d0e6eb86" />

<mark> find roy </mark>

<img width="902" height="737" alt="image" src="https://github.com/user-attachments/assets/4b7b3f49-adad-40f9-a40e-db8edbcb7540" />

<mark> delete 1 </mark> (Deletes Person with Index 1 in the list filtered by find: In this case, it deletes Roy Balakrishnan)

### **Organizing Your Contacts**

#### Sorting contacts: sort

Organize your contact list for easier access.

**Format:** sort \[CRITERION\]

**Available sorting options:**

- sort or sort name - Alphabetical by name (default)
- sort recent - Most recently added first
- sort priority - By priority level (HIGH → MEDIUM → LOW, then no priority)

**Examples:**

- Sort
- sort name
- sort recent
- sort priority

### **System Commands**

#### Clearing all contacts: clear

Removes all contacts from the database. Use with caution!

**Format:** clear

- You will be prompted to confirm: Type clear confirm to proceed

![](!)

- This action cannot be undone

#### Viewing help: help

Opens the help window showing all available commands.

**Format:** help

You can type help in the command input box and pressing enter on your keyboard :

**OR**

**You can hover over Help and click on the button 'Help F1':**

![]()

Will automatically launch this window:

![]()

| **Button** | Action |
| --- | --- |
| Copy URL | Clicking it copies URL shown into clipboard. |
| Open in Browser | Opens URL in default browser<br><br>![]() |

#### Exiting the program: exit

Closes QuickCLI. Your data is automatically saved.

**Format:** exit

![]

## **FAQ**

**Q: Can I use QuickCLI on multiple computers?** A: Yes! Copy the quickcli.json data file from the data folder to transfer your contacts between computers.

**Q: What happens if I enter an invalid command?** A: QuickCLI will show an error message explaining what went wrong. Check the command format and try again.

**Q: Can I have multiple contacts with the same name?** A: Yes, as long as they have different phone numbers. QuickCLI considers contacts duplicates only if both name AND phone number match.

**Q: Is there a limit to how many contacts I can store?** A: QuickCLI can handle up to 1,000 contacts efficiently. Performance may degrade with larger databases.

**Q: Can I undo a delete operation?** A: Currently, delete operations cannot be undone. Future versions will include undo/redo functionality.

**Q: How do I import contacts from another application?** A: Import/export functionality is coming in version 2.0. For now, you can manually edit the JSON data file.

**Q: What if QuickCLI doesn't start?**

- Check that Java 17 or higher is installed: java -version
- Ensure you're in the correct directory
- Try running with: java -jar quickcli.jar
- Check for error messages in the terminal
