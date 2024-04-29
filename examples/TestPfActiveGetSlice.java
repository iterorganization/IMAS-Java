import imasjava.*;
import imasjava.wrapper.LowLevel;

import java.io.*;

class TestPfActiveGetSlice {
    public static void main(String args[]) {
        // get username
        String userName = System.getenv("USER");
        if (userName == null) {
            System.out.println("PANIC: $USER not found! Exiting...");
            System.exit(1);
        }

        System.out.println(" ================================================== ");
        System.out.println(" Pulse 22, run 1 is written by a Java code (putslice) ");
        System.out.println(" ================================================== ");

        // Test the "getSlice" part
        imas.pf_active pf_active;
        try {
            String currentDir = System.getProperty("user.dir");
            String uri = "imas:mdsplus?path=" + currentDir + "/test_db";
            int idx = imas.open(uri, LowLevel.OPEN_PULSE);

            System.out.println("idx for getslice: " + idx);

            double time = 4.2;
            pf_active = imas.pf_active.getSlice(idx, "pf_active", time, 2);
            System.out.println("pf_activesget.ids_properties.comment: " + pf_active.ids_properties.comment);
            System.out.println("pf_activesget.ids_properties.homogeneous_time: " + pf_active.ids_properties.homogeneous_time);
            System.out.println("number of coil: " + pf_active.coil.length);

            for (int i = 0; i < pf_active.coil.length; i++) {
                System.out.println("name of coil[" + i + "]: " + pf_active.coil[i].name);
                System.out.println("voltage: " + pf_active.coil[i].current.data);
                System.out.println("Time   : " + pf_active.time);
            }
            imas.close(idx);
        } catch (Exception exc) {
            System.out.println("Error: " + exc);
        }
    }
}