package imasjava.examples;
import example001_OpenDatabase;

public class TestNewExamples {
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Running OpenDatabase example case");
            example001_OpenDatabase.createDBEntryLegacy();
            example001_OpenDatabase.openDBEntryURI();
            example001_OpenDatabase.createDBEntryURIwithPath();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
