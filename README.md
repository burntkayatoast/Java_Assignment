# <p align="center">!! Predictor-inator !!</p>
### <p align="center">README</p>

### List of Classes
1. Control
2. FileProcessor
3. Features
4. Predictor
5. GUI
6. ActionHandler

---
### 1. Control.java
**Purpose:**
It manages the overall flow of execution, initializes components, interacts with the data and triggers the predictions.
[note to self: edit this later]

---
### 2. FileProcessor.java
**Purpose:**
It interacts with the csv file, contains code to allow reading and writing to the file. 
<br><br>
**Main Methods:**
- `connectFile()`: connects to the csv file for reading and writing.
- `readFile()`: reads the csv file line by line and stores them in an ArrayList.
- `getFileWriter()`: initializes the file write to append data.
- `writeLineToFile()`: writes new lines to the csv file.
- `closeReadFile()` and `closeWriterFile()`: closes the file connections to the scanner and writer.
---
### 3. Features.java
**Purpose:**
Represents a single row in the dataset. It stores the values of the four features and their label so it makes accessing the data easier. 
! The data used was generated by ChatGPT ! 
<br><br>
**Features:**
- Power Status: on/off.
- Network Signal: weak/strong.
- Activity: active/inactive.
- Background Processes: running/stopped.
- Device Status (label: DeviceIsOnline): yes (online)/no (offline).
--- 
### 4. Predictor.java
**Purpose:**
Handles the logic of predicting whether a device is online based on the features. 
<br><br>
**Main Methods:**
- `predict()`: compares inputted features with dataset rows, counts the occurences of yes/no labels and returns the most frequent label as the predicted outcome.
- `recalculateClassifier()`: counts the total yes/no labels in the dataset and updates when new data is added. 
[add training and accuracy later]
---
### 5. GUI.java
**Purpose:**
Provides an interface for interacting with the program. It lets users input data for the four features and the label, make predictions, recalculates the totals, trains the model and evaluates the accuracy of the predictor.
<br><br>
**Main Methods:**
- `setupFileProcessor()`: intializes FileProcessor and Predictor (loads in dataset from the csv).
- `actionPerformed()`: Handles the button clicks.
- `createTextField()` / `createButton()`: methods hat help create the components.
- `setupFields()` / `setupLabels()` / `setupbuttons()` /  `setupFrame()`: methods that sets up the components.
- `addToFrame()`: adds the components to the GUI.
- `clearTextFields()`: clears existing data from the fields so user may enter new data.
---
### 6. ActionHandler.java
**Purpose:**
Handles triggered actions made by the user when clicking on a button from the GUI. 
<br><br>
-  `dataHandling()`: validates the input fields, creates a feature object and appends the data to the dataset.
-  `predictionHandling()`: passes user input to the `predict()` method and displays the result.
[add training and accuracy later]
