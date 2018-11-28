package com.tier1.translator.translation;

import com.tier1.translator.util.Settings;
import com.tier1.translator.util.YandexTranslationAPICaller;
import org.apache.log4j.Logger;

public class TranslationService {

    private static final Logger log = Logger.getLogger(TranslationService.class);

    private AppLangInfo sourceAppLangInfo;
    private AppLangInfo targetAppLangInfo;

    public TranslationService(AppLangInfo sourceAppLangInfo, AppLangInfo targetAppLangInfo) {

        this.sourceAppLangInfo = sourceAppLangInfo;
        this.targetAppLangInfo = targetAppLangInfo;
    }

    public AppLangInfo getSourceAppLangInfo() {

        return sourceAppLangInfo;
    }

    public void setSourceAppLangInfo(AppLangInfo sourceAppLangInfo) {

        this.sourceAppLangInfo = sourceAppLangInfo;
    }

    public AppLangInfo getTargetAppLangInfo() {

        return targetAppLangInfo;
    }

    public void setTargetAppLangInfo(AppLangInfo targetAppLangInfo) {

        this.targetAppLangInfo = targetAppLangInfo;
    }

    public String translate(String sourceMessage) {

        String translatedMessage;
        try {
            YandexTranslationAPICaller.setApiKey(Settings.yandexApiKey);

            translatedMessage = YandexTranslationAPICaller.execute(
                    sourceMessage,
                    sourceAppLangInfo.getLanguage(),
                    targetAppLangInfo.getLanguage()
            );
        } catch (Exception e) {
            if (e.getMessage().contains("403")) {
                log.error(String.format("Access error! API is not available for key %s", Settings.yandexApiKey));
                throw new RuntimeException(e);
            } else {
                translatedMessage = sourceMessage;
            }
        }

        return translatedMessage;
    }
}
