package com.consciousntelligentlabs.xavier.detection.snowboy;

import ai.kitt.snowboy.SnowboyDetect;
import com.consciousntelligentlabs.xavier.XavierApplication;
import com.consciousntelligentlabs.xavier.cognitive.ibm.stt.STTService;
import com.consciousntelligentlabs.xavier.detection.HotWord;
import com.consciousntelligentlabs.xavier.helper.ConfigReader;
import com.consciousntelligentlabs.xavier.helper.Listener;
import com.consciousntelligentlabs.xavier.skills.Controller;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResult;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;
import javax.sound.sampled.*;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class WakeSnowBoy implements HotWord {

  static {
    System.loadLibrary("snowboy-detect-java");
  }

  protected SnowboyDetect detector;
  protected AudioFormat format;
  protected DataLine.Info targetInfo;
  protected TargetDataLine targetLine;
  protected Boolean recordMode = true;
  public static volatile int tempState;

  @Override
  public void create() throws Exception {
    // Sets up audio.
    this.format = new AudioFormat(16000, 16, 1, true, false);
    this.targetInfo = new DataLine.Info(TargetDataLine.class, format);

    // Sets up Snowboy.
    String path = ConfigReader.getValue("model_path");
    this.detector = new SnowboyDetect(path + "common.res", path + "models/snowboy.umdl");
    this.detector.SetSensitivity("0.5");
    this.detector.SetAudioGain(1);
    this.detector.ApplyFrontend(false);
    this.targetLine = (TargetDataLine) AudioSystem.getLine(this.targetInfo);
  }

  @Override
  public void start() throws Exception {
    try {
      this.targetLine.open(format);
      this.targetLine.start();
    } catch (Exception e) {
      throw new Exception("Error starting recording. " + e.getMessage());
    }
  }

  @Override
  public void stop() throws Exception {
    try {
      this.recordMode = false;
      this.targetLine.stop();
    } catch (Exception e) {
      throw new Exception("Error stopping recording. " + e.getMessage());
    }
  }

  @Override
  public void listen() {
    try {
      // Reads 0.1 second of audio in each call.
      byte[] targetData = new byte[3200];
      short[] snowboyData = new short[1600];
      int numBytesRead;

      // Listener listen = new Listener();

      while (this.recordMode) {
        // Reads the audio data in the blocking mode. If you are on a very slow
        // machine such that the hotword detector could not process the audio
        // data in real time, this will cause problem...
        numBytesRead = targetLine.read(targetData, 0, targetData.length);

        if (numBytesRead == -1) {
          System.out.print("Fails to read audio data.");
          break;
        }

        // Converts bytes into int16 that Snowboy will read.
        ByteBuffer.wrap(targetData).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(snowboyData);

        // Detection.
        int result = detector.RunDetection(snowboyData, snowboyData.length);
        System.out.print("Hotword " + result + "\n");

        // listen.inputState = result;
        tempState = result;
        if (result > 0) {
          System.out.print("Hotword " + result + " detected!\n");

          final Listener listener = new Listener();
          Thread stopper =
              new Thread(
                  new Runnable() {
                    public void run() {
                      try {
                        Thread.sleep(Listener.getRecordTime());
                        listener.stopRecording();
                        SpeechRecognitionResults processedText = STTService.start();

                        String text = null;

                        // Get transcribed text.
                        if (!processedText.getResults().isEmpty()) {
                          List<SpeechRecognitionResult> transcripts = processedText.getResults();

                          for (SpeechRecognitionResult transcript : transcripts) {
                            if (transcript.isXFinal()) {
                              text = transcript.getAlternatives().get(0).getTranscript();
                              break;
                            }
                          }
                        }

                        // Lets complete a task or skill.
                        Controller.action(text).processCommand();

                      } catch (InterruptedException | FileNotFoundException ex) {
                        XavierApplication.LOGGER.info("Error running command. " + ex.getMessage());
                      } catch (Exception e) {
                        XavierApplication.LOGGER.info("Error running command. " + e.getMessage());
                      }
                    }
                  });

          stopper.start();
          listener.startRecording();
        }
      }
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  public void run() {
    try {
      this.create();
      this.start();
      this.listen();

      if (Thread.interrupted()) {
        this.stop();
      }
    } catch (Exception e) {
      System.out.println("Error running snowboy thread. " + e.getMessage());
    }
  }
}
