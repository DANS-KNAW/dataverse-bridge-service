/**
 * Copyright (C) 2018 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.bridge.service.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * XsltSource
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2018-11-10T10:59:08.643+01:00[Europe/Amsterdam]")

public class XsltSource   {
  @JsonProperty("xsl-name")
  private String xslName = null;

  @JsonProperty("xsl-url")
  private String xslUrl = null;

  public XsltSource xslName(String xslName) {
    this.xslName = xslName;
    return this;
  }

  /**
   * Get xslName
   * @return xslName
  **/
  @ApiModelProperty(value = "")


  public String getXslName() {
    return xslName;
  }

  public void setXslName(String xslName) {
    this.xslName = xslName;
  }

  public XsltSource xslUrl(String xslUrl) {
    this.xslUrl = xslUrl;
    return this;
  }

  /**
   * Get xslUrl
   * @return xslUrl
  **/
  @ApiModelProperty(value = "")


  public String getXslUrl() {
    return xslUrl;
  }

  public void setXslUrl(String xslUrl) {
    this.xslUrl = xslUrl;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    XsltSource xsltSource = (XsltSource) o;
    return Objects.equals(this.xslName, xsltSource.xslName) &&
        Objects.equals(this.xslUrl, xsltSource.xslUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(xslName, xslUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class XsltSource {\n");
    
    sb.append("    xslName: ").append(toIndentedString(xslName)).append("\n");
    sb.append("    xslUrl: ").append(toIndentedString(xslUrl)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

