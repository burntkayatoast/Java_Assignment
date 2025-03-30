/********************************************************************************
 * Program Description: interface the user interacts with duuuh
 * Goals:
 *      - input fields for the four features
 *      - buttons for predicting, training, adding data and testing accuracy.
 *      - displays the results of everything yur
 * Date: 27/03/25
 * Author: Renee Low
 *******************************************************************************/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI extends JFrame implements ActionListener { 
    // COMPONENTS
    private Predictor predictor;
    private FileProcessor fileProcessor;
    
    // buttons for actions
    private JButton addDataButton; 
    private JButton predictButton;
    private JButton trainDataButton;
    private JButton evaluateAccuracyButton;
    private JButton clearButton;
    private JButton calculateButton;
    
    // text fields for user input
    private JTextField powerStatusField;
    private JTextField networksignalField;
    private JTextField activityField;
    private JTextField backgroundProcessesField;
    private JTextField deviceStatusField;

    // lavels for the text fields
    private JLabel powerStatusLabel;
    private JLabel networkSignalLabel;
    private JLabel activityLabel;
    private JLabel backgroundProcessesLabel;
    private JLabel deviceStatusLabel;

    // constructor
    public GUI(String title) {
        super(title);
        setSize(600, 400);
        setLayout(new FlowLayout());
        setupFileProcessor();
        setupComponents();
        addToFrame();
    }

    // method for handling file processing
    public void setupFileProcessor() {
        // intializes FileProcessor and Predictor
        fileProcessor = new FileProcessor("device_status_dataset.csv");
        fileProcessor.connectFile();

        // puts dataset into predictor
        ArrayList<Features> dataset = new ArrayList<>();
        for (String line : fileProcessor.readFile()) {
            String[] part = line.split(","); // splits each row into features
            dataset.add(new Features(part[0], part[1], part[2], part[3], part[4])); // creates the feature objects
        }
        predictor = new Predictor(dataset);
    }

    // method for setting up the components
    public void setupComponents() {
        // creating labels for the fields
        powerStatusLabel = new JLabel("Power Status (on/off)");
        networkSignalLabel = new JLabel("Network Signal (weak/strong)");
        activityLabel = new JLabel("Activity (active/inactive)");
        backgroundProcessesLabel = new JLabel("Background Processes (running/stopped)");
        deviceStatusLabel = new JLabel("Device is Online (yes/no)");

        // creating the text fields with tooltips 
        powerStatusField = createTextField("Enter on or off");
        networksignalField = createTextField("Enter weak or strong");
        activityField = createTextField("Enter active or inactive");
        backgroundProcessesField = createTextField("Enter running or stopped");
        deviceStatusField = createTextField("Enter yes or no");

        // creating the buttons
        addDataButton = createButton("Add data row");
        predictButton = createButton("Predict");
        trainDataButton = createButton("Train data");
        evaluateAccuracyButton = createButton("Evaluate accuracy");
        clearButton = createButton("Clear Fields");
        calculateButton = createButton("Calculate");

        // connecting buttons to actionCommand
        addDataButton.setActionCommand("addData");
        predictButton.setActionCommand("predict");
        trainDataButton.setActionCommand("train");
        evaluateAccuracyButton.setActionCommand("evaluate");
        calculateButton.setActionCommand("calculate");
        clearButton.setActionCommand("clear");

    }

    // method for creating text field + tooltip
    public JTextField createTextField(String tooltip) {
        JTextField textField = new JTextField(10);
        textField.setPreferredSize(new Dimension(100, 30));
        textField.setToolTipText(tooltip);
        return textField;
    }
    // method for creating button and adding an action listener
    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        return button;
    }


    // adds the components to the JFrame
    public void addToFrame() {
        // adding the labels and text fields
        add(powerStatusLabel);
        add(powerStatusField);
        add(networkSignalLabel);
        add(networksignalField);
        add(activityLabel);
        add(activityField);
        add(backgroundProcessesLabel);
        add(backgroundProcessesField);
        add(deviceStatusLabel);
        add(deviceStatusField);

        // adding the buttons
        add(addDataButton);
        add(predictButton);
        add(clearButton);
        add(calculateButton);

        setVisible(true); 
    }


    // button handling
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();

        switch (action) {
            case "addData":
                dataHandling(); // handles adding new rows of data
                break;
            case "predict":
                predictionHandling(); // handles the prediction 
                break;
            case "train":
                trainingHandling(); // handles the training of the data
                break;
            case "accuracy":
                accuracyHandling(); // handles the accuracy evaluation
                break;
            case "calculate":
                predictor.calculateClassifier(); // handles (re)calculation of the classifier
                break;
            case "clear":
                clearTextFields();
                break;
        }
    }

    public void dataHandling() {
        // takes data from the textfields
        String powerStatus = powerStatusField.getText();
        String networkSignal = networksignalField.getText();
        String activity = activityField.getText();
        String backgroundProcesses = backgroundProcessesField.getText();
        String deviceStatus = deviceStatusField.getText();

        // validation to check if the fields have been filled
        if (powerStatusField.getText().isEmpty() ||
            networksignalField.getText().isEmpty() ||
            activityField.getText().isEmpty() ||
            backgroundProcessesField.getText().isEmpty() ||
            deviceStatusField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill in the fields rat!");

            return;
        }

        // creates feature obj with user input
        Features newData = new Features(powerStatus, networkSignal, activity, backgroundProcesses, deviceStatus);
        predictor.addData(newData); // adds new data to the csv

        // writes the data to the csv
        fileProcessor.getFileWriter();
        fileProcessor.writeLineToFile(String.join(",", powerStatus, networkSignal, activity, backgroundProcesses, deviceStatus));
        fileProcessor.closeWriteFile();
        
        JOptionPane.showMessageDialog(this, "new row added to the csv file!");
    }

    public void predictionHandling() {
        // predicts the label based on the users inputs
        String powerStatus = powerStatusField.getText();
        String networkSignal = networksignalField.getText();
        String activity = activityField.getText();
        String backgroundProcesses = backgroundProcessesField.getText();

        
        if (powerStatusField.getText().isEmpty() ||
            networksignalField.getText().isEmpty() ||
            activityField.getText().isEmpty() ||
            backgroundProcessesField.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Fill in the fields rat!");

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
        JOptionPane.showMessageDialog(this, "Prediction for if the Device if online: " + prediction);
    }

    public void trainingHandling() {
        JOptionPane.showMessageDialog(this, "The predictor-inator is training with your added data...");
            
        // reads in the dataset and calculates the rule / functionality where the predictive rules are driven by values that are calculated dynamically from the dataset
    }

    public void accuracyHandling() {
        JOptionPane.showMessageDialog(this, "The Accuracy of the predictor is [percentage value here]");

        // trains the predictor on 150 rows of the data and tests the data on 50 rows (each of the rows are automatically put through the rules and the predictive output automatically matched with the actual label)
    }

    public void clearTextFields() {
        // clear button
        powerStatusField.setText("");
        networksignalField.setText("");
        activityField.setText("");
        backgroundProcessesField.setText("");
        deviceStatusField.setText("");
    }
    
}