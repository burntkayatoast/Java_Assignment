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
import javax.swing.JOptionPane;

public class ActionHandler {
    private Predictor predictor;
    private FileProcessor fileProcessor;
    private GUI gui;

    // constructor
    public ActionHandler (Predictor predictor, FileProcessor fileProcessor, GUI gui) {
        setPredictor(predictor);
        setFileProcessor(fileProcessor);
        setGui(gui);
    }

    // setters
    public void setPredictor(Predictor predictor) {
        this.predictor = predictor;
    }
    public void setFileProcessor(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    // method for data handling
    public void dataHandling() {
        // getting data from the GUI using getters
        String powerStatus = gui.getPowerStatus();
        String networkSignal = gui.getNetworkSignal();
        String activity = gui.getActivity();
        String backgroundProcesses = gui.getBackgroundProcesses();
        String deviceStatus = gui.getDeviceStatus();

        // validation to check if the fields have been filled
        if (powerStatus.isEmpty() || networkSignal.isEmpty() || activity.isEmpty() || backgroundProcesses.isEmpty() || deviceStatus.isEmpty()) {

            JOptionPane.showMessageDialog(gui, "Fill in the fields rat!");

            return;
        }

        // creates the feature objects with the user's input
        Features newData = new Features(powerStatus, networkSignal, activity, backgroundProcesses, deviceStatus);
        predictor.addData(newData); // adds new data to the predictor

        // writes the data to the csv
        fileProcessor.getFileWriter();
        fileProcessor.writeLineToFile(String.join(",", powerStatus, networkSignal, activity, backgroundProcesses, deviceStatus));
        fileProcessor.closeWriteFile();

        JOptionPane.showMessageDialog(gui, "New row added to the CSV file!");
    }

    public void predictionHandling() {
        // predicts the label based on the user's inputs for the fields  
        String powerStatus = gui.getPowerStatus();
        String networkSignal = gui.getNetworkSignal();
        String activity = gui.getActivity();
        String backgroundProcesses = gui.getBackgroundProcesses();

        if (powerStatus.isEmpty() || networkSignal.isEmpty() || activity.isEmpty() || backgroundProcesses.isEmpty()) {
            JOptionPane.showMessageDialog(gui, "Fill in the fields rat!");

            return;
        }
        

        // Debugging input values bc something is not right
        // System.out.println("Predicting label for:");
        // System.out.println("PowerStatus: " + powerStatus);
        // System.out.println("NetworkSignal: " + networkSignal);
        // System.out.println("Activity: " + activity);
        // System.out.println("BackgroundProcesses: " + backgroundProcesses);


        // passing the values into the predict method in Predictor
        String prediction = predictor.predict(powerStatus, networkSignal, activity, backgroundProcesses);
        JOptionPane.showMessageDialog(gui, "Prediction for if the Device if online: " + prediction);
    }

    public void trainingHandling() {
        JOptionPane.showMessageDialog(gui, "The predictor-inator is training with your added data...");
            
        // reads in the dataset and calculates the rule / functionality where the predictive rules are driven by values that are calculated dynamically from the dataset
    }

    public void accuracyHandling() {
        JOptionPane.showMessageDialog(gui, "The Accuracy of the predictor is [percentage value here]");

        // trains the predictor on 150 rows of the data and tests the data on 50 rows (each of the rows are automatically put through the rules and the predictive output automatically matched with the actual label)
    }
}
