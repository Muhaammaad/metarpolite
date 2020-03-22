package com.muhaammaad.metarpolite;

import com.muhaammaad.metarpolite.global.constant.Constants;
import com.muhaammaad.metarpolite.persistence.entity.Station;
import com.muhaammaad.metarpolite.ui.main.util.AviationResponseParser;

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


/**
 * Station XML Parser unit test, which will execute on the development machine.
 */
public class StationParserUnitTest {
    private static final String XML_SAMPLE =
            "<Station>\n" +
                    "<station_id>KDEN</station_id>\n" +
                    "<wmo_id>72565</wmo_id>\n" +
                    "<latitude>39.85</latitude>\n" +
                    "<longitude>-104.65</longitude>\n" +
                    "<elevation_m>1640.0</elevation_m>\n" +
                    "<site>DENVER (DIA)</site>\n" +
                    "<state>CO</state>\n" +
                    "<country>US</country>\n" +
                    "<site_type>\n" +
                    "<METAR/>\n" +
                    "<TAF/>\n" +
                    "</site_type>\n" +
                    "</Station>";

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
    public void readStation() {
        try {
            Station station = AviationResponseParser.readStation(parser, Constants.AviationResponseTags.TAG_STATIONS);
            Assert.assertNotNull(station);
            Assert.assertNotNull(station.site);
            Assert.assertNotNull(station.country);
            Assert.assertNotNull(station.latitude);
            Assert.assertNotNull(station.longitude);
            Assert.assertNotNull(station.stationId);
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
