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
            
            int size_of_grid = 3;

            // Resize field of Array of Structures to specified size
            // Resizing will destroy the data previously stored in following ids.
            emptyEdgeProfiles.grid_ggd = new imas.edge_profiles.grid_ggdClass[size_of_grid];

            for (int i = 0; i < size_of_grid; i++){
                emptyEdgeProfiles.grid_ggd[i] = new imas.edge_profiles.grid_ggdClass();
            }
            
            emptyEdgeProfiles.grid_ggd[0].identifier.name = "First test struct";
            emptyEdgeProfiles.grid_ggd[1].identifier.name = "Second test struct";

            // Add new AoS Elements to existing data
            imas.edge_profiles.grid_ggdClass[] tmp_grid = new imas.edge_profiles.grid_ggdClass[size_of_grid];
            for (int i = 0; i < emptyEdgeProfiles.grid_ggd.length; i++) {
                tmp_grid[i] = emptyEdgeProfiles.grid_ggd[i];
            }
            emptyEdgeProfiles.grid_ggd = tmp_grid;
            
            // System.out.println("tutututut " + emptyEdgeProfiles.grid_ggd[0].identifier.name);

            imas.edge_profiles emptyEdgeProfiles2 = new imas.edge_profiles();
            emptyEdgeProfiles2.grid_ggd = new imas.edge_profiles.grid_ggdClass[1];
            emptyEdgeProfiles2.grid_ggd[0] = new imas.edge_profiles.grid_ggdClass();
            emptyEdgeProfiles2.grid_ggd[0].identifier.name = "Third test struct";

            emptyEdgeProfiles.grid_ggd[2] = emptyEdgeProfiles2.grid_ggd[0];

            System.out.println(emptyEdgeProfiles.grid_ggd[0].identifier.name);
            System.out.println(emptyEdgeProfiles.grid_ggd[1].identifier.name);
            System.out.println(emptyEdgeProfiles.grid_ggd[2].identifier.name);
            

            System.out.println("Default value for 'INT'     data  (edge_profiles/midplane/index)                                 : " + emptyEdgeProfiles.midplane.index);
            System.out.println("Default value for 'FLOAT'   data  (edge_profiles/vacuum_toroidal_field/vacuum_toroidal_field/r0) : " + emptyEdgeProfiles.vacuum_toroidal_field.r0);
            // System.out.println("Default value for 'COMPLEX' data                                                                 : " + imas.imasdef.EMPTY_COMPLEX);
            System.out.println("Default value for 1+ dimensional data                                                            : " + emptyEdgeProfiles.vacuum_toroidal_field.b0);
        
        }   catch (Exception e) {
                System.err.println("Fallowig exception occured\n" + e.getMessage());
                throw e;
        }
        
    }

}

