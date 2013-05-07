
package ualmemory.javainterface;
     public class imas {
     static {
        try {
            System.loadLibrary("UALLowLevel");
        }catch(Throwable exc)
        {
            System.err.println("Cannot link to JNI library: " + exc);
            System.exit(0);
        }
    }
 public static final int EMPTY_INT = -999999999;
 public static final double EMPTY_DOUBLE = -9.0E40;
    
 public static final int INTERPOLATION = 3, CLOSEST_SAMPLE = 1, PREVIOUS_SAMPLE = 2;

 public static final int NON_TIMED = 0, TIMED = 1, TIMED_CLEAR = 2;
 
 //Cache Management methods
 public static native void enableMemCache(int expIdx);
 public static native void disableMemCache(int expIdx);
 public static native void discardAll(int expIdx);
 public static native void flushAll(int expIdx);
 public static native void discard(int expIdx, String idsPath);
 public static native void flush(int expIdx, String idsPath);

 /**
 *Open the selected database. Missing fields in the database will be retrieved from the reference database.
 * @param name Name of the database (by convention imas).
 * @param shot Shot number.
 * @param run Run Number.
 * @param refShot Shot number of the reference database.
 * @param runRun Run Number of the reference database.
 * @return the database index to be used in subsequent get/put calls
 * @exception UALException is thrown if the database cannot be open
 **/
 static public native int open(String name, int shot, int run)  throws UALException;
 /**
 *Creates a new database instance. 
 * @param name Name of the database (by convention imas).
 * @param shot Shot number.
 * @param run Run Number.
 * @param refShot Shot number of the reference database.
 * @param runRun Run Number of the reference database.
 * @return the database index to be used in subsequent get/put calls
 * @exception UALException is thrown if the database cannot be open.
 **/
 static public native int create(String name, int shot, int run, int refShot, int refRun) throws UALException;
 static public native int openEnv(String name, int shot, int run, String user, String tokamak, String version)  throws UALException;
 /**
 *Creates a new database instance. 
 * @param name Name of the database (by convention imas).
 * @param shot Shot number.
 * @param run Run Number.
 * @param refShot Shot number of the reference database.
 * @param runRun Run Number of the reference database.
 * @return the database index to be used in subsequent get/put calls
 * @exception UALException is thrown if the database cannot be open.
 **/
 static public native int createEnv(String name, int shot, int run, int refShot, int refRun, String user, String tokamak, String version) throws UALException;

/*
//---------- Create a new run with an automatically generated run number ---------
 static public int createdb(String user, String machine, int shot, String dataVersion) throws UALException
 {
     return createdb(user, machine, shot, dataVersion, null, null, 0, 0);
 } 
 static public int createdb(String user, String machine, int shot, String dataVersion, String parentUser,
     String parentMachine, int parentShot, int parentRun) throws UALException
 {
     int run;
     if(parentUser == null)
     	run = UALLowLevel.createNewRunDb(user, machine, shot, dataVersion);
     else
    	run = UALLowLevel.createNewRunParentDb(user, machine, shot, dataVersion, parentUser, parentMachine, parentShot, parentRun);
     
     if(run  <  0)
	throw new UALException("Cannot create Catalogue Entry");
     return createEnv("ids", shot, run, shot, run, user, machine, dataVersion);
 }

//---------- Create a new run with a specified run number ---------
static public int createdb(String user, String machine, int shot, int run, String dataVersion) throws UALException
{
	return createdb(user, machine, shot, run, dataVersion, null, null, 0, 0);
} 
static public int createdb(String user, String machine, int shot, int run, String dataVersion, String parentUser,
	String parentMachine, int parentShot, int parentRun) throws UALException
{
	if(parentUser == null)
		UALLowLevel.createSpecifiedRunDb(user, machine, shot, run, dataVersion);
	else
		UALLowLevel.createSpecifiedRunParentDb(user, machine, shot, run, dataVersion, parentUser, parentMachine, parentShot, parentRun);
	
	return createEnv("ids", shot, run, shot, run, user, machine, dataVersion);
}
*/
 
 
 static public native int openHdf5(String name, int shot, int run)  throws UALException;
 /**
 *Creates a new database instance. 
 * @param name Name of the database (by convention imas).
 * @param shot Shot number.
 * @param run Run Number.
 * @param refShot Shot number of the reference database.
 * @param runRun Run Number of the reference database.
 * @return the database index to be used in subsequent get/put calls
 * @exception UALException is thrown if the database cannot be open.
 **/
 static public native int createHdf5(String name, int shot, int run, int refShot, int refRun) throws UALException;
 
  

 /**
 * Connect to a remote database
 * @param ip address or name of the MDSplus server
 * @exception UALException is thrown if the connection fails
 **/
 static public native void connect(String ip) throws UALException;

 /**
 * Disconnect from a remote database
 * @exception UALException is thrown if the disconnection fails
 **/
 static public native void disconnect() throws UALException;

 /**
 * Execute a shell command (either locally or remotely)
 * @param ip address or name of the MDSplus server
 * @param command command to be executed
 * @return the standard output of the command
 * @exception UALException is thrown if the execution fails
 **/
 static public native String exec(String ip, String command) throws UALException;

 /**
 *Closes the currently open database.
 * @param refIdx database index, returned by create or open.
 * @param name Name of the database (by convention imas).
 * @param shot Shot number.
 * @param run Run Number.
 **/
 static public native int close(int refIdx);
 
 static int getShot(int idx){return UALLowLevel.getShot(idx);}
 static int getRun(int idx){return UALLowLevel.getRun(idx);}
 
 static public int close(int refIdx, String name, int shot, int run){return close(refIdx);}

 /**
 *Get the time base of a ids.
 * @param idx database index, returned by create or open.
 * @param path name of the IDS
 * @return a vector containing the times of all slices
 * @exception UALException is thrown if the time base cannot be read.
 **/
 static public Vect1DDouble getTime(int expIdx, String path) throws UALException
{
	UALLowLevel.beginIDSGet(expIdx,path,true);
	Vect1DDouble time = UALLowLevel.getVect1DDouble(expIdx,path,"time");
	UALLowLevel.endIDSGet(expIdx,path);
	return time;
}
 

public static class Actuator
{
    
      public static class IDS_PropertiesClass {
      
      public String Status_of;
    
      public String Comment_of;
    
      public String Creator_of;
    
      public String Date_of;
    
      public String Source_of;
    
      public String Reference_of;
    
      public int Homogeneous_Timebase = EMPTY_INT;
    
      public int cocos = EMPTY_INT;
    
      }
      public IDS_PropertiesClass IDS_Properties = new IDS_PropertiesClass();
    
      public String Name;
    
      public Vect1DString Channels;
    
      public Vect2DDouble  Power;
    
      public Vect2DDouble  Generic_Signal;
    
      public static class Code_ParametersClass {
      
      public String Code_Name;
    
      public String Code_Version;
    
      public Vect1DString Parameters;
    
      public Vect1DString Output_Diagnostics;
    
      public Vect1DInt Output_Flag;
    
      }
      public Code_ParametersClass Code_Parameters = new Code_ParametersClass();
    
      public Vect1DDouble Timebase;
    

 /**
 * Method copy Actuator copies a full IDS between two databases.
 * @param srcIdx The index of the source database, returned by imas.open()
 * @param srcOccur The occurence of the IDS in the source database.
 * @param destIdx The index of the destination database, returned by imas.open()
 * @param destOccur The occurence of the IDS in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copy(int srcIdx, int srcOccur, int destIdx, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyIds(srcIdx, destIdx, "Actuator", srcOccur, destOccur);
    }

 /**
 * Method copyEnv Actuator copies a full IDS between two databases.
 * @param srcUser User of the source database
 * @param srcTokamak Tokamak of the source database
 * @param srcVersion Version of the source database
 * @param srcShot Shot number of the source database
 * @param srcRun Run number of the source database
 * @param srcOccur The occurence of the IDS in the source database.
 * @param destUser User of the destination database
 * @param destTokamak Tokamak of the destination database
 * @param destVersion Version of the destination database
 * @param destShot Shot number of the destination database
 * @param destRun Run number of the destination database
 * @param destOccur The occurence of the IDS in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copyEnv(String srcUser, String srcTokamak, String srcVersion, int srcShot, int srcRun, int srcOccur, String destUser, String destTokamak, String destVersion, int destShot, int destRun, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyIdsEnv(srcTokamak, srcVersion, srcUser, srcShot, srcRun, srcOccur, destTokamak, destVersion, destUser, destShot, destRun, destOccur, "Actuator");
    }

/**
 * Method put stores a non timed Actuator IDSs in the open database. 
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The passed Actuator ids.
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void put(int expIdx, String path, Actuator ids)  throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
	  Vect5DDouble  vect5DDouble;
	  Vect6DDouble  vect6DDouble;
	  Vect7DDouble  vect7DDouble;
          String        timebasepath;
          String        ual_debug = System.getenv("ual_debug");

          UALLowLevel.beginIDSPut(expIdx, path);
          
// Put IDS_Properties/Status_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Status_of", ids.IDS_Properties.Status_of);

// Put IDS_Properties/Comment_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Comment_of", ids.IDS_Properties.Comment_of);

// Put IDS_Properties/Creator_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Creator_of", ids.IDS_Properties.Creator_of);

// Put IDS_Properties/Date_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Date_of", ids.IDS_Properties.Date_of);

// Put IDS_Properties/Source_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Source_of", ids.IDS_Properties.Source_of);

// Put IDS_Properties/Reference_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Reference_of", ids.IDS_Properties.Reference_of);

// Put IDS_Properties/Homogeneous_Timebase
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase", ids.IDS_Properties.Homogeneous_Timebase);

// Put IDS_Properties/cocos
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/cocos", ids.IDS_Properties.cocos);

// Put Name
          UALLowLevel.putString(expIdx,path, "Name",  ids.Name);

// Put Channels
          UALLowLevel.putVect1DString(expIdx,path, "Channels", "", ids.Channels, false);
   		
// Put Power
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Power", timebasepath.trim(), ids.Power, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Generic_Signal
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Generic_Signal", timebasepath.trim(), ids.Generic_Signal, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Code_Parameters/Code_Name
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Name", ids.Code_Parameters.Code_Name);

// Put Code_Parameters/Code_Version
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Version", ids.Code_Parameters.Code_Version);

// Put Code_Parameters/Parameters
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DString(expIdx, path, "Code_Parameters" + "/Parameters", timebasepath.trim(), ids.Code_Parameters.Parameters, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Code_Parameters/Output_Diagnostics
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DString(expIdx, path, "Code_Parameters" + "/Output_Diagnostics", timebasepath.trim(), ids.Code_Parameters.Output_Diagnostics, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Code_Parameters/Output_Flag
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DInt(expIdx, path, "Code_Parameters" + "/Output_Flag", timebasepath.trim(), ids.Code_Parameters.Output_Flag, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect1DDouble(expIdx,path, "Timebase", timebasepath.trim(), ids.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
          UALLowLevel.endIDSPut(expIdx, path);
    }

 /**
 * Method putNonTimed stores the non time dependent fields of a (timed) Actuator IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The timed Actuator IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void putNonTimed(int expIdx, String path, Actuator ids) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
          Vect5DDouble  vect5DDouble;
          Vect6DDouble  vect6DDouble;
          Vect7DDouble  vect7DDouble;
          String        ual_debug = System.getenv("ual_debug");
          
          delete(expIdx, path);
          UALLowLevel.beginIDSPutNonTimed(expIdx, path);
          
// Put IDS_Properties/Status_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Status_of", ids.IDS_Properties.Status_of);

// Put IDS_Properties/Comment_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Comment_of", ids.IDS_Properties.Comment_of);

// Put IDS_Properties/Creator_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Creator_of", ids.IDS_Properties.Creator_of);

// Put IDS_Properties/Date_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Date_of", ids.IDS_Properties.Date_of);

// Put IDS_Properties/Source_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Source_of", ids.IDS_Properties.Source_of);

// Put IDS_Properties/Reference_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Reference_of", ids.IDS_Properties.Reference_of);

// Put IDS_Properties/Homogeneous_Timebase
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase", ids.IDS_Properties.Homogeneous_Timebase);

// Put IDS_Properties/cocos
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/cocos", ids.IDS_Properties.cocos);

// Put Name
          UALLowLevel.putString(expIdx,path, "Name",  ids.Name);

// Put Channels
          UALLowLevel.putVect1DString(expIdx,path, "Channels", "", ids.Channels, false);
   		
// Put Code_Parameters/Code_Name
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Name", ids.Code_Parameters.Code_Name);

// Put Code_Parameters/Code_Version
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Version", ids.Code_Parameters.Code_Version);

          UALLowLevel.endIDSPutNonTimed(expIdx, path);
    }

    
/**
 * Method putSlice appends a (timed) Actuator IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param ids The timed Actuator IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void putSlice(int expIdx, String path, Actuator ids) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
          Vect5DDouble  vect5DDouble;
          Vect6DDouble  vect6DDouble;
          Vect7DDouble  vect7DDouble;
          String        timebasepath;
          String        ual_debug = System.getenv("ual_debug");
          
    	  if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
            System.out.println("ERROR : the PUT_SLICE routine works only for homogeneous timebase IDS");
            return;
          }
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutSlice(expIdx, path);
          
// Put Slice Power
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Power", timebasepath.trim(), ids.Power, ids.Timebase.getElementAt(0));
		
// Put Slice Generic_Signal
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Generic_Signal", timebasepath.trim(), ids.Generic_Signal, ids.Timebase.getElementAt(0));
		
// Put Slice Code_Parameters/Parameters
          UALLowLevel.putVect1DStringSlice(expIdx, path, "Code_Parameters" + "/Parameters", timebasepath.trim(), ids.Code_Parameters.Parameters, ids.Timebase.getElementAt(0));
		
// Put Slice Code_Parameters/Output_Diagnostics
          UALLowLevel.putVect1DStringSlice(expIdx, path, "Code_Parameters" + "/Output_Diagnostics", timebasepath.trim(), ids.Code_Parameters.Output_Diagnostics, ids.Timebase.getElementAt(0));
		
// Put Slice Code_Parameters/Output_Flag
          UALLowLevel.putVect1DIntSlice(expIdx, path, "Code_Parameters" + "/Output_Flag", timebasepath.trim(), ids.Code_Parameters.Output_Flag, ids.Timebase.getElementAt(0));
		
// Put Slice Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx,path, "Timebase", timebasepath.trim(), ids.Timebase, ids.Timebase.getElementAt(0));
		
          UALLowLevel.endIDSPutSlice(expIdx, path);
    }
    

    

/**
 * Method putdb stores a non timed Actuator IDSs in the open database only if
 *  datainto.isref == 1. In any case it calls afterwards UALLowLevel.putIdsDb() in order to update catalogue
 * @param expIdx The index to the database, returned by imas.open()
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected ids. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @param ids The  Actuator IDSs
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
/*
    public static void putdb(int expIdx, String user, String machine, int shot, int run, 
    	String path, int occurrence, Actuator ids) throws UALException
    {
    	String actPath;
        if(occurrence > 0)
	    actPath = path+"/"+occurrence;
	else
	    actPath = path;
	if(ids.IDS_Properties.isref == 1)
	{
	    put(expIdx, actPath, ids);
	    UALLowLevel.putIdsDb(user, machine, shot, run, path, occurrence, 1,
	    	user, machine, shot, run, occurrence);
	}
	else //isref == 2
	{
	    UALLowLevel.putIdsDb(user, machine, shot, run, path, occurrence, 2,
	    	ids.IDS_Properties.whatref.user,  ids.IDS_Properties.whatref.machine,
		ids.IDS_Properties.whatref.shot, ids.IDS_Properties.whatref.run, 
		ids.IDS_Properties.whatref.occurrence);
	
	}
    }
*/

/**
 * Method getdb retrieves the non timed Actuator IDSs in the open database, taking the correct reference.
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the Actuator IDS . Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if float or double and empty String is string.
 **/
    public static Actuator getdb(String user, String machine, int shot, int run, String version, String path, int occurrence) throws UALException
    {
//		int idx = UALLowLevel.openDb(user, machine, shot, run, path, occurrence);
		int idx = openEnv("ids", shot, run, user, machine, version);
		if (occurrence != 0)
			path = path + "/" + occurrence;
		Actuator retIdss = null;
                try {
                        retIdss = get(idx, path);
                }
                catch(Exception exc) {
                        retIdss = new Actuator ();
                        System.out.println("Return a new Actuator empty IDS after catching exception: "+exc);
                }
		close(idx);
		return retIdss;
    }

/*
    public static void fillIdsRef(Actuator ids, int isRef, String refUser, String refMachine, int refShot, int refRun, int refOccurrence)
    {
        ids.IDS_Properties.isref = isRef;
        if(isRef != 1)
        {
            ids.IDS_Properties.whatref.user = refUser;
                ids.IDS_Properties.whatref.machine = refMachine;
                ids.IDS_Properties.whatref.shot = refShot;
                ids.IDS_Properties.whatref.run = refRun;
                ids.IDS_Properties.whatref.occurrence = refOccurrence;
        }
    } 
*/
            

    public static Actuator  get(int expIdx, String path)  throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");

      Actuator ids = new Actuator (); 
      UALLowLevel.beginIDSGet(expIdx, path, false);
      
          try {
            ids.IDS_Properties.Status_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Status_of");
          } catch(Exception exc){ids.IDS_Properties.Status_of = null;}
		
          try {
            ids.IDS_Properties.Comment_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Comment_of");
          } catch(Exception exc){ids.IDS_Properties.Comment_of = null;}
		
          try {
            ids.IDS_Properties.Creator_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Creator_of");
          } catch(Exception exc){ids.IDS_Properties.Creator_of = null;}
		
          try {
            ids.IDS_Properties.Date_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Date_of");
          } catch(Exception exc){ids.IDS_Properties.Date_of = null;}
		
          try {
            ids.IDS_Properties.Source_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Source_of");
          } catch(Exception exc){ids.IDS_Properties.Source_of = null;}
		
          try {
            ids.IDS_Properties.Reference_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Reference_of");
          } catch(Exception exc){ids.IDS_Properties.Reference_of = null;}
		
          try {
            ids.IDS_Properties.Homogeneous_Timebase = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase");
          } catch(Exception exc){ids.IDS_Properties.Homogeneous_Timebase = EMPTY_INT;}
		
          try {
            ids.IDS_Properties.cocos = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/cocos");
          } catch(Exception exc){ids.IDS_Properties.cocos = EMPTY_INT;}
		
          try {
            ids.Name = UALLowLevel.getString(expIdx, path, "Name");
          } catch(Exception exc){ids.Name = null;}
		
          try {
            ids.Channels = UALLowLevel.getVect1DString(expIdx, path, "Channels");
          } catch(Exception exc){ids.Channels = null;}
		
          try {
            ids.Power = UALLowLevel.getVect2DDouble(expIdx, path, "Power");
          } catch(Exception exc){ids.Power = null;}
		
          try {
            ids.Generic_Signal = UALLowLevel.getVect2DDouble(expIdx, path, "Generic_Signal");
          } catch(Exception exc){ids.Generic_Signal = null;}
		
          try {
            ids.Code_Parameters.Code_Name = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Name");
          } catch(Exception exc){ids.Code_Parameters.Code_Name = null;}
		
          try {
            ids.Code_Parameters.Code_Version = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Version");
          } catch(Exception exc){ids.Code_Parameters.Code_Version = null;}
		
          try {
            ids.Code_Parameters.Parameters = UALLowLevel.getVect1DString(expIdx, path, "Code_Parameters" + "/Parameters");
          } catch(Exception exc){ids.Code_Parameters.Parameters = null;}
		
          try {
            ids.Code_Parameters.Output_Diagnostics = UALLowLevel.getVect1DString(expIdx, path, "Code_Parameters" + "/Output_Diagnostics");
          } catch(Exception exc){ids.Code_Parameters.Output_Diagnostics = null;}
		
          try {
            ids.Code_Parameters.Output_Flag = UALLowLevel.getVect1DInt(expIdx, path, "Code_Parameters" + "/Output_Flag");
          } catch(Exception exc){ids.Code_Parameters.Output_Flag = null;}
		
          try {
            ids.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Timebase");
          } catch(Exception exc){ids.Timebase = null;}
		
      UALLowLevel.endIDSGet(expIdx, path);
      return ids;
    }

 /**
 * Method getSlice retrieves the  Actuator IDS in the open database corresponding to the passed time, based on the selectted interpolation mode.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param time The retrieval time.
 * @param interpolMode Defines the selected interpolation. Allowed values are imas.INTERPOLATION, imas.CLOSEST_SAMPLE,
 * imas.PREVIOUS_SAMPLE
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the selected Actuator ids. Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if double and empty String is string.
 **/
    public static Actuator  getSlice(int expIdx, String path, double time, int interpolMode) throws UALException
    {
      String        timebasepath;
      String        ual_debug = System.getenv("ual_debug");
      double        retTime;
      Actuator ids = new Actuator (); 
      retTime = UALLowLevel.beginIDSGetSlice(expIdx, path, time);

          try {
            ids.IDS_Properties.Status_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Status_of");
          } catch(Exception exc){ids.IDS_Properties.Status_of = null;}
		
          try {
            ids.IDS_Properties.Comment_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Comment_of");
          } catch(Exception exc){ids.IDS_Properties.Comment_of = null;}
		
          try {
            ids.IDS_Properties.Creator_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Creator_of");
          } catch(Exception exc){ids.IDS_Properties.Creator_of = null;}
		
          try {
            ids.IDS_Properties.Date_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Date_of");
          } catch(Exception exc){ids.IDS_Properties.Date_of = null;}
		
          try {
            ids.IDS_Properties.Source_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Source_of");
          } catch(Exception exc){ids.IDS_Properties.Source_of = null;}
		
          try {
            ids.IDS_Properties.Reference_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Reference_of");
          } catch(Exception exc){ids.IDS_Properties.Reference_of = null;}
		
          try {
            ids.IDS_Properties.Homogeneous_Timebase = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase");
          } catch(Exception exc){ids.IDS_Properties.Homogeneous_Timebase = EMPTY_INT;}
		
          try {
            ids.IDS_Properties.cocos = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/cocos");
          } catch(Exception exc){ids.IDS_Properties.cocos = EMPTY_INT;}
		
          try {
            ids.Name = UALLowLevel.getString(expIdx, path, "Name");
          } catch(Exception exc){ids.Name = null;}
		
          try {
            ids.Channels = UALLowLevel.getVect1DString(expIdx, path, "Channels");
          } catch(Exception exc){ids.Channels = null;}
		
// Get Slice Power
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Power");
	    if (obj.getElementAt(0) > 0) {
              ids.Power = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Power.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Power", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Power = null;}
		
// Get Slice Generic_Signal
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Generic_Signal");
	    if (obj.getElementAt(0) > 0) {
              ids.Generic_Signal = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Generic_Signal.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Generic_Signal", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Generic_Signal = null;}
		
          try {
            ids.Code_Parameters.Code_Name = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Name");
          } catch(Exception exc){ids.Code_Parameters.Code_Name = null;}
		
          try {
            ids.Code_Parameters.Code_Version = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Version");
          } catch(Exception exc){ids.Code_Parameters.Code_Version = null;}
		
// Get Slice Code_Parameters/Parameters
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
            ids.Code_Parameters.Parameters = new Vect1DString(1);
            ids.Code_Parameters.Parameters.setElementAt(0, UALLowLevel.getStringSlice(expIdx, path, "Code_Parameters" + "/Parameters", timebasepath.trim(), time, interpolMode));
          } catch(Exception exc){ids.Code_Parameters.Parameters = null;}
		
// Get Slice Code_Parameters/Output_Diagnostics
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
            ids.Code_Parameters.Output_Diagnostics = new Vect1DString(1);
            ids.Code_Parameters.Output_Diagnostics.setElementAt(0, UALLowLevel.getStringSlice(expIdx, path, "Code_Parameters" + "/Output_Diagnostics", timebasepath.trim(), time, interpolMode));
          } catch(Exception exc){ids.Code_Parameters.Output_Diagnostics = null;}
		
// Get Slice Code_Parameters/Output_Flag
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Code_Parameters" + "/Output_Flag");
	    if (obj.getElementAt(0) > 0) {
              ids.Code_Parameters.Output_Flag = new Vect1DInt(1);
              ids.Code_Parameters.Output_Flag.setElementAt(0, UALLowLevel.getIntSlice(expIdx, path, "Code_Parameters" + "/Output_Flag", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Code_Parameters.Output_Flag = null;}
		
// Get Slice Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Timebase = new Vect1DDouble(1);
              ids.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "/Timebase", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Timebase = null;}
		
      UALLowLevel.endIDSGetSlice(expIdx, path);
      return ids;
    }
    
 /**
 * Method delete removes all the data associated with a Actuator IDS in the open database.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void delete(int expIdx, String path) throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");
      
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Status_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Comment_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Creator_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Date_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Source_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Reference_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Homogeneous_Timebase");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/cocos");          
      UALLowLevel.deleteData(expIdx, path, "Name");  
      UALLowLevel.deleteData(expIdx, path, "Channels");  
      UALLowLevel.deleteData(expIdx, path, "Power");  
      UALLowLevel.deleteData(expIdx, path, "Generic_Signal");  
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Code_Name");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Code_Version");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Parameters");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Output_Diagnostics");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Output_Flag");          
      UALLowLevel.deleteData(expIdx, path, "Timebase");  
    }


     
 /**
 * Method dump is used for debugging and prints the content of the object
 **/
  public void dump()
  {
    System.out.println("***** Actuator *****");
    

  System.out.println("IDS_Properties ");
  

  System.out.println("Status_of ");
  
        if(IDS_Properties.Status_of!= null)
            System.out.println(IDS_Properties.Status_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Comment_of ");
  
        if(IDS_Properties.Comment_of!= null)
            System.out.println(IDS_Properties.Comment_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Creator_of ");
  
        if(IDS_Properties.Creator_of!= null)
            System.out.println(IDS_Properties.Creator_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Date_of ");
  
        if(IDS_Properties.Date_of!= null)
            System.out.println(IDS_Properties.Date_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Source_of ");
  
        if(IDS_Properties.Source_of!= null)
            System.out.println(IDS_Properties.Source_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Reference_of ");
  
        if(IDS_Properties.Reference_of!= null)
            System.out.println(IDS_Properties.Reference_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Homogeneous_Timebase ");
  
        if(IDS_Properties.Homogeneous_Timebase != EMPTY_INT)
            System.out.println(IDS_Properties.Homogeneous_Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("cocos ");
  
        if(IDS_Properties.cocos != EMPTY_INT)
            System.out.println(IDS_Properties.cocos);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Name ");
  
        if(Name!= null)
            System.out.println(Name);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Channels ");
  
        if(Channels != null)
            System.out.println(Channels);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Power ");
  
        if(Power != null)
            System.out.println(Power);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Generic_Signal ");
  
        if(Generic_Signal != null)
            System.out.println(Generic_Signal);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Code_Parameters ");
  

  System.out.println("Code_Name ");
  
        if(Code_Parameters.Code_Name!= null)
            System.out.println(Code_Parameters.Code_Name);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Code_Version ");
  
        if(Code_Parameters.Code_Version!= null)
            System.out.println(Code_Parameters.Code_Version);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Parameters ");
  
        if(Code_Parameters.Parameters != null)
            System.out.println(Code_Parameters.Parameters);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Output_Diagnostics ");
  
        if(Code_Parameters.Output_Diagnostics != null)
            System.out.println(Code_Parameters.Output_Diagnostics);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Output_Flag ");
  
        if(Code_Parameters.Output_Flag != null)
            System.out.println(Code_Parameters.Output_Flag);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Timebase ");
  
         if(Timebase != null)
            System.out.println(Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   
    System.out.println("******************");
  }
}


public static class Core_Profiles
{
    
      public static class IDS_PropertiesClass {
      
      public String Status_of;
    
      public String Comment_of;
    
      public String Creator_of;
    
      public String Date_of;
    
      public String Source_of;
    
      public String Reference_of;
    
      public int Homogeneous_Timebase = EMPTY_INT;
    
      public int cocos = EMPTY_INT;
    
      }
      public IDS_PropertiesClass IDS_Properties = new IDS_PropertiesClass();
    
      public Vect2DDouble  Rho_Tor_Norm;
    
      public static class Vacuum_Toroidal_FieldClass {
      
      public double R0 = EMPTY_DOUBLE;
    
      public Vect1DDouble B0;
    
      }
      public Vacuum_Toroidal_FieldClass Vacuum_Toroidal_Field = new Vacuum_Toroidal_FieldClass();
    
      public static class Plasma_CompositionClass {
      
      public static class IonsClass {
      
      public double A = EMPTY_DOUBLE;
    
      public double Zion = EMPTY_DOUBLE;
    
      public double Zn = EMPTY_DOUBLE;
    
      public String Label;
    
      public int Multiple_Charge_State_Flag = EMPTY_INT;
    
      public static class Charge_StatesClass {
      
      public double Z_Min = EMPTY_DOUBLE;
    
      public double Z_Max = EMPTY_DOUBLE;
    
      public String Label;
    
      }
      public Charge_StatesClass Charge_States[];
    
      }
      public IonsClass Ions[];
    
      public static class NeutralsClass {
      
      public static class ComponentsClass {
      
      public double A = EMPTY_DOUBLE;
    
      public double Zn = EMPTY_DOUBLE;
    
      public double Multiplicity = EMPTY_DOUBLE;
    
      }
      public ComponentsClass Components[];
    
      public Vect1DInt Energies;
    
      public String Label;
    
      }
      public NeutralsClass Neutrals[];
    
      }
      public Plasma_CompositionClass Plasma_Composition = new Plasma_CompositionClass();
    
      public Vect2DDouble  Psi;
    
      public Vect2DDouble  Te;
    
      public Vect3DDouble  Ti;
    
      public Vect2DDouble  Ne;
    
      public Vect2DDouble  Ne_Fast;
    
      public Vect3DDouble  Ni;
    
      public Vect3DDouble  Ni_Fast;
    
      public Vect2DDouble  Ni_Total_Over_Ne;
    
      public Vect2DDouble  Zeff;
    
      public Vect2DDouble  Pe;
    
      public Vect3DDouble  Pi;
    
      public Vect2DDouble  Pi_Total;
    
      public Vect2DDouble  Pressure_Thermal;
    
      public Vect2DDouble  Pressure_Perpendicular;
    
      public Vect2DDouble  Pressure_Parallel;
    
      public Vect2DDouble  J_Total;
    
      public Vect2DDouble  J_Tor;
    
      public Vect2DDouble  J_Ohmic;
    
      public Vect2DDouble  J_Non_Inductive;
    
      public Vect2DDouble  J_Bootstrap;
    
      public Vect2DDouble  Conductivity_Parallel;
    
      public Vect2DDouble  E_Parallel;
    
      public Vect2DDouble  q;
    
      public Vect2DDouble  Magnetic_Shear;
    
      public Vect3DDouble  Momentum_Tor;
    
      public Vect3DDouble  Rotation_Frequency_Tor;
    
      public static class GlobalClass {
      
      public Vect1DDouble Ip;
    
      public Vect1DDouble Current_Non_Inductive;
    
      public Vect1DDouble Current_Bootstrap;
    
      public Vect1DDouble V_Loop;
    
      public Vect1DDouble li_3;
    
      public Vect1DDouble Beta_Tor;
    
      public Vect1DDouble Beta_Tor_Norm;
    
      public Vect1DDouble Beta_Pol;
    
      public Vect1DDouble Energy_Diamagnetic;
    
      }
      public GlobalClass Global = new GlobalClass();
    
      public static class Code_ParametersClass {
      
      public String Code_Name;
    
      public String Code_Version;
    
      public Vect1DString Parameters;
    
      public Vect1DString Output_Diagnostics;
    
      public Vect1DInt Output_Flag;
    
      }
      public Code_ParametersClass Code_Parameters = new Code_ParametersClass();
    
      public Vect1DDouble Timebase;
    

 /**
 * Method copy Core_Profiles copies a full IDS between two databases.
 * @param srcIdx The index of the source database, returned by imas.open()
 * @param srcOccur The occurence of the IDS in the source database.
 * @param destIdx The index of the destination database, returned by imas.open()
 * @param destOccur The occurence of the IDS in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copy(int srcIdx, int srcOccur, int destIdx, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyIds(srcIdx, destIdx, "Core_Profiles", srcOccur, destOccur);
    }

 /**
 * Method copyEnv Core_Profiles copies a full IDS between two databases.
 * @param srcUser User of the source database
 * @param srcTokamak Tokamak of the source database
 * @param srcVersion Version of the source database
 * @param srcShot Shot number of the source database
 * @param srcRun Run number of the source database
 * @param srcOccur The occurence of the IDS in the source database.
 * @param destUser User of the destination database
 * @param destTokamak Tokamak of the destination database
 * @param destVersion Version of the destination database
 * @param destShot Shot number of the destination database
 * @param destRun Run number of the destination database
 * @param destOccur The occurence of the IDS in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copyEnv(String srcUser, String srcTokamak, String srcVersion, int srcShot, int srcRun, int srcOccur, String destUser, String destTokamak, String destVersion, int destShot, int destRun, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyIdsEnv(srcTokamak, srcVersion, srcUser, srcShot, srcRun, srcOccur, destTokamak, destVersion, destUser, destShot, destRun, destOccur, "Core_Profiles");
    }

/**
 * Method put stores a non timed Core_Profiles IDSs in the open database. 
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The passed Core_Profiles ids.
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void put(int expIdx, String path, Core_Profiles ids)  throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
	  Vect5DDouble  vect5DDouble;
	  Vect6DDouble  vect6DDouble;
	  Vect7DDouble  vect7DDouble;
          String        timebasepath;
          String        ual_debug = System.getenv("ual_debug");

          UALLowLevel.beginIDSPut(expIdx, path);
          
// Put IDS_Properties/Status_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Status_of", ids.IDS_Properties.Status_of);

// Put IDS_Properties/Comment_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Comment_of", ids.IDS_Properties.Comment_of);

// Put IDS_Properties/Creator_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Creator_of", ids.IDS_Properties.Creator_of);

// Put IDS_Properties/Date_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Date_of", ids.IDS_Properties.Date_of);

// Put IDS_Properties/Source_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Source_of", ids.IDS_Properties.Source_of);

// Put IDS_Properties/Reference_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Reference_of", ids.IDS_Properties.Reference_of);

// Put IDS_Properties/Homogeneous_Timebase
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase", ids.IDS_Properties.Homogeneous_Timebase);

// Put IDS_Properties/cocos
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/cocos", ids.IDS_Properties.cocos);

// Put Rho_Tor_Norm
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Rho_Tor_Norm", timebasepath.trim(), ids.Rho_Tor_Norm, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Vacuum_Toroidal_Field/R0
          UALLowLevel.putDouble(expIdx, path, "Vacuum_Toroidal_Field" + "/R0", ids.Vacuum_Toroidal_Field.R0);

// Put Vacuum_Toroidal_Field/B0
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Vacuum_Toroidal_Field" + "/B0", timebasepath.trim(), ids.Vacuum_Toroidal_Field.B0, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Plasma_Composition/Ions
          if (ids.Plasma_Composition.Ions != null) {
            UALLowLevel.putInt(expIdx,path, "Plasma_Composition"+"/Ions/Shape_of",
       		ids.Plasma_Composition.Ions.length);
             for (int iIons = 0; iIons<ids.Plasma_Composition.Ions.length; iIons++){
      
// Put Plasma_Composition/Ions/A
          UALLowLevel.putDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/A", ids.Plasma_Composition.Ions[iIons].A);

// Put Plasma_Composition/Ions/Zion
          UALLowLevel.putDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Zion", ids.Plasma_Composition.Ions[iIons].Zion);

// Put Plasma_Composition/Ions/Zn
          UALLowLevel.putDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Zn", ids.Plasma_Composition.Ions[iIons].Zn);

// Put Plasma_Composition/Ions/Label
          UALLowLevel.putString(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Label", ids.Plasma_Composition.Ions[iIons].Label);

// Put Plasma_Composition/Ions/Multiple_Charge_State_Flag
          UALLowLevel.putInt(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Multiple_Charge_State_Flag", ids.Plasma_Composition.Ions[iIons].Multiple_Charge_State_Flag);

// Put Plasma_Composition/Ions/Charge_States
          if (ids.Plasma_Composition.Ions[iIons].Charge_States != null) {
            UALLowLevel.putInt(expIdx,path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/Shape_of",
       		ids.Plasma_Composition.Ions[iIons].Charge_States.length);
             for (int iCharge_States = 0; iCharge_States<ids.Plasma_Composition.Ions[iIons].Charge_States.length; iCharge_States++){
      
// Put Plasma_Composition/Ions/Charge_States/Z_Min
          UALLowLevel.putDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/" + Integer.toString(iCharge_States+1).trim() + "/Z_Min", ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Z_Min);

// Put Plasma_Composition/Ions/Charge_States/Z_Max
          UALLowLevel.putDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/" + Integer.toString(iCharge_States+1).trim() + "/Z_Max", ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Z_Max);

// Put Plasma_Composition/Ions/Charge_States/Label
          UALLowLevel.putString(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/" + Integer.toString(iCharge_States+1).trim() + "/Label", ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Label);

             }
          }

             }
          }

// Put Plasma_Composition/Neutrals
          if (ids.Plasma_Composition.Neutrals != null) {
            UALLowLevel.putInt(expIdx,path, "Plasma_Composition"+"/Neutrals/Shape_of",
       		ids.Plasma_Composition.Neutrals.length);
             for (int iNeutrals = 0; iNeutrals<ids.Plasma_Composition.Neutrals.length; iNeutrals++){
      
// Put Plasma_Composition/Neutrals/Components
          if (ids.Plasma_Composition.Neutrals[iNeutrals].Components != null) {
            UALLowLevel.putInt(expIdx,path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/Shape_of",
       		ids.Plasma_Composition.Neutrals[iNeutrals].Components.length);
             for (int iComponents = 0; iComponents<ids.Plasma_Composition.Neutrals[iNeutrals].Components.length; iComponents++){
      
// Put Plasma_Composition/Neutrals/Components/A
          UALLowLevel.putDouble(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/" + Integer.toString(iComponents+1).trim() + "/A", ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].A);

// Put Plasma_Composition/Neutrals/Components/Zn
          UALLowLevel.putDouble(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/" + Integer.toString(iComponents+1).trim() + "/Zn", ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].Zn);

// Put Plasma_Composition/Neutrals/Components/Multiplicity
          UALLowLevel.putDouble(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/" + Integer.toString(iComponents+1).trim() + "/Multiplicity", ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].Multiplicity);

             }
          }

// Put Plasma_Composition/Neutrals/Energies   
          UALLowLevel.putVect1DInt(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim() + "/Energies", "", ids.Plasma_Composition.Neutrals[iNeutrals].Energies, false);
   		
// Put Plasma_Composition/Neutrals/Label
          UALLowLevel.putString(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim() + "/Label", ids.Plasma_Composition.Neutrals[iNeutrals].Label);

             }
          }

// Put Psi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Psi", timebasepath.trim(), ids.Psi, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Te
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Te", timebasepath.trim(), ids.Te, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Ti
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect3DDouble(expIdx,path, "Ti", timebasepath.trim(), ids.Ti, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Ne
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Ne", timebasepath.trim(), ids.Ne, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Ne_Fast
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Ne_Fast", timebasepath.trim(), ids.Ne_Fast, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Ni
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect3DDouble(expIdx,path, "Ni", timebasepath.trim(), ids.Ni, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Ni_Fast
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect3DDouble(expIdx,path, "Ni_Fast", timebasepath.trim(), ids.Ni_Fast, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Ni_Total_Over_Ne
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Ni_Total_Over_Ne", timebasepath.trim(), ids.Ni_Total_Over_Ne, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Zeff
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Zeff", timebasepath.trim(), ids.Zeff, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Pe
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Pe", timebasepath.trim(), ids.Pe, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Pi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect3DDouble(expIdx,path, "Pi", timebasepath.trim(), ids.Pi, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Pi_Total
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Pi_Total", timebasepath.trim(), ids.Pi_Total, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Pressure_Thermal
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Pressure_Thermal", timebasepath.trim(), ids.Pressure_Thermal, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Pressure_Perpendicular
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Pressure_Perpendicular", timebasepath.trim(), ids.Pressure_Perpendicular, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Pressure_Parallel
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Pressure_Parallel", timebasepath.trim(), ids.Pressure_Parallel, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put J_Total
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "J_Total", timebasepath.trim(), ids.J_Total, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put J_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "J_Tor", timebasepath.trim(), ids.J_Tor, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put J_Ohmic
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "J_Ohmic", timebasepath.trim(), ids.J_Ohmic, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put J_Non_Inductive
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "J_Non_Inductive", timebasepath.trim(), ids.J_Non_Inductive, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put J_Bootstrap
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "J_Bootstrap", timebasepath.trim(), ids.J_Bootstrap, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Conductivity_Parallel
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Conductivity_Parallel", timebasepath.trim(), ids.Conductivity_Parallel, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put E_Parallel
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "E_Parallel", timebasepath.trim(), ids.E_Parallel, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put q
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "q", timebasepath.trim(), ids.q, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Magnetic_Shear
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Magnetic_Shear", timebasepath.trim(), ids.Magnetic_Shear, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Momentum_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect3DDouble(expIdx,path, "Momentum_Tor", timebasepath.trim(), ids.Momentum_Tor, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Rotation_Frequency_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect3DDouble(expIdx,path, "Rotation_Frequency_Tor", timebasepath.trim(), ids.Rotation_Frequency_Tor, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Ip
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Ip", timebasepath.trim(), ids.Global.Ip, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Current_Non_Inductive
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Current_Non_Inductive", timebasepath.trim(), ids.Global.Current_Non_Inductive, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Current_Bootstrap
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Current_Bootstrap", timebasepath.trim(), ids.Global.Current_Bootstrap, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/V_Loop
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/V_Loop", timebasepath.trim(), ids.Global.V_Loop, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/li_3
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/li_3", timebasepath.trim(), ids.Global.li_3, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Beta_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Beta_Tor", timebasepath.trim(), ids.Global.Beta_Tor, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Beta_Tor_Norm
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Beta_Tor_Norm", timebasepath.trim(), ids.Global.Beta_Tor_Norm, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Beta_Pol
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Beta_Pol", timebasepath.trim(), ids.Global.Beta_Pol, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Energy_Diamagnetic
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Energy_Diamagnetic", timebasepath.trim(), ids.Global.Energy_Diamagnetic, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Code_Parameters/Code_Name
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Name", ids.Code_Parameters.Code_Name);

// Put Code_Parameters/Code_Version
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Version", ids.Code_Parameters.Code_Version);

// Put Code_Parameters/Parameters
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DString(expIdx, path, "Code_Parameters" + "/Parameters", timebasepath.trim(), ids.Code_Parameters.Parameters, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Code_Parameters/Output_Diagnostics
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DString(expIdx, path, "Code_Parameters" + "/Output_Diagnostics", timebasepath.trim(), ids.Code_Parameters.Output_Diagnostics, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Code_Parameters/Output_Flag
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DInt(expIdx, path, "Code_Parameters" + "/Output_Flag", timebasepath.trim(), ids.Code_Parameters.Output_Flag, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect1DDouble(expIdx,path, "Timebase", timebasepath.trim(), ids.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
          UALLowLevel.endIDSPut(expIdx, path);
    }

 /**
 * Method putNonTimed stores the non time dependent fields of a (timed) Core_Profiles IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The timed Core_Profiles IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void putNonTimed(int expIdx, String path, Core_Profiles ids) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
          Vect5DDouble  vect5DDouble;
          Vect6DDouble  vect6DDouble;
          Vect7DDouble  vect7DDouble;
          String        ual_debug = System.getenv("ual_debug");
          
          delete(expIdx, path);
          UALLowLevel.beginIDSPutNonTimed(expIdx, path);
          
// Put IDS_Properties/Status_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Status_of", ids.IDS_Properties.Status_of);

// Put IDS_Properties/Comment_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Comment_of", ids.IDS_Properties.Comment_of);

// Put IDS_Properties/Creator_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Creator_of", ids.IDS_Properties.Creator_of);

// Put IDS_Properties/Date_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Date_of", ids.IDS_Properties.Date_of);

// Put IDS_Properties/Source_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Source_of", ids.IDS_Properties.Source_of);

// Put IDS_Properties/Reference_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Reference_of", ids.IDS_Properties.Reference_of);

// Put IDS_Properties/Homogeneous_Timebase
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase", ids.IDS_Properties.Homogeneous_Timebase);

// Put IDS_Properties/cocos
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/cocos", ids.IDS_Properties.cocos);

// Put Vacuum_Toroidal_Field/R0
          UALLowLevel.putDouble(expIdx, path, "Vacuum_Toroidal_Field" + "/R0", ids.Vacuum_Toroidal_Field.R0);

// Put Plasma_Composition/Ions
          if (ids.Plasma_Composition.Ions != null) {
            UALLowLevel.putInt(expIdx,path, "Plasma_Composition"+"/Ions/Shape_of",
       		ids.Plasma_Composition.Ions.length);
             for (int iIons = 0; iIons<ids.Plasma_Composition.Ions.length; iIons++){
      
// Put Plasma_Composition/Ions/A
          UALLowLevel.putDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/A", ids.Plasma_Composition.Ions[iIons].A);

// Put Plasma_Composition/Ions/Zion
          UALLowLevel.putDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Zion", ids.Plasma_Composition.Ions[iIons].Zion);

// Put Plasma_Composition/Ions/Zn
          UALLowLevel.putDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Zn", ids.Plasma_Composition.Ions[iIons].Zn);

// Put Plasma_Composition/Ions/Label
          UALLowLevel.putString(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Label", ids.Plasma_Composition.Ions[iIons].Label);

// Put Plasma_Composition/Ions/Multiple_Charge_State_Flag
          UALLowLevel.putInt(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Multiple_Charge_State_Flag", ids.Plasma_Composition.Ions[iIons].Multiple_Charge_State_Flag);

// Put Plasma_Composition/Ions/Charge_States
          if (ids.Plasma_Composition.Ions[iIons].Charge_States != null) {
            UALLowLevel.putInt(expIdx,path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/Shape_of",
       		ids.Plasma_Composition.Ions[iIons].Charge_States.length);
             for (int iCharge_States = 0; iCharge_States<ids.Plasma_Composition.Ions[iIons].Charge_States.length; iCharge_States++){
      
// Put Plasma_Composition/Ions/Charge_States/Z_Min
          UALLowLevel.putDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/" + Integer.toString(iCharge_States+1).trim() + "/Z_Min", ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Z_Min);

// Put Plasma_Composition/Ions/Charge_States/Z_Max
          UALLowLevel.putDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/" + Integer.toString(iCharge_States+1).trim() + "/Z_Max", ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Z_Max);

// Put Plasma_Composition/Ions/Charge_States/Label
          UALLowLevel.putString(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/" + Integer.toString(iCharge_States+1).trim() + "/Label", ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Label);

             }
          }

             }
          }

// Put Plasma_Composition/Neutrals
          if (ids.Plasma_Composition.Neutrals != null) {
            UALLowLevel.putInt(expIdx,path, "Plasma_Composition"+"/Neutrals/Shape_of",
       		ids.Plasma_Composition.Neutrals.length);
             for (int iNeutrals = 0; iNeutrals<ids.Plasma_Composition.Neutrals.length; iNeutrals++){
      
// Put Plasma_Composition/Neutrals/Components
          if (ids.Plasma_Composition.Neutrals[iNeutrals].Components != null) {
            UALLowLevel.putInt(expIdx,path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/Shape_of",
       		ids.Plasma_Composition.Neutrals[iNeutrals].Components.length);
             for (int iComponents = 0; iComponents<ids.Plasma_Composition.Neutrals[iNeutrals].Components.length; iComponents++){
      
// Put Plasma_Composition/Neutrals/Components/A
          UALLowLevel.putDouble(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/" + Integer.toString(iComponents+1).trim() + "/A", ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].A);

// Put Plasma_Composition/Neutrals/Components/Zn
          UALLowLevel.putDouble(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/" + Integer.toString(iComponents+1).trim() + "/Zn", ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].Zn);

// Put Plasma_Composition/Neutrals/Components/Multiplicity
          UALLowLevel.putDouble(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/" + Integer.toString(iComponents+1).trim() + "/Multiplicity", ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].Multiplicity);

             }
          }

// Put Plasma_Composition/Neutrals/Energies   
          UALLowLevel.putVect1DInt(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim() + "/Energies", "", ids.Plasma_Composition.Neutrals[iNeutrals].Energies, false);
   		
// Put Plasma_Composition/Neutrals/Label
          UALLowLevel.putString(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim() + "/Label", ids.Plasma_Composition.Neutrals[iNeutrals].Label);

             }
          }

// Put Code_Parameters/Code_Name
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Name", ids.Code_Parameters.Code_Name);

// Put Code_Parameters/Code_Version
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Version", ids.Code_Parameters.Code_Version);

          UALLowLevel.endIDSPutNonTimed(expIdx, path);
    }

    
/**
 * Method putSlice appends a (timed) Core_Profiles IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param ids The timed Core_Profiles IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void putSlice(int expIdx, String path, Core_Profiles ids) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
          Vect5DDouble  vect5DDouble;
          Vect6DDouble  vect6DDouble;
          Vect7DDouble  vect7DDouble;
          String        timebasepath;
          String        ual_debug = System.getenv("ual_debug");
          
    	  if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
            System.out.println("ERROR : the PUT_SLICE routine works only for homogeneous timebase IDS");
            return;
          }
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutSlice(expIdx, path);
          
// Put Slice Rho_Tor_Norm
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Rho_Tor_Norm", timebasepath.trim(), ids.Rho_Tor_Norm, ids.Timebase.getElementAt(0));
		
// Put Slice Vacuum_Toroidal_Field/B0
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Vacuum_Toroidal_Field" + "/B0", timebasepath.trim(), ids.Vacuum_Toroidal_Field.B0, ids.Timebase.getElementAt(0));
		
// Put Plasma_Composition/Ions
          if (ids.Plasma_Composition.Ions != null) {
            UALLowLevel.putInt(expIdx,path, "Plasma_Composition"+"/Ions/Shape_of",
       		ids.Plasma_Composition.Ions.length);
             for (int iIons = 0; iIons<ids.Plasma_Composition.Ions.length; iIons++){
      
// Put Plasma_Composition/Ions/Charge_States
          if (ids.Plasma_Composition.Ions[iIons].Charge_States != null) {
            UALLowLevel.putInt(expIdx,path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/Shape_of",
       		ids.Plasma_Composition.Ions[iIons].Charge_States.length);
             for (int iCharge_States = 0; iCharge_States<ids.Plasma_Composition.Ions[iIons].Charge_States.length; iCharge_States++){
      
             }
          }

             }
          }

// Put Plasma_Composition/Neutrals
          if (ids.Plasma_Composition.Neutrals != null) {
            UALLowLevel.putInt(expIdx,path, "Plasma_Composition"+"/Neutrals/Shape_of",
       		ids.Plasma_Composition.Neutrals.length);
             for (int iNeutrals = 0; iNeutrals<ids.Plasma_Composition.Neutrals.length; iNeutrals++){
      
// Put Plasma_Composition/Neutrals/Components
          if (ids.Plasma_Composition.Neutrals[iNeutrals].Components != null) {
            UALLowLevel.putInt(expIdx,path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/Shape_of",
       		ids.Plasma_Composition.Neutrals[iNeutrals].Components.length);
             for (int iComponents = 0; iComponents<ids.Plasma_Composition.Neutrals[iNeutrals].Components.length; iComponents++){
      
             }
          }

             }
          }

// Put Slice Psi
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Psi", timebasepath.trim(), ids.Psi, ids.Timebase.getElementAt(0));
		
// Put Slice Te
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Te", timebasepath.trim(), ids.Te, ids.Timebase.getElementAt(0));
		
// Put Slice Ti
          UALLowLevel.putVect3DDoubleSlice(expIdx,path, "Ti", timebasepath.trim(), ids.Ti, ids.Timebase.getElementAt(0));
		
// Put Slice Ne
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Ne", timebasepath.trim(), ids.Ne, ids.Timebase.getElementAt(0));
		
// Put Slice Ne_Fast
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Ne_Fast", timebasepath.trim(), ids.Ne_Fast, ids.Timebase.getElementAt(0));
		
// Put Slice Ni
          UALLowLevel.putVect3DDoubleSlice(expIdx,path, "Ni", timebasepath.trim(), ids.Ni, ids.Timebase.getElementAt(0));
		
// Put Slice Ni_Fast
          UALLowLevel.putVect3DDoubleSlice(expIdx,path, "Ni_Fast", timebasepath.trim(), ids.Ni_Fast, ids.Timebase.getElementAt(0));
		
// Put Slice Ni_Total_Over_Ne
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Ni_Total_Over_Ne", timebasepath.trim(), ids.Ni_Total_Over_Ne, ids.Timebase.getElementAt(0));
		
// Put Slice Zeff
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Zeff", timebasepath.trim(), ids.Zeff, ids.Timebase.getElementAt(0));
		
// Put Slice Pe
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Pe", timebasepath.trim(), ids.Pe, ids.Timebase.getElementAt(0));
		
// Put Slice Pi
          UALLowLevel.putVect3DDoubleSlice(expIdx,path, "Pi", timebasepath.trim(), ids.Pi, ids.Timebase.getElementAt(0));
		
// Put Slice Pi_Total
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Pi_Total", timebasepath.trim(), ids.Pi_Total, ids.Timebase.getElementAt(0));
		
// Put Slice Pressure_Thermal
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Pressure_Thermal", timebasepath.trim(), ids.Pressure_Thermal, ids.Timebase.getElementAt(0));
		
// Put Slice Pressure_Perpendicular
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Pressure_Perpendicular", timebasepath.trim(), ids.Pressure_Perpendicular, ids.Timebase.getElementAt(0));
		
// Put Slice Pressure_Parallel
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Pressure_Parallel", timebasepath.trim(), ids.Pressure_Parallel, ids.Timebase.getElementAt(0));
		
// Put Slice J_Total
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "J_Total", timebasepath.trim(), ids.J_Total, ids.Timebase.getElementAt(0));
		
// Put Slice J_Tor
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "J_Tor", timebasepath.trim(), ids.J_Tor, ids.Timebase.getElementAt(0));
		
// Put Slice J_Ohmic
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "J_Ohmic", timebasepath.trim(), ids.J_Ohmic, ids.Timebase.getElementAt(0));
		
// Put Slice J_Non_Inductive
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "J_Non_Inductive", timebasepath.trim(), ids.J_Non_Inductive, ids.Timebase.getElementAt(0));
		
// Put Slice J_Bootstrap
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "J_Bootstrap", timebasepath.trim(), ids.J_Bootstrap, ids.Timebase.getElementAt(0));
		
// Put Slice Conductivity_Parallel
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Conductivity_Parallel", timebasepath.trim(), ids.Conductivity_Parallel, ids.Timebase.getElementAt(0));
		
// Put Slice E_Parallel
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "E_Parallel", timebasepath.trim(), ids.E_Parallel, ids.Timebase.getElementAt(0));
		
// Put Slice q
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "q", timebasepath.trim(), ids.q, ids.Timebase.getElementAt(0));
		
// Put Slice Magnetic_Shear
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Magnetic_Shear", timebasepath.trim(), ids.Magnetic_Shear, ids.Timebase.getElementAt(0));
		
// Put Slice Momentum_Tor
          UALLowLevel.putVect3DDoubleSlice(expIdx,path, "Momentum_Tor", timebasepath.trim(), ids.Momentum_Tor, ids.Timebase.getElementAt(0));
		
// Put Slice Rotation_Frequency_Tor
          UALLowLevel.putVect3DDoubleSlice(expIdx,path, "Rotation_Frequency_Tor", timebasepath.trim(), ids.Rotation_Frequency_Tor, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Ip
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Ip", timebasepath.trim(), ids.Global.Ip, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Current_Non_Inductive
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Current_Non_Inductive", timebasepath.trim(), ids.Global.Current_Non_Inductive, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Current_Bootstrap
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Current_Bootstrap", timebasepath.trim(), ids.Global.Current_Bootstrap, ids.Timebase.getElementAt(0));
		
// Put Slice Global/V_Loop
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/V_Loop", timebasepath.trim(), ids.Global.V_Loop, ids.Timebase.getElementAt(0));
		
// Put Slice Global/li_3
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/li_3", timebasepath.trim(), ids.Global.li_3, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Beta_Tor
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Beta_Tor", timebasepath.trim(), ids.Global.Beta_Tor, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Beta_Tor_Norm
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Beta_Tor_Norm", timebasepath.trim(), ids.Global.Beta_Tor_Norm, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Beta_Pol
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Beta_Pol", timebasepath.trim(), ids.Global.Beta_Pol, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Energy_Diamagnetic
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Energy_Diamagnetic", timebasepath.trim(), ids.Global.Energy_Diamagnetic, ids.Timebase.getElementAt(0));
		
// Put Slice Code_Parameters/Parameters
          UALLowLevel.putVect1DStringSlice(expIdx, path, "Code_Parameters" + "/Parameters", timebasepath.trim(), ids.Code_Parameters.Parameters, ids.Timebase.getElementAt(0));
		
// Put Slice Code_Parameters/Output_Diagnostics
          UALLowLevel.putVect1DStringSlice(expIdx, path, "Code_Parameters" + "/Output_Diagnostics", timebasepath.trim(), ids.Code_Parameters.Output_Diagnostics, ids.Timebase.getElementAt(0));
		
// Put Slice Code_Parameters/Output_Flag
          UALLowLevel.putVect1DIntSlice(expIdx, path, "Code_Parameters" + "/Output_Flag", timebasepath.trim(), ids.Code_Parameters.Output_Flag, ids.Timebase.getElementAt(0));
		
// Put Slice Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx,path, "Timebase", timebasepath.trim(), ids.Timebase, ids.Timebase.getElementAt(0));
		
          UALLowLevel.endIDSPutSlice(expIdx, path);
    }
    

    

/**
 * Method putdb stores a non timed Core_Profiles IDSs in the open database only if
 *  datainto.isref == 1. In any case it calls afterwards UALLowLevel.putIdsDb() in order to update catalogue
 * @param expIdx The index to the database, returned by imas.open()
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected ids. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @param ids The  Core_Profiles IDSs
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
/*
    public static void putdb(int expIdx, String user, String machine, int shot, int run, 
    	String path, int occurrence, Core_Profiles ids) throws UALException
    {
    	String actPath;
        if(occurrence > 0)
	    actPath = path+"/"+occurrence;
	else
	    actPath = path;
	if(ids.IDS_Properties.isref == 1)
	{
	    put(expIdx, actPath, ids);
	    UALLowLevel.putIdsDb(user, machine, shot, run, path, occurrence, 1,
	    	user, machine, shot, run, occurrence);
	}
	else //isref == 2
	{
	    UALLowLevel.putIdsDb(user, machine, shot, run, path, occurrence, 2,
	    	ids.IDS_Properties.whatref.user,  ids.IDS_Properties.whatref.machine,
		ids.IDS_Properties.whatref.shot, ids.IDS_Properties.whatref.run, 
		ids.IDS_Properties.whatref.occurrence);
	
	}
    }
*/

/**
 * Method getdb retrieves the non timed Core_Profiles IDSs in the open database, taking the correct reference.
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the Core_Profiles IDS . Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if float or double and empty String is string.
 **/
    public static Core_Profiles getdb(String user, String machine, int shot, int run, String version, String path, int occurrence) throws UALException
    {
//		int idx = UALLowLevel.openDb(user, machine, shot, run, path, occurrence);
		int idx = openEnv("ids", shot, run, user, machine, version);
		if (occurrence != 0)
			path = path + "/" + occurrence;
		Core_Profiles retIdss = null;
                try {
                        retIdss = get(idx, path);
                }
                catch(Exception exc) {
                        retIdss = new Core_Profiles ();
                        System.out.println("Return a new Core_Profiles empty IDS after catching exception: "+exc);
                }
		close(idx);
		return retIdss;
    }

/*
    public static void fillIdsRef(Core_Profiles ids, int isRef, String refUser, String refMachine, int refShot, int refRun, int refOccurrence)
    {
        ids.IDS_Properties.isref = isRef;
        if(isRef != 1)
        {
            ids.IDS_Properties.whatref.user = refUser;
                ids.IDS_Properties.whatref.machine = refMachine;
                ids.IDS_Properties.whatref.shot = refShot;
                ids.IDS_Properties.whatref.run = refRun;
                ids.IDS_Properties.whatref.occurrence = refOccurrence;
        }
    } 
*/
            

    public static Core_Profiles  get(int expIdx, String path)  throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");

      Core_Profiles ids = new Core_Profiles (); 
      UALLowLevel.beginIDSGet(expIdx, path, false);
      
          try {
            ids.IDS_Properties.Status_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Status_of");
          } catch(Exception exc){ids.IDS_Properties.Status_of = null;}
		
          try {
            ids.IDS_Properties.Comment_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Comment_of");
          } catch(Exception exc){ids.IDS_Properties.Comment_of = null;}
		
          try {
            ids.IDS_Properties.Creator_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Creator_of");
          } catch(Exception exc){ids.IDS_Properties.Creator_of = null;}
		
          try {
            ids.IDS_Properties.Date_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Date_of");
          } catch(Exception exc){ids.IDS_Properties.Date_of = null;}
		
          try {
            ids.IDS_Properties.Source_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Source_of");
          } catch(Exception exc){ids.IDS_Properties.Source_of = null;}
		
          try {
            ids.IDS_Properties.Reference_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Reference_of");
          } catch(Exception exc){ids.IDS_Properties.Reference_of = null;}
		
          try {
            ids.IDS_Properties.Homogeneous_Timebase = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase");
          } catch(Exception exc){ids.IDS_Properties.Homogeneous_Timebase = EMPTY_INT;}
		
          try {
            ids.IDS_Properties.cocos = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/cocos");
          } catch(Exception exc){ids.IDS_Properties.cocos = EMPTY_INT;}
		
          try {
            ids.Rho_Tor_Norm = UALLowLevel.getVect2DDouble(expIdx, path, "Rho_Tor_Norm");
          } catch(Exception exc){ids.Rho_Tor_Norm = null;}
		
          try {
            ids.Vacuum_Toroidal_Field.R0 = UALLowLevel.getDouble(expIdx, path, "Vacuum_Toroidal_Field" + "/R0");
          } catch(Exception exc){ids.Vacuum_Toroidal_Field.R0 = EMPTY_DOUBLE;}
		
          try {
            ids.Vacuum_Toroidal_Field.B0 = UALLowLevel.getVect1DDouble(expIdx, path, "Vacuum_Toroidal_Field" + "/B0");
          } catch(Exception exc){ids.Vacuum_Toroidal_Field.B0 = null;}
		
// Get Array of Structures Plasma_Composition/Ions
          try {
            ids.Plasma_Composition.Ions = new Plasma_CompositionClass.IonsClass[UALLowLevel.getInt(expIdx,path, "Plasma_Composition"+"/Ions/Shape_of")];
            for (int iIons = 0; iIons<ids.Plasma_Composition.Ions.length; iIons++){
              ids.Plasma_Composition.Ions[iIons] = new Plasma_CompositionClass.IonsClass();

          try {
            ids.Plasma_Composition.Ions[iIons].A = UALLowLevel.getDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/A");
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].A = EMPTY_DOUBLE;}
		
          try {
            ids.Plasma_Composition.Ions[iIons].Zion = UALLowLevel.getDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Zion");
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].Zion = EMPTY_DOUBLE;}
		
          try {
            ids.Plasma_Composition.Ions[iIons].Zn = UALLowLevel.getDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Zn");
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].Zn = EMPTY_DOUBLE;}
		
          try {
            ids.Plasma_Composition.Ions[iIons].Label = UALLowLevel.getString(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Label");
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].Label = null;}
		
          try {
            ids.Plasma_Composition.Ions[iIons].Multiple_Charge_State_Flag = UALLowLevel.getInt(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Multiple_Charge_State_Flag");
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].Multiple_Charge_State_Flag = EMPTY_INT;}
		
// Get Array of Structures Plasma_Composition/Ions/Charge_States
          try {
            ids.Plasma_Composition.Ions[iIons].Charge_States = new Plasma_CompositionClass.IonsClass.Charge_StatesClass[UALLowLevel.getInt(expIdx,path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/Shape_of")];
            for (int iCharge_States = 0; iCharge_States<ids.Plasma_Composition.Ions[iIons].Charge_States.length; iCharge_States++){
              ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States] = new Plasma_CompositionClass.IonsClass.Charge_StatesClass();

          try {
            ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Z_Min = UALLowLevel.getDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/" + Integer.toString(iCharge_States+1).trim() + "/Z_Min");
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Z_Min = EMPTY_DOUBLE;}
		
          try {
            ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Z_Max = UALLowLevel.getDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/" + Integer.toString(iCharge_States+1).trim() + "/Z_Max");
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Z_Max = EMPTY_DOUBLE;}
		
          try {
            ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Label = UALLowLevel.getString(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/" + Integer.toString(iCharge_States+1).trim() + "/Label");
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Label = null;}
		
            }
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].Charge_States = null;}

            }
          } catch(Exception exc){ids.Plasma_Composition.Ions = null;}

// Get Array of Structures Plasma_Composition/Neutrals
          try {
            ids.Plasma_Composition.Neutrals = new Plasma_CompositionClass.NeutralsClass[UALLowLevel.getInt(expIdx,path, "Plasma_Composition"+"/Neutrals/Shape_of")];
            for (int iNeutrals = 0; iNeutrals<ids.Plasma_Composition.Neutrals.length; iNeutrals++){
              ids.Plasma_Composition.Neutrals[iNeutrals] = new Plasma_CompositionClass.NeutralsClass();

// Get Array of Structures Plasma_Composition/Neutrals/Components
          try {
            ids.Plasma_Composition.Neutrals[iNeutrals].Components = new Plasma_CompositionClass.NeutralsClass.ComponentsClass[UALLowLevel.getInt(expIdx,path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/Shape_of")];
            for (int iComponents = 0; iComponents<ids.Plasma_Composition.Neutrals[iNeutrals].Components.length; iComponents++){
              ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents] = new Plasma_CompositionClass.NeutralsClass.ComponentsClass();

          try {
            ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].A = UALLowLevel.getDouble(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/" + Integer.toString(iComponents+1).trim() + "/A");
          } catch(Exception exc){ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].A = EMPTY_DOUBLE;}
		
          try {
            ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].Zn = UALLowLevel.getDouble(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/" + Integer.toString(iComponents+1).trim() + "/Zn");
          } catch(Exception exc){ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].Zn = EMPTY_DOUBLE;}
		
          try {
            ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].Multiplicity = UALLowLevel.getDouble(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/" + Integer.toString(iComponents+1).trim() + "/Multiplicity");
          } catch(Exception exc){ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].Multiplicity = EMPTY_DOUBLE;}
		
            }
          } catch(Exception exc){ids.Plasma_Composition.Neutrals[iNeutrals].Components = null;}

          try {
            ids.Plasma_Composition.Neutrals[iNeutrals].Energies = UALLowLevel.getVect1DInt(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim() + "/Energies");
          } catch(Exception exc){ids.Plasma_Composition.Neutrals[iNeutrals].Energies = null;}
		
          try {
            ids.Plasma_Composition.Neutrals[iNeutrals].Label = UALLowLevel.getString(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim() + "/Label");
          } catch(Exception exc){ids.Plasma_Composition.Neutrals[iNeutrals].Label = null;}
		
            }
          } catch(Exception exc){ids.Plasma_Composition.Neutrals = null;}

          try {
            ids.Psi = UALLowLevel.getVect2DDouble(expIdx, path, "Psi");
          } catch(Exception exc){ids.Psi = null;}
		
          try {
            ids.Te = UALLowLevel.getVect2DDouble(expIdx, path, "Te");
          } catch(Exception exc){ids.Te = null;}
		
          try {
            ids.Ti = UALLowLevel.getVect3DDouble(expIdx, path, "Ti");
          } catch(Exception exc){ids.Ti = null;}
		
          try {
            ids.Ne = UALLowLevel.getVect2DDouble(expIdx, path, "Ne");
          } catch(Exception exc){ids.Ne = null;}
		
          try {
            ids.Ne_Fast = UALLowLevel.getVect2DDouble(expIdx, path, "Ne_Fast");
          } catch(Exception exc){ids.Ne_Fast = null;}
		
          try {
            ids.Ni = UALLowLevel.getVect3DDouble(expIdx, path, "Ni");
          } catch(Exception exc){ids.Ni = null;}
		
          try {
            ids.Ni_Fast = UALLowLevel.getVect3DDouble(expIdx, path, "Ni_Fast");
          } catch(Exception exc){ids.Ni_Fast = null;}
		
          try {
            ids.Ni_Total_Over_Ne = UALLowLevel.getVect2DDouble(expIdx, path, "Ni_Total_Over_Ne");
          } catch(Exception exc){ids.Ni_Total_Over_Ne = null;}
		
          try {
            ids.Zeff = UALLowLevel.getVect2DDouble(expIdx, path, "Zeff");
          } catch(Exception exc){ids.Zeff = null;}
		
          try {
            ids.Pe = UALLowLevel.getVect2DDouble(expIdx, path, "Pe");
          } catch(Exception exc){ids.Pe = null;}
		
          try {
            ids.Pi = UALLowLevel.getVect3DDouble(expIdx, path, "Pi");
          } catch(Exception exc){ids.Pi = null;}
		
          try {
            ids.Pi_Total = UALLowLevel.getVect2DDouble(expIdx, path, "Pi_Total");
          } catch(Exception exc){ids.Pi_Total = null;}
		
          try {
            ids.Pressure_Thermal = UALLowLevel.getVect2DDouble(expIdx, path, "Pressure_Thermal");
          } catch(Exception exc){ids.Pressure_Thermal = null;}
		
          try {
            ids.Pressure_Perpendicular = UALLowLevel.getVect2DDouble(expIdx, path, "Pressure_Perpendicular");
          } catch(Exception exc){ids.Pressure_Perpendicular = null;}
		
          try {
            ids.Pressure_Parallel = UALLowLevel.getVect2DDouble(expIdx, path, "Pressure_Parallel");
          } catch(Exception exc){ids.Pressure_Parallel = null;}
		
          try {
            ids.J_Total = UALLowLevel.getVect2DDouble(expIdx, path, "J_Total");
          } catch(Exception exc){ids.J_Total = null;}
		
          try {
            ids.J_Tor = UALLowLevel.getVect2DDouble(expIdx, path, "J_Tor");
          } catch(Exception exc){ids.J_Tor = null;}
		
          try {
            ids.J_Ohmic = UALLowLevel.getVect2DDouble(expIdx, path, "J_Ohmic");
          } catch(Exception exc){ids.J_Ohmic = null;}
		
          try {
            ids.J_Non_Inductive = UALLowLevel.getVect2DDouble(expIdx, path, "J_Non_Inductive");
          } catch(Exception exc){ids.J_Non_Inductive = null;}
		
          try {
            ids.J_Bootstrap = UALLowLevel.getVect2DDouble(expIdx, path, "J_Bootstrap");
          } catch(Exception exc){ids.J_Bootstrap = null;}
		
          try {
            ids.Conductivity_Parallel = UALLowLevel.getVect2DDouble(expIdx, path, "Conductivity_Parallel");
          } catch(Exception exc){ids.Conductivity_Parallel = null;}
		
          try {
            ids.E_Parallel = UALLowLevel.getVect2DDouble(expIdx, path, "E_Parallel");
          } catch(Exception exc){ids.E_Parallel = null;}
		
          try {
            ids.q = UALLowLevel.getVect2DDouble(expIdx, path, "q");
          } catch(Exception exc){ids.q = null;}
		
          try {
            ids.Magnetic_Shear = UALLowLevel.getVect2DDouble(expIdx, path, "Magnetic_Shear");
          } catch(Exception exc){ids.Magnetic_Shear = null;}
		
          try {
            ids.Momentum_Tor = UALLowLevel.getVect3DDouble(expIdx, path, "Momentum_Tor");
          } catch(Exception exc){ids.Momentum_Tor = null;}
		
          try {
            ids.Rotation_Frequency_Tor = UALLowLevel.getVect3DDouble(expIdx, path, "Rotation_Frequency_Tor");
          } catch(Exception exc){ids.Rotation_Frequency_Tor = null;}
		
          try {
            ids.Global.Ip = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Ip");
          } catch(Exception exc){ids.Global.Ip = null;}
		
          try {
            ids.Global.Current_Non_Inductive = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Current_Non_Inductive");
          } catch(Exception exc){ids.Global.Current_Non_Inductive = null;}
		
          try {
            ids.Global.Current_Bootstrap = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Current_Bootstrap");
          } catch(Exception exc){ids.Global.Current_Bootstrap = null;}
		
          try {
            ids.Global.V_Loop = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/V_Loop");
          } catch(Exception exc){ids.Global.V_Loop = null;}
		
          try {
            ids.Global.li_3 = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/li_3");
          } catch(Exception exc){ids.Global.li_3 = null;}
		
          try {
            ids.Global.Beta_Tor = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Beta_Tor");
          } catch(Exception exc){ids.Global.Beta_Tor = null;}
		
          try {
            ids.Global.Beta_Tor_Norm = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Beta_Tor_Norm");
          } catch(Exception exc){ids.Global.Beta_Tor_Norm = null;}
		
          try {
            ids.Global.Beta_Pol = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Beta_Pol");
          } catch(Exception exc){ids.Global.Beta_Pol = null;}
		
          try {
            ids.Global.Energy_Diamagnetic = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Energy_Diamagnetic");
          } catch(Exception exc){ids.Global.Energy_Diamagnetic = null;}
		
          try {
            ids.Code_Parameters.Code_Name = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Name");
          } catch(Exception exc){ids.Code_Parameters.Code_Name = null;}
		
          try {
            ids.Code_Parameters.Code_Version = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Version");
          } catch(Exception exc){ids.Code_Parameters.Code_Version = null;}
		
          try {
            ids.Code_Parameters.Parameters = UALLowLevel.getVect1DString(expIdx, path, "Code_Parameters" + "/Parameters");
          } catch(Exception exc){ids.Code_Parameters.Parameters = null;}
		
          try {
            ids.Code_Parameters.Output_Diagnostics = UALLowLevel.getVect1DString(expIdx, path, "Code_Parameters" + "/Output_Diagnostics");
          } catch(Exception exc){ids.Code_Parameters.Output_Diagnostics = null;}
		
          try {
            ids.Code_Parameters.Output_Flag = UALLowLevel.getVect1DInt(expIdx, path, "Code_Parameters" + "/Output_Flag");
          } catch(Exception exc){ids.Code_Parameters.Output_Flag = null;}
		
          try {
            ids.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Timebase");
          } catch(Exception exc){ids.Timebase = null;}
		
      UALLowLevel.endIDSGet(expIdx, path);
      return ids;
    }

 /**
 * Method getSlice retrieves the  Core_Profiles IDS in the open database corresponding to the passed time, based on the selectted interpolation mode.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param time The retrieval time.
 * @param interpolMode Defines the selected interpolation. Allowed values are imas.INTERPOLATION, imas.CLOSEST_SAMPLE,
 * imas.PREVIOUS_SAMPLE
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the selected Core_Profiles ids. Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if double and empty String is string.
 **/
    public static Core_Profiles  getSlice(int expIdx, String path, double time, int interpolMode) throws UALException
    {
      String        timebasepath;
      String        ual_debug = System.getenv("ual_debug");
      double        retTime;
      Core_Profiles ids = new Core_Profiles (); 
      retTime = UALLowLevel.beginIDSGetSlice(expIdx, path, time);

          try {
            ids.IDS_Properties.Status_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Status_of");
          } catch(Exception exc){ids.IDS_Properties.Status_of = null;}
		
          try {
            ids.IDS_Properties.Comment_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Comment_of");
          } catch(Exception exc){ids.IDS_Properties.Comment_of = null;}
		
          try {
            ids.IDS_Properties.Creator_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Creator_of");
          } catch(Exception exc){ids.IDS_Properties.Creator_of = null;}
		
          try {
            ids.IDS_Properties.Date_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Date_of");
          } catch(Exception exc){ids.IDS_Properties.Date_of = null;}
		
          try {
            ids.IDS_Properties.Source_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Source_of");
          } catch(Exception exc){ids.IDS_Properties.Source_of = null;}
		
          try {
            ids.IDS_Properties.Reference_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Reference_of");
          } catch(Exception exc){ids.IDS_Properties.Reference_of = null;}
		
          try {
            ids.IDS_Properties.Homogeneous_Timebase = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase");
          } catch(Exception exc){ids.IDS_Properties.Homogeneous_Timebase = EMPTY_INT;}
		
          try {
            ids.IDS_Properties.cocos = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/cocos");
          } catch(Exception exc){ids.IDS_Properties.cocos = EMPTY_INT;}
		
// Get Slice Rho_Tor_Norm
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Rho_Tor_Norm");
	    if (obj.getElementAt(0) > 0) {
              ids.Rho_Tor_Norm = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Rho_Tor_Norm.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Rho_Tor_Norm", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Rho_Tor_Norm = null;}
		
          try {
            ids.Vacuum_Toroidal_Field.R0 = UALLowLevel.getDouble(expIdx, path, "Vacuum_Toroidal_Field" + "/R0");
          } catch(Exception exc){ids.Vacuum_Toroidal_Field.R0 = EMPTY_DOUBLE;}
		
// Get Slice Vacuum_Toroidal_Field/B0
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Vacuum_Toroidal_Field" + "/B0");
	    if (obj.getElementAt(0) > 0) {
              ids.Vacuum_Toroidal_Field.B0 = new Vect1DDouble(1);
              ids.Vacuum_Toroidal_Field.B0.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Vacuum_Toroidal_Field" + "/B0", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Vacuum_Toroidal_Field.B0 = null;}
		
// Get Array of Structures Plasma_Composition/Ions
          try {
            ids.Plasma_Composition.Ions = new Plasma_CompositionClass.IonsClass[UALLowLevel.getInt(expIdx,path, "Plasma_Composition"+"/Ions/Shape_of")];
            for (int iIons = 0; iIons<ids.Plasma_Composition.Ions.length; iIons++){
              ids.Plasma_Composition.Ions[iIons] = new Plasma_CompositionClass.IonsClass();
			
          try {
            ids.Plasma_Composition.Ions[iIons].A = UALLowLevel.getDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/A");
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].A = EMPTY_DOUBLE;}
		
          try {
            ids.Plasma_Composition.Ions[iIons].Zion = UALLowLevel.getDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Zion");
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].Zion = EMPTY_DOUBLE;}
		
          try {
            ids.Plasma_Composition.Ions[iIons].Zn = UALLowLevel.getDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Zn");
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].Zn = EMPTY_DOUBLE;}
		
          try {
            ids.Plasma_Composition.Ions[iIons].Label = UALLowLevel.getString(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Label");
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].Label = null;}
		
          try {
            ids.Plasma_Composition.Ions[iIons].Multiple_Charge_State_Flag = UALLowLevel.getInt(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Multiple_Charge_State_Flag");
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].Multiple_Charge_State_Flag = EMPTY_INT;}
		
// Get Array of Structures Plasma_Composition/Ions/Charge_States
          try {
            ids.Plasma_Composition.Ions[iIons].Charge_States = new Plasma_CompositionClass.IonsClass.Charge_StatesClass[UALLowLevel.getInt(expIdx,path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/Shape_of")];
            for (int iCharge_States = 0; iCharge_States<ids.Plasma_Composition.Ions[iIons].Charge_States.length; iCharge_States++){
              ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States] = new Plasma_CompositionClass.IonsClass.Charge_StatesClass();
			
          try {
            ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Z_Min = UALLowLevel.getDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/" + Integer.toString(iCharge_States+1).trim() + "/Z_Min");
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Z_Min = EMPTY_DOUBLE;}
		
          try {
            ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Z_Max = UALLowLevel.getDouble(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/" + Integer.toString(iCharge_States+1).trim() + "/Z_Max");
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Z_Max = EMPTY_DOUBLE;}
		
          try {
            ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Label = UALLowLevel.getString(expIdx, path, "Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/" + Integer.toString(iCharge_States+1).trim() + "/Label");
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].Charge_States[iCharge_States].Label = null;}
		
            }
          } catch(Exception exc){ids.Plasma_Composition.Ions[iIons].Charge_States = null;}
		
            }
          } catch(Exception exc){ids.Plasma_Composition.Ions = null;}
		
// Get Array of Structures Plasma_Composition/Neutrals
          try {
            ids.Plasma_Composition.Neutrals = new Plasma_CompositionClass.NeutralsClass[UALLowLevel.getInt(expIdx,path, "Plasma_Composition"+"/Neutrals/Shape_of")];
            for (int iNeutrals = 0; iNeutrals<ids.Plasma_Composition.Neutrals.length; iNeutrals++){
              ids.Plasma_Composition.Neutrals[iNeutrals] = new Plasma_CompositionClass.NeutralsClass();
			
// Get Array of Structures Plasma_Composition/Neutrals/Components
          try {
            ids.Plasma_Composition.Neutrals[iNeutrals].Components = new Plasma_CompositionClass.NeutralsClass.ComponentsClass[UALLowLevel.getInt(expIdx,path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/Shape_of")];
            for (int iComponents = 0; iComponents<ids.Plasma_Composition.Neutrals[iNeutrals].Components.length; iComponents++){
              ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents] = new Plasma_CompositionClass.NeutralsClass.ComponentsClass();
			
          try {
            ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].A = UALLowLevel.getDouble(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/" + Integer.toString(iComponents+1).trim() + "/A");
          } catch(Exception exc){ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].A = EMPTY_DOUBLE;}
		
          try {
            ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].Zn = UALLowLevel.getDouble(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/" + Integer.toString(iComponents+1).trim() + "/Zn");
          } catch(Exception exc){ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].Zn = EMPTY_DOUBLE;}
		
          try {
            ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].Multiplicity = UALLowLevel.getDouble(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/" + Integer.toString(iComponents+1).trim() + "/Multiplicity");
          } catch(Exception exc){ids.Plasma_Composition.Neutrals[iNeutrals].Components[iComponents].Multiplicity = EMPTY_DOUBLE;}
		
            }
          } catch(Exception exc){ids.Plasma_Composition.Neutrals[iNeutrals].Components = null;}
		
          try {
            ids.Plasma_Composition.Neutrals[iNeutrals].Energies = UALLowLevel.getVect1DInt(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim() + "/Energies");
          } catch(Exception exc){ids.Plasma_Composition.Neutrals[iNeutrals].Energies = null;}
		
          try {
            ids.Plasma_Composition.Neutrals[iNeutrals].Label = UALLowLevel.getString(expIdx, path, "Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim() + "/Label");
          } catch(Exception exc){ids.Plasma_Composition.Neutrals[iNeutrals].Label = null;}
		
            }
          } catch(Exception exc){ids.Plasma_Composition.Neutrals = null;}
		
// Get Slice Psi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Psi");
	    if (obj.getElementAt(0) > 0) {
              ids.Psi = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Psi.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Psi", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Psi = null;}
		
// Get Slice Te
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Te");
	    if (obj.getElementAt(0) > 0) {
              ids.Te = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Te.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Te", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Te = null;}
		
// Get Slice Ti
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Ti");
	    if (obj.getElementAt(0) > 0) {
              ids.Ti = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Ti.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "/Ti", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Ti = null;}
		
// Get Slice Ne
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Ne");
	    if (obj.getElementAt(0) > 0) {
              ids.Ne = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Ne.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Ne", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Ne = null;}
		
// Get Slice Ne_Fast
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Ne_Fast");
	    if (obj.getElementAt(0) > 0) {
              ids.Ne_Fast = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Ne_Fast.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Ne_Fast", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Ne_Fast = null;}
		
// Get Slice Ni
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Ni");
	    if (obj.getElementAt(0) > 0) {
              ids.Ni = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Ni.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "/Ni", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Ni = null;}
		
// Get Slice Ni_Fast
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Ni_Fast");
	    if (obj.getElementAt(0) > 0) {
              ids.Ni_Fast = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Ni_Fast.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "/Ni_Fast", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Ni_Fast = null;}
		
// Get Slice Ni_Total_Over_Ne
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Ni_Total_Over_Ne");
	    if (obj.getElementAt(0) > 0) {
              ids.Ni_Total_Over_Ne = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Ni_Total_Over_Ne.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Ni_Total_Over_Ne", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Ni_Total_Over_Ne = null;}
		
// Get Slice Zeff
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Zeff");
	    if (obj.getElementAt(0) > 0) {
              ids.Zeff = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Zeff.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Zeff", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Zeff = null;}
		
// Get Slice Pe
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Pe");
	    if (obj.getElementAt(0) > 0) {
              ids.Pe = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Pe.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Pe", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Pe = null;}
		
// Get Slice Pi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Pi");
	    if (obj.getElementAt(0) > 0) {
              ids.Pi = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Pi.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "/Pi", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Pi = null;}
		
// Get Slice Pi_Total
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Pi_Total");
	    if (obj.getElementAt(0) > 0) {
              ids.Pi_Total = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Pi_Total.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Pi_Total", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Pi_Total = null;}
		
// Get Slice Pressure_Thermal
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Pressure_Thermal");
	    if (obj.getElementAt(0) > 0) {
              ids.Pressure_Thermal = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Pressure_Thermal.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Pressure_Thermal", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Pressure_Thermal = null;}
		
// Get Slice Pressure_Perpendicular
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Pressure_Perpendicular");
	    if (obj.getElementAt(0) > 0) {
              ids.Pressure_Perpendicular = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Pressure_Perpendicular.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Pressure_Perpendicular", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Pressure_Perpendicular = null;}
		
// Get Slice Pressure_Parallel
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Pressure_Parallel");
	    if (obj.getElementAt(0) > 0) {
              ids.Pressure_Parallel = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Pressure_Parallel.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Pressure_Parallel", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Pressure_Parallel = null;}
		
// Get Slice J_Total
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "J_Total");
	    if (obj.getElementAt(0) > 0) {
              ids.J_Total = new Vect2DDouble(obj.getElementAt(0),1);
              ids.J_Total.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/J_Total", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.J_Total = null;}
		
// Get Slice J_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "J_Tor");
	    if (obj.getElementAt(0) > 0) {
              ids.J_Tor = new Vect2DDouble(obj.getElementAt(0),1);
              ids.J_Tor.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/J_Tor", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.J_Tor = null;}
		
// Get Slice J_Ohmic
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "J_Ohmic");
	    if (obj.getElementAt(0) > 0) {
              ids.J_Ohmic = new Vect2DDouble(obj.getElementAt(0),1);
              ids.J_Ohmic.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/J_Ohmic", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.J_Ohmic = null;}
		
// Get Slice J_Non_Inductive
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "J_Non_Inductive");
	    if (obj.getElementAt(0) > 0) {
              ids.J_Non_Inductive = new Vect2DDouble(obj.getElementAt(0),1);
              ids.J_Non_Inductive.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/J_Non_Inductive", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.J_Non_Inductive = null;}
		
// Get Slice J_Bootstrap
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "J_Bootstrap");
	    if (obj.getElementAt(0) > 0) {
              ids.J_Bootstrap = new Vect2DDouble(obj.getElementAt(0),1);
              ids.J_Bootstrap.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/J_Bootstrap", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.J_Bootstrap = null;}
		
// Get Slice Conductivity_Parallel
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Conductivity_Parallel");
	    if (obj.getElementAt(0) > 0) {
              ids.Conductivity_Parallel = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Conductivity_Parallel.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Conductivity_Parallel", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Conductivity_Parallel = null;}
		
// Get Slice E_Parallel
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "E_Parallel");
	    if (obj.getElementAt(0) > 0) {
              ids.E_Parallel = new Vect2DDouble(obj.getElementAt(0),1);
              ids.E_Parallel.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/E_Parallel", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.E_Parallel = null;}
		
// Get Slice q
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "q");
	    if (obj.getElementAt(0) > 0) {
              ids.q = new Vect2DDouble(obj.getElementAt(0),1);
              ids.q.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/q", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.q = null;}
		
// Get Slice Magnetic_Shear
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Magnetic_Shear");
	    if (obj.getElementAt(0) > 0) {
              ids.Magnetic_Shear = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Magnetic_Shear.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Magnetic_Shear", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Magnetic_Shear = null;}
		
// Get Slice Momentum_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Momentum_Tor");
	    if (obj.getElementAt(0) > 0) {
              ids.Momentum_Tor = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Momentum_Tor.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "/Momentum_Tor", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Momentum_Tor = null;}
		
// Get Slice Rotation_Frequency_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Rotation_Frequency_Tor");
	    if (obj.getElementAt(0) > 0) {
              ids.Rotation_Frequency_Tor = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Rotation_Frequency_Tor.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "/Rotation_Frequency_Tor", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Rotation_Frequency_Tor = null;}
		
// Get Slice Global/Ip
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Ip");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Ip = new Vect1DDouble(1);
              ids.Global.Ip.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Ip", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Ip = null;}
		
// Get Slice Global/Current_Non_Inductive
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Current_Non_Inductive");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Current_Non_Inductive = new Vect1DDouble(1);
              ids.Global.Current_Non_Inductive.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Current_Non_Inductive", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Current_Non_Inductive = null;}
		
// Get Slice Global/Current_Bootstrap
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Current_Bootstrap");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Current_Bootstrap = new Vect1DDouble(1);
              ids.Global.Current_Bootstrap.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Current_Bootstrap", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Current_Bootstrap = null;}
		
// Get Slice Global/V_Loop
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/V_Loop");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.V_Loop = new Vect1DDouble(1);
              ids.Global.V_Loop.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/V_Loop", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.V_Loop = null;}
		
// Get Slice Global/li_3
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/li_3");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.li_3 = new Vect1DDouble(1);
              ids.Global.li_3.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/li_3", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.li_3 = null;}
		
// Get Slice Global/Beta_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Beta_Tor");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Beta_Tor = new Vect1DDouble(1);
              ids.Global.Beta_Tor.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Beta_Tor", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Beta_Tor = null;}
		
// Get Slice Global/Beta_Tor_Norm
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Beta_Tor_Norm");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Beta_Tor_Norm = new Vect1DDouble(1);
              ids.Global.Beta_Tor_Norm.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Beta_Tor_Norm", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Beta_Tor_Norm = null;}
		
// Get Slice Global/Beta_Pol
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Beta_Pol");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Beta_Pol = new Vect1DDouble(1);
              ids.Global.Beta_Pol.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Beta_Pol", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Beta_Pol = null;}
		
// Get Slice Global/Energy_Diamagnetic
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Energy_Diamagnetic");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Energy_Diamagnetic = new Vect1DDouble(1);
              ids.Global.Energy_Diamagnetic.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Energy_Diamagnetic", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Energy_Diamagnetic = null;}
		
          try {
            ids.Code_Parameters.Code_Name = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Name");
          } catch(Exception exc){ids.Code_Parameters.Code_Name = null;}
		
          try {
            ids.Code_Parameters.Code_Version = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Version");
          } catch(Exception exc){ids.Code_Parameters.Code_Version = null;}
		
// Get Slice Code_Parameters/Parameters
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
            ids.Code_Parameters.Parameters = new Vect1DString(1);
            ids.Code_Parameters.Parameters.setElementAt(0, UALLowLevel.getStringSlice(expIdx, path, "Code_Parameters" + "/Parameters", timebasepath.trim(), time, interpolMode));
          } catch(Exception exc){ids.Code_Parameters.Parameters = null;}
		
// Get Slice Code_Parameters/Output_Diagnostics
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
            ids.Code_Parameters.Output_Diagnostics = new Vect1DString(1);
            ids.Code_Parameters.Output_Diagnostics.setElementAt(0, UALLowLevel.getStringSlice(expIdx, path, "Code_Parameters" + "/Output_Diagnostics", timebasepath.trim(), time, interpolMode));
          } catch(Exception exc){ids.Code_Parameters.Output_Diagnostics = null;}
		
// Get Slice Code_Parameters/Output_Flag
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Code_Parameters" + "/Output_Flag");
	    if (obj.getElementAt(0) > 0) {
              ids.Code_Parameters.Output_Flag = new Vect1DInt(1);
              ids.Code_Parameters.Output_Flag.setElementAt(0, UALLowLevel.getIntSlice(expIdx, path, "Code_Parameters" + "/Output_Flag", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Code_Parameters.Output_Flag = null;}
		
// Get Slice Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Timebase = new Vect1DDouble(1);
              ids.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "/Timebase", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Timebase = null;}
		
      UALLowLevel.endIDSGetSlice(expIdx, path);
      return ids;
    }
    
 /**
 * Method delete removes all the data associated with a Core_Profiles IDS in the open database.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void delete(int expIdx, String path) throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");
      
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Status_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Comment_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Creator_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Date_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Source_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Reference_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Homogeneous_Timebase");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/cocos");          
      UALLowLevel.deleteData(expIdx, path, "Rho_Tor_Norm");  
      UALLowLevel.deleteData(expIdx,path,"Vacuum_Toroidal_Field" + "/R0");          
      UALLowLevel.deleteData(expIdx,path,"Vacuum_Toroidal_Field" + "/B0");          
      for (int iIons = 0; iIons<5; iIons++){
       
      UALLowLevel.deleteData(expIdx,path,"Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/A");          
      UALLowLevel.deleteData(expIdx,path,"Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Zion");          
      UALLowLevel.deleteData(expIdx,path,"Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Zn");          
      UALLowLevel.deleteData(expIdx,path,"Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Label");          
      UALLowLevel.deleteData(expIdx,path,"Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim() + "/Multiple_Charge_State_Flag");          
      for (int iCharge_States = 0; iCharge_States<10; iCharge_States++){
       
      UALLowLevel.deleteData(expIdx,path,"Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/" + Integer.toString(iCharge_States+1).trim() + "/Z_Min");          
      UALLowLevel.deleteData(expIdx,path,"Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/" + Integer.toString(iCharge_States+1).trim() + "/Z_Max");          
      UALLowLevel.deleteData(expIdx,path,"Plasma_Composition"+"/Ions/" + Integer.toString(iIons+1).trim()+"/Charge_States/" + Integer.toString(iCharge_States+1).trim() + "/Label");          
      }
   
      }
   
      for (int iNeutrals = 0; iNeutrals<5; iNeutrals++){
       
      for (int iComponents = 0; iComponents<10; iComponents++){
       
      UALLowLevel.deleteData(expIdx,path,"Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/" + Integer.toString(iComponents+1).trim() + "/A");          
      UALLowLevel.deleteData(expIdx,path,"Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/" + Integer.toString(iComponents+1).trim() + "/Zn");          
      UALLowLevel.deleteData(expIdx,path,"Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim()+"/Components/" + Integer.toString(iComponents+1).trim() + "/Multiplicity");          
      }
   
      UALLowLevel.deleteData(expIdx,path,"Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim() + "/Energies");          
      UALLowLevel.deleteData(expIdx,path,"Plasma_Composition"+"/Neutrals/" + Integer.toString(iNeutrals+1).trim() + "/Label");          
      }
   
      UALLowLevel.deleteData(expIdx, path, "Psi");  
      UALLowLevel.deleteData(expIdx, path, "Te");  
      UALLowLevel.deleteData(expIdx, path, "Ti");  
      UALLowLevel.deleteData(expIdx, path, "Ne");  
      UALLowLevel.deleteData(expIdx, path, "Ne_Fast");  
      UALLowLevel.deleteData(expIdx, path, "Ni");  
      UALLowLevel.deleteData(expIdx, path, "Ni_Fast");  
      UALLowLevel.deleteData(expIdx, path, "Ni_Total_Over_Ne");  
      UALLowLevel.deleteData(expIdx, path, "Zeff");  
      UALLowLevel.deleteData(expIdx, path, "Pe");  
      UALLowLevel.deleteData(expIdx, path, "Pi");  
      UALLowLevel.deleteData(expIdx, path, "Pi_Total");  
      UALLowLevel.deleteData(expIdx, path, "Pressure_Thermal");  
      UALLowLevel.deleteData(expIdx, path, "Pressure_Perpendicular");  
      UALLowLevel.deleteData(expIdx, path, "Pressure_Parallel");  
      UALLowLevel.deleteData(expIdx, path, "J_Total");  
      UALLowLevel.deleteData(expIdx, path, "J_Tor");  
      UALLowLevel.deleteData(expIdx, path, "J_Ohmic");  
      UALLowLevel.deleteData(expIdx, path, "J_Non_Inductive");  
      UALLowLevel.deleteData(expIdx, path, "J_Bootstrap");  
      UALLowLevel.deleteData(expIdx, path, "Conductivity_Parallel");  
      UALLowLevel.deleteData(expIdx, path, "E_Parallel");  
      UALLowLevel.deleteData(expIdx, path, "q");  
      UALLowLevel.deleteData(expIdx, path, "Magnetic_Shear");  
      UALLowLevel.deleteData(expIdx, path, "Momentum_Tor");  
      UALLowLevel.deleteData(expIdx, path, "Rotation_Frequency_Tor");  
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Ip");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Current_Non_Inductive");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Current_Bootstrap");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/V_Loop");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/li_3");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Beta_Tor");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Beta_Tor_Norm");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Beta_Pol");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Energy_Diamagnetic");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Code_Name");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Code_Version");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Parameters");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Output_Diagnostics");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Output_Flag");          
      UALLowLevel.deleteData(expIdx, path, "Timebase");  
    }


     
 /**
 * Method dump is used for debugging and prints the content of the object
 **/
  public void dump()
  {
    System.out.println("***** Core_Profiles *****");
    

  System.out.println("IDS_Properties ");
  

  System.out.println("Status_of ");
  
        if(IDS_Properties.Status_of!= null)
            System.out.println(IDS_Properties.Status_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Comment_of ");
  
        if(IDS_Properties.Comment_of!= null)
            System.out.println(IDS_Properties.Comment_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Creator_of ");
  
        if(IDS_Properties.Creator_of!= null)
            System.out.println(IDS_Properties.Creator_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Date_of ");
  
        if(IDS_Properties.Date_of!= null)
            System.out.println(IDS_Properties.Date_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Source_of ");
  
        if(IDS_Properties.Source_of!= null)
            System.out.println(IDS_Properties.Source_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Reference_of ");
  
        if(IDS_Properties.Reference_of!= null)
            System.out.println(IDS_Properties.Reference_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Homogeneous_Timebase ");
  
        if(IDS_Properties.Homogeneous_Timebase != EMPTY_INT)
            System.out.println(IDS_Properties.Homogeneous_Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("cocos ");
  
        if(IDS_Properties.cocos != EMPTY_INT)
            System.out.println(IDS_Properties.cocos);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Rho_Tor_Norm ");
  
        if(Rho_Tor_Norm != null)
            System.out.println(Rho_Tor_Norm);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Vacuum_Toroidal_Field ");
  

  System.out.println("R0 ");
  
         if(Vacuum_Toroidal_Field.R0 != EMPTY_DOUBLE)
            System.out.println(Vacuum_Toroidal_Field.R0);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("B0 ");
  
         if(Vacuum_Toroidal_Field.B0 != null)
            System.out.println(Vacuum_Toroidal_Field.B0);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Plasma_Composition ");
  

  System.out.println("Ions ");
  
      if (Plasma_Composition.Ions != null) {
        for (int i1 = 0; i1 < Plasma_Composition.Ions.length; i1++) {
          

  System.out.println("A ");
  
         if(Plasma_Composition.Ions[i1].A != EMPTY_DOUBLE)
            System.out.println(Plasma_Composition.Ions[i1].A);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Zion ");
  
         if(Plasma_Composition.Ions[i1].Zion != EMPTY_DOUBLE)
            System.out.println(Plasma_Composition.Ions[i1].Zion);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Zn ");
  
         if(Plasma_Composition.Ions[i1].Zn != EMPTY_DOUBLE)
            System.out.println(Plasma_Composition.Ions[i1].Zn);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Label ");
  
        if(Plasma_Composition.Ions[i1].Label!= null)
            System.out.println(Plasma_Composition.Ions[i1].Label);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Multiple_Charge_State_Flag ");
  
        if(Plasma_Composition.Ions[i1].Multiple_Charge_State_Flag != EMPTY_INT)
            System.out.println(Plasma_Composition.Ions[i1].Multiple_Charge_State_Flag);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Charge_States ");
  
      if (Plasma_Composition.Ions[i1].Charge_States != null) {
        for (int i2 = 0; i2 < Plasma_Composition.Ions[i1].Charge_States.length; i2++) {
          

  System.out.println("Z_Min ");
  
         if(Plasma_Composition.Ions[i1].Charge_States[i2].Z_Min != EMPTY_DOUBLE)
            System.out.println(Plasma_Composition.Ions[i1].Charge_States[i2].Z_Min);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Z_Max ");
  
         if(Plasma_Composition.Ions[i1].Charge_States[i2].Z_Max != EMPTY_DOUBLE)
            System.out.println(Plasma_Composition.Ions[i1].Charge_States[i2].Z_Max);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Label ");
  
        if(Plasma_Composition.Ions[i1].Charge_States[i2].Label!= null)
            System.out.println(Plasma_Composition.Ions[i1].Charge_States[i2].Label);
        else
            System.out.println("Empty");
        System.out.println("");    
    
        }
      }
    
        }
      }
    

  System.out.println("Neutrals ");
  
      if (Plasma_Composition.Neutrals != null) {
        for (int i1 = 0; i1 < Plasma_Composition.Neutrals.length; i1++) {
          

  System.out.println("Components ");
  
      if (Plasma_Composition.Neutrals[i1].Components != null) {
        for (int i2 = 0; i2 < Plasma_Composition.Neutrals[i1].Components.length; i2++) {
          

  System.out.println("A ");
  
         if(Plasma_Composition.Neutrals[i1].Components[i2].A != EMPTY_DOUBLE)
            System.out.println(Plasma_Composition.Neutrals[i1].Components[i2].A);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Zn ");
  
         if(Plasma_Composition.Neutrals[i1].Components[i2].Zn != EMPTY_DOUBLE)
            System.out.println(Plasma_Composition.Neutrals[i1].Components[i2].Zn);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Multiplicity ");
  
         if(Plasma_Composition.Neutrals[i1].Components[i2].Multiplicity != EMPTY_DOUBLE)
            System.out.println(Plasma_Composition.Neutrals[i1].Components[i2].Multiplicity);
        else
            System.out.println("Empty");
        System.out.println("");    
    
        }
      }
    

  System.out.println("Energies ");
  
        if(Plasma_Composition.Neutrals[i1].Energies != null)
            System.out.println(Plasma_Composition.Neutrals[i1].Energies);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Label ");
  
        if(Plasma_Composition.Neutrals[i1].Label!= null)
            System.out.println(Plasma_Composition.Neutrals[i1].Label);
        else
            System.out.println("Empty");
        System.out.println("");    
    
        }
      }
    

  System.out.println("Psi ");
  
        if(Psi != null)
            System.out.println(Psi);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Te ");
  
        if(Te != null)
            System.out.println(Te);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Ti ");
  
        if(Ti != null)
            System.out.println(Ti);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Ne ");
  
        if(Ne != null)
            System.out.println(Ne);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Ne_Fast ");
  
        if(Ne_Fast != null)
            System.out.println(Ne_Fast);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Ni ");
  
        if(Ni != null)
            System.out.println(Ni);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Ni_Fast ");
  
        if(Ni_Fast != null)
            System.out.println(Ni_Fast);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Ni_Total_Over_Ne ");
  
        if(Ni_Total_Over_Ne != null)
            System.out.println(Ni_Total_Over_Ne);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Zeff ");
  
        if(Zeff != null)
            System.out.println(Zeff);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Pe ");
  
        if(Pe != null)
            System.out.println(Pe);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Pi ");
  
        if(Pi != null)
            System.out.println(Pi);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Pi_Total ");
  
        if(Pi_Total != null)
            System.out.println(Pi_Total);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Pressure_Thermal ");
  
        if(Pressure_Thermal != null)
            System.out.println(Pressure_Thermal);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Pressure_Perpendicular ");
  
        if(Pressure_Perpendicular != null)
            System.out.println(Pressure_Perpendicular);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Pressure_Parallel ");
  
        if(Pressure_Parallel != null)
            System.out.println(Pressure_Parallel);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("J_Total ");
  
        if(J_Total != null)
            System.out.println(J_Total);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("J_Tor ");
  
        if(J_Tor != null)
            System.out.println(J_Tor);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("J_Ohmic ");
  
        if(J_Ohmic != null)
            System.out.println(J_Ohmic);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("J_Non_Inductive ");
  
        if(J_Non_Inductive != null)
            System.out.println(J_Non_Inductive);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("J_Bootstrap ");
  
        if(J_Bootstrap != null)
            System.out.println(J_Bootstrap);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Conductivity_Parallel ");
  
        if(Conductivity_Parallel != null)
            System.out.println(Conductivity_Parallel);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("E_Parallel ");
  
        if(E_Parallel != null)
            System.out.println(E_Parallel);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("q ");
  
        if(q != null)
            System.out.println(q);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Magnetic_Shear ");
  
        if(Magnetic_Shear != null)
            System.out.println(Magnetic_Shear);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Momentum_Tor ");
  
        if(Momentum_Tor != null)
            System.out.println(Momentum_Tor);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Rotation_Frequency_Tor ");
  
        if(Rotation_Frequency_Tor != null)
            System.out.println(Rotation_Frequency_Tor);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Global ");
  

  System.out.println("Ip ");
  
         if(Global.Ip != null)
            System.out.println(Global.Ip);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Current_Non_Inductive ");
  
         if(Global.Current_Non_Inductive != null)
            System.out.println(Global.Current_Non_Inductive);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Current_Bootstrap ");
  
         if(Global.Current_Bootstrap != null)
            System.out.println(Global.Current_Bootstrap);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("V_Loop ");
  
         if(Global.V_Loop != null)
            System.out.println(Global.V_Loop);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("li_3 ");
  
         if(Global.li_3 != null)
            System.out.println(Global.li_3);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Beta_Tor ");
  
         if(Global.Beta_Tor != null)
            System.out.println(Global.Beta_Tor);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Beta_Tor_Norm ");
  
         if(Global.Beta_Tor_Norm != null)
            System.out.println(Global.Beta_Tor_Norm);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Beta_Pol ");
  
         if(Global.Beta_Pol != null)
            System.out.println(Global.Beta_Pol);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Energy_Diamagnetic ");
  
         if(Global.Energy_Diamagnetic != null)
            System.out.println(Global.Energy_Diamagnetic);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Code_Parameters ");
  

  System.out.println("Code_Name ");
  
        if(Code_Parameters.Code_Name!= null)
            System.out.println(Code_Parameters.Code_Name);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Code_Version ");
  
        if(Code_Parameters.Code_Version!= null)
            System.out.println(Code_Parameters.Code_Version);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Parameters ");
  
        if(Code_Parameters.Parameters != null)
            System.out.println(Code_Parameters.Parameters);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Output_Diagnostics ");
  
        if(Code_Parameters.Output_Diagnostics != null)
            System.out.println(Code_Parameters.Output_Diagnostics);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Output_Flag ");
  
        if(Code_Parameters.Output_Flag != null)
            System.out.println(Code_Parameters.Output_Flag);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Timebase ");
  
         if(Timebase != null)
            System.out.println(Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   
    System.out.println("******************");
  }
}


public static class Equilibrium
{
    
      public static class IDS_PropertiesClass {
      
      public String Status_of;
    
      public String Comment_of;
    
      public String Creator_of;
    
      public String Date_of;
    
      public String Source_of;
    
      public String Reference_of;
    
      public int Homogeneous_Timebase = EMPTY_INT;
    
      public int cocos = EMPTY_INT;
    
      }
      public IDS_PropertiesClass IDS_Properties = new IDS_PropertiesClass();
    
      public static class BoundaryClass {
      
      public Vect1DInt Type;
    
      public Vect1DInt N_Outline_Points;
    
      public Vect3DDouble  Outline_RZ;
    
      public Vect2DDouble  Geometric_Axis_RZ;
    
      public Vect1DDouble a_minor;
    
      public Vect1DDouble Elongation;
    
      public Vect1DDouble Elongation_Upper;
    
      public Vect1DDouble Elongation_Lower;
    
      public Vect1DDouble Triangularity_Upper;
    
      public Vect1DDouble Triangularity_Lower;
    
      public Vect1DInt N_X_Points;
    
      public Vect3DDouble  X_Points_RZ;
    
      public Vect2DDouble  Strike_Point_Lower_Left_RZ;
    
      public Vect2DDouble  Strike_Point_Upper_Left_RZ;
    
      public Vect2DDouble  Strike_Point_Lower_Right_RZ;
    
      public Vect2DDouble  Strike_Point_Upper_Right_RZ;
    
      public Vect2DDouble  Active_Limiter_Point_RZ;
    
      }
      public BoundaryClass Boundary = new BoundaryClass();
    
      public static class GlobalClass {
      
      public Vect1DDouble Beta_Pol;
    
      public Vect1DDouble Beta_Tor;
    
      public Vect1DDouble Beta_Normal;
    
      public Vect1DDouble Ip;
    
      public Vect1DDouble li_3;
    
      public Vect1DDouble Volume;
    
      public Vect1DDouble Area;
    
      public Vect1DDouble Psi_Axis;
    
      public Vect1DDouble Psi_Boundary;
    
      public static class Magnetic_AxisClass {
      
      public Vect1DDouble R;
    
      public Vect1DDouble Z;
    
      }
      public Magnetic_AxisClass Magnetic_Axis = new Magnetic_AxisClass();
    
      public Vect1DDouble B_Tor_Axis;
    
      public Vect1DDouble q_Axis;
    
      public Vect1DDouble q_95;
    
      public Vect1DDouble q_Min;
    
      public static class Vacuum_Toroidal_FieldClass {
      
      public double R0 = EMPTY_DOUBLE;
    
      public Vect1DDouble B0;
    
      }
      public Vacuum_Toroidal_FieldClass Vacuum_Toroidal_Field = new Vacuum_Toroidal_FieldClass();
    
      public Vect1DDouble W_MHD;
    
      public Vect1DDouble Gamma;
    
      }
      public GlobalClass Global = new GlobalClass();
    
      public static class Profiles_1DClass {
      
      public Vect2DDouble  Psi;
    
      public Vect2DDouble  Phi;
    
      public Vect2DDouble  Pressure;
    
      public Vect2DDouble  F;
    
      public Vect2DDouble  dPressure_dPsi;
    
      public Vect2DDouble  F_dF_dPsi;
    
      public Vect2DDouble  J_Tor;
    
      public Vect2DDouble  J_Parallel;
    
      public Vect2DDouble  q;
    
      public Vect2DDouble  Magnetic_Shear;
    
      public Vect2DDouble  R_Inboard;
    
      public Vect2DDouble  R_Outboard;
    
      public Vect2DDouble  Rho_Tor;
    
      public Vect2DDouble  dPsi_dRho_Tor;
    
      public Vect2DDouble  Rho_Vol_Norm;
    
      public Vect2DDouble  Elongation;
    
      public Vect2DDouble  Triangularity_Upper;
    
      public Vect2DDouble  Triangularity_Lower;
    
      public Vect2DDouble  Volume;
    
      public Vect2DDouble  dVolume_dPsi;
    
      public Vect2DDouble  dVolume_dRho_Tor;
    
      public Vect2DDouble  Area;
    
      public Vect2DDouble  dArea_dPsi;
    
      public Vect2DDouble  Surface;
    
      public Vect2DDouble  Trapped_Fraction;
    
      public Vect2DDouble  gm1;
    
      public Vect2DDouble  gm2;
    
      public Vect2DDouble  gm3;
    
      public Vect2DDouble  gm4;
    
      public Vect2DDouble  gm5;
    
      public Vect2DDouble  gm6;
    
      public Vect2DDouble  gm7;
    
      public Vect2DDouble  gm8;
    
      public Vect2DDouble  gm9;
    
      public Vect2DDouble  B_Average;
    
      public Vect2DDouble  B_Min;
    
      public Vect2DDouble  B_Max;
    
      }
      public Profiles_1DClass Profiles_1D = new Profiles_1DClass();
    
      public static class Profiles_2DClass {
      
      public String Grid_Type;
    
      public static class GridClass {
      
      public Vect2DDouble  Dim1;
    
      public Vect2DDouble  Dim2;
    
      }
      public GridClass Grid = new GridClass();
    
      public Vect3DDouble  R;
    
      public Vect3DDouble  Z;
    
      public Vect3DDouble  Psi;
    
      public Vect3DDouble  Theta;
    
      public Vect3DDouble  Phi;
    
      public Vect3DDouble  J_Tor;
    
      public Vect3DDouble  J_Parallel;
    
      public Vect3DDouble  B_R;
    
      public Vect3DDouble  B_Z;
    
      public Vect3DDouble  B_Tor;
    
      }
      public Profiles_2DClass Profiles_2D[];
    
      public static class Coordinate_SystemClass {
      
      public String Grid_Type;
    
      public static class GridClass {
      
      public Vect2DDouble  Dim1;
    
      public Vect2DDouble  Dim2;
    
      }
      public GridClass Grid = new GridClass();
    
      public Vect3DDouble  R;
    
      public Vect3DDouble  Z;
    
      public Vect3DDouble  Jacobian;
    
      public Vect3DDouble  g_11;
    
      public Vect3DDouble  g_12;
    
      public Vect3DDouble  g_13;
    
      public Vect3DDouble  g_22;
    
      public Vect3DDouble  g_23;
    
      public Vect3DDouble  g_33;
    
      }
      public Coordinate_SystemClass Coordinate_System = new Coordinate_SystemClass();
    
      public static class Code_ParametersClass {
      
      public String Code_Name;
    
      public String Code_Version;
    
      public Vect1DString Parameters;
    
      public Vect1DString Output_Diagnostics;
    
      public Vect1DInt Output_Flag;
    
      }
      public Code_ParametersClass Code_Parameters = new Code_ParametersClass();
    
      public Vect1DDouble Timebase;
    

 /**
 * Method copy Equilibrium copies a full IDS between two databases.
 * @param srcIdx The index of the source database, returned by imas.open()
 * @param srcOccur The occurence of the IDS in the source database.
 * @param destIdx The index of the destination database, returned by imas.open()
 * @param destOccur The occurence of the IDS in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copy(int srcIdx, int srcOccur, int destIdx, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyIds(srcIdx, destIdx, "Equilibrium", srcOccur, destOccur);
    }

 /**
 * Method copyEnv Equilibrium copies a full IDS between two databases.
 * @param srcUser User of the source database
 * @param srcTokamak Tokamak of the source database
 * @param srcVersion Version of the source database
 * @param srcShot Shot number of the source database
 * @param srcRun Run number of the source database
 * @param srcOccur The occurence of the IDS in the source database.
 * @param destUser User of the destination database
 * @param destTokamak Tokamak of the destination database
 * @param destVersion Version of the destination database
 * @param destShot Shot number of the destination database
 * @param destRun Run number of the destination database
 * @param destOccur The occurence of the IDS in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copyEnv(String srcUser, String srcTokamak, String srcVersion, int srcShot, int srcRun, int srcOccur, String destUser, String destTokamak, String destVersion, int destShot, int destRun, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyIdsEnv(srcTokamak, srcVersion, srcUser, srcShot, srcRun, srcOccur, destTokamak, destVersion, destUser, destShot, destRun, destOccur, "Equilibrium");
    }

/**
 * Method put stores a non timed Equilibrium IDSs in the open database. 
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The passed Equilibrium ids.
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void put(int expIdx, String path, Equilibrium ids)  throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
	  Vect5DDouble  vect5DDouble;
	  Vect6DDouble  vect6DDouble;
	  Vect7DDouble  vect7DDouble;
          String        timebasepath;
          String        ual_debug = System.getenv("ual_debug");

          UALLowLevel.beginIDSPut(expIdx, path);
          
// Put IDS_Properties/Status_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Status_of", ids.IDS_Properties.Status_of);

// Put IDS_Properties/Comment_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Comment_of", ids.IDS_Properties.Comment_of);

// Put IDS_Properties/Creator_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Creator_of", ids.IDS_Properties.Creator_of);

// Put IDS_Properties/Date_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Date_of", ids.IDS_Properties.Date_of);

// Put IDS_Properties/Source_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Source_of", ids.IDS_Properties.Source_of);

// Put IDS_Properties/Reference_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Reference_of", ids.IDS_Properties.Reference_of);

// Put IDS_Properties/Homogeneous_Timebase
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase", ids.IDS_Properties.Homogeneous_Timebase);

// Put IDS_Properties/cocos
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/cocos", ids.IDS_Properties.cocos);

// Put Boundary/Type
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DInt(expIdx, path, "Boundary" + "/Type", timebasepath.trim(), ids.Boundary.Type, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Boundary/N_Outline_Points
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DInt(expIdx, path, "Boundary" + "/N_Outline_Points", timebasepath.trim(), ids.Boundary.N_Outline_Points, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Boundary/Outline_RZ
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Boundary" + "/Outline_RZ", timebasepath.trim(), ids.Boundary.Outline_RZ, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Boundary/Geometric_Axis_RZ
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Boundary" + "/Geometric_Axis_RZ", timebasepath.trim(), ids.Boundary.Geometric_Axis_RZ, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Boundary/a_minor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Boundary" + "/a_minor", timebasepath.trim(), ids.Boundary.a_minor, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Boundary/Elongation
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Boundary" + "/Elongation", timebasepath.trim(), ids.Boundary.Elongation, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Boundary/Elongation_Upper
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Boundary" + "/Elongation_Upper", timebasepath.trim(), ids.Boundary.Elongation_Upper, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Boundary/Elongation_Lower
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Boundary" + "/Elongation_Lower", timebasepath.trim(), ids.Boundary.Elongation_Lower, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Boundary/Triangularity_Upper
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Boundary" + "/Triangularity_Upper", timebasepath.trim(), ids.Boundary.Triangularity_Upper, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Boundary/Triangularity_Lower
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Boundary" + "/Triangularity_Lower", timebasepath.trim(), ids.Boundary.Triangularity_Lower, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Boundary/N_X_Points
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DInt(expIdx, path, "Boundary" + "/N_X_Points", timebasepath.trim(), ids.Boundary.N_X_Points, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Boundary/X_Points_RZ
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Boundary" + "/X_Points_RZ", timebasepath.trim(), ids.Boundary.X_Points_RZ, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Boundary/Strike_Point_Lower_Left_RZ
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Boundary" + "/Strike_Point_Lower_Left_RZ", timebasepath.trim(), ids.Boundary.Strike_Point_Lower_Left_RZ, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Boundary/Strike_Point_Upper_Left_RZ
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Boundary" + "/Strike_Point_Upper_Left_RZ", timebasepath.trim(), ids.Boundary.Strike_Point_Upper_Left_RZ, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Boundary/Strike_Point_Lower_Right_RZ
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Boundary" + "/Strike_Point_Lower_Right_RZ", timebasepath.trim(), ids.Boundary.Strike_Point_Lower_Right_RZ, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Boundary/Strike_Point_Upper_Right_RZ
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Boundary" + "/Strike_Point_Upper_Right_RZ", timebasepath.trim(), ids.Boundary.Strike_Point_Upper_Right_RZ, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Boundary/Active_Limiter_Point_RZ
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Boundary" + "/Active_Limiter_Point_RZ", timebasepath.trim(), ids.Boundary.Active_Limiter_Point_RZ, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Beta_Pol
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Beta_Pol", timebasepath.trim(), ids.Global.Beta_Pol, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Beta_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Beta_Tor", timebasepath.trim(), ids.Global.Beta_Tor, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Beta_Normal
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Beta_Normal", timebasepath.trim(), ids.Global.Beta_Normal, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Ip
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Ip", timebasepath.trim(), ids.Global.Ip, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/li_3
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/li_3", timebasepath.trim(), ids.Global.li_3, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Volume
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Volume", timebasepath.trim(), ids.Global.Volume, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Area
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Area", timebasepath.trim(), ids.Global.Area, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Psi_Axis
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Psi_Axis", timebasepath.trim(), ids.Global.Psi_Axis, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Psi_Boundary
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Psi_Boundary", timebasepath.trim(), ids.Global.Psi_Boundary, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Magnetic_Axis/R
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Magnetic_Axis" + "/R", timebasepath.trim(), ids.Global.Magnetic_Axis.R, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Magnetic_Axis/Z
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Magnetic_Axis" + "/Z", timebasepath.trim(), ids.Global.Magnetic_Axis.Z, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/B_Tor_Axis
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/B_Tor_Axis", timebasepath.trim(), ids.Global.B_Tor_Axis, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/q_Axis
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/q_Axis", timebasepath.trim(), ids.Global.q_Axis, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/q_95
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/q_95", timebasepath.trim(), ids.Global.q_95, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/q_Min
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/q_Min", timebasepath.trim(), ids.Global.q_Min, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Vacuum_Toroidal_Field/R0
          UALLowLevel.putDouble(expIdx, path, "Global" + "/Vacuum_Toroidal_Field" + "/R0", ids.Global.Vacuum_Toroidal_Field.R0);

// Put Global/Vacuum_Toroidal_Field/B0
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Vacuum_Toroidal_Field" + "/B0", timebasepath.trim(), ids.Global.Vacuum_Toroidal_Field.B0, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/W_MHD
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/W_MHD", timebasepath.trim(), ids.Global.W_MHD, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Global/Gamma
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Global" + "/Gamma", timebasepath.trim(), ids.Global.Gamma, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/Psi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/Psi", timebasepath.trim(), ids.Profiles_1D.Psi, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/Phi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/Phi", timebasepath.trim(), ids.Profiles_1D.Phi, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/Pressure
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/Pressure", timebasepath.trim(), ids.Profiles_1D.Pressure, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/F
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/F", timebasepath.trim(), ids.Profiles_1D.F, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/dPressure_dPsi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/dPressure_dPsi", timebasepath.trim(), ids.Profiles_1D.dPressure_dPsi, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/F_dF_dPsi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/F_dF_dPsi", timebasepath.trim(), ids.Profiles_1D.F_dF_dPsi, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/J_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/J_Tor", timebasepath.trim(), ids.Profiles_1D.J_Tor, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/J_Parallel
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/J_Parallel", timebasepath.trim(), ids.Profiles_1D.J_Parallel, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/q
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/q", timebasepath.trim(), ids.Profiles_1D.q, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/Magnetic_Shear
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/Magnetic_Shear", timebasepath.trim(), ids.Profiles_1D.Magnetic_Shear, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/R_Inboard
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/R_Inboard", timebasepath.trim(), ids.Profiles_1D.R_Inboard, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/R_Outboard
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/R_Outboard", timebasepath.trim(), ids.Profiles_1D.R_Outboard, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/Rho_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/Rho_Tor", timebasepath.trim(), ids.Profiles_1D.Rho_Tor, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/dPsi_dRho_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/dPsi_dRho_Tor", timebasepath.trim(), ids.Profiles_1D.dPsi_dRho_Tor, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/Rho_Vol_Norm
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/Rho_Vol_Norm", timebasepath.trim(), ids.Profiles_1D.Rho_Vol_Norm, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/Elongation
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/Elongation", timebasepath.trim(), ids.Profiles_1D.Elongation, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/Triangularity_Upper
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/Triangularity_Upper", timebasepath.trim(), ids.Profiles_1D.Triangularity_Upper, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/Triangularity_Lower
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/Triangularity_Lower", timebasepath.trim(), ids.Profiles_1D.Triangularity_Lower, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/Volume
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/Volume", timebasepath.trim(), ids.Profiles_1D.Volume, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/dVolume_dPsi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/dVolume_dPsi", timebasepath.trim(), ids.Profiles_1D.dVolume_dPsi, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/dVolume_dRho_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/dVolume_dRho_Tor", timebasepath.trim(), ids.Profiles_1D.dVolume_dRho_Tor, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/Area
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/Area", timebasepath.trim(), ids.Profiles_1D.Area, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/dArea_dPsi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/dArea_dPsi", timebasepath.trim(), ids.Profiles_1D.dArea_dPsi, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/Surface
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/Surface", timebasepath.trim(), ids.Profiles_1D.Surface, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/Trapped_Fraction
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/Trapped_Fraction", timebasepath.trim(), ids.Profiles_1D.Trapped_Fraction, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/gm1
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/gm1", timebasepath.trim(), ids.Profiles_1D.gm1, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/gm2
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/gm2", timebasepath.trim(), ids.Profiles_1D.gm2, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/gm3
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/gm3", timebasepath.trim(), ids.Profiles_1D.gm3, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/gm4
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/gm4", timebasepath.trim(), ids.Profiles_1D.gm4, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/gm5
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/gm5", timebasepath.trim(), ids.Profiles_1D.gm5, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/gm6
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/gm6", timebasepath.trim(), ids.Profiles_1D.gm6, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/gm7
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/gm7", timebasepath.trim(), ids.Profiles_1D.gm7, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/gm8
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/gm8", timebasepath.trim(), ids.Profiles_1D.gm8, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/gm9
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/gm9", timebasepath.trim(), ids.Profiles_1D.gm9, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/B_Average
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/B_Average", timebasepath.trim(), ids.Profiles_1D.B_Average, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/B_Min
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/B_Min", timebasepath.trim(), ids.Profiles_1D.B_Min, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_1D/B_Max
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_1D" + "/B_Max", timebasepath.trim(), ids.Profiles_1D.B_Max, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_2D
          if (ids.Profiles_2D != null) {
            UALLowLevel.putInt(expIdx,path, "Profiles_2D/Shape_of",
       		ids.Profiles_2D.length);
            for (int iProfiles_2D = 0; iProfiles_2D<ids.Profiles_2D.length; iProfiles_2D++){
      
// Put Profiles_2D/Grid_Type
          UALLowLevel.putString(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid_Type", ids.Profiles_2D[iProfiles_2D].Grid_Type);

// Put Profiles_2D/Grid/Dim1
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid" + "/Dim1", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].Grid.Dim1, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_2D/Grid/Dim2
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid" + "/Dim2", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].Grid.Dim2, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_2D/R
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/R", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].R, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_2D/Z
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Z", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].Z, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_2D/Psi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Psi", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].Psi, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_2D/Theta
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Theta", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].Theta, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_2D/Phi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Phi", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].Phi, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_2D/J_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/J_Tor", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].J_Tor, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_2D/J_Parallel
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/J_Parallel", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].J_Parallel, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_2D/B_R
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_R", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].B_R, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_2D/B_Z
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_Z", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].B_Z, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Profiles_2D/B_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_Tor", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].B_Tor, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
            }
          }

// Put Coordinate_System/Grid_Type
          UALLowLevel.putString(expIdx, path, "Coordinate_System" + "/Grid_Type", ids.Coordinate_System.Grid_Type);

// Put Coordinate_System/Grid/Dim1
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Coordinate_System" + "/Grid" + "/Dim1", timebasepath.trim(), ids.Coordinate_System.Grid.Dim1, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Coordinate_System/Grid/Dim2
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Coordinate_System" + "/Grid" + "/Dim2", timebasepath.trim(), ids.Coordinate_System.Grid.Dim2, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Coordinate_System/R
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Coordinate_System" + "/R", timebasepath.trim(), ids.Coordinate_System.R, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Coordinate_System/Z
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Coordinate_System" + "/Z", timebasepath.trim(), ids.Coordinate_System.Z, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Coordinate_System/Jacobian
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Coordinate_System" + "/Jacobian", timebasepath.trim(), ids.Coordinate_System.Jacobian, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Coordinate_System/g_11
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Coordinate_System" + "/g_11", timebasepath.trim(), ids.Coordinate_System.g_11, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Coordinate_System/g_12
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Coordinate_System" + "/g_12", timebasepath.trim(), ids.Coordinate_System.g_12, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Coordinate_System/g_13
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Coordinate_System" + "/g_13", timebasepath.trim(), ids.Coordinate_System.g_13, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Coordinate_System/g_22
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Coordinate_System" + "/g_22", timebasepath.trim(), ids.Coordinate_System.g_22, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Coordinate_System/g_23
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Coordinate_System" + "/g_23", timebasepath.trim(), ids.Coordinate_System.g_23, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Coordinate_System/g_33
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect3DDouble(expIdx, path, "Coordinate_System" + "/g_33", timebasepath.trim(), ids.Coordinate_System.g_33, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Code_Parameters/Code_Name
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Name", ids.Code_Parameters.Code_Name);

// Put Code_Parameters/Code_Version
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Version", ids.Code_Parameters.Code_Version);

// Put Code_Parameters/Parameters
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DString(expIdx, path, "Code_Parameters" + "/Parameters", timebasepath.trim(), ids.Code_Parameters.Parameters, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Code_Parameters/Output_Diagnostics
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DString(expIdx, path, "Code_Parameters" + "/Output_Diagnostics", timebasepath.trim(), ids.Code_Parameters.Output_Diagnostics, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Code_Parameters/Output_Flag
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DInt(expIdx, path, "Code_Parameters" + "/Output_Flag", timebasepath.trim(), ids.Code_Parameters.Output_Flag, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect1DDouble(expIdx,path, "Timebase", timebasepath.trim(), ids.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
          UALLowLevel.endIDSPut(expIdx, path);
    }

 /**
 * Method putNonTimed stores the non time dependent fields of a (timed) Equilibrium IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The timed Equilibrium IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void putNonTimed(int expIdx, String path, Equilibrium ids) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
          Vect5DDouble  vect5DDouble;
          Vect6DDouble  vect6DDouble;
          Vect7DDouble  vect7DDouble;
          String        ual_debug = System.getenv("ual_debug");
          
          delete(expIdx, path);
          UALLowLevel.beginIDSPutNonTimed(expIdx, path);
          
// Put IDS_Properties/Status_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Status_of", ids.IDS_Properties.Status_of);

// Put IDS_Properties/Comment_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Comment_of", ids.IDS_Properties.Comment_of);

// Put IDS_Properties/Creator_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Creator_of", ids.IDS_Properties.Creator_of);

// Put IDS_Properties/Date_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Date_of", ids.IDS_Properties.Date_of);

// Put IDS_Properties/Source_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Source_of", ids.IDS_Properties.Source_of);

// Put IDS_Properties/Reference_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Reference_of", ids.IDS_Properties.Reference_of);

// Put IDS_Properties/Homogeneous_Timebase
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase", ids.IDS_Properties.Homogeneous_Timebase);

// Put IDS_Properties/cocos
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/cocos", ids.IDS_Properties.cocos);

// Put Global/Vacuum_Toroidal_Field/R0
          UALLowLevel.putDouble(expIdx, path, "Global" + "/Vacuum_Toroidal_Field" + "/R0", ids.Global.Vacuum_Toroidal_Field.R0);

// Put Profiles_2D
          if (ids.Profiles_2D != null) {
            UALLowLevel.putInt(expIdx,path, "Profiles_2D/Shape_of",
       		ids.Profiles_2D.length);
            for (int iProfiles_2D = 0; iProfiles_2D<ids.Profiles_2D.length; iProfiles_2D++){
      
// Put Profiles_2D/Grid_Type
          UALLowLevel.putString(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid_Type", ids.Profiles_2D[iProfiles_2D].Grid_Type);

            }
          }

// Put Coordinate_System/Grid_Type
          UALLowLevel.putString(expIdx, path, "Coordinate_System" + "/Grid_Type", ids.Coordinate_System.Grid_Type);

// Put Code_Parameters/Code_Name
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Name", ids.Code_Parameters.Code_Name);

// Put Code_Parameters/Code_Version
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Version", ids.Code_Parameters.Code_Version);

          UALLowLevel.endIDSPutNonTimed(expIdx, path);
    }

    
/**
 * Method putSlice appends a (timed) Equilibrium IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param ids The timed Equilibrium IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void putSlice(int expIdx, String path, Equilibrium ids) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
          Vect5DDouble  vect5DDouble;
          Vect6DDouble  vect6DDouble;
          Vect7DDouble  vect7DDouble;
          String        timebasepath;
          String        ual_debug = System.getenv("ual_debug");
          
    	  if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
            System.out.println("ERROR : the PUT_SLICE routine works only for homogeneous timebase IDS");
            return;
          }
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutSlice(expIdx, path);
          
// Put Slice Boundary/Type
          UALLowLevel.putVect1DIntSlice(expIdx, path, "Boundary" + "/Type", timebasepath.trim(), ids.Boundary.Type, ids.Timebase.getElementAt(0));
		
// Put Slice Boundary/N_Outline_Points
          UALLowLevel.putVect1DIntSlice(expIdx, path, "Boundary" + "/N_Outline_Points", timebasepath.trim(), ids.Boundary.N_Outline_Points, ids.Timebase.getElementAt(0));
		
// Put Slice Boundary/Outline_RZ
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Boundary" + "/Outline_RZ", timebasepath.trim(), ids.Boundary.Outline_RZ, ids.Timebase.getElementAt(0));
		
// Put Slice Boundary/Geometric_Axis_RZ
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Boundary" + "/Geometric_Axis_RZ", timebasepath.trim(), ids.Boundary.Geometric_Axis_RZ, ids.Timebase.getElementAt(0));
		
// Put Slice Boundary/a_minor
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Boundary" + "/a_minor", timebasepath.trim(), ids.Boundary.a_minor, ids.Timebase.getElementAt(0));
		
// Put Slice Boundary/Elongation
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Boundary" + "/Elongation", timebasepath.trim(), ids.Boundary.Elongation, ids.Timebase.getElementAt(0));
		
// Put Slice Boundary/Elongation_Upper
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Boundary" + "/Elongation_Upper", timebasepath.trim(), ids.Boundary.Elongation_Upper, ids.Timebase.getElementAt(0));
		
// Put Slice Boundary/Elongation_Lower
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Boundary" + "/Elongation_Lower", timebasepath.trim(), ids.Boundary.Elongation_Lower, ids.Timebase.getElementAt(0));
		
// Put Slice Boundary/Triangularity_Upper
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Boundary" + "/Triangularity_Upper", timebasepath.trim(), ids.Boundary.Triangularity_Upper, ids.Timebase.getElementAt(0));
		
// Put Slice Boundary/Triangularity_Lower
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Boundary" + "/Triangularity_Lower", timebasepath.trim(), ids.Boundary.Triangularity_Lower, ids.Timebase.getElementAt(0));
		
// Put Slice Boundary/N_X_Points
          UALLowLevel.putVect1DIntSlice(expIdx, path, "Boundary" + "/N_X_Points", timebasepath.trim(), ids.Boundary.N_X_Points, ids.Timebase.getElementAt(0));
		
// Put Slice Boundary/X_Points_RZ
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Boundary" + "/X_Points_RZ", timebasepath.trim(), ids.Boundary.X_Points_RZ, ids.Timebase.getElementAt(0));
		
// Put Slice Boundary/Strike_Point_Lower_Left_RZ
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Boundary" + "/Strike_Point_Lower_Left_RZ", timebasepath.trim(), ids.Boundary.Strike_Point_Lower_Left_RZ, ids.Timebase.getElementAt(0));
		
// Put Slice Boundary/Strike_Point_Upper_Left_RZ
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Boundary" + "/Strike_Point_Upper_Left_RZ", timebasepath.trim(), ids.Boundary.Strike_Point_Upper_Left_RZ, ids.Timebase.getElementAt(0));
		
// Put Slice Boundary/Strike_Point_Lower_Right_RZ
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Boundary" + "/Strike_Point_Lower_Right_RZ", timebasepath.trim(), ids.Boundary.Strike_Point_Lower_Right_RZ, ids.Timebase.getElementAt(0));
		
// Put Slice Boundary/Strike_Point_Upper_Right_RZ
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Boundary" + "/Strike_Point_Upper_Right_RZ", timebasepath.trim(), ids.Boundary.Strike_Point_Upper_Right_RZ, ids.Timebase.getElementAt(0));
		
// Put Slice Boundary/Active_Limiter_Point_RZ
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Boundary" + "/Active_Limiter_Point_RZ", timebasepath.trim(), ids.Boundary.Active_Limiter_Point_RZ, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Beta_Pol
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Beta_Pol", timebasepath.trim(), ids.Global.Beta_Pol, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Beta_Tor
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Beta_Tor", timebasepath.trim(), ids.Global.Beta_Tor, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Beta_Normal
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Beta_Normal", timebasepath.trim(), ids.Global.Beta_Normal, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Ip
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Ip", timebasepath.trim(), ids.Global.Ip, ids.Timebase.getElementAt(0));
		
// Put Slice Global/li_3
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/li_3", timebasepath.trim(), ids.Global.li_3, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Volume
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Volume", timebasepath.trim(), ids.Global.Volume, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Area
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Area", timebasepath.trim(), ids.Global.Area, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Psi_Axis
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Psi_Axis", timebasepath.trim(), ids.Global.Psi_Axis, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Psi_Boundary
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Psi_Boundary", timebasepath.trim(), ids.Global.Psi_Boundary, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Magnetic_Axis/R
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Magnetic_Axis" + "/R", timebasepath.trim(), ids.Global.Magnetic_Axis.R, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Magnetic_Axis/Z
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Magnetic_Axis" + "/Z", timebasepath.trim(), ids.Global.Magnetic_Axis.Z, ids.Timebase.getElementAt(0));
		
// Put Slice Global/B_Tor_Axis
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/B_Tor_Axis", timebasepath.trim(), ids.Global.B_Tor_Axis, ids.Timebase.getElementAt(0));
		
// Put Slice Global/q_Axis
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/q_Axis", timebasepath.trim(), ids.Global.q_Axis, ids.Timebase.getElementAt(0));
		
// Put Slice Global/q_95
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/q_95", timebasepath.trim(), ids.Global.q_95, ids.Timebase.getElementAt(0));
		
// Put Slice Global/q_Min
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/q_Min", timebasepath.trim(), ids.Global.q_Min, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Vacuum_Toroidal_Field/B0
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Vacuum_Toroidal_Field" + "/B0", timebasepath.trim(), ids.Global.Vacuum_Toroidal_Field.B0, ids.Timebase.getElementAt(0));
		
// Put Slice Global/W_MHD
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/W_MHD", timebasepath.trim(), ids.Global.W_MHD, ids.Timebase.getElementAt(0));
		
// Put Slice Global/Gamma
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Global" + "/Gamma", timebasepath.trim(), ids.Global.Gamma, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/Psi
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/Psi", timebasepath.trim(), ids.Profiles_1D.Psi, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/Phi
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/Phi", timebasepath.trim(), ids.Profiles_1D.Phi, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/Pressure
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/Pressure", timebasepath.trim(), ids.Profiles_1D.Pressure, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/F
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/F", timebasepath.trim(), ids.Profiles_1D.F, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/dPressure_dPsi
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/dPressure_dPsi", timebasepath.trim(), ids.Profiles_1D.dPressure_dPsi, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/F_dF_dPsi
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/F_dF_dPsi", timebasepath.trim(), ids.Profiles_1D.F_dF_dPsi, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/J_Tor
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/J_Tor", timebasepath.trim(), ids.Profiles_1D.J_Tor, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/J_Parallel
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/J_Parallel", timebasepath.trim(), ids.Profiles_1D.J_Parallel, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/q
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/q", timebasepath.trim(), ids.Profiles_1D.q, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/Magnetic_Shear
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/Magnetic_Shear", timebasepath.trim(), ids.Profiles_1D.Magnetic_Shear, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/R_Inboard
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/R_Inboard", timebasepath.trim(), ids.Profiles_1D.R_Inboard, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/R_Outboard
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/R_Outboard", timebasepath.trim(), ids.Profiles_1D.R_Outboard, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/Rho_Tor
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/Rho_Tor", timebasepath.trim(), ids.Profiles_1D.Rho_Tor, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/dPsi_dRho_Tor
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/dPsi_dRho_Tor", timebasepath.trim(), ids.Profiles_1D.dPsi_dRho_Tor, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/Rho_Vol_Norm
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/Rho_Vol_Norm", timebasepath.trim(), ids.Profiles_1D.Rho_Vol_Norm, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/Elongation
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/Elongation", timebasepath.trim(), ids.Profiles_1D.Elongation, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/Triangularity_Upper
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/Triangularity_Upper", timebasepath.trim(), ids.Profiles_1D.Triangularity_Upper, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/Triangularity_Lower
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/Triangularity_Lower", timebasepath.trim(), ids.Profiles_1D.Triangularity_Lower, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/Volume
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/Volume", timebasepath.trim(), ids.Profiles_1D.Volume, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/dVolume_dPsi
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/dVolume_dPsi", timebasepath.trim(), ids.Profiles_1D.dVolume_dPsi, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/dVolume_dRho_Tor
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/dVolume_dRho_Tor", timebasepath.trim(), ids.Profiles_1D.dVolume_dRho_Tor, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/Area
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/Area", timebasepath.trim(), ids.Profiles_1D.Area, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/dArea_dPsi
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/dArea_dPsi", timebasepath.trim(), ids.Profiles_1D.dArea_dPsi, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/Surface
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/Surface", timebasepath.trim(), ids.Profiles_1D.Surface, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/Trapped_Fraction
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/Trapped_Fraction", timebasepath.trim(), ids.Profiles_1D.Trapped_Fraction, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/gm1
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm1", timebasepath.trim(), ids.Profiles_1D.gm1, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/gm2
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm2", timebasepath.trim(), ids.Profiles_1D.gm2, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/gm3
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm3", timebasepath.trim(), ids.Profiles_1D.gm3, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/gm4
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm4", timebasepath.trim(), ids.Profiles_1D.gm4, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/gm5
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm5", timebasepath.trim(), ids.Profiles_1D.gm5, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/gm6
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm6", timebasepath.trim(), ids.Profiles_1D.gm6, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/gm7
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm7", timebasepath.trim(), ids.Profiles_1D.gm7, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/gm8
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm8", timebasepath.trim(), ids.Profiles_1D.gm8, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/gm9
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm9", timebasepath.trim(), ids.Profiles_1D.gm9, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/B_Average
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/B_Average", timebasepath.trim(), ids.Profiles_1D.B_Average, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/B_Min
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/B_Min", timebasepath.trim(), ids.Profiles_1D.B_Min, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_1D/B_Max
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_1D" + "/B_Max", timebasepath.trim(), ids.Profiles_1D.B_Max, ids.Timebase.getElementAt(0));
		
// Put Profiles_2D
          if (ids.Profiles_2D != null) {
            UALLowLevel.putInt(expIdx,path, "Profiles_2D/Shape_of",
       		ids.Profiles_2D.length);
            for (int iProfiles_2D = 0; iProfiles_2D<ids.Profiles_2D.length; iProfiles_2D++){
      
// Put Slice Profiles_2D/Grid/Dim1
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid" + "/Dim1", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].Grid.Dim1, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_2D/Grid/Dim2
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid" + "/Dim2", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].Grid.Dim2, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_2D/R
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/R", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].R, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_2D/Z
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Z", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].Z, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_2D/Psi
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Psi", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].Psi, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_2D/Theta
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Theta", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].Theta, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_2D/Phi
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Phi", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].Phi, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_2D/J_Tor
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/J_Tor", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].J_Tor, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_2D/J_Parallel
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/J_Parallel", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].J_Parallel, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_2D/B_R
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_R", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].B_R, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_2D/B_Z
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_Z", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].B_Z, ids.Timebase.getElementAt(0));
		
// Put Slice Profiles_2D/B_Tor
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_Tor", timebasepath.trim(), ids.Profiles_2D[iProfiles_2D].B_Tor, ids.Timebase.getElementAt(0));
		
            }
          }

// Put Slice Coordinate_System/Grid/Dim1
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Coordinate_System" + "/Grid" + "/Dim1", timebasepath.trim(), ids.Coordinate_System.Grid.Dim1, ids.Timebase.getElementAt(0));
		
// Put Slice Coordinate_System/Grid/Dim2
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Coordinate_System" + "/Grid" + "/Dim2", timebasepath.trim(), ids.Coordinate_System.Grid.Dim2, ids.Timebase.getElementAt(0));
		
// Put Slice Coordinate_System/R
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Coordinate_System" + "/R", timebasepath.trim(), ids.Coordinate_System.R, ids.Timebase.getElementAt(0));
		
// Put Slice Coordinate_System/Z
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Coordinate_System" + "/Z", timebasepath.trim(), ids.Coordinate_System.Z, ids.Timebase.getElementAt(0));
		
// Put Slice Coordinate_System/Jacobian
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Coordinate_System" + "/Jacobian", timebasepath.trim(), ids.Coordinate_System.Jacobian, ids.Timebase.getElementAt(0));
		
// Put Slice Coordinate_System/g_11
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Coordinate_System" + "/g_11", timebasepath.trim(), ids.Coordinate_System.g_11, ids.Timebase.getElementAt(0));
		
// Put Slice Coordinate_System/g_12
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Coordinate_System" + "/g_12", timebasepath.trim(), ids.Coordinate_System.g_12, ids.Timebase.getElementAt(0));
		
// Put Slice Coordinate_System/g_13
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Coordinate_System" + "/g_13", timebasepath.trim(), ids.Coordinate_System.g_13, ids.Timebase.getElementAt(0));
		
// Put Slice Coordinate_System/g_22
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Coordinate_System" + "/g_22", timebasepath.trim(), ids.Coordinate_System.g_22, ids.Timebase.getElementAt(0));
		
// Put Slice Coordinate_System/g_23
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Coordinate_System" + "/g_23", timebasepath.trim(), ids.Coordinate_System.g_23, ids.Timebase.getElementAt(0));
		
// Put Slice Coordinate_System/g_33
          UALLowLevel.putVect3DDoubleSlice(expIdx, path, "Coordinate_System" + "/g_33", timebasepath.trim(), ids.Coordinate_System.g_33, ids.Timebase.getElementAt(0));
		
// Put Slice Code_Parameters/Parameters
          UALLowLevel.putVect1DStringSlice(expIdx, path, "Code_Parameters" + "/Parameters", timebasepath.trim(), ids.Code_Parameters.Parameters, ids.Timebase.getElementAt(0));
		
// Put Slice Code_Parameters/Output_Diagnostics
          UALLowLevel.putVect1DStringSlice(expIdx, path, "Code_Parameters" + "/Output_Diagnostics", timebasepath.trim(), ids.Code_Parameters.Output_Diagnostics, ids.Timebase.getElementAt(0));
		
// Put Slice Code_Parameters/Output_Flag
          UALLowLevel.putVect1DIntSlice(expIdx, path, "Code_Parameters" + "/Output_Flag", timebasepath.trim(), ids.Code_Parameters.Output_Flag, ids.Timebase.getElementAt(0));
		
// Put Slice Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx,path, "Timebase", timebasepath.trim(), ids.Timebase, ids.Timebase.getElementAt(0));
		
          UALLowLevel.endIDSPutSlice(expIdx, path);
    }
    

    

/**
 * Method putdb stores a non timed Equilibrium IDSs in the open database only if
 *  datainto.isref == 1. In any case it calls afterwards UALLowLevel.putIdsDb() in order to update catalogue
 * @param expIdx The index to the database, returned by imas.open()
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected ids. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @param ids The  Equilibrium IDSs
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
/*
    public static void putdb(int expIdx, String user, String machine, int shot, int run, 
    	String path, int occurrence, Equilibrium ids) throws UALException
    {
    	String actPath;
        if(occurrence > 0)
	    actPath = path+"/"+occurrence;
	else
	    actPath = path;
	if(ids.IDS_Properties.isref == 1)
	{
	    put(expIdx, actPath, ids);
	    UALLowLevel.putIdsDb(user, machine, shot, run, path, occurrence, 1,
	    	user, machine, shot, run, occurrence);
	}
	else //isref == 2
	{
	    UALLowLevel.putIdsDb(user, machine, shot, run, path, occurrence, 2,
	    	ids.IDS_Properties.whatref.user,  ids.IDS_Properties.whatref.machine,
		ids.IDS_Properties.whatref.shot, ids.IDS_Properties.whatref.run, 
		ids.IDS_Properties.whatref.occurrence);
	
	}
    }
*/

/**
 * Method getdb retrieves the non timed Equilibrium IDSs in the open database, taking the correct reference.
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the Equilibrium IDS . Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if float or double and empty String is string.
 **/
    public static Equilibrium getdb(String user, String machine, int shot, int run, String version, String path, int occurrence) throws UALException
    {
//		int idx = UALLowLevel.openDb(user, machine, shot, run, path, occurrence);
		int idx = openEnv("ids", shot, run, user, machine, version);
		if (occurrence != 0)
			path = path + "/" + occurrence;
		Equilibrium retIdss = null;
                try {
                        retIdss = get(idx, path);
                }
                catch(Exception exc) {
                        retIdss = new Equilibrium ();
                        System.out.println("Return a new Equilibrium empty IDS after catching exception: "+exc);
                }
		close(idx);
		return retIdss;
    }

/*
    public static void fillIdsRef(Equilibrium ids, int isRef, String refUser, String refMachine, int refShot, int refRun, int refOccurrence)
    {
        ids.IDS_Properties.isref = isRef;
        if(isRef != 1)
        {
            ids.IDS_Properties.whatref.user = refUser;
                ids.IDS_Properties.whatref.machine = refMachine;
                ids.IDS_Properties.whatref.shot = refShot;
                ids.IDS_Properties.whatref.run = refRun;
                ids.IDS_Properties.whatref.occurrence = refOccurrence;
        }
    } 
*/
            

    public static Equilibrium  get(int expIdx, String path)  throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");

      Equilibrium ids = new Equilibrium (); 
      UALLowLevel.beginIDSGet(expIdx, path, false);
      
          try {
            ids.IDS_Properties.Status_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Status_of");
          } catch(Exception exc){ids.IDS_Properties.Status_of = null;}
		
          try {
            ids.IDS_Properties.Comment_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Comment_of");
          } catch(Exception exc){ids.IDS_Properties.Comment_of = null;}
		
          try {
            ids.IDS_Properties.Creator_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Creator_of");
          } catch(Exception exc){ids.IDS_Properties.Creator_of = null;}
		
          try {
            ids.IDS_Properties.Date_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Date_of");
          } catch(Exception exc){ids.IDS_Properties.Date_of = null;}
		
          try {
            ids.IDS_Properties.Source_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Source_of");
          } catch(Exception exc){ids.IDS_Properties.Source_of = null;}
		
          try {
            ids.IDS_Properties.Reference_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Reference_of");
          } catch(Exception exc){ids.IDS_Properties.Reference_of = null;}
		
          try {
            ids.IDS_Properties.Homogeneous_Timebase = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase");
          } catch(Exception exc){ids.IDS_Properties.Homogeneous_Timebase = EMPTY_INT;}
		
          try {
            ids.IDS_Properties.cocos = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/cocos");
          } catch(Exception exc){ids.IDS_Properties.cocos = EMPTY_INT;}
		
          try {
            ids.Boundary.Type = UALLowLevel.getVect1DInt(expIdx, path, "Boundary" + "/Type");
          } catch(Exception exc){ids.Boundary.Type = null;}
		
          try {
            ids.Boundary.N_Outline_Points = UALLowLevel.getVect1DInt(expIdx, path, "Boundary" + "/N_Outline_Points");
          } catch(Exception exc){ids.Boundary.N_Outline_Points = null;}
		
          try {
            ids.Boundary.Outline_RZ = UALLowLevel.getVect3DDouble(expIdx, path, "Boundary" + "/Outline_RZ");
          } catch(Exception exc){ids.Boundary.Outline_RZ = null;}
		
          try {
            ids.Boundary.Geometric_Axis_RZ = UALLowLevel.getVect2DDouble(expIdx, path, "Boundary" + "/Geometric_Axis_RZ");
          } catch(Exception exc){ids.Boundary.Geometric_Axis_RZ = null;}
		
          try {
            ids.Boundary.a_minor = UALLowLevel.getVect1DDouble(expIdx, path, "Boundary" + "/a_minor");
          } catch(Exception exc){ids.Boundary.a_minor = null;}
		
          try {
            ids.Boundary.Elongation = UALLowLevel.getVect1DDouble(expIdx, path, "Boundary" + "/Elongation");
          } catch(Exception exc){ids.Boundary.Elongation = null;}
		
          try {
            ids.Boundary.Elongation_Upper = UALLowLevel.getVect1DDouble(expIdx, path, "Boundary" + "/Elongation_Upper");
          } catch(Exception exc){ids.Boundary.Elongation_Upper = null;}
		
          try {
            ids.Boundary.Elongation_Lower = UALLowLevel.getVect1DDouble(expIdx, path, "Boundary" + "/Elongation_Lower");
          } catch(Exception exc){ids.Boundary.Elongation_Lower = null;}
		
          try {
            ids.Boundary.Triangularity_Upper = UALLowLevel.getVect1DDouble(expIdx, path, "Boundary" + "/Triangularity_Upper");
          } catch(Exception exc){ids.Boundary.Triangularity_Upper = null;}
		
          try {
            ids.Boundary.Triangularity_Lower = UALLowLevel.getVect1DDouble(expIdx, path, "Boundary" + "/Triangularity_Lower");
          } catch(Exception exc){ids.Boundary.Triangularity_Lower = null;}
		
          try {
            ids.Boundary.N_X_Points = UALLowLevel.getVect1DInt(expIdx, path, "Boundary" + "/N_X_Points");
          } catch(Exception exc){ids.Boundary.N_X_Points = null;}
		
          try {
            ids.Boundary.X_Points_RZ = UALLowLevel.getVect3DDouble(expIdx, path, "Boundary" + "/X_Points_RZ");
          } catch(Exception exc){ids.Boundary.X_Points_RZ = null;}
		
          try {
            ids.Boundary.Strike_Point_Lower_Left_RZ = UALLowLevel.getVect2DDouble(expIdx, path, "Boundary" + "/Strike_Point_Lower_Left_RZ");
          } catch(Exception exc){ids.Boundary.Strike_Point_Lower_Left_RZ = null;}
		
          try {
            ids.Boundary.Strike_Point_Upper_Left_RZ = UALLowLevel.getVect2DDouble(expIdx, path, "Boundary" + "/Strike_Point_Upper_Left_RZ");
          } catch(Exception exc){ids.Boundary.Strike_Point_Upper_Left_RZ = null;}
		
          try {
            ids.Boundary.Strike_Point_Lower_Right_RZ = UALLowLevel.getVect2DDouble(expIdx, path, "Boundary" + "/Strike_Point_Lower_Right_RZ");
          } catch(Exception exc){ids.Boundary.Strike_Point_Lower_Right_RZ = null;}
		
          try {
            ids.Boundary.Strike_Point_Upper_Right_RZ = UALLowLevel.getVect2DDouble(expIdx, path, "Boundary" + "/Strike_Point_Upper_Right_RZ");
          } catch(Exception exc){ids.Boundary.Strike_Point_Upper_Right_RZ = null;}
		
          try {
            ids.Boundary.Active_Limiter_Point_RZ = UALLowLevel.getVect2DDouble(expIdx, path, "Boundary" + "/Active_Limiter_Point_RZ");
          } catch(Exception exc){ids.Boundary.Active_Limiter_Point_RZ = null;}
		
          try {
            ids.Global.Beta_Pol = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Beta_Pol");
          } catch(Exception exc){ids.Global.Beta_Pol = null;}
		
          try {
            ids.Global.Beta_Tor = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Beta_Tor");
          } catch(Exception exc){ids.Global.Beta_Tor = null;}
		
          try {
            ids.Global.Beta_Normal = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Beta_Normal");
          } catch(Exception exc){ids.Global.Beta_Normal = null;}
		
          try {
            ids.Global.Ip = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Ip");
          } catch(Exception exc){ids.Global.Ip = null;}
		
          try {
            ids.Global.li_3 = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/li_3");
          } catch(Exception exc){ids.Global.li_3 = null;}
		
          try {
            ids.Global.Volume = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Volume");
          } catch(Exception exc){ids.Global.Volume = null;}
		
          try {
            ids.Global.Area = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Area");
          } catch(Exception exc){ids.Global.Area = null;}
		
          try {
            ids.Global.Psi_Axis = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Psi_Axis");
          } catch(Exception exc){ids.Global.Psi_Axis = null;}
		
          try {
            ids.Global.Psi_Boundary = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Psi_Boundary");
          } catch(Exception exc){ids.Global.Psi_Boundary = null;}
		
          try {
            ids.Global.Magnetic_Axis.R = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Magnetic_Axis" + "/R");
          } catch(Exception exc){ids.Global.Magnetic_Axis.R = null;}
		
          try {
            ids.Global.Magnetic_Axis.Z = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Magnetic_Axis" + "/Z");
          } catch(Exception exc){ids.Global.Magnetic_Axis.Z = null;}
		
          try {
            ids.Global.B_Tor_Axis = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/B_Tor_Axis");
          } catch(Exception exc){ids.Global.B_Tor_Axis = null;}
		
          try {
            ids.Global.q_Axis = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/q_Axis");
          } catch(Exception exc){ids.Global.q_Axis = null;}
		
          try {
            ids.Global.q_95 = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/q_95");
          } catch(Exception exc){ids.Global.q_95 = null;}
		
          try {
            ids.Global.q_Min = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/q_Min");
          } catch(Exception exc){ids.Global.q_Min = null;}
		
          try {
            ids.Global.Vacuum_Toroidal_Field.R0 = UALLowLevel.getDouble(expIdx, path, "Global" + "/Vacuum_Toroidal_Field" + "/R0");
          } catch(Exception exc){ids.Global.Vacuum_Toroidal_Field.R0 = EMPTY_DOUBLE;}
		
          try {
            ids.Global.Vacuum_Toroidal_Field.B0 = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Vacuum_Toroidal_Field" + "/B0");
          } catch(Exception exc){ids.Global.Vacuum_Toroidal_Field.B0 = null;}
		
          try {
            ids.Global.W_MHD = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/W_MHD");
          } catch(Exception exc){ids.Global.W_MHD = null;}
		
          try {
            ids.Global.Gamma = UALLowLevel.getVect1DDouble(expIdx, path, "Global" + "/Gamma");
          } catch(Exception exc){ids.Global.Gamma = null;}
		
          try {
            ids.Profiles_1D.Psi = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/Psi");
          } catch(Exception exc){ids.Profiles_1D.Psi = null;}
		
          try {
            ids.Profiles_1D.Phi = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/Phi");
          } catch(Exception exc){ids.Profiles_1D.Phi = null;}
		
          try {
            ids.Profiles_1D.Pressure = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/Pressure");
          } catch(Exception exc){ids.Profiles_1D.Pressure = null;}
		
          try {
            ids.Profiles_1D.F = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/F");
          } catch(Exception exc){ids.Profiles_1D.F = null;}
		
          try {
            ids.Profiles_1D.dPressure_dPsi = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/dPressure_dPsi");
          } catch(Exception exc){ids.Profiles_1D.dPressure_dPsi = null;}
		
          try {
            ids.Profiles_1D.F_dF_dPsi = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/F_dF_dPsi");
          } catch(Exception exc){ids.Profiles_1D.F_dF_dPsi = null;}
		
          try {
            ids.Profiles_1D.J_Tor = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/J_Tor");
          } catch(Exception exc){ids.Profiles_1D.J_Tor = null;}
		
          try {
            ids.Profiles_1D.J_Parallel = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/J_Parallel");
          } catch(Exception exc){ids.Profiles_1D.J_Parallel = null;}
		
          try {
            ids.Profiles_1D.q = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/q");
          } catch(Exception exc){ids.Profiles_1D.q = null;}
		
          try {
            ids.Profiles_1D.Magnetic_Shear = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/Magnetic_Shear");
          } catch(Exception exc){ids.Profiles_1D.Magnetic_Shear = null;}
		
          try {
            ids.Profiles_1D.R_Inboard = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/R_Inboard");
          } catch(Exception exc){ids.Profiles_1D.R_Inboard = null;}
		
          try {
            ids.Profiles_1D.R_Outboard = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/R_Outboard");
          } catch(Exception exc){ids.Profiles_1D.R_Outboard = null;}
		
          try {
            ids.Profiles_1D.Rho_Tor = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/Rho_Tor");
          } catch(Exception exc){ids.Profiles_1D.Rho_Tor = null;}
		
          try {
            ids.Profiles_1D.dPsi_dRho_Tor = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/dPsi_dRho_Tor");
          } catch(Exception exc){ids.Profiles_1D.dPsi_dRho_Tor = null;}
		
          try {
            ids.Profiles_1D.Rho_Vol_Norm = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/Rho_Vol_Norm");
          } catch(Exception exc){ids.Profiles_1D.Rho_Vol_Norm = null;}
		
          try {
            ids.Profiles_1D.Elongation = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/Elongation");
          } catch(Exception exc){ids.Profiles_1D.Elongation = null;}
		
          try {
            ids.Profiles_1D.Triangularity_Upper = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/Triangularity_Upper");
          } catch(Exception exc){ids.Profiles_1D.Triangularity_Upper = null;}
		
          try {
            ids.Profiles_1D.Triangularity_Lower = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/Triangularity_Lower");
          } catch(Exception exc){ids.Profiles_1D.Triangularity_Lower = null;}
		
          try {
            ids.Profiles_1D.Volume = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/Volume");
          } catch(Exception exc){ids.Profiles_1D.Volume = null;}
		
          try {
            ids.Profiles_1D.dVolume_dPsi = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/dVolume_dPsi");
          } catch(Exception exc){ids.Profiles_1D.dVolume_dPsi = null;}
		
          try {
            ids.Profiles_1D.dVolume_dRho_Tor = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/dVolume_dRho_Tor");
          } catch(Exception exc){ids.Profiles_1D.dVolume_dRho_Tor = null;}
		
          try {
            ids.Profiles_1D.Area = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/Area");
          } catch(Exception exc){ids.Profiles_1D.Area = null;}
		
          try {
            ids.Profiles_1D.dArea_dPsi = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/dArea_dPsi");
          } catch(Exception exc){ids.Profiles_1D.dArea_dPsi = null;}
		
          try {
            ids.Profiles_1D.Surface = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/Surface");
          } catch(Exception exc){ids.Profiles_1D.Surface = null;}
		
          try {
            ids.Profiles_1D.Trapped_Fraction = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/Trapped_Fraction");
          } catch(Exception exc){ids.Profiles_1D.Trapped_Fraction = null;}
		
          try {
            ids.Profiles_1D.gm1 = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/gm1");
          } catch(Exception exc){ids.Profiles_1D.gm1 = null;}
		
          try {
            ids.Profiles_1D.gm2 = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/gm2");
          } catch(Exception exc){ids.Profiles_1D.gm2 = null;}
		
          try {
            ids.Profiles_1D.gm3 = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/gm3");
          } catch(Exception exc){ids.Profiles_1D.gm3 = null;}
		
          try {
            ids.Profiles_1D.gm4 = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/gm4");
          } catch(Exception exc){ids.Profiles_1D.gm4 = null;}
		
          try {
            ids.Profiles_1D.gm5 = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/gm5");
          } catch(Exception exc){ids.Profiles_1D.gm5 = null;}
		
          try {
            ids.Profiles_1D.gm6 = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/gm6");
          } catch(Exception exc){ids.Profiles_1D.gm6 = null;}
		
          try {
            ids.Profiles_1D.gm7 = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/gm7");
          } catch(Exception exc){ids.Profiles_1D.gm7 = null;}
		
          try {
            ids.Profiles_1D.gm8 = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/gm8");
          } catch(Exception exc){ids.Profiles_1D.gm8 = null;}
		
          try {
            ids.Profiles_1D.gm9 = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/gm9");
          } catch(Exception exc){ids.Profiles_1D.gm9 = null;}
		
          try {
            ids.Profiles_1D.B_Average = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/B_Average");
          } catch(Exception exc){ids.Profiles_1D.B_Average = null;}
		
          try {
            ids.Profiles_1D.B_Min = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/B_Min");
          } catch(Exception exc){ids.Profiles_1D.B_Min = null;}
		
          try {
            ids.Profiles_1D.B_Max = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_1D" + "/B_Max");
          } catch(Exception exc){ids.Profiles_1D.B_Max = null;}
		
// Get Array of Structures Profiles_2D
          try {
            ids.Profiles_2D = new Profiles_2DClass[UALLowLevel.getInt(expIdx,path, "Profiles_2D/Shape_of")];
            for (int iProfiles_2D = 0; iProfiles_2D<ids.Profiles_2D.length; iProfiles_2D++){
              ids.Profiles_2D[iProfiles_2D] = new Profiles_2DClass();

          try {
            ids.Profiles_2D[iProfiles_2D].Grid_Type = UALLowLevel.getString(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid_Type");
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].Grid_Type = null;}
		
          try {
            ids.Profiles_2D[iProfiles_2D].Grid.Dim1 = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid" + "/Dim1");
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].Grid.Dim1 = null;}
		
          try {
            ids.Profiles_2D[iProfiles_2D].Grid.Dim2 = UALLowLevel.getVect2DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid" + "/Dim2");
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].Grid.Dim2 = null;}
		
          try {
            ids.Profiles_2D[iProfiles_2D].R = UALLowLevel.getVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/R");
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].R = null;}
		
          try {
            ids.Profiles_2D[iProfiles_2D].Z = UALLowLevel.getVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Z");
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].Z = null;}
		
          try {
            ids.Profiles_2D[iProfiles_2D].Psi = UALLowLevel.getVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Psi");
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].Psi = null;}
		
          try {
            ids.Profiles_2D[iProfiles_2D].Theta = UALLowLevel.getVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Theta");
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].Theta = null;}
		
          try {
            ids.Profiles_2D[iProfiles_2D].Phi = UALLowLevel.getVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Phi");
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].Phi = null;}
		
          try {
            ids.Profiles_2D[iProfiles_2D].J_Tor = UALLowLevel.getVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/J_Tor");
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].J_Tor = null;}
		
          try {
            ids.Profiles_2D[iProfiles_2D].J_Parallel = UALLowLevel.getVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/J_Parallel");
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].J_Parallel = null;}
		
          try {
            ids.Profiles_2D[iProfiles_2D].B_R = UALLowLevel.getVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_R");
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].B_R = null;}
		
          try {
            ids.Profiles_2D[iProfiles_2D].B_Z = UALLowLevel.getVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_Z");
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].B_Z = null;}
		
          try {
            ids.Profiles_2D[iProfiles_2D].B_Tor = UALLowLevel.getVect3DDouble(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_Tor");
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].B_Tor = null;}
		
            }
          } catch(Exception exc){ids.Profiles_2D = null;}

          try {
            ids.Coordinate_System.Grid_Type = UALLowLevel.getString(expIdx, path, "Coordinate_System" + "/Grid_Type");
          } catch(Exception exc){ids.Coordinate_System.Grid_Type = null;}
		
          try {
            ids.Coordinate_System.Grid.Dim1 = UALLowLevel.getVect2DDouble(expIdx, path, "Coordinate_System" + "/Grid" + "/Dim1");
          } catch(Exception exc){ids.Coordinate_System.Grid.Dim1 = null;}
		
          try {
            ids.Coordinate_System.Grid.Dim2 = UALLowLevel.getVect2DDouble(expIdx, path, "Coordinate_System" + "/Grid" + "/Dim2");
          } catch(Exception exc){ids.Coordinate_System.Grid.Dim2 = null;}
		
          try {
            ids.Coordinate_System.R = UALLowLevel.getVect3DDouble(expIdx, path, "Coordinate_System" + "/R");
          } catch(Exception exc){ids.Coordinate_System.R = null;}
		
          try {
            ids.Coordinate_System.Z = UALLowLevel.getVect3DDouble(expIdx, path, "Coordinate_System" + "/Z");
          } catch(Exception exc){ids.Coordinate_System.Z = null;}
		
          try {
            ids.Coordinate_System.Jacobian = UALLowLevel.getVect3DDouble(expIdx, path, "Coordinate_System" + "/Jacobian");
          } catch(Exception exc){ids.Coordinate_System.Jacobian = null;}
		
          try {
            ids.Coordinate_System.g_11 = UALLowLevel.getVect3DDouble(expIdx, path, "Coordinate_System" + "/g_11");
          } catch(Exception exc){ids.Coordinate_System.g_11 = null;}
		
          try {
            ids.Coordinate_System.g_12 = UALLowLevel.getVect3DDouble(expIdx, path, "Coordinate_System" + "/g_12");
          } catch(Exception exc){ids.Coordinate_System.g_12 = null;}
		
          try {
            ids.Coordinate_System.g_13 = UALLowLevel.getVect3DDouble(expIdx, path, "Coordinate_System" + "/g_13");
          } catch(Exception exc){ids.Coordinate_System.g_13 = null;}
		
          try {
            ids.Coordinate_System.g_22 = UALLowLevel.getVect3DDouble(expIdx, path, "Coordinate_System" + "/g_22");
          } catch(Exception exc){ids.Coordinate_System.g_22 = null;}
		
          try {
            ids.Coordinate_System.g_23 = UALLowLevel.getVect3DDouble(expIdx, path, "Coordinate_System" + "/g_23");
          } catch(Exception exc){ids.Coordinate_System.g_23 = null;}
		
          try {
            ids.Coordinate_System.g_33 = UALLowLevel.getVect3DDouble(expIdx, path, "Coordinate_System" + "/g_33");
          } catch(Exception exc){ids.Coordinate_System.g_33 = null;}
		
          try {
            ids.Code_Parameters.Code_Name = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Name");
          } catch(Exception exc){ids.Code_Parameters.Code_Name = null;}
		
          try {
            ids.Code_Parameters.Code_Version = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Version");
          } catch(Exception exc){ids.Code_Parameters.Code_Version = null;}
		
          try {
            ids.Code_Parameters.Parameters = UALLowLevel.getVect1DString(expIdx, path, "Code_Parameters" + "/Parameters");
          } catch(Exception exc){ids.Code_Parameters.Parameters = null;}
		
          try {
            ids.Code_Parameters.Output_Diagnostics = UALLowLevel.getVect1DString(expIdx, path, "Code_Parameters" + "/Output_Diagnostics");
          } catch(Exception exc){ids.Code_Parameters.Output_Diagnostics = null;}
		
          try {
            ids.Code_Parameters.Output_Flag = UALLowLevel.getVect1DInt(expIdx, path, "Code_Parameters" + "/Output_Flag");
          } catch(Exception exc){ids.Code_Parameters.Output_Flag = null;}
		
          try {
            ids.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Timebase");
          } catch(Exception exc){ids.Timebase = null;}
		
      UALLowLevel.endIDSGet(expIdx, path);
      return ids;
    }

 /**
 * Method getSlice retrieves the  Equilibrium IDS in the open database corresponding to the passed time, based on the selectted interpolation mode.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param time The retrieval time.
 * @param interpolMode Defines the selected interpolation. Allowed values are imas.INTERPOLATION, imas.CLOSEST_SAMPLE,
 * imas.PREVIOUS_SAMPLE
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the selected Equilibrium ids. Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if double and empty String is string.
 **/
    public static Equilibrium  getSlice(int expIdx, String path, double time, int interpolMode) throws UALException
    {
      String        timebasepath;
      String        ual_debug = System.getenv("ual_debug");
      double        retTime;
      Equilibrium ids = new Equilibrium (); 
      retTime = UALLowLevel.beginIDSGetSlice(expIdx, path, time);

          try {
            ids.IDS_Properties.Status_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Status_of");
          } catch(Exception exc){ids.IDS_Properties.Status_of = null;}
		
          try {
            ids.IDS_Properties.Comment_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Comment_of");
          } catch(Exception exc){ids.IDS_Properties.Comment_of = null;}
		
          try {
            ids.IDS_Properties.Creator_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Creator_of");
          } catch(Exception exc){ids.IDS_Properties.Creator_of = null;}
		
          try {
            ids.IDS_Properties.Date_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Date_of");
          } catch(Exception exc){ids.IDS_Properties.Date_of = null;}
		
          try {
            ids.IDS_Properties.Source_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Source_of");
          } catch(Exception exc){ids.IDS_Properties.Source_of = null;}
		
          try {
            ids.IDS_Properties.Reference_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Reference_of");
          } catch(Exception exc){ids.IDS_Properties.Reference_of = null;}
		
          try {
            ids.IDS_Properties.Homogeneous_Timebase = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase");
          } catch(Exception exc){ids.IDS_Properties.Homogeneous_Timebase = EMPTY_INT;}
		
          try {
            ids.IDS_Properties.cocos = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/cocos");
          } catch(Exception exc){ids.IDS_Properties.cocos = EMPTY_INT;}
		
// Get Slice Boundary/Type
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/Type");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.Type = new Vect1DInt(1);
              ids.Boundary.Type.setElementAt(0, UALLowLevel.getIntSlice(expIdx, path, "Boundary" + "/Type", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.Type = null;}
		
// Get Slice Boundary/N_Outline_Points
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/N_Outline_Points");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.N_Outline_Points = new Vect1DInt(1);
              ids.Boundary.N_Outline_Points.setElementAt(0, UALLowLevel.getIntSlice(expIdx, path, "Boundary" + "/N_Outline_Points", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.N_Outline_Points = null;}
		
// Get Slice Boundary/Outline_RZ
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/Outline_RZ");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.Outline_RZ = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Boundary.Outline_RZ.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Boundary" + "/Outline_RZ", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.Outline_RZ = null;}
		
// Get Slice Boundary/Geometric_Axis_RZ
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/Geometric_Axis_RZ");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.Geometric_Axis_RZ = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Boundary.Geometric_Axis_RZ.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Boundary" + "/Geometric_Axis_RZ", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.Geometric_Axis_RZ = null;}
		
// Get Slice Boundary/a_minor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/a_minor");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.a_minor = new Vect1DDouble(1);
              ids.Boundary.a_minor.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Boundary" + "/a_minor", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.a_minor = null;}
		
// Get Slice Boundary/Elongation
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/Elongation");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.Elongation = new Vect1DDouble(1);
              ids.Boundary.Elongation.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Boundary" + "/Elongation", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.Elongation = null;}
		
// Get Slice Boundary/Elongation_Upper
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/Elongation_Upper");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.Elongation_Upper = new Vect1DDouble(1);
              ids.Boundary.Elongation_Upper.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Boundary" + "/Elongation_Upper", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.Elongation_Upper = null;}
		
// Get Slice Boundary/Elongation_Lower
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/Elongation_Lower");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.Elongation_Lower = new Vect1DDouble(1);
              ids.Boundary.Elongation_Lower.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Boundary" + "/Elongation_Lower", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.Elongation_Lower = null;}
		
// Get Slice Boundary/Triangularity_Upper
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/Triangularity_Upper");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.Triangularity_Upper = new Vect1DDouble(1);
              ids.Boundary.Triangularity_Upper.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Boundary" + "/Triangularity_Upper", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.Triangularity_Upper = null;}
		
// Get Slice Boundary/Triangularity_Lower
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/Triangularity_Lower");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.Triangularity_Lower = new Vect1DDouble(1);
              ids.Boundary.Triangularity_Lower.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Boundary" + "/Triangularity_Lower", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.Triangularity_Lower = null;}
		
// Get Slice Boundary/N_X_Points
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/N_X_Points");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.N_X_Points = new Vect1DInt(1);
              ids.Boundary.N_X_Points.setElementAt(0, UALLowLevel.getIntSlice(expIdx, path, "Boundary" + "/N_X_Points", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.N_X_Points = null;}
		
// Get Slice Boundary/X_Points_RZ
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/X_Points_RZ");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.X_Points_RZ = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Boundary.X_Points_RZ.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Boundary" + "/X_Points_RZ", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.X_Points_RZ = null;}
		
// Get Slice Boundary/Strike_Point_Lower_Left_RZ
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/Strike_Point_Lower_Left_RZ");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.Strike_Point_Lower_Left_RZ = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Boundary.Strike_Point_Lower_Left_RZ.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Boundary" + "/Strike_Point_Lower_Left_RZ", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.Strike_Point_Lower_Left_RZ = null;}
		
// Get Slice Boundary/Strike_Point_Upper_Left_RZ
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/Strike_Point_Upper_Left_RZ");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.Strike_Point_Upper_Left_RZ = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Boundary.Strike_Point_Upper_Left_RZ.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Boundary" + "/Strike_Point_Upper_Left_RZ", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.Strike_Point_Upper_Left_RZ = null;}
		
// Get Slice Boundary/Strike_Point_Lower_Right_RZ
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/Strike_Point_Lower_Right_RZ");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.Strike_Point_Lower_Right_RZ = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Boundary.Strike_Point_Lower_Right_RZ.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Boundary" + "/Strike_Point_Lower_Right_RZ", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.Strike_Point_Lower_Right_RZ = null;}
		
// Get Slice Boundary/Strike_Point_Upper_Right_RZ
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/Strike_Point_Upper_Right_RZ");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.Strike_Point_Upper_Right_RZ = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Boundary.Strike_Point_Upper_Right_RZ.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Boundary" + "/Strike_Point_Upper_Right_RZ", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.Strike_Point_Upper_Right_RZ = null;}
		
// Get Slice Boundary/Active_Limiter_Point_RZ
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Boundary" + "/Active_Limiter_Point_RZ");
	    if (obj.getElementAt(0) > 0) {
              ids.Boundary.Active_Limiter_Point_RZ = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Boundary.Active_Limiter_Point_RZ.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Boundary" + "/Active_Limiter_Point_RZ", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Boundary.Active_Limiter_Point_RZ = null;}
		
// Get Slice Global/Beta_Pol
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Beta_Pol");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Beta_Pol = new Vect1DDouble(1);
              ids.Global.Beta_Pol.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Beta_Pol", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Beta_Pol = null;}
		
// Get Slice Global/Beta_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Beta_Tor");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Beta_Tor = new Vect1DDouble(1);
              ids.Global.Beta_Tor.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Beta_Tor", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Beta_Tor = null;}
		
// Get Slice Global/Beta_Normal
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Beta_Normal");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Beta_Normal = new Vect1DDouble(1);
              ids.Global.Beta_Normal.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Beta_Normal", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Beta_Normal = null;}
		
// Get Slice Global/Ip
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Ip");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Ip = new Vect1DDouble(1);
              ids.Global.Ip.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Ip", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Ip = null;}
		
// Get Slice Global/li_3
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/li_3");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.li_3 = new Vect1DDouble(1);
              ids.Global.li_3.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/li_3", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.li_3 = null;}
		
// Get Slice Global/Volume
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Volume");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Volume = new Vect1DDouble(1);
              ids.Global.Volume.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Volume", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Volume = null;}
		
// Get Slice Global/Area
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Area");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Area = new Vect1DDouble(1);
              ids.Global.Area.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Area", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Area = null;}
		
// Get Slice Global/Psi_Axis
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Psi_Axis");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Psi_Axis = new Vect1DDouble(1);
              ids.Global.Psi_Axis.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Psi_Axis", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Psi_Axis = null;}
		
// Get Slice Global/Psi_Boundary
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Psi_Boundary");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Psi_Boundary = new Vect1DDouble(1);
              ids.Global.Psi_Boundary.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Psi_Boundary", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Psi_Boundary = null;}
		
// Get Slice Global/Magnetic_Axis/R
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Magnetic_Axis" + "/R");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Magnetic_Axis.R = new Vect1DDouble(1);
              ids.Global.Magnetic_Axis.R.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Magnetic_Axis" + "/R", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Magnetic_Axis.R = null;}
		
// Get Slice Global/Magnetic_Axis/Z
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Magnetic_Axis" + "/Z");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Magnetic_Axis.Z = new Vect1DDouble(1);
              ids.Global.Magnetic_Axis.Z.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Magnetic_Axis" + "/Z", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Magnetic_Axis.Z = null;}
		
// Get Slice Global/B_Tor_Axis
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/B_Tor_Axis");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.B_Tor_Axis = new Vect1DDouble(1);
              ids.Global.B_Tor_Axis.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/B_Tor_Axis", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.B_Tor_Axis = null;}
		
// Get Slice Global/q_Axis
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/q_Axis");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.q_Axis = new Vect1DDouble(1);
              ids.Global.q_Axis.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/q_Axis", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.q_Axis = null;}
		
// Get Slice Global/q_95
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/q_95");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.q_95 = new Vect1DDouble(1);
              ids.Global.q_95.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/q_95", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.q_95 = null;}
		
// Get Slice Global/q_Min
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/q_Min");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.q_Min = new Vect1DDouble(1);
              ids.Global.q_Min.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/q_Min", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.q_Min = null;}
		
          try {
            ids.Global.Vacuum_Toroidal_Field.R0 = UALLowLevel.getDouble(expIdx, path, "Global" + "/Vacuum_Toroidal_Field" + "/R0");
          } catch(Exception exc){ids.Global.Vacuum_Toroidal_Field.R0 = EMPTY_DOUBLE;}
		
// Get Slice Global/Vacuum_Toroidal_Field/B0
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Vacuum_Toroidal_Field" + "/B0");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Vacuum_Toroidal_Field.B0 = new Vect1DDouble(1);
              ids.Global.Vacuum_Toroidal_Field.B0.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Vacuum_Toroidal_Field" + "/B0", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Vacuum_Toroidal_Field.B0 = null;}
		
// Get Slice Global/W_MHD
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/W_MHD");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.W_MHD = new Vect1DDouble(1);
              ids.Global.W_MHD.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/W_MHD", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.W_MHD = null;}
		
// Get Slice Global/Gamma
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Global" + "/Gamma");
	    if (obj.getElementAt(0) > 0) {
              ids.Global.Gamma = new Vect1DDouble(1);
              ids.Global.Gamma.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Global" + "/Gamma", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Global.Gamma = null;}
		
// Get Slice Profiles_1D/Psi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/Psi");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.Psi = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.Psi.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/Psi", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.Psi = null;}
		
// Get Slice Profiles_1D/Phi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/Phi");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.Phi = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.Phi.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/Phi", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.Phi = null;}
		
// Get Slice Profiles_1D/Pressure
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/Pressure");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.Pressure = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.Pressure.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/Pressure", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.Pressure = null;}
		
// Get Slice Profiles_1D/F
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/F");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.F = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.F.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/F", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.F = null;}
		
// Get Slice Profiles_1D/dPressure_dPsi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/dPressure_dPsi");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.dPressure_dPsi = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.dPressure_dPsi.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/dPressure_dPsi", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.dPressure_dPsi = null;}
		
// Get Slice Profiles_1D/F_dF_dPsi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/F_dF_dPsi");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.F_dF_dPsi = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.F_dF_dPsi.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/F_dF_dPsi", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.F_dF_dPsi = null;}
		
// Get Slice Profiles_1D/J_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/J_Tor");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.J_Tor = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.J_Tor.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/J_Tor", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.J_Tor = null;}
		
// Get Slice Profiles_1D/J_Parallel
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/J_Parallel");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.J_Parallel = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.J_Parallel.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/J_Parallel", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.J_Parallel = null;}
		
// Get Slice Profiles_1D/q
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/q");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.q = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.q.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/q", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.q = null;}
		
// Get Slice Profiles_1D/Magnetic_Shear
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/Magnetic_Shear");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.Magnetic_Shear = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.Magnetic_Shear.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/Magnetic_Shear", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.Magnetic_Shear = null;}
		
// Get Slice Profiles_1D/R_Inboard
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/R_Inboard");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.R_Inboard = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.R_Inboard.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/R_Inboard", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.R_Inboard = null;}
		
// Get Slice Profiles_1D/R_Outboard
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/R_Outboard");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.R_Outboard = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.R_Outboard.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/R_Outboard", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.R_Outboard = null;}
		
// Get Slice Profiles_1D/Rho_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/Rho_Tor");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.Rho_Tor = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.Rho_Tor.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/Rho_Tor", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.Rho_Tor = null;}
		
// Get Slice Profiles_1D/dPsi_dRho_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/dPsi_dRho_Tor");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.dPsi_dRho_Tor = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.dPsi_dRho_Tor.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/dPsi_dRho_Tor", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.dPsi_dRho_Tor = null;}
		
// Get Slice Profiles_1D/Rho_Vol_Norm
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/Rho_Vol_Norm");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.Rho_Vol_Norm = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.Rho_Vol_Norm.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/Rho_Vol_Norm", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.Rho_Vol_Norm = null;}
		
// Get Slice Profiles_1D/Elongation
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/Elongation");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.Elongation = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.Elongation.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/Elongation", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.Elongation = null;}
		
// Get Slice Profiles_1D/Triangularity_Upper
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/Triangularity_Upper");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.Triangularity_Upper = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.Triangularity_Upper.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/Triangularity_Upper", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.Triangularity_Upper = null;}
		
// Get Slice Profiles_1D/Triangularity_Lower
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/Triangularity_Lower");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.Triangularity_Lower = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.Triangularity_Lower.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/Triangularity_Lower", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.Triangularity_Lower = null;}
		
// Get Slice Profiles_1D/Volume
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/Volume");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.Volume = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.Volume.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/Volume", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.Volume = null;}
		
// Get Slice Profiles_1D/dVolume_dPsi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/dVolume_dPsi");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.dVolume_dPsi = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.dVolume_dPsi.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/dVolume_dPsi", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.dVolume_dPsi = null;}
		
// Get Slice Profiles_1D/dVolume_dRho_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/dVolume_dRho_Tor");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.dVolume_dRho_Tor = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.dVolume_dRho_Tor.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/dVolume_dRho_Tor", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.dVolume_dRho_Tor = null;}
		
// Get Slice Profiles_1D/Area
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/Area");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.Area = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.Area.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/Area", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.Area = null;}
		
// Get Slice Profiles_1D/dArea_dPsi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/dArea_dPsi");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.dArea_dPsi = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.dArea_dPsi.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/dArea_dPsi", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.dArea_dPsi = null;}
		
// Get Slice Profiles_1D/Surface
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/Surface");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.Surface = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.Surface.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/Surface", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.Surface = null;}
		
// Get Slice Profiles_1D/Trapped_Fraction
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/Trapped_Fraction");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.Trapped_Fraction = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.Trapped_Fraction.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/Trapped_Fraction", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.Trapped_Fraction = null;}
		
// Get Slice Profiles_1D/gm1
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/gm1");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.gm1 = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.gm1.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm1", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.gm1 = null;}
		
// Get Slice Profiles_1D/gm2
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/gm2");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.gm2 = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.gm2.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm2", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.gm2 = null;}
		
// Get Slice Profiles_1D/gm3
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/gm3");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.gm3 = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.gm3.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm3", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.gm3 = null;}
		
// Get Slice Profiles_1D/gm4
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/gm4");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.gm4 = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.gm4.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm4", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.gm4 = null;}
		
// Get Slice Profiles_1D/gm5
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/gm5");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.gm5 = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.gm5.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm5", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.gm5 = null;}
		
// Get Slice Profiles_1D/gm6
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/gm6");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.gm6 = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.gm6.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm6", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.gm6 = null;}
		
// Get Slice Profiles_1D/gm7
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/gm7");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.gm7 = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.gm7.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm7", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.gm7 = null;}
		
// Get Slice Profiles_1D/gm8
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/gm8");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.gm8 = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.gm8.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm8", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.gm8 = null;}
		
// Get Slice Profiles_1D/gm9
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/gm9");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.gm9 = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.gm9.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/gm9", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.gm9 = null;}
		
// Get Slice Profiles_1D/B_Average
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/B_Average");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.B_Average = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.B_Average.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/B_Average", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.B_Average = null;}
		
// Get Slice Profiles_1D/B_Min
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/B_Min");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.B_Min = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.B_Min.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/B_Min", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.B_Min = null;}
		
// Get Slice Profiles_1D/B_Max
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_1D" + "/B_Max");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_1D.B_Max = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_1D.B_Max.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_1D" + "/B_Max", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_1D.B_Max = null;}
		
// Get Array of Structures Profiles_2D
          try {
            ids.Profiles_2D = new Profiles_2DClass[UALLowLevel.getInt(expIdx,path, "Profiles_2D/Shape_of")];
            for (int iProfiles_2D = 0; iProfiles_2D<ids.Profiles_2D.length; iProfiles_2D++){
              ids.Profiles_2D[iProfiles_2D] = new Profiles_2DClass();
			
          try {
            ids.Profiles_2D[iProfiles_2D].Grid_Type = UALLowLevel.getString(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid_Type");
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].Grid_Type = null;}
		
// Get Slice Profiles_2D/Grid/Dim1
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid" + "/Dim1");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_2D[iProfiles_2D].Grid.Dim1 = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_2D[iProfiles_2D].Grid.Dim1.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid" + "/Dim1", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].Grid.Dim1 = null;}
		
// Get Slice Profiles_2D/Grid/Dim2
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid" + "/Dim2");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_2D[iProfiles_2D].Grid.Dim2 = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Profiles_2D[iProfiles_2D].Grid.Dim2.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid" + "/Dim2", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].Grid.Dim2 = null;}
		
// Get Slice Profiles_2D/R
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/R");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_2D[iProfiles_2D].R = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Profiles_2D[iProfiles_2D].R.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/R", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].R = null;}
		
// Get Slice Profiles_2D/Z
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Z");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_2D[iProfiles_2D].Z = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Profiles_2D[iProfiles_2D].Z.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Z", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].Z = null;}
		
// Get Slice Profiles_2D/Psi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Psi");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_2D[iProfiles_2D].Psi = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Profiles_2D[iProfiles_2D].Psi.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Psi", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].Psi = null;}
		
// Get Slice Profiles_2D/Theta
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Theta");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_2D[iProfiles_2D].Theta = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Profiles_2D[iProfiles_2D].Theta.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Theta", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].Theta = null;}
		
// Get Slice Profiles_2D/Phi
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Phi");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_2D[iProfiles_2D].Phi = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Profiles_2D[iProfiles_2D].Phi.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Phi", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].Phi = null;}
		
// Get Slice Profiles_2D/J_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/J_Tor");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_2D[iProfiles_2D].J_Tor = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Profiles_2D[iProfiles_2D].J_Tor.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/J_Tor", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].J_Tor = null;}
		
// Get Slice Profiles_2D/J_Parallel
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/J_Parallel");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_2D[iProfiles_2D].J_Parallel = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Profiles_2D[iProfiles_2D].J_Parallel.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/J_Parallel", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].J_Parallel = null;}
		
// Get Slice Profiles_2D/B_R
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_R");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_2D[iProfiles_2D].B_R = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Profiles_2D[iProfiles_2D].B_R.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_R", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].B_R = null;}
		
// Get Slice Profiles_2D/B_Z
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_Z");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_2D[iProfiles_2D].B_Z = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Profiles_2D[iProfiles_2D].B_Z.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_Z", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].B_Z = null;}
		
// Get Slice Profiles_2D/B_Tor
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_Tor");
	    if (obj.getElementAt(0) > 0) {
              ids.Profiles_2D[iProfiles_2D].B_Tor = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Profiles_2D[iProfiles_2D].B_Tor.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_Tor", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Profiles_2D[iProfiles_2D].B_Tor = null;}
		
            }
          } catch(Exception exc){ids.Profiles_2D = null;}
		
          try {
            ids.Coordinate_System.Grid_Type = UALLowLevel.getString(expIdx, path, "Coordinate_System" + "/Grid_Type");
          } catch(Exception exc){ids.Coordinate_System.Grid_Type = null;}
		
// Get Slice Coordinate_System/Grid/Dim1
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Coordinate_System" + "/Grid" + "/Dim1");
	    if (obj.getElementAt(0) > 0) {
              ids.Coordinate_System.Grid.Dim1 = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Coordinate_System.Grid.Dim1.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Coordinate_System" + "/Grid" + "/Dim1", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Coordinate_System.Grid.Dim1 = null;}
		
// Get Slice Coordinate_System/Grid/Dim2
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Coordinate_System" + "/Grid" + "/Dim2");
	    if (obj.getElementAt(0) > 0) {
              ids.Coordinate_System.Grid.Dim2 = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Coordinate_System.Grid.Dim2.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Coordinate_System" + "/Grid" + "/Dim2", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Coordinate_System.Grid.Dim2 = null;}
		
// Get Slice Coordinate_System/R
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Coordinate_System" + "/R");
	    if (obj.getElementAt(0) > 0) {
              ids.Coordinate_System.R = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Coordinate_System.R.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Coordinate_System" + "/R", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Coordinate_System.R = null;}
		
// Get Slice Coordinate_System/Z
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Coordinate_System" + "/Z");
	    if (obj.getElementAt(0) > 0) {
              ids.Coordinate_System.Z = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Coordinate_System.Z.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Coordinate_System" + "/Z", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Coordinate_System.Z = null;}
		
// Get Slice Coordinate_System/Jacobian
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Coordinate_System" + "/Jacobian");
	    if (obj.getElementAt(0) > 0) {
              ids.Coordinate_System.Jacobian = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Coordinate_System.Jacobian.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Coordinate_System" + "/Jacobian", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Coordinate_System.Jacobian = null;}
		
// Get Slice Coordinate_System/g_11
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Coordinate_System" + "/g_11");
	    if (obj.getElementAt(0) > 0) {
              ids.Coordinate_System.g_11 = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Coordinate_System.g_11.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Coordinate_System" + "/g_11", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Coordinate_System.g_11 = null;}
		
// Get Slice Coordinate_System/g_12
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Coordinate_System" + "/g_12");
	    if (obj.getElementAt(0) > 0) {
              ids.Coordinate_System.g_12 = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Coordinate_System.g_12.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Coordinate_System" + "/g_12", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Coordinate_System.g_12 = null;}
		
// Get Slice Coordinate_System/g_13
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Coordinate_System" + "/g_13");
	    if (obj.getElementAt(0) > 0) {
              ids.Coordinate_System.g_13 = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Coordinate_System.g_13.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Coordinate_System" + "/g_13", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Coordinate_System.g_13 = null;}
		
// Get Slice Coordinate_System/g_22
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Coordinate_System" + "/g_22");
	    if (obj.getElementAt(0) > 0) {
              ids.Coordinate_System.g_22 = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Coordinate_System.g_22.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Coordinate_System" + "/g_22", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Coordinate_System.g_22 = null;}
		
// Get Slice Coordinate_System/g_23
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Coordinate_System" + "/g_23");
	    if (obj.getElementAt(0) > 0) {
              ids.Coordinate_System.g_23 = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Coordinate_System.g_23.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Coordinate_System" + "/g_23", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Coordinate_System.g_23 = null;}
		
// Get Slice Coordinate_System/g_33
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Coordinate_System" + "/g_33");
	    if (obj.getElementAt(0) > 0) {
              ids.Coordinate_System.g_33 = new Vect3DDouble(obj.getElementAt(0),obj.getElementAt(1),1);
              ids.Coordinate_System.g_33.setElementAt(0, UALLowLevel.getVect2DDoubleSlice(expIdx, path, "Coordinate_System" + "/g_33", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Coordinate_System.g_33 = null;}
		
          try {
            ids.Code_Parameters.Code_Name = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Name");
          } catch(Exception exc){ids.Code_Parameters.Code_Name = null;}
		
          try {
            ids.Code_Parameters.Code_Version = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Version");
          } catch(Exception exc){ids.Code_Parameters.Code_Version = null;}
		
// Get Slice Code_Parameters/Parameters
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
            ids.Code_Parameters.Parameters = new Vect1DString(1);
            ids.Code_Parameters.Parameters.setElementAt(0, UALLowLevel.getStringSlice(expIdx, path, "Code_Parameters" + "/Parameters", timebasepath.trim(), time, interpolMode));
          } catch(Exception exc){ids.Code_Parameters.Parameters = null;}
		
// Get Slice Code_Parameters/Output_Diagnostics
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
            ids.Code_Parameters.Output_Diagnostics = new Vect1DString(1);
            ids.Code_Parameters.Output_Diagnostics.setElementAt(0, UALLowLevel.getStringSlice(expIdx, path, "Code_Parameters" + "/Output_Diagnostics", timebasepath.trim(), time, interpolMode));
          } catch(Exception exc){ids.Code_Parameters.Output_Diagnostics = null;}
		
// Get Slice Code_Parameters/Output_Flag
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Code_Parameters" + "/Output_Flag");
	    if (obj.getElementAt(0) > 0) {
              ids.Code_Parameters.Output_Flag = new Vect1DInt(1);
              ids.Code_Parameters.Output_Flag.setElementAt(0, UALLowLevel.getIntSlice(expIdx, path, "Code_Parameters" + "/Output_Flag", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Code_Parameters.Output_Flag = null;}
		
// Get Slice Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Timebase = new Vect1DDouble(1);
              ids.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "/Timebase", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Timebase = null;}
		
      UALLowLevel.endIDSGetSlice(expIdx, path);
      return ids;
    }
    
 /**
 * Method delete removes all the data associated with a Equilibrium IDS in the open database.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void delete(int expIdx, String path) throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");
      
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Status_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Comment_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Creator_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Date_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Source_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Reference_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Homogeneous_Timebase");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/cocos");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/Type");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/N_Outline_Points");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/Outline_RZ");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/Geometric_Axis_RZ");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/a_minor");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/Elongation");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/Elongation_Upper");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/Elongation_Lower");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/Triangularity_Upper");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/Triangularity_Lower");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/N_X_Points");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/X_Points_RZ");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/Strike_Point_Lower_Left_RZ");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/Strike_Point_Upper_Left_RZ");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/Strike_Point_Lower_Right_RZ");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/Strike_Point_Upper_Right_RZ");          
      UALLowLevel.deleteData(expIdx,path,"Boundary" + "/Active_Limiter_Point_RZ");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Beta_Pol");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Beta_Tor");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Beta_Normal");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Ip");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/li_3");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Volume");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Area");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Psi_Axis");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Psi_Boundary");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Magnetic_Axis" + "/R");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Magnetic_Axis" + "/Z");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/B_Tor_Axis");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/q_Axis");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/q_95");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/q_Min");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Vacuum_Toroidal_Field" + "/R0");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Vacuum_Toroidal_Field" + "/B0");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/W_MHD");          
      UALLowLevel.deleteData(expIdx,path,"Global" + "/Gamma");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/Psi");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/Phi");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/Pressure");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/F");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/dPressure_dPsi");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/F_dF_dPsi");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/J_Tor");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/J_Parallel");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/q");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/Magnetic_Shear");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/R_Inboard");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/R_Outboard");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/Rho_Tor");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/dPsi_dRho_Tor");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/Rho_Vol_Norm");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/Elongation");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/Triangularity_Upper");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/Triangularity_Lower");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/Volume");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/dVolume_dPsi");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/dVolume_dRho_Tor");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/Area");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/dArea_dPsi");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/Surface");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/Trapped_Fraction");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/gm1");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/gm2");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/gm3");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/gm4");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/gm5");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/gm6");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/gm7");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/gm8");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/gm9");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/B_Average");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/B_Min");          
      UALLowLevel.deleteData(expIdx,path,"Profiles_1D" + "/B_Max");          
      for (int iProfiles_2D = 0; iProfiles_2D<4; iProfiles_2D++){
      
      UALLowLevel.deleteData(expIdx,path,"/Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid_Type");          
      UALLowLevel.deleteData(expIdx,path,"/Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid" + "/Dim1");          
      UALLowLevel.deleteData(expIdx,path,"/Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Grid" + "/Dim2");          
      UALLowLevel.deleteData(expIdx,path,"/Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/R");          
      UALLowLevel.deleteData(expIdx,path,"/Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Z");          
      UALLowLevel.deleteData(expIdx,path,"/Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Psi");          
      UALLowLevel.deleteData(expIdx,path,"/Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Theta");          
      UALLowLevel.deleteData(expIdx,path,"/Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/Phi");          
      UALLowLevel.deleteData(expIdx,path,"/Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/J_Tor");          
      UALLowLevel.deleteData(expIdx,path,"/Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/J_Parallel");          
      UALLowLevel.deleteData(expIdx,path,"/Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_R");          
      UALLowLevel.deleteData(expIdx,path,"/Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_Z");          
      UALLowLevel.deleteData(expIdx,path,"/Profiles_2D/" + Integer.toString(iProfiles_2D+1).trim() + "/B_Tor");          
      }
   
      UALLowLevel.deleteData(expIdx,path,"Coordinate_System" + "/Grid_Type");          
      UALLowLevel.deleteData(expIdx,path,"Coordinate_System" + "/Grid" + "/Dim1");          
      UALLowLevel.deleteData(expIdx,path,"Coordinate_System" + "/Grid" + "/Dim2");          
      UALLowLevel.deleteData(expIdx,path,"Coordinate_System" + "/R");          
      UALLowLevel.deleteData(expIdx,path,"Coordinate_System" + "/Z");          
      UALLowLevel.deleteData(expIdx,path,"Coordinate_System" + "/Jacobian");          
      UALLowLevel.deleteData(expIdx,path,"Coordinate_System" + "/g_11");          
      UALLowLevel.deleteData(expIdx,path,"Coordinate_System" + "/g_12");          
      UALLowLevel.deleteData(expIdx,path,"Coordinate_System" + "/g_13");          
      UALLowLevel.deleteData(expIdx,path,"Coordinate_System" + "/g_22");          
      UALLowLevel.deleteData(expIdx,path,"Coordinate_System" + "/g_23");          
      UALLowLevel.deleteData(expIdx,path,"Coordinate_System" + "/g_33");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Code_Name");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Code_Version");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Parameters");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Output_Diagnostics");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Output_Flag");          
      UALLowLevel.deleteData(expIdx, path, "Timebase");  
    }


     
 /**
 * Method dump is used for debugging and prints the content of the object
 **/
  public void dump()
  {
    System.out.println("***** Equilibrium *****");
    

  System.out.println("IDS_Properties ");
  

  System.out.println("Status_of ");
  
        if(IDS_Properties.Status_of!= null)
            System.out.println(IDS_Properties.Status_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Comment_of ");
  
        if(IDS_Properties.Comment_of!= null)
            System.out.println(IDS_Properties.Comment_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Creator_of ");
  
        if(IDS_Properties.Creator_of!= null)
            System.out.println(IDS_Properties.Creator_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Date_of ");
  
        if(IDS_Properties.Date_of!= null)
            System.out.println(IDS_Properties.Date_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Source_of ");
  
        if(IDS_Properties.Source_of!= null)
            System.out.println(IDS_Properties.Source_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Reference_of ");
  
        if(IDS_Properties.Reference_of!= null)
            System.out.println(IDS_Properties.Reference_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Homogeneous_Timebase ");
  
        if(IDS_Properties.Homogeneous_Timebase != EMPTY_INT)
            System.out.println(IDS_Properties.Homogeneous_Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("cocos ");
  
        if(IDS_Properties.cocos != EMPTY_INT)
            System.out.println(IDS_Properties.cocos);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Boundary ");
  

  System.out.println("Type ");
  
        if(Boundary.Type != null)
            System.out.println(Boundary.Type);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("N_Outline_Points ");
  
        if(Boundary.N_Outline_Points != null)
            System.out.println(Boundary.N_Outline_Points);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Outline_RZ ");
  
        if(Boundary.Outline_RZ != null)
            System.out.println(Boundary.Outline_RZ);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Geometric_Axis_RZ ");
  
        if(Boundary.Geometric_Axis_RZ != null)
            System.out.println(Boundary.Geometric_Axis_RZ);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("a_minor ");
  
         if(Boundary.a_minor != null)
            System.out.println(Boundary.a_minor);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Elongation ");
  
         if(Boundary.Elongation != null)
            System.out.println(Boundary.Elongation);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Elongation_Upper ");
  
         if(Boundary.Elongation_Upper != null)
            System.out.println(Boundary.Elongation_Upper);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Elongation_Lower ");
  
         if(Boundary.Elongation_Lower != null)
            System.out.println(Boundary.Elongation_Lower);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Triangularity_Upper ");
  
         if(Boundary.Triangularity_Upper != null)
            System.out.println(Boundary.Triangularity_Upper);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Triangularity_Lower ");
  
         if(Boundary.Triangularity_Lower != null)
            System.out.println(Boundary.Triangularity_Lower);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("N_X_Points ");
  
        if(Boundary.N_X_Points != null)
            System.out.println(Boundary.N_X_Points);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("X_Points_RZ ");
  
        if(Boundary.X_Points_RZ != null)
            System.out.println(Boundary.X_Points_RZ);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Strike_Point_Lower_Left_RZ ");
  
        if(Boundary.Strike_Point_Lower_Left_RZ != null)
            System.out.println(Boundary.Strike_Point_Lower_Left_RZ);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Strike_Point_Upper_Left_RZ ");
  
        if(Boundary.Strike_Point_Upper_Left_RZ != null)
            System.out.println(Boundary.Strike_Point_Upper_Left_RZ);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Strike_Point_Lower_Right_RZ ");
  
        if(Boundary.Strike_Point_Lower_Right_RZ != null)
            System.out.println(Boundary.Strike_Point_Lower_Right_RZ);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Strike_Point_Upper_Right_RZ ");
  
        if(Boundary.Strike_Point_Upper_Right_RZ != null)
            System.out.println(Boundary.Strike_Point_Upper_Right_RZ);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Active_Limiter_Point_RZ ");
  
        if(Boundary.Active_Limiter_Point_RZ != null)
            System.out.println(Boundary.Active_Limiter_Point_RZ);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Global ");
  

  System.out.println("Beta_Pol ");
  
         if(Global.Beta_Pol != null)
            System.out.println(Global.Beta_Pol);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Beta_Tor ");
  
         if(Global.Beta_Tor != null)
            System.out.println(Global.Beta_Tor);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Beta_Normal ");
  
         if(Global.Beta_Normal != null)
            System.out.println(Global.Beta_Normal);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Ip ");
  
         if(Global.Ip != null)
            System.out.println(Global.Ip);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("li_3 ");
  
         if(Global.li_3 != null)
            System.out.println(Global.li_3);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Volume ");
  
         if(Global.Volume != null)
            System.out.println(Global.Volume);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Area ");
  
         if(Global.Area != null)
            System.out.println(Global.Area);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Psi_Axis ");
  
         if(Global.Psi_Axis != null)
            System.out.println(Global.Psi_Axis);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Psi_Boundary ");
  
         if(Global.Psi_Boundary != null)
            System.out.println(Global.Psi_Boundary);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Magnetic_Axis ");
  

  System.out.println("R ");
  
         if(Global.Magnetic_Axis.R != null)
            System.out.println(Global.Magnetic_Axis.R);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Z ");
  
         if(Global.Magnetic_Axis.Z != null)
            System.out.println(Global.Magnetic_Axis.Z);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("B_Tor_Axis ");
  
         if(Global.B_Tor_Axis != null)
            System.out.println(Global.B_Tor_Axis);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("q_Axis ");
  
         if(Global.q_Axis != null)
            System.out.println(Global.q_Axis);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("q_95 ");
  
         if(Global.q_95 != null)
            System.out.println(Global.q_95);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("q_Min ");
  
         if(Global.q_Min != null)
            System.out.println(Global.q_Min);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Vacuum_Toroidal_Field ");
  

  System.out.println("R0 ");
  
         if(Global.Vacuum_Toroidal_Field.R0 != EMPTY_DOUBLE)
            System.out.println(Global.Vacuum_Toroidal_Field.R0);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("B0 ");
  
         if(Global.Vacuum_Toroidal_Field.B0 != null)
            System.out.println(Global.Vacuum_Toroidal_Field.B0);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("W_MHD ");
  
         if(Global.W_MHD != null)
            System.out.println(Global.W_MHD);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Gamma ");
  
         if(Global.Gamma != null)
            System.out.println(Global.Gamma);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Profiles_1D ");
  

  System.out.println("Psi ");
  
        if(Profiles_1D.Psi != null)
            System.out.println(Profiles_1D.Psi);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Phi ");
  
        if(Profiles_1D.Phi != null)
            System.out.println(Profiles_1D.Phi);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Pressure ");
  
        if(Profiles_1D.Pressure != null)
            System.out.println(Profiles_1D.Pressure);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("F ");
  
        if(Profiles_1D.F != null)
            System.out.println(Profiles_1D.F);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("dPressure_dPsi ");
  
        if(Profiles_1D.dPressure_dPsi != null)
            System.out.println(Profiles_1D.dPressure_dPsi);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("F_dF_dPsi ");
  
        if(Profiles_1D.F_dF_dPsi != null)
            System.out.println(Profiles_1D.F_dF_dPsi);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("J_Tor ");
  
        if(Profiles_1D.J_Tor != null)
            System.out.println(Profiles_1D.J_Tor);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("J_Parallel ");
  
        if(Profiles_1D.J_Parallel != null)
            System.out.println(Profiles_1D.J_Parallel);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("q ");
  
        if(Profiles_1D.q != null)
            System.out.println(Profiles_1D.q);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Magnetic_Shear ");
  
        if(Profiles_1D.Magnetic_Shear != null)
            System.out.println(Profiles_1D.Magnetic_Shear);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("R_Inboard ");
  
        if(Profiles_1D.R_Inboard != null)
            System.out.println(Profiles_1D.R_Inboard);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("R_Outboard ");
  
        if(Profiles_1D.R_Outboard != null)
            System.out.println(Profiles_1D.R_Outboard);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Rho_Tor ");
  
        if(Profiles_1D.Rho_Tor != null)
            System.out.println(Profiles_1D.Rho_Tor);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("dPsi_dRho_Tor ");
  
        if(Profiles_1D.dPsi_dRho_Tor != null)
            System.out.println(Profiles_1D.dPsi_dRho_Tor);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Rho_Vol_Norm ");
  
        if(Profiles_1D.Rho_Vol_Norm != null)
            System.out.println(Profiles_1D.Rho_Vol_Norm);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Elongation ");
  
        if(Profiles_1D.Elongation != null)
            System.out.println(Profiles_1D.Elongation);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Triangularity_Upper ");
  
        if(Profiles_1D.Triangularity_Upper != null)
            System.out.println(Profiles_1D.Triangularity_Upper);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Triangularity_Lower ");
  
        if(Profiles_1D.Triangularity_Lower != null)
            System.out.println(Profiles_1D.Triangularity_Lower);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Volume ");
  
        if(Profiles_1D.Volume != null)
            System.out.println(Profiles_1D.Volume);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("dVolume_dPsi ");
  
        if(Profiles_1D.dVolume_dPsi != null)
            System.out.println(Profiles_1D.dVolume_dPsi);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("dVolume_dRho_Tor ");
  
        if(Profiles_1D.dVolume_dRho_Tor != null)
            System.out.println(Profiles_1D.dVolume_dRho_Tor);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Area ");
  
        if(Profiles_1D.Area != null)
            System.out.println(Profiles_1D.Area);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("dArea_dPsi ");
  
        if(Profiles_1D.dArea_dPsi != null)
            System.out.println(Profiles_1D.dArea_dPsi);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Surface ");
  
        if(Profiles_1D.Surface != null)
            System.out.println(Profiles_1D.Surface);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Trapped_Fraction ");
  
        if(Profiles_1D.Trapped_Fraction != null)
            System.out.println(Profiles_1D.Trapped_Fraction);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("gm1 ");
  
        if(Profiles_1D.gm1 != null)
            System.out.println(Profiles_1D.gm1);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("gm2 ");
  
        if(Profiles_1D.gm2 != null)
            System.out.println(Profiles_1D.gm2);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("gm3 ");
  
        if(Profiles_1D.gm3 != null)
            System.out.println(Profiles_1D.gm3);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("gm4 ");
  
        if(Profiles_1D.gm4 != null)
            System.out.println(Profiles_1D.gm4);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("gm5 ");
  
        if(Profiles_1D.gm5 != null)
            System.out.println(Profiles_1D.gm5);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("gm6 ");
  
        if(Profiles_1D.gm6 != null)
            System.out.println(Profiles_1D.gm6);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("gm7 ");
  
        if(Profiles_1D.gm7 != null)
            System.out.println(Profiles_1D.gm7);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("gm8 ");
  
        if(Profiles_1D.gm8 != null)
            System.out.println(Profiles_1D.gm8);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("gm9 ");
  
        if(Profiles_1D.gm9 != null)
            System.out.println(Profiles_1D.gm9);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("B_Average ");
  
        if(Profiles_1D.B_Average != null)
            System.out.println(Profiles_1D.B_Average);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("B_Min ");
  
        if(Profiles_1D.B_Min != null)
            System.out.println(Profiles_1D.B_Min);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("B_Max ");
  
        if(Profiles_1D.B_Max != null)
            System.out.println(Profiles_1D.B_Max);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Profiles_2D ");
  
      if (Profiles_2D != null) {
        for (int i1 = 0; i1 < Profiles_2D.length; i1++) {
          

  System.out.println("Grid_Type ");
  
        if(Profiles_2D[i1].Grid_Type!= null)
            System.out.println(Profiles_2D[i1].Grid_Type);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Grid ");
  

  System.out.println("Dim1 ");
  
        if(Profiles_2D[i1].Grid.Dim1 != null)
            System.out.println(Profiles_2D[i1].Grid.Dim1);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Dim2 ");
  
        if(Profiles_2D[i1].Grid.Dim2 != null)
            System.out.println(Profiles_2D[i1].Grid.Dim2);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("R ");
  
        if(Profiles_2D[i1].R != null)
            System.out.println(Profiles_2D[i1].R);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Z ");
  
        if(Profiles_2D[i1].Z != null)
            System.out.println(Profiles_2D[i1].Z);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Psi ");
  
        if(Profiles_2D[i1].Psi != null)
            System.out.println(Profiles_2D[i1].Psi);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Theta ");
  
        if(Profiles_2D[i1].Theta != null)
            System.out.println(Profiles_2D[i1].Theta);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Phi ");
  
        if(Profiles_2D[i1].Phi != null)
            System.out.println(Profiles_2D[i1].Phi);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("J_Tor ");
  
        if(Profiles_2D[i1].J_Tor != null)
            System.out.println(Profiles_2D[i1].J_Tor);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("J_Parallel ");
  
        if(Profiles_2D[i1].J_Parallel != null)
            System.out.println(Profiles_2D[i1].J_Parallel);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("B_R ");
  
        if(Profiles_2D[i1].B_R != null)
            System.out.println(Profiles_2D[i1].B_R);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("B_Z ");
  
        if(Profiles_2D[i1].B_Z != null)
            System.out.println(Profiles_2D[i1].B_Z);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("B_Tor ");
  
        if(Profiles_2D[i1].B_Tor != null)
            System.out.println(Profiles_2D[i1].B_Tor);
        else
            System.out.println("Empty");
        System.out.println("");    
    
        }
      }
    

  System.out.println("Coordinate_System ");
  

  System.out.println("Grid_Type ");
  
        if(Coordinate_System.Grid_Type!= null)
            System.out.println(Coordinate_System.Grid_Type);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Grid ");
  

  System.out.println("Dim1 ");
  
        if(Coordinate_System.Grid.Dim1 != null)
            System.out.println(Coordinate_System.Grid.Dim1);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Dim2 ");
  
        if(Coordinate_System.Grid.Dim2 != null)
            System.out.println(Coordinate_System.Grid.Dim2);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("R ");
  
        if(Coordinate_System.R != null)
            System.out.println(Coordinate_System.R);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Z ");
  
        if(Coordinate_System.Z != null)
            System.out.println(Coordinate_System.Z);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Jacobian ");
  
        if(Coordinate_System.Jacobian != null)
            System.out.println(Coordinate_System.Jacobian);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("g_11 ");
  
        if(Coordinate_System.g_11 != null)
            System.out.println(Coordinate_System.g_11);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("g_12 ");
  
        if(Coordinate_System.g_12 != null)
            System.out.println(Coordinate_System.g_12);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("g_13 ");
  
        if(Coordinate_System.g_13 != null)
            System.out.println(Coordinate_System.g_13);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("g_22 ");
  
        if(Coordinate_System.g_22 != null)
            System.out.println(Coordinate_System.g_22);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("g_23 ");
  
        if(Coordinate_System.g_23 != null)
            System.out.println(Coordinate_System.g_23);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("g_33 ");
  
        if(Coordinate_System.g_33 != null)
            System.out.println(Coordinate_System.g_33);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Code_Parameters ");
  

  System.out.println("Code_Name ");
  
        if(Code_Parameters.Code_Name!= null)
            System.out.println(Code_Parameters.Code_Name);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Code_Version ");
  
        if(Code_Parameters.Code_Version!= null)
            System.out.println(Code_Parameters.Code_Version);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Parameters ");
  
        if(Code_Parameters.Parameters != null)
            System.out.println(Code_Parameters.Parameters);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Output_Diagnostics ");
  
        if(Code_Parameters.Output_Diagnostics != null)
            System.out.println(Code_Parameters.Output_Diagnostics);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Output_Flag ");
  
        if(Code_Parameters.Output_Flag != null)
            System.out.println(Code_Parameters.Output_Flag);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Timebase ");
  
         if(Timebase != null)
            System.out.println(Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   
    System.out.println("******************");
  }
}


public static class Magnetics
{
    
      public static class IDS_PropertiesClass {
      
      public String Status_of;
    
      public String Comment_of;
    
      public String Creator_of;
    
      public String Date_of;
    
      public String Source_of;
    
      public String Reference_of;
    
      public int Homogeneous_Timebase = EMPTY_INT;
    
      public int cocos = EMPTY_INT;
    
      }
      public IDS_PropertiesClass IDS_Properties = new IDS_PropertiesClass();
    
      public static class Flux_LoopsClass {
      
      public String Name;
    
      public String Identifier;
    
      public Vect2DDouble  Position_RZPhi;
    
      public static class FluxClass {
      
      public Vect1DDouble Data;
    
      public Vect1DDouble Timebase;
    
      }
      public FluxClass Flux = new FluxClass();
    
      }
      public Flux_LoopsClass Flux_Loops[];
    
      public static class Bpol_ProbesClass {
      
      public String Name;
    
      public String Identifier;
    
      public Vect1DDouble Centre_Position_RZPhi;
    
      public double Poloidal_Angle = EMPTY_DOUBLE;
    
      public double Toroidal_Angle = EMPTY_DOUBLE;
    
      public double Area = EMPTY_DOUBLE;
    
      public double Length = EMPTY_DOUBLE;
    
      public int Turns = EMPTY_INT;
    
      public static class FieldClass {
      
      public Vect1DDouble Data;
    
      public Vect1DDouble Timebase;
    
      }
      public FieldClass Field = new FieldClass();
    
      }
      public Bpol_ProbesClass Bpol_Probes[];
    
      public static class Processed_DataClass {
      
      public String Method;
    
      public static class IpClass {
      
      public Vect1DDouble Data;
    
      public Vect1DDouble Timebase;
    
      }
      public IpClass Ip = new IpClass();
    
      public static class Diamagnetic_FluxClass {
      
      public Vect1DDouble Data;
    
      public Vect1DDouble Timebase;
    
      }
      public Diamagnetic_FluxClass Diamagnetic_Flux = new Diamagnetic_FluxClass();
    
      }
      public Processed_DataClass Processed_Data[];
    
      public static class Code_ParametersClass {
      
      public String Code_Name;
    
      public String Code_Version;
    
      public Vect1DString Parameters;
    
      public Vect1DString Output_Diagnostics;
    
      public Vect1DInt Output_Flag;
    
      }
      public Code_ParametersClass Code_Parameters = new Code_ParametersClass();
    
      public Vect1DDouble Timebase;
    

 /**
 * Method copy Magnetics copies a full IDS between two databases.
 * @param srcIdx The index of the source database, returned by imas.open()
 * @param srcOccur The occurence of the IDS in the source database.
 * @param destIdx The index of the destination database, returned by imas.open()
 * @param destOccur The occurence of the IDS in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copy(int srcIdx, int srcOccur, int destIdx, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyIds(srcIdx, destIdx, "Magnetics", srcOccur, destOccur);
    }

 /**
 * Method copyEnv Magnetics copies a full IDS between two databases.
 * @param srcUser User of the source database
 * @param srcTokamak Tokamak of the source database
 * @param srcVersion Version of the source database
 * @param srcShot Shot number of the source database
 * @param srcRun Run number of the source database
 * @param srcOccur The occurence of the IDS in the source database.
 * @param destUser User of the destination database
 * @param destTokamak Tokamak of the destination database
 * @param destVersion Version of the destination database
 * @param destShot Shot number of the destination database
 * @param destRun Run number of the destination database
 * @param destOccur The occurence of the IDS in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copyEnv(String srcUser, String srcTokamak, String srcVersion, int srcShot, int srcRun, int srcOccur, String destUser, String destTokamak, String destVersion, int destShot, int destRun, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyIdsEnv(srcTokamak, srcVersion, srcUser, srcShot, srcRun, srcOccur, destTokamak, destVersion, destUser, destShot, destRun, destOccur, "Magnetics");
    }

/**
 * Method put stores a non timed Magnetics IDSs in the open database. 
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The passed Magnetics ids.
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void put(int expIdx, String path, Magnetics ids)  throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
	  Vect5DDouble  vect5DDouble;
	  Vect6DDouble  vect6DDouble;
	  Vect7DDouble  vect7DDouble;
          String        timebasepath;
          String        ual_debug = System.getenv("ual_debug");

          UALLowLevel.beginIDSPut(expIdx, path);
          
// Put IDS_Properties/Status_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Status_of", ids.IDS_Properties.Status_of);

// Put IDS_Properties/Comment_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Comment_of", ids.IDS_Properties.Comment_of);

// Put IDS_Properties/Creator_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Creator_of", ids.IDS_Properties.Creator_of);

// Put IDS_Properties/Date_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Date_of", ids.IDS_Properties.Date_of);

// Put IDS_Properties/Source_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Source_of", ids.IDS_Properties.Source_of);

// Put IDS_Properties/Reference_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Reference_of", ids.IDS_Properties.Reference_of);

// Put IDS_Properties/Homogeneous_Timebase
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase", ids.IDS_Properties.Homogeneous_Timebase);

// Put IDS_Properties/cocos
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/cocos", ids.IDS_Properties.cocos);

// Put Flux_Loops
          if (ids.Flux_Loops != null) {
            UALLowLevel.putInt(expIdx,path, "Flux_Loops/Shape_of",
       		ids.Flux_Loops.length);
            for (int iFlux_Loops = 0; iFlux_Loops<ids.Flux_Loops.length; iFlux_Loops++){
      
// Put Flux_Loops/Name
          UALLowLevel.putString(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Name", ids.Flux_Loops[iFlux_Loops].Name);

// Put Flux_Loops/Identifier
          UALLowLevel.putString(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Identifier", ids.Flux_Loops[iFlux_Loops].Identifier);

// Put Flux_Loops/Position_RZPhi   
          UALLowLevel.putVect2DDouble(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Position_RZPhi", "", ids.Flux_Loops[iFlux_Loops].Position_RZPhi, false);
   		
// Put Flux_Loops/Flux/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Flux" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Flux_Loops[iFlux_Loops].Flux.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Flux" + "/Data", timebasepath.trim(), ids.Flux_Loops[iFlux_Loops].Flux.Data, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Flux_Loops/Flux/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Flux" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Flux_Loops[iFlux_Loops].Flux.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Flux" + "/Timebase", timebasepath.trim(), ids.Flux_Loops[iFlux_Loops].Flux.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
            }
          }

// Put Bpol_Probes
          if (ids.Bpol_Probes != null) {
            UALLowLevel.putInt(expIdx,path, "Bpol_Probes/Shape_of",
       		ids.Bpol_Probes.length);
            for (int iBpol_Probes = 0; iBpol_Probes<ids.Bpol_Probes.length; iBpol_Probes++){
      
// Put Bpol_Probes/Name
          UALLowLevel.putString(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Name", ids.Bpol_Probes[iBpol_Probes].Name);

// Put Bpol_Probes/Identifier
          UALLowLevel.putString(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Identifier", ids.Bpol_Probes[iBpol_Probes].Identifier);

// Put Bpol_Probes/Centre_Position_RZPhi   
          UALLowLevel.putVect1DDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Centre_Position_RZPhi", "", ids.Bpol_Probes[iBpol_Probes].Centre_Position_RZPhi, false);
   		
// Put Bpol_Probes/Poloidal_Angle
          UALLowLevel.putDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Poloidal_Angle", ids.Bpol_Probes[iBpol_Probes].Poloidal_Angle);

// Put Bpol_Probes/Toroidal_Angle
          UALLowLevel.putDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Toroidal_Angle", ids.Bpol_Probes[iBpol_Probes].Toroidal_Angle);

// Put Bpol_Probes/Area
          UALLowLevel.putDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Area", ids.Bpol_Probes[iBpol_Probes].Area);

// Put Bpol_Probes/Length
          UALLowLevel.putDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Length", ids.Bpol_Probes[iBpol_Probes].Length);

// Put Bpol_Probes/Turns
          UALLowLevel.putInt(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Turns", ids.Bpol_Probes[iBpol_Probes].Turns);

// Put Bpol_Probes/Field/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Field" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Bpol_Probes[iBpol_Probes].Field.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Field" + "/Data", timebasepath.trim(), ids.Bpol_Probes[iBpol_Probes].Field.Data, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Bpol_Probes/Field/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Field" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Bpol_Probes[iBpol_Probes].Field.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Field" + "/Timebase", timebasepath.trim(), ids.Bpol_Probes[iBpol_Probes].Field.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
            }
          }

// Put Processed_Data
          if (ids.Processed_Data != null) {
            UALLowLevel.putInt(expIdx,path, "Processed_Data/Shape_of",
       		ids.Processed_Data.length);
            for (int iProcessed_Data = 0; iProcessed_Data<ids.Processed_Data.length; iProcessed_Data++){
      
// Put Processed_Data/Method
          UALLowLevel.putString(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Method", ids.Processed_Data[iProcessed_Data].Method);

// Put Processed_Data/Ip/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Ip" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Processed_Data[iProcessed_Data].Ip.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Ip" + "/Data", timebasepath.trim(), ids.Processed_Data[iProcessed_Data].Ip.Data, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Processed_Data/Ip/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Ip" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Processed_Data[iProcessed_Data].Ip.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Ip" + "/Timebase", timebasepath.trim(), ids.Processed_Data[iProcessed_Data].Ip.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Processed_Data/Diamagnetic_Flux/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Diamagnetic_Flux" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Processed_Data[iProcessed_Data].Diamagnetic_Flux.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Diamagnetic_Flux" + "/Data", timebasepath.trim(), ids.Processed_Data[iProcessed_Data].Diamagnetic_Flux.Data, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Processed_Data/Diamagnetic_Flux/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Diamagnetic_Flux" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Processed_Data[iProcessed_Data].Diamagnetic_Flux.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Diamagnetic_Flux" + "/Timebase", timebasepath.trim(), ids.Processed_Data[iProcessed_Data].Diamagnetic_Flux.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
            }
          }

// Put Code_Parameters/Code_Name
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Name", ids.Code_Parameters.Code_Name);

// Put Code_Parameters/Code_Version
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Version", ids.Code_Parameters.Code_Version);

// Put Code_Parameters/Parameters
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DString(expIdx, path, "Code_Parameters" + "/Parameters", timebasepath.trim(), ids.Code_Parameters.Parameters, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Code_Parameters/Output_Diagnostics
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DString(expIdx, path, "Code_Parameters" + "/Output_Diagnostics", timebasepath.trim(), ids.Code_Parameters.Output_Diagnostics, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Code_Parameters/Output_Flag
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DInt(expIdx, path, "Code_Parameters" + "/Output_Flag", timebasepath.trim(), ids.Code_Parameters.Output_Flag, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect1DDouble(expIdx,path, "Timebase", timebasepath.trim(), ids.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
          UALLowLevel.endIDSPut(expIdx, path);
    }

 /**
 * Method putNonTimed stores the non time dependent fields of a (timed) Magnetics IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The timed Magnetics IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void putNonTimed(int expIdx, String path, Magnetics ids) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
          Vect5DDouble  vect5DDouble;
          Vect6DDouble  vect6DDouble;
          Vect7DDouble  vect7DDouble;
          String        ual_debug = System.getenv("ual_debug");
          
          delete(expIdx, path);
          UALLowLevel.beginIDSPutNonTimed(expIdx, path);
          
// Put IDS_Properties/Status_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Status_of", ids.IDS_Properties.Status_of);

// Put IDS_Properties/Comment_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Comment_of", ids.IDS_Properties.Comment_of);

// Put IDS_Properties/Creator_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Creator_of", ids.IDS_Properties.Creator_of);

// Put IDS_Properties/Date_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Date_of", ids.IDS_Properties.Date_of);

// Put IDS_Properties/Source_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Source_of", ids.IDS_Properties.Source_of);

// Put IDS_Properties/Reference_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Reference_of", ids.IDS_Properties.Reference_of);

// Put IDS_Properties/Homogeneous_Timebase
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase", ids.IDS_Properties.Homogeneous_Timebase);

// Put IDS_Properties/cocos
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/cocos", ids.IDS_Properties.cocos);

// Put Flux_Loops
          if (ids.Flux_Loops != null) {
            UALLowLevel.putInt(expIdx,path, "Flux_Loops/Shape_of",
       		ids.Flux_Loops.length);
            for (int iFlux_Loops = 0; iFlux_Loops<ids.Flux_Loops.length; iFlux_Loops++){
      
// Put Flux_Loops/Name
          UALLowLevel.putString(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Name", ids.Flux_Loops[iFlux_Loops].Name);

// Put Flux_Loops/Identifier
          UALLowLevel.putString(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Identifier", ids.Flux_Loops[iFlux_Loops].Identifier);

// Put Flux_Loops/Position_RZPhi   
          UALLowLevel.putVect2DDouble(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Position_RZPhi", "", ids.Flux_Loops[iFlux_Loops].Position_RZPhi, false);
   		
            }
          }

// Put Bpol_Probes
          if (ids.Bpol_Probes != null) {
            UALLowLevel.putInt(expIdx,path, "Bpol_Probes/Shape_of",
       		ids.Bpol_Probes.length);
            for (int iBpol_Probes = 0; iBpol_Probes<ids.Bpol_Probes.length; iBpol_Probes++){
      
// Put Bpol_Probes/Name
          UALLowLevel.putString(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Name", ids.Bpol_Probes[iBpol_Probes].Name);

// Put Bpol_Probes/Identifier
          UALLowLevel.putString(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Identifier", ids.Bpol_Probes[iBpol_Probes].Identifier);

// Put Bpol_Probes/Centre_Position_RZPhi   
          UALLowLevel.putVect1DDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Centre_Position_RZPhi", "", ids.Bpol_Probes[iBpol_Probes].Centre_Position_RZPhi, false);
   		
// Put Bpol_Probes/Poloidal_Angle
          UALLowLevel.putDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Poloidal_Angle", ids.Bpol_Probes[iBpol_Probes].Poloidal_Angle);

// Put Bpol_Probes/Toroidal_Angle
          UALLowLevel.putDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Toroidal_Angle", ids.Bpol_Probes[iBpol_Probes].Toroidal_Angle);

// Put Bpol_Probes/Area
          UALLowLevel.putDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Area", ids.Bpol_Probes[iBpol_Probes].Area);

// Put Bpol_Probes/Length
          UALLowLevel.putDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Length", ids.Bpol_Probes[iBpol_Probes].Length);

// Put Bpol_Probes/Turns
          UALLowLevel.putInt(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Turns", ids.Bpol_Probes[iBpol_Probes].Turns);

            }
          }

// Put Processed_Data
          if (ids.Processed_Data != null) {
            UALLowLevel.putInt(expIdx,path, "Processed_Data/Shape_of",
       		ids.Processed_Data.length);
            for (int iProcessed_Data = 0; iProcessed_Data<ids.Processed_Data.length; iProcessed_Data++){
      
// Put Processed_Data/Method
          UALLowLevel.putString(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Method", ids.Processed_Data[iProcessed_Data].Method);

            }
          }

// Put Code_Parameters/Code_Name
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Name", ids.Code_Parameters.Code_Name);

// Put Code_Parameters/Code_Version
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Version", ids.Code_Parameters.Code_Version);

          UALLowLevel.endIDSPutNonTimed(expIdx, path);
    }

    
/**
 * Method putSlice appends a (timed) Magnetics IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param ids The timed Magnetics IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void putSlice(int expIdx, String path, Magnetics ids) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
          Vect5DDouble  vect5DDouble;
          Vect6DDouble  vect6DDouble;
          Vect7DDouble  vect7DDouble;
          String        timebasepath;
          String        ual_debug = System.getenv("ual_debug");
          
    	  if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
            System.out.println("ERROR : the PUT_SLICE routine works only for homogeneous timebase IDS");
            return;
          }
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutSlice(expIdx, path);
          
// Put Flux_Loops
          if (ids.Flux_Loops != null) {
            UALLowLevel.putInt(expIdx,path, "Flux_Loops/Shape_of",
       		ids.Flux_Loops.length);
            for (int iFlux_Loops = 0; iFlux_Loops<ids.Flux_Loops.length; iFlux_Loops++){
      
// Put Slice Flux_Loops/Flux/Data
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Flux" + "/Data", timebasepath.trim(), ids.Flux_Loops[iFlux_Loops].Flux.Data, ids.Timebase.getElementAt(0));
		
// Put Slice Flux_Loops/Flux/Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Flux" + "/Timebase", timebasepath.trim(), ids.Flux_Loops[iFlux_Loops].Flux.Timebase, ids.Timebase.getElementAt(0));
		
            }
          }

// Put Bpol_Probes
          if (ids.Bpol_Probes != null) {
            UALLowLevel.putInt(expIdx,path, "Bpol_Probes/Shape_of",
       		ids.Bpol_Probes.length);
            for (int iBpol_Probes = 0; iBpol_Probes<ids.Bpol_Probes.length; iBpol_Probes++){
      
// Put Slice Bpol_Probes/Field/Data
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Field" + "/Data", timebasepath.trim(), ids.Bpol_Probes[iBpol_Probes].Field.Data, ids.Timebase.getElementAt(0));
		
// Put Slice Bpol_Probes/Field/Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Field" + "/Timebase", timebasepath.trim(), ids.Bpol_Probes[iBpol_Probes].Field.Timebase, ids.Timebase.getElementAt(0));
		
            }
          }

// Put Processed_Data
          if (ids.Processed_Data != null) {
            UALLowLevel.putInt(expIdx,path, "Processed_Data/Shape_of",
       		ids.Processed_Data.length);
            for (int iProcessed_Data = 0; iProcessed_Data<ids.Processed_Data.length; iProcessed_Data++){
      
// Put Slice Processed_Data/Ip/Data
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Ip" + "/Data", timebasepath.trim(), ids.Processed_Data[iProcessed_Data].Ip.Data, ids.Timebase.getElementAt(0));
		
// Put Slice Processed_Data/Ip/Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Ip" + "/Timebase", timebasepath.trim(), ids.Processed_Data[iProcessed_Data].Ip.Timebase, ids.Timebase.getElementAt(0));
		
// Put Slice Processed_Data/Diamagnetic_Flux/Data
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Diamagnetic_Flux" + "/Data", timebasepath.trim(), ids.Processed_Data[iProcessed_Data].Diamagnetic_Flux.Data, ids.Timebase.getElementAt(0));
		
// Put Slice Processed_Data/Diamagnetic_Flux/Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Diamagnetic_Flux" + "/Timebase", timebasepath.trim(), ids.Processed_Data[iProcessed_Data].Diamagnetic_Flux.Timebase, ids.Timebase.getElementAt(0));
		
            }
          }

// Put Slice Code_Parameters/Parameters
          UALLowLevel.putVect1DStringSlice(expIdx, path, "Code_Parameters" + "/Parameters", timebasepath.trim(), ids.Code_Parameters.Parameters, ids.Timebase.getElementAt(0));
		
// Put Slice Code_Parameters/Output_Diagnostics
          UALLowLevel.putVect1DStringSlice(expIdx, path, "Code_Parameters" + "/Output_Diagnostics", timebasepath.trim(), ids.Code_Parameters.Output_Diagnostics, ids.Timebase.getElementAt(0));
		
// Put Slice Code_Parameters/Output_Flag
          UALLowLevel.putVect1DIntSlice(expIdx, path, "Code_Parameters" + "/Output_Flag", timebasepath.trim(), ids.Code_Parameters.Output_Flag, ids.Timebase.getElementAt(0));
		
// Put Slice Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx,path, "Timebase", timebasepath.trim(), ids.Timebase, ids.Timebase.getElementAt(0));
		
          UALLowLevel.endIDSPutSlice(expIdx, path);
    }
    

    

/**
 * Method putdb stores a non timed Magnetics IDSs in the open database only if
 *  datainto.isref == 1. In any case it calls afterwards UALLowLevel.putIdsDb() in order to update catalogue
 * @param expIdx The index to the database, returned by imas.open()
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected ids. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @param ids The  Magnetics IDSs
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
/*
    public static void putdb(int expIdx, String user, String machine, int shot, int run, 
    	String path, int occurrence, Magnetics ids) throws UALException
    {
    	String actPath;
        if(occurrence > 0)
	    actPath = path+"/"+occurrence;
	else
	    actPath = path;
	if(ids.IDS_Properties.isref == 1)
	{
	    put(expIdx, actPath, ids);
	    UALLowLevel.putIdsDb(user, machine, shot, run, path, occurrence, 1,
	    	user, machine, shot, run, occurrence);
	}
	else //isref == 2
	{
	    UALLowLevel.putIdsDb(user, machine, shot, run, path, occurrence, 2,
	    	ids.IDS_Properties.whatref.user,  ids.IDS_Properties.whatref.machine,
		ids.IDS_Properties.whatref.shot, ids.IDS_Properties.whatref.run, 
		ids.IDS_Properties.whatref.occurrence);
	
	}
    }
*/

/**
 * Method getdb retrieves the non timed Magnetics IDSs in the open database, taking the correct reference.
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the Magnetics IDS . Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if float or double and empty String is string.
 **/
    public static Magnetics getdb(String user, String machine, int shot, int run, String version, String path, int occurrence) throws UALException
    {
//		int idx = UALLowLevel.openDb(user, machine, shot, run, path, occurrence);
		int idx = openEnv("ids", shot, run, user, machine, version);
		if (occurrence != 0)
			path = path + "/" + occurrence;
		Magnetics retIdss = null;
                try {
                        retIdss = get(idx, path);
                }
                catch(Exception exc) {
                        retIdss = new Magnetics ();
                        System.out.println("Return a new Magnetics empty IDS after catching exception: "+exc);
                }
		close(idx);
		return retIdss;
    }

/*
    public static void fillIdsRef(Magnetics ids, int isRef, String refUser, String refMachine, int refShot, int refRun, int refOccurrence)
    {
        ids.IDS_Properties.isref = isRef;
        if(isRef != 1)
        {
            ids.IDS_Properties.whatref.user = refUser;
                ids.IDS_Properties.whatref.machine = refMachine;
                ids.IDS_Properties.whatref.shot = refShot;
                ids.IDS_Properties.whatref.run = refRun;
                ids.IDS_Properties.whatref.occurrence = refOccurrence;
        }
    } 
*/
            

    public static Magnetics  get(int expIdx, String path)  throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");

      Magnetics ids = new Magnetics (); 
      UALLowLevel.beginIDSGet(expIdx, path, false);
      
          try {
            ids.IDS_Properties.Status_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Status_of");
          } catch(Exception exc){ids.IDS_Properties.Status_of = null;}
		
          try {
            ids.IDS_Properties.Comment_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Comment_of");
          } catch(Exception exc){ids.IDS_Properties.Comment_of = null;}
		
          try {
            ids.IDS_Properties.Creator_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Creator_of");
          } catch(Exception exc){ids.IDS_Properties.Creator_of = null;}
		
          try {
            ids.IDS_Properties.Date_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Date_of");
          } catch(Exception exc){ids.IDS_Properties.Date_of = null;}
		
          try {
            ids.IDS_Properties.Source_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Source_of");
          } catch(Exception exc){ids.IDS_Properties.Source_of = null;}
		
          try {
            ids.IDS_Properties.Reference_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Reference_of");
          } catch(Exception exc){ids.IDS_Properties.Reference_of = null;}
		
          try {
            ids.IDS_Properties.Homogeneous_Timebase = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase");
          } catch(Exception exc){ids.IDS_Properties.Homogeneous_Timebase = EMPTY_INT;}
		
          try {
            ids.IDS_Properties.cocos = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/cocos");
          } catch(Exception exc){ids.IDS_Properties.cocos = EMPTY_INT;}
		
// Get Array of Structures Flux_Loops
          try {
            ids.Flux_Loops = new Flux_LoopsClass[UALLowLevel.getInt(expIdx,path, "Flux_Loops/Shape_of")];
            for (int iFlux_Loops = 0; iFlux_Loops<ids.Flux_Loops.length; iFlux_Loops++){
              ids.Flux_Loops[iFlux_Loops] = new Flux_LoopsClass();

          try {
            ids.Flux_Loops[iFlux_Loops].Name = UALLowLevel.getString(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Name");
          } catch(Exception exc){ids.Flux_Loops[iFlux_Loops].Name = null;}
		
          try {
            ids.Flux_Loops[iFlux_Loops].Identifier = UALLowLevel.getString(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Identifier");
          } catch(Exception exc){ids.Flux_Loops[iFlux_Loops].Identifier = null;}
		
          try {
            ids.Flux_Loops[iFlux_Loops].Position_RZPhi = UALLowLevel.getVect2DDouble(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Position_RZPhi");
          } catch(Exception exc){ids.Flux_Loops[iFlux_Loops].Position_RZPhi = null;}
		
          try {
            ids.Flux_Loops[iFlux_Loops].Flux.Data = UALLowLevel.getVect1DDouble(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Flux" + "/Data");
          } catch(Exception exc){ids.Flux_Loops[iFlux_Loops].Flux.Data = null;}
		
          try {
            ids.Flux_Loops[iFlux_Loops].Flux.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Flux" + "/Timebase");
          } catch(Exception exc){ids.Flux_Loops[iFlux_Loops].Flux.Timebase = null;}
		
            }
          } catch(Exception exc){ids.Flux_Loops = null;}

// Get Array of Structures Bpol_Probes
          try {
            ids.Bpol_Probes = new Bpol_ProbesClass[UALLowLevel.getInt(expIdx,path, "Bpol_Probes/Shape_of")];
            for (int iBpol_Probes = 0; iBpol_Probes<ids.Bpol_Probes.length; iBpol_Probes++){
              ids.Bpol_Probes[iBpol_Probes] = new Bpol_ProbesClass();

          try {
            ids.Bpol_Probes[iBpol_Probes].Name = UALLowLevel.getString(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Name");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Name = null;}
		
          try {
            ids.Bpol_Probes[iBpol_Probes].Identifier = UALLowLevel.getString(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Identifier");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Identifier = null;}
		
          try {
            ids.Bpol_Probes[iBpol_Probes].Centre_Position_RZPhi = UALLowLevel.getVect1DDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Centre_Position_RZPhi");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Centre_Position_RZPhi = null;}
		
          try {
            ids.Bpol_Probes[iBpol_Probes].Poloidal_Angle = UALLowLevel.getDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Poloidal_Angle");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Poloidal_Angle = EMPTY_DOUBLE;}
		
          try {
            ids.Bpol_Probes[iBpol_Probes].Toroidal_Angle = UALLowLevel.getDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Toroidal_Angle");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Toroidal_Angle = EMPTY_DOUBLE;}
		
          try {
            ids.Bpol_Probes[iBpol_Probes].Area = UALLowLevel.getDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Area");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Area = EMPTY_DOUBLE;}
		
          try {
            ids.Bpol_Probes[iBpol_Probes].Length = UALLowLevel.getDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Length");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Length = EMPTY_DOUBLE;}
		
          try {
            ids.Bpol_Probes[iBpol_Probes].Turns = UALLowLevel.getInt(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Turns");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Turns = EMPTY_INT;}
		
          try {
            ids.Bpol_Probes[iBpol_Probes].Field.Data = UALLowLevel.getVect1DDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Field" + "/Data");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Field.Data = null;}
		
          try {
            ids.Bpol_Probes[iBpol_Probes].Field.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Field" + "/Timebase");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Field.Timebase = null;}
		
            }
          } catch(Exception exc){ids.Bpol_Probes = null;}

// Get Array of Structures Processed_Data
          try {
            ids.Processed_Data = new Processed_DataClass[UALLowLevel.getInt(expIdx,path, "Processed_Data/Shape_of")];
            for (int iProcessed_Data = 0; iProcessed_Data<ids.Processed_Data.length; iProcessed_Data++){
              ids.Processed_Data[iProcessed_Data] = new Processed_DataClass();

          try {
            ids.Processed_Data[iProcessed_Data].Method = UALLowLevel.getString(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Method");
          } catch(Exception exc){ids.Processed_Data[iProcessed_Data].Method = null;}
		
          try {
            ids.Processed_Data[iProcessed_Data].Ip.Data = UALLowLevel.getVect1DDouble(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Ip" + "/Data");
          } catch(Exception exc){ids.Processed_Data[iProcessed_Data].Ip.Data = null;}
		
          try {
            ids.Processed_Data[iProcessed_Data].Ip.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Ip" + "/Timebase");
          } catch(Exception exc){ids.Processed_Data[iProcessed_Data].Ip.Timebase = null;}
		
          try {
            ids.Processed_Data[iProcessed_Data].Diamagnetic_Flux.Data = UALLowLevel.getVect1DDouble(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Diamagnetic_Flux" + "/Data");
          } catch(Exception exc){ids.Processed_Data[iProcessed_Data].Diamagnetic_Flux.Data = null;}
		
          try {
            ids.Processed_Data[iProcessed_Data].Diamagnetic_Flux.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Diamagnetic_Flux" + "/Timebase");
          } catch(Exception exc){ids.Processed_Data[iProcessed_Data].Diamagnetic_Flux.Timebase = null;}
		
            }
          } catch(Exception exc){ids.Processed_Data = null;}

          try {
            ids.Code_Parameters.Code_Name = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Name");
          } catch(Exception exc){ids.Code_Parameters.Code_Name = null;}
		
          try {
            ids.Code_Parameters.Code_Version = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Version");
          } catch(Exception exc){ids.Code_Parameters.Code_Version = null;}
		
          try {
            ids.Code_Parameters.Parameters = UALLowLevel.getVect1DString(expIdx, path, "Code_Parameters" + "/Parameters");
          } catch(Exception exc){ids.Code_Parameters.Parameters = null;}
		
          try {
            ids.Code_Parameters.Output_Diagnostics = UALLowLevel.getVect1DString(expIdx, path, "Code_Parameters" + "/Output_Diagnostics");
          } catch(Exception exc){ids.Code_Parameters.Output_Diagnostics = null;}
		
          try {
            ids.Code_Parameters.Output_Flag = UALLowLevel.getVect1DInt(expIdx, path, "Code_Parameters" + "/Output_Flag");
          } catch(Exception exc){ids.Code_Parameters.Output_Flag = null;}
		
          try {
            ids.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Timebase");
          } catch(Exception exc){ids.Timebase = null;}
		
      UALLowLevel.endIDSGet(expIdx, path);
      return ids;
    }

 /**
 * Method getSlice retrieves the  Magnetics IDS in the open database corresponding to the passed time, based on the selectted interpolation mode.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param time The retrieval time.
 * @param interpolMode Defines the selected interpolation. Allowed values are imas.INTERPOLATION, imas.CLOSEST_SAMPLE,
 * imas.PREVIOUS_SAMPLE
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the selected Magnetics ids. Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if double and empty String is string.
 **/
    public static Magnetics  getSlice(int expIdx, String path, double time, int interpolMode) throws UALException
    {
      String        timebasepath;
      String        ual_debug = System.getenv("ual_debug");
      double        retTime;
      Magnetics ids = new Magnetics (); 
      retTime = UALLowLevel.beginIDSGetSlice(expIdx, path, time);

          try {
            ids.IDS_Properties.Status_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Status_of");
          } catch(Exception exc){ids.IDS_Properties.Status_of = null;}
		
          try {
            ids.IDS_Properties.Comment_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Comment_of");
          } catch(Exception exc){ids.IDS_Properties.Comment_of = null;}
		
          try {
            ids.IDS_Properties.Creator_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Creator_of");
          } catch(Exception exc){ids.IDS_Properties.Creator_of = null;}
		
          try {
            ids.IDS_Properties.Date_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Date_of");
          } catch(Exception exc){ids.IDS_Properties.Date_of = null;}
		
          try {
            ids.IDS_Properties.Source_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Source_of");
          } catch(Exception exc){ids.IDS_Properties.Source_of = null;}
		
          try {
            ids.IDS_Properties.Reference_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Reference_of");
          } catch(Exception exc){ids.IDS_Properties.Reference_of = null;}
		
          try {
            ids.IDS_Properties.Homogeneous_Timebase = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase");
          } catch(Exception exc){ids.IDS_Properties.Homogeneous_Timebase = EMPTY_INT;}
		
          try {
            ids.IDS_Properties.cocos = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/cocos");
          } catch(Exception exc){ids.IDS_Properties.cocos = EMPTY_INT;}
		
// Get Array of Structures Flux_Loops
          try {
            ids.Flux_Loops = new Flux_LoopsClass[UALLowLevel.getInt(expIdx,path, "Flux_Loops/Shape_of")];
            for (int iFlux_Loops = 0; iFlux_Loops<ids.Flux_Loops.length; iFlux_Loops++){
              ids.Flux_Loops[iFlux_Loops] = new Flux_LoopsClass();
			
          try {
            ids.Flux_Loops[iFlux_Loops].Name = UALLowLevel.getString(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Name");
          } catch(Exception exc){ids.Flux_Loops[iFlux_Loops].Name = null;}
		
          try {
            ids.Flux_Loops[iFlux_Loops].Identifier = UALLowLevel.getString(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Identifier");
          } catch(Exception exc){ids.Flux_Loops[iFlux_Loops].Identifier = null;}
		
          try {
            ids.Flux_Loops[iFlux_Loops].Position_RZPhi = UALLowLevel.getVect2DDouble(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Position_RZPhi");
          } catch(Exception exc){ids.Flux_Loops[iFlux_Loops].Position_RZPhi = null;}
		
// Get Slice Flux_Loops/Flux/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Flux" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Flux" + "/Data");
	    if (obj.getElementAt(0) > 0) {
              ids.Flux_Loops[iFlux_Loops].Flux.Data = new Vect1DDouble(1);
              ids.Flux_Loops[iFlux_Loops].Flux.Data.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Flux" + "/Data", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Flux_Loops[iFlux_Loops].Flux.Data = null;}
		
// Get Slice Flux_Loops/Flux/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Flux" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Flux" + "/Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Flux_Loops[iFlux_Loops].Flux.Timebase = new Vect1DDouble(1);
              ids.Flux_Loops[iFlux_Loops].Flux.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Flux" + "/Timebase", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Flux_Loops[iFlux_Loops].Flux.Timebase = null;}
		
            }
          } catch(Exception exc){ids.Flux_Loops = null;}
		
// Get Array of Structures Bpol_Probes
          try {
            ids.Bpol_Probes = new Bpol_ProbesClass[UALLowLevel.getInt(expIdx,path, "Bpol_Probes/Shape_of")];
            for (int iBpol_Probes = 0; iBpol_Probes<ids.Bpol_Probes.length; iBpol_Probes++){
              ids.Bpol_Probes[iBpol_Probes] = new Bpol_ProbesClass();
			
          try {
            ids.Bpol_Probes[iBpol_Probes].Name = UALLowLevel.getString(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Name");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Name = null;}
		
          try {
            ids.Bpol_Probes[iBpol_Probes].Identifier = UALLowLevel.getString(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Identifier");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Identifier = null;}
		
          try {
            ids.Bpol_Probes[iBpol_Probes].Centre_Position_RZPhi = UALLowLevel.getVect1DDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Centre_Position_RZPhi");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Centre_Position_RZPhi = null;}
		
          try {
            ids.Bpol_Probes[iBpol_Probes].Poloidal_Angle = UALLowLevel.getDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Poloidal_Angle");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Poloidal_Angle = EMPTY_DOUBLE;}
		
          try {
            ids.Bpol_Probes[iBpol_Probes].Toroidal_Angle = UALLowLevel.getDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Toroidal_Angle");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Toroidal_Angle = EMPTY_DOUBLE;}
		
          try {
            ids.Bpol_Probes[iBpol_Probes].Area = UALLowLevel.getDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Area");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Area = EMPTY_DOUBLE;}
		
          try {
            ids.Bpol_Probes[iBpol_Probes].Length = UALLowLevel.getDouble(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Length");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Length = EMPTY_DOUBLE;}
		
          try {
            ids.Bpol_Probes[iBpol_Probes].Turns = UALLowLevel.getInt(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Turns");
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Turns = EMPTY_INT;}
		
// Get Slice Bpol_Probes/Field/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Field" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Field" + "/Data");
	    if (obj.getElementAt(0) > 0) {
              ids.Bpol_Probes[iBpol_Probes].Field.Data = new Vect1DDouble(1);
              ids.Bpol_Probes[iBpol_Probes].Field.Data.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Field" + "/Data", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Field.Data = null;}
		
// Get Slice Bpol_Probes/Field/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Field" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Field" + "/Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Bpol_Probes[iBpol_Probes].Field.Timebase = new Vect1DDouble(1);
              ids.Bpol_Probes[iBpol_Probes].Field.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Field" + "/Timebase", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Bpol_Probes[iBpol_Probes].Field.Timebase = null;}
		
            }
          } catch(Exception exc){ids.Bpol_Probes = null;}
		
// Get Array of Structures Processed_Data
          try {
            ids.Processed_Data = new Processed_DataClass[UALLowLevel.getInt(expIdx,path, "Processed_Data/Shape_of")];
            for (int iProcessed_Data = 0; iProcessed_Data<ids.Processed_Data.length; iProcessed_Data++){
              ids.Processed_Data[iProcessed_Data] = new Processed_DataClass();
			
          try {
            ids.Processed_Data[iProcessed_Data].Method = UALLowLevel.getString(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Method");
          } catch(Exception exc){ids.Processed_Data[iProcessed_Data].Method = null;}
		
// Get Slice Processed_Data/Ip/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Ip" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Ip" + "/Data");
	    if (obj.getElementAt(0) > 0) {
              ids.Processed_Data[iProcessed_Data].Ip.Data = new Vect1DDouble(1);
              ids.Processed_Data[iProcessed_Data].Ip.Data.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Ip" + "/Data", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Processed_Data[iProcessed_Data].Ip.Data = null;}
		
// Get Slice Processed_Data/Ip/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Ip" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Ip" + "/Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Processed_Data[iProcessed_Data].Ip.Timebase = new Vect1DDouble(1);
              ids.Processed_Data[iProcessed_Data].Ip.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Ip" + "/Timebase", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Processed_Data[iProcessed_Data].Ip.Timebase = null;}
		
// Get Slice Processed_Data/Diamagnetic_Flux/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Diamagnetic_Flux" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Diamagnetic_Flux" + "/Data");
	    if (obj.getElementAt(0) > 0) {
              ids.Processed_Data[iProcessed_Data].Diamagnetic_Flux.Data = new Vect1DDouble(1);
              ids.Processed_Data[iProcessed_Data].Diamagnetic_Flux.Data.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Diamagnetic_Flux" + "/Data", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Processed_Data[iProcessed_Data].Diamagnetic_Flux.Data = null;}
		
// Get Slice Processed_Data/Diamagnetic_Flux/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Diamagnetic_Flux" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Diamagnetic_Flux" + "/Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Processed_Data[iProcessed_Data].Diamagnetic_Flux.Timebase = new Vect1DDouble(1);
              ids.Processed_Data[iProcessed_Data].Diamagnetic_Flux.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Diamagnetic_Flux" + "/Timebase", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Processed_Data[iProcessed_Data].Diamagnetic_Flux.Timebase = null;}
		
            }
          } catch(Exception exc){ids.Processed_Data = null;}
		
          try {
            ids.Code_Parameters.Code_Name = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Name");
          } catch(Exception exc){ids.Code_Parameters.Code_Name = null;}
		
          try {
            ids.Code_Parameters.Code_Version = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Version");
          } catch(Exception exc){ids.Code_Parameters.Code_Version = null;}
		
// Get Slice Code_Parameters/Parameters
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
            ids.Code_Parameters.Parameters = new Vect1DString(1);
            ids.Code_Parameters.Parameters.setElementAt(0, UALLowLevel.getStringSlice(expIdx, path, "Code_Parameters" + "/Parameters", timebasepath.trim(), time, interpolMode));
          } catch(Exception exc){ids.Code_Parameters.Parameters = null;}
		
// Get Slice Code_Parameters/Output_Diagnostics
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
            ids.Code_Parameters.Output_Diagnostics = new Vect1DString(1);
            ids.Code_Parameters.Output_Diagnostics.setElementAt(0, UALLowLevel.getStringSlice(expIdx, path, "Code_Parameters" + "/Output_Diagnostics", timebasepath.trim(), time, interpolMode));
          } catch(Exception exc){ids.Code_Parameters.Output_Diagnostics = null;}
		
// Get Slice Code_Parameters/Output_Flag
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Code_Parameters" + "/Output_Flag");
	    if (obj.getElementAt(0) > 0) {
              ids.Code_Parameters.Output_Flag = new Vect1DInt(1);
              ids.Code_Parameters.Output_Flag.setElementAt(0, UALLowLevel.getIntSlice(expIdx, path, "Code_Parameters" + "/Output_Flag", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Code_Parameters.Output_Flag = null;}
		
// Get Slice Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Timebase = new Vect1DDouble(1);
              ids.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "/Timebase", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Timebase = null;}
		
      UALLowLevel.endIDSGetSlice(expIdx, path);
      return ids;
    }
    
 /**
 * Method delete removes all the data associated with a Magnetics IDS in the open database.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void delete(int expIdx, String path) throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");
      
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Status_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Comment_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Creator_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Date_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Source_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Reference_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Homogeneous_Timebase");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/cocos");          
      for (int iFlux_Loops = 0; iFlux_Loops<200; iFlux_Loops++){
      
      UALLowLevel.deleteData(expIdx,path,"/Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Name");          
      UALLowLevel.deleteData(expIdx,path,"/Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Identifier");          
      UALLowLevel.deleteData(expIdx,path,"/Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Position_RZPhi");          
      UALLowLevel.deleteData(expIdx,path,"/Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Flux" + "/Data");          
      UALLowLevel.deleteData(expIdx,path,"/Flux_Loops/" + Integer.toString(iFlux_Loops+1).trim() + "/Flux" + "/Timebase");          
      }
   
      for (int iBpol_Probes = 0; iBpol_Probes<200; iBpol_Probes++){
      
      UALLowLevel.deleteData(expIdx,path,"/Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Name");          
      UALLowLevel.deleteData(expIdx,path,"/Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Identifier");          
      UALLowLevel.deleteData(expIdx,path,"/Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Centre_Position_RZPhi");          
      UALLowLevel.deleteData(expIdx,path,"/Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Poloidal_Angle");          
      UALLowLevel.deleteData(expIdx,path,"/Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Toroidal_Angle");          
      UALLowLevel.deleteData(expIdx,path,"/Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Area");          
      UALLowLevel.deleteData(expIdx,path,"/Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Length");          
      UALLowLevel.deleteData(expIdx,path,"/Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Turns");          
      UALLowLevel.deleteData(expIdx,path,"/Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Field" + "/Data");          
      UALLowLevel.deleteData(expIdx,path,"/Bpol_Probes/" + Integer.toString(iBpol_Probes+1).trim() + "/Field" + "/Timebase");          
      }
   
      for (int iProcessed_Data = 0; iProcessed_Data<10; iProcessed_Data++){
      
      UALLowLevel.deleteData(expIdx,path,"/Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Method");          
      UALLowLevel.deleteData(expIdx,path,"/Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Ip" + "/Data");          
      UALLowLevel.deleteData(expIdx,path,"/Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Ip" + "/Timebase");          
      UALLowLevel.deleteData(expIdx,path,"/Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Diamagnetic_Flux" + "/Data");          
      UALLowLevel.deleteData(expIdx,path,"/Processed_Data/" + Integer.toString(iProcessed_Data+1).trim() + "/Diamagnetic_Flux" + "/Timebase");          
      }
   
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Code_Name");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Code_Version");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Parameters");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Output_Diagnostics");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Output_Flag");          
      UALLowLevel.deleteData(expIdx, path, "Timebase");  
    }


     
 /**
 * Method dump is used for debugging and prints the content of the object
 **/
  public void dump()
  {
    System.out.println("***** Magnetics *****");
    

  System.out.println("IDS_Properties ");
  

  System.out.println("Status_of ");
  
        if(IDS_Properties.Status_of!= null)
            System.out.println(IDS_Properties.Status_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Comment_of ");
  
        if(IDS_Properties.Comment_of!= null)
            System.out.println(IDS_Properties.Comment_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Creator_of ");
  
        if(IDS_Properties.Creator_of!= null)
            System.out.println(IDS_Properties.Creator_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Date_of ");
  
        if(IDS_Properties.Date_of!= null)
            System.out.println(IDS_Properties.Date_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Source_of ");
  
        if(IDS_Properties.Source_of!= null)
            System.out.println(IDS_Properties.Source_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Reference_of ");
  
        if(IDS_Properties.Reference_of!= null)
            System.out.println(IDS_Properties.Reference_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Homogeneous_Timebase ");
  
        if(IDS_Properties.Homogeneous_Timebase != EMPTY_INT)
            System.out.println(IDS_Properties.Homogeneous_Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("cocos ");
  
        if(IDS_Properties.cocos != EMPTY_INT)
            System.out.println(IDS_Properties.cocos);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Flux_Loops ");
  
      if (Flux_Loops != null) {
        for (int i1 = 0; i1 < Flux_Loops.length; i1++) {
          

  System.out.println("Name ");
  
        if(Flux_Loops[i1].Name!= null)
            System.out.println(Flux_Loops[i1].Name);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Identifier ");
  
        if(Flux_Loops[i1].Identifier!= null)
            System.out.println(Flux_Loops[i1].Identifier);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Position_RZPhi ");
  
        if(Flux_Loops[i1].Position_RZPhi != null)
            System.out.println(Flux_Loops[i1].Position_RZPhi);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Flux ");
  

  System.out.println("Data ");
  
         if(Flux_Loops[i1].Flux.Data != null)
            System.out.println(Flux_Loops[i1].Flux.Data);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Timebase ");
  
         if(Flux_Loops[i1].Flux.Timebase != null)
            System.out.println(Flux_Loops[i1].Flux.Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   
        }
      }
    

  System.out.println("Bpol_Probes ");
  
      if (Bpol_Probes != null) {
        for (int i1 = 0; i1 < Bpol_Probes.length; i1++) {
          

  System.out.println("Name ");
  
        if(Bpol_Probes[i1].Name!= null)
            System.out.println(Bpol_Probes[i1].Name);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Identifier ");
  
        if(Bpol_Probes[i1].Identifier!= null)
            System.out.println(Bpol_Probes[i1].Identifier);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Centre_Position_RZPhi ");
  
         if(Bpol_Probes[i1].Centre_Position_RZPhi != null)
            System.out.println(Bpol_Probes[i1].Centre_Position_RZPhi);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Poloidal_Angle ");
  
         if(Bpol_Probes[i1].Poloidal_Angle != EMPTY_DOUBLE)
            System.out.println(Bpol_Probes[i1].Poloidal_Angle);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Toroidal_Angle ");
  
         if(Bpol_Probes[i1].Toroidal_Angle != EMPTY_DOUBLE)
            System.out.println(Bpol_Probes[i1].Toroidal_Angle);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Area ");
  
         if(Bpol_Probes[i1].Area != EMPTY_DOUBLE)
            System.out.println(Bpol_Probes[i1].Area);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Length ");
  
         if(Bpol_Probes[i1].Length != EMPTY_DOUBLE)
            System.out.println(Bpol_Probes[i1].Length);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Turns ");
  
        if(Bpol_Probes[i1].Turns != EMPTY_INT)
            System.out.println(Bpol_Probes[i1].Turns);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Field ");
  

  System.out.println("Data ");
  
         if(Bpol_Probes[i1].Field.Data != null)
            System.out.println(Bpol_Probes[i1].Field.Data);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Timebase ");
  
         if(Bpol_Probes[i1].Field.Timebase != null)
            System.out.println(Bpol_Probes[i1].Field.Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   
        }
      }
    

  System.out.println("Processed_Data ");
  
      if (Processed_Data != null) {
        for (int i1 = 0; i1 < Processed_Data.length; i1++) {
          

  System.out.println("Method ");
  
        if(Processed_Data[i1].Method!= null)
            System.out.println(Processed_Data[i1].Method);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Ip ");
  

  System.out.println("Data ");
  
         if(Processed_Data[i1].Ip.Data != null)
            System.out.println(Processed_Data[i1].Ip.Data);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Timebase ");
  
         if(Processed_Data[i1].Ip.Timebase != null)
            System.out.println(Processed_Data[i1].Ip.Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Diamagnetic_Flux ");
  

  System.out.println("Data ");
  
         if(Processed_Data[i1].Diamagnetic_Flux.Data != null)
            System.out.println(Processed_Data[i1].Diamagnetic_Flux.Data);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Timebase ");
  
         if(Processed_Data[i1].Diamagnetic_Flux.Timebase != null)
            System.out.println(Processed_Data[i1].Diamagnetic_Flux.Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   
        }
      }
    

  System.out.println("Code_Parameters ");
  

  System.out.println("Code_Name ");
  
        if(Code_Parameters.Code_Name!= null)
            System.out.println(Code_Parameters.Code_Name);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Code_Version ");
  
        if(Code_Parameters.Code_Version!= null)
            System.out.println(Code_Parameters.Code_Version);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Parameters ");
  
        if(Code_Parameters.Parameters != null)
            System.out.println(Code_Parameters.Parameters);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Output_Diagnostics ");
  
        if(Code_Parameters.Output_Diagnostics != null)
            System.out.println(Code_Parameters.Output_Diagnostics);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Output_Flag ");
  
        if(Code_Parameters.Output_Flag != null)
            System.out.println(Code_Parameters.Output_Flag);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Timebase ");
  
         if(Timebase != null)
            System.out.println(Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   
    System.out.println("******************");
  }
}


public static class PF
{
    
      public static class IDS_PropertiesClass {
      
      public String Status_of;
    
      public String Comment_of;
    
      public String Creator_of;
    
      public String Date_of;
    
      public String Source_of;
    
      public String Reference_of;
    
      public int Homogeneous_Timebase = EMPTY_INT;
    
      public int cocos = EMPTY_INT;
    
      }
      public IDS_PropertiesClass IDS_Properties = new IDS_PropertiesClass();
    
      public static class CoilsClass {
      
      public String Name;
    
      public String Identifier;
    
      public double Resistance = EMPTY_DOUBLE;
    
      public double Energy_Limit_Max = EMPTY_DOUBLE;
    
      public static class ElementsClass {
      
      public String Name;
    
      public String Identifier;
    
      public int Turns_With_Sign = EMPTY_INT;
    
      public double Area = EMPTY_DOUBLE;
    
      public static class GeometryClass {
      
      public int Description_Type = EMPTY_INT;
    
      public Vect2DDouble  Coordinates_RZ;
    
      public Vect1DDouble RZDRDZ;
    
      }
      public GeometryClass Geometry = new GeometryClass();
    
      }
      public ElementsClass Elements[];
    
      public static class CurrentClass {
      
      public Vect1DDouble Data;
    
      public Vect1DDouble Timebase;
    
      }
      public CurrentClass Current = new CurrentClass();
    
      public static class VoltageClass {
      
      public Vect1DDouble Data;
    
      public Vect1DDouble Timebase;
    
      }
      public VoltageClass Voltage = new VoltageClass();
    
      }
      public CoilsClass Coils[];
    
      public static class Vertical_ForcesClass {
      
      public Vect1DString Names;
    
      public Vect2DDouble  Combinations;
    
      public Vect1DDouble Limits_Max;
    
      public Vect1DDouble Limits_Min;
    
      public Vect2DDouble  Forces;
    
      public Vect1DDouble Timebase;
    
      }
      public Vertical_ForcesClass Vertical_Forces = new Vertical_ForcesClass();
    
      public static class CircuitsClass {
      
      public String Name;
    
      public String Identifier;
    
      public String Type;
    
      public Vect2DDouble  Connections;
    
      public static class VoltageClass {
      
      public Vect1DDouble Data;
    
      public Vect1DDouble Timebase;
    
      }
      public VoltageClass Voltage = new VoltageClass();
    
      public static class CurrentClass {
      
      public Vect1DDouble Data;
    
      public Vect1DDouble Timebase;
    
      }
      public CurrentClass Current = new CurrentClass();
    
      }
      public CircuitsClass Circuits[];
    
      public static class SuppliesClass {
      
      public String Name;
    
      public String Identifier;
    
      public int Type = EMPTY_INT;
    
      public double Resistance = EMPTY_DOUBLE;
    
      public double Delay = EMPTY_DOUBLE;
    
      public Vect1DDouble Filter_Numerator;
    
      public Vect1DDouble Filter_Denominator;
    
      public double Current_Limit_Max = EMPTY_DOUBLE;
    
      public double Current_Limit_Min = EMPTY_DOUBLE;
    
      public double Voltage_Limit_Max = EMPTY_DOUBLE;
    
      public double Voltage_Limit_Min = EMPTY_DOUBLE;
    
      public double Current_Limiter_Gain = EMPTY_DOUBLE;
    
      public double Energy_Limit_Max = EMPTY_DOUBLE;
    
      public String Nonlinear_Model;
    
      public static class VoltageClass {
      
      public Vect1DDouble Data;
    
      public Vect1DDouble Timebase;
    
      }
      public VoltageClass Voltage = new VoltageClass();
    
      public static class CurrentClass {
      
      public Vect1DDouble Data;
    
      public Vect1DDouble Timebase;
    
      }
      public CurrentClass Current = new CurrentClass();
    
      }
      public SuppliesClass Supplies[];
    
      public static class Passive_LoopsClass {
      
      public Vect1DString Names;
    
      public Vect1DDouble Areas;
    
      public Vect1DDouble Resistances;
    
      public static class GeometriesClass {
      
      public int Description_Type = EMPTY_INT;
    
      public Vect2DDouble  Coordinates_RZ;
    
      public Vect1DDouble RZDRDZ;
    
      }
      public GeometriesClass Geometries[];
    
      public Vect2DDouble  Currents;
    
      public Vect1DDouble Timebase;
    
      }
      public Passive_LoopsClass Passive_Loops = new Passive_LoopsClass();
    
      public static class Vacuum_ModelClass {
      
      public Vect1DString Names;
    
      public Vect2DDouble  Inductance_Matrix;
    
      public Vect1DDouble Resistance_Vector;
    
      }
      public Vacuum_ModelClass Vacuum_Model = new Vacuum_ModelClass();
    
      public static class Code_ParametersClass {
      
      public String Code_Name;
    
      public String Code_Version;
    
      public Vect1DString Parameters;
    
      public Vect1DString Output_Diagnostics;
    
      public Vect1DInt Output_Flag;
    
      }
      public Code_ParametersClass Code_Parameters = new Code_ParametersClass();
    
      public Vect1DDouble Timebase;
    

 /**
 * Method copy PF copies a full IDS between two databases.
 * @param srcIdx The index of the source database, returned by imas.open()
 * @param srcOccur The occurence of the IDS in the source database.
 * @param destIdx The index of the destination database, returned by imas.open()
 * @param destOccur The occurence of the IDS in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copy(int srcIdx, int srcOccur, int destIdx, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyIds(srcIdx, destIdx, "PF", srcOccur, destOccur);
    }

 /**
 * Method copyEnv PF copies a full IDS between two databases.
 * @param srcUser User of the source database
 * @param srcTokamak Tokamak of the source database
 * @param srcVersion Version of the source database
 * @param srcShot Shot number of the source database
 * @param srcRun Run number of the source database
 * @param srcOccur The occurence of the IDS in the source database.
 * @param destUser User of the destination database
 * @param destTokamak Tokamak of the destination database
 * @param destVersion Version of the destination database
 * @param destShot Shot number of the destination database
 * @param destRun Run number of the destination database
 * @param destOccur The occurence of the IDS in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copyEnv(String srcUser, String srcTokamak, String srcVersion, int srcShot, int srcRun, int srcOccur, String destUser, String destTokamak, String destVersion, int destShot, int destRun, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyIdsEnv(srcTokamak, srcVersion, srcUser, srcShot, srcRun, srcOccur, destTokamak, destVersion, destUser, destShot, destRun, destOccur, "PF");
    }

/**
 * Method put stores a non timed PF IDSs in the open database. 
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The passed PF ids.
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void put(int expIdx, String path, PF ids)  throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
	  Vect5DDouble  vect5DDouble;
	  Vect6DDouble  vect6DDouble;
	  Vect7DDouble  vect7DDouble;
          String        timebasepath;
          String        ual_debug = System.getenv("ual_debug");

          UALLowLevel.beginIDSPut(expIdx, path);
          
// Put IDS_Properties/Status_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Status_of", ids.IDS_Properties.Status_of);

// Put IDS_Properties/Comment_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Comment_of", ids.IDS_Properties.Comment_of);

// Put IDS_Properties/Creator_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Creator_of", ids.IDS_Properties.Creator_of);

// Put IDS_Properties/Date_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Date_of", ids.IDS_Properties.Date_of);

// Put IDS_Properties/Source_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Source_of", ids.IDS_Properties.Source_of);

// Put IDS_Properties/Reference_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Reference_of", ids.IDS_Properties.Reference_of);

// Put IDS_Properties/Homogeneous_Timebase
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase", ids.IDS_Properties.Homogeneous_Timebase);

// Put IDS_Properties/cocos
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/cocos", ids.IDS_Properties.cocos);

// Put Coils
          if (ids.Coils != null) {
            UALLowLevel.putInt(expIdx,path, "Coils/Shape_of",
       		ids.Coils.length);
            for (int iCoils = 0; iCoils<ids.Coils.length; iCoils++){
      
// Put Coils/Name
          UALLowLevel.putString(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Name", ids.Coils[iCoils].Name);

// Put Coils/Identifier
          UALLowLevel.putString(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Identifier", ids.Coils[iCoils].Identifier);

// Put Coils/Resistance
          UALLowLevel.putDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Resistance", ids.Coils[iCoils].Resistance);

// Put Coils/Energy_Limit_Max
          UALLowLevel.putDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Energy_Limit_Max", ids.Coils[iCoils].Energy_Limit_Max);

// Put Coils/Elements
          if (ids.Coils[iCoils].Elements != null) {
            UALLowLevel.putInt(expIdx,path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/Shape_of",
       		ids.Coils[iCoils].Elements.length);
             for (int iElements = 0; iElements<ids.Coils[iCoils].Elements.length; iElements++){
      
// Put Coils/Elements/Name
          UALLowLevel.putString(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Name", ids.Coils[iCoils].Elements[iElements].Name);

// Put Coils/Elements/Identifier
          UALLowLevel.putString(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Identifier", ids.Coils[iCoils].Elements[iElements].Identifier);

// Put Coils/Elements/Turns_With_Sign
          UALLowLevel.putInt(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Turns_With_Sign", ids.Coils[iCoils].Elements[iElements].Turns_With_Sign);

// Put Coils/Elements/Area
          UALLowLevel.putDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Area", ids.Coils[iCoils].Elements[iElements].Area);

// Put Coils/Elements/Geometry/Description_Type
          UALLowLevel.putInt(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Geometry" + "/Description_Type", ids.Coils[iCoils].Elements[iElements].Geometry.Description_Type);

// Put Coils/Elements/Geometry/Coordinates_RZ   
          UALLowLevel.putVect2DDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Geometry" + "/Coordinates_RZ", "", ids.Coils[iCoils].Elements[iElements].Geometry.Coordinates_RZ, false);
   		
// Put Coils/Elements/Geometry/RZDRDZ   
          UALLowLevel.putVect1DDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Geometry" + "/RZDRDZ", "", ids.Coils[iCoils].Elements[iElements].Geometry.RZDRDZ, false);
   		
             }
          }

// Put Coils/Current/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Coils/" + Integer.toString(iCoils+1).trim() + "/Current" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Coils[iCoils].Current.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Current" + "/Data", timebasepath.trim(), ids.Coils[iCoils].Current.Data, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Coils/Current/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Coils/" + Integer.toString(iCoils+1).trim() + "/Current" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Coils[iCoils].Current.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Current" + "/Timebase", timebasepath.trim(), ids.Coils[iCoils].Current.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Coils/Voltage/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Coils/" + Integer.toString(iCoils+1).trim() + "/Voltage" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Coils[iCoils].Voltage.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Voltage" + "/Data", timebasepath.trim(), ids.Coils[iCoils].Voltage.Data, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Coils/Voltage/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Coils/" + Integer.toString(iCoils+1).trim() + "/Voltage" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Coils[iCoils].Voltage.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Voltage" + "/Timebase", timebasepath.trim(), ids.Coils[iCoils].Voltage.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
            }
          }

// Put Vertical_Forces/Names   
          UALLowLevel.putVect1DString(expIdx, path, "Vertical_Forces" + "/Names", "", ids.Vertical_Forces.Names, false);
   		
// Put Vertical_Forces/Combinations   
          UALLowLevel.putVect2DDouble(expIdx, path, "Vertical_Forces" + "/Combinations", "", ids.Vertical_Forces.Combinations, false);
   		
// Put Vertical_Forces/Limits_Max   
          UALLowLevel.putVect1DDouble(expIdx, path, "Vertical_Forces" + "/Limits_Max", "", ids.Vertical_Forces.Limits_Max, false);
   		
// Put Vertical_Forces/Limits_Min   
          UALLowLevel.putVect1DDouble(expIdx, path, "Vertical_Forces" + "/Limits_Min", "", ids.Vertical_Forces.Limits_Min, false);
   		
// Put Vertical_Forces/Forces
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Vertical_Forces/Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Vertical_Forces.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Vertical_Forces" + "/Forces", timebasepath.trim(), ids.Vertical_Forces.Forces, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Vertical_Forces/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Vertical_Forces/Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Vertical_Forces.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Vertical_Forces" + "/Timebase", timebasepath.trim(), ids.Vertical_Forces.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Circuits
          if (ids.Circuits != null) {
            UALLowLevel.putInt(expIdx,path, "Circuits/Shape_of",
       		ids.Circuits.length);
            for (int iCircuits = 0; iCircuits<ids.Circuits.length; iCircuits++){
      
// Put Circuits/Name
          UALLowLevel.putString(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Name", ids.Circuits[iCircuits].Name);

// Put Circuits/Identifier
          UALLowLevel.putString(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Identifier", ids.Circuits[iCircuits].Identifier);

// Put Circuits/Type
          UALLowLevel.putString(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Type", ids.Circuits[iCircuits].Type);

// Put Circuits/Connections   
          UALLowLevel.putVect2DDouble(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Connections", "", ids.Circuits[iCircuits].Connections, false);
   		
// Put Circuits/Voltage/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Circuits/" + Integer.toString(iCircuits+1).trim() + "/Voltage" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Circuits[iCircuits].Voltage.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Voltage" + "/Data", timebasepath.trim(), ids.Circuits[iCircuits].Voltage.Data, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Circuits/Voltage/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Circuits/" + Integer.toString(iCircuits+1).trim() + "/Voltage" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Circuits[iCircuits].Voltage.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Voltage" + "/Timebase", timebasepath.trim(), ids.Circuits[iCircuits].Voltage.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Circuits/Current/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Circuits/" + Integer.toString(iCircuits+1).trim() + "/Current" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Circuits[iCircuits].Current.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Current" + "/Data", timebasepath.trim(), ids.Circuits[iCircuits].Current.Data, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Circuits/Current/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Circuits/" + Integer.toString(iCircuits+1).trim() + "/Current" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Circuits[iCircuits].Current.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Current" + "/Timebase", timebasepath.trim(), ids.Circuits[iCircuits].Current.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
            }
          }

// Put Supplies
          if (ids.Supplies != null) {
            UALLowLevel.putInt(expIdx,path, "Supplies/Shape_of",
       		ids.Supplies.length);
            for (int iSupplies = 0; iSupplies<ids.Supplies.length; iSupplies++){
      
// Put Supplies/Name
          UALLowLevel.putString(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Name", ids.Supplies[iSupplies].Name);

// Put Supplies/Identifier
          UALLowLevel.putString(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Identifier", ids.Supplies[iSupplies].Identifier);

// Put Supplies/Type
          UALLowLevel.putInt(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Type", ids.Supplies[iSupplies].Type);

// Put Supplies/Resistance
          UALLowLevel.putDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Resistance", ids.Supplies[iSupplies].Resistance);

// Put Supplies/Delay
          UALLowLevel.putDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Delay", ids.Supplies[iSupplies].Delay);

// Put Supplies/Filter_Numerator   
          UALLowLevel.putVect1DDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Filter_Numerator", "", ids.Supplies[iSupplies].Filter_Numerator, false);
   		
// Put Supplies/Filter_Denominator   
          UALLowLevel.putVect1DDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Filter_Denominator", "", ids.Supplies[iSupplies].Filter_Denominator, false);
   		
// Put Supplies/Current_Limit_Max
          UALLowLevel.putDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current_Limit_Max", ids.Supplies[iSupplies].Current_Limit_Max);

// Put Supplies/Current_Limit_Min
          UALLowLevel.putDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current_Limit_Min", ids.Supplies[iSupplies].Current_Limit_Min);

// Put Supplies/Voltage_Limit_Max
          UALLowLevel.putDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage_Limit_Max", ids.Supplies[iSupplies].Voltage_Limit_Max);

// Put Supplies/Voltage_Limit_Min
          UALLowLevel.putDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage_Limit_Min", ids.Supplies[iSupplies].Voltage_Limit_Min);

// Put Supplies/Current_Limiter_Gain
          UALLowLevel.putDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current_Limiter_Gain", ids.Supplies[iSupplies].Current_Limiter_Gain);

// Put Supplies/Energy_Limit_Max
          UALLowLevel.putDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Energy_Limit_Max", ids.Supplies[iSupplies].Energy_Limit_Max);

// Put Supplies/Nonlinear_Model
          UALLowLevel.putString(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Nonlinear_Model", ids.Supplies[iSupplies].Nonlinear_Model);

// Put Supplies/Voltage/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Supplies[iSupplies].Voltage.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage" + "/Data", timebasepath.trim(), ids.Supplies[iSupplies].Voltage.Data, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Supplies/Voltage/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Supplies[iSupplies].Voltage.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage" + "/Timebase", timebasepath.trim(), ids.Supplies[iSupplies].Voltage.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Supplies/Current/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Supplies[iSupplies].Current.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current" + "/Data", timebasepath.trim(), ids.Supplies[iSupplies].Current.Data, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Supplies/Current/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Supplies[iSupplies].Current.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current" + "/Timebase", timebasepath.trim(), ids.Supplies[iSupplies].Current.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
            }
          }

// Put Passive_Loops/Names   
          UALLowLevel.putVect1DString(expIdx, path, "Passive_Loops" + "/Names", "", ids.Passive_Loops.Names, false);
   		
// Put Passive_Loops/Areas   
          UALLowLevel.putVect1DDouble(expIdx, path, "Passive_Loops" + "/Areas", "", ids.Passive_Loops.Areas, false);
   		
// Put Passive_Loops/Resistances   
          UALLowLevel.putVect1DDouble(expIdx, path, "Passive_Loops" + "/Resistances", "", ids.Passive_Loops.Resistances, false);
   		
// Put Passive_Loops/Geometries
          if (ids.Passive_Loops.Geometries != null) {
            UALLowLevel.putInt(expIdx,path, "Passive_Loops"+"/Geometries/Shape_of",
       		ids.Passive_Loops.Geometries.length);
             for (int iGeometries = 0; iGeometries<ids.Passive_Loops.Geometries.length; iGeometries++){
      
// Put Passive_Loops/Geometries/Description_Type
          UALLowLevel.putInt(expIdx, path, "Passive_Loops"+"/Geometries/" + Integer.toString(iGeometries+1).trim() + "/Description_Type", ids.Passive_Loops.Geometries[iGeometries].Description_Type);

// Put Passive_Loops/Geometries/Coordinates_RZ   
          UALLowLevel.putVect2DDouble(expIdx, path, "Passive_Loops"+"/Geometries/" + Integer.toString(iGeometries+1).trim() + "/Coordinates_RZ", "", ids.Passive_Loops.Geometries[iGeometries].Coordinates_RZ, false);
   		
// Put Passive_Loops/Geometries/RZDRDZ   
          UALLowLevel.putVect1DDouble(expIdx, path, "Passive_Loops"+"/Geometries/" + Integer.toString(iGeometries+1).trim() + "/RZDRDZ", "", ids.Passive_Loops.Geometries[iGeometries].RZDRDZ, false);
   		
             }
          }

// Put Passive_Loops/Currents
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Passive_Loops/Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Passive_Loops.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect2DDouble(expIdx, path, "Passive_Loops" + "/Currents", timebasepath.trim(), ids.Passive_Loops.Currents, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Passive_Loops/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Passive_Loops/Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Passive_Loops.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Passive_Loops" + "/Timebase", timebasepath.trim(), ids.Passive_Loops.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Vacuum_Model/Names   
          UALLowLevel.putVect1DString(expIdx, path, "Vacuum_Model" + "/Names", "", ids.Vacuum_Model.Names, false);
   		
// Put Vacuum_Model/Inductance_Matrix   
          UALLowLevel.putVect2DDouble(expIdx, path, "Vacuum_Model" + "/Inductance_Matrix", "", ids.Vacuum_Model.Inductance_Matrix, false);
   		
// Put Vacuum_Model/Resistance_Vector   
          UALLowLevel.putVect1DDouble(expIdx, path, "Vacuum_Model" + "/Resistance_Vector", "", ids.Vacuum_Model.Resistance_Vector, false);
   		
// Put Code_Parameters/Code_Name
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Name", ids.Code_Parameters.Code_Name);

// Put Code_Parameters/Code_Version
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Version", ids.Code_Parameters.Code_Version);

// Put Code_Parameters/Parameters
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DString(expIdx, path, "Code_Parameters" + "/Parameters", timebasepath.trim(), ids.Code_Parameters.Parameters, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Code_Parameters/Output_Diagnostics
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DString(expIdx, path, "Code_Parameters" + "/Output_Diagnostics", timebasepath.trim(), ids.Code_Parameters.Output_Diagnostics, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Code_Parameters/Output_Flag
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
          UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DInt(expIdx, path, "Code_Parameters" + "/Output_Flag", timebasepath.trim(), ids.Code_Parameters.Output_Flag, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect1DDouble(expIdx,path, "Timebase", timebasepath.trim(), ids.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
          UALLowLevel.endIDSPut(expIdx, path);
    }

 /**
 * Method putNonTimed stores the non time dependent fields of a (timed) PF IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The timed PF IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void putNonTimed(int expIdx, String path, PF ids) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
          Vect5DDouble  vect5DDouble;
          Vect6DDouble  vect6DDouble;
          Vect7DDouble  vect7DDouble;
          String        ual_debug = System.getenv("ual_debug");
          
          delete(expIdx, path);
          UALLowLevel.beginIDSPutNonTimed(expIdx, path);
          
// Put IDS_Properties/Status_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Status_of", ids.IDS_Properties.Status_of);

// Put IDS_Properties/Comment_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Comment_of", ids.IDS_Properties.Comment_of);

// Put IDS_Properties/Creator_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Creator_of", ids.IDS_Properties.Creator_of);

// Put IDS_Properties/Date_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Date_of", ids.IDS_Properties.Date_of);

// Put IDS_Properties/Source_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Source_of", ids.IDS_Properties.Source_of);

// Put IDS_Properties/Reference_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Reference_of", ids.IDS_Properties.Reference_of);

// Put IDS_Properties/Homogeneous_Timebase
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase", ids.IDS_Properties.Homogeneous_Timebase);

// Put IDS_Properties/cocos
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/cocos", ids.IDS_Properties.cocos);

// Put Coils
          if (ids.Coils != null) {
            UALLowLevel.putInt(expIdx,path, "Coils/Shape_of",
       		ids.Coils.length);
            for (int iCoils = 0; iCoils<ids.Coils.length; iCoils++){
      
// Put Coils/Name
          UALLowLevel.putString(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Name", ids.Coils[iCoils].Name);

// Put Coils/Identifier
          UALLowLevel.putString(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Identifier", ids.Coils[iCoils].Identifier);

// Put Coils/Resistance
          UALLowLevel.putDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Resistance", ids.Coils[iCoils].Resistance);

// Put Coils/Energy_Limit_Max
          UALLowLevel.putDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Energy_Limit_Max", ids.Coils[iCoils].Energy_Limit_Max);

// Put Coils/Elements
          if (ids.Coils[iCoils].Elements != null) {
            UALLowLevel.putInt(expIdx,path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/Shape_of",
       		ids.Coils[iCoils].Elements.length);
             for (int iElements = 0; iElements<ids.Coils[iCoils].Elements.length; iElements++){
      
// Put Coils/Elements/Name
          UALLowLevel.putString(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Name", ids.Coils[iCoils].Elements[iElements].Name);

// Put Coils/Elements/Identifier
          UALLowLevel.putString(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Identifier", ids.Coils[iCoils].Elements[iElements].Identifier);

// Put Coils/Elements/Turns_With_Sign
          UALLowLevel.putInt(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Turns_With_Sign", ids.Coils[iCoils].Elements[iElements].Turns_With_Sign);

// Put Coils/Elements/Area
          UALLowLevel.putDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Area", ids.Coils[iCoils].Elements[iElements].Area);

// Put Coils/Elements/Geometry/Description_Type
          UALLowLevel.putInt(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Geometry" + "/Description_Type", ids.Coils[iCoils].Elements[iElements].Geometry.Description_Type);

// Put Coils/Elements/Geometry/Coordinates_RZ   
          UALLowLevel.putVect2DDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Geometry" + "/Coordinates_RZ", "", ids.Coils[iCoils].Elements[iElements].Geometry.Coordinates_RZ, false);
   		
// Put Coils/Elements/Geometry/RZDRDZ   
          UALLowLevel.putVect1DDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Geometry" + "/RZDRDZ", "", ids.Coils[iCoils].Elements[iElements].Geometry.RZDRDZ, false);
   		
             }
          }

            }
          }

// Put Vertical_Forces/Names   
          UALLowLevel.putVect1DString(expIdx, path, "Vertical_Forces" + "/Names", "", ids.Vertical_Forces.Names, false);
   		
// Put Vertical_Forces/Combinations   
          UALLowLevel.putVect2DDouble(expIdx, path, "Vertical_Forces" + "/Combinations", "", ids.Vertical_Forces.Combinations, false);
   		
// Put Vertical_Forces/Limits_Max   
          UALLowLevel.putVect1DDouble(expIdx, path, "Vertical_Forces" + "/Limits_Max", "", ids.Vertical_Forces.Limits_Max, false);
   		
// Put Vertical_Forces/Limits_Min   
          UALLowLevel.putVect1DDouble(expIdx, path, "Vertical_Forces" + "/Limits_Min", "", ids.Vertical_Forces.Limits_Min, false);
   		
// Put Circuits
          if (ids.Circuits != null) {
            UALLowLevel.putInt(expIdx,path, "Circuits/Shape_of",
       		ids.Circuits.length);
            for (int iCircuits = 0; iCircuits<ids.Circuits.length; iCircuits++){
      
// Put Circuits/Name
          UALLowLevel.putString(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Name", ids.Circuits[iCircuits].Name);

// Put Circuits/Identifier
          UALLowLevel.putString(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Identifier", ids.Circuits[iCircuits].Identifier);

// Put Circuits/Type
          UALLowLevel.putString(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Type", ids.Circuits[iCircuits].Type);

// Put Circuits/Connections   
          UALLowLevel.putVect2DDouble(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Connections", "", ids.Circuits[iCircuits].Connections, false);
   		
            }
          }

// Put Supplies
          if (ids.Supplies != null) {
            UALLowLevel.putInt(expIdx,path, "Supplies/Shape_of",
       		ids.Supplies.length);
            for (int iSupplies = 0; iSupplies<ids.Supplies.length; iSupplies++){
      
// Put Supplies/Name
          UALLowLevel.putString(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Name", ids.Supplies[iSupplies].Name);

// Put Supplies/Identifier
          UALLowLevel.putString(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Identifier", ids.Supplies[iSupplies].Identifier);

// Put Supplies/Type
          UALLowLevel.putInt(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Type", ids.Supplies[iSupplies].Type);

// Put Supplies/Resistance
          UALLowLevel.putDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Resistance", ids.Supplies[iSupplies].Resistance);

// Put Supplies/Delay
          UALLowLevel.putDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Delay", ids.Supplies[iSupplies].Delay);

// Put Supplies/Filter_Numerator   
          UALLowLevel.putVect1DDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Filter_Numerator", "", ids.Supplies[iSupplies].Filter_Numerator, false);
   		
// Put Supplies/Filter_Denominator   
          UALLowLevel.putVect1DDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Filter_Denominator", "", ids.Supplies[iSupplies].Filter_Denominator, false);
   		
// Put Supplies/Current_Limit_Max
          UALLowLevel.putDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current_Limit_Max", ids.Supplies[iSupplies].Current_Limit_Max);

// Put Supplies/Current_Limit_Min
          UALLowLevel.putDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current_Limit_Min", ids.Supplies[iSupplies].Current_Limit_Min);

// Put Supplies/Voltage_Limit_Max
          UALLowLevel.putDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage_Limit_Max", ids.Supplies[iSupplies].Voltage_Limit_Max);

// Put Supplies/Voltage_Limit_Min
          UALLowLevel.putDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage_Limit_Min", ids.Supplies[iSupplies].Voltage_Limit_Min);

// Put Supplies/Current_Limiter_Gain
          UALLowLevel.putDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current_Limiter_Gain", ids.Supplies[iSupplies].Current_Limiter_Gain);

// Put Supplies/Energy_Limit_Max
          UALLowLevel.putDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Energy_Limit_Max", ids.Supplies[iSupplies].Energy_Limit_Max);

// Put Supplies/Nonlinear_Model
          UALLowLevel.putString(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Nonlinear_Model", ids.Supplies[iSupplies].Nonlinear_Model);

            }
          }

// Put Passive_Loops/Names   
          UALLowLevel.putVect1DString(expIdx, path, "Passive_Loops" + "/Names", "", ids.Passive_Loops.Names, false);
   		
// Put Passive_Loops/Areas   
          UALLowLevel.putVect1DDouble(expIdx, path, "Passive_Loops" + "/Areas", "", ids.Passive_Loops.Areas, false);
   		
// Put Passive_Loops/Resistances   
          UALLowLevel.putVect1DDouble(expIdx, path, "Passive_Loops" + "/Resistances", "", ids.Passive_Loops.Resistances, false);
   		
// Put Passive_Loops/Geometries
          if (ids.Passive_Loops.Geometries != null) {
            UALLowLevel.putInt(expIdx,path, "Passive_Loops"+"/Geometries/Shape_of",
       		ids.Passive_Loops.Geometries.length);
             for (int iGeometries = 0; iGeometries<ids.Passive_Loops.Geometries.length; iGeometries++){
      
// Put Passive_Loops/Geometries/Description_Type
          UALLowLevel.putInt(expIdx, path, "Passive_Loops"+"/Geometries/" + Integer.toString(iGeometries+1).trim() + "/Description_Type", ids.Passive_Loops.Geometries[iGeometries].Description_Type);

// Put Passive_Loops/Geometries/Coordinates_RZ   
          UALLowLevel.putVect2DDouble(expIdx, path, "Passive_Loops"+"/Geometries/" + Integer.toString(iGeometries+1).trim() + "/Coordinates_RZ", "", ids.Passive_Loops.Geometries[iGeometries].Coordinates_RZ, false);
   		
// Put Passive_Loops/Geometries/RZDRDZ   
          UALLowLevel.putVect1DDouble(expIdx, path, "Passive_Loops"+"/Geometries/" + Integer.toString(iGeometries+1).trim() + "/RZDRDZ", "", ids.Passive_Loops.Geometries[iGeometries].RZDRDZ, false);
   		
             }
          }

// Put Vacuum_Model/Names   
          UALLowLevel.putVect1DString(expIdx, path, "Vacuum_Model" + "/Names", "", ids.Vacuum_Model.Names, false);
   		
// Put Vacuum_Model/Inductance_Matrix   
          UALLowLevel.putVect2DDouble(expIdx, path, "Vacuum_Model" + "/Inductance_Matrix", "", ids.Vacuum_Model.Inductance_Matrix, false);
   		
// Put Vacuum_Model/Resistance_Vector   
          UALLowLevel.putVect1DDouble(expIdx, path, "Vacuum_Model" + "/Resistance_Vector", "", ids.Vacuum_Model.Resistance_Vector, false);
   		
// Put Code_Parameters/Code_Name
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Name", ids.Code_Parameters.Code_Name);

// Put Code_Parameters/Code_Version
          UALLowLevel.putString(expIdx, path, "Code_Parameters" + "/Code_Version", ids.Code_Parameters.Code_Version);

          UALLowLevel.endIDSPutNonTimed(expIdx, path);
    }

    
/**
 * Method putSlice appends a (timed) PF IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param ids The timed PF IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void putSlice(int expIdx, String path, PF ids) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
          Vect5DDouble  vect5DDouble;
          Vect6DDouble  vect6DDouble;
          Vect7DDouble  vect7DDouble;
          String        timebasepath;
          String        ual_debug = System.getenv("ual_debug");
          
    	  if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
            System.out.println("ERROR : the PUT_SLICE routine works only for homogeneous timebase IDS");
            return;
          }
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutSlice(expIdx, path);
          
// Put Coils
          if (ids.Coils != null) {
            UALLowLevel.putInt(expIdx,path, "Coils/Shape_of",
       		ids.Coils.length);
            for (int iCoils = 0; iCoils<ids.Coils.length; iCoils++){
      
// Put Coils/Elements
          if (ids.Coils[iCoils].Elements != null) {
            UALLowLevel.putInt(expIdx,path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/Shape_of",
       		ids.Coils[iCoils].Elements.length);
             for (int iElements = 0; iElements<ids.Coils[iCoils].Elements.length; iElements++){
      
             }
          }

// Put Slice Coils/Current/Data
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Current" + "/Data", timebasepath.trim(), ids.Coils[iCoils].Current.Data, ids.Timebase.getElementAt(0));
		
// Put Slice Coils/Current/Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Current" + "/Timebase", timebasepath.trim(), ids.Coils[iCoils].Current.Timebase, ids.Timebase.getElementAt(0));
		
// Put Slice Coils/Voltage/Data
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Voltage" + "/Data", timebasepath.trim(), ids.Coils[iCoils].Voltage.Data, ids.Timebase.getElementAt(0));
		
// Put Slice Coils/Voltage/Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Voltage" + "/Timebase", timebasepath.trim(), ids.Coils[iCoils].Voltage.Timebase, ids.Timebase.getElementAt(0));
		
            }
          }

// Put Slice Vertical_Forces/Forces
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Vertical_Forces" + "/Forces", timebasepath.trim(), ids.Vertical_Forces.Forces, ids.Timebase.getElementAt(0));
		
// Put Slice Vertical_Forces/Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Vertical_Forces" + "/Timebase", timebasepath.trim(), ids.Vertical_Forces.Timebase, ids.Timebase.getElementAt(0));
		
// Put Circuits
          if (ids.Circuits != null) {
            UALLowLevel.putInt(expIdx,path, "Circuits/Shape_of",
       		ids.Circuits.length);
            for (int iCircuits = 0; iCircuits<ids.Circuits.length; iCircuits++){
      
// Put Slice Circuits/Voltage/Data
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Voltage" + "/Data", timebasepath.trim(), ids.Circuits[iCircuits].Voltage.Data, ids.Timebase.getElementAt(0));
		
// Put Slice Circuits/Voltage/Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Voltage" + "/Timebase", timebasepath.trim(), ids.Circuits[iCircuits].Voltage.Timebase, ids.Timebase.getElementAt(0));
		
// Put Slice Circuits/Current/Data
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Current" + "/Data", timebasepath.trim(), ids.Circuits[iCircuits].Current.Data, ids.Timebase.getElementAt(0));
		
// Put Slice Circuits/Current/Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Current" + "/Timebase", timebasepath.trim(), ids.Circuits[iCircuits].Current.Timebase, ids.Timebase.getElementAt(0));
		
            }
          }

// Put Supplies
          if (ids.Supplies != null) {
            UALLowLevel.putInt(expIdx,path, "Supplies/Shape_of",
       		ids.Supplies.length);
            for (int iSupplies = 0; iSupplies<ids.Supplies.length; iSupplies++){
      
// Put Slice Supplies/Voltage/Data
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage" + "/Data", timebasepath.trim(), ids.Supplies[iSupplies].Voltage.Data, ids.Timebase.getElementAt(0));
		
// Put Slice Supplies/Voltage/Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage" + "/Timebase", timebasepath.trim(), ids.Supplies[iSupplies].Voltage.Timebase, ids.Timebase.getElementAt(0));
		
// Put Slice Supplies/Current/Data
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current" + "/Data", timebasepath.trim(), ids.Supplies[iSupplies].Current.Data, ids.Timebase.getElementAt(0));
		
// Put Slice Supplies/Current/Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current" + "/Timebase", timebasepath.trim(), ids.Supplies[iSupplies].Current.Timebase, ids.Timebase.getElementAt(0));
		
            }
          }

// Put Passive_Loops/Geometries
          if (ids.Passive_Loops.Geometries != null) {
            UALLowLevel.putInt(expIdx,path, "Passive_Loops"+"/Geometries/Shape_of",
       		ids.Passive_Loops.Geometries.length);
             for (int iGeometries = 0; iGeometries<ids.Passive_Loops.Geometries.length; iGeometries++){
      
             }
          }

// Put Slice Passive_Loops/Currents
          UALLowLevel.putVect2DDoubleSlice(expIdx, path, "Passive_Loops" + "/Currents", timebasepath.trim(), ids.Passive_Loops.Currents, ids.Timebase.getElementAt(0));
		
// Put Slice Passive_Loops/Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Passive_Loops" + "/Timebase", timebasepath.trim(), ids.Passive_Loops.Timebase, ids.Timebase.getElementAt(0));
		
// Put Slice Code_Parameters/Parameters
          UALLowLevel.putVect1DStringSlice(expIdx, path, "Code_Parameters" + "/Parameters", timebasepath.trim(), ids.Code_Parameters.Parameters, ids.Timebase.getElementAt(0));
		
// Put Slice Code_Parameters/Output_Diagnostics
          UALLowLevel.putVect1DStringSlice(expIdx, path, "Code_Parameters" + "/Output_Diagnostics", timebasepath.trim(), ids.Code_Parameters.Output_Diagnostics, ids.Timebase.getElementAt(0));
		
// Put Slice Code_Parameters/Output_Flag
          UALLowLevel.putVect1DIntSlice(expIdx, path, "Code_Parameters" + "/Output_Flag", timebasepath.trim(), ids.Code_Parameters.Output_Flag, ids.Timebase.getElementAt(0));
		
// Put Slice Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx,path, "Timebase", timebasepath.trim(), ids.Timebase, ids.Timebase.getElementAt(0));
		
          UALLowLevel.endIDSPutSlice(expIdx, path);
    }
    

    

/**
 * Method putdb stores a non timed PF IDSs in the open database only if
 *  datainto.isref == 1. In any case it calls afterwards UALLowLevel.putIdsDb() in order to update catalogue
 * @param expIdx The index to the database, returned by imas.open()
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected ids. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @param ids The  PF IDSs
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
/*
    public static void putdb(int expIdx, String user, String machine, int shot, int run, 
    	String path, int occurrence, PF ids) throws UALException
    {
    	String actPath;
        if(occurrence > 0)
	    actPath = path+"/"+occurrence;
	else
	    actPath = path;
	if(ids.IDS_Properties.isref == 1)
	{
	    put(expIdx, actPath, ids);
	    UALLowLevel.putIdsDb(user, machine, shot, run, path, occurrence, 1,
	    	user, machine, shot, run, occurrence);
	}
	else //isref == 2
	{
	    UALLowLevel.putIdsDb(user, machine, shot, run, path, occurrence, 2,
	    	ids.IDS_Properties.whatref.user,  ids.IDS_Properties.whatref.machine,
		ids.IDS_Properties.whatref.shot, ids.IDS_Properties.whatref.run, 
		ids.IDS_Properties.whatref.occurrence);
	
	}
    }
*/

/**
 * Method getdb retrieves the non timed PF IDSs in the open database, taking the correct reference.
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the PF IDS . Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if float or double and empty String is string.
 **/
    public static PF getdb(String user, String machine, int shot, int run, String version, String path, int occurrence) throws UALException
    {
//		int idx = UALLowLevel.openDb(user, machine, shot, run, path, occurrence);
		int idx = openEnv("ids", shot, run, user, machine, version);
		if (occurrence != 0)
			path = path + "/" + occurrence;
		PF retIdss = null;
                try {
                        retIdss = get(idx, path);
                }
                catch(Exception exc) {
                        retIdss = new PF ();
                        System.out.println("Return a new PF empty IDS after catching exception: "+exc);
                }
		close(idx);
		return retIdss;
    }

/*
    public static void fillIdsRef(PF ids, int isRef, String refUser, String refMachine, int refShot, int refRun, int refOccurrence)
    {
        ids.IDS_Properties.isref = isRef;
        if(isRef != 1)
        {
            ids.IDS_Properties.whatref.user = refUser;
                ids.IDS_Properties.whatref.machine = refMachine;
                ids.IDS_Properties.whatref.shot = refShot;
                ids.IDS_Properties.whatref.run = refRun;
                ids.IDS_Properties.whatref.occurrence = refOccurrence;
        }
    } 
*/
            

    public static PF  get(int expIdx, String path)  throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");

      PF ids = new PF (); 
      UALLowLevel.beginIDSGet(expIdx, path, false);
      
          try {
            ids.IDS_Properties.Status_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Status_of");
          } catch(Exception exc){ids.IDS_Properties.Status_of = null;}
		
          try {
            ids.IDS_Properties.Comment_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Comment_of");
          } catch(Exception exc){ids.IDS_Properties.Comment_of = null;}
		
          try {
            ids.IDS_Properties.Creator_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Creator_of");
          } catch(Exception exc){ids.IDS_Properties.Creator_of = null;}
		
          try {
            ids.IDS_Properties.Date_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Date_of");
          } catch(Exception exc){ids.IDS_Properties.Date_of = null;}
		
          try {
            ids.IDS_Properties.Source_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Source_of");
          } catch(Exception exc){ids.IDS_Properties.Source_of = null;}
		
          try {
            ids.IDS_Properties.Reference_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Reference_of");
          } catch(Exception exc){ids.IDS_Properties.Reference_of = null;}
		
          try {
            ids.IDS_Properties.Homogeneous_Timebase = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase");
          } catch(Exception exc){ids.IDS_Properties.Homogeneous_Timebase = EMPTY_INT;}
		
          try {
            ids.IDS_Properties.cocos = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/cocos");
          } catch(Exception exc){ids.IDS_Properties.cocos = EMPTY_INT;}
		
// Get Array of Structures Coils
          try {
            ids.Coils = new CoilsClass[UALLowLevel.getInt(expIdx,path, "Coils/Shape_of")];
            for (int iCoils = 0; iCoils<ids.Coils.length; iCoils++){
              ids.Coils[iCoils] = new CoilsClass();

          try {
            ids.Coils[iCoils].Name = UALLowLevel.getString(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Name");
          } catch(Exception exc){ids.Coils[iCoils].Name = null;}
		
          try {
            ids.Coils[iCoils].Identifier = UALLowLevel.getString(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Identifier");
          } catch(Exception exc){ids.Coils[iCoils].Identifier = null;}
		
          try {
            ids.Coils[iCoils].Resistance = UALLowLevel.getDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Resistance");
          } catch(Exception exc){ids.Coils[iCoils].Resistance = EMPTY_DOUBLE;}
		
          try {
            ids.Coils[iCoils].Energy_Limit_Max = UALLowLevel.getDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Energy_Limit_Max");
          } catch(Exception exc){ids.Coils[iCoils].Energy_Limit_Max = EMPTY_DOUBLE;}
		
// Get Array of Structures Coils/Elements
          try {
            ids.Coils[iCoils].Elements = new CoilsClass.ElementsClass[UALLowLevel.getInt(expIdx,path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/Shape_of")];
            for (int iElements = 0; iElements<ids.Coils[iCoils].Elements.length; iElements++){
              ids.Coils[iCoils].Elements[iElements] = new CoilsClass.ElementsClass();

          try {
            ids.Coils[iCoils].Elements[iElements].Name = UALLowLevel.getString(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Name");
          } catch(Exception exc){ids.Coils[iCoils].Elements[iElements].Name = null;}
		
          try {
            ids.Coils[iCoils].Elements[iElements].Identifier = UALLowLevel.getString(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Identifier");
          } catch(Exception exc){ids.Coils[iCoils].Elements[iElements].Identifier = null;}
		
          try {
            ids.Coils[iCoils].Elements[iElements].Turns_With_Sign = UALLowLevel.getInt(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Turns_With_Sign");
          } catch(Exception exc){ids.Coils[iCoils].Elements[iElements].Turns_With_Sign = EMPTY_INT;}
		
          try {
            ids.Coils[iCoils].Elements[iElements].Area = UALLowLevel.getDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Area");
          } catch(Exception exc){ids.Coils[iCoils].Elements[iElements].Area = EMPTY_DOUBLE;}
		
          try {
            ids.Coils[iCoils].Elements[iElements].Geometry.Description_Type = UALLowLevel.getInt(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Geometry" + "/Description_Type");
          } catch(Exception exc){ids.Coils[iCoils].Elements[iElements].Geometry.Description_Type = EMPTY_INT;}
		
          try {
            ids.Coils[iCoils].Elements[iElements].Geometry.Coordinates_RZ = UALLowLevel.getVect2DDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Geometry" + "/Coordinates_RZ");
          } catch(Exception exc){ids.Coils[iCoils].Elements[iElements].Geometry.Coordinates_RZ = null;}
		
          try {
            ids.Coils[iCoils].Elements[iElements].Geometry.RZDRDZ = UALLowLevel.getVect1DDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Geometry" + "/RZDRDZ");
          } catch(Exception exc){ids.Coils[iCoils].Elements[iElements].Geometry.RZDRDZ = null;}
		
            }
          } catch(Exception exc){ids.Coils[iCoils].Elements = null;}

          try {
            ids.Coils[iCoils].Current.Data = UALLowLevel.getVect1DDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Current" + "/Data");
          } catch(Exception exc){ids.Coils[iCoils].Current.Data = null;}
		
          try {
            ids.Coils[iCoils].Current.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Current" + "/Timebase");
          } catch(Exception exc){ids.Coils[iCoils].Current.Timebase = null;}
		
          try {
            ids.Coils[iCoils].Voltage.Data = UALLowLevel.getVect1DDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Voltage" + "/Data");
          } catch(Exception exc){ids.Coils[iCoils].Voltage.Data = null;}
		
          try {
            ids.Coils[iCoils].Voltage.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Voltage" + "/Timebase");
          } catch(Exception exc){ids.Coils[iCoils].Voltage.Timebase = null;}
		
            }
          } catch(Exception exc){ids.Coils = null;}

          try {
            ids.Vertical_Forces.Names = UALLowLevel.getVect1DString(expIdx, path, "Vertical_Forces" + "/Names");
          } catch(Exception exc){ids.Vertical_Forces.Names = null;}
		
          try {
            ids.Vertical_Forces.Combinations = UALLowLevel.getVect2DDouble(expIdx, path, "Vertical_Forces" + "/Combinations");
          } catch(Exception exc){ids.Vertical_Forces.Combinations = null;}
		
          try {
            ids.Vertical_Forces.Limits_Max = UALLowLevel.getVect1DDouble(expIdx, path, "Vertical_Forces" + "/Limits_Max");
          } catch(Exception exc){ids.Vertical_Forces.Limits_Max = null;}
		
          try {
            ids.Vertical_Forces.Limits_Min = UALLowLevel.getVect1DDouble(expIdx, path, "Vertical_Forces" + "/Limits_Min");
          } catch(Exception exc){ids.Vertical_Forces.Limits_Min = null;}
		
          try {
            ids.Vertical_Forces.Forces = UALLowLevel.getVect2DDouble(expIdx, path, "Vertical_Forces" + "/Forces");
          } catch(Exception exc){ids.Vertical_Forces.Forces = null;}
		
          try {
            ids.Vertical_Forces.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Vertical_Forces" + "/Timebase");
          } catch(Exception exc){ids.Vertical_Forces.Timebase = null;}
		
// Get Array of Structures Circuits
          try {
            ids.Circuits = new CircuitsClass[UALLowLevel.getInt(expIdx,path, "Circuits/Shape_of")];
            for (int iCircuits = 0; iCircuits<ids.Circuits.length; iCircuits++){
              ids.Circuits[iCircuits] = new CircuitsClass();

          try {
            ids.Circuits[iCircuits].Name = UALLowLevel.getString(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Name");
          } catch(Exception exc){ids.Circuits[iCircuits].Name = null;}
		
          try {
            ids.Circuits[iCircuits].Identifier = UALLowLevel.getString(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Identifier");
          } catch(Exception exc){ids.Circuits[iCircuits].Identifier = null;}
		
          try {
            ids.Circuits[iCircuits].Type = UALLowLevel.getString(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Type");
          } catch(Exception exc){ids.Circuits[iCircuits].Type = null;}
		
          try {
            ids.Circuits[iCircuits].Connections = UALLowLevel.getVect2DDouble(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Connections");
          } catch(Exception exc){ids.Circuits[iCircuits].Connections = null;}
		
          try {
            ids.Circuits[iCircuits].Voltage.Data = UALLowLevel.getVect1DDouble(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Voltage" + "/Data");
          } catch(Exception exc){ids.Circuits[iCircuits].Voltage.Data = null;}
		
          try {
            ids.Circuits[iCircuits].Voltage.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Voltage" + "/Timebase");
          } catch(Exception exc){ids.Circuits[iCircuits].Voltage.Timebase = null;}
		
          try {
            ids.Circuits[iCircuits].Current.Data = UALLowLevel.getVect1DDouble(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Current" + "/Data");
          } catch(Exception exc){ids.Circuits[iCircuits].Current.Data = null;}
		
          try {
            ids.Circuits[iCircuits].Current.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Current" + "/Timebase");
          } catch(Exception exc){ids.Circuits[iCircuits].Current.Timebase = null;}
		
            }
          } catch(Exception exc){ids.Circuits = null;}

// Get Array of Structures Supplies
          try {
            ids.Supplies = new SuppliesClass[UALLowLevel.getInt(expIdx,path, "Supplies/Shape_of")];
            for (int iSupplies = 0; iSupplies<ids.Supplies.length; iSupplies++){
              ids.Supplies[iSupplies] = new SuppliesClass();

          try {
            ids.Supplies[iSupplies].Name = UALLowLevel.getString(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Name");
          } catch(Exception exc){ids.Supplies[iSupplies].Name = null;}
		
          try {
            ids.Supplies[iSupplies].Identifier = UALLowLevel.getString(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Identifier");
          } catch(Exception exc){ids.Supplies[iSupplies].Identifier = null;}
		
          try {
            ids.Supplies[iSupplies].Type = UALLowLevel.getInt(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Type");
          } catch(Exception exc){ids.Supplies[iSupplies].Type = EMPTY_INT;}
		
          try {
            ids.Supplies[iSupplies].Resistance = UALLowLevel.getDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Resistance");
          } catch(Exception exc){ids.Supplies[iSupplies].Resistance = EMPTY_DOUBLE;}
		
          try {
            ids.Supplies[iSupplies].Delay = UALLowLevel.getDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Delay");
          } catch(Exception exc){ids.Supplies[iSupplies].Delay = EMPTY_DOUBLE;}
		
          try {
            ids.Supplies[iSupplies].Filter_Numerator = UALLowLevel.getVect1DDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Filter_Numerator");
          } catch(Exception exc){ids.Supplies[iSupplies].Filter_Numerator = null;}
		
          try {
            ids.Supplies[iSupplies].Filter_Denominator = UALLowLevel.getVect1DDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Filter_Denominator");
          } catch(Exception exc){ids.Supplies[iSupplies].Filter_Denominator = null;}
		
          try {
            ids.Supplies[iSupplies].Current_Limit_Max = UALLowLevel.getDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current_Limit_Max");
          } catch(Exception exc){ids.Supplies[iSupplies].Current_Limit_Max = EMPTY_DOUBLE;}
		
          try {
            ids.Supplies[iSupplies].Current_Limit_Min = UALLowLevel.getDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current_Limit_Min");
          } catch(Exception exc){ids.Supplies[iSupplies].Current_Limit_Min = EMPTY_DOUBLE;}
		
          try {
            ids.Supplies[iSupplies].Voltage_Limit_Max = UALLowLevel.getDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage_Limit_Max");
          } catch(Exception exc){ids.Supplies[iSupplies].Voltage_Limit_Max = EMPTY_DOUBLE;}
		
          try {
            ids.Supplies[iSupplies].Voltage_Limit_Min = UALLowLevel.getDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage_Limit_Min");
          } catch(Exception exc){ids.Supplies[iSupplies].Voltage_Limit_Min = EMPTY_DOUBLE;}
		
          try {
            ids.Supplies[iSupplies].Current_Limiter_Gain = UALLowLevel.getDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current_Limiter_Gain");
          } catch(Exception exc){ids.Supplies[iSupplies].Current_Limiter_Gain = EMPTY_DOUBLE;}
		
          try {
            ids.Supplies[iSupplies].Energy_Limit_Max = UALLowLevel.getDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Energy_Limit_Max");
          } catch(Exception exc){ids.Supplies[iSupplies].Energy_Limit_Max = EMPTY_DOUBLE;}
		
          try {
            ids.Supplies[iSupplies].Nonlinear_Model = UALLowLevel.getString(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Nonlinear_Model");
          } catch(Exception exc){ids.Supplies[iSupplies].Nonlinear_Model = null;}
		
          try {
            ids.Supplies[iSupplies].Voltage.Data = UALLowLevel.getVect1DDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage" + "/Data");
          } catch(Exception exc){ids.Supplies[iSupplies].Voltage.Data = null;}
		
          try {
            ids.Supplies[iSupplies].Voltage.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage" + "/Timebase");
          } catch(Exception exc){ids.Supplies[iSupplies].Voltage.Timebase = null;}
		
          try {
            ids.Supplies[iSupplies].Current.Data = UALLowLevel.getVect1DDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current" + "/Data");
          } catch(Exception exc){ids.Supplies[iSupplies].Current.Data = null;}
		
          try {
            ids.Supplies[iSupplies].Current.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current" + "/Timebase");
          } catch(Exception exc){ids.Supplies[iSupplies].Current.Timebase = null;}
		
            }
          } catch(Exception exc){ids.Supplies = null;}

          try {
            ids.Passive_Loops.Names = UALLowLevel.getVect1DString(expIdx, path, "Passive_Loops" + "/Names");
          } catch(Exception exc){ids.Passive_Loops.Names = null;}
		
          try {
            ids.Passive_Loops.Areas = UALLowLevel.getVect1DDouble(expIdx, path, "Passive_Loops" + "/Areas");
          } catch(Exception exc){ids.Passive_Loops.Areas = null;}
		
          try {
            ids.Passive_Loops.Resistances = UALLowLevel.getVect1DDouble(expIdx, path, "Passive_Loops" + "/Resistances");
          } catch(Exception exc){ids.Passive_Loops.Resistances = null;}
		
// Get Array of Structures Passive_Loops/Geometries
          try {
            ids.Passive_Loops.Geometries = new Passive_LoopsClass.GeometriesClass[UALLowLevel.getInt(expIdx,path, "Passive_Loops"+"/Geometries/Shape_of")];
            for (int iGeometries = 0; iGeometries<ids.Passive_Loops.Geometries.length; iGeometries++){
              ids.Passive_Loops.Geometries[iGeometries] = new Passive_LoopsClass.GeometriesClass();

          try {
            ids.Passive_Loops.Geometries[iGeometries].Description_Type = UALLowLevel.getInt(expIdx, path, "Passive_Loops"+"/Geometries/" + Integer.toString(iGeometries+1).trim() + "/Description_Type");
          } catch(Exception exc){ids.Passive_Loops.Geometries[iGeometries].Description_Type = EMPTY_INT;}
		
          try {
            ids.Passive_Loops.Geometries[iGeometries].Coordinates_RZ = UALLowLevel.getVect2DDouble(expIdx, path, "Passive_Loops"+"/Geometries/" + Integer.toString(iGeometries+1).trim() + "/Coordinates_RZ");
          } catch(Exception exc){ids.Passive_Loops.Geometries[iGeometries].Coordinates_RZ = null;}
		
          try {
            ids.Passive_Loops.Geometries[iGeometries].RZDRDZ = UALLowLevel.getVect1DDouble(expIdx, path, "Passive_Loops"+"/Geometries/" + Integer.toString(iGeometries+1).trim() + "/RZDRDZ");
          } catch(Exception exc){ids.Passive_Loops.Geometries[iGeometries].RZDRDZ = null;}
		
            }
          } catch(Exception exc){ids.Passive_Loops.Geometries = null;}

          try {
            ids.Passive_Loops.Currents = UALLowLevel.getVect2DDouble(expIdx, path, "Passive_Loops" + "/Currents");
          } catch(Exception exc){ids.Passive_Loops.Currents = null;}
		
          try {
            ids.Passive_Loops.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Passive_Loops" + "/Timebase");
          } catch(Exception exc){ids.Passive_Loops.Timebase = null;}
		
          try {
            ids.Vacuum_Model.Names = UALLowLevel.getVect1DString(expIdx, path, "Vacuum_Model" + "/Names");
          } catch(Exception exc){ids.Vacuum_Model.Names = null;}
		
          try {
            ids.Vacuum_Model.Inductance_Matrix = UALLowLevel.getVect2DDouble(expIdx, path, "Vacuum_Model" + "/Inductance_Matrix");
          } catch(Exception exc){ids.Vacuum_Model.Inductance_Matrix = null;}
		
          try {
            ids.Vacuum_Model.Resistance_Vector = UALLowLevel.getVect1DDouble(expIdx, path, "Vacuum_Model" + "/Resistance_Vector");
          } catch(Exception exc){ids.Vacuum_Model.Resistance_Vector = null;}
		
          try {
            ids.Code_Parameters.Code_Name = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Name");
          } catch(Exception exc){ids.Code_Parameters.Code_Name = null;}
		
          try {
            ids.Code_Parameters.Code_Version = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Version");
          } catch(Exception exc){ids.Code_Parameters.Code_Version = null;}
		
          try {
            ids.Code_Parameters.Parameters = UALLowLevel.getVect1DString(expIdx, path, "Code_Parameters" + "/Parameters");
          } catch(Exception exc){ids.Code_Parameters.Parameters = null;}
		
          try {
            ids.Code_Parameters.Output_Diagnostics = UALLowLevel.getVect1DString(expIdx, path, "Code_Parameters" + "/Output_Diagnostics");
          } catch(Exception exc){ids.Code_Parameters.Output_Diagnostics = null;}
		
          try {
            ids.Code_Parameters.Output_Flag = UALLowLevel.getVect1DInt(expIdx, path, "Code_Parameters" + "/Output_Flag");
          } catch(Exception exc){ids.Code_Parameters.Output_Flag = null;}
		
          try {
            ids.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Timebase");
          } catch(Exception exc){ids.Timebase = null;}
		
      UALLowLevel.endIDSGet(expIdx, path);
      return ids;
    }

 /**
 * Method getSlice retrieves the  PF IDS in the open database corresponding to the passed time, based on the selectted interpolation mode.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param time The retrieval time.
 * @param interpolMode Defines the selected interpolation. Allowed values are imas.INTERPOLATION, imas.CLOSEST_SAMPLE,
 * imas.PREVIOUS_SAMPLE
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the selected PF ids. Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if double and empty String is string.
 **/
    public static PF  getSlice(int expIdx, String path, double time, int interpolMode) throws UALException
    {
      String        timebasepath;
      String        ual_debug = System.getenv("ual_debug");
      double        retTime;
      PF ids = new PF (); 
      retTime = UALLowLevel.beginIDSGetSlice(expIdx, path, time);

          try {
            ids.IDS_Properties.Status_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Status_of");
          } catch(Exception exc){ids.IDS_Properties.Status_of = null;}
		
          try {
            ids.IDS_Properties.Comment_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Comment_of");
          } catch(Exception exc){ids.IDS_Properties.Comment_of = null;}
		
          try {
            ids.IDS_Properties.Creator_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Creator_of");
          } catch(Exception exc){ids.IDS_Properties.Creator_of = null;}
		
          try {
            ids.IDS_Properties.Date_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Date_of");
          } catch(Exception exc){ids.IDS_Properties.Date_of = null;}
		
          try {
            ids.IDS_Properties.Source_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Source_of");
          } catch(Exception exc){ids.IDS_Properties.Source_of = null;}
		
          try {
            ids.IDS_Properties.Reference_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Reference_of");
          } catch(Exception exc){ids.IDS_Properties.Reference_of = null;}
		
          try {
            ids.IDS_Properties.Homogeneous_Timebase = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase");
          } catch(Exception exc){ids.IDS_Properties.Homogeneous_Timebase = EMPTY_INT;}
		
          try {
            ids.IDS_Properties.cocos = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/cocos");
          } catch(Exception exc){ids.IDS_Properties.cocos = EMPTY_INT;}
		
// Get Array of Structures Coils
          try {
            ids.Coils = new CoilsClass[UALLowLevel.getInt(expIdx,path, "Coils/Shape_of")];
            for (int iCoils = 0; iCoils<ids.Coils.length; iCoils++){
              ids.Coils[iCoils] = new CoilsClass();
			
          try {
            ids.Coils[iCoils].Name = UALLowLevel.getString(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Name");
          } catch(Exception exc){ids.Coils[iCoils].Name = null;}
		
          try {
            ids.Coils[iCoils].Identifier = UALLowLevel.getString(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Identifier");
          } catch(Exception exc){ids.Coils[iCoils].Identifier = null;}
		
          try {
            ids.Coils[iCoils].Resistance = UALLowLevel.getDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Resistance");
          } catch(Exception exc){ids.Coils[iCoils].Resistance = EMPTY_DOUBLE;}
		
          try {
            ids.Coils[iCoils].Energy_Limit_Max = UALLowLevel.getDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Energy_Limit_Max");
          } catch(Exception exc){ids.Coils[iCoils].Energy_Limit_Max = EMPTY_DOUBLE;}
		
// Get Array of Structures Coils/Elements
          try {
            ids.Coils[iCoils].Elements = new CoilsClass.ElementsClass[UALLowLevel.getInt(expIdx,path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/Shape_of")];
            for (int iElements = 0; iElements<ids.Coils[iCoils].Elements.length; iElements++){
              ids.Coils[iCoils].Elements[iElements] = new CoilsClass.ElementsClass();
			
          try {
            ids.Coils[iCoils].Elements[iElements].Name = UALLowLevel.getString(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Name");
          } catch(Exception exc){ids.Coils[iCoils].Elements[iElements].Name = null;}
		
          try {
            ids.Coils[iCoils].Elements[iElements].Identifier = UALLowLevel.getString(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Identifier");
          } catch(Exception exc){ids.Coils[iCoils].Elements[iElements].Identifier = null;}
		
          try {
            ids.Coils[iCoils].Elements[iElements].Turns_With_Sign = UALLowLevel.getInt(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Turns_With_Sign");
          } catch(Exception exc){ids.Coils[iCoils].Elements[iElements].Turns_With_Sign = EMPTY_INT;}
		
          try {
            ids.Coils[iCoils].Elements[iElements].Area = UALLowLevel.getDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Area");
          } catch(Exception exc){ids.Coils[iCoils].Elements[iElements].Area = EMPTY_DOUBLE;}
		
          try {
            ids.Coils[iCoils].Elements[iElements].Geometry.Description_Type = UALLowLevel.getInt(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Geometry" + "/Description_Type");
          } catch(Exception exc){ids.Coils[iCoils].Elements[iElements].Geometry.Description_Type = EMPTY_INT;}
		
          try {
            ids.Coils[iCoils].Elements[iElements].Geometry.Coordinates_RZ = UALLowLevel.getVect2DDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Geometry" + "/Coordinates_RZ");
          } catch(Exception exc){ids.Coils[iCoils].Elements[iElements].Geometry.Coordinates_RZ = null;}
		
          try {
            ids.Coils[iCoils].Elements[iElements].Geometry.RZDRDZ = UALLowLevel.getVect1DDouble(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Geometry" + "/RZDRDZ");
          } catch(Exception exc){ids.Coils[iCoils].Elements[iElements].Geometry.RZDRDZ = null;}
		
            }
          } catch(Exception exc){ids.Coils[iCoils].Elements = null;}
		
// Get Slice Coils/Current/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Coils/" + Integer.toString(iCoils+1).trim() + "/Current" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Current" + "/Data");
	    if (obj.getElementAt(0) > 0) {
              ids.Coils[iCoils].Current.Data = new Vect1DDouble(1);
              ids.Coils[iCoils].Current.Data.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Current" + "/Data", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Coils[iCoils].Current.Data = null;}
		
// Get Slice Coils/Current/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Coils/" + Integer.toString(iCoils+1).trim() + "/Current" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Current" + "/Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Coils[iCoils].Current.Timebase = new Vect1DDouble(1);
              ids.Coils[iCoils].Current.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Current" + "/Timebase", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Coils[iCoils].Current.Timebase = null;}
		
// Get Slice Coils/Voltage/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Coils/" + Integer.toString(iCoils+1).trim() + "/Voltage" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Voltage" + "/Data");
	    if (obj.getElementAt(0) > 0) {
              ids.Coils[iCoils].Voltage.Data = new Vect1DDouble(1);
              ids.Coils[iCoils].Voltage.Data.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Voltage" + "/Data", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Coils[iCoils].Voltage.Data = null;}
		
// Get Slice Coils/Voltage/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Coils/" + Integer.toString(iCoils+1).trim() + "/Voltage" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Voltage" + "/Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Coils[iCoils].Voltage.Timebase = new Vect1DDouble(1);
              ids.Coils[iCoils].Voltage.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Coils/" + Integer.toString(iCoils+1).trim() + "/Voltage" + "/Timebase", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Coils[iCoils].Voltage.Timebase = null;}
		
            }
          } catch(Exception exc){ids.Coils = null;}
		
          try {
            ids.Vertical_Forces.Names = UALLowLevel.getVect1DString(expIdx, path, "Vertical_Forces" + "/Names");
          } catch(Exception exc){ids.Vertical_Forces.Names = null;}
		
          try {
            ids.Vertical_Forces.Combinations = UALLowLevel.getVect2DDouble(expIdx, path, "Vertical_Forces" + "/Combinations");
          } catch(Exception exc){ids.Vertical_Forces.Combinations = null;}
		
          try {
            ids.Vertical_Forces.Limits_Max = UALLowLevel.getVect1DDouble(expIdx, path, "Vertical_Forces" + "/Limits_Max");
          } catch(Exception exc){ids.Vertical_Forces.Limits_Max = null;}
		
          try {
            ids.Vertical_Forces.Limits_Min = UALLowLevel.getVect1DDouble(expIdx, path, "Vertical_Forces" + "/Limits_Min");
          } catch(Exception exc){ids.Vertical_Forces.Limits_Min = null;}
		
// Get Slice Vertical_Forces/Forces
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Vertical_Forces/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Vertical_Forces" + "/Forces");
	    if (obj.getElementAt(0) > 0) {
              ids.Vertical_Forces.Forces = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Vertical_Forces.Forces.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Vertical_Forces" + "/Forces", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Vertical_Forces.Forces = null;}
		
// Get Slice Vertical_Forces/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Vertical_Forces/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Vertical_Forces" + "/Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Vertical_Forces.Timebase = new Vect1DDouble(1);
              ids.Vertical_Forces.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Vertical_Forces" + "/Timebase", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Vertical_Forces.Timebase = null;}
		
// Get Array of Structures Circuits
          try {
            ids.Circuits = new CircuitsClass[UALLowLevel.getInt(expIdx,path, "Circuits/Shape_of")];
            for (int iCircuits = 0; iCircuits<ids.Circuits.length; iCircuits++){
              ids.Circuits[iCircuits] = new CircuitsClass();
			
          try {
            ids.Circuits[iCircuits].Name = UALLowLevel.getString(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Name");
          } catch(Exception exc){ids.Circuits[iCircuits].Name = null;}
		
          try {
            ids.Circuits[iCircuits].Identifier = UALLowLevel.getString(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Identifier");
          } catch(Exception exc){ids.Circuits[iCircuits].Identifier = null;}
		
          try {
            ids.Circuits[iCircuits].Type = UALLowLevel.getString(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Type");
          } catch(Exception exc){ids.Circuits[iCircuits].Type = null;}
		
          try {
            ids.Circuits[iCircuits].Connections = UALLowLevel.getVect2DDouble(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Connections");
          } catch(Exception exc){ids.Circuits[iCircuits].Connections = null;}
		
// Get Slice Circuits/Voltage/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Circuits/" + Integer.toString(iCircuits+1).trim() + "/Voltage" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Voltage" + "/Data");
	    if (obj.getElementAt(0) > 0) {
              ids.Circuits[iCircuits].Voltage.Data = new Vect1DDouble(1);
              ids.Circuits[iCircuits].Voltage.Data.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Voltage" + "/Data", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Circuits[iCircuits].Voltage.Data = null;}
		
// Get Slice Circuits/Voltage/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Circuits/" + Integer.toString(iCircuits+1).trim() + "/Voltage" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Voltage" + "/Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Circuits[iCircuits].Voltage.Timebase = new Vect1DDouble(1);
              ids.Circuits[iCircuits].Voltage.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Voltage" + "/Timebase", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Circuits[iCircuits].Voltage.Timebase = null;}
		
// Get Slice Circuits/Current/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Circuits/" + Integer.toString(iCircuits+1).trim() + "/Current" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Current" + "/Data");
	    if (obj.getElementAt(0) > 0) {
              ids.Circuits[iCircuits].Current.Data = new Vect1DDouble(1);
              ids.Circuits[iCircuits].Current.Data.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Current" + "/Data", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Circuits[iCircuits].Current.Data = null;}
		
// Get Slice Circuits/Current/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Circuits/" + Integer.toString(iCircuits+1).trim() + "/Current" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Current" + "/Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Circuits[iCircuits].Current.Timebase = new Vect1DDouble(1);
              ids.Circuits[iCircuits].Current.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Circuits/" + Integer.toString(iCircuits+1).trim() + "/Current" + "/Timebase", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Circuits[iCircuits].Current.Timebase = null;}
		
            }
          } catch(Exception exc){ids.Circuits = null;}
		
// Get Array of Structures Supplies
          try {
            ids.Supplies = new SuppliesClass[UALLowLevel.getInt(expIdx,path, "Supplies/Shape_of")];
            for (int iSupplies = 0; iSupplies<ids.Supplies.length; iSupplies++){
              ids.Supplies[iSupplies] = new SuppliesClass();
			
          try {
            ids.Supplies[iSupplies].Name = UALLowLevel.getString(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Name");
          } catch(Exception exc){ids.Supplies[iSupplies].Name = null;}
		
          try {
            ids.Supplies[iSupplies].Identifier = UALLowLevel.getString(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Identifier");
          } catch(Exception exc){ids.Supplies[iSupplies].Identifier = null;}
		
          try {
            ids.Supplies[iSupplies].Type = UALLowLevel.getInt(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Type");
          } catch(Exception exc){ids.Supplies[iSupplies].Type = EMPTY_INT;}
		
          try {
            ids.Supplies[iSupplies].Resistance = UALLowLevel.getDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Resistance");
          } catch(Exception exc){ids.Supplies[iSupplies].Resistance = EMPTY_DOUBLE;}
		
          try {
            ids.Supplies[iSupplies].Delay = UALLowLevel.getDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Delay");
          } catch(Exception exc){ids.Supplies[iSupplies].Delay = EMPTY_DOUBLE;}
		
          try {
            ids.Supplies[iSupplies].Filter_Numerator = UALLowLevel.getVect1DDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Filter_Numerator");
          } catch(Exception exc){ids.Supplies[iSupplies].Filter_Numerator = null;}
		
          try {
            ids.Supplies[iSupplies].Filter_Denominator = UALLowLevel.getVect1DDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Filter_Denominator");
          } catch(Exception exc){ids.Supplies[iSupplies].Filter_Denominator = null;}
		
          try {
            ids.Supplies[iSupplies].Current_Limit_Max = UALLowLevel.getDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current_Limit_Max");
          } catch(Exception exc){ids.Supplies[iSupplies].Current_Limit_Max = EMPTY_DOUBLE;}
		
          try {
            ids.Supplies[iSupplies].Current_Limit_Min = UALLowLevel.getDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current_Limit_Min");
          } catch(Exception exc){ids.Supplies[iSupplies].Current_Limit_Min = EMPTY_DOUBLE;}
		
          try {
            ids.Supplies[iSupplies].Voltage_Limit_Max = UALLowLevel.getDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage_Limit_Max");
          } catch(Exception exc){ids.Supplies[iSupplies].Voltage_Limit_Max = EMPTY_DOUBLE;}
		
          try {
            ids.Supplies[iSupplies].Voltage_Limit_Min = UALLowLevel.getDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage_Limit_Min");
          } catch(Exception exc){ids.Supplies[iSupplies].Voltage_Limit_Min = EMPTY_DOUBLE;}
		
          try {
            ids.Supplies[iSupplies].Current_Limiter_Gain = UALLowLevel.getDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current_Limiter_Gain");
          } catch(Exception exc){ids.Supplies[iSupplies].Current_Limiter_Gain = EMPTY_DOUBLE;}
		
          try {
            ids.Supplies[iSupplies].Energy_Limit_Max = UALLowLevel.getDouble(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Energy_Limit_Max");
          } catch(Exception exc){ids.Supplies[iSupplies].Energy_Limit_Max = EMPTY_DOUBLE;}
		
          try {
            ids.Supplies[iSupplies].Nonlinear_Model = UALLowLevel.getString(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Nonlinear_Model");
          } catch(Exception exc){ids.Supplies[iSupplies].Nonlinear_Model = null;}
		
// Get Slice Supplies/Voltage/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage" + "/Data");
	    if (obj.getElementAt(0) > 0) {
              ids.Supplies[iSupplies].Voltage.Data = new Vect1DDouble(1);
              ids.Supplies[iSupplies].Voltage.Data.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage" + "/Data", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Supplies[iSupplies].Voltage.Data = null;}
		
// Get Slice Supplies/Voltage/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage" + "/Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Supplies[iSupplies].Voltage.Timebase = new Vect1DDouble(1);
              ids.Supplies[iSupplies].Voltage.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage" + "/Timebase", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Supplies[iSupplies].Voltage.Timebase = null;}
		
// Get Slice Supplies/Current/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current" + "/Data");
	    if (obj.getElementAt(0) > 0) {
              ids.Supplies[iSupplies].Current.Data = new Vect1DDouble(1);
              ids.Supplies[iSupplies].Current.Data.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current" + "/Data", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Supplies[iSupplies].Current.Data = null;}
		
// Get Slice Supplies/Current/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current" + "/Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Supplies[iSupplies].Current.Timebase = new Vect1DDouble(1);
              ids.Supplies[iSupplies].Current.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current" + "/Timebase", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Supplies[iSupplies].Current.Timebase = null;}
		
            }
          } catch(Exception exc){ids.Supplies = null;}
		
          try {
            ids.Passive_Loops.Names = UALLowLevel.getVect1DString(expIdx, path, "Passive_Loops" + "/Names");
          } catch(Exception exc){ids.Passive_Loops.Names = null;}
		
          try {
            ids.Passive_Loops.Areas = UALLowLevel.getVect1DDouble(expIdx, path, "Passive_Loops" + "/Areas");
          } catch(Exception exc){ids.Passive_Loops.Areas = null;}
		
          try {
            ids.Passive_Loops.Resistances = UALLowLevel.getVect1DDouble(expIdx, path, "Passive_Loops" + "/Resistances");
          } catch(Exception exc){ids.Passive_Loops.Resistances = null;}
		
// Get Array of Structures Passive_Loops/Geometries
          try {
            ids.Passive_Loops.Geometries = new Passive_LoopsClass.GeometriesClass[UALLowLevel.getInt(expIdx,path, "Passive_Loops"+"/Geometries/Shape_of")];
            for (int iGeometries = 0; iGeometries<ids.Passive_Loops.Geometries.length; iGeometries++){
              ids.Passive_Loops.Geometries[iGeometries] = new Passive_LoopsClass.GeometriesClass();
			
          try {
            ids.Passive_Loops.Geometries[iGeometries].Description_Type = UALLowLevel.getInt(expIdx, path, "Passive_Loops"+"/Geometries/" + Integer.toString(iGeometries+1).trim() + "/Description_Type");
          } catch(Exception exc){ids.Passive_Loops.Geometries[iGeometries].Description_Type = EMPTY_INT;}
		
          try {
            ids.Passive_Loops.Geometries[iGeometries].Coordinates_RZ = UALLowLevel.getVect2DDouble(expIdx, path, "Passive_Loops"+"/Geometries/" + Integer.toString(iGeometries+1).trim() + "/Coordinates_RZ");
          } catch(Exception exc){ids.Passive_Loops.Geometries[iGeometries].Coordinates_RZ = null;}
		
          try {
            ids.Passive_Loops.Geometries[iGeometries].RZDRDZ = UALLowLevel.getVect1DDouble(expIdx, path, "Passive_Loops"+"/Geometries/" + Integer.toString(iGeometries+1).trim() + "/RZDRDZ");
          } catch(Exception exc){ids.Passive_Loops.Geometries[iGeometries].RZDRDZ = null;}
		
            }
          } catch(Exception exc){ids.Passive_Loops.Geometries = null;}
		
// Get Slice Passive_Loops/Currents
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Passive_Loops/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Passive_Loops" + "/Currents");
	    if (obj.getElementAt(0) > 0) {
              ids.Passive_Loops.Currents = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Passive_Loops.Currents.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "Passive_Loops" + "/Currents", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Passive_Loops.Currents = null;}
		
// Get Slice Passive_Loops/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Passive_Loops/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Passive_Loops" + "/Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Passive_Loops.Timebase = new Vect1DDouble(1);
              ids.Passive_Loops.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Passive_Loops" + "/Timebase", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Passive_Loops.Timebase = null;}
		
          try {
            ids.Vacuum_Model.Names = UALLowLevel.getVect1DString(expIdx, path, "Vacuum_Model" + "/Names");
          } catch(Exception exc){ids.Vacuum_Model.Names = null;}
		
          try {
            ids.Vacuum_Model.Inductance_Matrix = UALLowLevel.getVect2DDouble(expIdx, path, "Vacuum_Model" + "/Inductance_Matrix");
          } catch(Exception exc){ids.Vacuum_Model.Inductance_Matrix = null;}
		
          try {
            ids.Vacuum_Model.Resistance_Vector = UALLowLevel.getVect1DDouble(expIdx, path, "Vacuum_Model" + "/Resistance_Vector");
          } catch(Exception exc){ids.Vacuum_Model.Resistance_Vector = null;}
		
          try {
            ids.Code_Parameters.Code_Name = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Name");
          } catch(Exception exc){ids.Code_Parameters.Code_Name = null;}
		
          try {
            ids.Code_Parameters.Code_Version = UALLowLevel.getString(expIdx, path, "Code_Parameters" + "/Code_Version");
          } catch(Exception exc){ids.Code_Parameters.Code_Version = null;}
		
// Get Slice Code_Parameters/Parameters
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
            ids.Code_Parameters.Parameters = new Vect1DString(1);
            ids.Code_Parameters.Parameters.setElementAt(0, UALLowLevel.getStringSlice(expIdx, path, "Code_Parameters" + "/Parameters", timebasepath.trim(), time, interpolMode));
          } catch(Exception exc){ids.Code_Parameters.Parameters = null;}
		
// Get Slice Code_Parameters/Output_Diagnostics
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
            ids.Code_Parameters.Output_Diagnostics = new Vect1DString(1);
            ids.Code_Parameters.Output_Diagnostics.setElementAt(0, UALLowLevel.getStringSlice(expIdx, path, "Code_Parameters" + "/Output_Diagnostics", timebasepath.trim(), time, interpolMode));
          } catch(Exception exc){ids.Code_Parameters.Output_Diagnostics = null;}
		
// Get Slice Code_Parameters/Output_Flag
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Code_Parameters" + "/Output_Flag");
	    if (obj.getElementAt(0) > 0) {
              ids.Code_Parameters.Output_Flag = new Vect1DInt(1);
              ids.Code_Parameters.Output_Flag.setElementAt(0, UALLowLevel.getIntSlice(expIdx, path, "Code_Parameters" + "/Output_Flag", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Code_Parameters.Output_Flag = null;}
		
// Get Slice Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Timebase = new Vect1DDouble(1);
              ids.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "/Timebase", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Timebase = null;}
		
      UALLowLevel.endIDSGetSlice(expIdx, path);
      return ids;
    }
    
 /**
 * Method delete removes all the data associated with a PF IDS in the open database.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void delete(int expIdx, String path) throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");
      
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Status_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Comment_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Creator_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Date_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Source_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Reference_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Homogeneous_Timebase");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/cocos");          
      for (int iCoils = 0; iCoils<20; iCoils++){
      
      UALLowLevel.deleteData(expIdx,path,"/Coils/" + Integer.toString(iCoils+1).trim() + "/Name");          
      UALLowLevel.deleteData(expIdx,path,"/Coils/" + Integer.toString(iCoils+1).trim() + "/Identifier");          
      UALLowLevel.deleteData(expIdx,path,"/Coils/" + Integer.toString(iCoils+1).trim() + "/Resistance");          
      UALLowLevel.deleteData(expIdx,path,"/Coils/" + Integer.toString(iCoils+1).trim() + "/Energy_Limit_Max");          
      for (int iElements = 0; iElements<20; iElements++){
       
      UALLowLevel.deleteData(expIdx,path,"/Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Name");          
      UALLowLevel.deleteData(expIdx,path,"/Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Identifier");          
      UALLowLevel.deleteData(expIdx,path,"/Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Turns_With_Sign");          
      UALLowLevel.deleteData(expIdx,path,"/Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Area");          
      UALLowLevel.deleteData(expIdx,path,"/Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Geometry" + "/Description_Type");          
      UALLowLevel.deleteData(expIdx,path,"/Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Geometry" + "/Coordinates_RZ");          
      UALLowLevel.deleteData(expIdx,path,"/Coils/" + Integer.toString(iCoils+1).trim()+"/Elements/" + Integer.toString(iElements+1).trim() + "/Geometry" + "/RZDRDZ");          
      }
   
      UALLowLevel.deleteData(expIdx,path,"/Coils/" + Integer.toString(iCoils+1).trim() + "/Current" + "/Data");          
      UALLowLevel.deleteData(expIdx,path,"/Coils/" + Integer.toString(iCoils+1).trim() + "/Current" + "/Timebase");          
      UALLowLevel.deleteData(expIdx,path,"/Coils/" + Integer.toString(iCoils+1).trim() + "/Voltage" + "/Data");          
      UALLowLevel.deleteData(expIdx,path,"/Coils/" + Integer.toString(iCoils+1).trim() + "/Voltage" + "/Timebase");          
      }
   
      UALLowLevel.deleteData(expIdx,path,"Vertical_Forces" + "/Names");          
      UALLowLevel.deleteData(expIdx,path,"Vertical_Forces" + "/Combinations");          
      UALLowLevel.deleteData(expIdx,path,"Vertical_Forces" + "/Limits_Max");          
      UALLowLevel.deleteData(expIdx,path,"Vertical_Forces" + "/Limits_Min");          
      UALLowLevel.deleteData(expIdx,path,"Vertical_Forces" + "/Forces");          
      UALLowLevel.deleteData(expIdx,path,"Vertical_Forces" + "/Timebase");          
      for (int iCircuits = 0; iCircuits<20; iCircuits++){
      
      UALLowLevel.deleteData(expIdx,path,"/Circuits/" + Integer.toString(iCircuits+1).trim() + "/Name");          
      UALLowLevel.deleteData(expIdx,path,"/Circuits/" + Integer.toString(iCircuits+1).trim() + "/Identifier");          
      UALLowLevel.deleteData(expIdx,path,"/Circuits/" + Integer.toString(iCircuits+1).trim() + "/Type");          
      UALLowLevel.deleteData(expIdx,path,"/Circuits/" + Integer.toString(iCircuits+1).trim() + "/Connections");          
      UALLowLevel.deleteData(expIdx,path,"/Circuits/" + Integer.toString(iCircuits+1).trim() + "/Voltage" + "/Data");          
      UALLowLevel.deleteData(expIdx,path,"/Circuits/" + Integer.toString(iCircuits+1).trim() + "/Voltage" + "/Timebase");          
      UALLowLevel.deleteData(expIdx,path,"/Circuits/" + Integer.toString(iCircuits+1).trim() + "/Current" + "/Data");          
      UALLowLevel.deleteData(expIdx,path,"/Circuits/" + Integer.toString(iCircuits+1).trim() + "/Current" + "/Timebase");          
      }
   
      for (int iSupplies = 0; iSupplies<20; iSupplies++){
      
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Name");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Identifier");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Type");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Resistance");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Delay");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Filter_Numerator");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Filter_Denominator");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current_Limit_Max");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current_Limit_Min");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage_Limit_Max");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage_Limit_Min");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current_Limiter_Gain");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Energy_Limit_Max");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Nonlinear_Model");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage" + "/Data");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Voltage" + "/Timebase");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current" + "/Data");          
      UALLowLevel.deleteData(expIdx,path,"/Supplies/" + Integer.toString(iSupplies+1).trim() + "/Current" + "/Timebase");          
      }
   
      UALLowLevel.deleteData(expIdx,path,"Passive_Loops" + "/Names");          
      UALLowLevel.deleteData(expIdx,path,"Passive_Loops" + "/Areas");          
      UALLowLevel.deleteData(expIdx,path,"Passive_Loops" + "/Resistances");          
      for (int iGeometries = 0; iGeometries<20; iGeometries++){
       
      UALLowLevel.deleteData(expIdx,path,"Passive_Loops"+"/Geometries/" + Integer.toString(iGeometries+1).trim() + "/Description_Type");          
      UALLowLevel.deleteData(expIdx,path,"Passive_Loops"+"/Geometries/" + Integer.toString(iGeometries+1).trim() + "/Coordinates_RZ");          
      UALLowLevel.deleteData(expIdx,path,"Passive_Loops"+"/Geometries/" + Integer.toString(iGeometries+1).trim() + "/RZDRDZ");          
      }
   
      UALLowLevel.deleteData(expIdx,path,"Passive_Loops" + "/Currents");          
      UALLowLevel.deleteData(expIdx,path,"Passive_Loops" + "/Timebase");          
      UALLowLevel.deleteData(expIdx,path,"Vacuum_Model" + "/Names");          
      UALLowLevel.deleteData(expIdx,path,"Vacuum_Model" + "/Inductance_Matrix");          
      UALLowLevel.deleteData(expIdx,path,"Vacuum_Model" + "/Resistance_Vector");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Code_Name");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Code_Version");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Parameters");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Output_Diagnostics");          
      UALLowLevel.deleteData(expIdx,path,"Code_Parameters" + "/Output_Flag");          
      UALLowLevel.deleteData(expIdx, path, "Timebase");  
    }


     
 /**
 * Method dump is used for debugging and prints the content of the object
 **/
  public void dump()
  {
    System.out.println("***** PF *****");
    

  System.out.println("IDS_Properties ");
  

  System.out.println("Status_of ");
  
        if(IDS_Properties.Status_of!= null)
            System.out.println(IDS_Properties.Status_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Comment_of ");
  
        if(IDS_Properties.Comment_of!= null)
            System.out.println(IDS_Properties.Comment_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Creator_of ");
  
        if(IDS_Properties.Creator_of!= null)
            System.out.println(IDS_Properties.Creator_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Date_of ");
  
        if(IDS_Properties.Date_of!= null)
            System.out.println(IDS_Properties.Date_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Source_of ");
  
        if(IDS_Properties.Source_of!= null)
            System.out.println(IDS_Properties.Source_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Reference_of ");
  
        if(IDS_Properties.Reference_of!= null)
            System.out.println(IDS_Properties.Reference_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Homogeneous_Timebase ");
  
        if(IDS_Properties.Homogeneous_Timebase != EMPTY_INT)
            System.out.println(IDS_Properties.Homogeneous_Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("cocos ");
  
        if(IDS_Properties.cocos != EMPTY_INT)
            System.out.println(IDS_Properties.cocos);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Coils ");
  
      if (Coils != null) {
        for (int i1 = 0; i1 < Coils.length; i1++) {
          

  System.out.println("Name ");
  
        if(Coils[i1].Name!= null)
            System.out.println(Coils[i1].Name);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Identifier ");
  
        if(Coils[i1].Identifier!= null)
            System.out.println(Coils[i1].Identifier);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Resistance ");
  
         if(Coils[i1].Resistance != EMPTY_DOUBLE)
            System.out.println(Coils[i1].Resistance);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Energy_Limit_Max ");
  
         if(Coils[i1].Energy_Limit_Max != EMPTY_DOUBLE)
            System.out.println(Coils[i1].Energy_Limit_Max);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Elements ");
  
      if (Coils[i1].Elements != null) {
        for (int i2 = 0; i2 < Coils[i1].Elements.length; i2++) {
          

  System.out.println("Name ");
  
        if(Coils[i1].Elements[i2].Name!= null)
            System.out.println(Coils[i1].Elements[i2].Name);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Identifier ");
  
        if(Coils[i1].Elements[i2].Identifier!= null)
            System.out.println(Coils[i1].Elements[i2].Identifier);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Turns_With_Sign ");
  
        if(Coils[i1].Elements[i2].Turns_With_Sign != EMPTY_INT)
            System.out.println(Coils[i1].Elements[i2].Turns_With_Sign);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Area ");
  
         if(Coils[i1].Elements[i2].Area != EMPTY_DOUBLE)
            System.out.println(Coils[i1].Elements[i2].Area);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Geometry ");
  

  System.out.println("Description_Type ");
  
        if(Coils[i1].Elements[i2].Geometry.Description_Type != EMPTY_INT)
            System.out.println(Coils[i1].Elements[i2].Geometry.Description_Type);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Coordinates_RZ ");
  
        if(Coils[i1].Elements[i2].Geometry.Coordinates_RZ != null)
            System.out.println(Coils[i1].Elements[i2].Geometry.Coordinates_RZ);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("RZDRDZ ");
  
         if(Coils[i1].Elements[i2].Geometry.RZDRDZ != null)
            System.out.println(Coils[i1].Elements[i2].Geometry.RZDRDZ);
        else
            System.out.println("Empty");
        System.out.println("");    
   
        }
      }
    

  System.out.println("Current ");
  

  System.out.println("Data ");
  
         if(Coils[i1].Current.Data != null)
            System.out.println(Coils[i1].Current.Data);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Timebase ");
  
         if(Coils[i1].Current.Timebase != null)
            System.out.println(Coils[i1].Current.Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Voltage ");
  

  System.out.println("Data ");
  
         if(Coils[i1].Voltage.Data != null)
            System.out.println(Coils[i1].Voltage.Data);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Timebase ");
  
         if(Coils[i1].Voltage.Timebase != null)
            System.out.println(Coils[i1].Voltage.Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   
        }
      }
    

  System.out.println("Vertical_Forces ");
  

  System.out.println("Names ");
  
        if(Vertical_Forces.Names != null)
            System.out.println(Vertical_Forces.Names);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Combinations ");
  
        if(Vertical_Forces.Combinations != null)
            System.out.println(Vertical_Forces.Combinations);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Limits_Max ");
  
         if(Vertical_Forces.Limits_Max != null)
            System.out.println(Vertical_Forces.Limits_Max);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Limits_Min ");
  
         if(Vertical_Forces.Limits_Min != null)
            System.out.println(Vertical_Forces.Limits_Min);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Forces ");
  
        if(Vertical_Forces.Forces != null)
            System.out.println(Vertical_Forces.Forces);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Timebase ");
  
         if(Vertical_Forces.Timebase != null)
            System.out.println(Vertical_Forces.Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Circuits ");
  
      if (Circuits != null) {
        for (int i1 = 0; i1 < Circuits.length; i1++) {
          

  System.out.println("Name ");
  
        if(Circuits[i1].Name!= null)
            System.out.println(Circuits[i1].Name);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Identifier ");
  
        if(Circuits[i1].Identifier!= null)
            System.out.println(Circuits[i1].Identifier);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Type ");
  
        if(Circuits[i1].Type!= null)
            System.out.println(Circuits[i1].Type);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Connections ");
  
        if(Circuits[i1].Connections != null)
            System.out.println(Circuits[i1].Connections);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Voltage ");
  

  System.out.println("Data ");
  
         if(Circuits[i1].Voltage.Data != null)
            System.out.println(Circuits[i1].Voltage.Data);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Timebase ");
  
         if(Circuits[i1].Voltage.Timebase != null)
            System.out.println(Circuits[i1].Voltage.Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Current ");
  

  System.out.println("Data ");
  
         if(Circuits[i1].Current.Data != null)
            System.out.println(Circuits[i1].Current.Data);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Timebase ");
  
         if(Circuits[i1].Current.Timebase != null)
            System.out.println(Circuits[i1].Current.Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   
        }
      }
    

  System.out.println("Supplies ");
  
      if (Supplies != null) {
        for (int i1 = 0; i1 < Supplies.length; i1++) {
          

  System.out.println("Name ");
  
        if(Supplies[i1].Name!= null)
            System.out.println(Supplies[i1].Name);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Identifier ");
  
        if(Supplies[i1].Identifier!= null)
            System.out.println(Supplies[i1].Identifier);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Type ");
  
        if(Supplies[i1].Type != EMPTY_INT)
            System.out.println(Supplies[i1].Type);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Resistance ");
  
         if(Supplies[i1].Resistance != EMPTY_DOUBLE)
            System.out.println(Supplies[i1].Resistance);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Delay ");
  
         if(Supplies[i1].Delay != EMPTY_DOUBLE)
            System.out.println(Supplies[i1].Delay);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Filter_Numerator ");
  
         if(Supplies[i1].Filter_Numerator != null)
            System.out.println(Supplies[i1].Filter_Numerator);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Filter_Denominator ");
  
         if(Supplies[i1].Filter_Denominator != null)
            System.out.println(Supplies[i1].Filter_Denominator);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Current_Limit_Max ");
  
         if(Supplies[i1].Current_Limit_Max != EMPTY_DOUBLE)
            System.out.println(Supplies[i1].Current_Limit_Max);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Current_Limit_Min ");
  
         if(Supplies[i1].Current_Limit_Min != EMPTY_DOUBLE)
            System.out.println(Supplies[i1].Current_Limit_Min);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Voltage_Limit_Max ");
  
         if(Supplies[i1].Voltage_Limit_Max != EMPTY_DOUBLE)
            System.out.println(Supplies[i1].Voltage_Limit_Max);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Voltage_Limit_Min ");
  
         if(Supplies[i1].Voltage_Limit_Min != EMPTY_DOUBLE)
            System.out.println(Supplies[i1].Voltage_Limit_Min);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Current_Limiter_Gain ");
  
         if(Supplies[i1].Current_Limiter_Gain != EMPTY_DOUBLE)
            System.out.println(Supplies[i1].Current_Limiter_Gain);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Energy_Limit_Max ");
  
         if(Supplies[i1].Energy_Limit_Max != EMPTY_DOUBLE)
            System.out.println(Supplies[i1].Energy_Limit_Max);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Nonlinear_Model ");
  
        if(Supplies[i1].Nonlinear_Model!= null)
            System.out.println(Supplies[i1].Nonlinear_Model);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Voltage ");
  

  System.out.println("Data ");
  
         if(Supplies[i1].Voltage.Data != null)
            System.out.println(Supplies[i1].Voltage.Data);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Timebase ");
  
         if(Supplies[i1].Voltage.Timebase != null)
            System.out.println(Supplies[i1].Voltage.Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Current ");
  

  System.out.println("Data ");
  
         if(Supplies[i1].Current.Data != null)
            System.out.println(Supplies[i1].Current.Data);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Timebase ");
  
         if(Supplies[i1].Current.Timebase != null)
            System.out.println(Supplies[i1].Current.Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   
        }
      }
    

  System.out.println("Passive_Loops ");
  

  System.out.println("Names ");
  
        if(Passive_Loops.Names != null)
            System.out.println(Passive_Loops.Names);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Areas ");
  
         if(Passive_Loops.Areas != null)
            System.out.println(Passive_Loops.Areas);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Resistances ");
  
         if(Passive_Loops.Resistances != null)
            System.out.println(Passive_Loops.Resistances);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Geometries ");
  
      if (Passive_Loops.Geometries != null) {
        for (int i1 = 0; i1 < Passive_Loops.Geometries.length; i1++) {
          

  System.out.println("Description_Type ");
  
        if(Passive_Loops.Geometries[i1].Description_Type != EMPTY_INT)
            System.out.println(Passive_Loops.Geometries[i1].Description_Type);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Coordinates_RZ ");
  
        if(Passive_Loops.Geometries[i1].Coordinates_RZ != null)
            System.out.println(Passive_Loops.Geometries[i1].Coordinates_RZ);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("RZDRDZ ");
  
         if(Passive_Loops.Geometries[i1].RZDRDZ != null)
            System.out.println(Passive_Loops.Geometries[i1].RZDRDZ);
        else
            System.out.println("Empty");
        System.out.println("");    
   
        }
      }
    

  System.out.println("Currents ");
  
        if(Passive_Loops.Currents != null)
            System.out.println(Passive_Loops.Currents);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Timebase ");
  
         if(Passive_Loops.Timebase != null)
            System.out.println(Passive_Loops.Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Vacuum_Model ");
  

  System.out.println("Names ");
  
        if(Vacuum_Model.Names != null)
            System.out.println(Vacuum_Model.Names);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Inductance_Matrix ");
  
        if(Vacuum_Model.Inductance_Matrix != null)
            System.out.println(Vacuum_Model.Inductance_Matrix);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Resistance_Vector ");
  
         if(Vacuum_Model.Resistance_Vector != null)
            System.out.println(Vacuum_Model.Resistance_Vector);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Code_Parameters ");
  

  System.out.println("Code_Name ");
  
        if(Code_Parameters.Code_Name!= null)
            System.out.println(Code_Parameters.Code_Name);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Code_Version ");
  
        if(Code_Parameters.Code_Version!= null)
            System.out.println(Code_Parameters.Code_Version);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Parameters ");
  
        if(Code_Parameters.Parameters != null)
            System.out.println(Code_Parameters.Parameters);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Output_Diagnostics ");
  
        if(Code_Parameters.Output_Diagnostics != null)
            System.out.println(Code_Parameters.Output_Diagnostics);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Output_Flag ");
  
        if(Code_Parameters.Output_Flag != null)
            System.out.println(Code_Parameters.Output_Flag);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Timebase ");
  
         if(Timebase != null)
            System.out.println(Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   
    System.out.println("******************");
  }
}


public static class SDN
{
    
      public static class IDS_PropertiesClass {
      
      public String Status_of;
    
      public String Comment_of;
    
      public String Creator_of;
    
      public String Date_of;
    
      public String Source_of;
    
      public String Reference_of;
    
      public int Homogeneous_Timebase = EMPTY_INT;
    
      public int cocos = EMPTY_INT;
    
      }
      public IDS_PropertiesClass IDS_Properties = new IDS_PropertiesClass();
    
      public static class Allocatable_SignalsClass {
      
      public Vect1DString Names;
    
      public Vect1DString Definitions ;
    
      public Vect1DInt Ip_Normalise;
    
      }
      public Allocatable_SignalsClass Allocatable_Signals = new Allocatable_SignalsClass();
    
      public Vect1DString Allocated_Signals;
    
      public Vect1DDouble Timebase;
    
      public Vect2DDouble  Signals;
    
      public int N_Topic_List = EMPTY_INT;
    
      public static class Topic_ListClass {
      
      public Vect1DString Names;
    
      public Vect1DInt Indices;
    
      }
      public Topic_ListClass Topic_List[];
    

 /**
 * Method copy SDN copies a full IDS between two databases.
 * @param srcIdx The index of the source database, returned by imas.open()
 * @param srcOccur The occurence of the IDS in the source database.
 * @param destIdx The index of the destination database, returned by imas.open()
 * @param destOccur The occurence of the IDS in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copy(int srcIdx, int srcOccur, int destIdx, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyIds(srcIdx, destIdx, "SDN", srcOccur, destOccur);
    }

 /**
 * Method copyEnv SDN copies a full IDS between two databases.
 * @param srcUser User of the source database
 * @param srcTokamak Tokamak of the source database
 * @param srcVersion Version of the source database
 * @param srcShot Shot number of the source database
 * @param srcRun Run number of the source database
 * @param srcOccur The occurence of the IDS in the source database.
 * @param destUser User of the destination database
 * @param destTokamak Tokamak of the destination database
 * @param destVersion Version of the destination database
 * @param destShot Shot number of the destination database
 * @param destRun Run number of the destination database
 * @param destOccur The occurence of the IDS in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copyEnv(String srcUser, String srcTokamak, String srcVersion, int srcShot, int srcRun, int srcOccur, String destUser, String destTokamak, String destVersion, int destShot, int destRun, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyIdsEnv(srcTokamak, srcVersion, srcUser, srcShot, srcRun, srcOccur, destTokamak, destVersion, destUser, destShot, destRun, destOccur, "SDN");
    }

/**
 * Method put stores a non timed SDN IDSs in the open database. 
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The passed SDN ids.
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void put(int expIdx, String path, SDN ids)  throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
	  Vect5DDouble  vect5DDouble;
	  Vect6DDouble  vect6DDouble;
	  Vect7DDouble  vect7DDouble;
          String        timebasepath;
          String        ual_debug = System.getenv("ual_debug");

          UALLowLevel.beginIDSPut(expIdx, path);
          
// Put IDS_Properties/Status_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Status_of", ids.IDS_Properties.Status_of);

// Put IDS_Properties/Comment_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Comment_of", ids.IDS_Properties.Comment_of);

// Put IDS_Properties/Creator_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Creator_of", ids.IDS_Properties.Creator_of);

// Put IDS_Properties/Date_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Date_of", ids.IDS_Properties.Date_of);

// Put IDS_Properties/Source_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Source_of", ids.IDS_Properties.Source_of);

// Put IDS_Properties/Reference_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Reference_of", ids.IDS_Properties.Reference_of);

// Put IDS_Properties/Homogeneous_Timebase
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase", ids.IDS_Properties.Homogeneous_Timebase);

// Put IDS_Properties/cocos
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/cocos", ids.IDS_Properties.cocos);

// Put Allocatable_Signals/Names   
          UALLowLevel.putVect1DString(expIdx, path, "Allocatable_Signals" + "/Names", "", ids.Allocatable_Signals.Names, false);
   		
// Put Allocatable_Signals/Definitions    
          UALLowLevel.putVect1DString(expIdx, path, "Allocatable_Signals" + "/Definitions ", "", ids.Allocatable_Signals.Definitions , false);
   		
// Put Allocatable_Signals/Ip_Normalise   
          UALLowLevel.putVect1DInt(expIdx, path, "Allocatable_Signals" + "/Ip_Normalise", "", ids.Allocatable_Signals.Ip_Normalise, false);
   		
// Put Allocated_Signals
          UALLowLevel.putVect1DString(expIdx,path, "Allocated_Signals", "", ids.Allocated_Signals, false);
   		
// Put Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect1DDouble(expIdx,path, "Timebase", timebasepath.trim(), ids.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Signals
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path,  ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect2DDouble(expIdx,path, "Signals", timebasepath.trim(), ids.Signals, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put N_Topic_List
          UALLowLevel.putInt(expIdx,path, "N_Topic_List",  ids.N_Topic_List);

// Put Topic_List
          if (ids.Topic_List != null) {
            UALLowLevel.putInt(expIdx,path, "Topic_List/Shape_of",
       		ids.Topic_List.length);
            for (int iTopic_List = 0; iTopic_List<ids.Topic_List.length; iTopic_List++){
      
// Put Topic_List/Names   
          UALLowLevel.putVect1DString(expIdx, path, "Topic_List/" + Integer.toString(iTopic_List+1).trim() + "/Names", "", ids.Topic_List[iTopic_List].Names, false);
   		
// Put Topic_List/Indices   
          UALLowLevel.putVect1DInt(expIdx, path, "Topic_List/" + Integer.toString(iTopic_List+1).trim() + "/Indices", "", ids.Topic_List[iTopic_List].Indices, false);
   		
            }
          }

          UALLowLevel.endIDSPut(expIdx, path);
    }

 /**
 * Method putNonTimed stores the non time dependent fields of a (timed) SDN IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The timed SDN IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void putNonTimed(int expIdx, String path, SDN ids) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
          Vect5DDouble  vect5DDouble;
          Vect6DDouble  vect6DDouble;
          Vect7DDouble  vect7DDouble;
          String        ual_debug = System.getenv("ual_debug");
          
          delete(expIdx, path);
          UALLowLevel.beginIDSPutNonTimed(expIdx, path);
          
// Put IDS_Properties/Status_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Status_of", ids.IDS_Properties.Status_of);

// Put IDS_Properties/Comment_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Comment_of", ids.IDS_Properties.Comment_of);

// Put IDS_Properties/Creator_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Creator_of", ids.IDS_Properties.Creator_of);

// Put IDS_Properties/Date_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Date_of", ids.IDS_Properties.Date_of);

// Put IDS_Properties/Source_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Source_of", ids.IDS_Properties.Source_of);

// Put IDS_Properties/Reference_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Reference_of", ids.IDS_Properties.Reference_of);

// Put IDS_Properties/Homogeneous_Timebase
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase", ids.IDS_Properties.Homogeneous_Timebase);

// Put IDS_Properties/cocos
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/cocos", ids.IDS_Properties.cocos);

// Put Allocatable_Signals/Names   
          UALLowLevel.putVect1DString(expIdx, path, "Allocatable_Signals" + "/Names", "", ids.Allocatable_Signals.Names, false);
   		
// Put Allocatable_Signals/Definitions    
          UALLowLevel.putVect1DString(expIdx, path, "Allocatable_Signals" + "/Definitions ", "", ids.Allocatable_Signals.Definitions , false);
   		
// Put Allocatable_Signals/Ip_Normalise   
          UALLowLevel.putVect1DInt(expIdx, path, "Allocatable_Signals" + "/Ip_Normalise", "", ids.Allocatable_Signals.Ip_Normalise, false);
   		
// Put Allocated_Signals
          UALLowLevel.putVect1DString(expIdx,path, "Allocated_Signals", "", ids.Allocated_Signals, false);
   		
// Put N_Topic_List
          UALLowLevel.putInt(expIdx,path, "N_Topic_List",  ids.N_Topic_List);

// Put Topic_List
          if (ids.Topic_List != null) {
            UALLowLevel.putInt(expIdx,path, "Topic_List/Shape_of",
       		ids.Topic_List.length);
            for (int iTopic_List = 0; iTopic_List<ids.Topic_List.length; iTopic_List++){
      
// Put Topic_List/Names   
          UALLowLevel.putVect1DString(expIdx, path, "Topic_List/" + Integer.toString(iTopic_List+1).trim() + "/Names", "", ids.Topic_List[iTopic_List].Names, false);
   		
// Put Topic_List/Indices   
          UALLowLevel.putVect1DInt(expIdx, path, "Topic_List/" + Integer.toString(iTopic_List+1).trim() + "/Indices", "", ids.Topic_List[iTopic_List].Indices, false);
   		
            }
          }

          UALLowLevel.endIDSPutNonTimed(expIdx, path);
    }

    
/**
 * Method putSlice appends a (timed) SDN IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param ids The timed SDN IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void putSlice(int expIdx, String path, SDN ids) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
          Vect5DDouble  vect5DDouble;
          Vect6DDouble  vect6DDouble;
          Vect7DDouble  vect7DDouble;
          String        timebasepath;
          String        ual_debug = System.getenv("ual_debug");
          
    	  if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
            System.out.println("ERROR : the PUT_SLICE routine works only for homogeneous timebase IDS");
            return;
          }
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutSlice(expIdx, path);
          
// Put Slice Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx,path, "Timebase", timebasepath.trim(), ids.Timebase, ids.Timebase.getElementAt(0));
		
// Put Slice Signals
          UALLowLevel.putVect2DDoubleSlice(expIdx,path, "Signals", timebasepath.trim(), ids.Signals, ids.Timebase.getElementAt(0));
		
// Put Topic_List
          if (ids.Topic_List != null) {
            UALLowLevel.putInt(expIdx,path, "Topic_List/Shape_of",
       		ids.Topic_List.length);
            for (int iTopic_List = 0; iTopic_List<ids.Topic_List.length; iTopic_List++){
      
            }
          }

          UALLowLevel.endIDSPutSlice(expIdx, path);
    }
    

    

/**
 * Method putdb stores a non timed SDN IDSs in the open database only if
 *  datainto.isref == 1. In any case it calls afterwards UALLowLevel.putIdsDb() in order to update catalogue
 * @param expIdx The index to the database, returned by imas.open()
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected ids. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @param ids The  SDN IDSs
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
/*
    public static void putdb(int expIdx, String user, String machine, int shot, int run, 
    	String path, int occurrence, SDN ids) throws UALException
    {
    	String actPath;
        if(occurrence > 0)
	    actPath = path+"/"+occurrence;
	else
	    actPath = path;
	if(ids.IDS_Properties.isref == 1)
	{
	    put(expIdx, actPath, ids);
	    UALLowLevel.putIdsDb(user, machine, shot, run, path, occurrence, 1,
	    	user, machine, shot, run, occurrence);
	}
	else //isref == 2
	{
	    UALLowLevel.putIdsDb(user, machine, shot, run, path, occurrence, 2,
	    	ids.IDS_Properties.whatref.user,  ids.IDS_Properties.whatref.machine,
		ids.IDS_Properties.whatref.shot, ids.IDS_Properties.whatref.run, 
		ids.IDS_Properties.whatref.occurrence);
	
	}
    }
*/

/**
 * Method getdb retrieves the non timed SDN IDSs in the open database, taking the correct reference.
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the SDN IDS . Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if float or double and empty String is string.
 **/
    public static SDN getdb(String user, String machine, int shot, int run, String version, String path, int occurrence) throws UALException
    {
//		int idx = UALLowLevel.openDb(user, machine, shot, run, path, occurrence);
		int idx = openEnv("ids", shot, run, user, machine, version);
		if (occurrence != 0)
			path = path + "/" + occurrence;
		SDN retIdss = null;
                try {
                        retIdss = get(idx, path);
                }
                catch(Exception exc) {
                        retIdss = new SDN ();
                        System.out.println("Return a new SDN empty IDS after catching exception: "+exc);
                }
		close(idx);
		return retIdss;
    }

/*
    public static void fillIdsRef(SDN ids, int isRef, String refUser, String refMachine, int refShot, int refRun, int refOccurrence)
    {
        ids.IDS_Properties.isref = isRef;
        if(isRef != 1)
        {
            ids.IDS_Properties.whatref.user = refUser;
                ids.IDS_Properties.whatref.machine = refMachine;
                ids.IDS_Properties.whatref.shot = refShot;
                ids.IDS_Properties.whatref.run = refRun;
                ids.IDS_Properties.whatref.occurrence = refOccurrence;
        }
    } 
*/
            

    public static SDN  get(int expIdx, String path)  throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");

      SDN ids = new SDN (); 
      UALLowLevel.beginIDSGet(expIdx, path, false);
      
          try {
            ids.IDS_Properties.Status_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Status_of");
          } catch(Exception exc){ids.IDS_Properties.Status_of = null;}
		
          try {
            ids.IDS_Properties.Comment_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Comment_of");
          } catch(Exception exc){ids.IDS_Properties.Comment_of = null;}
		
          try {
            ids.IDS_Properties.Creator_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Creator_of");
          } catch(Exception exc){ids.IDS_Properties.Creator_of = null;}
		
          try {
            ids.IDS_Properties.Date_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Date_of");
          } catch(Exception exc){ids.IDS_Properties.Date_of = null;}
		
          try {
            ids.IDS_Properties.Source_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Source_of");
          } catch(Exception exc){ids.IDS_Properties.Source_of = null;}
		
          try {
            ids.IDS_Properties.Reference_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Reference_of");
          } catch(Exception exc){ids.IDS_Properties.Reference_of = null;}
		
          try {
            ids.IDS_Properties.Homogeneous_Timebase = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase");
          } catch(Exception exc){ids.IDS_Properties.Homogeneous_Timebase = EMPTY_INT;}
		
          try {
            ids.IDS_Properties.cocos = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/cocos");
          } catch(Exception exc){ids.IDS_Properties.cocos = EMPTY_INT;}
		
          try {
            ids.Allocatable_Signals.Names = UALLowLevel.getVect1DString(expIdx, path, "Allocatable_Signals" + "/Names");
          } catch(Exception exc){ids.Allocatable_Signals.Names = null;}
		
          try {
            ids.Allocatable_Signals.Definitions  = UALLowLevel.getVect1DString(expIdx, path, "Allocatable_Signals" + "/Definitions ");
          } catch(Exception exc){ids.Allocatable_Signals.Definitions  = null;}
		
          try {
            ids.Allocatable_Signals.Ip_Normalise = UALLowLevel.getVect1DInt(expIdx, path, "Allocatable_Signals" + "/Ip_Normalise");
          } catch(Exception exc){ids.Allocatable_Signals.Ip_Normalise = null;}
		
          try {
            ids.Allocated_Signals = UALLowLevel.getVect1DString(expIdx, path, "Allocated_Signals");
          } catch(Exception exc){ids.Allocated_Signals = null;}
		
          try {
            ids.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Timebase");
          } catch(Exception exc){ids.Timebase = null;}
		
          try {
            ids.Signals = UALLowLevel.getVect2DDouble(expIdx, path, "Signals");
          } catch(Exception exc){ids.Signals = null;}
		
          try {
            ids.N_Topic_List = UALLowLevel.getInt(expIdx, path, "N_Topic_List");
          } catch(Exception exc){ids.N_Topic_List = EMPTY_INT;}
		
// Get Array of Structures Topic_List
          try {
            ids.Topic_List = new Topic_ListClass[UALLowLevel.getInt(expIdx,path, "Topic_List/Shape_of")];
            for (int iTopic_List = 0; iTopic_List<ids.Topic_List.length; iTopic_List++){
              ids.Topic_List[iTopic_List] = new Topic_ListClass();

          try {
            ids.Topic_List[iTopic_List].Names = UALLowLevel.getVect1DString(expIdx, path, "Topic_List/" + Integer.toString(iTopic_List+1).trim() + "/Names");
          } catch(Exception exc){ids.Topic_List[iTopic_List].Names = null;}
		
          try {
            ids.Topic_List[iTopic_List].Indices = UALLowLevel.getVect1DInt(expIdx, path, "Topic_List/" + Integer.toString(iTopic_List+1).trim() + "/Indices");
          } catch(Exception exc){ids.Topic_List[iTopic_List].Indices = null;}
		
            }
          } catch(Exception exc){ids.Topic_List = null;}

      UALLowLevel.endIDSGet(expIdx, path);
      return ids;
    }

 /**
 * Method getSlice retrieves the  SDN IDS in the open database corresponding to the passed time, based on the selectted interpolation mode.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param time The retrieval time.
 * @param interpolMode Defines the selected interpolation. Allowed values are imas.INTERPOLATION, imas.CLOSEST_SAMPLE,
 * imas.PREVIOUS_SAMPLE
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the selected SDN ids. Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if double and empty String is string.
 **/
    public static SDN  getSlice(int expIdx, String path, double time, int interpolMode) throws UALException
    {
      String        timebasepath;
      String        ual_debug = System.getenv("ual_debug");
      double        retTime;
      SDN ids = new SDN (); 
      retTime = UALLowLevel.beginIDSGetSlice(expIdx, path, time);

          try {
            ids.IDS_Properties.Status_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Status_of");
          } catch(Exception exc){ids.IDS_Properties.Status_of = null;}
		
          try {
            ids.IDS_Properties.Comment_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Comment_of");
          } catch(Exception exc){ids.IDS_Properties.Comment_of = null;}
		
          try {
            ids.IDS_Properties.Creator_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Creator_of");
          } catch(Exception exc){ids.IDS_Properties.Creator_of = null;}
		
          try {
            ids.IDS_Properties.Date_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Date_of");
          } catch(Exception exc){ids.IDS_Properties.Date_of = null;}
		
          try {
            ids.IDS_Properties.Source_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Source_of");
          } catch(Exception exc){ids.IDS_Properties.Source_of = null;}
		
          try {
            ids.IDS_Properties.Reference_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Reference_of");
          } catch(Exception exc){ids.IDS_Properties.Reference_of = null;}
		
          try {
            ids.IDS_Properties.Homogeneous_Timebase = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase");
          } catch(Exception exc){ids.IDS_Properties.Homogeneous_Timebase = EMPTY_INT;}
		
          try {
            ids.IDS_Properties.cocos = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/cocos");
          } catch(Exception exc){ids.IDS_Properties.cocos = EMPTY_INT;}
		
          try {
            ids.Allocatable_Signals.Names = UALLowLevel.getVect1DString(expIdx, path, "Allocatable_Signals" + "/Names");
          } catch(Exception exc){ids.Allocatable_Signals.Names = null;}
		
          try {
            ids.Allocatable_Signals.Definitions  = UALLowLevel.getVect1DString(expIdx, path, "Allocatable_Signals" + "/Definitions ");
          } catch(Exception exc){ids.Allocatable_Signals.Definitions  = null;}
		
          try {
            ids.Allocatable_Signals.Ip_Normalise = UALLowLevel.getVect1DInt(expIdx, path, "Allocatable_Signals" + "/Ip_Normalise");
          } catch(Exception exc){ids.Allocatable_Signals.Ip_Normalise = null;}
		
          try {
            ids.Allocated_Signals = UALLowLevel.getVect1DString(expIdx, path, "Allocated_Signals");
          } catch(Exception exc){ids.Allocated_Signals = null;}
		
// Get Slice Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Timebase = new Vect1DDouble(1);
              ids.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "/Timebase", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Timebase = null;}
		
// Get Slice Signals
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Signals");
	    if (obj.getElementAt(0) > 0) {
              ids.Signals = new Vect2DDouble(obj.getElementAt(0),1);
              ids.Signals.setElementAt(0, UALLowLevel.getVect1DDoubleSlice(expIdx, path, "/Signals", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Signals = null;}
		
          try {
            ids.N_Topic_List = UALLowLevel.getInt(expIdx, path, "N_Topic_List");
          } catch(Exception exc){ids.N_Topic_List = EMPTY_INT;}
		
// Get Array of Structures Topic_List
          try {
            ids.Topic_List = new Topic_ListClass[UALLowLevel.getInt(expIdx,path, "Topic_List/Shape_of")];
            for (int iTopic_List = 0; iTopic_List<ids.Topic_List.length; iTopic_List++){
              ids.Topic_List[iTopic_List] = new Topic_ListClass();
			
          try {
            ids.Topic_List[iTopic_List].Names = UALLowLevel.getVect1DString(expIdx, path, "Topic_List/" + Integer.toString(iTopic_List+1).trim() + "/Names");
          } catch(Exception exc){ids.Topic_List[iTopic_List].Names = null;}
		
          try {
            ids.Topic_List[iTopic_List].Indices = UALLowLevel.getVect1DInt(expIdx, path, "Topic_List/" + Integer.toString(iTopic_List+1).trim() + "/Indices");
          } catch(Exception exc){ids.Topic_List[iTopic_List].Indices = null;}
		
            }
          } catch(Exception exc){ids.Topic_List = null;}
		
      UALLowLevel.endIDSGetSlice(expIdx, path);
      return ids;
    }
    
 /**
 * Method delete removes all the data associated with a SDN IDS in the open database.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void delete(int expIdx, String path) throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");
      
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Status_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Comment_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Creator_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Date_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Source_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Reference_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Homogeneous_Timebase");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/cocos");          
      UALLowLevel.deleteData(expIdx,path,"Allocatable_Signals" + "/Names");          
      UALLowLevel.deleteData(expIdx,path,"Allocatable_Signals" + "/Definitions ");          
      UALLowLevel.deleteData(expIdx,path,"Allocatable_Signals" + "/Ip_Normalise");          
      UALLowLevel.deleteData(expIdx, path, "Allocated_Signals");  
      UALLowLevel.deleteData(expIdx, path, "Timebase");  
      UALLowLevel.deleteData(expIdx, path, "Signals");  
      UALLowLevel.deleteData(expIdx, path, "N_Topic_List");  
      for (int iTopic_List = 0; iTopic_List<30; iTopic_List++){
      
      UALLowLevel.deleteData(expIdx,path,"/Topic_List/" + Integer.toString(iTopic_List+1).trim() + "/Names");          
      UALLowLevel.deleteData(expIdx,path,"/Topic_List/" + Integer.toString(iTopic_List+1).trim() + "/Indices");          
      }
   
    }


     
 /**
 * Method dump is used for debugging and prints the content of the object
 **/
  public void dump()
  {
    System.out.println("***** SDN *****");
    

  System.out.println("IDS_Properties ");
  

  System.out.println("Status_of ");
  
        if(IDS_Properties.Status_of!= null)
            System.out.println(IDS_Properties.Status_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Comment_of ");
  
        if(IDS_Properties.Comment_of!= null)
            System.out.println(IDS_Properties.Comment_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Creator_of ");
  
        if(IDS_Properties.Creator_of!= null)
            System.out.println(IDS_Properties.Creator_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Date_of ");
  
        if(IDS_Properties.Date_of!= null)
            System.out.println(IDS_Properties.Date_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Source_of ");
  
        if(IDS_Properties.Source_of!= null)
            System.out.println(IDS_Properties.Source_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Reference_of ");
  
        if(IDS_Properties.Reference_of!= null)
            System.out.println(IDS_Properties.Reference_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Homogeneous_Timebase ");
  
        if(IDS_Properties.Homogeneous_Timebase != EMPTY_INT)
            System.out.println(IDS_Properties.Homogeneous_Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("cocos ");
  
        if(IDS_Properties.cocos != EMPTY_INT)
            System.out.println(IDS_Properties.cocos);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Allocatable_Signals ");
  

  System.out.println("Names ");
  
        if(Allocatable_Signals.Names != null)
            System.out.println(Allocatable_Signals.Names);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Definitions  ");
  
        if(Allocatable_Signals.Definitions  != null)
            System.out.println(Allocatable_Signals.Definitions );
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Ip_Normalise ");
  
        if(Allocatable_Signals.Ip_Normalise != null)
            System.out.println(Allocatable_Signals.Ip_Normalise);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Allocated_Signals ");
  
        if(Allocated_Signals != null)
            System.out.println(Allocated_Signals);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Timebase ");
  
         if(Timebase != null)
            System.out.println(Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Signals ");
  
        if(Signals != null)
            System.out.println(Signals);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("N_Topic_List ");
  
        if(N_Topic_List != EMPTY_INT)
            System.out.println(N_Topic_List);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Topic_List ");
  
      if (Topic_List != null) {
        for (int i1 = 0; i1 < Topic_List.length; i1++) {
          

  System.out.println("Names ");
  
        if(Topic_List[i1].Names != null)
            System.out.println(Topic_List[i1].Names);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Indices ");
  
        if(Topic_List[i1].Indices != null)
            System.out.println(Topic_List[i1].Indices);
        else
            System.out.println("Empty");
        System.out.println("");    
    
        }
      }
    
    System.out.println("******************");
  }
}


public static class TF
{
    
      public static class IDS_PropertiesClass {
      
      public String Status_of;
    
      public String Comment_of;
    
      public String Creator_of;
    
      public String Date_of;
    
      public String Source_of;
    
      public String Reference_of;
    
      public int Homogeneous_Timebase = EMPTY_INT;
    
      public int cocos = EMPTY_INT;
    
      }
      public IDS_PropertiesClass IDS_Properties = new IDS_PropertiesClass();
    
      public int N_Turns = EMPTY_INT;
    
      public int N_Coils = EMPTY_INT;
    
      public static class CurrentClass {
      
      public Vect1DDouble Data;
    
      public Vect1DDouble Timebase;
    
      }
      public CurrentClass Current = new CurrentClass();
    
      public static class VoltageClass {
      
      public Vect1DDouble Data;
    
      public Vect1DDouble Timebase;
    
      }
      public VoltageClass Voltage = new VoltageClass();
    
      public static class B_Tor_Vacuum_RClass {
      
      public Vect1DDouble Data;
    
      public Vect1DDouble Timebase;
    
      }
      public B_Tor_Vacuum_RClass B_Tor_Vacuum_R = new B_Tor_Vacuum_RClass();
    
      public Vect1DDouble Timebase;
    

 /**
 * Method copy TF copies a full IDS between two databases.
 * @param srcIdx The index of the source database, returned by imas.open()
 * @param srcOccur The occurence of the IDS in the source database.
 * @param destIdx The index of the destination database, returned by imas.open()
 * @param destOccur The occurence of the IDS in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copy(int srcIdx, int srcOccur, int destIdx, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyIds(srcIdx, destIdx, "TF", srcOccur, destOccur);
    }

 /**
 * Method copyEnv TF copies a full IDS between two databases.
 * @param srcUser User of the source database
 * @param srcTokamak Tokamak of the source database
 * @param srcVersion Version of the source database
 * @param srcShot Shot number of the source database
 * @param srcRun Run number of the source database
 * @param srcOccur The occurence of the IDS in the source database.
 * @param destUser User of the destination database
 * @param destTokamak Tokamak of the destination database
 * @param destVersion Version of the destination database
 * @param destShot Shot number of the destination database
 * @param destRun Run number of the destination database
 * @param destOccur The occurence of the IDS in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copyEnv(String srcUser, String srcTokamak, String srcVersion, int srcShot, int srcRun, int srcOccur, String destUser, String destTokamak, String destVersion, int destShot, int destRun, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyIdsEnv(srcTokamak, srcVersion, srcUser, srcShot, srcRun, srcOccur, destTokamak, destVersion, destUser, destShot, destRun, destOccur, "TF");
    }

/**
 * Method put stores a non timed TF IDSs in the open database. 
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The passed TF ids.
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void put(int expIdx, String path, TF ids)  throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
	  Vect5DDouble  vect5DDouble;
	  Vect6DDouble  vect6DDouble;
	  Vect7DDouble  vect7DDouble;
          String        timebasepath;
          String        ual_debug = System.getenv("ual_debug");

          UALLowLevel.beginIDSPut(expIdx, path);
          
// Put IDS_Properties/Status_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Status_of", ids.IDS_Properties.Status_of);

// Put IDS_Properties/Comment_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Comment_of", ids.IDS_Properties.Comment_of);

// Put IDS_Properties/Creator_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Creator_of", ids.IDS_Properties.Creator_of);

// Put IDS_Properties/Date_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Date_of", ids.IDS_Properties.Date_of);

// Put IDS_Properties/Source_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Source_of", ids.IDS_Properties.Source_of);

// Put IDS_Properties/Reference_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Reference_of", ids.IDS_Properties.Reference_of);

// Put IDS_Properties/Homogeneous_Timebase
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase", ids.IDS_Properties.Homogeneous_Timebase);

// Put IDS_Properties/cocos
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/cocos", ids.IDS_Properties.cocos);

// Put N_Turns
          UALLowLevel.putInt(expIdx,path, "N_Turns",  ids.N_Turns);

// Put N_Coils
          UALLowLevel.putInt(expIdx,path, "N_Coils",  ids.N_Coils);

// Put Current/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Current" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Current.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Current" + "/Data", timebasepath.trim(), ids.Current.Data, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Current/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Current" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Current.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Current" + "/Timebase", timebasepath.trim(), ids.Current.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Voltage/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Voltage" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Voltage.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Voltage" + "/Data", timebasepath.trim(), ids.Voltage.Data, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Voltage/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Voltage" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Voltage.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "Voltage" + "/Timebase", timebasepath.trim(), ids.Voltage.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put B_Tor_Vacuum_R/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="B_Tor_Vacuum_R" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.B_Tor_Vacuum_R.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "B_Tor_Vacuum_R" + "/Data", timebasepath.trim(), ids.B_Tor_Vacuum_R.Data, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put B_Tor_Vacuum_R/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="B_Tor_Vacuum_R" + "/Timebase" ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.B_Tor_Vacuum_R.Timebase);
       				
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			   
          UALLowLevel.putVect1DDouble(expIdx, path, "B_Tor_Vacuum_R" + "/Timebase", timebasepath.trim(), ids.B_Tor_Vacuum_R.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
// Put Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			
          UALLowLevel.putVect1DDouble(expIdx,path, "Timebase", timebasepath.trim(), ids.Timebase, true);
   		
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		
          UALLowLevel.endIDSPut(expIdx, path);
    }

 /**
 * Method putNonTimed stores the non time dependent fields of a (timed) TF IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The timed TF IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void putNonTimed(int expIdx, String path, TF ids) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
          Vect5DDouble  vect5DDouble;
          Vect6DDouble  vect6DDouble;
          Vect7DDouble  vect7DDouble;
          String        ual_debug = System.getenv("ual_debug");
          
          delete(expIdx, path);
          UALLowLevel.beginIDSPutNonTimed(expIdx, path);
          
// Put IDS_Properties/Status_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Status_of", ids.IDS_Properties.Status_of);

// Put IDS_Properties/Comment_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Comment_of", ids.IDS_Properties.Comment_of);

// Put IDS_Properties/Creator_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Creator_of", ids.IDS_Properties.Creator_of);

// Put IDS_Properties/Date_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Date_of", ids.IDS_Properties.Date_of);

// Put IDS_Properties/Source_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Source_of", ids.IDS_Properties.Source_of);

// Put IDS_Properties/Reference_of
          UALLowLevel.putString(expIdx, path, "IDS_Properties" + "/Reference_of", ids.IDS_Properties.Reference_of);

// Put IDS_Properties/Homogeneous_Timebase
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase", ids.IDS_Properties.Homogeneous_Timebase);

// Put IDS_Properties/cocos
          UALLowLevel.putInt(expIdx, path, "IDS_Properties" + "/cocos", ids.IDS_Properties.cocos);

// Put N_Turns
          UALLowLevel.putInt(expIdx,path, "N_Turns",  ids.N_Turns);

// Put N_Coils
          UALLowLevel.putInt(expIdx,path, "N_Coils",  ids.N_Coils);

          UALLowLevel.endIDSPutNonTimed(expIdx, path);
    }

    
/**
 * Method putSlice appends a (timed) TF IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param ids The timed TF IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void putSlice(int expIdx, String path, TF ids) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean vect1DBoolean;
          Vect1DInt     vect1DInt;
          Vect2DInt     vect2DInt;
          Vect3DInt     vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
          Vect5DDouble  vect5DDouble;
          Vect6DDouble  vect6DDouble;
          Vect7DDouble  vect7DDouble;
          String        timebasepath;
          String        ual_debug = System.getenv("ual_debug");
          
    	  if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
            System.out.println("ERROR : the PUT_SLICE routine works only for homogeneous timebase IDS");
            return;
          }
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutSlice(expIdx, path);
          
// Put Slice Current/Data
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Current" + "/Data", timebasepath.trim(), ids.Current.Data, ids.Timebase.getElementAt(0));
		
// Put Slice Current/Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Current" + "/Timebase", timebasepath.trim(), ids.Current.Timebase, ids.Timebase.getElementAt(0));
		
// Put Slice Voltage/Data
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Voltage" + "/Data", timebasepath.trim(), ids.Voltage.Data, ids.Timebase.getElementAt(0));
		
// Put Slice Voltage/Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "Voltage" + "/Timebase", timebasepath.trim(), ids.Voltage.Timebase, ids.Timebase.getElementAt(0));
		
// Put Slice B_Tor_Vacuum_R/Data
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "B_Tor_Vacuum_R" + "/Data", timebasepath.trim(), ids.B_Tor_Vacuum_R.Data, ids.Timebase.getElementAt(0));
		
// Put Slice B_Tor_Vacuum_R/Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx, path, "B_Tor_Vacuum_R" + "/Timebase", timebasepath.trim(), ids.B_Tor_Vacuum_R.Timebase, ids.Timebase.getElementAt(0));
		
// Put Slice Timebase
          UALLowLevel.putVect1DDoubleSlice(expIdx,path, "Timebase", timebasepath.trim(), ids.Timebase, ids.Timebase.getElementAt(0));
		
          UALLowLevel.endIDSPutSlice(expIdx, path);
    }
    

    

/**
 * Method putdb stores a non timed TF IDSs in the open database only if
 *  datainto.isref == 1. In any case it calls afterwards UALLowLevel.putIdsDb() in order to update catalogue
 * @param expIdx The index to the database, returned by imas.open()
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected ids. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @param ids The  TF IDSs
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
/*
    public static void putdb(int expIdx, String user, String machine, int shot, int run, 
    	String path, int occurrence, TF ids) throws UALException
    {
    	String actPath;
        if(occurrence > 0)
	    actPath = path+"/"+occurrence;
	else
	    actPath = path;
	if(ids.IDS_Properties.isref == 1)
	{
	    put(expIdx, actPath, ids);
	    UALLowLevel.putIdsDb(user, machine, shot, run, path, occurrence, 1,
	    	user, machine, shot, run, occurrence);
	}
	else //isref == 2
	{
	    UALLowLevel.putIdsDb(user, machine, shot, run, path, occurrence, 2,
	    	ids.IDS_Properties.whatref.user,  ids.IDS_Properties.whatref.machine,
		ids.IDS_Properties.whatref.shot, ids.IDS_Properties.whatref.run, 
		ids.IDS_Properties.whatref.occurrence);
	
	}
    }
*/

/**
 * Method getdb retrieves the non timed TF IDSs in the open database, taking the correct reference.
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the TF IDS . Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if float or double and empty String is string.
 **/
    public static TF getdb(String user, String machine, int shot, int run, String version, String path, int occurrence) throws UALException
    {
//		int idx = UALLowLevel.openDb(user, machine, shot, run, path, occurrence);
		int idx = openEnv("ids", shot, run, user, machine, version);
		if (occurrence != 0)
			path = path + "/" + occurrence;
		TF retIdss = null;
                try {
                        retIdss = get(idx, path);
                }
                catch(Exception exc) {
                        retIdss = new TF ();
                        System.out.println("Return a new TF empty IDS after catching exception: "+exc);
                }
		close(idx);
		return retIdss;
    }

/*
    public static void fillIdsRef(TF ids, int isRef, String refUser, String refMachine, int refShot, int refRun, int refOccurrence)
    {
        ids.IDS_Properties.isref = isRef;
        if(isRef != 1)
        {
            ids.IDS_Properties.whatref.user = refUser;
                ids.IDS_Properties.whatref.machine = refMachine;
                ids.IDS_Properties.whatref.shot = refShot;
                ids.IDS_Properties.whatref.run = refRun;
                ids.IDS_Properties.whatref.occurrence = refOccurrence;
        }
    } 
*/
            

    public static TF  get(int expIdx, String path)  throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");

      TF ids = new TF (); 
      UALLowLevel.beginIDSGet(expIdx, path, false);
      
          try {
            ids.IDS_Properties.Status_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Status_of");
          } catch(Exception exc){ids.IDS_Properties.Status_of = null;}
		
          try {
            ids.IDS_Properties.Comment_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Comment_of");
          } catch(Exception exc){ids.IDS_Properties.Comment_of = null;}
		
          try {
            ids.IDS_Properties.Creator_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Creator_of");
          } catch(Exception exc){ids.IDS_Properties.Creator_of = null;}
		
          try {
            ids.IDS_Properties.Date_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Date_of");
          } catch(Exception exc){ids.IDS_Properties.Date_of = null;}
		
          try {
            ids.IDS_Properties.Source_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Source_of");
          } catch(Exception exc){ids.IDS_Properties.Source_of = null;}
		
          try {
            ids.IDS_Properties.Reference_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Reference_of");
          } catch(Exception exc){ids.IDS_Properties.Reference_of = null;}
		
          try {
            ids.IDS_Properties.Homogeneous_Timebase = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase");
          } catch(Exception exc){ids.IDS_Properties.Homogeneous_Timebase = EMPTY_INT;}
		
          try {
            ids.IDS_Properties.cocos = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/cocos");
          } catch(Exception exc){ids.IDS_Properties.cocos = EMPTY_INT;}
		
          try {
            ids.N_Turns = UALLowLevel.getInt(expIdx, path, "N_Turns");
          } catch(Exception exc){ids.N_Turns = EMPTY_INT;}
		
          try {
            ids.N_Coils = UALLowLevel.getInt(expIdx, path, "N_Coils");
          } catch(Exception exc){ids.N_Coils = EMPTY_INT;}
		
          try {
            ids.Current.Data = UALLowLevel.getVect1DDouble(expIdx, path, "Current" + "/Data");
          } catch(Exception exc){ids.Current.Data = null;}
		
          try {
            ids.Current.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Current" + "/Timebase");
          } catch(Exception exc){ids.Current.Timebase = null;}
		
          try {
            ids.Voltage.Data = UALLowLevel.getVect1DDouble(expIdx, path, "Voltage" + "/Data");
          } catch(Exception exc){ids.Voltage.Data = null;}
		
          try {
            ids.Voltage.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Voltage" + "/Timebase");
          } catch(Exception exc){ids.Voltage.Timebase = null;}
		
          try {
            ids.B_Tor_Vacuum_R.Data = UALLowLevel.getVect1DDouble(expIdx, path, "B_Tor_Vacuum_R" + "/Data");
          } catch(Exception exc){ids.B_Tor_Vacuum_R.Data = null;}
		
          try {
            ids.B_Tor_Vacuum_R.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "B_Tor_Vacuum_R" + "/Timebase");
          } catch(Exception exc){ids.B_Tor_Vacuum_R.Timebase = null;}
		
          try {
            ids.Timebase = UALLowLevel.getVect1DDouble(expIdx, path, "Timebase");
          } catch(Exception exc){ids.Timebase = null;}
		
      UALLowLevel.endIDSGet(expIdx, path);
      return ids;
    }

 /**
 * Method getSlice retrieves the  TF IDS in the open database corresponding to the passed time, based on the selectted interpolation mode.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param time The retrieval time.
 * @param interpolMode Defines the selected interpolation. Allowed values are imas.INTERPOLATION, imas.CLOSEST_SAMPLE,
 * imas.PREVIOUS_SAMPLE
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the selected TF ids. Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if double and empty String is string.
 **/
    public static TF  getSlice(int expIdx, String path, double time, int interpolMode) throws UALException
    {
      String        timebasepath;
      String        ual_debug = System.getenv("ual_debug");
      double        retTime;
      TF ids = new TF (); 
      retTime = UALLowLevel.beginIDSGetSlice(expIdx, path, time);

          try {
            ids.IDS_Properties.Status_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Status_of");
          } catch(Exception exc){ids.IDS_Properties.Status_of = null;}
		
          try {
            ids.IDS_Properties.Comment_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Comment_of");
          } catch(Exception exc){ids.IDS_Properties.Comment_of = null;}
		
          try {
            ids.IDS_Properties.Creator_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Creator_of");
          } catch(Exception exc){ids.IDS_Properties.Creator_of = null;}
		
          try {
            ids.IDS_Properties.Date_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Date_of");
          } catch(Exception exc){ids.IDS_Properties.Date_of = null;}
		
          try {
            ids.IDS_Properties.Source_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Source_of");
          } catch(Exception exc){ids.IDS_Properties.Source_of = null;}
		
          try {
            ids.IDS_Properties.Reference_of = UALLowLevel.getString(expIdx, path, "IDS_Properties" + "/Reference_of");
          } catch(Exception exc){ids.IDS_Properties.Reference_of = null;}
		
          try {
            ids.IDS_Properties.Homogeneous_Timebase = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/Homogeneous_Timebase");
          } catch(Exception exc){ids.IDS_Properties.Homogeneous_Timebase = EMPTY_INT;}
		
          try {
            ids.IDS_Properties.cocos = UALLowLevel.getInt(expIdx, path, "IDS_Properties" + "/cocos");
          } catch(Exception exc){ids.IDS_Properties.cocos = EMPTY_INT;}
		
          try {
            ids.N_Turns = UALLowLevel.getInt(expIdx, path, "N_Turns");
          } catch(Exception exc){ids.N_Turns = EMPTY_INT;}
		
          try {
            ids.N_Coils = UALLowLevel.getInt(expIdx, path, "N_Coils");
          } catch(Exception exc){ids.N_Coils = EMPTY_INT;}
		
// Get Slice Current/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Current" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Current" + "/Data");
	    if (obj.getElementAt(0) > 0) {
              ids.Current.Data = new Vect1DDouble(1);
              ids.Current.Data.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Current" + "/Data", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Current.Data = null;}
		
// Get Slice Current/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Current" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Current" + "/Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Current.Timebase = new Vect1DDouble(1);
              ids.Current.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Current" + "/Timebase", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Current.Timebase = null;}
		
// Get Slice Voltage/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Voltage" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Voltage" + "/Data");
	    if (obj.getElementAt(0) > 0) {
              ids.Voltage.Data = new Vect1DDouble(1);
              ids.Voltage.Data.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Voltage" + "/Data", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Voltage.Data = null;}
		
// Get Slice Voltage/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="Voltage" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Voltage" + "/Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Voltage.Timebase = new Vect1DDouble(1);
              ids.Voltage.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "Voltage" + "/Timebase", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.Voltage.Timebase = null;}
		
// Get Slice B_Tor_Vacuum_R/Data
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="B_Tor_Vacuum_R" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "B_Tor_Vacuum_R" + "/Data");
	    if (obj.getElementAt(0) > 0) {
              ids.B_Tor_Vacuum_R.Data = new Vect1DDouble(1);
              ids.B_Tor_Vacuum_R.Data.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "B_Tor_Vacuum_R" + "/Data", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.B_Tor_Vacuum_R.Data = null;}
		
// Get Slice B_Tor_Vacuum_R/Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       
       	  timebasepath="B_Tor_Vacuum_R" + "/Timebase" ;
       			
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "B_Tor_Vacuum_R" + "/Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.B_Tor_Vacuum_R.Timebase = new Vect1DDouble(1);
              ids.B_Tor_Vacuum_R.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "B_Tor_Vacuum_R" + "/Timebase", timebasepath.trim(), time, interpolMode));
            }
          } catch(Exception exc){ids.B_Tor_Vacuum_R.Timebase = null;}
		
// Get Slice Timebase
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="Timebase";
   	} else {
          timebasepath="Timebase";
   	}   
   		
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, "Timebase");
	    if (obj.getElementAt(0) > 0) {
              ids.Timebase = new Vect1DDouble(1);
              ids.Timebase.setElementAt(0, UALLowLevel.getDoubleSlice(expIdx, path, "/Timebase", timebasepath.trim(), time, interpolMode));

            }
          } catch(Exception exc){ids.Timebase = null;}
		
      UALLowLevel.endIDSGetSlice(expIdx, path);
      return ids;
    }
    
 /**
 * Method delete removes all the data associated with a TF IDS in the open database.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void delete(int expIdx, String path) throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");
      
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Status_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Comment_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Creator_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Date_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Source_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Reference_of");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/Homogeneous_Timebase");          
      UALLowLevel.deleteData(expIdx,path,"IDS_Properties" + "/cocos");          
      UALLowLevel.deleteData(expIdx, path, "N_Turns");  
      UALLowLevel.deleteData(expIdx, path, "N_Coils");  
      UALLowLevel.deleteData(expIdx,path,"Current" + "/Data");          
      UALLowLevel.deleteData(expIdx,path,"Current" + "/Timebase");          
      UALLowLevel.deleteData(expIdx,path,"Voltage" + "/Data");          
      UALLowLevel.deleteData(expIdx,path,"Voltage" + "/Timebase");          
      UALLowLevel.deleteData(expIdx,path,"B_Tor_Vacuum_R" + "/Data");          
      UALLowLevel.deleteData(expIdx,path,"B_Tor_Vacuum_R" + "/Timebase");          
      UALLowLevel.deleteData(expIdx, path, "Timebase");  
    }


     
 /**
 * Method dump is used for debugging and prints the content of the object
 **/
  public void dump()
  {
    System.out.println("***** TF *****");
    

  System.out.println("IDS_Properties ");
  

  System.out.println("Status_of ");
  
        if(IDS_Properties.Status_of!= null)
            System.out.println(IDS_Properties.Status_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Comment_of ");
  
        if(IDS_Properties.Comment_of!= null)
            System.out.println(IDS_Properties.Comment_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Creator_of ");
  
        if(IDS_Properties.Creator_of!= null)
            System.out.println(IDS_Properties.Creator_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Date_of ");
  
        if(IDS_Properties.Date_of!= null)
            System.out.println(IDS_Properties.Date_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Source_of ");
  
        if(IDS_Properties.Source_of!= null)
            System.out.println(IDS_Properties.Source_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Reference_of ");
  
        if(IDS_Properties.Reference_of!= null)
            System.out.println(IDS_Properties.Reference_of);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Homogeneous_Timebase ");
  
        if(IDS_Properties.Homogeneous_Timebase != EMPTY_INT)
            System.out.println(IDS_Properties.Homogeneous_Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("cocos ");
  
        if(IDS_Properties.cocos != EMPTY_INT)
            System.out.println(IDS_Properties.cocos);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("N_Turns ");
  
        if(N_Turns != EMPTY_INT)
            System.out.println(N_Turns);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("N_Coils ");
  
        if(N_Coils != EMPTY_INT)
            System.out.println(N_Coils);
        else
            System.out.println("Empty");
        System.out.println("");    
    

  System.out.println("Current ");
  

  System.out.println("Data ");
  
         if(Current.Data != null)
            System.out.println(Current.Data);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Timebase ");
  
         if(Current.Timebase != null)
            System.out.println(Current.Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Voltage ");
  

  System.out.println("Data ");
  
         if(Voltage.Data != null)
            System.out.println(Voltage.Data);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Timebase ");
  
         if(Voltage.Timebase != null)
            System.out.println(Voltage.Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("B_Tor_Vacuum_R ");
  

  System.out.println("Data ");
  
         if(B_Tor_Vacuum_R.Data != null)
            System.out.println(B_Tor_Vacuum_R.Data);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Timebase ");
  
         if(B_Tor_Vacuum_R.Timebase != null)
            System.out.println(B_Tor_Vacuum_R.Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   

  System.out.println("Timebase ");
  
         if(Timebase != null)
            System.out.println(Timebase);
        else
            System.out.println("Empty");
        System.out.println("");    
   
    System.out.println("******************");
  }
}

 }
