<?xml version="1.0" encoding="UTF-8"?>
<?modxslt-stylesheet type="text/xsl" media="fuffa, screen and $GET[stylesheet]" href="./%24GET%5Bstylesheet%5D" alternate="no" title="Translation using provided stylesheet" charset="ISO-8859-1" ?>
<?modxslt-stylesheet type="text/xsl" media="screen" alternate="no" title="Show raw source of the XML file" charset="ISO-8859-1" ?>

<xsl:stylesheet 
   xmlns:yaslt="http://www.mod-xslt2.com/ns/1.0"
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
   xmlns:xs="http://www.w3.org/2001/XMLSchema" 
   xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
   xmlns:exsl="http://exslt.org/common"
   xmlns:str="http://exslt.org/strings"
   xmlns:func="http://exslt.org/functions"
   xmlns:my="http://localhost.localdomain/localns"
   exclude-result-prefixes="my"
   extension-element-prefixes="yaslt exsl func str">
   <xsl:include href="./identifiers.common.xsl"/>

<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>

<!-- MAIN, FILE GENERATION -->
<xsl:template match="/constants">

  <!-- JAVA FILE -->
  <xsl:variable name="jname">
    <xsl:value-of select="($name)"/>    
  </xsl:variable>
  <exsl:document href="{$prefix}{$jname}.java" method="text">
    <xsl:text>//package ... ;&#xA;</xsl:text>
    <xsl:text>import imasjava.*;&#xA;</xsl:text>
    <xsl:apply-templates select="header" mode="Java"/>
    <xsl:apply-templates select="include[@name='Java']"/><xsl:text>&#xA;&#xA;</xsl:text>
    <xsl:text>&#xA;&#xA;public final class </xsl:text>
    <xsl:value-of select="$jname"/><xsl:text> {&#xA;&#xA;</xsl:text>
    <xsl:apply-templates select="comment" mode="Java"/>
    
    <xsl:if test="//constants[@create_mapping_function]">
      <xsl:text>&#xA;    // Mapping functionality&#xA;</xsl:text>
      <xsl:text>    public static int getIndex(String name) {&#xA;</xsl:text>
      <xsl:for-each select="//constants/int[@name]">
        <xsl:text>        if ("</xsl:text><xsl:value-of select="@name"/><xsl:text>".equals(name)) {&#xA;</xsl:text>
        <xsl:text>            return </xsl:text><xsl:value-of select="."/><xsl:text>;&#xA;</xsl:text>
        <xsl:text>        }&#xA;</xsl:text>
        <xsl:if test="@alias">
          <xsl:text>        if ("</xsl:text><xsl:value-of select="@alias"/><xsl:text>".equals(name)) {&#xA;</xsl:text>
          <xsl:text>            return </xsl:text><xsl:value-of select="."/><xsl:text>;&#xA;</xsl:text>
          <xsl:text>        }&#xA;</xsl:text>
        </xsl:if>
      </xsl:for-each>
      <xsl:text>        return -999999999; // Unknown identifier&#xA;</xsl:text>
      <xsl:text>    }&#xA;&#xA;</xsl:text>
      
      <xsl:text>    public static String getDescription(int idx) {&#xA;</xsl:text>
      <xsl:text>        switch(idx) {&#xA;</xsl:text>
      <xsl:for-each select="//constants/int[@name]">
        <xsl:text>            case </xsl:text>
        <xsl:value-of select="."/>    
        <xsl:text>:&#xA;</xsl:text>
        <xsl:text>                return "</xsl:text>
        <xsl:value-of select="@description"/>    
        <xsl:text>";&#xA;</xsl:text>
      </xsl:for-each>
      <xsl:text>        }&#xA;</xsl:text>
      <xsl:text>        return "unknown";&#xA;</xsl:text>
      <xsl:text>    }&#xA;&#xA;</xsl:text>
      
      <xsl:text>    public static String getName(int idx) {&#xA;</xsl:text>
      <xsl:text>        switch(idx) {&#xA;</xsl:text>
      <xsl:for-each select="//constants/int[@name]">
        <xsl:text>            case </xsl:text>
        <xsl:value-of select="."/>    
        <xsl:text>:&#xA;</xsl:text>
        <xsl:text>                return "</xsl:text>
        <xsl:value-of select="@name"/>    
        <xsl:text>";&#xA;</xsl:text>
      </xsl:for-each>
      <xsl:text>        }&#xA;</xsl:text>
      <xsl:text>        return "unknown";&#xA;</xsl:text>
      <xsl:text>    }&#xA;&#xA;</xsl:text>
      
      <xsl:text>    public static class IdentifierData {&#xA;</xsl:text>
      <xsl:text>        public int index;&#xA;</xsl:text>
      <xsl:text>        public String description;&#xA;</xsl:text>
      <xsl:text>        &#xA;</xsl:text>
      <xsl:text>        public IdentifierData(int index, String description) {&#xA;</xsl:text>
      <xsl:text>            this.index = index;&#xA;</xsl:text>
      <xsl:text>            this.description = description;&#xA;</xsl:text>
      <xsl:text>        }&#xA;</xsl:text>
      <xsl:text>    }&#xA;&#xA;</xsl:text>
      
      <xsl:text>    public static IdentifierData getIdentifierDataByName(String name) throws IllegalArgumentException {&#xA;</xsl:text>
      <xsl:for-each select="//constants/int[@name]">
        <xsl:text>        if ("</xsl:text><xsl:value-of select="@name"/><xsl:text>".equals(name)) {&#xA;</xsl:text>
        <xsl:text>            return new IdentifierData(</xsl:text><xsl:value-of select="."/><xsl:text>, "</xsl:text><xsl:value-of select="@description"/><xsl:text>");&#xA;</xsl:text>
        <xsl:text>        }&#xA;</xsl:text>
        <xsl:if test="@alias">
          <xsl:text>        if ("</xsl:text><xsl:value-of select="@alias"/><xsl:text>".equals(name)) {&#xA;</xsl:text>
          <xsl:text>            return new IdentifierData(</xsl:text><xsl:value-of select="."/><xsl:text>, "</xsl:text><xsl:value-of select="@description"/><xsl:text>");&#xA;</xsl:text>
          <xsl:text>        }&#xA;</xsl:text>
        </xsl:if>
      </xsl:for-each>
      <xsl:text>        // Unknown identifier - throw exception&#xA;</xsl:text>
      <xsl:text>        throw new IllegalArgumentException("Unknown identifier: '" + name + "'");&#xA;</xsl:text>
      <xsl:text>    }&#xA;&#xA;</xsl:text>
      
      <xsl:text>    // Setter for any object using reflection&#xA;</xsl:text>
      <xsl:text>    public static void setIdentifier(Object obj, String name) throws IllegalArgumentException {&#xA;</xsl:text>
      <xsl:text>        try {&#xA;</xsl:text>
      <xsl:text>            IdentifierData IdentifierData = getIdentifierDataByName(name);&#xA;</xsl:text>
      <xsl:text>            String objName = getName(IdentifierData.index);&#xA;</xsl:text>
      <xsl:text>            &#xA;</xsl:text>
      <xsl:text>            // Use reflection to set the properties&#xA;</xsl:text>
      <xsl:text>            Class&lt;?&gt; clazz = obj.getClass();&#xA;</xsl:text>
      <xsl:text>            &#xA;</xsl:text>
      <xsl:text>            // Set index field&#xA;</xsl:text>
      <xsl:text>            try {&#xA;</xsl:text>
      <xsl:text>                java.lang.reflect.Field indexField = clazz.getField("index");&#xA;</xsl:text>
      <xsl:text>                indexField.setAccessible(true);&#xA;</xsl:text>
      <xsl:text>                indexField.set(obj, IdentifierData.index);&#xA;</xsl:text>
      <xsl:text>            } catch (NoSuchFieldException | IllegalAccessException e) {&#xA;</xsl:text>
      <xsl:text>                throw new IllegalArgumentException("Cannot set index on object of type " + clazz.getSimpleName() + ": no index field found", e);&#xA;</xsl:text>
      <xsl:text>            }&#xA;</xsl:text>
      <xsl:text>            &#xA;</xsl:text>
      <xsl:text>            // Set name field&#xA;</xsl:text>
      <xsl:text>            try {&#xA;</xsl:text>
      <xsl:text>                java.lang.reflect.Field nameField = clazz.getField("name");&#xA;</xsl:text>
      <xsl:text>                nameField.setAccessible(true);&#xA;</xsl:text>
      <xsl:text>                nameField.set(obj, objName);&#xA;</xsl:text>
      <xsl:text>            } catch (NoSuchFieldException | IllegalAccessException e) {&#xA;</xsl:text>
      <xsl:text>                throw new IllegalArgumentException("Cannot set name on object of type " + clazz.getSimpleName() + ": no name field found", e);&#xA;</xsl:text>
      <xsl:text>            }&#xA;</xsl:text>
      <xsl:text>            &#xA;</xsl:text>
      <xsl:text>            // Set description field&#xA;</xsl:text>
      <xsl:text>            try {&#xA;</xsl:text>
      <xsl:text>                java.lang.reflect.Field descriptionField = clazz.getField("description");&#xA;</xsl:text>
      <xsl:text>                descriptionField.setAccessible(true);&#xA;</xsl:text>
      <xsl:text>                descriptionField.set(obj, IdentifierData.description);&#xA;</xsl:text>
      <xsl:text>            } catch (NoSuchFieldException | IllegalAccessException e) {&#xA;</xsl:text>
      <xsl:text>                throw new IllegalArgumentException("Cannot set description on object of type " + clazz.getSimpleName() + ": no description field found", e);&#xA;</xsl:text>
      <xsl:text>            }&#xA;</xsl:text>
      <xsl:text>            &#xA;</xsl:text>
      <xsl:text>        } catch (IllegalArgumentException e) {&#xA;</xsl:text>
      <xsl:text>            // Re-throw with more context&#xA;</xsl:text>
      <xsl:text>            throw new IllegalArgumentException("Failed to set identifier: " + e.getMessage(), e);&#xA;</xsl:text>
      <xsl:text>        }&#xA;</xsl:text>
      <xsl:text>    }&#xA;&#xA;</xsl:text>
      
      
      <xsl:text>    // Setter for any object with array fields using reflection&#xA;</xsl:text>
      <xsl:text>    public static void setIdentifier(Object obj, String[] names) throws IllegalArgumentException {&#xA;</xsl:text>
      <xsl:text>        int[] indices = new int[names.length];&#xA;</xsl:text>
      <xsl:text>        String[] nameResults = new String[names.length];&#xA;</xsl:text>
      <xsl:text>        String[] descriptions = new String[names.length];&#xA;</xsl:text>
      <xsl:text>        &#xA;</xsl:text>
      <xsl:text>        for (int i = 0; i &lt; names.length; i++) {&#xA;</xsl:text>
      <xsl:text>            try {&#xA;</xsl:text>
      <xsl:text>                IdentifierData IdentifierData = getIdentifierDataByName(names[i]);&#xA;</xsl:text>
      <xsl:text>                indices[i] = IdentifierData.index;&#xA;</xsl:text>
      <xsl:text>                nameResults[i] = getName(IdentifierData.index);&#xA;</xsl:text>
      <xsl:text>                descriptions[i] = IdentifierData.description;&#xA;</xsl:text>
      <xsl:text>            } catch (IllegalArgumentException e) {&#xA;</xsl:text>
      <xsl:text>                // Re-throw with array index context&#xA;</xsl:text>
      <xsl:text>                throw new IllegalArgumentException("Failed to set identifier at index " + i + ": " + e.getMessage(), e);&#xA;</xsl:text>
      <xsl:text>            }&#xA;</xsl:text>
      <xsl:text>        }&#xA;</xsl:text>
      <xsl:text>        &#xA;</xsl:text>
      <xsl:text>        // Create Vect1D objects for indices, names and descriptions&#xA;</xsl:text>
      <xsl:text>        imasjava.Vect1DInt indicesVect = new imasjava.Vect1DInt(indices);&#xA;</xsl:text>
      <xsl:text>        imasjava.Vect1DString namesVect = new imasjava.Vect1DString(nameResults);&#xA;</xsl:text>
      <xsl:text>        imasjava.Vect1DString descriptionsVect = new imasjava.Vect1DString(descriptions);&#xA;</xsl:text>
      <xsl:text>        &#xA;</xsl:text>
      <xsl:text>        // Use reflection to set the vector properties&#xA;</xsl:text>
      <xsl:text>        Class&lt;?&gt; clazz = obj.getClass();&#xA;</xsl:text>
      <xsl:text>        &#xA;</xsl:text>
      <xsl:text>        // Set indices field&#xA;</xsl:text>
      <xsl:text>        try {&#xA;</xsl:text>
      <xsl:text>            java.lang.reflect.Field indicesField = clazz.getField("indices");&#xA;</xsl:text>
      <xsl:text>            indicesField.setAccessible(true);&#xA;</xsl:text>
      <xsl:text>            indicesField.set(obj, indicesVect);&#xA;</xsl:text>
      <xsl:text>        } catch (NoSuchFieldException | IllegalAccessException e) {&#xA;</xsl:text>
      <xsl:text>            throw new IllegalArgumentException("Cannot set indices on object: " + e.getMessage(), e);&#xA;</xsl:text>
      <xsl:text>        }&#xA;</xsl:text>
      <xsl:text>        &#xA;</xsl:text>
      <xsl:text>        // Set names field&#xA;</xsl:text>
      <xsl:text>        try {&#xA;</xsl:text>
      <xsl:text>            java.lang.reflect.Field namesField = clazz.getField("names");&#xA;</xsl:text>
      <xsl:text>            namesField.setAccessible(true);&#xA;</xsl:text>
      <xsl:text>            namesField.set(obj, namesVect);&#xA;</xsl:text>
      <xsl:text>        } catch (NoSuchFieldException | IllegalAccessException e) {&#xA;</xsl:text>
      <xsl:text>            throw new IllegalArgumentException("Cannot set names on object: " + e.getMessage(), e);&#xA;</xsl:text>
      <xsl:text>        }&#xA;</xsl:text>
      <xsl:text>        &#xA;</xsl:text>
      <xsl:text>        // Set descriptions field&#xA;</xsl:text>
      <xsl:text>        try {&#xA;</xsl:text>
      <xsl:text>            java.lang.reflect.Field descriptionsField = clazz.getField("descriptions");&#xA;</xsl:text>
      <xsl:text>            descriptionsField.setAccessible(true);&#xA;</xsl:text>
      <xsl:text>            descriptionsField.set(obj, descriptionsVect);&#xA;</xsl:text>
      <xsl:text>        } catch (NoSuchFieldException | IllegalAccessException e) {&#xA;</xsl:text>
      <xsl:text>            throw new IllegalArgumentException("Cannot set descriptions on object: " + e.getMessage(), e);&#xA;</xsl:text>
      <xsl:text>        }&#xA;</xsl:text>
      <xsl:text>    }&#xA;</xsl:text>
    </xsl:if>
    
    <xsl:text>&#xA;}&#xA;</xsl:text>
  </exsl:document>

  <!-- DOCBOOK FILE -->
  <exsl:document href="{$prefix}{$name}.xml" method="text">
    <xsl:call-template name="docbook"/>
  </exsl:document>

</xsl:template>

<!-- JAVA TEMPLATES -->

<xsl:template match="header" mode="Java">
  <xsl:call-template name="replace-string">
    <xsl:with-param name="text" select="concat('&#xA;',text())"/>
    <xsl:with-param name="replace" select="'&#xA;'"/>
    <xsl:with-param name="with" select="'&#xA;// '"/>
  </xsl:call-template>
</xsl:template>

<!-- Removed static final variable generation templates -->

<xsl:template match="comment" mode="Java">
  <xsl:call-template name="replace-string">
    <xsl:with-param name="text" select="concat('&#xA;',text())"/>
    <xsl:with-param name="replace" select="'&#xA;'"/>
    <xsl:with-param name="with" select="'&#xA;// '"/>
  </xsl:call-template>
  <xsl:text>&#xA;</xsl:text>
</xsl:template>

</xsl:stylesheet>
