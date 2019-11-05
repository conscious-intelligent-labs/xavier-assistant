package com.consciousntelligentlabs.xavier.detection;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public interface HotWord extends Runnable {

    public static long recordTime = 60000;  // 1 minute

    public void create() throws Exception;

    public void start() throws LineUnavailableException, Exception;

    public void stop() throws Exception;

    public void listen();

}
