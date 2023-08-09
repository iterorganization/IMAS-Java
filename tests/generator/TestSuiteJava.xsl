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
           <xsl:text>import imasjava.wrapper.*;&#10;</xsl:text>
        
        <xsl:text>&#10;</xsl:text>
        <xsl:text>public class TestSuite {&#10;</xsl:text>

   <xsl:text>&#9;private static final int NUMBER_SLICES = Generator.DIM_1;&#10;</xsl:text>
   <xsl:text>&#9;private static final int IDS_TIME_MODE = LowLevel.IDS_TIME_MODE_INDEPENDENT;&#10;</xsl:text>
     <!--   <xsl:text>&#9;private static CommandLine cmd;&#10;</xsl:text> -->
        <xsl:text>&#9;private static int idx;&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
	

       <xsl:text>&#9;public static void checkStatus(boolean status)  {&#10;</xsl:text>
       <xsl:text>&#9;&#9;}&#10;</xsl:text>

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
        <xsl:text>&#9;&#9;} catch (Exception e) {&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;e.printStackTrace();&#10;</xsl:text>
        <xsl:text>&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>

        <xsl:apply-templates select="child::IDS" mode="set_static"/>
        <xsl:apply-templates select="child::IDS" mode="set_dynamic"/>

        <xsl:apply-templates select="child::IDS" mode="get_static"/>
        <xsl:apply-templates select="child::IDS" mode="get_dynamic"/>


        <xsl:apply-templates select="child::IDS" mode="put"/>
        <xsl:apply-templates select="child::IDS" mode="get"/>
        <xsl:apply-templates select="child::IDS" mode="putSlice"/>
        <xsl:apply-templates select="child::IDS" mode="getSlice"/>
  <!--
        <xsl:apply-templates select="child::IDS[@name='temporary']" mode="put"/>
        <xsl:apply-templates select="child::IDS[@name='temporary']" mode="get"/>
        <xsl:apply-templates select="child::IDS[@name='temporary']" mode="putSlice"/>
        <xsl:apply-templates select="child::IDS[@name='temporary']" mode="getSlice"/>
-->
  
        <xsl:text>}&#10;</xsl:text>
    </xsl:template>

   

    <!-- IDS perform the tests -->

<!--
    <xsl:template match="IDS[@name='temporary']" mode="test">
-->

    <xsl:template match="IDS" mode="test">
	    <xsl:text>&#9;&#9;&#9;idx = Helper.initPut();&#10;</xsl:text> 
        <xsl:text>&#9;&#9;&#9;</xsl:text><xsl:value-of select="@name"/><xsl:text>_put();&#10;</xsl:text>
	    <xsl:text>&#9;&#9;&#9;Helper.finish(idx);&#10;</xsl:text> 
	    <xsl:text>&#9;&#9;&#9;Helper.initGet();&#10;</xsl:text> 
        <xsl:text>&#9;&#9;&#9;</xsl:text><xsl:value-of select="@name"/><xsl:text>_get();&#10;</xsl:text>
	    <xsl:text>&#9;&#9;&#9;Helper.finish(idx);&#10;</xsl:text> 
        <xsl:text>&#9;&#9;&#9;idx = Helper.initPut();&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;</xsl:text><xsl:value-of select="@name"/><xsl:text>_putSlice();&#10;</xsl:text>
	    <xsl:text>&#9;&#9;&#9;Helper.finish(idx);&#10;</xsl:text> 
        <xsl:text>&#9;&#9;&#9;idx = Helper.initGet();&#10;</xsl:text> 
        <xsl:text>&#9;&#9;&#9;</xsl:text><xsl:value-of select="@name"/><xsl:text>_getSlice();&#10;</xsl:text>
	    <xsl:text>&#9;&#9;&#9;Helper.finish(idx);&#10;</xsl:text> 
	    <xsl:text>&#10;</xsl:text>
    </xsl:template>

    <xsl:template match="IDS" mode="set_static">
        <xsl:text>&#9;public static void </xsl:text><xsl:value-of select="@name"/><xsl:text>_setStatic(imas.</xsl:text><xsl:value-of select="@name"/><xsl:text> ids) throws ALException {&#10;</xsl:text>
        <xsl:text>&#9;&#9;System.out.println(" --- --- --- Calling setStatic() on IDS: </xsl:text><xsl:value-of select="@name"/><xsl:text>");&#10;</xsl:text>
        <xsl:apply-templates select="field" mode="putStatic"/>
        <xsl:text>}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
    </xsl:template>

    <xsl:template match="IDS" mode="set_dynamic">
        <xsl:text>&#9;public static void </xsl:text><xsl:value-of select="@name"/><xsl:text>_setDynamic(imas.</xsl:text><xsl:value-of select="@name"/><xsl:text> ids, int timeIdx) throws ALException {&#10;</xsl:text>
        <xsl:text>&#9;&#9;System.out.println(" --- --- --- Calling setDynamic() on IDS: </xsl:text><xsl:value-of select="@name"/><xsl:text>");&#10;</xsl:text>
        <xsl:apply-templates select="field" mode="putDynamic"/>
        <xsl:text>}&#10;</xsl:text>
       <xsl:text>&#10;</xsl:text>
    </xsl:template>


    <xsl:template match="IDS" mode="get_static">
        <xsl:text>&#9;public static void </xsl:text><xsl:value-of select="@name"/><xsl:text>_getStatic(imas.</xsl:text><xsl:value-of select="@name"/><xsl:text> ids) throws ALException {&#10;</xsl:text>
        <xsl:text>&#9;&#9;boolean status = false;&#10;</xsl:text>
        <xsl:text>&#9;&#9;System.out.println(" --- --- --- Calling getStatic() on IDS: </xsl:text><xsl:value-of select="@name"/><xsl:text>");&#10;</xsl:text>
        <xsl:apply-templates select="field" mode="getStatic"/>
        <xsl:text>}&#10;</xsl:text>
       <xsl:text>&#10;</xsl:text>
    </xsl:template>

    <xsl:template match="IDS" mode="get_dynamic">
        <xsl:text>&#9;public static void </xsl:text><xsl:value-of select="@name"/><xsl:text>_getDynamic(imas.</xsl:text><xsl:value-of select="@name"/><xsl:text> ids, int timeIdx) throws ALException {&#10;</xsl:text>
        <xsl:text>&#9;&#9;boolean status = false;&#10;</xsl:text>
        <xsl:text>&#9;&#9;System.out.println(" --- --- --- Calling getDynamic() on IDS: </xsl:text><xsl:value-of select="@name"/><xsl:text>");&#10;</xsl:text>
        <xsl:apply-templates select="field" mode="getDynamic"/>
        <xsl:text>}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
    </xsl:template>


    <!-- IDS put()-->
    <xsl:template match="IDS" mode="put">
        <xsl:text>&#9;public static void </xsl:text><xsl:value-of select="@name"/><xsl:text>_put() throws ALException {&#10;</xsl:text>
        <xsl:text>&#9;&#9;System.out.println("Testing put() on </xsl:text><xsl:value-of select="@name"/><xsl:text>");&#10;</xsl:text>
        <xsl:text>&#9;&#9;Generator.clearSeed();&#10;</xsl:text>
        <xsl:text>&#9;&#9;imas.</xsl:text><xsl:value-of select="@name"/><xsl:text> ids = new imas.</xsl:text><xsl:value-of select="@name"/><xsl:text>();&#10;</xsl:text>
    <!--    <xsl:text>&#9;&#9;for (int i = 0; i &lt; </xsl:text><xsl:value-of select="@maxoccur"/><xsl:text> + 1; i++) {&#10;</xsl:text>
     -->    <xsl:text>&#9;&#9;for (int i = 0; i &lt; 1; i++) {&#10;</xsl:text>
        <xsl:apply-templates select="field" mode="put"/>
        <xsl:text>&#9;&#9;&#9;String occurrence = "</xsl:text><xsl:value-of select="@name"/><xsl:text>" + (i == 0 ? "" : "/" + i);&#10;</xsl:text>
       <xsl:text>&#9;&#9;&#9;System.out.println("===================================================================================================================================================================");&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;imas.</xsl:text><xsl:value-of select="@name"/><xsl:text>.put(idx, occurrence, ids);&#10;</xsl:text>
        <xsl:text>&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
    </xsl:template>




    <!-- IDS putSlice()-->
    <xsl:template match="IDS" mode="putSlice">
        <xsl:text>//====================================================================================&#10;</xsl:text>
        <xsl:text>//&#9;&#9; PUT SLICE </xsl:text><xsl:value-of select="@name"/> <xsl:text> &#10;</xsl:text>
        <xsl:text>//====================================================================================&#10;</xsl:text>
        <xsl:text>&#9;public static void </xsl:text><xsl:value-of select="@name"/><xsl:text>_putSlice() throws ALException {&#10;</xsl:text>
        <xsl:text>&#9;&#9;System.out.println("Testing putSlice() on </xsl:text><xsl:value-of select="@name"/><xsl:text>");&#10;</xsl:text>
        <xsl:text>&#9;&#9;Generator.clearSeed();&#10;</xsl:text>
        <xsl:text>&#9;&#9;imas.</xsl:text><xsl:value-of select="@name"/><xsl:text> ids = new imas.</xsl:text><xsl:value-of select="@name"/><xsl:text>();&#10;</xsl:text>
  
     <!--   <xsl:text>&#9;for (int occurrence = 0; occurrence &lt; </xsl:text><xsl:value-of select="@maxoccur"/><xsl:text> + 1; occurrence++) {&#10;</xsl:text>
    <xsl:text>&#9;&#9;&#9;printf("  Testing occurrence : %d\n", i);&#10;</xsl:text>
     -->  
    <!--     <xsl:text>&#9;&#9;&#9;String occurrence = "</xsl:text><xsl:value-of select="@name"/><xsl:text>" + (i == 0 ? "" : "/" + i);&#10;</xsl:text>
     -->   
        <xsl:text>&#9;&#9;&#9;String occurrence = "</xsl:text><xsl:value-of select="@name"/><xsl:text>" ;&#10;</xsl:text>
        <xsl:text>&#9;&#9;for (int j = 0; j &lt; NUMBER_SLICES; j++)&#10;</xsl:text>
        <xsl:text>&#9;&#9;{&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;System.out.println(" --- --- Testing slice : " + j);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;if (j == 0) &#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;{&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;</xsl:text><xsl:value-of select="@name"/><xsl:text>_setStatic(ids);&#10;</xsl:text> 
        <xsl:text>&#9;&#9;&#9;&#9;</xsl:text><xsl:value-of select="@name"/><xsl:text>_setDynamic(ids, j);&#10;</xsl:text> 
        <xsl:text>&#9;&#9;&#9;&#9;imas.</xsl:text><xsl:value-of select="@name"/><xsl:text>.put(idx, occurrence, ids);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;else&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;{&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;</xsl:text><xsl:value-of select="@name"/><xsl:text>_setDynamic(ids, j);&#10;</xsl:text> 
        <xsl:text>&#9;&#9;&#9;&#9;imas.</xsl:text><xsl:value-of select="@name"/><xsl:text>.putSlice(idx, occurrence, ids);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;&#9;}&#10;</xsl:text>
        <xsl:text>}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
    </xsl:template>
    

    <!-- IDS get()-->
    <xsl:template match="IDS" mode="get">
        <xsl:text>&#9;public static void </xsl:text><xsl:value-of select="@name"/><xsl:text>_get() throws ALException, IllegalAccessException, NoSuchFieldException {&#10;</xsl:text>
        <xsl:text>&#9;&#9;boolean status = false;&#10;</xsl:text>
        <xsl:text>&#9;&#9;System.out.println("Testing get() on </xsl:text><xsl:value-of select="@name"/><xsl:text>");&#10;</xsl:text>
        <xsl:text>&#9;&#9;Generator.clearSeed();&#10;</xsl:text>
 <!--       <xsl:text>&#9;&#9;for (int i = 0; i &lt; </xsl:text><xsl:value-of select="@maxoccur"/><xsl:text> + 1; i++) {&#10;</xsl:text>
  -->   <xsl:text>&#9;&#9;for (int i = 0; i &lt; 1; i++) {&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;String occurrence = "</xsl:text><xsl:value-of select="@name"/><xsl:text>" + (i == 0 ? "" : "/" + i);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;imas.</xsl:text><xsl:value-of select="@name"/><xsl:text> ids = imas.</xsl:text><xsl:value-of select="@name"/><xsl:text>.get(idx, occurrence);&#10;</xsl:text>

        <xsl:apply-templates select="field" mode="get"/>
        <xsl:text>&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
    </xsl:template>


   <!-- IDS getSlice()-->
   <xsl:template match="IDS" mode="getSlice">
        <xsl:text>//====================================================================================&#10;</xsl:text>
        <xsl:text>//&#9;&#9; GET SLICE </xsl:text><xsl:value-of select="@name"/> <xsl:text> &#10;</xsl:text>
        <xsl:text>//====================================================================================&#10;</xsl:text>
        <xsl:text>&#9;public static void </xsl:text><xsl:value-of select="@name"/><xsl:text>_getSlice() throws ALException, IllegalAccessException, NoSuchFieldException {&#10;</xsl:text>
        <xsl:text>&#9;&#9;boolean status = false;&#10;</xsl:text>
        <xsl:text>&#9;&#9;System.out.println("Testing getSlice() on </xsl:text><xsl:value-of select="@name"/><xsl:text>");&#10;</xsl:text>
        <xsl:text>&#9;&#9;Generator.clearSeed();&#10;</xsl:text>
        <!--     <xsl:text>&#9;for (int occurrence = 0; occurrence &lt; </xsl:text><xsl:value-of select="@maxoccur"/><xsl:text> + 1; occurrence++) {&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;String occurrence = "</xsl:text><xsl:value-of select="@name"/><xsl:text>" + (i == 0 ? "" : "/" + i);&#10;</xsl:text>
        -->   
        <xsl:text>&#9;&#9;&#9;String occurrence = "</xsl:text><xsl:value-of select="@name"/><xsl:text>" ;&#10;</xsl:text>
        <xsl:text>&#9;&#9;for (int j = 0; j &lt; NUMBER_SLICES; j++)&#10;</xsl:text>
        <xsl:text>&#9;&#9;{&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;System.out.println(" --- --- Testing slice : " + j);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;double  time = Generator.getTimeScalar(j);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;imas.</xsl:text><xsl:value-of select="@name"/><xsl:text> ids = imas.</xsl:text><xsl:value-of select="@name"/><xsl:text>.getSlice(idx, occurrence, time, imas.CLOSEST_SAMPLE);&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;if (j == 0)&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;{&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;</xsl:text><xsl:value-of select="@name"/><xsl:text>_getStatic(ids);&#10;</xsl:text> 
        <xsl:text>&#9;&#9;&#9;}&#10;</xsl:text>
        <xsl:text>&#9;&#9;&#9;&#9;</xsl:text><xsl:value-of select="@name"/><xsl:text>_getDynamic(ids, j);&#10;</xsl:text> 
        <xsl:text>&#9;&#9;}&#10;</xsl:text>
        <!--      <xsl:text>&#9;}&#10;</xsl:text>
     --> 
        <xsl:text>&#9;&#10;</xsl:text>
    <xsl:text>}&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
    </xsl:template>


    
    <xsl:template match="field" mode="putDynamic">
        <xsl:if test="@type ='dynamic' or @data_type='structure' or @data_type='struct_array'"> <!-- This skips the routine for non timed fields -->
            <xsl:text>&#10;&#9;&#9;&#9;//// </xsl:text><xsl:value-of select="@name"/> : <xsl:value-of select="@path"/> : <xsl:value-of select="@data_type"/> : :<xsl:value-of select="@type"/>:<xsl:text>&#10;</xsl:text>
             <xsl:apply-templates select="." mode="putSlice">
                <xsl:with-param name="dynamicOnly" select="true()"/>
                <xsl:with-param name="staticOnly" select="false()"/>
             </xsl:apply-templates>
        </xsl:if>
    </xsl:template>


    <xsl:template match="field" mode="putStatic">
        <xsl:if test="@type !='dynamic' or not(@type) or @data_type='structure' or (@data_type='struct_array' and @type !='dynamic')"> <!-- This skips the routine for timed fields when using this template in PUT_NON_TIMED mode -->
            <xsl:text>&#10;&#9;&#9;&#9;///STATIC!! </xsl:text><xsl:value-of select="@name"/> : <xsl:value-of select="@path"/> : <xsl:value-of select="@data_type"/> : :<xsl:value-of select="@type"/>:<xsl:text>&#10;</xsl:text>
            <xsl:apply-templates select="." mode="putSlice">
                <xsl:with-param name="dynamicOnly" select="false()"/>
                <xsl:with-param name="staticOnly" select="true()"/>
            </xsl:apply-templates>
        </xsl:if>
    </xsl:template>


    
<xsl:template match="field" mode="getDynamic">
    <xsl:if test="@type ='dynamic' or @data_type='structure' or @data_type='struct_array'"> <!-- This skips the routine for non timed fields -->
    <xsl:text>&#10;&#9;&#9;&#9;//// </xsl:text><xsl:value-of select="@name"/> : <xsl:value-of select="@path"/> : <xsl:value-of select="@data_type"/> : :<xsl:value-of select="@type"/>:<xsl:text>&#10;</xsl:text>
        <xsl:apply-templates select="." mode="getSlice">
                    <xsl:with-param name="dynamicOnly" select="true()"/>
            <xsl:with-param name="staticOnly" select="false()"/>
                </xsl:apply-templates>
    </xsl:if>
    </xsl:template>


 <xsl:template match="field" mode="getStatic">
<xsl:if test="@type !='dynamic' or not(@type) or @data_type='structure' or (@data_type='struct_array' and @type !='dynamic')"> <!-- This skips the routine for timed fields when using this template in PUT_NON_TIMED mode -->
    <xsl:text>&#10;&#9;&#9;&#9;///STATIC!! </xsl:text><xsl:value-of select="@name"/> : <xsl:value-of select="@path"/> : <xsl:value-of select="@data_type"/> : :<xsl:value-of select="@type"/>:<xsl:text>&#10;</xsl:text>
        <xsl:apply-templates select="." mode="getSlice">
                    <xsl:with-param name="dynamicOnly" select="false()"/>
            <xsl:with-param name="staticOnly" select="true()"/>
                </xsl:apply-templates>
    </xsl:if>
    </xsl:template>

    <!-- field put() -->
    <xsl:template match="field[not(@data_type='structure' or @data_type='struct_array')]" mode="put">
    <xsl:param name="dynamicOnly"/>
    <xsl:param name="staticOnly"/>

    <xsl:call-template name="COMMENT_FIELD"/>
        <xsl:call-template name="setValue">
          <xsl:with-param name="path" select="translate(@path, '/', '.')"/>
        <xsl:with-param name="slice" select="false()"/>
            <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
            <xsl:with-param name="staticOnly" select="$staticOnly"/>

    </xsl:call-template>
    </xsl:template>

    <!-- field putSlice() -->
    <xsl:template match="field[not(@data_type='structure' or @data_type='struct_array')]" mode="putSlice">
    <xsl:param name="dynamicOnly"/>
    <xsl:param name="staticOnly"/>
    <xsl:call-template name="COMMENT_FIELD"/>
        <xsl:call-template name="setValue">
          <xsl:with-param name="path" select="translate(@path, '/', '.')"/>
        <xsl:with-param name="slice" select="true()"/>
            <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
            <xsl:with-param name="staticOnly" select="$staticOnly"/>

    </xsl:call-template>
    </xsl:template>

    <!-- field put() for array of structures -->
    <xsl:template match="field[@data_type='struct_array']" mode="put">
    <xsl:param name="dynamicOnly"/>
    <xsl:param name="staticOnly"/>
        <xsl:call-template name="COMMENT_FIELD"/>
      <xsl:call-template name="putStructArray">
            <xsl:with-param name="path" select="concat(translate(@path, '/', '.'), '[0]')"/>
    <xsl:with-param name="slice" select="false()"/>
            <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
            <xsl:with-param name="staticOnly" select="$staticOnly"/>
        </xsl:call-template>
   </xsl:template>

  <!-- field put() for array of structures -->
    <xsl:template match="field[@data_type='structure']" mode="put">
    <xsl:param name="dynamicOnly"/>
    <xsl:param name="staticOnly"/>
        <xsl:call-template name="COMMENT_FIELD"/>
      <xsl:call-template name="putStructArray">
            <xsl:with-param name="path" select="translate(@path, '/', '.')"/>
    <xsl:with-param name="slice" select="false()"/>
            <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
            <xsl:with-param name="staticOnly" select="$staticOnly"/>
        </xsl:call-template>
   </xsl:template>
  

    <!-- field put() for array of structures -->
    <xsl:template match="field[@data_type='struct_array']" mode="putSlice">
    <xsl:param name="dynamicOnly"/>
    <xsl:param name="staticOnly"/>
        <xsl:call-template name="COMMENT_FIELD"/>
      <xsl:call-template name="putStructArray">
            <xsl:with-param name="path" select="concat(translate(@path, '/', '.'), '[0]')"/>
    <xsl:with-param name="slice" select="true()"/>
            <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
            <xsl:with-param name="staticOnly" select="$staticOnly"/>
        </xsl:call-template>
    </xsl:template>

  <!-- field put() for array of structures -->
    <xsl:template match="field[@data_type='structure']" mode="putSlice">
    <xsl:param name="dynamicOnly"/>
    <xsl:param name="staticOnly"/>
        <xsl:call-template name="COMMENT_FIELD"/>
      <xsl:call-template name="putStructArray">
            <xsl:with-param name="path" select="translate(@path, '/', '.')"/>
    <xsl:with-param name="slice" select="true()"/>
            <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
            <xsl:with-param name="staticOnly" select="$staticOnly"/>
        </xsl:call-template>
   </xsl:template>


    <xsl:template name="putStructArray">
        <xsl:param name="path"/>
    <xsl:param name="slice"/>
    <xsl:param name="dynamicOnly"/>
    <xsl:param name="staticOnly"/>


    <xsl:if test="@data_type='struct_array'">
    <xsl:if test="(not($dynamicOnly) and ( (@type !='dynamic' or not(@type)) and not(ancestor::field[@type='dynamic' and @data_type='struct_array'])))
    or
    (not($staticOnly) and (descendant-or-self::field[@type='dynamic'] or ancestor::field[@type='dynamic' and @data_type='struct_array']))"> 
    <xsl:text>&#9;&#9;if(ids.</xsl:text><xsl:value-of select="substring($path, 1, string-length($path) - 3)"/><xsl:text> == null) &#10;</xsl:text>
    <xsl:text>&#9;&#9;{&#10;</xsl:text>
    <xsl:text>&#9;&#9;&#9;ids.</xsl:text><xsl:value-of select="substring($path, 1, string-length($path) - 3)"/><xsl:text> = new imas.</xsl:text><xsl:value-of select="ancestor::IDS/@name"/><xsl:for-each select="ancestor-or-self::field"><xsl:text>.</xsl:text><xsl:value-of select="@name"/><xsl:if test="@data_type='struct_array' or @data_type='structure'">Class</xsl:if></xsl:for-each><xsl:text>[1];&#10;</xsl:text><xsl:text>&#9;&#9;&#9;ids.</xsl:text><xsl:value-of select="substring($path, 1, string-length($path) - 3)"/><xsl:text>[0] = new imas.</xsl:text><xsl:value-of select="ancestor::IDS/@name"/><xsl:for-each select="ancestor-or-self::field"><xsl:text>.</xsl:text><xsl:value-of select="@name"/><xsl:if test="@data_type='struct_array' or @data_type='structure'">Class</xsl:if></xsl:for-each><xsl:text>();&#10;</xsl:text>
    <xsl:text>&#9;&#9;}&#10;</xsl:text>
    </xsl:if>
    </xsl:if>
        <xsl:for-each select="field[not(@data_type='struct_array' or @data_type='structure')]">
        <xsl:call-template name="COMMENT_FIELD"/>
            <xsl:call-template name="setValue">
                <xsl:with-param name="path" select="concat($path, '.', @name)"/>
            <xsl:with-param name="slice" select="$slice"/>
            <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
            <xsl:with-param name="staticOnly" select="$staticOnly"/>
        </xsl:call-template>
        </xsl:for-each>
        <xsl:for-each select="field[@data_type='structure']">
      <xsl:call-template name="COMMENT_FIELD"/>
      <xsl:call-template name="putStructArray">
                <xsl:with-param name="path" select="concat($path, '.', @name)"/>
        <xsl:with-param name="slice" select="$slice"/>
        <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
        <xsl:with-param name="staticOnly" select="$staticOnly"/>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:for-each select="field[@data_type='struct_array']">
        <xsl:call-template name="COMMENT_FIELD"/>
            <xsl:call-template name="putStructArray">
                <xsl:with-param name="path" select="concat($path, '.', @name, '[0]')"/>
        <xsl:with-param name="slice" select="$slice"/>
        <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
        <xsl:with-param name="staticOnly" select="$staticOnly"/>
            </xsl:call-template>
        </xsl:for-each>
    </xsl:template> 


    <!-- field get() -->
    <xsl:template match="field[not(@data_type='structure' or @data_type='struct_array')]" mode="get">
    <xsl:param name="dynamicOnly"/>
    <xsl:param name="staticOnly"/>
    <xsl:call-template name="COMMENT_FIELD"/>
        <xsl:call-template name="COMMENT_FIELD"/>
            <xsl:call-template name="getValue">
                <xsl:with-param name="path" select="translate(@path, '/', '.')"/>
            <xsl:with-param name="slice" select="false()"/>
            <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
            <xsl:with-param name="staticOnly" select="$staticOnly"/>
        </xsl:call-template>
    </xsl:template>


    <!-- field get() for array of structures -->
    <xsl:template match="field[@data_type='struct_array']" mode="get">
    <xsl:param name="dynamicOnly"/>
    <xsl:param name="staticOnly"/>
    <xsl:call-template name="COMMENT_FIELD"/>
       <xsl:call-template name="getStructArray">
            <xsl:with-param name="path" select="concat(translate(@path, '/', '.'), '[0]')"/>
         <xsl:with-param name="slice" select="false()"/>
            <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
            <xsl:with-param name="staticOnly" select="$staticOnly"/>
        </xsl:call-template>
  </xsl:template>

    <xsl:template match="field[@data_type='structure']" mode="get">
    <xsl:param name="dynamicOnly"/>
    <xsl:param name="staticOnly"/>
    <xsl:call-template name="COMMENT_FIELD"/>
       <xsl:call-template name="getStructArray">
            <xsl:with-param name="path" select="translate(@path, '/', '.')"/>
         <xsl:with-param name="slice" select="false()"/>
            <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
            <xsl:with-param name="staticOnly" select="$staticOnly"/>
        </xsl:call-template>
  </xsl:template>

    <!-- field getSlice() -->
    <xsl:template match="field[not(@data_type='structure' or @data_type='struct_array')]" mode="getSlice">
    <xsl:param name="dynamicOnly"/>
    <xsl:param name="staticOnly"/>
   <xsl:call-template name="COMMENT_FIELD"/>
        <xsl:call-template name="COMMENT_FIELD"/>
            <xsl:call-template name="getValue">
                <xsl:with-param name="path" select="translate(@path, '/', '.')"/>
            <xsl:with-param name="slice" select="true()"/>
            <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
            <xsl:with-param name="staticOnly" select="$staticOnly"/>
        </xsl:call-template>
    </xsl:template>

        

    <!-- field get() for array of structures -->
    <xsl:template match="field[@data_type='struct_array']" mode="getSlice">
    <xsl:param name="dynamicOnly"/>
    <xsl:param name="staticOnly"/>
    <xsl:call-template name="COMMENT_FIELD"/>
      <xsl:call-template name="getStructArray">
            <xsl:with-param name="path" select="concat(translate(@path, '/', '.'), '[0]')"/>
         <xsl:with-param name="slice" select="true()"/>
            <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
            <xsl:with-param name="staticOnly" select="$staticOnly"/>
        </xsl:call-template>
    </xsl:template>

    <!-- field get() for array of structures -->
    <xsl:template match="field[@data_type='structure']" mode="getSlice">
    <xsl:param name="dynamicOnly"/>
    <xsl:param name="staticOnly"/>
    <xsl:call-template name="COMMENT_FIELD"/>
      <xsl:call-template name="getStructArray">
            <xsl:with-param name="path" select="translate(@path, '/', '.')"/>
         <xsl:with-param name="slice" select="true()"/>
            <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
            <xsl:with-param name="staticOnly" select="$staticOnly"/>
        </xsl:call-template>
    </xsl:template>
    


    <xsl:template name="getStructArray">
        <xsl:param name="path"/>
    <xsl:param name="slice"/>
    <xsl:param name="dynamicOnly"/>
    <xsl:param name="staticOnly"/>
        <xsl:for-each select="field[not(@data_type='struct_array' or @data_type='structure')]">
        <xsl:call-template name="COMMENT_FIELD"/>
            <xsl:call-template name="getValue">
                <xsl:with-param name="path" select="concat($path, '.', @name)"/>
            <xsl:with-param name="slice" select="$slice"/>
            <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
            <xsl:with-param name="staticOnly" select="$staticOnly"/>
        </xsl:call-template>
        </xsl:for-each>
        <xsl:for-each select="field[@data_type='structure']">
        <xsl:call-template name="COMMENT_FIELD"/>
        <xsl:call-template name="getStructArray">
                    <xsl:with-param name="path" select="concat($path, '.', @name)"/>
            <xsl:with-param name="slice" select="$slice"/>
        <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
        <xsl:with-param name="staticOnly" select="$staticOnly"/>
                </xsl:call-template>
        </xsl:for-each>
        <xsl:for-each select="field[@data_type='struct_array']">
            <xsl:call-template name="COMMENT_FIELD"/>
            <xsl:call-template name="getStructArray">
                <xsl:with-param name="path" select="concat($path, '.', @name, '[0]')"/>
         <xsl:with-param name="slice" select="$slice"/>
        <xsl:with-param name="dynamicOnly" select="$dynamicOnly"/>
        <xsl:with-param name="staticOnly" select="$staticOnly"/>
            </xsl:call-template>
        </xsl:for-each>
    </xsl:template>
    
   <xsl:template name="getValue">
        <xsl:param name="path"/>
    <xsl:param name="slice"/>
    <xsl:param name="dynamicOnly"/>
    <xsl:param name="staticOnly"/>
    
    <xsl:if test="(not($dynamicOnly) and ( (@type !='dynamic' or not(@type)) and not(ancestor::field[@type='dynamic' and @data_type='struct_array'])))
    or
    (not($staticOnly) and (@type='dynamic' or ancestor::field[@type='dynamic' and @data_type='struct_array']))"> 
    <xsl:if test="@type='dynamic' or ancestor::field[@type='dynamic' and @data_type='struct_array']"> 
    if(IDS_TIME_MODE != LowLevel.IDS_TIME_MODE_INDEPENDENT)
    {
    </xsl:if>
    <xsl:choose>
        <xsl:when test="@name='homogeneous_time'">
            <xsl:text>&#9;&#9;// NOT TESTED: ids.</xsl:text><xsl:value-of select="@path"/><xsl:text> = IDS_TIME_MODE;&#10;</xsl:text>
        </xsl:when>
        <xsl:when test="@path='ids_properties/version_put/data_dictionary'
                     or @path='ids_properties/version_put/access_layer'
                     or @path='ids_properties/version_put/access_layer_language'">
            <xsl:text>&#9;&#9;// Provenance data are not tested;&#10;</xsl:text>
        </xsl:when>
        <xsl:when test="@name='time' and @type='dynamic' and (@data_type='flt_1d_type' or @data_type='FLT_1D') ">
            <xsl:choose>
                <xsl:when test="$slice and @type='dynamic' and not(ancestor::field[@data_type='struct_array' and @maxoccur='unbounded'])  ">
                        <xsl:text>&#9;&#9;status = Checker.checkTime(ids.</xsl:text><xsl:value-of select="$path"/><xsl:text>, "</xsl:text><xsl:value-of select="ancestor::IDS/@name"/><xsl:text>/</xsl:text><xsl:value-of select="@path"/><xsl:text>", timeIdx);&#10;</xsl:text>
                    <xsl:text>&#9;&#9;checkStatus(status);&#10;</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                    <xsl:text>&#9;&#9;status = Checker.checkTime(ids.</xsl:text><xsl:value-of select="$path"/><xsl:text>, "</xsl:text><xsl:value-of select="ancestor::IDS/@name"/><xsl:text>/</xsl:text><xsl:value-of select="@path"/><xsl:text>", -1);&#10;</xsl:text>
                    <xsl:text>&#9;&#9;checkStatus(status);&#10;</xsl:text>
                        </xsl:otherwise>
                    </xsl:choose>
        </xsl:when>

        <xsl:otherwise>
            <xsl:choose>
                <xsl:when test="$slice and @type='dynamic' and not(ancestor::field[@data_type='struct_array' and @maxoccur='unbounded'])  ">
                    <xsl:text>&#9;&#9;status = Checker.checkData(ids.</xsl:text><xsl:value-of select="$path"/><xsl:text>, "</xsl:text><xsl:value-of select="ancestor::IDS/@name"/><xsl:text>/</xsl:text><xsl:value-of select="@path"/><xsl:text>", true);&#10;</xsl:text>
                    <xsl:text>&#9;&#9;checkStatus(status);&#10;</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                    <xsl:text>&#9;&#9;status = Checker.checkData(ids.</xsl:text><xsl:value-of select="$path"/><xsl:text>, "</xsl:text><xsl:value-of select="ancestor::IDS/@name"/><xsl:text>/</xsl:text><xsl:value-of select="@path"/><xsl:text>", false);&#10;</xsl:text>
                    <xsl:text>&#9;&#9;checkStatus(status);&#10;</xsl:text>
                        </xsl:otherwise>
                    </xsl:choose>

         
        </xsl:otherwise>
        </xsl:choose>
    <xsl:if test="@type='dynamic' or ancestor::field[@type='dynamic' and @data_type='struct_array']"> 
    }
    </xsl:if>
  </xsl:if>
   </xsl:template>


   <xsl:template name="setValue">
        <xsl:param name="path"/>
    <xsl:param name="slice"/>
    <xsl:param name="dynamicOnly"/>
    <xsl:param name="staticOnly"/>
    
    <xsl:if test="(not($dynamicOnly) and ( (@type !='dynamic' or not(@type)) and not(ancestor::field[@type='dynamic' and @data_type='struct_array'])))
    or
    (not($staticOnly) and (@type='dynamic' or ancestor::field[@type='dynamic' and @data_type='struct_array']))"> 
    <xsl:if test="@type='dynamic' or ancestor::field[@type='dynamic' and @data_type='struct_array']"> 
    if(IDS_TIME_MODE != LowLevel.IDS_TIME_MODE_INDEPENDENT)
    </xsl:if>
    <xsl:choose>
        <xsl:when test="@name='homogeneous_time'">
            <xsl:text>&#9;&#9;ids.</xsl:text><xsl:value-of select="$path"/><xsl:text> = IDS_TIME_MODE;&#10;</xsl:text>
        </xsl:when>
        <xsl:when test="@path='ids_properties/version_put/data_dictionary'
                     or @path='ids_properties/version_put/access_layer'
                     or @path='ids_properties/version_put/access_layer_language'">
             <xsl:text>&#9;&#9;// Provenance data are not tested;&#10;</xsl:text>
        </xsl:when>
        <xsl:when test="@name='time' and @type='dynamic' and (@data_type='flt_1d_type' or @data_type='FLT_1D') ">
            <xsl:choose>
                <xsl:when test="$slice and @type='dynamic' and not(ancestor::field[@data_type='struct_array' and @maxoccur='unbounded'])  ">
                        <xsl:text>&#9;&#9;ids.</xsl:text><xsl:value-of select="$path"/><xsl:text> = Generator.getTimeVector(timeIdx);&#10;</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>&#9;&#9;ids.</xsl:text><xsl:value-of select="$path"/><xsl:text> = Generator.getTimeVector(-1);&#10;</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
        </xsl:when>

        <xsl:otherwise>
            <xsl:choose>
                <xsl:when test="$slice and @type='dynamic' and not(ancestor::field[@data_type='struct_array' and @maxoccur='unbounded'])  ">
                        <xsl:text>&#9;&#9;ids.</xsl:text><xsl:value-of select="$path"/><xsl:text> = Generator.randomData(ids.</xsl:text><xsl:value-of select="$path"/><xsl:text>, true);&#10;</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>&#9;&#9;ids.</xsl:text><xsl:value-of select="$path"/><xsl:text> = Generator.randomData(ids.</xsl:text><xsl:value-of select="$path"/><xsl:text>, false);&#10;</xsl:text>
                    </xsl:otherwise>
            </xsl:choose>
        </xsl:otherwise>
        </xsl:choose>
  </xsl:if>
   </xsl:template>


        <!--Documentation for a single field-->
    <xsl:template name = "COMMENT_FIELD">
        <xsl:text>&#xA;</xsl:text>
    <xsl:choose>
    <xsl:when test="@data_type='struct_array' or @data_type='structure'">
        <xsl:text>&#009;&#009;//-----------------------------------------------------------------------------------------#&#xA;</xsl:text>
        <xsl:if test="@data_type='structure'">
            <xsl:text>&#009;&#009;//  STRUCTURE &#xA;</xsl:text>
        </xsl:if>
        <xsl:if test="@type='dynamic' and @maxoccur='unbounded' and @data_type='struct_array'">
            <xsl:text>&#009;&#009;//  ARRAY of TYPE 3 &#xA;</xsl:text>
        </xsl:if>
            <xsl:if test="(not(@type) or @type!='dynamic') and @maxoccur='unbounded' and @data_type='struct_array'">
            <xsl:text>&#009;&#009;//  ARRAY of TYPE 2 &#xA;</xsl:text>
        </xsl:if>

        <xsl:if test="@maxoccur!='unbounded' and @data_type='struct_array'">
            <xsl:text>&#009;&#009;//  ARRAY of TYPE 1 &#xA;</xsl:text>
        </xsl:if>
        <xsl:text>&#009;&#009;//  </xsl:text><xsl:value-of select="@name"/> : <xsl:value-of select="@path"/> : <xsl:value-of select="@data_type"/> : <xsl:value-of select="@type"/><xsl:text>&#xA;</xsl:text>
        <xsl:text>&#009;&#009;//-----------------------------------------------------------------------------------------#&#xA;</xsl:text>
    </xsl:when>
    <xsl:otherwise>
        <xsl:text>&#009;&#009;//  </xsl:text><xsl:value-of select="@name"/> : <xsl:value-of select="@path"/> : <xsl:value-of select="@data_type"/> : <xsl:value-of select="@type"/><xsl:text>&#xA;</xsl:text>
    </xsl:otherwise>
 </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
