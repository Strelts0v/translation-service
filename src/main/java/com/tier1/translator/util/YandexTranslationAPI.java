package com.tier1.translator.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Makes the generic Yandex API calls. Different service classes can then
 * extend this to make the specific service calls.
 */
public abstract class YandexTranslationAPI {

    protected static String apiKey;

    protected static final String ENCODING = "UTF-8";
    protected static final String PARAM_API_KEY = "key=";
    protected static final String PARAM_LANG_PAIR = "&lang=";
    protected static final String PARAM_TEXT = "&text=";
    protected static final Integer API_KEY_LENGTH = 27;
    protected static final Integer HTTP_SUCCESS_STATUS = 200;

    private static String referrer;

    public static void setApiKey(final String pKey) {
        apiKey = pKey;
    }

    public static void setReferrer(final String pReferrer) {
        referrer = pReferrer;
    }

    /**
     * Forms an HTTPS request, sends it using GET method and returns the result of the request as a String.
     *
     * @param url The URL to query for a String response.
     * @return The translated String.
     * @throws Exception on error.
     */
    private static String retrieveResponse(final URL url) throws Exception {

        final HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        if(referrer != null) {
            urlConnection.setRequestProperty("referer", referrer);
        }
        urlConnection.setRequestProperty("Content-Type","text/plain; charset=" + ENCODING);
        urlConnection.setRequestProperty("Accept-Charset",ENCODING);
        urlConnection.setRequestMethod("GET");

        try {
            final int responseCode = urlConnection.getResponseCode();
            final String result = inputStreamToString(urlConnection.getInputStream());
            if(responseCode != HTTP_SUCCESS_STATUS) {
                throw new RuntimeException("Error from Yandex API: " + result);
            }
            return result;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    /**
     * Forms a request, sends it using the GET method and returns the contents of the array of strings
     * with the given label, with multiple strings concatenated.
     */
    protected static String retrievePropArrString(final URL url, final String jsonValProperty) throws Exception {

        final String response = retrieveResponse(url);
        String[] translationArr = jsonObjValToStringArr(response, jsonValProperty);
        String combinedTranslations = "";
        for (String s : translationArr) {
            combinedTranslations += s;
        }
        return combinedTranslations.trim();
    }

    /**
     * Helper method to parse a JSONObject containing an array of Strings with the given label.
     */
    private static String[] jsonObjValToStringArr(final String inputString, final String subObjPropertyName)
            throws Exception {

        JSONObject jsonObj = (JSONObject)JSONValue.parse(inputString);
        JSONArray jsonArr = (JSONArray) jsonObj.get(subObjPropertyName);
        return jsonArrToStringArr(jsonArr.toJSONString(), null);
    }

    /**
     * Helper method to parse a JSONArray. Reads an array of JSONObjects and returns a String Array
     * containing the toString() of the desired property. If propertyName is null, just return the String value.
     */
    private static String[] jsonArrToStringArr(final String inputString, final String propertyName)
            throws Exception {

        final JSONArray jsonArr = (JSONArray)JSONValue.parse(inputString);
        String[] values = new String[jsonArr.size()];

        int i = 0;
        for (Object obj : jsonArr) {
            if (propertyName!=null&&propertyName.length()!=0) {
                final JSONObject json = (JSONObject)obj;
                if (json.containsKey(propertyName)) {
                    values[i] = json.get(propertyName).toString();
                }
            } else {
                values[i] = obj.toString();
            }
            i++;
        }
        return values;
    }

    /**
     * Reads an InputStream and returns its contents as a String.
     * Also effects rate control.
     * @param inputStream The InputStream to read from.
     * @return The contents of the InputStream as a String.
     * @throws Exception on error.
     */
    private static String inputStreamToString(final InputStream inputStream) {
        final StringBuilder outputBuilder = new StringBuilder();

        try {
            String string;
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, ENCODING));
                while (null != (string = reader.readLine())) {
                    // Need to strip the Unicode Zero-width Non-breaking Space. For some reason, the Microsoft AJAX
                    // services prepend this to every response
                    outputBuilder.append(string.replaceAll("\uFEFF", ""));
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("[yandex-translator-api] Error reading translation stream.", ex);
        }
        return outputBuilder.toString();
    }

    /**
     * Check if ready to make request, if not, throw a RuntimeException
     */
    protected static void validateServiceState() {
        if (apiKey == null || apiKey.length() < API_KEY_LENGTH) {
            throw new RuntimeException("INVALID_API_KEY - Please set the API Key with your Yandex API Key");
        }
    }

}
