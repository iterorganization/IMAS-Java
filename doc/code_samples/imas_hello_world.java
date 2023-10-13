// Import the Access Layer
import imasjava.imas;

public class imas_hello_world {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println("Access Layer version info:");
        System.out.println("  Low level version: " + imas.get_al_version());
        System.out.println("  Data Dictionary version: " + imas.al_dd_version);
        System.out.println("  Java HLI version: " + imas.al_java_version);
    }
}
