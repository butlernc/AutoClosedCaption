package com.grandtaiga.closedcaption.wordchunk;

/**
 * Created by NoahButler on 7/27/16.
 */
public class WordChunkCreator {

    public static int create(String...strings){
        WordChunk wordChunk = new WordChunk(strings[0], strings[1], strings[2]);
        WordChunkHandler.add(wordChunk);
        return WordChunkHandler.currentIndex();
    }

}
