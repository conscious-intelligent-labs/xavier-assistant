package com.consciousntelligentlabs.xavier.cognitive.microsoft;

import com.consciousntelligentlabs.xavier.cognitive.CognitiveApi;

public class CognitiveServices implements CognitiveApi {

  protected String endpoint;
  protected String token;
  protected String region;

  public CognitiveServices(String endpoint, String token, String region) {
    this.endpoint = endpoint;
    this.token = token;
    this.region = region;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public void start() {}
}
