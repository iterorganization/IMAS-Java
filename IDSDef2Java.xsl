<?xml version="1.0" encoding="UTF-8"?>
<?modxslt-stylesheet type="text/xsl" media="fuffa, screen and $GET[stylesheet]" href="./%24GET%5Bstylesheet%5D" alternate="no" title="Translation using provided stylesheet" charset="ISO-8859-1" ?>
<?modxslt-stylesheet make gentype="text/xsl" media="screen" alternate="no" title="Show raw source of the XML file" charset="ISO-8859-1" ?>


<xsl:stylesheet xmlns:yaslt="http://www.mod-xslt2.com/ns/1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xs="http://www.w3.org/2001/XMLSchema" version="1.0"  xmlns:exsl="http://exslt.org/common" extension-element-prefixes="yaslt exsl"
  xmlns:fn="http://www.w3.org/2005/02/xpath-functions">
 
  <!--   <xsl:stylesheet xmlns:yaslt="http://www.mod-xslt2.com/ns/1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
     xmlns:xs="http://www.w3.org/2001/XMLSchema" version="1.0"
       xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
       xmlns:exsl="http://exslt.org/common"
       xmlns:func="http://exslt.org/functions"
       xmlns:my="http://localhost.localdomain/localns"
       exclude-result-prefixes="my"
       extension-element-prefixes="yaslt exsl func">

-->
  <xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
  <!-- MODE can be set to get or put in the 2 transformations for generating the subroutines, this reduced editing requirements
  it could also be done with 2 xslt operations on a single file, but this might be overkill ??
  PUT HERE "get" OR "put"-->

<!--=================================================-->
<!--                 General section                 -->
<!--=================================================-->

<xsl:template match = "/IDSs">
<xsl:result-document href="src/imasjava/imas.java" method="text"> 
 package imasjava;
import java.io.File;

import imasjava.ids.*;

public class imas {
        static {
        String libpath = System.getenv("IMAS_HOME");
        String imasversion = System.getenv("IMAS_VERSION");
        String ualversion = System.getenv("UAL_VERSION");
        if ( libpath == null ) {
            System.err.println("IMAS library not set up in the environment. (IMAS_HOME missing)");
            System.exit(0);
        }
        if ( imasversion == null ) {
            System.err.println("IMAS library not set up in the environment. (IMAS_VERSION missing)");
            System.exit(0);
        }
        if ( ualversion == null ) {
            System.err.println("IMAS library not set up in the environment. (UAL_VERSION missing)");
            System.exit(0);
        }
        libpath = libpath + "/core/imas/" + imasversion + "/ual/" + ualversion + "/lib";
        String imas_library = libpath + "/libimas.so";
        File f = new File(imas_library);
        if (! f.exists() ) {
            System.err.println("IMAS library not set up in the environment. (libimas.so missing)");
        }

        try {
            System.load(imas_library);
        }
        catch(UnsatisfiedLinkError exc) {
          System.err.println("(imas.java) Caught UnsatisfiedLinkError: " + exc);
        }
        catch(Throwable exc)
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

 <xsl:apply-templates select = "IDS" mode="DEFINE_IDS_MEMBER"/>
 }

</xsl:result-document>

 <xsl:apply-templates select = "IDS" mode="DEFINE_IDS_BASE_CLASS"/> 
</xsl:template>



<xsl:template match = "IDS" mode="DEFINE_IDS_MEMBER">
public static class <xsl:value-of select="@name"/> extends <xsl:value-of select="@name"/>_IDSBase 
{


}
</xsl:template>
<!--=======================================================================================================================-->
<!--=======================================================================================================================-->
<!--=======================================================================================================================-->
<!--                                       Definition of IDS Base class                                                    -->
<!--=======================================================================================================================-->
<!--=======================================================================================================================-->
<!--=======================================================================================================================-->

<xsl:template match = "IDS" mode="DEFINE_IDS_BASE_CLASS">

<xsl:result-document href="src/imasjava/ids/{@name}_IDSBase.java" standalone="yes" method="text">

package imasjava.ids;

import imasjava.*;

public class <xsl:value-of select="@name"/>_IDSBase
{
    static private boolean isHomogeneous = false;
    static private Vect1DDouble idsTime = null;
    
    
    static private void setHomogeneous(boolean isHomogeneous)
    {
         <xsl:value-of select="@name"/>_IDSBase.isHomogeneous = isHomogeneous;
    }
    
    static private boolean isHomogeneous()
    {
        return <xsl:value-of select="@name"/>_IDSBase.isHomogeneous;
    }
    
    
    static private void setIdsTime(Vect1DDouble idsTime )
    {
         <xsl:value-of select="@name"/>_IDSBase.idsTime = idsTime;
    }
    
    static private Vect1DDouble getIdsTime()
    {
         return <xsl:value-of select="@name"/>_IDSBase.idsTime;
    }
  
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


   /* ------------------------------------------------------------------------------------------------------------ */
   /* -----------------------------------        PUT       ------------------------------------------------------- */  
   /* ------------------------------------------------------------------------------------------------------------ */
/**
 * Method put stores a non timed <xsl:value-of select="@name"/> IDSs in the open database.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The passed <xsl:value-of select="@name"/> ids.
 * @exception UALException Issued when data cannot be stored for any reason.
 **/
    public static void put(int expIdx, String path, imas.<xsl:value-of select="@name"/> ids)  throws UALException
    {
    	<xsl:value-of select="@name"/>_IDSBase.setHomogeneous(ids.ids_properties.homogeneous_time == 1);
	
	<xsl:value-of select="@name"/>_IDSBase.setIdsTime(ids.time);
	
	ids.put(expIdx, path);
    }

    public void put(int expIdx, String path)  throws UALException
    {
          String        timepath = null;
	  Vect1DDouble  time = null; 
          String        ual_debug = System.getenv("ual_debug");
	  String strNodePath = "";

          delete(expIdx, path);
          UALLowLevel.beginIDSPut(expIdx, path);
  <!--        <xsl:apply-templates select = "field" mode = "PUT_SINGLE"/> -->
          UALLowLevel.endIDSPut(expIdx, path);
    }
   /* ------------------------------------------------------------------------------------------------------------------ */
   /* -----------------------------------        PUT  NON TIMED     ------------------------------------------------------- */  
   /* ------------------------------------------------------------------------------------------------------------------------ */
 /**
 * Method putNonTimed stores the non time dependent fields of a (timed) <xsl:value-of select="@name"/> IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param idss The timed <xsl:value-of select="@name"/> IDS
 * @exception UALException Issued when data cannot be stored for any reason.
 **/
    public static void putNonTimed(int expIdx, String path, imas.<xsl:value-of select="@name"/> ids) throws UALException
    {
         ids.putNonTimed(expIdx,  path);
    }

    public void putNonTimed(int expIdx, String path) throws UALException
    {
          String        ual_debug = System.getenv("ual_debug");
	  
	  String strNodePath = "";

          delete(expIdx, path);
          UALLowLevel.beginIDSPutNonTimed(expIdx, path);
  <!--        <xsl:apply-templates select="field" mode="PUT_SINGLE">
          <xsl:with-param name="non_timed" select="'yes'"/>
          </xsl:apply-templates>
    -->   UALLowLevel.endIDSPutNonTimed(expIdx, path);
    }
    
       /* ------------------------------------------------------------------------------------------------------------------ */
   /* -----------------------------------        PUT  SLICE     ------------------------------------------------------- */  
    /* ------------------------------------------------------------------------------------------------------------------ */
/**
 * Method putSlice appends a (timed) <xsl:value-of select="@name"/> IDS in the open database. The IDS instance will bring the associated
 * time value in the time field.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected d
 ata item. By convention, only a single tree level is defined for IDS objects in the database.
 * @param ids The timed <xsl:value-of select="@name"/> IDS
 * @exception UALException Issued when data cannot be stored for any reason.
 **/
    public static void putSlice(int expIdx, String path, imas.<xsl:value-of select="@name"/> ids) throws UALException
    {
          String        timepath;
          String        ual_debug = System.getenv("ual_debug");

    	  if (<xsl:value-of select="@name"/>_IDSBase.isHomogeneous() ) {
            System.out.println("ERROR : the PUT_SLICE routine works only for homogeneous time IDS");
            return;
          }
	  
          timepath="time";
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
 * by imas.EMPTY_INT if integer, imas.EMPTY_DOUBLE if float or double and empty String is string.
 **/
    public static imas.<xsl:value-of select="@name"/> getdb(String user, String machine, int shot, int run, String version, String path, int occurrence) throws UALException
    {
//		int idx = UALLowLevel.openDb(user, machine, shot, run, path, occurrence);
		int idx = imas.openEnv("ids", shot, run, user, machine, version);
		if (occurrence != 0)
			path = path + "/" + occurrence;
		imas.<xsl:value-of select="@name"/> retIdss = null;
                try {
                        retIdss = get(idx, path);
                }
                catch(Exception exc) {
                        retIdss = new imas.<xsl:value-of select="@name"/> ();
                        System.out.println("Return a new <xsl:value-of select="@name"/> empty IDS after catching exception: "+exc);
                }
		imas.close(idx);
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
 * by imas.EMPTY_INT if integer, imas.EMPTY_DOUBLE if float or double and empty String is string.
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
		imas.close(idx);
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


   /* ------------------------------------------------------------------------------------------------------------ */
   /* -----------------------------------       GET       ------------------------------------------------------- */  
   /* ------------------------------------------------------------------------------------------------------------ */
    public static imas.<xsl:value-of select="@name"/>  get(int expIdx, String path)  throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");

      imas.<xsl:value-of select="@name"/> ids = new imas.<xsl:value-of select="@name"/> ();
      ids.doGet(expIdx, path);
      return ids;
    }
    
    
    public void doGet(int expIdx, String path)  throws UALException
    {
      String        ual_debug = System.getenv("ual_debug");
        String strNodePath = "";

      UALLowLevel.beginIDSGet(expIdx, path, false);
  <!--    <xsl:apply-templates select = "field" mode = "GET_SINGLE"/> -->

    UALLowLevel.endIDSGet(expIdx, path);
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
 * by imas.EMPTY_INT if integer, imas.EMPTY_DOUBLE if double and empty String is string.
 **/
    public static imas.<xsl:value-of select="@name"/>  getSlice(int expIdx, String path, double time, int interpolMode) throws UALException
    {
      int           obj1, obj2, obj3, obj4, obj5, obj6, obj7;
      int           obj_single_time;
      String        timepath;
      String        ual_debug = System.getenv("ual_debug");
      double        retTime;
      imas.<xsl:value-of select="@name"/> ids = new imas.<xsl:value-of select="@name"/> ();
      retTime = UALLowLevel.beginIDSGetSlice(expIdx, path, time);
  <!--  <xsl:apply-templates select = "field" mode = "GET_SLICE" />
   -->   UALLowLevel.endIDSGetSlice(expIdx, path);
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
  <!--    <xsl:apply-templates select = "field" mode = "DELETE"/>
    -->
    }


     </xsl:otherwise>
  </xsl:choose>
 /**
 * Method dump is used for debugging and prints the content of the object
 **/
  public void dump()
  {
    System.out.println("***** <xsl:value-of select="@name"/> *****");
<!--    <xsl:apply-templates select = "field" mode = "DUMP">
      <xsl:with-param name="level">0</xsl:with-param>
      <xsl:with-param name="idxpath"></xsl:with-param>
    </xsl:apply-templates>
  -->  System.out.println("******************");
  }
  
  
     <xsl:apply-templates select=".//field[@data_type='structure' or @data_type='struct_array']"/>
     
}
</xsl:result-document>
</xsl:template>

    <xsl:template match="field[@data_type='structure' or @data_type='struct_array']">
        <xsl:variable name="this-name" select="@name"/>
        <xsl:variable name="this-type" select="@data_type"/>
    <!--    <xsl:variable name="this-type-name" select="@type-name"/>  -->
        <xsl:variable name="this-cpo-type" select="ancestor::IDS/@name"/>

     <!--   <xsl:if test="not (preceding::field[@name=$this-name and @data_type=$this-type and  ancestor::IDS/@name=$this-cpo-type])"> -->
           <xsl:apply-templates select="." mode="CLASS"/> 
	
	 
      <!--  </xsl:if> -->

    </xsl:template>
    
    
    
 <!-- ====================================================================================================================================================================== -->
<!-- ======================================================================================================================================================================= -->
<!-- ============================================================ SUBCLASS == 'structure' type element	 =================================================================== -->
<!-- ======================================================================================================================================================================= -->

  <xsl:template match="field[@data_type='structure']" mode="CLASS">
	  <xsl:variable name="class_name">
	<xsl:call-template name="BUILD_CLASS_NAME">
<!--	<xsl:with-param name="typepath"><xsl:value-of select = "@path"/></xsl:with-param > -->
	</xsl:call-template>
</xsl:variable>
/* ------------------------------------------------------------------------------------------------------------------------ */  
/* ------------------------------------------------------------------------------------------------------------------------ */
/* ---------           CLASS: <xsl:value-of select="translate(@path,'/','_')"/>__structure                      ----------- */
/* ------------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------------ */
    
    public static class  <xsl:value-of select="$class_name"/> 
    {
    
 	 <xsl:apply-templates select = "field" mode = "DECLARE"/> 
    <!--
     <xsl:choose>
    <xsl:when test="not(ancestor-or-self::field[@data_type='struct_array' and @maxoccur='unbounded'])">
   /* ____________________________________________________________________________________________________________ */
   /*_________________________________       PUT      _________________________________ */  
   /* ____________________________________________________________________________________________________________  */
  	public void put(int expIdx, String path, String strParentPath)  throws UALException
    {
          String        timepath = null;
	  Vect1DDouble  time = null;
	  
          String        ual_debug = System.getenv("ual_debug");
	  
	 String strNodePath = strParentPath + "<xsl:value-of select="@name"/>/";
    
         <xsl:apply-templates select = "field" mode = "PUT_SINGLE"/> 
    }          
 </xsl:when> 
   <xsl:otherwise>
  // I am descendant of AoS 2/3 so all my data are stored by putInObject !!!!
   /* ____________________________________________________________________________________________________________ */
   /* _________________________________        PUT IN OBJECT   _________________________________ */  
   /* _______________________________________________________________________________________________________________ */
    	public void putInObject(int expIdx, int idObj, String strParentObjPath, int objIdx)  throws UALException
    {
          String        timepath = null;
	  Vect1DDouble  time = null;
	  
          String        ual_debug = System.getenv("ual_debug");
	  
	  String strObjPath = strParentObjPath + "<xsl:value-of select="@name"/>/";
    
       <xsl:apply-templates select = "field" mode = "PUT_IN_OBJECT"/> 
    }          
     </xsl:otherwise>
   </xsl:choose>
        
   <xsl:choose>
    <xsl:when test="not(ancestor-or-self::field[@data_type='struct_array' and @maxoccur='unbounded'])">
   /* ____________________________________________________________________________________________________________  */
   /* _________________________________       GET       ________________________________________________________ */  
   /* ____________________________________________________________________________________________________________  */
  	public void get(int expIdx, String path, String strParentPath)   throws UALException
    {
          String        timepath = null;
	  Vect1DDouble  time = null;
	  
          String       ual_debug = System.getenv("ual_debug");
	  
	 String strNodePath = strParentPath + "<xsl:value-of select="@name"/>/";
    
          <xsl:apply-templates select = "field" mode = "GET_SINGLE"/>
    }     
      </xsl:when>
        <xsl:otherwise>
  // I am descendant of AoS 2/3 so all my data are stored by putInObject !!!!
   /* ____________________________________________________________________________________________________________  */
   /* _________________________________        GET FROM OBJECT   _________________________________ */  
   /* ____________________________________________________________________________________________________________  */
   
    	public void getFromObject(int expIdx, int idObj, String strParentObjPath, int objIdx)  throws UALException
    {
          String        timepath = null;
	  Vect1DDouble  time = null;
	  
          String        ual_debug = System.getenv("ual_debug");
	  
	 String strObjPath = strParentObjPath + "<xsl:value-of select="@name"/>/";
    
          <xsl:apply-templates select = "field" mode = "GET_FROM_OBJECT"/>
    }          
     
   </xsl:otherwise>
   </xsl:choose>
   
   /* ____________________________________________________________________________________________________________  */
   /* _________________________________       DELETE     _________________________________ */  
   /* ____________________________________________________________________________________________________________  */ 
  <xsl:choose>
     <xsl:when test="ancestor::field[@data_type='struct_array' and @maxoccur='unbounded']">
  //  public void delete(int expIdx, String path, String strParentPath, int idx) throws UALException {}
  //  		I am descendant of AoS 2/3 so all my data were deleted by (grand)parent and no delete() is needed !!!! ABC
 </xsl:when>
<xsl:otherwise>
       public void delete(int expIdx, String path, String strParentPath) throws UALException
    {
      String ual_debug = System.getenv("ual_debug");
     
      String strNodePath = strParentPath + "<xsl:value-of select="@name"/>/";
       
      <xsl:apply-templates select = "field" mode = "DELETE"/>
        
    }             
     </xsl:otherwise>
   </xsl:choose>
    
   /* ____________________________________________________________________________________________________________  */
   /* ___________________________________        DUMP      ___________________________________ */  
   /* ____________________________________________________________________________________________________________  */
   /**
    * Method dump is used for debugging and prints the content of the object
    **/
    public void dump()
   {
     System.out.println("***** <xsl:value-of select="@name"/> *****");
     <xsl:apply-templates select = "field" mode = "DUMP">
    </xsl:apply-templates>
    System.out.println("******************");
}

    
       <xsl:if test="not(ancestor-or-self::field[@type='dynamic'])">
    /* ____________________________________________________________________________________________________________ */
   /*_________________________________       PUT NON TIMED    __________________________________________ */  
   /* ____________________________________________________________________________________________________________  */
  	public void putNonTimed(int expIdx, String path, String strParentPath)  throws UALException
    {
          String        timepath = null;
	  Vect1DDouble  time = null;
	  
          String        ual_debug = System.getenv("ual_debug");
	  
	 String strNodePath = strParentPath +  "<xsl:value-of select="@name"/>/";
    
          <xsl:apply-templates select="field" mode="PUT_SINGLE">
          <xsl:with-param name="non_timed" select="'yes'"/>
          </xsl:apply-templates>
    }    
    
    </xsl:if>      

      <xsl:choose>
    <xsl:when test="not(ancestor-or-self::field[@data_type='struct_array' and @maxoccur='unbounded'])">
     --> 
   /* ____________________________________________________________________________________________________________ */
   /*_________________________________       PUT SLICE     _________________________________ */  
   /* ____________________________________________________________________________________________________________  */
  	public void putSlice(int expIdx, String path, String strParentPath)  throws UALException
    {
          String        timepath = null;
	  Vect1DDouble  time = null;
	  
          String        ual_debug = System.getenv("ual_debug");
	  
	 String strNodePath = strParentPath + "<xsl:value-of select="@name"/>/";
    
         <xsl:apply-templates select = "field" mode = "PUT_SLICE"/> 
    }       
    <!--    
 </xsl:when> 
   <xsl:otherwise>
  // I am descendant of AoS 2/3 so all my data are stored by putInObject !!!!
   /* ____________________________________________________________________________________________________________ */
   /* _________________________________        PUT IN OBJECT   _________________________________ */  
   /* _______________________________________________________________________________________________________________ */
    	public void putInObject(int expIdx, int idObj, String strParentObjPath, int objIdx)  throws UALException
    {
          String        timepath = null;
	  Vect1DDouble  time = null;
	  
          String        ual_debug = System.getenv("ual_debug");
	  
	  String strObjPath = strParentObjPath + "<xsl:value-of select="@name"/>/";
    
       <xsl:apply-templates select = "field" mode = "PUT_IN_OBJECT"/> 
    }          
     </xsl:otherwise>
   </xsl:choose>
 -->
    }
 
 
 
   /* ____________________________________________________________________________________________________________  */
    </xsl:template>

<!-- ============================================================= SUBCLASS == 'structure' type element	- END ============================================================== -->

 <!-- ====================================================================================================================================================================== -->
<!-- ======================================================================================================================================================================= -->
<!-- ============================================================ SUBCLASS == 'AoS' type element	 =================================================================== -->
<!-- ======================================================================================================================================================================= -->

  <xsl:template match="field[@data_type='struct_array']" mode="CLASS">
	  <xsl:variable name="class_name">
	<xsl:call-template name="BUILD_CLASS_NAME">
<!--	<xsl:with-param name="typepath"><xsl:value-of select = "@path"/></xsl:with-param > -->
	</xsl:call-template>
</xsl:variable>
/* ------------------------------------------------------------------------------------------------------------------------ */  
/* ------------------------------------------------------------------------------------------------------------------------ */
/* ---------           CLASS: <xsl:call-template name="COMMENT_FIELD_SHORT"/>                           ----------- */
/* ------------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------------ */
     public static class  <xsl:value-of select="$class_name"/>
{
     <xsl:apply-templates select = "field" mode = "DECLARE"/>
    
  <!--
   <xsl:choose>
     <xsl:when test="ancestor-or-self::field[@data_type='struct_array' and @maxoccur='unbounded']">
  // I am descendant of AoS 2/3 so all my data are stored by putInObject !!!!
   /* ____________________________________________________________________________________________________________ */
   /* ___________________________________        PUT IN OBJECT   ___________________________________ */  
   /* ____________________________________________________________________________________________________________ */
   
    	public void putInObject(int expIdx, int idObj, String strParentObjPath, int objIdx)  throws UALException
    {
          String        timepath = null;
	  Vect1DDouble  time = null;
	  
          String        ual_debug = System.getenv("ual_debug");
	  
	 String strObjPath = strParentObjPath + "<xsl:value-of select="@name"/>/";
    
          <xsl:apply-templates select = "field" mode = "PUT_IN_OBJECT"/> 
    }          
    
  
      </xsl:when>
        <xsl:otherwise>

   /* ____________________________________________________________________________________________________________ */
   /* ___________________________________        PUT       ___________________________________ */  
   /* ____________________________________________________________________________________________________________ */
  	public void put(int expIdx, String path, String strParentPath, int objIdx)   throws UALException
    {
          String        timepath = null;
	  Vect1DDouble  time = null;
	  
          String       ual_debug = System.getenv("ual_debug");
	  
	 String strNodePath = strParentPath + "<xsl:value-of select="@name"/>/";
    
         <xsl:apply-templates select = "field" mode = "PUT_SINGLE"/> 
    }          
   </xsl:otherwise>
   </xsl:choose>
   
   <xsl:choose>
    <xsl:when test="not(ancestor-or-self::field[@data_type='struct_array' and @maxoccur='unbounded'])">
   /* ____________________________________________________________________________________________________________ */
   /* ___________________________________       GET       ___________________________________ */  
   /* ____________________________________________________________________________________________________________ */
  	public void get(int expIdx, String path, String strParentPath, int nodeIdx)   throws UALException
    {
          String        timepath = null;
	  Vect1DDouble  time = null;
	  
          String       ual_debug = System.getenv("ual_debug");
	  
	 String strNodePath = strParentPath + nodeIdx + "/<xsl:value-of select="@name"/>/";
    
          <xsl:apply-templates select = "field" mode = "GET_SINGLE"/>
    }     
      </xsl:when>
        <xsl:otherwise>
  // I am descendant of AoS 2/3 so all my data are stored by putInObject !!!!
   /* ____________________________________________________________________________________________________________ */
   /* ___________________________________        GET FROM OBJECT   ___________________________________ */  
   /* ____________________________________________________________________________________________________________ */
   
    	public void getFromObject(int expIdx, int idObj, String strParentObjPath, int objIdx)  throws UALException
    {
          String        timepath = null;
	  Vect1DDouble  time = null;
	  
          String        ual_debug = System.getenv("ual_debug");
	  
	 String strObjPath = strParentObjPath + "<xsl:value-of select="@name"/>/";
    
          <xsl:apply-templates select = "field" mode = "GET_FROM_OBJECT"/>
    }          
     
   </xsl:otherwise>
   </xsl:choose>
   
   /* ____________________________________________________________________________________________________________ */
   /* ___________________________________       DELETE     ___________________________________ */  
   /* ____________________________________________________________________________________________________________ */
  <xsl:choose>
     <xsl:when test="ancestor::field[@data_type='struct_array' and @maxoccur='unbounded']">
  //  public void delete(int expIdx, String path, String strParentPath, int idx) throws UALException {}
  //  		I am descendant of AoS 2/3 so all my data were deleted by (grand)parent and no delete() is needed !!!! ABC
 </xsl:when>
<xsl:otherwise>
       public void delete(int expIdx, String path, String strParentPath, int idx) throws UALException
    {
      String ual_debug = System.getenv("ual_debug");
     
      String strNodePath = strParentPath + "<xsl:value-of select="@name"/>/" + idx + "/";
       
      <xsl:apply-templates select = "field" mode = "DELETE"/>
    }             
     </xsl:otherwise>
   </xsl:choose>


  
 
   <xsl:if test="not(ancestor-or-self::field[@type='dynamic'])">
    /* ____________________________________________________________________________________________________________ */
   /*_________________________________       PUT   NON TIMED    _________________________________ */  
   /* ____________________________________________________________________________________________________________  */
  	public void putNonTimed(int expIdx, String path, String strParentPath, int iNodeIdx)  throws UALException
    {
          String        timepath = null;
	  Vect1DDouble  time = null;
	  
          String        ual_debug = System.getenv("ual_debug");
	  
	 String strNodePath = strParentPath + iNodeIdx + "/<xsl:value-of select="@name"/>/";
    
          <xsl:apply-templates select="field" mode="PUT_SINGLE">
          <xsl:with-param name="non_timed" select="'yes'"/>
          </xsl:apply-templates>
    }      
    </xsl:if>    
      -->
       /* ____________________________________________________________________________________________________________ */
   /*_________________________________       PUT  SLICE    _________________________________ */  
   /* ____________________________________________________________________________________________________________  */
  	public void putSlice(int expIdx, String path, String strParentPath)  throws UALException
    {
          String        timepath = null;
	  Vect1DDouble  time = null;
	  
          String        ual_debug = System.getenv("ual_debug");
	  
	 String strNodePath = strParentPath + "<xsl:value-of select="@name"/>/";
    
         <xsl:apply-templates select = "field" mode = "PUT_SLICE"/> 
    }       
    <!--    
 </xsl:when> 
   <xsl:otherwise>
  // I am descendant of AoS 2/3 so all my data are stored by putInObject !!!!
   /* ____________________________________________________________________________________________________________ */
   /* _________________________________        PUT IN OBJECT   _________________________________ */  
   /* _______________________________________________________________________________________________________________ */
    	public void putInObject(int expIdx, int idObj, String strParentObjPath, int objIdx)  throws UALException
    {
          String        timepath = null;
	  Vect1DDouble  time = null;
	  
          String        ual_debug = System.getenv("ual_debug");
	  
	  String strObjPath = strParentObjPath + "<xsl:value-of select="@name"/>/";
    
       <xsl:apply-templates select = "field" mode = "PUT_IN_OBJECT"/> 
    }          
     </xsl:otherwise>
   </xsl:choose>
 -->
 
           }
  /* ___________________________________ */
    </xsl:template>

<!-- ============================================================= SUBCLASS == 'AoS' type element	- END ============================================================== -->


 <!-- ====================================================================================================================================================================== -->
<!-- ======================================================================================================================================================================= -->
<!-- ============================================================              TEMPLATES         	 =================================================================== -->
<!-- ======================================================================================================================================================================= -->

   <xsl:template name = "COMMENT_FIELD_SHORT">
   <xsl:text>&#xA;// </xsl:text>
   	 <xsl:if test="@maxoccur!='unbounded' and @data_type='struct_array'">
		<xsl:text>[AoS 1] : </xsl:text>
	  </xsl:if>
       <xsl:if test="(not(@type) or @type!='dynamic') and @maxoccur='unbounded' and @data_type='struct_array'">
		<xsl:text>[AoS 2] : </xsl:text>
	  </xsl:if>
   <xsl:if test="@type='dynamic' and @maxoccur='unbounded' and @data_type='struct_array'">
		<xsl:text>[AoS 3] : </xsl:text>
	  </xsl:if>
 <xsl:value-of select="@name"/> : <xsl:value-of select="@path"/> : <xsl:value-of select="@data_type"/> : <xsl:value-of select="@type"/><xsl:text> : </xsl:text>
    </xsl:template>

   
    
<!--=====================================================================================================================================-->
<!--                  Delete field                                                                                                       -->
<!--=====================================================================================================================================-->

<xsl:template match="field" mode="DELETE">
<xsl:param name="variable_path"/>
<xsl:param name="mds_path"/>
<xsl:call-template name="COMMENT_FIELD_SHORT"/>
<xsl:choose>
        <!--========== Regular structures ==========-->
<xsl:when test="@data_type='structure'">
<!-- BP:
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
 -->
    this.<xsl:value-of select = "@name"/>.delete(expIdx,path,strNodePath);
</xsl:when>

        <!--========== Arrays of structures ==========-->
<xsl:when test="@data_type='struct_array' and @maxoccur!='unbounded'">
    for (int i = 0; i &lt;<xsl:value-of select = "@maxoccur"/>; i++){
       this.<xsl:value-of select = "@name"/>[i].delete(expIdx,path,strNodePath, i);
      }
    
</xsl:when>
<xsl:otherwise>
      
      <xsl:if test="@data_type='struct_array' and @maxoccur='unbounded'">
      //AOS 2/3 
     </xsl:if>
       UALLowLevel.deleteData(expIdx, path, strNodePath + "<xsl:value-of select = "@name"/>");  
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

  <xsl:call-template name="COMMENT_FIELD_SHORT"/>
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
        if(<xsl:value-of select = "$currentidxpath"/> != imas.EMPTY_INT)
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
        if(<xsl:value-of select = "$currentidxpath"/> != imas.EMPTY_DOUBLE)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
         if(<xsl:value-of select = "$currentidxpath"/> != imas.EMPTY_DOUBLE)
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
    
      if (<xsl:value-of select = "@name"/> != null) 
      {
        for (int i = 0; i &lt; <xsl:value-of select = "@name"/>.length; i++) 
	{
            this.<xsl:value-of select = "@name"/>[i].dump()
        }
      }
    </xsl:when>

    <xsl:when test="@data_type='structure'">
     if (<xsl:value-of select = "@name"/> != null) 
        this.<xsl:value-of select = "@name"/>.dump();
     else
        System.out.println("Empty");
    </xsl:when>

  </xsl:choose>
</xsl:template>

<!--=================================================-->
<!--                Field declaration                -->
<!--=================================================-->

<xsl:template match = "field" mode = "DECLARE">
  <xsl:call-template name="COMMENT_FIELD_SHORT"/>
 <!-- /* <xsl:value-of select="@documentation"/>**/ -->
  <xsl:choose>
    <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
      public String <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
      public int <xsl:value-of select = "@name"/> = imas.EMPTY_INT;
    </xsl:when>
    <xsl:when test="@name='xs:boolean'">
      public boolean <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='xs:double'">
      public double <xsl:value-of select = "@name"/> = imas.EMPTY_DOUBLE;
    </xsl:when>
    <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
      public double <xsl:value-of select = "@name"/> = imas.EMPTY_DOUBLE;
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
    <!--                       -->
    <xsl:when test="@data_type='FLT_2D'">
      public Vect2DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <!--                       -->
    <xsl:when test="@name='matdbl_type'">
      public Vect2DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <!--                       -->
    <xsl:when test="@data_type='INT_2D'">
      public Vect2DInt <xsl:value-of select = "@name"/>;
    </xsl:when>
     <!--                       -->
    <xsl:when test="@data_type='FLT_3D'">
      public Vect3DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <!--                       -->
    <xsl:when test="@data_type='INT_3D'">
      public Vect3DInt  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <!--                       -->
    <xsl:when test="@name='array3ddbl_type'">
      public Vect3DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <!--                       -->
    <xsl:when test="@data_type='FLT_4D'">
      public Vect4DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='array4dint_type'">
      public Vect4DInt  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='array4ddbl_type'">
      public Vect4DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <!--                       -->
    <xsl:when test="@data_type='FLT_5D'">
      public Vect5DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='array5dint_type'">
      public Vect5DInt  <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='array5ddbl_type'">
      public Vect5DDouble  <xsl:value-of select = "@name"/>;
    </xsl:when>
      <!--                       -->
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
    <!--                       -->
    <xsl:when test="@data_type='struct_array'">
      <xsl:variable name="class_name">
	<xsl:call-template name="BUILD_CLASS_NAME">
	</xsl:call-template>
</xsl:variable>
	public  <xsl:value-of select="$class_name"/><xsl:text> </xsl:text> <xsl:value-of select = "@name"/>[];
    </xsl:when>

    <xsl:when test="@data_type='structure'">
      <xsl:variable name="class_name">
	<xsl:call-template name="BUILD_CLASS_NAME">
	</xsl:call-template>
</xsl:variable>
    public  <xsl:value-of select="$class_name"/><xsl:text> </xsl:text>  <xsl:value-of select = "@name"/> = new  <xsl:value-of select="$class_name"/>();
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


<!--=================================================================-->
<!-- function to generate the full subclass name from the field path -->
<!--           (adds the "Class" keyword for each subclass)          -->
<!--=================================================================-->

<xsl:template name="BUILD_CLASS_NAME">
  <xsl:value-of select="translate(@path,'/','_')"/><xsl:text>__</xsl:text>
     <xsl:choose>
 	<!-- =============================== Regular structure  ====================================== -->
        <xsl:when test="@data_type='structure'">
        <xsl:text>structure</xsl:text>
        </xsl:when>

 	<!-- ===================================== AoS type 1  ========================================= -->
         <xsl:when test="@data_type='struct_array' and @maxoccur!='unbounded'">
             <xsl:text>AoS1</xsl:text>
        </xsl:when>
         <!-- ====================== AoS type 2  ============================= -->
	<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and (not(@type) or @type!='dynamic')">
             <xsl:text>AoS2</xsl:text>
      </xsl:when>
 	<!-- ===================================== AoS type 3  ========================================= -->
<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and @type='dynamic'">
               <xsl:text>AoS3</xsl:text>
      </xsl:when>
           </xsl:choose>
                   <xsl:text>_Class</xsl:text>
</xsl:template>


<!--=================================================-->
<!--      Get field from a time-independent IDS      -->
<!--=================================================-->

<xsl:template match = "field" mode = "GET_SINGLE">
<xsl:param name="variable_path"/>
<xsl:param name="mds_path"/>
<xsl:param name="ids_name"/>
  <xsl:call-template name="COMMENT_FIELD_SHORT"/>
  	  <xsl:variable name="class_name">
	<xsl:call-template name="BUILD_CLASS_NAME">
<!--	<xsl:with-param name="typepath"><xsl:value-of select = "@path"/></xsl:with-param > -->
	</xsl:call-template>
</xsl:variable>
      <xsl:choose>
 	<!-- =============================== Regular structure  ====================================== -->
        <xsl:when test="@data_type='structure'">
<!--          <xsl:apply-templates select = "field" mode = "GET_SINGLE"/> -->
	this.<xsl:value-of select = "@name"/>.get(expIdx, path, strNodePath);
        </xsl:when>

 	<!-- ===================================== AoS type 1  ========================================= -->
         <xsl:when test="@data_type='struct_array' and @maxoccur!='unbounded'">
          <!-- Generate the full name of the subclass from the field path -->

         try {
	    int iTimeVectSize = UALLowLevel.getInt(expIdx, path, strNodePath + "<xsl:value-of select = "@name"/>/Shape_of");
            this.<xsl:value-of select="@name"/> = new <xsl:value-of select="$class_name"/>[iTimeVectSize];
            for (int i = 0; i &lt; this.<xsl:value-of select = "@name"/>.length; i++) 
	    {
              this.<xsl:value-of select = "@name"/>[i] = new <xsl:value-of select="$class_name"/>();
	      this.<xsl:value-of select = "@name"/>[i].get(expIdx, path, strNodePath, i);

            }
     } catch(Exception exc){this.<xsl:value-of select="@name"/> = null;}

      </xsl:when>
         <!-- ====================== AoS type 2  ============================= -->
	<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and (not(@type) or @type!='dynamic')">
	TBDXXX!!!
      </xsl:when>
 	<!-- ===================================== AoS type 3  ========================================= -->
<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and @type='dynamic'">
          try {
            int idObjAllTimes = UALLowLevel.getObject(expIdx, path, strNodePath + "<xsl:value-of select = "@name"/>", imas.TIMED); //read the whole timed block, returns -1 if Aos is empty
            if (idObjAllTimes > -1) 
	    {
            int iTimeVectSize = UALLowLevel.getObjectDim(expIdx, idObjAllTimes); // the size of this top object is the number of time slices
             this.<xsl:value-of select = "@name"/> = new <xsl:value-of select="$class_name"/>[iTimeVectSize];
//          if (ual_debug.equals("yes")) System.out.printlln("Get ids%<xsl:value-of select="translate(@path,'/','.')"/> + lentime =" + lentime");
            for (int i = 0; i &lt; iTimeVectSize; i++) {
               this.<xsl:value-of select = "@name"/>[i] = new <xsl:value-of select="$class_name"/>();
              try {
                int idObj = UALLowLevel.getObjectFromObject(expIdx, idObjAllTimes, "ALLTIMES", i);
    		 this.<xsl:value-of select = "@name"/>[i].getFromObject(expIdx, idObj, "", 0);
              } catch(Exception exc){ this.<xsl:value-of select = "@name"/>[i] = null;}
            }
            UALLowLevel.releaseObject(expIdx, idObjAllTimes);
            } // close of "if (idObjAllTimes > -1)"
          } catch(Exception exc){ this.<xsl:value-of select = "@name"/> = null;}
</xsl:when>

<!-- old comment from ITM MDS object tobe implemented for AoS type 2
! Read non-timed content
call get_object(idx,path,"<xsl:value-of select = "@path"/>",obj1,0,status); ! read the whole non-timed block
if (status.EQ.0) then
   call get_object_dim(idx,obj1,dimObj1)
   if (dimObj1.NE.0) then
      do itime = 1,lentime     ! fill every time slice
         ! does not exist yet (there was no timed content)
         if (.NOT.associated(cpos(itime)%<xsl:value-of select = "translate(@path,'/','%')"/>)) then
            allocate(cpos(itime)%<xsl:value-of select = "translate(@path,'/','%')"/>(dimObj1))
         endif
         ! must have same number of non-timed elements and timed elements
         if (size(cpos(itime)%<xsl:value-of select = "translate(@path,'/','%')"/>).NE.dimObj1) then
            write(*,*) "Error in get: array of structures has different number of timed and nontimed elements for <xsl:value-of select = "@path"/>"
         else
            do i1 = 1,dimObj1     ! process array elements
               <xsl:apply-templates select = "field" mode = "GET_FROM_OBJECT">
                  <xsl:with-param name="level" select="1"/>
                  <xsl:with-param name="objpath" select="@name"/>
                  <xsl:with-param name="idxpath" select="concat('cpos(itime)%',translate(@path,'/','%'),'(i1)')"/>
                  <xsl:with-param name="timed" select="'no'"/>
               </xsl:apply-templates>
            enddo
         endif
      enddo
   endif
   call release_object(idx,obj1)
endif
 -->



        <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">String</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Int</xsl:with-param>
	<xsl:with-param name="Exception">imas.EMPTY_INT</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='xs:boolean'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Boolean</xsl:with-param>
	<xsl:with-param name="Exception">imas.EMPTY_INT</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='xs:double'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Double</xsl:with-param>
	<xsl:with-param name="Exception">imas.EMPTY_DOUBLE</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
	<xsl:call-template name="getVariable"> <xsl:with-param name="variable_path" select="$variable_path"/> <xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Double</xsl:with-param>
	<xsl:with-param name="Exception">imas.EMPTY_DOUBLE</xsl:with-param>
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
<xsl:variable name="fulltypepath">
	<xsl:call-template name="BUILD_TYPENAME">
	<xsl:with-param name="typepath"><xsl:value-of select = "@path"/></xsl:with-param >
	</xsl:call-template>
</xsl:variable>
<xsl:choose>
        <!--========== Regular structures =======
	===-->
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
        <!-- Generate the full name of the subclass from the field path -->

         <xsl:when test="@data_type='struct_array' and @maxoccur!='unbounded'">
<!-- Type 1 arrays of structure, with potentially multiple time bases -->// Get_slice <xsl:value-of select="@path"/>
		<xsl:choose>
			<xsl:when test="$variable_path">
        //06/12/2015: avoid large try blocks  try {
            ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> = new <xsl:value-of select="$fulltypepath"/>[UALLowLevel.getInt(expIdx,path, <xsl:value-of select="$mds_path"/>+&quot;/<xsl:value-of select="@name"/>/Shape_of&quot;)];
            for (int i<xsl:value-of select = "@name"/> = 0; i<xsl:value-of select = "@name"/>&lt;ids.<xsl:value-of select = "concat($variable_path,'.',@name)"/>.length; i<xsl:value-of select = "@name"/>++){
              ids.<xsl:value-of select="concat($variable_path,'.',@name,'[i',@name,']')"/> = new <xsl:value-of select="$fulltypepath"/>();
	      <xsl:apply-templates select = "field" mode = "GET_SLICE">
		<xsl:with-param name ="variable_path" select="concat($variable_path,'.',@name,'[i',@name,']')"/>
		<xsl:with-param name="mds_path" select="concat($mds_path,'+&quot;/',@name,'/&quot; + Integer.toString(i',@name,'+1).trim()')"/>
		<xsl:with-param name ="ids_name" select="$ids_name"/>
	      </xsl:apply-templates>
            }
        // 06/12/2015: avoid large try blocks  } catch(Exception exc){ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> = null;}
			</xsl:when>
			<xsl:otherwise>
          // 06/12/2015: avoid large try blocks try {
            ids.<xsl:value-of select="@name"/> = new <xsl:value-of select="$fulltypepath"/>[UALLowLevel.getInt(expIdx,path, &quot;<xsl:value-of select="@name"/>/Shape_of&quot;)];
            for (int i<xsl:value-of select = "@name"/> = 0; i<xsl:value-of select = "@name"/>&lt;ids.<xsl:value-of select = "@name"/>.length; i<xsl:value-of select = "@name"/>++){
              ids.<xsl:value-of select="concat(@name,'[i',@name,']')"/> = new <xsl:value-of select="$fulltypepath"/>();
	      <xsl:apply-templates select = "field" mode = "GET_SLICE">
		<xsl:with-param name ="variable_path" select="concat(@name,'[i',@name,']')"/>
		<xsl:with-param name ="mds_path" select="concat('&quot;',@name,'/&quot; + Integer.toString(i',@name,'+1).trim()')"/>
		<xsl:with-param name ="ids_name" select="$ids_name"/>
	      </xsl:apply-templates>
            }
          //06/12/2015: avoid large try blocks } catch(Exception exc){ids.<xsl:value-of select="@name"/> = null;}
			</xsl:otherwise>
		</xsl:choose>
	</xsl:when>
	<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and @type='dynamic'">
<!-- Type 3 arrays of structure, with a unique time base -->
		<xsl:choose>
			<xsl:when test="$variable_path">
	// Structure array of type 3 nested below a Type 1 : <xsl:value-of select = "concat($variable_path,'.',@name)"/>
//          try {
            obj_single_time = UALLowLevel.getObjectSlice(expIdx, path, <xsl:value-of select = "concat($mds_path,' + &quot;/',@name,'&quot;')"/>, time); //read the whole timed block containing a single slice
//          try {
              obj1 = UALLowLevel.getObjectFromObject(expIdx, obj_single_time,"ALLTIMES", 0);
              //UALLowLevel.getObjectDim(expIdx, obj1, dimObj1);
//===
              ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> = new <xsl:value-of select="$fulltypepath"/>[1];
              ids.<xsl:value-of select="concat($variable_path,'.',@name,'[0]')"/> = new <xsl:value-of select="$fulltypepath"/>();
//===
              <xsl:apply-templates select = "field" mode = "GET_FROM_OBJECT">
                <xsl:with-param name="level" select="1"/>
                <xsl:with-param name="objpath" select="@name"/>
                <xsl:with-param name="idxpath" select="concat('ids.',$variable_path,'.',@name,'[0]')"/>
                <xsl:with-param name="timed" select="'yes'"/>
              </xsl:apply-templates>
//          } catch(Exception exc){<xsl:value-of select="concat('ids.',$variable_path,'.',@name,'[0]')"/> = null;}
            UALLowLevel.releaseObject(expIdx,obj_single_time);
//          } catch(Exception exc){<xsl:value-of select="concat('ids.',$variable_path,'.',@name)"/> = null;}
			</xsl:when>
			<xsl:otherwise>
	// Structure array of type 3 : <xsl:value-of select = "@path"/>
//        try {
            obj_single_time = UALLowLevel.getObjectSlice(expIdx, path, "<xsl:value-of select = "@path"/>", time); //read the whole timed block containing a single slice
//          try {
              obj1 = UALLowLevel.getObjectFromObject(expIdx, obj_single_time,"ALLTIMES", 0);
              //UALLowLevel.getObjectDim(expIdx, obj1, dimObj1);
//===
              <xsl:value-of select="concat('ids.',translate(@path,'/','.'))"/> = new <xsl:value-of select="$fulltypepath"/>[1];
              <xsl:value-of select="concat('ids.',translate(@path,'/','.'),'[0]')"/> = new <xsl:value-of select="$fulltypepath"/>();
//===
              <xsl:apply-templates select = "field" mode = "GET_FROM_OBJECT">
                <xsl:with-param name="level" select="1"/>
                <xsl:with-param name="objpath" select="@name"/>
                <xsl:with-param name="idxpath" select="concat('ids.',translate(@path,'/','.'),'[0]')"/>
                <xsl:with-param name="timed" select="'yes'"/>
              </xsl:apply-templates>
//          } catch(Exception exc){<xsl:value-of select="concat('ids.',translate(@path,'/','.'),'[0]')"/> = null;}
            UALLowLevel.releaseObject(expIdx,obj_single_time);
//        } catch(Exception exc){<xsl:value-of select="concat('ids.',translate(@path,'/','.'))"/> = null;}
			</xsl:otherwise>
		</xsl:choose>
	</xsl:when>

   	<xsl:when test="@type='dynamic'">
	<xsl:choose>
		<xsl:when test="$variable_path">
   	if ( <xsl:value-of select="ancestor::IDS[1]/@name"/>_IDSBase.isHomogeneous()) {
       <!--XSLtest whether this is a data/time structure, otherwise assume that the timepath attribute from IDSDef is correct-->
       		<xsl:choose>
       			<xsl:when test="(@name='data' and ../field[@name='time']) or (@name='time' and ../field[@name='data']) or @name='data_error_upper' or @name='data_error_lower'">
       	  timepath=<xsl:value-of select="$mds_path"/> + &quot;/time&quot; ;
       			</xsl:when>
       			<xsl:otherwise>
       	  timepath=&quot;<xsl:call-template name="printtimepath"/>&quot; ;
       			</xsl:otherwise>
       		</xsl:choose>
   	} else {
          timepath="time";
   	}
   		</xsl:when>
 		<xsl:otherwise>
		// line 1312 of IDS ===
		// @path = <xsl:value-of select="@path"/>
		// $variable_path = <xsl:value-of select="$variable_path"/>
		// @data_type = <xsl:value-of select="@data_type"/>
   	if ( <xsl:value-of select="ancestor::IDS[1]/@name"/>_IDSBase.isHomogeneous()) {
       	  timepath="<xsl:call-template name="printtimepath"/>";
   	} else {
          timepath="time";
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

<xsl:template match="field" mode = "GET_OBJECTX">
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

<xsl:template match = "field" mode = "GET_FULLX">
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
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = imas.EMPTY_INT;
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
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = imas.EMPTY_DOUBLE;
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
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = imas.EMPTY_DOUBLE;
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
//            try {
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
//            } catch(UALException exc) {
//              throw new UALException("Error in get: for <xsl:value-of select = "translate(@path,'/','.')"/> " + exc);
//            }

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
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = imas.EMPTY_INT;
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
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = imas.EMPTY_DOUBLE;
            }
       </xsl:when>
        <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
          try {
          doubleValue = UALLowLevel.getDouble(expIdx, path, "<xsl:value-of select="@path"/>");
          for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = doubleValue;
          } catch(Exception exc)
           {   for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = imas.EMPTY_DOUBLE;
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
//            try {
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
                    <xsl:with-param name="timed" select="'no'"/>
                  </xsl:apply-templates>
                }
              }
              UALLowLevel.releaseObject(expIdx,obj1);
//            } catch (Exception exc) {
//              for(int i = 0; i <xsl:text disable-output-escaping = "yes"> &lt; </xsl:text>  numSamples; i++)
//              idss[i].<xsl:value-of select = "translate(@path,'/','.')"/> = null;
//            }
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
  <xsl:param name="currentobjpath"><xsl:value-of select="concat($objpath,'/',@name)"/></xsl:param>

  <!-- build the complete path of the current field -->
  <xsl:param name="currentidxpath"><xsl:value-of select="concat($idxpath,'.',@name)"/></xsl:param>

  <!-- Generate the full name of the subclass from the field path -->
  <xsl:param name="class_name">
    <xsl:call-template name="BUILD_CLASS_NAME">
      <xsl:with-param name="typepath"><xsl:value-of select = "@path"/></xsl:with-param >
    </xsl:call-template>
  </xsl:param>
  <xsl:call-template name="COMMENT_FIELD_SHORT"/>
  <xsl:choose>
      <!--============================== Regular structure ====================-->
    <xsl:when test="@data_type='structure'">
	this.<xsl:value-of select="@name"/>.getFromObject( expIdx,  idObj,  strObjPath, objIdx);
    </xsl:when>
    
         <!-- =========================== AoS type 2  ================================== -->
	<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and (not(@type) or @type!='dynamic')">
	 try 
	 { 
	     int idAoS2Obj = UALLowLevel.getObjectFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select = "@name"/>", objIdx);
	     int iAoS2Size = UALLowLevel.getObjectDim(expIdx, idAoS2Obj);
	     if(iAoS2Size > 0)
	     {
	        this.<xsl:value-of select="@name"/> = new <xsl:value-of select = "$class_name"/>[iAoS2Size];
	        for (int i = 0; i &lt; iAoS2Size; i++) {
                   this.<xsl:value-of select="@name"/>[i] = new <xsl:value-of select = "$class_name"/>();
		   this.<xsl:value-of select="@name"/>[i].getFromObject(expIdx, idAoS2Obj,  "", i);
                }
	    }

	 }  catch(UALException exc) 
	 {
//                throw new UALException("Error in get: for <xsl:value-of select = "translate(@path,'/','.')"/> " + exc);
	 }
      </xsl:when>
        <!-- =========================== AoS type 3  ================================== -->
      	<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and @type='dynamic'">
          try {
            int idObjAllTimes = UALLowLevel.getObjectFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select = "@name"/>", objIdx); //read the whole timed block, returns -1 if Aos is empty
            if (idObjAllTimes > -1) 
	    {
            int iTimeVectSize = UALLowLevel.getObjectDim(expIdx, idObjAllTimes); // the size of this top object is the number of time slices
	     if (this.<xsl:value-of select="@name"/> != null) { // does this array already exist? (timed and non timed parts can share the same array)
                 if (iTimeVectSize != 0 &amp;&amp;  this.<xsl:value-of select="@name"/>.length != iTimeVectSize) { // then it must have the right number of elements
                    throw new UALException("Error in get: array of structures has different number of timed and nontimed elements");
                 }
	      }
	      else
	      {
	          this.<xsl:value-of select = "@name"/> = new <xsl:value-of select="$class_name"/>[iTimeVectSize];
	      }
//          if (ual_debug.equals("yes")) System.out.printlln("Get ids%<xsl:value-of select="translate(@path,'/','.')"/> + lentime =" + lentime");
            for (int i = 0; i &lt; iTimeVectSize; i++) {
	        try {
		  if(this.<xsl:value-of select = "@name"/>[i] == null){
		       this.<xsl:value-of select = "@name"/>[i] = new <xsl:value-of select="$class_name"/>();
        	    }
                int idObjAoS3 = UALLowLevel.getObjectFromObject(expIdx, idObjAllTimes, "ALLTIMES", i);
    		 this.<xsl:value-of select = "@name"/>[i].getFromObject(expIdx, idObjAoS3, "", 0);
              } catch(Exception exc){ this.<xsl:value-of select = "@name"/>[i] = null;}
            }
            } 
          } catch(Exception exc){ this.<xsl:value-of select = "@name"/> = null;}
	</xsl:when>
      
    <!--========== Arrays of structures ==========-->
    <xsl:when test="@data_type='struct_array'">
    <!-- -->
    <xsl:choose>
      <xsl:when test="$timed = 'yes'">
      <!-- We are scanning the children of a Type 3 AoS, so we extract the child object at index 0 of the parent object -->
          try { 
            obj1 = UALLowLevel.getObjectFromObject(expIdx, obj, "<xsl:value-of select = "$currentobjpath"/>", 0);
//            int obj1= UALLowLevel.getObjectFromObject(expIdx, obj, "<xsl:value-of select = "$currentobjpath"/>", 0);
      </xsl:when>
      <xsl:otherwise>
<!-- Otherwise we assume it is a Type 2 AoS, so we extract the child object at index iobject -->
          //try { 06/12/2015: avoid large try blocks
            obj1 = UALLowLevel.getObjectFromObject(expIdx, obj, "<xsl:value-of select = "$currentobjpath"/>", i);
      </xsl:otherwise>
    </xsl:choose>
            if (this.<xsl:value-of select="@name"/> != null) { // does this array already exist? (timed and non timed parts can share the same array)
              if (UALLowLevel.getObjectDim(expIdx,obj1) != 0 &amp;&amp;  this.<xsl:value-of select="@name"/>.length != UALLowLevel.getObjectDim(expIdx,obj1)) { // then it must have the right number of elements
                throw new UALException("Error in get: array of structures has different number of timed and nontimed elements");
              }
            } else { // else allocate it
              if (UALLowLevel.getObjectDim(expIdx,obj1) != 0) // empty arrays of struct must remain set to null
                this.<xsl:value-of select="@name"/> = new <xsl:value-of select = "$class_name"/>[UALLowLevel.getObjectDim(expIdx,obj1)];

              for (int i1 = 0; i1 &lt; UALLowLevel.getObjectDim(expIdx,obj1); i1++) {
                this.<xsl:value-of select="@name"/>[i1] = new <xsl:value-of select = "$class_name"/>();
              }
            }
            for (int i1 = 0; i1 &lt; UALLowLevel.getObjectDim(expIdx,obj1); i1++) {
		this.<xsl:value-of select="@name"/>[i1].getFromObject();
            }

// 06/12/2015: avoid large try blocks         } catch(UALException exc) {
//                throw new UALException("Error in get: for <xsl:value-of select = "translate(@path,'/','.')"/> " + exc);
// 06/12/2015: avoid large try blocks         }
  <!--    </xsl:if> -->
    </xsl:when>



    <xsl:otherwise>
      <!--========== select either timed or non-timed fields ==========-->
      <!--<xsl:if test="@timed=$timed">-->
        <xsl:choose>
          <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
<!--xsl//GABRIELE MARCH 2011            try { -->
              this.<xsl:value-of select="@name"/> = UALLowLevel.getStringFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx);
<!--xsl//            } catch(Exception exc){  this.<xsl:value-of select="@name"/> = null;} -->
          </xsl:when>
          <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
<!--xsl //GABRIELE MARCH 2011              try { -->
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getIntFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx);
<!--xsl //            } catch(Exception exc){  this.<xsl:value-of select="@name"/> = imas.EMPTY_INT;} -->
          </xsl:when>
          <xsl:when test="@name='xs:boolean'">
<!--xsl//GABRIELE MARCH 2011              try { -->
              <xsl:value-of select="$currentidxpath"/> = UALLowLevel.getBooleanFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx);
<!--xsl//            } catch(Exception exc){} -->
          </xsl:when>
          <xsl:when test="@name='xs:double'">
<!--xsl//GABRIELE MARCH 2011              try { -->
              this.<xsl:value-of select="@name"/> = UALLowLevel.getDoubleFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx);
<!--xsl//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = imas.EMPTY_DOUBLE;} -->
          </xsl:when>
          <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
<!--xsl //GABRIELE MARCH 2011              try { -->
              this.<xsl:value-of select="@name"/> = UALLowLevel.getDoubleFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx);
<!--xsl//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = imas.EMPTY_DOUBLE;} -->
          </xsl:when>

          <xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
<!--xsl //GABRIELE MARCH 2011              try { -->
              this.<xsl:value-of select="@name"/> = UALLowLevel.getVect1DDoubleFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx);
<!--xsl //            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;} -->
          </xsl:when>
          <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
<!--xsl //GABRIELE MARCH 2011              try { -->
              this.<xsl:value-of select="@name"/> = UALLowLevel.getVect1DStringFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx);
<!--xsl//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;} -->
          </xsl:when>

          <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
<!--xsl //GABRIELE MARCH 2011              try { -->
              this.<xsl:value-of select="@name"/> = UALLowLevel.getVect1DIntFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx);
<!--xsl //            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;} -->
          </xsl:when>

          <xsl:when test="@data_type='FLT_2D'">
<!--xsl//GABRIELE MARCH 2011              try { -->
              this.<xsl:value-of select="@name"/> = UALLowLevel.getVect2DDoubleFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx);
<!--xsl//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;} -->
        </xsl:when>
          <xsl:when test="@data_type='INT_2D'">
<!--xsl //GABRIELE MARCH 2011              try { -->
              this.<xsl:value-of select="@name"/> = UALLowLevel.getVect2DIntFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx);
<!--xsl//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;} -->
        </xsl:when>

          <xsl:when test="@data_type='FLT_3D'">
<!--xsl //GABRIELE MARCH 2011              try { -->
              this.<xsl:value-of select="@name"/> = UALLowLevel.getVect3DDoubleFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx);
<!--xsl//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;} -->
        </xsl:when>
        <xsl:when test="@data_type='INT_3D'">
<!--xsl //GABRIELE MARCH 2011              try { -->
              this.<xsl:value-of select="@name"/> = UALLowLevel.getVect3DIntFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx);
<!--xsl//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;} -->
        </xsl:when>

          <xsl:when test="@data_type='FLT_4D'">
<!--xsl //GABRIELE MARCH 2011              try {-->
              this.<xsl:value-of select="@name"/> = UALLowLevel.getVect4DDoubleFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx);
<!--xsl//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}-->
        </xsl:when>
          <xsl:when test="@data_type='FLT_5D'">
<!--xsl//GABRIELE MARCH 2011              try {-->
              this.<xsl:value-of select="@name"/> = UALLowLevel.getVect5DDoubleFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx);
<!--xsl//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}-->
        </xsl:when>

          <xsl:when test="@data_type='FLT_6D'">
<!--xsl//GABRIELE MARCH 2011              try {-->
              this.<xsl:value-of select="@name"/> = UALLowLevel.getVect6DDoubleFromObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx);
<!--xsl//            } catch(Exception exc){  <xsl:value-of select="$currentidxpath"/> = null;}-->
        </xsl:when>

        </xsl:choose>
      <!--</xsl:if>-->
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>

<!--=================================================-->
<!--      Put field in a full time-dependent IDS     -->
<!--=================================================-->

<xsl:template match = "field" mode = "PUT_SINGLE">
<xsl:param name="non_timed"/>

  <xsl:call-template name="COMMENT_FIELD_SHORT"/>
   <!-- This skips the routine for timed fields when using this template in PUT_NON_TIMED mode -->
<xsl:if test="$non_timed !='yes' or @type !='dynamic' or not(@type) or @data_type='structure' or (@data_type='struct_array' and @type !='dynamic')">


<xsl:variable name='NonTimed'>
    <xsl:choose>
        <xsl:when  test="$non_timed ='yes'">
      	    <xsl:text>NonTimed</xsl:text>
	</xsl:when>
	<xsl:otherwise>
	    <xsl:text></xsl:text>
        </xsl:otherwise>
    </xsl:choose>
</xsl:variable>

      <xsl:choose>
        <!--==================== Regular structures ==============================-->
        <xsl:when test="@data_type='structure'">
	 this.<xsl:value-of select="@name"/>.put<xsl:value-of select = "$NonTimed"/>(expIdx, path, strNodePath);
       </xsl:when>
       
       	<!-- ====================== AoS type 1  ============================= -->
	<xsl:when test="@data_type='struct_array' and @maxoccur!='unbounded'">
          if (this.<xsl:value-of select="@name"/> != null) 
	  {
            UALLowLevel.putInt(expIdx,path, strNodePath + "<xsl:value-of select = "@name"/>/Shape_of", this.<xsl:value-of select="@name"/>.length);
            for (int i = 0; i &lt;this.<xsl:value-of select = "@name"/>.length;  i++){
 		this.<xsl:value-of select="@name"/>[i].put<xsl:value-of select = "$NonTimed"/>(expIdx, path, strNodePath, i);
            }
          }
	</xsl:when>
	
	<!-- ====================== AoS type 2  ============================= -->
	<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and (not(@type) or @type!='dynamic')">
	  if (this.<xsl:value-of select = "@name"/> != null) {
          int idxObj =UALLowLevel.beginObject(expIdx, -1, 0, path + strNodePath + "<xsl:value-of select = "@name"/>", imas.NON_TIMED);
          if (this.<xsl:value-of select = "@name"/> != null) {
            for (int i = 0; i &lt; this.<xsl:value-of select = "@name"/>.length; i++) {
        		this.<xsl:value-of select = "@name"/>[i].putInObject(expIdx, idxObj, "", i);
            }
          }
          UALLowLevel.putObject(expIdx, path, strNodePath + "<xsl:value-of select = "@name"/>", idxObj, 0);
	  }
	</xsl:when>
	
	<!-- ====================== AoS type 3  ============================= -->
 	<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and @type='dynamic'">

          if (this.<xsl:value-of select = "@name"/> != null) {
            int idxObjAllTimes = UALLowLevel.beginObject(expIdx,-1,0,path + strNodePath + "<xsl:value-of select = "@name"/>", imas.TIMED_CLEAR);
            for (int i = 0; i &lt; this.<xsl:value-of select = "@name"/>.length; i++) {
              int idxObj = UALLowLevel.beginObject(expIdx, idxObjAllTimes, i, "ALLTIMES", imas.TIMED);
            	this.<xsl:value-of select = "@name"/>[i].putInObject(expIdx, idxObj, "", 0);
              UALLowLevel.putObjectInObject(expIdx, idxObjAllTimes, "ALLTIMES", i, idxObj);
            }
            // Store time of the array of structure (hidden variable for the user, but used by the UAL for future get_slice operations)
          	    
	    if ( <xsl:value-of select="ancestor::IDS[1]/@name"/>_IDSBase.isHomogeneous()) 
	    {
                time = <xsl:value-of select="ancestor::IDS[1]/@name"/>_IDSBase.getIdsTime(); // Use the general time vector of the IDS to fill time
             }
            else 
	    {   time = new Vect1DDouble(this.<xsl:value-of select = "@name"/>.length);
	        for (int i = 0; i &lt; this.<xsl:value-of select = "@name"/>.length; i++)
		{// the AoS time vector is there, fill time with it
           		if (this.<xsl:value-of select = "@name"/>[i].time == imas.EMPTY_DOUBLE) 
			{ // Sanity check
                		System.out.println("ERROR : the time vector of the type 3 array of structure <xsl:value-of select = "translate(@path,'/','.')"/> must be filled");
                		return;
              		}
			 time.setElementAt(i, this.<xsl:value-of select = "@name"/>[i].time);
		 }
	      
             }
            
            timepath = strNodePath + "<xsl:value-of select = "@name"/>/time";
            UALLowLevel.beginIDSPutTimed(expIdx, path, time);
            UALLowLevel.putVect1DDouble(expIdx, path, timepath.trim(), timepath.trim(), time, true);
            UALLowLevel.endIDSPutTimed(expIdx, path);
            //if (ual_debug.equals("yes")) System.out.println("Put <xsl:call-template name="printtimepath"/> ");
            UALLowLevel.putObject(expIdx, path, strNodePath + "<xsl:value-of select = "@name"/>", idxObjAllTimes ,1);
          }

	</xsl:when>

	<!-- ====================== Primitive types ============================= -->


        <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
<!--      UALLowLevel.putString(expIdx, path,  strNodePath + "<xsl:value-of select = "@name"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>); -->
	<xsl:call-template name="putScalar"> 
	<xsl:with-param name="Function">String</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
<!--      UALLowLevel.putInt(expIdx, path,  strNodePath + "<xsl:value-of select = "@name"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>); -->
	<xsl:call-template name="putScalar">
	<xsl:with-param name="Function">Int</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='xs:boolean'">
<!--      UALLowLevel.putBoolean(expIdx, path,  strNodePath + "<xsl:value-of select = "@name"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>); -->
	<xsl:call-template name="putScalar"> 
	<xsl:with-param name="Function">Boolean</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='xs:double'">
<!--      UALLowLevel.putDouble(expIdx, path,  strNodePath + "<xsl:value-of select = "@name"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>); -->
	<xsl:call-template name="putScalar"> 
	<xsl:with-param name="Function">Double</xsl:with-param>
	</xsl:call-template>
	</xsl:when>
        <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
<!--       UALLowLevel.putDouble(expIdx, path,  strNodePath + "<xsl:value-of select = "@name"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>); -->
	<xsl:call-template name="putScalar"> 
	<xsl:with-param name="Function">Double</xsl:with-param>
	</xsl:call-template>
	</xsl:when>

	<xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
<!--      UALLowLevel.putVect1DDouble(expIdx, path,  strNodePath + "<xsl:value-of select = "@name"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic">
	<xsl:with-param name="Function">Vect1DDouble</xsl:with-param>
	</xsl:call-template>
	</xsl:when>

        <xsl:when test="@name='vecdbl_type'">
<!--      UALLowLevel.putVect1DDouble(expIdx, path,  strNodePath + "<xsl:value-of select = "@name"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic"> 
	<xsl:with-param name="Function">Vect1DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
 <!--     UALLowLevel.putVect1DInt(expIdx, path,  strNodePath + "<xsl:value-of select = "@name"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic">
	<xsl:with-param name="Function">Vect1DInt</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
<!--      UALLowLevel.putVect1DString(expIdx, path,  strNodePath + "<xsl:value-of select = "@name"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
 	<xsl:call-template name="putdynamic"> 
	<xsl:with-param name="Function">Vect1DString</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_2D'">
<!--      UALLowLevel.putVect2DDouble(expIdx, path,  strNodePath + "<xsl:value-of select = "@name"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic">
	<xsl:with-param name="Function">Vect2DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='INT_2D'">
<!--      UALLowLevel.putVect2DInt(expIdx, path,  strNodePath + "<xsl:value-of select = "@name"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic">
	<xsl:with-param name="Function">Vect2DInt</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='matdbl_type'">
<!--      UALLowLevel.putVect2DDouble(expIdx, path,  strNodePath + "<xsl:value-of select = "@name"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic">
	<xsl:with-param name="Function">Vect2DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_3D'">
<!--      UALLowLevel.putVect3DDouble(expIdx, path,  strNodePath + "<xsl:value-of select = "@name"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic"> 
	<xsl:with-param name="Function">Vect3DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='INT_3D'">
<!--      UALLowLevel.putVect3DInt(expIdx, path,  strNodePath + "<xsl:value-of select = "@name"/>"", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic"> 
	<xsl:with-param name="Function">Vect3DInt</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='array3ddbl_type'">
<!--      UALLowLevel.putVect3DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic"> 
	<xsl:with-param name="Function">Vect3DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_4D'">
<!--      UALLowLevel.putVect4DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic"> 
	<xsl:with-param name="Function">Vect4DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='array4dint_type'">
<!--      UALLowLevel.putVect4DInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic">
	<xsl:with-param name="Function">Vect4DInt</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='array4ddbl_type'">
<!--      UALLowLevel.putVect4DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic"> 
	<xsl:with-param name="Function">Vect4DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_5D'">
 <!--     UALLowLevel.putVect5DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic">
	<xsl:with-param name="Function">Vect5DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='array5dint_type'">
<!--      UALLowLevel.putVect5DInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic"> 
	<xsl:with-param name="Function">Vect5DInt</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='array5ddbl_type'">
<!--      UALLowLevel.putVect5DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic">
	<xsl:with-param name="Function">Vect5DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_6D'">
<!--      UALLowLevel.putVect6DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic"> 
	<xsl:with-param name="Function">Vect6DDouble</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='array6dint_type'">
<!--      UALLowLevel.putVect6DInt(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic">
	<xsl:with-param name="Function">Vect6DInt</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='array6ddbl_type'">
<!--      UALLowLevel.putVect6DDouble(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, false); -->
	<xsl:call-template name="putdynamic"> 
	<xsl:with-param name="Function">Vect6DDouble</xsl:with-param>
	</xsl:call-template>
       </xsl:when>

	<xsl:otherwise>
 Put <xsl:value-of select="@path"/> : PROBLEMX : UNIDENTIFIED TYPE !!! <!-- for comment only -->
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
  <xsl:call-template name="COMMENT_FIELD_SHORT"/>
<xsl:if test="@type ='dynamic' or @data_type='structure' or @data_type='struct_array'"> <!-- This skips the routine for non timed fields -->
      <xsl:choose>
        <!--========== Regular structures ==========-->
        <xsl:when test="@data_type='structure'">
	this.<xsl:value-of select = "@name"/>.putSlice();
        </xsl:when>

        <!-- ========================================================== AoS type 1  =================================================================================== -->
        <xsl:when test="@data_type='struct_array' and @maxoccur!='unbounded'">
	
          if (this.<xsl:value-of select = "@name"/> != null) {
            UALLowLevel.putInt(expIdx,path, <xsl:value-of select="$mds_path"/>+&quot;/<xsl:value-of select="@name"/>/Shape_of&quot;,
       		this.<xsl:value-of select="@name"/>.length);
             for (int i = 0; i&lt; this.<xsl:value-of select = "@name"/>.length; i++){
      		this.<xsl:value-of select = "@name"/>[i].putSlice();
             }
          }
		
        </xsl:when>
	<!-- ========================================================== AoS type 2  =================================================================================== -->
		<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and (not(@type) or @type!='dynamic')">
		TBD
        </xsl:when>
	<!-- ========================================================== AoS type 3  =================================================================================== -->
	<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and @type='dynamic'">
<!-- Type 3 arrays of structure, with a unique time base -->

	  // Structure array of type 3 nested below a Type 1 : <xsl:value-of select = "concat($variable_path,'.',@name)"/>
          if (this.<xsl:value-of select = "@name"/> != null) {
            int obj_single_time = UALLowLevel.beginObject(expIdx,-1,0,path + <xsl:value-of select = "concat('&quot;','/',substring($mds_path,2),' + &quot;/',@name,'&quot;')"/>, imas.TIMED);
            int obj1 = UALLowLevel.beginObject(expIdx,obj_single_time,0,"ALLTIMES", imas.TIMED);
        	this.<xsl:value-of select = "@name"/>.putInObject();
            UALLowLevel.putObjectInObject(expIdx,obj_single_time,"ALLTIMES", 0, obj1);
            UALLowLevel.putObjectSlice(expIdx, path, <xsl:value-of select = "concat($mds_path, ' + &quot;/', @name)"/>" , ids.time.getElementAt(0), obj_single_time);

            // Store time of the array of structure (hidden variable for the user, but used by the UAL for future get_slice operations)
            // A temporary "time" vector is filled then put as a regular variable (outside of the object) as AoS%time
            Vect1DDouble time = new Vect1DDouble(1);
            if (ids.<xsl:value-of select = "concat($variable_path,'.',@name)"/>[0].time == imas.EMPTY_DOUBLE) { // Check the presence of a time vector at the root of the AoS (on the first index only)
              if ( <xsl:value-of select="ancestor::IDS[1]/@name"/>_IDSBase.isHomogeneous()) {
                time = <xsl:value-of select="ancestor::IDS[1]/@name"/>_IDSBase.getIdsTime(); // Use the general time vector of the IDS to fill time
<!--then  ! For an homogeneous IDS, force the time of the AoS to be equal to the general one
                for ( i1 = 1; i1< ids.<xsl:value-of select = "concat($variable_path,'.',@name)"/>.length; i1++){
                  ids.<xsl:value-of select = "concat($variable_path,'.',@name)"/>[i1].time = ids.time[i1]
                  time(
                } -->
              }
              else {
                System.out.println("ERROR : the time vector of the type 3 array of structure <xsl:value-of select = "translate(@path,'/','.')"/> must be filled");
                return;
              }
            }
            else {
              for (int i1 = 0; i1 &lt; this.<xsl:value-of select = "@name"/>.length; i1++){ // the AoS time vector is there, fill time with it
                time.setElementAt(i1,this.<xsl:value-of select = "@name"/>[i1].time);
              }
            }
            timepath=<xsl:value-of select="concat($mds_path,' + &quot;/',@name,'/time')"/>"; // Start to put time
            UALLowLevel.putDoubleSlice(expIdx, path, timepath.trim(), timepath.trim(), time.getElementAt(0), time.getElementAt(0));
            //if (ual_debug.equals("yes")) System.out.println("Put " + <xsl:value-of select="concat($mds_path,' + &quot;/',@name,'/time')"/>");
          }
	</xsl:when>

        <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
<!--      UALLowLevel.putStringSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>, ids.time); -->
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">StringSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/>      		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">IntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='xs:boolean'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">BooleanSlice</xsl:with-param>
	</xsl:call-template>Slice
        </xsl:when>

        <xsl:when test="@name='xs:double'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/>
	<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">DoubleSlice</xsl:with-param>
	</xsl:call-template>
	</xsl:when>

        <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">DoubleSlice</xsl:with-param>
	</xsl:call-template>
	</xsl:when>

	<xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect1DDoubleSlice</xsl:with-param>
	</xsl:call-template>
	</xsl:when>

        <xsl:when test="@name='vecdbl_type'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect1DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect1DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
 	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect1DStringSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_2D'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect2DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='INT_2D'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect2DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='matdbl_type'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect2DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_3D'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect3DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='INT_3D'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect3DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array3ddbl_type'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect3DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_4D'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect4DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array4dint_type'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect4DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array4ddbl_type'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect4DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_5D'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect5DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array5dint_type'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect5DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array5ddbl_type'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect5DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_6D'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect6DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array6dint_type'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
	<xsl:with-param name="Function">Vect6DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array6ddbl_type'">
	<xsl:call-template name="putdynamicSlice"> <xsl:with-param name="variable_path" select="$variable_path"/> 		<xsl:with-param name="mds_path" select="$mds_path"/>
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
              obj1 = UALLowLevel.beginObject(expIdx,-1,0,path+"/<xsl:value-of select = "@path"/>", imas.TIMED);
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
              if(ids.<xsl:value-of select = "translate(@path,'/','.')"/> != imas.EMPTY_INT)
                  UALLowLevel.replaceLastIntSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@name='xs:boolean'">
              if(ids.<xsl:value-of select = "translate(@path,'/','.')"/> != imas.EMPTY_INT)
                  UALLowLevel.replaceLastBooleanSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@name='xs:double'">
              if(ids.<xsl:value-of select = "translate(@path,'/','.')"/> != imas.EMPTY_DOUBLE)
                  UALLowLevel.replaceLastDoubleSlice(expIdx, path, "<xsl:value-of select = "@path"/>", ids.<xsl:value-of select = "translate(@path,'/','.')"/>);
          </xsl:when>
          <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
              if(ids.<xsl:value-of select = "translate(@path,'/','.')"/> != imas.EMPTY_DOUBLE)
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

<xsl:template match = "field" mode = "PUTX">
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
        obj1 = UALLowLevel.beginObject(expIdx,-1,0,path+"/<xsl:value-of select = "@path"/>", imas.NON_TIMED);
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
        UALLowLevel.putObject(expIdx, path, "<xsl:value-of select = "@path"/>", obj1, 1);
      }
    </xsl:when>

    <!--========== Regular structures ==========-->
    <xsl:when test="@data_type='structure'">
      <xsl:apply-templates select = "field" mode = "PUTX"/>
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
  <xsl:param name="child_index"/>     <!-- Index to use to add a child in the current object -->

  <!-- build the path of the current field inside the object -->
  <xsl:param name="currentobjpath" select="concat($objpath,'/',@name)"/>
  <!-- build the complete path of the current field -->
  <xsl:param name="currentidxpath" select="concat($idxpath,'.',@name)"/>

  <xsl:call-template name="COMMENT_FIELD_SHORT"/>
  <xsl:choose>
  
      <!--====================== Regular structure ==================================-->
    <xsl:when test="@data_type='structure'">
  		this.<xsl:value-of select = "@name"/>.putInObject(expIdx, idObj, "<xsl:value-of select = "../@name"/>", objIdx);
    </xsl:when>
    
       <!-- ====================== AoS type 1  ============================= -->
           <xsl:when test="@data_type='struct_array'  and @maxoccur!='unbounded'">
      <xsl:message terminate="yes"> Error! AoS1 nested in AoS2/3: <xsl:value-of select="@path"/></xsl:message>
       </xsl:when>
       	<!-- ====================== AoS type 2  ============================= -->
	<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and (not(@type) or @type!='dynamic')">

          if (this.<xsl:value-of select = "@name"/>  != null) {
            int idAoS2Obj = UALLowLevel.beginObject(expIdx, idObj, 0, strObjPath + "<xsl:value-of select="@name"/>", imas.NON_TIMED);
            for (int i = 0; i  &lt; this.<xsl:value-of select="@name"/>.length; i++) {
              this.<xsl:value-of select = "@name"/>[i].putInObject(expIdx, idAoS2Obj, "", i);
            }
            UALLowLevel.putObjectInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, idAoS2Obj);
          }
    </xsl:when>

       	<!-- ====================== AoS type 3  ============================= -->
	    	<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and  @type='dynamic'">
		 
		   <xsl:message terminate="no"><xsl:call-template name="COMMENT_FIELD_SHORT"/>     
		    <xsl:for-each select="./ancestor::field[@data_type='struct_array' and @maxoccur='unbounded' and  @type='dynamic']">
		             <xsl:text>&#xA; Ancestor of type AoS3:   </xsl:text>  <xsl:value-of select="@path"/>        
		</xsl:for-each>
		</xsl:message>
	     </xsl:when>

    <!--========== select either timed or non-timed fields ==========-->
    <xsl:otherwise>
 <!--     <xsl:if test="(@type='dynamic' and $timed='yes') or (@type!='dynamic' and $timed='no')"> -->
        <xsl:choose>
          <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
              UALLowLevel.putStringInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>",objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
              UALLowLevel.putIntInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@name='xs:boolean'">
              UALLowLevel.putBooleanInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@name='xs:double'">
              UALLowLevel.putDoubleInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
              UALLowLevel.putDoubleInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>

          <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
              UALLowLevel.putVect1DStringInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
              UALLowLevel.putVect1DDoubleInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@name='vecdbl_type'">
              UALLowLevel.putVect1DDoubleInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
              UALLowLevel.putVect1DIntInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>

          <xsl:when test="@data_type='FLT_2D'">
              UALLowLevel.putVect2DDoubleInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@name='matdbl_type'">
              UALLowLevel.putVect2DDoubleInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@data_type='INT_2D'">
              UALLowLevel.putVect2DIntInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>

          <xsl:when test="@data_type='FLT_3D'">
              UALLowLevel.putVect3DDoubleInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@name='array3ddbl_type'">
              UALLowLevel.putVect3DDoubleInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@data_type='INT_3D'">
              UALLowLevel.putVect3DIntInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>

          <xsl:when test="@data_type='FLT_4D'">
              UALLowLevel.putVect4DDoubleInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@name='array4ddbl_type'">
              UALLowLevel.putVect4DDoubleInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@name='array4dint_type'">
              UALLowLevel.putVect4DIntInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>

          <xsl:when test="@data_type='FLT_5D'">
              UALLowLevel.putVect5DDoubleInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@name='array5ddbl_type'">
              UALLowLevel.putVect5DDoubleInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@name='array5dint_type'">
              UALLowLevel.putVect5DIntInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>

        <xsl:when test="@data_type='FLT_6D'">
              UALLowLevel.putVect6DDoubleInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@name='array6ddbl_type'">
              UALLowLevel.putVect6DDoubleInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>
          <xsl:when test="@name='array6dint_type'">
              UALLowLevel.putVect6DIntInObject(expIdx, idObj, strObjPath + "<xsl:value-of select="@name"/>", objIdx, this.<xsl:value-of select="@name"/>);
          </xsl:when>

          <xsl:when test="@data_type='structure'">
            <xsl:apply-templates select = "field" mode = "PUT"/>
          </xsl:when>
        </xsl:choose>
   <!-- </xsl:if> -->
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>


<xsl:template name ="printtimepath">
<xsl:if test="@type = 'dynamic'">
<xsl:choose>
<xsl:when test="contains(@coordinate7,'time')"> <xsl:value-of select="translate(@coordinate7,'(:)','')"/></xsl:when> <!-- We removed the (:) character since the coordinate attribute in IDSDef is documentation-oriented -->
<xsl:when test="contains(@coordinate6,'time')"> <xsl:value-of select="translate(@coordinate6,'(:)','')"/></xsl:when>
<xsl:when test="contains(@coordinate5,'time')"> <xsl:value-of select="translate(@coordinate5,'(:)','')"/></xsl:when>
<xsl:when test="contains(@coordinate4,'time')"> <xsl:value-of select="translate(@coordinate4,'(:)','')"/></xsl:when>
<xsl:when test="contains(@coordinate3,'time')"> <xsl:value-of select="translate(@coordinate3,'(:)','')"/></xsl:when>
<xsl:when test="contains(@coordinate2,'time')"> <xsl:value-of select="translate(@coordinate2,'(:)','')"/></xsl:when>
<xsl:when test="contains(@coordinate1,'time')"> <xsl:value-of select="translate(@coordinate1,'(:)','')"/></xsl:when>
</xsl:choose>
</xsl:if>
<xsl:if test="@name='time'"><xsl:value-of select="@path"/></xsl:if>  <!-- If the field itself IS time, then it is its own time coordinate -->
</xsl:template>





<xsl:template name ="printtimevariable">
<xsl:if test="@type = 'dynamic'">
<xsl:choose>
<xsl:when test="contains(@coordinate7,'time')"> this.<xsl:value-of select="translate(@coordinate7,'/','.')"/></xsl:when>
<xsl:when test="contains(@coordinate6,'time')"> this.<xsl:value-of select="translate(@coordinate6,'/','.')"/></xsl:when>
<xsl:when test="contains(@coordinate5,'time')"> this.<xsl:value-of select="translate(@coordinate5,'/','.')"/></xsl:when>
<xsl:when test="contains(@coordinate4,'time')"> this.<xsl:value-of select="translate(@coordinate4,'/','.')"/></xsl:when>
<xsl:when test="contains(@coordinate3,'time')"> this.<xsl:value-of select="translate(@coordinate3,'/','.')"/></xsl:when>
<xsl:when test="contains(@coordinate2,'time')"> this.<xsl:value-of select="translate(@coordinate2,'/','.')"/></xsl:when>
<xsl:when test="contains(@coordinate1,'time')"> this.<xsl:value-of select="translate(@coordinate1,'/','.')"/></xsl:when>
</xsl:choose>
</xsl:if>
<xsl:if test="@name='time'">this.time</xsl:if>  <!-- If the field itself IS time, then it is its own time coordinate -->
</xsl:template>

<xsl:template name ="printIsTimed">
<xsl:choose><xsl:when test="@type = 'dynamic'"> <xsl:value-of select="'true'"/> </xsl:when> <xsl:otherwise> <xsl:value-of select="'false'"/> </xsl:otherwise> </xsl:choose>
</xsl:template>

<xsl:template name ="printPathoftime">
<xsl:choose><xsl:when test="@type = 'dynamic'"> <xsl:value-of select="'timepath.trim()'"/> </xsl:when> <xsl:otherwise>""</xsl:otherwise> </xsl:choose>
</xsl:template>

<xsl:template name ="putdynamic">
<xsl:param name="Function" />
<!--  ================================================ -->

	<xsl:if test="@type='dynamic'">
		<xsl:variable name = "NodeTimePath">
				<xsl:call-template name="printtimepath"/>
		</xsl:variable>		
		<xsl:choose>
			<xsl:when test="$NodeTimePath='time'">
	//time coordinate point to IDSRoot/time so no difference between homo- and heterogeneous mode
	timepath = "time" ;
	time = <xsl:value-of select="ancestor::IDS[1]/@name"/>_IDSBase.getIdsTime();
	      		</xsl:when>
	      		<xsl:otherwise>  
   	if ( <xsl:value-of select="ancestor::IDS[1]/@name"/>_IDSBase.isHomogeneous())
	{
	  timepath = "time" ;
	  time = <xsl:value-of select="ancestor::IDS[1]/@name"/>_IDSBase.getIdsTime();
	}
	else 
	{
	  timepath = strNodePath + "time" ;
	  time = this.time;
	}
	 		</xsl:otherwise>
   		</xsl:choose>
	  UALLowLevel.beginIDSPutTimed(expIdx, path, time);
   	</xsl:if>
	
	
          UALLowLevel.put<xsl:value-of select="$Function"/>(expIdx,path, strNodePath + "<xsl:value-of select="@name"/>", <xsl:call-template name="printPathoftime"/>, this.<xsl:value-of select="@name"/>, <xsl:call-template name="printIsTimed"/>);
   		<xsl:if test="@type='dynamic'">
          UALLowLevel.endIDSPutTimed(expIdx, path);
   		</xsl:if>
<!--
          if (ual_debug.equals("yes")) {
            System.out.println("Put ids.<xsl:value-of select="translate(@path,'/','.')"/> " + ids.<xsl:value-of select="translate(@path,'/','.')"/>.toString());
          }
-->
</xsl:template>




<xsl:template name ="putScalar">
<xsl:param name="Function" />
          UALLowLevel.put<xsl:value-of select="$Function"/>(expIdx, path, strNodePath + "<xsl:value-of select="@name"/>", this.<xsl:value-of select="@name"/>);
<!--   	if (ual_debug.equals("yes")) {
	  System.out.println("Put ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> " + ids.<xsl:value-of select="concat($variable_path,'.',@name)"/>.toString());
	} -->

</xsl:template>

<xsl:template name ="putdynamicSlice">
<xsl:param name="Function" />
          UALLowLevel.put<xsl:value-of select="$Function"/>(expIdx,path, "<xsl:value-of select="@path"/>", <xsl:call-template name="printPathoftime"/>, this.<xsl:value-of select="@name"/>, ids.time.getElementAt(0));
</xsl:template>


<xsl:template name ="putScalarSlice">
<xsl:param name="Function" />
          UALLowLevel.put<xsl:value-of select="$Function"/>(expIdx, path, ??? + &quot;/<xsl:value-of select="@name"/>&quot;, this.<xsl:value-of select="@name"/>,ids.time.getElementAt(0));
<!--   	if (ual_debug.equals("yes")) {
	  System.out.println("Put ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> " + ids.<xsl:value-of select="concat($variable_path,'.',@name)"/>.toString());
	} -->
</xsl:template>

<xsl:template name ="getVariable">
<xsl:param name="variable_path" />
<xsl:param name="mds_path" />
<xsl:param name="Function" />
<xsl:param name="Exception" />
          try {
            this.<xsl:value-of select="@name"/> = UALLowLevel.get<xsl:value-of select="$Function"/>(expIdx, path, strNodePath + &quot;/<xsl:value-of select="@name"/>&quot;);
          } catch(Exception exc){this.<xsl:value-of select="@name"/> = <xsl:value-of select="$Exception"/>;}
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
              ids.<xsl:value-of select="concat($variable_path,'.',@name)"/>.setElementAt(0, UALLowLevel.get<xsl:value-of select="$SliceFunction"/>Slice(expIdx, path, <xsl:value-of select="$mds_path"/> + &quot;/<xsl:value-of select="@name"/>&quot;, <xsl:call-template name="printPathoftime"/>, time, interpolMode));
            }
          } catch(Exception exc){ids.<xsl:value-of select="concat($variable_path,'.',@name)"/> = <xsl:value-of select="$Exception"/>;}
		</xsl:when>
		<xsl:otherwise>
          try {
	    Vect1DInt obj = UALLowLevel.getDimension(expIdx, path, &quot;<xsl:value-of select="@name"/>&quot;);
	    if (obj.getElementAt(0) > 0) {
              ids.<xsl:value-of select="@name"/> = new <xsl:value-of select="$Function"/>(<xsl:call-template name="printDimensions"><xsl:with-param name="number" select="$number"/></xsl:call-template>);
              ids.<xsl:value-of select="@name"/>.setElementAt(0, UALLowLevel.get<xsl:value-of select="$SliceFunction"/>Slice(expIdx, path, &quot;<xsl:value-of select="@name"/>&quot;, <xsl:call-template name="printPathoftime"/>, time, interpolMode));
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
            ids.<xsl:value-of select="concat($variable_path,'.',@name)"/>.setElementAt(0, UALLowLevel.getStringSlice(expIdx, path, <xsl:value-of select="$mds_path"/> + &quot;/<xsl:value-of select="@name"/>&quot;, <xsl:call-template name="printPathoftime"/>, time, interpolMode));
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
