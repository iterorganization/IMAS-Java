import imasjava.*;

class TestVersion {
    public static void main(String args[]) {
        System.out.println("Lowlevel version: " + imas.get_al_version());
        System.out.println("Java HLI version: " + imas.al_java_version);
        System.out.println("Data Dictionary version: " + imas.al_dd_version);
   }
}
