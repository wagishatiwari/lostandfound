package com.it.rabo.lostandfound.util;

import com.it.rabo.lostandfound.service.impl.LostItemsServiceImplTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileUtil {
    public static  File getFileFromResource(String fileName) throws IOException {
        ClassLoader classLoader = LostItemsServiceImplTest.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("com/it/rabo/lostandfound/service/impl/" +fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found! " + fileName);
            }
            File tempFile = File.createTempFile("temp", null);
            Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return tempFile;
        }
    }
}
