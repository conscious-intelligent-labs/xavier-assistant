package com.consciousntelligentlabs.xavier.detection.sphinx;

import com.consciousntelligentlabs.xavier.XavierApplication;
import com.consciousntelligentlabs.xavier.detection.HotWord;
import com.consciousntelligentlabs.xavier.helper.ConfigReader;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class WakeSphinx implements HotWord {

    public static long recordTime = 60000;  // 1 minute

    protected Configuration configuration;
    protected LiveSpeechRecognizer recognizer;
    protected SpeechResult result;
    protected String wakeWord;

    /**
     * Setup for the live speech recognizer.
     *
     * @param useGrammar Configuration to use grammar.
     *
     * @return LiveSpeechRecognizer
     * @throws IOException
     */
    public LiveSpeechRecognizer setup(boolean useGrammar) throws Exception {
        this.wakeWord = ConfigReader.getValue("wakeword");
        this.configuration = new Configuration();
        this.configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        this.configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        this.configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
        this.configuration.setSampleRate(16000);

        if (useGrammar) {
            this.configuration.setUseGrammar(true);
        }

        return this.createRecognizer();
    }

    /**
     * Getter for LiveSpeechRecognizer
     *
     * @return LiveSpeechRecognizer
     */
    public LiveSpeechRecognizer getRecognizer() {
        return recognizer;
    }

    /**
     * Getter for SpeechResult.
     *
     * @return SpeechResult
     */
    public SpeechResult getResult() {
        return result;
    }

    /**
     * Creates a recognizer for live speech streaming.
     *
     * @return LiveSpeechRecognizer
     * @throws IOException
     */
    public LiveSpeechRecognizer createRecognizer() throws IOException {
         this.recognizer = new LiveSpeechRecognizer(this.configuration);
         return this.recognizer;
    }

    /**
     * Creates a listener.
     *
     * @throws Exception
     */
    public void create() throws Exception {
        this.createRecognizer();
    }

    /**
     * Starts the listener.
     */
    public void start() {
        this.recognizer.startRecognition(true);
    }

    /**
     * Stops the listener.
     */
    public void stop() {
        try {
            recognizer.stopRecognition();
        } catch (Exception e) {
            XavierApplication.LOGGER.error("Xavier: There was an error stopping the recorder");
        }
    }

    /**
     * Listens for the hot/wake word.
     */
    public  void listen() {

        while ((this.result = this.recognizer.getResult()) != null) {
            System.out.println("Hypo: " + this.result.getHypothesis());

            // Get individual words and their times.
            for (WordResult r : this.result.getWords()) {
                System.out.println("word: " + r);
                // Pause recognition process. It can be resumed then with startRecognition(false).
                recognizer.stopRecognition();
                this.wakeUpAction();
                recognizer.startRecognition(false);
            }
        }


        XavierApplication.LOGGER.info("Xavier is done recording...");
    }

    public void run() {

    }

    /**
     * Use API to capture mic audio.
     */
    protected void wakeUpAction(){

    }
}
