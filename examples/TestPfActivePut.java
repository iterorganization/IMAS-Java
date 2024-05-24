import imasjava.*;
import imasjava.wrapper.LowLevel;

import java.io.*;

class TestPfActivePut {
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

        // Test the "put" part
        try {
            int number = 10;
            
            String currentDir = System.getProperty("user.dir");
            String uri = "imas:mdsplus?path=" + currentDir + "/test_db_TestPfActive";
            int idx = imas.open(uri, LowLevel.FORCE_CREATE_PULSE);
            System.out.println("idx: " + idx);

            imas.pf_active pf_active = new imas.pf_active();
            pf_active.ids_properties.comment = "Debugging phase";
            pf_active.ids_properties.homogeneous_time = 0;
            pf_active.coil = new imas.pf_active.coilClass[2];
            for (int i = 0; i < 2; i++) {
                pf_active.coil[i] = new imas.pf_active.coilClass();
            }
            System.out.println("Completed allocation of pf_active");

            pf_active.coil[0].name = "SAMPLE 1";
            pf_active.coil[1].name = "SAMPLE 2";
            pf_active.coil[0].current.data = new Vect1DDouble(number);
            pf_active.coil[0].current.time = new Vect1DDouble(number);
            for (int i = 0; i < number; i++) {
                pf_active.coil[0].current.data.setElementAt(i, 2 * i);
                pf_active.coil[0].current.time.setElementAt(i, i);
            }

            number = number + 2;
            pf_active.coil[1].current.data = new Vect1DDouble(number);
            pf_active.coil[1].current.time = new Vect1DDouble(number);
            for (int i = 0; i < number; i++) {
                pf_active.coil[1].current.data.setElementAt(i, 2 * i + 10);
                pf_active.coil[1].current.time.setElementAt(i, i + number);
            }
            System.out.println("Completed filling of pf_active");

            pf_active.coil[0].voltage.data = new Vect1DDouble(number);
            pf_active.coil[1].voltage.data = new Vect1DDouble(number);
            pf_active.coil[0].voltage.time = new Vect1DDouble(number);
            pf_active.coil[1].voltage.time = new Vect1DDouble(number);
            System.out.println("Start putting pf_activate");
            pf_active.put(idx, "pf_active", pf_active);
            System.out.println("End putting pf_activate");
            imas.close(idx);
        } catch (Exception exc) {
            System.out.println("Error: " + exc);
        }
    }
}