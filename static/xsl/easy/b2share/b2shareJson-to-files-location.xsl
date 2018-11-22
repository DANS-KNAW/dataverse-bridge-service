<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output encoding="UTF-8" indent="yes" method="xml"/>
  <xsl:strip-space elements="*"/>

  <xsl:param name="dvnJson"/>

  <xsl:mode on-no-match="shallow-copy"/>

  <xsl:template name="initialTemplate">
    <xsl:apply-templates select="json-to-xml($dvnJson)"/>
  </xsl:template>
  <!--[@key='typeName' and text()='title']-->
  <xsl:template match="/" xpath-default-namespace="http://www.w3.org/2005/xpath-functions">
    <files>
      <xsl:variable name="b2shareApiBaseUrl">
        <xsl:value-of select="'http://localhost:5000/api/records'"/>
      </xsl:variable>
      <file restricted="false" size="10122004">
        <xsl:attribute name="url">
          <xsl:value-of select="concat($b2shareApiBaseUrl, '/', /map/string[@key='id']/.)"/>
        </xsl:attribute>
        <xsl:variable name="jsonfilename" select="concat(/map/string[@key='id']/.,'.json')"/>
        <xsl:value-of select="concat('Metadata export from DataverseNL/',$jsonfilename)"/>
      </file>
      <xsl:for-each select="//map[@key='datasetVersion']/array[@key='files']/map">
        <file>
          <xsl:variable name="restrictedVal" select="./boolean[@key='restricted']/."/>
          <xsl:variable name="urlVal" select="concat('https://dataverse.nl/api/access/datafile/',./map[@key='dataFile']/number[@key='id']/.)"/>
          <xsl:attribute name="restricted">
            <xsl:value-of select="$restrictedVal"/>
          </xsl:attribute>
          <xsl:attribute name="size">
            <xsl:value-of select="./map[@key='dataFile']/number[@key='filesize']/."/>
          </xsl:attribute>
          <xsl:attribute name="url">
            <xsl:choose>
              <xsl:when test="$restrictedVal = 'true'">
                <xsl:value-of select="concat($urlVal, '?key=API-TOKEN')"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="$urlVal"/>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:attribute>

          <xsl:value-of select="./map[@key='dataFile']/string[@key='filename']/."/>
        </file>
      </xsl:for-each>
    </files>
  </xsl:template>


</xsl:stylesheet>
