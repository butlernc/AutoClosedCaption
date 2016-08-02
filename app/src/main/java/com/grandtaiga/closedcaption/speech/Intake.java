package com.grandtaiga.closedcaption.speech;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.grandtaiga.closedcaption.MainActivity;
import com.grandtaiga.closedcaption.R;
import com.grandtaiga.closedcaption.thread_utils.MessengerHandler;
import com.grandtaiga.closedcaption.utils.ConversionCallback;
import com.grandtaiga.closedcaption.utils.IConverter;
import com.grandtaiga.closedcaption.utils.TranslatorUtils;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by NoahButler on 7/27/16.
 */
public class Intake implements IConverter {

    public static int currentTextIndex = 0;
    public static Context context;
    private static Activity activity;
    private ConversionCallback conversionCallback;
    private ArrayList<String> data;
    private int spliceSize = 120; //120 characters

    private static void tick() {
        currentTextIndex++;
    }
    public static void setParams(Context c, Activity a) {
        context = c;
        activity = a;
    }
    public static void startIntake() {

    }
    public static int getCurrentTextIndex() {
        return currentTextIndex;
    }

    public Intake(ConversionCallback callback) {
        this.conversionCallback = callback;
    }

    /**
     * Take speech input and convert it back as text
     */
    @Override
    public Intake initialize(String message, Activity activity) {

        //Prepeare Intent
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                message);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                activity.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 10000);

        Log.d("PACKAGE", activity.getPackageName());

        //Add listeners
        CustomRecognitionListener listener = new CustomRecognitionListener();
        SpeechRecognizer sr = SpeechRecognizer.createSpeechRecognizer(activity);
        sr.setRecognitionListener(listener);

        sr.startListening(intent);

        return this;
    }


    class CustomRecognitionListener implements RecognitionListener {
        private static final String TAG = "RecognitionListener";

        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "onReadyForSpeech");
        }

        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech");
        }

        public void onRmsChanged(float rmsdB) {
            Log.d(TAG, "onRmsChanged");
        }

        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived");
        }

        public void onEndOfSpeech() {
            Log.d(TAG, "onEndofSpeech");
        }

        public void onError(int error) {
            Log.e(TAG, "error " + error);

            conversionCallback.onErrorOccured(TranslatorUtils.getErrorText(error));
        }

        public void onResults(Bundle results) {
            String str = new String();
            Log.d(TAG, "onResults " + results);
            data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String spliced = "";
            for (int i = 0; i < data.size(); i++) {
                str += data.get(i);
            }
            Log.d("str", data.get(0));
            Log.d("Amount of lines", "Amount: " + Math.ceil(str.length()/spliceSize));

            int data_len = data.get(0).length();
            for(int i = 0; i < Math.ceil(data_len/spliceSize); i++) { //find out how many splices we need to make
                int end = ((i+1)*spliceSize) - 1;
                if(end > data_len) {
                    end = data_len - 1;
                }
                spliced += data.get(0).substring(i*spliceSize, end);
                spliced += "\n";
            }

            Log.d("Copy", spliced);

            conversionCallback.onSuccess(spliced);
        }

        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults");
        }

        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent " + eventType);
        }


    }

}
