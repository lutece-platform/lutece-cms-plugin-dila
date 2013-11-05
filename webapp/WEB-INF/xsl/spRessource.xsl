<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">
    
    <xsl:import href="spCommon.xsl"/>
    <xsl:import href="spFilDAriane.xsl"/>
    <xsl:import href="spTitre.xsl"/>
    <xsl:import href="spTexte.xsl"/>
    <xsl:import href="spServiceEnLigne.xsl"/>
    <xsl:import href="spCentreDeContact.xsl"/>
    <xsl:import href="spReference.xsl"/>
    <xsl:import href="spPourEnSavoirPlus.xsl"/>
    <xsl:import href="spSiteInternetPublic.xsl"/>
    <xsl:import href="spTeleservice.xsl"/>
    <xsl:import href="spFormulaire.xsl"/>
    <xsl:import href="spLettreType.xsl"/>
    <xsl:import href="spModuleDeCalcul.xsl"/>
    <xsl:import href="spGlossaire.xsl"/>
    
    <xsl:output method="xml" encoding="ISO-8859-15" cdata-section-elements="script" indent="yes"/> 

    <xsl:template match="/">
        <xsl:variable name="type">
            <xsl:value-of select="/ServiceComplementaire/@type"/>
        </xsl:variable>
        <xsl:choose>
            <xsl:when test="$type = 'T�l�service'">
                <xsl:apply-templates mode="Teleservice"/>
            </xsl:when>
            <xsl:when test="$type = 'Formulaire'">
                <xsl:apply-templates mode="Formulaire"/>
            </xsl:when>
            <xsl:when test="$type = 'Lettre type'">
                <xsl:apply-templates mode="Lettre-type"/>
            </xsl:when>
            <xsl:when test="$type = 'Module de calcul'">
                <xsl:apply-templates mode="Module-de-calcul"/>
            </xsl:when>
            <xsl:when test="$type = 'Centre de contact'">
                <xsl:apply-templates mode="Centre-de-contact"/>
            </xsl:when>
            <xsl:when test="$type = 'D�finition de glossaire'">
                <xsl:apply-templates mode="Glossaire"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
        
</xsl:stylesheet>
