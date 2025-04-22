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
    private ActionHandler actionHandler;
    
    // buttons for actions
    private JButton addDataButton; 
    private JButton predictButton;
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
        setupFileProcessor();
        actionHandler = new ActionHandler(predictor, fileProcessor, this);
        setupFrame();
        setupComponents();
        addToFrame();
    }

    // getters
    public String getPowerStatus() {
        return powerStatusField.getText();
    }
    public String getNetworkSignal() {
        return networksignalField.getText();
    }
    public String getActivity() {
        return activityField.getText();
    }
    public String getBackgroundProcesses() {
        return backgroundProcessesField.getText();
    }
    public String getDeviceStatus() {
        return deviceStatusField.getText();
    }

    // puts the components together 
    public void setupComponents() {
        setupLabels();
        setupFields();
        setupButtons();
    }

    // sets up the layout of the frame
    public void setupFrame() {
        setSize(600, 400);
        setLayout(new FlowLayout());
    }

    // sets up labels for the fields
    public void setupLabels() {
        powerStatusLabel = new JLabel("Power Status (on/off)");
        networkSignalLabel = new JLabel("Network Signal (weak/strong)");
        activityLabel = new JLabel("Activity (active/inactive)");
        backgroundProcessesLabel = new JLabel("Background Processes (running/stopped)");
        deviceStatusLabel = new JLabel("Device is Online (yes/no)");
    }

    // sets up the text fields with tooltips 
    public void setupFields() {
        powerStatusField = createTextField("Enter on or off");
        networksignalField = createTextField("Enter weak or strong");
        activityField = createTextField("Enter active or inactive");
        backgroundProcessesField = createTextField("Enter running or stopped");
        deviceStatusField = createTextField("Enter yes or no");
    }

    // sets up the buttons
    public void setupButtons() {
        addDataButton = createButton("Add data row");
        predictButton = createButton("Predict");
        evaluateAccuracyButton = createButton("Evaluate accuracy");
        clearButton = createButton("Clear Fields");
        calculateButton = createButton("Calculate");

        // connecting buttons to actionCommand
        addDataButton.setActionCommand("addData");
        predictButton.setActionCommand("predict");
        evaluateAccuracyButton.setActionCommand("accuracy");
        calculateButton.setActionCommand("calculate");
        clearButton.setActionCommand("clear");
    }

    // adds the components to the interface
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
        add(calculateButton);
        add(evaluateAccuracyButton);
        add(clearButton);

        setVisible(true); 
    }   

    // clears text from the fields
    public void clearTextFields() {
        powerStatusField.setText("");
        networksignalField.setText("");
        activityField.setText("");
        backgroundProcessesField.setText("");
        deviceStatusField.setText("");
    }

    // creates text field + tooltip 
    public JTextField createTextField(String tooltip) {
        JTextField textField = new JTextField(10);
        textField.setPreferredSize(new Dimension(100, 30));
        textField.setToolTipText(tooltip);
        return textField;
    }

    // creates button and connects to an action listener
    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        return button;
    }

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

    // button handling
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();

        switch (action) {
            case "addData":
                actionHandler.dataHandling(); 
                break;

            case "predict":
                actionHandler.predictionHandling();
                break;

            case "accuracy":
                actionHandler.accuracyHandling();
                break;

            case "clear":
                clearTextFields();
                break;

            case "calculate":
                predictor.calculate(fileProcessor);
                break;
        }
    } 
}