package imasjava.examples;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.Stream;
import imasjava.*;
import imasjava.wrapper.LowLevel;

public class Example001_OpenDatabase {

    // This example focuses on creating DBEntry using legacy mode method
    public static void createDBEntryLegacy() throws Exception {
        String user           = System.getenv("USER");
        String dbName         = "test";
        String ddMajorVersion = "3";
        int pulse             = 1;
        int run               = 10;
        int backendID         = LowLevel.HDF5_BACKEND;

        int entry = 0;
        try {
            // Create new entry, catch exceptions if failed
            entry = imas.createEnv(pulse, run, user, dbName, ddMajorVersion, backendID);
            /*
             * You can access IDSes in here - take a look at sample code dealing with IDSes for details
             */
        } catch (Exception e) {
            System.err.println("\nFailed to create DBEntry with legacy arguments\n" + e.getMessage());
            throw e;
        } finally {
            imas.close(entry);
        }
    }

    // This example focuses on opening DBEntry using URI
    public static void openDBEntryURI() throws Exception {
        String user           = System.getenv("USER");
        String dbName         = "test";
        String ddMajorVersion = "3";
        int pulse             = 1;
        int run               = 10;
        String backend        = "hdf5";

        /*
            Format URI with required data.
            Available backends:

            ascii - only for debugging purposes
            mdsplus
            hdf5
            memory - data is lost after entry is closed
            uda
         */
        String uri = String.format("imas:%s?user=%s;pulse=%s;run=%s;database=%s;version=%s",
                                    backend,user,pulse,run,dbName,ddMajorVersion);
        int entry = 0;
        try {
            /*
                Open existing entry with given mode, catch exceptions if failed
                Available modes:

                OPEN_PULSE - Opens the access to the data only if the Data Entry exists, returns error otherwise.
                FORCE_OPEN_PULSE - Opens access to the data, creates the Data Entry if it does not exists yet.
                CREATE_PULSE - Creates a new empty Data Entry (returns error if Data Entry already exists) and opens it at the same time.
                FORCE_CREATE_PULSE - Creates an empty Data Entry (overwrites if Data Entry already exists) and opens it at the same time.
            */
            entry = imas.open(uri, LowLevel.OPEN_PULSE);
            /*
            * You can access IDSes in here - take a look at sample code dealing with IDSes for details
            */
        } catch (Exception e) {
            System.err.println("\nFailed to open DBEntry with URI\n" + e.getMessage());
            throw e;
        } finally {
            imas.close(entry);
        }
    }

    // This example focuses on creating DBEntry using explicit path
    public static void createDBEntryURIwithPath() throws Exception {
        int entry = 0;

        // example usage of uri with 'path' keyword pointing to relative location
        try {
            entry = imas.open("imas:mdsplus?path=./testdb_mdsplus", LowLevel.FORCE_CREATE_PULSE);
            
            System.out.println("\nContents of testdb_mdsplus:");
            Stream<Path> stream = Files.list(Paths.get("./testdb_mdsplus"));
            stream.forEach(System.out::println);
            // Contents of testdb_mdsplus:
            // ./testdb_mdsplus/ids_001.tree
            // ./testdb_mdsplus/ids_001.characteristics
            // ./testdb_mdsplus/ids_001.datafile

        } catch (Exception e) {
            System.err.println("\nFailed to open DBEntry with path\n" + e.getMessage());
            throw e;
        } finally {
            imas.close(entry);
        }

        // example usage of uri with 'path' keyword pointing to relative location
        try {
            entry = imas.open("imas:hdf5?path=./testdb_hdf5", LowLevel.FORCE_CREATE_PULSE);
            
            System.out.println("\nContents of testdb_hdf5:");
            Stream<Path> stream = Files.list(Paths.get("./testdb_hdf5"));
            stream.forEach(System.out::println);
            // Contents of testdb_hdf5:
            // ./testdb_hdf5/master.h5

        } catch (Exception e) {
            System.err.println("\nFailed to open DBEntry with path\n" + e.getMessage());
            throw e;
        } finally {
            imas.close(entry);
        }

        // example usage of uri with 'path' keyword pointing to relative location
        try {
            entry = imas.open("imas:ascii?path=./testdb_ascii", LowLevel.FORCE_CREATE_PULSE);
            
            System.out.println("Contents of testdb_ascii:");
            Stream<Path> stream = Files.list(Paths.get("./testdb_ascii"));
            stream.forEach(System.out::println);
            // Contents of testdb_ascii:

        } catch (Exception e) {
            System.err.println("Failed to open DBEntry with path\n" + e.getMessage());
            throw e;
        } finally {
            imas.close(entry);
        }
    }
}