package ualmemory.javainterface;
// import ualmemory.javainterface.*;

import java.io.*;

//Test class for the UAL Java interface
//The main class of the Java interface ia imas. It defines a set of inner classes whose names correspond
//to the IDS defined in the UAL configuration.
//Class IDS defines the following static methods:
// - int open(String name, int shot, int run) to open a database instance. It returns the identifier to be ised in the subsequent calls.
// - int create(String name, int shot, int run, int refShot, int refRun) to create a new database instance. It returns the identifier to be ised in the subsequent calls.
//- close(int refIdx, String name, int shot, int run) to close the Current database
//
//If the IDS is not time dependent, the corresponding class defines the following static methods:
// - put(int expIdx, String path, <IDS class>  ids) to write the content of the passed IDS instance in the database
// - <IDS  Class>get(int expIdx, String path) to read the IDS instance from the database. In the Current implementation
// the string argumeny path has the same name of the IDS class.
//
//If the IDS is time dependent, the corresponding class defines the following static methods:
// - <IDS class> [] get(int expIdx, String path) to read the whole array of IDSs corresponding to a time evolution  
// - <IDS class>  getSlice(int expIdx, String path, double time, int interpolMode)  to read the single IDS instance corresponding to the passed time.
// - <IDS class> []  getResampled(int expIdx, String path, double start, double end, double delta, int mode) to read a resampled version of the stored time evolution for that imas.
// -  void put(int expIdx, String path, <IDS class> idss[]) to write the whole array of IDSs in the database
// -  void putSlice(int expIdx, String path, <IDS class>  ids) to append the passed IDS instance to the curren IDS array in the database
//      this operation cosiders only time dependent IDS fields
// -  void putNonTimed(int expIdx, String path, controllers ids) to write in the database those fields of the IDS which do not depend on thime.




class TestUAL
{
    static int expIdx;
    
        

    public static void main(String args[])
    {
    
/*
 * Tests: shot 12/run 1 is written by a Java code (put)
 *        shot 10/run 1 is written by a Fortran code (putslice)
 *        shot 20/run 1 is written by a Fortran code and read by a Java code (get and getslice)
 *        shot 22/run 1 is written by a Java code (put slice) and read by a Fortran code
 */
     
    System.out.println(" ============================================= ");
    System.out.println(" Shot 12/run 2 is written by a Java code (put) ");
    System.out.println(" ============================================= ");
    try  {
        int number = 10; //number of elements

	int idx = imas.create("ids",12, 2, 12, 0);
	System.out.println("idx: " + idx);
        
/*        int run = imas.getRun(idx);
    	System.out.println("Run: " + run);
*/
	imas.PF pfs = new imas.PF();

        //Fill some fields
	//Those fields are not time dependent. Even if in this example thei values are filled for all 
        //the elements of the IDS array, only pfs[0] is considered in the put() method.
        pfs.IDS_Properties.Comment_of = "Debugging phase";
        //A sample int
        pfs.IDS_Properties.cocos = 1; 

	pfs.IDS_Properties.Homogeneous_Timebase = 0; // Mandatory to define this property
            
	//A sample string array. A set of supporty vector classes is defined in the UAL java interface
	//the naming convention for vector classes is vect<num dimensions>D<type>
        pfs.Coils = new imas.PF.CoilsClass[2];
        for (int i=0;i<2;i++) {
        	pfs.Coils[i] = new imas.PF.CoilsClass();
	}
       	pfs.Coils[0].Name = "SAMPLE 1";
       	pfs.Coils[1].Name = "SAMPLE 2";

      	//The following field is time dependent. All the elements of the IDS array are considered
        //A sample time dependent 1D vector
        pfs.Coils[0].Current.Data     = new Vect1DDouble(number);
        //Never forget to write the time for time dependent IDS instances!!!!
        pfs.Coils[0].Current.Timebase = new Vect1DDouble(number);
        for (int i=0;i<number;i++) {
        	pfs.Coils[0].Current.Data.setElementAt(i, 2*i);
        	pfs.Coils[0].Current.Timebase.setElementAt(i, i);
	}
	
        number = number+2;
        pfs.Coils[1].Current.Data     = new Vect1DDouble(number);
        //Never forget to write the time for time dependent IDS instances!!!!
        pfs.Coils[1].Current.Timebase = new Vect1DDouble(number);
        for (int i=0;i<number;i++) {
        	pfs.Coils[1].Current.Data.setElementAt(i, 2*i+10);
        	pfs.Coils[1].Current.Timebase.setElementAt(i, i+number);
	}
	pfs.Coils[0].Voltage.Data = new Vect1DDouble(0); 
	pfs.Coils[1].Voltage.Data = new Vect1DDouble(0); 
/*    
	pfs.Coils[0].Voltage.Timebase = new Vect1DDouble(0); 
	pfs.Coils[1].Voltage.Timebase = new Vect1DDouble(0); 
*/    	
// Put Vertical_Forces/Names   
/*
        pfs.Vertical_Forces = new imas.PF.Vertical_ForcesClass();
        pfs.Vertical_Forces.Names = new Vect1DString(1);
	pfs.Vertical_Forces.Names.setElementAt(0,"vertical 1");
*/
	pfs.put(idx, "pf", pfs);

    	imas.close(idx);
    } catch(Exception exc) {
	System.out.println("Error: " + exc);
    }

    System.out.println(" ================================================== ");
    System.out.println(" Shot 22/run 1 is written by a Java code (putslice) ");
    System.out.println(" ================================================== ");
    try  {
        int number = 1; //number of elements one time value 

	int idxSlice = imas.create("ids",22, 1, 22, 0);
	System.out.println("idx: " + idxSlice);
        
	imas.PF pfsSlice = new imas.PF();

        //Fill some fields
	//Those fields are not time dependent. Even if in this example thei values are filled for all 
        //the elements of the IDS array, only pfs[0] is considered in the put() method.
        pfsSlice.IDS_Properties.Comment_of = "Test of put slice";
        //A sample int
        pfsSlice.IDS_Properties.cocos = 1; 

	pfsSlice.IDS_Properties.Homogeneous_Timebase = 1; // Mandatory to define this property
            
	//A sample string array. A set of supporty vector classes is defined in the UAL java interface
	//the naming convention for vector classes is vect<num dimensions>D<type>
        pfsSlice.Coils = new imas.PF.CoilsClass[2];
        for (int i=0;i<2;i++) {
        	pfsSlice.Coils[i] = new imas.PF.CoilsClass();
	}
       	pfsSlice.Coils[0].Name = "Coil 1";
       	pfsSlice.Coils[1].Name = "Coil 2";

	pfsSlice.putNonTimed(idxSlice,"PF",pfsSlice);
	System.out.println("After putnonTimed ");

      	//The following field is time dependent. All the elements of the IDS array are considered
        //A sample time dependent 1D vector
        pfsSlice.Coils[0].Current.Data     = new Vect1DDouble(number);
        pfsSlice.Coils[1].Current.Data     = new Vect1DDouble(number);
        //Never forget to write the general time!!!!
        pfsSlice.Timebase = new Vect1DDouble(number);
	
	// write 7 slices for example
	int numberofSlice = 7;
        for (int i=0;i<numberofSlice;i++) {
        	pfsSlice.Coils[0].Current.Data.setElementAt(0, 2*i);
        	pfsSlice.Coils[1].Current.Data.setElementAt(0, 2*i+10);
                pfsSlice.Timebase.setElementAt(0, i);
		pfsSlice.putSlice(idxSlice, "pf", pfsSlice);
	}
	
    	imas.close(idxSlice);
    } catch(Exception exc) {
	System.out.println("Error: " + exc);
    }

// Test the "get" part
    imas.PF pfsget;
    try  {
//	int idxget = imas.open("ids",20, 1); // 12, 1    String user, String tokamak, String version
	int idxget = imas.openEnv("ids",12, 2, "guilleb", "test", "1.0"); // 12, 1    String user, String tokamak, String version
	System.out.println("idx for get: " + idxget);
        
	pfsget = imas.PF.get(idxget,"PF");
        System.out.println("pfsget.IDS_Properties.Comment_of: "+ pfsget.IDS_Properties.Comment_of);
	System.out.println("pfsget.IDS_Properties.Homogeneous_Timebase: " + pfsget.IDS_Properties.Homogeneous_Timebase);
        System.out.println("number of coils: "+ pfsget.Coils.length);
//        pfsget.dump();
	for (int i=0;i<pfsget.Coils.length;i++) {
          System.out.println("name of coils["+i+"]: "+ pfsget.Coils[i].Name);
          System.out.println("Voltage: "+ pfsget.Coils[i].Current.Data);
          System.out.println("Time   : "+ pfsget.Coils[i].Current.Timebase);
        }

    	imas.close(idxget);

    } catch(Exception exc) {
	System.out.println("Error: " + exc);
    }

// Test the "get slice" part
    imas.PF pfsgetslice;
    try  {
	int idxget = imas.open("ids",22, 1); // 12, 2 or 20, 1
	System.out.println("idx for getslice: " + idxget);
        
	double time=4.2;

	pfsgetslice = imas.PF.getSlice(idxget,"PF",time, 2);
        System.out.println("pfsget.IDS_Properties.Comment_of: "+ pfsgetslice.IDS_Properties.Comment_of);
	System.out.println("pfsget.IDS_Properties.Homogeneous_Timebase: " + pfsgetslice.IDS_Properties.Homogeneous_Timebase);
        System.out.println("number of coils: "+ pfsgetslice.Coils.length);
//        pfsget.dump();
	for (int i=0;i<pfsgetslice.Coils.length;i++) {
          System.out.println("name of coils["+i+"]: "+ pfsgetslice.Coils[i].Name);
          System.out.println("Voltage: "+ pfsgetslice.Coils[i].Current.Data);
          System.out.println("Time   : "+ pfsgetslice.Coils[i].Current.Timebase);
        }
        System.out.println("Timebase: "+ pfsgetslice.Timebase);

    	imas.close(idxget);

    } catch(Exception exc) {
	System.out.println("Error: " + exc);
    }

// Test the "copy" part
    imas.PF pfssrc, pfsdst ;
    try  {
	int idxsrc = imas.open("ids",12, 2); // 12, 1    String user, String tokamak, String version
	System.out.println("idx for get. src: " + idxsrc);

	int idxdst = imas.create("ids",12, 998, 12, 0);
        
	pfssrc = imas.PF.get(idxsrc,"PF");
        System.out.println("**** SOURCE ******");
        System.out.println("pfsget.IDS_Properties.Comment_of: "+ pfssrc.IDS_Properties.Comment_of);
	System.out.println("pfsget.IDS_Properties.Homogeneous_Timebase: " + pfssrc.IDS_Properties.Homogeneous_Timebase);
        System.out.println("number of coils: "+ pfssrc.Coils.length);
//        pfsget.dump();
	for (int i=0;i<pfssrc.Coils.length;i++) {
          System.out.println("name of coils["+i+"]: "+ pfssrc.Coils[i].Name);
          System.out.println("Voltage: "+ pfssrc.Coils[i].Current.Data);
          System.out.println("Time   : "+ pfssrc.Coils[i].Current.Timebase);
        }

        imas.PF.copy(idxsrc, 0, idxdst, 0);

/*
*/	
	pfsdst = imas.PF.get(idxdst,"PF");
        System.out.println("**** DESTINATION ******");
        System.out.println("pfsget.IDS_Properties.Comment_of: "+ pfsdst.IDS_Properties.Comment_of);
	System.out.println("pfsget.IDS_Properties.Homogeneous_Timebase: " + pfsdst.IDS_Properties.Homogeneous_Timebase);
        System.out.println("number of coils: "+ pfsdst.Coils.length);
//        pfsget.dump();
	for (int i=0;i<pfsdst.Coils.length;i++) {
          System.out.println("name of coils["+i+"]: "+ pfsdst.Coils[i].Name);
          System.out.println("Voltage: "+ pfsdst.Coils[i].Current.Data);
          System.out.println("Time   : "+ pfsdst.Coils[i].Current.Timebase);
        }
    	imas.close(idxsrc);
    	imas.close(idxdst);

    } catch(Exception exc) {
	System.out.println("Error: " + exc);
    }

}   
}
         
        
  
