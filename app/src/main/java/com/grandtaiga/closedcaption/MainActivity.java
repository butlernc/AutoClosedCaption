package com.grandtaiga.closedcaption;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.grandtaiga.closedcaption.speech.SpeechController;
import com.grandtaiga.closedcaption.speech.TranslatorFactory;
import com.grandtaiga.closedcaption.utils.ConversionCallback;
import com.grandtaiga.closedcaption.wordchunk.WordChunk;
import com.grandtaiga.closedcaption.wordchunk.WordChunkCreator;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity implements ConversionCallback {

    Chronometer chronometer;
    Button start, in, out;
    TextView textDisplay;

    static String startTime = "";
    static int startIndex, endIndex = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    static int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    boolean started = false;
    boolean message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpeechController.setParams(getBaseContext(), this);


        chronometer = (Chronometer)findViewById(R.id.timer_display);

        textDisplay = (TextView)findViewById(R.id.text_display);

        start = (Button)findViewById(R.id.start_speech);
        in    = (Button)findViewById(R.id.start_cc_button);
        out   = (Button)findViewById(R.id.out_cc_button);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!started) {
                    started = true;
                    //zero out the chronometer
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    start.setText("Stop");
                    TranslatorFactory.getInstance().getTranslator(TranslatorFactory.TRANSLATOR_TYPE.SPEECH_TO_TEXT, MainActivity.this).initialize("Hello There", MainActivity.this);
//                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                    intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 100000);
//                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
//                            "Play Video");
//                    try {
//                        startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
//                    } catch (ActivityNotFoundException a) {
//                        Toast.makeText(getBaseContext(),
//                                "Not supported",
//                                Toast.LENGTH_SHORT).show();
//                    }
                }else{
                    started = false;
                    chronometer.stop();
                    Log.d("END TIME", chronometer.getText().toString());
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    start.setText("Start");
                }
            }
        });

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWordChunk(chronometer.getText().toString());
                startIndex = SpeechController.getCurrentTextIndex();
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endWordChunk(chronometer.getText().toString(), startIndex, endIndex);
                startIndex = SpeechController.getCurrentTextIndex();
            }
        });

    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textDisplay.setText(result.get(0));
                    Log.d("Text", result.get(0));
                }
                break;
            }

        }
    }

    @Override
    public void onSuccess(String result) {
        Toast.makeText(this, "Result " + result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCompletion() {
        Toast.makeText(this, "Done ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onErrorOccured(String errorMessage) {
        Toast.makeText(this, "Error " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void startWordChunk(String time) {
        startTime = time;
    }

    private void endWordChunk(String endTime, int startIndex, int endIndex) {
        String cc = (textDisplay.getText().subSequence(startIndex, endIndex)).toString();
        String[] params = {startTime, endTime, cc};
        WordChunkCreator.create(params);
    }

    private void checkPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_REQUEST_RECORD_AUDIO);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}
