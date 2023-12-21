import java.io.*;

import imasjava.*;
import imasjava.wrapper.LowLevel;

class TestCoreProfilesValidate {
    public static void main(String args[]) {
        imas.core_profiles ids = new imas.core_profiles();
        String expected;

        ids.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;
        System.out.println("### Testing simplest case...");
        expected = "With the time mode 'HOMOGENEOUS', the time array must be allocated and not be empty.";
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        ids.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HETEROGENEOUS;
        ids.profiles_1d = new imas.core_profiles.profiles_1dClass[5];
        for (int i = 0; i < 5; i++) {
            ids.profiles_1d[i] = new imas.core_profiles.profiles_1dClass();
        }
        System.out.println("### Testing HETEROGENEOUS scalar time coordinate issue");
        expected = "Time coordinate of profiles_1d wrong. profiles_1d[itime].time is invalid (-9.0E40) .";
	CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HETEROGENEOUS scalar time coordinate issue fixed");
        expected = "";
        for (int i = 0; i < 5; i++) {
            ids.profiles_1d[i].time = 0.1*i;
        }
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");
    }
}



