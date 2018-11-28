package com.tier1.translator.translation;


/**
 * Wrapper for language info supported by app
 */
public enum AppLangInfo {

    GERMAN("de", Language.GERMAN),
    ENGLISH("en", Language.ENGLISH),
    SPANISH("es", Language.SPANISH),
    MEXICAN("es_419", Language.SPANISH),
    FRENCH("fr", Language.FRENCH),
    HINDI("hi", Language.HINDI),
    ITALIAN("it", Language.ITALIAN),
    JAPANESE("ja", Language.JAPANESE),
    POLISH("pl", Language.POLISH),
    CHINESE("zh_CN", Language.CHINESE),
    ARABIC("ar", Language.ARABIC),
    BELARUSIAN("be", Language.BELARUSIAN),
    BULGARIAN("bg", Language.BULGARIAN),
    CZECH("cs", Language.CZECH),
    DANISH("da", Language.DANISH),
    DUTCH("nl", Language.DUTCH),
    FINNISH("fi", Language.FINNISH),
    GREEK("el", Language.GREEK),
    HUNGARIAN("hu", Language.HUNGARIAN),
    MACEDONIAN("mk", Language.MACEDONIAN),
    PORTUGUESE("pt", Language.PORTUGUESE),
    RUSSIAN("ru", Language.RUSSIAN),
    SWEDISH("sv", Language.SWEDISH);

    private String fileAppendix;
    private Language language;

    AppLangInfo(String fileAppendix, Language language) {

        this.fileAppendix = fileAppendix;
        this.language = language;
    }

    public String getFileAppendix() {
        return fileAppendix;
    }

    public Language getLanguage() {
        return language;
    }
}
