package com.consciousntelligentlabs.xavier.helper;

import com.consciousntelligentlabs.xavier.XavierApplication;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

  /**
   * Gets value from config.
   *
   * @param key
   * @return
   * @throws Exception
   */
  public static String getValue(String key) throws Exception {
    String value;

    try (InputStream input =
        XavierApplication.class.getClassLoader().getResourceAsStream("application.properties")) {

      Properties prop = new Properties();

      if (input == null) {
        System.out.println("Sorry, unable to find config.properties");
        throw new Exception("Sorry, unable to find config.properties");
      }

      // load a properties file from class path, inside static method
      prop.load(input);

      value = prop.getProperty(key);

    } catch (Exception ex) {
      throw new Exception(ex.getMessage());
    }

    return value;
  }
}
