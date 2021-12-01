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
    <xsl:param name="SYSTEM" as="xs:string" required="yes"/>
    <xsl:param name="DD_VERSION" as="xs:string" required="yes"/>
    <xsl:param name="AL_VERSION" as="xs:string" required="yes"/>
    
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
        import imasjava.wrapper.Wrapper;
        
        public class imas {
        static {
        String libpath = System.getenv("IMAS_PREFIX");
        String imasversion = System.getenv("IMAS_VERSION");
        String ualversion = System.getenv("UAL_VERSION");
        if (libpath == null) {
        System.err.println("IMAS library not set up in the environment. (IMAS_PREFIX missing)");
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
        <xsl:choose><xsl:when test="$SYSTEM = 'Linux'">
            libpath = libpath + "/lib";
            String imas_library = libpath + "/libimas-java-binding.so";
          </xsl:when><xsl:otherwise>
            String imas_library = libpath + "/javainterface/lib/libimas-java-binding.dll";
          </xsl:otherwise></xsl:choose>
        File f = new File(imas_library);
        if (!f.exists()) {
        System.err.println("IMAS library not set up in the environment. (libimas-java-binding.so missing)");
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
        * Opens database instance.
        * @param uri, URI of the IMAS Data Entry
        * @param mode, opening mode {OPEN_PULSE, FORCE_OPEN_PULSE, CREATE_PULSE, FORCE_CREATE_PULSE}
        * @exception UALException is thrown if the database cannot be open.
        **/
        
        public static int open(String uri, int mode) throws UALException
        {
        int pulseCtx;
        
        try{ 
        pulseCtx = Wrapper.ualBeginDataEntryAction(uri, mode);
        } catch(Exception exc) {
        throw new UALException(  "[ual_begin_dataentry_action]: Error opening data entry with URI: " + uri + ", using mode:" + mode + ":\n" + exc.getMessage()  );
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
        * Opens database instance.
        *
        * @param shot Shot number.
        * @param run Run Number.
        * @param user User name
        * @param tokamak Machine name
        * @param version Database version
        * @return the database index to be used in subsequent get/put calls
        * @exception UALException is thrown if the database cannot be open.
        */
        public static int openEnv(int shot, int run, String user, String tokamak, String version)
        throws UALException {
        return openEnv(shot, run, user, tokamak, version, LowLevel.MDSPLUS_BACKEND);
        }
        
        /**
        * Opens database instance.
        *
        * @param shot Shot number.
        * @param run Run Number.
        * @param user User name
        * @param tokamak Machine name
        * @param version Database version
        * @param options Options passed down to LowLevel
        * @return the database index to be used in subsequent get/put calls
        * @exception UALException is thrown if the database cannot be open.
        */
        public static int openEnv(
        int shot, int run, String user, String tokamak, String version, String options)
        throws UALException {
        return openEnv(shot, run, user, tokamak, version, LowLevel.MDSPLUS_BACKEND, options);
        }
        
        /**
        * Opens database instance.
        *
        * @param backendType Type of the backend to be used
        * @param shot Shot number.
        * @param run Run Number.
        * @param user User name
        * @param tokamak Name of the machine
        * @param version Database version
        * @param backendType Type of the backend to be used
        * @return the database index to be used in subsequent get/put calls
        * @exception UALException is thrown if the database cannot be open.
        */
        public static int openEnv(
        int shot, int run, String user, String tokamak, String version, int backendType)
        throws UALException {
        return openEnv(shot, run, user, tokamak, version, backendType, "");
        }
        
        
        
        /**
        * Opens database instance.
        * @param backendType Type of the backend to be used
        * @param name Name of the database (by convention imas).
        * @param shot Shot number.
        * @param run Run Number.
        * @param user User name
        * @param tokamak Name of the machine
        * @param version Database version
        * @param backendType Type of the backend to be used
        * @param options Options passed down to LowLevel
        * @return the database index to be used in subsequent get/put calls
        * @exception UALException is thrown if the database cannot be open.
        **/
        
        public static int openEnv(int shot, int run, String user, String tokamak, String version, int backendType, String options) throws UALException
        {
        int pulseCtx;
        
        try{ 
        String uri = Wrapper.ualBuildUriFromLegacyParameters(backendType, shot, run, user, tokamak, version, options);
        pulseCtx = Wrapper.ualBeginDataEntryAction(uri, LowLevel.OPEN_PULSE);
        } catch(Exception exc) {
        throw new UALException(  "[ual_begin_dataentry_action]: Error creating pulse file: " + user + "/" + tokamak + "/" + version + "/" + shot + "/" + run + "/" + backendType + ":\n" + exc.getMessage()  );
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
        * Creates a new database instance.
        *
        * @param shot Shot number.
        * @param run Run Number.
        * @param user User name
        * @param tokamak Name of the machine
        * @param version Database version
        * @return the database index to be used in subsequent get/put calls
        * @exception UALException is thrown if the database cannot be open.
        */
        public static int createEnv(int shot, int run, String user, String tokamak, String version)
        throws UALException {
        return createEnv(shot, run, user, tokamak, version, LowLevel.MDSPLUS_BACKEND);
        }
        
        /**
        * Creates a new database instance.
        *
        * @param shot Shot number.
        * @param run Run Number.
        * @param user User name
        * @param tokamak Name of the machine
        * @param version Database version
        * @param options Options that are passed down to LowLevel
        * @return the database index to be used in subsequent get/put calls
        * @exception UALException is thrown if the database cannot be open.
        */
        public static int createEnv(
        int shot, int run, String user, String tokamak, String version, String options)
        throws UALException {
        return createEnv(shot, run, user, tokamak, version, LowLevel.MDSPLUS_BACKEND, options);
        }
        
        
        /**
        *Creates a new database instance.
        * @param shot Shot number.
        * @param run Run Number.
        * @param user User name
        * @param tokamak Name of the machine
        * @param version Database version
        * @param backendType Type of the backend to be use (take a look inside wrapper/LowLevel)
        * @return the database index to be used in subsequent get/put calls
        * @exception UALException is thrown if the database cannot be open.
        **/
        
        public static int createEnv(int shot, int run, String user, String tokamak, String version, int backendType) throws UALException
        {
        return createEnv(shot, run, user, tokamak, version, backendType, "");
        }
        
        /**
        *Creates a new database instance.
        * @param shot Shot number.
        * @param run Run Number.
        * @param user User name
        * @param tokamak Name of the machine
        * @param version Database version
        * @param backendType Type of the backend to be use (take a look inside wrapper/LowLevel)
        * @return the database index to be used in subsequent get/put calls
        * @exception UALException is thrown if the database cannot be open.
        **/
        
        public static int createEnv(int shot, int run, String user, String tokamak, String version, int backendType, String options) throws UALException
        {
        int pulseCtx = -1;
        
        try { 
        String uri = Wrapper.ualBuildUriFromLegacyParameters(backendType, shot, run, user, tokamak, version, options);
        pulseCtx = Wrapper.ualBeginDataEntryAction(uri, LowLevel.FORCE_CREATE_PULSE);
        } catch(Exception exc){
        throw new UALException("[ual_begin_uri_action]: Error creating pulse file: " + user + "/" + tokamak + "/" + version + "/"+ shot + "/" + run + "/" + backendType + ":\n" + exc.getMessage()  );
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
        close(imas.pulseCtx);
        }
        
        static public void close(int refIdx, String name, int shot, int run) throws UALException{
        System.err.println(  "WARNING:\n"
        + "\"int close(int refIdx, String name, int shot, int run)\"  is DEPRECATED.\n"
        + "Please use \"close()\" instead");
        close(refIdx);
        }
        
        static public void close(int refIdx) throws UALException
        {
        try{
        LowLevel.ual_close_pulse(refIdx, LowLevel.CLOSE_PULSE, "");
        } catch (Exception exc) {
        throw new UALException("[ual_close_pulse]: Error closing pulse file: " + imas.user + "/" + imas.tokamak + "/" + imas.version + "/"+ imas.shot + "/" + imas.run + ":\n" + exc.getMessage()  );
        } finally {
        if(refIdx >= 0)
        LowLevel.ual_end_action(refIdx);
        }
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
        
        static public int readIdsTimeMode(int pulseCtx, String idsFullName) throws UALException
        {
        int idsTimeMode = LowLevel.IDS_TIME_MODE_UNKNOWN;
        int ctx = -1;
        
        // Open  ctx
        ctx = LowLevel.ual_begin_global_action(pulseCtx, idsFullName, LowLevel.READ_OP);
        
        try
        {
        idsTimeMode = Wrapper.readData(ctx, "ids_properties/homogeneous_time", "", idsTimeMode);
        }
        finally
        {
        LowLevel.ual_end_action(ctx);
        }
        
        switch(idsTimeMode)
        {
        case LowLevel.IDS_TIME_MODE_UNKNOWN:     
        case LowLevel.IDS_TIME_MODE_HETEROGENEOUS: 
        case LowLevel.IDS_TIME_MODE_HOMOGENEOUS:   
        case LowLevel.IDS_TIME_MODE_INDEPENDENT:   
        break;
        
        default: 
        throw new UALException("ERROR! IDS '<xsl:value-of select="@name"/>': time dependency mode ('ids_properties/homogeneous_time') set to unknown value!");
        
        }
        
        return idsTimeMode;
        }
        
        static public String timeModeToString( int idsTimeMode )
        {
        String strTimeMode;
        
        switch(idsTimeMode)
        {
        case LowLevel.IDS_TIME_MODE_UNKNOWN:     
        strTimeMode = "UNKNOWN";
        break;
        case LowLevel.IDS_TIME_MODE_HETEROGENEOUS: 
        strTimeMode = "HETEROGENEOUS";
        break;
        case LowLevel.IDS_TIME_MODE_HOMOGENEOUS:   
        strTimeMode = "HOMOGENEOUS";
        break;
        case LowLevel.IDS_TIME_MODE_INDEPENDENT:
        strTimeMode = "INDEPENDENT";
        break;
        default: 
        strTimeMode = "UNKNOWN";
        break;
        
        }
        return strTimeMode;
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
        
        import java.util.List;
        import java.util.ArrayList;
        import java.util.Collections;
        
        import imasjava.wrapper.*;
        import imasjava.*;
        
        public class <xsl:value-of select="@name"/>_IDSBase
        {
        
        private static final int maxOccurences = <xsl:value-of select="@maxoccur"/>;
        private static final String IDS_NAME    = "<xsl:value-of select="@name"/>";
        
        private static int idsTimeMode = LowLevel.IDS_TIME_MODE_UNKNOWN;
        private static Vect1DDouble idsTime = null;
        private static int pulseCtx = -1;
        
        public static int getMaxOccurences() {
        return maxOccurences;
        }
        
        public static String getIdsName() 
        {
        return <xsl:value-of select="@name"/>_IDSBase.IDS_NAME;
        }
        /*    
        static private void setHomogeneous(boolean isHomogeneous)
        {
        <xsl:value-of select="@name"/>_IDSBase.isHomogeneous = isHomogeneous;
        }
        
        static boolean isHomogeneous()
        {
        return <xsl:value-of select="@name"/>_IDSBase.isHomogeneous;
        }
        
        
        */
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
            int iOccurrence = 0;
            
            /*     System.err.println("WARNING:\n"
            + "\"put(int pulseCtx, String idsFullName, imas.<xsl:value-of select="@name"/> ids) \"  is DEPRECATED.\n"
            + "Please use \"put()\" instead");
            */
            //<xsl:value-of select="@name"/>_IDSBase.setIdsTime(ids.time);
            
            if(!<xsl:value-of select="@name"/>_IDSBase.IDS_NAME.equals(idsFullName))
            {
            String tokens[] = idsFullName.split("/");
            iOccurrence = Integer.parseInt(tokens[1]);          
            }
            
            ids.setPulseCtx(pulseCtx);
            ids.put(iOccurrence);
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
            
            
            int idsTimeMode = this.ids_properties.homogeneous_time;
            
            if(iOccurrence > 0)
            idsFullName = idsFullName + "/" + iOccurrence;
            
            if(idsTimeMode == LowLevel.IDS_TIME_MODE_UNKNOWN)
            {
            System.err.println("Warning: IDS <xsl:value-of select="@name"/> is found to be EMPTY (homogeneous_time undefined). PUT quits with no action.");
            return;
            }
            
            
            
            if(idsTimeMode == LowLevel.IDS_TIME_MODE_HOMOGENEOUS &amp;&amp; (this.time == null || (this.time != null  &amp;&amp; this.time.getDim() &lt; 1)))
            {
            throw new UALException("ERROR: Time vector of homogeneous IDS '<xsl:value-of select="@name"/>' cannot be EMPTY!.");
            }
            
            try{
            // Open put context
            ctx = LowLevel.ual_begin_global_action(pulseCtx, idsFullName, LowLevel.WRITE_OP);
            
            this.deleteRootFields(ctx);
            this.putRootFields(ctx, idsTimeMode, idsFullName);
            }
            finally {
            if(ctx >= 0)
            LowLevel.ual_end_action(ctx);
            }
            }
            
            public void putRootFields(int ctx, int idsTimeMode, String idsFullName)  throws UALException
            {
            int aosCtx = -1;
            int arraySize = -1;
            
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
            
            int iOccurrence = 0;
            /*
            System.err.println("WARNING:\n"
            + "\"putSlice(int pulseCtx, String idsFullName, imas.<xsl:value-of select="@name"/> ids) \"  is DEPRECATED.\n"
            + "Please use \"putSlice()\" instead");
            
            */
            if(!<xsl:value-of select="@name"/>_IDSBase.IDS_NAME.equals(idsFullName))
            {
            String tokens[] = idsFullName.split("/");
            iOccurrence = Integer.parseInt(tokens[1]);          
            }
            
            ids.setPulseCtx(pulseCtx);
            ids.putSlice(iOccurrence);
            
            }
            public void putSlice(int iOccurrence) throws UALException
            {
            int pulseCtx = this.pulseCtx;
            int ctx = -1;
            int aosCtx = -1;
            int arraySize = -1;
            String idsFullName = <xsl:value-of select="@name"/>_IDSBase.IDS_NAME;
            int storedTimeMode = LowLevel.IDS_TIME_MODE_UNKNOWN;
            int idsTimeMode = this.ids_properties.homogeneous_time;
            
            if(iOccurrence > 0)
            idsFullName = idsFullName + "/" + iOccurrence;
            
            if(idsTimeMode == LowLevel.IDS_TIME_MODE_UNKNOWN)
            {
            System.err.println("Warning: IDS <xsl:value-of select="@name"/> is found to be EMPTY (homogeneous_time undefined). PUTSLICE quits with no action.");
            return;
            }
            
            if (idsTimeMode == LowLevel.IDS_TIME_MODE_INDEPENDENT) 
            {
            System.err.println("Warning: IDS '<xsl:value-of select="@name"/>' time mode 'independent'. PUTSLICE quits with no action.");
            return;
            }
            
            if(idsTimeMode == LowLevel.IDS_TIME_MODE_HOMOGENEOUS &amp;&amp; (this.time == null || (this.time != null  &amp;&amp; this.time.getDim() &lt; 1)))
            {
            throw new UALException("ERROR: Time vector of homogeneous IDS '<xsl:value-of select="@name"/>' cannot be EMPTY!.");
            }
            
            
            /***   Checking homogeneous_time read from file   ***/
            storedTimeMode = imas.readIdsTimeMode(pulseCtx, idsFullName);
            
            
            // adding slice to an empty IDS
            if( storedTimeMode ==  LowLevel.IDS_TIME_MODE_UNKNOWN)
            {
            System.out.println("Warning: Slice is being added to an empty IDS '<xsl:value-of select="@name"/>'. PUT is called to save time independent data.");
            this.put(iOccurrence);
            return ;
            }
            
            // time mode conflict
            if( storedTimeMode != idsTimeMode)
            {
            throw new UALException("ERROR! IDS '<xsl:value-of select="@name"/>': time dependency mode ('" + imas.timeModeToString(idsTimeMode) + "') differs from value stored in IDS ('" + imas.timeModeToString(storedTimeMode) + "')!");
            }
            
            
            /***   Put slice   ***/
            
            try{
            // Open putSlice context
            ctx = LowLevel.ual_begin_slice_action(pulseCtx, idsFullName, LowLevel.WRITE_OP, LowLevel.UNDEFINED_TIME, LowLevel.UNDEFINED_INTERP);
            
            this.putSliceRootFields(ctx, idsTimeMode, idsFullName);
            }
            finally {
            if(ctx >= 0)
            LowLevel.ual_end_action(ctx);
            }
            }
            
            
            public void putSliceRootFields(int ctx, int idsTimeMode, String idsFullName) throws UALException
            {
            int aosCtx = -1;
            int arraySize = -1;
            
            String strTimeBasePath;
            String strNodePath;
            
            <xsl:apply-templates select="field" mode="PUT_SINGLE">
              <xsl:with-param name="dynamic_only" select="'yes'"/>
            </xsl:apply-templates>
            }
            
            
            
            
            
            
            /* ------------------------------------------------------------------------------------------------------------ */
            /* -----------------------------------       GET       ------------------------------------------------------- */  
            /* ------------------------------------------------------------------------------------------------------------ */
            public static imas.<xsl:value-of select="@name"/>  get(int expIdx, String idsFullName)  throws UALException
            {
            imas.<xsl:value-of select="@name"/> ids = new imas.<xsl:value-of select="@name"/> ();
            int iOccurrence = 0;
            
            /*      System.err.println("WARNING:\n"
            + "\"get(int expIdx, String path)) \"  is DEPRECATED.\n"
            + "Please use \"get()\" instead");
            */
            if(!<xsl:value-of select="@name"/>_IDSBase.IDS_NAME.equals(idsFullName))
            {
            String tokens[] = idsFullName.split("/");
            iOccurrence = Integer.parseInt(tokens[1]);          
            }
            ids.setPulseCtx(expIdx);
            ids.get(iOccurrence);
            return ids;
            }
            
            
            public void get(int iOccurrence)  throws UALException
            {
            String strNodePath = "";
            int pulseCtx = this.pulseCtx;
            int ctx = -1;
            String idsFullName = <xsl:value-of select="@name"/>_IDSBase.IDS_NAME;
            
            
            int idsTimeMode = LowLevel.IDS_TIME_MODE_UNKNOWN;
            
            if(iOccurrence > 0)
            idsFullName = idsFullName + "/" + iOccurrence;
            
            idsTimeMode = imas.readIdsTimeMode(pulseCtx, idsFullName);
            
            this.reset();
            try{
            // Open get context
            ctx = LowLevel.ual_begin_global_action(pulseCtx, idsFullName, LowLevel.READ_OP);
            this.getRootFields(ctx, idsTimeMode);
            }
            finally {
            if(ctx >= 0)
            LowLevel.ual_end_action(ctx);
            }
            }
            public void getRootFields(int ctx, int idsTimeMode)  throws UALException
            {
            int aosCtx = -1;
            int arraySize = -1;
            
            String strNodePath = "";
            String strTimeBasePath = "";
            
            <xsl:apply-templates select="field" mode="GET_SINGLE"/>
            }
            
            
            /* ------------------------------------------------------------------------------------------------------------ */
            /* -----------------------------------       GET  SLICE       ------------------------------------------------- */ 
            /* ------------------------------------------------------------------------------------------------------------ */
            
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
            public static imas.<xsl:value-of select="@name"/>  getSlice(int pulseCtx, String idsFullName, double time, int interpolMode) throws UALException
            {
            imas.<xsl:value-of select="@name"/> ids = new imas.<xsl:value-of select="@name"/> ();
            int iOccurrence = 0;
            /*
            System.err.println("WARNING:\n"
            + "\"getSlice(int pulseCtx, String idsFullName, double time, int interpolMode)  \"  is DEPRECATED.\n"
            + "Please use \"getSlice(int iOccurrence, double time, int interpolMode)\" instead");
            
            */
            if(!<xsl:value-of select="@name"/>_IDSBase.IDS_NAME.equals(idsFullName))
            {
            String tokens[] = idsFullName.split("/");
            iOccurrence = Integer.parseInt(tokens[1]);          
            }
            
            ids.setPulseCtx(pulseCtx);
            ids.getSlice(iOccurrence, time, interpolMode);
            
            return ids;
            
            }
            public void getSlice(int iOccurrence, double time, int interpolMode) throws UALException
            {
            int pulseCtx = this.pulseCtx;
            int ctx = -1;
            String idsFullName = <xsl:value-of select="@name"/>_IDSBase.IDS_NAME;
            
            int idsTimeMode = LowLevel.IDS_TIME_MODE_UNKNOWN;
            
            if(iOccurrence > 0)
            idsFullName = idsFullName + "/" + iOccurrence;
            
            idsTimeMode = imas.readIdsTimeMode(pulseCtx, idsFullName);
            
            this.reset();
            try{
            // Open putSlice context
            ctx = LowLevel.ual_begin_slice_action(pulseCtx, idsFullName, LowLevel.READ_OP, time, interpolMode);
            
            this.getSliceRootFields(ctx, idsTimeMode);
            }
            finally {
            if(ctx >= 0)
            LowLevel.ual_end_action(ctx);
            }
            }
            
            
            public void getSliceRootFields(int ctx, int idsTimeMode) throws UALException
            {
            int aosCtx = -1;
            int arraySize = -1;
            
            String strTimeBasePath;
            String strNodePath;
            
            <xsl:apply-templates select="field" mode="GET_SINGLE"/>
            
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
            int iOccurrence = 0;
            
            if(!<xsl:value-of select="@name"/>_IDSBase.IDS_NAME.equals(idsFullName))
            {
            String tokens[] = idsFullName.split("/");
            iOccurrence = Integer.parseInt(tokens[1]);          
            }
            
            ids.setPulseCtx(pulseCtx);
            ids.delete(iOccurrence);
            
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
            if(ctx >= 0)
            LowLevel.ual_end_action(ctx);
            }
            }
            
            
            private void deleteRootFields(int ctx) throws UALException
            {  
            String strNodePath = "";
            
            <xsl:apply-templates select = "field" mode = "DELETE"/>
            }
            /* ------------------------------------------------------------------------------------------------------------ */
            /* -----------------------------------       RESET       ----------------------------------------------------- */  
            /* ----------------------------------------------------------------------------------------------------------- */
            private void reset() 
            {  
            <xsl:apply-templates select = "field" mode = "RESET"/>
            }
            
          </xsl:otherwise>
        </xsl:choose>
        
        
        
        
        /* ------------------------------------------------------------------------------------------------------------ */
        /* -----------------------------------       APPEND     ------------------------------------------------------- */  
        /* ------------------------------------------------------------------------------------------------------------ */
        //  Field(s) to be appended: <xsl:value-of select="descendant-or-self::field[@appendable_by_appender_actor='yes']/@path"/>
        /* ------------------------------------------------------------------------------------------------------------ */
        public static imas.<xsl:value-of select="@name"/> appendIDSes(imas.<xsl:value-of select="@name"/>[] idsArray)  throws UALException
        {
        <xsl:choose>
          
          <xsl:when test="count(descendant-or-self::field[@appendable_by_appender_actor='yes' ]) lt 1">
            throw new UALException("IDS '<xsl:value-of select="@name"/>' has no fields that could be appended!");
          </xsl:when>
          
          <xsl:otherwise>
            int listSize = -1;
            imas.<xsl:value-of select="@name"/> outIDS = new imas.<xsl:value-of select="@name"/> ();
            
            //Sanity check - array size 
            if(idsArray.length &lt; 1)
            throw new UALException("IDS '<xsl:value-of select="@name"/>': appendIDSes called with an empty array of IDSes!");
            
            //homogeneous time check
            for(imas.<xsl:value-of select="@name"/> inIDS : idsArray)
            {
            if (inIDS.ids_properties.homogeneous_time != 1)
            throw new UALException("Heterogeneous IDSes cannot be appended!");
            }
            outIDS.ids_properties.homogeneous_time = 1;
            outIDS.time = idsArray[0].time;
            <xsl:for-each select="descendant-or-self::field[@appendable_by_appender_actor='yes' ]">
              
              <xsl:variable name="idsName" select="ancestor::IDS/@name"/>
              <xsl:variable name="fieldName" select="translate(@path,'/','_')"/>
              <xsl:variable name="className" select="concat('imas.', $idsName,'.', replace(@path,'/','Class.'), 'Class')"/>
              
              /* - - - - - - - - - - - - - - - - - - - - *** <xsl:value-of select="@path"/> ***  - - - - - - - - - - - - - - - - - - - - */
              
              List &lt; <xsl:value-of select="$className"/> &gt; <xsl:value-of select="$fieldName"/>List = new ArrayList&lt; <xsl:value-of select="$className"/> &gt;(); 
              <xsl:value-of select="$className"/>[] <xsl:value-of select="$fieldName"/>Array;
              for(imas.<xsl:value-of select="$idsName"/> inIDS : idsArray)
              {
              <xsl:value-of select="$fieldName"/>Array = inIDS.<xsl:value-of select="translate(@path,'/','.')"/>;
              if(<xsl:value-of select="$fieldName"/>Array != null)
              Collections.addAll(<xsl:value-of select="$fieldName"/>List, <xsl:value-of select="$fieldName"/>Array); 
              }
              
              listSize = <xsl:value-of select="$fieldName"/>List.size();
              if(listSize > 0)
              {
              <xsl:value-of select="$fieldName"/>Array =  new <xsl:value-of select="$className"/>[listSize] ;
              outIDS.<xsl:value-of select="translate(@path,'/','.')"/> = <xsl:value-of select="$fieldName"/>List.toArray(<xsl:value-of select="$fieldName"/>Array);
              }
            </xsl:for-each>
            
            return outIDS;
          </xsl:otherwise>
        </xsl:choose>
        }
        
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
        
        
        <xsl:apply-templates select=".//field[@data_type='structure' or @data_type='struct_array']"/>
        
        }
      </xsl:result-document>
    </xsl:template>
    
    
    
    <xsl:template match="field[@data_type='structure' or @data_type='struct_array']">
      <xsl:variable name="this-name" select="@name"/>
      <xsl:variable name="this-type" select="@data_type"/>
      
      <xsl:variable name="this-cpo-type" select="ancestor::IDS/@name"/>
      
      
      <xsl:apply-templates select="." mode="CLASS"/> 
      
      
      
    </xsl:template>
    
    
    <!-- ====================================================================================================================================================================== -->
    <!-- ======================================================================================================================================================================= -->
    <!-- ================================================================== SUBCLASS == -=================== =================================================================== -->
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
      public void put(int ctx, int idsTimeMode, String idsFullName)  throws UALException
      {
      String strTimeBasePath = null;
      String strNodePath = null;
      int arraySize = -1;
      int aosCtx = -1;
      
      <xsl:apply-templates select="field" mode="PUT_SINGLE">
        <xsl:with-param name="dynamic_only" select="'no'"/>
      </xsl:apply-templates>
      }       
      
      <xsl:if test="descendant-or-self::field[@type='dynamic'] or ancestor::field[@type='dynamic' and @data_type='struct_array']">
        /* ____________________________________________________________________________________________________________ */
        /*_________________________________       PUT SLICE     _______________________________________________________ */  
        /* ____________________________________________________________________________________________________________  */
        public void putSlice(int ctx, int idsTimeMode, String idsFullName)  throws UALException
        {
        String strTimeBasePath = null;
        String strNodePath = null;
        int arraySize = -1;
        int aosCtx = -1;
        
        
        <xsl:apply-templates select="field" mode="PUT_SINGLE">
          <xsl:with-param name="dynamic_only" select="'yes'"/>
        </xsl:apply-templates>
        }       
        
      </xsl:if>
      
      
      
      /* ____________________________________________________________________________________________________________  */
      /* _________________________________       GET       ________________________________________________________ */  
      /* ____________________________________________________________________________________________________________  */
      public void get(int ctx, int idsTimeMode)   throws UALException
      {
      String strTimeBasePath = null;
      String strNodePath = null;
      int arraySize = -1;
      int aosCtx = -1;
      
      
      
      <xsl:apply-templates select = "field" mode = "GET_SINGLE"/>
      
      }     
      
      
      /* ____________________________________________________________________________________________________________  */
      /* _________________________________       GET  SLICE     ________________________________________________________ */  
      /* ____________________________________________________________________________________________________________  */
      public void getSlice(int ctx, int idsTimeMode)   throws UALException
      {
      String strTimeBasePath = null;
      String strNodePath = null;
      int arraySize = -1;
      int aosCtx = -1;
      
      <xsl:apply-templates select = "field" mode = "GET_SINGLE"/> 
      }   
      
      
      <xsl:if test="@data_type='structure' and not(ancestor::field[@data_type='struct_array'])">
        /* ____________________________________________________________________________________________________________  */
        /* ________________________________________       DELETE     ___________________________________________________ */  
        /* ____________________________________________________________________________________________________________  */ 
        
        public void delete(int ctx) throws UALException
        {
        String strNodePath = null;
        
        <xsl:apply-templates select = "field" mode = "DELETE"/>
        
        }           
        /* ____________________________________________________________________________________________________________  */
        /* ________________________________________       RESET     ___________________________________________________ */  
        /* ____________________________________________________________________________________________________________  */ 
        
        public void reset() 
        {
        <xsl:apply-templates select = "field" mode = "RESET"/>
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
    
    <!-- ============================================================= SUBCLASS == 'structure' type element - END ============================================================== -->
    
    
    
    <!-- ============================================================= SUBCLASS == 'AoS' type element   - END ============================================================== -->
    
    
    <!-- ====================================================================================================================================================================== -->
    <!-- ======================================================================================================================================================================= -->
    <!-- ============================================================              TEMPLATES             =================================================================== -->
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
    
    <!--=====================================================================================================================================-->
    <!--                  reset fields content to default values                                                                                                      -->
    <!--=====================================================================================================================================-->
    
    
    <xsl:template match="field" mode="RESET">
      <xsl:call-template name="COMMENT_NODE"/>
      <xsl:choose>
        <xsl:when test="@data_type='structure'">
          this.<xsl:value-of select="@name"/>.reset();
        </xsl:when>
        <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">
          this.<xsl:value-of select = "@name"/> = LowLevel.EMPTY_INT;
        </xsl:when>
        <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
          this.<xsl:value-of select = "@name"/> = LowLevel.EMPTY_DOUBLE;
        </xsl:when>
        <xsl:when test="@data_type='cpx_type' or @data_type='CPX_0D'">
          this.<xsl:value-of select = "@name"/> = LowLevel.EMPTY_COMPLEX;
        </xsl:when>
        <xsl:when test="
                  @data_type='struct_array'
                  or @data_type='str_type' or @data_type='STR_0D'
                  or @data_type='str_1d_type' or @data_type='STR_1D'
                  or @data_type='flt_1d_type' or @data_type='FLT_1D'
                  or @data_type='cpx_1d_type' or @data_type='CPX_1D'
                  or @data_type='int_1d_type' or @data_type='INT_1D'
                  or @data_type='FLT_2D' or @data_type='INT_2D' or @data_type='CPX_2D'
                  or @data_type='FLT_3D' or @data_type='INT_3D' or @data_type='CPX_3D'
                  or @data_type='FLT_4D' or @data_type='INT_4D' or @data_type='CPX_4D'
                  or @data_type='FLT_5D' or @data_type='INT_5D' or @data_type='CPX_5D'
                  or @data_type='FLT_6D' or @data_type='INT_6D' or @data_type='CPX_6D'">
          this.<xsl:value-of select = "@name"/> = null;
        </xsl:when>
        <xsl:otherwise>
          //Doc GET <xsl:value-of select="@path"/> : PROBLEM : UNIDENTIFIED TYPE !!! <!-- for comment only -->
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
        <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
          if(<xsl:value-of select = "$currentidxpath"/> != LowLevel.EMPTY_DOUBLE)
          System.out.println(<xsl:value-of select = "$currentidxpath"/>);
          else
          System.out.println("Empty");
          System.out.println("");
        </xsl:when>
        
        <xsl:when test="@data_type='cpx_type' or @data_type='CPX_0D'">
          if(<xsl:value-of select = "$currentidxpath"/> != LowLevel.EMPTY_COMPLEX)
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
        
        <xsl:when test="@data_type='cpx_1d_type' or @data_type='CPX_1D' or @data_type='CPX_2D' or @data_type='CPX_3D' or @data_type='CPX_4D' or @data_type='CPX_5D' or @data_type='CPX_6D'">
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
        
        <xsl:when test="@data_type='FLT_4D'">
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
        
        
        
        <xsl:when test="@data_type='FLT_6D'">
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
        <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">
          public double <xsl:value-of select = "@name"/> = LowLevel.EMPTY_DOUBLE;
        </xsl:when>
        
        <xsl:when test="@data_type='cpx_type' or @data_type='CPX_0D'">
          public Complex <xsl:value-of select = "@name"/> = LowLevel.EMPTY_COMPLEX;
        </xsl:when>
        
        <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">
          public Vect1DString <xsl:value-of select = "@name"/>;
        </xsl:when>
        <xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">
          public Vect1DDouble <xsl:value-of select = "@name"/>;
        </xsl:when>
        
        <xsl:when test="@data_type='cpx_1d_type' or @data_type='CPX_1D'">
          public Vect1DComplex <xsl:value-of select = "@name"/>;
        </xsl:when>
        
        <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">
          public Vect1DInt <xsl:value-of select = "@name"/>;
        </xsl:when>
        <!--                       -->
        <xsl:when test="@data_type='FLT_2D'">
          public Vect2DDouble  <xsl:value-of select = "@name"/>;
        </xsl:when>
        <!--                       -->
        <xsl:when test="@data_type='CPX_2D'">
          public Vect2DComplex  <xsl:value-of select = "@name"/>;
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
        <xsl:when test="@data_type='CPX_3D'">
          public Vect3DComplex  <xsl:value-of select = "@name"/>;
        </xsl:when>
        <!--                       -->
        <xsl:when test="@data_type='INT_3D'">
          public Vect3DInt  <xsl:value-of select = "@name"/>;
        </xsl:when>
        <!--                       -->
        <xsl:when test="@data_type='FLT_4D'">
          public Vect4DDouble  <xsl:value-of select = "@name"/>;
        </xsl:when>
        <!--                       -->
        <xsl:when test="@data_type='CPX_4D'">
          public Vect4DComplex  <xsl:value-of select = "@name"/>;
        </xsl:when>
        <!--                       -->
        <xsl:when test="@data_type='FLT_5D'">
          public Vect5DDouble  <xsl:value-of select = "@name"/>;
        </xsl:when>
        <!--                       -->
        <xsl:when test="@data_type='CPX_5D'">
          public Vect5DComplex  <xsl:value-of select = "@name"/>;
        </xsl:when>
        <!--                       -->
        <xsl:when test="@data_type='FLT_6D'">
          public Vect6DDouble  <xsl:value-of select = "@name"/>;
        </xsl:when>
        <!--                       -->
        <xsl:when test="@data_type='CPX_6D'">
          public Vect6DComplex  <xsl:value-of select = "@name"/>;
        </xsl:when>
        <!--                       -->
        
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
            this.<xsl:value-of select="@name"/>.<xsl:value-of select="$methodName"/>(ctx, idsTimeMode, idsFullName);
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
            
            if( !( this.<xsl:value-of select = "@name"/> == null || this.<xsl:value-of select = "@name"/>.length &lt; 1 ))
            {   
            try{
            arraySize = this.<xsl:value-of select = "@name"/>.length;
            int tmpArray[] = { arraySize };
            aosCtx = LowLevel.ual_begin_arraystruct_action(ctx, strNodePath, strTimeBasePath, tmpArray);
            for( int i = 0; i &lt;arraySize; i++)
            {
            this.<xsl:value-of select="@name"/>[i].<xsl:value-of select="$methodName"/>(aosCtx, idsTimeMode, idsFullName);
            LowLevel.ual_iterate_over_arraystruct(aosCtx, 1); 
            }
            }
            
            finally { 
            if(aosCtx >= 0)
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
            if( !( this.<xsl:value-of select = "@name"/> == null ||  this.<xsl:value-of select = "@name"/>.length &lt; 1 || idsTimeMode == LowLevel.IDS_TIME_MODE_INDEPENDENT))
            {   
            try{
            arraySize = this.<xsl:value-of select = "@name"/>.length;
            int tmpArray[] = { arraySize };
            aosCtx = LowLevel.ual_begin_arraystruct_action(ctx, strNodePath, strTimeBasePath, tmpArray);
            for( int i = 0; i &lt;arraySize; i++)
            {
            this.<xsl:value-of select="@name"/>[i].<xsl:value-of select="$methodName"/>(aosCtx, idsTimeMode, idsFullName);
            LowLevel.ual_iterate_over_arraystruct(aosCtx, 1); 
            }
            }
            
            finally { 
            if(aosCtx >= 0)
            LowLevel.ual_end_action(aosCtx);
            }
            }
          </xsl:when>
          <xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and @type='dynamic'">
            
            <xsl:text>/*-----------------------------------------------------------------------------------------*/&#xA;</xsl:text>
            <xsl:choose>
              <xsl:when test="ancestor::field[@data_type='struct_array']">
                strNodePath = &quot;<xsl:call-template  name="printAosRelativePath"/>&quot;;
                if (idsTimeMode == LowLevel.IDS_TIME_MODE_HOMOGENEOUS) 
                strTimeBasePath = "/time";
                else
                strTimeBasePath = &quot;<xsl:call-template  name="printAosRelativePath"/>/time&quot;;
              </xsl:when>
              <xsl:otherwise>
                strNodePath = &quot;<xsl:value-of select="@path"/>&quot;;
                if (idsTimeMode == LowLevel.IDS_TIME_MODE_HOMOGENEOUS) 
                strTimeBasePath = "/time";
                else
                strTimeBasePath = &quot;<xsl:value-of select="@path"/>/time&quot;;
              </xsl:otherwise>
            </xsl:choose>
            
            if( !( this.<xsl:value-of select = "@name"/> == null ||  this.<xsl:value-of select = "@name"/>.length &lt; 1 || idsTimeMode == LowLevel.IDS_TIME_MODE_INDEPENDENT))
            {   
            try{
            arraySize = this.<xsl:value-of select = "@name"/>.length;
            int tmpArray[] = { arraySize };
            aosCtx = LowLevel.ual_begin_arraystruct_action(ctx, strNodePath, strTimeBasePath, tmpArray);
            for( int i = 0; i &lt;arraySize; i++)
            {
            this.<xsl:value-of select="@name"/>[i].<xsl:value-of select="$methodName"/>(aosCtx, idsTimeMode, idsFullName);
            LowLevel.ual_iterate_over_arraystruct(aosCtx, 1); 
            }
            }
            
            finally { 
            if(aosCtx >= 0)
            LowLevel.ual_end_action(aosCtx);
            }
            }
          </xsl:when>
          
          <xsl:when test="
                    @data_type='str_type' or @data_type='STR_0D'
                    or @data_type='int_type' or @data_type='INT_0D'
                    or @data_type='flt_type' or @data_type='FLT_0D' 
                    or @data_type='cpx_type' or @data_type='CPX_0D' 
                    or @data_type='str_1d_type' or @data_type='STR_1D'
                    or @data_type='flt_1d_type' or @data_type='FLT_1D'
                    or @data_type='cpx_1d_type' or @data_type='CPX_1D'
                    or @data_type='int_1d_type' or @data_type='INT_1D'
                    or @data_type='FLT_2D' or @data_type='INT_2D' or @data_type='CPX_2D'
                    or @data_type='FLT_3D' or @data_type='INT_3D' or @data_type='CPX_3D'
                    or @data_type='FLT_4D' or @data_type='INT_4D' or @data_type='CPX_4D'
                    or @data_type='FLT_5D' or @data_type='INT_5D' or @data_type='CPX_5D'
                    or @data_type='FLT_6D' or @data_type='INT_6D' or @data_type='CPX_6D'">
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
                if( idsTimeMode != LowLevel.IDS_TIME_MODE_INDEPENDENT)
                {
                if (idsTimeMode == LowLevel.IDS_TIME_MODE_HOMOGENEOUS) 
                strTimeBasePath="/time";
                else
                strTimeBasePath=&quot;<xsl:value-of select="@timebasepath"/>&quot;;
              </xsl:when>
              <xsl:otherwise>
                strTimeBasePath = "";
              </xsl:otherwise>
            </xsl:choose>
            <xsl:choose>
              <xsl:when test="@path='ids_properties/version_put/data_dictionary'">
                Wrapper.writeData(ctx, idsFullName, strNodePath, strTimeBasePath, "<xsl:value-of select="$DD_VERSION"/>", "<xsl:value-of select="@lifecycle_status"/>");
              </xsl:when>
              <xsl:when test="@path='ids_properties/version_put/access_layer'">
                Wrapper.writeData(ctx, idsFullName, strNodePath, strTimeBasePath, "<xsl:value-of select="$AL_VERSION"/>", "<xsl:value-of select="@lifecycle_status"/>");
              </xsl:when>
              <xsl:when test="@path='ids_properties/version_put/access_layer_language'">
                Wrapper.writeData(ctx, idsFullName, strNodePath, strTimeBasePath, "java", "<xsl:value-of select="@lifecycle_status"/>");
              </xsl:when>
              <xsl:otherwise>
                Wrapper.writeData(ctx, idsFullName, strNodePath, strTimeBasePath, this.<xsl:value-of select="@name"/>, "<xsl:value-of select="@lifecycle_status"/>");
              </xsl:otherwise>
            </xsl:choose>
            
            <xsl:if test="@type='dynamic' and not(ancestor::field[@type='dynamic' and @data_type='struct_array'])">
              }
            </xsl:if>
          </xsl:when>
          <xsl:otherwise>
            //Doc Put <xsl:value-of select="@path"/> : PROBLEM : UNIDENTIFIED TYPE !!! <!-- for comment only -->
          </xsl:otherwise>
        </xsl:choose>
      </xsl:if>
    </xsl:template>
    
    
    
    <!--=================================================-->
    <!--                 Get field from IDS              -->
    <!--=================================================-->
    
    
    
    
    <xsl:template match="field" mode="GET_SINGLE">
      <xsl:call-template name="COMMENT_NODE"/>
      <xsl:choose>
        <!--========== Regular structures ==========-->
        <!-- YB 2014 -->
        <xsl:when test="@data_type='structure'">
          this.<xsl:value-of select="@name"/>.get(ctx, idsTimeMode);
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
          this.<xsl:value-of select="@name"/>[i].get(aosCtx, idsTimeMode);
          LowLevel.ual_iterate_over_arraystruct(aosCtx, 1); 
          }
          }
          }     
          finally { 
          if(aosCtx >= 0)
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
          this.<xsl:value-of select="@name"/>[i].get(aosCtx, idsTimeMode);
          LowLevel.ual_iterate_over_arraystruct(aosCtx, 1); 
          }
          }
          }     
          finally { 
          if(aosCtx >= 0)
          LowLevel.ual_end_action(aosCtx);
          }
        </xsl:when>
        <xsl:when test="@data_type='struct_array' and @maxoccur='unbounded' and @type='dynamic'">
          <xsl:text>/*-----------------------------------------------------------------------------------------*/&#xA;</xsl:text>
          if (idsTimeMode != LowLevel.IDS_TIME_MODE_INDEPENDENT) 
          {
          <xsl:choose>
            <xsl:when test="ancestor::field[@data_type='struct_array']">
              strNodePath = &quot;<xsl:call-template  name="printAosRelativePath"/>&quot;;
              if (idsTimeMode == LowLevel.IDS_TIME_MODE_HOMOGENEOUS) 
              strTimeBasePath = "/time";
              else
              strTimeBasePath = &quot;<xsl:call-template  name="printAosRelativePath"/>/time&quot;;
            </xsl:when>
            <xsl:otherwise>
              strNodePath = &quot;<xsl:value-of select="@path"/>&quot;;
              if (idsTimeMode == LowLevel.IDS_TIME_MODE_HOMOGENEOUS) 
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
          this.<xsl:value-of select="@name"/>[i].get(aosCtx, idsTimeMode);
          LowLevel.ual_iterate_over_arraystruct(aosCtx, 1); 
          }
          }
          }     
          finally { 
          if(aosCtx >= 0)
          LowLevel.ual_end_action(aosCtx);
          }
          }
        </xsl:when>
        
        <xsl:when test="
                  @data_type='str_type' or @data_type='STR_0D'
                  or @data_type='int_type' or @data_type='INT_0D'
                  or @data_type='flt_type' or @data_type='FLT_0D' 
                  or @data_type='cpx_type' or @data_type='CPX_0D' 
                  or @data_type='str_1d_type' or @data_type='STR_1D'
                  or @data_type='flt_1d_type' or @data_type='FLT_1D'
                  or @data_type='cpx_1d_type' or @data_type='CPX_1D'
                  or @data_type='int_1d_type' or @data_type='INT_1D'
                  or @data_type='FLT_2D' or @data_type='INT_2D' or @data_type='CPX_2D'
                  or @data_type='FLT_3D' or @data_type='INT_3D' or @data_type='CPX_3D'
                  or @data_type='FLT_4D' or @data_type='INT_4D' or @data_type='CPX_4D'
                  or @data_type='FLT_5D' or @data_type='INT_5D' or @data_type='CPX_5D'
                  or @data_type='FLT_6D' or @data_type='INT_6D' or @data_type='CPX_6D'">
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
              if (idsTimeMode != LowLevel.IDS_TIME_MODE_INDEPENDENT) 
              {
              if (idsTimeMode == LowLevel.IDS_TIME_MODE_HOMOGENEOUS) 
              strTimeBasePath="/time";
              else
              strTimeBasePath=&quot;<xsl:value-of select="@timebasepath"/>&quot;;
            </xsl:when>
            <xsl:otherwise>
              strTimeBasePath = "";
            </xsl:otherwise>
          </xsl:choose>
          this.<xsl:value-of select="@name"/> = Wrapper.readData(ctx, strNodePath, strTimeBasePath, this.<xsl:value-of select="@name"/>);
          <xsl:if test="@type='dynamic' and not(ancestor::field[@type='dynamic' and @data_type='struct_array'])">
            }
          </xsl:if>
        </xsl:when>
        <xsl:otherwise>
          //Doc GET <xsl:value-of select="@path"/> : PROBLEM : UNIDENTIFIED TYPE !!! <!-- for comment only -->
        </xsl:otherwise>
      </xsl:choose>
    </xsl:template>
    
    
    <xsl:template name ="printAosRelativePath">
      <xsl:variable name="AoSPath" select="ancestor::field[@data_type='struct_array'][1]/@path"/>
      <xsl:variable name="elementPath" select="@path"/>
      
      <xsl:value-of select="replace($elementPath,concat($AoSPath,'/'),'')"/>
    </xsl:template>
    
    
  </xsl:stylesheet>
