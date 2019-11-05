package com.consciousntelligentlabs.xavier.helper;

import com.microsoft.cognitiveservices.speech.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

public class Ears {

  // The Source to stop recognition.
  private static Semaphore stopRecognitionSemaphore;

  // Keyword-triggered speech recognition from microphone
  public static void keywordTriggeredSpeechRecognitionWithMicrophone() throws Exception {
    stopRecognitionSemaphore = new Semaphore(0);

    // Creates an instance of a speech config with specified
    // subscription key and service region. Replace with your own subscription key
    // and service region (e.g., "westus").
    String ms_recognition_token = ConfigReader.getValue("ms_recognition_token");
    String ms_zone_recognition = ConfigReader.getValue("ms_recognition_token");
    SpeechConfig config = SpeechConfig.fromSubscription(ms_recognition_token, ms_zone_recognition);

    // Creates a speech recognizer using microphone as audio input.
    try {
      SpeechRecognizer recognizer = new SpeechRecognizer(config);
      {
        // Subscribes to events.
        recognizer.recognizing.addEventListener(
            (s, e) -> {
              if (e.getResult().getReason() == ResultReason.RecognizingKeyword) {
                System.out.println("RECOGNIZING KEYWORD: Text=" + e.getResult().getText());
              } else if (e.getResult().getReason() == ResultReason.RecognizingSpeech) {
                System.out.println("RECOGNIZING: Text=" + e.getResult().getText());
              }
            });

        recognizer.recognized.addEventListener(
            (s, e) -> {
              if (e.getResult().getReason() == ResultReason.RecognizedSpeech) {
                System.out.println("RECOGNIZED: Text=" + e.getResult().getText());
              } else if (e.getResult().getReason() == ResultReason.NoMatch) {
                System.out.println("NOMATCH: Speech could not be recognized.");
              }
            });

        recognizer.canceled.addEventListener(
            (s, e) -> {
              System.out.println("CANCELED: Reason=" + e.getReason());

              if (e.getReason() == CancellationReason.Error) {
                System.out.println("CANCELED: ErrorCode=" + e.getErrorCode());
                System.out.println("CANCELED: ErrorDetails=" + e.getErrorDetails());
                System.out.println("CANCELED: Did you update the subscription info?");
              }
            });

        recognizer.sessionStarted.addEventListener(
            (s, e) -> {
              System.out.println("\n    Session started event.");
            });

        recognizer.sessionStopped.addEventListener(
            (s, e) -> {
              System.out.println("\n    Session stopped event.");

              stopRecognitionSemaphore.release();
            });

        // Creates an instance of a keyword recognition model. Update this to
        // point to the location of your keyword recognition model.
        KeywordRecognitionModel model = KeywordRecognitionModel.fromFile("kws.table");

        // The phrase your keyword recognition model triggers on.
        String keyword = "Yo Xavier";

        // Starts continuous recognition using the keyword model. Use
        // stopKeywordRecognitionAsync() to stop recognition.
        recognizer.startKeywordRecognitionAsync(model).get();

        System.out.println(
            "Say something starting with '" + keyword + "' followed by whatever you want...");

        // Waits for a single successful keyword-triggered speech recognition (or error).
        stopRecognitionSemaphore.acquire();

        recognizer.stopKeywordRecognitionAsync().get();
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
