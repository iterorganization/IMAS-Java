package imasjava.examples;
import imasjava.examples.Example001_OpenDatabase;
import imasjava.examples.Example002_fillingDataInIDS;
import imasjava.examples.Example003_writeDataIntoEntry;
import imasjava.examples.Example004_readDataFromEntry;

public class TestNewExamples {
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Running OpenDatabase examples");
            Example001_OpenDatabase.createDBEntryLegacy();
            Example001_OpenDatabase.openDBEntryURI();
            Example001_OpenDatabase.createDBEntryURIwithPath();
            
            System.out.println("Running IDS filling examples");
            Example002_fillingDataInIDS.creatingCompletlyNewIDS();
            Example002_fillingDataInIDS.defaultValuesAndAosOperations();
            Example002_fillingDataInIDS.copyingAndValidatingIDS();

            System.out.println("Running write examples");
            Example003_writeDataIntoEntry.putEntireIDS();
            Example003_writeDataIntoEntry.putSlice();
            Example003_writeDataIntoEntry.putIntoNonDefaultOccurrence();

	    System.out.println("Running read examples");
            Example004_readDataFromEntry.readEntireIDS();
            Example004_readDataFromEntry.readSlice();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
