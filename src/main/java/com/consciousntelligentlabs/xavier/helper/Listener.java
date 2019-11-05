package com.consciousntelligentlabs.xavier.helper;

import com.consciousntelligentlabs.xavier.XavierApplication;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioInputStream;
import java.io.*;


public class Listener implements Runnable {
  // Record duration, in milliseconds
  private int recordTime = 3000; // 60000;  // 1 minute

  // Path of the wav file
  protected File wavFile;

  // Format of audio file
  protected AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

  // The line from which audio data is captured
  protected TargetDataLine line;

  /**
   * Defines an audio format.
   *
   * @return void
   * @throws Exception
   */
  AudioFormat getAudioFormat() throws Exception {
    float sampleRate = 16000;
    int sampleSizeInBits = 16;
    int channels = 1;
    boolean signed = true;
    boolean bigEndian = true;

    AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);

    String audioFile_path = ConfigReader.getValue("audioFile_path");
    this.wavFile = new File(audioFile_path + "RecordAudio.wav");

    this.setRecordTime(Integer.parseInt(ConfigReader.getValue("listen_time")));

    return format;
  }

  /**
   * Gets recording time.
   *
   * @return int
   * @throws Exception
   */
  public static int getRecordTime() throws Exception {
    return Integer.parseInt(ConfigReader.getValue("listen_time"));
  }

  /**
   * Sets recording time.
   *
   * @param recordTime
   */
  public void setRecordTime(int recordTime) {
    this.recordTime = recordTime;
  }

  /**
   * Captures the sound and record into a WAV file
   *
   * @return void
   */
  public void startRecording() {
    try {
      AudioFormat format = getAudioFormat();
      DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

      // checks if system supports the data line
      if (!AudioSystem.isLineSupported(info)) {
        XavierApplication.LOGGER.error("Line not supported");
        System.exit(0);
      }

      line = AudioSystem.getTargetDataLine(format);
      line.open(format);
      line.start(); // start capturing

      XavierApplication.LOGGER.info("Start capturing audio...");

      AudioInputStream ais = new AudioInputStream(line);

      XavierApplication.LOGGER.info("Start recording...");

      // start recording
      AudioSystem.write(ais, fileType, wavFile);

    } catch (LineUnavailableException ex) {
      ex.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Closes the target data line to finish capturing and recording
   *
   * @return void
   */
  public void stopRecording() {
    line.stop();
    line.close();
    XavierApplication.LOGGER.info("Finished recording...");
  }

  /** Implemented method run() */
  public void run() {}
}
