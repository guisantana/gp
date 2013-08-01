<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:template match="/">
	
		<xsl:for-each select="renderData/controls/control/control/control[@type='lum_list']/control[@type='lum_tabularData']/data/row">
			<xsl:value-of select="content" disable-output-escaping="yes"/>
		</xsl:for-each>
	
	</xsl:template>

</xsl:stylesheet>