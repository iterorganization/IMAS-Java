import java.lang.*;

import imasjava.*;
import imasjava.ids.*;
import imasjava.wrapper.*;

class TestCoreProfilesSerialize {
    public static void main(String args[]) {
        // Define a generic vector and its time base
        double[] time = { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0 };
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

        // Test the "serialize" part
        try {
            int idx = imas.createEnv(13, 1, userName, "test", "3");

            // allocate the ids fields
            imas.core_profiles ids1 = new imas.core_profiles();
            imas.core_profiles ids2 = new imas.core_profiles();
            ;
            ids1.profiles_1d = new imas.core_profiles.profiles_1dClass[time.length];
            System.out.println("Completed allocation of profiles_1d");

            // fill profiles_1d fields
            for (int i = 0; i < time.length; i++) {
                ids1.profiles_1d[i] = new imas.core_profiles.profiles_1dClass();
                ids1.profiles_1d[i].grid.rho_tor_norm = new Vect1DDouble(i + 1);
                for (int j = 0; j <= i; j++) {
                    ids1.profiles_1d[i].grid.rho_tor_norm.setElementAt(j, vect1DDouble_1.getElementAt(j));
                }
                ids1.profiles_1d[i].time = time[i];
                System.out.println("rho" + ids1.profiles_1d[i].grid.rho_tor_norm.toString());

                ids1.profiles_1d[i].ion = new imas.core_profiles.profiles_1dClass.ionClass[i + 1];
                for (int j = 0; j < ids1.profiles_1d[i].ion.length; j++) {
                    ids1.profiles_1d[i].ion[j] = new imas.core_profiles.profiles_1dClass.ionClass();
                    ids1.profiles_1d[i].ion[j].z_ion = time[i];
                    ids1.profiles_1d[i].ion[j].density = new Vect1DDouble(i + 1);
                }
            }
            System.out.println("Completed filling of profiles_1d fields");

            // Fill the ids1 fields with data
            ids1.ids_properties.homogeneous_time = 1;
            ids1.ids_properties.comment = "This is a test ids. User: " + userName;
            ids1.time = new Vect1DDouble(time);

            byte[] data = ids1.serialize(LowLevel.ASCII_SERIALIZER_PROTOCOL);
            ids2.deserialize(data);
            boolean commentCompare = ids1.ids_properties.comment.equals(ids2.ids_properties.comment);
            boolean homogeneous_timeCompare = ids1.ids_properties.homogeneous_time == ids2.ids_properties.homogeneous_time;
            boolean timeCompare = true;
            for (int i = 0; i < time.length; i++) {
                if (ids1.profiles_1d[i].time != ids2.profiles_1d[i].time)
                    timeCompare = false;
            }

            for (int i = 0; i < time.length; i++) {

                System.out.println("rho" + ids2.profiles_1d[i].grid.rho_tor_norm.toString());
            }
            if ((commentCompare & homogeneous_timeCompare & timeCompare) == false)
                System.out.println("Serialization failed");
            else
                System.out.println("Successful serialization");
            imas.close(idx);
        } catch (Exception exc) {
            System.out.println("Error: " + exc);
        }
    }
}