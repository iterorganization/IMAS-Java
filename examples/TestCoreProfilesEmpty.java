import java.io.*;

import imasjava.*;

class TestCoreProfilesEmpty {
    public static void main(String args[]) {

        double[] time = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};

        // Get username
        String userName = System.getenv("USER");
        if (userName == null) {
            System.out.println("PANIC: $USER not found! Exiting...");
            System.exit(1);
        }

        // Get empty IDS
        try {
            int idx = imas.createEnv(13, 1, userName, "test", "3");

            imas.core_profiles ids = imas.core_profiles.get(idx, "core_profiles");
            System.out.println("Is defined: " + ids.isDefined());

            imas.close(idx);
        } catch (Exception exc) {
            System.out.println("Error: " + exc);
        }

        // Store IDS with a basic data, get it from the pulse file, and check
        // whether it is empty
        try {
            int idx = imas.createEnv(13, 1, userName, "test", "3");
            
            imas.core_profiles ids = new imas.core_profiles();
            
            ids.ids_properties.homogeneous_time = 1;
            ids.ids_properties.comment = "This is a test ids. User: " + userName;
            ids.time = new Vect1DDouble(time);

            ids.put(idx, "core_profiles", ids);
            imas.close(idx);

            idx = imas.openEnv(13, 1, userName, "test", "3");
            ids = imas.core_profiles.get(idx, "core_profiles");
            System.out.println("Is defined: " + ids.isDefined());
            
            imas.close(idx); 

        } catch (Exception exc) {
            System.out.println("Error: " + exc);
        }

	
    }
}



