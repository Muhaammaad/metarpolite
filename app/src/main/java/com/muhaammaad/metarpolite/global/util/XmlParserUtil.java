package com.muhaammaad.metarpolite.global.util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Xml parsing global utils
 */
public class XmlParserUtil {
    public static String ns = null;

    /**
     * Processes tags.
     */
    public static String readTag(XmlPullParser parser, String TAG) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG);
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG);
        return summary;
    }

    /**
     * For the tags, extracts their text values.
     */
    public static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    /**
     * Skips the unwanted tags.
     */
    public static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
