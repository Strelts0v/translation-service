package com.tier1.translator.util;

import com.tier1.translator.translation.Language;

import java.net.URL;
import java.net.URLEncoder;

/**
 * Makes calls to the Yandex machine translation web service API
 */
public final class YandexTranslationAPICaller extends YandexTranslationAPI {

    private static final String SERVICE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
    private static final String TRANSLATION_LABEL = "text";
    private static final Integer TEXT_MAX_LENGTH = 10240;

    private YandexTranslationAPICaller(){
    }

    /**
     * Translates text from a given Language to another given Language using Yandex.
     *
     * @param text The String to translate.
     * @param from The language code to translate from.
     * @param to The language code to translate to.
     * @return The translated String.
     * @throws Exception on error.
     */
    public static String execute(final String text, final Language from, final Language to) throws Exception {

        validateServiceState(text);
        final String params =
                PARAM_API_KEY + URLEncoder.encode(apiKey, ENCODING) +
                PARAM_LANG_PAIR + URLEncoder.encode(from.toString(), ENCODING) +
                URLEncoder.encode("-", ENCODING) + URLEncoder.encode(to.toString(), ENCODING) +
                PARAM_TEXT + URLEncoder.encode(text, ENCODING);

        final URL url = new URL(SERVICE_URL + params);

        return retrievePropArrString(url, TRANSLATION_LABEL).trim();
    }

    private static void validateServiceState(final String text) throws Exception {

        final int byteLength = text.getBytes(ENCODING).length;
        if (byteLength > TEXT_MAX_LENGTH) {
            throw new RuntimeException("TEXT_TOO_LARGE");
        }

        validateServiceState();
    }
}
