package com.grandtaiga.closedcaption.wordchunk;

/**
 * Created by NoahButler on 7/27/16.
 */
public class WordChunk {

    private String startTime;
    private String endTime;
    private String entry;

    public WordChunk(String startTime, String endTime, String entry) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.entry = entry;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getEntry() {
        return entry;
    }
}
