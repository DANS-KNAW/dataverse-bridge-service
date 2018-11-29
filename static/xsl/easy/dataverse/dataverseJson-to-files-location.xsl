<!--

    Copyright (C) 2018 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

-->
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
      <xsl:variable name="dataverseApiBaseUrl">
        <xsl:value-of select="'http://ddvn.dans.knaw.nl:8080/api'"/>
      </xsl:variable>
      <file restricted="false" size="10122004">
        <xsl:attribute name="url">
          <xsl:value-of select="concat($dataverseApiBaseUrl,'/datasets/export?exporter=dataverse_json&#38;persistentId=', /map/string[@key='protocol']/.,':',/map/string[@key='authority']/.,'/',/map/string[@key='identifier']/.)"/>
        </xsl:attribute>
        <xsl:value-of select="concat('Metadata export from DataverseNL/',/map/string[@key='protocol']/.,'-',/map/string[@key='authority']/.,'-',/map/string[@key='identifier']/.,'.json')"/>
      </file>
      <xsl:for-each select="//map[@key='datasetVersion']/array[@key='files']/map">
        <file>
          <xsl:variable name="restrictedVal" select="./boolean[@key='restricted']/."/>
          <xsl:variable name="urlVal" select="concat($dataverseApiBaseUrl,'/access/datafile/',./map[@key='dataFile']/number[@key='id']/.)"/>
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
