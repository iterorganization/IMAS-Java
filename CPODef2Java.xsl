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

<xsl:template match = "/CPOs">
package ualmemory.javainterface;
     public class UALAccess {
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
 public static native void discard(int expIdx, String cpoPath);
 public static native void flush(int expIdx, String cpoPath);

 /**
 *Open the selected database. Missing fields in the database will be retrieved from the reference database.
 * @param name Name of the database (by convention euitm).
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
 * @param name Name of the database (by convention euitm).
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
 * @param name Name of the database (by convention euitm).
 * @param shot Shot number.
 * @param run Run Number.
 * @param refShot Shot number of the reference database.
 * @param runRun Run Number of the reference database.
 * @return the database index to be used in subsequent get/put calls
 * @exception UALException is thrown if the database cannot be open.
 **/
 static public native int createEnv(String name, int shot, int run, int refShot, int refRun, String user, String tokamak, String version) throws UALException;

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
     return createEnv("euitm", shot, run, shot, run, user, machine, dataVersion);
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

	return createEnv("euitm", shot, run, shot, run, user, machine, dataVersion);
}



 static public native int openHdf5(String name, int shot, int run)  throws UALException;
 /**
 *Creates a new database instance.
 * @param name Name of the database (by convention euitm).
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
 * @param name Name of the database (by convention euitm).
 * @param shot Shot number.
 * @param run Run Number.
 **/
 static public native int close(int refIdx);

 static int getShot(int idx){return UALLowLevel.getShot(idx);}
 static int getRun(int idx){return UALLowLevel.getRun(idx);}

 static public int close(int refIdx, String name, int shot, int run){return close(refIdx);}

 /**
 *Get the time base of a CPO.
 * @param idx database index, returned by create or open.
 * @param path name of the CPO
 * @return a vector containing the times of all slices
 * @exception UALException is thrown if the time base cannot be read.
 **/
 static public Vect1DDouble getTime(int idx, String path) throws UALException
{
	UALLowLevel.beginCPOGet(idx,path,true);
	Vect1DDouble time = UALLowLevel.getVect1DDouble(idx,path,"time");
	UALLowLevel.endCPOGet(idx,path);
	return time;
}
 <xsl:apply-templates select = "CPO"/>
 }
</xsl:template>


<!--=================================================-->
<!--                Definition of CPOs               -->
<!--=================================================-->

<xsl:template match = "CPO">

public static class <xsl:value-of select="@type"/>
{
    <xsl:apply-templates select = "field" mode = "DECLARE"/>


<xsl:choose>
  <!--============ Define time-dependent CPOs ============-->
  <xsl:when test = "@timed = 'yes'">
/* First a series of functions to get the arrays of structures */

    <xsl:apply-templates select="field" mode = "GET_OBJECT"/>

 /**
 * Method get retrieves the whole set of <xsl:value-of select="@type"/> CPOs in the open database.
 * @param expIdx The index to the database, returned by UALAccess.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for CPO objects in the database.
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are
 * missing CPO fields.
 * @return the array of stored <xsl:value-of select="@type"/> CPO items. Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if float or double and empty String is string.
 **/
    public static <xsl:value-of select="@type"/> [] get(int expIdx, String path) throws UALException
    {
      int numSamples;
      int intValue;
      double doubleValue;
      String stringValue;
      boolean booleanValue;
      Vect1DString vect1DString;
      Vect1DBoolean vect1DBoolean;
      Vect1DInt vect1DInt;
      Vect2DInt vect2DInt;
      Vect3DInt vect3DInt;
      Vect1DDouble vect1DDouble;
      Vect2DDouble vect2DDouble;
      Vect3DDouble vect3DDouble;
      Vect4DDouble vect4DDouble;
      Vect5DDouble vect5DDouble;
      Vect6DDouble vect6DDouble;
      Vect7DDouble vect7DDouble;


       numSamples = UALLowLevel.beginCPOGet(expIdx, path, true);
       <xsl:value-of select="@type"/> []cpos = new <xsl:value-of select="@type"/> [numSamples];
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
           cpos[i] = new <xsl:value-of select="@type"/> ();
<xsl:apply-templates select = "field" mode = "GET_FULL"/>
      UALLowLevel.endCPOGet(expIdx, path);
      return cpos;
    }

 /**
 * Method getSlice retrieves the  <xsl:value-of select="@type"/> CPO in the open database corresponding to the passed time, based on the selectted interpolation mode.
 * @param expIdx The index to the database, returned by UALAccess.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for CPO objects in the database.
 * @param time The retrieval time.
 * @param interpolMode Defines the selected interpolation. Allowed values are UALAccess.INTERPOLATION, UALAccess.CLOSEST_SAMPLE,
 * UALAccess.PREVIOUS_SAMPLE
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are
 * missing CPO fields.
 * @return the selected <xsl:value-of select="@type"/> CPO. Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if double and empty String is string.
 **/
    public static <xsl:value-of select="@type"/>  getSlice(int expIdx, String path, double time, int interpolMode) throws UALException
    {
        double retTime;
        <xsl:value-of select="@type"/> cpo = new <xsl:value-of select="@type"/> ();
        retTime = UALLowLevel.beginCPOGetSlice(expIdx, path, time);
        cpo.time = retTime;
<xsl:apply-templates select = "field" mode = "GET_SLICE" />
        UALLowLevel.endCPOGetSlice(expIdx, path);
        return cpo;
    }


 /**
 * Method put stores an array of (timed) <xsl:value-of select="@type"/> CPOs in the open database. Each CPO instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by UALAccess.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for CPO objects in the database.
 * @param cpos The array of timed <xsl:value-of select="@type"/> CPOs
 * @exception UALException Issued when data cannot be stored for any reason.
 **/
    public static void put(int expIdx, String path, <xsl:value-of select="@type"/> cpos[]) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean  vect1DBoolean;
          Vect1DInt  vect1DInt;
          Vect2DInt  vect2DInt;
          Vect3DInt  vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
	  Vect5DDouble vect5DDouble;
	  Vect6DDouble vect6DDouble;
      	  Vect7DDouble vect7DDouble;
          Vect1DDouble times;

          times = new Vect1DDouble(cpos.length);
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              times.setElementAt(i, (double)cpos[i].time);
          deleteAll(expIdx, path);
          UALLowLevel.beginCPOPutTimed(expIdx, path, times);

      <xsl:apply-templates select = "field" mode = "PUT_TIMED"/>
          UALLowLevel.endCPOPutTimed(expIdx, path);
    }


 /**
 * Method getdb retrieves the whole set of <xsl:value-of select="@type"/> CPOs in the open database, taking the correct reference.
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for CPO objects in the database.
 * @param occurrence The occurence of the selected cpo.
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are
 * missing CPO fields.
 * @return the array of stored <xsl:value-of select="@type"/> CPO items. Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if float or double and empty String is string.
 **/
    public static <xsl:value-of select="@type"/> [] getdb(String user, String machine, int shot, int run, String path, int occurrence) throws UALException
    {
		int idx = UALLowLevel.openDb(user, machine, shot, run, path, occurrence);
		if (occurrence != 0)
			path = path + "/" + occurrence;
		<xsl:value-of select="@type"/> [] retCpos = null;
                try {
                        retCpos = get(idx, path);
                }
                catch(Exception exc) {
                        retCpos = new <xsl:value-of select="@type"/> [1];
                        retCpos[0] = new <xsl:value-of select="@type"/> ();
                        System.out.println("Return a new <xsl:value-of select="@type"/> empty CPO array after catching exception: "+exc);
                }
		close(idx);
		return retCpos;
    }
/**
 * Method putdb stores an array of (timed) <xsl:value-of select="@type"/> CPOs in the open database only if
 *  datainto.isref == 1. In any case it calls afterwards UALLowLevel.putCpoDb() in order to update catalogue
 * @param expIdx The index to the database, returned by UALAccess.open()
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected cpo. By convention, only a single tree level is defined for CPO objects in the database.
 * @param occurrence The occurence of the selected cpo.
 * @param cpos The array of timed <xsl:value-of select="@type"/> CPOs
 * @exception UALException Issued when data cannot be stored for any reason.
 **/
    public static void putdb(int expIdx, String user, String machine, int shot, int run,
    	String path, int occurrence, <xsl:value-of select="@type"/> cpos[]) throws UALException
    {
    	String actPath;
        if(occurrence > 0)
	    actPath = path+"/"+occurrence;
	else
	    actPath = path;
	if(cpos[0].datainfo.isref == 1)
	{
	    put(expIdx, actPath, cpos);
	    UALLowLevel.putCpoDb(user, machine, shot, run, path, occurrence,
	    	1, user, machine, shot, run, occurrence);
	}
	else //isref == 2
	{
	    UALLowLevel.putCpoDb(user, machine, shot, run, path, occurrence, 2,
	    	(cpos[0].datainfo.whatref.user != null)?cpos[0].datainfo.whatref.user:"",
		(cpos[0].datainfo.whatref.machine != null)?cpos[0].datainfo.whatref.machine:"",
		cpos[0].datainfo.whatref.shot,
		cpos[0].datainfo.whatref.run,
		cpos[0].datainfo.whatref.occurrence);

	}
    }


    public static void fillCpoRef(<xsl:value-of select="@type"/>[]cpos, int isRef, String refUser, String refMachine, int refShot, int refRun, int refOccurrence)
    {
        cpos[0].datainfo.isref = isRef;
	if(isRef != 1)
	{
	    cpos[0].datainfo.whatref.user = refUser;
    	    cpos[0].datainfo.whatref.machine = refMachine;
    	    cpos[0].datainfo.whatref.shot = refShot;
    	    cpos[0].datainfo.whatref.run = refRun;
    	    cpos[0].datainfo.whatref.occurrence = refOccurrence;
	}
    }


 /**
 * Method delete removes all the data associated with a <xsl:value-of select="@type"/> CPO in the open database.
 * @param expIdx The index to the database, returned by UALAccess.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for CPO objects in the database.
 * @exception UALException Issued when data cannot be stored for any reason.
 **/
    public static void deleteAll(int expIdx, String path) throws UALException
    {
      <xsl:apply-templates select = "field" mode = "DELETE"/>
    }

/**
 * Method putSlice appends a (timed) <xsl:value-of select="@type"/> CPO in the open database. The CPO instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by UALAccess.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for CPO objects in the database.
 * @param cpo The timed <xsl:value-of select="@type"/> CPO
 * @exception UALException Issued when data cannot be stored for any reason.
 **/
    public static void putSlice(int expIdx, String path, <xsl:value-of select="@type"/> cpo) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean  vect1DBoolean;
          Vect1DInt  vect1DInt;
          Vect2DInt  vect2DInt;
          Vect3DInt  vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
	  Vect5DDouble vect5DDouble;
	  Vect6DDouble vect6DDouble;
          Vect7DDouble vect7DDouble;

          UALLowLevel.beginCPOPutSlice(expIdx, path);
      <xsl:apply-templates select = "field" mode = "PUT_SLICE"/>
          UALLowLevel.endCPOPutSlice(expIdx, path);
    }


 /**
 * Method putNonTimed stores the non time dependent fields of a (timed) <xsl:value-of select="@type"/> CPO in the open database. The CPO instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by UALAccess.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for CPO objects in the database.
 * @param cpos The timed <xsl:value-of select="@type"/> CPO
 * @exception UALException Issued when data cannot be stored for any reason.
 **/
    public static void putNonTimed(int expIdx, String path, <xsl:value-of select="@type"/> cpo) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean  vect1DBoolean;
          Vect1DInt  vect1DInt;
          Vect2DInt  vect2DInt;
          Vect3DInt  vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
      Vect5DDouble vect5DDouble;
      Vect6DDouble vect6DDouble;
      Vect7DDouble vect7DDouble;

          deleteAll(expIdx, path);
          UALLowLevel.beginCPOPutNonTimed(expIdx, path);
      <xsl:apply-templates select = "field" mode = "PUT_NON_TIMED"/>
          UALLowLevel.endCPOPutNonTimed(expIdx, path);
    }

/**
 * Method replaceLastSlice replaces the last stored (timed) <xsl:value-of select="@type"/> CPO in the open database.
 * @param expIdx The index to the database, returned by UALAccess.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for CPO objects in the database.
 * @param cpo The timed <xsl:value-of select="@type"/> CPO
 * @exception UALException Issued when data cannot be stored for any reason.
 **/
    public static void replaceLastSlice(int expIdx, String path, <xsl:value-of select="@type"/> cpo) throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean  vect1DBoolean;
          Vect1DInt  vect1DInt;
          Vect2DInt  vect2DInt;
          Vect3DInt  vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
	  Vect5DDouble vect5DDouble;
	  Vect6DDouble vect6DDouble;
          Vect7DDouble vect7DDouble;

          UALLowLevel.beginCPOReplaceLastSlice(expIdx, path);
      <xsl:apply-templates select = "field" mode = "REPLACE_LAST_SLICE"/>
          UALLowLevel.endCPOReplaceLastSlice(expIdx, path);
    }



 /**
 * Method deleteTimed removes all timed fields for  <xsl:value-of select="@type"/> CPO in the open database.
 * This corresponds to removing all the instances for that CPO, and only time-independent fields will be preserved
 * @param expIdx The index to the database, returned by UALAccess.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for CPO objects in the database.
 * @exception UALException Issued when data cannot be deleted for any reason.
 **/
    public static void deleteTimed(int expIdx, String path) throws UALException
    {
      <xsl:apply-templates select = "field" mode = "DELETE_TIMED"/>
    }

 /**
 * Method copy <xsl:value-of select="@type"/> copies a full CPO between two databases.
 * @param srcIdx The index of the source database, returned by UALAccess.open()
 * @param srcOccur The occurence of the CPO in the source database.
 * @param destIdx The index of the destination database, returned by UALAccess.open()
 * @param destOccur The occurence of the CPO in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copy(int srcIdx, int srcOccur, int destIdx, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyCpo(srcIdx, destIdx, "<xsl:value-of select="@type"/>", srcOccur, destOccur);
    }

 /**
 * Method copyEnv <xsl:value-of select="@type"/> copies a full CPO between two databases.
 * @param srcUser User of the source database
 * @param srcTokamak Tokamak of the source database
 * @param srcVersion Version of the source database
 * @param srcShot Shot number of the source database
 * @param srcRun Run number of the source database
 * @param srcOccur The occurence of the CPO in the source database.
 * @param destUser User of the destination database
 * @param destTokamak Tokamak of the destination database
 * @param destVersion Version of the destination database
 * @param destShot Shot number of the destination database
 * @param destRun Run number of the destination database
 * @param destOccur The occurence of the CPO in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copyEnv(String srcUser, String srcTokamak, String srcVersion, int srcShot, int srcRun, int srcOccur, String destUser, String destTokamak, String destVersion, int destShot, int destRun, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyCpoEnv(srcTokamak, srcVersion, srcUser, srcShot, srcRun, srcOccur, destTokamak, destVersion, destUser, destShot, destRun, destOccur, "<xsl:value-of select="@type"/>");
    }

 /**
 * Method copySlice <xsl:value-of select="@type"/> copies a single slice between two databases.
 * @param srcIdx The index of the source database, returned by UALAccess.open()
 * @param srcOccur The occurence of the CPO in the source database.
 * @param destIdx The index of the destination database, returned by UALAccess.open()
 * @param destOccur The occurence of the CPO in the destination database.
 * @param time The time of the slice.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copySlice(int srcIdx, int srcOccur, int destIdx, int destOccur, double time) throws UALException
    {
        String srcPathOccur = "<xsl:value-of select="@type"/>";
        if (srcOccur != 0) srcPathOccur += "/" + srcOccur;
        <xsl:value-of select="@type"/> slice = getSlice(srcIdx,srcPathOccur,time,CLOSEST_SAMPLE);

        String destPathOccur = "<xsl:value-of select="@type"/>";
        if (destOccur != 0) destPathOccur += "/" + destOccur;

        // if the destination CPO does not exist yet, then put non-timed fields
        try {
            UALLowLevel.beginCPOGet(destIdx,destPathOccur,true);
            UALLowLevel.endCPOGet(destIdx,destPathOccur);
        } catch (UALException exc) {
            if (exc.getMessage().indexOf("Internal error: Time not found") >= 0)
                putNonTimed(destIdx,destPathOccur,slice);
            else
                throw exc;
        }

        // put timed fields
        putSlice(destIdx,destPathOccur,slice);
    }

 /**
 * Method copySliceEnv <xsl:value-of select="@type"/> copies a single slice between two databases.
 * @param srcUser User of the source database
 * @param srcTokamak Tokamak of the source database
 * @param srcVersion Version of the source database
 * @param srcShot Shot number of the source database
 * @param srcRun Run number of the source database
 * @param srcOccur The occurence of the CPO in the source database.
 * @param destUser User of the destination database
 * @param destTokamak Tokamak of the destination database
 * @param destVersion Version of the destination database
 * @param destShot Shot number of the destination database
 * @param destRun Run number of the destination database
 * @param destOccur The occurence of the CPO in the destination database.
 * @param time The time of the slice.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copySliceEnv(String srcUser, String srcTokamak, String srcVersion, int srcShot, int srcRun, int srcOccur, String destUser, String destTokamak, String destVersion, int destShot, int destRun, int destOccur, double time) throws UALException
    {
        String srcPathOccur = "<xsl:value-of select="@type"/>";
        if (srcOccur != 0) srcPathOccur += "/" + srcOccur;
        int srcIdx = openEnv("euitm",srcShot,srcRun,srcUser,srcTokamak,srcVersion);
        <xsl:value-of select="@type"/> slice = getSlice(srcIdx,srcPathOccur,time,CLOSEST_SAMPLE);
        close(srcIdx);

        String destPathOccur = "<xsl:value-of select="@type"/>";
        if (destOccur != 0) destPathOccur += "/" + destOccur;
        int destIdx = openEnv("euitm",destShot,destRun,destUser,destTokamak,destVersion);

        // if the destination CPO does not exist yet, then put non-timed fields
        try {
            UALLowLevel.beginCPOGet(destIdx,destPathOccur,true);
            UALLowLevel.endCPOGet(destIdx,destPathOccur);
        } catch (UALException exc) {
            if (exc.getMessage().indexOf("Internal error: Time not found") >= 0)
                putNonTimed(destIdx,destPathOccur,slice);
            else
                throw exc;
        }

        // put timed fields
        putSlice(destIdx,destPathOccur,slice);
        close(destIdx);
    }

  </xsl:when>

  <!--============ Define time-independent CPOs ============-->
  <xsl:otherwise>

 /**
 * Method copy <xsl:value-of select="@type"/> copies a full CPO between two databases.
 * @param srcIdx The index of the source database, returned by UALAccess.open()
 * @param srcOccur The occurence of the CPO in the source database.
 * @param destIdx The index of the destination database, returned by UALAccess.open()
 * @param destOccur The occurence of the CPO in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copy(int srcIdx, int srcOccur, int destIdx, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyCpo(srcIdx, destIdx, "<xsl:value-of select="@type"/>", srcOccur, destOccur);
    }

 /**
 * Method copyEnv <xsl:value-of select="@type"/> copies a full CPO between two databases.
 * @param srcUser User of the source database
 * @param srcTokamak Tokamak of the source database
 * @param srcVersion Version of the source database
 * @param srcShot Shot number of the source database
 * @param srcRun Run number of the source database
 * @param srcOccur The occurence of the CPO in the source database.
 * @param destUser User of the destination database
 * @param destTokamak Tokamak of the destination database
 * @param destVersion Version of the destination database
 * @param destShot Shot number of the destination database
 * @param destRun Run number of the destination database
 * @param destOccur The occurence of the CPO in the destination database.
 * @exception UALException Issued when the operation fails for any reason.
 **/
    public static void copyEnv(String srcUser, String srcTokamak, String srcVersion, int srcShot, int srcRun, int srcOccur, String destUser, String destTokamak, String destVersion, int destShot, int destRun, int destOccur) throws UALException
    {
        UALLowLevel.ualCopyCpoEnv(srcTokamak, srcVersion, srcUser, srcShot, srcRun, srcOccur, destTokamak, destVersion, destUser, destShot, destRun, destOccur, "<xsl:value-of select="@type"/>");
    }

/**
 * Method put stores a non timed <xsl:value-of select="@type"/> CPOs in the open database.
 * @param expIdx The index to the database, returned by UALAccess.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for CPO objects in the database.
 * @param cpos The passed <xsl:value-of select="@type"/> CPO.
 * @exception UALException Issued when data cannot be stored for any reason.
 **/
    public static void put(int expIdx, String path, <xsl:value-of select="@type"/> cpo)  throws UALException
    {
          Vect1DString  vect1DString;
          Vect1DBoolean  vect1DBoolean;
          Vect1DInt  vect1DInt;
          Vect2DInt  vect2DInt;
          Vect3DInt  vect3DInt;
          Vect1DDouble  vect1DDouble;
          Vect2DDouble  vect2DDouble;
          Vect3DDouble  vect3DDouble;
          Vect4DDouble  vect4DDouble;
	  Vect5DDouble vect5DDouble;
	  Vect6DDouble vect6DDouble;
	  Vect7DDouble vect7DDouble;
          UALLowLevel.beginCPOPut(expIdx, path);
      <xsl:apply-templates select = "field" mode = "PUT"/>
          UALLowLevel.endCPOPut(expIdx, path);
    }


    <xsl:choose>
      <!--============ General case (except topinfo) ============-->
      <xsl:when test = "@type != 'topinfo'">
/**
 * Method putdb stores a non timed <xsl:value-of select="@type"/> CPOs in the open database only if
 *  datainto.isref == 1. In any case it calls afterwards UALLowLevel.putCpoDb() in order to update catalogue
 * @param expIdx The index to the database, returned by UALAccess.open()
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected cpo. By convention, only a single tree level is defined for CPO objects in the database.
 * @param occurrence The occurence of the selected cpo.
 * @param cpo The  <xsl:value-of select="@type"/> CPOs
 * @exception UALException Issued when data cannot be stored for any reason.
 **/
    public static void putdb(int expIdx, String user, String machine, int shot, int run,
    	String path, int occurrence, <xsl:value-of select="@type"/> cpo) throws UALException
    {
    	String actPath;
        if(occurrence > 0)
	    actPath = path+"/"+occurrence;
	else
	    actPath = path;
	if(cpo.datainfo.isref == 1)
	{
	    put(expIdx, actPath, cpo);
	    UALLowLevel.putCpoDb(user, machine, shot, run, path, occurrence, 1,
	    	user, machine, shot, run, occurrence);
	}
	else //isref == 2
	{
	    UALLowLevel.putCpoDb(user, machine, shot, run, path, occurrence, 2,
	    	cpo.datainfo.whatref.user,  cpo.datainfo.whatref.machine,
		cpo.datainfo.whatref.shot, cpo.datainfo.whatref.run,
		cpo.datainfo.whatref.occurrence);

	}
    }


/**
 * Method getdb retrieves the non timed <xsl:value-of select="@type"/> CPOs in the open database, taking the correct reference.
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for CPO objects in the database.
 * @param occurrence The occurence of the selected cpo.
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are
 * missing CPO fields.
 * @return the <xsl:value-of select="@type"/> CPO . Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if float or double and empty String is string.
 **/
    public static <xsl:value-of select="@type"/> getdb(String user, String machine, int shot, int run, String path, int occurrence) throws UALException
    {
		int idx = UALLowLevel.openDb(user, machine, shot, run, path, occurrence);
		if (occurrence != 0)
			path = path + "/" + occurrence;
		<xsl:value-of select="@type"/> retCpos = null;
                try {
                        retCpos = get(idx, path);
                }
                catch(Exception exc) {
                        retCpos = new <xsl:value-of select="@type"/> ();
                        System.out.println("Return a new <xsl:value-of select="@type"/> empty CPO after catching exception: "+exc);
                }
		close(idx);
		return retCpos;
    }

    public static void fillCpoRef(<xsl:value-of select="@type"/> cpo, int isRef, String refUser, String refMachine, int refShot, int refRun, int refOccurrence)
    {
        cpo.datainfo.isref = isRef;
        if(isRef != 1)
        {
            cpo.datainfo.whatref.user = refUser;
                cpo.datainfo.whatref.machine = refMachine;
                cpo.datainfo.whatref.shot = refShot;
                cpo.datainfo.whatref.run = refRun;
                cpo.datainfo.whatref.occurrence = refOccurrence;
        }
    }

        </xsl:when>
        <!--============ Special case: topinfo ============-->
       <xsl:otherwise>
/**
 * Method putdb always stores a non timed topinfo CPO in the open database (it is never a reference). It calls afterwards UALLowLevel.putCpoDb() in order to update catalogue
 * @param expIdx The index to the database, returned by UALAccess.open()
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected cpo. By convention, only a single tree level is defined for CPO objects in the database.
 * @param occurrence The occurence of the selected cpo.
 * @param cpo The topinfo CPO
 * @exception UALException Issued when data cannot be stored for any reason.
 **/
	public static void putdb(int expIdx, String user, String machine, int shot, int run,
		String path, int occurrence, <xsl:value-of select="@type"/> cpo) throws UALException
	{
		String actPath;
		if(occurrence > 0)
			actPath = path+"/"+occurrence;
		else
			actPath = path;
		put(expIdx, actPath, cpo);
		UALLowLevel.putCpoDb(user, machine, shot, run, path, occurrence, 1, user, machine, shot, run, occurrence);
	}


/**
 * Method getdb retrieves the non timed topinfo CPO in the open database.
 * @param user The current username
 * @param machine The current machine
 * @param shot The current shot
 * @param run The current run
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for CPO objects in the database.
 * @param occurrence The occurence of the selected cpo.
 * @exception UALException Issued when data cannot be accessed for any reason. Note that the exception is not raised if there are
 * missing CPO fields.
 * @return the topinfo CPO. Missing fields are represented by zero sized vectors if not scalars,
 * by EMPTY_INT if integer, EMPTY_DOUBLE if float or double and empty String is string.
 **/
    public static <xsl:value-of select="@type"/> getdb(String user, String machine, int shot, int run, String path, int occurrence) throws UALException
    {
		int idx = UALLowLevel.openDb(user, machine, shot, run, path, occurrence);
		if (occurrence != 0)
			path = path + "/" + occurrence;
		<xsl:value-of select="@type"/> retCpos = null;
                try {
                        retCpos = get(idx, path);
                }
                catch(Exception exc) {
                        retCpos = new <xsl:value-of select="@type"/> ();
                        System.out.println("Return a new <xsl:value-of select="@type"/> empty CPO after catching exception: "+exc);
                }
		close(idx);
		return retCpos;
    }

	public static void fillCpoRef(<xsl:value-of select="@type"/> cpo, int isRef, String refUser, String refMachine,
		int refShot, int refRun, int refOccurrence)
	{
		// do nothing since topinfo does not have any reference field
	}

        </xsl:otherwise>
   </xsl:choose>

    public static <xsl:value-of select="@type"/>  get(int expIdx, String path)  throws UALException
    {
      <xsl:value-of select="@type"/> cpo = new <xsl:value-of select="@type"/> ();
      UALLowLevel.beginCPOGet(expIdx, path, false);
      <xsl:apply-templates select = "field" mode = "GET_SINGLE"/>
      UALLowLevel.endCPOGet(expIdx, path);
      return cpo;
    }
     </xsl:otherwise>
  </xsl:choose>
 /**
 * Method dump is used for debugging and prints the content of the object
 **/
  public void dump()
  {
    System.out.println("***** <xsl:value-of select="@type"/> *****");
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

<xsl:template match = "field" mode = "DELETE">
  <xsl:choose>
      <xsl:when test="@type='structure'">
        <xsl:apply-templates select = "field" mode = "DELETE"/>
      </xsl:when>
      <xsl:otherwise>
      UALLowLevel.deleteData(expIdx, path, "<xsl:value-of select = "@path"/>");
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
     <xsl:when test="@type='structure'">
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
    <xsl:when test="@type='xs:string'">
        if(<xsl:value-of select = "$currentidxpath"/>!= null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@type='xs:integer'">
        if(<xsl:value-of select = "$currentidxpath"/> != EMPTY_INT)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@type='xs:boolean'">
         System.out.println(<xsl:value-of select = "$currentidxpath"/>);
         System.out.println("");
    </xsl:when>
    <xsl:when test="@type='xs:double'">
        if(<xsl:value-of select = "$currentidxpath"/> != EMPTY_DOUBLE)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@type='xs:float'">
         if(<xsl:value-of select = "$currentidxpath"/> != EMPTY_DOUBLE)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>

    <xsl:when test="@type='vecstring_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@type='vecflt_type'">
         if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
   </xsl:when>
    <xsl:when test="@type='vecdbl_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@type='vecint_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>

    <xsl:when test="@type='matflt_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@type='matdbl_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@type='matint_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>

    <xsl:when test="@type='array3dflt_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@type='array3dint_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@type='array3ddbl_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>

    <xsl:when test="@type='array4ddbl_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@type='array4dflt_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@type='array4dint_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>

    <xsl:when test="@type='array5ddbl_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@type='array5dflt_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@type='array5dint_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>

    <xsl:when test="@type='array6ddbl_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@type='array6dflt_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@type='array6dint_type'">
        if(<xsl:value-of select = "$currentidxpath"/> != null)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>

    <xsl:when test="@type='struct_array'">
      if (<xsl:value-of select = "$currentidxpath"/> != null) {
        for (int i<xsl:value-of select="$level + 1"/> = 0; i<xsl:value-of select="$level + 1"/> &lt; <xsl:value-of select = "$currentidxpath"/>.length; i<xsl:value-of select="$level + 1"/>++) {
          <xsl:apply-templates select = "field" mode = "DUMP">
            <xsl:with-param name="level"><xsl:value-of select="$level + 1"/></xsl:with-param>
            <xsl:with-param name="idxpath"><xsl:value-of select="$currentidxpath"/>[i<xsl:value-of select="$level + 1"/>]</xsl:with-param>
          </xsl:apply-templates>
        }
      }
    </xsl:when>

    <xsl:when test="@type='structure'">
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
    <xsl:when test="@type='xs:string'">
      public String <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='xs:integer'">
      public int <xsl:value-of select = "@name"/> = EMPTY_INT;
    </xsl:when>
    <xsl:when test="@type='xs:boolean'">
      public boolean <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='xs:double'">
      public double <xsl:value-of select = "@name"/> = EMPTY_DOUBLE;
    </xsl:when>
    <xsl:when test="@type='xs:float'">
      public double <xsl:value-of select = "@name"/> = EMPTY_DOUBLE;
    </xsl:when>

    <xsl:when test="@type='vecstring_type'">
      public Vect1DString <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='vecflt_type'">
      public Vect1DDouble <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='vecdbl_type'">
      public Vect1DDouble <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='vecint_type'">
      public Vect1DInt <xsl:value-of select = "@name"/>;
    </xsl:when>

    <xsl:when test="@type='matflt_type'">
      public Vect2DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='matdbl_type'">
      public Vect2DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='matint_type'">
      public Vect2DInt <xsl:value-of select = "@name"/>;
    </xsl:when>

    <xsl:when test="@type='array3dflt_type'">
      public Vect3DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='array3dint_type'">
      public Vect3DInt  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='array3ddbl_type'">
      public Vect3DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>

    <xsl:when test="@type='array4dflt_type'">
      public Vect4DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='array4dint_type'">
      public Vect4DInt  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='array4ddbl_type'">
      public Vect4DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>

    <xsl:when test="@type='array5dflt_type'">
      public Vect5DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='array5dint_type'">
      public Vect5DInt  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='array5ddbl_type'">
      public Vect5DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>

    <xsl:when test="@type='array6dflt_type'">
      public Vect6DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='array6dint_type'">
      public Vect6DInt  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='array6ddbl_type'">
      public Vect6DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>

    <xsl:when test="@type='array7dflt_type'">
      public Vect7DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='array7dint_type'">
      public Vect7DInt  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@type='array7ddbl_type'">
      public Vect7DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>

    <xsl:when test="@type='struct_array'">
      public static class <xsl:value-of select = "@name"/>Class {
      <xsl:apply-templates select = "field" mode = "DECLARE"/>
      }
      public <xsl:value-of select = "@name"/>Class <xsl:value-of select = "@name"/>[];
    </xsl:when>

    <xsl:when test="@type='structure'">
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
<!--      Get field from a time-independent CPO      -->
<!--=================================================-->

<xsl:template match = "field" mode = "GET_SINGLE">
  <xsl:choose>
    <xsl:when test = "@timed = 'yes'">
<!-- Cannot occur -->
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="@type='xs:string'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getString(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='xs:integer'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getInt(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_INT;}
        </xsl:when>
        <xsl:when test="@type='xs:boolean'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getBoolean(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){}
        </xsl:when>
        <xsl:when test="@type='xs:double'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_DOUBLE;}
        </xsl:when>
        <xsl:when test="@type='xs:float'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_DOUBLE;}
       </xsl:when>

        <xsl:when test="@type='vecflt_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect1DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
         </xsl:when>
        <xsl:when test="@type='vecstring_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect1DString(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
         </xsl:when>
        <xsl:when test="@type='vecdbl_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect1DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
         </xsl:when>
        <xsl:when test="@type='vecint_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect1DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
         </xsl:when>

        <xsl:when test="@type='matflt_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect2DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
        <xsl:when test="@type='matdbl_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect2DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
        <xsl:when test="@type='matint_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect2DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>

        <xsl:when test="@type='array3dflt_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect3DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
       <xsl:when test="@type='array3ddbl_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect3DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
       <xsl:when test="@type='array3dint_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect3DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>

        <xsl:when test="@type='array4dflt_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect4DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
       <xsl:when test="@type='array4ddbl_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect4DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
       <xsl:when test="@type='array4dint_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect4DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>

        <xsl:when test="@type='array5dflt_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect5DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
       <xsl:when test="@type='array5ddbl_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect5DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
       <xsl:when test="@type='array5dint_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect5DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>

        <xsl:when test="@type='array6dflt_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect6DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
       <xsl:when test="@type='array6ddbl_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect6DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
       <xsl:when test="@type='array6dint_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect6DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>

        <!--========== Arrays of structures ==========-->
        <xsl:when test="@type='struct_array'">
          <!-- Generate the full name of the subclass from the field path -->
          <xsl:variable name="fulltypepath">
            <xsl:call-template name="BUILD_TYPENAME">
              <xsl:with-param name="typepath"><xsl:value-of select = "@path"/></xsl:with-param >
            </xsl:call-template>
          </xsl:variable>

          {/*    Array of structure    */
            int obj1;
            try {
              obj1 = UALLowLevel.getObject(expIdx, path, "<xsl:value-of select = "@path"/>", 0); // read the whole block
              if (UALLowLevel.getObjectDim(expIdx,obj1) != 0) // empty arrays of struct must remain set to null
                cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = new <xsl:value-of select="$fulltypepath"/>[UALLowLevel.getObjectDim(expIdx,obj1)];
              for (int i1 = 0; i1 &lt; UALLowLevel.getObjectDim(expIdx,obj1); i1++) {  // process array elements
                cpo.<xsl:value-of select = "translate(@path,'/','.')"/>[i1] = new <xsl:value-of select="$fulltypepath"/>();
                <xsl:apply-templates select = "field" mode = "GET_FROM_OBJECT">
                  <xsl:with-param name="level">1</xsl:with-param>
                  <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
                  <xsl:with-param name="idxpath">cpo.<xsl:value-of select = "translate(@path,'/','.')"/>[i1]</xsl:with-param>
                  <xsl:with-param name="timed">no</xsl:with-param>
                </xsl:apply-templates>
              }
              UALLowLevel.releaseObject(expIdx,obj1);
            } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
          }
        </xsl:when>

        <!--========== Regular structures ==========-->
        <xsl:when test="@type='structure'">
          <xsl:apply-templates select = "field" mode = "GET_SINGLE"/>
        </xsl:when>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!--=================================================-->
<!--             Get field from a slice              -->
<!--=================================================-->

<xsl:template match = "field" mode = "GET_SLICE">
  <xsl:choose>

    <!-- Time-dependent field -->
<xsl:when test = "@timed = 'yes'">
      <xsl:choose>
        <xsl:when test="@type='xs:integer'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getIntSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_INT;}
      </xsl:when>
        <xsl:when test="@type='xs:boolean'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getBooleanSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){}
       </xsl:when>
        <xsl:when test="@type='xs:string'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getStringSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='xs:double'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getDoubleSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_DOUBLE;}
        </xsl:when>
        <xsl:when test="@type='xs:float'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getDoubleSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_DOUBLE;}
        </xsl:when>

        <xsl:when test="@type='vecflt_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect1DDoubleSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='vecint_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect1DIntSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='vecdbl_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect1DDoubleSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>

        <xsl:when test="@type='matflt_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/>= UALLowLevel.getVect2DDoubleSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='matdbl_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/>= UALLowLevel.getVect2DDoubleSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='matint_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect2DIntSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>

        <xsl:when test="@type='array3ddbl_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect3DDoubleSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='array3dflt_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect3DDoubleSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='array3dint_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect3DIntSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>

         <xsl:when test="@type='array4ddbl_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect4DDoubleSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='array4dflt_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect4DDoubleSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='array4dint_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect4DIntSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>

        <xsl:when test="@type='array5ddbl_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect5DDoubleSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='array5dflt_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect5DDoubleSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='array5dint_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect5DIntSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>

        <xsl:when test="@type='array6ddbl_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect6DDoubleSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='array6dflt_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect6DDoubleSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='array6dint_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect6DIntSlice(expIdx, path, "<xsl:value-of select="@path"/>",time, interpolMode);
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>

        <!--========== Arrays of structures ==========-->
        <xsl:when test="@type='struct_array'">
          <!-- Generate the full name of the subclass from the field path -->
          <xsl:variable name="fulltypepath">
            <xsl:call-template name="BUILD_TYPENAME">
              <xsl:with-param name="typepath"><xsl:value-of select = "@path"/></xsl:with-param >
            </xsl:call-template>
          </xsl:variable>

          { /*     Timed array of structure     */
            /*    Read timed content    */
            int obj_single_time;
            int obj1;
            try {
              /* timed arrays of structures are included inside a time container, even if there is a single time */
              obj_single_time = UALLowLevel.getObjectSlice(expIdx, path, "<xsl:value-of select = "@path"/>", time); // read the whole timed block
              obj1 = UALLowLevel.getObjectFromObject(expIdx,obj_single_time,"ALLTIMES",0);
              if (UALLowLevel.getObjectDim(expIdx,obj1) != 0) // empty arrays of struct must remain set to null
                cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = new <xsl:value-of select="$fulltypepath"/>[UALLowLevel.getObjectDim(expIdx,obj1)];
              get_timed_<xsl:value-of select = "translate(@path,'/','_')"/>(expIdx,cpo.<xsl:value-of select = "translate(@path,'/','.')"/>,obj1);
              UALLowLevel.releaseObject(expIdx,obj_single_time);
            } catch(Exception exc) {
              throw new UALException("Error in getSlice: could not read the array of structures for <xsl:value-of select = "translate(@path,'/','.')"/> " + exc);
            }
            /*    Read non-timed content    */
            try {
              obj1 = UALLowLevel.getObject(expIdx, path, "<xsl:value-of select = "@path"/>", 0); // read the whole non-timed block
              // must have same number of non-timed elements and timed elements
	      if (UALLowLevel.getObjectDim(expIdx,obj1) !=0 &amp;&amp; (cpo.<xsl:value-of select = "translate(@path,'/','.')"/> == null || cpo.<xsl:value-of select = "translate(@path,'/','.')"/>.length != UALLowLevel.getObjectDim(expIdx,obj1))) {
                throw new UALException("Error in getSlice: array of structures has different number of timed and nontimed elements");
              }
              get_nontimed_<xsl:value-of select = "translate(@path,'/','_')"/>(expIdx,cpo.<xsl:value-of select = "translate(@path,'/','.')"/>,obj1);
              UALLowLevel.releaseObject(expIdx,obj1);
            } catch(UALException exc) {
              throw new UALException("Error in getSlice: for <xsl:value-of select = "translate(@path,'/','.')"/> " + exc);
            }
          }
        </xsl:when>

        <!--========== Regular structure ==========-->
      <xsl:when test="@type='structure'">
          <xsl:apply-templates select = "field" mode = "GET_SLICE"/>
        </xsl:when>
      </xsl:choose>
    </xsl:when>

    <!-- Time-independent field -->
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="@type='xs:string'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getString(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='xs:integer'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getInt(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_INT;}
        </xsl:when>
        <xsl:when test="@type='xs:boolean'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getBoolean(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){}
        </xsl:when>
        <xsl:when test="@type='xs:double'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_DOUBLE;}
        </xsl:when>
        <xsl:when test="@type='xs:float'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_DOUBLE;}
        </xsl:when>

        <xsl:when test="@type='vecstring_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect1DString(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='vecflt_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect1DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='vecdbl_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect1DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='vecint_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect1DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>

        <xsl:when test="@type='matflt_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect2DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='matint_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect2DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>
        <xsl:when test="@type='matdbl_type'">
          try {
          cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect2DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
        </xsl:when>

        <xsl:when test="@type='array3dflt_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect3DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
        <xsl:when test="@type='array3dint_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect3DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
        <xsl:when test="@type='array3ddbl_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect3DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>

        <xsl:when test="@type='array4dflt_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect4DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
        <xsl:when test="@type='array4dint_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect4DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
        <xsl:when test="@type='array4ddbl_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect4DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>

        <xsl:when test="@type='array5dflt_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect5DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
        <xsl:when test="@type='array5dint_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect5DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
        <xsl:when test="@type='array5ddbl_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect5DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>

         <xsl:when test="@type='array6dflt_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect6DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
        <xsl:when test="@type='array6dint_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect6DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
        <xsl:when test="@type='array6ddbl_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect6DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>

        <xsl:when test="@type='array7dflt_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect7DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
        <xsl:when test="@type='array7dint_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect7DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>
        <xsl:when test="@type='array7ddbl_type'">
           try {
         cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = UALLowLevel.getVect7DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
       </xsl:when>

        <!--========== Arrays of structures ==========-->
        <xsl:when test="@type='struct_array'">
          <!-- Generate the full name of the subclass from the field path -->
          <xsl:variable name="fulltypepath">
            <xsl:call-template name="BUILD_TYPENAME">
              <xsl:with-param name="typepath"><xsl:value-of select = "@path"/></xsl:with-param >
            </xsl:call-template>
          </xsl:variable>

          { /*    Non-timed array of structure    */
            int obj1;
            try {
              obj1 = UALLowLevel.getObject(expIdx, path, "<xsl:value-of select = "@path"/>", 0); // read the whole block
              if (UALLowLevel.getObjectDim(expIdx,obj1) != 0) // empty arrays of struct must remain set to null
                cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = new <xsl:value-of select="$fulltypepath"/>[UALLowLevel.getObjectDim(expIdx,obj1)];
              for (int i1 = 0; i1 &lt; UALLowLevel.getObjectDim(expIdx,obj1); i1++) {  // process array elements
                cpo.<xsl:value-of select = "translate(@path,'/','.')"/>[i1] = new <xsl:value-of select="$fulltypepath"/>();
                <xsl:apply-templates select = "field" mode = "GET_FROM_OBJECT">
                  <xsl:with-param name="level">1</xsl:with-param>
                  <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
                  <xsl:with-param name="idxpath">cpo.<xsl:value-of select = "translate(@path,'/','.')"/>[i1]</xsl:with-param>
                  <xsl:with-param name="timed">no</xsl:with-param>
                </xsl:apply-templates>
              }
              UALLowLevel.releaseObject(expIdx,obj1);
            } catch(Exception exc){cpo.<xsl:value-of select = "translate(@path,'/','.')"/> = null;}
          }
        </xsl:when>

        <!--========== Regular structures ==========-->
        <xsl:when test="@type='structure'">
          <xsl:apply-templates select = "field" mode = "GET_SLICE"/>
        </xsl:when>
      </xsl:choose>
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
    <xsl:when test="@type='structure'">
      <xsl:apply-templates select="field" mode="GET_OBJECT"/>
    </xsl:when>
    <xsl:when test="@type='struct_array'">
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
<!--       Get field for a time-dependent CPO      -->
<!--=================================================-->

<xsl:template match = "field" mode = "GET_FULL">
  <xsl:choose>
    <!-- Time-dependent field -->
    <xsl:when test = "@timed = 'yes'">
      <xsl:choose>
        <xsl:when test="@type='xs:integer'">
          try {
           vect1DInt = UALLowLevel.getVect1DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect1DInt.getDim() != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DInt.getElementAt(i);
          } catch(Exception exc)
          {           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_INT;
          }
        </xsl:when>
        <xsl:when test="@type='xs:boolean'">
          try {
           vect1DBoolean = UALLowLevel.getVect1DBoolean(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect1DBoolean.getDim() != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DBoolean.getElementAt(i);
           } catch(Exception exc){}
        </xsl:when>
        <xsl:when test="@type='xs:double'">
          try {
           vect1DDouble = UALLowLevel.getVect1DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect1DDouble.getDim() != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DDouble.getElementAt(i);
           } catch(Exception exc)
           {           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_DOUBLE;
          }
        </xsl:when>
        <xsl:when test="@type='xs:float'">
          try {
           vect1DDouble = UALLowLevel.getVect1DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect1DDouble.getDim() != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DDouble.getElementAt(i);
           } catch(Exception exc)
          {           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_DOUBLE;
          }
        </xsl:when>
        <xsl:when test="@type='xs:string'">
          try {
           vect1DString = UALLowLevel.getVect1DString(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect1DString.getDim() != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DString.getElementAt(i);
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
        </xsl:when>

        <xsl:when test="@type='vecflt_type'">
          try {
           vect2DDouble = UALLowLevel.getVect2DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect2DDouble.getDim(1) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect2DDouble.getElementAt(i);
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@type='vecint_type'">
          try {
           vect2DInt = UALLowLevel.getVect2DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect2DInt.getDim(1) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect2DInt.getElementAt(i);
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@type='vecdbl_type'">
          try {
           vect2DDouble = UALLowLevel.getVect2DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect2DDouble.getDim(1) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect2DDouble.getElementAt(i);
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>

        <xsl:when test="@type='matflt_type'">
          try {
           vect3DDouble = UALLowLevel.getVect3DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect3DDouble.getDim(2) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect3DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@type='matdbl_type'">
          try {
           vect3DDouble = UALLowLevel.getVect3DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect3DDouble.getDim(2) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect3DDouble.getElementAt(i);
           } catch(Exception exc)
                  {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
        </xsl:when>
        <xsl:when test="@type='matint_type'">
          try {
           vect3DInt = UALLowLevel.getVect3DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect3DInt.getDim(2) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect3DInt.getElementAt(i);
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>

        <xsl:when test="@type='array3ddbl_type'">
          try {
           vect4DDouble = UALLowLevel.getVect4DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect4DDouble.getDim(3) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect4DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@type='array3dflt_type'">
          try {
           vect4DDouble = UALLowLevel.getVect4DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect4DDouble.getDim(3) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect4DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@type='array3dint_type'">
          try {
           vect4DInt = UALLowLevel.getVect4DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect4DInt.getDim(3) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect4DInt.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>

        <xsl:when test="@type='array4ddbl_type'">
          try {
           vect5DDouble = UALLowLevel.getVect5DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect5DDouble.getDim(4) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect5DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@type='array4dflt_type'">
          try {
           vect5DDouble = UALLowLevel.getVect5DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect5DDouble.getDim(4) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect5DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@type='array4dint_type'">
          try {
           vect5DInt = UALLowLevel.getVect5DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect5DInt.getDim(4) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect5DInt.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>

        <xsl:when test="@type='array5ddbl_type'">
          try {
           vect6DDouble = UALLowLevel.getVect6DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect6DDouble.getDim(5) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect6DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@type='array5dflt_type'">
          try {
           vect6DDouble = UALLowLevel.getVect6DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect6DDouble.getDim(5) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect6DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@type='array5dint_type'">
          try {
           vect6DInt = UALLowLevel.getVect6DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect6DInt.getDim(5) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect6DInt.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>

        <xsl:when test="@type='array6ddbl_type'">
          try {
           vect7DDouble = UALLowLevel.getVect7DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect7DDouble.getDim(6) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect7DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@type='array6dflt_type'">
          try {
           vect7DDouble = UALLowLevel.getVect7DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect7DDouble.getDim(6) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect7DDouble.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>
        <xsl:when test="@type='array6dint_type'">
          try {
           vect7DInt = UALLowLevel.getVect6DInt(expIdx, path, "<xsl:value-of select="@path"/>");
           if(vect7DInt.getDim(6) != numSamples)
                throw new UALException("Wrong returned array dimension for <xsl:value-of select = "translate(@path,'/','.')"/> ");
           for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect7DInt.getElementAt(i);
           } catch(Exception exc)
            {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
      </xsl:when>

        <!--========== Arrays of structures ==========-->
        <xsl:when test="@type='struct_array'">
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
                  cpos[i0].<xsl:value-of select = "translate(@path,'/','.')"/> = new <xsl:value-of select = "$fulltypepath"/>[UALLowLevel.getObjectDim(expIdx,obj1)];
                get_timed_<xsl:value-of select = "translate(@path,'/','_')"/>(expIdx,cpos[i0].<xsl:value-of select = "translate(@path,'/','.')"/>,obj1);
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
                if (UALLowLevel.getObjectDim(expIdx,obj1) != 0 &amp;&amp; (cpos[i0].<xsl:value-of select = "translate(@path,'/','.')"/> == null || cpos[i0].<xsl:value-of select = "translate(@path,'/','.')"/>.length != UALLowLevel.getObjectDim(expIdx,obj1))) {
                  throw new UALException("Error in get: array of structures has different number of timed and nontimed elements");
                }
                get_nontimed_<xsl:value-of select = "translate(@path,'/','_')"/>(expIdx,cpos[i0].<xsl:value-of select = "translate(@path,'/','.')"/>,obj1);
              }
              UALLowLevel.releaseObject(expIdx,obj1);
            } catch(UALException exc) {
//GABRIELE JUNE 2012 HANDLE EMPTY NONTIMED OBEJCT PARTS
              //throw new UALException("Error in get: for <xsl:value-of select = "translate(@path,'/','.')"/> " + exc);
            }
          }
        </xsl:when>

        <!--========== Regular structures ==========-->
        <xsl:when test="@type='structure'">
          <xsl:apply-templates select = "field" mode = "GET_FULL"/>
        </xsl:when>
      </xsl:choose>
    </xsl:when>

    <!-- Time-independent field -->
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="@type='xs:string'">
          try {
          stringValue = UALLowLevel.getString(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = stringValue;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@type='xs:integer'">
          try {
          intValue = UALLowLevel.getInt(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = intValue;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_INT;
            }
       </xsl:when>
        <xsl:when test="@type='xs:boolean'">
          try {
          booleanValue = UALLowLevel.getBoolean(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = booleanValue;
            } catch(Exception exc){}
      </xsl:when>
        <xsl:when test="@type='xs:double'">
          try {
          doubleValue = UALLowLevel.getDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = doubleValue;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_DOUBLE;
            }
       </xsl:when>
        <xsl:when test="@type='xs:float'">
          try {
          doubleValue = UALLowLevel.getDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = doubleValue;
          } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = EMPTY_DOUBLE;
            }
       </xsl:when>

        <xsl:when test="@type='vecstring_type'">
          try {
          vect1DString = UALLowLevel.getVect1DString(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DString;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
         </xsl:when>
        <xsl:when test="@type='vecflt_type'">
          try {
          vect1DDouble = UALLowLevel.getVect1DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DDouble;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@type='vecint_type'">
          try {
          vect1DInt = UALLowLevel.getVect1DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DInt;
            } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
        </xsl:when>
        <xsl:when test="@type='vecdbl_type'">
          try {
          vect1DDouble = UALLowLevel.getVect1DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect1DDouble;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>

        <xsl:when test="@type='matflt_type'">
          try {
          vect2DDouble = UALLowLevel.getVect2DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect2DDouble;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@type='matint_type'">
          try {
          vect2DInt = UALLowLevel.getVect2DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect2DInt;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@type='matdbl_type'">
          try {
          vect2DDouble = UALLowLevel.getVect2DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect2DDouble;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>

        <xsl:when test="@type='array3dflt_type'">
          try {
          vect3DDouble = UALLowLevel.getVect3DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect3DDouble;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@type='array3ddbl_type'">
          try {
          vect3DDouble = UALLowLevel.getVect3DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect3DDouble;
           }catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@type='array3dint_type'">
          try {
          vect3DInt = UALLowLevel.getVect3DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect3DInt;
            } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>

       <xsl:when test="@type='array4dflt_type'">
          try {
          vect4DDouble = UALLowLevel.getVect4DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect4DDouble;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@type='array4ddbl_type'">
          try {
          vect4DDouble = UALLowLevel.getVect4DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect4DDouble;
           }catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@type='array4dint_type'">
          try {
          vect4DInt = UALLowLevel.getVect4DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect4DInt;
            } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
        </xsl:when>

        <xsl:when test="@type='array5dflt_type'">
          try {
          vect5DDouble = UALLowLevel.getVect5DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect5DDouble;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@type='array5ddbl_type'">
          try {
          vect5DDouble = UALLowLevel.getVect5DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect5DDouble;
           }catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@type='array5dint_type'">
          try {
          vect5DInt = UALLowLevel.getVect5DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect5DInt;
            } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
        </xsl:when>

        <xsl:when test="@type='array6dflt_type'">
          try {
          vect6DDouble = UALLowLevel.getVect6DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect6DDouble;
           } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@type='array6ddbl_type'">
          try {
          vect6DDouble = UALLowLevel.getVect6DDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect6DDouble;
           }catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
       </xsl:when>
        <xsl:when test="@type='array6dint_type'">
          try {
          vect6DInt = UALLowLevel.getVect6DInt(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = vect6DInt;
            } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
        </xsl:when>

        <!--========== Arrays of structures ==========-->
        <xsl:when test="@type='struct_array'">
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
                  cpos[i0].<xsl:value-of select = "translate(@path,'/','.')"/> = new <xsl:value-of select = "$fulltypepath"/>[UALLowLevel.getObjectDim(expIdx,obj1)];
                for (int i1 = 0; i1 &lt; UALLowLevel.getObjectDim(expIdx,obj1); i1++) {  // process array elements
                  cpos[i0].<xsl:value-of select = "translate(@path,'/','.')"/>[i1] = new <xsl:value-of select = "$fulltypepath"/>();
                  <xsl:apply-templates select = "field" mode = "GET_FROM_OBJECT">
                    <xsl:with-param name="level">1</xsl:with-param>
                    <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
                    <xsl:with-param name="idxpath">cpos[i0].<xsl:value-of select = "translate(@path,'/','.')"/>[i1]</xsl:with-param>
                    <xsl:with-param name="timed">no</xsl:with-param>
                  </xsl:apply-templates>
                }
              }
              UALLowLevel.releaseObject(expIdx,obj1);
            } catch (Exception exc) {
              for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
            }
          }
        </xsl:when>

        <!--========== Regular structures ==========-->
        <xsl:when test="@type='structure'">
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
    <xsl:when test="@type='struct_array'">
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
    <xsl:when test="@type='structure'">
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
          <xsl:when test="@type='xs:string'">
//GABRIELE MARCH 2011            try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getStringFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
          </xsl:when>
          <xsl:when test="@type='xs:integer'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getIntFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = EMPTY_INT;}
          </xsl:when>
          <xsl:when test="@type='xs:boolean'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getBooleanFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){}
          </xsl:when>
          <xsl:when test="@type='xs:double'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = EMPTY_DOUBLE;}
          </xsl:when>
          <xsl:when test="@type='xs:float'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = EMPTY_DOUBLE;}
          </xsl:when>

          <xsl:when test="@type='vecflt_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect1DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
          </xsl:when>
          <xsl:when test="@type='vecstring_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect1DStringFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
          </xsl:when>
          <xsl:when test="@type='vecdbl_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect1DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
//          </xsl:when>
          <xsl:when test="@type='vecint_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect1DIntFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
          </xsl:when>

          <xsl:when test="@type='matflt_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect2DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
          <xsl:when test="@type='matdbl_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect2DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
          <xsl:when test="@type='matint_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect2DIntFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>

          <xsl:when test="@type='array3dflt_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect3DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        <xsl:when test="@type='array3ddbl_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect3DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        <xsl:when test="@type='array3dint_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect3DIntFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>

          <xsl:when test="@type='array4dflt_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect4DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        <xsl:when test="@type='array4ddbl_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect4DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        <xsl:when test="@type='array4dint_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect4DIntFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>

          <xsl:when test="@type='array5dflt_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect5DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        <xsl:when test="@type='array5ddbl_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect5DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        <xsl:when test="@type='array5dint_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect5DIntFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>

          <xsl:when test="@type='array6dflt_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect6DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        <xsl:when test="@type='array6ddbl_type'">
//GABRIELE MARCH 2011              try {
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getVect6DDoubleFromObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>);
//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}
        </xsl:when>
        <xsl:when test="@type='array6dint_type'">
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
<!--      Put field in a full time-dependent CPO     -->
<!--=================================================-->

<xsl:template match = "field" mode = "PUT_TIMED">
  <xsl:choose>
    <!-- Time-dependent field -->
    <xsl:when test = "@timed = 'yes'">
      <xsl:choose>
        <xsl:when test="@type='xs:string'">
          vect1DString = new Vect1DString(cpos.length);
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect1DString.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          UALLowLevel.putVect1DString(expIdx, path, "<xsl:value-of select = "@path"/>", vect1DString, true);
        </xsl:when>
        <xsl:when test="@type='xs:integer'">
          vect1DInt = new Vect1DInt(cpos.length);
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect1DInt.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          UALLowLevel.putVect1DInt(expIdx, path, "<xsl:value-of select = "@path"/>", vect1DInt, true);
        </xsl:when>
        <xsl:when test="@type='xs:boolean'">
          vect1DBoolean = new Vect1DBoolean(cpos.length);
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect1DBoolean.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          UALLowLevel.putVect1DBoolean(expIdx, path, "<xsl:value-of select = "@path"/>", vect1DBoolean, true);
        </xsl:when>
        <xsl:when test="@type='xs:double'">
          vect1DDouble = new Vect1DDouble(cpos.length);
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect1DDouble.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          UALLowLevel.putVect1DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", vect1DDouble, true);
        </xsl:when>
        <xsl:when test="@type='xs:float'">
          vect1DDouble = new Vect1DDouble (cpos.length);
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect1DDouble.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          UALLowLevel.putVect1DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", vect1DDouble, true);
        </xsl:when>

        <xsl:when test="@type='vecflt_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect2DDouble = null;
          else
          {
            vect2DDouble = new Vect2DDouble(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(), cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect2DDouble.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect2DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", vect2DDouble, true);
        </xsl:when>
        <xsl:when test="@type='vecdbl_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect2DDouble = null;
          else
          {
            vect2DDouble = new Vect2DDouble(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(), cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect2DDouble.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect2DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", vect2DDouble, true);
        </xsl:when>
        <xsl:when test="@type='vecint_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect2DInt = null;
          else
          {
            vect2DInt = new Vect2DInt(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(), cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect2DInt.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect2DInt(expIdx, path, "<xsl:value-of select = "@path"/>", vect2DInt, true);
        </xsl:when>

        <xsl:when test="@type='matflt_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect3DDouble = null;
          else
          {
            vect3DDouble = new Vect3DDouble(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(0), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(1), cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect3DDouble.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect3DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", vect3DDouble, true);
        </xsl:when>
        <xsl:when test="@type='matint_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect3DInt = null;
          else
          {
            vect3DInt = new Vect3DInt(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(0), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(1), cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect3DInt.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect3DInt(expIdx, path, "<xsl:value-of select = "@path"/>", vect3DInt, true);
        </xsl:when>
        <xsl:when test="@type='matdbl_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect3DDouble = null;
          else
          {
            vect3DDouble = new Vect3DDouble(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(0), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(1), cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect3DDouble.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect3DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", vect3DDouble, true);
        </xsl:when>

        <xsl:when test="@type='array3dflt_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect4DDouble = null;
          else
          {
            vect4DDouble = new Vect4DDouble(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(0), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(1), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(2), cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect4DDouble.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect4DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", vect4DDouble, true);
        </xsl:when>
        <xsl:when test="@type='array3ddbl_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect4DDouble = null;
          else
          {
            vect4DDouble = new Vect4DDouble(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(0), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(1), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(2), cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect4DDouble.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect4DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", vect4DDouble, true);
        </xsl:when>
        <xsl:when test="@type='array3dint_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect4DInt = null;
          else
          {
            vect4DInt = new Vect4DInt(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(0), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(1), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(2), cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect4DInt.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect4DInt(expIdx, path, "<xsl:value-of select = "@path"/>", vect4Dint, true);
        </xsl:when>

        <xsl:when test="@type='array4dflt_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect5DDouble = null;
          else
          {
            vect5DDouble = new Vect5DDouble(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(0), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(1), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(2), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(3), cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect5DDouble.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect5DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", vect5DDouble, true);
        </xsl:when>
        <xsl:when test="@type='array4ddbl_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect5DDouble = null;
          else
          {
            vect5DDouble = new Vect5DDouble(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(0), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(1), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(2), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(3), cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect5DDouble.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect5DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", vect5DDouble, true);
        </xsl:when>
        <xsl:when test="@type='array4dint_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect5DInt = null;
          else
          {
            vect5DInt = new Vect5DInt(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(0), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(1), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(2), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(3), cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect5DInt.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect5DInt(expIdx, path, "<xsl:value-of select = "@path"/>", vect5DInt, true);
        </xsl:when>

        <xsl:when test="@type='array5dflt_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect6DDouble = null;
          else
          {
            vect6DDouble = new Vect6DDouble(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(0), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(1), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(2), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(3), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(4), cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect6DDouble.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect6DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", vect6DDouble, true);
        </xsl:when>
        <xsl:when test="@type='array5ddbl_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect6DDouble = null;
          else
          {
            vect6DDouble = new Vect6DDouble(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(0), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(1), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(2), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(3), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(4),  cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect6DDouble.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect6DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", vect6DDouble, true);
        </xsl:when>
        <xsl:when test="@type='array5dint_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect6DInt = null;
          else
          {
            vect6DInt = new Vect6DInt(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(0), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(1), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(2), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(3), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(4), cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect6DInt.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect6DInt(expIdx, path, "<xsl:value-of select = "@path"/>", vect6DInt, true);
        </xsl:when>

        <xsl:when test="@type='array6dflt_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect7DDouble = null;
          else
          {
            vect7DDouble = new Vect7DDouble(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(0), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(1), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(2), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(3), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(4), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(5), cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect7DDouble.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect7DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", vect7DDouble, true);
        </xsl:when>
        <xsl:when test="@type='array6ddbl_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect7DDouble = null;
          else
          {
            vect7DDouble = new Vect7DDouble(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(0), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(1), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(2), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(3), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(4),  cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(5),cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect7DDouble.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect7DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", vect7DDouble, true);
        </xsl:when>
        <xsl:when test="@type='array6dint_type'">
          if(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> == null)
            vect7DInt = null;
          else
          {
            vect7DInt = new Vect7DInt(cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(0), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(1), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(2), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(3), cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(4),  cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(5),  cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.getDim(6),cpos.length);
            for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  cpos.length; i++)
              vect7DInt.setElementAt(i, cpos[i].<xsl:value-of select = "translate(@path,'/','.')"/>);
          }
          UALLowLevel.putVect7DInt(expIdx, path, "<xsl:value-of select = "@path"/>", vect6DInt, true);
        </xsl:when>

        <!--========== Arrays of structures ==========-->
        <xsl:when test="@type='struct_array'">
          { /*     Array of structure     */
            /*     Write timed fields    */
            int obj_all_times = UALLowLevel.beginObject(expIdx,-1,0,path+"/<xsl:value-of select = "@path"/>",TIMED_CLEAR);
            for (int i0 = 0; i0 &lt; cpos.length; i0++) {
              int obj1 = UALLowLevel.beginObject(expIdx,obj_all_times,i0,"ALLTIMES",TIMED);
              if (cpos[i0].<xsl:value-of select = "translate(@path,'/','.')"/> != null) {
                for (int i1 = 0; i1 &lt; cpos[i0].<xsl:value-of select = "translate(@path,'/','.')"/>.length; i1++) {
                  <xsl:apply-templates select = "field" mode = "PUT_IN_OBJECT">
                    <xsl:with-param name="level">1</xsl:with-param>
                    <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
                    <xsl:with-param name="idxpath">cpos[i0].<xsl:value-of select = "translate(@path,'/','.')"/>[i1]</xsl:with-param>
                    <xsl:with-param name="timed">yes</xsl:with-param>
                  </xsl:apply-templates>
                }
              }
              UALLowLevel.putObjectInObject(expIdx, obj_all_times, "ALLTIMES", i0, obj1);
            }
            UALLowLevel.putObject(expIdx, path, "<xsl:value-of select = "@path"/>", obj_all_times, 1);

            /*     Write non-timed fields    */
            int obj1 = UALLowLevel.beginObject(expIdx,-1,0,path+"/<xsl:value-of select = "@path"/>",NON_TIMED);
            if (cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> != null) {
              for (int i1 = 0; i1 &lt; cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.length; i1++) {
                <xsl:apply-templates select = "field" mode = "PUT_IN_OBJECT">
                  <xsl:with-param name="level">1</xsl:with-param>
                  <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
                  <xsl:with-param name="idxpath">cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>[i1]</xsl:with-param>
                  <xsl:with-param name="timed">no</xsl:with-param>
                </xsl:apply-templates>
              }
            }
            UALLowLevel.putObject(expIdx, path, "<xsl:value-of select = "@path"/>", obj1, 0);
          }
        </xsl:when>

        <!--========== Regular structures ==========-->
        <xsl:when test="@type='structure'">
          <xsl:apply-templates select = "field" mode = "PUT_TIMED"/>
        </xsl:when>
      </xsl:choose>
    </xsl:when>

    <!-- Time-independent field -->
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="@type='xs:string'">
          UALLowLevel.putString(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>);
        </xsl:when>
        <xsl:when test="@type='xs:integer'">
          UALLowLevel.putInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>);
        </xsl:when>
        <xsl:when test="@type='xs:boolean'">
          UALLowLevel.putBoolean(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>);
        </xsl:when>
        <xsl:when test="@type='xs:double'">
          UALLowLevel.putDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>);
        </xsl:when>
        <xsl:when test="@type='xs:float'">
          UALLowLevel.putDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>);
        </xsl:when>

        <xsl:when test="@type='vecflt_type'">
          UALLowLevel.putVect1DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>
        <xsl:when test="@type='vecdbl_type'">
          UALLowLevel.putVect1DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>
        <xsl:when test="@type='vecint_type'">
          UALLowLevel.putVect1DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>
        <xsl:when test="@type='vecstring_type'">
          UALLowLevel.putVect1DString(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>

        <xsl:when test="@type='matflt_type'">
          UALLowLevel.putVect2DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>
        <xsl:when test="@type='matint_type'">
          UALLowLevel.putVect2DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>
        <xsl:when test="@type='matdbl_type'">
          UALLowLevel.putVect2DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>

        <xsl:when test="@type='array3dflt_type'">
          UALLowLevel.putVect3DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>
        <xsl:when test="@type='array3dint_type'">
          UALLowLevel.putVect3DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>
        <xsl:when test="@type='array3ddbl_type'">
          UALLowLevel.putVect3DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>

        <xsl:when test="@type='array4dflt_type'">
          UALLowLevel.putVect4DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>
        <xsl:when test="@type='array4dint_type'">
          UALLowLevel.putVect4DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>
        <xsl:when test="@type='array4ddbl_type'">
          UALLowLevel.putVect4DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>

        <xsl:when test="@type='array5dflt_type'">
          UALLowLevel.putVect5DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>
        <xsl:when test="@type='array5dint_type'">
          UALLowLevel.putVect5DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>
        <xsl:when test="@type='array5ddbl_type'">
          UALLowLevel.putVect5DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>

        <xsl:when test="@type='array6dflt_type'">
          UALLowLevel.putVect6DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>
        <xsl:when test="@type='array6dint_type'">
          UALLowLevel.putVect6DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>
        <xsl:when test="@type='array6ddbl_type'">
          UALLowLevel.putVect6DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>, false);
        </xsl:when>

        <!--========== Arrays of structures ==========-->
        <xsl:when test="@type='struct_array'">
          { /*     Array of structure    */
            int obj1 = UALLowLevel.beginObject(expIdx,-1,0,path+"/<xsl:value-of select = "@path"/>",NON_TIMED);
            if (cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/> != null) {
              for (int i1 = 0; i1 &lt; cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>.length; i1++) {
                <xsl:apply-templates select = "field" mode = "PUT_IN_OBJECT">
                  <xsl:with-param name="level">1</xsl:with-param>
                  <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
                  <xsl:with-param name="idxpath">cpos[0].<xsl:value-of select = "translate(@path,'/','.')"/>[i1]</xsl:with-param>
                  <xsl:with-param name="timed">no</xsl:with-param>
                </xsl:apply-templates>
              }
            }
            UALLowLevel.putObject(expIdx, path, "<xsl:value-of select = "@path"/>", obj1, 0);
          }
        </xsl:when>

        <!--========== Regular structures ==========-->
        <xsl:when test="@type='structure'">
          <xsl:apply-templates select = "field" mode = "PUT_TIMED"/>
        </xsl:when>
      </xsl:choose>

    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!--=======================================================-->
<!--  Put time-independent fields of a time-dependent CPO  -->
<!--=======================================================-->

<xsl:template match = "field" mode = "PUT_NON_TIMED">
  <xsl:choose>

    <!--========== Arrays of structures ==========-->
    <xsl:when test="@type='struct_array'">
      { /*     Array of structure     */
        int obj1 = UALLowLevel.beginObject(expIdx,-1,0,path+"/<xsl:value-of select = "@path"/>",NON_TIMED);
        if (cpo.<xsl:value-of select = "translate(@path,'/','.')"/> != null) {
          for (int i1 = 0; i1 &lt; cpo.<xsl:value-of select = "translate(@path,'/','.')"/>.length; i1++) {
            <xsl:apply-templates select = "field" mode = "PUT_IN_OBJECT">
              <xsl:with-param name="level">1</xsl:with-param>
              <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
              <xsl:with-param name="idxpath">cpo.<xsl:value-of select = "translate(@path,'/','.')"/>[i1]</xsl:with-param>
              <xsl:with-param name="timed">no</xsl:with-param>
            </xsl:apply-templates>
          }
        }
        UALLowLevel.putObject(expIdx, path, "<xsl:value-of select = "@path"/>", obj1, 0);
      }
    </xsl:when>

    <!--========== Regular structures ==========-->
    <xsl:when test="@type='structure'">
      <xsl:apply-templates select = "field" mode = "PUT_NON_TIMED"/>
    </xsl:when>

    <xsl:otherwise>
      <xsl:if test = "@timed = 'no'">
        <xsl:choose>
          <xsl:when test="@type='xs:string'">
            UALLowLevel.putString(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='xs:integer'">
            UALLowLevel.putInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='xs:boolean'">
            UALLowLevel.putBoolean(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='xs:double'">
            UALLowLevel.putDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='xs:float'">
            UALLowLevel.putDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>

          <xsl:when test="@type='vecflt_type'">
            UALLowLevel.putVect1DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>
          <xsl:when test="@type='vecdbl_type'">
            UALLowLevel.putVect1DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>
          <xsl:when test="@type='vecint_type'">
            UALLowLevel.putVect1DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>
          <xsl:when test="@type='vecstring_type'">
            UALLowLevel.putVect1DString(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>

          <xsl:when test="@type='matflt_type'">
            UALLowLevel.putVect2DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>
          <xsl:when test="@type='matint_type'">
            UALLowLevel.putVect2DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>
          <xsl:when test="@type='matdbl_type'">
            UALLowLevel.putVect2DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>

          <xsl:when test="@type='array3dflt_type'">
            UALLowLevel.putVect3DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>
          <xsl:when test="@type='array3dint_type'">
            UALLowLevel.putVect3DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>
          <xsl:when test="@type='array3ddbl_type'">
            UALLowLevel.putVect3DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>

          <xsl:when test="@type='array4dflt_type'">
            UALLowLevel.putVect4DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>
          <xsl:when test="@type='array4dint_type'">
            UALLowLevel.putVect4DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>
          <xsl:when test="@type='array4ddbl_type'">
            UALLowLevel.putVect4DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>

          <xsl:when test="@type='array5dflt_type'">
            UALLowLevel.putVect5DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>
          <xsl:when test="@type='array5dint_type'">
            UALLowLevel.putVect5DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>
          <xsl:when test="@type='array5ddbl_type'">
            UALLowLevel.putVect5DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>

          <xsl:when test="@type='array6dflt_type'">
            UALLowLevel.putVect6DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>
          <xsl:when test="@type='array6dint_type'">
            UALLowLevel.putVect6DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>
          <xsl:when test="@type='array6ddbl_type'">
            UALLowLevel.putVect6DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
          </xsl:when>

        </xsl:choose>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!--=================================================-->
<!--        Put time-dependent field in a slice      -->
<!--=================================================-->

<xsl:template match = "field" mode = "PUT_SLICE">
  <xsl:choose>

    <!--========== Regular structures ==========-->
    <xsl:when test="@type='structure'">
      <xsl:apply-templates select = "field" mode = "PUT_SLICE"/>
    </xsl:when>

    <xsl:otherwise>
      <xsl:if test = "@timed = 'yes'">
        <xsl:choose>

          <!--========== Arrays of structures ==========-->
          <xsl:when test="@type='struct_array'">
            { /*     Array of structure    */
              /* timed arrays of structures must be put inside a time container, even if there is a single time */
              int obj_single_time = UALLowLevel.beginObject(expIdx,-1,0,path+"/<xsl:value-of select = "@path"/>",TIMED);
              int obj1 = UALLowLevel.beginObject(expIdx,obj_single_time,0,"ALLTIMES",TIMED);
              if (cpo.<xsl:value-of select = "translate(@path,'/','.')"/> != null) {
                for (int i1 = 0; i1 &lt; cpo.<xsl:value-of select = "translate(@path,'/','.')"/>.length; i1++) {
                  <xsl:apply-templates select = "field" mode = "PUT_IN_OBJECT">
                    <xsl:with-param name="level">1</xsl:with-param>
                    <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
                    <xsl:with-param name="idxpath">cpo.<xsl:value-of select = "translate(@path,'/','.')"/>[i1]</xsl:with-param>
                    <xsl:with-param name="timed">yes</xsl:with-param>
                  </xsl:apply-templates>
                }
              }
              UALLowLevel.putObjectInObject(expIdx, obj_single_time, "ALLTIMES", 0, obj1);
              UALLowLevel.putObjectSlice(expIdx, path, "<xsl:value-of select = "@path"/>", (double)cpo.time, obj_single_time);
            }
          </xsl:when>

          <xsl:when test="@type='xs:string'">
            UALLowLevel.putStringSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>
          <xsl:when test="@type='xs:integer'">
              if(cpo.<xsl:value-of select = "translate(@path,'/','.')"/> != EMPTY_INT)
                  UALLowLevel.putIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>
          <xsl:when test="@type='xs:boolean'">
              if(cpo.<xsl:value-of select = "translate(@path,'/','.')"/> != EMPTY_INT)
                  UALLowLevel.putBooleanSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>
          <xsl:when test="@type='xs:double'">
              if(cpo.<xsl:value-of select = "translate(@path,'/','.')"/> != EMPTY_DOUBLE)
                  UALLowLevel.putDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>
          <xsl:when test="@type='xs:float'">
              if(cpo.<xsl:value-of select = "translate(@path,'/','.')"/> != EMPTY_DOUBLE)
                  UALLowLevel.putDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>

          <xsl:when test="@type='vecflt_type'">
            UALLowLevel.putVect1DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>
          <xsl:when test="@type='vecdbl_type'">
            UALLowLevel.putVect1DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>
          <xsl:when test="@type='vecint_type'">
            UALLowLevel.putVect1DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>

          <xsl:when test="@type='matflt_type'">
            UALLowLevel.putVect2DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>
          <xsl:when test="@type='matint_type'">
            UALLowLevel.putVect2DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>
          <xsl:when test="@type='matdbl_type'">
            UALLowLevel.putVect2DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>

          <xsl:when test="@type='array3dflt_type'">
            UALLowLevel.putVect3DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>
          <xsl:when test="@type='array3dint_type'">
            UALLowLevel.putVect3DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>
          <xsl:when test="@type='array3ddbl_type'">
            UALLowLevel.putVect3DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>

          <xsl:when test="@type='array4dflt_type'">
            UALLowLevel.putVect4DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>
          <xsl:when test="@type='array4dint_type'">
            UALLowLevel.putVect4DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>
          <xsl:when test="@type='array4ddbl_type'">
            UALLowLevel.putVect4DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>

          <xsl:when test="@type='array5dflt_type'">
            UALLowLevel.putVect5DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>
          <xsl:when test="@type='array5dint_type'">
            UALLowLevel.putVect5DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>
          <xsl:when test="@type='array5ddbl_type'">
            UALLowLevel.putVect5DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>

          <xsl:when test="@type='array6dflt_type'">
            UALLowLevel.putVect6DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>
          <xsl:when test="@type='array6dint_type'">
            UALLowLevel.putVect6DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>
          <xsl:when test="@type='array6ddbl_type'">
            UALLowLevel.putVect6DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, cpo.time);
          </xsl:when>

        </xsl:choose>

      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!--=================================================-->
<!--               Replace last slice                -->
<!--=================================================-->

<xsl:template match = "field" mode = "REPLACE_LAST_SLICE">
  <xsl:choose>

    <!--========== Regular structures ==========-->
    <xsl:when test="@type='structure'">
      <xsl:apply-templates select = "field" mode = "REPLACE_LAST_SLICE"/>
    </xsl:when>

    <xsl:otherwise>
      <xsl:if test = "@timed = 'yes'">
        <xsl:choose>
          <!--========== Arrays of structures ==========-->
          <xsl:when test="@type='struct_array'">
            { /*     Array of structure    */
              int obj1 = UALLowLevel.beginObject(expIdx,-1,0,path+"/<xsl:value-of select = "@path"/>",TIMED);
              for (int i1 = 0; i1 &lt; cpo.<xsl:value-of select = "translate(@path,'/','.')"/>.length; i1++) {
                <xsl:apply-templates select = "field" mode = "PUT_IN_OBJECT">
                  <xsl:with-param name="level">1</xsl:with-param>
                  <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
                  <xsl:with-param name="idxpath">cpo.<xsl:value-of select = "translate(@path,'/','.')"/>[i1]</xsl:with-param>
                  <xsl:with-param name="timed">yes</xsl:with-param>
                </xsl:apply-templates>
              }
              UALLowLevel.replaceLastObjectSlice(expIdx, path, "<xsl:value-of select = "@path"/>", obj1);
            }
          </xsl:when>

          <xsl:when test="@type='xs:string'">
            UALLowLevel.replaceLastStringSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='xs:integer'">
              if(cpo.<xsl:value-of select = "translate(@path,'/','.')"/> != EMPTY_INT)
                  UALLowLevel.replaceLastIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='xs:boolean'">
              if(cpo.<xsl:value-of select = "translate(@path,'/','.')"/> != EMPTY_INT)
                  UALLowLevel.replaceLastBooleanSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='xs:double'">
              if(cpo.<xsl:value-of select = "translate(@path,'/','.')"/> != EMPTY_DOUBLE)
                  UALLowLevel.replaceLastDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='xs:float'">
              if(cpo.<xsl:value-of select = "translate(@path,'/','.')"/> != EMPTY_DOUBLE)
                  UALLowLevel.replaceLastDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>

          <xsl:when test="@type='vecflt_type'">
            UALLowLevel.replaceLastVect1DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='vecdbl_type'">
            UALLowLevel.replaceLastVect1DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='vecint_type'">
            UALLowLevel.replaceLastVect1DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>

          <xsl:when test="@type='matflt_type'">
            UALLowLevel.replaceLastVect2DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='matint_type'">
            UALLowLevel.replaceLastVect2DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='matdbl_type'">
            UALLowLevel.replaceLastVect2DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>

          <xsl:when test="@type='array3dflt_type'">
            UALLowLevel.replaceLastVect3DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='array3dint_type'">
            UALLowLevel.replaceLastVect3DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='array3ddbl_type'">
            UALLowLevel.replaceLastVect3DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>

          <xsl:when test="@type='array4dflt_type'">
            UALLowLevel.replaceLastVect4DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='array4dint_type'">
            UALLowLevel.replaceLastVect4DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='array4ddbl_type'">
            UALLowLevel.replaceLastVect4DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>

          <xsl:when test="@type='array5dflt_type'">
            UALLowLevel.replaceLastVect5DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='array5dint_type'">
            UALLowLevel.replaceLastVect5DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='array5ddbl_type'">
            UALLowLevel.replaceLastVect5DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>

          <xsl:when test="@type='array6dflt_type'">
            UALLowLevel.replaceLastVect6DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='array6dint_type'">
            UALLowLevel.replaceLastVect6DIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@type='array6ddbl_type'">
            UALLowLevel.replaceLastVect6DDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>

        </xsl:choose>

      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!--=================================================-->
<!--       Put field in a time-independent CPO       -->
<!--=================================================-->

<xsl:template match = "field" mode = "PUT">
  <xsl:choose>
    <xsl:when test="@type='xs:string'">
      UALLowLevel.putString(expIdx, path, "<xsl:value-of select="@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
    </xsl:when>
    <xsl:when test="@type='xs:integer'">
      UALLowLevel.putInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
    </xsl:when>
    <xsl:when test="@type='xs:boolean'">
      UALLowLevel.putBoolean(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
    </xsl:when>
    <xsl:when test="@type='xs:double'">
      UALLowLevel.putDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
    </xsl:when>
    <xsl:when test="@type='xs:float'">
      UALLowLevel.putDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>);
    </xsl:when>

    <xsl:when test="@type='vecstring_type'">
      UALLowLevel.putVect1DString(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@type='vecflt_type'">
      UALLowLevel.putVect1DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@type='vecdbl_type'">
      UALLowLevel.putVect1DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@type='vecint_type'">
      UALLowLevel.putVect1DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>

    <xsl:when test="@type='matflt_type'">
      UALLowLevel.putVect2DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@type='matdbl_type'">
      UALLowLevel.putVect2DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@type='matint_type'">
      UALLowLevel.putVect2DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>

    <xsl:when test="@type='array3dflt_type'">
      UALLowLevel.putVect3DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@type='array3ddbl_type'">
      UALLowLevel.putVect3DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@type='array3dint_type'">
      UALLowLevel.putVect3DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>

    <xsl:when test="@type='array4dflt_type'">
      UALLowLevel.putVect4DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@type='array4ddbl_type'">
      UALLowLevel.putVect4DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@type='array4dint_type'">
      UALLowLevel.putVect4DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>

    <xsl:when test="@type='array5dflt_type'">
      UALLowLevel.putVect5DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@type='array5ddbl_type'">
      UALLowLevel.putVect5DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@type='array5dint_type'">
      UALLowLevel.putVect5DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>

   <xsl:when test="@type='array6dflt_type'">
      UALLowLevel.putVect6DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@type='array6ddbl_type'">
      UALLowLevel.putVect6DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>
    <xsl:when test="@type='array6dint_type'">
      UALLowLevel.putVect6DInt(expIdx, path, "<xsl:value-of select = "@path"/>", cpo.<xsl:value-of select = "translate(@path,'/','.')"/>, false);
    </xsl:when>

    <!--========== Arrays of structures ==========-->
    <xsl:when test="@type='struct_array'">
      { /*     Array of structure     */
        int obj1 = UALLowLevel.beginObject(expIdx,-1,0,path+"/<xsl:value-of select = "@path"/>",NON_TIMED);
        if (cpo.<xsl:value-of select = "translate(@path,'/','.')"/> != null) {
          for (int i1 = 0; i1 &lt; cpo.<xsl:value-of select = "translate(@path,'/','.')"/>.length; i1++) {
            <xsl:apply-templates select = "field" mode = "PUT_IN_OBJECT">
              <xsl:with-param name="level">1</xsl:with-param>
              <xsl:with-param name="objpath"><xsl:value-of select="@name"/></xsl:with-param>
              <xsl:with-param name="idxpath">cpo.<xsl:value-of select = "translate(@path,'/','.')"/>[i1]</xsl:with-param>
              <xsl:with-param name="timed">no</xsl:with-param>
            </xsl:apply-templates>
          }
        }
        UALLowLevel.putObject(expIdx, path, "<xsl:value-of select = "@path"/>", obj1, 0);
      }
    </xsl:when>

    <!--========== Regular structures ==========-->
    <xsl:when test="@type='structure'">
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
    <xsl:when test="@type='struct_array'">
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
    <xsl:when test="@type='structure'">
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
          <xsl:when test="@type='xs:string'">
            UALLowLevel.putStringInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='xs:integer'">
            UALLowLevel.putIntInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='xs:boolean'">
            UALLowLevel.putBooleanInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='xs:double'">
            UALLowLevel.putDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='xs:float'">
            UALLowLevel.putDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>

          <xsl:when test="@type='vecstring_type'">
            UALLowLevel.putVect1DStringInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='vecflt_type'">
            UALLowLevel.putVect1DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='vecdbl_type'">
            UALLowLevel.putVect1DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='vecint_type'">
            UALLowLevel.putVect1DIntInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>

          <xsl:when test="@type='matflt_type'">
            UALLowLevel.putVect2DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='matdbl_type'">
            UALLowLevel.putVect2DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='matint_type'">
            UALLowLevel.putVect2DIntInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>

          <xsl:when test="@type='array3dflt_type'">
            UALLowLevel.putVect3DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='array3ddbl_type'">
            UALLowLevel.putVect3DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='array3dint_type'">
            UALLowLevel.putVect3DIntInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>

          <xsl:when test="@type='array4dflt_type'">
            UALLowLevel.putVect4DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='array4ddbl_type'">
            UALLowLevel.putVect4DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='array4dint_type'">
            UALLowLevel.putVect4DIntInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>

          <xsl:when test="@type='array5dflt_type'">
            UALLowLevel.putVect5DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='array5ddbl_type'">
            UALLowLevel.putVect5DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='array5dint_type'">
            UALLowLevel.putVect5DIntInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>

        <xsl:when test="@type='array6dflt_type'">
            UALLowLevel.putVect6DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='array6ddbl_type'">
            UALLowLevel.putVect6DDoubleInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>
          <xsl:when test="@type='array6dint_type'">
            UALLowLevel.putVect6DIntInObject(expIdx, obj<xsl:value-of select="$level"/>, "<xsl:value-of select="$currentobjpath"/>", i<xsl:value-of select="$level"/>, <xsl:value-of select="$currentidxpath"/>);
          </xsl:when>

          <xsl:when test="@type='structure'">
            <xsl:apply-templates select = "field" mode = "PUT"/>
          </xsl:when>
        </xsl:choose>
      </xsl:if>

    </xsl:otherwise>
  </xsl:choose>
</xsl:template>


</xsl:stylesheet>
