/********************************************************************************
 * Program Description: contains the logic for handling the user's action thts triggered by the buttons in the GUI. 
 * Tasks:
 *      - handles adding new data (rows) to the dataset.
 *      - predicts the label based on user input.
 *      - trains the predictor with 150 rows of the dataset.
 *      - evaluates the accuracy of the predictor.
 * Date: 30/03/25
 * Author: Renee Low
 *******************************************************************************/
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

public class ActionHandler extends Handler {
    // constructor
    public ActionHandler(Predictor predictor, FileProcessor fileProcessor, GUI gui) {
        super(predictor, fileProcessor, gui);
    }

    // method that handles adding new rows of data to the data set
    public void dataHandling() {
        // gets data inputted by users from the GUI
        String powerStatus = gui.getPowerStatus();
        String networkSignal = gui.getNetworkSignal();
        String activity = gui.getActivity();
        String backgroundProcesses = gui.getBackgroundProcesses();
        String deviceStatus = gui.getDeviceStatus();

        // validation to check if all fields have been filled
        if (powerStatus.isEmpty() || networkSignal.isEmpty() || activity.isEmpty() || backgroundProcesses.isEmpty() || deviceStatus.isEmpty()) {
            // error message that displays if required fields haven't been inputted
            JOptionPane.showMessageDialog(gui, "Fill in the fields rat!");
            return;
        }

        // creates the feature object with the user's input
        Features newData = new Features(powerStatus, networkSignal, activity, backgroundProcesses, deviceStatus);
        predictor.addData(newData); // adds new data to the predictor's dataset

        // writes (appends) the data to the csv 
        fileProcessor.getFileWriter();
        fileProcessor.writeLineToFile(String.join(",", powerStatus, networkSignal, activity, backgroundProcesses, deviceStatus));
        fileProcessor.closeWriteFile();

        // displays a message when new data is added
        JOptionPane.showMessageDialog(gui, "New row added to the CSV file!");
    }

    // method that predicts the corresponding label based on the inputted features
    public void predictionHandling() {
        String powerStatus = gui.getPowerStatus();
        String networkSignal = gui.getNetworkSignal();
        String activity = gui.getActivity();
        String backgroundProcesses = gui.getBackgroundProcesses();

        if (powerStatus.isEmpty() || networkSignal.isEmpty() || activity.isEmpty() || backgroundProcesses.isEmpty()) {

            JOptionPane.showMessageDialog(gui, "Fill in the fields rat!");
            return;
        }
        
        // passes the values into the predict method in Predictor
        String prediction = predictor.predict(powerStatus, networkSignal, activity, backgroundProcesses);
        JOptionPane.showMessageDialog(gui, "Prediction for if the Device if online: " + prediction);

        // Debugging input values bc something is not right
        // System.out.println("Predicting label for:");
        // System.out.println("PowerStatus: " + powerStatus);
        // System.out.println("NetworkSignal: " + networkSignal);
        // System.out.println("Activity: " + activity);
        // System.out.println("BackgroundProcesses: " + backgroundProcesses);
    }

    // method for training the predictor with 150 random rows of data from the dataset
    public void trainingHandling() {
        // gets the dataset from the predictor
        ArrayList<Features> fullDataset = reloadDataset();
        Collections.shuffle(fullDataset); // shufflin the dataset
        // picks the first 150 rows from the shuffled dataset
        ArrayList<Features> trainingData = new ArrayList<>(fullDataset.subList(0, 150));
        predictor.setDataset(trainingData); // updates the predictor's data with the training data

        // confirm that there are 150 rows that are random
        System.out.println("Training data: "); {
            for (int i = 0; i < trainingData.size(); i++) {
                System.out.println("Row no. " + (i+1) + ": " + trainingData.get(i));
            }
        }

        System.out.println("\n=== Training Data ===");
        System.out.println("Total rows: " + trainingData.size()); 

        int yes = 0, no = 0;
        for (Features row : trainingData) {
            if (row.getDeviceIsOnline().equalsIgnoreCase("yes")) yes++;
            else no++;
        }
        System.out.println("Yes: " + yes + " | No: " + no);

        JOptionPane.showMessageDialog(gui, "The predictor-inator is trained!");
    }

    // method for calculating the accuracy o fthe predictor
    public void accuracyHandling() {
        // reloads the original dataset 
        ArrayList<Features> fullDataset = reloadDataset();

        Collections.shuffle(fullDataset);
        ArrayList<Features> trainingData = new ArrayList<>(fullDataset.subList(0, 150));
        ArrayList<Features> testData = new ArrayList<>(fullDataset.subList(150, 200));

        predictor.setDataset(trainingData); 

        int correctPredictions = 0;
        for (Features row : testData) {
            String predictedLabel = predictor.predict(row.getPowerStatus(), row.getNetworkSignal(), row.getActivity(), row.getBackgroundProcesses());

            String actualLabel = row.getDeviceIsOnline();

            if (predictedLabel.equalsIgnoreCase(actualLabel)) {
                correctPredictions++;
            }
        }

        float accuracy = ((float) correctPredictions / testData.size()) * 100; // gets the percentage
        // outputting results
        System.out.println("\n=== Prediction Accuracy ===");
        System.out.println("Correct Predictions: " + correctPredictions);
        System.out.println("Accuracy: " + accuracy + "%");
        JOptionPane.showMessageDialog(gui, "\nThe accuracy of the predictor is: " + String.format("%.2f", accuracy) + "%\n");

    }
}
