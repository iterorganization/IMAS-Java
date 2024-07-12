package imasjava.examples;
import imasjava.examples.Example001_OpenDatabase;

public class TestNewExamples {
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Running OpenDatabase example case");
            Example001_OpenDatabase.createDBEntryLegacy();
            Example001_OpenDatabase.openDBEntryURI();
            Example001_OpenDatabase.createDBEntryURIwithPath();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
