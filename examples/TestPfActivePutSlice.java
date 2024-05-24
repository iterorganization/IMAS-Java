import imasjava.*;
import imasjava.wrapper.LowLevel;

import java.io.*;

class TestPfActivePutSlice {
    public static void main(String args[]) {
        // get username
        String userName = System.getenv("USER");
        if (userName == null) {
            System.out.println("PANIC: $USER not found! Exiting...");
            System.exit(1);
        }

        System.out.println(" ================================================== ");
        System.out.println(" Pulse 22, run 1 is written by a Java code (putSlice) ");
        System.out.println(" ================================================== ");

        // Test the "PutSlice" part
        try {
            int number = 1;
            String currentDir = System.getProperty("user.dir");
            String uri = "imas:mdsplus?path=" + currentDir + "/test_db_TestPfActive";
            int idx = imas.open(uri, LowLevel.FORCE_CREATE_PULSE);
            System.out.println("idx: " + idx);

            imas.pf_active pf_active = new imas.pf_active();
            pf_active.ids_properties.comment = "Test of put slice";
            pf_active.ids_properties.homogeneous_time = 1;
            pf_active.coil = new imas.pf_active.coilClass[2];
            System.out.println("Completed allocation of coil");

            for (int i = 0; i < 2; i++) {
                pf_active.coil[i] = new imas.pf_active.coilClass();
            }
            pf_active.coil[0].name = "Coil 1";
            pf_active.coil[1].name = "Coil 2";
            pf_active.coil[0].current.data = new Vect1DDouble(number);
            pf_active.coil[1].current.data = new Vect1DDouble(number);
            pf_active.time = new Vect1DDouble(number);

            int numberofSlice = 7;
            for (int i = 0; i < numberofSlice; i++) {
                pf_active.coil[0].current.data.setElementAt(0, 2 * i);
                pf_active.coil[1].current.data.setElementAt(0, 2 * i + 10);
                pf_active.time.setElementAt(0, i);
                if (i == 0)
                    pf_active.put(idx, "pf_active", pf_active);
                else
                    pf_active.putSlice(idx, "pf_active", pf_active);
            }
            System.out.println("Completed filling of fields");
            System.out.println("Completed putSlice part");
            imas.close(idx);
        } catch (Exception exc) {
            System.out.println("Error: " + exc);
        }
    }
}