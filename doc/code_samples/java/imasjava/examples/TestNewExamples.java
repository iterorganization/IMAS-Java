package imasjava.examples;
import Example002_fillingDataInIDS;

public class testNewExamples {
    public static void main(String[] args) throws Exception {
        try {
            
            System.out.println("Running example case");
            Example002_fillingDataInIDS.creatingCompletlyNewIDS();
            Example002_fillingDataInIDS.defaultValuesAndAosOperations();
            Example002_fillingDataInIDS.copyingAndValidatingIDS();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}