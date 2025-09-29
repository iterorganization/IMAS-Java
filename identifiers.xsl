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
      
      <xsl:text>    public static class TypeData {&#xA;</xsl:text>
      <xsl:text>        public int index;&#xA;</xsl:text>
      <xsl:text>        public String description;&#xA;</xsl:text>
      <xsl:text>        &#xA;</xsl:text>
      <xsl:text>        public TypeData(int index, String description) {&#xA;</xsl:text>
      <xsl:text>            this.index = index;&#xA;</xsl:text>
      <xsl:text>            this.description = description;&#xA;</xsl:text>
      <xsl:text>        }&#xA;</xsl:text>
      <xsl:text>    }&#xA;&#xA;</xsl:text>
      
      <xsl:text>    public static TypeData getTypeDataByName(String name) throws IllegalArgumentException {&#xA;</xsl:text>
      <xsl:for-each select="//constants/int[@name]">
        <xsl:text>        if ("</xsl:text><xsl:value-of select="@name"/><xsl:text>".equals(name)) {&#xA;</xsl:text>
        <xsl:text>            return new TypeData(</xsl:text><xsl:value-of select="."/><xsl:text>, "</xsl:text><xsl:value-of select="@description"/><xsl:text>");&#xA;</xsl:text>
        <xsl:text>        }&#xA;</xsl:text>
        <xsl:if test="@alias">
          <xsl:text>        if ("</xsl:text><xsl:value-of select="@alias"/><xsl:text>".equals(name)) {&#xA;</xsl:text>
          <xsl:text>            return new TypeData(</xsl:text><xsl:value-of select="."/><xsl:text>, "</xsl:text><xsl:value-of select="@description"/><xsl:text>");&#xA;</xsl:text>
          <xsl:text>        }&#xA;</xsl:text>
        </xsl:if>
      </xsl:for-each>
      <xsl:text>        // Unknown identifier - throw exception&#xA;</xsl:text>
      <xsl:text>        throw new IllegalArgumentException("Unknown identifier: '" + name + "'");&#xA;</xsl:text>
      <xsl:text>    }&#xA;&#xA;</xsl:text>
      
      <xsl:text>    // Utility interface for objects that can have identifiers set&#xA;</xsl:text>
      <xsl:text>    public interface IdentifiableObject {&#xA;</xsl:text>
      <xsl:text>        void setIndex(int index);&#xA;</xsl:text>
      <xsl:text>        void setName(String name);&#xA;</xsl:text>
      <xsl:text>        void setDescription(String description);&#xA;</xsl:text>
      <xsl:text>    }&#xA;&#xA;</xsl:text>
      
      <xsl:text>    // Utility interface for objects that can have array identifiers set&#xA;</xsl:text>
      <xsl:text>    public interface IdentifiableArrayObject {&#xA;</xsl:text>
      <xsl:text>        void setIndices(int[] indices);&#xA;</xsl:text>
      <xsl:text>        void setNames(String[] names);&#xA;</xsl:text>
      <xsl:text>        void setDescriptions(String[] descriptions);&#xA;</xsl:text>
      <xsl:text>    }&#xA;&#xA;</xsl:text>
      
      <xsl:text>    // Setter for an object&#xA;</xsl:text>
      <xsl:text>    public static void setIdentifier(IdentifiableObject obj, String name) throws IllegalArgumentException {&#xA;</xsl:text>
      <xsl:text>        try {&#xA;</xsl:text>
      <xsl:text>            TypeData typeData = getTypeDataByName(name);&#xA;</xsl:text>
      <xsl:text>            obj.setIndex(typeData.index);&#xA;</xsl:text>
      <xsl:text>            obj.setName(getName(typeData.index));&#xA;</xsl:text>
      <xsl:text>            obj.setDescription(typeData.description);&#xA;</xsl:text>
      <xsl:text>        } catch (IllegalArgumentException e) {&#xA;</xsl:text>
      <xsl:text>            // Re-throw with more context&#xA;</xsl:text>
      <xsl:text>            throw new IllegalArgumentException("Failed to set identifier: " + e.getMessage(), e);&#xA;</xsl:text>
      <xsl:text>        }&#xA;</xsl:text>
      <xsl:text>    }&#xA;&#xA;</xsl:text>
      
      <xsl:text>    // Setter for an array&#xA;</xsl:text>
      <xsl:text>    public static void setIdentifier(IdentifiableArrayObject obj, String[] names) throws IllegalArgumentException {&#xA;</xsl:text>
      <xsl:text>        int[] indices = new int[names.length];&#xA;</xsl:text>
      <xsl:text>        String[] nameResults = new String[names.length];&#xA;</xsl:text>
      <xsl:text>        String[] descriptions = new String[names.length];&#xA;</xsl:text>
      <xsl:text>        &#xA;</xsl:text>
      <xsl:text>        for (int i = 0; i &lt; names.length; i++) {&#xA;</xsl:text>
      <xsl:text>            try {&#xA;</xsl:text>
      <xsl:text>                TypeData typeData = getTypeDataByName(names[i]);&#xA;</xsl:text>
      <xsl:text>                indices[i] = typeData.index;&#xA;</xsl:text>
      <xsl:text>                nameResults[i] = getName(typeData.index);&#xA;</xsl:text>
      <xsl:text>                descriptions[i] = typeData.description;&#xA;</xsl:text>
      <xsl:text>            } catch (IllegalArgumentException e) {&#xA;</xsl:text>
      <xsl:text>                // Re-throw with array index context&#xA;</xsl:text>
      <xsl:text>                throw new IllegalArgumentException("Failed to set identifier at index " + i + ": " + e.getMessage(), e);&#xA;</xsl:text>
      <xsl:text>            }&#xA;</xsl:text>
      <xsl:text>        }&#xA;</xsl:text>
      <xsl:text>        &#xA;</xsl:text>
      <xsl:text>        obj.setIndices(indices);&#xA;</xsl:text>
      <xsl:text>        obj.setNames(nameResults);&#xA;</xsl:text>
      <xsl:text>        obj.setDescriptions(descriptions);&#xA;</xsl:text>
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
