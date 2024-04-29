import java.io.*;

import imasjava.*;
import imasjava.wrapper.LowLevel;

class TestCoreProfilesGet {
    public static void main(String args[]) {
        // Get username
        String userName = System.getenv("USER");
        
        if (userName == null) {
            System.out.println("PANIC: $USER not found! Exiting...");
            System.exit(1);
        }

        System.out.println(" ============================================= ");
        System.out.println(" pulse 13, run 1 is written by a Java code (put) ");
        System.out.println("          Get the core_profiles IDS             ");
        System.out.println(" ============================================= ");

        // Test the "get" part
        imas.core_profiles ids;
        try {
            String currentDir =  System.getProperty("user.dir");
            String uri = "imas:mdsplus?path=" + currentDir + "/test_db";
            int idx = imas.open(uri, LowLevel.OPEN_PULSE)
            System.out.println("idx for get: " + idx);

            ids = imas.core_profiles.get(idx, "core_profiles");
            System.out.println("ids_properties.homogeneous: " + ids.ids_properties.homogeneous_time);
            System.out.println("ids_properties.comment: " + ids.ids_properties.comment);
            System.out.println("size of profiles_1d: " + ids.profiles_1d.length);

            for (int i = 0; i < ids.profiles_1d.length; i++) {
                System.out.println("rho = " + ids.profiles_1d[i].grid.rho_tor_norm);
                System.out.println("rho = " + ids.profiles_1d[i].ion.length);
                for (int j = 0; j < ids.profiles_1d[i].ion.length; j++) {
                    System.out.println("List of ion masses = " + ids.profiles_1d[i].ion[j].z_ion);
                    System.out.println("Ni for ion j at time i" + ids.profiles_1d[i].ion[j].density);
                }
            }

            System.out.println("Main IDS time : " + ids.time);
            System.out.println("Ip = " + ids.global_quantities.ip);
            imas.close(idx);
        } catch (Exception exc) {
            System.out.println("Error: " + exc);
        }
    }
}



