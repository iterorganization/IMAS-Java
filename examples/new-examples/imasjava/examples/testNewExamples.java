package imasjava.examples;

public class testNewExamples {
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Running example case");
            // example002_fillingDataInIDS example002 = new example002_fillingDataInIDS();
            // example002.creatingCompletlyNewIDS();
            // example002.defaultValuesAndAosOperations();
            // example002.copyingAndValidatingIDS();

            example003_writeDataIntoEntry example003 = new example003_writeDataIntoEntry();
            example003.putEntireIDS();
            example003.putSlice();
            example003.putIntoNonDefaultOccurrence();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}