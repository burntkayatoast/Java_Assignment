import java.util.ArrayList;

/*********************************************************
 * Program Description: Handles the logic of the program
 * Goals:    
 *      - connects to data from FileProcessor
 *      - implements frequency based prediction
 *      - recalculates probability after adding new data
 *      - tests for accuracy
 * Date: 27/03/25
 * Author: Renee Low
*********************************************************/

public class Predictor {
    private ArrayList<Features> dataset; 

    // Constructor
    public Predictor(ArrayList<Features> dataset) {
        setDataset(dataset);
    }

    // setter
    public void setDataset(ArrayList<Features> dataset) {
        this.dataset = dataset;
    }
    // Getter
    public ArrayList<Features> getDataset() {
        return dataset;
    }
    // method to add new row of data
    public void addData(Features newData) {
        this.dataset.add(newData);
    }


    // predicts the label based on the features inputted by the user
    public String predict(String powerStatus, String networkSignal, String activity, String backgroundProcesses) {
        int yesCount = 0;
        int noCount = 0;
        int total = 0;
        
        // iterates through the dataset to find rows that match the given features
        for (Features row : dataset) {
            // checks if the current row matches the given features 
            if (row.getPowerStatus().equalsIgnoreCase(powerStatus) &&  row.getNetworkSignal().equalsIgnoreCase(networkSignal) && row.getActivity().equalsIgnoreCase(activity) && row.getBackgroundProcesses().equalsIgnoreCase(backgroundProcesses)) {
                // checking the label and incrementing the counters
                if (row.getDeviceIsOnline().equalsIgnoreCase("yes")) {
                    yesCount++;
                } else if (row.getDeviceIsOnline().equalsIgnoreCase("no")) {
                    noCount++;
                }
            }
         }

        //  prints counts for yes/no
        System.out.println("\n=== Prediction Counts for matching rows ===");
        System.out.println("Yes Count: " + yesCount);
        System.out.println("No Count: " + noCount);
        System.out.println("\n");

        // calculates the probabilities
        total = yesCount + noCount;
        float probabilityYes = ((float) yesCount / total) * 100;
        float probabilityNo = ((float) noCount / total ) * 100;

        // prints results
        if (yesCount >= noCount) {
            System.out.println("Chances of 'DeviceIsOnline' being Yes: " + probabilityYes + "%");
        } else {
            System.out.println("Chances of 'DeviceIsOnline' being No: " + probabilityNo + "%");
        }

        // returns results
        if (yesCount >= noCount) {
            return "yes " + probabilityYes + "%";
        } else {
            return "no " + probabilityNo + "%";
        }
    }

    // method for recalculation of the classifier after adding new data to the dataset
    public void calculate(FileProcessor fileProcessor) {
        // reloads main dataset (the one with 200 hundred rows bc if you click eval accuracy data then calculate, it counts the training data and not the main data with 200 rows)
        ArrayList<Features> mainDataset = new ArrayList<>();
        for (String line : fileProcessor.readFile()) {
            String[] part = line.split(",");
            mainDataset.add(new Features(part[0], part[1], part[2], part[3], part[4]));
        }
        setDataset(mainDataset); // updates the predictors dataset with the reloaded 200 row dataset

        int totalYes = 0;
        int totalNo = 0;

        // iterates through the dataset to count the labels
        for (Features row : dataset) {
            if (row.getDeviceIsOnline().equalsIgnoreCase("yes")) {
                totalYes++;
            } else if (row.getDeviceIsOnline().equalsIgnoreCase("no")) {
                totalNo++;
            }
        }

        //  prints out the total number of yesses and no's
        System.out.println("\n=== Total rows & yes/no counts in the dataset ===");
        System.out.println("Dataset size: " + dataset.size() + " rows");
        System.out.println("Total Yes: " + totalYes);
        System.out.println("Total No: " + totalNo);

        fileProcessor.closeReadFile();
    }
}