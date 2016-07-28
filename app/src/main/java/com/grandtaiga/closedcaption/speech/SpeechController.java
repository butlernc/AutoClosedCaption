package com.grandtaiga.closedcaption.speech;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import com.grandtaiga.closedcaption.R;
import com.grandtaiga.closedcaption.thread_utils.Messenger;

import java.util.Locale;

/**
 * Created by NoahButler on 7/27/16.
 */
public class SpeechController {

    static Messenger messenger = new Messenger();

    //Create connection between speech rec and a string builder
    public static int getCurrentTextIndex() {
        return Intake.getCurrentTextIndex();
    }

    public static void startIntake() {
        Intake.startIntake();

    }

    public static void setParams(Context c, Activity a) {
        Intake.setParams(c, a);
    }



}
