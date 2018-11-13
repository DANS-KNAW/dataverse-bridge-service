package nl.knaw.dans.bridge.service.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import nl.knaw.dans.bridge.service.generated.model.XslSource;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Plugin
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2018-11-13T20:06:05.738+01:00[Europe/Amsterdam]")

public class Plugin   {
  @JsonProperty("dar-name")
  private String darName = null;

  @JsonProperty("dar-iri")
  private String darIri = null;

  @JsonProperty("action-class-name")
  private String actionClassName = null;

  @JsonProperty("plugin-lib-location")
  private String pluginLibLocation = null;

  @JsonProperty("xsl")
  @Valid
  private List<XslSource> xsl = null;

  public Plugin darName(String darName) {
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

  public Plugin darIri(String darIri) {
    this.darIri = darIri;
    return this;
  }

  /**
   * Get darIri
   * @return darIri
  **/
  @ApiModelProperty(value = "")


  public String getDarIri() {
    return darIri;
  }

  public void setDarIri(String darIri) {
    this.darIri = darIri;
  }

  public Plugin actionClassName(String actionClassName) {
    this.actionClassName = actionClassName;
    return this;
  }

  /**
   * Get actionClassName
   * @return actionClassName
  **/
  @ApiModelProperty(value = "")


  public String getActionClassName() {
    return actionClassName;
  }

  public void setActionClassName(String actionClassName) {
    this.actionClassName = actionClassName;
  }

  public Plugin pluginLibLocation(String pluginLibLocation) {
    this.pluginLibLocation = pluginLibLocation;
    return this;
  }

  /**
   * Get pluginLibLocation
   * @return pluginLibLocation
  **/
  @ApiModelProperty(value = "")


  public String getPluginLibLocation() {
    return pluginLibLocation;
  }

  public void setPluginLibLocation(String pluginLibLocation) {
    this.pluginLibLocation = pluginLibLocation;
  }

  public Plugin xsl(List<XslSource> xsl) {
    this.xsl = xsl;
    return this;
  }

  public Plugin addXslItem(XslSource xslItem) {
    if (this.xsl == null) {
      this.xsl = new ArrayList<>();
    }
    this.xsl.add(xslItem);
    return this;
  }

  /**
   * Get xsl
   * @return xsl
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<XslSource> getXsl() {
    return xsl;
  }

  public void setXsl(List<XslSource> xsl) {
    this.xsl = xsl;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Plugin plugin = (Plugin) o;
    return Objects.equals(this.darName, plugin.darName) &&
        Objects.equals(this.darIri, plugin.darIri) &&
        Objects.equals(this.actionClassName, plugin.actionClassName) &&
        Objects.equals(this.pluginLibLocation, plugin.pluginLibLocation) &&
        Objects.equals(this.xsl, plugin.xsl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(darName, darIri, actionClassName, pluginLibLocation, xsl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Plugin {\n");
    
    sb.append("    darName: ").append(toIndentedString(darName)).append("\n");
    sb.append("    darIri: ").append(toIndentedString(darIri)).append("\n");
    sb.append("    actionClassName: ").append(toIndentedString(actionClassName)).append("\n");
    sb.append("    pluginLibLocation: ").append(toIndentedString(pluginLibLocation)).append("\n");
    sb.append("    xsl: ").append(toIndentedString(xsl)).append("\n");
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

