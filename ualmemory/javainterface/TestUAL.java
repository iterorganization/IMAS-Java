package ualmemory.javainterface;
// import ualmemory.javainterface.*;

import java.io.*;

//Test class for the UAL Java interface
//The main class of the Java interface ia imas. It defines a set of inner classes whose names correspond
//to the IDS defined in the UAL configuration.
//Class IDS defines the following static methods:
// - int open(String name, int shot, int run) to open a database instance. It returns the identifier to be ised in the subsequent calls.
// - int create(String name, int shot, int run, int refShot, int refRun) to create a new database instance. It returns the identifier to be ised in the subsequent calls.
//- close(int refIdx, String name, int shot, int run) to close the current database
//
//If the IDS is not time dependent, the corresponding class defines the following static methods:
// - put(int expIdx, String path, <IDS class>  ids) to write the content of the passed IDS instance in the database
// - <IDS  Class>get(int expIdx, String path) to read the IDS instance from the database. In the current implementation
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
	imas.pf_active pf_actives = new imas.pf_active();

        //Fill some fields
	//Those fields are not time dependent. Even if in this example thei values are filled for all 
        //the elements of the IDS array, only pf_actives[0] is considered in the put() method.
        pf_actives.ids_properties.comment = "Debugging phase";
        //A sample int
        pf_actives.ids_properties.cocos = 1; 

	pf_actives.ids_properties.homogeneous_time = 0; // Mandatory to define this property
            
	//A sample string array. A set of supporty vector classes is defined in the UAL java interface
	//the naming convention for vector classes is vect<num dimensions>D<type>
        pf_actives.coil = new imas.pf_active.coilClass[2];
        for (int i=0;i<2;i++) {
        	pf_actives.coil[i] = new imas.pf_active.coilClass();
	}
       	pf_actives.coil[0].name = "SAMPLE 1";
       	pf_actives.coil[1].name = "SAMPLE 2";

      	//The following field is time dependent. All the elements of the IDS array are considered
        //A sample time dependent 1D vector
        pf_actives.coil[0].current.data     = new Vect1DDouble(number);
        //Never forget to write the time for time dependent IDS instances!!!!
        pf_actives.coil[0].current.time = new Vect1DDouble(number);
        for (int i=0;i<number;i++) {
        	pf_actives.coil[0].current.data.setElementAt(i, 2*i);
        	pf_actives.coil[0].current.time.setElementAt(i, i);
	}
	
        number = number+2;
        pf_actives.coil[1].current.data     = new Vect1DDouble(number);
        //Never forget to write the time for time dependent IDS instances!!!!
        pf_actives.coil[1].current.time = new Vect1DDouble(number);
        for (int i=0;i<number;i++) {
        	pf_actives.coil[1].current.data.setElementAt(i, 2*i+10);
        	pf_actives.coil[1].current.time.setElementAt(i, i+number);
	}
	pf_actives.coil[0].voltage.data = new Vect1DDouble(0); 
	pf_actives.coil[1].voltage.data = new Vect1DDouble(0); 
/*    
	pf_actives.coil[0].voltage.time = new Vect1DDouble(0); 
	pf_actives.coil[1].voltage.time = new Vect1DDouble(0); 
*/    	
// Put Vertical_Forces/names   
/*
        pf_actives.Vertical_Forces = new imas.pf_active.Vertical_ForcesClass();
        pf_actives.Vertical_Forces.names = new Vect1DString(1);
	pf_actives.Vertical_Forces.names.setElementAt(0,"vertical 1");
*/
	pf_actives.put(idx, "pf_active", pf_actives);

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
        
	imas.pf_active pf_activesSlice = new imas.pf_active();

        //Fill some fields
	//Those fields are not time dependent. Even if in this example thei values are filled for all 
        //the elements of the IDS array, only pf_actives[0] is considered in the put() method.
        pf_activesSlice.ids_properties.comment = "Test of put slice";
        //A sample int
        pf_activesSlice.ids_properties.cocos = 1; 

	pf_activesSlice.ids_properties.homogeneous_time = 1; // Mandatory to define this property
            
	//A sample string array. A set of supporty vector classes is defined in the UAL java interface
	//the naming convention for vector classes is vect<num dimensions>D<type>
        pf_activesSlice.coil = new imas.pf_active.coilClass[2];
        for (int i=0;i<2;i++) {
        	pf_activesSlice.coil[i] = new imas.pf_active.coilClass();
	}
       	pf_activesSlice.coil[0].name = "Coil 1";
       	pf_activesSlice.coil[1].name = "Coil 2";

	pf_activesSlice.putNonTimed(idxSlice,"pf_active",pf_activesSlice);
	System.out.println("After putnonTimed ");

      	//The following field is time dependent. All the elements of the IDS array are considered
        //A sample time dependent 1D vector
        pf_activesSlice.coil[0].current.data     = new Vect1DDouble(number);
        pf_activesSlice.coil[1].current.data     = new Vect1DDouble(number);
        //Never forget to write the general time!!!!
        pf_activesSlice.time = new Vect1DDouble(number);
	
	// write 7 slices for example
	int numberofSlice = 7;
        for (int i=0;i<numberofSlice;i++) {
        	pf_activesSlice.coil[0].current.data.setElementAt(0, 2*i);
        	pf_activesSlice.coil[1].current.data.setElementAt(0, 2*i+10);
                pf_activesSlice.time.setElementAt(0, i);
		pf_activesSlice.putSlice(idxSlice, "pf_active", pf_activesSlice);
	}
	
    	imas.close(idxSlice);
    } catch(Exception exc) {
	System.out.println("Error: " + exc);
    }

// Test the "get" part
    imas.pf_active pf_activesget;
    try  {
//	int idxget = imas.open("ids",20, 1); // 12, 1    String user, String tokamak, String version
	int idxget = imas.openEnv("ids",12, 2, "guilleb", "test", "1.0"); // 12, 1    String user, String tokamak, String version
	System.out.println("idx for get: " + idxget);
        
	pf_activesget = imas.pf_active.get(idxget,"pf_active");
        System.out.println("pf_activesget.ids_properties.comment: "+ pf_activesget.ids_properties.comment);
	System.out.println("pf_activesget.ids_properties.homogeneous_time: " + pf_activesget.ids_properties.homogeneous_time);
        System.out.println("number of coil: "+ pf_activesget.coil.length);
//        pf_activesget.dump();
	for (int i=0;i<pf_activesget.coil.length;i++) {
          System.out.println("name of coil["+i+"]: "+ pf_activesget.coil[i].name);
          System.out.println("voltage: "+ pf_activesget.coil[i].current.data);
          System.out.println("Time   : "+ pf_activesget.coil[i].current.time);
        }

    	imas.close(idxget);

    } catch(Exception exc) {
	System.out.println("Error: " + exc);
    }

// Test the "get slice" part
    imas.pf_active pf_activesgetslice;
    try  {
	int idxget = imas.open("ids",22, 1); // 12, 2 or 20, 1
	System.out.println("idx for getslice: " + idxget);
        
	double time=4.2;

	pf_activesgetslice = imas.pf_active.getSlice(idxget,"pf_active",time, 2);
        System.out.println("pf_activesget.ids_properties.comment: "+ pf_activesgetslice.ids_properties.comment);
	System.out.println("pf_activesget.ids_properties.homogeneous_time: " + pf_activesgetslice.ids_properties.homogeneous_time);
        System.out.println("number of coil: "+ pf_activesgetslice.coil.length);
//        pf_activesget.dump();
	for (int i=0;i<pf_activesgetslice.coil.length;i++) {
          System.out.println("name of coil["+i+"]: "+ pf_activesgetslice.coil[i].name);
          System.out.println("voltage: "+ pf_activesgetslice.coil[i].current.data);
          System.out.println("Time   : "+ pf_activesgetslice.coil[i].current.time);
        }
        System.out.println("time: "+ pf_activesgetslice.time);

    	imas.close(idxget);

    } catch(Exception exc) {
	System.out.println("Error: " + exc);
    }

// Test the "copy" part
    imas.pf_active pf_activessrc, pf_activesdst ;
    try  {
	int idxsrc = imas.open("ids",12, 2); // 12, 1    String user, String tokamak, String version
	System.out.println("idx for get. src: " + idxsrc);

	int idxdst = imas.create("ids",12, 998, 12, 0);
        
	pf_activessrc = imas.pf_active.get(idxsrc,"pf_active");
        System.out.println("**** SOURCE ******");
        System.out.println("pf_activesget.ids_properties.comment: "+ pf_activessrc.ids_properties.comment);
	System.out.println("pf_activesget.ids_properties.homogeneous_time: " + pf_activessrc.ids_properties.homogeneous_time);
        System.out.println("number of coil: "+ pf_activessrc.coil.length);
//        pf_activesget.dump();
	for (int i=0;i<pf_activessrc.coil.length;i++) {
          System.out.println("name of coil["+i+"]: "+ pf_activessrc.coil[i].name);
          System.out.println("voltage: "+ pf_activessrc.coil[i].current.data);
          System.out.println("Time   : "+ pf_activessrc.coil[i].current.time);
        }

        imas.pf_active.copy(idxsrc, 0, idxdst, 0);

/*
*/	
	pf_activesdst = imas.pf_active.get(idxdst,"pf_active");
        System.out.println("**** DESTINATION ******");
        System.out.println("pf_activesget.ids_properties.comment: "+ pf_activesdst.ids_properties.comment);
	System.out.println("pf_activesget.ids_properties.homogeneous_time: " + pf_activesdst.ids_properties.homogeneous_time);
        System.out.println("number of coil: "+ pf_activesdst.coil.length);
//        pf_activesget.dump();
	for (int i=0;i<pf_activesdst.coil.length;i++) {
          System.out.println("name of coil["+i+"]: "+ pf_activesdst.coil[i].name);
          System.out.println("voltage: "+ pf_activesdst.coil[i].current.data);
          System.out.println("Time   : "+ pf_activesdst.coil[i].current.time);
        }
    	imas.close(idxsrc);
    	imas.close(idxdst);

    } catch(Exception exc) {
	System.out.println("Error: " + exc);
    }

}   
}
         
        
  
