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
 * Body
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2018-11-13T20:06:05.738+01:00[Europe/Amsterdam]")

public class Body   {
  @JsonProperty("zipPlugin")
  private byte[] zipPlugin = null;

  public Body zipPlugin(byte[] zipPlugin) {
    this.zipPlugin = zipPlugin;
    return this;
  }

  /**
   * The zip-plugin to upload.
   * @return zipPlugin
  **/
  @ApiModelProperty(value = "The zip-plugin to upload.")


  public byte[] getZipPlugin() {
    return zipPlugin;
  }

  public void setZipPlugin(byte[] zipPlugin) {
    this.zipPlugin = zipPlugin;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Body body = (Body) o;
    return Objects.equals(this.zipPlugin, body.zipPlugin);
  }

  @Override
  public int hashCode() {
    return Objects.hash(zipPlugin);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body {\n");
    
    sb.append("    zipPlugin: ").append(toIndentedString(zipPlugin)).append("\n");
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

