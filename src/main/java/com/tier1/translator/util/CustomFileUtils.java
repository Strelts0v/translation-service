package com.tier1.translator.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

public class CustomFileUtils {

    public static File createFile(String destinationPath, String fileName) throws IOException{

        File newFile;

        if (StringUtils.isBlank(destinationPath)) {
            newFile = new File(fileName);
        } else {
            newFile = new File(destinationPath + File.separator + fileName);
        }

        FileUtils.touch(newFile);

        return newFile;
    }
}
