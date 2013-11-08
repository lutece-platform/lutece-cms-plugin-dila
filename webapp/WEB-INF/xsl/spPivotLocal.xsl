<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dc="http://purl.org/dc/elements/1.1/"
	exclude-result-prefixes="xsl dc">

	<xsl:output method="xml" encoding="ISO-8859-15"
		cdata-section-elements="script" indent="yes" />

	<xsl:template name="affPivotLocal">
		<div class="spPublicationPivotOSATitle">
			<h4>
				<xsl:value-of select="PivotLocal/Organisme/Nom" />
			</h4>
		</div>

		<div class="spTexteChapitre">

			<p class="spTexteParagraphe">
				<xsl:value-of select="PivotLocal/Organisme/Adresse/Ligne"></xsl:value-of>
			</p>
			<p class="spTexteParagraphe">
				<xsl:value-of select="PivotLocal/Organisme/Adresse/CodePostal"></xsl:value-of>
				<xsl:text> </xsl:text>
				<xsl:value-of select="PivotLocal/Organisme/Adresse/NomCommune"></xsl:value-of>
			</p>
			<xsl:if test="PivotLocal/Organisme/Coordonn�esNum/T�l�phone">
				<p class="spTexteParagraphe">
					<xsl:text>T�l�phone : </xsl:text>
					<xsl:value-of select="PivotLocal/Organisme/Coordonn�esNum/T�l�phone"></xsl:value-of>
				</p>
			</xsl:if>
			<xsl:if test="PivotLocal/Organisme/Coordonn�esNum/T�l�copie">
				<p class="spTexteParagraphe">
					<xsl:text>T�l�copie : </xsl:text>
					<xsl:value-of select="PivotLocal/Organisme/Coordonn�esNum/T�l�copie"></xsl:value-of>
				</p>
			</xsl:if>
			<xsl:if test="PivotLocal/Organisme/Coordonn�esNum/Email">
				<p class="spTexteParagraphe">
					<xsl:text>Courriel : </xsl:text>
					<xsl:value-of select="PivotLocal/Organisme/Coordonn�esNum/Email"></xsl:value-of>
				</p>
			</xsl:if>
			<xsl:if test="PivotLocal/Organisme/Coordonn�esNum/Url">
				<p class="spTexteParagraphe">
					<xsl:text>Site internet : </xsl:text>
					<xsl:value-of select="PivotLocal/Organisme/Coordonn�esNum/Url"></xsl:value-of>
				</p>
			</xsl:if>
			<xsl:if test="PivotLocal/Organisme/Ouverture">
				<xsl:for-each select="PivotLocal/Organisme/Ouverture/PlageJ">
					<p class="spTexteParagraphe">
						<xsl:text>Ouverture </xsl:text>
						<xsl:choose>
							<xsl:when test="@d�but = @fin">
								<xsl:text>le </xsl:text>
								<xsl:value-of select="@d�but" />
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>du </xsl:text>
								<xsl:value-of select="@d�but" />
								<xsl:text> au </xsl:text>
								<xsl:value-of select="@fin" />
							</xsl:otherwise>
						</xsl:choose>
						<xsl:text> : <br /></xsl:text>
						<xsl:text> - de </xsl:text>
						<xsl:value-of select="PlageH/@d�but" />
						<xsl:text> � </xsl:text>
						<xsl:value-of select="PlageH/@fin" />
					</p>
				</xsl:for-each>
			</xsl:if>
		</div>
	</xsl:template>

</xsl:stylesheet>
