import java.util.ArrayList;

public class Handler {
    protected Predictor predictor;
    protected FileProcessor fileProcessor;
    protected GUI gui;

    public Handler(Predictor predictor, FileProcessor fileProcessor, GUI gui) {
        setPredictor(predictor);
        setFileProcessor(fileProcessor);
        setGui(gui);
    }

    public void setPredictor(Predictor predictor) {
        this.predictor = predictor;
    }
    public void setFileProcessor(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    // method to reload the dataset from the csv
    public ArrayList<Features> reloadDataset() {
        ArrayList<Features> fullDataset = new ArrayList<>();
        // reads each row from the csv file
        for (String line : fileProcessor.readFile()) {
            String[] parts = line.split(","); // splits them into an array based on teh commas
            fullDataset.add(new Features(parts[0], parts[1], parts[2], parts[3], parts[4])); // creates featurew object using the values and adds them into the dataset
        }
        return fullDataset;
    }
}
