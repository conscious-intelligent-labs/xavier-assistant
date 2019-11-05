package com.consciousntelligentlabs.xavier.cognitive.ibm.tts;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.text_to_speech.v1.util.WaveUtils;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;

public class TTSService {

    static void textToSpeech(String text) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Authenticator authenticator = new IamAuthenticator("gpdCs6sW6z-WyiIThPyjt96gcETfv1KVNSBnLmQkrSzw");
        TextToSpeech textToSpeech = new TextToSpeech(authenticator);

        try {
            SynthesizeOptions synthesizeOptions =
                    new SynthesizeOptions.Builder()
                            .text(text)
                            .accept("audio/wav")
                            .voice("en-US_AllisonVoice")
                            .build();

            InputStream inputStream =
                    textToSpeech.synthesize(synthesizeOptions).execute().getResult();
            InputStream in = WaveUtils.reWriteWaveHeader(inputStream);

            OutputStream out = new FileOutputStream("output.wav");
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            out.close();
            in.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        playSound("output.wav");

    }

    private static void writeToFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void playSound(String soundFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        //	File f = new File( soundFile);
        InputStream in = new FileInputStream(soundFile);

        // create an audiostream from the inputstream
        AudioStream audioStream = new AudioStream(in);

        // play the audio clip with the audioplayer class
        AudioPlayer.player.start(audioStream);
    }

}
