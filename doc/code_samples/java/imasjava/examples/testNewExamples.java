package imasjava.examples;
import example004_readDataFromEntry;

public class testNewExamples {
    public static void main(String[] args) throws Exception {
        try {
            
            System.out.println("Running example case");
            example004_readDataFromEntry.readEntireIDS();
            example004_readDataFromEntry.readSlice();
            example004_readDataFromEntry.pratialGet();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}