package imasjava.examples;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.io.*;
import imasjava.*;
import imasjava.wrapper.LowLevel;

public class example004_readDataFromEntry{

    // This example focuses on reading whole IDS from entry.
    // We are storing and reading back an IDS - equilibrium. Data are stored inside MDS+ file.
    public static void readEntireIDS() throws Exception {

        try {
            // NOTE: this block of code uses 'FORCE_CREATE_PULSE' mode in order to create example data
            int dataEntry = imas.open("imas:mdsplus?path=./testdb_mdsplus", LowLevel.FORCE_CREATE_PULSE);

            // fill IDS with example data
            imas.equilibrium emptyEquilibrium = new imas.equilibrium();
            emptyEquilibrium.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;
            double[] timeArray = {1.0, 2.0, 3.0};
            double[] b0Array = {1.0, 2.0, 3.0};
            emptyEquilibrium.time = new Vect1DDouble(timeArray);
            emptyEquilibrium.vacuum_toroidal_field.b0 = new Vect1DDouble(timeArray);

            emptyEquilibrium.put(dataEntry, "equilibrium", emptyEquilibrium);
            imas.close(dataEntry);

            // NOTE: this block of code uses 'OPEN_PULSE' mode in order to read example data
            int dataEntry1 = imas.open("imas:mdsplus?path=./testdb_mdsplus", LowLevel.OPEN_PULSE);
            imas.equilibrium savedEquilibrium = imas.equilibrium.get(dataEntry1, "equilibrium");

            //  here you can access content of the IDS equilibrium
            //  someVariable = savedEquilibrium.some_element
            //  savedEquilibrium.some_element = ...
            //  etc.

            // one may check if IDS was filled with data using <ids>.isDefined() method
            System.out.println("is equilibrium defined?: " +  savedEquilibrium.isDefined());
            imas.close(dataEntry1);

        } catch (Exception e) {
            System.out.println("Following error occured:\n" + e.getMessage());
            throw e;
        }
    
    }

    // This example focuses on reading IDS slices from entry
    // We are storing and reading back an IDS - summary. Data are stored inside MDS+ file.
    public static void readSlice() throws Exception {

        try {
            
            // NOTE: this block of code uses 'FORCE_CREATE_PULSE' mode in order to create example data
            int dataEntry = imas.open("imas:mdsplus?path=./testdb_mdsplus", LowLevel.FORCE_CREATE_PULSE);

            // fill IDS with example data
            imas.summary emptySummary = new imas.summary();
            emptySummary.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;
            
            double[] ipValueArray = {10.0, 20.0, 30.0};
            emptySummary.global_quantities.ip.value = new Vect1DDouble(ipValueArray);

            emptySummary.heating_current_drive.nbi = new imas.summary.heating_current_driveClass.nbiClass[1];
            emptySummary.heating_current_drive.nbi[0] = new imas.summary.heating_current_driveClass.nbiClass();

            double[][] beamValueArray = new double[3][3];
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    double doubleI = (double) i;
                    double doubleJ = (double) j;

                    beamValueArray[i][j] = doubleI * doubleJ * 100;
                }
            }
            emptySummary.heating_current_drive.nbi[0].beam_current_fraction.value = new Vect2DDouble(3,3);
            emptySummary.heating_current_drive.nbi[0].beam_current_fraction.value.set(beamValueArray);

            double[] timeArray = {1.0, 2.0, 3.0};
            emptySummary.time = new Vect1DDouble(timeArray);

            emptySummary.put(dataEntry, "summary", emptySummary);
            imas.close(dataEntry);

            // NOTE: this block of code uses 'OPEN_PULSE' mode in order to read example data
            int dataEntry1 = imas.open("imas:mdsplus?path=./testdb_mdsplus", LowLevel.OPEN_PULSE);
            // Access Layer API shares 3 different methods of interpolating values from DBEntry
            // LowLevel.PREVIOUS_INTERP
            // LowLevel.CLOSEST_INTERP
            // LowLevel.LINEAR_INTERP

            // this part of code presents PREVIOUS_INTERP. It is interpolation method that returns the previous time slice if the requested time does not exactly exist in the original IDS
            // if requested time is outside of time array, first, or last slice will be returned respectively
            imas.summary savedSummaryPreviousInterp = imas.summary.getSlice(dataEntry1, "summary", 1.75 , LowLevel.PREVIOUS_INTERP);        
            // previous time value for 1.75 is 1.0
            // summary/global_quantities/ip/value and time=1 is 10.0
            System.out.println("\nsummary/global_quantities/ip/value for PREVIOUS_INTERP and time=1: " + savedSummaryPreviousInterp.global_quantities.ip.value);

            // this part of code presents CLOSEST_INTERP. It is interpolation method that returns the closest time slice in the original IDS
            // if requested time is equally spaced between two time slices, slice with higher index will be returned
            imas.summary savedSummaryClosestInterp = imas.summary.getSlice(dataEntry1, "summary", 1.75 , LowLevel.CLOSEST_INTERP);        
            // closest time value to 1.75 is 2.0
            // value for summary/global_quantities/ip/value and time=2 is 11.0
            System.out.println("summary/global_quantities/ip/value for CLOSEST_INTERP and time=1: " + savedSummaryClosestInterp.global_quantities.ip.value);

            // this part of code presents LINEAR_INTERP. It is interpolation method that returns a linear interpolation between the existing slices before and after the requested time.
            // NOTE: The linear interpolation will be successful only if, between the two time slices of an interpolated dynamic array of structure,
            // the same leaves are populated and they have the same size.
            // Otherwise DBEntry.get_slice() will interpolate all fields with a compatible size and leave others empty.

            // NOTE: If time requested is smaller than <ids>.time[0], first slice will be returned. If requested time exceeds highest time, last slice will be returned.
            imas.summary savedSummaryLinearInterp = imas.summary.getSlice(dataEntry1, "summary", 1.75 , LowLevel.LINEAR_INTERP);        
            // interpolated value for summary/global_quantities/ip/value and time=1.75 is 10.75
            System.out.println("summary/global_quantities/ip/value for LINEAR_INTERP and time=1.75: " + savedSummaryLinearInterp.global_quantities.ip.value);

            imas.close(dataEntry1);

        } catch (Exception e) {
            System.out.println("Following error occured:\n" + e.getMessage());
            throw e;
        }

    }

    public static void pratialGet() throws Exception {

        try {
            System.out.println("\nThe Java interface does not support partial_get");
        } catch (Exception e) {
            System.out.println("Following error occured:\n" + e.getMessage());
            throw e;
        }

    }
}