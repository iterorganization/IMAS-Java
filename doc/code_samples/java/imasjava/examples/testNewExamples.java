package imasjava.examples;

public class testNewExamples {
    public static void main(String[] args) throws Exception {
        try {

            System.out.println("Running example case");
            example003_writeDataIntoEntry.putEntireIDS();
            example003_writeDataIntoEntry.putSlice();
            example003_writeDataIntoEntry.putIntoNonDefaultOccurrence();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}