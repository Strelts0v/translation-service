package com.tier1.translator.main;

import com.tier1.translator.concurrency.Translator;
import com.tier1.translator.translation.AppLangInfo;
import com.tier1.translator.util.CustomFileUtils;
import com.tier1.translator.util.Settings;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    private static Properties sourceProperties;
    private static FileInputStream propertiesInputStream = null;

    public static void main(String[] args) {

        sourceProperties = new Properties();
        try {
            executeDefaultTranslationLogic();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (propertiesInputStream != null) {
                try {
                    propertiesInputStream.close();
                } catch (IOException ioe) {
                    log.error(ioe.getMessage(), ioe);
                }
            }
        }
    }

    private static void executeDefaultTranslationLogic() throws IOException {

        String filePath =
                Settings.messagesFileName + "_" +
                AppLangInfo.ENGLISH.getFileAppendix() +
                Settings.messagesFileExtension;

        propertiesInputStream = new FileInputStream(filePath);
        sourceProperties.load(propertiesInputStream);

        launchTranslationService(sourceProperties);
    }

    private static void launchTranslationService(Properties sourceProperties) {

        ExecutorService executorService = Executors.newFixedThreadPool(Settings.executorServicePoolSize);

        try {
            for (AppLangInfo appLangInfo : AppLangInfo.values()) {
                if (!appLangInfo.equals(AppLangInfo.ENGLISH)) {

                    final String destinationPath = "";
                    final String fileName =
                            Settings.messagesFileName + "_" +
                            appLangInfo.getFileAppendix() +
                            Settings.messagesFileExtension;

                    CustomFileUtils.createFile(destinationPath, fileName);

                    executorService.submit(new Translator(sourceProperties, appLangInfo));
                }
            }
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        }

        executorService.shutdown();
    }
}
