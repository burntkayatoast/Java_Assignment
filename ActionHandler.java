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
import javax.swing.JOptionPane;

public class ActionHandler extends Handler {
    // constructor
    public ActionHandler(Predictor predictor, FileProcessor fileProcessor, GUI gui) {
        super(predictor, fileProcessor, gui); //passes the parameters to the parent class. 
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
        // initializes file writing, then adds the new data to form a row with a comma
        fileProcessor.getFileWriter();
        fileProcessor.writeLineToFile(String.join(",", powerStatus, networkSignal, activity, backgroundProcesses, deviceStatus));
        fileProcessor.closeWriteFile();
        fileProcessor.closeReadFile();

        // displays a message when new data is added
        JOptionPane.showMessageDialog(gui, "New row added to the CSV file!");
    }

    // method that predicts the corresponding label based on the inputted features
    public void predictionHandling() {
        // gets the feature values from the GUI input fields
        String powerStatus = gui.getPowerStatus();
        String networkSignal = gui.getNetworkSignal();
        String activity = gui.getActivity();
        String backgroundProcesses = gui.getBackgroundProcesses();

        // checks if the fields are filled
        if (powerStatus.isEmpty() || networkSignal.isEmpty() || activity.isEmpty() || backgroundProcesses.isEmpty()) {

            JOptionPane.showMessageDialog(gui, "Fill in the fields rat!");
            return;
        }
        
        // passes the values into the predict method in Predictor
        String prediction = predictor.predict(powerStatus, networkSignal, activity, backgroundProcesses);
        JOptionPane.showMessageDialog(gui, "Prediction: " + prediction);

        // Debugging input values bc something is not right
        // System.out.println("Predicting label for:");
        // System.out.println("PowerStatus: " + powerStatus);
        // System.out.println("NetworkSignal: " + networkSignal);
        // System.out.println("Activity: " + activity);
        // System.out.println("BackgroundProcesses: " + backgroundProcesses);
    }

    // method for calculating the accuracy o fthe predictor
    // splits the dataset into training and testing sublists, trainst he predictor on the training set, then compares the prediction's labels with the actual label
    public void accuracyHandling() {
        // reloads datset to include any newly added rows
        ArrayList<Features> fullDataset = reloadDataset();

        // goes through the dataset and categorizes rows based on labels. Yes adds to yesRows, No adds to noRows.
        ArrayList<Features> yesRows = new ArrayList<>();
        ArrayList<Features> noRows = new ArrayList<>();

        for (Features row : fullDataset) {
            if (row.getDeviceIsOnline().equalsIgnoreCase("yes")) {
                yesRows.add(row);
            } else {
                noRows.add(row);
            }
        }

        // calulates the number of rows needed for training and testing.
        int yesTrainSize = (int) (yesRows.size() * 0.75); 
        int noTrainSize = (int) (noRows.size() * 0.75);

        // creating training and testing sets
        ArrayList<Features> trainingData = new ArrayList<>();
        ArrayList<Features> testingData = new ArrayList<>();

        // adds 75% of yes/no rows to the training set
        trainingData.addAll(yesRows.subList(0, yesTrainSize));
        trainingData.addAll(noRows.subList(0, noTrainSize));
        // adds eth remaining yes/no rows into the testing data
        testingData.addAll(yesRows.subList(yesTrainSize, yesRows.size()));
        testingData.addAll(noRows.subList(noTrainSize, noRows.size()));

        // sets the dataset the predictor uses to the training data
        predictor.setDataset(trainingData);

        int correctPredictions = 0;
        System.out.println("\n=== Prediction Testing Details ===");

        // goes through each row in the testitng data
        for (Features row : testingData) {
            // takes features of the current row in the testing dataset to make a prediction for the label
            String fullRow = predictor.predict(
                row.getPowerStatus(), 
                row.getNetworkSignal(), 
                row.getActivity(), 
                row.getBackgroundProcesses()
            );
            
            // takes the predicted label from the result string and gets the actual label from the dataset row
            String predictedLabel = fullRow.split(" ")[0]; // splots the prediction row where there's a space, then takes the first element (the label part) and stores it
            String actualLabel = row.getDeviceIsOnline();
            
            // debugging. Displays the features. prediction and actual label for the current row. 
            System.out.println("Features: " + row.getPowerStatus() + ", " + row.getNetworkSignal() + 
                              ", " + row.getActivity() + ", " + row.getBackgroundProcesses());
            System.out.println("Prediction: '" + fullRow + "'");
            System.out.println("Label: '" + predictedLabel + "', Actual: '" + actualLabel + "'");
            
            // compares just the predicted label part with the actual label
            boolean isCorrect = predictedLabel.equalsIgnoreCase(actualLabel);
            System.out.println("Correct? " + isCorrect);
            
            if (isCorrect) {
                correctPredictions++;
            }
            System.out.println("---");
        }

        float accuracy = ((float) correctPredictions / testingData.size()) * 100; // gets the percentage
        // outputting results
        System.out.println("\n=== Data Separation Debugging ===");
        System.out.println("Total rows in dataset: " + fullDataset.size());
        System.out.println("Yes rows: " + yesRows.size());
        System.out.println("No rows: " + noRows.size());
        System.out.println("Training data size: " + trainingData.size());
        System.out.println("Testing data size: " + testingData.size());
        System.out.println("Yes rows in training: " + yesTrainSize);
        System.out.println("No rows in training: " + noTrainSize);
        System.out.println("Yes rows in testing: " + (yesRows.size() - yesTrainSize));
        System.out.println("No rows in testing: " + (noRows.size() - noTrainSize));
        
        // results of the split datasets for the yes/no labels
        System.out.println("\nProportion of 'Yes' in training: " + (yesTrainSize / (float) trainingData.size()) * 100 + "%");
        System.out.println("Proportion of 'No' in training: " + (noTrainSize / (float) trainingData.size()) * 100 + "%");
        System.out.println("Proportion of 'Yes' in testing: " + ((yesRows.size() - yesTrainSize) / (float) testingData.size()) * 100 + "%");
        System.out.println("Proportion of 'No' in testing: " + ((noRows.size() - noTrainSize) / (float) testingData.size()) * 100 + "%");

        // prints the final accuracy results
        System.out.println("\n=== Prediction Accuracy ===");
        System.out.println("Correct Predictions: " + correctPredictions);
        System.out.println("Accuracy: " + accuracy + "%");
        JOptionPane.showMessageDialog(gui, "\nThe accuracy of the predictor is: " + String.format("%.2f", accuracy) + "%\n");

        fileProcessor.closeReadFile();
    }
}
