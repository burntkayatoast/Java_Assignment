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
<<<<<<< HEAD
=======

>>>>>>> ac14605a6ab4641b73421f2d42d896a80d41a521
    // setter
    public void setDataset(ArrayList<Features> dataset) {
        this.dataset = dataset;
    }
    // Getter
    public ArrayList<Features> getDataset() {
        return dataset;
    }

<<<<<<< HEAD

=======
    
>>>>>>> ac14605a6ab4641b73421f2d42d896a80d41a521
    // method to add new row of data
    public void addData(Features newData) {
        this.dataset.add(newData);
    }
<<<<<<< HEAD

    // prediction for the label based on the features
    public String predict(String powerStatus, String networkSignal, String activity, String backgroundProcesses) {
        int yesCount = 0;
        int noCount = 0;

        
        // iterates through the dataset
        for (Features row : dataset) {
            // checks if the row matches the features
            if (row.getPowerStatus().equalsIgnoreCase(powerStatus) &&  row.getNetworkSignal().equalsIgnoreCase(networkSignal) && row.getActivity().equalsIgnoreCase(activity) && row.getBackgroundProcesses().equalsIgnoreCase(backgroundProcesses)) {
                // checking the label and incrementing the counters
                if (row.getDeviceIsOnline().equalsIgnoreCase("yes")) {
                    yesCount++;
                } else if (row.getDeviceIsOnline().equalsIgnoreCase("no")) {
                    noCount++;
                }
            }
         }

        // Debugging: Print the final counts
        // System.out.println("Final Yes Count: " + yesCount);
        // System.out.println("Final No Count: " + noCount);

         // compares the counts and returns the label with the higher count
         if (yesCount >= noCount ) {
            return "Yes";
         } else {
            return "No";
         }
    }

=======
>>>>>>> ac14605a6ab4641b73421f2d42d896a80d41a521
}

