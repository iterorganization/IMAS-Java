package imasjava.examples;
import example004_readDataFromEntry;

public class testNewExamples {
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Running example case");

            example004_readDataFromEntry example004 = new example004_readDataFromEntry();
            example004.readEntireIDS();
            example004.readSlice();
            example004.pratialGet();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}