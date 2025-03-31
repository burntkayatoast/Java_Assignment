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

    public ArrayList<Features> reloadDataset() {
        ArrayList<Features> fullDataset = new ArrayList<>();
        for (String line : fileProcessor.readFile()) {
            String[] parts = line.split(",");
            fullDataset.add(new Features(parts[0], parts[1], parts[2], parts[3], parts[4]));
        }
        return fullDataset;
    }
}
