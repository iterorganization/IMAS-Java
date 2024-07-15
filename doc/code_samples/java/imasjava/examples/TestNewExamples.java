package imasjava.examples;
import imasjava.examples.Example001_OpenDatabase;
import imasjava.examples.Example002_fillingDataInIDS;

public class TestNewExamples {
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Running OpenDatabase example case");
            Example001_OpenDatabase.createDBEntryLegacy();
            Example001_OpenDatabase.openDBEntryURI();
            Example001_OpenDatabase.createDBEntryURIwithPath();
            
            System.out.println("Running IDS filling example case");
            Example002_fillingDataInIDS.creatingCompletlyNewIDS();
            Example002_fillingDataInIDS.defaultValuesAndAosOperations();
            Example002_fillingDataInIDS.copyingAndValidatingIDS();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
