import java.io.IOException;

interface FileHandler {
    void saveData(String filename) throws IOException;
    void loadData(String filename) throws IOException, ClassNotFoundException;
    void exportReport(String filename) throws IOException;
}
