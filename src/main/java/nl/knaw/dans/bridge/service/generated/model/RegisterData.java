package nl.knaw.dans.bridge.service.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * RegisterData
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2018-11-10T10:59:08.643+01:00[Europe/Amsterdam]")

public class RegisterData   {
  @JsonProperty("srcName")
  private String srcName = null;

  @JsonProperty("srcMetadataUrl")
  private String srcMetadataUrl = null;

  @JsonProperty("srcApiToken")
  private String srcApiToken = null;

  @JsonProperty("raName")
  private String raName = null;

  @JsonProperty("raUsername")
  private String raUsername = null;

  @JsonProperty("raPassword")
  private String raPassword = null;

  public RegisterData srcName(String srcName) {
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

  public RegisterData srcMetadataUrl(String srcMetadataUrl) {
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

  public RegisterData srcApiToken(String srcApiToken) {
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

  public RegisterData raName(String raName) {
    this.raName = raName;
    return this;
  }

  /**
   * Get raName
   * @return raName
  **/
  @ApiModelProperty(value = "")


  public String getRaName() {
    return raName;
  }

  public void setRaName(String raName) {
    this.raName = raName;
  }

  public RegisterData raUsername(String raUsername) {
    this.raUsername = raUsername;
    return this;
  }

  /**
   * Get raUsername
   * @return raUsername
  **/
  @ApiModelProperty(value = "")


  public String getRaUsername() {
    return raUsername;
  }

  public void setRaUsername(String raUsername) {
    this.raUsername = raUsername;
  }

  public RegisterData raPassword(String raPassword) {
    this.raPassword = raPassword;
    return this;
  }

  /**
   * Get raPassword
   * @return raPassword
  **/
  @ApiModelProperty(value = "")


  public String getRaPassword() {
    return raPassword;
  }

  public void setRaPassword(String raPassword) {
    this.raPassword = raPassword;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RegisterData registerData = (RegisterData) o;
    return Objects.equals(this.srcName, registerData.srcName) &&
        Objects.equals(this.srcMetadataUrl, registerData.srcMetadataUrl) &&
        Objects.equals(this.srcApiToken, registerData.srcApiToken) &&
        Objects.equals(this.raName, registerData.raName) &&
        Objects.equals(this.raUsername, registerData.raUsername) &&
        Objects.equals(this.raPassword, registerData.raPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(srcName, srcMetadataUrl, srcApiToken, raName, raUsername, raPassword);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RegisterData {\n");
    
    sb.append("    srcName: ").append(toIndentedString(srcName)).append("\n");
    sb.append("    srcMetadataUrl: ").append(toIndentedString(srcMetadataUrl)).append("\n");
    sb.append("    srcApiToken: ").append(toIndentedString(srcApiToken)).append("\n");
    sb.append("    raName: ").append(toIndentedString(raName)).append("\n");
    sb.append("    raUsername: ").append(toIndentedString(raUsername)).append("\n");
    sb.append("    raPassword: ").append(toIndentedString(raPassword)).append("\n");
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

