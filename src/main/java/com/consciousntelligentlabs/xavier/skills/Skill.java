package com.consciousntelligentlabs.xavier.skills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Skill {

  protected String command;
  protected String hotword;
  protected String[] hotPhrase;

  /**
   * Constructor
   *
   * @param command
   */
  public Skill(String command) {
    this.command = command;
  }

  /**
   * Constructor
   *
   * @param command
   */
  public Skill(String command, String hotword) {
    this.command = command;
    this.hotword = hotword;
  }

  /**
   * Constructor
   *
   * @param command
   */
  public Skill(String command, String[] hotPhrase) {
    this.command = command;
    this.hotPhrase = hotPhrase.clone();
  }

  /**
   * Processes a command.
   *
   * @return boolean
   * @throws Exception
   */
  public boolean processCommand() throws Exception {

    if (this.command == null) {
      return false;
    }

    ProcessBuilder processBuilder = new ProcessBuilder();
    processBuilder.command("bash", "-c", this.command);

    try {
      Process process = processBuilder.start();

      StringBuilder output = new StringBuilder();

      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }

      int exitVal = process.waitFor();
      if (exitVal == 0) {
        System.out.println("\n Running Command... \n\n");
        System.out.println(output);
        System.out.println("\n\n Command Successful! \n\n");
      } else {
        throw new Exception("Something went wrong running the command");
      }

    } catch (IOException e) {
      throw new Exception(e.getMessage());
    } catch (InterruptedException e) {
      throw new Exception(e.getMessage());
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }

    return true;
  }

  /**
   * Processes a specific defined command.
   *
   * @param command
   * @throws Exception
   */
  public void processCommandOnDemand(String command) throws Exception {
    ProcessBuilder processBuilder = new ProcessBuilder();
    // -- Linux --
    // Run a shell command
    processBuilder.command("bash", "-c", command);

    try {
      Process process = processBuilder.start();

      StringBuilder output = new StringBuilder();

      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }

      int exitVal = process.waitFor();
      if (exitVal == 0) {
        System.out.println("\n Running Command... \n\n");
        System.out.println(output);
        System.out.println("\n\n Command Successful! \n\n");
      } else {
        throw new Exception("Something went wrong running the command");
      }
    } catch (IOException e) {
      throw new Exception(e.getMessage());
    } catch (InterruptedException e) {
      throw new Exception(e.getMessage());
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }
}
