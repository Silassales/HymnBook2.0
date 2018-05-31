package com.gmail.timothy10.silas.hymnbook.model;

import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipInputStreamImpl {

    public void unzipFile(InputStream inputStream, FileOutputStream outputStream) throws IOException {
        // create a buffer to improve copy performance later.
        byte[] buffer = new byte[2048];

        // open the zip file stream
        ZipInputStream zippedStream = new ZipInputStream(inputStream);

        // now iterate through each item in the stream. The get next
        // entry call will return a ZipEntry for each file in the
        // stream
        ZipEntry entry;
        while ((entry = zippedStream.getNextEntry()) != null) {
            String message = String.format("Entry: %s len %d added %TD", entry.getName(), entry.getSize(),
                    new Date(entry.getTime()));
            Log.i("Info", message);

            if(Objects.equals(entry.getName(), "p44.xml")) {
                Log.i("Info", "Found container, actually writing to xml");
                int len;
                while ((len = zippedStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
            }

        }
    }
}
