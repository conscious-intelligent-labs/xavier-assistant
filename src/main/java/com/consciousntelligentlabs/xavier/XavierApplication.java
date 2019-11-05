package com.consciousntelligentlabs.xavier;

import com.consciousntelligentlabs.xavier.detection.snowboy.WakeSnowBoy;
import com.consciousntelligentlabs.xavier.detection.sphinx.WakeSphinx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class XavierApplication  {

    public static final Logger LOGGER = LoggerFactory.getLogger(XavierApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(XavierApplication.class, args);
    }

    @PostConstruct
    protected void init () {
        Thread t1 = new Thread(new WakeSnowBoy ());
        t1.start();
    }

    private WakeSnowBoy snowboy() throws Exception {
        LOGGER.info("Starting Xavier: Snowboy...");
        WakeSnowBoy snowBoy = new WakeSnowBoy();
        snowBoy.create();
        snowBoy.start();
        snowBoy.listen();

        return snowBoy;
    }


    private void sphinx() throws Exception {
        LOGGER.info("Starting Xavier: Sphinx...");
        WakeSphinx wake = new WakeSphinx();

        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(WakeSphinx.recordTime);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                LOGGER.info("Time is up. Xavier is done recording...");
                wake.stop();
            }
        });

        stopper.start();

        try {
            wake.setup(false);
            wake.start();
            wake.listen();
        } catch (Exception e) {
            LOGGER.error("Xavier: There was an error starting the listener. " + e.getMessage());
        }




        // Microsoft wake words
            //Ears.keywordTriggeredSpeechRecognitionWithMicrophone();

/*
        Service stt = new Service(
                "https://centralus.stt.speech.microsoft.com/speech/recognition/conversation/cognitiveservices/v1?language=en-US",
                "e4ea55510f3a4c7eaa0fd3e4a48f096f",
                "centralus"
        );

        stt.run();

*/
    }

}
