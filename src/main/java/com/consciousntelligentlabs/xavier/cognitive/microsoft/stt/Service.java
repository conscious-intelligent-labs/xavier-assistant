package com.consciousntelligentlabs.xavier.cognitive.microsoft.stt;

import com.consciousntelligentlabs.xavier.cognitive.microsoft.CognitiveServices;
import com.consciousntelligentlabs.xavier.helper.HTTPClient;
import com.consciousntelligentlabs.xavier.helper.ConfigReader;
import com.microsoft.cognitiveservices.speech.*;

import java.util.concurrent.Future;

public class Service extends CognitiveServices {

  public Service(String endpoint, String token, String region) {
    super(endpoint, token, region);
  }

  public void sendRequest() throws Exception {
    String token = ConfigReader.getValue("stt_token");
    HTTPClient client = new HTTPClient();
    client.createClient();
    client.setHost(ConfigReader.getValue("stt_endpoint"));
    client.createPost();
    client.setHeader("Content-type", "audio/wav; codecs=audio/pcm; samplerate=16000");
    client.setHeader("OcpApimSubscriptionKey", token);
  }

  public void run() {
    try {

      // Replace below with your own subscription key
      String speechSubscriptionKey = this.getToken();
      // Replace below with your own service region (e.g., "westus").
      String serviceRegion = this.getRegion();

      int exitCode = 1;
      SpeechConfig config = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion);
      assert (config != null);

      SpeechRecognizer reco = new SpeechRecognizer(config);
      assert (reco != null);

      System.out.println("Say something...");

      Future<SpeechRecognitionResult> task = reco.recognizeOnceAsync();
      assert (task != null);

      SpeechRecognitionResult result = task.get();
      assert (result != null);

      if (result.getReason() == ResultReason.RecognizedSpeech) {
        System.out.println("We recognized: " + result.getText());
        exitCode = 0;
      } else if (result.getReason() == ResultReason.NoMatch) {
        System.out.println("NOMATCH: Speech could not be recognized.");
      } else if (result.getReason() == ResultReason.Canceled) {
        CancellationDetails cancellation = CancellationDetails.fromResult(result);
        System.out.println("CANCELED: Reason=" + cancellation.getReason());

        if (cancellation.getReason() == CancellationReason.Error) {
          System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
          System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
          System.out.println("CANCELED: Did you update the subscription info?");
        }
      }

      reco.close();

      System.exit(exitCode);
    } catch (Exception ex) {
      System.out.println("Unexpected exception: " + ex.getMessage());

      assert (false);
      System.exit(1);
    }
  }
}
