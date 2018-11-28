package com.tier1.translator.translation;

/**
 * Language - an enum of language codes supported by the Yandex API
 */
public enum Language {

    ARABIC("ar"),
    BELARUSIAN("be"),
    BENGAL("bn"),
    BULGARIAN("bg"),
    BOSNIAN("bs"),
    CATALAN("ca"),
    CHINESE("zh"),
    CZECH("cs"),
    DANISH("da"),
    DUTCH("nl"),
    ENGLISH("en"),
    ESTONIAN("et"),
    FINNISH("fi"),
    FRENCH("fr"),
    GERMAN("de"),
    GREEK("el"),
    HINDI("hi"),
    HUNGARIAN("hu"),
    ITALIAN("it"),
    LATVIAN("lv"),
    LITHUANIAN("lt"),
    MACEDONIAN("mk"),
    NORWEGIAN("no"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    RUSSIAN("ru"),
    SLOVAK("sk"),
    SPANISH("es"),
    SWEDISH("sv"),
    JAPANESE("ja"),
    WELSH("cy");

    /**
     * String representation of this language.
     */
    private final String language;

    Language(final String pLanguage) {
        language = pLanguage;
    }

    public static Language fromString(final String pLanguage) {
        for (Language l : values()) {
            if (l.toString().equals(pLanguage)) {
                return l;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return language;
    }

}
