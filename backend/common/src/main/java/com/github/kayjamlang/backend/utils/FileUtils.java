package com.github.kayjamlang.backend.utils;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileUtils {
    public static String read(File file) throws IOException {
        Scanner reader = new Scanner(file, UniversalDetector.detectCharset(file));
        StringBuilder value = new StringBuilder();
        while (reader.hasNextLine()) {
            value.append(reader.nextLine()).append("\n");
        }
        reader.close();


        value = new StringBuilder(value.toString().replaceAll("\r", "")
                .replaceAll("\t", ""));
        if (value.toString().startsWith("\uFEFF")) {
            value = new StringBuilder(value.substring(1));
        }

        return value.toString();
    }
}
