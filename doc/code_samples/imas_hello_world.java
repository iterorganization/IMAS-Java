// Import the Access Layer
import imasjava.imas;

public class imas_hello_world {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println("Using access layer version: " + System.getenv("AL_VERSION"));
    }
}
