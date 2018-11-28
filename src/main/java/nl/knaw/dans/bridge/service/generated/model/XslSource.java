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
import java.util.ArrayList;
import java.util.List;
import nl.knaw.dans.bridge.service.generated.model.XslTransformer;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * XslSource
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2018-11-13T20:06:05.738+01:00[Europe/Amsterdam]")

public class XslSource   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("xsl-transformer-list")
  @Valid
  private List<XslTransformer> xslTransformerList = null;

  public XslSource name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public XslSource xslTransformerList(List<XslTransformer> xslTransformerList) {
    this.xslTransformerList = xslTransformerList;
    return this;
  }

  public XslSource addXslTransformerListItem(XslTransformer xslTransformerListItem) {
    if (this.xslTransformerList == null) {
      this.xslTransformerList = new ArrayList<>();
    }
    this.xslTransformerList.add(xslTransformerListItem);
    return this;
  }

  /**
   * Get xslTransformerList
   * @return xslTransformerList
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<XslTransformer> getXslTransformerList() {
    return xslTransformerList;
  }

  public void setXslTransformerList(List<XslTransformer> xslTransformerList) {
    this.xslTransformerList = xslTransformerList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    XslSource xslSource = (XslSource) o;
    return Objects.equals(this.name, xslSource.name) &&
        Objects.equals(this.xslTransformerList, xslSource.xslTransformerList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, xslTransformerList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class XslSource {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    xslTransformerList: ").append(toIndentedString(xslTransformerList)).append("\n");
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

