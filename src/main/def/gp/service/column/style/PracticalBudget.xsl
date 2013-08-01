<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:lum="http://www.lumis.com.br/doui" exclude-result-prefixes="lum" version="1.0">
<!-- $Revision: 9418 $ $Date: 2008-06-17 17:57:26 -0300 (Tue, 17 Jun 2008) $ -->
<xsl:import href="../../../../../lumis/doui/style/DouiControls.xsl" />
<xsl:output omit-xml-declaration = "yes" method="xml" />

	
	<xsl:template match="control[@type='lum_checkBoxList']" name="lum_checkBoxList">
		<script type="text/javascript">
		$(function(){
		    var btCheck = $("input:checkbox[name='checkAllCategory']"); //Checkbox que vai gerar a ação
		    var checkBox = $("input:checkbox[name='idCategory']");   //Checkbox que receberão a ação

			$(btCheck).bind('click',function(){
				$(btCheck).attr('checked')== true ? $(checkBox).attr('checked',true) : $(checkBox).attr('checked',false);
		    })
		})

		</script>
		<input name="checkAllCategory" id="checkAllCategory" type="checkbox" value="1" /> Todas as categorias <br/>
		<xsl:for-each select="data/option">
		
			<xsl:variable name='PrevCategory' select="preceding-sibling::option[position()=1]/value" />
			<xsl:variable name='CurCategory' select="value" />
			
			<xsl:if test="value!=''">
				<xsl:if test="(not($PrevCategory)) or ($PrevCategory != $CurCategory)">
					<input name="idCategory" id="idCategory" type="checkbox" value="{$CurCategory}" /> <xsl:value-of select="text"/><br/>
				</xsl:if>
			</xsl:if>

		</xsl:for-each>
		<xsl:apply-templates select="control" />
	</xsl:template>
	
</xsl:stylesheet>