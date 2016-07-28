package com.grandtaiga.closedcaption.wordchunk;

import java.util.ArrayList;

/**
 * Created by NoahButler on 7/27/16.
 */
public class WordChunkHandler {

    private static ArrayList<WordChunk> wordChunks;

    public static void forgeWordChunks() {
        wordChunks = new ArrayList<>();
    }

    public static void add(WordChunk wordChunk) {
        wordChunks.add(wordChunk);
    }

    public static WordChunk get(int index) {
        return wordChunks.get(index);
    }

    public static int currentIndex() {
        return wordChunks.size() -1;
    }
}
