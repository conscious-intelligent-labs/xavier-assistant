package com.consciousntelligentlabs.xavier.skills;

public class Controller {

  /**
   * Maps the action to the correct command based on what was transcribed.
   *
   * @param actionText
   * @return
   */
  public static Skill action(String actionText) {

    if (actionText.contains("what") && actionText.contains("running")) {
      return new Skill("ps aux");
    } else if (actionText.contains("directory") && actionText.contains("show")) {
      return new Skill("pwd");
    } else if (actionText.contains("files") && actionText.contains("show")) {
      return new Skill("ls");
    } else {
      return new Skill(null);
    }
  }
}
