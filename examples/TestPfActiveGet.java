import imasjava.*;
import imasjava.wrapper.LowLevel;

import java.io.*;

class TestPfActiveGet {
    public static void main(String args[]) {
        // get username
        String userName = System.getenv("USER");
        if (userName == null) {
            System.out.println("PANIC: $USER not found! Exiting...");
            System.exit(1);
        }

        System.out.println(" ============================================= ");
        System.out.println(" pulse 12, run 2 is written by a Java code (put) ");
        System.out.println(" ============================================= ");

        // Test the "get" part
        imas.pf_active pf_actives;
        try {
            String currentDir = System.getProperty("user.dir");
            String uri = "imas:mdsplus?path=" + currentDir + "/test_db_TestPfActive";
            int idx = imas.open(uri, LowLevel.OPEN_PULSE);
            System.out.println("idx for get: " + idx);

            pf_actives = imas.pf_active.get(idx, "pf_active");
            System.out.println("pf_actives.ids_properties.comment: " + pf_actives.ids_properties.comment);
            System.out.println("pf_actives.ids_properties.homogeneous_time: " + pf_actives.ids_properties.homogeneous_time);
            System.out.println("number of coil: " + pf_actives.coil.length);

            for (int i = 0; i < pf_actives.coil.length; i++) {
                System.out.println("name of coil[" + i + "]: " + pf_actives.coil[i].name);
                System.out.println("voltage: " + pf_actives.coil[i].current.data);
                System.out.println("Time   : " + pf_actives.coil[i].current.time);
            }
            imas.close(idx);
        } catch (Exception exc) {
            System.out.println("Error: " + exc);
        }

    }
}