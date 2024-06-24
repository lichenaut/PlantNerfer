package com.lichenaut.plantnerfer.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Copier {

    public static void smallCopy(InputStream in, String outFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
             Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFilePath), StandardCharsets.UTF_8))) {
            char[] buffer = new char[1024];
            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, charsRead);
            }
        }
    }
}
