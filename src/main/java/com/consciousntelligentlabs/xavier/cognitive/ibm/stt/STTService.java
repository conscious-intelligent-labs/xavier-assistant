package com.consciousntelligentlabs.xavier.cognitive.ibm.stt;

import com.consciousntelligentlabs.xavier.XavierApplication;
import com.consciousntelligentlabs.xavier.helper.ConfigReader;
import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;
import java.io.FileInputStream;
import java.io.InputStream;

public class STTService {

  /**
   * Starts the transcription of a wav file.
   *
   * @return SpeechRecognitionResults
   */
  public static SpeechRecognitionResults start() {

    try {
      String ibm_stt_key = ConfigReader.getValue("ibm_stt_key");
      String audioFile_path = ConfigReader.getValue("audioFile_path");

      System.out.println("Transcribing audio...");
      Authenticator authenticator = new IamAuthenticator(ibm_stt_key);
      SpeechToText service = new SpeechToText(authenticator);

      InputStream audio = new FileInputStream(audioFile_path + "RecordAudio.wav");

      RecognizeOptions options =
          new RecognizeOptions.Builder().audio(audio).contentType(HttpMediaType.AUDIO_WAV).build();

      SpeechRecognitionResults transcript = service.recognize(options).execute().getResult();

      // Lets do some logging.
      XavierApplication.LOGGER.info(transcript.toString());
      XavierApplication.LOGGER.info("Transcribing audio complete...");

      return transcript;
    } catch (Exception e) {
      XavierApplication.LOGGER.error("Error Sending API STT request: " + e.getMessage());
    }

    return null;
  }
}
