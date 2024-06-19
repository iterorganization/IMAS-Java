package imasjava.examples;
import example002_fillingDataInIDS;

public class testNewExamples {
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Running example case");
            example002_fillingDataInIDS x = new example002_fillingDataInIDS();
            x.creatingCompletlyNewIDS();
            x.defaultValuesAndAosOperations();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}