package com.grandtaiga.closedcaption.speech;

import com.grandtaiga.closedcaption.utils.ConversionCallback;
import com.grandtaiga.closedcaption.utils.IConverter;

/**
 * Created by Noah Butler on 7/28/2016.
 */
public class TranslatorFactory {

    private static TranslatorFactory ourInstance = new TranslatorFactory();

    private TranslatorFactory() {
    }

    public static TranslatorFactory getInstance() {
        return ourInstance;
    }

    public IConverter getTranslator(TRANSLATOR_TYPE translator_type, ConversionCallback callback) {
        switch (translator_type) {
            case TEXT_TO_SPEECH:
                //return new TextToSpechConvertor(conversionCallaback);

            case SPEECH_TO_TEXT:
                return new Intake(callback);
        }

        return null;
    }


    public enum TRANSLATOR_TYPE {TEXT_TO_SPEECH, SPEECH_TO_TEXT}

}
