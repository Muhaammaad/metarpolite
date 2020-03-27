package com.muhaammaad.metarpolite.global.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Utils for {@Link InputStream}
 */
public class InputStreamUtil {

    /**
     * Util to get stream from string
     *
     * @param str String to be converted in input stream
     * @return InputStream
     */
    public static InputStream fromString(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }
}
