<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
    <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>

    <xsl:param name="versionParam" select="'1.0'"/>
    <xsl:param name="imageUrl"/>
    <xsl:param name="legenduri"/>

    <xsl:variable name="noordpijl" select="'images/pzh_noordpijl.jpg'"/>

    <!-- master set -->
    <xsl:template name="layout-master-set">
        <fo:layout-master-set>
            <fo:simple-page-master master-name="a4-liggend" page-height="210mm" page-width="297mm" margin-top="0.4cm" margin-bottom="0.4cm" margin-left="0.4cm" margin-right="0.5cm">
                <fo:region-body region-name="body"/>
            </fo:simple-page-master>
        </fo:layout-master-set>
    </xsl:template>

    <!-- styles -->
    <xsl:attribute-set name="title-font">
        <xsl:attribute name="font-size">15pt</xsl:attribute>
        <xsl:attribute name="color">#ffffff</xsl:attribute>
    </xsl:attribute-set>

    <xsl:attribute-set name="default-font">
        <xsl:attribute name="font-size">12pt</xsl:attribute>
        <xsl:attribute name="color">#000000</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="simple-border">
        <xsl:attribute name="border-top-color">#000000</xsl:attribute>
        <xsl:attribute name="border-top-style">solid</xsl:attribute>
        <xsl:attribute name="border-top-width">medium</xsl:attribute>
        <xsl:attribute name="border-bottom-color">#000000</xsl:attribute>
        <xsl:attribute name="border-bottom-style">solid</xsl:attribute>
        <xsl:attribute name="border-bottom-width">medium</xsl:attribute>
        <xsl:attribute name="border-left-color">#000000</xsl:attribute>
        <xsl:attribute name="border-left-style">solid</xsl:attribute>
        <xsl:attribute name="border-left-width">medium</xsl:attribute>
        <xsl:attribute name="border-right-color">#000000</xsl:attribute>
        <xsl:attribute name="border-right-style">solid</xsl:attribute>
        <xsl:attribute name="border-right-width">medium</xsl:attribute>
    </xsl:attribute-set>

    <xsl:attribute-set name="column-block">
        <xsl:attribute name="position">absolute</xsl:attribute>
        <xsl:attribute name="top">0cm</xsl:attribute>
        <xsl:attribute name="left">0cm</xsl:attribute>
        <xsl:attribute name="width">100%</xsl:attribute>
    </xsl:attribute-set>

    <xsl:attribute-set name="column-block-border" use-attribute-sets="simple-border">
        <xsl:attribute name="position">absolute</xsl:attribute>
        <xsl:attribute name="top">0cm</xsl:attribute>
        <xsl:attribute name="left">0cm</xsl:attribute>
        <xsl:attribute name="width">100%</xsl:attribute>
    </xsl:attribute-set>

    <!-- root -->
    <xsl:template match="info">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xlink="http://www.w3.org/1999/xlink">
            <xsl:call-template name="layout-master-set"/>
            
            <fo:page-sequence master-reference="a4-liggend">
                <fo:flow flow-name="body">
                    <fo:block-container width="26.9cm" height="1.5cm" top="0cm" left="0cm" background-color="#4C0033" xsl:use-attribute-sets="column-block">
                        <xsl:call-template name="title-block"/>
                    </fo:block-container>
                    <fo:block-container width="1.5cm" height="1.5cm" top="0cm" left="26.9cm" background-color="#72F2D9" xsl:use-attribute-sets="column-block">
                        <fo:block />
                    </fo:block-container>
                    <fo:block-container width="6.7cm" height="16.2cm" top="1.6cm" left="0cm" xsl:use-attribute-sets="column-block">
                        <xsl:call-template name="info-block"/>
                    </fo:block-container>
                    <fo:block-container width="21.7cm" height="16.2cm" top="1.6cm" left="6.7cm" xsl:use-attribute-sets="column-block-border">
                        <xsl:call-template name="map-block"/>
                    </fo:block-container>
                    <fo:block-container width="20.8cm" height="2.3cm" top="17.9cm" left="0cm" xsl:use-attribute-sets="column-block">
                        <xsl:call-template name="disclaimer-block"/>
                    </fo:block-container>
                    <fo:block-container width="7.6cm" height="2.3cm" top="17.9cm" left="20.8cm" xsl:use-attribute-sets="column-block">
                        <xsl:call-template name="logo-block"/>
                    </fo:block-container>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>    
    
    <!-- blocks -->
    <xsl:template name="title-block">        
        <fo:block margin-left="0.2cm" margin-top="0.5cm" xsl:use-attribute-sets="title-font">
            Titel
        </fo:block>
    </xsl:template>    

    <xsl:template name="info-block">
        <fo:block margin-left="0.2cm" margin-top="0.5cm" xsl:use-attribute-sets="default-font">
            <fo:block>
                Noordpijl
            </fo:block>
            <fo:block>
                Titel
            </fo:block>
            <fo:block>
                Datum
            </fo:block>
        </fo:block>
    </xsl:template>

    <xsl:template name="map-block">
        <xsl:variable name="map">
            <xsl:value-of select="$imageUrl"/>
            <xsl:text>&amp;bbox=135000,490000,155000,510000</xsl:text>
        </xsl:variable>
        <fo:block>
            <fo:external-graphic src="{$map}"/>
        </fo:block>
    </xsl:template>
    
    <xsl:template name="disclaimer-block">
        <fo:block margin-left="0.2cm" margin-top="0.5cm" color="#000000" xsl:use-attribute-sets="default-font">
            Aan deze kaart kunnen geen rechten worden ontleend.
        </fo:block>
    </xsl:template>

    <xsl:template name="logo-block">
        <fo:block>
            <fo:external-graphic src="url('limburg_logo.jpg')" width="283px" height="57px"/>
        </fo:block>
    </xsl:template>    
</xsl:stylesheet>