import java.io.*;

import imasjava.*;
import imasjava.wrapper.LowLevel;

class TestCoreProfilesGetSlice {
    public static void main(String args[]) {
        // Get username
        String userName = System.getenv("USER");
        
        if (userName == null) {
            System.out.println("PANIC: $USER not found! Exiting...");
            System.exit(1);
        }

        System.out.println(" =================================================== ");
        System.out.println(" pulse 13, run 2 is written by a Java code (getSlice) ");
        System.out.println("          GetSlice the core_profiles IDS             ");
        System.out.println(" =================================================== ");

        // Test the "getSlice" part
        imas.core_profiles ids;
        try {
            String currentDir =  System.getProperty("user.dir");
            String uri = "imas:mdsplus?path=" + currentDir + "/test_db_TestCoreProfiles";
            int idx = imas.open(uri, LowLevel.OPEN_PULSE);
            double time = 4.0;

            ids = imas.core_profiles.getSlice(idx, "core_profiles", time, 1);
            System.out.println("time = " + time);
            System.out.println("ids_properties.homogeneous: " + ids.ids_properties.homogeneous_time);
            System.out.println("ids_properties.comment:     " + ids.ids_properties.comment);
	    if (ids.profiles_1d != null) {
		System.out.println("size of profiles_1d:        " + ids.profiles_1d.length);
		System.out.println("profiles_1d.time :          " + ids.profiles_1d[0].time);
	    }
            System.out.println("main IDS time :             " + ids.time);
            System.out.println("IP            :             " + ids.global_quantities.ip);
            imas.close(idx);
        } catch (Exception exc) {
            System.out.println("Error: " + exc);
        }

    }
}



