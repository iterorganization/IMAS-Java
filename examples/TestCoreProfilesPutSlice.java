import java.lang.*;

import imasjava.*;
import imasjava.wrapper.LowLevel;

class TestCoreProfilesPutSlice {
    public static void main(String args[]) {
        // Define a generic vector and its time base
        double time[] = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};

        // Get username
        String userName = System.getenv("USER");
        if (userName == null) {
            System.out.println("PANIC: $USER not found! Exiting...");
            System.exit(1);
        }

        System.out.println(" =================================================== ");
        System.out.println(" pulse 13, run 2 is written by a Java code (putSlice) ");
        System.out.println("          PutSlice the core_profiles IDS             ");
        System.out.println(" =================================================== ");

        // Test the "putSlice" part
        try {
            String currentDir = System.getProperty("user.dir");
            String uri = "imas:mdsplus?path=" + currentDir + "/test_db_TestCoreProfiles";
            int idx = imas.open(uri, LowLevel.FORCE_CREATE_PULSE);
            System.out.println("idx: " + idx);

            // allocate the core_profiles fields
            imas.core_profiles ids = new imas.core_profiles();
            ids.ids_properties.homogeneous_time = 1;
            ids.ids_properties.comment = "This is a test ids put using put_slice.";
            ids.time = new Vect1DDouble(10);
            ids.global_quantities.ip = new Vect1DDouble(10);
            System.out.println("Completed allocation of core_profiles");

            // fill time and global_quantities fields
            for (int i = 0; i < time.length; i++) {
                ids.time.setElementAt(i, time[i]);
                ids.global_quantities.ip.setElementAt(i, time[i] * 3);

                if (i == 0)
                    ids.put(idx, "core_profiles", ids);
                else
                    ids.putSlice(idx, "core_profiles", ids);
            }
            System.out.println("Completed putSlice part");
            imas.close(idx);
        } catch (Exception exc) {
            System.out.println("Error: " + exc);
        }
    }
}

