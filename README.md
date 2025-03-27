# <p align="center">!! Predictor-inator !!</p>
### <p align="center">README</p>

### List of Classes
1. Control
2. FileProcessor
3. DataModel
4. Predictor

---
### 1. Control.java
**Purpose:**
It manages the overall flow of execution, initializes components, interacts with the data and triggers the predictions.
[note to self: edit this later]

---
### 2. FileProcessor.java
**Purpose:**
It interacts with the csv file, contains code to allow reading and writing to the file. 
**Main Methods:**
- `connectFile()`: connects to the csv file for reading and writing.
- `readFile()`: reads the csv file line by line and stores them in an ArrayList.
- `getFileWriter()`: initializes the file write to append data.
- `writeLineToFile()`: writes new lines to the csv file.
- `closeReadFile()` and `closeWriterFile()`: closes the file connections to the scanner and writer.
---
