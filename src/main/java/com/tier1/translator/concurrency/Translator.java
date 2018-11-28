package com.tier1.translator.concurrency;

import com.tier1.translator.translation.AppLangInfo;
import com.tier1.translator.translation.TranslationService;
import com.tier1.translator.util.Settings;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Translator implements Runnable {

    private static final Logger log = Logger.getLogger(Translator.class);

    private final Properties sourceProperties;
    private final AppLangInfo appLangInfo;

    public Translator(Properties sourceProperties, AppLangInfo appLangInfo) {
        this.sourceProperties = sourceProperties;
        this.appLangInfo = appLangInfo;
    }

    @Override
    public void run() {

        log.info(String.format("Launching translation for %s language.", appLangInfo.toString()));

        addMessagesToTargetFile(appLangInfo, sourceProperties.entrySet());

        log.info(String.format("Translation for %s language has been finished.", appLangInfo.toString()));
    }

    private void addMessagesToTargetFile(AppLangInfo appLangInfo, Set<Map.Entry<Object, Object>> entries) {

        try (FileOutputStream outputStream = new FileOutputStream(
                 Settings.messagesFileName +
                        "_" +appLangInfo.getFileAppendix() +
                        Settings.messagesFileExtension)) {

            Properties properties = new Properties();
            TranslationService translationService = new TranslationService(AppLangInfo.ENGLISH, appLangInfo);

            for (Map.Entry<Object, Object> entry : entries) {
                String translatedMessage = translationService.translate(String.valueOf(entry.getValue()));
                properties.setProperty(String.valueOf(entry.getKey()), translatedMessage);
            }

            String comments = null;
            properties.store(outputStream, comments);

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
