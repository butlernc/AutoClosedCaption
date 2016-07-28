package com.grandtaiga.closedcaption.utils;

/**
 * Created by Noah Butler on 7/28/2016.
 * Cloned from hiteshsahu Android-TTS-STT
 */
public interface ConversionCallback {

    void onSuccess(String result);

    void onCompletion();

    void onErrorOccured(String errorMessage);

}
