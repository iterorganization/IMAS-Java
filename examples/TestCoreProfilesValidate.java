import java.io.*;

import imasjava.*;
import imasjava.wrapper.LowLevel;

class TestCoreProfilesValidate {
    public static void main(String args[]) {
        imas.core_profiles ids = new imas.core_profiles();

        ids.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;
        System.out.println("### Testing simplest case...");
        try {
            ids.validate();
            System.out.println("Error, expected ValidationException.");
            System.exit(1);
        } catch (Exception e) {
        }
        System.out.println("");

        ids.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HETEROGENEOUS;
        ids.profiles_1d = new imas.core_profiles.profiles_1dClass[5];
        for (int i = 0; i < 5; i++) {
            ids.profiles_1d[i] = new imas.core_profiles.profiles_1dClass();
        }
        System.out.println("### Testing HETEROGENEOUS scalar time coordinate issue");
        try {
            ids.validate();
            System.out.println("Error, expected ValidationException.");
            System.exit(1);
        } catch (Exception e) {
        }
        System.out.println("");

        System.out.println("### Testing HETEROGENEOUS scalar time coordinate issue fixed");
        for (int i = 0; i < 5; i++) {
            ids.profiles_1d[i].time = 0.1 * i;
        }
        try {
            ids.validate();
        } catch (Exception e) {
            System.out.println("Error: unexpected ValidationException: " + e.getMessage());
            System.exit(1);
        }
        System.out.println("");
    }
}



