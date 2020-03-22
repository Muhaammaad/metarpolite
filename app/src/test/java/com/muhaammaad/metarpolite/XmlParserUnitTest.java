package com.muhaammaad.metarpolite;

import com.muhaammaad.metarpolite.global.util.XmlParserUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

/**
 * XML Parser utils unit test, which will execute on the development machine.
 */
public class XmlParserUnitTest {

    private static final String TAG_TYPE = "type";
    private static final String CONTENT_TYPE = "METAR";
    private static final String XML_SAMPLE = "<".concat(TAG_TYPE).concat(">").concat(CONTENT_TYPE).concat("</").concat(TAG_TYPE).concat(">");

    private XmlPullParser parser;

    @Before
    public void createParser() throws XmlPullParserException, IOException {
        InputStream inputStream = new ByteArrayInputStream(XML_SAMPLE.getBytes(Charset.forName("UTF-8")));
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        parser = factory.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(inputStream, null);
        int eventType = parser.next();
        while (eventType != XmlPullParser.START_TAG) {
            eventType = parser.next();
        }
    }

    @Test
    public void readTag() {
        try {
            assertEquals(XmlParserUtil.readTag(parser, TAG_TYPE), CONTENT_TYPE);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void readContent() {
        try {
            assertEquals(XmlParserUtil.readText(parser), CONTENT_TYPE);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void skipTag() {
        try {
            int attributeCount = parser.getAttributeCount();
            XmlParserUtil.skip(parser);
            int attributeCountAfterSkipping = parser.getAttributeCount();
            assertNotEquals(attributeCount, attributeCountAfterSkipping);
            assertEquals(attributeCount - 1, attributeCountAfterSkipping);
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
