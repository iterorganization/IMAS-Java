package imasjava.examples;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.io.*;
import imasjava.*;
import imasjava.wrapper.LowLevel;

public class example002_fillingDataInIDS {

    // This example focuses on creating empty IDS and allocating arrays inside IDS structure
    public static void creatingCompletlyNewIDS() throws Exception {

        try {
            // empty IDS structures can be created without opening data entry
            // all you have to do is to instantiate IDS object
            imas.core_profiles emptyCoreProfiles = new imas.core_profiles();

            // Note! Every IDS must have <ids>/ids_properties/homogeneous_time field set with one of possible values
            // Possible homogeneous_time values are:
            // IDS_TIME_MODE_HETEROGENEOUS: All time-dependent quantities in the IDS may have different time coordinates.
            // IDS_TIME_MODE_HOMOGENEOUS: All time-dependent quantities in this IDS use the same time coordinate, namely <ids>/time
            // IDS_TIME_MODE_INDEPENDENT: The IDS stores no time-dependent data.
            emptyCoreProfiles.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;
            
            // it is also recommended to provide basic information regarding data source
            // even though this information is not required to store IDS, it is highly recommended
            // to fill these fields.
            //  <ids>/ids_properties/comment
            //  <ids>/ids_properties/provider
            //  <ids>/ids_properties/creation_date

            // when ids_properties.homogeneous_time is set to IDS_TIME_MODE_HOMOGENEOUS, 
            // all time-dependent fields values correspond to <ids>.time vector.
            double[] time = {1.0, 2.0, 3.0};
            emptyCoreProfiles.time = new Vect1DDouble(time);

            // size of time dependent variables must be equal to the size of time vector
            emptyCoreProfiles.global_quantities.ip = new Vect1DDouble(time);

            // IDSs can be printed using dump() method. Empty fields are not printed unless show_empty flag is set to True
            System.out.println("Dumping emptyCoreProfiles from creatingCompletlyNewIDS() function");
            System.out.println("time: \n" + emptyCoreProfiles.time);
            System.out.println("ip: \n" + emptyCoreProfiles.global_quantities.ip);
            // some fields are automatically written by AL during 'put' procedure
            // AL adds some information behind your back. This is particularly important
            // in case you want later on find out what particular version of AL was used when data were stored.
            // examples of this type of fields are <ids>/ids_properties/version_put and <ids>/ids_properties/plugins
        } catch (Exception e) {
            System.err.println("Fallowing exception occured\n" + e.getMessage());
            throw e;
        }
    }

    // This example focuses on handling arrays of structures and default values
    public static void defaultValuesAndAosOperations() throws Exception {

        try {
        
            imas.edge_profiles emptyEdgeProfiles = new imas.edge_profiles();
            emptyEdgeProfiles.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;
            
            emptyEdgeProfiles.grid_ggd = new imas.edge_profiles.grid_ggdClass[1];
            emptyEdgeProfiles.grid_ggd[0] = new imas.edge_profiles.grid_ggdClass();
            emptyEdgeProfiles.grid_ggd[0].identifier.name = "Sctructure before resize";

            System.out.println("\nSize of grid_ggd before resize: " + emptyEdgeProfiles.grid_ggd.length);
            System.out.println("Message in IDS before resize: " + emptyEdgeProfiles.grid_ggd[0].identifier.name);

            int size_of_grid = 2;

            // Resize field of Array of Structures to specified size
            // Resizing this way will destroy stored data
            emptyEdgeProfiles.grid_ggd = new imas.edge_profiles.grid_ggdClass[size_of_grid];
            for (int i = 0; i < size_of_grid; i++) {
                emptyEdgeProfiles.grid_ggd[i] = new imas.edge_profiles.grid_ggdClass();
            }

            System.out.println("\nSize of grid_ggd after resize: " + emptyEdgeProfiles.grid_ggd.length);
            System.out.println("Message in IDS after resize: " + emptyEdgeProfiles.grid_ggd[0].identifier.name);

            emptyEdgeProfiles.grid_ggd[0].identifier.name = "First test struct";
            emptyEdgeProfiles.grid_ggd[1].identifier.name = "Second test struct";
            
            // Now we want to resize grid_ggd to 3 and keep previously existing data
            // In order to do that we have to use temporary variable to keep existing data
            size_of_grid++;
            imas.edge_profiles.grid_ggdClass[] tmp_grid = new imas.edge_profiles.grid_ggdClass[size_of_grid];
            for (int i = 0; i < emptyEdgeProfiles.grid_ggd.length; i++) {
                tmp_grid[i] = emptyEdgeProfiles.grid_ggd[i];
            }
            emptyEdgeProfiles.grid_ggd = tmp_grid;
            
            System.out.println("\nSize of grid_ggd after resize to 3: " + emptyEdgeProfiles.grid_ggd.length);
            System.out.println("Message in IDS after resize: " + emptyEdgeProfiles.grid_ggd[0].identifier.name);

            // Now we'll create another IDS which will be saved inside previously created object.
            imas.edge_profiles emptyEdgeProfiles2 = new imas.edge_profiles();
            emptyEdgeProfiles2.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;
            emptyEdgeProfiles2.grid_ggd = new imas.edge_profiles.grid_ggdClass[1];
            emptyEdgeProfiles2.grid_ggd[0] = new imas.edge_profiles.grid_ggdClass();
            emptyEdgeProfiles2.grid_ggd[0].identifier.name = "Third test struct";

            emptyEdgeProfiles.grid_ggd[2] = emptyEdgeProfiles2.grid_ggd[0];

            System.out.println("\nAll messages saved in grid_ggd: ");
            System.out.println(emptyEdgeProfiles.grid_ggd[0].identifier.name);
            System.out.println(emptyEdgeProfiles.grid_ggd[1].identifier.name);
            System.out.println(emptyEdgeProfiles.grid_ggd[2].identifier.name);

        } catch (Exception e) {
            System.err.println("Fallowing exception occured\n" + e.getMessage());
            throw e;
        }   
    }

    // This example focuses on creating multi-dimensional arrays, using copmlex type and copying IDS structures
    public static void copyingAndValidatingIDS() throws Exception {
        
        try {

            // create empty gyrokinetics_local
            // NOTE: gyrokinetics_local is an alpha IDS
            imas.gyrokinetics_local emptyGyrokineticsLocal = new imas.gyrokinetics_local();

            // there is mandatory field <ids>/ids_properties/homogeneous_time
            emptyGyrokineticsLocal.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;

            // Some IDS fields contain multi-dimensional arrays.
            // First we have to create 2D array containing Complex values
            Complex[][] complexArray = new Complex[3][3];
            for (int x = 0; x < 3; x++){
                for (int y = 0; y < 3; y++){
                    complexArray[x][y] = new Complex (x,y);
                }
            }
            
            // Then we create new object using Vect2DComplex method and set its dimensions to 3x3
            // After that we need to set previously created array to IDS field
            emptyGyrokineticsLocal.non_linear.fields_zonal_2d.phi_potential_perturbed_norm = new Vect2DComplex(3,3);
            emptyGyrokineticsLocal.non_linear.fields_zonal_2d.phi_potential_perturbed_norm.set(complexArray);

            System.out.println("Filled 2D array (gyrokinetics_local/non_linear/fields_zonal_2d/phi_potential_perturbed_norm): " + emptyGyrokineticsLocal.non_linear.fields_zonal_2d.phi_potential_perturbed_norm);

            // some fields have coordinates consistency. <isd>.validate() method checks for this consistency
            // example of field of this type is gyrokinetics_local/non_linear/fields_zonal_2d/phi_potential_perturbed_norm
            // it's first dimension size must be equal to non_linear/radial_wavevector_norm size and second dimension size equal to non_linear/time_norm
            try {
                emptyGyrokineticsLocal.validate();
            } catch (Exception e) {
                System.err.println("Caught exception (raised intentionally): " + e.getMessage());
            }

            // This will fix it
            double[] time = {1.0, 2.0, 3.0};
            emptyGyrokineticsLocal.non_linear.radial_wavevector_norm = new Vect1DDouble(time);
            emptyGyrokineticsLocal.non_linear.time_norm = new Vect1DDouble(time);

            // IDSs can be copied using copy package
            // gyrokinetics_local/linear.wavevector(i1)/eigenmode(i2)/fields.phi_potential_perturbed_norm has two dimensions and stores complex numbers
            emptyGyrokineticsLocal.linear.wavevector = new imas.gyrokinetics_local.linearClass.wavevectorClass[1];
            emptyGyrokineticsLocal.linear.wavevector[0] = new imas.gyrokinetics_local.linearClass.wavevectorClass();

            emptyGyrokineticsLocal.linear.wavevector[0].eigenmode = new imas.gyrokinetics_local.linearClass.wavevectorClass.eigenmodeClass[1];
            emptyGyrokineticsLocal.linear.wavevector[0].eigenmode[0] = new imas.gyrokinetics_local.linearClass.wavevectorClass.eigenmodeClass();

            double[] dim = {1.0,2.0,3.0};
            emptyGyrokineticsLocal.linear.wavevector[0].eigenmode[0].angle_pol = new Vect1DDouble(dim);
            emptyGyrokineticsLocal.linear.wavevector[0].eigenmode[0].time_norm = new Vect1DDouble(dim);

            emptyGyrokineticsLocal.linear.wavevector[0].eigenmode[0].fields.phi_potential_perturbed_norm = new Vect2DComplex(3,3);
            emptyGyrokineticsLocal.linear.wavevector[0].eigenmode[0].fields.phi_potential_perturbed_norm.set(complexArray);

            // // Right way of copying IDF in Java
            int dataEntry = imas.open("imas:memory?path=/", LowLevel.FORCE_CREATE_PULSE);
            emptyGyrokineticsLocal.time = new Vect1DDouble(time);
            emptyGyrokineticsLocal.put(dataEntry, "gyrokinetics_local", emptyGyrokineticsLocal);
            imas.gyrokinetics_local emptyGyrokineticsLocalCopy = imas.gyrokinetics_local.get(dataEntry, "gyrokinetics_local");
            imas.close(dataEntry);

            System.out.println("Original value: \n" + emptyGyrokineticsLocal.linear.wavevector[0].eigenmode[0].fields.phi_potential_perturbed_norm);
            System.out.println("Copied   value: \n" + emptyGyrokineticsLocalCopy.linear.wavevector[0].eigenmode[0].fields.phi_potential_perturbed_norm);

        } catch (Exception e) {
            System.err.println("Fallowing exception occured\n" + e.getMessage());
            throw e;       
        }
    }
}

