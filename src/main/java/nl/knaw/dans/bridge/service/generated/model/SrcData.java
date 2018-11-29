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

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SrcData
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2018-11-13T20:06:05.738+01:00[Europe/Amsterdam]")

public class SrcData   {
  @JsonProperty("srcName")
  private String srcName = null;

  @JsonProperty("srcMetadataUrl")
  private String srcMetadataUrl = null;

  @JsonProperty("srcMetadataVersion")
  private String srcMetadataVersion = null;

  @JsonProperty("srcApiToken")
  private String srcApiToken = null;

  public SrcData srcName(String srcName) {
    this.srcName = srcName;
    return this;
  }

  /**
   * Get srcName
   * @return srcName
  **/
  @ApiModelProperty(value = "")


  public String getSrcName() {
    return srcName;
  }

  public void setSrcName(String srcName) {
    this.srcName = srcName;
  }

  public SrcData srcMetadataUrl(String srcMetadataUrl) {
    this.srcMetadataUrl = srcMetadataUrl;
    return this;
  }

  /**
   * Get srcMetadataUrl
   * @return srcMetadataUrl
  **/
  @ApiModelProperty(value = "")


  public String getSrcMetadataUrl() {
    return srcMetadataUrl;
  }

  public void setSrcMetadataUrl(String srcMetadataUrl) {
    this.srcMetadataUrl = srcMetadataUrl;
  }

  public SrcData srcMetadataVersion(String srcMetadataVersion) {
    this.srcMetadataVersion = srcMetadataVersion;
    return this;
  }

  /**
   * Get srcMetadataVersion
   * @return srcMetadataVersion
  **/
  @ApiModelProperty(value = "")


  public String getSrcMetadataVersion() {
    return srcMetadataVersion;
  }

  public void setSrcMetadataVersion(String srcMetadataVersion) {
    this.srcMetadataVersion = srcMetadataVersion;
  }

  public SrcData srcApiToken(String srcApiToken) {
    this.srcApiToken = srcApiToken;
    return this;
  }

  /**
   * Get srcApiToken
   * @return srcApiToken
  **/
  @ApiModelProperty(value = "")


  public String getSrcApiToken() {
    return srcApiToken;
  }

  public void setSrcApiToken(String srcApiToken) {
    this.srcApiToken = srcApiToken;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SrcData srcData = (SrcData) o;
    return Objects.equals(this.srcName, srcData.srcName) &&
        Objects.equals(this.srcMetadataUrl, srcData.srcMetadataUrl) &&
        Objects.equals(this.srcMetadataVersion, srcData.srcMetadataVersion) &&
        Objects.equals(this.srcApiToken, srcData.srcApiToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(srcName, srcMetadataUrl, srcMetadataVersion, srcApiToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SrcData {\n");
    
    sb.append("    srcName: ").append(toIndentedString(srcName)).append("\n");
    sb.append("    srcMetadataUrl: ").append(toIndentedString(srcMetadataUrl)).append("\n");
    sb.append("    srcMetadataVersion: ").append(toIndentedString(srcMetadataVersion)).append("\n");
    sb.append("    srcApiToken: ").append(toIndentedString(srcApiToken)).append("\n");
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

