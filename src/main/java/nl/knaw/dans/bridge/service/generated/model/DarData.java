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
 * DarData
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2018-11-13T20:06:05.738+01:00[Europe/Amsterdam]")

public class DarData   {
  @JsonProperty("darUsername")
  private String darUsername = null;

  @JsonProperty("darPassword")
  private String darPassword = null;

  @JsonProperty("darName")
  private String darName = null;

  @JsonProperty("darUserAffiliation")
  private String darUserAffiliation = null;

  public DarData darUsername(String darUsername) {
    this.darUsername = darUsername;
    return this;
  }

  /**
   * Get darUsername
   * @return darUsername
  **/
  @ApiModelProperty(value = "")


  public String getDarUsername() {
    return darUsername;
  }

  public void setDarUsername(String darUsername) {
    this.darUsername = darUsername;
  }

  public DarData darPassword(String darPassword) {
    this.darPassword = darPassword;
    return this;
  }

  /**
   * Get darPassword
   * @return darPassword
  **/
  @ApiModelProperty(value = "")


  public String getDarPassword() {
    return darPassword;
  }

  public void setDarPassword(String darPassword) {
    this.darPassword = darPassword;
  }

  public DarData darName(String darName) {
    this.darName = darName;
    return this;
  }

  /**
   * Get darName
   * @return darName
  **/
  @ApiModelProperty(value = "")


  public String getDarName() {
    return darName;
  }

  public void setDarName(String darName) {
    this.darName = darName;
  }

  public DarData darUserAffiliation(String darUserAffiliation) {
    this.darUserAffiliation = darUserAffiliation;
    return this;
  }

  /**
   * Get darUserAffiliation
   * @return darUserAffiliation
  **/
  @ApiModelProperty(value = "")


  public String getDarUserAffiliation() {
    return darUserAffiliation;
  }

  public void setDarUserAffiliation(String darUserAffiliation) {
    this.darUserAffiliation = darUserAffiliation;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DarData darData = (DarData) o;
    return Objects.equals(this.darUsername, darData.darUsername) &&
        Objects.equals(this.darPassword, darData.darPassword) &&
        Objects.equals(this.darName, darData.darName) &&
        Objects.equals(this.darUserAffiliation, darData.darUserAffiliation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(darUsername, darPassword, darName, darUserAffiliation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DarData {\n");
    
    sb.append("    darUsername: ").append(toIndentedString(darUsername)).append("\n");
    sb.append("    darPassword: ").append(toIndentedString(darPassword)).append("\n");
    sb.append("    darName: ").append(toIndentedString(darName)).append("\n");
    sb.append("    darUserAffiliation: ").append(toIndentedString(darUserAffiliation)).append("\n");
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

