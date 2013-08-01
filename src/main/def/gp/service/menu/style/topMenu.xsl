<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output omit-xml-declaration = "yes" method="xml" />

	<xsl:template match="/">
		<xsl:for-each select="renderData/controls/control/data/row">
            <li><a href="{href}"><xsl:value-of select="name" /></a></li>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>