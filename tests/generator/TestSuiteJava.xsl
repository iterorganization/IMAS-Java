<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fn="http://www.w3.org/2005/xpath-functions">
    <xsl:output method="text"/>
    <xsl:strip-space elements="*"/>

    <!-- Initial code -->
    <xsl:template match="IDSs">
        <xsl:text>import java.util.Arrays;&#10;</xsl:text>
        <xsl:text>import java.util.Random;&#10;</xsl:text>
 <!--       <xsl:text>import org.apache.commons.cli.CommandLine;&#10;</xsl:text>
        <xsl:text>import org.apache.commons.cli.CommandLineParser;&#10;</xsl:text>
        <xsl:text>import org.apache.commons.cli.Options;&#10;</xsl:text>
        <xsl:text>import org.apache.commons.cli.PosixParser;&#10;</xsl:text>
     -->   <xsl:text>import imasjava.*;&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
        <xsl:text>public class TestSuite {&#10;</xsl:text>
        <xsl:text>&#9;private static final int TESTSHOT = 9999;&#10;</xsl:text>
        <xsl:text>&#9;private static final int TESTRUN = 9999;&#10;</xsl:text>
        <xsl:text>&#9;private static final Random RANDOM = new Random();&#10;</xsl:text>
        <xsl:text>&#9;private static final String PRINTABLE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%&amp;\'()*+,-./:;&lt;=&gt;?@[\\]^_`{|}~\t\n\r";&#10;</xsl:text>
     <!--   <xsl:text>&#9;private static CommandLine cmd;&#10;</xsl:text> -->
        <xsl:text>&#9;private static int idx;&#10;</xsl:text>
        <xsl:text>&#9;private static long seed = RANDOM.nextInt();&#10;</xsl:text>
        <xsl:text>&#9;private enum Types { DOUBLE, INTEGER, STRING, COMPLEX };&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
	
       <xsl:text>&#9;public static void imasTest() throws Exception {&#10;</xsl:text>
       <xsl:text>&#9;&#9;java.util.ArrayList idss = imas.getAvailableIDSs();&#10;</xsl:text>
       <xsl:text>&#9;&#9;System.out.println("List of available CPOs\n");&#10;</xsl:text>
       <xsl:text>&#9;&#9;for(int i=0; i  &lt; idss.size();i++) {&#10;</xsl:text>
       <xsl:text>&#9;&#9;&#9;System.out.println(idss.get(i));&#10;</xsl:text>
       <xsl:text>&#9;&#9;}&#10;</xsl:text>
       <xsl:text>&#10;</xsl:text>
       <xsl:text>&#9;&#9;System.out.println("\nNumber of occurences\n");&#10;</xsl:text>
       <xsl:text>&#9;&#9;for(int i=0; i &lt; idss.size();i++) {&#10;</xsl:text>
       <xsl:text>&#9;&#9;&#9;int occur = imas.getMaxOccurences((String)idss.get(i));&#10;</xsl:text>
       <xsl:text>&#9;&#9;&#9;System.out.format("%2d - %s\n",occur, (String)idss.get(i));&#10;</xsl:text>
       <xsl:text>&#9;&#9;}&#10;</xsl:text>
       <xsl:text>&#10;</xsl:text>
       <xsl:text>&#9;&#9;}&#10;</xsl:text>
       <xsl:text>&#10;</xsl:text>
       
        <xsl:text>&#9;public static void main(String[] args) {&#10;</xsl:text>
        <xsl:text>&#9;&#9;try {&#10;</xsl:text>
     <!--   <xsl:text>&#9;&#9;&#9;init(args);&#10;</xsl:text> -->
        <xsl:apply-templates select="child::IDS" mode="test"/>
   <!--     <xsl:text>&#9;&#9;&#9;finish();&#10;</xsl:text> -->
        <xsl:text>&#9;&#9;} catch (UALException e) {&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;e.printStackTrace();&#10;</xsl:text>
        <xsl:text>&#9;&#9;} catch (IllegalAccessException e) {&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;e.printStackTrace();&#10;</xsl:text>
        <xsl:text>&#9;&#9;} catch (NoSuchFieldException e) {&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;e.printStackTrace();&#10;</xsl:text>
        <xsl:text>&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>


  
  

        <xsl:text>&#9;private static Vect1DDouble getTime() {&#10;</xsl:text>
        <xsl:text>&#9;&#9;return new Vect1DDouble(new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0});&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>

        <xsl:text>&#9;private static Double getdouble() { return getDouble(); }&#10;</xsl:text>
        <xsl:text>&#9;private static Double getDouble() {&#10;</xsl:text>
        <xsl:text>&#9;&#9;return RANDOM.nextGaussian();&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>

        <xsl:text>&#9;private static Integer getint() { return getInteger(); }&#10;</xsl:text>
        <xsl:text>&#9;private static Integer getInteger() {&#10;</xsl:text>
        <xsl:text>&#9;&#9;return RANDOM.nextInt();&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>

        <xsl:text>&#9;private static String getString() {&#10;</xsl:text>
        <xsl:text>&#9;&#9;int length = (Math.abs(RANDOM.nextInt()) % 1024) + 8;&#10;</xsl:text>
        <xsl:text>&#9;&#9;StringBuilder builder = new StringBuilder();&#10;</xsl:text>
        <xsl:text>&#9;&#9;while (length-- > 0)&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;builder.append(PRINTABLE.charAt(Math.abs(RANDOM.nextInt()) % PRINTABLE.length()));&#10;</xsl:text>
        <xsl:text>&#9;&#9;return builder.toString();&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
	
        <xsl:call-template name="getArrayGenerator"/>

        <xsl:text>&#9;private static void assertField(Object observed, Object expected, String fieldname) {&#10;</xsl:text>
        <xsl:text>&#9;&#9;assert observed != null : fieldname + " is NULL!";&#10;</xsl:text>
        <xsl:text>&#9;&#9;String className = observed.getClass().toString();&#10;</xsl:text>
        <xsl:text>&#9;&#9;if (className.contains("Vect")) {&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;Object oarray = null, earray = null;&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;try {&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;oarray = observed.getClass().getMethod("getArray").invoke(observed);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;earray = expected.getClass().getMethod("getArray").invoke(expected);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;} catch (Exception e) {&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;System.out.println(fieldname + " : error, observed=" + observed + ", expected=" + expected);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;e.printStackTrace();&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;if (className.contains("Boolean"))&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;assert Arrays.equals((boolean[]) oarray, (boolean[]) earray) : fieldname + " : different values, observed=" + Arrays.toString((boolean[]) oarray) + ", expected=" + Arrays.toString((boolean[]) earray);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;if (className.contains("Double"))&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;assert Arrays.equals((double[]) oarray, (double[]) earray) : fieldname + " : different values, observed=" + Arrays.toString((double[]) oarray) + ", expected=" + Arrays.toString((double[]) earray);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;if (className.contains("Float"))&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;assert Arrays.equals((float[]) oarray, (float[]) earray) : fieldname + " : different values, observed=" + Arrays.toString((float[]) oarray) + ", expected=" + Arrays.toString((float[]) earray);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;if (className.contains("Int"))&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;assert Arrays.equals((int[]) oarray, (int[]) earray) : fieldname + " : different values, observed=" + Arrays.toString((int[]) oarray) + ", expected=" + Arrays.toString((int[]) earray);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;if (className.contains("String"))&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;assert Arrays.equals((String[]) oarray, (String[]) earray) : fieldname + " : different values, observed=" + Arrays.toString((String[]) oarray) + ", expected=" + Arrays.toString((String[]) earray);&#10;</xsl:text>
   <!--     <xsl:text>&#9;&#9;&#9;if (className.contains("Complex"))&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;assert Arrays.equals((UALComplexNumber[]) oarray, (UALComplexNumber[]) earray) : fieldname + " : different values, observed=" + Arrays.toString((UALComplexNumber[]) oarray) + ", expected=" + Arrays.toString((UALComplexNumber[]) earray);&#10;</xsl:text>
     --> 
        <xsl:text>&#9;&#9;} else&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;assert observed.hashCode() == expected.hashCode() : fieldname + " : different values, observed=" + observed + ", expected=" + expected;&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>



        <xsl:apply-templates select="child::IDS" mode="put"/>
        <xsl:apply-templates select="child::IDS" mode="get"/>

  	<xsl:apply-templates select="child::IDS" mode="putSlice"/>
        <xsl:apply-templates select="child::IDS" mode="getSlice"/>

<!--
                 <xsl:apply-templates select="child::IDS[@name='core_transport']" mode="put"/>
                 <xsl:apply-templates select="child::IDS[@name='core_transport']" mode="get"/>
        
             <xsl:apply-templates select="child::IDS[@name='core_transport']" mode="putSlice"/>
                 <xsl:apply-templates select="child::IDS[@name='core_transport']" mode="getSlice"/>
-->
        <!--
        <xsl:text>&#9;public static void init(String[] args) throws UALException {&#10;</xsl:text>
        <xsl:text>&#9;&#9;Options options = new Options();&#10;</xsl:text>
        <xsl:text>&#9;&#9;options.addOption("seed", true, "seed of rng");&#10;</xsl:text>
        <xsl:text>&#9;&#9;options.addOption("remote", true, "mdsip address");&#10;</xsl:text>
        <xsl:text>&#9;&#9;options.addOption("method", true, "get or put");&#10;</xsl:text>
        <xsl:text>&#9;&#9;options.addOption("hdf5", false, "use hdf5?");&#10;</xsl:text>
        <xsl:text>&#9;&#9;CommandLineParser parser = new PosixParser();&#10;</xsl:text>
        <xsl:text>&#9;&#9;try {&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;cmd = parser.parse(options, args);&#10;</xsl:text>
        <xsl:text>&#9;&#9;} catch (Exception e) {&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;e.printStackTrace();&#10;</xsl:text>
        <xsl:text>&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;&#9;if (cmd.hasOption("seed"))&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;seed = Long.valueOf(cmd.getOptionValue("seed"));&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
<xsl:text>&#9;&#9;if (cmd.hasOption("remote"))&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;imas.connect(cmd.getOptionValue("remote"));&#10;</xsl:text>
        <xsl:text>&#9;&#9;if (cmd.hasOption("method") &amp;&amp; cmd.getOptionValue("method").equals("get")) {&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;if (cmd.hasOption("hdf5"))&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;idx = imas.openHdf5("euitm", TESTSHOT, TESTRUN);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;else&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;idx = imas.open("euitm", TESTSHOT, TESTRUN);&#10;</xsl:text>
        <xsl:text>&#9;&#9;} else {&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;if (cmd.hasOption("hdf5"))&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;idx = imas.createHdf5("euitm", TESTSHOT, TESTRUN, -1, -1);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;else&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;idx = imas.create("euitm", TESTSHOT, TESTRUN, -1, -1);&#10;</xsl:text>
        <xsl:text>&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>

        <xsl:text>&#9;public static void finish() throws UALException {&#10;</xsl:text>
        <xsl:text>&#9;&#9;imas.close(idx);&#10;</xsl:text>
        <xsl:text>&#9;&#9;if (cmd.hasOption("remote"))&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;imas.disconnect();&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>

        <xsl:text>}&#10;</xsl:text>
    </xsl:template>
-->

        <xsl:text>&#9;public static void initPut() throws UALException {&#10;</xsl:text>
        <xsl:text>&#9;{&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;idx = imas.create("ids", TESTSHOT, TESTRUN, -1, -1);&#10;</xsl:text>
        <xsl:text>&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>

        <xsl:text>&#9;public static void initGet() throws UALException {&#10;</xsl:text>
        <xsl:text>&#9;{&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;idx = imas.open("ids", TESTSHOT, TESTRUN);&#10;</xsl:text>
        <xsl:text>&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
	

        <xsl:text>&#9;public static void finish() throws UALException {&#10;</xsl:text>
        <xsl:text>&#9;&#9;imas.close(idx);&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>

        <xsl:text>}&#10;</xsl:text>
    </xsl:template>

    <xsl:template name="getArrayGenerator">
        <xsl:text>&#9;private static Object getArray(Types t, int length) {&#10;</xsl:text>
        <xsl:text>&#9;&#9;switch (t) {&#10;</xsl:text>
        <xsl:text>&#9;&#9;case DOUBLE:&#10;</xsl:text>
        <xsl:call-template name="generateArray">
            <xsl:with-param name="type" select="'double'"/>
        </xsl:call-template>

    <xsl:text>&#9;&#9;case INTEGER:&#10;</xsl:text>
        <xsl:call-template name="generateArray">
            <xsl:with-param name="type" select="'int'"/>
        </xsl:call-template>
        <xsl:text>&#9;&#9;case STRING:&#10;</xsl:text>
        <xsl:call-template name="generateArray">
            <xsl:with-param name="type" select="'String'"/>
        </xsl:call-template>
        <xsl:text>&#9;&#9;default:&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;return null;&#10;</xsl:text>
        <xsl:text>&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
    </xsl:template>


    <xsl:template name="generateArray">
        <xsl:param name="type"/>
  	       <xsl:text>&#9;&#9;&#9;</xsl:text><xsl:value-of select="$type"/><xsl:text>[] array</xsl:text><xsl:value-of select="$type"/><xsl:text> = new </xsl:text><xsl:value-of select="$type"/><xsl:text>[length];&#10;</xsl:text>
               <xsl:text>&#9;&#9;&#9;while (--length >= 0)&#10;</xsl:text>
               <xsl:text>&#9;&#9;&#9;&#9;array</xsl:text><xsl:value-of select="$type"/><xsl:text>[length] = get</xsl:text><xsl:value-of select="$type"/><xsl:text>();&#10;</xsl:text>
               <xsl:text>&#9;&#9;&#9;return array</xsl:text><xsl:value-of select="$type"/><xsl:text>;&#10;</xsl:text>        
    </xsl:template>


    <!-- IDS perform the tests -->
<!--

    <xsl:template match="IDS[@name='core_transport']" mode="test">
-->

 <xsl:template match="IDS" mode="test">


	<xsl:text>&#9;&#9;&#9;initPut();&#10;</xsl:text> 
        <xsl:text>&#9;&#9;&#9;</xsl:text><xsl:value-of select="@name"/><xsl:text>put();&#10;</xsl:text>
	<xsl:text>&#9;&#9;&#9;finish();&#10;</xsl:text> 
	<xsl:text>&#9;&#9;&#9;initGet();&#10;</xsl:text> 
        <xsl:text>&#9;&#9;&#9;</xsl:text><xsl:value-of select="@name"/><xsl:text>get();&#10;</xsl:text>
	<xsl:text>&#9;&#9;&#9;finish();&#10;</xsl:text> 
     	<xsl:text>&#9;&#9;&#9;initPut();&#10;</xsl:text> 
        <xsl:text>&#9;&#9;&#9;</xsl:text><xsl:value-of select="@name"/><xsl:text>putSlice();&#10;</xsl:text>
	<xsl:text>&#9;&#9;&#9;finish();&#10;</xsl:text> 
   	<xsl:text>&#9;&#9;&#9;initGet();&#10;</xsl:text> 
        <xsl:text>&#9;&#9;&#9;</xsl:text><xsl:value-of select="@name"/><xsl:text>getSlice();&#10;</xsl:text>
	<xsl:text>&#9;&#9;&#9;finish();&#10;</xsl:text> 
   
	<xsl:text>&#10;</xsl:text>
    </xsl:template>

    
    <!-- IDS put()-->
    <xsl:template match="IDS" mode="put">
        <xsl:text>&#9;public static void </xsl:text><xsl:value-of select="@name"/><xsl:text>put() throws UALException {&#10;</xsl:text>
        <xsl:text>&#9;&#9;System.out.println("Testing put() on </xsl:text><xsl:value-of select="@name"/><xsl:text>");&#10;</xsl:text>
        <xsl:text>&#9;&#9;RANDOM.setSeed(seed);&#10;</xsl:text>
        <xsl:text>&#9;&#9;imas.</xsl:text><xsl:value-of select="@name"/><xsl:text> ids = new imas.</xsl:text><xsl:value-of select="@name"/><xsl:text>();&#10;</xsl:text>
        <xsl:text>&#9;&#9;for (int i = 0; i &lt; </xsl:text><xsl:value-of select="@maxoccur"/><xsl:text> + 1; i++) {&#10;</xsl:text>
        <xsl:apply-templates select="field" mode="put"/>
        <xsl:text>&#9;&#9;&#9;String occurrence = "</xsl:text><xsl:value-of select="@name"/><xsl:text>" + (i == 0 ? "" : "/" + i);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;imas.</xsl:text><xsl:value-of select="@name"/><xsl:text>.put(idx, occurrence, ids);&#10;</xsl:text>
        <xsl:text>&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
    </xsl:template>


    <!-- IDS putSlice()-->
    <xsl:template match="IDS" mode="putSlice">
        <xsl:text>&#9;public static void </xsl:text><xsl:value-of select="@name"/><xsl:text>putSlice() throws UALException {&#10;</xsl:text>
        <xsl:text>&#9;&#9;System.out.println("Testing putSlice() on </xsl:text><xsl:value-of select="@name"/><xsl:text>");&#10;</xsl:text>
        <xsl:text>&#9;&#9;RANDOM.setSeed(seed);&#10;</xsl:text>
        <xsl:text>&#9;&#9;imas.</xsl:text><xsl:value-of select="@name"/><xsl:text> ids = new imas.</xsl:text><xsl:value-of select="@name"/><xsl:text>();&#10;</xsl:text>
        <xsl:text>&#9;&#9;for (int i = 0; i &lt; </xsl:text><xsl:value-of select="@maxoccur"/><xsl:text> + 1; i++) {&#10;</xsl:text>
        <xsl:apply-templates select="field" mode="put"/>
        <xsl:text>&#9;&#9;&#9;String occurrence = "</xsl:text><xsl:value-of select="@name"/><xsl:text>" + (i == 0 ? "" : "/" + i);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;imas.</xsl:text><xsl:value-of select="@name"/><xsl:text>.putNonTimed(idx, occurrence, ids);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;imas.</xsl:text><xsl:value-of select="@name"/><xsl:text>.putSlice(idx, occurrence, ids);&#10;</xsl:text>
        <xsl:text>&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
    </xsl:template>
    

    <!-- IDS get()-->
    <xsl:template match="IDS" mode="get">
        <xsl:text>&#9;public static void </xsl:text><xsl:value-of select="@name"/><xsl:text>get() throws UALException, IllegalAccessException, NoSuchFieldException {&#10;</xsl:text>
        <xsl:text>&#9;&#9;System.out.println("Testing get() on </xsl:text><xsl:value-of select="@name"/><xsl:text>");&#10;</xsl:text>
        <xsl:text>&#9;&#9;RANDOM.setSeed(seed);&#10;</xsl:text>
        <xsl:text>&#9;&#9;for (int i = 0; i &lt; </xsl:text><xsl:value-of select="@maxoccur"/><xsl:text> + 1; i++) {&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;String occurrence = "</xsl:text><xsl:value-of select="@name"/><xsl:text>" + (i == 0 ? "" : "/" + i);&#10;</xsl:text>
      <!--  <xsl:choose>
            <xsl:when test="@timed='no'">
	  --> <xsl:text>&#9;&#9;&#9;imas.</xsl:text><xsl:value-of select="@name"/><xsl:text> ids = imas.</xsl:text><xsl:value-of select="@name"/><xsl:text>.get(idx, occurrence);&#10;</xsl:text>
	   <!-- </xsl:when>
          <xsl:otherwise>
	    	<xsl:text>&#9;&#9;&#9;imas.</xsl:text><xsl:value-of select="@name"/><xsl:text> ids = imas.</xsl:text><xsl:value-of select="@name"/><xsl:text>.getSlice(idx, occurrence, 0.0, imas.CLOSEST_SAMPLE);&#10;</xsl:text>
	    </xsl:otherwise>
        </xsl:choose>
       --> <xsl:apply-templates select="field" mode="get"/>
        <xsl:text>&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
    </xsl:template>


   <!-- IDS get()-->
    <xsl:template match="IDS" mode="getSlice">
        <xsl:text>&#9;public static void </xsl:text><xsl:value-of select="@name"/><xsl:text>getSlice() throws UALException, IllegalAccessException, NoSuchFieldException {&#10;</xsl:text>
        <xsl:text>&#9;&#9;System.out.println("Testing getSlice() on </xsl:text><xsl:value-of select="@name"/><xsl:text>");&#10;</xsl:text>
        <xsl:text>&#9;&#9;RANDOM.setSeed(seed);&#10;</xsl:text>
        <xsl:text>&#9;&#9;for (int i = 0; i &lt; </xsl:text><xsl:value-of select="@maxoccur"/><xsl:text> + 1; i++) {&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;String occurrence = "</xsl:text><xsl:value-of select="@name"/><xsl:text>" + (i == 0 ? "" : "/" + i);&#10;</xsl:text>
 	<xsl:text>&#9;&#9;&#9;imas.</xsl:text><xsl:value-of select="@name"/><xsl:text> ids = imas.</xsl:text><xsl:value-of select="@name"/><xsl:text>.getSlice(idx, occurrence, 0.0, imas.CLOSEST_SAMPLE);&#10;</xsl:text>
	 <xsl:apply-templates select="field" mode="getSlice"/>
        <xsl:text>&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
    </xsl:template>
    

    <!-- field put() -->
    <xsl:template match="field[not(@data_type='structure' or @data_type='struct_array')]" mode="put">
        <xsl:text>&#9;&#9;&#9;ids.</xsl:text><xsl:value-of select="translate(@path, '/', '.')"/><xsl:text> = </xsl:text><xsl:call-template name="type2value"/><xsl:text>;&#10;</xsl:text>
    </xsl:template>


    <!-- field put() for array of structures -->
    <xsl:template match="field[@data_type='struct_array']" mode="put">
        <xsl:call-template name="putStructArray">
            <xsl:with-param name="path" select="concat(translate(@path, '/', '.'), '[0]')"/>
            <xsl:with-param name="resize" select="true()"/>
        </xsl:call-template>
    </xsl:template>


    <xsl:template name="putStructArray">
        <xsl:param name="path"/>
        <xsl:param name="resize"/>
        <xsl:if test="$resize"><xsl:text>&#9;&#9;&#9;ids.</xsl:text><xsl:value-of select="substring($path, 1, string-length($path) - 3)"/><xsl:text> = new imas.</xsl:text><xsl:value-of select="ancestor::IDS/@name"/><xsl:for-each select="ancestor-or-self::field"><xsl:text>.</xsl:text><xsl:value-of select="@name"/><xsl:if test="@data_type='struct_array' or @data_type='structure'">Class</xsl:if></xsl:for-each><xsl:text>[1];&#10;</xsl:text><xsl:text>&#9;&#9;&#9;ids.</xsl:text><xsl:value-of select="substring($path, 1, string-length($path) - 3)"/><xsl:text>[0] = new imas.</xsl:text><xsl:value-of select="ancestor::IDS/@name"/><xsl:for-each select="ancestor-or-self::field"><xsl:text>.</xsl:text><xsl:value-of select="@name"/><xsl:if test="@data_type='struct_array' or @data_type='structure'">Class</xsl:if></xsl:for-each><xsl:text>();&#10;</xsl:text></xsl:if>
        <xsl:for-each select="field[not(@data_type='struct_array' or @data_type='structure')]">
            <xsl:text>&#9;&#9;&#9;ids.</xsl:text><xsl:value-of select="concat($path, '.', @name)"/><xsl:text> = </xsl:text><xsl:call-template name="type2value"/>;<xsl:text>&#10;</xsl:text>
        </xsl:for-each>
        <xsl:for-each select="field[@data_type='structure']">
            <xsl:call-template name="putStructArray">
                <xsl:with-param name="path" select="concat($path, '.', @name)"/>
                <xsl:with-param name="resize" select="false()"/>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:for-each select="field[@data_type='struct_array']">
            <xsl:call-template name="putStructArray">
                <xsl:with-param name="path" select="concat($path, '.', @name, '[0]')"/>
                <xsl:with-param name="resize" select="true()"/>
            </xsl:call-template>
        </xsl:for-each>
    </xsl:template> 


    <!-- field get() -->
    <xsl:template match="field[not(@data_type='structure' or @data_type='struct_array')]" mode="get">
        <xsl:text>&#9;&#9;&#9;assertField(ids.</xsl:text><xsl:value-of select="translate(@path, '/', '.')"/><xsl:text>, </xsl:text><xsl:call-template name="type2value"/><xsl:text>, "</xsl:text><xsl:value-of select="ancestor::IDS/@name"/><xsl:text>/</xsl:text><xsl:value-of select="@path"/><xsl:text>");&#10;</xsl:text>
    </xsl:template>


    <!-- field get() for array of structures -->
    <xsl:template match="field[@data_type='struct_array']" mode="get">
        <xsl:call-template name="getStructArray">
            <xsl:with-param name="path" select="concat(translate(@path, '/', '.'), '[0]')"/>
	     <xsl:with-param name="slice" select="false()"/>
        </xsl:call-template>
    </xsl:template>



    <!-- field getSlice() -->
    <xsl:template match="field[not(@data_type='structure' or @data_type='struct_array')]" mode="getSlice">
        <xsl:text>&#9;&#9;&#9;assertField(ids.</xsl:text><xsl:value-of select="translate(@path, '/', '.')"/><xsl:text>, </xsl:text><xsl:call-template name="type2value"/><xsl:text>, "</xsl:text><xsl:value-of select="ancestor::IDS/@name"/><xsl:text>/</xsl:text><xsl:value-of select="@path"/><xsl:text>");&#10;</xsl:text>
    </xsl:template>


    <!-- field get() for array of structures -->
    <xsl:template match="field[@data_type='struct_array']" mode="getSlice">
        <xsl:call-template name="getStructArray">
            <xsl:with-param name="path" select="concat(translate(@path, '/', '.'), '[0]')"/>
	     <xsl:with-param name="slice" select="true()"/>
        </xsl:call-template>
    </xsl:template>
    


    <xsl:template name="getStructArray">
        <xsl:param name="path"/>
	<xsl:param name="slice"/>
        <xsl:for-each select="field[not(@data_type='struct_array' or @data_type='structure')]">
            <xsl:choose>
                <xsl:when test="$slice and @type='dynamic' and not(ancestor::field[@data_type='struct_array' and @maxoccur='unbounded'])  ">
      		  <xsl:text>&#9;&#9;&#9;assertField(ids.</xsl:text><xsl:value-of select="concat($path, '.', @name)"/><xsl:text>, </xsl:text><xsl:call-template name="type2value4slice"/><xsl:text>, "</xsl:text><xsl:value-of select="ancestor::IDS/@name"/><xsl:text>/</xsl:text><xsl:value-of select="@path"/><xsl:text>");&#10;</xsl:text>
        	</xsl:when>
	        <xsl:otherwise>
	        <xsl:text>&#9;&#9;&#9;assertField(ids.</xsl:text><xsl:value-of select="concat($path, '.', @name)"/><xsl:text>, </xsl:text><xsl:call-template name="type2value"/><xsl:text>, "</xsl:text><xsl:value-of select="ancestor::IDS/@name"/><xsl:text>/</xsl:text><xsl:value-of select="@path"/><xsl:text>");&#10;</xsl:text>
     	        </xsl:otherwise>
            </xsl:choose>
        </xsl:for-each>
        <xsl:for-each select="field[@data_type='structure']">
            <xsl:call-template name="getStructArray">
                <xsl:with-param name="path" select="concat($path, '.', @name)"/>
		   <xsl:with-param name="slice" select="$slice"/>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:for-each select="field[@data_type='struct_array']">
            <xsl:call-template name="getStructArray">
                <xsl:with-param name="path" select="concat($path, '.', @name, '[0]')"/>
		 <xsl:with-param name="slice" select="$slice"/>
            </xsl:call-template>
        </xsl:for-each>
    </xsl:template>
    
    


    <xsl:template name="type2value">
        <xsl:choose>
	
	  <xsl:when test="@name='homogeneous_time'">              <xsl:text>1</xsl:text></xsl:when>

	
       <xsl:when test="@name='time'  and (@data_type='flt_1d_type' or @data_type='FLT_1D') ">              <xsl:text>getTime()</xsl:text></xsl:when>

            <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">        <xsl:text>getString()</xsl:text></xsl:when>
            <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">    <xsl:text>new Vect1DString((String[]) getArray(Types.STRING, 1))</xsl:text></xsl:when>

            <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">          <xsl:text>getDouble()</xsl:text></xsl:when>
            <xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">     <xsl:text>new Vect1DDouble((double[]) getArray(Types.DOUBLE, 1))</xsl:text></xsl:when>
            <xsl:when test="@data_type='FLT_2D'">     <xsl:text>new Vect2DDouble(1, 2, (double[]) getArray(Types.DOUBLE, 2))</xsl:text></xsl:when>
            <xsl:when test="@data_type='FLT_3D'">   <xsl:text>new Vect3DDouble(1, 1, 3, (double[]) getArray(Types.DOUBLE, 3))</xsl:text></xsl:when>
            <xsl:when test="@data_type='FLT_4D'">   <xsl:text>new Vect4DDouble(1, 1, 1, 4, (double[]) getArray(Types.DOUBLE, 4))</xsl:text></xsl:when>
            <xsl:when test="@data_type='FLT_5D'">   <xsl:text>new Vect5DDouble(1, 1, 1, 1, 5, (double[]) getArray(Types.DOUBLE, 5))</xsl:text></xsl:when>
            <xsl:when test="@data_type='FLT_6D'">	<xsl:text>new Vect6DDouble(1, 1, 1, 1, 1, 6, (double[]) getArray(Types.DOUBLE, 6))</xsl:text></xsl:when>

            <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">        <xsl:text>getInteger()</xsl:text></xsl:when>
            <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">       <xsl:text>new Vect1DInt((int[]) getArray(Types.INTEGER, 1))</xsl:text></xsl:when>
            <xsl:when test="@data_type='INT_2D'">      <xsl:text>new Vect2DInt(1, 2, (int[]) getArray(Types.INTEGER, 2))</xsl:text></xsl:when>
            <xsl:when test="@data_type='INT_3D'">  <xsl:text>new Vect3DInt(1, 1, 3, (int[]) getArray(Types.INTEGER, 3))</xsl:text></xsl:when>
            <xsl:when test="@data_type='INT_4D'">  <xsl:text>new Vect4DInt(1, 1, 1, 4, (int[]) getArray(Types.INTEGER, 4))</xsl:text></xsl:when>
            <xsl:when test="@data_type='INT_5D'">   <xsl:text>new Vect5DInt(1, 1, 1, 1, 5, (int[]) getArray(Types.INTEGER, 5))</xsl:text></xsl:when>
            <xsl:when test="@data_type='INT_6D'">  <xsl:text>new Vect6DInt(1, 1, 1, 1, 1, 6, (int[]) getArray(Types.INTEGER, 6))</xsl:text></xsl:when>

     	    
	    <xsl:otherwise>
	       <xsl:message terminate="yes">     
		             <xsl:text>&#xA; UNKNOWN TYPE:   </xsl:text>  <xsl:value-of select="@data_type"/>  : <xsl:value-of select="@path"/>   : <xsl:value-of select="@maxoccur"/>   :  <xsl:value-of select="@type"/> 
		</xsl:message>
		</xsl:otherwise>
        </xsl:choose>
    </xsl:template>
   
      <xsl:template name="type2value4slice">
        <xsl:choose>
	      <xsl:when test="@name='homogeneous_time'">              <xsl:text>1</xsl:text>	      </xsl:when>
       	      <xsl:when test="@name='time' and (@data_type='flt_1d_type' or @data_type='FLT_1D')">   <xsl:text>getTime()</xsl:text></xsl:when> 
              <xsl:when test="@data_type='str_type' or @data_type='STR_0D'">                         <xsl:text>getString()</xsl:text></xsl:when>
              <xsl:when test="@data_type='str_1d_type' or @data_type='STR_1D'">                      <xsl:text>(new Vect1DString((String[]) getArray(Types.STRING, 1))).getElementAt(0)</xsl:text></xsl:when>
              <xsl:when test="@data_type='flt_type' or @data_type='FLT_0D'">                         <xsl:text>getDouble()</xsl:text></xsl:when>
              <xsl:when test="@data_type='flt_1d_type' or @data_type='FLT_1D'">             <xsl:text>new Vect1DDouble((double[]) getArray(Types.DOUBLE, 1))</xsl:text></xsl:when>
              <xsl:when test="@data_type='FLT_2D'">     <xsl:text>(new Vect2DDouble(1, 2, (double[]) getArray(Types.DOUBLE, 2))).getElementAt(0)</xsl:text></xsl:when>
              <xsl:when test="@data_type='FLT_3D'">   <xsl:text>(new Vect3DDouble(1, 1, 3, (double[]) getArray(Types.DOUBLE, 3))).getElementAt(0)</xsl:text></xsl:when>
              <xsl:when test="@data_type='FLT_4D'">   <xsl:text>(new Vect4DDouble(1, 1, 1, 4, (double[]) getArray(Types.DOUBLE, 4))).getElementAt(0)</xsl:text></xsl:when>
              <xsl:when test="@data_type='FLT_5D'">   <xsl:text>(new Vect5DDouble(1, 1, 1, 1, 5, (double[]) getArray(Types.DOUBLE, 5))).getElementAt(0)</xsl:text></xsl:when>
              <xsl:when test="@data_type='FLT_6D'">	<xsl:text>(new Vect6DDouble(1, 1, 1, 1, 1, 6, (double[]) getArray(Types.DOUBLE, 6))).getElementAt(0)</xsl:text></xsl:when>
              
	      <xsl:when test="@data_type='int_type' or @data_type='INT_0D'">        <xsl:text>(getInteger()</xsl:text></xsl:when>
              <xsl:when test="@data_type='int_1d_type' or @data_type='INT_1D'">       <xsl:text>new Vect1DInt((int[]) getArray(Types.INTEGER, 1))</xsl:text></xsl:when>
              <xsl:when test="@data_type='INT_2D'">      <xsl:text>(new Vect2DInt(1, 2, (int[]) getArray(Types.INTEGER, 2))).getElementAt(0)</xsl:text></xsl:when>
              <xsl:when test="@data_type='INT_3D'">  <xsl:text>(new Vect3DInt(1, 1, 3, (int[]) getArray(Types.INTEGER, 3))).getElementAt(0)</xsl:text></xsl:when>
              <xsl:when test="@data_type='INT_4D'">  <xsl:text>(new Vect4DInt(1, 1, 1, 4, (int[]) getArray(Types.INTEGER, 4))).getElementAt(0)</xsl:text></xsl:when>
              <xsl:when test="@data_type='INT_5D'">   <xsl:text>(new Vect5DInt(1, 1, 1, 1, 5, (int[]) getArray(Types.INTEGER, 5))).getElementAt(0)</xsl:text></xsl:when>
              <xsl:when test="@data_type='INT_6D'">  <xsl:text>(new Vect6DInt(1, 1, 1, 1, 1, 6, (int[]) getArray(Types.INTEGER, 6))).getElementAt(0)</xsl:text></xsl:when>
	      <xsl:otherwise>
	         <xsl:message terminate="yes">     
		      <xsl:text>&#xA; UNKNOWN TYPE:   </xsl:text>  <xsl:value-of select="@data_type"/>  : <xsl:value-of select="@path"/>   : <xsl:value-of select="@maxoccur"/>   :  <xsl:value-of select="@type"/> 
		</xsl:message>
	      </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
