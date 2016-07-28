package com.grandtaiga.closedcaption.speech;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import com.grandtaiga.closedcaption.R;
import com.grandtaiga.closedcaption.thread_utils.MessengerHandler;

import java.util.Locale;

/**
 * Created by NoahButler on 7/27/16.
 */
public class Intake extends RecognitionService {

    public static MessengerHandler handler = new MessengerHandler();
    public static int currentTextIndex = 0;
    public static Context context;
    private static int REQ_CODE_SPEECH_INPUT = 100;
    private static Activity activity;

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

    @Override
    protected void onStartListening(Intent recognizerIntent, Callback listener) {

    }

    @Override
    protected void onCancel(Callback listener) {

    }

    @Override
    protected void onStopListening(Callback listener) {

    }
}
