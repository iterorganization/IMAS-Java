<?xml version="1.0" encoding="UTF-8"?>
<?modxslt-stylesheet type="text/xsl" media="fuffa, screen and $GET[stylesheet]" href="./%24GET%5Bstylesheet%5D" alternate="no" title="Translation using provided stylesheet" charset="ISO-8859-1" ?>
<?modxslt-stylesheet make gentype="text/xsl" media="screen" alternate="no" title="Show raw source of the XML file" charset="ISO-8859-1" ?>


<xsl:stylesheet xmlns:yaslt="http://www.mod-xslt2.com/ns/1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xs="http://www.w3.org/2001/XMLSchema" version="2.0"  xmlns:exsl="http://exslt.org/common" extension-element-prefixes="yaslt exsl"
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
import java.lang.reflect.*;

import imasjava.utilities.ImasReflection;
import imasjava.ids.*;
import imasjava.wrapper.LowLevel;

public class imas {
 static {
  String libpath = System.getenv("IMAS_HOME");
  String imasversion = System.getenv("IMAS_VERSION");
  String ualversion = System.getenv("UAL_VERSION");
  if (libpath == null) {
   System.err.println("IMAS library not set up in the environment. (IMAS_HOME missing)");
   System.exit(0);
  }
  if (imasversion == null) {
   System.err.println("IMAS library not set up in the environment. (IMAS_VERSION missing)");
   System.exit(0);
  }
  if (ualversion == null) {
   System.err.println("IMAS library not set up in the environment. (UAL_VERSION missing)");
   System.exit(0);
  }
  libpath = libpath + "/core/imas/" + imasversion + "/ual/" + ualversion + "/lib";
  String imas_library = libpath + "/libimas.so";
  File f = new File(imas_library);
  if (!f.exists()) {
   System.err.println("IMAS library not set up in the environment. (libimas.so missing)");
  }

  try {
   System.load(imas_library);
  } catch (UnsatisfiedLinkError exc) {
   System.err.println("(imas.java) Caught UnsatisfiedLinkError: " + exc);
  } catch (Throwable exc) {
   System.err.println("Cannot link to JNI library: " + exc);
   System.exit(0);
  }
 }


 public static final int INTERPOLATION = 3, CLOSEST_SAMPLE = 1, PREVIOUS_SAMPLE = 2;

 //public static final int NON_TIMED = 0, TIMED = 1, TIMED_CLEAR = 2;

 public static int pulseCtx = -1;
 
    public static String user;
    public static String tokamak;
    public static String version;
    public static int shot; 
    public static int run;


 public static boolean isIDSClassTimeDependent(String idsName) throws java.lang.ClassNotFoundException {
  Class ids = ImasReflection.getIdsClass(idsName);
  return imas.isIDSClassTimeDependent(ids);
 }


 public static boolean isIDSClassTimeDependent(Class idsClass) {
  Field[] fields = idsClass.getFields();
  boolean timedIds = false;

  for (Field field: fields) {
   if (field.getName().equals("time")) {
    timedIds = true;
    break;
   }
  }

  return timedIds;
 }



 public static java.util.ArrayList getAvailableIDSs() throws
 java.lang.NoSuchMethodException,
  java.lang.IllegalAccessException,
  java.lang.reflect.InvocationTargetException {
   java.util.ArrayList result = new java.util.ArrayList();
   Class[] classes = imasjava.imas.class.getClasses();

   for (Class classId: classes) {
    //  if(CPOInterface.class.isAssignableFrom(classId))
    {
     Method method = classId.getMethod("getIdsName");
     Object retVal = method.invoke(null);
     result.add((String) retVal);
    }
   }
   return result;
  }

 public static int getMaxOccurences(String idsName) throws
 java.lang.NoSuchMethodException,
  java.lang.IllegalAccessException,
  java.lang.reflect.InvocationTargetException {
   Class[] classes = imasjava.imas.class.getClasses();
   Class idsClass = null;

   for (Class classId: classes) {
    if (classId.getName().contains(idsName)) {
     Method method = classId.getMethod("getMaxOccurences");
     Object retVal = method.invoke(null);
     return ((Integer) retVal).intValue();
    }
   }
   return -1;
  }






 


 static public void setPulseCtx(int pulseCtx)
{
    <xsl:apply-templates select="IDS" mode="SET_PULSE_CTX"/>
}





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

 static public int openEnv(String name, int shot, int run, String user, String tokamak, String version) throws UALException
{
    int pulseCtx;
   try{ 
    
    pulseCtx = LowLevel.ual_begin_pulse_action(LowLevel.MDSPLUS_BACKEND, shot, run, user, tokamak, version); 
    }
    catch(Exception exc){
        throw new UALException("[ual_begin_pulse_action]: Error creating pulse file: " + user + "/" + tokamak + "/" + version + "/"+ shot + "/" + run + ":\n" + exc.getMessage()  );
   
    }

    try{ 
    
    LowLevel.ual_open_pulse(pulseCtx, LowLevel.OPEN_PULSE, "");
   }
    catch(Exception exc){
        throw new UALException("[ual_open_pulse]: Error creating pulse file: " + user + "/" + tokamak + "/" + version + "/"+ shot + "/" + run + ":\n" + exc.getMessage()  );
   
    }


    imas.shot = shot;
    imas.run = run;
    imas.user = user;
    imas.tokamak = tokamak;
    imas.version = version;
    imas.pulseCtx = pulseCtx;
    imas.setPulseCtx(pulseCtx);
    return pulseCtx;
}

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
 static public int createEnv(String name, int shot, int run, int refShot, int refRun, String user, String tokamak, String version) throws UALException
{
  /*  System.err.println("WARNING:\n"
                        + "\"createEnv(String name, int shot, int run, int refShot, int refRun, String user, String tokamak, String version)\"  is DEPRECATED.\n"
                        + "Please use \"createEnv(int shot, int run, String user, String tokamak, String version)\" instead");
    */ return imas.createEnv(shot,  run,  user,  tokamak,  version);
}

static public int createEnv(int shot, int run, String user, String tokamak, String version) throws UALException
{
    int pulseCtx = -1;

    try{ 
    
    pulseCtx = LowLevel.ual_begin_pulse_action(LowLevel.MDSPLUS_BACKEND, shot, run, user, tokamak, version); 
    }
    catch(Exception exc){
        throw new UALException("[ual_begin_pulse_action]: Error creating pulse file: " + user + "/" + tokamak + "/" + version + "/"+ shot + "/" + run + ":\n" + exc.getMessage()  );
   
    }

    try{ 
    
    LowLevel.ual_open_pulse(pulseCtx, LowLevel.FORCE_CREATE_PULSE, "");
   }
    catch(Exception exc){
        throw new UALException("[ual_open_pulse]: Error creating pulse file: " + user + "/" + tokamak + "/" + version + "/"+ shot + "/" + run + ":\n" + exc.getMessage()  );
   
    }

    imas.shot = shot;
    imas.run = run;
    imas.user = user;
    imas.tokamak = tokamak;
    imas.version = version;

    imas.pulseCtx = pulseCtx;
    imas.setPulseCtx(pulseCtx);
    return pulseCtx;
}


 
 /**
  *Closes the currently open database.
  * @param refIdx database index, returned by create or open.
  **/
 static public void close() throws UALException
{
    try{
        LowLevel.ual_close_pulse(imas.pulseCtx, LowLevel.CLOSE_PULSE, "");
    }
    catch (Exception exc)
    {
        throw new UALException("[ual_close_pulse]: Error closing pulse file: " + imas.user + "/" + imas.tokamak + "/" + imas.version + "/"+ imas.shot + "/" + imas.run + ":\n" + exc.getMessage()  );
    }
    finally
    {
        LowLevel.ual_end_action(imas.pulseCtx);
    }
}

 static public void close(int refIdx) throws UALException
{
 /*      System.err.println("WARNING:\n"
                        + "\"int close(int refIdx)\"  is DEPRECATED.\n"
                        + "Please use \"close()\" instead");
  */ close();
}

 static public void close(int refIdx, String name, int shot, int run) throws UALException{
    System.err.println("WARNING:\n"
                        + "\"int close(int refIdx, String name, int shot, int run)\"  is DEPRECATED.\n"
                        + "Please use \"close()\" instead");
   close();
 }

 /**
  *Get the time base of a ids.
  * @param idx database index, returned by create or open.
  * @param path name of the IDS
  * @return a vector containing the times of all slices
  * @exception UALException is thrown if the time base cannot be read.
  **/
 static public Vect1DDouble getTime(int expIdx, String path) throws UALException {
  UALLowLevel.beginIDSGet(expIdx, path, true);
  Vect1DDouble time = UALLowLevel.getVect1DDouble(expIdx, path, "time");
  UALLowLevel.endIDSGet(expIdx, path);
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
<!--=========================================================================================================================================================-->
<!--=========================================================================================================================================================-->
<!--=========================================================================================================================================================-->
<!--                                                           Definition of IDS Base class                                                                  -->
<!--=========================================================================================================================================================-->
<!--=========================================================================================================================================================-->
<!--=========================================================================================================================================================-->

<xsl:template match = "IDS" mode="DEFINE_IDS_BASE_CLASS">

<xsl:result-document href="src/imasjava/ids/{@name}_IDSBase.java" standalone="yes" method="text">

package imasjava.ids;

import imasjava.wrapper.*;
import imasjava.*;

public class <xsl:value-of select="@name"/>_IDSBase
{

    private static final int maxOccurences = <xsl:value-of select="@maxoccur"/>;
    private static final String IDS_NAME    = "<xsl:value-of select="@name"/>";

    private static boolean isHomogeneous = false;
    private static Vect1DDouble idsTime = null;
    private static int pulseCtx = -1;
    
    public static int getMaxOccurences() {
                return maxOccurences;
        }

    public static String getIdsName() 
    {
           return <xsl:value-of select="@name"/>_IDSBase.IDS_NAME;
    }
    
     static private void setHomogeneous(boolean isHomogeneous)
    {
         <xsl:value-of select="@name"/>_IDSBase.isHomogeneous = isHomogeneous;
    }
    
     static boolean isHomogeneous()
    {
        return <xsl:value-of select="@name"/>_IDSBase.isHomogeneous;
    }
    
    boolean readHomogeneous(int ctx) throws UALException
    {
        int homogenousTime = -1;
        
        homogenousTime = Wrapper.readData(ctx, "ids_properties/homogeneous_time", "", homogenousTime);
        
        <xsl:value-of select="@name"/>_IDSBase.isHomogeneous =  (homogenousTime == 1);

        return (homogenousTime == 1);
    }
    
    static private void setIdsTime(Vect1DDouble idsTime )
    {
          <xsl:value-of select="@name"/>_IDSBase.idsTime = idsTime;
    }
    
    static private Vect1DDouble getIdsTime()
    {
         return  <xsl:value-of select="@name"/>_IDSBase.idsTime;
    }

    static public void setPulseCtx(int pulseCtx)
    {
        <xsl:value-of select="@name"/>_IDSBase.pulseCtx = pulseCtx;
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
    public static void put(int pulseCtx, String idsFullName, imas.<xsl:value-of select="@name"/> ids)  throws UALException
    {
        int iOcurrence = 0;

   /*     System.err.println("WARNING:\n"
                        + "\"put(int pulseCtx, String idsFullName, imas.<xsl:value-of select="@name"/> ids) \"  is DEPRECATED.\n"
                        + "Please use \"put()\" instead");
*/
        if(ids.ids_properties.homogeneous_time == LowLevel.EMPTY_INT)
        {
            System.err.println("Warning: IDS <xsl:value-of select="@name"/> is found to be EMPTY (homogeneous_time undefined). PUT quits with no action.");
            return;
        }
    	<xsl:value-of select="@name"/>_IDSBase.setHomogeneous(ids.ids_properties.homogeneous_time == 1);
	
	    //<xsl:value-of select="@name"/>_IDSBase.setIdsTime(ids.time);
	
        if(!<xsl:value-of select="@name"/>_IDSBase.IDS_NAME.equals(idsFullName))
        {
            String tokens[] = idsFullName.split("/");
            iOcurrence = Integer.parseInt(tokens[1]);          
        }

        ids.setPulseCtx(pulseCtx);
	    ids.put(iOcurrence);
    }

    public void put()  throws UALException
    {
        this.put(0);
    }

    public void put(int iOccurrence)  throws UALException
    {
        int pulseCtx = this.pulseCtx;
        int ctx = -1;
        String idsFullName = <xsl:value-of select="@name"/>_IDSBase.IDS_NAME;
 

        boolean isIdsHomogeneous = this.isHomogeneous();

        if(iOccurrence > 0)
            idsFullName = idsFullName + "/" + iOccurrence;

      
      
        try{
            // Open put context
            ctx = LowLevel.ual_begin_global_action(pulseCtx, idsFullName, LowLevel.WRITE_OP);

            this.deleteRootFields(ctx);
            this.putRootFields(ctx, isIdsHomogeneous);
        }
        finally {
            LowLevel.ual_end_action(ctx);
        }
    }

    public void putRootFields(int ctx, boolean isIdsHomogeneous)  throws UALException
    {
        int aosCtx = -1;
        int arraySize = -1;

        Vect1DDouble  time = null; 
        String        ual_debug = System.getenv("ual_debug");
        String strNodePath = "";
        String strTimeBasePath = "";

       <xsl:apply-templates select="field" mode="PUT_SINGLE">
            <xsl:with-param name="dynamic_only" select="'no'"/>
        </xsl:apply-templates>
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
    public static void putSlice(int pulseCtx, String idsFullName, imas.<xsl:value-of select="@name"/> ids) throws UALException
    {
        double sliceTime = -1.0;
        int ctx = -1;
        int putSliceOpCtx = -1;
        int aosCtx = -1;
        int arraySize = -1;

        System.err.println("WARNING:\n"
                        + "\"putSlice(int pulseCtx, String idsFullName, imas.<xsl:value-of select="@name"/> ids) \"  is DEPRECATED.\n"
                        + "Please use \"putSlice()\" instead");


        if(ids.ids_properties.homogeneous_time == LowLevel.EMPTY_INT)
        {
            System.err.println("Warning: IDS <xsl:value-of select="@name"/> is found to be EMPTY (homogeneous_time undefined). PUTSLICE quits with no action.");
            return;
        }
	  	
	  <xsl:value-of select="@name"/>_IDSBase.setIdsTime(ids.time);


    sliceTime = ids.time.getElementAt(0);

    // Open put context
    putSliceOpCtx = LowLevel.ual_begin_slice_action(pulseCtx, idsFullName, LowLevel.WRITE_OP, sliceTime, LowLevel.UNDEFINED_INTERP);



    ctx = putSliceOpCtx;

      ids.putSlice(ctx, idsFullName);

    LowLevel.ual_end_action(putSliceOpCtx);



    }


    public void putSlice(int expIdx, String path) throws UALException
    {
          String        strTimeBasePath;
	  String strNodePath = "";
	  Vect1DDouble  idsGlobalTime = this.time;
	  
          String        ual_debug = System.getenv("ual_debug");

  	  
          strTimeBasePath="time";
   <!--     <xsl:apply-templates select = "field" mode = "PUT_SLICE"/> 
    <xsl:apply-templates select="field" mode="PUT_SINGLE">
        <xsl:with-param name="dynamic_only" select="'yes'"/>
    </xsl:apply-templates>
    --> }






   /* ------------------------------------------------------------------------------------------------------------ */
   /* -----------------------------------       GET       ------------------------------------------------------- */  
   /* ------------------------------------------------------------------------------------------------------------ */
    public static imas.<xsl:value-of select="@name"/>  get(int expIdx, String idsFullName)  throws UALException
    {
        imas.<xsl:value-of select="@name"/> ids = new imas.<xsl:value-of select="@name"/> ();
        int iOcurrence = 0;

  /*      System.err.println("WARNING:\n"
                        + "\"get(int expIdx, String path)) \"  is DEPRECATED.\n"
                        + "Please use \"get()\" instead");
  */
        if(!<xsl:value-of select="@name"/>_IDSBase.IDS_NAME.equals(idsFullName))
        {
            String tokens[] = idsFullName.split("/");
            iOcurrence = Integer.parseInt(tokens[1]);          
        }
        ids.setPulseCtx(expIdx);
        ids.get(iOcurrence);
        return ids;
     }
    
    
    public void get(int iOccurrence)  throws UALException
    {
        String strNodePath = "";
        int pulseCtx = this.pulseCtx;
        int ctx = -1;
        String idsFullName = <xsl:value-of select="@name"/>_IDSBase.IDS_NAME;
 

        boolean isIdsHomogeneous = false;

        if(iOccurrence > 0)
            idsFullName = idsFullName + "/" + iOccurrence;

        // TO DO: ZERO CONTENT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        try{
            // Open get context
            ctx = LowLevel.ual_begin_global_action(pulseCtx, idsFullName, LowLevel.READ_OP);

            isIdsHomogeneous = this.readHomogeneous(ctx);
            this.getRootFields(ctx, isIdsHomogeneous);
        }
        finally {
            LowLevel.ual_end_action(ctx);
        }
    }
    public void getRootFields(int ctx, boolean isIdsHomogeneous)  throws UALException
    {
        int aosCtx = -1;
        int arraySize = -1;

        String strNodePath = "";
        String strTimeBasePath = "";

      <xsl:apply-templates select="field" mode="GET_SINGLE"/>
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
 * by LowLevel.EMPTY_INT if integer, imas.EMPTY_DOUBLE if double and empty String is string.
 **/
    public static imas.<xsl:value-of select="@name"/>  getSlice(int expIdx, String path, double time, int interpolMode) throws UALException
    {
      double        retTime;
      
      imas.<xsl:value-of select="@name"/> ids = new imas.<xsl:value-of select="@name"/> ();
      retTime = UALLowLevel.beginIDSGetSlice(expIdx, path, time);
      
      // read a value of ids_properties/homogeneous_time  
          try {
            int homogeneous_time = UALLowLevel.getInt(expIdx, path, "ids_properties/homogeneous_time");
            <xsl:value-of select="@name"/>_IDSBase.setHomogeneous(homogeneous_time == 1);
            
          } catch(Exception exc)
          {
          	throw new UALException("Fatal error: value of homogeneous_time cannot be read: " + exc.getMessage());  
          }
      
      
 	ids.doGetSlice( expIdx,  path,  time,  interpolMode);
      UALLowLevel.endIDSGetSlice(expIdx, path);
      return ids;
    }
    
    public void doGetSlice(int expIdx, String path, double time, int interpolMode) throws UALException
    {
      String        strTimeBasePath;
      String        ual_debug = System.getenv("ual_debug");
      double        retTime;
      String strNodePath = "";
    <!--  
      <xsl:apply-templates select = "field" mode = "GET_SLICE" />
-->   
 }


 /* ------------------------------------------------------------------------------------------------------------ */
 /* -----------------------------------       DELETE       ----------------------------------------------------- */  
 /* ------------------------------------------------------------------------------------------------------------ */
 /**
 * Method delete removes all the data associated with a <xsl:value-of select="@name"/> IDS in the open database.
 * @param expIdx The index to the database, returned by imas.open()
 * @param path The path name to the selected data item. By convention, only a single tree level is defined for IDS objects in the database.
 * @exception UALException Issued when data cannot be stored for any reason.
 **/
    public static void delete(int pulseCtx, String idsFullName) throws UALException
    {

        imas.<xsl:value-of select="@name"/> ids = new imas.<xsl:value-of select="@name"/> ();
        int iOcurrence = 0;

        if(!<xsl:value-of select="@name"/>_IDSBase.IDS_NAME.equals(idsFullName))
        {
            String tokens[] = idsFullName.split("/");
            iOcurrence = Integer.parseInt(tokens[1]);          
        }

        ids.setPulseCtx(pulseCtx);
        ids.delete(iOcurrence);

     }

   public void delete(int iOccurrence) throws UALException
    {  
        String idsFullName = <xsl:value-of select="@name"/>_IDSBase.IDS_NAME;
        int ctx = -1;
 
        if(iOccurrence > 0)
            idsFullName = idsFullName + "/" + iOccurrence;

        try{
            // Open put context
            ctx = LowLevel.ual_begin_global_action(pulseCtx, idsFullName, LowLevel.WRITE_OP);

            this.deleteRootFields(ctx);
        }
        finally {
            LowLevel.ual_end_action(ctx);
        }
     }


   private void deleteRootFields(int ctx) throws UALException
    {  
        String strNodePath = "";

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

  <xsl:template match="field[@data_type='structure' or @data_type='struct_array']" mode="CLASS">
	  <xsl:variable name="class_name">
	<xsl:call-template name="BUILD_CLASS_NAME">
	</xsl:call-template>
</xsl:variable>
/* ------------------------------------------------------------------------------------------------------------------------ */  
/* ------------------------------------------------------------------------------------------------------------------------ */
/* ---------                         CLASS: <xsl:value-of select="$class_name"/>                                ----------- */
/* ------------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------------ */
    public static class  <xsl:value-of select="$class_name"/> 
    {
    
 	 <xsl:apply-templates select = "field" mode = "DECLARE"/> 
     

   /* _____________________________________________________________________________________________________________ */
   /*_________________________________       PUT      _____________________________________________________________ */  
   /* ____________________________________________________________________________________________________________  */
  	public void put(int ctx, boolean isIdsHomogeneous)  throws UALException
    {
        String strTimeBasePath = null;
        String strNodePath = null;
        int arraySize = -1;
        int aosCtx = -1;
        //Vect1DDouble  time = null;
        //String   ual_debug = System.getenv("ual_debug");
	  
    
        <xsl:apply-templates select = "field" mode = "PUT_SINGLE"/> 
    }       
     <xsl:choose>
    <xsl:when test="not(ancestor-or-self::field[@data_type='struct_array' and @maxoccur='unbounded'])">
   /* ____________________________________________________________________________________________________________ */
   /*_________________________________       PUT SLICE     _______________________________________________________ */  
   /* ____________________________________________________________________________________________________________  */
  	public void putSlice(int expIdx, String path, String strParentPath)  throws UALException
    {
          String        strTimeBasePath = null;
//	  Vect1DDouble  idsGlobalTime = getIdsTime();
	  
          String        ual_debug = System.getenv("ual_debug");
	  
	 String strNodePath = strParentPath + "<xsl:value-of select="@name"/>/";
    
      <!--   <xsl:apply-templates select = "field" mode = "PUT_SLICE"/> 
  --> 
   }       
 </xsl:when> 
   <xsl:otherwise>
  // I am descendant of AoS 2/3 so all my data are stored by putInObject !!!!
   /* ____________________________________________________________________________________________________________ */
   /* _________________________________        PUT IN OBJECT   _________________________________ */  
   /* _______________________________________________________________________________________________________________ */
    	public void putInObject(int expIdx, int idObj, String strParentObjPath, int objIdx)  throws UALException
    {
          String        strTimeBasePath = null;
	  Vect1DDouble  time = null;
	  
          String        ual_debug = System.getenv("ual_debug");
	  
	  String strObjPath = strParentObjPath + "<xsl:value-of select="@name"/>/";
    
    <!--   <xsl:apply-templates select = "field" mode = "PUT_IN_OBJECT"/> 
    -->
    }          
     </xsl:otherwise>
   </xsl:choose>
 

     
   /* ____________________________________________________________________________________________________________  */
   /* _________________________________       GET       ________________________________________________________ */  
   /* ____________________________________________________________________________________________________________  */
  	public void get(int ctx, boolean isIdsHomogeneous)   throws UALException
    {
        String strTimeBasePath = null;
        String strNodePath = null;
        int arraySize = -1;
        int aosCtx = -1;
	  
	

          <xsl:apply-templates select = "field" mode = "GET_SINGLE"/>

    }     
     
   <xsl:choose>
    <xsl:when test="not(ancestor-or-self::field[@data_type='struct_array' and @maxoccur='unbounded'])">
   /* ____________________________________________________________________________________________________________  */
   /* _________________________________       GET  SLICE     ________________________________________________________ */  
   /* ____________________________________________________________________________________________________________  */
  	public void getSlice(int expIdx, String path, String strParentPath, double time, int interpolMode)   throws UALException
    {
          String        strTimeBasePath = null;
	  
          String       ual_debug = System.getenv("ual_debug");
	  
	 String strNodePath = strParentPath + "<xsl:value-of select="@name"/>/";
    <!--
          <xsl:apply-templates select = "field" mode = "GET_SLICE"/>
-->    
}   
    
      </xsl:when>
        <xsl:otherwise>
  // I am descendant of AoS 2/3 so all my data are stored by putInObject !!!!
   /* _____________________________________________________________________________________________________________  */
   /* _________________________________        GET FROM OBJECT   ___________________________________________________ */  
   /* _____________________________________________________________________________________________________________  */
   
    	public void getFromObject(int expIdx, int idObj, String strParentObjPath, int objIdx)  throws UALException
    {
          String        strTimeBasePath = null;
	  Vect1DDouble  time = null;
	  
          String        ual_debug = System.getenv("ual_debug");
	  
	 String strObjPath = strParentObjPath + "<xsl:value-of select="@name"/>/";
    
        <!--  <xsl:apply-templates select = "field" mode = "GET_FROM_OBJECT"/>
-->   
 }          
     
   </xsl:otherwise>
   </xsl:choose>

   /* ____________________________________________________________________________________________________________  */
   /* ________________________________________       DELETE     ___________________________________________________ */  
   /* ____________________________________________________________________________________________________________  */ 
     <xsl:if test="@data_type='structure'">
       public void delete(int ctx) throws UALException
    {
        String strNodePath = null;
       
        <xsl:apply-templates select = "field" mode = "DELETE"/>
   
  }           
 </xsl:if>

    


   /* ____________________________________________________________________________________________________________  */
   /* ___________________________________        DUMP      ________________________________________________________ */  
   /* ____________________________________________________________________________________________________________  */
   /**
    * Method dump is used for debugging and prints the content of the object
    **/
    public void dump()
   {
     System.out.println("***** <xsl:value-of select="@name"/> *****");
     <xsl:apply-templates select = "field" mode = "DUMP"/>
    System.out.println("******************");
   }
   
}

   /* ____________________________________________________________________________________________________________  */
    </xsl:template>

<!-- ============================================================= SUBCLASS == 'structure' type element	- END ============================================================== -->



<!-- ============================================================= SUBCLASS == 'AoS' type element	- END ============================================================== -->


 <!-- ====================================================================================================================================================================== -->
<!-- ======================================================================================================================================================================= -->
<!-- ============================================================              TEMPLATES         	 =================================================================== -->
<!-- ======================================================================================================================================================================= -->

   <xsl:template name = "COMMENT_NODE">
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

   <!--=================================================-->
<!--                 set idx in IDS                  -->
<!--=================================================-->

<xsl:template match="IDS" mode="SET_PULSE_CTX">
    imas.<xsl:value-of select="@name"/>.setPulseCtx(imas.pulseCtx);
</xsl:template>
    
<!--=====================================================================================================================================-->
<!--                  Delete fields                                                                                                       -->
<!--=====================================================================================================================================-->


<xsl:template match="field" mode="DELETE">
    <xsl:call-template name="COMMENT_NODE"/>
    <xsl:choose>
        <xsl:when test="@data_type='structure'">
            this.<xsl:value-of select="@name"/>.delete(ctx);
        </xsl:when>
        <xsl:otherwise>
            strNodePath = "<xsl:value-of select="@path"/>";
            LowLevel.ual_delete_data(ctx, strNodePath);
        </xsl:otherwise>
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

 <xsl:call-template name="COMMENT_NODE"/>
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
        if(<xsl:value-of select = "$currentidxpath"/> != LowLevel.EMPTY_INT)
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
        if(<xsl:value-of select = "$currentidxpath"/> != LowLevel.EMPTY_DOUBLE)
            System.out.println(<xsl:value-of select = "$currentidxpath"/>);
        else
            System.out.println("Empty");
        System.out.println("");
    </xsl:when>
    <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
         if(<xsl:value-of select = "$currentidxpath"/> != LowLevel.EMPTY_DOUBLE)
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
            this.<xsl:value-of select = "@name"/>[i].dump();
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
 <xsl:call-template name="COMMENT_NODE"/>
 <!-- /* <xsl:value-of select="@documentation"/>**/ -->
  <xsl:choose>
    <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
      public String <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
      public int <xsl:value-of select = "@name"/> = LowLevel.EMPTY_INT;
    </xsl:when>
    <xsl:when test="@name='xs:boolean'">
      public boolean <xsl:value-of select = "@name"/>;
    </xsl:when>
    <xsl:when test="@name='xs:double'">
      public double <xsl:value-of select = "@name"/> = LowLevel.EMPTY_DOUBLE;
    </xsl:when>
    <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
      public double <xsl:value-of select = "@name"/> = LowLevel.EMPTY_DOUBLE;
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
        //an empty nested static class for easier initialization of IDS elements (new x.y.zClass() )
	public static class <xsl:value-of select="@name"/>Class extends  <xsl:value-of select="$class_name"/>{}
	public  <xsl:value-of select="@name"/>Class <xsl:value-of select = "@name"/>[];
    </xsl:when>

    <xsl:when test="@data_type='structure'">
      <xsl:variable name="class_name">
	<xsl:call-template name="BUILD_CLASS_NAME">
	</xsl:call-template>
     </xsl:variable>
        //an empty nested static class for easier initialization of IDS elements (new x.y.zClass() )
       public static class <xsl:value-of select="@name"/>Class extends  <xsl:value-of select="$class_name"/>{}
       public  <xsl:value-of select="@name"/>Class  <xsl:value-of select = "@name"/> = new   <xsl:value-of select="@name"/>Class();
    </xsl:when>

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


<xsl:template match="field" mode="PUT_SINGLE">
<xsl:param name="dynamic_only"/>
    <xsl:call-template name="COMMENT_NODE"/>
<xsl:if test="$dynamic_only !='yes' or descendant-or-self::field[@type='dynamic'] or ancestor::field[@type='dynamic' and @data_type='struct_array']">

<xsl:variable name="methodName">
            <xsl:choose>
                <xsl:when test="$dynamic_only !='yes'" >
                        <xsl:value-of select="'put'" />
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="'putSlice'" />
                    </xsl:otherwise>
            </xsl:choose>
    </xsl:variable>
<xsl:choose>
<!--========== Regular structures ==========-->
    <!-- YB 2014 -->
        <xsl:when test="@data_type='structure'">
        this.<xsl:value-of select="@name"/>.<xsl:value-of select="$methodName"/>(ctx, isIdsHomogeneous);
        </xsl:when>

<!-- XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX -->
        <xsl:when test="@data_type='struct_array' and @maxoccur!='unbounded'">
            <xsl:text>/*-----------------------------------------------------------------------------------------*/&#xA;</xsl:text>
            <xsl:choose>
                <xsl:when test="ancestor::field[@data_type='struct_array']">
                    strNodePath = &quot;<xsl:call-template  name="printAosRelativePath"/>&quot;;
                </xsl:when>
                <xsl:otherwise>
                    strNodePath = &quot;<xsl:value-of select="@path"/>&quot;;
                </xsl:otherwise>
            </xsl:choose>
            strTimeBasePath = "";
            arraySize = this.<xsl:value-of select = "@name"/>.length;
            if(arraySize > 0)
            {   
                try{
                    int tmpArray[] = { arraySize };
                    aosCtx = LowLevel.ual_begin_arraystruct_action(ctx, strNodePath, strTimeBasePath, tmpArray);
                    for( int i = 0; i &lt;arraySize; i++)
                    {
                        this.<xsl:value-of select="@name"/>[i].<xsl:value-of select="$methodName"/>(aosCtx, isIdsHomogeneous);
                        LowLevel.ual_iterate_over_arraystruct(aosCtx, 1); 
                    }
                }
                    
                finally { 
                    LowLevel.ual_end_action(aosCtx);
                }
            }
        </xsl:when>
        <xsl:when  test="@data_type='struct_array' and @maxoccur='unbounded' and (@type!='dynamic' or not(@type))">
        
            <xsl:text>/*-----------------------------------------------------------------------------------------*/&#xA;</xsl:text>
            <xsl:choose>
                <xsl:when test="ancestor::field[@data_type='struct_array']">
                    strNodePath = &quot;<xsl:call-template  name="printAosRelativePath"/>&quot;;
                </xsl:when>
                <xsl:otherwise>
                    strNodePath = &quot;<xsl:value-of select="@path"/>&quot;;
                </xsl:otherwise>
            </xsl:choose>
            strTimeBasePath = "";
            arraySize = this.<xsl:value-of select = "@name"/>.length;
            if(arraySize > 0)
            {   
                try{
                    int tmpArray[] = { arraySize };
                    aosCtx = LowLevel.ual_begin_arraystruct_action(ctx, strNodePath, strTimeBasePath, tmpArray);
                    for( int i = 0; i &lt;arraySize; i++)
                    {
                        this.<xsl:value-of select="@name"/>[i].<xsl:value-of select="$methodName"/>(aosCtx, isIdsHomogeneous);
                        LowLevel.ual_iterate_over_arraystruct(aosCtx, 1); 
                    }
                }
                    
                finally { 
                    LowLevel.ual_end_action(aosCtx);
                }
            }
        </xsl:when>
        <xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and @type='dynamic'">

            <xsl:text>/*-----------------------------------------------------------------------------------------*/&#xA;</xsl:text>
            <xsl:choose>
                <xsl:when test="ancestor::field[@data_type='struct_array']">
                    strNodePath = &quot;<xsl:call-template  name="printAosRelativePath"/>&quot;;
                    if (isIdsHomogeneous) 
                            strTimeBasePath = "/time";
                        else
                        strTimeBasePath = &quot;<xsl:call-template  name="printAosRelativePath"/>/time&quot;;
                </xsl:when>
                <xsl:otherwise>
                    strNodePath = &quot;<xsl:value-of select="@path"/>&quot;;
                    if (isIdsHomogeneous) 
                            strTimeBasePath = "/time";
                        else
                        strTimeBasePath = &quot;<xsl:value-of select="@path"/>/time&quot;;
                </xsl:otherwise>
            </xsl:choose>
            arraySize = this.<xsl:value-of select = "@name"/>.length;
            if(arraySize > 0)
            {   
                try{
                    int tmpArray[] = { arraySize };
                    aosCtx = LowLevel.ual_begin_arraystruct_action(ctx, strNodePath, strTimeBasePath, tmpArray);
                    for( int i = 0; i &lt;arraySize; i++)
                    {
                        this.<xsl:value-of select="@name"/>[i].<xsl:value-of select="$methodName"/>(aosCtx, isIdsHomogeneous);
                        LowLevel.ual_iterate_over_arraystruct(aosCtx, 1); 
                    }
                }
                    
                finally { 
                    LowLevel.ual_end_action(aosCtx);
                }
            }
        </xsl:when>

    <xsl:when test="
           @data_type='str_type' or @data_type='STR_0D'
        or @data_type='str_1d_type' or @data_type='STR_1D'
        or @data_type='int_type' or @data_type='INT_0D'
        or @data_type='flt_type' or @data_type='FLT_0D' 
        or @data_type='flt_1d_type' or @data_type='FLT_1D'
        or @data_type='int_1d_type' or @data_type='INT_1D'
        or @data_type='FLT_2D' or @data_type='INT_2D'
        or @data_type='FLT_3D'  or @data_type='INT_3D'
        or @data_type='FLT_4D'  or @data_type='INT_4D'
        or @data_type='FLT_5D'or @data_type='INT_5D'
        or @data_type='FLT_6D'or @data_type='INT_6D'">
        <xsl:choose>
            <xsl:when test="ancestor::field[@data_type='struct_array']">
                strNodePath = &quot;<xsl:call-template  name="printAosRelativePath"/>&quot;;
            </xsl:when>
            <xsl:otherwise>
                strNodePath = &quot;<xsl:value-of select="@path"/>&quot;;
            </xsl:otherwise>
        </xsl:choose>
        <xsl:choose>
            <xsl:when test="@type='dynamic' and not(ancestor::field[@type='dynamic' and @data_type='struct_array'])">
            if (isIdsHomogeneous) 
            strTimeBasePath="/time";
            else
                strTimeBasePath=&quot;<xsl:value-of select="@strTimeBasePath"/>&quot;;
            </xsl:when>
            <xsl:otherwise>
                    strTimeBasePath = "";
            </xsl:otherwise>
        </xsl:choose>
        Wrapper.writeData(ctx, strNodePath, strTimeBasePath, this.<xsl:value-of select="@name"/>);
    </xsl:when>
        <xsl:otherwise>
            //Doc Put <xsl:value-of select="@path"/> : PROBLEM : UNIDENTIFIED TYPE !!! <!-- for comment only -->
        </xsl:otherwise>
    </xsl:choose>
</xsl:if>
</xsl:template>



<!--=================================================-->
<!--      Get field from a time-independent IDS      -->
<!--=================================================-->

<xsl:template match = "field" mode = "GET_SINGLE_X">
<xsl:param name="variable_path"/>
<xsl:param name="mds_path"/>
<xsl:param name="ids_name"/>
<xsl:call-template name="COMMENT_NODE"/>
  	  <xsl:variable name="class_name">
	<xsl:call-template name="BUILD_CLASS_NAME">
	</xsl:call-template>
</xsl:variable>
      <xsl:choose>
 	<!-- =============================== Regular structure  ====================================== -->
        <xsl:when test="@data_type='structure'">
	    this.<xsl:value-of select = "@name"/>.get(ctx, path, strNodePath);
        </xsl:when>

 	<!-- ===================================== AoS type 1  ========================================= -->
         <xsl:when test="@data_type='struct_array' and @maxoccur!='unbounded'">
          <!-- Generate the full name of the subclass from the field path -->

         try {
	    int iTimeVectSize = UALLowLevel.getInt(expIdx, path, strNodePath + "<xsl:value-of select = "@name"/>/Shape_of");
            this.<xsl:value-of select="@name"/> = new <xsl:value-of select="@name"/>Class[iTimeVectSize];
            for (int i = 0; i &lt; this.<xsl:value-of select = "@name"/>.length; i++) 
	    {
              this.<xsl:value-of select = "@name"/>[i] = new <xsl:value-of select="@name"/>Class();
	      this.<xsl:value-of select = "@name"/>[i].get(expIdx, path, strNodePath, i + 1);

            }
     } catch(Exception exc){this.<xsl:value-of select="@name"/> = null;}

      </xsl:when>
         <!-- ====================== AoS type 2  ============================= -->
	<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and (not(@type) or @type!='dynamic')">
 	 try 
	 { 
	    
	     int iAoS2Size = UALLowLevel.getObjectDim(expIdx, idAoS2Obj);
	     if(iAoS2Size > 0)
	     {
	        this.<xsl:value-of select="@name"/> = new <xsl:value-of select = "@name"/>Class[iAoS2Size];
	        for (int i = 0; i &lt; iAoS2Size; i++) {
                   this.<xsl:value-of select="@name"/>[i] = new <xsl:value-of select = "@name"/>Class();
		   this.<xsl:value-of select="@name"/>[i].getFromObject(expIdx, idAoS2Obj,  "", i);
                }
	    }
	     UALLowLevel.releaseObject(expIdx, idAoS2Obj);

	 }  catch(UALException exc) 
	 {
                throw new UALException("Error in get: for <xsl:value-of select = "translate(@path,'/','.')"/> " + exc);
	 }
	 
      </xsl:when>
 	<!-- ===================================== AoS type 3  ========================================= -->
<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and @type='dynamic'">
          try {
            int idObjAllTimes = UALLowLevel.getObject(expIdx, path, strNodePath + "<xsl:value-of select = "@name"/>", imas.TIMED); //read the whole timed block, returns -1 if Aos is empty
            if (idObjAllTimes > -1) 
	    {
            int iTimeVectSize = UALLowLevel.getObjectDim(expIdx, idObjAllTimes); // the size of this top object is the number of time slices
             this.<xsl:value-of select = "@name"/> = new <xsl:value-of select="@name"/>Class[iTimeVectSize];
//          if (ual_debug.equals("yes")) System.out.printlln("Get ids%<xsl:value-of select="translate(@path,'/','.')"/> + lentime =" + lentime");
            for (int i = 0; i &lt; iTimeVectSize; i++) {
               this.<xsl:value-of select = "@name"/>[i] = new <xsl:value-of select="@name"/>Class();
              try {
                int idObj = UALLowLevel.getObjectFromObject(expIdx, idObjAllTimes, "ALLTIMES", i);
    		 this.<xsl:value-of select = "@name"/>[i].getFromObject(expIdx, idObj, "", 0);
              } catch(Exception exc){ this.<xsl:value-of select = "@name"/>[i] = null;}
            }
            UALLowLevel.releaseObject(expIdx, idObjAllTimes);
            } // close of "if (idObjAllTimes > -1)"
          } catch(Exception exc){ this.<xsl:value-of select = "@name"/> = null;}
</xsl:when>


        <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">String</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Int</xsl:with-param>
	<xsl:with-param name="Exception">LowLevel.EMPTY_INT</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='xs:boolean'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Boolean</xsl:with-param>
	<xsl:with-param name="Exception">LowLevel.EMPTY_INT</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@name='xs:double'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Double</xsl:with-param>
	<xsl:with-param name="Exception">LowLevel.EMPTY_DOUBLE</xsl:with-param>
	</xsl:call-template>
        </xsl:when>
        <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Double</xsl:with-param>
	<xsl:with-param name="Exception">LowLevel.EMPTY_DOUBLE</xsl:with-param>
	</xsl:call-template>
       </xsl:when>

        <xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect1DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
         </xsl:when>
        <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect1DString</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
         </xsl:when>
        <xsl:when test="@name='vecdbl_type'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect1DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
         </xsl:when>
        <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect1DInt</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
         </xsl:when>

        <xsl:when test="@data_type='FLT_2D'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect2DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
        <xsl:when test="@name='matdbl_type'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect2DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
        <xsl:when test="@data_type='INT_2D'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect2DInt</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>

        <xsl:when test="@data_type='FLT_3D'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect3DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array3ddbl_type'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect3DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       <xsl:when test="@data_type='INT_3D'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect3DInt</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>

        <xsl:when test="@data_type='FLT_4D'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect4DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array4ddbl_type'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect4DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array4dint_type'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect4DInt</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>

        <xsl:when test="@data_type='FLT_5D'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect5DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array5ddbl_type'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect5DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array5dint_type'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect5DInt</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>

        <xsl:when test="@data_type='FLT_6D'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect6DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array6ddbl_type'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect6DDouble</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array6dint_type'">
	<xsl:call-template name="getVariable"> 
	<xsl:with-param name="Function">Vect6DInt</xsl:with-param>
	<xsl:with-param name="Exception">null</xsl:with-param>
	</xsl:call-template>
       </xsl:when>

      </xsl:choose>
</xsl:template>


<xsl:template match="field" mode="GET_SINGLE">
    <xsl:call-template name="COMMENT_NODE"/>
<xsl:choose>
<!--========== Regular structures ==========-->
    <!-- YB 2014 -->
        <xsl:when test="@data_type='structure'">
            this.<xsl:value-of select="@name"/>.get(ctx, isIdsHomogeneous);
        </xsl:when>

<!-- XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX -->
        <xsl:when test="@data_type='struct_array' and @maxoccur!='unbounded'">
            <xsl:text>/*-----------------------------------------------------------------------------------------*/&#xA;</xsl:text>
            <xsl:choose>
                <xsl:when test="ancestor::field[@data_type='struct_array']">
                    strNodePath = &quot;<xsl:call-template  name="printAosRelativePath"/>&quot;;  
                </xsl:when>
                <xsl:otherwise>
                    strNodePath = &quot;<xsl:value-of select="@path"/>&quot;;
                </xsl:otherwise>
            </xsl:choose>
            strTimeBasePath = "";
            try{       
                    int tmpArray[] = new int[1];
                    aosCtx = LowLevel.ual_begin_arraystruct_action(ctx, strNodePath, strTimeBasePath, tmpArray);
                    arraySize = tmpArray[0];
                    if(arraySize &lt;= 0)
                    {
                        this.<xsl:value-of select="@name"/> = null;
                    }
                    else
                    {   
        

                        this.<xsl:value-of select="@name"/> = new <xsl:value-of select = "@name"/>Class[arraySize];
                        for( int i = 0; i &lt;arraySize; i++)
                        {
                            this.<xsl:value-of select="@name"/>[i] = new <xsl:value-of select = "@name"/>Class();
                            this.<xsl:value-of select="@name"/>[i].get(aosCtx, isIdsHomogeneous);
                            LowLevel.ual_iterate_over_arraystruct(aosCtx, 1); 
                        }
                    }
               }     
                finally { 
                    LowLevel.ual_end_action(aosCtx);
                }
            
            
        </xsl:when>
        <xsl:when  test="@data_type='struct_array' and @maxoccur='unbounded' and (@type!='dynamic' or not(@type))"> 
            <xsl:text>/*-----------------------------------------------------------------------------------------*/&#xA;</xsl:text>
            <xsl:choose>
                <xsl:when test="ancestor::field[@data_type='struct_array']">
                    strNodePath = &quot;<xsl:call-template  name="printAosRelativePath"/>&quot;;
                </xsl:when>
                <xsl:otherwise>
                    strNodePath = &quot;<xsl:value-of select="@path"/>&quot;;
                </xsl:otherwise>
            </xsl:choose>
            strTimeBasePath = "";
                 try{       
                    int tmpArray[] = new int[1];
                    aosCtx = LowLevel.ual_begin_arraystruct_action(ctx, strNodePath, strTimeBasePath, tmpArray);
                    arraySize = tmpArray[0];
                    if(arraySize &lt;= 0)
                    {
                        this.<xsl:value-of select="@name"/> = null;
                    }
                    else
                    {   
        

                        this.<xsl:value-of select="@name"/> = new <xsl:value-of select = "@name"/>Class[arraySize];
                        for( int i = 0; i &lt;arraySize; i++)
                        {
                            this.<xsl:value-of select="@name"/>[i] = new <xsl:value-of select = "@name"/>Class();
                            this.<xsl:value-of select="@name"/>[i].get(aosCtx, isIdsHomogeneous);
                            LowLevel.ual_iterate_over_arraystruct(aosCtx, 1); 
                        }
                    }
               }     
                finally { 
                    LowLevel.ual_end_action(aosCtx);
                }
        </xsl:when>
        <xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and @type='dynamic'">
            <xsl:text>/*-----------------------------------------------------------------------------------------*/&#xA;</xsl:text>
            <xsl:choose>
                <xsl:when test="ancestor::field[@data_type='struct_array']">
                    strNodePath = &quot;<xsl:call-template  name="printAosRelativePath"/>&quot;;
                    if (isIdsHomogeneous) 
                            strTimeBasePath = "/time";
                        else
                        strTimeBasePath = &quot;<xsl:call-template  name="printAosRelativePath"/>/time&quot;;
                </xsl:when>
                <xsl:otherwise>
                    strNodePath = &quot;<xsl:value-of select="@path"/>&quot;;
                    if (isIdsHomogeneous) 
                            strTimeBasePath = "/time";
                        else
                        strTimeBasePath = &quot;<xsl:value-of select="@path"/>/time&quot;;
                </xsl:otherwise>
            </xsl:choose>
                   try{       
                    int tmpArray[] = new int[1];
                    aosCtx = LowLevel.ual_begin_arraystruct_action(ctx, strNodePath, strTimeBasePath, tmpArray);
                    arraySize = tmpArray[0];
                    if(arraySize &lt;= 0)
                    {
                        this.<xsl:value-of select="@name"/> = null;
                    }
                    else
                    {   
        

                        this.<xsl:value-of select="@name"/> = new <xsl:value-of select = "@name"/>Class[arraySize];
                        for( int i = 0; i &lt;arraySize; i++)
                        {
                            this.<xsl:value-of select="@name"/>[i] = new <xsl:value-of select = "@name"/>Class();
                            this.<xsl:value-of select="@name"/>[i].get(aosCtx, isIdsHomogeneous);
                            LowLevel.ual_iterate_over_arraystruct(aosCtx, 1); 
                        }
                    }
               }     
                finally { 
                    LowLevel.ual_end_action(aosCtx);
                }
        </xsl:when>

    <xsl:when test="
           @data_type='str_type' or @data_type='STR_0D'
        or @data_type='str_1d_type' or @data_type='STR_1D'
        or @data_type='int_type' or @data_type='INT_0D'
        or @data_type='flt_type' or @data_type='FLT_0D' 
        or @data_type='flt_1d_type' or @data_type='FLT_1D'
        or @data_type='int_1d_type' or @data_type='INT_1D'
        or @data_type='FLT_2D' or @data_type='INT_2D'
        or @data_type='FLT_3D'  or @data_type='INT_3D'
        or @data_type='FLT_4D'  or @data_type='INT_4D'
        or @data_type='FLT_5D'or @data_type='INT_5D'
        or @data_type='FLT_6D'or @data_type='INT_6D'">
        <xsl:choose>
            <xsl:when test="ancestor::field[@data_type='struct_array']">
                strNodePath = &quot;<xsl:call-template  name="printAosRelativePath"/>&quot;;
            </xsl:when>
            <xsl:otherwise>
                strNodePath = &quot;<xsl:value-of select="@path"/>&quot;;
            </xsl:otherwise>
        </xsl:choose>
        <xsl:choose>
            <xsl:when test="@type='dynamic' and not(ancestor::field[@type='dynamic' and @data_type='struct_array'])">
            if (isIdsHomogeneous) 
            strTimeBasePath="/time";
            else
                strTimeBasePath=&quot;<xsl:value-of select="@timebasepath"/>&quot;;
            </xsl:when>
            <xsl:otherwise>
                    strTimeBasePath = "";
            </xsl:otherwise>
        </xsl:choose>
        this.<xsl:value-of select="@name"/> = Wrapper.readData(ctx, strNodePath, strTimeBasePath, this.<xsl:value-of select="@name"/>);

    </xsl:when>
        <xsl:otherwise>
            //Doc GET <xsl:value-of select="@path"/> : PROBLEM : UNIDENTIFIED TYPE !!! <!-- for comment only -->
        </xsl:otherwise>
    </xsl:choose>
</xsl:template>

<!--=================================================-->
<!--             Get field from a slice              -->
<!--=================================================-->

<xsl:template match = "field" mode = "GET_SLICE">
<xsl:param name="variable_path"/>
<xsl:param name="mds_path"/>
<xsl:param name="ids_name"/>
  	  <xsl:variable name="class_name">
	<xsl:call-template name="BUILD_CLASS_NAME">
	</xsl:call-template>
</xsl:variable>
<xsl:call-template name="COMMENT_NODE"/>
<xsl:choose>
   	<!-- ===================================== Structures  ========================================= -->
        <xsl:when test="@data_type='structure'">
		this.<xsl:value-of select = "@name"/>.getSlice(expIdx, path, strNodePath, time, interpolMode);
        </xsl:when>

         <!-- ===================================== AoS type 1  ========================================= -->
         <xsl:when test="@data_type='struct_array' and @maxoccur!='unbounded'">
	try {
	  int iVectSize = UALLowLevel.getInt(expIdx, path, strNodePath + "<xsl:value-of select = "@name"/>/Shape_of");
            this.<xsl:value-of select="@name"/> = new <xsl:value-of select="@name"/>Class[iVectSize];
            for (int i = 0; i &lt; this.<xsl:value-of select = "@name"/>.length; i++)
	    {
              this.<xsl:value-of select="@name"/>[i] = new <xsl:value-of select="@name"/>Class();
	      this.<xsl:value-of select="@name"/>[i].getSlice(expIdx, path, strNodePath, time, interpolMode, i + 1);
            }
         } 
	 catch(Exception exc)
	 {
	 	this.<xsl:value-of select="@name"/> = null;
	 }

	</xsl:when>
	
	<!-- ===================================== AoS type 3  ========================================= -->
	<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and @type='dynamic'">
          try {
             int idObjSingleTime = UALLowLevel.getObjectSlice(expIdx, path, strNodePath + "<xsl:value-of select = "@name"/>", time); //read the whole timed block containing a single slice
             try {
               int idAoS3Obj = UALLowLevel.getObjectFromObject(expIdx, idObjSingleTime, "ALLTIMES", 0);
	       
               this.<xsl:value-of select = "@name"/> = new <xsl:value-of select="@name"/>Class[1];
               this.<xsl:value-of select = "@name"/>[0] = new <xsl:value-of select="@name"/>Class();
               this.<xsl:value-of select="@name"/>[0].getFromObject(expIdx, idAoS3Obj, "", 0);
             }catch(Exception exc)
	     {
	       <xsl:value-of select="@name"/>[0] = null;
	     }
            UALLowLevel.releaseObject(expIdx, idObjSingleTime);
          } catch(Exception exc)
	  {
             this.<xsl:value-of select="@name"/> = null;
	  }
	</xsl:when>

   	<xsl:when test="@type='dynamic'">
   	if ( <xsl:value-of select="ancestor::IDS[1]/@name"/>_IDSBase.isHomogeneous()) {
       	  strTimeBasePath = "time";
    	} 
	else {
          strTimeBasePath = strNodePath + "time";
   	}
 	<xsl:choose>
		<xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
				<xsl:call-template name="getStringSlice"/>
		</xsl:when>
		<xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
		<xsl:call-template name="getVariableSlice"> 
		<xsl:with-param name="Function">Vect1DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Double</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">0</xsl:with-param>
		</xsl:call-template>
         </xsl:when>
        <xsl:when test="@name='vecdbl_type'">
		<xsl:call-template name="getVariableSlice"> 
		<xsl:with-param name="Function">Vect1DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Double</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">0</xsl:with-param>
		</xsl:call-template>
         </xsl:when>
        <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
// in sequence of test on data_type .... int
		<xsl:call-template name="getVariableSlice"> 
		<xsl:with-param name="Function">Vect1DInt</xsl:with-param>
		<xsl:with-param name="SliceFunction">Int</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">0</xsl:with-param>
		</xsl:call-template>
         </xsl:when>

        <xsl:when test="@data_type='FLT_2D'">
		<xsl:call-template name="getVariableSlice"> 
		<xsl:with-param name="Function">Vect2DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect1DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">1</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
        <xsl:when test="@name='matdbl_type'">
		<xsl:call-template name="getVariableSlice">
		<xsl:with-param name="Function">Vect2DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect1DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">1</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
        <xsl:when test="@data_type='INT_2D'">
		<xsl:call-template name="getVariableSlice"> 
		<xsl:with-param name="Function">Vect2DInt</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect1DInt</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">1</xsl:with-param>
		</xsl:call-template>
       </xsl:when>

        <xsl:when test="@data_type='FLT_3D'">
		<xsl:call-template name="getVariableSlice"> 
		<xsl:with-param name="Function">Vect3DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect2DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">2</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array3ddbl_type'">
		<xsl:call-template name="getVariableSlice"> 
		<xsl:with-param name="Function">Vect3DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect2DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">2</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       <xsl:when test="@data_type='INT_3D'">
		<xsl:call-template name="getVariableSlice"> 
		<xsl:with-param name="Function">Vect3DInt</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect2DInt</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">2</xsl:with-param>
		</xsl:call-template>
       </xsl:when>

        <xsl:when test="@data_type='FLT_4D'">
		<xsl:call-template name="getVariableSlice"> 
		<xsl:with-param name="Function">Vect4DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect3DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">3</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array4ddbl_type'">
		<xsl:call-template name="getVariableSlice"> 
		<xsl:with-param name="Function">Vect4DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect3DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">3</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array4dint_type'">
		<xsl:call-template name="getVariableSlice"> 
		<xsl:with-param name="Function">Vect4DInt</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect3DInt</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">3</xsl:with-param>
		</xsl:call-template>
       </xsl:when>

        <xsl:when test="@data_type='FLT_5D'">
		<xsl:call-template name="getVariableSlice"> 
		<xsl:with-param name="Function">Vect5DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect4DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">4</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array5ddbl_type'">
		<xsl:call-template name="getVariableSlice"> 
		<xsl:with-param name="Function">Vect5DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect4DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">4</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array5dint_type'">
		<xsl:call-template name="getVariableSlice"> 
		<xsl:with-param name="Function">Vect5DInt</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect4DInt</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">4</xsl:with-param>
		</xsl:call-template>
       </xsl:when>

        <xsl:when test="@data_type='FLT_6D'">
		<xsl:call-template name="getVariableSlice"> 
		<xsl:with-param name="Function">Vect6DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect5DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">5</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array6ddbl_type'">
		<xsl:call-template name="getVariableSlice"> 
		<xsl:with-param name="Function">Vect6DDouble</xsl:with-param>
		<xsl:with-param name="SliceFunction">Vect5DDouble</xsl:with-param>
		<xsl:with-param name="Exception">null</xsl:with-param>
		<xsl:with-param name="number">5</xsl:with-param>
		</xsl:call-template>
       </xsl:when>
       <xsl:when test="@name='array6dint_type'">
		<xsl:call-template name="getVariableSlice"> 
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
<!--      Put field in a full time-dependent IDS     -->
<!--=================================================-->

<xsl:template match = "field" mode = "PUT_SINGLE_X">
<xsl:param name="non_timed"/>

 <xsl:call-template name="COMMENT_NODE"/>
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
 		this.<xsl:value-of select="@name"/>[i].put<xsl:value-of select = "$NonTimed"/>(expIdx, path, strNodePath, i + 1);
            }
          }
	</xsl:when>
	
	<!-- ====================== AoS type 2  ============================= -->
	<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and (not(@type) or @type!='dynamic')">
	  if (this.<xsl:value-of select = "@name"/> != null) {
          int idxObj =UALLowLevel.beginObject(expIdx, -1, 0, path + "/" + strNodePath + "<xsl:value-of select = "@name"/>", imas.NON_TIMED);
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
            int idObjAllTimes = UALLowLevel.beginObject(expIdx,-1,0,path + "/" + strNodePath + "<xsl:value-of select = "@name"/>", imas.TIMED_CLEAR);
            for (int i = 0; i &lt; this.<xsl:value-of select = "@name"/>.length; i++) 
	    {
               int idAoS3Obj = UALLowLevel.beginObject(expIdx, idObjAllTimes, i, "ALLTIMES", imas.TIMED);
               this.<xsl:value-of select = "@name"/>[i].putInObject(expIdx, idAoS3Obj, "", 0);
               UALLowLevel.putObjectInObject(expIdx, idObjAllTimes, "ALLTIMES", i, idAoS3Obj);
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
            
            strTimeBasePath = strNodePath + "<xsl:value-of select = "@name"/>/time";
            UALLowLevel.beginIDSPutTimed(expIdx, path, time);
            UALLowLevel.putVect1DDouble(expIdx, path, strTimeBasePath.trim(), strTimeBasePath.trim(), time, true);
            UALLowLevel.endIDSPutTimed(expIdx, path);
            //if (ual_debug.equals("yes")) System.out.println("Put <xsl:call-template name="printstrTimeBasePath"/> ");
            UALLowLevel.putObject(expIdx, path, strNodePath + "<xsl:value-of select = "@name"/>", idObjAllTimes , 1);
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
<xsl:call-template name="COMMENT_NODE"/>
<xsl:if test="@type ='dynamic' or @data_type='structure' or @data_type='struct_array'"> <!-- This skips the routine for non timed fields -->
      <xsl:choose>
        <!--========== Regular structures ==========-->
        <xsl:when test="@data_type='structure'">
	this.<xsl:value-of select = "@name"/>.putSlice(expIdx, path, strNodePath);
        </xsl:when>

        <!-- ========================================================== AoS type 1  =================================================================================== -->
        <xsl:when test="@data_type='struct_array' and @maxoccur!='unbounded'">
	
          if (this.<xsl:value-of select = "@name"/> != null) {
            UALLowLevel.putInt(expIdx, path, strNodePath + "<xsl:value-of select = "@name"/>/Shape_of", this.<xsl:value-of select="@name"/>.length);
             for (int i = 0; i&lt; this.<xsl:value-of select = "@name"/>.length; i++){
      		this.<xsl:value-of select = "@name"/>[i].putSlice(expIdx, path, strNodePath, i + 1);
             }
          }
		
        </xsl:when>
	<!-- ========================================================== AoS type 2  =================================================================================== -->
		<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and (not(@type) or @type!='dynamic')">
		 
		<!--   <xsl:message terminate="no">XXX<xsl:call-template name="COMMENT_NODE"/>     
		             <xsl:text>&#xA; Ancestor of type AoS2:   </xsl:text>  <xsl:value-of select="../@data_type"/>  : <xsl:value-of select="../@path"/>   : <xsl:value-of select="../@maxoccur"/>   :  <xsl:value-of select="../@type"/> 
		</xsl:message>   -->
        </xsl:when>
	<!-- ========================================================== AoS type 3  =================================================================================== -->
	<xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and @type='dynamic'">
          if (this.<xsl:value-of select = "@name"/> != null &amp;&amp; this.<xsl:value-of select = "@name"/>[0] != null) 
	  {
            int idObjSingleTime = UALLowLevel.beginObject(expIdx, -1, 0, path + "/" + strNodePath + "<xsl:value-of select = "@name"/>", imas.TIMED);
            int idAoS3Obj = UALLowLevel.beginObject(expIdx, idObjSingleTime, 0, "ALLTIMES", imas.TIMED);
             this.<xsl:value-of select = "@name"/>[0].putInObject(expIdx, idAoS3Obj, "", 0);
	     
	    double sliceTime =  <xsl:value-of select="ancestor::IDS[1]/@name"/>_IDSBase.getIdsTime().getElementAt(0);
            UALLowLevel.putObjectInObject(expIdx, idObjSingleTime, "ALLTIMES", 0, idAoS3Obj);
            UALLowLevel.putObjectSlice(expIdx, path, strNodePath + "<xsl:value-of select = "@name"/>", sliceTime, idObjSingleTime);
         
                      
	    strTimeBasePath = strNodePath + "<xsl:value-of select = "@name"/>/time";
	<!--    //strTimeBasePath = "time"; -->
            UALLowLevel.putDoubleSlice(expIdx, path, strTimeBasePath, strTimeBasePath, sliceTime, sliceTime);
            //if (ual_debug.equals("yes")) System.out.println("Put " + <xsl:value-of select="@path"/>/time");
          }
	</xsl:when>

  <xsl:when test="(@data_type='str_type' or @data_type='STR_0D'
	                 or @data_type='int_type' or @data_type='INT_0D'
			 or @data_type='flt_type' or @data_type='FLT_0D') and not(ancestor::field[@data_type='struct_array' and @maxoccur='unbounded']) ">
		         <xsl:message terminate="no"> Error! Scalar cannot be of DYNAMIC type: <xsl:value-of select="ancestor::IDS/@name"/>:<xsl:value-of select="@path"/>:<xsl:value-of select="@data_type"/>:<xsl:value-of select="@type"/> </xsl:message>
  </xsl:when>
  
	<xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
	<xsl:call-template name="putdynamicSlice">
	<xsl:with-param name="Function">Vect1DDoubleSlice</xsl:with-param>
	</xsl:call-template>
	</xsl:when>

        <xsl:when test="@name='vecdbl_type'">
	<xsl:call-template name="putdynamicSlice"> 
	<xsl:with-param name="Function">Vect1DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
	<xsl:call-template name="putdynamicSlice">
	<xsl:with-param name="Function">Vect1DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
 	<xsl:call-template name="putdynamicSlice">
	<xsl:with-param name="Function">Vect1DStringSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_2D'">
	<xsl:call-template name="putdynamicSlice"> 
	<xsl:with-param name="Function">Vect2DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='INT_2D'">
	<xsl:call-template name="putdynamicSlice"> 
	<xsl:with-param name="Function">Vect2DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='matdbl_type'">
	<xsl:call-template name="putdynamicSlice">
	<xsl:with-param name="Function">Vect2DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_3D'">
	<xsl:call-template name="putdynamicSlice">
	<xsl:with-param name="Function">Vect3DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='INT_3D'">
	<xsl:call-template name="putdynamicSlice">
	<xsl:with-param name="Function">Vect3DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array3ddbl_type'">
	<xsl:call-template name="putdynamicSlice"> 
	<xsl:with-param name="Function">Vect3DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_4D'">
	<xsl:call-template name="putdynamicSlice"> 
	<xsl:with-param name="Function">Vect4DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array4dint_type'">
	<xsl:call-template name="putdynamicSlice"> 
	<xsl:with-param name="Function">Vect4DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array4ddbl_type'">
	<xsl:call-template name="putdynamicSlice">
	<xsl:with-param name="Function">Vect4DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_5D'">
	<xsl:call-template name="putdynamicSlice"> 
	<xsl:with-param name="Function">Vect5DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array5dint_type'">
	<xsl:call-template name="putdynamicSlice"> 
	<xsl:with-param name="Function">Vect5DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array5ddbl_type'">
	<xsl:call-template name="putdynamicSlice"> 
	<xsl:with-param name="Function">Vect5DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@data_type='FLT_6D'">
	<xsl:call-template name="putdynamicSlice"> 
	<xsl:with-param name="Function">Vect6DDoubleSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array6dint_type'">
	<xsl:call-template name="putdynamicSlice"> 
	<xsl:with-param name="Function">Vect6DIntSlice</xsl:with-param>
	</xsl:call-template>
        </xsl:when>

        <xsl:when test="@name='array6ddbl_type'">
	<xsl:call-template name="putdynamicSlice"> 
	<xsl:with-param name="Function">Vect6DDoubleSlice</xsl:with-param>
	</xsl:call-template>
       </xsl:when>

	<xsl:otherwise>
// Put <xsl:value-of select="@path"/> : PROBLEM : UNIDENTIFIED TYPE !!! <!-- for comment only -->
	</xsl:otherwise>

      </xsl:choose>
   </xsl:if>
</xsl:template>








<xsl:template name ="printstrTimeBasePath">
<xsl:if test="@type = 'dynamic'">
<xsl:choose>
<xsl:when test="contains(@coordinate7,'time')"> <xsl:value-of select="translate(replace(@coordinate7,'(itime)',''),'()','')"/></xsl:when> <!-- We remove the (itime) pattern from the coordinate attribute in IDSDef, which is documentation-oriented -->
<xsl:when test="contains(@coordinate6,'time')"> <xsl:value-of select="translate(replace(@coordinate6,'(itime)',''),'()','')"/></xsl:when>
<xsl:when test="contains(@coordinate5,'time')"> <xsl:value-of select="translate(replace(@coordinate5,'(itime)',''),'()','')"/></xsl:when>
<xsl:when test="contains(@coordinate4,'time')"> <xsl:value-of select="translate(replace(@coordinate4,'(itime)',''),'()','')"/></xsl:when>
<xsl:when test="contains(@coordinate3,'time')"> <xsl:value-of select="translate(replace(@coordinate3,'(itime)',''),'()','')"/></xsl:when>
<xsl:when test="contains(@coordinate2,'time')"> <xsl:value-of select="translate(replace(@coordinate2,'(itime)',''),'()','')"/></xsl:when>
<xsl:when test="contains(@coordinate1,'time')"> <xsl:value-of select="translate(replace(@coordinate1,'(itime)',''),'()','')"/></xsl:when>
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
<xsl:choose><xsl:when test="@type = 'dynamic'"> <xsl:value-of select="'strTimeBasePath.trim()'"/> </xsl:when> <xsl:otherwise>""</xsl:otherwise> </xsl:choose>
</xsl:template>

<xsl:template name ="putdynamic">
<xsl:param name="Function" />
<!--  ================================================ -->

	<xsl:if test="@type='dynamic'">
		<xsl:variable name = "NodestrTimeBasePath">
				<xsl:call-template name="printstrTimeBasePath"/>
		</xsl:variable>		
		<xsl:choose>
			<xsl:when test="$NodestrTimeBasePath='time'">
	//time coordinate point to IDSRoot/time so no difference between homo- and heterogeneous mode
	strTimeBasePath = "time" ;
	time = <xsl:value-of select="ancestor::IDS[1]/@name"/>_IDSBase.getIdsTime();
	      		</xsl:when>
	      		<xsl:otherwise>  
   	if ( <xsl:value-of select="ancestor::IDS[1]/@name"/>_IDSBase.isHomogeneous())
	{
	  strTimeBasePath = "time" ;
	  time = <xsl:value-of select="ancestor::IDS[1]/@name"/>_IDSBase.getIdsTime();
	}
	else 
	{
	  strTimeBasePath = strNodePath + "time" ;
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
	if(this.<xsl:value-of select="@name"/> != null)
	{
          UALLowLevel.put<xsl:value-of select="$Function"/>(expIdx,path, strNodePath + "<xsl:value-of select="@name"/>", "time", this.<xsl:value-of select="@name"/>, idsGlobalTime.getElementAt(0));
	}
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
            this.<xsl:value-of select="@name"/> = UALLowLevel.get<xsl:value-of select="$Function"/>(expIdx, path, strNodePath + &quot;<xsl:value-of select="@name"/>&quot;);
          } catch(Exception exc){this.<xsl:value-of select="@name"/> = <xsl:value-of select="$Exception"/>;}
</xsl:template>

<xsl:template name ="getVariableSlice">
<xsl:param name="variable_path" />
<xsl:param name="mds_path" />
<xsl:param name="Function" />
<xsl:param name="SliceFunction" />
<xsl:param name="number" />
<xsl:param name="Exception" />
          try {
	    Vect1DInt dimVect = UALLowLevel.getDimension(expIdx, path, strNodePath + &quot;<xsl:value-of select="@name"/>&quot;);
	    if (dimVect.getElementAt(0) > 0) {
              this.<xsl:value-of select="@name"/> = new <xsl:value-of select="$Function"/>(<xsl:call-template name="printDimensions"><xsl:with-param name="number" select="$number"/></xsl:call-template>);
              this.<xsl:value-of select="@name"/>.setElementAt(0, UALLowLevel.get<xsl:value-of select="$SliceFunction"/>Slice(expIdx, path, strNodePath + &quot;<xsl:value-of select="@name"/>&quot;, <xsl:call-template name="printPathoftime"/>, time, interpolMode));
            }
          } catch(Exception exc){this.<xsl:value-of select="@name"/> = <xsl:value-of select="$Exception"/>;}
</xsl:template>

<xsl:template name ="getStringSlice">
<xsl:param name="variable_path" />
<xsl:param name="mds_path" />
          try {
            this.<xsl:value-of select="@name"/> = new Vect1DString(1);
            this.<xsl:value-of select="@name"/>.setElementAt(0, UALLowLevel.getStringSlice(expIdx, path, strNodePath + &quot;<xsl:value-of select="@name"/>&quot;, <xsl:call-template name="printPathoftime"/>, time, interpolMode));
          } catch(Exception exc){this.<xsl:value-of select="@name"/> = null;}
</xsl:template>

<xsl:template name ="printDimensions">
<xsl:param name="number" />
<!-- *** printDimensions <xsl:value-of select="$number" /> **** -->
	<xsl:choose>
		<xsl:when test="$number='0'">1</xsl:when>
		<xsl:when test="$number='1'">dimVect.getElementAt(0),1</xsl:when>
		<xsl:when test="$number='2'">dimVect.getElementAt(0),dimVect.getElementAt(1),1</xsl:when>
		<xsl:when test="$number='3'">dimVect.getElementAt(0),dimVect.getElementAt(1),dimVect.getElementAt(2),1</xsl:when>
		<xsl:when test="$number='4'">dimVect.getElementAt(0),dimVect.getElementAt(1),dimVect.getElementAt(2),dimVect.getElementAt(3),1</xsl:when>
		<xsl:when test="$number='5'">dimVect.getElementAt(0),dimVect.getElementAt(1),dimVect.getElementAt(2),dimVect.getElementAt(3),dimVect.getElementAt(4),1</xsl:when>
		<xsl:when test="$number='6'">dimVect.getElementAt(0),dimVect.getElementAt(1),dimVect.getElementAt(2),dimVect.getElementAt(3),dimVect.getElementAt(4),dimVect.getElementAt(5),1</xsl:when>
		<xsl:when test="$number='7'">dimVect.getElementAt(0),dimVect.getElementAt(1),dimVect.getElementAt(2),dimVect.getElementAt(3),dimVect.getElementAt(4),dimVect.getElementAt(5),dimVect.getElementAt(6),1</xsl:when>
		<xsl:otherwise></xsl:otherwise>
	</xsl:choose>
</xsl:template>


<xsl:template name ="printAosRelativePath">
    <xsl:variable name="AoSPath" select="ancestor::field[@data_type='struct_array'][1]/@path"/>
    <xsl:variable name="elementPath" select="@path"/>

    <xsl:value-of select="replace($elementPath,concat($AoSPath,'/'),'')"/>
</xsl:template>


</xsl:stylesheet>
