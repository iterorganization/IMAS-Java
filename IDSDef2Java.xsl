<?xml version="1.0" encoding="UTF-8"?>
<?modxslt-stylesheet type="text/xsl" media="fuffa, screen and $GET[stylesheet]" href="./%24GET%5Bstylesheet%5D" alternate="no" title="Translation using provided stylesheet" charset="ISO-8859-1" ?>
<?modxslt-stylesheet type="text/xsl" media="screen" alternate="no" title="Show raw source of the XML file" charset="ISO-8859-1" ?>
<!-- Jo Lister, CRPP-EPFL, 2005, Generating  Fortran 90 code calls from XSD schemas -->
<!-- -->

<xsl:stylesheet xmlns:yaslt="http://www.mod-xslt2.com/ns/1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xs="http://www.w3.org/2001/XMLSchema" version="1.0" extension-element-prefixes="yaslt"
  xmlns:fn="http://www.w3.org/2005/02/xpath-functions">
  <!-- -->
   <xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
  <!-- MODE can be set to get or put in the 2 transformations for generating the subroutines, this reduced editing requirements
  it could also be done with 2 xslt operations on a single file, but this might be overkill ??
  PUT HERE "get" OR "put"-->
  
<!--=================================================-->
<!--                 General section                 -->
<!--=================================================-->
  
<xsl:template match = "/IDSs">
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
     
     if(run <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text> 0)
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
 <xsl:apply-templates select = "IDS"/>
 }
</xsl:template>


<!--=================================================-->
<!--                Definition of IDSs               -->
<!--=================================================-->

<xsl:template match = "IDS">

public static class <xsl:value-of select="@name"/>
{
    <xsl:apply-templates select = "field" mode = "DECLARE"/>
  

<xsl:choose>
  <!--============ Define time-dependent IDSs ============-->
  <xsl:when test = "@timed = 'yes'">

  </xsl:when>
  
  <!--============ Define time-independent IDSs ============-->
  <xsl:otherwise>

 /**
 * Method copy <xsl:value-of select="@name"/> copies a full IDS between two databases.
 * @param srcIdx The index of the source database, returned by imas.open()
 * @param srcOccur The occurence of the IDS in the source database.
 * @param destIdx The index of the destination database, returned by imas.open()
 * @param destOccur The occurence of the IDS in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copy(int srcIdx, int srcOccur, int destIdx, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyIds(srcIdx, destIdx, "<xsl:value-of select="@name"/>", srcOccur, destOccur);
    }

 /**
 * Method copyEnv <xsl:value-of select="@name"/> copies a full IDS between two databases.
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
        UALLowLevel.ualCopyIdsEnv(srcTokamak, srcVersion, srcUser, srcShot, srcRun, srcOccur, destTokamak, destVersion, destUser, destShot, destRun, destOccur, "<xsl:value-of select="@name"/>");
    }

/**
 * Method put stores a non timed <xsl:value-of select="@name"/> IDSs in the open database. 
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The passed <xsl:value-of select="@name"/> ids.
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void put(int expIdx, String path, <xsl:value-of select="@name"/> ids)  throws UALException
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
          <xsl:apply-templates select = "field" mode = "PUT_SINGLE"/>
          UALLowLevel.endIDSPut(expIdx, path);
    }

 /**
 * Method putNonTimed stores the non time dependent fields of a (timed) <xsl:value-of select="@name"/> IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The timed <xsl:value-of select="@name"/> IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void putNonTimed(int expIdx, String path, <xsl:value-of select="@name"/> ids) throws UALException
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
          <xsl:apply-templates select="field" mode="PUT_SINGLE">
          <xsl:with-param name="non_timed" select="yes"/>
          </xsl:apply-templates>
          UALLowLevel.endIDSPutNonTimed(expIdx, path);
    }

    
/**
 * Method putSlice appends a (timed) <xsl:value-of select="@name"/> IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param ids The timed <xsl:value-of select="@name"/> IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void putSlice(int expIdx, String path, <xsl:value-of select="@name"/> ids) throws UALException
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
          <xsl:apply-templates select = "field" mode = "PUT_SLICE"/>
          UALLowLevel.endIDSPutSlice(expIdx, path);
    }
    

    <xsl:choose>
      <!--============ General case (except topinfo) ============-->
      <xsl:when test = "@name != 'topinfo'">

/**
 * Method putdb stores a non timed <xsl:value-of select="@name"/> IDSs in the open database only if
 *  datainto.isref == 1. In any case it calls afterwards UALLowLevel.putIdsDb() in order to update catalogue
 * @param expIdx The index to the database, returned by imas.open()
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected ids. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @param ids The  <xsl:value-of select="@name"/> IDSs
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
/*
    public static void putdb(int expIdx, String user, String machine, int shot, int run, 
    	String path, int occurrence, <xsl:value-of select="@name"/> ids) throws UALException
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
 * Method getdb retrieves the non timed <xsl:value-of select="@name"/> IDSs in the open database, taking the correct reference.
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the <xsl:value-of select="@name"/> IDS . Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if float or double and empty String is string.
 **/
    public static <xsl:value-of select="@name"/> getdb(String user, String machine, int shot, int run, String version, String path, int occurrence) throws UALException
    {
//		int idx = UALLowLevel.openDb(user, machine, shot, run, path, occurrence);
		int idx = openEnv("ids", shot, run, user, machine, version);
		if (occurrence != 0)
			path = path + "/" + occurrence;
		<xsl:value-of select="@name"/> retIdss = null;
                try {
                        retIdss = get(idx, path);
                }
                catch(Exception exc) {
                        retIdss = new <xsl:value-of select="@name"/> ();
                        System.out.println("Return a new <xsl:value-of select="@name"/> empty IDS after catching exception: "+exc);
                }
		close(idx);
		return retIdss;
    }

/*
    public static void fillIdsRef(<xsl:value-of select="@name"/> ids, int isRef, String refUser, String refMachine, int refShot, int refRun, int refOccurrence)
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
        </xsl:when>
        <!--============ Special case: topinfo ============-->
       <xsl:otherwise>
/**
 * Method putdb always stores a non timed topinfo IDS in the open database (it is never a reference). It calls afterwards UALLowLevel.putIdsDb() in order to update catalogue
 * @param expIdx The index to the database, returned by imas.open()
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected ids. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @param ids The topinfo IDS
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
/*
	public static void putdb(int expIdx, String user, String machine, int shot, int run,
		String path, int occurrence, <xsl:value-of select="@name"/> ids) throws UALException
	{
		String actPath;
		if(occurrence > 0)
			actPath = path+"/"+occurrence;
		else
			actPath = path;
		put(expIdx, actPath, ids);
		UALLowLevel.putIdsDb(user, machine, shot, run, path, occurrence, 1, user, machine, shot, run, occurrence);
	}
*/

/**
 * Method getdb retrieves the non timed topinfo IDS in the open database.
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param occurrence The occurence of the selected ids.
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the topinfo ids. Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if float or double and empty String is string.
 **/
/*
    public static <xsl:value-of select="@name"/> getdb(String user, String machine, int shot, int run, String path, int occurrence) throws UALException
    {
		int idx = UALLowLevel.openDb(user, machine, shot, run, path, occurrence);
		if (occurrence != 0)
			path = path + "/" + occurrence;
		<xsl:value-of select="@name"/> retIdss = null;
                try {
                        retIdss = get(expIdx, path);
                }
                catch(Exception exc) {
                        retIdss = new <xsl:value-of select="@name"/> ();
                        System.out.println("Return a new <xsl:value-of select="@name"/> empty IDS after catching exception: "+exc);
                }
		close(idx);
		return retIdss;
    }

	public static void fillIdsRef(<xsl:value-of select="@name"/> ids, int isRef, String refUser, String refMachine,
		int refShot, int refRun, int refOccurrence)
	{
		// do nothing since topinfo does not have any reference field
	}
*/
        </xsl:otherwise>
   </xsl:choose>    

    public static <xsl:value-of select="@name"/>  get(int expIdx, String path)  throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");

      <xsl:value-of select="@name"/> ids = new <xsl:value-of select="@name"/> (); 
      UALLowLevel.beginIDSGet(expIdx, path, false);
      <xsl:apply-templates select = "field" mode = "GET_SINGLE"> 
        <xsl:with-param name="ids_name" select="@name"/>
      </xsl:apply-templates>
      UALLowLevel.endIDSGet(expIdx, path);
      return ids;
    }

 /**
 * Method getSlice retrieves the  <xsl:value-of select="@name"/> IDS in the open database corresponding to the passed time, based on the selectted interpolation mode.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param time The retrieval time.
 * @param interpolMode Defines the selected interpolation. Allowed values are imas.INTERPOLATION, imas.CLOSEST_SAMPLE,
 * imas.PREVIOUS_SAMPLE
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are 
 * missing IDS fields. 
 * @return the selected <xsl:value-of select="@name"/> ids. Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if double and empty String is string.
 **/
    public static <xsl:value-of select="@name"/>  getSlice(int expIdx, String path, double time, int interpolMode) throws UALException
    {
      String        timebasepath;
      String        ual_debug = System.getenv("ual_debug");
      double        retTime;
      <xsl:value-of select="@name"/> ids = new <xsl:value-of select="@name"/> (); 
      retTime = UALLowLevel.beginIDSGetSlice(expIdx, path, time);
<xsl:apply-templates select = "field" mode = "GET_SLICE" />
      UALLowLevel.endIDSGetSlice(expIdx, path);
      return ids;
    }
    
 /**
 * Method delete removes all the data associated with a <xsl:value-of select="@name"/> IDS in the open database.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @exception UALException Issued when data cannot be stored for any reason. 
 **/
    public static void delete(int expIdx, String path) throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");
      <xsl:apply-templates select = "field" mode = "DELETE"/>
    }


     </xsl:otherwise>
  </xsl:choose>
 /**
 * Method dump is used for debugging and prints the content of the object
 **/
  public void dump()
  {
    System.out.println("***** <xsl:value-of select="@name"/> *****");
    <xsl:apply-templates select = "field" mode = "DUMP">
      <xsl:with-param name="level">0</xsl:with-param>
      <xsl:with-param name="idxpath"></xsl:with-param>
    </xsl:apply-templates>
    System.out.println("******************");
  }
}
</xsl:template>

<!--=================================================-->
<!--                  Delete field                   -->
<!--=================================================-->

<xsl:template match="field" mode="DELETE">
<xsl:param name="variable_path"/>
<xsl:param name="mds_path"/>
<xsl:choose>
        <!--========== Regular structures ==========-->
<xsl:when test="@data_type='structure'">
   <xsl:choose>
   <xsl:when test="$variable_path">
   <xsl:apply-templates select="field" mode="DELETE">
   <xsl:with-param name ="variable_path" select="concat($variable_path,'.',@name)"/>
   <xsl:with-param name ="mds_path" select="concat($mds_path,' + &quot;/',@name,'&quot;')"/>
   </xsl:apply-templates>
   </xsl:when>
   <xsl:otherwise>
   <xsl:apply-templates select="field" mode="DELETE">
   <xsl:with-param name ="variable_path" select="@name"/>
   <xsl:with-param name ="mds_path" select="concat('&quot;',@name,'&quot;')"/>
   </xsl:apply-templates>
   </xsl:otherwise>
   </xsl:choose>
</xsl:when>

        <!--========== Arrays of structures ==========-->
<xsl:when test="@data_type='struct_array'">
   <xsl:choose>
   <xsl:when test="$mds_path">
      for (int i<xsl:value-of select = "@name"/> = 0; i<xsl:value-of select = "@name"/>&lt;<xsl:value-of select = "@maxoccur"/>; i<xsl:value-of select = "@name"/>++){
       <xsl:apply-templates select = "field" mode = "DELETE">
<xsl:with-param name ="variable_path" select="concat($variable_path,'.',@name,'[i',@name,']')"/>
<xsl:with-param name="mds_path" select="concat($mds_path,'+&quot;/',@name,'/&quot; + Integer.toString(i',@name,'+1).trim()')"/>
       </xsl:apply-templates>
      }
   </xsl:when>
   <xsl:otherwise>
      for (int i<xsl:value-of select = "@name"/> = 0; i<xsl:value-of select = "@name"/>&lt;<xsl:value-of select = "@maxoccur"/>; i<xsl:value-of select = "@name"/>++){
      <xsl:apply-templates select = "field" mode = "DELETE">
<xsl:with-param name ="variable_path" select="concat(@name,'[i',@name,']')"/>
<xsl:with-param name="mds_path" select="concat('&quot;/',@name,'/&quot; + Integer.toString(i',@name,'+1).trim()')"/>
</xsl:apply-templates>
      }
   </xsl:otherwise>
   </xsl:choose>
</xsl:when>
<xsl:otherwise>
   <xsl:choose>
   <xsl:when test="$mds_path">
      UALLowLevel.deleteData(expIdx,path,<xsl:value-of select="concat($mds_path,' + &quot;/',@name,'&quot;')"/>);          <!-- call to the low level delete_data routine -->
   </xsl:when>
   <xsl:otherwise>
      UALLowLevel.deleteData(expIdx, path, "<xsl:value-of select = "@path"/>");  <!-- call to the low level delete_data routine -->
   </xsl:otherwise>
   </xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	

<!--=================================================-->
<!--               Delete timed field                -->
<!--=================================================-->

<xsl:template match = "field" mode = "DELETE_TIMED">
  <xsl:choose>
    <xsl:when test = "@timed = 'yes'">
        UALLowLevel.deleteData(expIdx, path, "<xsl:value-of select="@path"/>");
    </xsl:when>
   </xsl:choose>
   <xsl:choose>
     <xsl:when test="@data_type='structure'">
      <xsl:apply-templates select = "field" mode = "DELETE_TIMED"/>
    </xsl:when>
  </xsl:choose>
</xsl:template>



<!--=================================================-->
<!--                Display content                  -->
<!--=================================================-->

<xsl:template match = "field" mode = "DUMP">
  <xsl:param name="level"/>     <!-- recursion level -->
  <xsl:param name="idxpath"/>   <!-- full java path including indices -->

  <!-- build the complete path of the current field -->
  <xsl:param name="currentidxpath">
    <xsl:choose>
      <xsl:when test="$idxpath=''">
        <xsl:value-of select="@name"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$idxpath"/>.<xsl:value-of select="@name"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:param>

  System.out.println("<xsl:value-of select = "@name"/> ");
  <xsl:choose>
    <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
        if(<xsl:value-of select = "$currentidxpath"/>!= null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
        if(<xsl:value-of select = "$currentidxpath"/> != EMPTY_INT)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    <xsl:when test="@name='xs:boolean'">
         System.out.println(<xsl:value-of select = "$currentidxpath"/>);
         System.out.println("");    
    </xsl:when>
    <xsl:when test="@name='xs:double'">
        if(<xsl:value-of select = "$currentidxpath"/> != EMPTY_DOUBLE)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
         if(<xsl:value-of select = "$currentidxpath"/> != EMPTY_DOUBLE)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    
    <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    <xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
         if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
   </xsl:when>
    <xsl:when test="@name='vecdbl_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    
    <xsl:when test="@data_type='FLT_2D'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    <xsl:when test="@name='matdbl_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    <xsl:when test="@data_type='INT_2D'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    
    <xsl:when test="@data_type='FLT_3D'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    <xsl:when test="@data_type='INT_3D'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    <xsl:when test="@name='array3ddbl_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    
    <xsl:when test="@name='array4ddbl_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    <xsl:when test="@data_type='FLT_4D'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    <xsl:when test="@name='array4dint_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    
    <xsl:when test="@name='array5ddbl_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    <xsl:when test="@data_type='FLT_5D'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    <xsl:when test="@name='array5dint_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    
    <xsl:when test="@name='array6ddbl_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    <xsl:when test="@data_type='FLT_6D'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>
    <xsl:when test="@name='array6dint_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");    
    </xsl:when>

    <xsl:when test="@data_type='struct_array'">
      if (<xsl:value-of select = "$currentidxpath"/> != null) {
        for (int i<xsl:value-of select="$level + 1"/> = 0; i<xsl:value-of select="$level + 1"/> &lt; <xsl:value-of select = "$currentidxpath"/>.length; i<xsl:value-of select="$level + 1"/>++) {
          <xsl:apply-templates select = "field" mode = "DUMP">
            <xsl:with-param name="level"><xsl:value-of select="$level + 1"/></xsl:with-param>
            <xsl:with-param name="idxpath"><xsl:value-of select="$currentidxpath"/>[i<xsl:value-of select="$level + 1"/>]</xsl:with-param>
          </xsl:apply-templates>
        }
      }
    </xsl:when>

    <xsl:when test="@data_type='structure'">
      <xsl:apply-templates select = "field" mode = "DUMP">
        <xsl:with-param name="level"><xsl:value-of select="$level"/></xsl:with-param>
        <xsl:with-param name="idxpath"><xsl:value-of select="$currentidxpath"/></xsl:with-param>
      </xsl:apply-templates>
    </xsl:when>

  </xsl:choose>
</xsl:template>

<!--=================================================-->
<!--                Field declaration                -->
<!--=================================================-->

<xsl:template match = "field" mode = "DECLARE">
  <xsl:choose>
    <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
      public String <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
      public int <xsl:value-of select = "@name"/> = EMPTY_INT;
    </xsl:when>
    <xsl:when test="@name='xs:boolean'">
      public boolean <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='xs:double'">
      public double <xsl:value-of select = "@name"/> = EMPTY_DOUBLE;
    </xsl:when>
    <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
      public double <xsl:value-of select = "@name"/> = EMPTY_DOUBLE;
    </xsl:when>
    
    <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
      public Vect1DString <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
      public Vect1DDouble <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='vecdbl_type'">
      public Vect1DDouble <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
      public Vect1DInt <xsl:value-of select = "@name"/>;
    </xsl:when>
    
    <xsl:when test="@data_type='FLT_2D'">
      public Vect2DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='matdbl_type'">
      public Vect2DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@data_type='INT_2D'">
      public Vect2DInt <xsl:value-of select = "@name"/>;
    </xsl:when>
    
    <xsl:when test="@data_type='FLT_3D'">
      public Vect3DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@data_type='INT_3D'">
      public Vect3DInt  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='array3ddbl_type'">
      public Vect3DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    
    <xsl:when test="@data_type='FLT_4D'">
      public Vect4DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='array4dint_type'">
      public Vect4DInt  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='array4ddbl_type'">
      public Vect4DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    
    <xsl:when test="@data_type='FLT_5D'">
      public Vect5DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='array5dint_type'">
      public Vect5DInt  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='array5ddbl_type'">
      public Vect5DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>

    <xsl:when test="@data_type='FLT_6D'">
      public Vect6DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='array6dint_type'">
      public Vect6DInt  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='array6ddbl_type'">
      public Vect6DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    
    <xsl:when test="@name='array7dflt_type'">
      public Vect7DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='array7dint_type'">
      public Vect7DInt  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='array7ddbl_type'">
      public Vect7DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>

    <xsl:when test="@data_type='struct_array'">
      public static class <xsl:value-of select = "@name"/>Class {
      <xsl:apply-templates select = "field" mode = "DECLARE"/>
      }
      public <xsl:value-of select = "@name"/>Class <xsl:value-of select = "@name"/>[];
    </xsl:when>

    <xsl:when test="@data_type='structure'">
      public static class <xsl:value-of select = "@name"/>Class {
      <xsl:apply-templates select = "field" mode = "DECLARE"/>
      }
      public <xsl:value-of select = "@name"/>Class <xsl:value-of select = "@name"/> = new <xsl:value-of select = "@name"/>Class();
    </xsl:when>

  </xsl:choose>
</xsl:template>

<!--=================================================================-->
<!-- function to generate the full subclass name from the field path -->
<!--           (adds the "Class" keyword for each subclass)          -->
<!--=================================================================-->

<xsl:template name="BUILD_TYPENAME">
  <xsl:param name="typepath"/>
  <xsl:choose>
    <xsl:when test="contains($typepath,'/')">
      <xsl:call-template name="BUILD_TYPENAME">
        <xsl:with-param name="typepath">
          <xsl:value-of select="substring-before($typepath,'/')"/>Class.<xsl:value-of select="substring-after($typepath,'/')"/>
        </xsl:with-param >
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$typepath"/><xsl:text>Class</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>


<!--=================================================-->
<!--      Get field from a time-independent IDS      -->
<!--=================================================-->

<xsl:template match = "field" mode = "GET_SINGLE">
<xsl:param name="variable_path"/>
<xsl:param name="mds_path"/>
<xsl:param name="ids_name"/>

      <xsl:choose>
        <!--========== Regular structures ==========-->
        <xsl:when test="@data_type='structure'">
<!--          <xsl:apply-templates select = "field" mode = "GET_SINGLE"/> -->
<xsl:choose>
<xsl:when test="$variable_path">
<xsl:apply-templates select="field" mode="GET_SINGLE">
<xsl:with-param name ="variable_path" select="concat($variable_path,'.',@name)"/>
<xsl:with-param name ="mds_path" select="concat($mds_path,' + &quot;/',@name,'&quot;')"/>
<xsl:with-param name ="ids_name" select="$ids_name"/>
</xsl:apply-templates>
</xsl:when>
<xsl:otherwise>
<xsl:apply-templates select="field" mode="GET_SINGLE">
<xsl:with-param name ="variable_path" select="@name"/>
<xsl:with-param name ="mds_path" select="concat('&quot;',@name,'&quot;')"/>
<xsl:with-param name ="ids_name" select="$ids_name"/>
</xsl:apply-templates>
</xsl:otherwise>
</xsl:choose>
        </xsl:when>

        <!--========== Arrays of structures ==========-->
        <xsl:when test="@data_type='struct_array'">
          <!-- Generate the full name of the subclass from the field path -->
          <xsl:variable name="fulltypepath">
            <xsl:call-template name="BUILD_TYPENAME">
              <xsl:with-param name="typepath"><xsl:value-of select = "@path"/></xsl:with-param >
            </xsl:call-template>
          </xsl:variable>
// Get Array of Structures <xsl:value-of select="@path"/>
<xsl:choose>
<xsl:when test="$variable_path">
          try {
            ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> = new <xsl:value-of select="$fulltypepath"/>[UALLowLevel.getInt(expIdx,path, <xsl:value-of select="$mds_path"/>+&quot;/<xsl:value-of select="@name"/>/Shape_of&quot;)];
            for (int i<xsl:value-of select = "@name"/> = 0; i<xsl:value-of select = "@name"/>&lt;ids.<xsl:value-of select = "concat($variable_path,'.',@name)"/>.length; i<xsl:value-of select = "@name"/>++){
              ids.<xsl:value-of select="concat($variable_path,'.',@name,'[i',@name,']')"/> = new <xsl:value-of select="$fulltypepath"/>();
<xsl:apply-templates select = "field" mode = "GET_SINGLE">
<xsl:with-param name ="variable_path" select="concat($variable_path,'.',@name,'[i',@name,']')"/>
<xsl:with-param name="mds_path" select="concat($mds_path,'+&quot;/',@name,'/&quot; + Integer.toString(i',@name,'+1).trim()')"/>
<xsl:with-param name ="ids_name" select="$ids_name"/>
</xsl:apply-templates>
            }
          } catch(Exception exc){ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> = null;}
</xsl:when>

<xsl:otherwise>
          try {
            ids.<xsl:value-of select="@name"/> = new <xsl:value-of select="$fulltypepath"/>[UALLowLevel.getInt(expIdx,path, &quot;<xsl:value-of select="@name"/>/Shape_of&quot;)];
            for (int i<xsl:value-of select = "@name"/> = 0; i<xsl:value-of select = "@name"/>&lt;ids.<xsl:value-of select = "@name"/>.length; i<xsl:value-of select = "@name"/>++){
              ids.<xsl:value-of select="concat(@name,'[i',@name,']')"/> = new <xsl:value-of select="$fulltypepath"/>();
<xsl:apply-templates select = "field" mode = "GET_SINGLE">
<xsl:with-param name ="variable_path" select="concat(@name,'[i',@name,']')"/>
<xsl:with-param name="mds_path" select="concat('&quot;',@name,'/&quot; + Integer.toString(i',@name,'+1).trim()')"/>
<xsl:with-param name ="ids_name" select="$ids_name"/>

</xsl:apply-templates>
            }
          } catch(Exception exc){ids.<xsl:value-of select="@name"/> = null;}
</xsl:otherwise>
</xsl:choose>
        </xsl:when>

        <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">String</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Int</xsl:with-param>
	<xsl:with-param name="Exception">EMPTY_INT</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='xs:boolean'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Boolean</xsl:with-param>
	<xsl:with-param name="Exception">EMPTY_INT</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='xs:double'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Double</xsl:with-param>
	<xsl:with-param name="Exception">EMPTY_DOUBLE</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Double</xsl:with-param>
	<xsl:with-param name="Exception">EMPTY_DOUBLE</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       
        <xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect1DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
         </xsl:when>
        <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect1DString</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
         </xsl:when>
        <xsl:when test="@name='vecdbl_type'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect1DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
         </xsl:when>
        <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect1DInt</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
         </xsl:when>
         
        <xsl:when test="@data_type='FLT_2D'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect2DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
        <xsl:when test="@name='matdbl_type'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect2DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
        <xsl:when test="@data_type='INT_2D'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect2DInt</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       
        <xsl:when test="@data_type='FLT_3D'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect3DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array3ddbl_type'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect3DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       <xsl:when test="@data_type='INT_3D'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect3DInt</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       
        <xsl:when test="@data_type='FLT_4D'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect4DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array4ddbl_type'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect4DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array4dint_type'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect4DInt</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       
        <xsl:when test="@data_type='FLT_5D'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect5DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array5ddbl_type'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect5DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array5dint_type'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect5DInt</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>

        <xsl:when test="@data_type='FLT_6D'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect6DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array6ddbl_type'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect6DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array6dint_type'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect6DInt</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>

      </xsl:choose>
</xsl:template>

<!--=================================================-->
<!--             Get field from a slice              -->
<!--=================================================-->

<xsl:template match = "field" mode = "GET_SLICE">
<xsl:param name="variable_path"/>
<xsl:param name="mds_path"/>
<xsl:param name="ids_name"/>
<xsl:choose>
        <!--========== Regular structures ==========-->
        <xsl:when test="@data_type='structure'">
<!--          <xsl:apply-templates select = "field" mode = "GET_SLICE"/> -->
		<xsl:choose>
		<xsl:when test="$variable_path">
			<xsl:apply-templates select="field" mode="GET_SLICE">
			<xsl:with-param name ="variable_path" select="concat($variable_path,'.',@name)"/>
			<xsl:with-param name ="mds_path" select="concat($mds_path,' + &quot;/',@name,'&quot;')"/>
			<xsl:with-param name ="ids_name" select="$ids_name"/>
			</xsl:apply-templates>
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates select="field" mode="GET_SLICE">
			<xsl:with-param name ="variable_path" select="@name"/>
			<xsl:with-param name ="mds_path" select="concat('&quot;',@name,'&quot;')"/>
			<xsl:with-param name ="ids_name" select="$ids_name"/>
			</xsl:apply-templates>
		</xsl:otherwise>
		</xsl:choose>
        </xsl:when>

        <!--========== Arrays of structures ==========-->
        <xsl:when test="@data_type='struct_array'">
          	<!-- Generate the full name of the subclass from the field path -->
		<xsl:variable name="fulltypepath">
			<xsl:call-template name="BUILD_TYPENAME">
		        <xsl:with-param name="typepath"><xsl:value-of select = "@path"/></xsl:with-param >
		        </xsl:call-template>
		</xsl:variable>
// Get Array of Structures <xsl:value-of select="@path"/>
		<xsl:choose>
		<xsl:when test="$variable_path">
          try {
            ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> = new <xsl:value-of select="$fulltypepath"/>[UALLowLevel.getInt(expIdx,path, <xsl:value-of select="$mds_path"/>+&quot;/<xsl:value-of select="@name"/>/Shape_of&quot;)];
            for (int i<xsl:value-of select = "@name"/> = 0; i<xsl:value-of select = "@name"/>&lt;ids.<xsl:value-of select = "concat($variable_path,'.',@name)"/>.length; i<xsl:value-of select = "@name"/>++){
              ids.<xsl:value-of select="concat($variable_path,'.',@name,'[i',@name,']')"/> = new <xsl:value-of select="$fulltypepath"/>();
			<xsl:apply-templates select = "field" mode = "GET_SLICE">
			<xsl:with-param name ="variable_path" select="concat($variable_path,'.',@name,'[i',@name,']')"/>
			<xsl:with-param name="mds_path" select="concat($mds_path,'+&quot;/',@name,'/&quot; + Integer.toString(i',@name,'+1).trim()')"/>
			<xsl:with-param name ="ids_name" select="$ids_name"/>
			</xsl:apply-templates>
            }
          } catch(Exception exc){ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> = null;}
		</xsl:when>

		<xsl:otherwise>
          try {
            ids.<xsl:value-of select="@name"/> = new <xsl:value-of select="$fulltypepath"/>[UALLowLevel.getInt(expIdx,path, &quot;<xsl:value-of select="@name"/>/Shape_of&quot;)];
            for (int i<xsl:value-of select = "@name"/> = 0; i<xsl:value-of select = "@name"/>&lt;ids.<xsl:value-of select = "@name"/>.length; i<xsl:value-of select = "@name"/>++){
              ids.<xsl:value-of select="concat(@name,'[i',@name,']')"/> = new <xsl:value-of select="$fulltypepath"/>();
			<xsl:apply-templates select = "field" mode = "GET_SLICE">
			<xsl:with-param name ="variable_path" select="concat(@name,'[i',@name,']')"/>
			<xsl:with-param name="mds_path" select="concat('&quot;',@name,'/&quot; + Integer.toString(i',@name,'+1).trim()')"/>
			<xsl:with-param name ="ids_name" select="$ids_name"/>
			</xsl:apply-templates>
            }
          } catch(Exception exc){ids.<xsl:value-of select="@name"/> = null;}
		</xsl:otherwise>
		</xsl:choose>
	</xsl:when>

   	<xsl:when test="@type='SIGNAL'">
	<xsl:choose>
		<xsl:when test="$variable_path">
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       <!--XSLtest whether this is a Data/Timebase structure, otherwise assume that the Timebasepath attribute from IDSDef is correct-->
       		<xsl:choose>
       			<xsl:when test="(@name='Data' and ../field[@name='Timebase']) or (@name='Timebase' and ../field[@name='Data'])">
       	  timebasepath=<xsl:value-of select="$mds_path"/> + &quot;/Timebase&quot; ;
       			</xsl:when>
       			<xsl:otherwise>
       	  timebasepath=&quot;<xsl:call-template name="printTimebasepath"/>&quot; ;
       			</xsl:otherwise>
       		</xsl:choose>
   	} else {
          timebasepath="Timebase";
   	}   
   		</xsl:when>
 		<xsl:otherwise>
		// line 1312 of IDS ===
		// @path = <xsl:value-of select="@path"/>
		// $variable_path = <xsl:value-of select="$variable_path"/>
		// @data_type = <xsl:value-of select="@data_type"/>
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="<xsl:call-template name="printTimebasepath"/>";
   	} else {
          timebasepath="Timebase";
   	}   
   		</xsl:otherwise>
 	</xsl:choose> 

 	<xsl:choose>
	<xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
		<xsl:call-template name="getStringSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">String</xsl:with-param>
		<xsl:with-param name="SliceFunction">String</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect1DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Double</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">0</xsl:with-param>
		</xsl:call-template>
         </xsl:when>
        <xsl:when test="@name='vecdbl_type'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect1DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Double</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">0</xsl:with-param>
		</xsl:call-template>
         </xsl:when>
        <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
// in sequence of test on data_type .... int
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect1DInt</xsl:with-param>
		<xsl:with-param name="SliceFunction">Int</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">0</xsl:with-param>
		</xsl:call-template>
         </xsl:when>
         
        <xsl:when test="@data_type='FLT_2D'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect2DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect1DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">1</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
        <xsl:when test="@name='matdbl_type'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect2DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect1DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">1</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
        <xsl:when test="@data_type='INT_2D'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect2DInt</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect1DInt</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">1</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       
        <xsl:when test="@data_type='FLT_3D'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect3DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect2DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">2</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array3ddbl_type'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect3DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect2DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">2</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       <xsl:when test="@data_type='INT_3D'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect3DInt</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect2DInt</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">2</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       
        <xsl:when test="@data_type='FLT_4D'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect4DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect3DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">3</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array4ddbl_type'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect4DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect3DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">3</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array4dint_type'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect4DInt</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect3DInt</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">3</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       
        <xsl:when test="@data_type='FLT_5D'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect5DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect4DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">4</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array5ddbl_type'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect5DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect4DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">4</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array5dint_type'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect5DInt</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect4DInt</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">4</xsl:with-param>
		</xsl:call-template>
       </xsl:when>

        <xsl:when test="@data_type='FLT_6D'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect6DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect5DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">5</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array6ddbl_type'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect6DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect5DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">5</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array6dint_type'">
		<xsl:call-template name="getVariableSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
		<xsl:with-param name="Function">Vect6DInt</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect5DInt</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">5</xsl:with-param>
		</xsl:call-template>
       </xsl:when>

	<xsl:otherwise>
 // Get Slice <xsl:value-of select="@path"/> : PROBLEM : UNIDENTIFIED TYPE !!! <!-- for comment only -->
	</xsl:otherwise>
	</xsl:choose>

</xsl:when>
	<xsl:otherwise>
   <!-- Get the data from a time-independent field is the same procedure as GET_SINGLE -->
	   <xsl:apply-templates select="." mode="GET_SINGLE">
	   <xsl:with-param name="variable_path" select="$variable_path"/>
	   <xsl:with-param name="mds_path" select="$mds_path"/>
	   </xsl:apply-templates>
	</xsl:otherwise>

</xsl:choose>
</xsl:template>

<!--=================================================-->
<!--              Get array of structure             -->
<!--=================================================-->

<xsl:template match="field" mode = "GET_OBJECT">
  <xsl:param name="fulltypepath">
    <xsl:call-template name="BUILD_TYPENAME">
      <xsl:with-param name="typepath"><xsl:value-of select = "@path"/></xsl:with-param >
    </xsl:call-template>
  </xsl:param>

  <xsl:choose>
    <xsl:when test="@data_type='structure'">
      <xsl:apply-templates select="field" mode="GET_OBJECT"/>
    </xsl:when>
    <xsl:when test="@data_type='struct_array'">
      static void get_timed_<xsl:value-of select = "translate(@path,'/','_')"/>(int expIdx,<xsl:value-of select = "$fulltypepath"/>[] field, int obj1) throws UALException
      {
          for (int i1 = 0; i1 &lt; UALLowLevel.getObjectDim(expIdx,obj1); i1++) {     // process array elements
            if (field[i1]==null)
              field[i1] = new <xsl:value-of select = "$fulltypepath"/>();
            <xsl:apply-templates select = "field" mode = "GET_FROM_OBJECT">
              <xsl:with-param name="level">1</xsl:with-param>
              <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
              <xsl:with-param name="idxpath">field[i1]</xsl:with-param>
            <xsl:with-param name="timed">yes</xsl:with-param>
            </xsl:apply-templates>
          }
       }
 
      static void get_nontimed_<xsl:value-of select = "translate(@path,'/','_')"/>(int expIdx,<xsl:value-of select = "$fulltypepath"/>[] field, int obj1) throws UALException
      {
          for (int i1 = 0; i1 &lt; UALLowLevel.getObjectDim(expIdx,obj1); i1++) {     // process array elements
            if (field[i1]==null)
              field[i1] = new <xsl:value-of select = "$fulltypepath"/>();
            <xsl:apply-templates select = "field" mode = "GET_FROM_OBJECT">
              <xsl:with-param name="level">1</xsl:with-param>
              <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
              <xsl:with-param name="idxpath">field[i1]</xsl:with-param>
            <xsl:with-param name="timed">no</xsl:with-param>
            </xsl:apply-templates>
          }
       }
   </xsl:when>
  </xsl:choose>
</xsl:template>

<!--=================================================-->
<!--       Get field for a time-dependent IDS      -->
<!--=================================================-->

<xsl:template match = "field" mode = "GET_FULL">
  <xsl:choose>
    <!-- Time-dependent field -->
    <xsl:when test = "@timed = 'yes'">
      <xsl:choose>
        <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
          try {
           vect1DInt = UALLowLevel.getVect1DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect1DInt.getDim() != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DInt.getElementAt(i);
          } catch(Exception exc)
          {           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_INT;
          }
        </xsl:when>
        <xsl:when test="@name='xs:boolean'">
          try {
           vect1DBoolean = UALLowLevel.getVect1DBoolean(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect1DBoolean.getDim() != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DBoolean.getElementAt(i);
           } catch(Exception exc){}
        </xsl:when>
        <xsl:when test="@name='xs:double'">
          try {
           vect1DDouble = UALLowLevel.getVect1DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect1DDouble.getDim() != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DDouble.getElementAt(i);
           } catch(Exception exc)
           {           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_DOUBLE;
          }
        </xsl:when>
        <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
          try {
           vect1DDouble = UALLowLevel.getVect1DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect1DDouble.getDim() != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DDouble.getElementAt(i);
           } catch(Exception exc)
          {           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_DOUBLE;
          }
        </xsl:when>
        <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
          try {
           vect1DString = UALLowLevel.getVect1DString(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect1DString.getDim() != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DString.getElementAt(i);
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
        </xsl:when>
        
        <xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
          try {
           vect2DDouble = UALLowLevel.getVect2DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect2DDouble.getDim(1) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect2DDouble.getElementAt(i);
           } catch(Exception exc)           
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
          try {
           vect2DInt = UALLowLevel.getVect2DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect2DInt.getDim(1) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect2DInt.getElementAt(i);
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@name='vecdbl_type'">
          try {
           vect2DDouble = UALLowLevel.getVect2DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect2DDouble.getDim(1) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect2DDouble.getElementAt(i);
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
       
        <xsl:when test="@data_type='FLT_2D'">
          try {
           vect3DDouble = UALLowLevel.getVect3DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect3DDouble.getDim(2) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect3DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@name='matdbl_type'">
          try {
           vect3DDouble = UALLowLevel.getVect3DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect3DDouble.getDim(2) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect3DDouble.getElementAt(i);
           } catch(Exception exc)
                  {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
        </xsl:when>
        <xsl:when test="@data_type='INT_2D'">
          try {
           vect3DInt = UALLowLevel.getVect3DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect3DInt.getDim(2) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect3DInt.getElementAt(i);
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
       
        <xsl:when test="@name='array3ddbl_type'">
          try {
           vect4DDouble = UALLowLevel.getVect4DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect4DDouble.getDim(3) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect4DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@data_type='FLT_3D'">
          try {
           vect4DDouble = UALLowLevel.getVect4DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect4DDouble.getDim(3) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect4DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@data_type='INT_3D'">
          try {
           vect4DInt = UALLowLevel.getVect4DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect4DInt.getDim(3) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect4DInt.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
      
        <xsl:when test="@name='array4ddbl_type'">
          try {
           vect5DDouble = UALLowLevel.getVect5DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect5DDouble.getDim(4) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect5DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@data_type='FLT_4D'">
          try {
           vect5DDouble = UALLowLevel.getVect5DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect5DDouble.getDim(4) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect5DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@name='array4dint_type'">
          try {
           vect5DInt = UALLowLevel.getVect5DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect5DInt.getDim(4) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect5DInt.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
      
        <xsl:when test="@name='array5ddbl_type'">
          try {
           vect6DDouble = UALLowLevel.getVect6DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect6DDouble.getDim(5) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect6DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@data_type='FLT_5D'">
          try {
           vect6DDouble = UALLowLevel.getVect6DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect6DDouble.getDim(5) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect6DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@name='array5dint_type'">
          try {
           vect6DInt = UALLowLevel.getVect6DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect6DInt.getDim(5) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect6DInt.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>

        <xsl:when test="@name='array6ddbl_type'">
          try {
           vect7DDouble = UALLowLevel.getVect7DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect7DDouble.getDim(6) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect7DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@data_type='FLT_6D'">
          try {
           vect7DDouble = UALLowLevel.getVect7DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect7DDouble.getDim(6) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect7DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@name='array6dint_type'">
          try {
           vect7DInt = UALLowLevel.getVect6DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect7DInt.getDim(6) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect7DInt.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>

        <!--========== Arrays of structures ==========-->
        <xsl:when test="@data_type='struct_array'">
          <!-- Generate the full name of the subclass from the field path -->
          <xsl:variable name="fulltypepath">
            <xsl:call-template name="BUILD_TYPENAME">
              <xsl:with-param name="typepath"><xsl:value-of select = "@path"/></xsl:with-param >
            </xsl:call-template>
          </xsl:variable>
          
          {/*     Timed array of structure     */
            /*    Read timed content     */
            int obj_all_times;
            try {
              obj_all_times = UALLowLevel.getObject(expIdx, path, "<xsl:value-of select = "@path"/>", 1); // read the whole timed block
              if (UALLowLevel.getObjectDim(expIdx,obj_all_times) != numSamples) {  // object must contain the right number of times
                throw new UALException("Error in get: array of structures is missing time slices");
              }
              for (int i0 = 0; i0 &lt; numSamples; i0++) {  // fill every time slice
                int obj1;
                obj1 = UALLowLevel.getObjectFromObject(expIdx,obj_all_times, "ALLTIMES", i0);  // extract a single time
                if (UALLowLevel.getObjectDim(expIdx,obj1) != 0) // empty arrays of struct must remain set to null
                  idss[i0].<xsl:value-of select = "translate(@path,'/','.')"/> = new <xsl:value-of select = "$fulltypepath"/>[UALLowLevel.getObjectDim(expIdx,obj1)];
                get_timed_<xsl:value-of select = "translate(@path,'/','_')"/>(expIdx,idss[i0].<xsl:value-of select = "translate(@path,'/','.')"/>,obj1);
              }
              UALLowLevel.releaseObject(expIdx,obj_all_times);
            } catch(UALException exc) {
              throw new UALException("Error in get: for <xsl:value-of select = "translate(@path,'/','.')"/> " + exc);
            }
            
            <!--  The following is not very efficient because we re-parse the object to fill every time slice.
                  However, this is the same as for timed content. Moreover it is not the most common case -->
            /*    Read non-timed content    */
            int obj1;
            try {
              obj1 = UALLowLevel.getObject(expIdx, path, "<xsl:value-of select = "@path"/>", 0); // read the whole non-timed block
              for (int i0 = 0; i0 &lt; numSamples; i0++) {  // fill every time slice
                // must have same number of non-timed elements and timed elements
                if (UALLowLevel.getObjectDim(expIdx,obj1) != 0 &amp;&amp; (idss[i0].<xsl:value-of select = "translate(@path,'/','.')"/> == null || idss[i0].<xsl:value-of select = "translate(@path,'/','.')"/>.length != UALLowLevel.getObjectDim(expIdx,obj1))) {
                  throw new UALException("Error in get: array of structures has different number of timed and nontimed elements");
                }
                get_nontimed_<xsl:value-of select = "translate(@path,'/','_')"/>(expIdx,idss[i0].<xsl:value-of select = "translate(@path,'/','.')"/>,obj1);
              }
              UALLowLevel.releaseObject(expIdx,obj1);
            } catch(UALException exc) {
//GABRIELE JUNE 2012 HANDLE EMPTY NONTIMED OBEJCT PARTS
              //throw new UALException("Error in get: for <xsl:value-of select = "translate(@path,'/','.')"/> " + exc);
            }
          }
        </xsl:when>
          
        <!--========== Regular structures ==========-->
        <xsl:when test="@data_type='structure'">
          <xsl:apply-templates select = "field" mode = "GET_FULL"/>
        </xsl:when>
      </xsl:choose>
    </xsl:when>
    
    <!-- Time-independent field -->
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
          try {
          stringValue = UALLowLevel.getString(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = stringValue;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
          try {
          intValue = UALLowLevel.getInt(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = intValue;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_INT;
            }
       </xsl:when>
        <xsl:when test="@name='xs:boolean'">
          try {
          booleanValue = UALLowLevel.getBoolean(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = booleanValue;
            } catch(Exception exc){}
      </xsl:when>
        <xsl:when test="@name='xs:double'">
          try {
          doubleValue = UALLowLevel.getDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = doubleValue;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_DOUBLE;
            }
       </xsl:when>
        <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
          try {
          doubleValue = UALLowLevel.getDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = doubleValue;
          } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_DOUBLE;
            }
       </xsl:when>
       
        <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
          try {
          vect1DString = UALLowLevel.getVect1DString(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DString;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
         </xsl:when>
        <xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
          try {
          vect1DDouble = UALLowLevel.getVect1DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DDouble;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
          try {
          vect1DInt = UALLowLevel.getVect1DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DInt;
            } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
        </xsl:when>
        <xsl:when test="@name='vecdbl_type'">
          try {
          vect1DDouble = UALLowLevel.getVect1DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DDouble;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
       
        <xsl:when test="@data_type='FLT_2D'">
          try {
          vect2DDouble = UALLowLevel.getVect2DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect2DDouble;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@data_type='INT_2D'">
          try {
          vect2DInt = UALLowLevel.getVect2DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect2DInt;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@name='matdbl_type'">
          try {
          vect2DDouble = UALLowLevel.getVect2DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect2DDouble;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
       
        <xsl:when test="@data_type='FLT_3D'">
          try {
          vect3DDouble = UALLowLevel.getVect3DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect3DDouble;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@name='array3ddbl_type'">
          try {
          vect3DDouble = UALLowLevel.getVect3DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect3DDouble;
           }catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@data_type='INT_3D'">
          try {
          vect3DInt = UALLowLevel.getVect3DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect3DInt;
            } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
       
       <xsl:when test="@data_type='FLT_4D'">
          try {
          vect4DDouble = UALLowLevel.getVect4DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect4DDouble;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@name='array4ddbl_type'">
          try {
          vect4DDouble = UALLowLevel.getVect4DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect4DDouble;
           }catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@name='array4dint_type'">
          try {
          vect4DInt = UALLowLevel.getVect4DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect4DInt;
            } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
        </xsl:when>
        
        <xsl:when test="@data_type='FLT_5D'">
          try {
          vect5DDouble = UALLowLevel.getVect5DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect5DDouble;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@name='array5ddbl_type'">
          try {
          vect5DDouble = UALLowLevel.getVect5DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect5DDouble;
           }catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@name='array5dint_type'">
          try {
          vect5DInt = UALLowLevel.getVect5DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect5DInt;
            } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
        </xsl:when>

        <xsl:when test="@data_type='FLT_6D'">
          try {
          vect6DDouble = UALLowLevel.getVect6DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect6DDouble;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@name='array6ddbl_type'">
          try {
          vect6DDouble = UALLowLevel.getVect6DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect6DDouble;
           }catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@name='array6dint_type'">
          try {
          vect6DInt = UALLowLevel.getVect6DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect6DInt;
            } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
        </xsl:when>

        <!--========== Arrays of structures ==========-->
        <xsl:when test="@data_type='struct_array'">
          <!-- Generate the full name of the subclass from the field path -->
          <xsl:variable name="fulltypepath">
            <xsl:call-template name="BUILD_TYPENAME">
              <xsl:with-param name="typepath"><xsl:value-of select = "@path"/></xsl:with-param >
            </xsl:call-template>
          </xsl:variable>
          
          <!--  The following is not very efficient because we re-parse the object to fill every time slice.
                However, this is the same as for timed content. Moreover it is not the most common case -->
          {/*    Non-timed array of structure    */
            int obj1;
            try {
              obj1 = UALLowLevel.getObject(expIdx, path, "<xsl:value-of select = "@path"/>", 0); // read the whole block
              for (int i0 = 0; i0 &lt; numSamples; i0++) {  // fill every time slice
                if (UALLowLevel.getObjectDim(expIdx,obj1) != 0) // empty arrays of struct must remain set to null
                  idss[i0].<xsl:value-of select = "translate(@path,'/','.')"/> = new <xsl:value-of select = "$fulltypepath"/>[UALLowLevel.getObjectDim(expIdx,obj1)];
                for (int i1 = 0; i1 &lt; UALLowLevel.getObjectDim(expIdx,obj1); i1++) {  // process array elements
                  idss[i0].<xsl:value-of select = "translate(@path,'/','.')"/>[i1] = new <xsl:value-of select = "$fulltypepath"/>();
                  <xsl:apply-templates select = "field" mode = "GET_FROM_OBJECT">
                    <xsl:with-param name="level">1</xsl:with-param>
                    <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
                    <xsl:with-param name="idxpath">idss[i0].<xsl:value-of select = "translate(@path,'/','.')"/>[i1]</xsl:with-param>
                    <xsl:with-param name="timed">no</xsl:with-param>
                  </xsl:apply-templates>
                }
              }
              UALLowLevel.releaseObject(expIdx,obj1);
            } catch (Exception exc) {
              for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
          }
        </xsl:when>
        
        <!--========== Regular structures ==========-->
        <xsl:when test="@data_type='structure'">
          <xsl:apply-templates select = "field" mode = "GET_FULL"/>
        </xsl:when>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!--=================================================-->
<!--            get fields from an object            -->
<!--=================================================-->

<xsl:template match = "field" mode = "GET_FROM_OBJECT">
  <xsl:param name="level"/>     <!-- recursion level -->
  <xsl:param name="objpath"/>   <!-- path inside the object -->
  <xsl:param name="idxpath"/>   <!-- full java path including indices -->
  <xsl:param name="timed"/>     <!-- are we looking for timed or non-timed fields? -->
  
  <!-- build the path of the current field inside the object -->
  <xsl:param name="currentobjpath"><xsl:value-of select="$objpath"/>/<xsl:value-of select="@name"/></xsl:param>
  
  <!-- build the complete path of the current field -->
  <xsl:param name="currentidxpath"><xsl:value-of select="$idxpath"/>.<xsl:value-of select="@name"/></xsl:param>
  
  <!-- Generate the full name of the subclass from the field path -->
  <xsl:param name="fulltypepath">
    <xsl:call-template name="BUILD_TYPENAME">
      <xsl:with-param name="typepath"><xsl:value-of select = "@path"/></xsl:with-param >
    </xsl:call-template>
  </xsl:param>

  <xsl:choose>
    <!--========== Arrays of structures ==========-->
    <xsl:when test="@data_type='struct_array'">
      <xsl:if test="@timed='yes' or $timed='no'">  <!-- Non-timed struct_array must not appear in the timed section -->
        { /*    Array of structure     */
          int obj<xsl:value-of select="$level + 1"/>;
          try {
            obj<xsl:value-of select="$level + 1"/> = UALLowLevel.getObjectFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select = "$currentobjpath"/>", i<xsl:value-of select="$level"/>);
            if (<xsl:value-of select="$currentidxpath"/> != null) { // does this array already exist? (timed and non timed parts can share the same array)
              if (UALLowLevel.getObjectDim(expIdx,obj<xsl:value-of select="$level + 1"/>) != 0 &amp;&amp; <xsl:value-of select="$currentidxpath"/>.length != UALLowLevel.getObjectDim(expIdx,obj<xsl:value-of select="$level + 1"/>)) { // then it must have the right number of elements
                throw new UALException("Error in get: array of structures has different number of timed and nontimed elements");
              }
            } else { // else allocate it
              if (UALLowLevel.getObjectDim(expIdx,obj<xsl:value-of select="$level + 1"/>) != 0) // empty arrays of struct must remain set to null
                <xsl:value-of select="$currentidxpath"/> = new <xsl:value-of select = "$fulltypepath"/>[UALLowLevel.getObjectDim(expIdx,obj<xsl:value-of select="$level + 1"/>)];
              for (int i<xsl:value-of select="$level + 1"/> = 0; i<xsl:value-of select="$level + 1"/> &lt; UALLowLevel.getObjectDim(expIdx,obj<xsl:value-of select="$level + 1"/>); i<xsl:value-of select="$level + 1"/>++) {
                <xsl:value-of select="$currentidxpath"/>[i<xsl:value-of select="$level + 1"/>] = new <xsl:value-of select = "$fulltypepath"/>();
              }
            }
            for (int i<xsl:value-of select="$level + 1"/> = 0; i<xsl:value-of select="$level + 1"/> &lt; UALLowLevel.getObjectDim(expIdx,obj<xsl:value-of select="$level + 1"/>); i<xsl:value-of select="$level + 1"/>++) {
              <xsl:apply-templates select = "field" mode = "GET_FROM_OBJECT">
                <xsl:with-param name="level"><xsl:value-of select="$level + 1"/></xsl:with-param>
                <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
                <xsl:with-param name="idxpath"><xsl:value-of select="$currentidxpath"/>[i<xsl:value-of select="$level + 1"/>]</xsl:with-param>
                <xsl:with-param name="timed"><xsl:value-of select="$timed"/></xsl:with-param>
              </xsl:apply-templates>
            }
          } catch(UALException exc) {
                throw new UALException("Error in get: for <xsl:value-of select = "translate(@path,'/','.')"/> " + exc);
          }
        }
      </xsl:if>
    </xsl:when>
        
    <!--========== Regular structure ==========-->
    <xsl:when test="@data_type='structure'">
      <xsl:apply-templates select = "field" mode = "GET_FROM_OBJECT">
        <xsl:with-param name="level"><xsl:value-of select="$level"/></xsl:with-param>
        <xsl:with-param name="objpath"><xsl:value-of select="$currentobjpath"/></xsl:with-param>
        <xsl:with-param name="idxpath"><xsl:value-of select="$currentidxpath"/></xsl:with-param>
        <xsl:with-param name="timed"><xsl:value-of select="$timed"/></xsl:with-param>
      </xsl:apply-templates>
    </xsl:when>
    
    <xsl:otherwise>
      <!--========== select either timed or non-timed fields ==========-->
      <xsl:if test="@timed=$timed">
        <xsl:choose>
          <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
//GABRIELE MARCH 2011            try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getStringFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
          </xsl:when>
          <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getIntFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = EMPTY_INT;}
          </xsl:when>
          <xsl:when test="@name='xs:boolean'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getBooleanFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){}
          </xsl:when>
          <xsl:when test="@name='xs:double'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = EMPTY_DOUBLE;}
          </xsl:when>
          <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = EMPTY_DOUBLE;}
          </xsl:when>
        
          <xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect1DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
          </xsl:when>
          <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect1DStringFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
          </xsl:when>
          <xsl:when test="@name='vecdbl_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect1DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
//          </xsl:when>
          <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect1DIntFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
          </xsl:when>
          
          <xsl:when test="@data_type='FLT_2D'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect2DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
          <xsl:when test="@name='matdbl_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect2DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
          <xsl:when test="@data_type='INT_2D'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect2DIntFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        
          <xsl:when test="@data_type='FLT_3D'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect3DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        <xsl:when test="@name='array3ddbl_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect3DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        <xsl:when test="@data_type='INT_3D'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect3DIntFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        
          <xsl:when test="@data_type='FLT_4D'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect4DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        <xsl:when test="@name='array4ddbl_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect4DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        <xsl:when test="@name='array4dint_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect4DIntFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        
          <xsl:when test="@data_type='FLT_5D'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect5DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        <xsl:when test="@name='array5ddbl_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect5DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        <xsl:when test="@name='array5dint_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect5DIntFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
  
          <xsl:when test="@data_type='FLT_6D'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect6DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        <xsl:when test="@name='array6ddbl_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect6DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        <xsl:when test="@name='array6dint_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect6DIntFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
  
        </xsl:choose>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
  
</xsl:template>

<!--=================================================-->
<!--      Put field in a full time-dependent IDS     -->
<!--=================================================-->

<xsl:template match = "field" mode = "PUT_SINGLE">
<xsl:param name="variable_path"/>
<xsl:param name="mds_path"/>
<xsl:param name="non_timed"/>
<xsl:if test="$non_timed !='yes' or @type !='SIGNAL' or not(@type) or @data_type='structure' or @data_type='struct_array'"> <!-- This skips the routine for timed fields when using this template in PUT_NON_TIMED mode -->
      <xsl:choose>
        <!--========== Regular structures ==========-->
        <xsl:when test="@data_type='structure'">
<!--          <xsl:apply-templates select = "field" mode = "PUT_SINGLE"/> -->
<xsl:choose>
<xsl:when test="$variable_path">
<xsl:apply-templates select="field" mode="PUT_SINGLE">
<xsl:with-param name ="variable_path" select="concat($variable_path,'.',@name)"/>
<xsl:with-param name ="mds_path" select="concat($mds_path,' + &quot;/',@name,'&quot;')"/>
<xsl:with-param name="non_timed" select="$non_timed"/>
</xsl:apply-templates>
</xsl:when>
<xsl:otherwise>
<xsl:apply-templates select="field" mode="PUT_SINGLE">
<xsl:with-param name ="variable_path" select="@name"/>
<xsl:with-param name ="mds_path" select="concat('&quot;',@name,'&quot;')"/>
<xsl:with-param name="non_timed" select="$non_timed"/>
</xsl:apply-templates>
</xsl:otherwise>
</xsl:choose>
        </xsl:when>

        <!--========== Arrays of structures ==========-->
        <xsl:when test="@data_type='struct_array'">
// Put <xsl:value-of select="@path"/>
<xsl:choose>
<xsl:when test="$variable_path">
          if (ids.<xsl:value-of select = "concat($variable_path,'.',@name)"/> != null) {
            UALLowLevel.putInt(expIdx,path, <xsl:value-of select="$mds_path"/>+&quot;/<xsl:value-of select="@name"/>/Shape_of&quot;,
       		ids.<xsl:value-of select="concat($variable_path,'.',@name)"/>.length);
             for (int i<xsl:value-of select = "@name"/> = 0; i<xsl:value-of select = "@name"/>&lt;ids.<xsl:value-of select = "concat($variable_path,'.',@name)"/>.length; i<xsl:value-of select = "@name"/>++){
      <xsl:apply-templates select = "field" mode = "PUT_SINGLE">
<xsl:with-param name ="variable_path" select="concat($variable_path,'.',@name,'[i',@name,']')"/>
<xsl:with-param name="mds_path" select="concat($mds_path,'+&quot;/',@name,'/&quot; + Integer.toString(i',@name,'+1).trim()')"/>
<xsl:with-param name="non_timed" select="$non_timed"/>
</xsl:apply-templates>
             }
          }
</xsl:when>
<xsl:otherwise>
          if (ids.<xsl:value-of select = "translate(@path,'/','.')"/> != null) {
            UALLowLevel.putInt(expIdx,path, &quot;<xsl:value-of select="@name"/>/Shape_of&quot;,
       		ids.<xsl:value-of select="@name"/>.length);
            for (int i<xsl:value-of select = "@name"/> = 0; i<xsl:value-of select = "@name"/>&lt;ids.<xsl:value-of select = "@name"/>.length; i<xsl:value-of select = "@name"/>++){
      <xsl:apply-templates select = "field" mode = "PUT_SINGLE">
<xsl:with-param name ="variable_path" select="concat(@name,'[i',@name,']')"/>
<xsl:with-param name="mds_path" select="concat('&quot;',@name,'/&quot; + Integer.toString(i',@name,'+1).trim()')"/>
<xsl:with-param name="non_timed" select="$non_timed"/>
</xsl:apply-templates>
            }
          }
</xsl:otherwise>
</xsl:choose>
        </xsl:when>

        <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
<!--      UALLowLevel.putString(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>); -->
	<xsl:call-template name="putScalar"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">String</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
<!--      UALLowLevel.putInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>); -->
	<xsl:call-template name="putScalar"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Int</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='xs:boolean'">
<!--      UALLowLevel.putBoolean(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>); -->
	<xsl:call-template name="putScalar"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Boolean</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='xs:double'">
<!--      UALLowLevel.putDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>); -->
	<xsl:call-template name="putScalar"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Double</xsl:with-param>
	</xsl:call-template>
	</xsl:when>
        <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
<!--       UALLowLevel.putDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>); -->
	<xsl:call-template name="putScalar"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Double</xsl:with-param>
	</xsl:call-template>
	</xsl:when>

	<xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
<!--      UALLowLevel.putVect1DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect1DDouble</xsl:with-param>
	</xsl:call-template>
	</xsl:when>

        <xsl:when test="@name='vecdbl_type'">
<!--      UALLowLevel.putVect1DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect1DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
 <!--     UALLowLevel.putVect1DInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect1DInt</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
<!--      UALLowLevel.putVect1DString(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
 	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect1DString</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        
        <xsl:when test="@data_type='FLT_2D'">
<!--      UALLowLevel.putVect2DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect2DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='INT_2D'">
<!--      UALLowLevel.putVect2DInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect2DInt</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='matdbl_type'">
<!--      UALLowLevel.putVect2DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect2DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        
        <xsl:when test="@data_type='FLT_3D'">
<!--      UALLowLevel.putVect3DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect3DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='INT_3D'">
<!--      UALLowLevel.putVect3DInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect3DInt</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='array3ddbl_type'">
<!--      UALLowLevel.putVect3DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect3DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        
        <xsl:when test="@data_type='FLT_4D'">
<!--      UALLowLevel.putVect4DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect4DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='array4dint_type'">
<!--      UALLowLevel.putVect4DInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect4DInt</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='array4ddbl_type'">
<!--      UALLowLevel.putVect4DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect4DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        
        <xsl:when test="@data_type='FLT_5D'">
 <!--     UALLowLevel.putVect5DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect5DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='array5dint_type'">
<!--      UALLowLevel.putVect5DInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect5DInt</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='array5ddbl_type'">
<!--      UALLowLevel.putVect5DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect5DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_6D'">
<!--      UALLowLevel.putVect6DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect6DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='array6dint_type'">
<!--      UALLowLevel.putVect6DInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect6DInt</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='array6ddbl_type'">
<!--      UALLowLevel.putVect6DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putSignal"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect6DDouble</xsl:with-param>
	</xsl:call-template>
       </xsl:when>

	<xsl:otherwise>
// Put <xsl:value-of select="@path"/> : PROBLEM : UNIDENTIFIED TYPE !!! <!-- for comment only -->
	</xsl:otherwise>

      </xsl:choose>
   </xsl:if>
</xsl:template>


<!--=================================================-->
<!--        Put time-dependent field in a slice      -->
<!--=================================================-->
<xsl:template match = "field" mode = "PUT_SLICE">
<xsl:param name="variable_path"/>
<xsl:param name="mds_path"/>
<xsl:if test="@type ='SIGNAL' or @data_type='structure' or @data_type='struct_array'"> <!-- This skips the routine for non timed fields -->
      <xsl:choose>
        <!--========== Regular structures ==========-->
        <xsl:when test="@data_type='structure'">
<!--          <xsl:apply-templates select = "field" mode = "PUT_SLICE"/> -->
<xsl:choose>
<xsl:when test="$variable_path">
<xsl:apply-templates select="field" mode="PUT_SLICE">
<xsl:with-param name ="variable_path" select="concat($variable_path,'.',@name)"/>
<xsl:with-param name ="mds_path" select="concat($mds_path,' + &quot;/',@name,'&quot;')"/>
</xsl:apply-templates>
</xsl:when>
<xsl:otherwise>
<xsl:apply-templates select="field" mode="PUT_SLICE">
<xsl:with-param name ="variable_path" select="@name"/>
<xsl:with-param name ="mds_path" select="concat('&quot;',@name,'&quot;')"/>
</xsl:apply-templates>
</xsl:otherwise>
</xsl:choose>
        </xsl:when>

        <!--========== Arrays of structures ==========-->
        <xsl:when test="@data_type='struct_array'">
// Put <xsl:value-of select="@path"/>
<xsl:choose>
<xsl:when test="$variable_path">
          if (ids.<xsl:value-of select = "concat($variable_path,'.',@name)"/> != null) {
            UALLowLevel.putInt(expIdx,path, <xsl:value-of select="$mds_path"/>+&quot;/<xsl:value-of select="@name"/>/Shape_of&quot;,
       		ids.<xsl:value-of select="concat($variable_path,'.',@name)"/>.length);
             for (int i<xsl:value-of select = "@name"/> = 0; i<xsl:value-of select = "@name"/>&lt;ids.<xsl:value-of select = "concat($variable_path,'.',@name)"/>.length; i<xsl:value-of select = "@name"/>++){
      <xsl:apply-templates select = "field" mode = "PUT_SLICE">
<xsl:with-param name ="variable_path" select="concat($variable_path,'.',@name,'[i',@name,']')"/>
<xsl:with-param name="mds_path" select="concat($mds_path,'+&quot;/',@name,'/&quot; + Integer.toString(i',@name,'+1).trim()')"/>
</xsl:apply-templates>
             }
          }
</xsl:when>
<xsl:otherwise>
          if (ids.<xsl:value-of select = "translate(@path,'/','.')"/> != null) {
            UALLowLevel.putInt(expIdx,path, &quot;<xsl:value-of select="@name"/>/Shape_of&quot;,
       		ids.<xsl:value-of select="@name"/>.length);
            for (int i<xsl:value-of select = "@name"/> = 0; i<xsl:value-of select = "@name"/>&lt;ids.<xsl:value-of select = "@name"/>.length; i<xsl:value-of select = "@name"/>++){
      <xsl:apply-templates select = "field" mode = "PUT_SLICE">
<xsl:with-param name ="variable_path" select="concat(@name,'[i',@name,']')"/>
<xsl:with-param name="mds_path" select="concat('&quot;',@name,'/&quot; + Integer.toString(i',@name,'+1).trim()')"/>
</xsl:apply-templates>
            }
          }
</xsl:otherwise>
</xsl:choose>
        </xsl:when>

        <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
<!--      UALLowLevel.putStringSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, ids.time); -->
	<xsl:call-template name="putScalarSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">StringSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
	<xsl:call-template name="putScalarSlice"> <xsl:with-param name="variable_path" select="$variable_path"/>      		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">IntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='xs:boolean'">
	<xsl:call-template name="putScalarSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">BooleanSlice</xsl:with-param>
	</xsl:call-template>Slice
        </xsl:when>

        <xsl:when test="@name='xs:double'">
	<xsl:call-template name="putScalarSlice"> <xsl:with-param name="variable_path" select="$variable_path"/>
	<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">DoubleSlice</xsl:with-param>
	</xsl:call-template>
	</xsl:when>

        <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
	<xsl:call-template name="putScalarSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">DoubleSlice</xsl:with-param>
	</xsl:call-template>
	</xsl:when>

	<xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect1DDoubleSlice</xsl:with-param>
	</xsl:call-template>
	</xsl:when>

        <xsl:when test="@name='vecdbl_type'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect1DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect1DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
 	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect1DStringSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        
        <xsl:when test="@data_type='FLT_2D'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect2DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='INT_2D'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect2DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='matdbl_type'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect2DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        
        <xsl:when test="@data_type='FLT_3D'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect3DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='INT_3D'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect3DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array3ddbl_type'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect3DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        
        <xsl:when test="@data_type='FLT_4D'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect4DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array4dint_type'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect4DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array4ddbl_type'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect4DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        
        <xsl:when test="@data_type='FLT_5D'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect5DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array5dint_type'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect5DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array5ddbl_type'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect5DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_6D'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect6DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array6dint_type'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect6DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array6ddbl_type'">
	<xsl:call-template name="putSignalSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect6DDoubleSlice</xsl:with-param>
	</xsl:call-template>
       </xsl:when>

	<xsl:otherwise>
// Put <xsl:value-of select="@path"/> : PROBLEM : UNIDENTIFIED TYPE !!! <!-- for comment only -->
	</xsl:otherwise>

      </xsl:choose>
   </xsl:if>
</xsl:template>



<!--=================================================-->
<!--               Replace last slice                -->
<!--=================================================-->

<xsl:template match = "field" mode = "REPLACE_LAST_SLICE">
  <xsl:choose>
    
    <!--========== Regular structures ==========-->
    <xsl:when test="@data_type='structure'">
      <xsl:apply-templates select = "field" mode = "REPLACE_LAST_SLICE"/>
    </xsl:when>
    
    <xsl:otherwise>
      <xsl:if test = "@timed = 'yes'">
        <xsl:choose>
          <!--========== Arrays of structures ==========-->
          <xsl:when test="@data_type='struct_array'">
            { /*     Array of structure    */
              int obj1 = UALLowLevel.beginObject(expIdx,-1,0,path+"/<xsl:value-of select = "@path"/>",TIMED);
              for (int i1 = 0; i1 &lt; ids.<xsl:value-of select = "translate(@path,'/','.')"/>.length; i1++) {
                <xsl:apply-templates select = "field" mode = "PUT_IN_OBJECT">
                  <xsl:with-param name="level">1</xsl:with-param>
                  <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
                  <xsl:with-param name="idxpath">ids.<xsl:value-of select = "translate(@path,'/','.')"/>[i1]</xsl:with-param>
                  <xsl:with-param name="timed">yes</xsl:with-param>
                </xsl:apply-templates>
              }
              UALLowLevel.replaceLastObjectSlice(expIdx, path, "<xsl:value-of select = "@path"/>", obj1);
            }
          </xsl:when>
          
          <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
            UALLowLevel.replaceLastStringSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
              if(ids.<xsl:value-of select = "translate(@path,'/','.')"/> != EMPTY_INT)
                  UALLowLevel.replaceLastIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@name='xs:boolean'">
              if(ids.<xsl:value-of select = "translate(@path,'/','.')"/> != EMPTY_INT)
                  UALLowLevel.replaceLastBooleanSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@name='xs:double'">
              if(ids.<xsl:value-of select = "translate(@path,'/','.')"/> != EMPTY_DOUBLE)
                  UALLowLevel.replaceLastDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
              if(ids.<xsl:value-of select = "translate(@path,'/','.')"/> != EMPTY_DOUBLE)
                  UALLowLevel.replaceLastDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          
          <xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
            UALLowLevel.replaceLastVect1DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@name='vecdbl_type'">
            UALLowLevel.replaceLastVect1DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
            UALLowLevel.replaceLastVect1DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          
          <xsl:when test="@data_type='FLT_2D'">
            UALLowLevel.replaceLastVect2DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@data_type='INT_2D'">
            UALLowLevel.replaceLastVect2DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@name='matdbl_type'">
            UALLowLevel.replaceLastVect2DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          
          <xsl:when test="@data_type='FLT_3D'">
            UALLowLevel.replaceLastVect3DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@data_type='INT_3D'">
            UALLowLevel.replaceLastVect3DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@name='array3ddbl_type'">
            UALLowLevel.replaceLastVect3DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          
          <xsl:when test="@data_type='FLT_4D'">
            UALLowLevel.replaceLastVect4DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@name='array4dint_type'">
            UALLowLevel.replaceLastVect4DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@name='array4ddbl_type'">
            UALLowLevel.replaceLastVect4DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          
          <xsl:when test="@data_type='FLT_5D'">
            UALLowLevel.replaceLastVect5DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@name='array5dint_type'">
            UALLowLevel.replaceLastVect5DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@name='array5ddbl_type'">
            UALLowLevel.replaceLastVect5DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          
          <xsl:when test="@data_type='FLT_6D'">
            UALLowLevel.replaceLastVect6DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@name='array6dint_type'">
            UALLowLevel.replaceLastVect6DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@name='array6ddbl_type'">
            UALLowLevel.replaceLastVect6DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          
        </xsl:choose>
  
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!--=================================================-->
<!--       Put field in a time-independent IDS       -->
<!--=================================================-->

<xsl:template match = "field" mode = "PUT">
  <xsl:choose>
    <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
      UALLowLevel.putString(expIdx, path, "<xsl:value-of select="@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
    </xsl:when>
    <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
      UALLowLevel.putInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
    </xsl:when>
    <xsl:when test="@name='xs:boolean'">
      UALLowLevel.putBoolean(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
    </xsl:when>
    <xsl:when test="@name='xs:double'">
      UALLowLevel.putDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
    </xsl:when>
    <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
      UALLowLevel.putDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
    </xsl:when>
    
    <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
      UALLowLevel.putVect1DString(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
      UALLowLevel.putVect1DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@name='vecdbl_type'">
      UALLowLevel.putVect1DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
      UALLowLevel.putVect1DInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    
    <xsl:when test="@data_type='FLT_2D'">
      UALLowLevel.putVect2DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@name='matdbl_type'">
      UALLowLevel.putVect2DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@data_type='INT_2D'">
      UALLowLevel.putVect2DInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    
    <xsl:when test="@data_type='FLT_3D'">
      UALLowLevel.putVect3DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@name='array3ddbl_type'">
      UALLowLevel.putVect3DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@data_type='INT_3D'">
      UALLowLevel.putVect3DInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    
    <xsl:when test="@data_type='FLT_4D'">
      UALLowLevel.putVect4DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@name='array4ddbl_type'">
      UALLowLevel.putVect4DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@name='array4dint_type'">
      UALLowLevel.putVect4DInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    
    <xsl:when test="@data_type='FLT_5D'">
      UALLowLevel.putVect5DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@name='array5ddbl_type'">
      UALLowLevel.putVect5DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@name='array5dint_type'">
      UALLowLevel.putVect5DInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    
   <xsl:when test="@data_type='FLT_6D'">
      UALLowLevel.putVect6DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@name='array6ddbl_type'">
      UALLowLevel.putVect6DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@name='array6dint_type'">
      UALLowLevel.putVect6DInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    
    <!--========== Arrays of structures ==========-->
    <xsl:when test="@data_type='struct_array'">
      { /*     Array of structure     */
        int obj1 = UALLowLevel.beginObject(expIdx,-1,0,path+"/<xsl:value-of select = "@path"/>",NON_TIMED);
        if (ids.<xsl:value-of select = "translate(@path,'/','.')"/> != null) {
          for (int i1 = 0; i1 &lt; ids.<xsl:value-of select = "translate(@path,'/','.')"/>.length; i1++) {
            <xsl:apply-templates select = "field" mode = "PUT_IN_OBJECT">
              <xsl:with-param name="level">1</xsl:with-param>
              <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
              <xsl:with-param name="idxpath">ids.<xsl:value-of select = "translate(@path,'/','.')"/>[i1]</xsl:with-param>
              <xsl:with-param name="timed">no</xsl:with-param>
            </xsl:apply-templates>
          }
        }
        UALLowLevel.putObject(expIdx, path, "<xsl:value-of select = "@path"/>", obj1, 0);
      }
    </xsl:when>
    
    <!--========== Regular structures ==========-->
    <xsl:when test="@data_type='structure'">
      <xsl:apply-templates select = "field" mode = "PUT"/>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<!--=================================================-->
<!--            put fields into an object            -->
<!--=================================================-->

<xsl:template match = "field" mode = "PUT_IN_OBJECT">
  <xsl:param name="level"/>     <!-- recursion level -->
  <xsl:param name="objpath"/>   <!-- path inside the object -->
  <xsl:param name="idxpath"/>   <!-- full java path including indices -->
  <xsl:param name="timed"/>     <!-- are we looking for timed or non-timed fields? -->
  
  <!-- build the path of the current field inside the object -->
  <xsl:param name="currentobjpath"><xsl:value-of select="$objpath"/>/<xsl:value-of select="@name"/></xsl:param>
  <!-- build the complete path of the current field -->
  <xsl:param name="currentidxpath"><xsl:value-of select="$idxpath"/>.<xsl:value-of select="@name"/></xsl:param>
  
  <xsl:choose>
    <!--========== Array of structures ==========-->
    <xsl:when test="@data_type='struct_array'">
      <xsl:if test="@timed='yes' or $timed='no'">  <!-- Non-timed struct_array must not appear in the timed section -->
        { /*     Array of structure    */
      <!-- OH fix -->
      <xsl:choose>
        <xsl:when test="$timed='yes'">
          int obj<xsl:value-of select="$level + 1"/> = UALLowLevel.beginObject(expIdx,obj<xsl:value-of select="$level"/>,i<xsl:value-of select="$level"/>,"<xsl:value-of select="$currentobjpath"/>",TIMED);          
        </xsl:when>
        <xsl:otherwise>
          int obj<xsl:value-of select="$level + 1"/> = UALLowLevel.beginObject(expIdx,obj<xsl:value-of select="$level"/>,i<xsl:value-of select="$level"/>,"<xsl:value-of select="$currentobjpath"/>",NON_TIMED);
        </xsl:otherwise>
      </xsl:choose>
          if (<xsl:value-of select = "$currentidxpath"/> != null) {
            for (int i<xsl:value-of select="$level + 1"/> = 0; i<xsl:value-of select="$level + 1"/> &lt; <xsl:value-of select="$currentidxpath"/>.length; i<xsl:value-of select="$level + 1"/>++) {
              <xsl:apply-templates select = "field" mode = "PUT_IN_OBJECT">
                <xsl:with-param name="level"><xsl:value-of select="$level + 1"/></xsl:with-param>
                <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
                <xsl:with-param name="idxpath"><xsl:value-of select="$currentidxpath"/>[i<xsl:value-of select="$level + 1"/>]</xsl:with-param>
                <xsl:with-param name="timed"><xsl:value-of select="$timed"/></xsl:with-param>
              </xsl:apply-templates>
            }
          }
          UALLowLevel.putObjectInObject(expIdx,obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, obj<xsl:value-of select="$level + 1"/>);
        }
      </xsl:if>
    </xsl:when>
    
    <!--========== Regular structure ==========-->
    <xsl:when test="@data_type='structure'">
      <xsl:apply-templates select = "field" mode = "PUT_IN_OBJECT">
        <xsl:with-param name="level"><xsl:value-of select="$level"/></xsl:with-param>
        <xsl:with-param name="objpath"><xsl:value-of select="$currentobjpath"/></xsl:with-param>
        <xsl:with-param name="idxpath"><xsl:value-of select="$currentidxpath"/></xsl:with-param>
        <xsl:with-param name="timed"><xsl:value-of select="$timed"/></xsl:with-param>
      </xsl:apply-templates>
    </xsl:when>
    
    <!--========== select either timed or non-timed fields ==========-->
    <xsl:otherwise>
      <xsl:if test="@timed=$timed">
        <xsl:choose>
          <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
            UALLowLevel.putStringInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
            UALLowLevel.putIntInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@name='xs:boolean'">
            UALLowLevel.putBooleanInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@name='xs:double'">
            UALLowLevel.putDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
            UALLowLevel.putDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          
          <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
            UALLowLevel.putVect1DStringInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
            UALLowLevel.putVect1DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@name='vecdbl_type'">
            UALLowLevel.putVect1DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
            UALLowLevel.putVect1DIntInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          
          <xsl:when test="@data_type='FLT_2D'">
            UALLowLevel.putVect2DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@name='matdbl_type'">
            UALLowLevel.putVect2DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@data_type='INT_2D'">
            UALLowLevel.putVect2DIntInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          
          <xsl:when test="@data_type='FLT_3D'">
            UALLowLevel.putVect3DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@name='array3ddbl_type'">
            UALLowLevel.putVect3DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@data_type='INT_3D'">
            UALLowLevel.putVect3DIntInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          
          <xsl:when test="@data_type='FLT_4D'">
            UALLowLevel.putVect4DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@name='array4ddbl_type'">
            UALLowLevel.putVect4DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@name='array4dint_type'">
            UALLowLevel.putVect4DIntInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          
          <xsl:when test="@data_type='FLT_5D'">
            UALLowLevel.putVect5DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@name='array5ddbl_type'">
            UALLowLevel.putVect5DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@name='array5dint_type'">
            UALLowLevel.putVect5DIntInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          
        <xsl:when test="@data_type='FLT_6D'">
            UALLowLevel.putVect6DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@name='array6ddbl_type'">
            UALLowLevel.putVect6DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@name='array6dint_type'">
            UALLowLevel.putVect6DIntInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          
          <xsl:when test="@data_type='structure'">
            <xsl:apply-templates select = "field" mode = "PUT"/>
          </xsl:when>
        </xsl:choose>
      </xsl:if>
      
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>


<xsl:template name ="printTimebasepath">
<xsl:if test="@type = 'SIGNAL'">
<xsl:choose>
<xsl:when test="contains(@axis7,'Timebase')"> <xsl:value-of select="@axis7"/></xsl:when>
<xsl:when test="contains(@axis6,'Timebase')"> <xsl:value-of select="@axis6"/></xsl:when>
<xsl:when test="contains(@axis5,'Timebase')"> <xsl:value-of select="@axis5"/></xsl:when>
<xsl:when test="contains(@axis4,'Timebase')"> <xsl:value-of select="@axis4"/></xsl:when>
<xsl:when test="contains(@axis3,'Timebase')"> <xsl:value-of select="@axis3"/></xsl:when>
<xsl:when test="contains(@axis2,'Timebase')"> <xsl:value-of select="@axis2"/></xsl:when>
<xsl:when test="contains(@axis1,'Timebase')"> <xsl:value-of select="@axis1"/></xsl:when>
</xsl:choose>
</xsl:if>
<xsl:if test="@name='Timebase'"><xsl:value-of select="@path"/></xsl:if>  <!-- If the field itself IS Timebase, then it is its own time axis -->
</xsl:template>

<xsl:template name ="printTimebasevariable">
<xsl:if test="@type = 'SIGNAL'">
<xsl:choose>
<xsl:when test="contains(@axis7,'Timebase')"> ids.<xsl:value-of select="translate(@axis7,'/','.')"/></xsl:when>
<xsl:when test="contains(@axis6,'Timebase')"> ids.<xsl:value-of select="translate(@axis6,'/','.')"/></xsl:when>
<xsl:when test="contains(@axis5,'Timebase')"> ids.<xsl:value-of select="translate(@axis5,'/','.')"/></xsl:when>
<xsl:when test="contains(@axis4,'Timebase')"> ids.<xsl:value-of select="translate(@axis4,'/','.')"/></xsl:when>
<xsl:when test="contains(@axis3,'Timebase')"> ids.<xsl:value-of select="translate(@axis3,'/','.')"/></xsl:when>
<xsl:when test="contains(@axis2,'Timebase')"> ids.<xsl:value-of select="translate(@axis2,'/','.')"/></xsl:when>
<xsl:when test="contains(@axis1,'Timebase')"> ids.<xsl:value-of select="translate(@axis1,'/','.')"/></xsl:when>
</xsl:choose>
</xsl:if>
<xsl:if test="@name='Timebase'">ids.<xsl:value-of select="translate(@path,'/','.')"/></xsl:if>  <!-- If the field itself IS Timebase, then it is its own time axis -->
</xsl:template>

<xsl:template name ="printIsTimed">
<xsl:choose><xsl:when test="@type = 'SIGNAL'"> <xsl:value-of select="'true'"/> </xsl:when> <xsl:otherwise> <xsl:value-of select="'false'"/> </xsl:otherwise> </xsl:choose>
</xsl:template>

<xsl:template name ="printPathofTimebase">
<xsl:choose><xsl:when test="@type = 'SIGNAL'"> <xsl:value-of select="'timebasepath.trim()'"/> </xsl:when> <xsl:otherwise>""</xsl:otherwise> </xsl:choose>
</xsl:template>

<xsl:template name ="putSignal">
<xsl:param name="variable_path" />
<xsl:param name="mds_path" />
<xsl:param name="Function" />
// Put <xsl:value-of select="@path"/>
	<xsl:choose>
		<xsl:when test="$variable_path">
   		<xsl:choose>
   			<xsl:when test="@type='SIGNAL'">
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       <!--XSLtest whether this is a Data/Timebase structure, otherwise assume that the Timebasepath attribute from IDSDef is correct-->
       			<xsl:choose>
       				<xsl:when test="(@name='Data' and ../field[@name='Timebase']) or (@name='Timebase' and ../field[@name='Data'])">
       	  timebasepath=<xsl:value-of select="$mds_path"/> + &quot;/Timebase&quot; ;
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.<xsl:value-of select="concat($variable_path,'.Timebase')"/>);
       				</xsl:when>
       				<xsl:otherwise>
       	  timebasepath=&quot;<xsl:call-template name="printTimebasepath"/>&quot; ;
          UALLowLevel.beginIDSPutTimed(expIdx, path, <xsl:call-template name="printTimebasevariable"/>);
       				</xsl:otherwise>
       			</xsl:choose>
   	} else {
          timebasepath="Timebase";
          UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			</xsl:when>
   		</xsl:choose>   
          UALLowLevel.put<xsl:value-of select="$Function"/>(expIdx, path, <xsl:value-of select="$mds_path"/> + &quot;/<xsl:value-of select="@name"/>&quot;, <xsl:call-template name="printPathofTimebase"/>, ids.<xsl:value-of select="concat($variable_path,'.',@name)"/>, <xsl:call-template name="printIsTimed"/>);
   		<xsl:if test="@type='SIGNAL'">
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		</xsl:if>
<!--   	if (ual_debug == "yes") {
	  System.out.println("Put ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> " + ids.<xsl:value-of select="concat($variable_path,'.',@name)"/>.toString()); 
	} -->
		</xsl:when>
		<xsl:otherwise>
   		<xsl:choose>
   			<xsl:when test="@type='SIGNAL'">
   	if (ids.IDS_Properties.Homogeneous_Timebase == 0) {
       	  timebasepath="<xsl:call-template name="printTimebasepath"/>";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, <xsl:call-template name="printTimebasevariable"/>);
   	} else {
          timebasepath="Timebase";
       	  UALLowLevel.beginIDSPutTimed(expIdx, path, ids.Timebase);
   	}   
   			</xsl:when>
   		</xsl:choose>
          UALLowLevel.put<xsl:value-of select="$Function"/>(expIdx,path, "<xsl:value-of select="@path"/>", <xsl:call-template name="printPathofTimebase"/>, ids.<xsl:value-of select="translate(@path,'/','.')"/>, <xsl:call-template name="printIsTimed"/>);
   		<xsl:if test="@type='SIGNAL'">
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		</xsl:if>
<!--
          if (ual_debug == "yes") { 
            System.out.println("Put ids.<xsl:value-of select="translate(@path,'/','.')"/> " + ids.<xsl:value-of select="translate(@path,'/','.')"/>.toString());
          }
-->
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name ="putScalar">
<xsl:param name="variable_path" />
<xsl:param name="mds_path" />
<xsl:param name="Function" />
// Put <xsl:value-of select="@path"/>
	<xsl:choose>
		<xsl:when test="$variable_path">
          UALLowLevel.put<xsl:value-of select="$Function"/>(expIdx, path, <xsl:value-of select="$mds_path"/> + &quot;/<xsl:value-of select="@name"/>&quot;, ids.<xsl:value-of select="concat($variable_path,'.',@name)"/>);
<!--   	if (ual_debug == "yes") {
	  System.out.println("Put ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> " + ids.<xsl:value-of select="concat($variable_path,'.',@name)"/>.toString()); 
	} -->
		</xsl:when>
		<xsl:otherwise>
          UALLowLevel.put<xsl:value-of select="$Function"/>(expIdx,path, "<xsl:value-of select="@path"/>",  ids.<xsl:value-of select="translate(@path,'/','.')"/>);
<!--
          if (ual_debug == "yes") { 
            System.out.println("Put ids.<xsl:value-of select="translate(@path,'/','.')"/> " + ids.<xsl:value-of select="translate(@path,'/','.')"/>.toString());
          }
-->
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>	

<xsl:template name ="putSignalSlice">
<xsl:param name="variable_path" />
<xsl:param name="mds_path" />
<xsl:param name="Function" />
// Put Slice <xsl:value-of select="@path"/>
	<xsl:choose>
		<xsl:when test="$variable_path">
          UALLowLevel.put<xsl:value-of select="$Function"/>(expIdx, path, <xsl:value-of select="$mds_path"/> + &quot;/<xsl:value-of select="@name"/>&quot;, <xsl:call-template name="printPathofTimebase"/>, ids.<xsl:value-of select="concat($variable_path,'.',@name)"/>, ids.Timebase.getElementAt(0));
		</xsl:when>
		<xsl:otherwise>
          UALLowLevel.put<xsl:value-of select="$Function"/>(expIdx,path, "<xsl:value-of select="@path"/>", <xsl:call-template name="printPathofTimebase"/>, ids.<xsl:value-of select="translate(@path,'/','.')"/>, ids.Timebase.getElementAt(0));
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>


<xsl:template name ="putScalarSlice">
<xsl:param name="variable_path" />
<xsl:param name="mds_path" />
<xsl:param name="Function" />
// Put Slice <xsl:value-of select="@path"/>
	<xsl:choose>
		<xsl:when test="$variable_path">
          UALLowLevel.put<xsl:value-of select="$Function"/>(expIdx, path, <xsl:value-of select="$mds_path"/> + &quot;/<xsl:value-of select="@name"/>&quot;, ids.<xsl:value-of select="concat($variable_path,'.',@name)"/>,ids.Timebase.getElementAt(0));
<!--   	if (ual_debug == "yes") {
	  System.out.println("Put ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> " + ids.<xsl:value-of select="concat($variable_path,'.',@name)"/>.toString()); 
	} -->
		</xsl:when>
		<xsl:otherwise>
          UALLowLevel.put<xsl:value-of select="$Function"/>(expIdx,path, "<xsl:value-of select="@path"/>",  ids.<xsl:value-of select="translate(@path,'/','.')"/>,ids.Timebase.getElementAt(0));
<!--
          if (ual_debug == "yes") { 
            System.out.println("Put ids.<xsl:value-of select="translate(@path,'/','.')"/> " + ids.<xsl:value-of select="translate(@path,'/','.')"/>.toString());
          }
-->
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>	

<xsl:template name ="getVariable">
<xsl:param name="variable_path" />
<xsl:param name="mds_path" />
<xsl:param name="Function" />
<xsl:param name="Exception" />
	<xsl:choose>
		<xsl:when test="$variable_path">
          try {
            ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> = UALLowLevel.get<xsl:value-of select="$Function"/>(expIdx, path, <xsl:value-of select="$mds_path"/> + &quot;/<xsl:value-of select="@name"/>&quot;);
          } catch(Exception exc){ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> = <xsl:value-of select="$Exception"/>;}
		</xsl:when>
		<xsl:otherwise>
          try {
            ids.<xsl:value-of select="@name"/> = UALLowLevel.get<xsl:value-of select="$Function"/>(expIdx, path, &quot;<xsl:value-of select="@name"/>&quot;);
          } catch(Exception exc){ids.<xsl:value-of select="@name"/> = <xsl:value-of select="$Exception"/>;}
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name ="getVariableSlice">
<xsl:param name="variable_path" />
<xsl:param name="mds_path" />
<xsl:param name="Function" />
<xsl:param name="SliceFunction" />
<xsl:param name="number" />
<xsl:param name="Exception" />
	<xsl:choose>
		<xsl:when test="$variable_path">
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, <xsl:value-of select="$mds_path"/> + &quot;/<xsl:value-of select="@name"/>&quot;);
	    if (obj.getElementAt(0) > 0) {
              ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> = new <xsl:value-of select="$Function"/>(<xsl:call-template name="printDimensions"><xsl:with-param name="number" select="$number"/></xsl:call-template>);
              ids.<xsl:value-of select="concat($variable_path,'.',@name)"/>.setElementAt(0, UALLowLevel.get<xsl:value-of select="$SliceFunction"/>Slice(expIdx, path, <xsl:value-of select="$mds_path"/> + &quot;/<xsl:value-of select="@name"/>&quot;, <xsl:call-template name="printPathofTimebase"/>, time, interpolMode));
            }
          } catch(Exception exc){ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> = <xsl:value-of select="$Exception"/>;}
		</xsl:when>
		<xsl:otherwise>
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, &quot;<xsl:value-of select="@name"/>&quot;);
	    if (obj.getElementAt(0) > 0) {
              ids.<xsl:value-of select="@name"/> = new <xsl:value-of select="$Function"/>(<xsl:call-template name="printDimensions"><xsl:with-param name="number" select="$number"/></xsl:call-template>);
              ids.<xsl:value-of select="@name"/>.setElementAt(0, UALLowLevel.get<xsl:value-of select="$SliceFunction"/>Slice(expIdx, path, &quot;<xsl:value-of select="@name"/>&quot;, <xsl:call-template name="printPathofTimebase"/>, time, interpolMode));
<!--          ids.<xsl:value-of select="@name"/> = UALLowLevel.get<xsl:value-of select="$Function"/>(expIdx, path, &quot;<xsl:value-of select="@name"/>&quot;);
-->
            }
          } catch(Exception exc){ids.<xsl:value-of select="@name"/> = <xsl:value-of select="$Exception"/>;}
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name ="getStringSlice">
<xsl:param name="variable_path" />
<xsl:param name="mds_path" />
	<xsl:choose>
		<xsl:when test="$variable_path">
          try {
            ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> = new Vect1DString(1);
            ids.<xsl:value-of select="concat($variable_path,'.',@name)"/>.setElementAt(0, UALLowLevel.getStringSlice(expIdx, path, <xsl:value-of select="$mds_path"/> + &quot;/<xsl:value-of select="@name"/>&quot;, <xsl:call-template name="printPathofTimebase"/>, time, interpolMode));
          } catch(Exception exc){ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> = null;}
		</xsl:when>
		<xsl:otherwise>
          try {
            ids.<xsl:value-of select="@name"/> = new Vect1DString(1);
            ids.<xsl:value-of select="@name"/>.setElementAt(0, UALLowLevel.getString(expIdx, path, &quot;/<xsl:value-of select="@name"/>&quot;));
          } catch(Exception exc){ids.<xsl:value-of select="@name"/> = null;}
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name ="printDimensions">
<xsl:param name="number" />
<!-- *** printDimensions <xsl:value-of select="$number" /> **** -->
	<xsl:choose>
		<xsl:when test="$number='0'">1</xsl:when>
		<xsl:when test="$number='1'">obj.getElementAt(0),1</xsl:when>
		<xsl:when test="$number='2'">obj.getElementAt(0),obj.getElementAt(1),1</xsl:when>
		<xsl:when test="$number='3'">obj.getElementAt(0),obj.getElementAt(1),obj.getElementAt(2),1</xsl:when>
		<xsl:when test="$number='4'">obj.getElementAt(0),obj.getElementAt(1),obj.getElementAt(2),obj.getElementAt(3),1</xsl:when>
		<xsl:when test="$number='5'">obj.getElementAt(0),obj.getElementAt(1),obj.getElementAt(2),obj.getElementAt(3),obj.getElementAt(4),1</xsl:when>
		<xsl:when test="$number='6'">obj.getElementAt(0),obj.getElementAt(1),obj.getElementAt(2),obj.getElementAt(3),obj.getElementAt(4),obj.getElementAt(5),1</xsl:when>
		<xsl:when test="$number='7'">obj.getElementAt(0),obj.getElementAt(1),obj.getElementAt(2),obj.getElementAt(3),obj.getElementAt(4),obj.getElementAt(5),obj.getElementAt(6),1</xsl:when>
		<xsl:otherwise></xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>
