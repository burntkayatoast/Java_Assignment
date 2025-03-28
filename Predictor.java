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
}
