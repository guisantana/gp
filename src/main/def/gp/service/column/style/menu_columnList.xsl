<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output omit-xml-declaration = "yes" method="xml" />

	<xsl:template match="/">
	
		<a class="sub" href="#">Colunas</a>
		<div class="submenu colx2">
			<div class="col">
				<ul class="sub">
					<xsl:for-each select="renderData/controls/control/control/control[@type='lum_list']/control[@type='lum_tabularData']/data/row">
						<li>
							<a href="#"><xsl:value-of select="title"/></a>
						</li>
					</xsl:for-each>
				</ul>
			</div>
		</div>
	
	</xsl:template>
	
</xsl:stylesheet>