<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output omit-xml-declaration = "yes" method="xml" />

	<xsl:template match="/">
	
		<xsl:for-each select="renderData/controls/control/control/control[@type='lum_list']/control[@type='lum_tabularData']/data/row">
			<xsl:if test="position() &lt; 3">
	            <article class="col-1">
	                <h5><xsl:value-of select="category"/></h5>
					<h3> <xsl:value-of select="title"/></h3>
	                <p><xsl:value-of select="introduction"/></p>
					<div class="relative">
	                    <xsl:call-template name="linkProperties"/>
	                </div>
	            </article>
            </xsl:if>
		</xsl:for-each>
        <div class="col-2">
			<div>
				<span class="sub-adv-pub">Publicidade</span>
				<img src="images/avecelegance3.jpg" style="margin-bottom:16px"/>
				<span class="sub-adv-pub">Publicidade</span>
				<img src="images/marcelovideo1.jpg" />
			</div>
		</div>
	
	</xsl:template>
	
	<xsl:template name="linkProperties">
		<a class="button-1" href="#">
			<xsl:choose>
			<xsl:when test="type='2'">
				<xsl:attribute name="href"><xsl:value-of select="documentId/downloadHref"/></xsl:attribute>
				Download
			</xsl:when>
			<xsl:when test="type='1'">
				<xsl:attribute name="href"><xsl:value-of select="@href"/></xsl:attribute>
				Leia Mais
			</xsl:when>
			<xsl:when test="type='0'">
				<xsl:attribute name="href"><xsl:value-of select="url"/></xsl:attribute>
				<xsl:if test="clickPopup = 'true'">
					<xsl:attribute name="target">_blank</xsl:attribute>
				</xsl:if>
				Leia Mais
			</xsl:when>
			</xsl:choose>
		</a>
	</xsl:template>		

	
</xsl:stylesheet>