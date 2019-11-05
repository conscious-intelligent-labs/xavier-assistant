package com.consciousntelligentlabs.xavier.helper;

import java.io.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HTTPClient {

  protected String host;
  protected String service;
  protected String body;
  protected String OcpApimSubscriptionKey;
  protected HttpClient httpclient;
  protected String accept;
  protected HttpPost request;

  // Accept: application/json;text/xml
  // Content-Type: audio/wav; codecs=audio/pcm; samplerate=16000

  public HttpClient createClient() {
    // Create http client
    return this.httpclient = HttpClients.createDefault();
  }

  public HttpPost getRequest() {
    return request;
  }

  public void setRequest(HttpPost request) {
    this.request = request;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getOcpApimSubscriptionKey() {
    return OcpApimSubscriptionKey;
  }

  public void setOcpApimSubscriptionKey(String ocpApimSubscriptionKey) {
    OcpApimSubscriptionKey = ocpApimSubscriptionKey;
  }

  public HttpClient getHttpclient() {
    return httpclient;
  }

  public void setHttpclient(HttpClient httpclient) {
    this.httpclient = httpclient;
  }

  public String getAccept() {
    return accept;
  }

  public void setAccept(String accept) {
    this.accept = accept;
  }

  public void setHeader(String type, String value) {
    this.request.setHeader(type, value);
  }

  public HttpPost createPost() {
    this.request = new HttpPost(this.host);
    return this.request;
  }

  public void post() throws IOException {

    // Add host + service to get full URI
    String answer_uri = host + service;
    System.out.println(answer_uri);

    this.createPost();

    // set question
    StringEntity entity = new StringEntity(this.body);
    request.setEntity(entity);

    // Send request to Azure service, get response
    HttpResponse response = this.httpclient.execute(request);

    HttpEntity entityResponse = response.getEntity();

    if (entityResponse != null) {
      System.out.println("response back!");
      System.out.println(EntityUtils.toString(entityResponse));
    }
  }
}
