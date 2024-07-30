package imasjava.examples;
import imasjava.*;
import java.util.*;
import imasjava.wrapper.LowLevel;

public class Example003_writeDataIntoEntry {

    // This example focuses on putting IDS into entry and passing IDS validation
    public static void putEntireIDS() throws Exception {
        
        try {
            int dataEntry = imas.open("imas:mdsplus?path=./testdb_mdsplus", LowLevel.FORCE_CREATE_PULSE);
            imas.equilibrium emptyEquilibrium = new imas.equilibrium();

            // set mandatory field of equilibrium IDS
            emptyEquilibrium.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;

            // when ids_properties.homogeneous_time is set to IDS_TIME_MODE_HOMOGENEOUS,
            // all time-dependent fields values correspond to <ids>.time vector.
            double[] timeArray = {1.0, 2.0, 3.0};
            emptyEquilibrium.time = new Vect1DDouble(timeArray);

            // intentional error when entering data. equilibrium/vacuum_toroidal_field/b0 should have the same size as equilibrium/time
            double[] incorrectVacuumToroidalField_b0Size = {1.0};
            emptyEquilibrium.vacuum_toroidal_field.b0 = new Vect1DDouble(incorrectVacuumToroidalField_b0Size);

            // NOTE: putting ids into entry will trigger validate function.
            // IDS fields types and dimensions will be checked
            try {
                emptyEquilibrium.put(dataEntry, "equilibrium", emptyEquilibrium);
            } catch (Exception e) {
                System.out.println("Caught exception (raised intentionally): " + e.getMessage());
            }

            // to fix it we need to set proper size of that field in IDS
            double[] correctVacuumToroidalField_b0Size = {1.0, 2.0, 3.0};
            emptyEquilibrium.vacuum_toroidal_field.b0 = new Vect1DDouble(correctVacuumToroidalField_b0Size);
            emptyEquilibrium.put(dataEntry, "equilibrium", emptyEquilibrium);

            imas.close(dataEntry);
            // NOTE: some IDS fields are put automatically by Access Layer. Examples of this type of fields are:
            // - <ids>/ids_properties/version_put/data_dictionary
            // - <ids>/ids_properties/version_put/access_layer
            // - <ids>/ids_properties/version_put/access_layer_language

            // IDSs can be printed this way
            try {
                int dataEntry1 = imas.open("imas:mdsplus?path=./testdb_mdsplus", LowLevel.OPEN_PULSE);
                imas.equilibrium savedEquilibrium = imas.equilibrium.get(dataEntry1, "equilibrium");
                System.out.println("Saved equilibrium/vacuum_toroidal_field/b0 field: " + savedEquilibrium.vacuum_toroidal_field.b0);
                imas.close(dataEntry1);
            } catch (Exception e) {
                System.out.println("Caught exception (raised intentionally): " + e.getMessage());
                throw e;
            }

        } catch (Exception e) {
            System.err.println("Following exception occurred\n" + e.getMessage());
            throw e;
        }
    } 

    // This example focuses on putting multiple slices of IDS into entry
    public static void putSlice() throws Exception {
      
        try { 
            int dataEntry = imas.open("imas:mdsplus?path=./testdb_mdsplus", LowLevel.FORCE_CREATE_PULSE);
            imas.summary emptySummary = new imas.summary();

            // set mandatory field
            emptySummary.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;

            // summary/heating_current_drive/nbi is an array of structures, so we need to resize it to use it.
            int arraySize = 1;
            emptySummary.heating_current_drive.nbi = new imas.summary.heating_current_driveClass.nbiClass[arraySize];
            emptySummary.heating_current_drive.nbi[0] = new imas.summary.heating_current_driveClass.nbiClass();

            emptySummary.stationary_phase_flag.value = new Vect1DInt(arraySize);
            emptySummary.time = new Vect1DDouble(arraySize);
            emptySummary.heating_current_drive.nbi[0].beam_current_fraction.value = new Vect2DDouble(3,1);

            for (int timeSlice = 0; timeSlice < 5; timeSlice++) {
                
                // NOTE: time-independent data is being put only if it is empty in entry
                // in this case summary/stationary_phase_flag/source will be put only at first iteration.
                // suggested way to fill this type of fields is to do this outside loop
                emptySummary.stationary_phase_flag.source = "Name saved by example code iteration: " + timeSlice;

                // fill example data
                emptySummary.stationary_phase_flag.value.setElementAt(0, 10*timeSlice);

                // fill 2D data
                double timeSliceDouble = (double) timeSlice;             
                double[][] value2DArray = new double[3][1];
                for (int i = 0; i<3; i++){
                    value2DArray[i][0] = 100 * timeSliceDouble;
                }

                emptySummary.heating_current_drive.nbi[0].beam_current_fraction.value.set(value2DArray);

                // NOTE: it is user's responsibility to organize <ids>/time field in ascending manner
                // breaking this rule will make get_slice() command to fail
                // slice time is being appended to <ids>/time stored in entry   
                emptySummary.time.setElementAt(0, timeSliceDouble);
                emptySummary.putSlice(dataEntry, "summary", emptySummary);
            }

            // multiple slices can be put into entry as well
            emptySummary.stationary_phase_flag.value = new Vect1DInt(3);
            double[] valueArray = {11.0, 12.0, 13.0};
            Vect1DDouble vectValueArray = new Vect1DDouble(valueArray);
            for (int i = 0; i < 3; i++){
                int intValue = (int) vectValueArray.getElementAt(i);
                emptySummary.stationary_phase_flag.value.setElementAt(i, intValue);
            }

            emptySummary.heating_current_drive.nbi[0].beam_current_fraction.value = new Vect2DDouble(3,3);
            double[][] value2DArray = new double[3][3];
            for ( int i = 0; i < 3; i++){
                value2DArray[i] = new double[]{1000.0, 2000.0, 3000.0};
            }
            emptySummary.heating_current_drive.nbi[0].beam_current_fraction.value.set(value2DArray);



            emptySummary.time = new Vect1DDouble(3);
            double[] timeArray = {50.0, 60.0, 70.0};
            Vect1DDouble vectTimeArray = new Vect1DDouble(timeArray);
            for (int i =0; i < 3; i++) {
                emptySummary.time.setElementAt(i, vectTimeArray.getElementAt(i));
            }
            
            emptySummary.putSlice(dataEntry, "summary", emptySummary);
            imas.close(dataEntry);

            // printing all saved slices in IDS.
            try {
                int dataEntry1 = imas.open("imas:mdsplus?path=./testdb_mdsplus", LowLevel.OPEN_PULSE);
                imas.summary savedSummary = imas.summary.get(dataEntry1, "summary");
                System.out.println("\nSaved summary/time field: \n" + savedSummary.time);
                System.out.println("Saved summary/heating_current_drive/nbi[0]/beam_current_fraction/value field: \n" + savedSummary.heating_current_drive.nbi[0].beam_current_fraction.value);
                System.out.println("Saved summary/stationary_phase_flag/value field: \n" + savedSummary.stationary_phase_flag.value);

                imas.close(dataEntry1);
            } catch (Exception e) {
                System.out.println("Caught exception (raised intentionally): " + e.getMessage());
                throw e;
            }               
        } catch (Exception e) {
            System.err.println("Following exception occurred\n" + e.getMessage());
            throw e;
        }
    }

    // This example focuses on putting IDS into another occurrence
    public static void putIntoNonDefaultOccurrence() throws Exception {
        
        try { 
            int dataEntry = imas.open("imas:mdsplus?path=./testdb_mdsplus", LowLevel.FORCE_CREATE_PULSE);

            // default occurrence for get/put is 0
            // list of available occurrences can be found inside Data Dictionary documentation.
            imas.equilibrium emptyEquilibrium = new imas.equilibrium();

            // set mandatory field
            emptyEquilibrium.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;

            // when ids_properties.homogeneous_time is set to IDS_TIME_MODE_HOMOGENEOUS,
            // all time-dependent fields values correspond to <ids>.time vector.
            double[] timeArray = {1.0, 2.0, 3.0};
            emptyEquilibrium.time = new Vect1DDouble(timeArray);

            // in order to print properly all occurrences with content of a given node, we need to put empty IDS to default occurrence
            emptyEquilibrium.put(dataEntry, "equilibrium", emptyEquilibrium);

            // add some comment to IDS
            emptyEquilibrium.ids_properties.comment = "comment from 1st occurrence";

            // put IDS into occurrence 1
            emptyEquilibrium.put(dataEntry, "equilibrium/1", emptyEquilibrium);
        
            // modify comment, so differences between occurrences can be spotted
            emptyEquilibrium.ids_properties.comment = "comment from 2nd occurrence";

            // put IDS into occurrence 2
            emptyEquilibrium.put(dataEntry, "equilibrium/2", emptyEquilibrium);
            imas.close(dataEntry);

            try {
                // now we can print saved comments
                int dataEntry1 = imas.open("imas:mdsplus?path=./testdb_mdsplus", LowLevel.OPEN_PULSE);
                imas.equilibrium savedEquilibriumOccurrence1 = imas.equilibrium.get(dataEntry1, "equilibrium/1");
                imas.equilibrium savedEquilibriumOccurrence2 = imas.equilibrium.get(dataEntry1, "equilibrium/2");
                
                System.out.println("\nSaved equilibrium/ids_properties/comment field: (occurrence 1)\n" + savedEquilibriumOccurrence1.ids_properties.comment);
                System.out.println("\nSaved equilibrium/ids_properties/comment field: (occurrence 2)\n" + savedEquilibriumOccurrence2.ids_properties.comment);
                imas.close(dataEntry1);

            } catch (Exception e) {
            System.err.println("Following exception occurred\n" + e.getMessage());
                throw e; 
            }
            
            try {
                // occurrences can be listed with listAllOccurrences() function
                // listAllOccurrences also returns content of IDS field pointed by nodePath argument    
                // nodePath in Java HLI works with STR_0D (Sting) fields
                int dataEntry2 = imas.open("imas:mdsplus?path=./testdb_mdsplus", LowLevel.OPEN_PULSE);
                
                HashMap<Integer, String> occurrences;
                
                // listAllOccurrences accepts as its 3rd parameter string containing nodePath
                occurrences = imas.listAllOccurrences(dataEntry2, "equilibrium", "");
                System.out.println("\nequilibrium occurrences without nodePath\n" + occurrences);

                occurrences = imas.listAllOccurrences(dataEntry2, "equilibrium", "ids_properties/comment");
                System.out.println("\nequilibrium occurrences with 'ids_properties/comment' nodePath\n" + occurrences);

                imas.close(dataEntry2);
            } catch (Exception e) { 
            System.err.println("Following exception occurred\n" + e.getMessage());
                throw e; 
            }
        } catch (Exception e) {
            System.err.println("Following exception occurred\n" + e.getMessage());
            throw e;
        }
    }
}