/********************************************************************************
 * Program Description: interface the user interacts with duuuh
 * Goals:
 *      - input fields for the four features
 *      - buttons for predicting, training, adding data and testing accuracy.
 *      - displays the results of everything yur
 * Date: 27/03/25
 * Author: Renee Low
 *******************************************************************************/
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JTextField;

public class GUI extends JFrame implements ActionListener { 
    // COMPONENTS
    private JFrame frame;
    private JPanel panel;
    private Predictor predictor;
    private FileProcessor fileProcessor;
   
    private JButton addDataButton; 
    private JButton predictButton;
    private JButton trainDataButton;
    private JButton evaluateAccuracyButton;
    private JButton clearButton;
    
    private JTextField powerStatusField;
    private JTextField networksignalField;
    private JTextField activityField;
    private JTextField backgroundProcessesField;
    private JTextField deviceStatusField;
    
    private JLabel powerStatusLabel;
    private JLabel networkSignalLabel;
    private JLabel activityLabel;
    private JLabel backgroundProcessesLabel;
    private JLabel deviceStatusLabel;

    // constructor
    public GUI(String title) {
        super(title);
        setSize(800, 500);
        setLayout(new FlowLayout());

        // intializing FileProcessor and Predictor
        fileProcessor = new FileProcessor("device_status_dataset.csv");
        fileProcessor.connectFile();

        // putting dataset into predictor
        ArrayList<Features> dataset = new ArrayList<>();
        for (String line : fileProcessor.readFile()) {
            String[] part = line.split(","); // splits each row into features
            dataset.add(new Features(part[0], part[1], part[2], part[3], part[4])); // creates the feature objects
        }
        predictor = new Predictor(dataset);

        // creating buttons
        addDataButton = new JButton("Add data row");
        predictButton = new JButton("Predict");
        trainDataButton = new JButton("Train data");
        evaluateAccuracyButton = new JButton("Evaluate accuracy");
        clearButton = new JButton("Clear Fields");

        // creating the textfields
        powerStatusField = new JTextField("");
        networksignalField = new JTextField("");
        activityField = new JTextField("");
        backgroundProcessesField = new JTextField("");
        deviceStatusField = new JTextField("");

        // setting text fireld sizes
        powerStatusField.setPreferredSize(new Dimension(100, 30));
        networksignalField.setPreferredSize(new Dimension(100, 30));
        activityField.setPreferredSize(new Dimension(100, 30));
        backgroundProcessesField.setPreferredSize(new Dimension(100, 30));
        deviceStatusField.setPreferredSize(new Dimension(100, 30));

        // setting tooltips
        powerStatusField.setToolTipText("Enter on or off");
        networksignalField.setToolTipText("Enter weak or strong");
        activityField.setToolTipText("Enter active or inactive ");
        backgroundProcessesField.setToolTipText("Enter running or stopped");
        deviceStatusField.setToolTipText("Enter yes or no");

        // creating labels for the fields
        powerStatusLabel = new JLabel("Power Status (on/off)");
        networkSignalLabel = new JLabel("Network Signal (weak/strong)");
        activityLabel = new JLabel("Activity (active/inactive)");
        backgroundProcessesLabel = new JLabel("Background Processes (running/stopped)");
        deviceStatusLabel = new JLabel("Device is Online (yes/no)");

        // action listeners
        addDataButton.addActionListener(this);
        predictButton.addActionListener(this);
        trainDataButton.addActionListener(this);
        evaluateAccuracyButton.addActionListener(this);
        clearButton.addActionListener(this);

        // adding the components to Frame
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
        add(addDataButton);
        add(predictButton);
        add(clearButton); 

        setVisible(true);
    }

    // button handling
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == addDataButton) {
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
        

            //  creates feature obj with user input
            Features newData = new Features(powerStatus, networkSignal, activity, backgroundProcesses, deviceStatus);
            // adds new data to the csv
            predictor.addData(newData);
            // writes the data to the csv
            fileProcessor.getFileWriter();
            fileProcessor.writeLineToFile(String.join(",", powerStatus, networkSignal, activity, backgroundProcesses, deviceStatus));
            fileProcessor.closeWriteFile();

            JOptionPane.showMessageDialog(this, "new row added to the csv file!");

        } else if (event.getSource() == predictButton) {
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

            // Debugging input values bc something is nawt right
            // System.out.println("Predicting label for:");
            // System.out.println("PowerStatus: " + powerStatus);
            // System.out.println("NetworkSignal: " + networkSignal);
            // System.out.println("Activity: " + activity);
            // System.out.println("BackgroundProcesses: " + backgroundProcesses);
    

            // passing the values into the predict method in Predictor
            String prediction = predictor.predict(powerStatus, networkSignal, activity, backgroundProcesses);
            JOptionPane.showMessageDialog(this, "Prediction for if the Device if online: " + prediction);

        } else if (event.getSource() == trainDataButton) {
            JOptionPane.showMessageDialog(this, "The predictor-inator is training with your added data...");
            
            // reads in the dataset and calculates the rule / functionality where the predictive rules are driven by values that are calculated dynamically from the dataset
        } else if (event.getSource() == evaluateAccuracyButton) {
            JOptionPane.showMessageDialog(this, "The Accuracy of the predictor is [percentage value here]");

            // trains the predictor on 150 rows of the data and tests the data on 50 rows (each of the rows are automatically put through the rules and the predictive output automatically matched with the actual label)
        } else {
            // clear button
            powerStatusField.setText("");
            networksignalField.setText("");
            activityField.setText("");
            backgroundProcessesField.setText("");
            deviceStatusField.setText("");
        }
    }

    
}
