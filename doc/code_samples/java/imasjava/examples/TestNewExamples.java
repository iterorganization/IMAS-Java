package imasjava.examples;
import imasjava.examples.Example001_OpenDatabase;
import imasjava.examples.Example002_fillingDataInIDS;
import imasjava.examples.Example003_writeDataIntoEntry;
import imasjava.examples.Example004_readDataFromEntry;

public class TestNewExamples {
    public static void main(String[] args) throws Exception {
        try {
            // Running 001 OpenDatabase example case methods
            System.out.println("\n\n-------------------------------------------------------------------");
            System.out.println("                Running 001 OpenDatabase example case");
            System.out.println("-------------------------------------------------------------------\n\n");

            System.out.println("\n *** Running: Example001_OpenDatabase.createDBEntryLegacy ***");
            Example001_OpenDatabase.createDBEntryLegacy();

            System.out.println("\n *** Running: Example001_OpenDatabase.openDBEntryURI ***");
            Example001_OpenDatabase.openDBEntryURI();

            System.out.println("\n *** Running: Example001_OpenDatabase.createDBEntryURIwithPath ***");
            Example001_OpenDatabase.createDBEntryURIwithPath();

            // Running 002 IDS filling example case methods
            System.out.println("\n\n-------------------------------------------------------------------");
            System.out.println("                Running 002 IDS filling example case");
            System.out.println("-------------------------------------------------------------------\n\n");

            System.out.println("\n *** Running: Example002_fillingDataInIDS.creatingCompletlyNewIDS ***");
            Example002_fillingDataInIDS.creatingCompletlyNewIDS();

            System.out.println("\n *** Running: Example002_fillingDataInIDS.defaultValuesAndAosOperations ***");
            Example002_fillingDataInIDS.defaultValuesAndAosOperations();

            System.out.println("\n *** Running: Example002_fillingDataInIDS.copyingAndValidatingIDS ***");
            Example002_fillingDataInIDS.copyingAndValidatingIDS();

            // Running 003 Putting IDS case methods
            System.out.println("\n\n-------------------------------------------------------------------");
            System.out.println("                Running 003 Putting IDS case");
            System.out.println("-------------------------------------------------------------------\n\n");

            System.out.println("\n *** Running: Example003_writeDataIntoEntry.putEntireIDS ***");
            Example003_writeDataIntoEntry.putEntireIDS();

            System.out.println("\n *** Running: Example003_writeDataIntoEntry.putSlice ***");
            Example003_writeDataIntoEntry.putSlice();

            System.out.println("\n *** Running: Example003_writeDataIntoEntry.putIntoNonDefaultOccurrence ***");
            Example003_writeDataIntoEntry.putIntoNonDefaultOccurrence();

            // Running 004 Reading IDS case methods
            System.out.println("\n\n-------------------------------------------------------------------");
            System.out.println("                Running 004 Reading IDS case");
            System.out.println("-------------------------------------------------------------------\n\n");

            System.out.println("\n *** Running: Example004_readDataFromEntry.readEntireIDS ***");
            Example004_readDataFromEntry.readEntireIDS();

            System.out.println("\n *** Running: Example004_readDataFromEntry.readSlice ***");
            Example004_readDataFromEntry.readSlice();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
