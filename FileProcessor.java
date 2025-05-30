/**************************************************
 *  Program Description: Handles CSV interaction
 *  Goals: 
 *      - reads and writes to the csv file
 *      - parses data into a usable format
 *      - updates csv when adding ddata
 *  Date: 27/03/25
 *  Author: Renee Low 
**************************************************/ 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileProcessor {
    private File file; // reps the csv file
    private String path; // path to csv file
    Scanner myScanner; // for reading the file
    PrintWriter pw;  // for writing to fiel

    // Constructor
    public FileProcessor(String path) {
        setData(path);
    }
    // Setter
    public void setData(String path) {
        this.path = path;
    }


    // CONNECTING to the csv file 
    public void connectFile() {
        file = new File(path);
    }


    // READING the file
    public ArrayList<String> readFile() {
        // initialising an ArrayList to store the contents fo the csv file
        ArrayList<String> lines = new ArrayList<>();

        try {
            myScanner = new Scanner(file); // scanner to read the file
            boolean ifHeader = true; 
            
            // reading lines from the csv then adding it to the ArrayList
            while(myScanner.hasNextLine()) {
                String line = myScanner.nextLine();
                // skips the 1st row of the csv file since it's the headers, then adds all other lines to the list
                if (ifHeader) {
                    ifHeader = false;
                    continue;
                }
                lines.add(line);
            }
            myScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found ");
        }
        return lines;
    }

    
    // INITIALIZING pw for writing 
    public void getFileWriter() {
        try {
            // opening file to append
            pw = new PrintWriter(new FileWriter(file, true));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    // WRITING to file 
    public void writeLineToFile(String line) {
        System.out.println("Row added: " + line);
        pw.println(line); // writes the string to the file
    }


    // CLOSING 
    public void closeReadFile() {
        myScanner.close();
    }
    public void closeWriteFile() {
        pw.close();
    }    
}
