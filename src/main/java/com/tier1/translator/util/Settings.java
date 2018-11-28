package com.tier1.translator.util;

import java.util.ResourceBundle;

public class Settings {

    /** object for extracting properties from resource bundle settings.properties */
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("settings");

    public static final String messagesFileName;
    public static final String messagesFileExtension;
    public static final String yandexApiKey;
    public static final Integer executorServicePoolSize;

    static {
        messagesFileName = resourceBundle.getString("messages.file.name");
        messagesFileExtension = resourceBundle.getString("messages.file.extension");
        yandexApiKey = resourceBundle.getString("yandex.api.key");
        executorServicePoolSize = Integer.valueOf(resourceBundle.getString("executor.service.pool.size"));
    }

}
