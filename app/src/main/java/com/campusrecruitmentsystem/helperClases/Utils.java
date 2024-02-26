package com.campusrecruitmentsystem.helperClases;

import android.content.Context;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static byte[] uriToBytes(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // Read bytes from the InputStream and write them into the ByteArrayOutputStream
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        // Convert ByteArrayOutputStream to byte array
        byte[] bytes = byteBuffer.toByteArray();

        // Close the InputStream
        inputStream.close();

        return bytes;
    }
}

