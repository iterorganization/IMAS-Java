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
    <xsl:apply-templates select="*[name()!='header' and name()!='include']" mode="Java"/>
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

<xsl:template match="int" mode="Java">
  <xsl:text>&#009;public static final int </xsl:text>
  <xsl:value-of select="@name"/>
  <xsl:text>&#009; = </xsl:text>
  <xsl:value-of select="."/>
  <xsl:text>;</xsl:text>
  <xsl:value-of select="my:desc('//')"/>
</xsl:template>

<xsl:template match="float" mode="Java">
  <xsl:text>&#009;public static final double </xsl:text>
  <xsl:value-of select="@name"/>
  <xsl:choose>
    <xsl:when test="@alias!='' or @alias='true'">
      <xsl:text>;</xsl:text>
      <xsl:value-of select="my:desc('//')"/>
      <xsl:text>&#009;static {&#xA;&#009;&#009;</xsl:text>
      <xsl:value-of select="@name"/>
      <xsl:text> = </xsl:text>
      <xsl:value-of select="."/>
      <xsl:text>;&#xA;&#009;}&#xA;</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>&#009; = </xsl:text>
      <xsl:value-of select="."/>
      <xsl:text>;</xsl:text>
      <xsl:value-of select="my:desc('//')"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="string" mode="Java">
  <xsl:text>&#009;public static final String </xsl:text>
  <xsl:value-of select="@name"/>
  <xsl:text>&#009; = "</xsl:text>
  <xsl:value-of select="."/><xsl:text>";</xsl:text>
  <xsl:value-of select="my:desc('//')"/>
</xsl:template>

<xsl:template match="comment" mode="Java">
  <xsl:call-template name="replace-string">
    <xsl:with-param name="text" select="concat('&#xA;',text())"/>
    <xsl:with-param name="replace" select="'&#xA;'"/>
    <xsl:with-param name="with" select="'&#xA;// '"/>
  </xsl:call-template>
  <xsl:text>&#xA;</xsl:text>
</xsl:template>

</xsl:stylesheet>
