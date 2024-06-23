package com.lichenaut.plantnerfer.util;

import java.io.*;

public class Copier {

    public static void smallCopy(InputStream in, String outFilePath) throws IOException {
        try (BufferedInputStream bufferedIn = new BufferedInputStream(in);
                FileOutputStream out = new FileOutputStream(outFilePath);
                BufferedOutputStream bufferedOut = new BufferedOutputStream(out)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bufferedIn.read(buffer)) != -1) {
                bufferedOut.write(buffer, 0, bytesRead);
            }
        }
    }
}
