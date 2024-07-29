import java.lang.*;

import imasjava.*;
import imasjava.wrapper.LowLevel;

class TestCoreProfilesPut {
    public static void main(String args[]) {
        // Define a generic vector and its time base
        double[] time = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};
        Vect1DDouble vect1DDouble_1 = new Vect1DDouble(time);

        // Get username
        String userName = System.getenv("USER");
        if (userName == null) {
            System.out.println("PANIC: $USER not found! Exiting...");
            System.exit(1);
        }

        System.out.println(" ============================================= ");
        System.out.println(" pulse 13, run 1 is written by a Java code (put) ");
        System.out.println("          Put the core_profiles IDS             ");
        System.out.println(" ============================================= ");

        // Test the "put" part
        try {
            String currentDir = System.getProperty("user.dir");
            String uri = "imas:hdf5?path=" + currentDir + "/test_db_TestCoreProfiles";
            int idx = imas.open(uri, LowLevel.FORCE_CREATE_PULSE);
            System.out.println("idx: " + idx);

            // allocate the ids fields
            imas.core_profiles ids = new imas.core_profiles();
            System.out.println("Size: " + time.length);
            ids.profiles_1d = new imas.core_profiles.profiles_1dClass[time.length];
            System.out.println("Completed allocation of profiles_1d");

            // fill profiles_1d fields
            for (int i = 0; i < time.length; i++) {
                ids.profiles_1d[i] = new imas.core_profiles.profiles_1dClass();
                ids.profiles_1d[i].grid.rho_tor_norm = new Vect1DDouble(i + 1);
                for (int j = 0; j <= i; j++) {
                    ids.profiles_1d[i].grid.rho_tor_norm.setElementAt(j, vect1DDouble_1.getElementAt(j));
                }
                ids.profiles_1d[i].time = time[i];
                System.out.println("rho" + ids.profiles_1d[i].grid.rho_tor_norm.toString());

                ids.profiles_1d[i].ion = new imas.core_profiles.profiles_1dClass.ionClass[i + 1];
                for (int j = 0; j < ids.profiles_1d[i].ion.length; j++) {
                    ids.profiles_1d[i].ion[j] = new imas.core_profiles.profiles_1dClass.ionClass();
                    ids.profiles_1d[i].ion[j].z_ion = time[i];
                    ids.profiles_1d[i].ion[j].density = new Vect1DDouble(i + 1);
                }
            }
            System.out.println("Completed filling of profiles_1d fields");

            // Fill the ids fields with data
            ids.ids_properties.homogeneous_time = 1;
            ids.ids_properties.comment = "This is a test ids. User: " + userName;
            ids.time = new Vect1DDouble(time);

            System.out.println("Start putting the ids IDS");
            ids.put(idx, "core_profiles", ids);
            imas.close(idx);
            System.out.println("End putting the code_profiles IDS");
        } catch (Exception exc) {
            System.out.println("Error: " + exc);
            System.exit(1);
        }
    }
}
